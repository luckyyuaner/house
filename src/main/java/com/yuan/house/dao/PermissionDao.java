package com.yuan.house.dao;

import com.yuan.house.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;


public interface PermissionDao {
	/**
	 * 查询用户所有权限
	 */
	Set<Permission> getUserPermissions(@Param("username") String username);
}
