package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Menu;

public interface MenuMapper {
	public List<Map<String,Object>> queryAllMenu();//查询所有菜单
	public List<Map<String,Object>> queryFirstMenu();//查询一级菜单
	public Integer createNewMenu(Menu menu);//新增菜单
	public Integer manageMenu(Integer menuId);//管理菜单(启用)
	public Integer stopMenu(Integer menuId);//禁用菜单
}
