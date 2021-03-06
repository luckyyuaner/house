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
import org.springframework.transaction.annotation.Transactional;

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
	public List<Permission> getAllPermissions(int number) {
        String key = "permissions_number_" + number;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<Permission>)rs;
        }
        List<Permission> pers = permissionDao.getAllPermissions();
        commonService.insertRedis(key, pers);
        return pers;
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
	public List<Permission> queryPermissionLikeMsg(String msg, int number) {
        String key = "permissions_like_" + msg +"_number_" + number;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<Permission>)rs;
        }
        List<Permission> pers = permissionDao.queryPermissionLikeMsg(msg);
        commonService.insertRedis(key, pers);
        return pers;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int addPermission(Permission object) {
	    if(object.getParentId()!=0) {
            Permission parent = permissionDao.queryPermissionById(object.getParentId());
            object.setType(parent.getType()+1);
        }
		return permissionDao.addPermission(object);
	}

	@Override
    @Transactional(rollbackFor=Exception.class)
	public int updatePermission(Permission object) {
	    String key = "permission_" + object.getPermissionId();
	    commonService.deleteRedis(key);
	    commonService.deleteByPrex("permissions_");
	    //commonService.deleteByPrex("role_");
		//commonService.deleteByPrex("roles_");
		//commonService.deleteByPrex("user_");
		//commonService.deleteByPrex("users_");
        if(object.getParentId()!=0) {
            Permission parent = permissionDao.queryPermissionById(object.getParentId());
            object.setType(parent.getType()+1);
        }
		return permissionDao.updatePermission(object);
	}

    @Override
    public List<Permission> getAllPermissionsByNoPage() {
        String key = "permissions_all";
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<Permission>)rs;
        }
        List<Permission> pers = permissionDao.getAllPermissionsByNoPage();
        commonService.insertRedis(key, pers);
        return pers;
    }

    @Override
	public int deletePermission(Long id) {
        String key = "permission_" + id;
        commonService.deleteRedis(key);
		commonService.deleteByPrex("permissions_");
		//commonService.deleteByPrex("role_");
		//commonService.deleteByPrex("roles_");
		//commonService.deleteByPrex("user_");
		//commonService.deleteByPrex("users_");
		return permissionDao.deletePermission(id);
	}
}
