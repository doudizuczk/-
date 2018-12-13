package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Menu;

public interface IMenuService {
	public List<Map<String,Object>> queryAllMenu();//查询所有菜单
	public List<Map<String,Object>> queryFirstMenu();//查询一级菜单
}
