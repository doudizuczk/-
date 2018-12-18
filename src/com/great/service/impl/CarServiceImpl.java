package com.great.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Car;
import com.great.mapper.CarMapper;
import com.great.service.ICarService;

@Service("carServiceImpl")
public class CarServiceImpl implements ICarService {
	@Autowired
	private CarMapper carMapper;
	
	@Override
	public Car queryCarById(String carId) {
		// TODO Auto-generated method stub
		return null;
	}

}
