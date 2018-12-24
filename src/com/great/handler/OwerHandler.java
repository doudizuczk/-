package com.great.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.great.bean.Car;
import com.great.bean.Menu;
import com.great.bean.Ower;
import com.great.bean.TranSact;
import com.great.service.IOwerService;
import com.great.util.DateUtils;
import com.great.util.Simple;

@Controller
@RequestMapping("/owerHandler")
public class OwerHandler {
	@Autowired
	@Qualifier("owerServiceImpl")
	private IOwerService owerService;
	//��¼
	@RequestMapping(value = "/owerLogin.action", method = RequestMethod.POST)
	public @ResponseBody String owerLogin(HttpServletRequest request, @RequestBody Ower ower) {
		ModelAndView model = new ModelAndView();
		Ower loginOwer = owerService.owerLogin(ower);
		if (loginOwer != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginOwer", loginOwer);
			return "1";
		} else {
			return "0";
		}
	}

	// ע��ʱaccount�����Լ��1
	@RequestMapping(value = "/checkOwerAccount.action", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody String checkOwerAccount(String owerAccount) {
		Ower ower = new Ower();
		ower.setOwerAccount(owerAccount);
		Ower loginOwer = owerService.owerLogin(ower);
		if (loginOwer == null) {
			return "1";
		} else {
			return "0";
		}
	}

	// ����ע��
	@RequestMapping(value = "/owerRegister.action", method = RequestMethod.POST)
	public @ResponseBody String owerRegister(HttpServletRequest request, @RequestBody Ower ower) {
		HttpSession session=request.getSession();
		String code=(String)session.getAttribute("Code");
		session.removeAttribute("Code");
		if(code.equals(ower.getCheckCode())) {
		int result = owerService.addOwer(ower);
		if (result > 0) {
			return "1";
		} else {
			return "0";
		}
		}else {
			return "3";
		}
	}

	// �����鿴
	@RequestMapping(value = "/searchOwersCar.action")
	public ModelAndView searchOwersCar(
			@RequestParam(value = "owerId", required = true, defaultValue = "0") int owerId) {
		ModelAndView model = new ModelAndView();
		List<Map<String, Object>> carList = owerService.searchOwersCar(owerId);
		model.addObject("carList", carList);
		model.setViewName("forward:/frontstage/mycar.jsp");
		return model;
	}

	// �����󶨳���
	@RequestMapping(value = "/addCars.action")
	public @ResponseBody String addCars(@RequestBody Car car) {
		System.out.println("��ȡ���ĳ���id=" + car.getOwerId());
		Ower ower = new Ower();
		ower.setOwerId(car.getOwerId());
		System.out.println(ower.getOwerId());
		Ower loginOwer = owerService.owerLogin(ower);
		int age = loginOwer.getOwerAge();
		int result = 0;
		if (age != 0 && age >= 18) {
			result = owerService.addCars(car);
			if (result > 0) {
				return "1";// �󶨳ɹ�
			} else {
				return "0";// ��ʧ��
			}
		} else {
			return "3";// δͨ��ʵ����֤
		}

	}

	// �������
	@RequestMapping(value = "/escCars.action", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody String escCars(@Param("carId") String carId) {
		int result = owerService.escCars(carId);
		if (result > 0) {
			return "1";
		} else {
			return "0";
		}
	}

	// ��ѯ���ײ��б�
	@RequestMapping(value = "/searchPack.action", method = RequestMethod.GET)
	public ModelAndView searchPack() {
		ModelAndView model = new ModelAndView();
		List<Map<String, Object>> packList = owerService.searchPack();
		model.addObject("packList", packList);
		model.setViewName("forward:/frontstage/paymoneyforcar.jsp");
		return model;
	}

	// �½ɰ���
	@RequestMapping(value = "/payMoneyForCar.action")
	public @ResponseBody String payMoneyForCar(@RequestBody int owerId, @RequestBody String carId,
			@RequestBody int packId) {

		return "";
	}

	// ʵ����֤
	@RequestMapping(value = "/upImage.action")
	public @ResponseBody String upImage(HttpServletRequest request, MultipartFile img) throws Exception {
		JSONObject map = Simple.getMap(img.getBytes());
		JSONObject maptwo = (JSONObject) map.getJSONObject("words_result").get("����");
		JSONObject three = (JSONObject) map.getJSONObject("words_result").get("������ݺ���");
		JSONObject four = (JSONObject) map.getJSONObject("words_result").get("����");
		String birth = (String) maptwo.get("words");
		String owerIDcard = (String) three.get("words");
		System.out.println(owerIDcard);
		String owerName = (String) four.get("words");
		Map messige = new HashMap();
		messige = DateUtils.getCarInfo15W(owerIDcard);

		int age = (int) messige.get("age");
		String sex = (String) messige.get("sex");
		int myage = 0;
		if (sex.equals("��")) {
			myage = 1;
		} else {
			myage = 2;
		}
		// ��ȡ��ǰ�û�id
		HttpSession session = request.getSession();
		Ower ower = (Ower) session.getAttribute("loginOwer");
		ower.setOwerIdcard(owerIDcard);
		ower.setOwerAge(age);
		ower.setOwerSex(myage);
		System.out.println(ower.getOwerIdcard());
		int result = owerService.addMessage(ower);
		if (age >= 18) {
			return "1";
		} else {
			return "0";
		}
	}
		//��ȡ��������
		@RequestMapping(value = "/getMeans.action")
		public ModelAndView getMeans(@RequestParam(value = "owerId", required = true, defaultValue = "0") int owerId) {
			ModelAndView model =new ModelAndView();
			Ower ower =new Ower();
			ower.setOwerId(owerId);
			Ower mess =owerService.owerLogin(ower);
			model.addObject("mess", mess);
			model.setViewName("forward:/frontstage/means.jsp");
			return model;
		}
	//�޸�����
		@RequestMapping(value = "/changeMeans.action", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
		public @ResponseBody String changeMeans(@RequestParam(value = "owerId", required = true, defaultValue = "0")int owerId,@RequestParam(value = "owerName", required = true, defaultValue = "")String owerName,@RequestParam(value = "owerPhone", required = true, defaultValue = "")String owerPhone) {
			Ower ower =new Ower();
			ower.setOwerId(owerId);
			ower.setOwerName(owerName);
			ower.setOwerPhone(owerPhone);
			int result=owerService.changeMeans(ower);
			if(result>0) {
				return "1";
			}else {
				return "0";
			}
		}
		//�����ҵ�ҳ��֮ǰ����ѯ�û���Ϣ(�޸������������Ҫ��ת)
		@RequestMapping(value="/beforeToMy.action")
		public ModelAndView beforeToMy(HttpServletRequest request) {
			ModelAndView model=new ModelAndView();
			HttpSession session=request.getSession();
			Ower ower=(Ower)session.getAttribute("loginOwer");
			Ower util=owerService.owerLogin(ower);
			model.addObject("ower", util);
			model.setViewName("forward:/frontstage/my.jsp");
			return model;
		}
		//��¼�ɹ��󣬲�ѯ��¼�û��ĳ������ײ���Ϣ
		@RequestMapping(value="/getOwerCarMess.action")
		public ModelAndView getOwerCarMess(HttpServletRequest request) {
			ModelAndView model =new ModelAndView();
			HttpSession session=request.getSession();
			Ower ower=(Ower)session.getAttribute("loginOwer");
			int owerId=ower.getOwerId();//��ȡ����½�û���id
			List<Car> carList=owerService.carList(owerId);
			List<TranSact> tranList=new ArrayList<>();
			for(Car car:carList) {
				TranSact tran=new TranSact();
				tran=owerService.tranList(car);
				tranList.add(tran);
			}
			model.addObject("tranList", tranList);
			model.setViewName("forward:/frontstage/user_main.jsp");
			return model;
		}
}
