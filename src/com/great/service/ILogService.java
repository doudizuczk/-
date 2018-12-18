package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Log;
import com.great.bean.SearchUtil;

public interface ILogService {
	public List<Map<String,Object>> logList(SearchUtil searchUtil);//获取所有日志列表
	public int addLog(Log log);//添加日志
	public List<Map<String,Object>> queryLogByfactor(SearchUtil searchUtil);//按条件查询日志
}
