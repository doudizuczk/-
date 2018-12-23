package com.great.bean;

//��λ��
public class CarLocation {
	public static final  long serialVersionUID=135996870L;
	private Integer carLocationId;//��λID
	private Integer state;//��λ״̬
	private String stateName;//״̬����
	private String area;//������
	private String cdate;//��������
	private Integer twoId;//2d��λ����Ӧ��ģ��ID
	private Integer threeId;//3d��λ����Ӧ��ģ��ID
	private String parkX;//��λ�ڵ�ͼģ���е�x����
	private String parkY;//��λ�ڵ�ͼģ���е�y����
	
	
	public CarLocation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CarLocation(Integer carLocationId, Integer state, String stateName, String area, String cdate, Integer twoId,
			Integer threeId, String parkX, String parkY) {
		super();
		this.carLocationId = carLocationId;
		this.state = state;
		this.stateName = stateName;
		this.area = area;
		this.cdate = cdate;
		this.twoId = twoId;
		this.threeId = threeId;
		this.parkX = parkX;
		this.parkY = parkY;
	}
	public Integer getCarLocationId() {
		return carLocationId;
	}
	public void setCarLocationId(Integer carLocationId) {
		this.carLocationId = carLocationId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public Integer getTwoId() {
		return twoId;
	}
	public void setTwoId(Integer twoId) {
		this.twoId = twoId;
	}
	public Integer getThreeId() {
		return threeId;
	}
	public void setThreeId(Integer threeId) {
		this.threeId = threeId;
	}
	public String getParkX() {
		return parkX;
	}
	public void setParkX(String parkX) {
		this.parkX = parkX;
	}
	public String getParkY() {
		return parkY;
	}
	public void setParkY(String parkY) {
		this.parkY = parkY;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CarLocation [carLocationId=" + carLocationId + ", state=" + state + ", stateName=" + stateName
				+ ", area=" + area + ", cdate=" + cdate + ", twoId=" + twoId + ", threeId=" + threeId + ", parkX="
				+ parkX + ", parkY=" + parkY + "]";
	}
	
	
	
	
	

	
	

}
