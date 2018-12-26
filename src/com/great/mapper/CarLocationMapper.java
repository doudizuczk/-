package com.great.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.great.bean.CarInfo;
import com.great.bean.CarLocation;
import com.great.bean.ParkCount;


public interface CarLocationMapper {
	public List<CarLocation> queryAll(CarLocation carLocation);//��ѯ���г�λ
	public List<CarLocation> queryForbid();//��ѯ���н��ó�λ
	public int forbidden(int carLocationId);//��λ����
	public int permission(int carLocationId);//��λ��
	public List<CarInfo> queryCarInfo(String carId);//��ѯָ����λ�ĳ�����Ϣ
	public List<Map<String, Object>> queryByArea();//�������ѯ�ܳ�λ�����ó�λ
	public Map<String, Object> statisAll();//ͳ���ܳ�λ
	public List<CarInfo> getDetil(Integer modelId);//����λ��ģ��ID���Ҹó�λ��ͣ��������Ϣ
	public int updateLink(String address,String carId);//���ƶ���λ����ͼƬ
	public List<Integer> queryAllCount();//��ѯ���г�λ��
	public List<Integer> queryFreeCount();//��ѯ���ó�λ��
	public List<Integer> queryUsedCount();//��ѯռ�õĳ�λ��
	
	public List<Map<String, Object>> getParkIdList();//��ȡ��λ
	
	public List<Map<String, Object>> getVipParkIdList();//��ȡVIP��λ

	public int updateParkStateById(Map<String, Object> park);//���ĳ�λ��״̬

}
