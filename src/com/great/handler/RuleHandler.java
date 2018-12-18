package com.great.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.great.bean.Menu;
import com.great.bean.PageInfo;
import com.great.bean.Parm;
import com.great.bean.Role;
import com.great.bean.Rule;
import com.great.service.IMenuService;
import com.great.service.IParmService;
import com.great.service.IRuleService;
import com.mysql.fabric.xmlrpc.base.Data;

/*
 * 收费规则处理对象 @linanping
 * */
@Controller()
@RequestMapping("/ruleHandler")
public class RuleHandler {
	@Autowired
	@Qualifier("ruleServiceImpl")
	private IRuleService ruleService;

	@Autowired
	private IParmService parmService;

	/*
	 * 查询规则列表
	 */
	@RequestMapping("/ruleList.action")
	public ModelAndView queryRules(HttpServletRequest request, Rule rule,
			@RequestParam(value = "curPage", required = true, defaultValue = "1") int curPage) {
		ModelAndView model = new ModelAndView();
		Page<Object> page = PageHelper.startPage(curPage, 5);// 第curPage页，包含5条
		List<Rule> ruleList = ruleService.queryRuleList(rule);

		List<Parm> stateList = parmService.queryParmByPid(12);

		int totalPage = page.getPages();// 总页数
		int totalNum = (int) page.getTotal();
		Map<String, Object> dates = new HashMap<String, Object>();
		dates.put("ruleList", ruleList);
		dates.put("stateList", stateList);
		PageInfo pageInfo = new PageInfo(curPage, totalPage, totalNum, dates);

		model.addObject("pageInfo", pageInfo);
		model.setViewName("forward:/backstage/rule_list.jsp");
		return model;
	}

	@RequestMapping("/deleteRule.action")
	public ModelAndView deleteRule(HttpServletRequest request, Rule rule, int curPage) {
		ModelAndView model = new ModelAndView();
		ruleService.deleteRule(rule.getSequence());
		model.setViewName("forward:/ruleHandler/ruleList.action");
		return model;
	}

	@RequestMapping("/enableRule.action")
	public ModelAndView enableRule(HttpServletRequest request, Rule rule, int curPage) {
		ModelAndView model = new ModelAndView();
		ruleService.enableRule(rule.getSequence());
		model.setViewName("forward:/ruleHandler/ruleList.action");
		return model;
	}

	@RequestMapping("/checkRuleName.action")
	public @ResponseBody String checkRuleName(HttpServletRequest request, String ruleName) {
		Rule rule = new Rule();
		rule.setRuleName(ruleName);
		List<Map<String, Object>> list = ruleService.checkRuleName(rule);
		if (list == null || list.size() == 0) {
			return "1";
		} else {
			return "0";
		}
	}

	@RequestMapping("/addRule.action")
	public @ResponseBody String addRule(HttpServletRequest request, String ruleName, String ruleDetail) {
		boolean flag = true;
		List<Rule> list = new ArrayList<Rule>();

		String[] detail = ruleDetail.split(";");
		int seq = ruleService.getRuleSeq();

		for (int i = 0; i < detail.length; i++) {
			String[] str = detail[i].split(",");
			Rule rule = new Rule();
			rule.setRuleName(ruleName);
			rule.setSequence(seq);

			rule.setCriticalHours(Integer.valueOf(str[0]));
			rule.setFixCost(Double.valueOf(str[1]));
			rule.setOutCost(Double.valueOf(str[2]));

			list.add(rule);
		}
		if (ruleService.addRule(list)) {
			return "1";
		} else {
			return "0";
		}
	}

	@RequestMapping("/updatingRule.action")
	public ModelAndView updatingRule(HttpServletRequest request, Rule rule) {
		ModelAndView model = new ModelAndView();
		List<Rule> ruleList = ruleService.queryRule(rule);
		model.addObject("ruleList", ruleList);
		model.addObject("ruleSum", ruleList.size());
		model.setViewName("forward:/backstage/updateRule.jsp");
		return model;
	}

	@RequestMapping("/updateRule.action")
	public ModelAndView updateRule(HttpServletRequest request, String ruleName, String ruleDetail,int sequence) {
		ModelAndView model = new ModelAndView();
		// 将之前规则的删掉
		Rule rule=new Rule();
		rule.setSequence(sequence);
		ruleService.deleteRule(rule);
		// 添加新规则
		model.setViewName("forward:/ruleHandler/addRule.action");
		return model;
	}

}
