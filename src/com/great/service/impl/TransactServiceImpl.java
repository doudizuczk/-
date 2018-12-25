package com.great.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Pack;
import com.great.bean.TranSact;
import com.great.mapper.CarMapper;
import com.great.mapper.OwerMapper;
import com.great.mapper.PackMapper;
import com.great.mapper.TransactMapper;
import com.great.service.ITransactService;

@Service("transactServiceImpl")
public class TransactServiceImpl implements ITransactService {
	@Autowired
	private TransactMapper transactMapper;
	@Autowired
	private CarMapper carMapper;
	@Autowired
	private OwerMapper owerMapper;
	@Autowired
	private PackMapper packMapper;
	
	
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
		//<-----------------------------------------------����������-------------------------------------------------------------->
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

	@Override//<----------------------------------------------���˷ѽ��----------------------------------------------------------------->
	public String refund(String carId) {
		TranSact tran = new TranSact();
		tran.setTranState(1);
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactMapper.CidQueryTransact(tran);//���ݳ��ƺŲ��ײ�
		String escDate =  (String) tranList.get(0).get("TRAN_ETIME");//��ø��ײͽ���ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		 Date now = new Date();
		 String time = sdf.format(now);//��������
		 double c =(int) this.getDaySub(time,escDate);//��ʣ�������ķ��������뵱ǰʱ�䣬���ײͽ���ʱ��
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

	@Override//�շѶ�<-----------------------------------------�½��˷�--------------------------------------------------------------------------------->
	//1.��ѯ�Ƿ�����ʱ������2.��ʱ�������ֽ��û�����3.�����˷ѷ�ʽ1=�ֽ�2=��� ���˷��˻����˷ѽ��
	public int refundMoney(String carId,double money) {//����.�˿���
		// TODO Auto-generated method stub
		int v = 0;
		Map<String,Object> carMap =carMapper.carIdQueryCar(carId);//�鳵����Ϣ
		if(carMap.containsKey("OWER_ID")==true) {
			//�ж��Ƿ����û�ID,�����˿���
			int owerId = Integer.parseInt(carMap.get("OWER_ID").toString());//�û�id
			Map<String,Object> map = new HashMap<>();
			map.put("owerId", owerId);//int
			map.put("money", money);//double
			int a =owerMapper.updateOwerBalance(map);
			v=a;//����˿�
		}else {
			v=2;//�ֽ��˿�
		}
		int a =transactMapper.updateTransactState(carId);//�޸�״̬
		
		return v;
	}

	@Override//<-------------------------------------------�ײͰ���������-------------------------------------------------------------------->
	public Map<String, Object> carIdTransactPack(String carId, int packId) {
		// TODO Auto-generated method stub
		//1.�鳵���ײ��Ƿ����������õ� ��2.������Ҫ������ײͶԱȣ���ͬ�����ѣ�����ͬ���˷��ٰ���3.û�������
		int addState  = 0;//��������
		String maney = "";//�˿���
		String updateTime ="";//����---������ʱ��
		int refundState = 0;//�����˷��˷ѵķ�ʽ��  ����1=����˿�  2=�ֽ��˿�
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		tran.setTranState(1);
		List<Map<String,Object>> list = transactMapper.CidQueryTransact(tran);//--�����״̬Ϊ1=���ã��ͳ���=carid ������0
		Integer tranState=0;
		int packId2 = 0;
		if(list.size()>0) {//������ײͰ����¼�� ��״̬
			tranState =Integer.parseInt( list.get(0).get("TRAN_STATE").toString());//ԭ�ײ͵�״̬
			packId2 = Integer.parseInt( list.get(0).get("PACK_ID").toString());//ԭ�ײ͵�id
		}
		if(list.size()==0 || tranState!=1) {//û�а�����ײͣ������ײ�״̬���ڻ����ˡ�tranState=2��3
			//ֱ�Ӱ���
			int a =addTransact(packId,carId,null);
			if(a==1) {
				addState=1;//����1�������ɹ�//*
			}
		}else {//������ײͣ����ײ�����ʹ�á�tranState=2
			
			if(tranState==1 && packId2==packId) {//�ײ�����״̬���ײ�ID��ͬ--�ײ�����
				//-----------------------------------------����
				Pack pack = new Pack();
				pack.setPackId(packId);
				List<Map<String,Object>> packlist = packMapper.queryPackList(pack);//id���ײ��ײ�
				Integer month =Integer.parseInt( packlist.get(0).get("PACK_TIME").toString());//����ײ�����
				String escDate =  (String) list.get(0).get("TRAN_ETIME");//��õ�ǰ�ײͽ���ʱ��
				updateTime = addOneDay(escDate,month*30);//�޸Ľ�������Ϊ������
				TranSact tranSact = new TranSact();
				tranSact.setCarId(carId);
				tranSact.setTranEtime(updateTime);
				int v = transactMapper.updateTransactTime(tranSact);
				if(v>0) {
					addState=2;//����2�������ѳɹ�
				}
				
			}else if(tranState==1 && packId2!=packId) {//�ײ�����״̬���ײ�ID��ͬ--�˷�--����
				//---------------------------------------�˷Ѱ���-------------------------------------------------��
				maney = refund(carId);
				String CNY = maney;
				Double money = Double.parseDouble(CNY);
				refundState = this.refundMoney(carId,money);//�����˷��˷ѵķ���������1=����˿�  2=�ֽ��˿�
				int a =addTransact(packId,carId,null);
				if(a==1) {
					addState=3;//����3�����˷Ѱ���ɹ�
				}
			}
		}
		 Map<String, Object> map = new HashMap<>();
		 map.put("addState", addState);
		 map.put("money", maney);
		 map.put("refundState", refundState);
		 map.put("eDate", updateTime);
		return map;
	}
	
	//-------------------------------------------------�����ײͣ���������-�ײͰ����--------------------------------------------------------
	public int addTransact(int packId,String carId,Integer parkId) {
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> packlist = packMapper.queryPackList(pack);//id���ײ��ײ�
		Integer month =Integer.parseInt( packlist.get(0).get("PACK_TIME").toString());//����ײ�����
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//�������ڸ�ʽ"yyyy-MM-dd"
		 Date now = new Date();
		 String time = sdf.format(now);//��������
		 String etime = addOneDay(time,month*30);//��������
		 
		 TranSact tran = new TranSact();
		 tran.setCarId(carId);
		 tran.setPackId(packId);
		 tran.setParkId(0);
		 tran.setTranEtime(etime);
		 
		 int a =transactMapper.addTransact(tran);
		return a;
	}
	//<------------------------------------------------����һ������  ������ ��������ڼ���ָ�������������------------------------------------------��
	public String addOneDay(String time,int a)
	{
	String add = null;
	try {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	java.util.Date timeNow = df.parse(time);
	Calendar begin=Calendar.getInstance();
	begin.setTime(timeNow);
	begin.add(Calendar.DAY_OF_MONTH,a);
	add = df.format(begin.getTime());
	} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
	}
	return add;
	}
	//======================================================�½������ѣ�����===========================================================================================//

