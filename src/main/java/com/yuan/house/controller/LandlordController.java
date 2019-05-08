package com.yuan.house.controller;

import com.yuan.house.config.websocket.WebSocketConfig;
import com.yuan.house.model.User;
import com.yuan.house.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 房东Controller，进行权限控制
 */
@Controller
public class LandlordController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/landlord/chat")
    public ModelAndView showLandlordChat(Model model) {
        Map<String, String> onlines = WebSocketConfig.getUsers();
        Set<User> managers = new HashSet<>();
        for(String name : onlines.keySet()) {
            User u = userService.queryUserByName(name);
            //if(u.getUserType() == 0) {
                managers.add(u);
            //}
        }
        model.addAttribute("onlines", managers);
        System.out.println("================================");
        System.out.println(onlines);
        System.out.println("================================");
        return new ModelAndView("/landlord/chat", "Model", model);
    }

    @RequestMapping("/landlord/info")
    public String showLandlordInfo() {
        return "/landlord/info";
    }
}
