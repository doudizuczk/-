package com.great.bean;

public class ParkCount {
	public static final  long serialVersionUID=135996885L;
	private Integer all;
	private Integer free;
	private Integer used;
	
	
	public ParkCount() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ParkCount(Integer all, Integer free, Integer used) {
		super();
		this.all = all;
		this.free = free;
		this.used = used;
	}
	public Integer getAll() {
		return all;
	}
	public void setAll(Integer all) {
		this.all = all;
	}
	public Integer getFree() {
		return free;
	}
	public void setFree(Integer free) {
		this.free = free;
	}
	public Integer getUsed() {
		return used;
	}
	public void setUsed(Integer used) {
		this.used = used;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ParkCount [all=" + all + ", free=" + free + ", used=" + used + "]";
	}
	
	

}
