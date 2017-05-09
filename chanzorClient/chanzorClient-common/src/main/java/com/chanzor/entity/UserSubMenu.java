package com.chanzor.entity;

import java.io.Serializable;

public class UserSubMenu implements Serializable {

	private static final long serialVersionUID = 8504699866803962722L;
    
	private Integer id;
	private Integer parentAccountId;
	private Integer subAccountId;
	private String menuCode;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentAccountId() {
		return parentAccountId;
	}
	public void setParentAccountId(Integer parentAccountId) {
		this.parentAccountId = parentAccountId;
	}
	public Integer getSubAccountId() {
		return subAccountId;
	}
	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	
	
	
	
}
