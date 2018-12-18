package com.great.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.mapper.TransactMapper;
import com.great.service.ITransactService;

@Service("transactServiceImpl")
public class TransactServiceImpl implements ITransactService {
	@Autowired
	private TransactMapper transactMapper;
	
	@Override
	public String getTransactETime(String carId) {
		// TODO Auto-generated method stub
		return transactMapper.getTransactETime(carId);
	}

}
