package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.mapper.CarMapper;
import com.great.service.ICarBrakeService;
/*创建人：@lian shengwei
 * 创建日期：2018-12-19
 * 闸道业务
 */
@Service("carBrakeServiceImpl")
public class CarBrakeServiceImpl implements ICarBrakeService {
     
	
	@Autowired
	private CarMapper carMapper;
	//白名单搜索
	@Override
	public List<Map<String, Object>> queryWhiteListCarByCarId(String carId) {
		// TODO Auto-generated method stub
		return carMapper.queryWhiteListCarByCarId(carId);
	}
	@Override
	public List<Map<String, Object>> querymouthListCarByCarId(String carId) {
		// TODO Auto-generated method stub
		return carMapper.querymouthListCarByCarId(carId);
	}

	
		
}
