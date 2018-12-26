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
	
	public int refundMoney(String a,double b);//�����˷��˷ѵķ���
	
	public Map<String,Object> carIdTransactPack(String carId,int packId);//���ư����ײ�������
	
	public  Map<String,Object> addpackTran(String carId,int packId,int payType,int adminId);//�½� --payType=֧����ʽ
	
	public  Map<String,Object> RenewalPackTran(String carId,int packId,int payType,int adminId);//����
	
	public  Map<String,Object> changePackTran(String carId,int packId,int payType,int adminId);//����
	
}
