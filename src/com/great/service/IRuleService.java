package com.great.service;


import java.util.List;

import com.great.bean.Rule;

public interface IRuleService {
	/*
	 * 查询收费规则实体对象 @linanping
	 * */
	public List<Rule> queryRule(Rule Rule);
}
