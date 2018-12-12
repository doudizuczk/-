package com.great.service.impl;

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

}
