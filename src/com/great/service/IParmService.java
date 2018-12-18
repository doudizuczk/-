package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Parm;

public interface IParmService {
	public List<Map<String,Object>> queryAllParm();//��ѯ���в���
	public Parm changeParm(Integer parmId);//�޸Ĳ���֮ǰ������id��ѯ������Ϣ
	public List<Map<String,Object>> queryParmType();//��ѯ��������
	public List<Parm> queryParmByPid(int parmPid);//��ѯ������
	public Parm searchType(int parmPid);//��ѯ��������
	public int savechange(Parm parm);//��������޸�
	public int createParm(Parm parm);//�����˵�
}
