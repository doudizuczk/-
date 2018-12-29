package com.great.handler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.great.bean.Dock;
import com.great.service.ICarBrakeService;
import com.great.service.ICarLocationService;
import com.great.service.ICarService;
import com.great.service.IChargeService;
import com.great.service.IDockService;
import com.great.util.Sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

/** SSE 服务器发送事件
 */
@Controller
@RequestMapping("/sse")
public class SSEController {
	volatile public static String gCarId = null;//进场车牌号
	volatile public static String gCarTypes = null;//进场车辆类型
	volatile public static String gCanGo = null;//进场车辆是否可进入
	volatile public static String oCarId = null;//出场车牌号
	volatile public static String oCarTypes = null;//出场车辆类型
	volatile public static String oCanGo = null;//出场车辆是否可出去
	volatile public static String oCost = null;//出场费用
	volatile public static String oStarTime = null;//入场时间
	volatile public static String cCarId = null; //缴费推送车牌号
	volatile public static String cCarTypes = null; //缴费推送车辆类型
	volatile public static Double cCost =0.0; //缴费推送出场费用
	volatile public static String gOut = null; //手动放行
	volatile public static String Name = null;
	//---------------------------------------
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
	
	@Autowired
	@Qualifier("chargeServiceImpl")
	private IChargeService chargeServices;
	
