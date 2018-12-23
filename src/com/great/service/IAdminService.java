package com.great.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.great.bean.Admin;

public interface IAdminService {
	public Admin queryAdmin(Admin admin);//查询管理员-登录
	
	public List<Map<String,Object>> queryAdminList();//czk查询管理员-显示列表

	public List<Map<String,Object>> conditionQueryAdminList(Admin admin);//czk条件模糊查询管理员列表
	
	public List<Map<String,Object>> addQueryAdminExist(Admin ad);//新增管理员查存在

}
