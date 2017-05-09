package com.chanzor.entity;

import java.util.Date;

public class ChannelGroup {

	//通道组
	private int id;
	private String gname;//通道组名称
	private String status;//状态
	private Date createtime;//创建时间
	private String username;//创建者
	private Integer createid;
	
    public Integer getCreateid() {
		return createid;
	}
	public void setCreateid(Integer createid) {
		this.createid = createid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private Page page;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
    
    
}
