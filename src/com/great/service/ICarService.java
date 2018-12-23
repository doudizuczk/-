package com.great.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Car;

public interface ICarService {
	public Car queryCarById(Car car);//根据车牌号查询车辆信息
//	public Car queryCarById(String carId);//根据车牌号查询车辆信息
	public boolean createCarWithNewUser(String carId);//创建临时用户
	public List<Map<String,Object>> selectCarById(String carId);//判断车库表是否存在该车牌号
	public HashMap<String,Object> selectCarForType(String carId);//搜索车辆类型
}
