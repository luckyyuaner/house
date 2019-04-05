package com.yuan.house.service;

import com.alibaba.fastjson.JSONObject;

public interface UserService {

	/**
	 * 用户登录
	 */
	JSONObject userLogin(JSONObject jsonObject);

	/**
	 * 根据用户名和密码查询对应的用户
	 *
	 * @param username 用户名
	 * @param password 密码
	 */
	JSONObject getUser(String username, String password);

	/**
	 * 查询当前登录用户的权限等信息
	 */
	JSONObject getUserPermissions();

	/**
	 * 退出登录
	 */
	JSONObject logout();

}
