package com.great.bean;

import java.io.Serializable;


/*创建人@lian shengwei
 * 创建日期：2018-12-13
 * 车辆实体类Car
 */
public class Car implements Serializable {

	private static final long serialVersionUID = 15666778890008L;
	/**
	 * 车辆编号（车牌号）：carId
	 * 车辆颜色：carColor
	 * 车辆状态：carState
	 * 车辆类型：carType
	 * 创建时间：carCdate
	 * 用户id：owerId
	 */
	private String carId;
	private String carColor;
	private Integer carState;
	private Integer carType;
	private String carCdate;
	private Integer owerId;
	
	public Car() {
		super();
	}
	
	public Car(String carId, String carColor, Integer carState, Integer carType, String carCdate, Integer owerId) {
		super();
		this.carId = carId;
		this.carColor = carColor;
		this.carState = carState;
		this.carType = carType;
		this.carCdate = carCdate;
		this.owerId = owerId;
	}

	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	public Integer getCarState() {
		return carState;
	}
	public void setCarState(Integer carState) {
		this.carState = carState;
	}
	public Integer getCarType() {
		return carType;
	}
	public void setCarType(Integer carType) {
		this.carType = carType;
	}
	public String getCarCdate() {
		return carCdate;
	}
	public void setCarCdate(String carCdate) {
		this.carCdate = carCdate;
	}
	public Integer getOwerId() {
		return owerId;
	}
	public void setOwerId(Integer owerId) {
		this.owerId = owerId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	



}
