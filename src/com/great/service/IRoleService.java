package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Role;

public interface IRoleService {
	public List<Map<String,Object>>queryAllRoleList();//czk查询所有角色
	public List<Map<String,Object>>queryRole(Role role);//查询所有角色
	public int startRole(int roleId);//启用角色;
	public int stopRole(int roleId);//禁用角色
	public int createRole(String roleName);//新增角色
}
