package com.yuan.house.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.dao.PermissionDao;
import com.yuan.house.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	/**
	 * 查询用户的所有权限
	 */
	@Override
	public JSONObject getUserPermissions(String username) {
		JSONObject userPermissions = permissionDao.getUserPermissions(username);
		return userPermissions;
	}
}
