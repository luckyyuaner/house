package com.yuan.house.constants;

import java.util.HashSet;
import java.util.Set;

/**
 * 常量类
 */
public class Constants {
	/**
	 * session中存放用户信息的key值
	 */
	public static final String SESSION_CURR_USER = "currUser";
    public static final String MAIL_SENDER = "1326652059@qq.com";
	public static final String SESSION_USER_PERMISSIONS = "userPermissions";
	public static final String SESSION_STRING_USER_PERMISSIONS = "userStringPermissions";
	public static final String UPLOAD_URL = "D:\\graduate\\house\\src\\main\\resources\\static\\file\\";
	public static final Set<String> SEARCH_EXCEPT_WORDS = new HashSet<String>() {{
        add("不限");
        add("市辖区");
        add("县");
        add("市");
        add("省直辖行政单位");
        add("省直辖县级行政单位");
    }};

}
