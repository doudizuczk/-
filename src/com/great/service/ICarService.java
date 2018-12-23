package com.great.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Car;

public interface ICarService {
	public Car queryCarById(Car car);//���ݳ��ƺŲ�ѯ������Ϣ
//	public Car queryCarById(String carId);//���ݳ��ƺŲ�ѯ������Ϣ
	public boolean createCarWithNewUser(String carId);//������ʱ�û�
	public List<Map<String,Object>> selectCarById(String carId);//�жϳ�����Ƿ���ڸó��ƺ�
	public HashMap<String,Object> selectCarForType(String carId);//������������
}
