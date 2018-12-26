package com.great.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Car;

public interface CarMapper {
	public Car queryCarById(Car car);
	
	public List<Map<String,Object>> queryWhiteListCarByCarId(@Param("carId") String carId);//������
	public List<Map<String,Object>> querymouthListCarByCarId(@Param("carId") String carId);//�½ɷ�
	public int createCarWithNewUser(@Param("carId") String carId);//���û�
	public List<Map<String,Object>> selectCarById(@Param("carId") String carId);//�жϳ����Ƿ�����ڿ�
	public HashMap<String,Object> selectCarForType(@Param("carId") String carId);//������������

	public int changeCarType(@Param("carId")String carId);//�޸ĳ�������--��ʱ���½ɡ�vip������

	public Map<String,Object> carIdQueryCar(String carId);//���ݳ��Ʋ鳵����Ϣ
	
	public int updateCarType(String carId);//���ĳ���״̬=2�½�
	
	public int updateCarType3(String carId);//���ĳ���״̬=3vip
	

}
