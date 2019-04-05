package com.yuan.house.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户controller
 * 访问需要权限控制
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:9520", maxAge = 3600)
public class UserController extends BaseController {
	/**
	 * 查询用户列表
	 */
	@RequiresPermissions("user:list")
	@GetMapping("/list")
	public JSONObject listUser(HttpServletRequest request) {
		return null;
	}

	@RequiresPermissions("user:add")
	@PostMapping("/addUser")
	public JSONObject addUser(@RequestBody JSONObject requestJson) {
		return null;
	}

	@RequiresPermissions("user:update")
	@PostMapping("/updateUser")
	public JSONObject updateUser(@RequestBody JSONObject requestJson) {
		return null;
	}
}
