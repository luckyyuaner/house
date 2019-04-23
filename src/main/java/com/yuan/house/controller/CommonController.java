package com.yuan.house.controller;
import com.yuan.house.constants.Constants;
import com.yuan.house.constants.ResultEnum;
import com.yuan.house.model.User;
import com.yuan.house.service.PermissionService;
import com.yuan.house.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 公共Controller，不限制权限
 */
@Controller
@RequestMapping("/")
public class CommonController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;

    /**
     * 获取用户登录页面
     * @param model
     * @return
     */
    @GetMapping("/common/showLogin")
    public ModelAndView showLogin(Model model) {
        return new ModelAndView("common/login", "userModel", model);
    }


    /**
     * 用户登录
     * @param username
     * @param password
     * @param model
     * @return
     */
	@PostMapping("/common/login")
	public ModelAndView userLogin(@RequestParam("username")String username, @RequestParam("password")String password, Model model) {
	    if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            model.addAttribute("msg", ResultEnum.R_required.getResMsg());
            return new ModelAndView("error","msgModel",model);
        }
		String rs = userService.userLogin(username, password);
		if(ResultEnum.R_wrong.getResCode().equals(rs)){
            model.addAttribute("msg", ResultEnum.R_error.getResMsg());
            return new ModelAndView("error","msgModel",model);
        }
        permissionService.getUserPermissions();
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        /*if(0 == user.getUserType()){
            return new ModelAndView("manager/index");
        }
        if(1 == user.getUserType()){
            return new ModelAndView("tenant/index");
        }
        if(2 == user.getUserType()){
            return new ModelAndView("landlord/index");
        }*/
        return new ModelAndView("common/list");
	}

    /**
     * 用户注销
     * @return
     */
	@GetMapping("/common/logout")
	public ModelAndView userLogout(Model model) {
		String rs = userService.logout();
		if(ResultEnum.R_error.getResCode().equals(rs)) {
            model.addAttribute("msg", ResultEnum.R_error.getResMsg());
            return new ModelAndView("error","msgModel",model);
        }
        return new ModelAndView("common/login", "userModel", model);
	}
}
