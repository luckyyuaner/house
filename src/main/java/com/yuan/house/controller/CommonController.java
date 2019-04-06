package com.yuan.house.controller;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.constants.ResultEnum;
import com.yuan.house.service.PermissionService;
import com.yuan.house.service.UserService;
import com.yuan.house.util.FieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公共Controller，不限制权限
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;

    /**
     * 用户登录
     * @param requestJson
     * @return
     */
	@PostMapping("/login")
	public String userLogin(@RequestBody JSONObject requestJson) {
		JSONObject check = FieldUtil.checkRequiredFields(requestJson, "username,password");
		if(ResultEnum.R_required.getResCode().equals(check.get("code"))) {
			return ResultEnum.R_required.getResCode();
        }
		if(ResultEnum.R_wrong.getResCode().equals(userService.userLogin(requestJson))) {
            return ResultEnum.R_wrong.getResCode();
        }
        permissionService.getUserPermissions();
        return ResultEnum.R_success.getResCode();
	}

    /**
     * 用户注销
     * @return
     */
	@PostMapping("/logout")
	public String userLogout() {
		return userService.logout();
	}
}
