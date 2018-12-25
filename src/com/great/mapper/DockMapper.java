package com.great.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Dock;

public interface DockMapper {
	public List<Dock> queryDock(Dock dock);//根据车牌号获得进入车库时间
	public String getDockSTime(String carId);//根据车牌号获得进入车库时间
	public int creatDockByCar(Map<String,Object> dock);//创建停靠表
	public Dock queryParkIdByCar(@Param("carId") String carId);//根据车牌获取车位信息
	public int updateDockState(Map<String, Object> dock);//修改停靠表状态和出场时间
	public int updateDockETime(String carId);////修改停靠表出场时间
}
