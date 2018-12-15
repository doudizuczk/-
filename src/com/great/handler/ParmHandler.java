package com.great.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.great.service.IParmService;

@Controller
@RequestMapping("/parm")
public class ParmHandler {
	@Autowired
	@Qualifier("parmServiceImpl")
	private IParmService parmService;
	@RequestMapping(value="/parmList.action")
	public ModelAndView parmList() {
		ModelAndView mav=new ModelAndView();
		List<Map<String,Object>> parmList=parmService.queryAllParm();
		mav.addObject("parmList", parmList);
		mav.setViewName("forward://");
		
		return mav;
	}
}
