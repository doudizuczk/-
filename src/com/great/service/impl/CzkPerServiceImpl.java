package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Admin;
import com.great.bean.Ower;
import com.great.mapper.CzkPersonalMapper;
import com.great.service.ICzkPersonalService;

@Service("czkPerServiceImpl")
public class CzkPerServiceImpl implements ICzkPersonalService {
	
	@Autowired
	private CzkPersonalMapper CzkPersonalMapper;

	@Override
	public int updateAdminAtt(Admin admin) {
		// TODO Auto-generated method stub
		return CzkPersonalMapper.updateAdminAtt(admin);
	}

	@Override
	public int addAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return CzkPersonalMapper.addAdmin(admin);
	}

	@Override
	public List<Map<String, Object>> conditionQueryCarUserList(Ower o) {
		// TODO Auto-generated method stub
		return CzkPersonalMapper.conditionQueryCarUserList(o);
	}
	
	@Override
	public int addUpdateAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return CzkPersonalMapper.addUpdateAdmin(admin);
	}

	@Override
	public int updateUserState(Ower ower) {
		// TODO Auto-generated method stub
		return CzkPersonalMapper.updateUserState(ower);
	}

	@Override
	public int addupdateUser(Ower ower) {
		// TODO Auto-generated method stub
		return CzkPersonalMapper.addupdateUser(ower);
	}


}
