package com.yuan.house.exception;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.constants.TypeEnum;
import com.yuan.house.constants.ResultEnum;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常拦截
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	/**
	 * GET/POST请求方法错误的拦截器,发生在进入controller之前
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public JSONObject httpRequestMethodHandler() {
	    JSONObject res = new JSONObject();
	    res.put("type", TypeEnum.T_result.getTypeCode());
	    res.put("code", ResultEnum.R_500.getResCode());
	    res.put("msg", ResultEnum.R_500.getResMsg());
        return res;
	}

	/**
	 * 权限不足报错拦截
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public JSONObject unauthorizedExceptionHandler() {
        JSONObject res = new JSONObject();
        res.put("type", TypeEnum.T_result.getTypeCode());
        res.put("code", ResultEnum.R_unauth.getResCode());
        res.put("msg", ResultEnum.R_unauth.getResMsg());
        return res;
	}

	/**
	 * 未登录报错拦截
	 */
	@ExceptionHandler(UnauthenticatedException.class)
	public JSONObject unauthenticatedException() {
        JSONObject res = new JSONObject();
        res.put("type", TypeEnum.T_result.getTypeCode());
        res.put("code", ResultEnum.R_out.getResCode());
        res.put("msg", ResultEnum.R_out.getResMsg());
        return res;
	}
}
