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

	@Override//查询所有菜单
	public  List<Map<String,Object>> queryAllMenu(){
		List<Map<String,Object>> menuList=null;
		menuList=menuMapper.queryAllMenu();
		return menuList;
	}

	@Override //查询一级菜单
	public List<Map<String, Object>> queryFirstMenu() {
		
		return null;
	}
	
}
