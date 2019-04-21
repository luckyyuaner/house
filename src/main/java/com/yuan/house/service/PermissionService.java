package com.yuan.house.service;

import com.yuan.house.model.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService {
	/**
	 * 查询用户的所有权限值
	 */
	Set<String> getUserPermissions();

    /**
     * 查询所有权限
     * @return
     */
    List<Permission> getAllPermissions();

	/**
	 * 根据id查询权限
	 * @param id
	 * @return
	 */
	Permission queryPermissionById(Long id);

	/**
	 * 根据name模糊查询权限
	 * @param name
	 * @return
	 */
    List<Permission> queryPermissionLikeName(String name);

	/**
	 * 添加权限
	 * @param object
	 * @return
	 */
	Long addPermission(Permission object);

	/**
	 * 更新权限
	 * @param object
	 * @return
	 */
	int updatePermission(Permission object);

	/**
	 * 删除权限
	 * @param id
	 * @return
	 */
	int deletePermission(Long id);
}
