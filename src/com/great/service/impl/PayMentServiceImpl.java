package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.mapper.PayMentMapper;
import com.great.service.IPayMentService;
@Service
public class PayMentServiceImpl implements IPayMentService {
	@Autowired
	private PayMentMapper payMenyMapper;
	
	@Override
	public List<Map<String, Object>> queryWeekCount() {
		return payMenyMapper.queryWeekCount();
	}

	@Override
	public List<Map<String, Object>> queryWeekPayCount() {
		return payMenyMapper.queryWeekPayCount();
	}

	@Override
	public List<Map<String, Object>> queryWeekMouthPay() {
		return payMenyMapper.queryWeekMouthPay();
	}

	@Override
	public List<Map<String, Object>> queryMouthCountOne(Map map) {
		// TODO Auto-generated method stub
		return payMenyMapper.queryMouthCountOne(map);
	}

	@Override
	public List<Map<String, Object>> queryMouthCountTwo(Map map) {
		// TODO Auto-generated method stub
		return payMenyMapper.queryMouthCountTwo(map);
	}

	@Override
	public List<Map<String, Object>> queryMouthCountThree(Map map) {
		// TODO Auto-generated method stub
		return payMenyMapper.queryMouthCountThree(map);
	}

	@Override
	public List<Map<String, Object>> querySixMouthOne() {
		// TODO Auto-generated method stub
		return payMenyMapper.querySixMouthOne();
	}

	@Override
	public List<Map<String, Object>> querySixMouthTwo() {
		// TODO Auto-generated method stub
		return payMenyMapper.querySixMouthTwo();
	}

	@Override
	public List<Map<String, Object>> querySixMouthThree() {
		// TODO Auto-generated method stub
		return payMenyMapper.querySixMouthThree();
	}

}
