package com.great.service;


import java.util.List;
import java.util.Map;

import com.great.bean.Rule;

public interface IRuleService {
	/*
	 * 查询收费规则实体对象 @linanping
	 * */
	public List<Map<String, Object>> checkRuleName(Rule rule);
	public boolean addRule(List<Rule> list);
	public int getRuleSeq();
	public boolean enableRule(int sequence);
	public boolean deleteRule(int sequence);
	public boolean deleteRule(Rule rule);
	public List<Rule> queryRule(Rule Rule);
	public List<Rule> queryRuleList(Rule rule);
}
