package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Log;
import com.great.bean.SearchUtil;

public interface LogMapper {
	public List<Map<String,Object>> logList(SearchUtil searchUitl);//��ȡ������־�б�
	public int addLog(Log log);//�����־
	public List<Map<String,Object>> queryLogByfactor(SearchUtil searchUtil);//��������ѯ��־
}
