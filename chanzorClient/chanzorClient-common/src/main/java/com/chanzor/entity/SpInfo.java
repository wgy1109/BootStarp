package com.chanzor.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jian.zhang
 * @date 2014年6月7日
 * @version 1.0
 */
public class SpInfo implements Serializable {

	private static final long serialVersionUID = 80510789958901029L;
	private Integer spid;
	private Integer id;
	private Integer userId;// 用户ID
	private String sp_name;
	private String ip;
	private String status;// 状态是否冻结
	private Date createTime;
	private Date createTime1;
	private String mobile_prefix;
	private String mobile_suffix;
	private String mobile_num;
	private String unicom_prefix;
	private String unicom_suffix;
	private String unicom_num;
	private String telecom_prefix;
	private String telecom_suffix;
	private String telecom_num;
	private String report_url;
	private String reply_url;
	private String is_lock;
	private String username;
	private String password;
	private String feetype;
	private Integer is_audit_word;// 是否敏感词审核
	private Integer error_count;// 提交错误次数
	private String spContact_name;
	private String spContact_mdn;
	private String audit_type;
	private String signature;// 企业签名
	private String sameNameMaxnum;
	private Integer sp_type;// 企业类型
	private Date droptime;
	private Integer sp_channelconfig;// 企业发送能力
	private Page page;
	private SpCharge spCharge;
	private String effect_month;
	private String samemdnmaxnum;// 同一号码最大发送次数
	private String minsendtotal;// 最小提交人数
	private String return_type;// 计费类型
	private String no_limit_mdn; // 不限制号码
	private String sp_audit_number;// 企业审核人数
	private double saleprice;// 单价
	private String email;
	private String sp_saler;// 销售经理
	private String sp_saler_mdn;
	private String alarmnumber;// 提醒条数
	private String version;// 0朗宇，1易米
	private Integer isvalidbome;// 0不需要；1需要炸弹验证机制
	private Integer isreportnow; // 是否需要及时报告 ;0不需要1需要
	private String saler_name;
	private String sp_desc;// 备注信息
	private String platform;
	private String content_type;
	private String is_need_signture;
	private String groupid;
	private Integer channelgroupid;
	private String samecontentmaxnum;
	private String sp_audit_num;
	private String provice;// 客户所在省份
	private Integer sp_industry;// 应用行业
	private Integer sp_through_status;// 应用状态 1：通过 2：未通过
	private String sp_website;// 企业网站
	private Integer leftover_num;// 短信剩余条数
	private Integer masterplateNum;// 应用模板总数
	private Integer noAuditMasterPlateNum;// 应用未审核模板
	private Integer yesterDaySendTask;
	private Integer monthSendTask;
	private Integer whiteCount;// 测试手机号码个数
	private String sp_app_type;// 应用类型(1:网站2：移动应用3：微信公众号4：其它)
	private String redisStatus;// 存放redis中应用状态 //1正常 0欠费 -1下线
	private String redisType;// 存放redis中应用状态//0后付费 1预付费 2测试
	private String sendCount;// 存放redis中用户发送条数
	private Integer sp_signature_type;// 签名证明图片描述(1:商标证书2:商标受理单3:微信公众号备案截图4:网站备案截图5:商标授权函)
	private String sp_signature_img;// 签名证明图片的路径 500
	private String sp_app_info;
	private Integer is_templete_send;// S端新增是否模板发送选项
	private Integer is_use_blacklist;// S端新增是否使用黑名单选项
	private Integer receive_mode;// S端新增接收方式1：不接受2：推送3：主动获取
	private String is_sub_failwords;
	private String account_name;// 用户名称
	private String deductionType;//计费模式默认为成功计费(国内短信和语音短信)
	private Integer weight_level;
	private Integer chargeCount;// h
	private String joinNumber;// CMPP接入码校验
	private String corpIdCutNum;// 上行位数截取
	private Integer isMulti;// 验证方式0:默认1：签名库2：不验证
	private Integer max_num;
	private Integer use_num;
	private Integer submitCount;
	private Integer successCount;
	private Integer unKnownCount;
	private Integer sendedCount;
	private Integer sp_service_type;// 应用业务类型1：国内短信2：国际短信3：语音短信
	private String sp_relation_prove;// 公司关系证明
	private String sp_domain_auth;// 域名授权书
	private String sp_signature_auth;// 签名授权书
	private String sp_other_info;// 应用类型中的其他信息
	private String sp_weixin_info;// 应用类型中的微信公众号
	private boolean is_default;
	private String photo_img;
	private String spRejectReason;// 应用驳回原因
	private String spNameSuffix;// 应用名称后缀
	private String interDeductionType;//国际短信计费模式默认为提交计费

