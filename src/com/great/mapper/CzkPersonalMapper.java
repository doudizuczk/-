package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Admin;
import com.great.bean.Ower;

public interface CzkPersonalMapper {
	
	public int updateAdminAtt(Admin admin);//�޸Ĺ���Ա�����ԣ�����״̬�޸ģ�
	
	public int addAdmin(Admin admin );//���ӹ���Ա
	
	public List<Map<String,Object>> conditionQueryCarUserList(Ower ower);//ģ����ѯ�����б�

}
