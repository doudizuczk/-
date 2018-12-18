package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Log;
import com.great.bean.SearchUtil;
import com.great.mapper.LogMapper;
import com.great.service.ILogService;

@Service
public class LogServiceImpl implements ILogService {
	@Autowired
	private LogMapper logMapper;
	@Override
	public List<Map<String, Object>> logList(SearchUtil searchUtil) {
		return logMapper.logList(searchUtil);
	}
	@Override
	public int addLog(Log log) {
		return logMapper.addLog(log);
	}
	@Override
	public List<Map<String, Object>> queryLogByfactor(SearchUtil searchUtil) {
		return logMapper.queryLogByfactor(searchUtil);
	}

}
