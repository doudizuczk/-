package com.great.bean;

import java.io.Serializable;
/*
 * ��־ģ������������@su_yang
 * */
public class SearchUtil implements Serializable {
	public static final  long serialVersionUID=135996868L;
	/*
	 * ҳ��
	 * ��ʼʱ��
	 * ��ֹʱ��
	 * ����Ա����
	 * */
	private int pageNum;
	private String startTime;
	private String finalTime;
	private String adminAccount;
	public SearchUtil(int pageNum, String startTime, String finalTime, String adminAccount) {
		super();
		this.pageNum = pageNum;
		this.startTime = startTime;
		this.finalTime = finalTime;
		this.adminAccount = adminAccount;
	}
	public SearchUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getFinalTime() {
		return finalTime;
	}
	public void setFinalTime(String finalTime) {
		this.finalTime = finalTime;
	}
	public String getAdminAccount() {
		return adminAccount;
	}
	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
