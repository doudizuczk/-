package com.great.service.impl;

import java.util.List;
import java.util.Map;

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
	public String getDockSTime(String carId) {
		// TODO Auto-generated method stub
		return dockMapper.getDockSTime(carId);
	}
    //����ͣ����(��������)
	@Override
	public boolean creatDockByCar(Map<String, Object> dock) {
		// TODO Auto-generated method stub
		int count=dockMapper.creatDockByCar(dock);
		return count>0;
	}
	//����carId��ȡ��λ��Ϣ
	@Override
	public Dock queryParkIdByCar(String carId) {
		// TODO Auto-generated method stub
		return dockMapper.queryParkIdByCar(carId);
	}
	//�޸�ͣ����״̬
	@Override
	public boolean updateDockState(Map<String, Object> dock) {
		// TODO Auto-generated method stub
		int count=dockMapper.updateDockState(dock);
		return count>0;
	}

}
