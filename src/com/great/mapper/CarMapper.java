package com.great.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Car;

public interface CarMapper {
	public Car queryCarById(Car car);
//	public Car queryCarById(String carId);
	public List<Map<String,Object>> queryWhiteListCarByCarId(@Param("carId") String carId);//白名单
	public List<Map<String,Object>> querymouthListCarByCarId(@Param("carId") String carId);//月缴费
	public int createCarWithNewUser(@Param("carId") String carId);//新用户
	public List<Map<String,Object>> selectCarById(@Param("carId") String carId);//判断车辆是否存在于库
	public HashMap<String,Object> selectCarForType(@Param("carId") String carId);//搜索车辆类型
}
