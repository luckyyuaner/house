package com.yuan.house.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.constants.Constants;
import com.yuan.house.dao.PermissionDao;
import com.yuan.house.model.Permission;
import com.yuan.house.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	/**
	 * 查询用户的所有权限
	 */

	/**
	 * 查询当前登录用户的权限等信息
	 */
	@Override
	public Set<Permission> getUserPermissions() {
		//从session获取用户信息
		Session session = SecurityUtils.getSubject().getSession();
		JSONObject user = (JSONObject) session.getAttribute(Constants.SESSION_CURR_USER);
		String username = user.getString("username");
        Set<Permission> userPermissions = permissionDao.getUserPermissions(username);
		session.setAttribute(Constants.SESSION_USER_PERMISSIONS, userPermissions);
		return userPermissions;
	}
}
