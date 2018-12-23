package com.great.mapper;

import java.util.List;
import java.util.Map;

public interface RoleMenuMapper {
	public List<Map<String,Object>> queryAll(int roleId);//查询所有
	public int deleteRoleMenu(int roleId);//删除角色对应的菜单
	public int addRoleMenu(int roleId,int menuId);//添加权限菜单
}
