package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Car;
import com.great.bean.Ower;

public interface IOwerService {
	public Ower owerLogin(Ower ower);//车主登录
	public int addOwer(Ower ower);//车主注册
	public List<Map<String,Object>> searchOwersCar(int owerId);//查询车主绑定的车辆
	public int addCars(Car car);//新增绑定车辆
	public int escCars(String carId);//接触绑定车辆
	public List<Map<String,Object>> searchPack();//搜索套餐表的套餐
}
