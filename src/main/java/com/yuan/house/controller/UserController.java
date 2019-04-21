package com.yuan.house.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.house.model.Permission;
import com.yuan.house.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共Controller，不限制权限
 */
@Controller
@RequestMapping("/")
public class UserController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @RequiresPermissions("user:read")
    @GetMapping("/user/listUser")
    public ModelAndView listUser(Model model) {
        return new ModelAndView("common/login", "Model", model);
    }
    @RequiresPermissions("user:create")
    @GetMapping("/user/addUser")
    public ModelAndView addUser(Model model) {
        return new ModelAndView("common/login", "Model", model);
    }
    @RequiresPermissions("user:update")
    @GetMapping("/user/updateUser")
    public ModelAndView updateUser(Model model) {
        return new ModelAndView("common/login", "Model", model);
    }
    @RequiresPermissions("user:delete")
    @GetMapping("/user/deleteUser")
    public ModelAndView deleteUser(Model model) {
        return new ModelAndView("common/login", "Model", model);
    }

    @RequiresPermissions("permission:read")
    @GetMapping("/permission/listPermission")
    public ModelAndView listPermission(Model model, int number, String msg, String type) {
        if("" == type) {
            System.out.println("type=");
            PageHelper.startPage(number, 5);
            List<Permission> permissions = permissionService.getAllPermissions();
            System.out.println(permissions.size());
            PageInfo<Permission> permissionPageInfo = new PageInfo<Permission>(permissions);
            model.addAttribute("permissionPageInfo", permissionPageInfo);
        }
        else if("id".equals(type)) {
            PageHelper.startPage(number, 5);
            Permission permission = permissionService.queryPermissionById(Long.parseLong(msg));
            List<Permission> permissions = new ArrayList<Permission>();
            permissions.add(permission);
            PageInfo<Permission> permissionPageInfo = new PageInfo<Permission>(permissions);
            model.addAttribute("permissionPageInfo", permissionPageInfo);
        }
        else if("msg".equals(type)) {
            PageHelper.startPage(number, 5);
            List<Permission> permissions = permissionService.queryPermissionLikeMsg(msg);
            PageInfo<Permission> permissionPageInfo = new PageInfo<Permission>(permissions);
            model.addAttribute("permissionPageInfo", permissionPageInfo);
        }
        model.addAttribute("type", type);
        model.addAttribute("msg", msg);
        return new ModelAndView("permission/show", "Model", model);
    }

    @RequiresPermissions("permission:create")
    @GetMapping("/permission/showAdd")
    public ModelAndView showAddPermission(Model model) {
        model.addAttribute("permission", new Permission());
        return new ModelAndView("permission/new", "Model", model);
    }
    @RequiresPermissions("permission:create")
    @PostMapping("/permission/addPermission")
    public ModelAndView addPermission(@ModelAttribute(value = "permission") Permission permission, Model model) {
        permissionService.addPermission(permission);
        return new ModelAndView("common/login", "Model", model);
    }

    @RequiresPermissions("permission:update")
    @GetMapping("/permission/showUpdate")
    public ModelAndView showUpdatePermission(Model model, Long id) {
        model.addAttribute("permission", permissionService.queryPermissionById(id));
        return new ModelAndView("permission/modify", "Model", model);
    }

    @RequiresPermissions("permission:update")
    @PostMapping("/permission/updatePermission")
    public ModelAndView updatePermission(@ModelAttribute(value = "permission") Permission permission, Model model) {
        permissionService.updatePermission(permission);
        return new ModelAndView("common/login", "Model", model);
    }
    @RequiresPermissions("permission:delete")
    @GetMapping("/permission/deletePermission")
    public ModelAndView deletePermission(Model model, Long id) {
        permissionService.deletePermission(id);
        return new ModelAndView("common/login", "Model", model);
    }
}
