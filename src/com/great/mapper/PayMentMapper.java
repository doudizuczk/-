package com.great.mapper;

import java.util.List;
import java.util.Map;

/*缴费渠道统计接口 @su_yang*/
public interface PayMentMapper {
	public List<Map<String,Object>> queryWeekCount();//查询一周内的数据
	public List<Map<String,Object>> queryWeekPayCount();//一周内的充值用户数量
	public List<Map<String,Object>> queryWeekMouthPay();//一周内新增月缴用户
	public List<Map<String,Object>> queryMouthCountOne(Map map);//查询一个月内的临时用户
	public List<Map<String,Object>> queryMouthCountTwo(Map map);//查询一个月内的充值
	public List<Map<String,Object>> queryMouthCountThree(Map map);//查询一个月内的新增月缴用户
	public List<Map<String,Object>> querySixMouthOne();//查询近半年的临时用户
	public List<Map<String,Object>> querySixMouthTwo();//查询近半年的充值
	public List<Map<String,Object>> querySixMouthThree();//查询近半年的新增月缴用户
	
  }
