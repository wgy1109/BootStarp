package com.chanzor.entity;

import java.util.Date;

public class AppBalanceReminder {
	private Integer id;

	private Integer balance;

	private String reminderPhone;

	private Integer spId;

	private Date createTime;

	private Date updateTime;

	private Integer createUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public String getReminderPhone() {
		return reminderPhone;
	}

	public void setReminderPhone(String reminderPhone) {
		this.reminderPhone = reminderPhone == null ? null : reminderPhone.trim();
	}

	public Integer getSpId() {
		return spId;
	}

	public void setSpId(Integer spId) {
		this.spId = spId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

}