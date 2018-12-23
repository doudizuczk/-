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
		//����������
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
          //System.out.println("���������="+day);   
      } catch (ParseException e)
      {
          // TODO �Զ����� catch ��
              e.printStackTrace();
          }   
          return day;
   
	}

	@Override
	public String refund(String carId) {
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactMapper.CidQueryTransact(tran);//���ݳ��ƺŲ��ײ�
		String escDate =  (String) tranList.get(0).get("TRAN_ETIME");//��ø��ײͽ���ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		 Date now = new Date();
		 String time = sdf.format(now);//��������
		 double c =(int) this.getDaySub(time,escDate);//ʣ������
		 if(c<1) {
			 return null;
		 }
		 double packTime =Integer.parseInt(tranList.get(0).get("PACK_TIME").toString());//��ø��ײ�����(/��)
		 double packCost = Integer.parseInt(tranList.get(0).get("PACK_COST").toString());//�ײ��ܷ���
		DecimalFormat df = new DecimalFormat("#.00");
//		long a =packCost/(packTime*30);
		double ref = (packCost/(packTime*30))*c;
		String refund =df.format(ref);
		return refund;
	}

	@Override//�շѶ��½��˷�1.��ѯ�Ƿ�����ʱ������2.��ʱ�������ֽ��û�����3.�����˷ѷ�ʽ1=�ֽ�2=��� ���˷��˻����˷ѽ��
	public Map<String, Object> refundMoney(Map<String,Object> map) {
		// TODO Auto-generated method stub
		
		String cerId = (String) map.get("carId");
		int a =transactMapper.updateTransactState(cerId);//�޸�״̬
		
		Map<String, Object> refund=new HashMap<>();
		refund.put("cerId", cerId);
		return refund;
	}

}
