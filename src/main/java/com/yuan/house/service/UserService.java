package com.yuan.house.service;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.model.User;

public interface UserService {

	/**
	 * 用户登录
	 */
	String userLogin(JSONObject jsonObject);

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
