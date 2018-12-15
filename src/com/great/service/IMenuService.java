package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Menu;

public interface IMenuService {
	public List<Map<String,Object>> queryAllMenu();//��ѯ���в˵�111111
	public List<Map<String,Object>> queryFirstMenu();//��ѯһ���˵�
	public Integer createNewMenu(Menu menu);//�����˵�
	public Integer manageMenu(Integer menuId);//����˵�(����)
	public Integer stopMenu(Integer menuId);//���ò˵�
	public List<Map<String,Object>> queryLeftMenu(int roleId);//��ѯ��ҳ���˵���ֻ��ѯ���ò˵���
}
