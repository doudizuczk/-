package com.great.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Dock;
import com.great.mapper.DockMapper;
import com.great.service.IDockService;

@Service("dockServiceImpl")
public class DockServiceImpl implements IDockService{
	@Autowired
	private DockMapper dockMapper;
	
	@Override
	public List<Dock> queryDock(Dock dock) {
		// TODO Auto-generated method stub
		return dockMapper.queryDock(dock);
	}

}
