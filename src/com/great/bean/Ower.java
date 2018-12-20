package com.great.bean;

import java.io.Serializable;

public class Ower implements Serializable {
	/**
	 * czk
	 */
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
	private int owerBalance;
	
	
	public Ower() {
		super();
	}

	
	public Ower(int owerId, String owerName, String owerAccount, String owerPwd, String owerPhone, int owerSex,
			String owerIdcard, int owerAge, int owerState, String owerCdate,int owerBalance) {
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
		this.owerBalance=owerBalance;
	}
	

	public int getOwerBalance() {
		return owerBalance;
	}


	public void setOwerBalance(int owerBalance) {
		this.owerBalance = owerBalance;
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
