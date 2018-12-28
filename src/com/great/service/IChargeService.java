package com.great.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Charge;

/*创建人@lian shengwei
 * 创建日期：20181216
 * 收支明细
 */
public interface IChargeService{
	public double getParkingCost(String carId);//临时车辆自动计算停车费用
	public int addCharge(Charge charge);//添加收费记录
	public int updateCharge(Charge charge);//修改收费表
	public Charge queryChargeById(int chargeId);
	public BigDecimal queryChargeOrder(Charge charge);//查询结算单数据
	
	public List<Map<String,Object>> queryRechargeWeekChart();//月缴充值周统计总
	public List<Map<String,Object>> queryLaborWeekChart();//人工缴费周统计
	public List<Map<String,Object>> querySelfHelpWeekChart();//自助缴费周统计
	public List<Map<String,Object>> weekChart(Map<String,Object> map);//周总统计
    public List<Map<String,Object>> queryAllChargeList();//搜索账单列表
    public List<Map<String,Object>> turnPageChargeList(Map<String,Object> map);//账单列表翻页和模糊查询
    
    public List<Map<String,Object>> mouthChart(String mouth);//月总统计
    public List<Map<String,Object>> querySelfHelpMouthChart(String mouth);//自助月统计
  	public List<Map<String,Object>> queryLaborMouthChart(String mouth);//人工月统计
  	public List<Map<String,Object>> queryRechargeMouthChart(String mouth);//月缴费月统计
  	
  	public List<Map<String,Object>> queryAllHalfyearChart();//近半年总统计
  	public List<Map<String,Object>> querySelfHelpHalfyearChart();//自助近半年统计总
	public List<Map<String,Object>> queryLaborHalfyearChart();//人工近半年统计总
	public List<Map<String,Object>> queryRechargeHalfyearChart();//月缴费近半年统计总
	
}
