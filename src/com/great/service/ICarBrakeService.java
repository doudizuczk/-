package com.great.service;

import java.util.List;
import java.util.Map;
/*创建人：@lian shengwei
 * 创建日期：2018-12-19
 * 闸道业务
 */
public interface ICarBrakeService {
	public List<Map<String,Object>> queryWhiteListCarByCarId(String carId);
	public List<Map<String,Object>> querymouthListCarByCarId(String carId);
}
