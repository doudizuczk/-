package com.great.bean;

import java.io.Serializable;

/**
 *车主实体对象czk--!
 *@author 
 */
public class Ower implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	 * 车主id
	 * 车主姓名
	 * 车主账号
	 * 车主密码
	 * 车主电话
	 * 车主性别
	 * 车主身份证号
	 * 车主年龄
	 * 账号状态
	 * 注册时间
	 * 余额
	 */
	private int owerId;
	private String owerName;
	private String owerAccount;
	private String owerPwd;
	private String owerPhone; 
	private int owerSex;
	private String owerIdcard;
	private int owerAge;
	private int owerState;
	private String owerCdate;
	private int balance;
	private String checkCode;
	
	@Override
	public String toString() {
		return "Ower [owerId=" + owerId + ", owerName=" + owerName + ", owerAccount=" + owerAccount + ", owerPwd="
				+ owerPwd + ", owerPhone=" + owerPhone + ", owerSex=" + owerSex + ", owerIdcard=" + owerIdcard
				+ ", owerAge=" + owerAge + ", owerState=" + owerState + ", owerCdate=" + owerCdate + "]";
	}


	public Ower() {
		super();
	}


	public Ower(int owerId, String owerName, String owerAccount, String owerPwd, String owerPhone, int owerSex,
			String owerIdcard, int owerAge, int owerState, String owerCdate, int balance,String checkCode) {
		super();
		this.owerId = owerId;
		this.owerName = owerName;
		this.owerAccount = owerAccount;
		this.owerPwd = owerPwd;
		this.owerPhone = owerPhone;
		this.owerSex = owerSex;
		this.owerIdcard = owerIdcard;
		this.owerAge = owerAge;
		this.owerState = owerState;
		this.owerCdate = owerCdate;
		this.balance = balance;
		this.checkCode=checkCode;
	}


	public String getCheckCode() {
		return checkCode;
	}


	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}


	public int getBalance() {
		return balance;
	}


	public void setBalance(int balance) {
		this.balance = balance;
	}


	public int getOwerId() {
		return owerId;
	}
	
	public String getOwerAccount() {
		return owerAccount;
	}
	public void setOwerAccount(String owerAccount) {
		this.owerAccount = owerAccount;
	}
	public void setOwerId(int owerId) {
		this.owerId = owerId;
	}
	public String getOwerName() {
		return owerName;
	}
	public void setOwerName(String owerName) {
		this.owerName = owerName;
	}
	public String getOwerPwd() {
		return owerPwd;
	}
	public void setOwerPwd(String owerPwd) {
		this.owerPwd = owerPwd;
	}
	public String getOwerPhone() {
		return owerPhone;
	}
	public void setOwerPhone(String owerPhone) {
		this.owerPhone = owerPhone;
	}
	public int getOwerSex() {
		return owerSex;
	}
	public void setOwerSex(int owerSex) {
		this.owerSex = owerSex;
	}
	public String getOwerIdcard() {
		return owerIdcard;
	}
	public void setOwerIdcard(String owerIdcard) {
		this.owerIdcard = owerIdcard;
	}
	public int getOwerAge() {
		return owerAge;
	}
	public void setOwerAge(int owerAge) {
		this.owerAge = owerAge;
	}
	public int getOwerState() {
		return owerState;
	}
	public void setOwerState(int owerState) {
		this.owerState = owerState;
	}
	public String getOwerCdate() {
		return owerCdate;
	}
	public void setOwerCdate(String owerCdate) {
		this.owerCdate = owerCdate;
	}

	
	
}
