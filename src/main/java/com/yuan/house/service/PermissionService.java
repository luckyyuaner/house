package com.yuan.house.service;

import com.yuan.house.model.Permission;

import java.util.Set;

public interface PermissionService {
	/**
	 * 查询用户的所有权限
	 */
	Set<Permission> getUserPermissions();
}
