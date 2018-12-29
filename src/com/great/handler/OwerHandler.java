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
import com.great.bean.Pack;
import com.great.bean.TranSact;
import com.great.service.ICarService;
import com.great.service.IOwerService;
import com.great.service.IPackService;
import com.great.service.IParmService;
import com.great.service.ITransactService;
import com.great.util.DateUtils;
import com.great.util.Simple;

@Controller
@RequestMapping("/owerHandler")
public class OwerHandler {
	@Autowired
	@Qualifier("owerServiceImpl")
	private IOwerService owerService;
	@Autowired
	@Qualifier("transactServiceImpl")
	private ITransactService transactService;
	@Autowired
	@Qualifier("parmServiceImpl")
	private IParmService parmService;
	@Autowired
	@Qualifier("packServiceImpl")
	private IPackService packService;
	@Autowired
	@Qualifier("carServiceImpl")
	private ICarService carService;

	// 登录
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

	// 注册时account可用性检测1
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

	// 车主注册
	@RequestMapping(value = "/owerRegister.action", method = RequestMethod.POST)
	public @ResponseBody String owerRegister(HttpServletRequest request, @RequestBody Ower ower) {
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("Code");
		session.removeAttribute("Code");
		if (code.equals(ower.getCheckCode())) {
			int result = owerService.addOwer(ower);
			if (result > 0) {
				return "1";
			} else {
				return "0";
			}
		} else {
			return "3";
		}
	}

	// 车辆查看
	@RequestMapping(value = "/searchOwersCar.action")
	public ModelAndView searchOwersCar(
			@RequestParam(value = "owerId", required = true, defaultValue = "0") int owerId) {
		ModelAndView model = new ModelAndView();
		List<Map<String, Object>> carList = owerService.searchOwersCar(owerId);
		model.addObject("carList", carList);
		model.setViewName("forward:/frontstage/mycar.jsp");
		return model;
	}

	// 车主绑定车辆
	@RequestMapping(value = "/addCars.action")
	public @ResponseBody String addCars(@RequestBody Car car) {
		/* 判断是否实名认证 */
		Ower ower = new Ower();
		ower.setOwerId(car.getOwerId());
		System.out.println(ower.getOwerId());
		Ower loginOwer = owerService.owerLogin(ower);
		int age = loginOwer.getOwerAge();
		// 判断车辆是否在车辆表里
		Map<String, Object> map = carService.carIdQueryCar(car.getCarId());
		int result = 0;
		if (age != 0 && age >= 18) {
			if (map != null) {
				result = owerService.updateCarMess(car);
				if (result > 0) {
					return "1";// 绑定成功
				} else {
					return "0";// 绑定失败
				}
			} else {
				result = owerService.addCars(car);
				if (result > 0) {
					return "1";// 绑定成功
				} else {
					return "0";// 绑定失败
				}
			}
		} else {
			return "3";// 未通过实名认证
		}

	}

