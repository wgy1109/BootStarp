package com.chanzor.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
* <p>Title: SpCharge.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: langyu</p>
* @author jian.zhang
* @date 2014年6月7日
* @version 1.0
 */
public class SpCharge implements Serializable {
	private static final long serialVersionUID = -232699484727565038L;
	private int id;
	private int max_num;
	private int use_num;
	private int  leftover_num;
	private String effect_month="";
	private Integer sp_id;
	private int type;
	private float price1;
	private float price2;
	private int number1;
	private int number2;
	private String descption;
	private int chargeNum;
	
	
	public int getChargeNum() {
		return chargeNum;
	}
	public void setChargeNum(int chargeNum) {
		this.chargeNum = chargeNum;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public float getPrice1() {
		return price1;
	}
	public void setPrice1(float price1) {
		this.price1 = price1;
	}
	public float getPrice2() {
		return price2;
	}
	public void setPrice2(float price2) {
		this.price2 = price2;
	}
	public int getNumber1() {
		return number1;
	}
	public void setNumber1(int number1) {
		this.number1 = number1;
	}
	public int getNumber2() {
		return number2;
	}
	public void setNumber2(int number2) {
		this.number2 = number2;
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
	public int getMax_num() {
		return max_num;
	}
	public void setMax_num(int max_num) {
		this.max_num = max_num;
	}
	public int getUse_num() {
		return use_num;
	}
	public void setUse_num(int use_num) {
		this.use_num = use_num;
	}
	public int getLeftover_num() {
		return leftover_num;
	}
	public void setLeftover_num(int leftover_num) {
		this.leftover_num = leftover_num;
	}
	public String getEffect_month() {
		return effect_month;
	}
	public void setEffect_month(String effect_month) {
		this.effect_month = effect_month;
	}
	public Integer getSp_id() {
		return sp_id;
	}
	public void setSp_id(Integer sp_id) {
		this.sp_id = sp_id;
	}
	
	
	public static void main(String[] args) {
		float i=1000.5f;
		BigDecimal df = new BigDecimal(i/33);
		float rate2 = df.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
         System.out.println(rate2);
		
		
	}
	

}
