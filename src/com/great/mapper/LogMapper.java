package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Log;
import com.great.bean.SearchUtil;

public interface LogMapper {
	public List<Map<String,Object>> logList(SearchUtil searchUitl);//获取所有日志列表
	public int addLog(Log log);//添加日志
	public List<Map<String,Object>> queryLogByfactor(SearchUtil searchUtil);//按条件查询日志
}
