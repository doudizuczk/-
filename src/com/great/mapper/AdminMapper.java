package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Admin;


public interface AdminMapper {
	public Admin queryAdmin(Admin admin);
	
	
	public List<Map<String,Object>> queryAdminList();//czk��ѯ����Ա�б�

	public List<Map<String,Object>> conditionQueryAdminList(Admin admin);//czk����ģ����ѯ����Ա�б�
	
	public List<Map<String,Object>> addQueryAdminExist(Admin admin);//��������Ա�����
}
