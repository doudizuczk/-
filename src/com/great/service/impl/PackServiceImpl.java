package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Pack;
import com.great.mapper.PackMapper;
import com.great.service.IPackService;

@Service("packServiceImpl")
public class PackServiceImpl implements IPackService {
	
	@Autowired
	private PackMapper packMapper;
	

	@Override
	public List<Map<String, Object>> queryPackList(Pack a) {
		// TODO Auto-generated method stub
		return packMapper.queryPackList(a);
	}


	@Override
	public int updatePackAtt(Pack p) {
		// TODO Auto-generated method stub
		return packMapper.updatePackAtt(p);
	}


	@Override
	public int updatePackState(Pack p) {
		// TODO Auto-generated method stub
		return packMapper.updatePackState(p);
	}


	@Override
	public int addPackAtt(Pack p) {
		// TODO Auto-generated method stub
		return packMapper.addPackAtt(p);
	}


	@Override
	public List<Map<String, Object>> addQueryPackExist(Pack p) {
		// TODO Auto-generated method stub
		return packMapper.addQueryPackExist(p);
	}

}
