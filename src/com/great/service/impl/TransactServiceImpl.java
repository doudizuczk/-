package com.great.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.TranSact;
import com.great.mapper.TransactMapper;
import com.great.service.ITransactService;

@Service("transactServiceImpl")
public class TransactServiceImpl implements ITransactService {
	@Autowired
	private TransactMapper transactMapper;
	
	@Override
	public String getTransactETime(String carId) {
		// TODO Auto-generated method stub
		return transactMapper.getTransactETime(carId);
	}
	
	@Override
	public List<Map<String, Object>> CidQueryTransact(TranSact a) {
		// TODO Auto-generated method stub
		return transactMapper.CidQueryTransact(a);
	}
	
	@Override
	public long getDaySub(String beginDateStr, String endDateStr) {
		//日期算天数
      long day=0;
      java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");    
      java.util.Date beginDate;
      java.util.Date endDate;
      try
      {
    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	  Date date1 = sdf.parse(beginDateStr);
    	  Date date2 = sdf.parse(endDateStr);
          day=(date2.getTime()-date1.getTime())/(24*60*60*1000);    
          //System.out.println("相隔的天数="+day);   
      } catch (ParseException e)
      {
          // TODO 自动生成 catch 块
              e.printStackTrace();
          }   
          return day;
   
	}

	@Override
	public String refund(String carId) {
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactMapper.CidQueryTransact(tran);//根据车牌号查套餐
		String escDate =  (String) tranList.get(0).get("TRAN_ETIME");//获得该套餐结束时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		 Date now = new Date();
		 String time = sdf.format(now);//当天日期
		 double c =(int) this.getDaySub(time,escDate);//剩余天数
		 if(c<1) {
			 return null;
		 }
		 double packTime =Integer.parseInt(tranList.get(0).get("PACK_TIME").toString());//获得该套餐期限(/月)
		 double packCost = Integer.parseInt(tranList.get(0).get("PACK_COST").toString());//套餐总费用
		DecimalFormat df = new DecimalFormat("#.00");
//		long a =packCost/(packTime*30);
		double ref = (packCost/(packTime*30))*c;
		String refund =df.format(ref);
		return refund;
	}

	@Override//收费端月缴退费1.查询是否是临时车辆，2.临时车辆退现金，用户退余额。3.返回退费方式1=现金2=余额 。退费账户。退费金额
	public Map<String, Object> refundMoney(Map<String,Object> map) {
		// TODO Auto-generated method stub
		
		String cerId = (String) map.get("carId");
		int a =transactMapper.updateTransactState(cerId);//修改状态
		
		Map<String, Object> refund=new HashMap<>();
		refund.put("cerId", cerId);
		return refund;
	}

}
