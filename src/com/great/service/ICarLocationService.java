package com.great.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.great.bean.CarLocation;

public interface ICarLocationService {
	public List<CarLocation> queryAll(CarLocation carLocation);//查询所有车位
	public List<CarLocation> queryForbid();//查询所有禁用车位
	//public boolean updateCarLocation(int carLocationId);//更新车位状态
	public int forbidden(int carLocationId);//车位禁用
	public int permission(int carLocationId);//车位启用
	public List<Map<String, Object>> getParkIdList();//获取车位
	public boolean updateParkStateById(Map<String, Object> park);//更改车位表状态
	
}
