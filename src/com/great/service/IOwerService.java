package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Car;
import com.great.bean.Ower;

public interface IOwerService {
	public Ower owerLogin(Ower ower);//������¼
	public int addOwer(Ower ower);//����ע��
	public List<Map<String,Object>> searchOwersCar(int owerId);//��ѯ�����󶨵ĳ���
	public int addCars(Car car);//�����󶨳���
	public int escCars(String carId);//�Ӵ��󶨳���
	public List<Map<String,Object>> searchPack();//�����ײͱ���ײ�
}
