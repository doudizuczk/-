package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.TranSact;

public interface TransactMapper {
	public String getTransactETime(String carId);//根据车牌号获得最近一次套餐的过期时间
	
	public List<Map<String,Object>> CidQueryTransact(TranSact a);//根据车牌号,状态查套餐
	
	public int updateTransactState(String  cid);//套餐车牌号改状态
	
	public int addTransact(TranSact a );//办理套餐插入套餐表
	
	public int updateTransactTime(TranSact a );//修改套餐办理表，结束日期。
	
	
}
