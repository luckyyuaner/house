package com.yuan.house.controller;

import com.yuan.house.model.User;
import com.yuan.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;


/**
 * 房东Controller，进行权限控制
 */
@Controller
public class LandlordController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/landlord/info")
    public String showLandlordInfo() {
        return "/landlord/info";
    }

    @RequestMapping("/landlord/chat")
    public ModelAndView  showLandlordChat(Model model) {
        List<User> managerlist = userService.getUsersByRole("一般管理员");
        model.addAttribute("managers", managerlist);
        return new ModelAndView("/landlord/chat", "Model", model);
    }
}
