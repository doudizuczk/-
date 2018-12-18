package com.great.mapper;

import java.util.List;
import java.util.Map;

import com.great.bean.Rule;

/*
 * 规则映射实体对象 @linanping
 * */
public interface RuleMapper {
	public List<Map<String,Object>> checkRuleName(Rule rule);
	public int addRule(Rule rule);
	public int updateRule(Rule rule);
	public int deleteRule(Rule rule);
	public int getRuleSeq();
	public Rule getCurRule(double criticalHours);
	public List<Rule> queryRule(Rule rule);
	public List<Rule> queryRuleList(Rule rule);
}
