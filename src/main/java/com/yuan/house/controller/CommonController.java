package com.yuan.house.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.house.POJO.TenantSearchPOJO;
import com.yuan.house.constants.Constants;
import com.yuan.house.constants.ResultEnum;
import com.yuan.house.model.House;
import com.yuan.house.model.Role;
import com.yuan.house.model.User;
import com.yuan.house.service.HouseService;
import com.yuan.house.service.PermissionService;
import com.yuan.house.service.RoleService;
import com.yuan.house.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * 公共Controller，不限制权限
 */
@Controller
public class CommonController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RoleService roleService;

    @Autowired
    private HouseService houseService;

    @RequestMapping("")
    public String index() {
        return "/tenant/index";
    }

    @RequestMapping("/common/tenant")
    public String showTenant() {
        return "/tenant/index";
    }

    @RequestMapping("/common/landlord")
    public String showLandlord() {
        return "/landlord/index";
    }

    @RequestMapping("/common/manager")
    public String showManager() {
        return "/manager/index";
    }

    @RequestMapping(value="/common/checkCode")
    @ResponseBody
    public String checkCode(String code) {
        System.out.println("code:"+code);
        String sessionCode=(String)((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("sessionCode");
        System.out.println("sessionCode:"+sessionCode);
        if(sessionCode.equalsIgnoreCase(code)){
            return "true";
        }
        else{
            return "false";
        }
    }

    @RequestMapping(value="/common/checkName")
    @ResponseBody
    public String checkName(String name) {
        User user = userService.queryUserByName(name);
        if(user == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 获取用户注册页面
     * @param model
     * @return
     */
    @GetMapping("/common/showRegister")
    public ModelAndView showRegister(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("common/register", "userModel", model);
    }

    /**
     * 用户注册
     * @param model
     * @return
     */
	@PostMapping("/common/register")
	public ModelAndView userRegister(@ModelAttribute(value = "user") User user, Model model) {
	    Role role = null;
	    if(user.getUserType() == 1) {
	        role = roleService.queryRoleByName("房东");
	        user.setUserType(2);
        }
        else {
	        role = roleService.queryRoleByName("租客");
            user.setUserType(1);
        }
	    userService.addUser(user, ""+role.getRoleId());
		String rs = userService.userLogin(user.getUsername(), user.getPassword());
		if(ResultEnum.R_wrong.getResCode().equals(rs)){
            model.addAttribute("msg", ResultEnum.R_error.getResMsg());
            return new ModelAndView("error","msgModel",model);
        }
        permissionService.getUserPermissions();
        if(1 == user.getUserType()){
            return new ModelAndView("tenant/info");
        }
        if(2 == user.getUserType()){
            return new ModelAndView("landlord/info");
        }
        return new ModelAndView("common/login");
	}

    /**
     * 获取用户登录页面
     * @param model
     * @return
     */
    @GetMapping("/common/showLogin")
    public ModelAndView showLogin(Model model) {
        return new ModelAndView("common/login", "userModel", model);
    }


    /**
     * 用户登录
     * @param username
     * @param password
     * @param model
     * @return
     */
    @PostMapping("/common/login")
    public ModelAndView userLogin(@RequestParam("username")String username, @RequestParam("password")String password, Model model) {
        System.out.println("执行");
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            model.addAttribute("msg", ResultEnum.R_required.getResMsg());
            return new ModelAndView("error","msgModel",model);
        }
        String rs = userService.userLogin(username, password);
        if(ResultEnum.R_wrong.getResCode().equals(rs)){
            model.addAttribute("msg", ResultEnum.R_error.getResMsg());
            return new ModelAndView("error","msgModel",model);
        }
        permissionService.getUserPermissions();
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        if(0 == user.getUserType()){
            return new ModelAndView("manager/index");
        }
        if(1 == user.getUserType()){
            return new ModelAndView("tenant/info");
        }
        if(2 == user.getUserType()){
            return new ModelAndView("landlord/info");
        }
        return new ModelAndView("common/login");
    }

    /**
     * 用户注销
     * @return
     */
	@GetMapping("/common/logout")
	public ModelAndView userLogout(Model model) {
		String rs = userService.logout();
		if(ResultEnum.R_error.getResCode().equals(rs)) {
            model.addAttribute("msg", ResultEnum.R_error.getResMsg());
            return new ModelAndView("error","msgModel",model);
        }
        return new ModelAndView("common/login", "userModel", model);
	}

    /**
     * 地图找房
     * @param model
     * @return
     */
    @RequestMapping("/common/map/show")
    public ModelAndView showMap(Model model) {
        return new ModelAndView("/tenant/map", "Model", model);
    }

    @RequestMapping("/common/search")
    public ModelAndView showSearch(@RequestParam("type")int type, @RequestParam("msg")String msg, @RequestParam("province")String province,
                                   @RequestParam("city")String city, @RequestParam("area")String area, @RequestParam("counts")int counts,
                                   @RequestParam("orientation")String orientation,@RequestParam("number")int number, @RequestParam("show")String show,
                                   @RequestParam("sx")String sx, Model model) {
        System.out.println("number:"+number);
        TenantSearchPOJO ts = new TenantSearchPOJO();
        ts.setType(type);
        ts.setShow(show);
        ts.setSx(sx);
        if(StringUtils.isNotBlank(orientation) && !"0".equals(orientation)) {
            ts.setOrientation(orientation);
        }
        if(counts != 0) {
            ts.setKind(counts+"0000");
        }
        StringBuffer sb = new StringBuffer();
        if(StringUtils.isNotBlank(province) && !Constants.SEARCH_EXCEPT_WORDS.contains(province)) {
            sb.append(",").append(province);
        }
        if(StringUtils.isNotBlank(city) && !Constants.SEARCH_EXCEPT_WORDS.contains(city)) {
            sb.append(",").append(city);
        }
        if(StringUtils.isNotBlank(area) && !Constants.SEARCH_EXCEPT_WORDS.contains(area)) {
            sb.append(",").append(area);
        }
        if(StringUtils.isNotBlank(msg)) {
            String[] arr = msg.trim().split("\\s+");
            int len = arr.length;
            if(len > 0) {
                for(int i = 0; i<len; i++) {
                    if(!Constants.SEARCH_EXCEPT_WORDS.contains(arr[i])) {
                        sb.append(",").append(arr[i]);
                    }
                }
            }
        }
        if(sb != null && sb.length() > 0) {
            sb.deleteCharAt(0);
            ts.setMsg(sb.toString());
        }
        System.out.println("ts.toString()"+ts.toString());
        PageHelper.startPage(number, 8);
        List<House> houses = houseService.queryHousesLikeMsg(ts);
        PageInfo<House> housePageInfo = new PageInfo<House>(houses);
        model.addAttribute("housePageInfo", housePageInfo);
        return new ModelAndView("/tenant/show_search", "Model", model);
    }
}
