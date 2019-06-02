package com.yuan.house.dao;

import com.yuan.house.model.User;
import com.yuan.house.model.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
	/**
	 * 根据用户名和密码查询对应的用户
	 */
	User getUser(@Param("username") String username, @Param("password") String password);

	/**
	 * 查询所有用户
	 * @return
	 */
	List<User> getAllUsers();

	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
    User queryUserById(@Param("id") Long id);

    /**
     * 根据用户名查询用户
     * @param name
     * @return
     */
	User queryUserByName(@Param("name") String name);

	/**
	 * 根据msg模糊查询用户
	 * @param msg
	 * @return
	 */
	List<User> queryUserLikeMsg(@Param("msg") String msg);

	/**
	 * 添加用户
	 * @param object
	 * @return
	 */
	int addUser(@Param("object") User object);

	/**
	 * 更新用户
	 * @param object
	 * @return
	 */
	int updateUser(@Param("object") User object);

	int updateUserInfo(@Param("object") User object);

	int updateUserPassword(@Param("pass") String pass, @Param("uid") Long uid);

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	int deleteUser(@Param("id") Long id);


	int deleteUserRole(@Param("id") Long id);

	/**
	 * 添加角色权限
	 * @return
	 */
	int addUserRole(@Param("object") UserRole object);

	/**
	 * 根据角色名查找用户
	 * @param name
	 * @return
	 */
	List<User> getUsersByRole(@Param("name") String name);
}
