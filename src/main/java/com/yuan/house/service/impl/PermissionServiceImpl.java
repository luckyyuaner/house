package com.yuan.house.service.impl;

import com.yuan.house.constants.Constants;
import com.yuan.house.dao.PermissionDao;
import com.yuan.house.model.Permission;
import com.yuan.house.model.User;
import com.yuan.house.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	/**
	 * 查询当前登录用户的权限等信息
	 */
	@Override
	public Set<String> getUserPermissions() {
		//从session获取用户信息
		Session session = SecurityUtils.getSubject().getSession();
		User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
		String username = user.getUsername();
        Set<String> userPermissions = permissionDao.getUserPermissions(username);
		session.setAttribute(Constants.SESSION_USER_PERMISSIONS, userPermissions);
		return userPermissions;
	}

	@Override
	public List<Permission> getAllPermissions() {
		return permissionDao.getAllPermissions();
	}

	@Override
	public Permission queryPermissionById(Long id) {
		return permissionDao.queryPermissionById(id);
	}

	@Override
	public List<Permission> queryPermissionLikeName(String name) {
		return permissionDao.queryPermissionLikeName(name);
	}

	@Override
	public Long addPermission(Permission object) {
		return permissionDao.addPermission(object);
	}

	@Override
	public int updatePermission(Permission object) {
		return permissionDao.updatePermission(object);
	}

	@Override
	public int deletePermission(Long id) {
		return permissionDao.deletePermission(id);
	}
}
