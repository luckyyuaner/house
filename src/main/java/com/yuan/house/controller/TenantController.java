package com.yuan.house.controller;


import com.alibaba.fastjson.JSONObject;
import com.yuan.house.constants.Constants;
import com.yuan.house.model.Contract;
import com.yuan.house.model.House;
import com.yuan.house.model.User;
import com.yuan.house.service.HouseService;
import com.yuan.house.service.UserService;
import com.yuan.house.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @RequestMapping("/tenant/showUserInfo")
    public ModelAndView showUserInfo(Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        user = userService.queryUserByName(user.getUsername());
        model.addAttribute("curruser",user);
        return new ModelAndView("/tenant/info", "Model", model);
    }


    @RequiresPermissions("user:update")
    @RequestMapping("/tenant/updateUserInfo")
    public ModelAndView updateUserInfo(Model model, @ModelAttribute(value = "curruser")User curruser,
                                       @RequestParam String birthh,  MultipartFile headd, MultipartFile card) {
        //System.out.println("head:"+head);
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        if(StringUtils.isNotBlank(birthh)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date birth = formatter.parse(birthh, pos);
            curruser.setBirth(new java.sql.Date(birth.getTime()));
        }
        JSONObject json = FileUtil.upload(headd);
        if("fail".equals(json.getString("rs"))) {
            model.addAttribute("curruser",user);
            model.addAttribute("msg", json.getString("msg"));
            return new ModelAndView("/tenant/info", "Model", model);
        }
        curruser.setHead(json.getString("msg"));
        JSONObject json1 = FileUtil.upload(card);
        if("fail".equals(json1.getString("rs"))) {
            model.addAttribute("curruser",user);
            model.addAttribute("msg", json1.getString("msg"));
            return new ModelAndView("/tenant/info", "Model", model);
        }
        curruser.setPhoto(json1.getString("msg"));
        //System.out.println("phone"+curruser.getPhone());
        //System.out.println("birth"+birthh);
        User newUser = userService.updateUserInfo(curruser);
        model.addAttribute("curruser",newUser);
        return new ModelAndView("/tenant/info", "Model", model);
    }


    @RequiresPermissions("user:update")
    @RequestMapping("/tenant/updateUserPassword")
    public ModelAndView updateUserPassword(Model model, @ModelAttribute(value = "curruser")User curruser) {
        model.addAttribute("curruser",curruser);
        return new ModelAndView("/tenant/info", "Model", model);
    }
}
