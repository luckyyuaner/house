package com.yuan.house.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 公共Controller，不限制权限
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @RequiresPermissions("user:read")
    @GetMapping("/listUser")
    public ModelAndView listUser(Model model) {
        return new ModelAndView("common/login", "userModel", model);
    }
    @RequiresPermissions("user:create")
    @GetMapping("/addUser")
    public ModelAndView addUser(Model model) {
        return new ModelAndView("common/login", "userModel", model);
    }
    @RequiresPermissions("user:update")
    @GetMapping("/updateUser")
    public ModelAndView updateUser(Model model) {
        return new ModelAndView("common/login", "userModel", model);
    }
    @RequiresPermissions("user:delete")
    @GetMapping("/deleteUser")
    public ModelAndView deleteUser(Model model) {
        return new ModelAndView("common/login", "userModel", model);
    }
}
