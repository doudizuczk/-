package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.TranSact;

public interface TransactMapper {
	public String getTransactETime(String carId);//���ݳ��ƺŻ�����һ���ײ͵Ĺ���ʱ��
	
	public List<Map<String,Object>> CidQueryTransact(TranSact a);//���ݳ��ƺ�,״̬���ײ�
	
	public int updateTransactState(String  cid);//�ײͳ��ƺŸ�״̬
	
	public int addTransact(TranSact a );//�����ײͲ����ײͱ�
	
	public int updateTransactTime(TranSact a );//�޸��ײͰ�����������ڡ�
	
	
}
