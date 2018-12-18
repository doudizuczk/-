package com.great.bean;

import java.io.Serializable;
/*
 * 套餐办理表实体类，@su_yang
 * */
public class TranSact implements Serializable {
	public static final  long serialVersionUID=135996818L;
	/*
	 * 套餐办理id
	 * 套餐id
	 * 车牌号
	 * 车位id
	 * 套餐生效时间
	 * 套餐过期时间
	 * 状态
	 * */
	private int tranId;
	private int packId;
	private String carId;
	private int parkId;
	private String tranStime;
	private String tranEtime;
	private int tranState;
	public TranSact(int tranId, int packId, String carId, int parkId, String tranStime, String tranEtime,
			int tranState) {
		super();
		this.tranId = tranId;
		this.packId = packId;
		this.carId = carId;
		this.parkId = parkId;
		this.tranStime = tranStime;
		this.tranEtime = tranEtime;
		this.tranState = tranState;
	}
	public TranSact() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTranId() {
		return tranId;
	}
	public void setTranId(int tranId) {
		this.tranId = tranId;
	}
	public int getPackId() {
		return packId;
	}
	public void setPackId(int packId) {
		this.packId = packId;
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
	public String getTranStime() {
		return tranStime;
	}
	public void setTranStime(String tranStime) {
		this.tranStime = tranStime;
	}
	public String getTranEtime() {
		return tranEtime;
	}
	public void setTranEtime(String tranEtime) {
		this.tranEtime = tranEtime;
	}
	public int getTranState() {
		return tranState;
	}
	public void setTranState(int tranState) {
		this.tranState = tranState;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
