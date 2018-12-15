package com.great.bean;

import java.io.Serializable;

/*
 * 收费规则实体对象 @linanping
 * */
public class Rule implements Serializable {
	public static final  long serialVersionUID=41569153L;
	/*收费规则id
	 *规则序号
	 *规则名称
	 *临界小时数
	 *超额前固定收费
	 *超额每小时收费
	 *规则状态
	 *创建时间
	 * */
	private int  ruleId;
	private String ruleName;
	private int  sequence;
	private int  criticalHours;
	private double fixCost;
	private double outCost;
	private int state;
	private String stateName;
	private String  createTime;
	private String sTime;
	private String eTime;
	
	public Rule() {
		super();
	}

	public Rule(int  ruleId, String ruleName,int  sequence,int  criticalHours, double fixCost, double outCost, int  state,
			String stateName,String createTime,String sTime,String eTime) {
		super();
		this.ruleId = ruleId;
		this.ruleName = ruleName;
		this.sequence = sequence;
		this.criticalHours = criticalHours;
		this.fixCost = fixCost;
		this.outCost = outCost;
		this.state = state;
		this.stateName = stateName;
		this.createTime = createTime;
	}

	public int  getRuleId() {
		return ruleId;
	}

	public int  getSequence() {
		return sequence;
	}

	public void setSequence(int  sequence) {
		this.sequence = sequence;
	}

	public void setRuleId(int  ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public int  getCriticalHours() {
		return criticalHours;
	}

	public void setCriticalHours(int  criticalHours) {
		this.criticalHours = criticalHours;
	}

	public double getFixCost() {
		return fixCost;
	}

	public void setFixCost(double fixCost) {
		this.fixCost = fixCost;
	}

	public double getOutCost() {
		return outCost;
	}

	public void setOutCost(double outCost) {
		this.outCost = outCost;
	}

	public int  getState() {
		return state;
	}

	public void setState(int  state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getsTime() {
		return sTime;
	}

	public void setsTime(String sTime) {
		this.sTime = sTime;
	}

	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}
	
	
}
