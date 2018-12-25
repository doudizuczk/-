package com.great.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Role;

public interface RoleMapper {
	public List<Map<String,Object>>queryAllRole();//czk查询所有角色
	public List<Map<String,Object>>queryRole(Role role);//查询所有角色
	public int startRole(int roleId);//启用角色;
	public int stopRole(int roleId);//禁用角色
	public int createRole(@Param("roleName")String roleName);//新增角色
}
