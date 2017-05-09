package com.chanzor.entity;

import java.io.Serializable;

public class UserSubSp implements Serializable{

	private static final long serialVersionUID = -4021420130497573195L;

	private Integer id;
	private Integer parentAccountId;
	private Integer subAccountId;
	private Integer spId;
	private String spAccountName;
	
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
	public Integer getSpId() {
		return spId;
	}
	public void setSpId(Integer spId) {
		this.spId = spId;
	}
	public String getSpAccountName() {
		return spAccountName;
	}
	public void setSpAccountName(String spAccountName) {
		this.spAccountName = spAccountName;
	}
	
}
