package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Role;

public interface IRoleService {
	public List<Map<String,Object>>queryAllRoleList();//czk��ѯ���н�ɫ
	public List<Map<String,Object>>queryRole(Role role);//��ѯ���н�ɫ
	public int startRole(int roleId);//���ý�ɫ;
	public int stopRole(int roleId);//���ý�ɫ
	public int createRole(String roleName);//������ɫ
}
