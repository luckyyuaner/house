package com.yuan.house.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.constants.ResultEnum;
import com.yuan.house.constants.TypeEnum;
import com.yuan.house.util.LoggerUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @description: 对没有登录的请求进行拦截, 全部返回json信息.
 * 覆盖掉shiro原本的跳转login.jsp的拦截方式
 */
public class AjaxPermissionsAuthorizationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", TypeEnum.T_result.getTypeCode());
		jsonObject.put("code", ResultEnum.R_out.getResCode());
		jsonObject.put("msg", ResultEnum.R_out.getResMsg());
		PrintWriter out = null;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json");
			out = response.getWriter();
			out.println(jsonObject);
		} catch (Exception e) {
		    LoggerUtil.error("AjaxPermissionsAuthorizationFilter onAccessDenied 返回json异常",e);
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
		return false;
	}

	@Bean
	public FilterRegistrationBean registration(AjaxPermissionsAuthorizationFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean(filter);
		registration.setEnabled(false);
		return registration;
	}
}
