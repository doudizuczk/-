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
	private Integer ruleId;
	private String ruleName;
	private Integer sequence;
	private Integer criticalHours;
	private Double fixCost;
	private Double outCost;
	private Integer state;
	private String  createTime;
	
	public Rule() {
		super();
	}

	public Rule(Integer ruleId, String ruleName,Integer sequence,Integer criticalHours, Double fixCost, Double outCost, Integer state,
			String createTime) {
		super();
		this.ruleId = ruleId;
		this.ruleName = ruleName;
		this.sequence = sequence;
		this.criticalHours = criticalHours;
		this.fixCost = fixCost;
		this.outCost = outCost;
		this.state = state;
		this.createTime = createTime;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getCriticalHours() {
		return criticalHours;
	}

	public void setCriticalHours(Integer criticalHours) {
		this.criticalHours = criticalHours;
	}

	public Double getFixCost() {
		return fixCost;
	}

	public void setFixCost(Double fixCost) {
		this.fixCost = fixCost;
	}

	public Double getOutCost() {
		return outCost;
	}

	public void setOutCost(Double outCost) {
		this.outCost = outCost;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
