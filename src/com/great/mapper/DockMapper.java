package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Dock;

public interface DockMapper {
	public String getDockSTime(String carId);//���ݳ��ƺŻ�ý��복��ʱ��
	public int creatDockByCar(Map<String,Object> dock);//����ͣ����
	public Dock queryParkIdByCar(String carId);//���ݳ��ƻ�ȡ��λ��Ϣ
	public int updateDockState(Map<String, Object> dock);//�޸�ͣ����״̬
}
