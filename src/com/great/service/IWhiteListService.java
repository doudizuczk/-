package com.great.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/*������@lian shengwei
 * �������ڣ�2018-12-13
 * ������ҵ��ӿ�1
 */
public interface IWhiteListService {
	public List<Map<String,Object>> queryAllWhiteList();//�����������б�
	public List<Map<String,Object>> turnPageWhiteList(Map<String,Object> map);//��������ҳ��ģ����ѯ
	public boolean stopWhiteList(int tranId);//����
	public boolean starWhiteList(int tranId);//����
	public boolean chechCarId(String carId);//��⳵�ƺ�
	
}
