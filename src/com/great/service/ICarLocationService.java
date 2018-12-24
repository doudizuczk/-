package com.great.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.great.bean.CarInfo;
import com.great.bean.CarLocation;

public interface ICarLocationService {
	public List<CarLocation> queryAll(CarLocation carLocation);//��ѯ���г�λ
	public List<CarLocation> queryForbid();//��ѯ���н��ó�λ
	public int forbidden(int carLocationId);//��λ����
	public int permission(int carLocationId);//��λ��
	public List<CarInfo> queryCarInfo(String carId);//��ѯָ����λ�ĳ�����Ϣ
	public List<Map<String, Object>> queryByArea();//�������ѯ�ܳ�λ�����ó�λ
	public Map<String, Object> statisAll();//ͳ���ܳ�λ

}
