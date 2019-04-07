package com.yuan.house.service;

import com.yuan.house.model.User;

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

}
