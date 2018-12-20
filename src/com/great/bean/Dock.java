package com.great.bean;

import java.io.Serializable;
/*创建人：@lian shengwei
 * 日期：20181220
 * 停靠表Bean
 * */
public class Dock implements Serializable{
	private static final long serialVersionUID = 1162583851596220855L;
	private String carId;
	private int parkId;
	private String dockSTime;
	private String dockETime;
	private int dockState;
	public Dock() {
		super();
	}
	public Dock(String carId, int parkId, String dockSTime, String dockETime, int dockState) {
		super();
		this.carId = carId;
		this.parkId = parkId;
		this.dockSTime = dockSTime;
		this.dockETime = dockETime;
		this.dockState = dockState;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public String getDockSTime() {
		return dockSTime;
	}
	public void setDockSTime(String dockSTime) {
		this.dockSTime = dockSTime;
	}
	public String getDockETime() {
		return dockETime;
	}
	public void setDockETime(String dockETime) {
		this.dockETime = dockETime;
	}
	public int getDockState() {
		return dockState;
	}
	public void setDockState(int dockState) {
		this.dockState = dockState;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
