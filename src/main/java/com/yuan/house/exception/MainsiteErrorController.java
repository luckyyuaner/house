package com.yuan.house.exception;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.constants.TypeEnum;
import com.yuan.house.constants.ResultEnum;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统错误拦截, 主要是针对404的错误
 */
@Controller
public class MainsiteErrorController implements ErrorController {

	private static final String ERROR_PATH = "/error";

	/**
	 * 404页面改为返回此json
	 */
	@RequestMapping(value = ERROR_PATH)
	@ResponseBody
	public JSONObject handleError() {
        JSONObject res = new JSONObject();
        res.put("type", TypeEnum.T_result.getTypeCode());
        res.put("code", ResultEnum.R_404.getResCode());
        res.put("msg", ResultEnum.R_404.getResMsg());
        return res;
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}