	//入口显示屏推送信息
	@RequestMapping(value="/goIn.action",method=RequestMethod.GET,produces = "text/event-stream;charset=UTF-8")
	public void  push(HttpServletResponse response){
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("utf-8");
		while (true) {
	        try {
	        Thread.sleep(500);
	        PrintWriter pw=response.getWriter();
	        //进场
	        pw.write("retry: 86400000\n");
	        if(gCarId!=null&&gCarTypes!=null&&gCanGo!=null) {
//	        	pw.write("event: slide\n");
//	        	pw.write("data:" + Name + "\n\n");
	        	pw.write("data:{'gCarId':'"+gCarId+"','gCarTypes':'"+gCarTypes+"','gCanGo':'"+gCanGo+"'}\n\n");
	        }
	        //出厂
	        if(oCarId!=null&&oCarTypes!=null&&oCanGo!=null&&oCost!=null&&oStarTime!=null) {
	        	pw.write("event: slide\n");
	        	pw.write("data:{'oCarId':'"+oCarId+"','oCarTypes':'"+oCarTypes+"','oCanGo':'"+oCanGo+"','oCost':'"+oCost+"','oStarTime':'"+oStarTime+"'}\n\n");
	        }
	        if(cCarId!=null&&cCarTypes!=null&&cCost!=0.0) {
	        	pw.write("event: paymoney\n");
	        	pw.write("data:{'cCarId':'"+cCarId+"','cCarTypes':'"+cCarTypes+"','cCost':"+cCost+"}\n\n");
	        }
	        if(gOut!=null){
	        	pw.write("event: cango\n");
	        	pw.write("data:{'gOut':'"+gOut+"'}\n\n");
	        }
	        pw.flush();
	      //进场初始化
	        if(gCarId!=null&&gCarTypes!=null&&gCanGo!=null) {
	        	gCarId=null;
	        	gCarTypes=null;
	        	gCanGo=null;
	        }
	        //出厂初始化
	        if(oCarId!=null&&oCarTypes!=null&&oCanGo!=null) {
	        	oCarId=null;
	        	oCarTypes=null;
	        	oCanGo=null;
	        }
	        //缴费初始化
	        if(cCarId!=null&&cCarTypes!=null&&cCost!=null) {
	        	cCarId=null;
	        	cCarTypes=null;
	        	cCost=null;
	        }
	        //放行初始化
	        if(gOut!=null) {
	        	gOut=null;
	        }
	        if(pw.checkError()){
	            System.out.println("客户端断开连接");
	            return;
	        }
	        } catch (Exception e) {
	         e.printStackTrace();
	       }
	     }
	}
	  //入口显示动态：识别图片
	    @Transactional(rollbackFor=Exception.class)
		@RequestMapping(value ="/updatePhoto.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody Map<String,Object> updatePhoto(MultipartFile img){ 
		      System.out.println("开始上传1");
		      Map<String,Object> m=new HashMap<>();
		      Dock doc=new Dock();
		      String carId =null;
		      try {
				    JSONObject res =Sample.getCarId(img.getBytes());
				    carId = res.getJSONObject("words_result").getString("number");
//-----------------------------车辆进场star-----------------------------------------------------------					
				    boolean result=false;
					String CARID =carId;
			        /*使用toUpperCase()方法实现大写转换*/
					if(CARID!=null||CARID!="") {
						String carIds = CARID.toUpperCase();
						//判断车辆是否已经入库
						doc=dockService.queryParkIdByCar(carId);
						if(doc!=null) {
							gCarTypes="该车辆已入库！请联系管理员";
							gCarId=carId;
							gCanGo="error";
							m.put("message", "该车辆已入库！请联系管理员");
							return m;
						}
						//判断车牌号是否为白名单以及为可用状态(搜索车辆表)
						List<Map<String,Object>> carInfo=carBrakeService.queryWhiteListCarByCarId(carId);
						if(carInfo.size()>0) {
							int parkId=Integer.parseInt(carInfo.get(0).get("parkId").toString());//车位ID
							//插入停靠表
							Map<String,Object> dock=new HashMap<String, Object>(); 
							dock.put("carId", carId);
							dock.put("parkId", parkId);
							boolean result1=dockService.creatDockByCar(dock);
							//转发信息
							if(result1) {
								gCarTypes="白名单vip";
								gCarId=carId;
								gCanGo="success";
								m.put("message", "可以进入");
								return m;
							}else {
								gCarTypes="白名单vip";
								gCarId=carId;
								gCanGo="error";
								m.put("message", "系统故障");
								return m;
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
								List<Map<String,Object>> carInfo2=carBrakeService.querymouthListCarByCarId(carId);
								if(carInfo2.size()>0){
									Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
									dockAndPark.put("carId", carId);
									dockAndPark.put("parkId", parkId2);
									dockAndPark.put("parkState", 2);
									boolean result1=dockService.creatDockByCar(dockAndPark);//插入停靠表
									//车位状态改为2
									boolean result2=carLocationService.updateParkStateById(dockAndPark);
									if(result1&&result2) {
										gCarTypes="月缴用户";
										gCarId=carId;
										gCanGo="success";
										m.put("message", "可以进入");
										return m;
									}
									else {
										gCarTypes="月缴用户";
										gCarId=carId;
										gCanGo="error";
										m.put("message", "系统故障");
										return m;
									}
								}
								else 
								{
									//判断用户是否存在
									List<Map<String,Object>> carById=carService.selectCarById(carId);
									if(carById.size()==0) {
										//创建临时用户
										boolean result2=carService.createCarWithNewUser(carIds);
									}
									Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
									dockAndPark.put("carId", carId);
									dockAndPark.put("parkId", parkId2);
									dockAndPark.put("parkState", 2);
									boolean result3=dockService.creatDockByCar(dockAndPark);
									boolean result2=carLocationService.updateParkStateById(dockAndPark);
									if(result2&&result3) {
										gCarTypes="临时用户";
										gCarId=carId;
										gCanGo="success";
										m.put("message", "可以进入");
										return m;
									}else {
										gCarTypes="临时用户";
										gCarId=carId;
										gCanGo="error";
										m.put("message", "系统故障");
										return m;
									}
								}
							}else
							{
								//没有车位
								gCarTypes="没有车位";
								gCarId=carIds;
								gCanGo="error";
								m.put("message", "没有车位");
								return m;
							}
							
						}
					
					
				  }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				m.put("message", "无法识别");
				return m;
			}
		      return m;
		}
//-----------------------------车辆进场end-----------------------------------------------------------					
//-----------------------------车辆出场star----------------------------------------------------------	
	    @Transactional(rollbackFor=Exception.class)
	    @RequestMapping(value ="/updateGetOutPhoto.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody Map<String,Object> updateGetOutPhoto(MultipartFile img){
			System.out.println("开始上传2");
			Map<String,Object> m=new HashMap<>();
			Dock dock=new Dock();
		      String carId =null;
		      try {
				    JSONObject res =Sample.getCarId(img.getBytes());
				    carId = res.getJSONObject("words_result").getString("number");
//-------------------------------------车辆出场判断---------------------------------------------------------
					double cost=0;
						//判断车辆类型
						HashMap<String,Object> car=carService.selectCarForType(carId);
						//根据停靠表判断车辆所处的车位
						dock=dockService.queryParkIdByCar(carId);
						if(dock!=null) {
//							cost=1.5;
							cost=chargeServices.getParkingCost(carId);
							if(cost>0) {
								oCarTypes=""+car.get("parmName");
								oCarId=carId;
								oCanGo="请联系管理员进行缴费";
								oCost=cost+"元";
								oStarTime=dock.getStartTime();
								
								cCarId=carId;
					        	cCarTypes=""+car.get("parmName");
					        	cCost=cost;
					        	m.put("message","请联系管理员进行缴费");
					        	return m;
							}else {
								//接口查看费用
								Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
								dockAndPark.put("carId", carId);
								dockAndPark.put("parkId", dock.getCarLocationId());
								dockAndPark.put("parkState", 1);
								dockAndPark.put("dockState", 2);
								//车位状态改为2
					 	        boolean result2=carLocationService.updateParkStateById(dockAndPark);
								//修改停靠表状态
						        boolean result3=dockService.updateDockState(dockAndPark);
						        if(result2&&result3) {
						        	oCarTypes=""+car.get("parmName");
						        	oCarId=carId;
						        	oCanGo="success";
						        	oCost="0元";
						        	oStarTime=dock.getStartTime();
						        	m.put("message","可以出库");
						        }else {
						        	oCarTypes=""+car.get("parmName");
						        	oCarId=carId;
						        	oCanGo="系统故障！请联系管理员";
						        	oCost="0元";
						        	oStarTime=dock.getStartTime();
						        	m.put("message","系统故障！请联系管理员");
						        	return m;
						        }
							}
					
						}else {
							oCarTypes=""+car.get("parmName");
							oCarId=carId;
							oCanGo="该车辆未入库！请联系管理员";
							oCost=cost+"元";
							oStarTime="。。。。。";
							m.put("message","该车辆未入库！请联系管理员");
							return m;
						}
						
//----------------------------------------------------------------------------------------------
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				oCarTypes="未识别";
				oCarId="未识别";
				oCanGo="请联系管理员";
				oCost="0元";
				oStarTime="。。。。。";
				m.put("message","无法识别");
				return m;
			}
		      
		      return m;
		}
//-----------------------------车辆出场end-----------------------------------------------------------	
//手动进场------------------------------------------------------------------------------------
	    @Transactional(rollbackFor=Exception.class)
	    @RequestMapping(value ="/goInCar.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody Map<String,Object> goInCar(@RequestParam String carId){ 
//-----------------------------车辆进场star-----------------------------------------------------------					
					String c=carId;
					Map<String,Object> m=new HashMap<>();
					Dock doc=new Dock();
			        /*使用toUpperCase()方法实现大写转换*/
					if(c!=null||c!="") {
						String carIds = c.toUpperCase();
						//判断车辆是否已经入库
						doc=dockService.queryParkIdByCar(carId);
						if(doc!=null) {
							gCarTypes="该车辆已入库！请联系管理员";
							gCarId=carId;
							gCanGo="error";
							m.put("message", "该车辆已入库！请联系管理员");
							return m;
						}else {
							//判断车牌号是否为白名单以及为可用状态(搜索车辆表)
							List<Map<String,Object>> carInfo=carBrakeService.queryWhiteListCarByCarId(carId);
							if(carInfo.size()>0) {
								int parkId=Integer.parseInt(carInfo.get(0).get("parkId").toString());//车位ID
								//插入停靠表
								Map<String,Object> dock=new HashMap<String, Object>(); 
								dock.put("carId", carId);
								dock.put("parkId", parkId);
								boolean result1=dockService.creatDockByCar(dock);
								//转发信息
								if(result1) {
									gCarTypes="白名单vip";
									gCarId=carId;
									gCanGo="success";
									m.put("message", "放行成功");
									return m;
								}else {
									gCarTypes="白名单vip";
									gCarId=carId;
									gCanGo="error";
									m.put("message", "系统故障！请手动处理");
									return m;
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
									List<Map<String,Object>> carInfo2=carBrakeService.querymouthListCarByCarId(carId);
									if(carInfo2.size()>0){
										Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
										dockAndPark.put("carId", carId);
										dockAndPark.put("parkId", parkId2);
										dockAndPark.put("parkState", 2);
										boolean result1=dockService.creatDockByCar(dockAndPark);//插入停靠表
										//车位状态改为2
										boolean result2=carLocationService.updateParkStateById(dockAndPark);
										if(result1&&result2) {
											gCarTypes="月缴用户";
											gCarId=carId;
											gCanGo="success";
											m.put("message", "放行成功");
											return m;
										}
										else {
											gCarTypes="月缴用户";
											gCarId=carId;
											gCanGo="error";
											m.put("message", "系统故障！请手动处理");
											return m;
										}
									}
									else 
									{
										//判断用户是否存在
										List<Map<String,Object>> carById=carService.selectCarById(carId);
										if(carById.size()==0) {
											//创建临时用户
											boolean result2=carService.createCarWithNewUser(carIds);
										}
										Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
										dockAndPark.put("carId", carId);
										dockAndPark.put("parkId", parkId2);
										dockAndPark.put("parkState", 2);
										boolean result3=dockService.creatDockByCar(dockAndPark);
										boolean result2=carLocationService.updateParkStateById(dockAndPark);
										if(result2&&result3) {
											gCarTypes="临时用户";
											gCarId=carId;
											gCanGo="success";
											m.put("message", "放行成功");
											return m;
										}else {
											gCarTypes="临时用户";
											gCarId=carId;
											gCanGo="error";
											m.put("message", "系统故障！请手动处理");
											return m;
										}
									}
								}else
								{
									//没有车位
									gCarTypes="没有车位";
									gCarId=carIds;
									gCanGo="error";
									m.put("message", "没有车位");
									return m;
								}
								
							}
						}
					
					
				  }
				return m;
				  
		}		
		
//手动出场-----------------------------------------------------------------------------------------
	    @Transactional(rollbackFor=Exception.class)
	    @RequestMapping(value ="/getOutCar.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody Map<String,Object> getOutCar(@RequestParam String carId){
			Map<String,Object> m=new HashMap<>();
		      String CARID =carId;
		      Dock dock=new Dock();
		        /*使用toUpperCase()方法实现大写转换*/
				if(CARID!=null||CARID!="") {
					String carIds = CARID.toUpperCase();
//-------------------------------------车辆出场判断---------------------------------------------------------
					   double cost=0;
						//判断车辆类型
						HashMap<String,Object> car=carService.selectCarForType(carId);
						//根据停靠表判断车辆所处的车位
						dock=dockService.queryParkIdByCar(carId);
						if(dock!=null) {
//							cost=1.5;
							cost=chargeServices.getParkingCost(carIds);
							if(cost>0) {
								oCarTypes=""+car.get("parmName");
								oCarId=carId;
								oCanGo="请联系管理员进行缴费";
								oCost=cost+"元";
								oStarTime=dock.getStartTime();
								
								cCarId=carId;
					        	cCarTypes=""+car.get("parmName");
					        	cCost=cost;
					        	m.put("message", "请联系管理员进行缴费");
					        	return m;
							}else {
								//接口查看费用
								Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
								dockAndPark.put("carId", carId);
								dockAndPark.put("parkId", dock.getCarLocationId());
								dockAndPark.put("parkState", 1);
								dockAndPark.put("dockState", 2);
								//车位状态改为2
					 	        boolean result2=carLocationService.updateParkStateById(dockAndPark);
								//修改停靠表状态
						        boolean result3=dockService.updateDockState(dockAndPark);
						        if(result2&&result3) {
						        	oCarTypes=""+car.get("parmName");
						        	oCarId=carId;
						        	oCanGo="success";
						        	oCost="0元";
						        	oStarTime=dock.getStartTime();
						        	m.put("message", "可以放行");
						        	return m;
						        }else {
						        	oCarTypes=""+car.get("parmName");
						        	oCarId=carId;
						        	oCanGo="系统故障！请联系管理员";
						        	oCost="0元";
						        	oStarTime=dock.getStartTime();
						        	m.put("message", "系统故障！请联系管理员");
						        	return m;
						        }
							}
					
						}else {
							oCarTypes=""+car.get("parmName");
							oCarId=carId;
							oCanGo="该车辆未入库！请联系管理员";
							oCost=cost+"元";
							oStarTime="。。。。。";
							m.put("message", "该车辆未入库！请联系管理员");
							return m;
						}
						
//----------------------------------------------------------------------------------------------
				}
				return m;
		}		
//-------------------------------------------------------------------------------------------
		//手动出场-----------------------------------------------------------------------------------------
				@RequestMapping(value ="/canOut.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
				public @ResponseBody Map<String,Object> canOut(){
					Map<String,Object> m=new HashMap<>();
					m.put("message", "放行");
					    gOut="success";
						return m;
				}		
		
		
}