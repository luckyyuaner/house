package com.yuan.house.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.house.POJO.TenantContractPOJO;
import com.yuan.house.constants.Constants;
import com.yuan.house.model.Comment;
import com.yuan.house.model.Contract;
import com.yuan.house.model.House;
import com.yuan.house.model.User;
import com.yuan.house.service.*;
import com.yuan.house.util.FileUtil;
import com.yuan.house.util.PasswordUtil;
import com.yuan.house.util.TimeUtils;
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
import java.util.ArrayList;
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

    @Autowired
    private ContractService contractService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommonService commonService;

    @RequestMapping("/tenant/info")
    public ModelAndView showTenantInfo(Model model) {
        return new ModelAndView("/tenant/info", "Model", model);
    }

    @RequestMapping(value = "/common/showOneHouse")
    @ResponseBody
    public House showOneHouse(Long hid) {
        return houseService.queryHouseById(hid);
    }

    @RequiresPermissions({"cart:create", "cart:delete"})
    @RequestMapping(value = "/tenant/collectHouse")
    @ResponseBody
    public int collectHouse(Long hid, int val) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        if (val == 0) {
            houseService.addHouseCollect(hid, user.getUserId());
            return 1;
        }
        houseService.deleteHouseCollect(hid, user.getUserId());
        return 0;
    }

    //@RequiresPermissions("contract:create")
    @RequestMapping("/common/showNew")
    public ModelAndView showContractNew(Model model, @RequestParam("hid") Long hid) {
        model.addAttribute("house", houseService.queryHouseById(hid));
        model.addAttribute("landlord", houseService.queryLandlordByHouse(hid));
        model.addAttribute("contract", new Contract());
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        List<Long> hids = null;
        if (user != null) {
            hids = houseService.queryCollectHouseIdsByUser(user.getUserId());
        }
        model.addAttribute("hids", hids);
        return new ModelAndView("/tenant/new_contract", "Model", model);
    }

    @RequiresPermissions("cart:read")
    @RequestMapping("/tenant/showCollect")
    public ModelAndView showCollect(Model model) {
        List<House> houses = houseService.queryHousesByCollect();
        model.addAttribute("collects", houses);
        return new ModelAndView("/tenant/info", "Model", model);
    }

    @RequiresPermissions("user:update")
    @RequestMapping("/tenant/showUserInfo")
    public ModelAndView showUserInfo(Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        user = userService.queryUserByName(user.getUsername());
        model.addAttribute("curruser", user);
        return new ModelAndView("/tenant/info", "Model", model);
    }


    @RequiresPermissions("user:update")
    @RequestMapping("/tenant/updateUserInfo")
    public ModelAndView updateUserInfo(Model model, @ModelAttribute(value = "curruser") User curruser,
                                       @RequestParam String birthh, MultipartFile headd, MultipartFile card) {
        //System.out.println("head:"+head);
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        if (StringUtils.isNotBlank(birthh)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date birth = formatter.parse(birthh, pos);
            curruser.setBirth(new java.sql.Date(birth.getTime()));
        }
        JSONObject json = FileUtil.upload(headd);
        if ("fail".equals(json.getString("rs"))) {
            if ("未选择文件".equals(json.getString("msg"))) {
                curruser.setHead(user.getHead());
            } else {
                model.addAttribute("curruser", user);
                model.addAttribute("msg", json.getString("msg"));
                return new ModelAndView("/tenant/info", "Model", model);
            }
        } else {
            curruser.setHead(json.getString("msg"));
        }
        JSONObject json1 = FileUtil.upload(card);
        if ("fail".equals(json1.getString("rs"))) {
            if ("未选择文件".equals(json1.getString("msg"))) {
                curruser.setPhoto(user.getPhoto());
            } else {
                model.addAttribute("curruser", user);
                model.addAttribute("msg", json1.getString("msg"));
                return new ModelAndView("/tenant/info", "Model", model);
            }
        } else {
            curruser.setPhoto(json1.getString("msg"));
        }
        //System.out.println("phone"+curruser.getPhone());
        //System.out.println("birth"+birthh);
        User newUser = userService.updateUserInfo(curruser);
        session.setAttribute(Constants.SESSION_CURR_USER, newUser);
        model.addAttribute("curruser", newUser);
        return new ModelAndView("/tenant/info", "Model", model);
    }


    @RequiresPermissions("user:update")
    @RequestMapping("/tenant/updateUserPassword")
    public ModelAndView updateUserPassword(Model model, @RequestParam("pass") String pass) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        userService.updateUserPassword(PasswordUtil.md5Password(pass), user.getUserId());
        user = userService.queryUserById(user.getUserId());
        session.setAttribute(Constants.SESSION_CURR_USER, user);
        model.addAttribute("curruser", user);
        return new ModelAndView("/tenant/info", "Model", model);
    }

    @RequiresPermissions("contract:create")
    @RequestMapping("/tenant/createNewContract")
    public ModelAndView createNewContract(Model model, String stime, String etime, MultipartFile[] url, Long hid) {
        Contract con = new Contract();
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        if (StringUtils.isNotBlank(stime)) {
            con.setStime(TimeUtils.changeStringToTimestamp(stime));
        }
        if (StringUtils.isNotBlank(etime)) {
            con.setEtime(TimeUtils.changeStringToTimestamp(etime));
        }
        con.setHouseId(hid);
        con.setUserId(user.getUserId());
        con.setCtime(new java.sql.Timestamp(System.currentTimeMillis()));

        JSONObject json = FileUtil.uploadByNumber(url, 3);
        if ("fail".equals(json.getString("rs"))) {
            model.addAttribute("msg", json.getString("msg"));
            return new ModelAndView("redirect:/common/showNew?hid=" + hid);
        }
        con.setTenantInfo(json.getString("msg"));
        contractService.createNewContractByTenant(con);
        return new ModelAndView("/tenant/info", "Model", model);
    }


    @RequiresPermissions("contract:read")
    @RequestMapping("/tenant/showContracts")
    public ModelAndView showContracts(Model model, int number, int sta) {
        model.addAttribute("sta", sta);
        model.addAttribute("number", number);
        PageHelper.startPage(number, 1);
        List<Contract> contracts = contractService.queryContractsByTenant(number, sta);
        PageInfo<Contract> contractPageInfo = new PageInfo<Contract>(contracts);
        model.addAttribute("contractPageInfo", contractPageInfo);
        if (contracts != null && contracts.size() > 0) {
            model.addAttribute("tenantContractPOJO", commonService.createTenantContractPOJO(contracts.get(0)));
        }
        return new ModelAndView("/tenant/show_contract", "Model", model);
    }

    @RequiresPermissions("contract:update")
    @RequestMapping("/tenant/showContractUpdate")
    public ModelAndView showContractUpdate(Model model, @RequestParam("cid") Long cid) {
        Contract contract = contractService.queryContractById(cid);
        model.addAttribute("house", houseService.queryHouseById(contract.getHouseId()));
        model.addAttribute("landlord", houseService.queryLandlordByHouse(contract.getHouseId()));
        model.addAttribute("contract", contract);
        List<Long> hids = houseService.queryCollectHouseIdsByUser(contract.getUserId());
        model.addAttribute("hids", hids);
        return new ModelAndView("/tenant/update_contract", "Model", model);
    }

    @RequiresPermissions("contract:delete")
    @RequestMapping("/tenant/deleteContract")
    public ModelAndView deleteContract(Model model, @RequestParam("cid") Long cid) {
        contractService.deleteContractById(cid);
        return new ModelAndView("/tenant/info", "Model", model);
    }

    @RequiresPermissions("contract:update")
    @RequestMapping("/tenant/updateContract")
    public ModelAndView updateContract(Model model, String stime, String etime, MultipartFile[] url, Long cid) {
        Contract contract = new Contract();
        contract.setContractId(cid);
        if (StringUtils.isNotBlank(stime)) {
            contract.setStime(TimeUtils.changeStringToTimestamp(stime));
        }
        if (StringUtils.isNotBlank(etime)) {
            contract.setEtime(TimeUtils.changeStringToTimestamp(etime));
        }

        JSONObject json = FileUtil.uploadByNumber(url, 3);
        if ("fail".equals(json.getString("rs"))) {
            model.addAttribute("msg", json.getString("msg"));
            return new ModelAndView("redirect:/tenant/showContractUpdate?cid=" + cid);
        }
        contract.setTenantInfo(json.getString("msg"));
        contractService.updateContractByTenant(contract);
        return new ModelAndView("/tenant/info", "Model", model);
    }


    @RequiresPermissions("contract:update")
    @RequestMapping("/tenant/updateContract2")
    public ModelAndView updateContract2(Model model, MultipartFile[] url2, Long cid, int sta, int number) {
        Contract contract = new Contract();
        contract.setContractId(cid);
        JSONObject json = FileUtil.uploads(url2);
        if ("fail".equals(json.getString("rs"))) {
            model.addAttribute("msg", json.getString("msg"));
            return new ModelAndView("redirect:/tenant/showContracts?number=" + number+"&sta="+sta);
        }
        contract.setTenantInfo(json.getString("msg"));
        contractService.updateContractByTenant2(contract);
        return new ModelAndView("/tenant/info", "Model", model);
    }

    @RequiresPermissions("comment:create")
    @RequestMapping("/tenant/addComment")
    public ModelAndView addComment(Model model, MultipartFile url3, Long cid, String info,String grade) {
        Comment comment = new Comment();
        comment.setContractId(cid);
        JSONObject json = FileUtil.upload(url3);
        if ("fail".equals(json.getString("rs")) && !"未选择文件".equals(json.getString("msg"))) {
            model.addAttribute("msg", json.getString("msg"));
            return new ModelAndView("redirect:/tenant/info");
        }
        if("success".equals(json.getString("rs"))) {
            comment.setUrl(json.getString("msg"));
        }
        comment.setInfo(info);
        if(grade != null) {
            System.out.println("grade:"+grade);
            comment.setHouseGrade(Double.parseDouble(grade));
        }
        commentService.addCommentByTenant(comment);
        return new ModelAndView("/tenant/info", "Model", model);
    }

    @RequiresPermissions("comment:delete")
    @RequestMapping("/comment/delete")
    public ModelAndView deleteComment(Model model, Long cid, int sta ,int number) {
        commentService.deleteComment(cid);
        return new ModelAndView("redirect:/tenant/showContracts?number=" + number+"&sta="+sta);
    }
}
