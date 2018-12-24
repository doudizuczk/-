package com.great.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.great.bean.CarInfo;
import com.great.bean.CarLocation;
import com.great.mapper.CarLocationMapper;
import com.great.service.ICarLocationService;

@Service("carLocationServiceImpl")
public class CarLocationServiceImpl implements ICarLocationService {
	
	@Autowired
	private CarLocationMapper carLocationMapper;

	@Override
	public List<CarLocation> queryAll(CarLocation carLocation) {
		// TODO Auto-generated method 
		List<CarLocation> list=carLocationMapper.queryAll(carLocation);
		return list;
	}

	@Override
	public int forbidden(int carLocationId) {
		// TODO Auto-generated method stub
		int result=carLocationMapper.forbidden(carLocationId);
		return result;
	}

	@Override
	public int permission(int carLocationId) {
		// TODO Auto-generated method stub
		int result=carLocationMapper.permission(carLocationId);
		return result;
	}

	@Override
	public List<CarLocation> queryForbid() {
		// TODO Auto-generated method stub
		List<CarLocation> list=carLocationMapper.queryForbid();
		return list;
	}

	@Override
	public List<CarInfo> queryCarInfo(String carId) {
		// TODO Auto-generated method stub
		 List<CarInfo> list=carLocationMapper.queryCarInfo(carId);
		 return list;
	}

	@Override
	public List<Map<String, Object>> queryByArea() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list=carLocationMapper.queryByArea();
		return list;
	}

	@Override
	public Map<String, Object> statisAll() {
		// TODO Auto-generated method stub
		Map<String, Object> list=carLocationMapper.statisAll();
		return list;
	}



}
