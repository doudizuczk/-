package com.great.service;

import com.great.bean.Car;

public interface ICarService {
	public Car queryCarById(String carId);//根据车牌号查询车辆信息
}
