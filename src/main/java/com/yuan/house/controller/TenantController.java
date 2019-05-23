package com.yuan.house.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;


/**
 * 租客Controller，进行权限控制
 */
@Controller
public class TenantController extends BaseController {
    @RequestMapping("/map/show")
    public ModelAndView showMap(Model model) {
        return new ModelAndView("/tenant/map", "Model", model);
    }
}
