package com.great.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.great.service.IPayMentService;
import com.great.util.DateUtils;

import freemarker.template.utility.DateUtil;

@Controller//渠道统计类
@RequestMapping("/payMentHandler")
public class PayMentHandler {
	@Autowired
	@Qualifier("payMentServiceImpl")
	private IPayMentService payMentService;
	
	@RequestMapping("/queryWeekCount.action")
	public ModelAndView queryWeekCount() {
		ModelAndView model=new ModelAndView();
		SimpleDateFormat simpl=new SimpleDateFormat("yyyyMM");
		DateUtils date=new DateUtils();
		String time=simpl.format(date.getBeginDayOfWeek());
		Map map=new HashMap();
		map.put("time", time);
		List<Map<String,Object>> list=payMentService.queryWeekCount();
		List<Map<String,Object>> list2=payMentService.queryWeekPayCount();
		List<Map<String,Object>> list3=payMentService.queryWeekMouthPay();
		List<Map<String,Object>> list4=payMentService.queryMouthCountOne(map);
		List<Map<String,Object>> list5=payMentService.queryMouthCountTwo(map);
		List<Map<String,Object>> list6=payMentService.queryMouthCountThree(map);
		List<Map<String,Object>> list7=payMentService.querySixMouthOne();
		List<Map<String,Object>> list8=payMentService.querySixMouthTwo();
		List<Map<String,Object>> list9=payMentService.querySixMouthThree();
		model.addObject("list", list);
		model.addObject("list2", list2);
		model.addObject("list3", list3);
		model.addObject("list4", list4);
		model.addObject("list5", list5);
		model.addObject("list6", list6);
		model.addObject("list7", list7);
		model.addObject("list8", list8);
		model.addObject("list9", list9);
		model.setViewName("forward:/backstage/count.jsp");
		return model;
	}
	@RequestMapping("/queryMouthCount.action")
	public ModelAndView queryMouthCount() {
		ModelAndView model=new ModelAndView();
		
		return model;
	}
}
