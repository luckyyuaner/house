package com.yuan.house.controller;


import com.yuan.house.constants.Constants;
import com.yuan.house.model.Contract;
import com.yuan.house.model.House;
import com.yuan.house.model.User;
import com.yuan.house.service.HouseService;
import com.yuan.house.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * 租客Controller，进行权限控制
 */
@Controller
public class TenantController extends BaseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @RequestMapping("/tenant/info")
    public ModelAndView showTenantInfo(Model model) {
        return new ModelAndView("/tenant/info", "Model", model);
    }

    @RequiresPermissions({"cart:create", "cart:delete"})
    @RequestMapping(value="/tenant/collectHouse")
    @ResponseBody
    public int collectHouse(Long hid, int val) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        if(val == 0) {
            houseService.addHouseCollect(hid, user.getUserId());
            return 1;
        }
        houseService.deleteHouseCollect(hid, user.getUserId());
        return 0;
    }

    @RequiresPermissions("contract:create")
    @RequestMapping("/contract/showNew")
    public ModelAndView showContractNew(Model model, @RequestParam("hid")Long hid) {
        model.addAttribute("house",houseService.queryHouseById(hid));
        model.addAttribute("landlord",houseService.queryLandlordByHouse(hid));
        model.addAttribute("contract", new Contract());
        return new ModelAndView("/tenant/new_contract", "Model", model);
    }

    @RequiresPermissions("cart:read")
    @RequestMapping("/tenant/showCollect")
    public ModelAndView showCollect(Model model) {
        List<House> houses = houseService.queryHousesByCollect();
        model.addAttribute("collects",houses);
        return new ModelAndView("/tenant/info", "Model", model);
    }

    @RequiresPermissions("user:update")
    @RequestMapping("/tenant/updateUserInfo")
    public ModelAndView updateUserInfo(Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        user = userService.queryUserByName(user.getUsername());
        model.addAttribute("curruser",user);
        return new ModelAndView("/tenant/info", "Model", model);
    }

}
