package com.yuan.house.service;

import java.util.Set;

public interface PermissionService {
	/**
	 * 查询用户的所有权限值
	 */
	Set<String> getUserPermissions();
}
