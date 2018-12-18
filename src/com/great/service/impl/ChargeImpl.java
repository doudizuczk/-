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
	//�б�
	@Override
	public List<Map<String, Object>> queryAllChargeList() {
		// TODO Auto-generated method stub
		return chargeMapper.queryAllChargeList();
	}
	//��ҳ
	@Override
	public List<Map<String, Object>> turnPageChargeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return chargeMapper.turnPageChargeList(map);
	}
	//��ͳ��
	@Override
	public List<Map<String, Object>> weekChart(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return chargeMapper.queryAllWeekChart(map);
	}
	//�����ɷ�ͳ��
	@Override
	public List<Map<String, Object>> querySelfHelpWeekChart() {
		// TODO Auto-generated method stub
		return chargeMapper.querySelfHelpWeekChart();
	}
	//�˹��ɷ�ͳ��
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
	//��ͳ��
	@Override
	public List<Map<String, Object>> mouthChart(String mouth) {
		// TODO Auto-generated method stub
		return chargeMapper.queryAllMouthChart(mouth);
	}
	//�����ɷ���ͳ��
	@Override
	public List<Map<String, Object>> querySelfHelpMouthChart(String mouth) {
		// TODO Auto-generated method stub
		return chargeMapper.querySelfHelpMouthChart(mouth);
	}
	//�˹��ɷ���ͳ��
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
	//������
	@Override
	public List<Map<String, Object>> queryAllHalfyearChart() {
		// TODO Auto-generated method stub
		return chargeMapper.queryAllHalfyearChart();
	}
	//����������
	@Override
	public List<Map<String, Object>> querySelfHelpHalfyearChart() {
		// TODO Auto-generated method stub
		return chargeMapper.querySelfHelpHalfyearChart();
	}
	//�˹�������
	@Override
	public List<Map<String, Object>> queryLaborHalfyearChart() {
		// TODO Auto-generated method stub
		return chargeMapper.queryLaborHalfyearChart();
	}
	//�½ɽ�����
	@Override
	public List<Map<String, Object>> queryRechargeHalfyearChart() {
		// TODO Auto-generated method stub
		return chargeMapper.queryRechargeHalfyearChart();
	}
	
	

}
