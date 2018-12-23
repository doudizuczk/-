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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.great.bean.Pack;
import com.great.bean.TranSact;
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
	public@ResponseBody Map<String,Object> SelectTypeChange(HttpServletRequest request ) {//pageCurr不能为空，并初始化
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
		String carId = request.getParameter("carId");
		TranSact tran = new TranSact();
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactService.CidQueryTransact(tran);//根据车牌号查套餐
		String money = transactService.refund(carId);
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("tran", tranList.get(0));
		dates.put("money", money);
		return dates;
	}
	
	//czk-点击退费办理
	@RequestMapping(value = "/RefunndTransact.action")
	public@ResponseBody Map<String,Object> RefunndTransact(HttpServletRequest request ) {
		
		String carId = request.getParameter("carId");
		String money = request.getParameter("money");
		Map<String,Object> map = new HashMap<>();
		map.put("carId", carId);
		map.put("money", money);
		Map<String,Object> refundMap = transactService.refundMoney(map);
		
		TranSact tran = new TranSact();
		
		tran.setCarId(carId);
		List<Map<String,Object>> tranList = transactService.CidQueryTransact(tran);//根据车牌号查套餐
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("tran", tranList.get(0));
		dates.put("money", money);
		return dates;
	}
	

}
