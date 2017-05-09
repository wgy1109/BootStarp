package com.chanzor.entity;

public class SystemParameter {

	private Integer id;
	private String prm_name;
	private Integer prm_type;
	private String prm_value;
	private String prm_desc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrm_name() {
		return prm_name;
	}

	public void setPrm_name(String prm_name) {
		this.prm_name = prm_name;
	}

	public Integer getPrm_type() {
		return prm_type;
	}

	public void setPrm_type(Integer prm_type) {
		this.prm_type = prm_type;
	}

	public String getPrm_value() {
		return prm_value;
	}

	public void setPrm_value(String prm_value) {
		this.prm_value = prm_value;
	}

	public String getPrm_desc() {
		return prm_desc;
	}

	public void setPrm_desc(String prm_desc) {
		this.prm_desc = prm_desc;
	}

}
