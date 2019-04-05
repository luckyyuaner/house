package com.yuan.house.util;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.constants.ResultEnum;
import com.yuan.house.constants.TypeEnum;
import org.apache.commons.lang3.StringUtils;


/**
 * 字段校验工具类
 */
public class FieldUtil {

	/**
	 * 验证是否含有全部必填字段
	 *
	 * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
	 */
	public static JSONObject checkRequiredFields(JSONObject jsonObject, String requiredColumns) {
		JSONObject res = new JSONObject();
		res.put("type", TypeEnum.T_result.getTypeCode());
		if(StringUtils.isBlank(requiredColumns)) {
            res.put("code", ResultEnum.R_success.getResCode());
            res.put("msg", ResultEnum.R_success.getResMsg());
            return res;
		}

		//验证字段非空
        String[] columns = requiredColumns.split(",");
		StringBuffer missCol = new StringBuffer();
		for (String column : columns) {
		    String val = (String)jsonObject.get(column.trim());
		    if (StringUtils.isBlank(val)) {
		        missCol.append(missCol);
		    }
		}
		if(StringUtils.isBlank(missCol)) {
            res.put("code", ResultEnum.R_success.getResCode());
            res.put("msg", ResultEnum.R_success.getResMsg());
            return res;
        }
        res.put("code", ResultEnum.R_required.getResCode());
		res.put("msg", missCol.append(ResultEnum.R_required.getResMsg()));
		return res;
	}
}
