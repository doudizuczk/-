package com.great.service;

import java.util.List;
import java.util.Map;

public interface IRoleMenuService {
	public List<Map<String,Object>> queryAll(int roleId);//��ѯ����
	public int deleteRoleMenu(int roleId);//ɾ����ɫ��Ӧ�Ĳ˵�
	public int addRoleMenu(int roleId,int menuId);//���Ȩ�޲˵�
}
