package com.great.bean;

import java.io.Serializable;
import java.util.Date;
/*
 * 日志对像实体
 * */
public class Log implements Serializable {
	public static final  long serialVersionUID=135996865L;
	/*
	 * 日志id
	 * 管理员id
	 * 日志记录时间
	 * 日志信息
	 * */
	private int logId;
	private int adminId;
	private Date logTime;
	private String logInfo;
	public Log(int logId,int adminId, Date logTime, String logInfo) {
		super();
		this.logId = logId;
		this.adminId = adminId;
		this.logTime = logTime;
		this.logInfo = logInfo;
	}
	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public String getLogInfo() {
		return logInfo;
	}
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	
	public int getadminId() {
		return adminId;
	}
	public void setadminId(int adminId) {
		this.adminId = adminId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