	public String getSpRejectReason() {
		return spRejectReason;
	}

	public void setSpRejectReason(String spRejectReason) {
		this.spRejectReason = spRejectReason;
	}

	public String getSpNameSuffix() {
		return spNameSuffix;
	}

	public void setSpNameSuffix(String spNameSuffix) {
		this.spNameSuffix = spNameSuffix;
	}

	public String getPhoto_img() {
		return photo_img;
	}


	public String getInterDeductionType() {
		return interDeductionType;
	}

	public void setInterDeductionType(String interDeductionType) {
		this.interDeductionType = interDeductionType;
	}

	public void setPhoto_img(String photo_img) {
		this.photo_img = photo_img;
	}

	public boolean isIs_default() {
		return is_default;
	}

	public void setIs_default(boolean is_default) {
		this.is_default = is_default;
	}

	public String getSp_other_info() {
		return sp_other_info;
	}

	public void setSp_other_info(String sp_other_info) {
		this.sp_other_info = sp_other_info;
	}

	public String getSp_weixin_info() {
		return sp_weixin_info;
	}

	public void setSp_weixin_info(String sp_weixin_info) {
		this.sp_weixin_info = sp_weixin_info;
	}

	public String getSp_relation_prove() {
		return sp_relation_prove;
	}

	public void setSp_relation_prove(String sp_relation_prove) {
		this.sp_relation_prove = sp_relation_prove;
	}

	public String getSp_domain_auth() {
		return sp_domain_auth;
	}

	public void setSp_domain_auth(String sp_domain_auth) {
		this.sp_domain_auth = sp_domain_auth;
	}

	public String getSp_signature_auth() {
		return sp_signature_auth;
	}

	public void setSp_signature_auth(String sp_signature_auth) {
		this.sp_signature_auth = sp_signature_auth;
	}

	public Integer getSp_service_type() {
		return sp_service_type;
	}

	public void setSp_service_type(Integer sp_service_type) {
		this.sp_service_type = sp_service_type;
	}

	public Integer getSendedCount() {
		return sendedCount;
	}

	public void setSendedCount(Integer sendedCount) {
		if (sendedCount != null) {
			this.sendedCount = sendedCount;
		} else {
			this.sendedCount = 0;
		}
	}

	public Integer getSubmitCount() {
		return submitCount;
	}

	public void setSubmitCount(Integer submitCount) {
		if (submitCount != null) {
			this.submitCount = submitCount;
		} else {
			this.submitCount = 0;
		}
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		if (successCount != null) {
			this.successCount = successCount;
		} else {
			this.successCount = 0;
		}
	}

	public Integer getUnKnownCount() {
		return unKnownCount;
	}

	public void setUnKnownCount(Integer unKnownCount) {
		if (unKnownCount != null) {
			this.unKnownCount = unKnownCount;
		} else {
			this.unKnownCount = 0;
		}
	}

	public Integer getUse_num() {
		return use_num;
	}

	public void setUse_num(Integer use_num) {
		this.use_num = use_num;
	}

	public Integer getMax_num() {
		return max_num;
	}

	public void setMax_num(Integer max_num) {
		this.max_num = max_num;
	}

	public String getSamecontentmaxnum() {
		return samecontentmaxnum;
	}

