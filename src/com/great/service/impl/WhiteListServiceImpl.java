package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.mapper.WhiteListMapper;
import com.great.service.IWhiteListService;

/*创建人：@lian shengwei
 * 创建日期：2018-12-13
 * 白名单Service实现类
 */
@Service("whiteListServiceImpl")
public class WhiteListServiceImpl implements IWhiteListService {
	@Autowired
	private WhiteListMapper whiteListMapper;
	//查询白名单列表
	@Override
	public List<Map<String, Object>> queryAllWhiteList() {
		return whiteListMapper.queryAllWhiteList();
	}
    
}
