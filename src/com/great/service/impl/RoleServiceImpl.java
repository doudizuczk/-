package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Role;
import com.great.mapper.RoleMapper;
import com.great.service.IRoleService;
@Service("roleServiceImpl")
public class RoleServiceImpl implements IRoleService {

	
	@Autowired
	private RoleMapper RoleMapper;
	
	@Override//查询所有角色
	public List<Map<String, Object>> queryAllRoleList() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = RoleMapper.queryAllRole();
		return list;
	}

	@Override
	public List<Map<String, Object>> queryRole(Role role) {
		return RoleMapper.queryRole(role);
	}

	@Override
	public int startRole(int roleId) {
		// TODO Auto-generated method stub
		return RoleMapper.startRole(roleId);
	}

	@Override
	public int stopRole(int roleId) {
		// TODO Auto-generated method stub
		return RoleMapper.stopRole(roleId);
	}

	@Override
	public int createRole(String roleName) {
		// TODO Auto-generated method stub
		return RoleMapper.createRole(roleName);
	}

}
