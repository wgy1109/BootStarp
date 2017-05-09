package com.chanzor.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * 订单 表
 * 
 * @author user
 * 
 */
public class AppOrderInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 130719635295188119L;

	// ID NUMBER Yes 1
	// ORDER_TYPE NUMBER Yes 2
	// ORDER_TIME DATE Yes 3
	// AMOUNT NUMBER Yes 4
	// STATUS NUMBER Yes 5
	// USERID NUMBER Yes 6

	/**
	 * 1:支付成功(审核通过) 通过支付类型判断是支付成功还是审核通过 线上支付 --> 支付成功 线下支付 --> 审核通过
	 */
	public static final Integer STATUS_SUCCESS = 1;

	/**
	 * 0:新建订单
	 */
	public static final Integer STATUS_NEW = 0;

	/**
	 * 2:审核中(待审核)
	 */
	public static final Integer STATUS_CHECKING = 2;

	/**
	 * 4:审核驳回
	 */
	public static final Integer STATUS_CHECK_BACK = 4;

	/**
	 * 线上支付
	 */
	public static final Integer PAY_TYPE_ONLINE = 1;

	/**
	 * 线下支付
	 */
	public static final Integer PAY_TYPE_OFFLINE = 2;

	/**
	 * 订单ID
	 */
	private Integer id;
	/**
	 * 支付类型(线上, 线下) 线上: 支付宝
	 */
	private Integer pay_type;
	/**
	 * 创单时间
	 */
	private Date order_time;
	/**
	 * 订单金额
	 */
	private Double amount;

	/**
	 * 订单编号
	 */
	private String code;

	/**
	 * 订单状态 1:支付成功, 3: 新建订单, 4:审核中
	 * 
	 * 线上支付: 新建订单 --> 支付成功(支付失败后, 不修改订单状态, 用户可以再次支付) 线下支付: 新建订单 --> 审核中 -->
	 * 支付成功(审核成功) 或 新建订单 --> 审核中 --> 审核失败
	 */
	private Integer status;

	/**
	 * 用户ID
	 */
	private Integer userid;

	private Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public Date getOrder_time() {
		return order_time;
	}

	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
