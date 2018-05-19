/*
 * @(#)RoleServiceImpl.java 2018年5月19日
 * 
 * Copyright (c), 2018-2020 赵名阳（shining everyday.）
 * 
 * 著作权人保留一切权利，任何使用需经授权。
 */
package com.common.service.base.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.command.RoleCommand;
import com.common.dao.base.RoleDAO;
import com.common.dao.base.RolePermissionDAO;
import com.common.dao.base.UserRoleDAO;
import com.common.pojo.base.Role;
import com.common.pojo.base.RolePermission;
import com.common.pojo.base.User;
import com.common.service.base.RoleService;

/**
 *
 * @author Administrator
 * @date 2018年5月19日 下午2:42:18
 * @version V1.0.0
 * description：
 * 
 */
@Service
public class RoleServiceImpl implements RoleService {

	private static final Long ADMIN_ID = 1L;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Autowired
	private RolePermissionDAO rolePermissionDAO;
	
	@Override
	public Role getRole(Long roleId) {
		return roleDAO.selectByPrimaryKey(roleId);
	}

	@Override
	public int createRole(RoleCommand roleCommand) {
		Role role = new Role();
		role.setName(roleCommand.getName());
		role.setDescription(roleCommand.getDescription());
		int result = roleDAO.insertSelective(role);
		if (result == 1) {
			String[] permissions = roleCommand.getPermissions().split(",");
			RolePermission rolePermission = null;
			for (String element : permissions) {
				rolePermission = new RolePermission();
				rolePermission.setRoleId(role.getId());
				rolePermission.setElement(element);
				rolePermissionDAO.insertSelective(rolePermission);
			}
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int deleteRole(Long roleId) {
		int result = roleDAO.deleteByPrimaryKey(roleId);
		if (result == 1) {
			//清理权限
			rolePermissionDAO.deletePermissionsByRoleId(roleId);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int updateRole(Role role, RoleCommand roleCommand) {
		role.setName(roleCommand.getName());
		role.setDescription(roleCommand.getDescription());
		int result = roleDAO.updateByPrimaryKeySelective(role);
		if (result == 1) {
			//首先清理权限
			rolePermissionDAO.deletePermissionsByRoleId(role.getId());
			//重新赋予权限
			String[] permissions = roleCommand.getPermissions().split(",");
			RolePermission rolePermission = null;
			for (String element : permissions) {
				rolePermission = new RolePermission();
				rolePermission.setRoleId(role.getId());
				rolePermission.setElement(element);
				rolePermissionDAO.insertSelective(rolePermission);
			}
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int initAdminRole(User user, Set<String> permissions) {
		List<Long> list = userRoleDAO.selectRoleIdsByUserId(user.getId());
		if (list != null && !list.isEmpty()) {
			// 拥有超级管理员1L的角色才可以进行初始化
			if (list.contains(ADMIN_ID)) {
				//首先清理权限
				rolePermissionDAO.deletePermissionsByRoleId(ADMIN_ID);
				//重新赋予权限
				RolePermission rolePermission = null;
				for (String element : permissions) {
					rolePermission = new RolePermission();
					rolePermission.setRoleId(ADMIN_ID);
					rolePermission.setElement(element);
					rolePermissionDAO.insertSelective(rolePermission);
				}
				return 1;
			}
		}
		return 0;
	}

	@Override
	public List<Role> getAllRole() {
		return roleDAO.getAllRole();
	}

}