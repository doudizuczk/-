package com.great.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Car;
import com.great.bean.Dock;
import com.great.bean.Ower;
import com.great.mapper.CarMapper;
import com.great.mapper.DockMapper;
import com.great.mapper.OwerMapper;
import com.great.service.ICarService;

@Service("carServiceImpl")
public class CarServiceImpl implements ICarService {
	@Autowired
	private CarMapper carMapper;
	@Autowired
	private OwerMapper owerMapper;
	@Autowired
	private DockMapper dockMappers;
	
	@Override
	public Car queryCarById(Car car) {
		// TODO Auto-generated method stub
		return carMapper.queryCarById(car);
	}

	@Override
	public boolean createCarWithNewUser(String carId) {
		// TODO Auto-generated method stub
		int count=carMapper.createCarWithNewUser(carId);
		return count>0;
	}
    //判断车辆账户是否存在
	@Override
	public List<Map<String, Object>> selectCarById(String carId) {
		// TODO Auto-generated method stub
		return carMapper.selectCarById(carId);
	}
    //搜索车辆类型
	@Override
	public HashMap<String, Object> selectCarForType(String carId) {
		// TODO Auto-generated method stub
		return carMapper.selectCarForType(carId);
	}

	@Override
	public Map<String, Object> carIdQueryCar(String carId) {
		// TODO Auto-generated method stub
		return carMapper.carIdQueryCar(carId);
	}

}
