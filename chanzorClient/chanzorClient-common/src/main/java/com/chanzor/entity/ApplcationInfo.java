package com.chanzor.entity;

import java.io.Serializable;
import java.util.Date;

public class ApplcationInfo implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private int app_id;
	private String app_name;// 应用名称
	private int app_type;// 应用类型
	private int app_industry;// 应用行业
	private String app_description;// 应用描述
	private int user_id; // 用户id
	private int status; // 状态 1表示 上线 ,3申请上线 ，0未上线， 11 表示通过 ， 22 表示未通过
	private Date createtime;
	private String signature ;//申请上线是的签名
	private String sp_id;
	
	
	private Page page; 
	
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public int getApp_id() {
		return app_id;
	}
	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public int getApp_type() {
		return app_type;
	}
	public void setApp_type(int app_type) {
		this.app_type = app_type;
	}
	public int getApp_industry() {
		return app_industry;
	}
	public void setApp_industry(int app_industry) {
		this.app_industry = app_industry;
	}
	public String getApp_description() {
		return app_description;
	}
	public void setApp_description(String app_description) {
		this.app_description = app_description;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getSp_id() {
		return sp_id;
	}
	public void setSp_id(String sp_id) {
		this.sp_id = sp_id;
	}

	

}
