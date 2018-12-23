package com.great.bean;

import java.io.Serializable;


/*创建人@linanping
 * 收费实体类
 */
public class Charge implements Serializable {
	private static final long serialVersionUID = 6100005566L;
	/**
	 * 收费ID
	 * 管理员ID
	 * 车牌ID
	 * 停车费用
	 * 是否开具发票
	 * 类型
	 * 状态
	 * 创建时间
	 */
	private int chargeId;
	private String carId;
	private int adminId;
	private double cost;
	private int invoice;
	private int type;
	private String typeName;
	private int state;
	private String stateName;
	private String  createTime;
	private String sTime;//临时参数，查询用
	private String eTime;//临时参数，查询用
	
	public Charge() {
		super();
	}

	public Charge(int chargeId, String carId, int adminId, double cost, int invoice,int type, String typeName, int state,
			String stateName, String createTime,String sTime,String eTime) {
		super();
		this.chargeId = chargeId;
		this.carId = carId;
		this.adminId = adminId;
		this.cost = cost;
		this.invoice = invoice;
		this.type = type;
		this.typeName = typeName;
		this.state = state;
		this.stateName = stateName;
		this.createTime = createTime;
		this.sTime = sTime;
		this.eTime = eTime;
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

	public int getInvoice() {
		return invoice;
	}

	public void setInvoice(int invoice) {
		this.invoice = invoice;
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

	public String getsTime() {
		return sTime;
	}

	public void setsTime(String sTime) {
		this.sTime = sTime;
	}

	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}
	
}
