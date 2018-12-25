package com.great.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Role;

public interface RoleMapper {
	public List<Map<String,Object>>queryAllRole();//czk��ѯ���н�ɫ
	public List<Map<String,Object>>queryRole(Role role);//��ѯ���н�ɫ
	public int startRole(int roleId);//���ý�ɫ;
	public int stopRole(int roleId);//���ý�ɫ
	public int createRole(@Param("roleName")String roleName);//������ɫ
}
