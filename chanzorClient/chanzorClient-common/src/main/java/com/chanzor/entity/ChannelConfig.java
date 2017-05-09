package com.chanzor.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* <p>Title: ChannelConfig.java</p>
* <p>Description: 通道配置模型 </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: langyu</p> 
* @author jian.zhang
* @date 2014年6月7日
* @version 1.0
 */

public class ChannelConfig implements Serializable {
	private static final long serialVersionUID = -4445498141123689342L;
	
	private int id;
	private int opertor_type;
	private int channelId;
	private Integer sp_id;
	private Date createtime;
	private int unicom;//联通
	private int mobile;//移动
	private int telecom;//电信
	private String telecom_prefix;//电信接入号
	private String mobile_prefix;//移动接入号
	private String unicom_prefix;//联通接入号
	
	public String getTelecom_prefix() {
		return telecom_prefix;
	}
	public void setTelecom_prefix(String telecom_prefix) {
		this.telecom_prefix = telecom_prefix;
	}
	public String getMobile_prefix() {
		return mobile_prefix;
	}
	public void setMobile_prefix(String mobile_prefix) {
		this.mobile_prefix = mobile_prefix;
	}
	public String getUnicom_prefix() {
		return unicom_prefix;
	}
	public void setUnicom_prefix(String unicom_prefix) {
		this.unicom_prefix = unicom_prefix;
	}
	public int getUnicom() {
		return unicom;
	}
	public void setUnicom(int unicom) {
		this.unicom = unicom;
	}
	public int getMobile() {
		return mobile;
	}
	public void setMobile(int mobile) {
		this.mobile = mobile;
	}
	public int getTelecom() {
		return telecom;
	}
	public void setTelecom(int telecom) {
		this.telecom = telecom;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOpertor_type() {
		return opertor_type;
	}
	public void setOpertor_type(int opertor_type) {
		this.opertor_type = opertor_type;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public Integer getSp_id() {
		return sp_id;
	}
	public void setSp_id(Integer sp_id) {
		this.sp_id = sp_id;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
