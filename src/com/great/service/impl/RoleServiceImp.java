package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.mapper.RoleMapper;
import com.great.service.IRoleService;
@Service("roleServiceImp")
public class RoleServiceImp implements IRoleService {

	
	@Autowired
	private RoleMapper RoleMapper;
	
	@Override//查询所有角色
	public List<Map<String, Object>> queryAllRoleList() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = RoleMapper.queryAllRole();
		return list;
	}

}
