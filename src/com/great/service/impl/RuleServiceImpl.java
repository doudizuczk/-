package com.great.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.great.bean.Rule;
import com.great.mapper.MenuMapper;
import com.great.mapper.RuleMapper;
import com.great.service.IRuleService;

@Service("RuleServiceImpl")
public class RuleServiceImpl implements IRuleService{
	@Autowired
	private RuleMapper ruleMapper;

	@Override
	public List<Rule> queryRule(Rule rule) {
		// TODO Auto-generated method stub
		return ruleMapper.queryRule(rule);
	}

	
	
}
