package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Car;
import com.great.bean.Ower;
import com.great.bean.TranSact;

public interface OwerMapper {
	public Ower owerLogin(Ower ower);//������¼
	public int addOwer(Ower ower);//����ע��
	public List<Map<String,Object>> searchOwersCar(int owerId);//��ѯ�����󶨵ĳ���
	public int addCars(Car car);//�����󶨳���
	public int escCars(String carId);//����󶨳���
	public List<Map<String,Object>> searchPack();//�����ײͱ����ײ�

	public int addMessage(Ower ower);//ʵ����֤
	public int changeMeans(Ower ower);//�༭��������
	public List<Car> carList(int owerId);//��ѯ��¼�û��ĳ���
	public TranSact tranList(Car car);//���ݳ����鳵������ײ�

	public int updateOwerBalance(Map<String,Object> map);//��Ǯ���û����

}