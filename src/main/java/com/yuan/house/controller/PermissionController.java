package com.yuan.house.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 * 权限controller
 * 访问需要权限控制
 */
@RestController
@RequestMapping("/permission")
@CrossOrigin(origins = "http://localhost:9520", maxAge = 3600)
public class PermissionController extends BaseController {

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	@RequiresPermissions("role:list")
	@GetMapping("/listAllPermission")
	public JSONObject listAllPermission() {
		return null;
	}
}
