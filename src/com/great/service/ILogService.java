package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Log;
import com.great.bean.SearchUtil;

public interface ILogService {
	public List<Map<String,Object>> logList(SearchUtil searchUtil);//��ȡ������־�б�
	public int addLog(Log log);//�����־
	public List<Map<String,Object>> queryLogByfactor(SearchUtil searchUtil);//��������ѯ��־
}
