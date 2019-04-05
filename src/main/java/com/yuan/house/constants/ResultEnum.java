package com.yuan.house.constants;

/**
 * 返回信息枚举类
 */
public enum ResultEnum {
	R_400("400", "请求处理异常，请稍后再试"),
	R_500("500", "请求方式有误,请检查 GET/POST"),
	R_404("404", "请求路径不存在"),
	R_unauth("unauth", "权限不足"),
	R_exist("exist", "账户已存在"),
	R_out("out", "登陆已过期,请重新登陆"),
	R_required("required", "缺少必填参数"),
	R_wrong("wrong", "账号密码错误"),
	R_error("error", "操作错误"),
    R_related("related", "删除失败,存在关联"),
	R_success("success", "操作成功");

	private String resCode;

	private String resMsg;

	ResultEnum(String resCode, String resMsg) {
		this.resCode = resCode;
		this.resMsg = resMsg;
	}

	public String getResCode() {
		return resCode;
	}

	public String getResMsg() {
		return resMsg;
	}

}