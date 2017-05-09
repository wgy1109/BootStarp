package com.chanzor.entity;

import java.io.Serializable;
import java.util.Date;

public class AgreementForm implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private int id = 0;

	private String userid;
	private String workbegintime;
	private String workbegintime1;
	private String workendtime;
	private String workendtime1;
	private String expressnumber;
	private String type;
	private String remark;
	private String signuseraddress;
	private String signusername;
	private String signuserphone;
	
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
	public String getUserid(){
		return userid;
	}
	public void setUserid(String userid){
		this.userid=userid;
	}
	public String getWorkbegintime(){
		return workbegintime;
	}
	public void setWorkbegintime(String workbegintime){
		this.workbegintime=workbegintime;
	}
	public String getWorkendtime(){
		return workendtime;
	}
	public void setWorkendtime(String workendtime){
		this.workendtime=workendtime;
	}
	public String getExpressnumber(){
		return expressnumber;
	}
	public void setExpressnumber(String expressnumber){
		this.expressnumber=expressnumber;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getSignuseraddress(){
		return signuseraddress;
	}
	public void setSignuseraddress(String signuseraddress){
		this.signuseraddress=signuseraddress;
	}
	public String getSignusername(){
		return signusername;
	}
	public void setSignusername(String signusername){
		this.signusername=signusername;
	}
	public String getSignuserphone(){
		return signuserphone;
	}
	public void setSignuserphone(String signuserphone){
		this.signuserphone=signuserphone;
	}
	public String getWorkbegintime1() {
		return workbegintime1;
	}
	public void setWorkbegintime1(String workbegintime1) {
		this.workbegintime1 = workbegintime1;
	}
	public String getWorkendtime1() {
		return workendtime1;
	}
	public void setWorkendtime1(String workendtime1) {
		this.workendtime1 = workendtime1;
	}

}
