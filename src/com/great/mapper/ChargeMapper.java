package com.great.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Charge;
/*创建人@lian shengwei,linanping
 * 创建日期：20181216
 * 收支明细
 */
public interface ChargeMapper {
	public int addCharge(Charge charge);//添加收费记录
	public int getChargeSeq();//生成收费id
	public int updateCharge(Charge charge);//修改收费表
	public Charge queryChargeById(int chargeId);//根据收费id查询记录
	public Map<String,Object> queryChargeOrder(Charge charge);//生成日结算
	
	public List<Map<String,Object>> queryAllChargeList();//搜索账单列表
	public List<Map<String,Object>> turnPageChargeList(Map<String,Object> map);//账单列表翻页和模糊查询
	
	public List<Map<String,Object>> queryAllHalfyearChart();//近半年统计总
	public List<Map<String,Object>> querySelfHelpHalfyearChart();//自助近半年统计
	public List<Map<String,Object>> queryLaborHalfyearChart();//人工近半年统计
	public List<Map<String,Object>> queryRechargeHalfyearChart();//月缴费近半年统计
	
	public List<Map<String,Object>> queryAllMouthChart(@Param("mouth") String mouth);//月统计总
	public List<Map<String,Object>> querySelfHelpMouthChart(@Param("mouth") String mouth);//自助月统计
	public List<Map<String,Object>> queryLaborMouthChart(@Param("mouth") String mouth);//人工月统计
	public List<Map<String,Object>> queryRechargeMouthChart(@Param("mouth") String mouth);//月缴费月统计
	
	public List<Map<String,Object>> queryAllWeekChart(Map<String,Object> map);//周统计总
	public List<Map<String,Object>> querySelfHelpWeekChart();//自助周统计总
	public List<Map<String,Object>> queryLaborWeekChart();//人工周统计总
	public List<Map<String,Object>> queryRechargeWeekChart();//月缴充值周统计总
	public int createjilu(int balance);//车主余额充值
}
