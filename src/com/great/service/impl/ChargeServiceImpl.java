	package com.great.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Charge;
import com.great.bean.Dock;
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
	public boolean addCharge(Charge charge) {
		// TODO Auto-generated method stub
		int count=chargeMapper.addCharge(charge);
		return count>0;
	}
	
	@Override
	public double getParkingCost(String carId) {
		// TODO Auto-generated method stub
		Dock temp=new Dock();
		temp.setCarId(carId);
		temp.setState(1);
		Dock dock=dockMapper.queryDock(temp).get(0);
		
		String dockSTime=dock.getStartTime();
		String dockETime=dock.getEndTime();
		String transactEtime=transactMapper.getTransactETime(carId);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now=new Date();
		double cost=0;
		
		try {
			Date dockSDate = df.parse(dockSTime);
			
		/**************************����Ϊ�����ɷѳ�ʱ20min���׷�ӷ���******************************/
			if (dockETime!=null) {
				Date dockEDate = df.parse(dockETime);
				Long lateTime=(long) (20*60*1000);
				if ((now.getTime()-dockEDate.getTime())>lateTime) {
					dockSDate=new Date(dockEDate.getTime()+lateTime);
				}
			}
		
		/*******************************************************************************/
			
			long minutes=0;
			// ������ѯȷ���Ƿ���ͣ���������ײ͹���
			if (transactEtime == null || dockSTime.compareTo(transactEtime) >= 0) {// ͣ��ǰ������ʱ����
				// ����ͣ��ʱ��
				minutes=(now.getTime()-dockSDate.getTime())/1000/60;
				
			}else if (dockSTime.compareTo(transactEtime) < 0&&transactEtime.compareTo(df.format(now))<0) {// ͣ��ʱ�ײ͹���
				Date transactEDate = df.parse(transactEtime);
				minutes=(now.getTime()-transactEDate.getTime())/1000/60;
				
			}else if(transactEtime.compareTo(df.format(now)) >= 0){//�ײ�δ����
				return 0;
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
