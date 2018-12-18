package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Admin;
import com.great.bean.Ower;

public interface ICzkPersonalService {
	//czk的接口
	public int updateAdminAtt(Admin admin);//修改管理员的属性，包括状态修改
	
	public int addAdmin(Admin admin );//增加管理员
	
	public List<Map<String,Object>> conditionQueryCarUserList(Ower o);//模糊查询车主列表

}