	@Override
	public Map<String, Object> addpackTran(String carId,int packId,int payType) {
		// TODO Auto-generated method stub
		//ֱ�Ӱ���
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> list =packMapper.queryPackList(pack);
		Integer type =Integer.parseInt( list.get(0).get("PACK_TYPE").toString());//����ײ�����
		if(type==1) {
			carMapper.updateCarType(carId);//���ĳ���״̬�½�
		}
		if(type==2) {
			carMapper.updateCarType3(carId);//������
		}
		
		int a =addTransact(packId,carId,null);
		Map<String, Object> map = new HashMap<>();
		map.put("state", a);
		
		return map;
	}

	@Override
	public Map<String, Object> RenewalPackTran(String carId,int packId,int payType) {
		// TODO Auto-generated method stub
		
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		tran.setTranState(1);
		List<Map<String,Object>> list = transactMapper.CidQueryTransact(tran);//--�����״̬Ϊ1=���ã��ͳ���=carid ���ײͰ����
		
		String updateTime ="";//����---������ʱ��
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> packlist = packMapper.queryPackList(pack);//id���ײ��ײ�
		Integer month =Integer.parseInt( packlist.get(0).get("PACK_TIME").toString());//����ײ�����
		String escDate =  (String) list.get(0).get("TRAN_ETIME");//��õ�ǰ�ײͽ���ʱ��
		updateTime = addOneDay(escDate,month*30);//�޸Ľ�������Ϊ������
		TranSact tranSact = new TranSact();
		tranSact.setCarId(carId);
		tranSact.setTranEtime(updateTime);
		int v = transactMapper.updateTransactTime(tranSact);
		Map<String, Object> map = new HashMap<>();
		map.put("state", v);
		
		return map;
	}
	@Override
	public Map<String, Object> changePackTran(String carId,int packId,int payType) {
		// TODO Auto-generated method stub
		String maney = "";//�˿���
		int refundState = 0;//�����˷��˷ѵķ�ʽ��  ����1=����˿�  2=�ֽ��˿�
		maney = refund(carId);
		String CNY = maney;
		Double money = Double.parseDouble(CNY);
		refundState = this.refundMoney(carId,money);//�����˷��˷ѵķ���������1=����˿�  2=�ֽ��˿�
		int a =addTransact(packId,carId,null);
		Map<String, Object> map = new HashMap<>();
		map.put("state", a);
		map.put("refundState", refundState);
		return map;
	}
	
	
	
	
	
	
	
	
	

}
