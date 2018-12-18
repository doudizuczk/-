package com.great.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.mapper.ChargeMapper;
import com.great.service.IChargeService;
@Service("chargeImpl")
public class ChargeImpl implements IChargeService {
	@Autowired 
	private ChargeMapper chargeMapper;
	//列表
	@Override
	public List<Map<String, Object>> queryAllChargeList() {
		// TODO Auto-generated method stub
		return chargeMapper.queryAllChargeList();
	}
	//翻页
	@Override
	public List<Map<String, Object>> turnPageChargeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return chargeMapper.turnPageChargeList(map);
	}
	//周统计
	@Override
	public List<Map<String, Object>> weekChart(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return chargeMapper.queryAllWeekChart(map);
	}
	//自助缴费统计
	@Override
	public List<Map<String, Object>> querySelfHelpWeekChart() {
		// TODO Auto-generated method stub
		return chargeMapper.querySelfHelpWeekChart();
	}
	//人工缴费统计
	@Override
	public List<Map<String, Object>> queryLaborWeekChart(){
		// TODO Auto-generated method stub
		return chargeMapper.queryLaborWeekChart();
	}
	@Override
	public List<Map<String, Object>> queryRechargeWeekChart() {
		// TODO Auto-generated method stub
		return chargeMapper.queryRechargeWeekChart();
	}
	//月统计
	@Override
	public List<Map<String, Object>> mouthChart(String mouth) {
		// TODO Auto-generated method stub
		return chargeMapper.queryAllMouthChart(mouth);
	}
	//自助缴费月统计
	@Override
	public List<Map<String, Object>> querySelfHelpMouthChart(String mouth) {
		// TODO Auto-generated method stub
		return chargeMapper.querySelfHelpMouthChart(mouth);
	}
	//人工缴费月统计
	@Override
	public List<Map<String, Object>> queryLaborMouthChart(String mouth) {
		// TODO Auto-generated method stub
		return chargeMapper.queryLaborMouthChart(mouth);
	}
	@Override
	public List<Map<String, Object>> queryRechargeMouthChart(String mouth) {
		// TODO Auto-generated method stub
		return chargeMapper.queryRechargeMouthChart(mouth);
	}
	//近半年
	@Override
	public List<Map<String, Object>> queryAllHalfyearChart() {
		// TODO Auto-generated method stub
		return chargeMapper.queryAllHalfyearChart();
	}
	//自助近半年
	@Override
	public List<Map<String, Object>> querySelfHelpHalfyearChart() {
		// TODO Auto-generated method stub
		return chargeMapper.querySelfHelpHalfyearChart();
	}
	//人工近半年
	@Override
	public List<Map<String, Object>> queryLaborHalfyearChart() {
		// TODO Auto-generated method stub
		return chargeMapper.queryLaborHalfyearChart();
	}
	//月缴近半年
	@Override
	public List<Map<String, Object>> queryRechargeHalfyearChart() {
		// TODO Auto-generated method stub
		return chargeMapper.queryRechargeHalfyearChart();
	}
	
	

}
