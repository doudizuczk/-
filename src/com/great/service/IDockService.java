package com.great.service;


import java.util.List;
import java.util.Map;

import com.great.bean.Dock;
import com.great.bean.Rule;

public interface IDockService {
	/*
	 * ��ѯͣ��ʵ����� @linanping
	 * */
	public String getDockSTime(String carId);
	public boolean creatDockByCar(Map<String,Object> dock); //����ͣ����
	public Dock queryParkIdByCar(String carId);//����carId��ȡ��λ��Ϣ
	public boolean updateDockState(Map<String,Object> dock);//�޸�ͣ����״̬
}
