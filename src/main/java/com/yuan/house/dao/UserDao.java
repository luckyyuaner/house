package com.yuan.house.dao;

import com.yuan.house.model.User;
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
	Long addUser(@Param("object") User object);

	/**
	 * 更新用户
	 * @param object
	 * @return
	 */
	int updateUser(@Param("object") User object);

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	int deleteUser(@Param("id") Long id);
}
