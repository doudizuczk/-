package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Car;
import com.great.bean.Ower;
import com.great.mapper.OwerMapper;
import com.great.service.IOwerService;

@Service
public class OwerServiceImpl implements IOwerService {
	@Autowired
	private OwerMapper owerMapper;
	@Override
	public Ower owerLogin(Ower ower) {
		return owerMapper.owerLogin(ower);
	}
	@Override
	public int addOwer(Ower ower) {
		return owerMapper.addOwer(ower);
	}
	@Override
	public List<Map<String, Object>> searchOwersCar(int owerId) {
		return owerMapper.searchOwersCar(owerId);
	}
	@Override
	public int addCars(Car car) {
		return owerMapper.addCars(car);
	}
	@Override
	public int escCars(String carId) {
		// TODO Auto-generated method stub
		return owerMapper.escCars(carId);
	}
	@Override
	public List<Map<String, Object>> searchPack() {
		// TODO Auto-generated method stub
		return owerMapper.searchPack();
	}


}
