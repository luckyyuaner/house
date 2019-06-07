package com.yuan.house.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.house.VO.HouseManagerVO;
import com.yuan.house.model.Contract;
import com.yuan.house.model.House;
import com.yuan.house.service.*;
import com.yuan.house.util.PdfUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


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

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommentService commentService;


    @RequestMapping("/manager/index")
    public ModelAndView showManagerIndex(Model model) {
        return new ModelAndView("/manager/index", "Model", model);
    }

    @RequiresPermissions("contract:read")
    @RequestMapping("/contract/index")
    public ModelAndView showContracts(Model model, String number) {
        if(StringUtils.isBlank(number)) {
            number = "1";
        }
        model.addAttribute("number", number);
        PageHelper.startPage(Integer.parseInt(number), 1);
        List<Contract> contracts = contractService.queryAllContract(Integer.parseInt(number));
        PageInfo<Contract> contractPageInfo = new PageInfo<Contract>(contracts);
        model.addAttribute("contractPageInfo", contractPageInfo);
        if (contracts != null && contracts.size() > 0) {
            model.addAttribute("tenantContractPOJO", commonService.createTenantContractPOJO(contracts.get(0)));
        }
        return new ModelAndView("/manager/show_contract", "Model", model);
    }

    @RequiresPermissions("contract:update")
    @RequestMapping("/manager/contract/updateAgree")
    public ModelAndView updateContractAgree(Model model, @RequestParam("file")String file, Long cid) {
        String rs = PdfUtil.changeTxtToPdf(file);
        if(rs == null) {
            return new ModelAndView("redirect:/contract/index?number=1");
        }
        System.out.println("file="+rs);
        Contract contract = new Contract();
        contract.setContractId(cid);
        contract.setFile(rs);
        contractService.updateContractByManagerWithAgree(contract);
        return new ModelAndView("/manager/index", "Model", model);
    }

    @RequiresPermissions("contract:update")
    @RequestMapping("/manager/contract/updateRefuse")
    public ModelAndView updateContractRefuse(Model model, Long cid) {
        Contract contract = new Contract();
        contract.setContractId(cid);
        contractService.updateContractByManagerWithRefuse(contract);
        return new ModelAndView("/manager/index", "Model", model);
    }

    @RequiresPermissions("house:read")
    @RequestMapping("/house/read")
    public ModelAndView showUpdateHouse (Model model, int number) {
        PageHelper.startPage(number, 3);
        List<HouseManagerVO> houses = houseService.getAllHouses(number);
        PageInfo<HouseManagerVO> housePageInfo = new PageInfo<HouseManagerVO>(houses);
        model.addAttribute("housePageInfo", housePageInfo);
        return new ModelAndView("/manager/index", "Model", model);
    }

    @RequiresPermissions("house:delete")
    @RequestMapping("/manager/deleteTheHouse")
    public ModelAndView deleteTheHouse (Model model, Long hid,int number) {
        houseService.deleteHouse(hid);
        PageHelper.startPage(number, 3);
        List<HouseManagerVO> houses = houseService.getAllHouses(number);
        PageInfo<HouseManagerVO> housePageInfo = new PageInfo<HouseManagerVO>(houses);
        model.addAttribute("housePageInfo", housePageInfo);
        return new ModelAndView("/manager/index", "Model", model);
    }

    @RequiresPermissions("house:update")
    @RequestMapping("/manager/updateHouseStatus")
    public ModelAndView updateHouseStatus (Model model, Long hid,int number, int status) {
        houseService.updateHouseStatus(hid, status);
        PageHelper.startPage(number, 3);
        List<HouseManagerVO> houses = houseService.getAllHouses(number);
        PageInfo<HouseManagerVO> housePageInfo = new PageInfo<HouseManagerVO>(houses);
        model.addAttribute("housePageInfo", housePageInfo);
        return new ModelAndView("/manager/index", "Model", model);
    }


    @RequiresPermissions("contract:update")
    @RequestMapping("/manager/contract/updateAgree2")
    public ModelAndView updateContractAgree2(Model model, Long cid) {
        Contract contract = new Contract();
        contract.setContractId(cid);
        contractService.updateContractByManagerWithAgree2(contract);
        return new ModelAndView("/manager/index", "Model", model);
    }

    @RequiresPermissions("contract:update")
    @RequestMapping("/manager/contract/updateRefuse2")
    public ModelAndView updateContractRefuse2(Model model, Long cid) {
        Contract contract = new Contract();
        contract.setContractId(cid);
        contractService.updateContractByManagerWithRefuse2(contract);
        return new ModelAndView("/manager/index", "Model", model);
    }

    @RequiresPermissions("comment:delete")
    @RequestMapping("/manager/comment/delete")
    public ModelAndView deleteComment(Model model, Long cid, int number) {
        commentService.deleteComment(cid);
        return new ModelAndView("redirect:/contract/index?number=" + number);
    }
}
