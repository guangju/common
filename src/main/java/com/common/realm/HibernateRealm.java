package com.common.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.common.dao.base.RolePermissionDAO;
import com.common.dao.base.UserDAO;
import com.common.dao.base.UserRoleDAO;
import com.common.pojo.base.Role;
import com.common.pojo.base.User;

/**
 * Configured Apache Shiro Realm.
 */
@SuppressWarnings("deprecation")
@Component
public class HibernateRealm extends AuthorizingRealm {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private RolePermissionDAO rolePermissionDAO;

	public HibernateRealm() {
		// This name must match the name in the User class's getPrincipals() method
		setName("hibernateRealm"); 
		setCredentialsMatcher(new Sha256CredentialsMatcher());
		setCacheManager(new MemoryConstrainedCacheManager());
	}

	/**
	 * 登录认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		// 测试冲突
		// UsernamePasswordToken对象用来存放提交的登录信息
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 查出是否有此用户
		User user = userDAO.selectByUsername(token.getUsername());
		if (user != null) {
			// 若存在，将此用户存放到登录认证info中
			return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
		} else {
			return null;
		}
	}

	 /** 
     * 权限认证 
     */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取登录时输入的id
		Long userId = (Long) principals.fromRealm(getName()).iterator().next();
		//到数据库查是否有此对象  
		User user = userDAO.selectByPrimaryKey(userId);
		if (user != null) {
			//权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<Role> list = userRoleDAO.selectRolesByUserId(userId);
			if (list != null && !list.isEmpty()) {
				for (Role role : list) {
					info.addRole(role.getName());
					info.addStringPermissions(rolePermissionDAO.selectPermissionsByRoleId(role.getId()));
				}
				return info;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
