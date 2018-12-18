package com.great.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.mapper.DockMapper;
import com.great.service.IDockService;

@Service("dockServiceImpl")
public class DockServiceImpl implements IDockService{
	@Autowired
	private DockMapper dockMapper;
	
	@Override
	public String getDockSTime(String carId) {
		// TODO Auto-generated method stub
		return dockMapper.getDockSTime(carId);
	}

}
