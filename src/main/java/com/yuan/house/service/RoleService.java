package com.yuan.house.service;

import com.yuan.house.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    /**
     * 查询所有角色
     * @return
     */
    List<Role> getAllRoles(int number);

	/**
	 * 根据id查询角色
	 * @param id
	 * @return
	 */
	Role queryRoleById(Long id);

    /**
     * 根据name查询角色
     * @param name
     * @return
     */
	Role queryRoleByName(String name);

	/**
	 * 根据msg模糊查询角色
	 * @param msg
	 * @return
	 */
    List<Role> queryRoleLikeMsg(String msg, int number);

	/**
	 * 添加角色
	 * @param object
	 * @return
	 */
	void addRole(Role object, String pid);

	/**
	 * 更新角色
	 * @param object
	 * @return
	 */
	int updateRole(Role object, String pid);

	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	int deleteRole(Long id);

	List<Role> getAllRolesByNoPage();
}
