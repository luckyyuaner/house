package com.yuan.house.util;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.constants.Constants;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件处理类
 */
public class FileUtil {
    public static JSONObject upload(MultipartFile item) {
        JSONObject json = new JSONObject();
        if (item == null ||item.getSize() <= 0) {
            // 未选择文件
            json.put("rs", "fail");
            json.put("msg", "未选择文件");
            return json;
        }
        String filename = item.getOriginalFilename();
        if (!filename.endsWith("jpg") && !filename.endsWith("gif") && !filename.endsWith("png")) {
            // 限制文件上传类型
            json.put("rs", "fail");
            json.put("msg", "文件类型不是图片");
            return json;
        }
        String newFileName = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
        File newFile = new File(Constants.UPLOAD_URL + newFileName);
        // 将内存中的数据写入磁盘
        try {
            item.transferTo(newFile);
        } catch (IOException e) {
            LoggerUtil.error("文件上传出错：",e);
            json.put("rs", "fail");
            json.put("msg", "文件上传出错");
            return json;
        }
        json.put("rs", "success");
        json.put("msg", newFileName);
        return json;
    }


    public static JSONObject uploadByNumber(MultipartFile[] urls, int number) {
        JSONObject json = new JSONObject();
        StringBuffer sb = new StringBuffer();
        if (urls == null || urls.length <number) {
            json.put("rs", "fail");
            json.put("msg", "未选择文件");
            return json;
        }
        try {
            int count = 0;
            for (MultipartFile item : urls) {
                if(count >= number) {
                    break;
                }
                if (item.getSize() <= 0) {
                    // 未选择文件
                    json.put("rs", "fail");
                    json.put("msg", "未选择文件");
                    return json;
                }
                String filename = item.getOriginalFilename();
                if (!filename.endsWith("jpg") && !filename.endsWith("gif") && !filename.endsWith("png")) {
                    // 限制文件上传类型
                    json.put("rs", "fail");
                    json.put("msg", "文件类型不是图片");
                    return json;
                }
                String newFileName =  UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
                File newFile = new File(Constants.UPLOAD_URL + newFileName);
                // 将内存中的数据写入磁盘
                item.transferTo(newFile);
                sb.append(newFileName);
                sb.append(",");
                count++;
            }
            sb.deleteCharAt(sb.length() - 1);
        } catch (IOException e) {
            LoggerUtil.error("写入新文件出错：",e);
            json.put("rs", "fail");
            json.put("msg", "文件上传出错");
            return json;
        }
        json.put("rs", "success");
        json.put("msg", sb.toString());
        return json;
    }

    public static JSONObject uploads(MultipartFile[] urls) {
        JSONObject json = new JSONObject();
        StringBuffer sb = new StringBuffer();
        if (urls == null || urls.length <= 0) {
            json.put("rs", "fail");
            json.put("msg", "未选择文件");
            return json;
        }
        try {
            for (MultipartFile item : urls) {
                if (item.getSize() <= 0) {
                    // 未选择文件
                    json.put("rs", "fail");
                    json.put("msg", "未选择文件");
                    return json;
                }
                String filename = item.getOriginalFilename();
                if (!filename.endsWith("jpg") && !filename.endsWith("gif") && !filename.endsWith("png")) {
                    // 限制文件上传类型
                    json.put("rs", "fail");
                    json.put("msg", "文件类型不是图片");
                    return json;
                }
                String newFileName =  UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
                File newFile = new File(Constants.UPLOAD_URL + newFileName);
                // 将内存中的数据写入磁盘
                item.transferTo(newFile);
                sb.append(newFileName);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        } catch (IOException e) {
            LoggerUtil.error("写入新文件出错：",e);
            json.put("rs", "fail");
            json.put("msg", "文件上传出错");
            return json;
        }
        json.put("rs", "success");
        json.put("msg", sb.toString());
        return json;
    }
}
