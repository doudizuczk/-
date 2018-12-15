package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.mapper.ParmMapper;
import com.great.service.IParmService;
@Service
public class ParmServiceImpl implements IParmService {
	@Autowired
	private ParmMapper parmMapper;
	@Override
	public List<Map<String, Object>> queryAllParm() {
		return parmMapper.queryAllParm();
	}

}
