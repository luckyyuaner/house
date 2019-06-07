package com.yuan.house.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.house.model.Permission;
import com.yuan.house.model.Role;
import com.yuan.house.model.User;
import com.yuan.house.service.PermissionService;
import com.yuan.house.service.RoleService;
import com.yuan.house.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.jsoup.helper.StringUtil;
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

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequiresPermissions("user:read")
    @GetMapping("/user/listUser")
    public ModelAndView listUser(Model model, int number, String msg, String type) {
        if("" == type) {
            PageHelper.startPage(number, 10);
            List<User> users = userService.getAllUsers(number);
            PageInfo<User> userPageInfo = new PageInfo<User>(users);
            model.addAttribute("userPageInfo", userPageInfo);
        }
        else if("id".equals(type)) {
            if(StringUtil.isBlank(msg)) {
                msg = "-1";
            }
            PageHelper.startPage(number, 10);
            User user = userService.queryUserById(Long.parseLong(msg));
            List<User> users = new ArrayList<User>();
            users.add(user);
            PageInfo<User> userPageInfo = new PageInfo<User>(users);
            model.addAttribute("userPageInfo", userPageInfo);
        }
        else if("msg".equals(type)) {
            PageHelper.startPage(number, 10);
            List<User> users = userService.queryUserLikeMsg(msg, number);
            PageInfo<User> userPageInfo = new PageInfo<User>(users);
            model.addAttribute("userPageInfo", userPageInfo);
        }
        model.addAttribute("type", type);
        model.addAttribute("msg", msg);
        return new ModelAndView("user/show", "Model", model);
    }

    @RequiresPermissions("user:create")
    @GetMapping("/user/showAdd")
    public ModelAndView showAddUser(Model model, int number) {
        List<Role> roleList = roleService.getAllRoles(number);
        model.addAttribute("roleList", roleList);
        model.addAttribute("user", new User());
        return new ModelAndView("user/new", "Model", model);
    }
    @RequiresPermissions("user:create")
    @PostMapping("/user/addUser")
    public ModelAndView addUser(@ModelAttribute(value = "user") User user, Model model, String rid) {
        userService.addUser(user, rid);
        return new ModelAndView("user/new", "Model", model);
    }

    @RequiresPermissions("user:update")
    @GetMapping("/user/showUpdate")
    public ModelAndView showUpdateUser(Model model, Long id, int number) {
        List<Role> roleList = roleService.getAllRoles(number);
        model.addAttribute("roleList", roleList);
        model.addAttribute("user", userService.queryUserById(id));
        return new ModelAndView("user/modify", "Model", model);
    }

    @RequiresPermissions("user:update")
    @PostMapping("/user/updateUser")
    public ModelAndView updateUser(@ModelAttribute(value = "user") User user, Model model, String rid) {
        userService.updateUser(user, rid);
        return new ModelAndView("user/show", "Model", model);
    }
    @RequiresPermissions("user:delete")
    @GetMapping("/user/deleteUser")
    public ModelAndView deleteUser(Model model, Long id) {
        userService.deleteUser(id);
        return new ModelAndView("user/show", "Model", model);
    }

    @RequiresPermissions("permission:read")
    @GetMapping("/permission/listPermission")
    public ModelAndView listPermission(Model model, int number, String msg, String type) {
        if("" == type) {
            PageHelper.startPage(number, 10);
            List<Permission> permissions = permissionService.getAllPermissions(number);
            PageInfo<Permission> permissionPageInfo = new PageInfo<Permission>(permissions);
            model.addAttribute("permissionPageInfo", permissionPageInfo);
        }
        else if("id".equals(type)) {
            if(StringUtil.isBlank(msg)) {
                msg = "-1";
            }
            PageHelper.startPage(number, 10);
            Permission permission = permissionService.queryPermissionById(Long.parseLong(msg));
            List<Permission> permissions = new ArrayList<Permission>();
            permissions.add(permission);
            PageInfo<Permission> permissionPageInfo = new PageInfo<Permission>(permissions);
            model.addAttribute("permissionPageInfo", permissionPageInfo);
        }
        else if("msg".equals(type)) {
            PageHelper.startPage(number, 10);
            List<Permission> permissions = permissionService.queryPermissionLikeMsg(msg, number);
            PageInfo<Permission> permissionPageInfo = new PageInfo<Permission>(permissions);
            model.addAttribute("permissionPageInfo", permissionPageInfo);
        }
        model.addAttribute("type", type);
        model.addAttribute("msg", msg);
        return new ModelAndView("permission/show", "Model", model);
    }

    @RequiresPermissions("permission:create")
    @GetMapping("/permission/showAdd")
    public ModelAndView showAddPermission(Model model,int number) {
        PageHelper.startPage(number, 10);
        List<Permission> permissions = permissionService.getAllPermissions(number);
        PageInfo<Permission> permissionPageInfo = new PageInfo<Permission>(permissions);
        model.addAttribute("permissionPageInfo", permissionPageInfo);
        model.addAttribute("permission", new Permission());
        return new ModelAndView("permission/new", "Model", model);
    }
    @RequiresPermissions("permission:create")
    @PostMapping("/permission/addPermission")
    public ModelAndView addPermission(@ModelAttribute(value = "permission") Permission permission, Model model, String pid) {
        if(pid!=null) {
            permission.setParentId(Long.parseLong(pid));
        }
        permissionService.addPermission(permission);
        return new ModelAndView("redirect:/permission/showAdd?number=1");
    }

    @RequiresPermissions("permission:update")
    @GetMapping("/permission/showUpdate")
    public ModelAndView showUpdatePermission(Model model, Long id) {
        List<Permission> permissions = permissionService.getAllPermissionsByNoPage();
        model.addAttribute("permissions", permissions);
        Permission permission = permissionService.queryPermissionById(id);
        model.addAttribute("permission", permission);
        return new ModelAndView("permission/modify", "Model", model);
    }

    @RequiresPermissions("permission:update")
    @PostMapping("/permission/updatePermission")
    public ModelAndView updatePermission(@ModelAttribute(value = "permission") Permission permission, String pid, Model model) {
        if(pid!=null) {
            permission.setParentId(Long.parseLong(pid));
        }
        permissionService.updatePermission(permission);
        return new ModelAndView("redirect:/permission/showUpdate?number=1&id="+permission.getPermissionId());
    }
    @RequiresPermissions("permission:delete")
    @GetMapping("/permission/deletePermission")
    public ModelAndView deletePermission(Model model, Long id) {
        permissionService.deletePermission(id);
        return new ModelAndView("redirect:/permission/listPermission?number=1&type=&msg=");
    }


    @RequiresPermissions("role:read")
    @GetMapping("/role/listRole")
    public ModelAndView listRole(Model model, int number, String msg, String type) {
        if("" == type) {
            PageHelper.startPage(number, 10);
            List<Role> roles = roleService.getAllRoles(number);
            PageInfo<Role> rolePageInfo = new PageInfo<Role>(roles);
            model.addAttribute("rolePageInfo", rolePageInfo);
        }
        else if("id".equals(type)) {
            if(StringUtil.isBlank(msg)) {
                msg = "-1";
            }
            PageHelper.startPage(number, 10);
            Role role = roleService.queryRoleById(Long.parseLong(msg));
            List<Role> roles = new ArrayList<Role>();
            roles.add(role);
            PageInfo<Role> rolePageInfo = new PageInfo<Role>(roles);
            model.addAttribute("rolePageInfo", rolePageInfo);
        }
        else if("msg".equals(type)) {
            PageHelper.startPage(number, 10);
            List<Role> roles = roleService.queryRoleLikeMsg(msg, number);
            PageInfo<Role> rolePageInfo = new PageInfo<Role>(roles);
            model.addAttribute("rolePageInfo", rolePageInfo);
        }
        model.addAttribute("type", type);
        model.addAttribute("msg", msg);
        return new ModelAndView("role/show", "Model", model);
    }

    @RequiresPermissions("role:create")
    @GetMapping("/role/showAdd")
    public ModelAndView showAddRole(Model model) {
        List<Permission> permissions = permissionService.getAllPermissionsByNoPage();
        model.addAttribute("permissions", permissions);
        model.addAttribute("role", new Role());
        return new ModelAndView("role/new", "Model", model);
    }
    @RequiresPermissions("role:create")
    @PostMapping("/role/addRole")
    public ModelAndView addRole(@ModelAttribute(value = "role") Role role, Model model, String pid) {
        roleService.addRole(role, pid);
        return new ModelAndView("redirect:/role/showAdd");
    }

    @RequiresPermissions("role:update")
    @GetMapping("/role/showUpdate")
    public ModelAndView showUpdateRole(Model model, Long id) {
        List<Permission> permissions = permissionService.getAllPermissionsByNoPage();
        model.addAttribute("permissions", permissions);
        model.addAttribute("role", roleService.queryRoleById(id));
        return new ModelAndView("role/modify", "Model", model);
    }

    @RequiresPermissions("role:update")
    @PostMapping("/role/updateRole")
    public ModelAndView updateRole(@ModelAttribute(value = "role") Role role, Model model, String pid) {
        roleService.updateRole(role, pid);
        return new ModelAndView("redirect:/role/showUpdate?id="+role.getRoleId());
    }
    @RequiresPermissions("role:delete")
    @GetMapping("/role/deleteRole")
    public ModelAndView deleteRole(Model model, Long id) {
        roleService.deleteRole(id);
        return new ModelAndView("redirect:/role/listRole?number=1&type=&msg=");
    }
}
