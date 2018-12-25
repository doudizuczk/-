package com.great.bean;

//������ŵ��ָ����λ��ó�λ�ĳ�����Ϣ 
public class CarInfo {
	public static final  long serialVersionUID=135996880L;
	private String carId;//���ƺ�
	private String owerName;//�ó��ĳ�������
	private Integer parkId;//��λId
	private String area;//��λ����
	private String xCoord;//�ó�λ��x����
	private String yCoord;//�ó�λ��y����
	private Integer twoId;//�ó�λ��ģ��ID
	private String color;//�ó�λ����ɫ
	private String iDate;//�ó�λ �����ʱ��
	private String picture;//�ó�λ��ͣ��������ͼƬ
	
	
	public CarInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CarInfo(String carId, String owerName, Integer parkId, String area, String xCoord, String yCoord,
			Integer twoId, String color, String iDate, String picture) {
		super();
		this.carId = carId;
		this.owerName = owerName;
		this.parkId = parkId;
		this.area = area;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.twoId = twoId;
		this.color = color;
		this.iDate = iDate;
		this.picture = picture;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getOwerName() {
		return owerName;
	}
	public void setOwerName(String owerName) {
		this.owerName = owerName;
	}
	public Integer getParkId() {
		return parkId;
	}
	public void setParkId(Integer parkId) {
		this.parkId = parkId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getxCoord() {
		return xCoord;
	}
	public void setxCoord(String xCoord) {
		this.xCoord = xCoord;
	}
	public String getyCoord() {
		return yCoord;
	}
	public void setyCoord(String yCoord) {
		this.yCoord = yCoord;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getiDate() {
		return iDate;
	}
	public void setiDate(String iDate) {
		this.iDate = iDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getTwoId() {
		return twoId;
	}
	public void setTwoId(Integer twoId) {
		this.twoId = twoId;
	}

	@Override
	public String toString() {
		return "CarInfo [carId=" + carId + ", owerName=" + owerName + ", parkId=" + parkId + ", area=" + area
				+ ", xCoord=" + xCoord + ", yCoord=" + yCoord + ", twoId=" + twoId + ", color=" + color + ", iDate="
				+ iDate + ", picture=" + picture + "]";
	}
	

}
