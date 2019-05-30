package com.yuan.house.controller;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.config.websocket.WebSocketConfig;
import com.yuan.house.constants.Constants;
import com.yuan.house.model.Contract;
import com.yuan.house.model.House;
import com.yuan.house.model.User;
import com.yuan.house.service.ContractService;
import com.yuan.house.service.HouseService;
import com.yuan.house.service.UserService;
import com.yuan.house.util.FileUtil;
import com.yuan.house.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 房东Controller，进行权限控制
 */
@Controller
public class LandlordController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private ContractService contractService;

    @RequestMapping("/landlord/chat")
    public ModelAndView showLandlordChat(Model model) {
        Map<String, String> onlines = WebSocketConfig.getUsers();
        Set<User> managers = new HashSet<>();
        for(String name : onlines.keySet()) {
            User u = userService.queryUserByName(name);
            //if(u.getUserType() == 0) {
                managers.add(u);
            //}
        }
        model.addAttribute("onlines", managers);
        System.out.println("================================");
        System.out.println(onlines);
        System.out.println("================================");
        return new ModelAndView("/landlord/chat", "Model", model);
    }

    @RequestMapping("/landlord/info")
    public ModelAndView showLandlordInfo(Model model) {
        return new ModelAndView("/landlord/info");
    }

    @RequiresPermissions("house:create")
    @RequestMapping("/house/showNew")
    public ModelAndView showHouseNew(Model model) {
        model.addAttribute("house", new House());
        return new ModelAndView("/landlord/new_house", "Model", model);
    }

    @RequiresPermissions("house:read")
    @RequestMapping("/house/show")
    public ModelAndView showHouses(Model model) {
        List<House> houses = houseService.getHousesByUser();
        System.out.println("length:"+houses.size());
        for(House h : houses) {
            System.out.println("url:"+h.getUrls());
        }
        model.addAttribute("houses", houses);
        model.addAttribute("type", "house_manager");
        return new ModelAndView("/landlord/info", "Model", model);
    }

    @RequiresPermissions("house:create")
    @PostMapping("/house/new")
    public ModelAndView newHouse(@ModelAttribute(value = "house") House house, @RequestParam MultipartFile[] url, @RequestParam("location")String location, Model model) {
        if(StringUtils.isNotBlank(location)) {
            String[] arr = location.split(",");
            house.setLongitude(Double.parseDouble(arr[0]));
            house.setLatitude(Double.parseDouble(arr[1]));
        }
        JSONObject json = FileUtil.uploads(url);
        if("fail".equals(json.getString("rs"))) {
            model.addAttribute("msg", json.getString("msg"));
            return new ModelAndView("/landlord/new_house", "Model", model);
        }
        house.setStatus(0);
        house.setUrls(json.getString("msg"));
        houseService.addHouse(house);
        return new ModelAndView("/landlord/info", "Model", model);
    }

    @RequiresPermissions("house:update")
    @GetMapping("/house/showUpdate")
    public ModelAndView showUpdateHouse(Model model, Long id) {
        model.addAttribute("house", houseService.queryHouseById(id));
        return new ModelAndView("/landlord/update_house", "Model", model);
    }

    @RequiresPermissions("house:update")
    @PostMapping("/house/updateHouse")
    public ModelAndView updateHouse(@ModelAttribute(value = "house") House house, @RequestParam MultipartFile[] url, @RequestParam("location")String location, Model model) {
        if(StringUtils.isNotBlank(location)) {
            String[] arr = location.split(",");
            house.setLongitude(Double.parseDouble(arr[0]));
            house.setLatitude(Double.parseDouble(arr[1]));
        }
        JSONObject json = FileUtil.uploads(url);
        if("fail".equals(json.getString("rs"))) {
            model.addAttribute("msg", json.getString("msg"));
            return new ModelAndView("redirect:/house/showUpdate?id="+house.getHouseId());
        }
        house.setStatus(0);
        house.setUrls(json.getString("msg"));
        houseService.updateHouse(house);
        model.addAttribute("type", "house_manager");
        return new ModelAndView("/landlord/info", "Model", model);
    }
    @RequiresPermissions("house:delete")
    @GetMapping("/house/deleteHouse")
    public ModelAndView deleteHouse(Model model, Long id) {
        houseService.deleteHouse(id);
        model.addAttribute("type", "house_manager");
        return new ModelAndView("/landlord/info", "Model", model);
    }


    //@RequiresPermissions("contract:update")
    @RequestMapping("/common/landlord/contract/showDetail")
    public ModelAndView showContractDetail(Model model, @RequestParam("cid")Long cid) {
        Contract contract = contractService.queryContractById(cid);
        House house = houseService.queryHouseById(contract.getHouseId());
        model.addAttribute("contract",contract);
        model.addAttribute("house",house);
        model.addAttribute("tenant", userService.queryUserById(contract.getUserId()));
        return new ModelAndView("/landlord/show_contract", "Model", model);
    }

    //@RequiresPermissions("contract:read")
    @RequestMapping("/common/landlord/contract/download")
    public void contractDownload(HttpServletResponse res, @RequestParam("url")String url) {
        res.setContentType("application/octet-stream");
        res.setHeader("content-type", "application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;fileName=" + url);// 设置文件名
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        File file = null;
        FileInputStream fis = null;
        try {
            os = res.getOutputStream();
            file = new File(Constants.UPLOAD_URL, url);
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buff);
            while(i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        }
        catch(FileNotFoundException e) {
            LoggerUtil.error("找不到相应文件", e);
        }
        catch(IOException e) {
            LoggerUtil.error("下载文件失败", e);
        }
        finally {
            try {
                if(fis != null) {
                    fis.close();
                }
                if(bis != null) {
                    bis.close();
                }
                if(os != null) {
                    os.close();
                }
            }
            catch(IOException e) {
                LoggerUtil.error("下载文件流关闭失败", e);
            }
        }
        System.out.println("success");
    }
}
