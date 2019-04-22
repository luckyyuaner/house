package com.yuan.house.service.impl;

import com.yuan.house.dao.RoleDao;
import com.yuan.house.model.Role;
import com.yuan.house.service.CommonService;
import com.yuan.house.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private CommonService commonService;

	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAllRoles();
	}

	@Override
	public Role queryRoleById(Long id) {
        String key = "role_" + id;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (Role)rs;
        }
        Role role = roleDao.queryRoleById(id);
        commonService.insertRedis(key, role);
		return role;
	}

	@Override
	public List<Role> queryRoleLikeMsg(String msg) {
        String key = "role_like_" + msg;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<Role>)rs;
        }
        List<Role> roles = roleDao.queryRoleLikeMsg(msg);
        commonService.insertRedis(key, roles);
        return roles;
	}

	@Override
	public Long addRole(Role object) {
		return roleDao.addRole(object);
	}

	@Override
	public int updateRole(Role object) {
	    String key = "role_" + object.getRoleId();
	    commonService.deleteRedis(key);
		return roleDao.updateRole(object);
	}

	@Override
	public int deleteRole(Long id) {
        String key = "role_" + id;
        commonService.deleteRedis(key);
		return roleDao.deleteRole(id);
	}
}
