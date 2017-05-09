package com.chanzor.entity;

import java.util.Date;

public class SmsAccountChargeRecord {
	private Integer id;

	private double chargeNum;

	private Short chargeType;

	private Integer chargeSpId;

	private Date chargeDate;

	private Short chargeUser;

	private String spName;

	private Short chargeStatus;

	private String orderNum;

	private Integer accountBalance;
	
	private Integer isbeginrecharge;

	
	
	public Integer getIsbeginrecharge() {
		return isbeginrecharge;
	}

	public void setIsbeginrecharge(Integer isbeginrecharge) {
		this.isbeginrecharge = isbeginrecharge;
	}

	public Integer getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Integer accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Short getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(Short chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getChargeNum() {
		return chargeNum;
	}

	public void setChargeNum(double chargeNum) {
		this.chargeNum = chargeNum;
	}

	public Short getChargeType() {
		return chargeType;
	}

	public void setChargeType(Short chargeType) {
		this.chargeType = chargeType;
	}

	public Integer getChargeSpId() {
		return chargeSpId;
	}

	public void setChargeSpId(Integer chargeSpId) {
		this.chargeSpId = chargeSpId;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public Short getChargeUser() {
		return chargeUser;
	}

	public void setChargeUser(Short chargeUser) {
		this.chargeUser = chargeUser;
	}
}