package com.yuan.house.controller;


import com.yuan.house.model.Contract;
import com.yuan.house.model.House;
import com.yuan.house.service.ContractService;
import com.yuan.house.service.HouseService;
import com.yuan.house.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * 管理员Controller，进行权限控制
 */
@Controller
public class ManagerController extends BaseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContractService contractService;


    //@RequiresPermissions("contract:update")
    @RequestMapping("/common/manager/contract/showNew")
    public ModelAndView showContractNew(Model model, @RequestParam("cid")Long cid) {
        Contract contract = contractService.queryContractById(cid);
        House house = houseService.queryHouseById(contract.getHouseId());
        model.addAttribute("contract",contract);
        model.addAttribute("house",house);
        model.addAttribute("landlord",houseService.queryLandlordByHouse(house.getHouseId()));
        model.addAttribute("tenant", userService.queryUserById(contract.getUserId()));
        return new ModelAndView("/manager/show_contract", "Model", model);
    }

}