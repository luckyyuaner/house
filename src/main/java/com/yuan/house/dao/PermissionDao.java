package com.yuan.house.dao;

import com.alibaba.fastjson.JSONObject;


public interface PermissionDao {
	/**
	 * 查询用户所有权限
	 */
	JSONObject getUserPermissions(String username);
}
