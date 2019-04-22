package com.yuan.house.service.impl;

import com.yuan.house.constants.Constants;
import com.yuan.house.dao.PermissionDao;
import com.yuan.house.model.Permission;
import com.yuan.house.model.User;
import com.yuan.house.service.CommonService;
import com.yuan.house.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	@Autowired
	private CommonService commonService;

	/**
	 * 查询当前登录用户的权限等信息
	 */
	@Override
	public Set<Permission> getUserPermissions() {
		//从session获取用户信息
		Session session = SecurityUtils.getSubject().getSession();
		User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
		String username = user.getUsername();
        Set<Permission> userPermissions = permissionDao.getUserPermissions(username);
        session.setAttribute(Constants.SESSION_USER_PERMISSIONS, userPermissions);
		Set<String> userStringPermissions = new HashSet<String>();
		for(Permission p : userPermissions) {
		    userStringPermissions.add(p.getPermissionValue());
        }
        session.setAttribute(Constants.SESSION_STRING_USER_PERMISSIONS, userStringPermissions);
        return userPermissions;
	}

	@Override
	public List<Permission> getAllPermissions() {
		return permissionDao.getAllPermissions();
	}

	@Override
	public Permission queryPermissionById(Long id) {
        String key = "permission_" + id;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (Permission)rs;
        }
        Permission per = permissionDao.queryPermissionById(id);
        commonService.insertRedis(key, per);
		return per;
	}

	@Override
	public List<Permission> queryPermissionLikeMsg(String msg) {
        String key = "permissions_like_" + msg;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<Permission>)rs;
        }
        List<Permission> pers = permissionDao.queryPermissionLikeMsg(msg);
        commonService.insertRedis(key, pers);
        return pers;
	}

	@Override
	public Long addPermission(Permission object) {
		return permissionDao.addPermission(object);
	}

	@Override
	public int updatePermission(Permission object) {
	    String key = "permission_" + object.getPermissionId();
	    commonService.deleteRedis(key);
		return permissionDao.updatePermission(object);
	}

	@Override
	public int deletePermission(Long id) {
        String key = "permission_" + id;
        commonService.deleteRedis(key);
		return permissionDao.deletePermission(id);
	}
}