	public void setSamecontentmaxnum(String samecontentmaxnum) {
		this.samecontentmaxnum = samecontentmaxnum;
	}

	public String getSp_audit_num() {
		return sp_audit_num;
	}

	public void setSp_audit_num(String sp_audit_num) {
		this.sp_audit_num = sp_audit_num;
	}

	public String getRedisStatus() {
		return redisStatus;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getDeductionType() {
		return deductionType;
	}

	public void setDeductionType(String deductionType) {
		this.deductionType = deductionType;
	}

	public Integer getChargeCount() {
		return chargeCount;
	}

	public void setChargeCount(Integer chargeCount) {
		this.chargeCount = chargeCount;
	}

	public void setRedisStatus(String redisStatus) {
		this.redisStatus = redisStatus;
	}

	public Integer getIs_templete_send() {
		return is_templete_send;
	}

	public Integer getReceive_mode() {
		return receive_mode;
	}

	public void setReceive_mode(Integer receive_mode) {
		this.receive_mode = receive_mode;
	}

	public void setIs_templete_send(Integer is_templete_send) {
		this.is_templete_send = is_templete_send;
	}

	public Integer getIs_use_blacklist() {
		return is_use_blacklist;
	}

	public void setIs_use_blacklist(Integer is_use_blacklist) {
		this.is_use_blacklist = is_use_blacklist;
	}

	public String getRedisType() {
		return redisType;
	}

	public String getSendCount() {
		return sendCount;
	}

	public void setSendCount(String sendCount) {
		this.sendCount = sendCount;
	}

	public void setRedisType(String redisType) {
		this.redisType = redisType;
	}

	public String getIs_sub_failwords() {
		return is_sub_failwords;
	}

	public void setIs_sub_failwords(String is_sub_failwords) {
		this.is_sub_failwords = is_sub_failwords;
	}

	private float price;
	private Integer number;

	private String gname;

	private String extend;

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public float getPrice() {
		return price;
	}

	public Integer getMonthSendTask() {
		return monthSendTask;
	}

	public void setMonthSendTask(Integer monthSendTask) {
		this.monthSendTask = monthSendTask;
	}

	public Integer getYesterDaySendTask() {
		return yesterDaySendTask;
	}

	public void setYesterDaySendTask(Integer yesterDaySendTask) {
		this.yesterDaySendTask = yesterDaySendTask;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public Integer getChannelgroupid() {
		return channelgroupid;
	}

	public void setChannelgroupid(Integer channelgroupid) {
		this.channelgroupid = channelgroupid;
	}

	public String getPlatform() {
		return platform;
	}

	public Integer getLeftover_num() {
		return leftover_num;
	}

	public void setLeftover_num(Integer leftover_num) {
		this.leftover_num = leftover_num;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSp_desc() {
		return sp_desc;
	}

	public void setSp_desc(String sp_desc) {
		this.sp_desc = sp_desc;
	}

	public String getSaler_name() {
		return saler_name;
	}

	public void setSaler_name(String saler_name) {
		this.saler_name = saler_name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIsreportnow(Integer isreportnow) {
		this.isreportnow = isreportnow;
	}

	public Integer getIsreportnow() {
		return isreportnow;
	}

	public Integer getIsvalidbome() {
		return isvalidbome;
	}

	public void setIsvalidbome(Integer isvalidbome) {
		this.isvalidbome = isvalidbome;
	}

	public String getAlarmnumber() {
		return alarmnumber;
	}

	public void setAlarmnumber(String alarmnumber) {
		this.alarmnumber = alarmnumber;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(double saleprice) {
		this.saleprice = saleprice;
	}

	public String getSamemdnmaxnum() {
		return samemdnmaxnum;
	}

	public void setSamemdnmaxnum(String samemdnmaxnum) {
		this.samemdnmaxnum = samemdnmaxnum;
	}

	public String getMinsendtotal() {
		return minsendtotal;
	}

	public void setMinsendtotal(String minsendtotal) {
		this.minsendtotal = minsendtotal;
	}

	public String getReturn_type() {
		return return_type;
	}

	public void setReturn_type(String return_type) {
		this.return_type = return_type;
	}

	public String getNo_limit_mdn() {
		return no_limit_mdn;
	}

	public void setNo_limit_mdn(String no_limit_mdn) {
		this.no_limit_mdn = no_limit_mdn;
	}

	public String getSp_audit_number() {
		return sp_audit_number;
	}

	public void setSp_audit_number(String sp_audit_number) {
		this.sp_audit_number = sp_audit_number;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getEffect_month() {
		return effect_month;
	}

	public void setEffect_month(String effect_month) {
		this.effect_month = effect_month;
	}

	public SpCharge getSpCharge() {
		return spCharge;
	}

	public void setSpCharge(SpCharge spCharge) {
		this.spCharge = spCharge;
	}

	public Integer getSp_channelconfig() {
		return sp_channelconfig;
	}

	public void setSp_channelconfig(Integer sp_channelconfig) {
		this.sp_channelconfig = sp_channelconfig;
	}

	public Date getCreateTime1() {
		return createTime1;
	}

	public void setCreateTime1(Date createTime1) {
		this.createTime1 = createTime1;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getSpid() {
		return spid;
	}

	public void setSpid(Integer spid) {
		this.spid = spid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMobile_prefix() {
		return mobile_prefix;
	}

	public void setMobile_prefix(String mobile_prefix) {
		this.mobile_prefix = mobile_prefix;
	}

	public String getMobile_suffix() {
		return mobile_suffix;
	}

	public void setMobile_suffix(String mobile_suffix) {
		this.mobile_suffix = mobile_suffix;
	}

	public String getMobile_num() {
		return mobile_num;
	}

	public void setMobile_num(String mobile_num) {
		this.mobile_num = mobile_num;
	}

	public String getUnicom_prefix() {
		return unicom_prefix;
	}

	public void setUnicom_prefix(String unicom_prefix) {
		this.unicom_prefix = unicom_prefix;
	}

	public String getUnicom_suffix() {
		return unicom_suffix;
	}

	public void setUnicom_suffix(String unicom_suffix) {
		this.unicom_suffix = unicom_suffix;
	}

	public String getUnicom_num() {
		return unicom_num;
	}

	public void setUnicom_num(String unicom_num) {
		this.unicom_num = unicom_num;
	}

	public String getTelecom_prefix() {
		return telecom_prefix;
	}

	public void setTelecom_prefix(String telecom_prefix) {
		this.telecom_prefix = telecom_prefix;
	}

	public String getTelecom_suffix() {
		return telecom_suffix;
	}

	public void setTelecom_suffix(String telecom_suffix) {
		this.telecom_suffix = telecom_suffix;
	}

	public String getTelecom_num() {
		return telecom_num;
	}

	public void setTelecom_num(String telecom_num) {
		this.telecom_num = telecom_num;
	}

	public String getReport_url() {
		return report_url;
	}

	public void setReport_url(String report_url) {
		this.report_url = report_url;
	}

	public String getReply_url() {
		return reply_url;
	}

	public void setReply_url(String reply_url) {
		this.reply_url = reply_url;
	}

	public String getIs_lock() {
		return is_lock;
	}

	public void setIs_lock(String is_lock) {
		this.is_lock = is_lock;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public Integer getIs_audit_word() {
		return is_audit_word;
	}

	public void setIs_audit_word(Integer is_audit_word) {
		this.is_audit_word = is_audit_word;
	}

	public Integer getError_count() {
		return error_count;
	}

	public void setError_count(Integer error_count) {
		if (error_count != null) {
			this.error_count = error_count;
		} else {
			this.error_count = 0;
		}
	}

	public String getSpContact_name() {
		return spContact_name;
	}

	public void setSpContact_name(String spContact_name) {
		this.spContact_name = spContact_name;
	}

	public String getSpContact_mdn() {
		return spContact_mdn;
	}

	public void setSpContact_mdn(String spContact_mdn) {
		this.spContact_mdn = spContact_mdn;
	}

	public String getSp_saler() {
		return sp_saler;
	}

	public void setSp_saler(String sp_saler) {
		this.sp_saler = sp_saler;
	}

	public String getSp_saler_mdn() {
		return sp_saler_mdn;
	}

	public void setSp_saler_mdn(String sp_saler_mdn) {
		this.sp_saler_mdn = sp_saler_mdn;
	}

	public String getAudit_type() {
		return audit_type;
	}

	public void setAudit_type(String audit_type) {
		this.audit_type = audit_type;
	}

	public String getSameNameMaxnum() {
		return sameNameMaxnum;
	}

	public void setSameNameMaxnum(String sameNameMaxnum) {
		this.sameNameMaxnum = sameNameMaxnum;
	}

	public Integer getSp_type() {
		return sp_type;
	}

	public void setSp_type(Integer sp_type) {
		this.sp_type = sp_type;
	}

	public String getSp_name() {
		return sp_name;
	}

	public void setSp_name(String sp_name) {
		this.sp_name = sp_name;
	}

	public Date getDroptime() {
		return droptime;
	}

	public void setDroptime(Date droptime) {
		this.droptime = droptime;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public String getIs_need_signture() {
		return is_need_signture;
	}

	public void setIs_need_signture(String is_need_signture) {
		this.is_need_signture = is_need_signture;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSp_industry() {
		return sp_industry;
	}

	public void setSp_industry(Integer sp_industry) {
		this.sp_industry = sp_industry;
	}

	public Integer getSp_through_status() {
		return sp_through_status;
	}

	public void setSp_through_status(Integer sp_through_status) {
		this.sp_through_status = sp_through_status;
	}

	public String getSp_website() {
		return sp_website;
	}

	public void setSp_website(String sp_website) {
		this.sp_website = sp_website;
	}

	public Integer getMasterplateNum() {
		return masterplateNum;
	}

	public void setMasterplateNum(Integer masterplateNum) {
		this.masterplateNum = masterplateNum;
	}

	public Integer getNoAuditMasterPlateNum() {
		return noAuditMasterPlateNum;
	}

	public void setNoAuditMasterPlateNum(Integer noAuditMasterPlateNum) {
		this.noAuditMasterPlateNum = noAuditMasterPlateNum;
	}

	public Integer getWhiteCount() {
		return whiteCount;
	}

	public void setWhiteCount(Integer whiteCount) {
		this.whiteCount = whiteCount;
	}

	public String getSp_app_type() {
		return sp_app_type;
	}

	public void setSp_app_type(String sp_app_type) {
		this.sp_app_type = sp_app_type;
	}

	public Integer getSp_signature_type() {
		return sp_signature_type;
	}

	public void setSp_signature_type(Integer sp_signature_type) {
		this.sp_signature_type = sp_signature_type;
	}

	public String getSp_signature_img() {
		return sp_signature_img;
	}

	public void setSp_signature_img(String sp_signature_img) {
		this.sp_signature_img = sp_signature_img;
	}

	public String getSp_app_info() {
		return sp_app_info;
	}

	public void setSp_app_info(String sp_app_info) {
		this.sp_app_info = sp_app_info;
	}

	public Integer getWeight_level() {
		return weight_level;
	}

	public void setWeight_level(Integer weight_level) {
		this.weight_level = weight_level;
	}

	public String getJoinNumber() {
		return joinNumber;
	}

	public void setJoinNumber(String joinNumber) {
		this.joinNumber = joinNumber;
	}

	public String getCorpIdCutNum() {
		return corpIdCutNum;
	}

	public void setCorpIdCutNum(String corpIdCutNum) {
		this.corpIdCutNum = corpIdCutNum;
	}

	public Integer getIsMulti() {
		return isMulti;
	}

	public void setIsMulti(Integer isMulti) {
		this.isMulti = isMulti;
	}

}
