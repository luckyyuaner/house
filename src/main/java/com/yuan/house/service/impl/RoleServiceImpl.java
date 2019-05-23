package com.yuan.house.service.impl;

import com.yuan.house.dao.RoleDao;
import com.yuan.house.model.Role;
import com.yuan.house.model.RolePermission;
import com.yuan.house.service.CommonService;
import com.yuan.house.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Role queryRoleByName(String name) {
        return roleDao.queryRoleByName(name);
    }

    @Override
	public List<Role> queryRoleLikeMsg(String msg) {
        String key = "roles_like_" + msg;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<Role>)rs;
        }
        List<Role> roles = roleDao.queryRoleLikeMsg(msg);
        commonService.insertRedis(key, roles);
        return roles;
	}

    @Transactional(rollbackFor=Exception.class)
	@Override
	public void addRole(Role object, String pid) {
		roleDao.addRole(object);
        if(StringUtils.isNotBlank(pid)){
            Role role = roleDao.queryRoleByName(object.getName());
            RolePermission rp = new RolePermission();
            rp.setRoleId(role.getRoleId());
            String[] pds = pid.split(",");
            for(String ps: pds) {
                rp.setPermissionId(Long.parseLong(ps));
                roleDao.addRolePermission(rp);
            }
        }
	}

    @Transactional(rollbackFor=Exception.class)
	@Override
	public int updateRole(Role object, String pid) {
	    String key = "role_" + object.getRoleId();
	    commonService.deleteRedis(key);
	    commonService.deleteByPrex("roles_");
        commonService.deleteByPrex("users_role_");
        //commonService.deleteByPrex("user_");
        //commonService.deleteByPrex("users_");
		int rs = roleDao.updateRole(object);
		roleDao.deleteRolePermission(object.getRoleId());
        if(StringUtils.isNotBlank(pid)){
            String[] pds = pid.split(",");
            RolePermission rp = new RolePermission();
            rp.setRoleId(object.getRoleId());
            for(String ps: pds) {
                rp.setPermissionId(Long.parseLong(ps));
                roleDao.addRolePermission(rp);
            }
        }
        return rs;
	}

	@Override
	public int deleteRole(Long id) {
        String key = "role_" + id;
        commonService.deleteRedis(key);
        commonService.deleteByPrex("roles_");
        commonService.deleteByPrex("users_role_");
        //commonService.deleteByPrex("user_");
        //commonService.deleteByPrex("users_");
		return roleDao.deleteRole(id);
	}
}
