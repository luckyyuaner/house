package com.yuan.house.service;

import com.yuan.house.model.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService {
	/**
	 * 查询用户的所有权限值
	 */
	Set<Permission> getUserPermissions();

    /**
     * 查询所有权限
     * @return
     */
    List<Permission> getAllPermissions(int number);

	/**
	 * 根据id查询权限
	 * @param id
	 * @return
	 */
	Permission queryPermissionById(Long id);

	/**
	 * 根据msg模糊查询权限
	 * @param msg
	 * @return
	 */
    List<Permission> queryPermissionLikeMsg(String msg, int number);

	/**
	 * 添加权限
	 * @param object
	 * @return
	 */
	int addPermission(Permission object);

	/**
	 * 更新权限
	 * @param object
	 * @return
	 */
	int updatePermission(Permission object);

	List<Permission> getAllPermissionsByNoPage();

	/**
	 * 删除权限
	 * @param id
	 * @return
	 */
	int deletePermission(Long id);
}
