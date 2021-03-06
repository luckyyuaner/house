package com.yuan.house.config.shiro;

import com.yuan.house.constants.Constants;
import com.yuan.house.model.User;
import com.yuan.house.service.UserService;
import com.yuan.house.util.LoggerUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;

	@Override
	@SuppressWarnings("unchecked")
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Session session = SecurityUtils.getSubject().getSession();
		//查询用户的权限
        Collection<String> permission = (Collection<String>) session.getAttribute(Constants.SESSION_STRING_USER_PERMISSIONS);
		LoggerUtil.info("permission的值为:" + permission);
		//为当前用户设置角色和权限
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions(permission);
		return authorizationInfo;
	}

	/**
	 * 验证当前登录的Subject
	 * LoginController.login()方法中执行Subject.login()时 执行此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		String username = (String) authcToken.getPrincipal();
		// 获取用户密码
		String password = new String((char[]) authcToken.getCredentials());
		User user = userService.getUser(username, password);
		if (user == null) {
			//没找到帐号
            LoggerUtil.error("用户{}不存在",username);
			throw new UnknownAccountException();
		}
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user.getUsername(),
				user.getPassword(),
				//ByteSource.Util.bytes("salt"), salt=username+salt,采用明文访问时，不需要此句
				getName()
		);
		//session中不需要保存密码
		//user.setPassword("");
		//将用户信息放入session中
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_CURR_USER, user);
		return authenticationInfo;
	}
}
