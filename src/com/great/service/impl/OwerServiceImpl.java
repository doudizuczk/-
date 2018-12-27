package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Car;
import com.great.bean.Ower;
import com.great.bean.TranSact;
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
	@Override
	public int addMessage(Ower ower) {
		// TODO Auto-generated method stub
		return owerMapper.addMessage(ower);
	}
	@Override
	public int changeMeans(Ower ower) {
		// TODO Auto-generated method stub
		return owerMapper.changeMeans(ower);
	}
	@Override
	public List<Car> carList(int owerId) {
		// TODO Auto-generated method stub
		return owerMapper.carList(owerId);
	}
	@Override
	public TranSact tranList(Car car) {
		// TODO Auto-generated method stub
		return owerMapper.tranList(car);
	}
	@Override
	public int addMoney(int owerId, int balance) {
		// TODO Auto-generated method stub
		return owerMapper.addMoney(owerId, balance);
	}
	@Override
	public Map<String, Object> searchPayNotes(Car car) {
		// TODO Auto-generated method stub
		return owerMapper.searchPayNotes(car);
	}
	@Override
	public List<Car> carTypeneone(int owerId) {
		// TODO Auto-generated method stub
		return owerMapper.carTypeneone(owerId);
	}
	@Override
	public Ower queryOwerById(int owerId) {
		// TODO Auto-generated method stub
		return owerMapper.queryOwerById(owerId);
	}
	
	public int updateCarMess(Car car) {
		// TODO Auto-generated method stub
		return owerMapper.updateCarMess(car);
	}
	@Override
	public int updatePwd(Ower ower) {
		// TODO Auto-generated method stub
		return owerMapper.updatePwd(ower);
	}


}
