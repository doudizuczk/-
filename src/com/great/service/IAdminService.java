package com.great.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.great.bean.Admin;

public interface IAdminService {
	public Admin queryAdmin(Admin admin);//查询管理员-登录
	
	public List<Map<String,Object>> queryAdminList();//czk查询管理员-显示列表
}
