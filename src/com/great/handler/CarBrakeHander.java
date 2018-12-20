package com.great.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Dock;
import com.great.service.ICarBrakeService;
import com.great.service.ICarLocationService;
import com.great.service.ICarService;
import com.great.service.IDockService;
import com.great.service.IWhiteListService;

/*创建人：@lian shengwei
 * 日期：20181219
 * 闸道端 
 * */
@Controller
@RequestMapping("carBrakeHander")
public class CarBrakeHander {
	@Autowired
	@Qualifier("carLocationServiceImpl")
	private ICarLocationService carLocationService;
	
	@Autowired
	@Qualifier("carServiceImpl")
	private ICarService carService;
	
	@Autowired
	@Qualifier("carBrakeServiceImpl")
	private ICarBrakeService carBrakeService;
	
	@Autowired
	@Qualifier("dockServiceImpl")
	private IDockService dockService;
	//车辆入场
	@RequestMapping(value ="/carGoIn.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody Map<String,Object> carGoIn(@RequestParam String carId) {
		boolean result=false;
		Map<String,Object> carInformation=new HashMap<String,Object>();
		String CARID =carId;
        /*使用toUpperCase()方法实现大写转换*/
		if(CARID!=null||CARID!="") {
			String carIds = CARID.toUpperCase();
			//判断车牌号是否为白名单以及为可用状态(搜索车辆表)
			List<Map<String,Object>> carInfo=carBrakeService.queryWhiteListCarByCarId(carIds);
			if(carInfo.size()>0) {
				int parkId=Integer.parseInt(carInfo.get(0).get("parkId").toString());//车位ID
				//插入停靠表
				Map<String,Object> dock=new HashMap<String, Object>(); 
				dock.put("carId", carIds);
				dock.put("parkId", parkId);
				boolean result1=dockService.creatDockByCar(dock);
				//转发信息
				if(result1) {
					carInformation.put("result", true);
					carInformation.put("carId", carIds);
					carInformation.put("parkId", parkId);	
					carInformation.put("carType", "白名单vip");	
				}else {
					carInformation.put("result", false);
					carInformation.put("carId", carIds);
					carInformation.put("carType", "白名单vip");
				}
				
			}else 
			{
				//判断是否有车位
				List<Map<String,Object>> getParkList=carLocationService.getParkIdList();
				if(getParkList.size()>0) {
					//有车位
					//获取车位
					int parkId2=Integer.parseInt(getParkList.get(0).get("parkId").toString());//车位ID
					//判断用户是否为月缴用户
					List<Map<String,Object>> carInfo2=carBrakeService.querymouthListCarByCarId(carIds);
					if(carInfo2.size()>0){
						Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
						dockAndPark.put("carId", carIds);
						dockAndPark.put("parkId", parkId2);
						dockAndPark.put("parkState", 2);
						boolean result1=dockService.creatDockByCar(dockAndPark);//插入停靠表
						//车位状态改为2
						boolean result2=carLocationService.updateParkStateById(dockAndPark);
						if(result1&&result2) {
							carInformation.put("result", true);
							carInformation.put("carId", carIds);
							carInformation.put("parkId", parkId2);	
							carInformation.put("carType", "月缴用户");
						}
						else {
							carInformation.put("result", false);
							carInformation.put("carId", carIds);
							carInformation.put("carType", "月缴用户");
						}
					}
					else 
					{
						//判断用户是否存在
						List<Map<String,Object>> carById=carService.selectCarById(carIds);
						if(carById.size()==0) {
							//创建临时用户
							boolean result2=carService.createCarWithNewUser(carIds);
						}
						Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
						dockAndPark.put("carId", carIds);
						dockAndPark.put("parkId", parkId2);
						dockAndPark.put("parkState", 2);
						boolean result3=dockService.creatDockByCar(dockAndPark);
						boolean result2=carLocationService.updateParkStateById(dockAndPark);
						if(result2&&result3) {
							carInformation.put("result", true);
							carInformation.put("carId", carIds);
							carInformation.put("parkId", parkId2);	
							carInformation.put("carType", "临时用户");
						}else {
							carInformation.put("result", false);
							carInformation.put("carId", carIds);
							carInformation.put("carType", "临时用户");
						}
					}
				}else
				{
					//没有车位
					carInformation.put("result", false);
					carInformation.put("carId", carIds);
					carInformation.put("parkId", "无空闲车位");	
					carInformation.put("carType", "。。。");
				}
				
			}
		
		}
		return carInformation;
	}
	//车辆出库
	@RequestMapping(value ="/carGetOut.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody Map<String,Object> carGetOut(@RequestParam String carId) {
		boolean result=false;
		Map<String,Object> carInformation=new HashMap<String,Object>();
		String CARID =carId;
		/*使用toUpperCase()方法实现大写转换*/
		if(CARID!=null||CARID!="") {
			String carIds = CARID.toUpperCase();
			//判断车辆类型
			HashMap<String,Object> car=carService.selectCarForType(carIds);
			
			//根据停靠表判断车辆所处的车位
			Dock dock=dockService.queryParkIdByCar(carIds);
			//接口查看费用
			
			Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
			dockAndPark.put("carId", carIds);
			dockAndPark.put("parkId", dock.getCarLocationId());
			dockAndPark.put("parkState", 1);
			dockAndPark.put("dockState", 2);
			
			//车位状态改为2
			boolean result2=carLocationService.updateParkStateById(dockAndPark);
			//修改停靠表状态
			boolean result3=dockService.updateDockState(dockAndPark);
			carInformation.put("result", true);
			carInformation.put("carId", carIds);
			carInformation.put("parkId",dock.getCarLocationId());
			carInformation.put("dockSTime",dock.getStartTime());	
			carInformation.put("carType",car.get("parmName"));
			
		}
		return carInformation;
	}
	
	@RequestMapping(value ="/getParkNum.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody int getParkNum(HttpServletRequest request) {		
		List<Map<String,Object>> getParkList=carLocationService.getParkIdList();
		int parkNum=getParkList.size();
		return parkNum;
	}
	
	
}
