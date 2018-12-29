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

/** SSE �����������¼�
 */
@Controller
@RequestMapping("/sse")
public class SSEController {
	volatile public static String gCarId = null;//�������ƺ�
	volatile public static String gCarTypes = null;//������������
	volatile public static String gCanGo = null;//���������Ƿ�ɽ���
	volatile public static String oCarId = null;//�������ƺ�
	volatile public static String oCarTypes = null;//������������
	volatile public static String oCanGo = null;//���������Ƿ�ɳ�ȥ
	volatile public static String oCost = null;//��������
	volatile public static String oStarTime = null;//�볡ʱ��
	volatile public static String cCarId = null; //�ɷ����ͳ��ƺ�
	volatile public static String cCarTypes = null; //�ɷ����ͳ�������
	volatile public static Double cCost =0.0; //�ɷ����ͳ�������
	volatile public static String gOut = null; //�ֶ�����
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
	
	//�����ʾ��������Ϣ
	@RequestMapping(value="/goIn.action",method=RequestMethod.GET,produces = "text/event-stream;charset=UTF-8")
	public void  push(HttpServletResponse response){
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("utf-8");
		while (true) {
	        try {
	        Thread.sleep(500);
	        PrintWriter pw=response.getWriter();
	        //����
	        pw.write("retry: 86400000\n");
	        if(gCarId!=null&&gCarTypes!=null&&gCanGo!=null) {
//	        	pw.write("event: slide\n");
//	        	pw.write("data:" + Name + "\n\n");
	        	pw.write("data:{'gCarId':'"+gCarId+"','gCarTypes':'"+gCarTypes+"','gCanGo':'"+gCanGo+"'}\n\n");
	        }
	        //����
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
	      //������ʼ��
	        if(gCarId!=null&&gCarTypes!=null&&gCanGo!=null) {
	        	gCarId=null;
	        	gCarTypes=null;
	        	gCanGo=null;
	        }
	        //������ʼ��
	        if(oCarId!=null&&oCarTypes!=null&&oCanGo!=null) {
	        	oCarId=null;
	        	oCarTypes=null;
	        	oCanGo=null;
	        }
	        //�ɷѳ�ʼ��
	        if(cCarId!=null&&cCarTypes!=null&&cCost!=null) {
	        	cCarId=null;
	        	cCarTypes=null;
	        	cCost=null;
	        }
	        //���г�ʼ��
	        if(gOut!=null) {
	        	gOut=null;
	        }
	        if(pw.checkError()){
	            System.out.println("�ͻ��˶Ͽ�����");
	            return;
	        }
	        } catch (Exception e) {
	         e.printStackTrace();
	       }
	     }
	}
	  //�����ʾ��̬��ʶ��ͼƬ
	    @Transactional(rollbackFor=Exception.class)
		@RequestMapping(value ="/updatePhoto.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody Map<String,Object> updatePhoto(MultipartFile img){ 
		      System.out.println("��ʼ�ϴ�1");
		      Map<String,Object> m=new HashMap<>();
		      Dock doc=new Dock();
		      String carId =null;
		      try {
				    JSONObject res =Sample.getCarId(img.getBytes());
				    carId = res.getJSONObject("words_result").getString("number");
//-----------------------------��������star-----------------------------------------------------------					
				    boolean result=false;
					String CARID =carId;
			        /*ʹ��toUpperCase()����ʵ�ִ�дת��*/
					if(CARID!=null||CARID!="") {
						String carIds = CARID.toUpperCase();
						//�жϳ����Ƿ��Ѿ����
						doc=dockService.queryParkIdByCar(carId);
						if(doc!=null) {
							gCarTypes="�ó�������⣡����ϵ����Ա";
							gCarId=carId;
							gCanGo="error";
							m.put("message", "�ó�������⣡����ϵ����Ա");
							return m;
						}
						//�жϳ��ƺ��Ƿ�Ϊ�������Լ�Ϊ����״̬(����������)
						List<Map<String,Object>> carInfo=carBrakeService.queryWhiteListCarByCarId(carId);
						if(carInfo.size()>0) {
							int parkId=Integer.parseInt(carInfo.get(0).get("parkId").toString());//��λID
							//����ͣ����
							Map<String,Object> dock=new HashMap<String, Object>(); 
							dock.put("carId", carId);
							dock.put("parkId", parkId);
							boolean result1=dockService.creatDockByCar(dock);
							//ת����Ϣ
							if(result1) {
								gCarTypes="������vip";
								gCarId=carId;
								gCanGo="success";
								m.put("message", "���Խ���");
								return m;
							}else {
								gCarTypes="������vip";
								gCarId=carId;
								gCanGo="error";
								m.put("message", "ϵͳ����");
								return m;
							}
							
						}else 
						{
							//�ж��Ƿ��г�λ
							List<Map<String,Object>> getParkList=carLocationService.getParkIdList();
							if(getParkList.size()>0) {
								//�г�λ
								//��ȡ��λ
								int parkId2=Integer.parseInt(getParkList.get(0).get("parkId").toString());//��λID
								//�ж��û��Ƿ�Ϊ�½��û�
								List<Map<String,Object>> carInfo2=carBrakeService.querymouthListCarByCarId(carId);
								if(carInfo2.size()>0){
									Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
									dockAndPark.put("carId", carId);
									dockAndPark.put("parkId", parkId2);
									dockAndPark.put("parkState", 2);
									boolean result1=dockService.creatDockByCar(dockAndPark);//����ͣ����
									//��λ״̬��Ϊ2
									boolean result2=carLocationService.updateParkStateById(dockAndPark);
									if(result1&&result2) {
										gCarTypes="�½��û�";
										gCarId=carId;
										gCanGo="success";
										m.put("message", "���Խ���");
										return m;
									}
									else {
										gCarTypes="�½��û�";
										gCarId=carId;
										gCanGo="error";
										m.put("message", "ϵͳ����");
										return m;
									}
								}
								else 
								{
									//�ж��û��Ƿ����
									List<Map<String,Object>> carById=carService.selectCarById(carId);
									if(carById.size()==0) {
										//������ʱ�û�
										boolean result2=carService.createCarWithNewUser(carIds);
									}
									Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
									dockAndPark.put("carId", carId);
									dockAndPark.put("parkId", parkId2);
									dockAndPark.put("parkState", 2);
									boolean result3=dockService.creatDockByCar(dockAndPark);
									boolean result2=carLocationService.updateParkStateById(dockAndPark);
									if(result2&&result3) {
										gCarTypes="��ʱ�û�";
										gCarId=carId;
										gCanGo="success";
										m.put("message", "���Խ���");
										return m;
									}else {
										gCarTypes="��ʱ�û�";
										gCarId=carId;
										gCanGo="error";
										m.put("message", "ϵͳ����");
										return m;
									}
								}
							}else
							{
								//û�г�λ
								gCarTypes="û�г�λ";
								gCarId=carIds;
								gCanGo="error";
								m.put("message", "û�г�λ");
								return m;
							}
							
						}
					
					
				  }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				m.put("message", "�޷�ʶ��");
				return m;
			}
		      return m;
		}
//-----------------------------��������end-----------------------------------------------------------					
//-----------------------------��������star----------------------------------------------------------	
	    @Transactional(rollbackFor=Exception.class)
	    @RequestMapping(value ="/updateGetOutPhoto.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody Map<String,Object> updateGetOutPhoto(MultipartFile img){
			System.out.println("��ʼ�ϴ�2");
			Map<String,Object> m=new HashMap<>();
			Dock dock=new Dock();
		      String carId =null;
		      try {
				    JSONObject res =Sample.getCarId(img.getBytes());
				    carId = res.getJSONObject("words_result").getString("number");
//-------------------------------------���������ж�---------------------------------------------------------
					double cost=0;
						//�жϳ�������
						HashMap<String,Object> car=carService.selectCarForType(carId);
						//����ͣ�����жϳ��������ĳ�λ
						dock=dockService.queryParkIdByCar(carId);
						if(dock!=null) {
//							cost=1.5;
							cost=chargeServices.getParkingCost(carId);
							if(cost>0) {
								oCarTypes=""+car.get("parmName");
								oCarId=carId;
								oCanGo="����ϵ����Ա���нɷ�";
								oCost=cost+"Ԫ";
								oStarTime=dock.getStartTime();
								
								cCarId=carId;
					        	cCarTypes=""+car.get("parmName");
					        	cCost=cost;
					        	m.put("message","����ϵ����Ա���нɷ�");
					        	return m;
							}else {
								//�ӿڲ鿴����
								Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
								dockAndPark.put("carId", carId);
								dockAndPark.put("parkId", dock.getCarLocationId());
								dockAndPark.put("parkState", 1);
								dockAndPark.put("dockState", 2);
								//��λ״̬��Ϊ2
					 	        boolean result2=carLocationService.updateParkStateById(dockAndPark);
								//�޸�ͣ����״̬
						        boolean result3=dockService.updateDockState(dockAndPark);
						        if(result2&&result3) {
						        	oCarTypes=""+car.get("parmName");
						        	oCarId=carId;
						        	oCanGo="success";
						        	oCost="0Ԫ";
						        	oStarTime=dock.getStartTime();
						        	m.put("message","���Գ���");
						        }else {
						        	oCarTypes=""+car.get("parmName");
						        	oCarId=carId;
						        	oCanGo="ϵͳ���ϣ�����ϵ����Ա";
						        	oCost="0Ԫ";
						        	oStarTime=dock.getStartTime();
						        	m.put("message","ϵͳ���ϣ�����ϵ����Ա");
						        	return m;
						        }
							}
					
						}else {
							oCarTypes=""+car.get("parmName");
							oCarId=carId;
							oCanGo="�ó���δ��⣡����ϵ����Ա";
							oCost=cost+"Ԫ";
							oStarTime="����������";
							m.put("message","�ó���δ��⣡����ϵ����Ա");
							return m;
						}
						
//----------------------------------------------------------------------------------------------
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				oCarTypes="δʶ��";
				oCarId="δʶ��";
				oCanGo="����ϵ����Ա";
				oCost="0Ԫ";
				oStarTime="����������";
				m.put("message","�޷�ʶ��");
				return m;
			}
		      
		      return m;
		}
//-----------------------------��������end-----------------------------------------------------------	
//�ֶ�����------------------------------------------------------------------------------------
	    @Transactional(rollbackFor=Exception.class)
	    @RequestMapping(value ="/goInCar.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody Map<String,Object> goInCar(@RequestParam String carId){ 
//-----------------------------��������star-----------------------------------------------------------					
					String c=carId;
					Map<String,Object> m=new HashMap<>();
					Dock doc=new Dock();
			        /*ʹ��toUpperCase()����ʵ�ִ�дת��*/
					if(c!=null||c!="") {
						String carIds = c.toUpperCase();
						//�жϳ����Ƿ��Ѿ����
						doc=dockService.queryParkIdByCar(carId);
						if(doc!=null) {
							gCarTypes="�ó�������⣡����ϵ����Ա";
							gCarId=carId;
							gCanGo="error";
							m.put("message", "�ó�������⣡����ϵ����Ա");
							return m;
						}else {
							//�жϳ��ƺ��Ƿ�Ϊ�������Լ�Ϊ����״̬(����������)
							List<Map<String,Object>> carInfo=carBrakeService.queryWhiteListCarByCarId(carId);
							if(carInfo.size()>0) {
								int parkId=Integer.parseInt(carInfo.get(0).get("parkId").toString());//��λID
								//����ͣ����
								Map<String,Object> dock=new HashMap<String, Object>(); 
								dock.put("carId", carId);
								dock.put("parkId", parkId);
								boolean result1=dockService.creatDockByCar(dock);
								//ת����Ϣ
								if(result1) {
									gCarTypes="������vip";
									gCarId=carId;
									gCanGo="success";
									m.put("message", "���гɹ�");
									return m;
								}else {
									gCarTypes="������vip";
									gCarId=carId;
									gCanGo="error";
									m.put("message", "ϵͳ���ϣ����ֶ�����");
									return m;
								}
								
							}else 
							{
								//�ж��Ƿ��г�λ
								List<Map<String,Object>> getParkList=carLocationService.getParkIdList();
								if(getParkList.size()>0) {
									//�г�λ
									//��ȡ��λ
									int parkId2=Integer.parseInt(getParkList.get(0).get("parkId").toString());//��λID
									//�ж��û��Ƿ�Ϊ�½��û�
									List<Map<String,Object>> carInfo2=carBrakeService.querymouthListCarByCarId(carId);
									if(carInfo2.size()>0){
										Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
										dockAndPark.put("carId", carId);
										dockAndPark.put("parkId", parkId2);
										dockAndPark.put("parkState", 2);
										boolean result1=dockService.creatDockByCar(dockAndPark);//����ͣ����
										//��λ״̬��Ϊ2
										boolean result2=carLocationService.updateParkStateById(dockAndPark);
										if(result1&&result2) {
											gCarTypes="�½��û�";
											gCarId=carId;
											gCanGo="success";
											m.put("message", "���гɹ�");
											return m;
										}
										else {
											gCarTypes="�½��û�";
											gCarId=carId;
											gCanGo="error";
											m.put("message", "ϵͳ���ϣ����ֶ�����");
											return m;
										}
									}
									else 
									{
										//�ж��û��Ƿ����
										List<Map<String,Object>> carById=carService.selectCarById(carId);
										if(carById.size()==0) {
											//������ʱ�û�
											boolean result2=carService.createCarWithNewUser(carIds);
										}
										Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
										dockAndPark.put("carId", carId);
										dockAndPark.put("parkId", parkId2);
										dockAndPark.put("parkState", 2);
										boolean result3=dockService.creatDockByCar(dockAndPark);
										boolean result2=carLocationService.updateParkStateById(dockAndPark);
										if(result2&&result3) {
											gCarTypes="��ʱ�û�";
											gCarId=carId;
											gCanGo="success";
											m.put("message", "���гɹ�");
											return m;
										}else {
											gCarTypes="��ʱ�û�";
											gCarId=carId;
											gCanGo="error";
											m.put("message", "ϵͳ���ϣ����ֶ�����");
											return m;
										}
									}
								}else
								{
									//û�г�λ
									gCarTypes="û�г�λ";
									gCarId=carIds;
									gCanGo="error";
									m.put("message", "û�г�λ");
									return m;
								}
								
							}
						}
					
					
				  }
				return m;
				  
		}		
		
//�ֶ�����-----------------------------------------------------------------------------------------
	    @Transactional(rollbackFor=Exception.class)
	    @RequestMapping(value ="/getOutCar.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody Map<String,Object> getOutCar(@RequestParam String carId){
			Map<String,Object> m=new HashMap<>();
		      String CARID =carId;
		      Dock dock=new Dock();
		        /*ʹ��toUpperCase()����ʵ�ִ�дת��*/
				if(CARID!=null||CARID!="") {
					String carIds = CARID.toUpperCase();
//-------------------------------------���������ж�---------------------------------------------------------
					   double cost=0;
						//�жϳ�������
						HashMap<String,Object> car=carService.selectCarForType(carId);
						//����ͣ�����жϳ��������ĳ�λ
						dock=dockService.queryParkIdByCar(carId);
						if(dock!=null) {
//							cost=1.5;
							cost=chargeServices.getParkingCost(carIds);
							if(cost>0) {
								oCarTypes=""+car.get("parmName");
								oCarId=carId;
								oCanGo="����ϵ����Ա���нɷ�";
								oCost=cost+"Ԫ";
								oStarTime=dock.getStartTime();
								
								cCarId=carId;
					        	cCarTypes=""+car.get("parmName");
					        	cCost=cost;
					        	m.put("message", "����ϵ����Ա���нɷ�");
					        	return m;
							}else {
								//�ӿڲ鿴����
								Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
								dockAndPark.put("carId", carId);
								dockAndPark.put("parkId", dock.getCarLocationId());
								dockAndPark.put("parkState", 1);
								dockAndPark.put("dockState", 2);
								//��λ״̬��Ϊ2
					 	        boolean result2=carLocationService.updateParkStateById(dockAndPark);
								//�޸�ͣ����״̬
						        boolean result3=dockService.updateDockState(dockAndPark);
						        if(result2&&result3) {
						        	oCarTypes=""+car.get("parmName");
						        	oCarId=carId;
						        	oCanGo="success";
						        	oCost="0Ԫ";
						        	oStarTime=dock.getStartTime();
						        	m.put("message", "���Է���");
						        	return m;
						        }else {
						        	oCarTypes=""+car.get("parmName");
						        	oCarId=carId;
						        	oCanGo="ϵͳ���ϣ�����ϵ����Ա";
						        	oCost="0Ԫ";
						        	oStarTime=dock.getStartTime();
						        	m.put("message", "ϵͳ���ϣ�����ϵ����Ա");
						        	return m;
						        }
							}
					
						}else {
							oCarTypes=""+car.get("parmName");
							oCarId=carId;
							oCanGo="�ó���δ��⣡����ϵ����Ա";
							oCost=cost+"Ԫ";
							oStarTime="����������";
							m.put("message", "�ó���δ��⣡����ϵ����Ա");
							return m;
						}
						
//----------------------------------------------------------------------------------------------
				}
				return m;
		}		
//-------------------------------------------------------------------------------------------
		//�ֶ�����-----------------------------------------------------------------------------------------
				@RequestMapping(value ="/canOut.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
				public @ResponseBody Map<String,Object> canOut(){
					Map<String,Object> m=new HashMap<>();
					m.put("message", "����");
					    gOut="success";
						return m;
				}		
		
		
}