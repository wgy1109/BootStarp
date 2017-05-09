package com.chanzor.entity;

public class OrderPackage {

	private int id;
	private String package_num;
	private String package_amount;
	private String package_name;
	private String package_type;//1 包月 2 包年
	private String package_desc;
	private Page page;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPackage_num() {
		return package_num;
	}
	public void setPackage_num(String package_num) {
		this.package_num = package_num;
	}
	public String getPackage_amount() {
		return package_amount;
	}
	public void setPackage_amount(String package_amount) {
		this.package_amount = package_amount;
	}
	public String getPackage_name() {
		return package_name;
	}
	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}
	public String getPackage_type() {
		return package_type;
	}
	public void setPackage_type(String package_type) {
		this.package_type = package_type;
	}
	public String getPackage_desc() {
		return package_desc;
	}
	public void setPackage_desc(String package_desc) {
		this.package_desc = package_desc;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	
   
}
