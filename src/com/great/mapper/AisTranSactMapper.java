package com.great.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.great.bean.TranSact;

/*
 * �Զ��ж��½��û��Ƿ����
 * */
public interface AisTranSactMapper {
	public List<TranSact> queryAllTran();//��ѯ�ײͰ���������м�¼
	//public int updateTranSactState(TranSact tran);//�޸Ĺ����ײ͵�״̬(�޸�������ر��)
	public int updateTranState(@Param("carId")String carId);//�������
	public int updatePark(int parkId);//�޸ĳ�λ״̬
}
