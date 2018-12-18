package com.great.bean;

//³µÎ»±í
public class CarLocation {
	public static final  long serialVersionUID=135996870L;
	private Integer carLocationId;
	private Integer state;
	private String stateName;
	private String area;
	private String cdate;
	
	
	public CarLocation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CarLocation(Integer carLocationId, Integer state, String area, String cdate,String stateName) {
		super();
		this.carLocationId = carLocationId;
		this.state = state;
		this.area = area;
		this.cdate = cdate;
		this.stateName=stateName;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	@Override
	public String toString() {
		return "CarLocation [carLocationId=" + carLocationId + ", state=" + state + ", stateName=" + stateName
				+ ", area=" + area + ", cdate=" + cdate + "]";
	}

	
	

}
