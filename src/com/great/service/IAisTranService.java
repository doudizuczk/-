package com.great.service;

import java.util.List;

import com.great.bean.TranSact;

public interface IAisTranService {
	public List<TranSact> queryAllTran();//��ѯ�ײͰ���������м�¼
	public int updateTranSactState(int tranId);//�޸Ĺ����ײ͵�״̬
}
