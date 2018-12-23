package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.mapper.RoleMenuMapper;
import com.great.service.IRoleMenuService;

@Service
public class RoleMenuServiceImpl implements IRoleMenuService {
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	@Override
	public List<Map<String, Object>> queryAll(int roleId) {
		// TODO Auto-generated method stub
		return roleMenuMapper.queryAll(roleId);
	}
	@Override
	public int deleteRoleMenu(int roleId) {
		// TODO Auto-generated method stub
		return roleMenuMapper.deleteRoleMenu(roleId);
	}
	@Override
	public int addRoleMenu(int roleId, int menuId) {
		// TODO Auto-generated method stub
		return roleMenuMapper.addRoleMenu(roleId, menuId);
	}

}
