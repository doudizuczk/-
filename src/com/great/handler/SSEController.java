package com.great.handler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
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
	volatile public static String getOut = null; //手动放行
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
	        if(getOut!=null) {
	        	getOut=null;
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
	
//-------------------------------------------------------------------------------
//	@RequestMapping(value="/push.action",method=RequestMethod.GET,produces = "text/event-stream;charset=UTF-8")
//	public void  push(HttpServletResponse response){
//		response.setContentType("text/event-stream");
//		response.setCharacterEncoding("utf-8");
//		while (true) {
//	        Random r = new Random();
//	        try {
//	        Thread.sleep(3000);
//	        PrintWriter pw=response.getWriter();
//	        System.out.println("name1"+Name);
//	        if(Name!=null) {
//	        	pw.write("data:" + Name+r.nextInt() + "\n\n");
//	        }
//	        pw.flush();
//	        if(Name!=null) {
//	        	Name=null;
//	        }
//	        if(pw.checkError()){
//	            System.out.println("客户端断开连接");
//	            return;
//	        }
//	        } catch (Exception e) {
//	         e.printStackTrace();
//	       }
//	     }
//	}
//-------------------------------------------------------------------------------	
//	    public @ResponseBody String push(HttpServletResponse response){
//	         Random r = new Random();
//	         try {
//	                 Thread.sleep(5000);
//	         } catch (InterruptedException e) {
//	                 e.printStackTrace();
//	         }
//	         System.out.println("111111");
//	         return "data:Testing 1,2,3" + "wwwwwwwwwwww"+"\n\n";
//	         return "data:Testing 1,2,3" + r.nextInt() +"\n\n";
//	    }
//-------------------------------------------------------------------------------
	   //入口显示动态
//	    @RequestMapping(value="/push2.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
//	    public @ResponseBody String push2(@RequestParam String name){
//		   Name=name;
//		  return "1";
//	    }
	  //入口显示动态：识别图片
		@RequestMapping(value ="/updatePhoto.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody String updatePhoto(MultipartFile img){ 
		      System.out.println("开始上传1");
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
						Dock doc=dockService.queryParkIdByCar(carId);
						if(doc!=null) {
							gCarTypes="该车辆已入库！请联系管理员";
							gCarId=carId;
							gCanGo="error";;
							return carId;
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
								return carId;
							}else {
								gCarTypes="白名单vip";
								gCarId=carId;
								gCanGo="error";
								return carId;
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
										return carId;
									}
									else {
										gCarTypes="月缴用户";
										gCarId=carId;
										gCanGo="error";
										return carId;
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
										return carId;
									}else {
										gCarTypes="临时用户";
										gCarId=carId;
										gCanGo="error";
										return carId;
									}
								}
							}else
							{
								//没有车位
								gCarTypes="没有车位";
								gCarId=carIds;
								gCanGo="error";
								return carId;
							}
							
						}
					
					
				  }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("无法识别");
			}
		      System.out.println(carId);
		      return carId;
		}
//-----------------------------车辆进场end-----------------------------------------------------------					
//-----------------------------车辆出场star----------------------------------------------------------	
		@RequestMapping(value ="/updateGetOutPhoto.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody String updateGetOutPhoto(MultipartFile img){
			System.out.println("开始上传2");
		      String carId =null;
		      try {
				    JSONObject res =Sample.getCarId(img.getBytes());
				    carId = res.getJSONObject("words_result").getString("number");
//-------------------------------------车辆出场判断---------------------------------------------------------
					double cost=0;
						//判断车辆类型
						HashMap<String,Object> car=carService.selectCarForType(carId);
						//根据停靠表判断车辆所处的车位
						Dock dock=dockService.queryParkIdByCar(carId);
						if(dock!=null) {
							cost=1.5;
							if(cost>0) {
								oCarTypes=""+car.get("parmName");
								oCarId=carId;
								oCanGo="请联系管理员进行缴费";
								oCost=cost+"元";
								oStarTime=dock.getStartTime();
								
								cCarId=carId;
					        	cCarTypes=""+car.get("parmName");
					        	cCost=cost;
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
						        }else {
						        	oCarTypes=""+car.get("parmName");
						        	oCarId=carId;
						        	oCanGo="系统故障！请联系管理员";
						        	oCost="0元";
						        	oStarTime=dock.getStartTime();
						        }
							}
					
						}else {
							oCarTypes=""+car.get("parmName");
							oCarId=carId;
							oCanGo="该车辆未入库！请联系管理员";
							oCost=cost+"元";
							oStarTime="。。。。。";
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
				System.out.println("无法识别");
			}
		      System.out.println(carId);
		      return carId;
		}
//-----------------------------车辆出场end-----------------------------------------------------------	
	//手动放行
}