package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.TranSact;

public interface ITransactService {
	/*
	 * 查询套餐办理实体对象 @linanping
	 * */
	public String getTransactETime(String carId);
	
	public List<Map<String,Object>> CidQueryTransact(TranSact a);//根据车牌号查套餐
	
	public  long getDaySub(String beginDateStr,String endDateStr);//根据日期算天数
	 
	public String refund(String carId);//车牌查套餐算天数算退费金额
	
	public Map<String,Object> refundMoney(Map<String,Object> map);//车牌退费退费的方法
}
