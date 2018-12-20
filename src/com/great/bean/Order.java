package com.great.bean;

import java.io.Serializable;
/*
 * 结算单实体对象 @linanping
 * */
public class Order implements Serializable {
	public static final  long serialVersionUID=781230006L;
	/* 结算单id
	 * 总金额
	 * 现金
	 * 交易数量
	 * 发票金额
	 * 发票数量
	 * 班次
	 * 创建时间
	 * */
	private int orderId;
	private double amount;
	private double cash;
	private int count;
	private double invAmount;
	private int invCount;
	private int shift;
	private String  createTime;
	
	public Order() {
		super();
	}

	public Order(int orderId, double amount, double cash, int count, double invAmount, int invCount, int shift,
			String createTime) {
		super();
		this.orderId = orderId;
		this.amount = amount;
		this.cash = cash;
		this.count = count;
		this.invAmount = invAmount;
		this.invCount = invCount;
		this.shift = shift;
		this.createTime = createTime;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getInvAmount() {
		return invAmount;
	}

	public void setInvAmount(double invAmount) {
		this.invAmount = invAmount;
	}

	public int getInvCount() {
		return invCount;
	}

	public void setInvCount(int invCount) {
		this.invCount = invCount;
	}

	public int getShift() {
		return shift;
	}

	public void setShift(int shift) {
		this.shift = shift;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
