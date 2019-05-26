package com.yuan.house.controller;


import com.yuan.house.model.Contract;
import com.yuan.house.service.HouseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;


/**
 * 租客Controller，进行权限控制
 */
@Controller
public class TenantController extends BaseController {

    @Autowired
    private HouseService houseService;


    //@RequiresPermissions("contract:create")
    @RequestMapping("/common/contract/showNew")
    public ModelAndView showContractNew(Model model, @RequestParam("hid")Long hid) {
        model.addAttribute("house",houseService.queryHouseById(hid));
        model.addAttribute("landlord",houseService.queryLandlordByHouse(hid));
        model.addAttribute("contract", new Contract());
        return new ModelAndView("/tenant/new_contract", "Model", model);
    }

}
