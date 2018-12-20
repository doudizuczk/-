package com.great.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public boolean createCarWithNewUser(String carId) {
		// TODO Auto-generated method stub
		int count=carMapper.createCarWithNewUser(carId);
		return count>0;
	}
    //�жϳ����˻��Ƿ����
	@Override
	public List<Map<String, Object>> selectCarById(String carId) {
		// TODO Auto-generated method stub
		return carMapper.selectCarById(carId);
	}
    //������������
	@Override
	public HashMap<String, Object> selectCarForType(String carId) {
		// TODO Auto-generated method stub
		return carMapper.selectCarForType(carId);
	}

}
