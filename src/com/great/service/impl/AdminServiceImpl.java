package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Admin;
import com.great.mapper.AdminMapper;
import com.great.service.IAdminService;

@Service("adminServiceImpl")
public class AdminServiceImpl implements IAdminService {
	
	@Autowired
	private AdminMapper AdminMapper;

	@Override
	public Admin queryAdmin(Admin admin) {//µÇÂ¼
		// TODO Auto-generated method stub
		Admin admins=AdminMapper.queryAdmin(admin);
		return admins;
		
	}
	
	@Override
	public List<Map<String,Object>> queryAdminList() {
		// TODO Auto-generated method stub
		List<Map<String,Object>> adminList=AdminMapper.queryAdminList();
		return adminList;
	}

}
