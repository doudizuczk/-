package com.great.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.great.bean.Admin;

public interface IAdminService {
	public Admin queryAdmin(Admin admin);//��ѯ����Ա-��¼
	
	public List<Map<String,Object>> queryAdminList();//czk��ѯ����Ա-��ʾ�б�
}
