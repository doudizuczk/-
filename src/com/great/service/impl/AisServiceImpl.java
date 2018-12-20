package com.great.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.TranSact;
import com.great.mapper.AisTranSactMapper;
import com.great.service.IAisTranService;
@Service("aisServiceImpl")
public class AisServiceImpl implements IAisTranService{
	@Autowired
	private AisTranSactMapper aisTranMapper;
	@Override
	public List<TranSact> queryAllTran() {
		return aisTranMapper.queryAllTran();
	}

}
