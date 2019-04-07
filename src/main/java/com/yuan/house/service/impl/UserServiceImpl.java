package com.yuan.house.service.impl;

import com.yuan.house.constants.ResultEnum;
import com.yuan.house.dao.UserDao;
import com.yuan.house.model.User;
import com.yuan.house.service.PermissionService;
import com.yuan.house.service.UserService;
import com.yuan.house.util.LoggerUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserDao userDao;

	@Autowired
	private PermissionService permissionService;

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
		return userDao.getUser(username, password);
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
}
