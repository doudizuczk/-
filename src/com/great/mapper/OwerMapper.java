package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Car;
import com.great.bean.Ower;

public interface OwerMapper {
	public Ower owerLogin(Ower ower);//车主登录
	public int addOwer(Ower ower);//车主注册
	public List<Map<String,Object>> searchOwersCar(int owerId);//查询车主绑定的车辆
	public int addCars(Car car);//新增绑定车辆
	public int escCars(String carId);//解除绑定车辆
	public List<Map<String,Object>> searchPack();//搜索套餐表的套餐
	public int updateOwerBalance(Map<String,Object> map);//退钱给用户余额
}
