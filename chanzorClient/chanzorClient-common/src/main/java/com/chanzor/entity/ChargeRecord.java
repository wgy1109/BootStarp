package com.chanzor.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Title: ChargeRecord.java
 * </p>
 * <p>
 * Description: 充值记录
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: langyu
 * </p>
 * 
 * @author jian.zhang
 * @date 2014年6月7日
 * @version 1.0
 */
public class ChargeRecord implements Serializable {
	private static final long serialVersionUID = -8943303235163502544L;
	private int id;
	private Integer sp_id;
	private String productid;// 订单号
	/**
	 * 
	 * 充值条数
	 * 
	 */
	private int chargenum;
	/**
	 * 
	 * 充值时间
	 * 
	 */
	private Date charge_time;
	/**
	 * 
	 * 操作员
	 * 
	 */
	private String operator;
	/**
	 * 
	 * 充值金额
	 * 
	 */
	private float amount;
	/**
	 * 
	 * 充值单价
	 * 
	 */
	private float price;
	private String descption;
	private String month;
	/**
	 * 
	 * 企业名称
	 * 
	 */
	private String sp_name;
	private Page page;
	private Integer charge_type;

	/**
	 * 开户公司
	 */
	private String platform;
	private double salePrice;
	private Integer spServiceType;
	private String chargeopeartor;
	private boolean is_default;

	public boolean isIs_default() {
		return is_default;
	}

	public void setIs_default(boolean is_default) {
		this.is_default = is_default;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * 充值类型 1为充值;2为扣除
	 * 
	 */
	private int type;

	private int alipayType;

	private int balance;// 剩余短信条数

	private String tablename;

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getSp_name() {
		return sp_name;
	}

	public String getChargeopeartor() {
		return chargeopeartor;
	}

	public void setChargeopeartor(String chargeopeartor) {
		this.chargeopeartor = chargeopeartor;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getSpServiceType() {
		return spServiceType;
	}

	public void setSpServiceType(Integer spServiceType) {
		this.spServiceType = spServiceType;
	}

	public void setSp_name(String sp_name) {
		this.sp_name = sp_name;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getDescption() {
		return descption;
	}

	public void setDescption(String descption) {
		this.descption = descption;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChargenum() {
		return chargenum;
	}

	public void setChargenum(int chargenum) {
		this.chargenum = chargenum;
	}

	public Date getCharge_time() {
		return charge_time;
	}

	public void setCharge_time(Date charge_time) {
		this.charge_time = charge_time;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Integer getSp_id() {
		return sp_id;
	}

	public void setSp_id(Integer sp_id) {
		this.sp_id = sp_id;
	}

	public Integer getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(Integer charge_type) {
		this.charge_type = charge_type;
	}

	public int getAlipayType() {
		return alipayType;
	}

	public void setAlipayType(int alipayType) {
		this.alipayType = alipayType;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

}
