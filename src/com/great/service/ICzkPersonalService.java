package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Admin;
import com.great.bean.Ower;

public interface ICzkPersonalService {
	//czk�Ľӿ�
	public int updateAdminAtt(Admin admin);//�޸Ĺ���Ա�����ԣ�����״̬�޸�
	
	public int addAdmin(Admin admin );//���ӹ���Ա
	
	public List<Map<String,Object>> conditionQueryCarUserList(Ower o);//ģ����ѯ�����б�

}
