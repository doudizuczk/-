package com.great.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Menu;
import com.great.bean.PageInfo;
import com.great.bean.Rule;
import com.great.service.IMenuService;
import com.great.service.IRuleService;

/*
 * 收费规则处理对象 @su_yang
 * */
@Controller()
@RequestMapping("/ruleHandler")
public class RuleHandler {
	@Autowired
	@Qualifier("RuleServiceImpl")
	private IRuleService ruleService;
	
	
	@RequestMapping("/ruleList.action")
	public ModelAndView queryRules(HttpServletRequest request,Rule rule
			,@RequestParam(value = "curPage", required = true, defaultValue = "1")int curPage) {
		ModelAndView model=new ModelAndView();
		Page<Object> page=PageHelper.startPage(curPage, 5);
		List<Rule> ruleList=ruleService.queryRule(rule);
		
		int curePage=page.getPageNum();//当前页数
		int totalPage=page.getPages();//总页数
		int totalNum=(int) page.getTotal();
		Map<String, Object> dates=new HashMap<String, Object>();
		dates.put("ruleList", ruleList);
		PageInfo pageInfo=new PageInfo(curePage, totalPage, totalNum,dates);
		
		
		model.addObject("pageInfo",pageInfo);
		model.setViewName("forward:/backstage/rule_list.jsp");
		return model;
	}
}
