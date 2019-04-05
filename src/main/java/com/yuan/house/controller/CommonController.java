package com.yuan.house.controller;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.constants.ResultEnum;
import com.yuan.house.service.UserService;
import com.yuan.house.util.FieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共Controller，不限制权限
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {

	@Autowired
	private UserService userService;

    /**
     * 用户登录
     * @param requestJson
     * @return
     */
	@PostMapping("/login")
	public JSONObject userLogin(@RequestBody JSONObject requestJson) {
		JSONObject check = FieldUtil.checkRequiredFields(requestJson, "username,password");
		if(ResultEnum.R_required.getResCode().equals(check.get("code"))) {
		    return check;
        }
		return userService.authLogin(requestJson);
	}

    /**
     * 用户注销
     * @return
     */
	@PostMapping("/logout")
	public JSONObject userLogout() {
		return userService.logout();
	}
}
