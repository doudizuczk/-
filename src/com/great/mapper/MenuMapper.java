package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Menu;

public interface MenuMapper {
	public List<Map<String,Object>> queryAllMenu();//��ѯ���в˵�
	public List<Map<String,Object>> queryFirstMenu();//��ѯһ���˵�
	public Integer createNewMenu(Menu menu);//�����˵�
	public Integer manageMenu(Integer menuId);//����˵�(����)
	public Integer stopMenu(Integer menuId);//���ò˵�
}
