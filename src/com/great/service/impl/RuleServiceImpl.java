package com.great.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.great.bean.Rule;
import com.great.mapper.MenuMapper;
import com.great.mapper.RuleMapper;
import com.great.service.IRuleService;

@Service("ruleServiceImpl")
public class RuleServiceImpl implements IRuleService{
	@Autowired
	private RuleMapper ruleMapper;

	@Override
	public List<Rule> queryRule(Rule rule) {
		// TODO Auto-generated method stub
		return ruleMapper.queryRule(rule);
	}
	
	@Override
	public List<Rule> queryRuleList(Rule rule) {
		// TODO Auto-generated method stub
		return ruleMapper.queryRuleList(rule);
	}

	@Transactional
	@Override
	public boolean enableRule(int sequence) {
		// TODO Auto-generated method stub
		
		//把原来启用的规则禁用
		Rule temp=new Rule();
		temp.setState(1);
		List<Rule> rules=ruleMapper.queryRule(temp);
		if (rules!=null&&rules.size()>0) {
			temp.setSequence(rules.get(0).getSequence());
			temp.setState(2);
			ruleMapper.updateRule(temp);
		}
		
		//新规则启用
		temp.setSequence(sequence);
		temp.setState(1);
		int count=ruleMapper.updateRule(temp);
		
		return count>0;
	}

	@Override
	public boolean deleteRule(int sequence) {
		// TODO Auto-generated method stub
		Rule temp=new Rule();
		temp.setSequence(sequence);
		temp.setState(3);
		int count=ruleMapper.updateRule(temp);
		return count>0;
	}
	
	@Override
	public boolean deleteRule(Rule rule) {
		// TODO Auto-generated method stub
		int count=ruleMapper.deleteRule(rule);
		return count>0;
	}

	@Override
	public List<Map<String, Object>> checkRuleName(Rule rule) {
		// TODO Auto-generated method stub
		return ruleMapper.checkRuleName(rule);
	}

	@Override
	@Transactional
	public boolean addRule(List<Rule> list) {
		// TODO Auto-generated method stub
		boolean flag=true;
		for(Rule i:list) {
			if (ruleMapper.addRule(i)<=0) {
				flag=false;
				break;
			}
		}
		return flag;
	}

	@Override
	public int getRuleSeq() {
		// TODO Auto-generated method stub
		return ruleMapper.getRuleSeq();
	}

	
	


}
