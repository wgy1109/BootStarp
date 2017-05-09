package com.chanzor.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* <p>Title: UserInfo.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: langyu</p>
* @author jian.zhang
* @date 2014年6月7日
* @version 1.0
 */
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 7569280663153231474L;
	private int user_id=0;
	private String userName;
	private String password;
	private int role_id;
	private String user_rights;
	private int error_count;
	private Date create_time;
	private int status;
	private Date delete_time;
	private String desc;
	private String mdn;
	private String name;
	private String email;
	private Date lastlogin_time;
	private String lastlogin_ip;
	private int create_user;
	private String remember;
	private RoleInfo roleInfo;
	private int Session_role_id;
	private Page page;

	
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getSession_role_id() {
		return Session_role_id;
	}

	public void setSession_role_id(int session_role_id) {
		Session_role_id = session_role_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public int getError_count() {
		return error_count;
	}

	public void setError_count(int error_count) {
		this.error_count = error_count;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDelete_time() {
		return delete_time;
	}

	public void setDelete_time(Date delete_time) {
		this.delete_time = delete_time;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastlogin_time() {
		return lastlogin_time;
	}

	public void setLastlogin_time(Date lastlogin_time) {
		this.lastlogin_time = lastlogin_time;
	}

	public String getLastlogin_ip() {
		return lastlogin_ip;
	}

	public void setLastlogin_ip(String lastlogin_ip) {
		this.lastlogin_ip = lastlogin_ip;
	}

	public int getCreate_user() {
		return create_user;
	}

	public void setCreate_user(int create_user) {
		this.create_user = create_user;
	}

	public String getRemember() {
		return remember;
	}

	public void setRemember(String remember) {
		this.remember = remember;
	}

	public RoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public String getMdn() {
		return mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	public String getUser_rights() {
		return user_rights;
	}

	public void setUser_rights(String user_rights) {
		this.user_rights = user_rights;
	}


}
