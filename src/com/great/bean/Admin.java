package com.great.bean;

import java.io.Serializable;

public class Admin implements Serializable {
	public static final  long serialVersionUID=135996869L;
	private Integer adminId;//管理员id
	private String account;//管理员帐户
	private String password;//密码
	private String name;//姓名
	private Integer roleId;//角色id
	private String roleName;//角色名
	private Integer state;//状态
	private String  createTime;//创建时间
	
	
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
