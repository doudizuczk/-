package com.great.service;

import java.util.List;
import java.util.Map;

public interface IRoleMenuService {
	public List<Map<String,Object>> queryAll(int roleId);//查询所有
	public int deleteRoleMenu(int roleId);//删除角色对应的菜单
	public int addRoleMenu(int roleId,int menuId);//添加权限菜单
}
