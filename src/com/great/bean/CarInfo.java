package com.great.bean;

//用来存放点击指定车位后该车位的车辆信息 
public class CarInfo {
	public static final  long serialVersionUID=135996880L;
	private String carId;//车牌号
	private String owerName;//该车的车主姓名
	private Integer parkId;//车位Id
	private String area;//车位区域
	private String xCoord;//该车位的x坐标
	private String yCoord;//该车位的y坐标
	private Integer twoId;//该车位的模型ID
	private String color;//该车位的颜色
	private String iDate;//该车位 的入库时间
	private String picture;//该车位的停靠车辆的图片
	
	
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
