package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Dock;
import com.great.mapper.DockMapper;
import com.great.service.IDockService;

@Service("dockServiceImpl")
public class DockServiceImpl implements IDockService{
	@Autowired
	private DockMapper dockMapper;
	
	@Override
	public String getDockSTime(String carId) {
		// TODO Auto-generated method stub
		return dockMapper.getDockSTime(carId);
	}
    //创建停靠表(车辆进场)
	@Override
	public boolean creatDockByCar(Map<String, Object> dock) {
		// TODO Auto-generated method stub
		int count=dockMapper.creatDockByCar(dock);
		return count>0;
	}
	//根据carId获取车位信息
	@Override
	public Dock queryParkIdByCar(String carId) {
		// TODO Auto-generated method stub
		return dockMapper.queryParkIdByCar(carId);
	}
	//修改停靠表状态
	@Override
	public boolean updateDockState(Map<String, Object> dock) {
		// TODO Auto-generated method stub
		int count=dockMapper.updateDockState(dock);
		return count>0;
	}

}
