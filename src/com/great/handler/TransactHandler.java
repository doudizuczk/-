package com.great.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.great.bean.Pack;
import com.great.bean.TranSact;
import com.great.mapper.CarLocationMapper;
import com.great.mapper.CarMapper;
import com.great.mapper.OwerMapper;
import com.great.service.IPackService;
import com.great.service.IParmService;
import com.great.service.ITransactService;

/**
 * 套餐办理
 * @author Administrator
 */

@Controller// 此注释的含义是将该类设置成为浏览器提交的上来的类
@RequestMapping("/transact")
public class TransactHandler {
	
	@Autowired
	@Qualifier("parmServiceImpl")
	private IParmService parmService;
	@Autowired
	@Qualifier("packServiceImpl")
	private IPackService packService;
	@Autowired
	@Qualifier("transactServiceImpl")
	private ITransactService transactService;
	@Autowired
	private OwerMapper owerMapper;
	@Autowired
	private CarMapper carMapper;
	@Autowired
	private CarLocationMapper carLocationMapper;
	
	//CZK-菜单点击套餐办理页面跳转
	@RequestMapping(value = "/pack_transact.action")
	public ModelAndView JumpPackList(HttpServletRequest request ) {
		List<Map<String,Object>> typePack= parmService.IdQueryParmName(9);//套餐类型列表
		ModelAndView mav = new ModelAndView();
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("TypePack", typePack);
		mav.addObject("dates",dates);
		mav.setViewName("forward:/backstage/pack_Transact.jsp");
		return mav;
	}
	//czk-办理页套餐类型改变事件返回套餐列表
	@RequestMapping(value = "/SelectTypeChange.action")
	public@ResponseBody Map<String,Object> SelectTypeChange(HttpServletRequest request ) {
		Integer typeId = Integer.parseInt(request.getParameter("packType"));
		Pack pack = new Pack();
		pack.setPackType(typeId);
		List<Map<String,Object>> packList = packService.queryPackList(pack);
		
		
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("packList", packList);
		return dates;
	}
	//菜单点击套餐退费跳转
	@RequestMapping(value = "/pack_Refund.action")
	public ModelAndView JumpPack_Refund(HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/backstage/pack_Refund.jsp");
		return mav;
	}
	//czk-输入车牌查看返回套餐办理表数据
	@RequestMapping(value = "/CarIdSelectTransact.action")
	public@ResponseBody Map<String,Object> CarIdSelectTransact(HttpServletRequest request ) {
		int rstState = 0;
		String money = "";
		String carId = request.getParameter("carId");
		TranSact tran = new TranSact();
		tran.setTranState(1);
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactService.CidQueryTransact(tran);//根据车牌号查套餐
		Map<String, Object> dates=new HashMap<String, Object>();
		if(tranList.size()>0) {
			 money = transactService.refund(carId);
			rstState=1;
			dates.put("tran", tranList.get(0));//返回套餐办理表 的数据
		}
		dates.put("rstState", rstState);
		dates.put("money", money);//可退金额
		return dates;
	}
	
