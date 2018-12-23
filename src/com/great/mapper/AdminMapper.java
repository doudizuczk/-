package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Admin;


public interface AdminMapper {
	public Admin queryAdmin(Admin admin);
	
	
	public List<Map<String,Object>> queryAdminList();//czk查询管理员列表

	public List<Map<String,Object>> conditionQueryAdminList(Admin admin);//czk条件模糊查询管理员列表
	
	public List<Map<String,Object>> addQueryAdminExist(Admin admin);//新增管理员查存在
}
