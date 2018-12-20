package com.great.service;


import java.util.List;
import java.util.Map;

import com.great.bean.Dock;
import com.great.bean.Rule;

public interface IDockService {
	/*
	 * 查询停靠实体对象 @linanping
	 * */
	public String getDockSTime(String carId);
	public boolean creatDockByCar(Map<String,Object> dock); //创建停靠表
	public Dock queryParkIdByCar(String carId);//根据carId获取车位信息
	public boolean updateDockState(Map<String,Object> dock);//修改停靠表状态
}
