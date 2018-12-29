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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.great.bean.Charge;
import com.great.bean.Pack;
import com.great.bean.TranSact;
import com.great.mapper.CarLocationMapper;
import com.great.mapper.CarMapper;
import com.great.mapper.ChargeMapper;
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
	@Autowired
	private ChargeMapper chargeMapper;
	@Autowired
	private CarLocationMapper carLocationMapper;
	
	
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
	@Transactional
	@Override//�շѶ�<-----------------------------------------�½��˷�--------------------------------------------------------------------------------->
	//1.��ѯ�Ƿ�����ʱ������2.��ʱ�������ֽ��û�����3.�����˷ѷ�ʽ1=�ֽ�2=��� ���˷��˻����˷ѽ��
	public int refundMoney(String carId,double money,int adminId) {//����.�˿���
		// TODO Auto-generated method stub
		//�����λ
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		tran.setTranState(1);
		List<Map<String,Object>> ranList =transactMapper.CidQueryTransact(tran);
		int parkId = Integer.parseInt(ranList.get(0).get("PARK_ID").toString());//��λid
		if(parkId!=0) {
			//�����λռ��
			Map<String, Object> map = new HashMap<>();
			map.put("parkState", 1);
			map.put("parkId", parkId);
			int a = carLocationMapper.updateParkStateById(map);
		}
		//--------------
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
		Charge cha=new Charge();
		//�����շѼ�¼id
		int seq=chargeMapper.getChargeSeq();
		cha.setAdminId(adminId);
		cha.setCarId(carId);
		cha.setCost(money);
		cha.setType(4);
		cha.setIsCash(2);
		cha.setChargeId(seq);
		chargeMapper.addCharge(cha);//�����շѱ�
		
		int a =transactMapper.updateTransactState(carId);//�޸�״̬
		
		return v;
	}

	@Transactional
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
				refundState = this.refundMoney(carId,money,1);//�����˷��˷ѵķ���������1=����˿�  2=�ֽ��˿�
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
		 if(parkId==null) {//��λ
			 parkId=0;
		 }
		 tran.setParkId(parkId);
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
	@Transactional
	@Override//�½�
	public Map<String, Object> addpackTran(String carId,int packId,int payType,int adminId,int carPark) {
		// TODO Auto-generated method stub
		//�����ײ͸��ĳ���״̬
		String prompt = "";
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> list =packMapper.queryPackList(pack);//Ҫ������ײ�
		Integer type =Integer.parseInt( list.get(0).get("PACK_TYPE").toString());//����ײ�����
		if(type==1) {
			carMapper.updateCarType(carId);//���ĳ���״̬�½�
		}
		if(type==2) {
			carMapper.updateCarType3(carId);//������
		}
		double packCost =Double.parseDouble( list.get(0).get("PACK_COST").toString());//����ײͷ���
		int seq=0;
		if(payType==1) {//1�����  2���ֽ�   3��������
			Map<String,Object> carMap =carMapper.carIdQueryCar(carId);//�鳵����Ϣ
			int owerId = Integer.parseInt(carMap.get("OWER_ID").toString());//�û�id
			
			Map<String,Object> map = new HashMap<>();
			map.put("owerId", owerId);//int
			map.put("money", packCost);//double
			int a =owerMapper.updateOwerLessBalance(map);//��Ǯ
			prompt="����ɹ��������˻��۳�"+packCost+"Ԫ";
			
			Charge cha=new Charge();
			//�����շѼ�¼id
			seq=chargeMapper.getChargeSeq();
			cha.setAdminId(adminId);
			cha.setCarId(carId);
			cha.setCost(packCost);
			cha.setType(3);
			cha.setIsCash(2);
			cha.setChargeId(seq);
			chargeMapper.addCharge(cha);
		}else if(payType==2) {
			prompt="�ֽ����ɹ����շ�"+packCost+"Ԫ";
			Charge cha=new Charge();
			//�����շѼ�¼id
			seq=chargeMapper.getChargeSeq();
			cha.setAdminId(adminId);
			cha.setCarId(carId);
			cha.setCost(packCost);
			cha.setType(3);
			cha.setIsCash(1);
			cha.setChargeId(seq);
			chargeMapper.addCharge(cha);
		}else if(payType==3){
			prompt="����������ɹ�������֧��"+packCost+"Ԫ";
			Charge cha=new Charge();
			//�����շѼ�¼id
			seq=chargeMapper.getChargeSeq();
			cha.setAdminId(adminId);
			cha.setCarId(carId);
			cha.setCost(packCost);
			cha.setType(3);
			cha.setIsCash(2);
			cha.setChargeId(seq);
			chargeMapper.addCharge(cha);
		}
		int a=0;
		if(carPark!=0) {
			 a =addTransact(packId,carId,carPark);
		}else {
			a =addTransact(packId,carId,null);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("state", a);
		map.put("prompt", prompt);
		map.put("seq", seq);
		return map;
	}
	@Transactional
	@Override//����
	public Map<String, Object> RenewalPackTran(String carId,int packId,int payType,int adminId) {
		String prompt = "";
		
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> packlist = packMapper.queryPackList(pack);//id���ײ��ײ�
		
		Integer type =Integer.parseInt( packlist.get(0).get("PACK_TYPE").toString());//����ײ�����
		if(type==1) {
			carMapper.updateCarType(carId);//���ĳ���״̬�½�
		}
		if(type==2) {
			carMapper.updateCarType3(carId);//������
		}
		
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		tran.setTranState(1);
		
		List<Map<String,Object>> list = transactMapper.CidQueryTransact(tran);//--�����״̬Ϊ1=���ã��ͳ���=carid ���ײͰ����
		
		String updateTime ="";//����---������ʱ��
		Integer month =Integer.parseInt( packlist.get(0).get("PACK_TIME").toString());//����ײ�����
		String escDate =  (String) list.get(0).get("TRAN_ETIME");//��õ�ǰ�ײͽ���ʱ��
		updateTime = addOneDay(escDate,month*30);//�޸Ľ�������Ϊ������
		TranSact tranSact = new TranSact();
		tranSact.setCarId(carId);
		tranSact.setTranEtime(updateTime);
		int v = transactMapper.updateTransactTime(tranSact);//����������
		int seq=0;
		if(payType==1) {//1�����  2���ֽ�   3��������
			double packCost =Double.parseDouble( list.get(0).get("PACK_COST").toString());//����ײͷ���
			Map<String,Object> carMap =carMapper.carIdQueryCar(carId);//�鳵����Ϣ
			int owerId = Integer.parseInt(carMap.get("OWER_ID").toString());//�û�id
			Map<String,Object> map = new HashMap<>();
			map.put("owerId", owerId);//int
			map.put("money", packCost);//double
			int a =owerMapper.updateOwerLessBalance(map);//��Ǯ
			prompt="������ѳɹ��������˻��۳�"+packCost+"Ԫ���ײͽ�ֹ��������Ϊ"+updateTime+"";
			
			Charge cha=new Charge();
			//�����շѼ�¼id
			seq=chargeMapper.getChargeSeq();
			cha.setAdminId(adminId);
			cha.setCarId(carId);
			cha.setCost(packCost);
			cha.setType(3);
			cha.setIsCash(2);
			cha.setChargeId(seq);
			chargeMapper.addCharge(cha);//�����շѱ�
		}else if(payType==2) {//�ֽ�
			double packCost =Double.parseDouble( list.get(0).get("PACK_COST").toString());//����ײͷ���
			prompt="�ֽ����ɹ�����ȡ"+packCost+"Ԫ���ײͽ�ֹ��������Ϊ"+updateTime+"";
			Charge cha=new Charge();
			//�����շѼ�¼id
			seq=chargeMapper.getChargeSeq();
			cha.setAdminId(adminId);
			cha.setCarId(carId);
			cha.setCost(packCost);
			cha.setType(3);
			cha.setIsCash(1);
			cha.setChargeId(seq);
			chargeMapper.addCharge(cha);//�����շѱ�
		}else if(payType==3) {//������֧��
			double packCost =Double.parseDouble( list.get(0).get("PACK_COST").toString());//����ײͷ���
			prompt="����������ɹ�����ȡ"+packCost+"Ԫ���ײͽ�ֹ��������Ϊ"+updateTime+"";
			Charge cha=new Charge();
			//�����շѼ�¼id
			seq=chargeMapper.getChargeSeq();
			cha.setAdminId(adminId);
			cha.setCarId(carId);
			cha.setCost(packCost);
			cha.setType(3);
			cha.setIsCash(2);
			cha.setChargeId(seq);
			chargeMapper.addCharge(cha);//�����շѱ�
		}
		Map<String, Object> map = new HashMap<>();
		map.put("state", v);
		map.put("prompt", prompt);
		map.put("seq", seq);
		return map;
	}
	@Transactional
	@Override//=======================����=============================
	public Map<String, Object> changePackTran(String carId,int packId,int payType,int adminId,int carPark) {
		// TODO Auto-generated method stub
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		tran.setTranState(1);
		List<Map<String,Object>> ranList =transactMapper.CidQueryTransact(tran);
		int parkId = Integer.parseInt(ranList.get(0).get("PARK_ID").toString());//��λid
		if(parkId!=0) {
			//�����λռ��
			Map<String, Object> map = new HashMap<>();
			map.put("parkState", 1);
			map.put("parkId", parkId);
			int a = carLocationMapper.updateParkStateById(map);
		}
		
		String prompt = "";
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> packlist = packMapper.queryPackList(pack);//id���ײ��ײ�
		double packCost =Double.parseDouble( packlist.get(0).get("PACK_COST").toString());//����ײͷ���
		
		Map<String,Object> carMap =carMapper.carIdQueryCar(carId);//�鳵����Ϣ
		int owerId = Integer.parseInt(carMap.get("OWER_ID").toString());//�û�id
		
		Integer type =Integer.parseInt( packlist.get(0).get("PACK_TYPE").toString());//����ײ�����
		if(type==1) {
			carMapper.updateCarType(carId);//���ĳ���״̬�½�
		}
		if(type==2) {
			carMapper.updateCarType3(carId);//������
		}
		int refundState = 0;//�����˷��˷ѵķ�ʽ��  ����1=����˿�  2=�ֽ��˿�
		String maney = "";//�˿���
		maney = refund(carId);
		String CNY = maney;
		double money = Double.parseDouble(CNY);//�˿���
		int seq=0;
		if(payType==1) {//1�����  2���ֽ�   3��������
			if(money>packCost) {//�˿��㹻���ײͷ�
				double tranmoney =money-packCost;//�Ƿ�-���ײͷ���
				refundState = this.refundMoney(carId,tranmoney,adminId);//�����˷�---�����˷ѡ���---���˻����޸��ײͰ���״̬
				Charge cha=new Charge();
				//�����շѼ�¼id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(packCost);
				cha.setType(3);
				cha.setIsCash(2);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);//�����շѱ�
				
				prompt="�������ײͰ���ɹ���ԭ�ײ��˷�"+money+"Ԫ�����ײ�"+packCost+"Ԫʣ��"+tranmoney+"Ԫ���������˻����";
			}else {
				double tranmoney2 =packCost-money;//���ײͷ���-ԭ�ײ�ʣ��Ǯ
				refundState = this.refundMoney(carId,0,adminId);//�����˷�---�����˷ѡ���---���˻����޸��ײͰ���״̬
				Map<String,Object> map = new HashMap<>();
				map.put("owerId", owerId);//int
				map.put("money", tranmoney2);//double
				int a =owerMapper.updateOwerLessBalance(map);//��������Ǯ����
				
				Charge cha=new Charge();
				//�����շѼ�¼id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(tranmoney2);
				cha.setType(4);
				cha.setIsCash(2);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);//�����շѱ�
				
				prompt="�������ײͰ���ɹ���ԭ�ײ��˷�"+money+"Ԫ�����ײ�"+packCost+"Ԫ���˻����۳�"+tranmoney2+"Ԫ��";
			}
		}else if(payType==2) {//�ֽ�֧��
			if(money>packCost) {//�˿��㹻���ײͷ�
				double tranmoney =money-packCost;//�Ƿ�-���ײͷ���
				refundState = this.refundMoney(carId,tranmoney,adminId);//�����˷��˷Ѹ��˻����޸��ײͰ���״̬
				Charge cha=new Charge();
				//�����շѼ�¼id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(tranmoney);
				cha.setType(3);
				cha.setIsCash(1);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);//�����շѱ�
				prompt="�ֽ�����ײͰ���ɹ���ԭ�ײ��˷�"+money+"Ԫ�����ײ�"+packCost+"Ԫʣ��"+tranmoney+"Ԫ���ֽ��˿�";
			}else {
				double tranmoney2 =packCost-money;//���ײͷ���-ԭ�ײ�ʣ��Ǯ
				refundState = this.refundMoney(carId,0,adminId);//�����˷��˷Ѹ��˻����޸��ײͰ���״̬
				Map<String,Object> map = new HashMap<>();
				map.put("owerId", owerId);//int
				map.put("money", tranmoney2);//double
				int a =owerMapper.updateOwerLessBalance(map);//��Ǯ
				
				Charge cha=new Charge();
				//�����շѼ�¼id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(tranmoney2);
				cha.setType(3);
				cha.setIsCash(1);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);
				prompt="�ֽ�����ײͰ���ɹ���ԭ�ײ��˷�"+money+"Ԫ�����ײ�"+packCost+"Ԫ���ֽ�۳�"+tranmoney2+"Ԫ��";
			}
		}else if(payType==3) {//������֧��
			if(money>packCost) {//�˿��㹻���ײͷ�
				double tranmoney =money-packCost;//�Ƿ�-���ײͷ���
				refundState = this.refundMoney(carId,tranmoney,adminId);//�����˷��˷Ѹ��˻����޸��ײͰ���״̬
				Charge cha=new Charge();
				//�����շѼ�¼id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(tranmoney);
				cha.setType(3);
				cha.setIsCash(2);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);//�����շѱ�
				prompt="������֧���������ײͰ���ɹ���ԭ�ײ��˷�"+money+"Ԫ�����ײ�"+packCost+"Ԫʣ��"+tranmoney+"Ԫ���������˿�";
			}else {
				double tranmoney2 =packCost-money;//���ײͷ���-ԭ�ײ�ʣ��Ǯ
				refundState = this.refundMoney(carId,0,adminId);//�����˷��˷Ѹ��˻����޸��ײͰ���״̬
				Map<String,Object> map = new HashMap<>();
				map.put("owerId", owerId);//int
				map.put("money", tranmoney2);//double
				int a =owerMapper.updateOwerLessBalance(map);//��Ǯ
				
				Charge cha=new Charge();
				//�����շѼ�¼id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(tranmoney2);
				cha.setType(3);
				cha.setIsCash(2);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);
				prompt="������֧���������ײͰ���ɹ���ԭ�ײ��˷�"+money+"Ԫ�����ײ�"+packCost+"Ԫ�����Ͽ۳�"+tranmoney2+"Ԫ��";
			}
		}
		int a=0;
		if(carPark!=0) {
			a =addTransact(packId,carId,carPark);
		}else {
			 a =addTransact(packId,carId,null);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("state", a);
		map.put("prompt", prompt);
		map.put("seq", seq);
		return map;
	}
	
	//============================================
	@Override//�˿���
	public double payAndRefund(String carId,int packId) {
		// TODO Auto-generated method stub
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> packlist = packMapper.queryPackList(pack);//id���ײ��ײ�
		double packCost =Double.parseDouble( packlist.get(0).get("PACK_COST").toString());//����ײͷ���
		String maney = "";//�˿���
		maney = refund(carId);
		String CNY = maney;
		double money = Double.parseDouble(CNY);//�˿���
		double tranmoney =money-packCost;//ԭ�ײ�ʣ��Ǯ-��ȥ-���ײͷ���
		return tranmoney;
	}

}
