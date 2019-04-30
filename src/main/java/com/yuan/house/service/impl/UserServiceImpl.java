package com.yuan.house.service.impl;

import com.yuan.house.config.websocket.WebSocketConfig;
import com.yuan.house.constants.ResultEnum;
import com.yuan.house.dao.UserDao;
import com.yuan.house.model.User;
import com.yuan.house.model.UserRole;
import com.yuan.house.service.CommonService;
import com.yuan.house.service.PermissionService;
import com.yuan.house.service.UserService;
import com.yuan.house.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserDao userDao;

    @Autowired
    private CommonService commonService;

	@Autowired
	private PermissionService permissionService;

    @Autowired
    WebSocketConfig webSocketConfig;

	/**
	 * 用户登录
	 */
	@Override
	public String userLogin(String username, String password) {
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			currentUser.login(token);
			return ResultEnum.R_success.getResCode();
		} catch (UnknownAccountException e) {
			LoggerUtil.error("用户不存在异常：",e);
			return ResultEnum.R_wrong.getResCode();
		} catch (AuthenticationException e) {
		    LoggerUtil.error("用户登录异常：",e);
            return ResultEnum.R_wrong.getResCode();
		}
	}

	/**
	 * 根据用户名和密码查询对应的用户
	 */
	@Override
	public User getUser(String username, String password) {
	    System.out.println("username="+username);
        System.out.println("password="+password);
		User user = userDao.getUser(username, password);
		System.out.println(user.getUsername());
		return user;
    }


	/**
	 * 用户注销
	 */
	@Override
	public String logout() {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
            return ResultEnum.R_success.getResCode();
		} catch (Exception e) {
		    LoggerUtil.error("用户注销失败：",e);
            return ResultEnum.R_error.getResCode();
		}
	}

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User queryUserById(Long id) {
	    System.out.println("1执行:"+id);
        String key = "user_" + id;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (User)rs;
        }
        User user = userDao.queryUserById(id);
        commonService.insertRedis(key, user);
        return user;
    }

    @Override
    public User queryUserByName(String name) {
        return userDao.queryUserByName(name);
    }

    @Override
    public List<User> queryUserLikeMsg(String msg) {
        String key = "user_like_" + msg;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<User>)rs;
        }
        List<User> users = userDao.queryUserLikeMsg(msg);
        commonService.insertRedis(key, users);
        return users;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addUser(User object, String rid) {
        userDao.addUser(object);
        if(StringUtils.isNotBlank(rid)){
            String[] rds = rid.split(",");
            UserRole ur = new UserRole();
            User user = userDao.queryUserByName(object.getUsername());
            ur.setUserId(user.getUserId());
            for(String rs: rds) {
                System.out.println("9执行：roleId="+rs);
                ur.setRoleId(Long.parseLong(rs));
                userDao.addUserRole(ur);
            }
        }
    }

    @Override
    public List<User> getUsersByRole(String roleName) {
        return userDao.getUsersByRole(roleName);
    }


    @Override
    public int updateUser(User object, String rid) {
        String key = "user_" + object.getUserId();
        commonService.deleteRedis(key);
        int rs= userDao.updateUser(object);
        userDao.deleteUserRole(object.getUserId());
        if(StringUtils.isNotBlank(rid)){
            String[] rds = rid.split(",");
            UserRole rp = new UserRole();
            rp.setUserId(object.getUserId());
            for(String ps: rds) {
                rp.setRoleId(Long.parseLong(ps));
                userDao.addUserRole(rp);
            }
        }
        return rs;
    }

    @Override
    public int deleteUser(Long id) {
        String key = "user_" + id;
        commonService.deleteRedis(key);
        return userDao.deleteUser(id);
    }

}
