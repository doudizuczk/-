package com.great.mapper;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.great.bean.CarLocation;


public interface CarLocationMapper {
	public List<CarLocation> queryAll(CarLocation carLocation);//��ѯ���г�λ
	public List<CarLocation> queryForbid();//��ѯ���н��ó�λ
	//public int updateCarLocation(int carLocationId);//���³�λ״̬
	public int forbidden(int carLocationId);//��λ����
	public int permission(int carLocationId);//��λ��
	
}
