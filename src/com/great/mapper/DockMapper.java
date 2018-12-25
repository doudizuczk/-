package com.great.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Dock;

public interface DockMapper {
	public List<Dock> queryDock(Dock dock);//���ݳ��ƺŻ�ý��복��ʱ��
	public String getDockSTime(String carId);//���ݳ��ƺŻ�ý��복��ʱ��
	public int creatDockByCar(Map<String,Object> dock);//����ͣ����
	public Dock queryParkIdByCar(@Param("carId") String carId);//���ݳ��ƻ�ȡ��λ��Ϣ
	public int updateDockState(Map<String, Object> dock);//�޸�ͣ����״̬�ͳ���ʱ��
	public int updateDockETime(String carId);////�޸�ͣ�������ʱ��
}
