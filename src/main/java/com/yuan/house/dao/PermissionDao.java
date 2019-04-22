package com.yuan.house.dao;

import com.yuan.house.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;


public interface PermissionDao {
	/**
	 * 查询用户所有权限
	 */
	Set<Permission> getUserPermissions(@Param("username") String username);

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
	Permission queryPermissionById(@Param("id") Long id);

    /**
     * 根据msg模糊查询权限
     * @param msg
     * @return
     */
    List<Permission> queryPermissionLikeMsg(@Param("msg") String msg);

    /**
     * 添加权限
     * @param object
     * @return
     */
    Long addPermission(@Param("object") Permission object);

    /**
     * 更新权限
     * @param object
     * @return
     */
    int updatePermission(@Param("object") Permission object);

    /**
     * 删除权限
     * @param id
     * @return
     */
    int deletePermission(@Param("id") Long id);

}
