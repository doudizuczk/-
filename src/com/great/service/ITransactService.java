package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.TranSact;

public interface ITransactService {
	/*
	 * ��ѯ�ײͰ���ʵ����� @linanping
	 * */
	public String getTransactETime(String carId);
	
	public List<Map<String,Object>> CidQueryTransact(TranSact a);//���ݳ��ƺŲ��ײ�
	
	public  long getDaySub(String beginDateStr,String endDateStr);//��������������
	 
	public String refund(String carId);//���Ʋ��ײ����������˷ѽ��
	
	public Map<String,Object> refundMoney(Map<String,Object> map);//�����˷��˷ѵķ���
}
