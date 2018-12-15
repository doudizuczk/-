package com.great.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.mapper.WhiteListMapper;
import com.great.service.IWhiteListService;

/*创建人@lian shengwei
 * 创建日期：2018-12-13
 * 白名单业务实现类1
 */
@Service("whiteListServiceImpl")
public class WhiteListServiceImpl implements IWhiteListService {
	@Autowired
	private WhiteListMapper whiteListMapper;
	//白名单搜索
	@Override
	public List<Map<String, Object>> queryAllWhiteList() {
		return whiteListMapper.queryAllWhiteList();
	}
	//禁用白名单
	@Override
	public boolean stopWhiteList(int tranId) {
		// TODO Auto-generated method stub
		int count=whiteListMapper.stopWhiteList(tranId);
		return count>0;
	}
	//启用白名单
	@Override
	public boolean starWhiteList(int tranId) {
		// TODO Auto-generated method stub
		int count=whiteListMapper.starWhiteList(tranId);
		return count>0;
	}
	//白名单翻页和模糊查询
	@Override
	public List<Map<String, Object>> turnPageWhiteList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return whiteListMapper.turnPageWhiteList(map);
	}
	//检测车牌号
	@Override
	public boolean chechCarId(String carId) {
		// TODO Auto-generated method stub
		return false;
	}
    
}
