package com.great.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.great.bean.Car;
import com.great.bean.Menu;
import com.great.bean.Ower;
import com.great.service.IOwerService;

@Controller
@RequestMapping("/owerHandler")
public class OwerHandler {
	@Autowired
	@Qualifier("owerServiceImpl")
	private IOwerService owerService;
	
	@RequestMapping(value="/owerLogin.action",method = RequestMethod.POST)
	public @ResponseBody String owerLogin(HttpServletRequest request,@RequestBody Ower ower) {
		ModelAndView model=new ModelAndView();
		Ower loginOwer=owerService.owerLogin(ower);
		if(loginOwer!=null) {
			HttpSession session=request.getSession();
			session.setAttribute("loginOwer", loginOwer);
			return "1";
		}else {
			return "0";
		}
	}
	//注册时account可用性检测
	@RequestMapping(value="/checkOwerAccount.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String checkOwerAccount(String owerAccount) {
		Ower ower=new Ower();
		ower.setOwerAccount(owerAccount);
		Ower loginOwer=owerService.owerLogin(ower);
		if(loginOwer==null) {
			return "1";
		}else {	
			return "0";
		}
	}
	//车主注册
	@RequestMapping(value="/owerRegister.action",method = RequestMethod.POST)
	public @ResponseBody String owerRegister(HttpServletRequest request,@RequestBody Ower ower) {
		int result=owerService.addOwer(ower);
		if(result>0) {
			return "1";
		}else {
			return "0";
		}
	}
	//车辆查看
	@RequestMapping(value="/searchOwersCar.action")
	public ModelAndView searchOwersCar(@RequestParam(value = "owerId", required = true, defaultValue = "0") int owerId) {
		ModelAndView model = new ModelAndView();
		List<Map<String,Object>> carList=owerService.searchOwersCar(owerId);
		model.addObject("carList", carList);
		model.setViewName("forward:/frontstage/mycar.jsp");
		return model;
	}
	//车主绑定车辆
	@RequestMapping(value="/addCars.action")
	public @ResponseBody String addCars(@RequestBody Car car) {
		System.out.println("获取到的车主id="+car.getOwerId());
		int result=owerService.addCars(car);
		if(result>0) {
			return "1";
		}else {
			return "0";
		}
	}
	//车辆解绑
	@RequestMapping(value="/escCars.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String escCars(@Param("carId") String carId) {
		int result=owerService.escCars(carId);
		if(result>0) {
			return "1";
		}else {
			return "0";
		}
	}
	//查询出套餐列表
	@RequestMapping(value="/searchPack.action",method = RequestMethod.GET)
	public ModelAndView searchPack(){
		ModelAndView model=new ModelAndView();
		List<Map<String,Object>> packList=owerService.searchPack();
		model.addObject("packList", packList);
		model.setViewName("forward:/frontstage/paymoneyforcar.jsp");
		return model;
	}
	@RequestMapping(value="/payMoneyForCar.action")
	public @ResponseBody String payMoneyForCar(@RequestBody int owerId,@RequestBody String carId,@RequestBody int packId) {
		
		return "";
	}
	
}
