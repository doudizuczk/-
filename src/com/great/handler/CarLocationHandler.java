package com.great.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.great.bean.CarLocation;
import com.great.service.ICarLocationService;
import com.great.service.impl.CarLocationServiceImpl;

@Controller
@RequestMapping("/carLocation")
public class CarLocationHandler {
	
	@Autowired
	@Qualifier("carLocationServiceImpl")
	private CarLocationServiceImpl carLocationServiceImpl;
	
	@RequestMapping(value="/queryAll.action")
	public @ResponseBody PageInfo queryAll(CarLocation carLocation) {
		List<CarLocation> list=new ArrayList<>();
		PageHelper.startPage(1, 5);
		list=carLocationServiceImpl.queryAll(carLocation);
		PageInfo p = new PageInfo<>(list);
		return p ;
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
	//获取总页数
	@RequestMapping(value="/queryAllPage.action",method = RequestMethod.POST)
	public @ResponseBody Integer queryAllPage() {
		Page<Object> page=PageHelper.startPage(1, 5);
		Integer allPage=page.getPages();
		CarLocation carLocation=new CarLocation();
		List<CarLocation> list=carLocationServiceImpl.queryAll(carLocation);
		return allPage ;
	}
	//模糊查找
	@RequestMapping(value="/queryByCondition.action",method = RequestMethod.POST)
	public @ResponseBody PageInfo queryByCondition(CarLocation carLocation) {
		List<CarLocation> list=new ArrayList<>();
		PageHelper.startPage(1, 5);
		list=carLocationServiceImpl.queryAll(carLocation);
		PageInfo p = new PageInfo<>(list);
		return p ;
	}
}
