package com.yuan.house.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yuan.house.constants.TypeEnum;
import com.yuan.house.dao.PermissionDao;
import com.yuan.house.model.Permission;
import com.yuan.house.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	/**
	 * 查询用户的所有权限
	 */
	@Override
	public JSONObject getUserPermissions(int userId) {
		Set<Permission> userPermissions = permissionDao.getUserPermissions(userId);
		JSONObject rs = new JSONObject();
		rs.put("type", TypeEnum.T_list.getTypeCode());
		rs.put("permissionList", userPermissions);
		return rs;
	}
}
