package com.great.mapper;

import java.util.List;

import com.great.bean.TranSact;

/*
 * �Զ��ж��½��û��Ƿ����
 * */
public interface AisTranSactMapper {
	public List<TranSact> queryAllTran();//��ѯ�ײͰ���������м�¼
	public int updateTranSactState(int tranId);//�޸Ĺ����ײ͵�״̬
}
