package com.great.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.great.bean.CarInfo;
import com.great.bean.CarLocation;


public interface CarLocationMapper {
	public List<CarLocation> queryAll(CarLocation carLocation);//查询所有车位
	public List<CarLocation> queryForbid();//查询所有禁用车位
	public int forbidden(int carLocationId);//车位禁用
	public int permission(int carLocationId);//车位启
	public List<CarInfo> queryCarInfo(String carId);//查询指定车位的车辆信息
	public List<Map<String, Object>> queryByArea();//按区域查询总车位，禁用车位
	public Map<String, Object> statisAll();//统计总车位
	public List<Map<String, Object>> getParkIdList();//获取车位
	public int updateParkStateById(Map<String, Object> park);//更改车位表状态

}
