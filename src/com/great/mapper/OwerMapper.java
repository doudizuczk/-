package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Car;
import com.great.bean.Ower;

public interface OwerMapper {
	public Ower owerLogin(Ower ower);//������¼
	public int addOwer(Ower ower);//����ע��
	public List<Map<String,Object>> searchOwersCar(int owerId);//��ѯ�����󶨵ĳ���
	public int addCars(Car car);//�����󶨳���
	public int escCars(String carId);//����󶨳���
	public List<Map<String,Object>> searchPack();//�����ײͱ���ײ�
	public int updateOwerBalance(Map<String,Object> map);//��Ǯ���û����
}
