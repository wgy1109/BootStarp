package com.chanzor.entity;

import java.io.Serializable;
import java.util.Date;

public class Coupon implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private int id = 0;

	private String num;
	private String sms_sum;
	private String userid;
	private String type;
	private String begintime;
	private String endtime;
	private String isclose;
	private String createtime;
	private String createid;
	private String des;
	
	private Page page; 
	
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public int getId (){
		return id;
	}
	public void setId (int id){
		this.id = id;
	}
	public String getNum(){
		return num;
	}
	public void setNum(String num){
		this.num=num;
	}
	public String getSms_sum(){
		return sms_sum;
	}
	public void setSms_sum(String sms_sum){
		this.sms_sum=sms_sum;
	}
	public String getUserid(){
		return userid;
	}
	public void setUserid(String userid){
		this.userid=userid;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getBegintime(){
		return begintime;
	}
	public void setBegintime(String begintime){
		this.begintime=begintime;
	}
	public String getEndtime(){
		return endtime;
	}
	public void setEndtime(String endtime){
		this.endtime=endtime;
	}
	public String getIsclose(){
		return isclose;
	}
	public void setIsclose(String isclose){
		this.isclose=isclose;
	}
	public String getCreatetime(){
		return createtime;
	}
	public void setCreatetime(String createtime){
		this.createtime=createtime;
	}
	public String getCreateid(){
		return createid;
	}
	public void setCreateid(String createid){
		this.createid=createid;
	}
	public String getDes(){
		return des;
	}
	public void setDes(String des){
		this.des=des;
	}

}
