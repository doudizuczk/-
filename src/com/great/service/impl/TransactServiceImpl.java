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
		//<-----------------------------------------------日期算天数-------------------------------------------------------------->
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

	@Override//<----------------------------------------------算退费金额----------------------------------------------------------------->
	public String refund(String carId) {
		TranSact tran = new TranSact();
		tran.setTranState(1);
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactMapper.CidQueryTransact(tran);//根据车牌号查套餐
		String escDate =  (String) tranList.get(0).get("TRAN_ETIME");//获得该套餐结束时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		 Date now = new Date();
		 String time = sdf.format(now);//当天日期
		 double c =(int) this.getDaySub(time,escDate);//算剩余天数的方法，输入当前时间，和套餐结束时间
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
	@Transactional
	@Override//收费端<-----------------------------------------月缴退费--------------------------------------------------------------------------------->
	//1.查询是否是临时车辆，2.临时车辆退现金，用户退余额。3.返回退费方式1=现金2=余额 。退费账户。退费金额
	public int refundMoney(String carId,double money,int adminId) {//车牌.退款金额
		// TODO Auto-generated method stub
		//解除车位
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		tran.setTranState(1);
		List<Map<String,Object>> ranList =transactMapper.CidQueryTransact(tran);
		int parkId = Integer.parseInt(ranList.get(0).get("PARK_ID").toString());//车位id
		if(parkId!=0) {
			//解除车位占用
			Map<String, Object> map = new HashMap<>();
			map.put("parkState", 1);
			map.put("parkId", parkId);
			int a = carLocationMapper.updateParkStateById(map);
		}
		//--------------
		int v = 0;
		Map<String,Object> carMap =carMapper.carIdQueryCar(carId);//查车辆信息
		if(carMap.containsKey("OWER_ID")==true) {
			//判断是否有用户ID,有则退款到余额
			int owerId = Integer.parseInt(carMap.get("OWER_ID").toString());//用户id
			Map<String,Object> map = new HashMap<>();
			map.put("owerId", owerId);//int
			map.put("money", money);//double
			int a =owerMapper.updateOwerBalance(map);
			v=a;//余额退款
		}else {
			v=2;//现金退款
		}
		Charge cha=new Charge();
		//插入收费记录id
		int seq=chargeMapper.getChargeSeq();
		cha.setAdminId(adminId);
		cha.setCarId(carId);
		cha.setCost(money);
		cha.setType(4);
		cha.setIsCash(2);
		cha.setChargeId(seq);
		chargeMapper.addCharge(cha);//插入收费表
		
		int a =transactMapper.updateTransactState(carId);//修改状态
		
		return v;
	}

	@Transactional
	@Override//<-------------------------------------------套餐办理主方法-------------------------------------------------------------------->
	public Map<String, Object> carIdTransactPack(String carId, int packId) {
		// TODO Auto-generated method stub
		//1.查车牌套餐是否有正在启用的 ，2.有则与要办理的套餐对比，相同就续费，不相同先退费再办理，3.没有则办理
		int addState  = 0;//办理类型
		String maney = "";//退款金额
		String updateTime ="";//续费---续结束时间
		int refundState = 0;//车牌退费退费的方式，  返回1=余额退款  2=现金退款
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		tran.setTranState(1);
		List<Map<String,Object>> list = transactMapper.CidQueryTransact(tran);//--查办理状态为1=启用，和车牌=carid 的数据0
		Integer tranState=0;
		int packId2 = 0;
		if(list.size()>0) {//如果有套餐办理记录就 查状态
			tranState =Integer.parseInt( list.get(0).get("TRAN_STATE").toString());//原套餐的状态
			packId2 = Integer.parseInt( list.get(0).get("PACK_ID").toString());//原套餐的id
		}
		if(list.size()==0 || tranState!=1) {//没有办理过套餐，或者套餐状态过期或已退。tranState=2或3
			//直接办理
			int a =addTransact(packId,carId,null);
			if(a==1) {
				addState=1;//返回1代表办理成功//*
			}
		}else {//办理过套餐，且套餐正在使用。tranState=2
			
			if(tranState==1 && packId2==packId) {//套餐启用状态，套餐ID相同--套餐续费
				//-----------------------------------------续费
				Pack pack = new Pack();
				pack.setPackId(packId);
				List<Map<String,Object>> packlist = packMapper.queryPackList(pack);//id查套餐套餐
				Integer month =Integer.parseInt( packlist.get(0).get("PACK_TIME").toString());//获得套餐月数
				String escDate =  (String) list.get(0).get("TRAN_ETIME");//获得当前套餐结束时间
				updateTime = addOneDay(escDate,month*30);//修改结束日期为此日期
				TranSact tranSact = new TranSact();
				tranSact.setCarId(carId);
				tranSact.setTranEtime(updateTime);
				int v = transactMapper.updateTransactTime(tranSact);
				if(v>0) {
					addState=2;//返回2代表续费成功
				}
				
			}else if(tranState==1 && packId2!=packId) {//套餐启用状态，套餐ID不同--退费--办理
				//---------------------------------------退费办理-------------------------------------------------》
				maney = refund(carId);
				String CNY = maney;
				Double money = Double.parseDouble(CNY);
				refundState = this.refundMoney(carId,money,1);//车牌退费退费的方法，返回1=余额退款  2=现金退款
				int a =addTransact(packId,carId,null);
				if(a==1) {
					addState=3;//返回3代表退费办理成功
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
	
	//-------------------------------------------------办理套餐，插入数据-套餐办理表--------------------------------------------------------
	public int addTransact(int packId,String carId,Integer parkId) {
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> packlist = packMapper.queryPackList(pack);//id查套餐套餐
		Integer month =Integer.parseInt( packlist.get(0).get("PACK_TIME").toString());//获得套餐月数
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式"yyyy-MM-dd"
		 Date now = new Date();
		 String time = sdf.format(now);//当天日期
		 String etime = addOneDay(time,month*30);//结束日期
		 
		 TranSact tran = new TranSact();
		 tran.setCarId(carId);
		 tran.setPackId(packId);
		 if(parkId==null) {//车位
			 parkId=0;
		 }
		 tran.setParkId(parkId);
		 tran.setTranEtime(etime);
		 
		 int a =transactMapper.addTransact(tran);
		return a;
	}
	//<------------------------------------------------输入一个日期  和天数 ，算出日期加上指定天数后的日期------------------------------------------》
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
	//======================================================新建，续费，更改===========================================================================================//
	@Transactional
	@Override//新建
	public Map<String, Object> addpackTran(String carId,int packId,int payType,int adminId,int carPark) {
		// TODO Auto-generated method stub
		//办理套餐更改车辆状态
		String prompt = "";
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> list =packMapper.queryPackList(pack);//要办理的套餐
		Integer type =Integer.parseInt( list.get(0).get("PACK_TYPE").toString());//获得套餐类型
		if(type==1) {
			carMapper.updateCarType(carId);//更改车辆状态月缴
		}
		if(type==2) {
			carMapper.updateCarType3(carId);//白名单
		}
		double packCost =Double.parseDouble( list.get(0).get("PACK_COST").toString());//获得套餐费用
		int seq=0;
		if(payType==1) {//1，余额  2，现金   3，第三方
			Map<String,Object> carMap =carMapper.carIdQueryCar(carId);//查车辆信息
			int owerId = Integer.parseInt(carMap.get("OWER_ID").toString());//用户id
			
			Map<String,Object> map = new HashMap<>();
			map.put("owerId", owerId);//int
			map.put("money", packCost);//double
			int a =owerMapper.updateOwerLessBalance(map);//扣钱
			prompt="余额付款成功，您的账户扣除"+packCost+"元";
			
			Charge cha=new Charge();
			//插入收费记录id
			seq=chargeMapper.getChargeSeq();
			cha.setAdminId(adminId);
			cha.setCarId(carId);
			cha.setCost(packCost);
			cha.setType(3);
			cha.setIsCash(2);
			cha.setChargeId(seq);
			chargeMapper.addCharge(cha);
		}else if(payType==2) {
			prompt="现金办理成功，收费"+packCost+"元";
			Charge cha=new Charge();
			//插入收费记录id
			seq=chargeMapper.getChargeSeq();
			cha.setAdminId(adminId);
			cha.setCarId(carId);
			cha.setCost(packCost);
			cha.setType(3);
			cha.setIsCash(1);
			cha.setChargeId(seq);
			chargeMapper.addCharge(cha);
		}else if(payType==3){
			prompt="第三方办理成功，线上支付"+packCost+"元";
			Charge cha=new Charge();
			//插入收费记录id
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
	@Override//续费
	public Map<String, Object> RenewalPackTran(String carId,int packId,int payType,int adminId) {
		String prompt = "";
		
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> packlist = packMapper.queryPackList(pack);//id查套餐套餐
		
		Integer type =Integer.parseInt( packlist.get(0).get("PACK_TYPE").toString());//获得套餐类型
		if(type==1) {
			carMapper.updateCarType(carId);//更改车辆状态月缴
		}
		if(type==2) {
			carMapper.updateCarType3(carId);//白名单
		}
		
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		tran.setTranState(1);
		
		List<Map<String,Object>> list = transactMapper.CidQueryTransact(tran);//--查办理状态为1=启用，和车牌=carid 的套餐办理表
		
		String updateTime ="";//续费---续结束时间
		Integer month =Integer.parseInt( packlist.get(0).get("PACK_TIME").toString());//获得套餐月数
		String escDate =  (String) list.get(0).get("TRAN_ETIME");//获得当前套餐结束时间
		updateTime = addOneDay(escDate,month*30);//修改结束日期为此日期
		TranSact tranSact = new TranSact();
		tranSact.setCarId(carId);
		tranSact.setTranEtime(updateTime);
		int v = transactMapper.updateTransactTime(tranSact);//续结束日期
		int seq=0;
		if(payType==1) {//1，余额  2，现金   3，第三方
			double packCost =Double.parseDouble( list.get(0).get("PACK_COST").toString());//获得套餐费用
			Map<String,Object> carMap =carMapper.carIdQueryCar(carId);//查车辆信息
			int owerId = Integer.parseInt(carMap.get("OWER_ID").toString());//用户id
			Map<String,Object> map = new HashMap<>();
			map.put("owerId", owerId);//int
			map.put("money", packCost);//double
			int a =owerMapper.updateOwerLessBalance(map);//扣钱
			prompt="余额续费成功，您的账户扣除"+packCost+"元，套餐截止日期延期为"+updateTime+"";
			
			Charge cha=new Charge();
			//插入收费记录id
			seq=chargeMapper.getChargeSeq();
			cha.setAdminId(adminId);
			cha.setCarId(carId);
			cha.setCost(packCost);
			cha.setType(3);
			cha.setIsCash(2);
			cha.setChargeId(seq);
			chargeMapper.addCharge(cha);//插入收费表
		}else if(payType==2) {//现金
			double packCost =Double.parseDouble( list.get(0).get("PACK_COST").toString());//获得套餐费用
			prompt="现金办理成功，收取"+packCost+"元，套餐截止日期延期为"+updateTime+"";
			Charge cha=new Charge();
			//插入收费记录id
			seq=chargeMapper.getChargeSeq();
			cha.setAdminId(adminId);
			cha.setCarId(carId);
			cha.setCost(packCost);
			cha.setType(3);
			cha.setIsCash(1);
			cha.setChargeId(seq);
			chargeMapper.addCharge(cha);//插入收费表
		}else if(payType==3) {//第三方支付
			double packCost =Double.parseDouble( list.get(0).get("PACK_COST").toString());//获得套餐费用
			prompt="第三方办理成功，收取"+packCost+"元，套餐截止日期延期为"+updateTime+"";
			Charge cha=new Charge();
			//插入收费记录id
			seq=chargeMapper.getChargeSeq();
			cha.setAdminId(adminId);
			cha.setCarId(carId);
			cha.setCost(packCost);
			cha.setType(3);
			cha.setIsCash(2);
			cha.setChargeId(seq);
			chargeMapper.addCharge(cha);//插入收费表
		}
		Map<String, Object> map = new HashMap<>();
		map.put("state", v);
		map.put("prompt", prompt);
		map.put("seq", seq);
		return map;
	}
	@Transactional
	@Override//=======================更改=============================
	public Map<String, Object> changePackTran(String carId,int packId,int payType,int adminId,int carPark) {
		// TODO Auto-generated method stub
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		tran.setTranState(1);
		List<Map<String,Object>> ranList =transactMapper.CidQueryTransact(tran);
		int parkId = Integer.parseInt(ranList.get(0).get("PARK_ID").toString());//车位id
		if(parkId!=0) {
			//解除车位占用
			Map<String, Object> map = new HashMap<>();
			map.put("parkState", 1);
			map.put("parkId", parkId);
			int a = carLocationMapper.updateParkStateById(map);
		}
		
		String prompt = "";
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> packlist = packMapper.queryPackList(pack);//id查套餐套餐
		double packCost =Double.parseDouble( packlist.get(0).get("PACK_COST").toString());//获得套餐费用
		
		Map<String,Object> carMap =carMapper.carIdQueryCar(carId);//查车辆信息
		int owerId = Integer.parseInt(carMap.get("OWER_ID").toString());//用户id
		
		Integer type =Integer.parseInt( packlist.get(0).get("PACK_TYPE").toString());//获得套餐类型
		if(type==1) {
			carMapper.updateCarType(carId);//更改车辆状态月缴
		}
		if(type==2) {
			carMapper.updateCarType3(carId);//白名单
		}
		int refundState = 0;//车牌退费退费的方式，  返回1=余额退款  2=现金退款
		String maney = "";//退款金额
		maney = refund(carId);
		String CNY = maney;
		double money = Double.parseDouble(CNY);//退款金额
		int seq=0;
		if(payType==1) {//1，余额  2，现金   3，第三方
			if(money>packCost) {//退款足够抵套餐费
				double tranmoney =money-packCost;//颓废-新套餐费用
				refundState = this.refundMoney(carId,tranmoney,adminId);//车牌退费---》》退费《《---给账户余额，修改套餐办理状态
				Charge cha=new Charge();
				//插入收费记录id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(packCost);
				cha.setType(3);
				cha.setIsCash(2);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);//插入收费表
				
				prompt="余额更改套餐办理成功！原套餐退费"+money+"元，新套餐"+packCost+"元剩余"+tranmoney+"元，已退入账户余额";
			}else {
				double tranmoney2 =packCost-money;//新套餐费用-原套餐剩余钱
				refundState = this.refundMoney(carId,0,adminId);//车牌退费---》》退费《《---给账户余额，修改套餐办理状态
				Map<String,Object> map = new HashMap<>();
				map.put("owerId", owerId);//int
				map.put("money", tranmoney2);//double
				int a =owerMapper.updateOwerLessBalance(map);//《《《扣钱《《
				
				Charge cha=new Charge();
				//插入收费记录id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(tranmoney2);
				cha.setType(4);
				cha.setIsCash(2);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);//插入收费表
				
				prompt="余额更改套餐办理成功！原套餐退费"+money+"元，新套餐"+packCost+"元，账户余额扣除"+tranmoney2+"元。";
			}
		}else if(payType==2) {//现金支付
			if(money>packCost) {//退款足够抵套餐费
				double tranmoney =money-packCost;//颓废-新套餐费用
				refundState = this.refundMoney(carId,tranmoney,adminId);//车牌退费退费给账户余额，修改套餐办理状态
				Charge cha=new Charge();
				//插入收费记录id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(tranmoney);
				cha.setType(3);
				cha.setIsCash(1);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);//插入收费表
				prompt="现金更改套餐办理成功！原套餐退费"+money+"元，新套餐"+packCost+"元剩余"+tranmoney+"元，现金退款";
			}else {
				double tranmoney2 =packCost-money;//新套餐费用-原套餐剩余钱
				refundState = this.refundMoney(carId,0,adminId);//车牌退费退费给账户余额，修改套餐办理状态
				Map<String,Object> map = new HashMap<>();
				map.put("owerId", owerId);//int
				map.put("money", tranmoney2);//double
				int a =owerMapper.updateOwerLessBalance(map);//扣钱
				
				Charge cha=new Charge();
				//插入收费记录id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(tranmoney2);
				cha.setType(3);
				cha.setIsCash(1);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);
				prompt="现金更改套餐办理成功！原套餐退费"+money+"元，新套餐"+packCost+"元，现金扣除"+tranmoney2+"元。";
			}
		}else if(payType==3) {//第三方支付
			if(money>packCost) {//退款足够抵套餐费
				double tranmoney =money-packCost;//颓废-新套餐费用
				refundState = this.refundMoney(carId,tranmoney,adminId);//车牌退费退费给账户余额，修改套餐办理状态
				Charge cha=new Charge();
				//插入收费记录id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(tranmoney);
				cha.setType(3);
				cha.setIsCash(2);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);//插入收费表
				prompt="第三方支付，更改套餐办理成功！原套餐退费"+money+"元，新套餐"+packCost+"元剩余"+tranmoney+"元，第三方退款";
			}else {
				double tranmoney2 =packCost-money;//新套餐费用-原套餐剩余钱
				refundState = this.refundMoney(carId,0,adminId);//车牌退费退费给账户余额，修改套餐办理状态
				Map<String,Object> map = new HashMap<>();
				map.put("owerId", owerId);//int
				map.put("money", tranmoney2);//double
				int a =owerMapper.updateOwerLessBalance(map);//扣钱
				
				Charge cha=new Charge();
				//插入收费记录id
				seq=chargeMapper.getChargeSeq();
				cha.setAdminId(adminId);
				cha.setCarId(carId);
				cha.setCost(tranmoney2);
				cha.setType(3);
				cha.setIsCash(2);
				cha.setChargeId(seq);
				chargeMapper.addCharge(cha);
				prompt="第三方支付，更改套餐办理成功！原套餐退费"+money+"元，新套餐"+packCost+"元，线上扣除"+tranmoney2+"元。";
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
	@Override//退款金额
	public double payAndRefund(String carId,int packId) {
		// TODO Auto-generated method stub
		Pack pack = new Pack();
		pack.setPackId(packId);
		List<Map<String,Object>> packlist = packMapper.queryPackList(pack);//id查套餐套餐
		double packCost =Double.parseDouble( packlist.get(0).get("PACK_COST").toString());//获得套餐费用
		String maney = "";//退款金额
		maney = refund(carId);
		String CNY = maney;
		double money = Double.parseDouble(CNY);//退款金额
		double tranmoney =money-packCost;//原套餐剩余钱-减去-新套餐费用
		return tranmoney;
	}

}
