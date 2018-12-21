package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Pack;

public interface IPackService {
	
	public List<Map<String,Object>> queryPackList(Pack p);//czk查询套餐列表
	
	public int updatePackState(Pack p);//修改套餐状态
	
	public int updatePackAtt(Pack p);//修改套餐属性
	
	public int addPackAtt(Pack p);//添加套餐属性
	
	public List<Map<String,Object>>addQueryPackExist(Pack p);//添加套餐查存在
}
