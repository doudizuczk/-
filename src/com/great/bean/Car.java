package com.great.bean;

import java.io.Serializable;

/*������@lian shengwei
 * �������ڣ�2018-12-13
 * ����ʵ����Car
 */
public class Car implements Serializable {

	private static final long serialVersionUID = 15666778890008L;
	/**
	 * ������ţ����ƺţ���carId ������ɫ��carColor ����״̬��carState �������ͣ�carType ����ʱ�䣺carCdate
	 * �û�id��owerId
	 */
	private String carId;
	private String carColor;
	private Integer carState;
	private Integer carType;
	private String carTypeName;
	private String carCdate;
	private Integer owerId;
	private Ower ower;
	private Dock dock;

	public Car() {
		super();
	}

	public Car(String carId, String carColor, Integer carState, Integer carType, String carTypeName, String carCdate,
			Integer owerId, Ower ower, Dock dock) {
		super();
		this.carId = carId;
		this.carColor = carColor;
		this.carState = carState;
		this.carType = carType;
		this.carTypeName = carTypeName;
		this.carCdate = carCdate;
		this.owerId = owerId;
		this.ower = ower;
		this.dock = dock;
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

	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
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

	public Ower getOwer() {
		return ower;
	}

	public void setOwer(Ower ower) {
		this.ower = ower;
	}

	public Dock getDock() {
		return dock;
	}

	public void setDock(Dock dock) {
		this.dock = dock;
	}

}
