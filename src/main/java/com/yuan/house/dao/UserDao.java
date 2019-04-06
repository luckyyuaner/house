package com.yuan.house.dao;

import com.yuan.house.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
	/**
	 * 根据用户名和密码查询对应的用户
	 */
	User getUser(@Param("username") String username, @Param("password") String password);
}
