package com.great.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.great.bean.CarInfo;
import com.great.bean.CarLocation;
import com.great.bean.ParkCount;


public interface CarLocationMapper {
	public List<CarLocation> queryAll(CarLocation carLocation);//查询所有车位
	public List<CarLocation> queryForbid();//查询所有禁用车位
	public int forbidden(int carLocationId);//车位禁用
	public int permission(int carLocationId);//车位启
	public List<CarInfo> queryCarInfo(String carId);//查询指定车位的车辆信息
	public List<Map<String, Object>> queryByArea();//按区域查询总车位，禁用车位
	public Map<String, Object> statisAll();//统计总车位
	public List<CarInfo> getDetil(Integer modelId);//按车位的模型ID查找该车位的停开车辆信息
	public int updateLink(String address,String carId);//给制定车位增加图片
	public List<Integer> queryAllCount();//查询所有车位数
	public List<Integer> queryFreeCount();//查询启用车位数
	public List<Integer> queryUsedCount();//查询占用的车位数
	
	public List<Map<String, Object>> getParkIdList();//获取车位
	
	public List<Map<String, Object>> getVipParkIdList();//获取VIP车位

	public int updateParkStateById(Map<String, Object> park);//更改车位表状态

}
