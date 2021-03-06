package com.yuan.house.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.house.POJO.TenantSearchPOJO;
import com.yuan.house.VO.MapHouseVO;
import com.yuan.house.constants.Constants;
import com.yuan.house.constants.ResultEnum;
import com.yuan.house.model.Contract;
import com.yuan.house.model.House;
import com.yuan.house.model.Role;
import com.yuan.house.model.User;
import com.yuan.house.service.*;
import com.yuan.house.util.LoggerUtil;
import com.yuan.house.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


/**
 * 公共Controller，不限制权限
 */
@Controller
@EnableScheduling   // 1.开启定时任务
@EnableAsync
public class CommonController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RoleService roleService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private MailService mailService;

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

    @RequestMapping(value="/common/checkPassword")
    @ResponseBody
    public String checkPassword(String pass) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        if(user.getPassword().equals(PasswordUtil.md5Password(pass))){
            return "true";
        }
        else{
            return "false";
        }
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

    @RequestMapping(value="/common/showTheHouse")
    @ResponseBody
    public House showTheHouse(Long hid) {
        System.out.println("hid:"+hid);
        House h = houseService.queryHouseById(hid);
        return h;
    }

    @RequestMapping(value="/common/checkMail")
    @ResponseBody
    public String checkMail(String mail) {
        System.out.println("mail:"+mail);
        String sessionCode=(String)((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("emailCode");
        System.out.println("sessionCode:"+sessionCode);
        if(sessionCode.equalsIgnoreCase(mail)){
            return "true";
        }
        else{
            return "false";
        }
    }

    @RequestMapping(value="/common/mail/getCode")
    @ResponseBody
    public String getMailCode(HttpSession session, @RequestParam("email")String email) {
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的邮箱验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(email, "注册验证码", message);
            session.setAttribute("emailCode", checkCode);
            //session.setMaxInactiveInterval(60);
            return "success";
        }
        catch(Exception e) {
            LoggerUtil.error("验证码发送失败",e);
            return "fail";
        }
    }

    public void sendMailInfo(String email,String msg) {
        try {
            mailService.sendSimpleMail(email, "租房合约到期提醒", msg);
        }
        catch(Exception e) {
            LoggerUtil.error("提醒邮箱发送失败",e);
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
	    user.setPassword(PasswordUtil.md5Password(user.getPassword()));
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
        password = PasswordUtil.md5Password(password);
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
    public ModelAndView showMap(Model model, @RequestParam("city")String city, @RequestParam("lng")double lng, @RequestParam("lat")double lat) {
        List<MapHouseVO> houses = houseService.queryHousesByCity(city);
        model.addAttribute("houses", houses);
        model.addAttribute("city", city);
        model.addAttribute("lng", lng);
        model.addAttribute("lat", lat);
        return new ModelAndView("/tenant/map", "Model", model);
    }

    @RequestMapping("/common/search")
    public ModelAndView showSearch(@RequestParam("pageType") String pageType, @RequestParam("type")int type, @RequestParam("msg")String msg, @RequestParam("province")String province,
                                   @RequestParam("city")String city, @RequestParam("area")String area, @RequestParam("counts")int counts,
                                   @RequestParam("orientation")String orientation,@RequestParam("number")int number, @RequestParam("show")String show,
                                   @RequestParam("sx")String sx, Model model) {
        System.out.println("number:"+number);
        System.out.println("orientation="+orientation);
        TenantSearchPOJO ts = new TenantSearchPOJO();
        ts.setType(type);
        ts.setShow(show);
        ts.setSx(sx);
        if(StringUtils.isNotBlank(orientation) && !"0".equals(orientation)) {
            ts.setOrientation(orientation);
        }
        ts.setKind(counts+"0000");
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
        ts.setElevator(-1);
        System.out.println("ts.toString()"+ts.toString());
        PageHelper.startPage(number, 4);
        List<House> houses = houseService.queryHousesLikeMsg(ts, number);
        PageInfo<House> housePageInfo = new PageInfo<House>(houses);
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        List<Long> hids = null;
        if(user != null) {
            hids = houseService.queryCollectHouseIdsByUser(user.getUserId());
        }
        model.addAttribute("hids", hids);
        model.addAttribute("pageType", pageType);
        model.addAttribute("number", 1);
        model.addAttribute("housePageInfo", housePageInfo);
        return new ModelAndView("/tenant/show_search", "Model", model);
    }

    @RequestMapping("/common/all/search")
    public ModelAndView showAllSearch(@RequestParam("pageType")String pageType, @RequestParam("elevator")int elevator, @RequestParam("cycle")int cycle, @RequestParam("money1")int money1,
                                      @RequestParam("area1")int area1, @RequestParam("area2")int area2, @RequestParam("money2")int money2,
                                      @RequestParam("type")int type, @RequestParam("msg")String msg, @RequestParam("province")String province,
                                      @RequestParam("city")String city, @RequestParam("area")String area, @RequestParam("counts")int counts,
                                      @RequestParam("orientation")String orientation,@RequestParam("number")int number, @RequestParam("show")String show,
                                      @RequestParam("words")String words, @RequestParam("sx")String sx, Model model) {
        System.out.println("number="+number);
        System.out.println("words="+words);
        TenantSearchPOJO ts = new TenantSearchPOJO();
        ts.setType(type);
        ts.setShow(show);
        ts.setSx(sx);
        ts.setElevator(elevator);
        ts.setCycle(cycle);
        ts.setArea1(area1);
        ts.setArea2(area2);
        ts.setMoney1(money1);
        ts.setMoney2(money2);
        if(StringUtils.isNotBlank(orientation) && !"0".equals(orientation)) {
            ts.setOrientation(orientation);
        }
        ts.setKind(counts+"0000");
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
        if(StringUtils.isNotBlank(words)) {
            String[] arr = words.split("\\s+");
            int len = arr.length;
            if(len > 0) {
                for(int i = 1; i<len; i++) {
                    sb.append(",").append(arr[i]);
                }
            }
        }
        if(sb != null && sb.length() > 0) {
            sb.deleteCharAt(0);
            ts.setMsg(sb.toString());
        }
        System.out.println("ts.toString()"+ts.toString());
        PageHelper.startPage(number, 4);
        List<House> houses = houseService.queryHousesLikeMsg(ts, number);
        PageInfo<House> housePageInfo = new PageInfo<House>(houses);
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        List<Long> hids = null;
        if(user != null) {
            hids = houseService.queryCollectHouseIdsByUser(user.getUserId());
        }
        model.addAttribute("hids", hids);
        model.addAttribute("pageType", pageType);
        model.addAttribute("number", number);
        model.addAttribute("housePageInfo", housePageInfo);
        return new ModelAndView("/tenant/show_search", "Model", model);
    }

    @Async
    //@Scheduled(fixedDelay = 10000)  //间隔10秒
    //每天23点50分0秒执行
    @Scheduled(cron = "00 50 23 * * ?")
    public void autoEndContract(){
        //System.out.println("djflskjgdlkfgjdf");
        List<Contract> contracts = contractService.queryContractsByStatusAndTime(new java.sql.Timestamp(System.currentTimeMillis()));
        if(contracts!=null && contracts.size()>0) {
            for(Contract con:contracts) {
                contractService.updateContractStatus(con.getContractId(),con.getHouseId(),0,5);
                House house = houseService.queryHouseById(con.getHouseId());
                User user = userService.queryUserById(con.getUserId());
                String msg = "租房合同到期通知：租房合同编号为"+con.getContractId()+"的合约于今日到期，已自动更新合同和房源的状态，请及时安排工作人员前往实地帮助租客完成退租。" +
                        "房源("+house.getHouseId()+")"+house.getName()+":"+house.getAddress()+
                        "，租客("+user.getUserId()+")"+user.getUsername()+":"+user.getEmail();
                sendMailInfo(Constants.MAIL_SENDER,msg);
            }
        }
    }
}
