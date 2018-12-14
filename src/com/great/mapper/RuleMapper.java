package com.great.mapper;

import java.util.List;

import com.great.bean.Rule;

/*
 * 规则映射实体对象 @su_yang
 * */
public interface RuleMapper {
	public List<Rule> queryRule(Rule rule);
}
