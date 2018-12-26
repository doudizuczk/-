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
	
	public int refundMoney(String a,double b);//车牌退费退费的方法
	
	public Map<String,Object> carIdTransactPack(String carId,int packId);//车牌办理套餐主方法
	
	public  Map<String,Object> addpackTran(String carId,int packId,int payType,int adminId);//新建 --payType=支付方式
	
	public  Map<String,Object> RenewalPackTran(String carId,int packId,int payType,int adminId);//续费
	
	public  Map<String,Object> changePackTran(String carId,int packId,int payType,int adminId);//更改
	
}
