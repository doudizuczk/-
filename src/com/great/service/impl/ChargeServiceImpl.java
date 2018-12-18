package com.great.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Rule;
import com.great.mapper.ChargeMapper;
import com.great.mapper.DockMapper;
import com.great.mapper.RuleMapper;
import com.great.mapper.TransactMapper;
import com.great.service.IChargeService;

@Service("chargeServiceImpl")
public class ChargeServiceImpl implements IChargeService {
	@Autowired
	private DockMapper dockMapper;
	@Autowired
	private TransactMapper transactMapper;
	@Autowired
	private RuleMapper ruleMapper;
	@Autowired 
	private ChargeMapper chargeMapper;
	
	@Override
	public double getParkingCost(String carId) {
		// TODO Auto-generated method stub
		String dockSTime=dockMapper.getDockSTime(carId);
		String transactEtime=transactMapper.getTransactETime(carId);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		double cost=0;
		
		try {
			Date now=new Date();
			long minutes=0;
			// ������ѯȷ���Ƿ���ͣ���������ײ͹���
			if (transactEtime == null || dockSTime.compareTo(transactEtime) >= 0) {// ͣ��ǰ������ʱ����
				// ����ͣ��ʱ��
				Date dockSDate = df.parse(dockSTime);
				minutes=(now.getTime()-dockSDate.getTime())/1000/60;
				
			} else {// ͣ��ʱ�ײ͹���
				Date transactEDate = df.parse(transactEtime);
				minutes=(now.getTime()-transactEDate.getTime())/1000/60;
			}
			//������ط���
			double hours=1.0*minutes/60;
			Rule rule=ruleMapper.getCurRule(hours);
			cost=rule.getFixCost()+(hours-rule.getCriticalHours())*rule.getOutCost();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cost;
	}

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
