package com.great.mapper;

import java.util.List;
import java.util.Map;

/*�ɷ�����ͳ�ƽӿ� @su_yang*/
public interface PayMentMapper {
	public List<Map<String,Object>> queryWeekCount();//��ѯһ���ڵ�����
	public List<Map<String,Object>> queryWeekPayCount();//һ���ڵĳ�ֵ�û�����
	public List<Map<String,Object>> queryWeekMouthPay();//һ���������½��û�
	public List<Map<String,Object>> queryMouthCountOne(Map map);//��ѯһ�����ڵ���ʱ�û�
	public List<Map<String,Object>> queryMouthCountTwo(Map map);//��ѯһ�����ڵĳ�ֵ
	public List<Map<String,Object>> queryMouthCountThree(Map map);//��ѯһ�����ڵ������½��û�
	public List<Map<String,Object>> querySixMouthOne();//��ѯ���������ʱ�û�
	public List<Map<String,Object>> querySixMouthTwo();//��ѯ������ĳ�ֵ
	public List<Map<String,Object>> querySixMouthThree();//��ѯ������������½��û�
	
  }
