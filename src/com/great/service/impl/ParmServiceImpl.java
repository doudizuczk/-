package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Parm;
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
	@Override
	public Parm changeParm(Integer parmId) {
		return parmMapper.changeParm(parmId);
	}
	@Override
	public List<Map<String, Object>> queryParmType() {
		return parmMapper.queryParmType();
	}
	@Override
	public Parm searchType(int parmPid) {
		return parmMapper.searchType(parmPid);
	}
	@Override
	public int savechange(Parm parm) {
		return parmMapper.savechange(parm);
	}
	@Override
	public int createParm(Parm parm) {
		return parmMapper.createParm(parm);
	}
	@Override
	public List<Parm> queryParmByPid(int parmPid) {
		// TODO Auto-generated method stub
		return parmMapper.queryParmByPid(parmPid);
	}
	
	@Override
	public List<Map<String, Object>> IdQueryParmName(int pId) {
		// TODO Auto-generated method stub
		return parmMapper.IdQueryParmName(pId);
	}

}
