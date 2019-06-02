package com.yuan.house.service;

import com.yuan.house.model.User;

import java.util.List;

public interface UserService {

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	String userLogin(String username, String password);

	/**
	 * 根据用户名和密码查询对应的用户
	 *
	 * @param username 用户名
	 * @param password 密码
	 */
	User getUser(String username, String password);


	/**
	 * 退出登录
	 */
	String logout();

	/**
	 * 查询所有用户
	 * @return
	 */
	List<User> getAllUsers(int number);

	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	User queryUserById(Long id);

    /**
     * 根据用户名查询用户
     * @param name
     * @return
     */
	User queryUserByName(String name);

	/**
	 * 根据msg模糊查询用户
	 * @param msg
	 * @return
	 */
	List<User> queryUserLikeMsg(String msg, int number);

	/**
	 * 添加用户
	 * @param object
	 * @return
	 */
	void addUser(User object, String id);

    /**
     * 根据角色名查找用户
     * @param roleName
     * @return
     */
    List<User> getUsersByRole(String roleName);

	/**
	 * 更新用户
	 * @param object
	 * @return
	 */
	int updateUser(User object,String id);

    /**
     * 更新用户信息
     * @param object
     * @return
     */
	User updateUserInfo(User object);

    /**
     * 修改用户密码
     * @param pass
     * @return
     */
	int updateUserPassword(String pass, Long uid);

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	int deleteUser(Long id);

}
