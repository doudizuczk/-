package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Pack;

public interface PackMapper {
	
	public List<Map<String,Object>> queryPackList(Pack p);//czk��ѯ�ײ��б�
	
	public int updatePackState(Pack p);//�޸��ײ�״̬
	
	public int updatePackAtt(Pack p);//�޸��ײ�����
	
	public int addPackAtt(Pack p);//����ײ�����
	
	public List<Map<String,Object>>addQueryPackExist(Pack a );//����ײͲ����
	
	
}
