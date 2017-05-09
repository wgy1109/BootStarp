package com.chanzor.entity;

import java.io.Serializable;
/**
 * 
* <p>Title: ChannelInfo.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: langyu</p>
* @author jian.zhang
* @date 2014年6月7日
* @version 1.0
 */
public class ChannelInfo implements Serializable{
	private static final long serialVersionUID = 7956100845230759269L;
	private Integer id;
	private String channel_name;
	private Integer pm_id;
	private Integer is_support_unicom=0;
	private Integer is_support_mobile=0;
	private Integer is_support_telecom=0;
	private Integer status;
	private String mobile_prefix;
	private String unicom_prefix;
	private String telecom_prefix;
    
	private Integer is_unicom_extend;
	private Integer is_mobile_extend;
	private Integer is_telecom_extend;
	private String desc;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public Integer getPm_id() {
		return pm_id;
	}
	public void setPm_id(Integer pm_id) {
		this.pm_id = pm_id;
	}
	public Integer getIs_support_unicom() {
		return is_support_unicom;
	}
	public void setIs_support_unicom(Integer is_support_unicom) {
		this.is_support_unicom = is_support_unicom;
	}
	public Integer getIs_support_mobile() {
		return is_support_mobile;
	}
	public void setIs_support_mobile(Integer is_support_mobile) {
		this.is_support_mobile = is_support_mobile;
	}
	public Integer getIs_support_telecom() {
		return is_support_telecom;
	}
	public void setIs_support_telecom(Integer is_support_telecom) {
		this.is_support_telecom = is_support_telecom;
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
	public String getTelecom_prefix() {
		return telecom_prefix;
	}
	public void setTelecom_prefix(String telecom_prefix) {
		this.telecom_prefix = telecom_prefix;
	}
	public Integer getIs_unicom_extend() {
		return is_unicom_extend;
	}
	public void setIs_unicom_extend(Integer is_unicom_extend) {
		this.is_unicom_extend = is_unicom_extend;
	}
	public Integer getIs_mobile_extend() {
		return is_mobile_extend;
	}
	public void setIs_mobile_extend(Integer is_mobile_extend) {
		this.is_mobile_extend = is_mobile_extend;
	}
	public Integer getIs_telecom_extend() {
		return is_telecom_extend;
	}
	public void setIs_telecom_extend(Integer is_telecom_extend) {
		this.is_telecom_extend = is_telecom_extend;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
	
	

}
