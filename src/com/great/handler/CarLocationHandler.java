package com.great.handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.great.bean.CarInfo;
import com.great.bean.CarLocation;
import com.great.bean.ParkCount;
import com.great.service.ICarLocationService;
import com.great.service.impl.CarLocationServiceImpl;

@Controller
@RequestMapping("/carLocation")
public class CarLocationHandler {
	
	@Autowired
	@Qualifier("carLocationServiceImpl")
	private CarLocationServiceImpl carLocationServiceImpl;
	
	//查找全部
	@RequestMapping(value="/queryAll.action")
	public @ResponseBody PageInfo queryAll(CarLocation carLocation) {
		List<CarLocation> list=new ArrayList<>();
		PageHelper.startPage(1, 5);
		list=carLocationServiceImpl.queryAll(carLocation);
		PageInfo p = new PageInfo<>(list);
		return p ;
	}
	//查询禁用车位,跳转到鸟瞰图
	@RequestMapping(value="/queryForbid.action")
	public ModelAndView queryForbid() throws JsonProcessingException{
		ModelAndView mav=new ModelAndView();
		List<CarLocation> list=carLocationServiceImpl.queryForbid();
		ObjectMapper o = new ObjectMapper();
		mav.addObject("forbidList", o.writeValueAsString(list));
		mav.setViewName("forward:/backstage/airscape.jsp");
		return mav;
		
	}
	//查询禁用车位,跳转到实时车位查看
	@RequestMapping(value="/queryParkView.action")
	public ModelAndView queryParkView() throws JsonProcessingException{
		ModelAndView mav=new ModelAndView();
		List<CarLocation> list=carLocationServiceImpl.queryForbid();
		ObjectMapper o = new ObjectMapper();
		mav.addObject("forbidList", o.writeValueAsString(list));
		mav.setViewName("forward:/backstage/parkingview.jsp");
		return mav;
		
	}
	//禁用
	@RequestMapping(value="/forbidden.action")
	public @ResponseBody String forbidden(int carLocationId) {
		System.out.println(carLocationServiceImpl.forbidden(carLocationId));
		if(carLocationServiceImpl.forbidden(carLocationId)>0) {
			return "1";
		}else {
			return "0";
		}
	}
	//启用
	@RequestMapping(value="/permission.action")
	public @ResponseBody String permission(int carLocationId) {
		if(carLocationServiceImpl.permission(carLocationId)>0) {
			return "1";
		}else {
			return "0";
		}
	}
	//起始进入jsp
	@RequestMapping(value="/tojsp.action")//tojsp
	public ModelAndView tojsp() {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("backstage/carlocationmange");
		return andView;
	} 
	//分页查询
	@RequestMapping(value="/queryPage.action")
	public  @ResponseBody List<CarLocation> queryPage(Integer nowPage) {
		Page<Object> page=PageHelper.startPage(nowPage, 5);
		CarLocation carLocation=new CarLocation();
		List<CarLocation> list=carLocationServiceImpl.queryAll(carLocation);
		return list;
	} 
	//按车牌查找该车位车辆信息
	@RequestMapping(value="/queryCarInfo.action")
	public @ResponseBody List<CarInfo> queryCarInfo(String carId){
		List<CarInfo> list=carLocationServiceImpl.queryCarInfo(carId);
		return list;
	}
	//进入反向寻车页面
	@RequestMapping(value="/toSearchCar.action")
	public @ResponseBody ModelAndView toSearchCar(){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("frontstage/searchcar");
		return mav;
	}
	
	//进入导航页面
	@RequestMapping(value="/startNaviga.action")
	public @ResponseBody ModelAndView startNav(String xCoord,String yCoord,Integer twoId) throws JsonProcessingException {
		ModelAndView mav=new ModelAndView();
		ObjectMapper o = new ObjectMapper();
		mav.addObject("xCoord", o.writeValueAsString(xCoord));
		mav.addObject("yCoord", o.writeValueAsString(yCoord));
		mav.addObject("twoId", o.writeValueAsString(twoId));
		mav.setViewName("forward:/frontstage/startnav.jsp");
		return mav;
	}
	//查找总区，以及各个区域的总车位，空闲车位和已用车位
	@RequestMapping(value="/queryAreaNum.action")
	public ModelAndView queryAreaNum() {
		ModelAndView mav=new ModelAndView();
		List<Map<String, Object>> list=carLocationServiceImpl.queryByArea();
		Map<String, Object> statis=carLocationServiceImpl.statisAll();
		mav.addObject("list", list);
		mav.addObject("all",statis);
		mav.setViewName("forward:/backstage/carlocationcheck.jsp");
		return mav; 
		
	}
	//根据房间模型查取该车位的停靠车辆详细信息
	@RequestMapping(value="/getDetil.action")
	public @ResponseBody List<CarInfo> getDetil(Integer modelId){
		List<CarInfo> list=carLocationServiceImpl.getDetil(modelId);
		return list;
	}
	//上传文件
	@RequestMapping(value="/sendImg.action") 
	public @ResponseBody int sendImg(
		@RequestParam(value = "carId",required=true) String carId,
		MultipartFile img,
		HttpServletRequest request) throws IllegalStateException, IOException {
		System.out.println(carId);
		String name = UUID.randomUUID().toString();
		String mime = img.getOriginalFilename().substring(img.getOriginalFilename().indexOf('.'), img.getOriginalFilename().length());
		String dbName = name+mime;
		System.out.println(dbName);
		int count=carLocationServiceImpl.updateLink(dbName, carId);
		if(count!=0) {
			File file = new File(request.getRealPath("/images/"+dbName));
			img.transferTo(file);
			FileUtil.copyFile(file, new File("F:\\"+dbName));
		}
		return count;
	}
	//车位统计折线图数据传输
	@RequestMapping(value="/sendData.action") 
	public ModelAndView sendData(){
		ModelAndView mav=new ModelAndView();
		List<Integer> listAll=carLocationServiceImpl.queryAllCount();
		List<Integer> listFree=carLocationServiceImpl.queryFreeCount();
		List<Integer> listUsed=carLocationServiceImpl.queryUsedCount();
		mav.addObject("listAll", listAll);
		mav.addObject("listFree", listFree);
		mav.addObject("listUsed", listUsed);
		mav.setViewName("forward:/backstage/carlocationcount.jsp");
		return mav;
	}
}
