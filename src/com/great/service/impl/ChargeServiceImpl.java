	package com.great.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	//修改收费表
	@Override
	public int updateCharge(Charge charge) {
		// TODO Auto-generated method stub
		return chargeMapper.updateCharge(charge);
	}

	//根据收费id查询记录
	@Override
	public Charge queryChargeById(int chargeId) {
		// TODO Auto-generated method stub
		return chargeMapper.queryChargeById(chargeId);
	}
	
	//查询结算单数据
	@Override
	public BigDecimal queryChargeOrder(Charge charge) {
		// TODO Auto-generated method stub
		Map<String,Object> map=chargeMapper.queryChargeOrder(charge);
		return (BigDecimal)map.get("val");
	}
	
	//添加收费记录(返回值：收费id)
	@Transactional
	@Override
	public int addCharge(Charge charge) {
		// TODO Auto-generated method stub
		
		//插入收费记录
		int seq=chargeMapper.getChargeSeq();
		charge.setChargeId(seq);
		chargeMapper.addCharge(charge);
		
		//插入停靠表的出场时间
		dockMapper.updateDockETime(charge.getCarId());
		
		return seq;
	}
	
	//停车计费接口
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
			
		/**************************以下为自助缴费超时20min后的追加费用******************************/
			if (dockETime!=null) {
				Date dockEDate = df.parse(dockETime);
				Long lateTime=(long) (20*60*1000);
				if ((now.getTime()-dockEDate.getTime())>lateTime) {//超时
					dockSDate=new Date(dockEDate.getTime()+lateTime);
				}else {//未超时直接显示0元
					return 0;
				}
			}
		
		/*******************************************************************************/
			
			long minutes=0;
			// 办理表查询确认是否在停车过程中套餐过期
			if (transactEtime == null || dockSTime.compareTo(transactEtime) >= 0) {// 停车前就是临时车辆
				// 计算停车时长
				minutes=(now.getTime()-dockSDate.getTime())/1000/60;
				
			}else if (dockSTime.compareTo(transactEtime) < 0&&transactEtime.compareTo(df.format(now))<0) {// 停车时套餐过期
				Date transactEDate = df.parse(transactEtime);
				minutes=(now.getTime()-transactEDate.getTime())/1000/60;
				
			}else if(transactEtime.compareTo(df.format(now)) >= 0){//套餐未过期
				return 0;
			}
			//计算相关费用
			double hours=1.0*minutes/60;
			Rule rule=ruleMapper.getCurRule(hours);
			cost=rule.getFixCost()+(hours-rule.getCriticalHours())*rule.getOutCost();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BigDecimal b = new BigDecimal(cost);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

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
