package com.great.service;

import java.util.List;
import java.util.Map;
/*�����ˣ�@lian shengwei
 * �������ڣ�2018-12-19
 * բ��ҵ��
 */
public interface ICarBrakeService {
	public List<Map<String,Object>> queryWhiteListCarByCarId(String carId);
	public List<Map<String,Object>> querymouthListCarByCarId(String carId);
}
