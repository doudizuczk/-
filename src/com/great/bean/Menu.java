package com.great.bean;

import java.io.Serializable;
/*
 * 菜单实体对象 @su_yang
 * */
public class Menu implements Serializable{
	public static final  long serialVersionUID=135996868L;
	/*
	 * 菜单id
	 * 菜单名称
	 * 菜单链接
	 * 菜单父类id
	 * 菜单序号
	 * */
	private Integer menuId;
	private String menuName;
	private String menuUrl;
	private Integer menuPid;
	private Integer menuSeq;
	public Menu() {
		
	}
	public Menu(Integer menuId, String menuName, String menuUrl, Integer menuPid, Integer menuSeq) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuUrl = menuUrl;
		this.menuPid = menuPid;
		this.menuSeq = menuSeq;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public Integer getMenuPid() {
		return menuPid;
	}
	public void setMenuFid(Integer menuPid) {
		this.menuPid = menuPid;
	}
	public Integer getMenuSeq() {
		return menuSeq;
	}
	public void setMenuSeq(Integer menuSeq) {
		this.menuSeq = menuSeq;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
