package com.great.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Car;
import com.great.bean.Dock;
import com.great.bean.Menu;
import com.great.bean.Ower;
import com.great.bean.PageInfo;
import com.great.bean.Parm;
import com.great.bean.Role;
import com.great.bean.Rule;
import com.great.bean.TranSact;
import com.great.service.ICarService;
import com.great.service.IDockService;
import com.great.service.IMenuService;
import com.great.service.IOwerService;
import com.great.service.IParmService;
import com.great.service.IRuleService;
import com.great.service.ITransactService;
import com.great.service.impl.CarLocationServiceImpl;
import com.great.service.impl.CarServiceImpl;
import com.mysql.fabric.xmlrpc.base.Data;

/*
 * 车辆处理对象 @linanping
 * */
@Controller()
@RequestMapping("/carHandler")
public class CarHandler {
	@Autowired
	@Qualifier("carServiceImpl")
	private ICarService carService;
	@Autowired
	@Qualifier("owerServiceImpl")
	private IOwerService owerService;
	@Autowired
	@Qualifier("dockServiceImpl")
	private IDockService dockService;
	@Autowired
	@Qualifier("transactServiceImpl")
	private ITransactService transactService;
	
	@RequestMapping("/carInfo.action")
	public @ResponseBody Map<String, Object> carLogin(HttpServletRequest request, String carId) {
		Map<String, Object> map=new HashMap<String, Object>();
		Car temp=new Car();
		temp.setCarId(carId);
		Car car=carService.queryCarById(temp);
		map.put("car", car);
		
		if (car!=null) {
			//查询对应车主
			Ower ower=owerService.queryOwerById(car.getOwerId());
			//插入停靠状态
			Dock dock=dockService.queryParkIdByCar(carId);
			//查询套餐使用情况
			TranSact tranSact=new TranSact();
			tranSact.setCarId(carId);
			tranSact.setTranState(1);
			List<Map<String, Object>> tranSacts=transactService.CidQueryTransact(tranSact);
			
			map.put("ower", ower);
			map.put("dock", dock);
			if (tranSacts!=null&&tranSacts.size()!=0) {
				map.put("trans", tranSacts.get(0));
			}
		}
		
		return map;
	}
	
	

}
