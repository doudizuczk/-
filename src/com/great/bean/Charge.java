package com.great.bean;

import java.io.Serializable;


/*������@linanping
 * �շ�ʵ����
 */
public class Charge implements Serializable {
	private static final long serialVersionUID = 6100005566L;
	/**
	 * �շ�ID
	 * ����ԱID
	 * ����ID
	 * ͣ������
	 * ����
	 * ״̬
	 * ����ʱ��
	 */
	private int chargeId;
	private String carId;
	private int adminId;
	private double cost;
	private int type;
	private String typeName;
	private int state;
	private String stateName;
	private String  createTime;
	
	public Charge() {
		super();
	}

	public Charge(int chargeId, String carId, int adminId, double cost, int type, String typeName, int state,
			String stateName, String createTime) {
		super();
		this.chargeId = chargeId;
		this.carId = carId;
		this.adminId = adminId;
		this.cost = cost;
		this.type = type;
		this.typeName = typeName;
		this.state = state;
		this.stateName = stateName;
		this.createTime = createTime;
	}

	public int getChargeId() {
		return chargeId;
	}

	public void setChargeId(int chargeId) {
		this.chargeId = chargeId;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
}