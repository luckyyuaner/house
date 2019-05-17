package com.yuan.house.controller;

import com.yuan.house.config.websocket.WebSocketConfig;
import com.yuan.house.model.House;
import com.yuan.house.model.User;
import com.yuan.house.service.UserService;
import com.yuan.house.util.LoggerUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


/**
 * 房东Controller，进行权限控制
 */
@Controller
public class LandlordController extends BaseController {

    @Autowired
    private UserService userService;

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

    @RequiresPermissions("house:create")
    @PostMapping("/house/new")
    public ModelAndView newHouse(@ModelAttribute(value = "house") House house, @RequestParam MultipartFile[] urls, @RequestParam("location")String location, Model model) {
        System.out.println("location:"+location);
        StringBuffer sb = new StringBuffer();
        if (urls.length <= 0) {
            model.addAttribute("msg", "未选择店铺图片");
            return new ModelAndView("/landlord/new_house", "Model", model);
        }
        try {
            for (MultipartFile item : urls) {
                if (item.getSize() <= 0) {
                    // 未选择文件
                    model.addAttribute("msg", "未选择文件");
                    return new ModelAndView("/landlord/new_house", "Model", model);
                }
                String filename = item.getOriginalFilename();
                if (!filename.endsWith("jpg") && !filename.endsWith("gif") && !filename.endsWith("png")) {
                    // 限制文件上传类型
                    model.addAttribute("msg", "文件类型不是图片");
                    return new ModelAndView("/landlord/new_house", "Model", model);
                }
                String leftPath = "D:\\graduate\\house\\src\\main\\resources\\static\\file\\";
                String newFileName = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
                File newFile = new File(leftPath + newFileName);
                // 将内存中的数据写入磁盘
                item.transferTo(newFile);
                sb.append(newFileName);
                sb.append(",");
                System.out.println("文件上传成功");
            }
            sb.deleteCharAt(sb.length() - 1);
        } catch (IOException e) {
            LoggerUtil.error("写入新文件出错：",e);
        }
        house.setUrl(sb.toString());
        System.out.println("url:"+house.getUrl());
        System.out.println("kind:"+house.getKind());
        return new ModelAndView("/landlord/info", "Model", model);
    }
}
