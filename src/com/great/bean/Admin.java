package com.great.bean;

import java.io.Serializable;
/*
 * ��̨�û�ʵ����� @linanp
 * */
public class Admin implements Serializable {
	public static final  long serialVersionUID=135996869L;
	/*
	 * ����Աid
	 * ����Ա�ʻ�
	 * ����
	 * ����
	 * ��ɫid
	 * ��ɫ��
	 * ״̬
	 * ����ʱ��
	 * */
	private Integer adminId;
	private String account;
	private String password;
	private String name;
	private Integer roleId;
	private String roleName;
	private Integer state;
	private String  createTime;
	
	
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
