package com.great.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.great.bean.CarInfo;
import com.great.bean.CarLocation;
import com.great.bean.ParkCount;

public interface ICarLocationService {
	public List<CarLocation> queryAll(CarLocation carLocation);//��ѯ���г�λ
	public List<CarLocation> queryForbid();//��ѯ���н��ó�λ
	public int forbidden(int carLocationId);//��λ����
	public int permission(int carLocationId);//��λ��
	public List<CarInfo> queryCarInfo(String carId);//��ѯָ����λ�ĳ�����Ϣ
	public List<Map<String, Object>> queryByArea();//�������ѯ�ܳ�λ�����ó�λ
	public Map<String, Object> statisAll();//ͳ���ܳ�λ
	public List<CarInfo> getDetil(Integer modelId);//����λ��ģ��ID���Ҹó�λ��ͣ��������Ϣ
	public int updateLink(String address,String carId);//给制定车位增加图片
	public List<Integer> queryAllCount();//查询所有车位数
	public List<Integer> queryFreeCount();//查询启用车位数
	public List<Integer> queryUsedCount();//查询占用的车位数
	
	public List<Map<String, Object>> getParkIdList();//��ȡ��λ
	public boolean updateParkStateById(Map<String, Object> park);//���ĳ�λ��״̬

}
