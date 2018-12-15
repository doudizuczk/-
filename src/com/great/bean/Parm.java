package com.great.bean;

import java.io.Serializable;
//参数实体类，@su_yang
public class Parm implements Serializable{
	public static final  long serialVersionUID=135996860L;
	/*
	 * 参数id
	 * 参数名称
	 * 参数类型
	 * 参数值
	 * 参数父类id
	 * */
	private int parmId;
	private String parmName;
	private String parmType;
	private int parmVal;
	private int parmPid;
	public Parm(int parmId, String parmName, String parmType, int parmVal, int parmPid) {
		super();
		this.parmId = parmId;
		this.parmName = parmName;
		this.parmType = parmType;
		this.parmVal = parmVal;
		this.parmPid = parmPid;
	}
	public Parm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getParmId() {
		return parmId;
	}
	public void setParmId(int parmId) {
		this.parmId = parmId;
	}
	public String getParmName() {
		return parmName;
	}
	public void setParmName(String parmName) {
		this.parmName = parmName;
	}
	public String getParmType() {
		return parmType;
	}
	public void setParmType(String parmType) {
		this.parmType = parmType;
	}
	public int getParmVal() {
		return parmVal;
	}
	public void setParmVal(int parmVal) {
		this.parmVal = parmVal;
	}
	public int getParmPid() {
		return parmPid;
	}
	public void setParmPid(int parmPid) {
		this.parmPid = parmPid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