	// 车辆解绑
	@RequestMapping(value = "/escCars.action", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody String escCars(@Param("carId") String carId) {
		int result = owerService.escCars(carId);
		if (result > 0) {
			return "1";
		} else {
			return "0";
		}
	}

//	// 查询出套餐列表
//	@RequestMapping(value = "/searchPack.action", method = RequestMethod.GET)
//	public ModelAndView searchPack() {
//		ModelAndView model = new ModelAndView();
//		List<Map<String, Object>> packList = owerService.searchPack();
//		model.addObject("packList", packList);
//		model.setViewName("forward:/frontstage/paymoneyforcar.jsp");
//		return model;
//	}

	// CZK-菜单点击套餐办理页面跳转
	@RequestMapping(value = "/searchPack.action")
//	@RequestParam(value = "owerId", required = true, defaultValue = "0") int owerId)
	public ModelAndView JumpPackList(HttpServletRequest request,
			@RequestParam(value = "owerId", required = true, defaultValue = "0") int owerId) {
		List<Map<String, Object>> typePack = parmService.IdQueryParmName(9);// 套餐类型列表
		List<Map<String, Object>> carList = owerService.searchOwersCar(owerId);// 用户车辆
		ModelAndView mav = new ModelAndView();
		Map<String, Object> dates = new HashMap<String, Object>();
		dates.put("TypePack", typePack);
		mav.addObject("dates", dates);
		mav.addObject("carList", carList);
		mav.setViewName("forward:/frontstage/paymoneyforcar.jsp");
		return mav;
	}

	// czk-办理页套餐类型改变事件返回套餐列表
	@RequestMapping(value = "/SelectTypeChange.action")
	public @ResponseBody Map<String, Object> SelectTypeChange(HttpServletRequest request) {// pageCurr不能为空，并初始化
		Integer typeId = Integer.parseInt(request.getParameter("packType"));
		Pack pack = new Pack();
		pack.setPackType(typeId);
		List<Map<String, Object>> packList = packService.queryPackList(pack);

		Map<String, Object> dates = new HashMap<String, Object>();
		dates.put("packList", packList);
		return dates;
	}

	// czk-点击套餐办理
	@RequestMapping(value = "/carIdPackTransact.action")
	public @ResponseBody Map<String, Object> packTransact(HttpServletRequest request) {
		int packId = Integer.parseInt(request.getParameter("packId"));
		String carID = request.getParameter("carAccount");

		Map<String, Object> add = transactService.carIdTransactPack(carID, packId);

		return add;
	}

//	// 月缴办理
//	@RequestMapping(value = "/payMoneyForCar.action")
//	public @ResponseBody String payMoneyForCar(@RequestBody int owerId, @RequestBody String carId,
//			@RequestBody int packId) {
//
//		return "";
//	}

	// 实名认证
	@RequestMapping(value = "/upImage.action")
	public @ResponseBody String upImage(HttpServletRequest request, MultipartFile img) throws Exception {
		JSONObject map = Simple.getMap(img.getBytes());
		JSONObject maptwo = (JSONObject) map.getJSONObject("words_result").get("出生");
		JSONObject three = (JSONObject) map.getJSONObject("words_result").get("公民身份号码");
		JSONObject four = (JSONObject) map.getJSONObject("words_result").get("姓名");
		String birth = (String) maptwo.get("words");
		String owerIDcard = (String) three.get("words");
		System.out.println(owerIDcard);
		String owerName = (String) four.get("words");
		Map messige = new HashMap();
		messige = DateUtils.getCarInfo15W(owerIDcard);

		int age = (int) messige.get("age");
		String sex = (String) messige.get("sex");
		int myage = 0;
		if (sex.equals("男")) {
			myage = 1;
		} else {
			myage = 2;
		}
		// 获取当前用户id
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

	// 获取个人资料
	@RequestMapping(value = "/getMeans.action")
	public ModelAndView getMeans(@RequestParam(value = "owerId", required = true, defaultValue = "0") int owerId) {
		ModelAndView model = new ModelAndView();
		Ower ower = new Ower();
		ower.setOwerId(owerId);
		Ower mess = owerService.owerLogin(ower);
		model.addObject("mess", mess);
		model.setViewName("forward:/frontstage/means.jsp");
		return model;
	}

	// 修改资料
	@RequestMapping(value = "/changeMeans.action", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody String changeMeans(
			@RequestParam(value = "owerId", required = true, defaultValue = "0") int owerId,
			@RequestParam(value = "owerName", required = true, defaultValue = "") String owerName,
			@RequestParam(value = "owerPhone", required = true, defaultValue = "") String owerPhone) {
		Ower ower = new Ower();
		ower.setOwerId(owerId);
		ower.setOwerName(owerName);
		ower.setOwerPhone(owerPhone);
		int result = owerService.changeMeans(ower);
		if (result > 0) {
			return "1";
		} else {
			return "0";
		}
	}

	// 进入我的页面之前，查询用户信息(修改完个人资料需要跳转)
	@RequestMapping(value = "/beforeToMy.action")
	public ModelAndView beforeToMy(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();
		Ower ower = (Ower) session.getAttribute("loginOwer");
		Ower util = owerService.owerLogin(ower);
		model.addObject("ower", util);
		model.setViewName("forward:/frontstage/my.jsp");
		return model;
	}

	// 登录成功后，查询登录用户的车辆及套餐信息
	@RequestMapping(value = "/getOwerCarMess.action")
	public ModelAndView getOwerCarMess(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();
		Ower ower = (Ower) session.getAttribute("loginOwer");
		int owerId = ower.getOwerId();// 获取到登陆用户的id
		List<Car> carList = owerService.carList(owerId);
		List<TranSact> tranList = new ArrayList<>();
		for (Car car : carList) {
			TranSact tran = new TranSact();
			tran = owerService.tranList(car);
			if(tran!=null) {
				tranList.add(tran);
			}
		}
		System.out.println(tranList.size());
		model.addObject("tranList", tranList);
		model.setViewName("forward:/frontstage/user_main.jsp");
		return model;
	}

	// 充值
	@RequestMapping(value = "/addMoney.action", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody String addMoney(
			@RequestParam(value = "owerId", required = true, defaultValue = "0") int owerId,
			@RequestParam(value = "balance", required = true, defaultValue = "0") int balance) {
		int count = owerService.addMoney(owerId, balance);
		if (count > 0) {
			return "1";
		} else {
			return "0";
		}
	}

	// 跳转到我的账户
	@RequestMapping(value = "/toMyCount.action")
	public ModelAndView toMyCount(@RequestParam(value = "owerId", required = true, defaultValue = "0") int owerId) {
		ModelAndView model = new ModelAndView();
		Ower ower = new Ower();
		ower.setOwerId(owerId);
		Ower owers = owerService.owerLogin(ower);
		model.addObject("owers", owers);
		model.setViewName("forward:/frontstage/myaccount.jsp");
		return model;
	}

	// 缴费查询
	@RequestMapping(value = "/searchPayNotes.action")
	public ModelAndView searchPayNotes(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		HttpSession session = request.getSession();
		Ower ower = (Ower) session.getAttribute("loginOwer");
		int owerId = ower.getOwerId();// 获取到登陆用户的id
		List<Car> carList = owerService.carTypeneone(owerId);
		List<Map<String, Object>> notesList = new ArrayList<>();
		for (Car car : carList) {
			Map map = new HashMap();
			map = owerService.searchPayNotes(car);
			notesList.add(map);
		}
		model.addObject("notesList", notesList);
		model.setViewName("forward:/frontstage/mypaynotes.jsp");
		return model;
	}
	//修改密码
	@RequestMapping(value="/changePwd.action",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public @ResponseBody String changePwd(Ower ower) {
		int count=owerService.updatePwd(ower);
		if(count>0) {
			return "1";
		}else {
			return "0";
		}
	}

}
