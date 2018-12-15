package com.great.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Parm;
import com.great.service.IParmService;

@Controller
@RequestMapping("/parm")
public class ParmHandler {
	@Autowired
	@Qualifier("parmServiceImpl")
	private IParmService parmService;
	//获取参数列表
	@RequestMapping(value="/parmList.action")
	public ModelAndView parmList() {
		ModelAndView mav=new ModelAndView();
		Integer pages=1;//页数
		Page<Object> page=PageHelper.startPage(pages, 5);
		List<Map<String,Object>> parmList=parmService.queryAllParm();
		mav.addObject("parmList", parmList);
		Integer nowNum=page.getPageNum();//当前页数
		Integer allNum=page.getPages();//总页数
		mav.addObject("pageNum", nowNum);
		mav.addObject("allNum", allNum);
		mav.setViewName("forward:/backstage/parm.jsp");
		
		return mav;
	}
	//翻页请求
	@RequestMapping(value="/pageParmList.action",method = RequestMethod.GET)
	public ModelAndView pageMenuList(HttpServletRequest request,Integer pageNum) {
		ModelAndView model=new ModelAndView();
		Page<Object> page=PageHelper.startPage(pageNum, 5);
		List<Map<String,Object>> parmList=parmService.queryAllParm();
		Integer nowNum=page.getPageNum();//当前页数
		Integer allNum=page.getPages();//总页数
		model.addObject("parmList",parmList);
		model.addObject("pageNum", nowNum);
		model.addObject("allNum", allNum);
		model.setViewName("forward:/backstage/parm.jsp");
		return model;
	}
	//修改之前查询参数信息
	@RequestMapping(value="/beforeChange.action",method = RequestMethod.GET)
	public ModelAndView beforeChange(int parmId) {
		ModelAndView mav=new ModelAndView();
		Parm parm=parmService.changeParm(parmId);//查询参数对象
		List<Map<String,Object>> parmTypeList=parmService.queryParmType();//查询父类参数列表
		mav.addObject("parm", parm);
		mav.addObject("parmTypeList", parmTypeList);
		mav.setViewName("forward:/backstage/changeparm.jsp");
		return mav;
	}
	//提交保存修改信息
	@RequestMapping(value="/saveChanges.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String saveChanges(@RequestBody Parm parm) {
		int parmPid=parm.getParmPid();
		int parmId=parm.getParmId()+12;
		Parm theparm=parmService.searchType(parmPid);//根据父类菜单的id查询出名称，作为参数类型字段存入数据库
		parm.setParmType(theparm.getParmName());//传入数据库对象的参数名称
		parm.setParmId(parmId);//传入数据库对象的参数id
		int result=parmService.savechange(parm);
		if(result>0) {
			return "1";
		}else {
			return "0";
		}
	}
	//获取父级参数
	@RequestMapping(value="/getParmPname.action")
	public ModelAndView getParmPname() {
		ModelAndView mav=new ModelAndView();
		List<Map<String,Object>> parmTypeList=parmService.queryParmType();//查询父类参数列表
		mav.addObject("parmTypeList", parmTypeList);
		mav.setViewName("forward:/backstage/createparm.jsp");
		return mav;
	}
	//新增参数
	@RequestMapping(value="/createParm.action",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public @ResponseBody String createParm(@RequestBody Parm parm) {
		//int result=parmService.createParm(parm);
		return "1";
	}
}
