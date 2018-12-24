package com.great.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Dock;
import com.great.service.ICarBrakeService;
import com.great.service.ICarLocationService;
import com.great.service.ICarService;
import com.great.service.IChargeService;
import com.great.service.IDockService;
import com.great.service.IWhiteListService;
import com.great.util.Sample;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

/*�����ˣ�@lian shengwei
 * ���ڣ�20181219
 * բ���� 
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
	
	@Autowired
	@Qualifier("chargeServiceImpl")
	private IChargeService chargeServices;
	
	
	//�����볡
	@RequestMapping(value ="/carGoIn.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody Map<String,Object> carGoIn(@RequestParam String carId) {
		boolean result=false;
		Map<String,Object> carInformation=new HashMap<String,Object>();
		String CARID =carId;
        /*ʹ��toUpperCase()����ʵ�ִ�дת��*/
		if(CARID!=null||CARID!="") {
			String carIds = CARID.toUpperCase();
			//�жϳ����Ƿ��Ѿ����
			Dock doc=dockService.queryParkIdByCar(carIds);
			if(doc!=null) {
				carInformation.put("result", false);
				carInformation.put("existIn", false);
				carInformation.put("carId", carIds);
				carInformation.put("carType", "�ó�������⣡����ϵ����Ա");
				return carInformation;
			}
			//�жϳ��ƺ��Ƿ�Ϊ�������Լ�Ϊ����״̬(����������)
			List<Map<String,Object>> carInfo=carBrakeService.queryWhiteListCarByCarId(carIds);
			if(carInfo.size()>0) {
				int parkId=Integer.parseInt(carInfo.get(0).get("parkId").toString());//��λID
				//����ͣ����
				Map<String,Object> dock=new HashMap<String, Object>(); 
				dock.put("carId", carIds);
				dock.put("parkId", parkId);
				boolean result1=dockService.creatDockByCar(dock);
				//ת����Ϣ
				if(result1) {
					carInformation.put("result", true);
					carInformation.put("existIn", true);
					carInformation.put("carId", carIds);
					carInformation.put("parkId", parkId);	
					carInformation.put("carType", "������vip");	
				}else {
					carInformation.put("result", false);
					carInformation.put("existIn", true);
					carInformation.put("carId", carIds);
					carInformation.put("carType", "������vip");
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
					List<Map<String,Object>> carInfo2=carBrakeService.querymouthListCarByCarId(carIds);
					if(carInfo2.size()>0){
						Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
						dockAndPark.put("carId", carIds);
						dockAndPark.put("parkId", parkId2);
						dockAndPark.put("parkState", 2);
						boolean result1=dockService.creatDockByCar(dockAndPark);//����ͣ����
						//��λ״̬��Ϊ2
						boolean result2=carLocationService.updateParkStateById(dockAndPark);
						if(result1&&result2) {
							carInformation.put("result", true);
							carInformation.put("existIn", true);
							carInformation.put("carId", carIds);
							carInformation.put("parkId", parkId2);	
							carInformation.put("carType", "�½��û�");
						}
						else {
							carInformation.put("result", false);
							carInformation.put("existIn", true);
							carInformation.put("carId", carIds);
							carInformation.put("carType", "�½��û�");
						}
					}
					else 
					{
						//�ж��û��Ƿ����
						List<Map<String,Object>> carById=carService.selectCarById(carIds);
						if(carById.size()==0) {
							//������ʱ�û�
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
							carInformation.put("existIn", true);
							carInformation.put("carId", carIds);
							carInformation.put("parkId", parkId2);	
							carInformation.put("carType", "��ʱ�û�");
						}else {
							carInformation.put("result", false);
							carInformation.put("existIn", true);
							carInformation.put("carId", carIds);
							carInformation.put("carType", "��ʱ�û�");
						}
					}
				}else
				{
					//û�г�λ
					carInformation.put("result", false);
					carInformation.put("existIn", true);
					carInformation.put("carId", carIds);
					carInformation.put("parkId", "�޿��г�λ");	
					carInformation.put("carType", "������");
				}
				
			}
		
		
	  }
		return carInformation;
	}
	//��������
	@RequestMapping(value ="/carGetOut.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody Map<String,Object> carGetOut(@RequestParam String carId) {
		boolean result=false;
		Map<String,Object> carInformation=new HashMap<String,Object>();
		String CARID =carId;
		double cost=0;
		/*ʹ��toUpperCase()����ʵ�ִ�дת��*/
		if(CARID!=null||CARID!="") {
			String carIds = CARID.toUpperCase();
			//�жϳ�������
			HashMap<String,Object> car=carService.selectCarForType(carIds);
			
			//����ͣ�����жϳ��������ĳ�λ
			Dock dock=dockService.queryParkIdByCar(carIds);
			if(dock!=null) {
				cost=chargeServices.getParkingCost(carIds);
//				if(cost>0) {
//				}else {
//				}
				carInformation.put("result", true);
				carInformation.put("money",cost);
				carInformation.put("parkId",dock.getCarLocationId());
				carInformation.put("dockSTime",dock.getStartTime());
				carInformation.put("carType",car.get("parmName"));
			}else {
				carInformation.put("result", false);
				carInformation.put("parkId","�ó���δ��⣡����ϵ����Ա");
				carInformation.put("dockSTime","");
			}
			//�ӿڲ鿴����
//			Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
//			dockAndPark.put("carId", carIds);
//			dockAndPark.put("parkId", dock.getCarLocationId());
//			dockAndPark.put("parkState", 1);
//			dockAndPark.put("dockState", 2);
			
			//��λ״̬��Ϊ2
//			boolean result2=carLocationService.updateParkStateById(dockAndPark);
			//�޸�ͣ����״̬
//			boolean result3=dockService.updateDockState(dockAndPark);
			carInformation.put("carId", carIds);

		}
		return carInformation;
	}
	//��ȡ���г�λ����
	@RequestMapping(value ="/getParkNum.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody int getParkNum(HttpServletRequest request) {		
		List<Map<String,Object>> getParkList=carLocationService.getParkIdList();
		int parkNum=getParkList.size();
		return parkNum;
	}
	//ʶ��ͼƬ
	@RequestMapping(value ="/updatePhoto.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody String updatePhoto(MultipartFile img){ 
	      System.out.println("��ʼ�ϴ�");
	      String name =null;
	      try {
			JSONObject res =Sample.getCarId(img.getBytes());
			if(res!=null) {
				name = res.getJSONObject("words_result").getString("number");
			}else {
				System.out.println("�޷�ʶ��");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      System.out.println(name);
	      
	      return name;
	} 
	

	//����
		@RequestMapping(value ="/carCanGetOut.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody boolean carCanGetOut(@RequestParam String carId) {
			boolean result=false;
			String CARID =carId;
			/*ʹ��toUpperCase()����ʵ�ִ�дת��*/
			if(CARID!=null||CARID!="") {
				String carIds = CARID.toUpperCase();
				//�жϳ�������
				HashMap<String,Object> car=carService.selectCarForType(carIds);
				
				//����ͣ�����жϳ��������ĳ�λ
				Dock dock=dockService.queryParkIdByCar(carIds);
				if(dock!=null) {
					//�޸�״̬
					Map<String,Object> dockAndPark=new HashMap<String, Object>(); 
					dockAndPark.put("carId", carIds);
					dockAndPark.put("parkId", dock.getCarLocationId());
					dockAndPark.put("parkState", 1);
					dockAndPark.put("dockState", 2);
					//��λ״̬��Ϊ2
					boolean result2=carLocationService.updateParkStateById(dockAndPark);
					//�޸�ͣ����״̬
					boolean result3=dockService.updateDockState(dockAndPark);
					result=true;
				}
			}
			return result;
		}
			
		
			

}
