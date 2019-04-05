package com.yuan.house.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.constants.Constants;
import com.yuan.house.constants.ResultEnum;
import com.yuan.house.constants.TypeEnum;
import com.yuan.house.dao.UserDao;
import com.yuan.house.service.PermissionService;
import com.yuan.house.service.UserService;
import com.yuan.house.util.LoggerUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
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
	public JSONObject userLogin(JSONObject jsonObject) {
		String username = jsonObject.getString("username");
		String password = jsonObject.getString("password");
		JSONObject res = new JSONObject();
        res.put("type", TypeEnum.T_result.getTypeCode());
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			currentUser.login(token);
            res.put("code", ResultEnum.R_success.getResCode());
            res.put("msg", ResultEnum.R_success.getResMsg());
		} catch (AuthenticationException e) {
		    LoggerUtil.error("用户登录异常：",e);
            res.put("code", ResultEnum.R_wrong.getResCode());
            res.put("msg", ResultEnum.R_wrong.getResMsg());
		}
		return res;
	}

	/**
	 * 根据用户名和密码查询对应的用户
	 */
	@Override
	public JSONObject getUser(String username, String password) {
		JSONObject rs = new JSONObject();
		rs.put("type", TypeEnum.T_object.getTypeCode());
		rs.put("user", userDao.getUser(username, password));
		return rs;
	}

	/**
	 * 查询当前登录用户的权限等信息
	 */
	@Override
	public JSONObject getUserPermissions() {
		//从session获取用户信息
		Session session = SecurityUtils.getSubject().getSession();
		JSONObject user = (JSONObject) session.getAttribute(Constants.SESSION_CURR_USER);
		int userId = user.getInteger("userId");
		JSONObject userPermissions = permissionService.getUserPermissions(userId);
		session.setAttribute(Constants.SESSION_USER_PERMISSIONS, userPermissions);
		return userPermissions;
	}

	/**
	 * 用户注销
	 */
	@Override
	public JSONObject logout() {
        JSONObject res = new JSONObject();
        res.put("type", TypeEnum.T_result.getTypeCode());
		try {
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
			res.put("code", ResultEnum.R_success.getResCode());
            res.put("msg", ResultEnum.R_success.getResMsg());
            return res;
		} catch (Exception e) {
		    LoggerUtil.error("用户注销失败：",e);
            res.put("code", ResultEnum.R_error.getResCode());
            res.put("msg", ResultEnum.R_error.getResMsg());
            return res;
		}
	}
}
