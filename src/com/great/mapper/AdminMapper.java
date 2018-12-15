package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Admin;


public interface AdminMapper {
	public Admin queryAdmin(Admin admin);
	
	
	public List<Map<String,Object>> queryAdminList();//czk查询管理员列表
}
