package com.great.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Menu;
import com.great.mapper.MenuMapper;
import com.great.service.IMenuService;

@Service("menuServiceImpl")
public class MenuServiceImpl implements IMenuService{
	@Autowired
	private MenuMapper menuMapper;

	@Override//查询所有菜单1
	public  List<Map<String,Object>> queryAllMenu(){
		List<Map<String,Object>> menuList=null;
		menuList=menuMapper.queryAllMenu();
		return menuList;
	}

	@Override //查询一级菜单
	public List<Map<String, Object>> queryFirstMenu() {
		List<Map<String,Object>> firstMenuList=null;
		firstMenuList=menuMapper.queryFirstMenu();
		return firstMenuList;
	}

	@Override//新增菜单
	public Integer createNewMenu(Menu menu) {
		return menuMapper.createNewMenu(menu);
	}

	@Override//管理菜单（启用）
	public Integer manageMenu(Integer menuId) {
		return menuMapper.manageMenu(menuId);
	}

	@Override//禁用菜单
	public Integer stopMenu(Integer menuId) {
		return menuMapper.stopMenu(menuId);
	}


	@Override//查询主页左侧菜单（只查询可用菜单）11111
	public List<Map<String, Object>> queryLeftMenu(int roleId) {
		return menuMapper.queryLeftMenu(roleId);
	}
	
}
