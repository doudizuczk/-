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

	@Override//��ѯ���в˵�
	public  List<Map<String,Object>> queryAllMenu(){
		List<Map<String,Object>> menuList=null;
		menuList=menuMapper.queryAllMenu();
		return menuList;
	}

	@Override //��ѯһ���˵�
	public List<Map<String, Object>> queryFirstMenu() {
		
		return null;
	}
	
}
