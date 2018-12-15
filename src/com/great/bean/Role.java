package com.great.bean;

import java.io.Serializable;

public class Role implements Serializable {

	/**修改
	 * 角色实体对象 @linanp
	 */
	private static final long serialVersionUID = 1L;
	
	private int roleId;
	private String roleName;
	private int roleState;
	private String roleCdate;
	
	
	
	public Role() {
		super();
	}
	public Role(int roleId, String roleName, int roleState, String roleCdate) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleState = roleState;
		this.roleCdate = roleCdate;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getRoleState() {
		return roleState;
	}
	public void setRoleState(int roleState) {
		this.roleState = roleState;
	}
	public String getRoleCdate() {
		return roleCdate;
	}
	public void setRoleCdate(String roleCdate) {
		this.roleCdate = roleCdate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
