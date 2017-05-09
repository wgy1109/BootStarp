package com.chanzor.entity;

import java.io.Serializable;
import java.util.Date;


public class SendTaskInfo implements Serializable {
	private static final long serialVersionUID = 7569280663153231474L;
	
	private Integer id; //主键ID
	
	private Integer sp_id; //用户ID
	
	private String content; //内容
	
	private Date createtime; //创建时间
	
	private Integer total; // 总人数
	
	private Integer allnumber;  //  总条数
	
    private Integer unicomnumber; // 联通
    
    private Integer mobilenumber; // 移动
    
    private Integer telecomnumer; // 电信
    
    private Integer succ; //成功条数
    
    private Integer fail; //失败条数
    
    private Integer arrive_succ; //到达成功条数
    
    private Integer arrive_fail; //到达失败条数
    
    private Integer task_type;  //任务类型
    
    private Integer status;  //状态
    
    private Date endtime; //结束时间
    
    
    private String send_type; //发送类型
    
    private String descption; //描述
    
    private String dynamicdata; //动态数据
    
    private String extno; //类别号
    
    private Page page;
    
    private String quereyStartTime;
    
    private String queryEndTime;
    
    private Integer start;
    
    private Integer end;
    
    private String mdn; //手机号
    
    private Integer status1; //状态获取
    
    private String stat_desc; //网管回执信息
    
	public String getMdn() {
		return mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	public Integer getStatus1() {
		return status1;
	}

	public void setStatus1(Integer status1) {
		this.status1 = status1;
	}

	public String getStat_desc() {
		return stat_desc;
	}

	public void setStat_desc(String stat_desc) {
		this.stat_desc = stat_desc;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public String getQuereyStartTime() {
		return quereyStartTime;
	}

	public void setQuereyStartTime(String quereyStartTime) {
		this.quereyStartTime = quereyStartTime;
	}

	public String getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(String queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSp_id() {
		return sp_id;
	}

	public void setSp_id(Integer sp_id) {
		this.sp_id = sp_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getAllnumber() {
		return allnumber;
	}

	public void setAllnumber(Integer allnumber) {
		this.allnumber = allnumber;
	}

	public Integer getUnicomnumber() {
		return unicomnumber;
	}

	public void setUnicomnumber(Integer unicomnumber) {
		this.unicomnumber = unicomnumber;
	}

	public Integer getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(Integer mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public Integer getTelecomnumer() {
		return telecomnumer;
	}

	public void setTelecomnumer(Integer telecomnumer) {
		this.telecomnumer = telecomnumer;
	}

	public Integer getSucc() {
		return succ;
	}

	public void setSucc(Integer succ) {
		this.succ = succ;
	}

	public Integer getFail() {
		return fail;
	}

	public void setFail(Integer fail) {
		this.fail = fail;
	}

	public Integer getArrive_succ() {
		return arrive_succ;
	}

	public void setArrive_succ(Integer arrive_succ) {
		this.arrive_succ = arrive_succ;
	}

	public Integer getArrive_fail() {
		return arrive_fail;
	}

	public void setArrive_fail(Integer arrive_fail) {
		this.arrive_fail = arrive_fail;
	}

	public Integer getTask_type() {
		return task_type;
	}

	public void setTask_type(Integer task_type) {
		this.task_type = task_type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getSend_type() {
		return send_type;
	}

	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}

	public String getDescption() {
		return descption;
	}

	public void setDescption(String descption) {
		this.descption = descption;
	}

	public String getDynamicdata() {
		return dynamicdata;
	}

	public void setDynamicdata(String dynamicdata) {
		this.dynamicdata = dynamicdata;
	}

	public String getExtno() {
		return extno;
	}

	public void setExtno(String extno) {
		this.extno = extno;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
