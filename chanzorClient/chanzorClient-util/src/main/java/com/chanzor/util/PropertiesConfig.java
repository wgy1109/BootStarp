package com.chanzor.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class PropertiesConfig {
	private String s_nginx_url;
	private String nginx_url;
	private String service_nginx_url;
	private String mailAddress;
	private String mailpassword;
	private String default_channe_id;
	private String default_charge_num;
	private String send_message_url;

	private String notify_url;
	private String return_url;

	private String sp_userid;
	private String sp_account;
	private String sp_password;

	private String inter_send_message_url;
	private String inter_default_charge_num;

	private String office_url;
	private String file_url;
	private String file_loc;
	private String customized_send_message_url;

	private String user_manager_id;
	private String customized_message_size;
	
	private String subaccount_extend_finance;

	public String getCustomized_message_size() {
		return customized_message_size;
	}

	public void setCustomized_message_size(String customized_message_size) {
		this.customized_message_size = customized_message_size;
	}

	public String getCustomized_send_message_url() {
		return customized_send_message_url;
	}

	public void setCustomized_send_message_url(String customized_send_message_url) {
		this.customized_send_message_url = customized_send_message_url;
	}

	public String getOffice_url() {
		return office_url;
	}

	public void setOffice_url(String office_url) {
		this.office_url = office_url;
	}

	private String voice_send_message_url;

	public String getS_nginx_url() {
		return s_nginx_url;
	}

	public void setS_nginx_url(String s_nginx_url) {
		this.s_nginx_url = s_nginx_url;
	}

	public String getSp_userid() {
		return sp_userid;
	}

	public void setSp_userid(String sp_userid) {
		this.sp_userid = sp_userid;
	}

	public String getSp_account() {
		return sp_account;
	}

	public void setSp_account(String sp_account) {
		this.sp_account = sp_account;
	}

	public String getSp_password() {
		return sp_password;
	}

	public void setSp_password(String sp_password) {
		this.sp_password = sp_password;
	}

	public String getSend_message_url() {
		return send_message_url;
	}

	public void setSend_message_url(String send_message_url) {
		this.send_message_url = send_message_url;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getNginx_url() {
		return nginx_url;
	}

	public void setNginx_url(String nginx_url) {
		this.nginx_url = nginx_url;
	}

	public String getService_nginx_url() {
		return service_nginx_url;
	}

	public void setService_nginx_url(String service_nginx_url) {
		this.service_nginx_url = service_nginx_url;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getMailpassword() {
		return mailpassword;
	}

	public void setMailpassword(String mailpassword) {
		this.mailpassword = mailpassword;
	}

	public String getDefault_channe_id() {
		return default_channe_id;
	}

	public void setDefault_channe_id(String default_channe_id) {
		this.default_channe_id = default_channe_id;
	}

	public String getDefault_charge_num() {
		return default_charge_num;
	}

	public void setDefault_charge_num(String default_charge_num) {
		this.default_charge_num = default_charge_num;
	}

	public String getInter_send_message_url() {
		return inter_send_message_url;
	}

	public void setInter_send_message_url(String inter_send_message_url) {
		this.inter_send_message_url = inter_send_message_url;
	}

	public String getInter_default_charge_num() {
		return inter_default_charge_num;
	}

	public void setInter_default_charge_num(String inter_default_charge_num) {
		this.inter_default_charge_num = inter_default_charge_num;
	}

	public String getVoice_send_message_url() {
		return voice_send_message_url;
	}

	public void setVoice_send_message_url(String voice_send_message_url) {
		this.voice_send_message_url = voice_send_message_url;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public String getFile_loc() {
		return file_loc;
	}

	public void setFile_loc(String file_loc) {
		this.file_loc = file_loc;
	}

	public String getUser_manager_id() {
		return user_manager_id;
	}

	public void setUser_manager_id(String user_manager_id) {
		this.user_manager_id = user_manager_id;
	}

	public String getSubaccount_extend_finance() {
		return subaccount_extend_finance;
	}

	public void setSubaccount_extend_finance(String subaccount_extend_finance) {
		this.subaccount_extend_finance = subaccount_extend_finance;
	}

}
