package com.yuan.house.service;

import com.alibaba.fastjson.JSONObject;

public interface PermissionService {
	/**
	 * 查询用户的所有权限
	 */
	JSONObject getUserPermissions(String username);
}
