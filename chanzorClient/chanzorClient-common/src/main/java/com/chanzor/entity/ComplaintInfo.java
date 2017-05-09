package com.chanzor.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @作者 Liloo
 * @E-mail liloo@vip.qq.com
 * @时间 2015年7月13日
 * @版权 © Liloo 版权所有.
 */
public class ComplaintInfo implements Serializable {

	private static final long serialVersionUID = -7885364483530750220L;

	private Integer id;
	private Integer spid;
	private String mdn;
	private String content;
	private String callmdn;
	private Date createtime;
	private String complain_time;
	private String complain_begin_time;
	private String complain_end_time;
	private Page page;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSpid() {
		return spid;
	}

	public void setSpid(Integer spid) {
		this.spid = spid;
	}

	public String getMdn() {
		return mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCallmdn() {
		return callmdn;
	}

	public void setCallmdn(String callmdn) {
		this.callmdn = callmdn;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getComplain_time() {
		return complain_time;
	}

	public void setComplain_time(String complain_time) {
		this.complain_time = complain_time;
	}

	public String getComplain_begin_time() {
		return complain_begin_time;
	}

	public void setComplain_begin_time(String complain_begin_time) {
		this.complain_begin_time = complain_begin_time;
	}

	public String getComplain_end_time() {
		return complain_end_time;
	}

	public void setComplain_end_time(String complain_end_time) {
		this.complain_end_time = complain_end_time;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
