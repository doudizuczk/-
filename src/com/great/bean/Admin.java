package com.great.bean;

import java.io.Serializable;

public class Admin implements Serializable {
	public static final  long serialVersionUID=135996869L;
	private Integer adminId;//����Աid
	private String account;//����Ա�ʻ�
	private String password;//����
	private String name;//����
	private Integer roleId;//��ɫid
	private String roleName;//��ɫ��
	private Integer state;//״̬
	private String  createTime;//����ʱ��
	
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Admin(Integer adminId, String account, String password, String name, Integer roleId, String roleName,
			Integer state, String createTime) {
		super();
		this.adminId = adminId;
		this.account = account;
		this.password = password;
		this.name = name;
		this.roleId = roleId;
		this.roleName = roleName;
		this.state = state;
		this.createTime = createTime;
	}


	public Integer getAdminId() {
		return adminId;
	}


	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}


	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getRoleId() {
		return roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
}
