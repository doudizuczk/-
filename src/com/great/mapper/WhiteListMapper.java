package com.great.mapper;

import java.util.List;
import java.util.Map;
/*�����ˣ�@lian shengwei
 * �������ڣ�2018-12-13
 * 1
 */
public interface WhiteListMapper {
	public List<Map<String, Object>> queryAllWhiteList();//�����������б�
	public List<Map<String, Object>> turnPageWhiteList(Map<String,Object> map);//��������ҳ��ģ����ѯ
	public int stopWhiteList(int tranId);//���ð�����
	public int starWhiteList(int tranId);//���ð�����
	public int chechCarId(int carId);//�����������ƺ�
}
