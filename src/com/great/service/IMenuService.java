package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Menu;

public interface IMenuService {
	public List<Map<String,Object>> queryAllMenu();//��ѯ���в˵�
	public List<Map<String,Object>> queryFirstMenu();//��ѯһ���˵�
}
