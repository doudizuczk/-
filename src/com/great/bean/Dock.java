package com.great.bean;

import java.io.Serializable;

/*
 * ͣ��ʵ����� @linanping
 * */
public class Dock implements Serializable {
	/*����ID
	 *��λID
	 *�볡ʱ��
	 *����ʱ��
	 *״̬
	 *ͣ����
	 * */
	public static final  long serialVersionUID=5154003L;
	private String carId;
	private int carLocationId;
	private String startTime;
	private String endTime;
	private int state;
	private String stateName;
	private double cost; 
	
	public Dock() {
		super();
	}

	public Dock(String carId, int carLocationId, String startTime, String endTime, int state, String stateName,double cost) {
		super();
		this.carId = carId;
		this.carLocationId = carLocationId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
		this.stateName = stateName;
		this.cost = cost;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public int getCarLocationId() {
		return carLocationId;
	}

	public void setCarLocationId(int carLocationId) {
		this.carLocationId = carLocationId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	

}