	//czk-点击退费办理
	@RequestMapping(value = "/RefunndTransact.action")
	public@ResponseBody Map<String,Object> RefunndTransact(HttpServletRequest request ) {
		String carId = request.getParameter("carId");
		String CNY = request.getParameter("money");
		Double money = Double.parseDouble(CNY);
		int refundState = transactService.refundMoney(carId,money);//车牌退费退费的方法，返回1=余额退款  2=现金退款
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactService.CidQueryTransact(tran);//根据车牌号查套餐
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("tran", tranList.get(0));//该套餐
		dates.put("money", money);//应退金额
		dates.put("refundState", refundState);//退款方式
		return dates;
	}
	//czk-点击套餐办理
	@RequestMapping(value = "/carIdPackTransact.action")
	public@ResponseBody Map<String,Object> packTransact(HttpServletRequest request ) {
		int packId = Integer.parseInt(request.getParameter("packId"));
		String carID = request.getParameter("carAccount");
		
		Map<String,Object> add = transactService.carIdTransactPack(carID, packId);
		
		return add;
	}
	//==========================================================================================================================
	//czk-2-套餐办理输入车牌查看返回套餐办理表数据
	@RequestMapping(value = "/CarIdSelectPack2.action")
	public@ResponseBody Map<String,Object> CarIdSelectPack(HttpServletRequest request ) {
		int rstState = 0;//是否有套餐
		int owerState = 0;//车牌是否绑定用户
		String money = "";
		String carId = request.getParameter("carId");
		TranSact tran = new TranSact();
		tran.setTranState(1);
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactService.CidQueryTransact(tran);//根据车牌号查套餐
		

		
		List<Map<String,Object>> typePack= parmService.IdQueryParmName(9);//套餐类型列表
		Map<String, Object> dates=new HashMap<String, Object>();
		Map<String,Object> owerMoney= owerMapper.CarQueryOwer(carId);//carID查用户余额
		if(owerMoney!=null) {
			owerState=1;
			dates.put("owerMoney", owerMoney);
		}
		
		if(tranList.size()>0) {
			 money = transactService.refund(carId);//车牌查套餐算天数算退费金额
			rstState=1;
			dates.put("tran", tranList.get(0));//返回套餐办理表 的数据
		}
		
		dates.put("TypePack", typePack);
		dates.put("rstState", rstState);
		dates.put("owerState", owerState);
		dates.put("money", money);//可退金额
		return dates;
	}
	//czk-办理页套餐类型改变事件返回套餐列表
	@RequestMapping(value = "/SelectTypeChange2.action")
	public@ResponseBody Map<String,Object> SelectTypeChange2(HttpServletRequest request ) {//pageCurr不能为空，并初始化
		Integer typeId = Integer.parseInt(request.getParameter("packType"));
		Pack pack = new Pack();
		pack.setPackType(typeId);
		pack.setPactState(1);
		List<Map<String,Object>> packList = packService.queryPackList(pack);
		List<Map<String, Object>>  carLoc =  carLocationMapper.getVipParkIdList();
		
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("packList", packList);
		dates.put("carLoc",carLoc);
		return dates;
	}
	//============================================================================================================================
	//czk-确认支付后办理套餐
	@RequestMapping(value = "/confirmPay.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public@ResponseBody Map<String,Object> confirmPay(HttpServletRequest request,int PackTranPyte,int payType
			,int adminId,int packId,String carId,@RequestParam(value = "carPark", required = true, defaultValue = "0")int carPark) {
		carPark(carId,carPark);//更改车位状态方法
		List<Map<String,Object>> y = carMapper.selectCarById(carId);
		if(y.size()==0) {
			int t = carMapper.createCarWithNewUser(carId);//新用户加入车辆表
		}
		Map<String,Object> map = null;
		if(PackTranPyte==1) {//新建套餐
			 map =transactService.addpackTran(carId, packId,payType,adminId,carPark);//payType=支付方式——1，余额  2，现金   3，第三方
		}else if(PackTranPyte==2) {//续费
			map=transactService.RenewalPackTran(carId, packId, payType,adminId);
		}else if(PackTranPyte==3) {//更改
			map=transactService.changePackTran(carId, packId, payType,adminId,carPark);
		}
		
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("map", map);
		return dates;
	}
	
	//czk-更改套餐办理。改变套餐退款判断
	@RequestMapping(value = "/payAndRefund.action")
	public@ResponseBody double payAndRefund(HttpServletRequest request ) {
		Integer packId = Integer.parseInt(request.getParameter("packId"));//套餐id
		String carId = request.getParameter("carId");//车牌
		double tranmoney =transactService.payAndRefund(carId, packId);
	
		return tranmoney;
	}
	
	public void carPark(String carId,int carPark  ) {
		if(carPark!=0) {
			//更改车位carPark状态 占用
			Map<String, Object> map = new HashMap<>();
			map.put("parkState", 2);
			map.put("parkId", carPark);
			int a = carLocationMapper.updateParkStateById(map);
		}
		
	}
	

}
