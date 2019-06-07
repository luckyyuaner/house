package com.yuan.house.dao;

import com.yuan.house.model.Role;
import com.yuan.house.model.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleDao {

    /**
     * 查询所有角色
     * @return
     */
    List<Role> getAllRoles();

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
	Role queryRoleById(@Param("id") Long id);

    /**
     * 根据name查询角色
     * @param name
     * @return
     */
    Role queryRoleByName(@Param("name") String name);

    /**
     * 根据msg模糊查询角色
     * @param msg
     * @return
     */
    List<Role> queryRoleLikeMsg(@Param("msg") String msg);

    /**
     * 添加角色
     * @param object
     * @return
     */
    int addRole(@Param("object") Role object);

    /**
     * 更新角色
     * @param object
     * @return
     */
    int updateRole(@Param("object") Role object);

    /**
     * 删除角色
     * @param id
     * @return
     */
    int deleteRole(@Param("id") Long id);


    int deleteRolePermission(@Param("id") Long id);

    /**
     * 添加角色权限
     * @return
     */
    int addRolePermission(@Param("object") RolePermission object);

    List<Role> getAllRolesByNoPage();
}
