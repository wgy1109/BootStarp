package com.chanzor.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.chanzor.entity.AppBalanceReminder;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.RedisService;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.UserService;
import com.chanzor.sms.redis.RedisMessageSender;
import com.chanzor.sms.redis.message.BlackListMessage;
import com.chanzor.util.FormData;
import com.chanzor.util.PropertiesConfig;
import com.chanzor.util.Tools;

@Service
public class RedisServiceImpl implements RedisService {
	@Autowired
	private SpinfoService spInfoService;
	@Autowired
	private UserService userInfoService;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private RedisMessageSender redisMessageSender;
	@Autowired
	PropertiesConfig propertiesConfig;

	public void InsertRedis(SpInfo spInfo, Map<String, Object> map) throws Exception {
		String username = spInfoService.getSpUserName(spInfo.getSpid());
		if (redisTemplate.hasKey("SMS.USER-" + username)) {
			// redisTemplate.rename(oldKey, newKey);
			Map<String, String> redis = new HashMap<String, String>();
			// 应用用户名
			if (Tools.notEmpty(spInfo.getUsername())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "name", spInfo.getUsername());
			}
			// 应用密码
			if (Tools.notEmpty(spInfo.getPassword())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "password", spInfo.getPassword());
				redisTemplate.opsForHash().put("SMS.USER-" + username, "passwordMD5",
						Tools.encodeByMD5(spInfo.getPassword()));
			}
			if (Tools.notEmpty(spInfo.getSp_name())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "spName", spInfo.getSp_name());
			}
			// 应用签名
			if (Tools.notEmpty(spInfo.getSignature())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "signature", spInfo.getSignature());
			}
			// 单号码限制次数
			if (Tools.notEmpty(spInfo.getSamemdnmaxnum())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "phoneCount", spInfo.getSamemdnmaxnum());
			}
			// 短信优先级，范围0-1000，默认3。
			// redisTemplate.opsForHash().put("SMS.USER-" + username,
			// "priority", "3");
			// ////////////////////////////////////////////////
			// 用户最少发送人数
			if (Tools.notEmpty(spInfo.getMinsendtotal())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "minSendTotal", spInfo.getMinsendtotal());
			}
			// 付费方式：预付费，后付费
			if (Tools.notEmpty(spInfo.getReturn_type())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "returnType", spInfo.getReturn_type());
			}
			// 扣费模式 成功计费提交计费
			if (Tools.notEmpty(spInfo.getDeductionType())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "deductionType", spInfo.getDeductionType());
			}
			// 发送类型 1营销2验证码3行业通知4订单
			if (Tools.notEmpty(spInfo.getContent_type())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "contentType", spInfo.getContent_type());
			}
			// 短信单价
			if (spInfo.getSaleprice() > 0) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "salePrice",
						String.valueOf(spInfo.getSaleprice()));
			}
			// 审核条数
			if (Tools.notEmpty(spInfo.getSp_audit_number())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "spAuditNumber", spInfo.getSp_audit_number());
			}
			// 是否启用内容模板 1启用0禁用
			if (spInfo.getIs_templete_send() != null) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "isAuditWord",
						String.valueOf(spInfo.getIs_templete_send()));
			}
			// 是否启用敏感词1启用0禁用
			if (spInfo.getIs_audit_word() != null) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "isAuditWord",
						String.valueOf(spInfo.getIs_audit_word()));
			}
			// 是否启用黑名单1启用0禁用
			if (spInfo.getIs_use_blacklist() != null) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "isUseBlackList",
						String.valueOf(spInfo.getIs_use_blacklist()));
			}

			// 是否启用验证码炸弹机制1启用0禁用
			if (spInfo.getIsvalidbome() != null) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "isValidaBome",
						String.valueOf(spInfo.getIsvalidbome()));
			}
			// 新增接收方式1：不接受2：推送3：主动获取
			if (spInfo.getReceive_mode() != null) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "receiveMode",
						String.valueOf(spInfo.getReceive_mode()));
			}
			// 企业扩展号码
			if (Tools.notEmpty(spInfo.getExtend())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "corpId", spInfo.getExtend());
				Map<String, String> corpId = new HashMap<String, String>();
				corpId.put(spInfo.getExtend(), spInfo.getUsername());
				redisTemplate.opsForHash().putAll("SMS.USER-CORPID", corpId);
			}
			// 1正常 0欠费 -1下线
			if (Tools.notEmpty(spInfo.getRedisStatus())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "status", spInfo.getRedisStatus());
			}
			if (spInfo.getNumber() != null) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "unsend", String.valueOf(spInfo.getNumber()));
			}
			// CMPP接入码校验
			if (Tools.notEmpty(spInfo.getJoinNumber())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "joinNumber", spInfo.getJoinNumber());
			}
			// 上行位数截取
			if (Tools.notEmpty(spInfo.getCorpIdCutNum())) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "corpIdCutNum", spInfo.getCorpIdCutNum());
			}
			// 验证方式0:默认1：签名库2：不验证
			if (spInfo.getIsMulti() != null) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "isMulti", spInfo.getIsMulti());
			}
			if (spInfo.getSp_service_type() != null) {
				redisTemplate.opsForHash().put("SMS.USER-" + username, "spServiceType", spInfo.getSp_service_type());
			}
			if (spInfo.getChargeCount() != null) {
				if (redisTemplate.opsForHash().hasKey("SMS.USER-" + username, "chargeCount")) {
					redisTemplate.opsForHash().increment("SMS.USER-" + username, "chargeCount",
							spInfo.getChargeCount());
				} else {
					redisTemplate.opsForHash().put("SMS.USER-" + username, "chargeCount", spInfo.getChargeCount());
				}

			}
		}

	}

	@Override
	public void InsertNewRedis(SpInfo spInfo, Map<String, Object> map) throws Exception {
		String username = spInfoService.getSpUserName(spInfo.getSpid());
		Map<String, String> redis = new HashMap<String, String>();
		// 应用用户名
		if (spInfo.getSpid() != null) {
			redis.put("id", spInfo.getSpid().toString());
		}
		if (Tools.notEmpty(spInfo.getUsername())) {
			redis.put("name", spInfo.getUsername());
		}
		// 应用密码
		if (Tools.notEmpty(spInfo.getPassword())) {
			redis.put("password", spInfo.getPassword());
			redis.put("passwordMD5", Tools.encodeByMD5(spInfo.getPassword()));
		}
		// 应用签名
		if (Tools.notEmpty(spInfo.getSignature())) {
			redis.put("signature", spInfo.getSignature());
		}
		// 用户最少发送人数
		if (Tools.notEmpty(spInfo.getMinsendtotal())) {
			redis.put("phoneCount", spInfo.getMinsendtotal());
		}
		if (Tools.notEmpty(spInfo.getRedisStatus())) {
			redis.put("status", spInfo.getRedisStatus());
		}
		if (Tools.notEmpty(spInfo.getMinsendtotal())) {
			redis.put("minSendTotal", spInfo.getMinsendtotal());
		}
		// 付费方式：预付费，后付费
		if (Tools.notEmpty(spInfo.getReturn_type())) {
			redis.put("returnType", spInfo.getReturn_type());
		}
		// 扣费模式 成功计费提交计费
		if (Tools.notEmpty(spInfo.getDeductionType())) {
			redis.put("deductionType", spInfo.getDeductionType());
		}
		// 发送类型 1营销2验证码3行业通知4订单
		if (Tools.notEmpty(spInfo.getContent_type())) {
			redis.put("contentType", spInfo.getContent_type());
		}
		// 短信单价
		if (spInfo.getSaleprice() > 0) {
			String salePrice = String.valueOf(spInfo.getSaleprice());
			redis.put("salePrice", salePrice);
		}
		// 审核条数
		if (Tools.notEmpty(spInfo.getSp_audit_number())) {
			redis.put("spAuditNumber", spInfo.getSp_audit_number());
		}
		// 是否启用内容模板 1启用0禁用
		if (spInfo.getIs_templete_send() != null) {
			redis.put("isTemplateSend", String.valueOf(spInfo.getIs_templete_send()));
		}
		// 是否启用敏感词1启用0禁用
		if (spInfo.getIs_audit_word() != null) {
			redis.put("isAuditWord", String.valueOf(spInfo.getIs_audit_word()));
		}
		// 是否启用黑名单1启用0禁用
		if (spInfo.getIs_use_blacklist() != null) {
			redis.put("isUseBlackList", String.valueOf(spInfo.getIs_use_blacklist()));
		}

		// 是否启用验证码炸弹机制1启用0禁用
		if (spInfo.getIsvalidbome() != null) {
			redis.put("isValidaBome", String.valueOf(spInfo.getIsvalidbome()));
		}
		// 新增接收方式1：不接受2：推送3：主动获取
		if (spInfo.getReceive_mode() != null) {
			redis.put("receiveMode", String.valueOf(spInfo.getReceive_mode()));
		}
		// 单号码限制次数
		if (Tools.notEmpty(spInfo.getSamemdnmaxnum())) {
			redis.put("phoneCount", spInfo.getSamemdnmaxnum());
		}
		if (spInfo.getLeftover_num() != null && spInfo.getLeftover_num() != 0) {
			redis.put("unsend", String.valueOf(spInfo.getLeftover_num()));
		} else {
			redis.put("unsend", "0");
		}
		if (map != null && map.get("mobile") != null) {
			redis.put("bigName", (String) map.get("mobile"));
		}
		if (Tools.notEmpty(spInfo.getSp_name())) {
			redis.put("spName", spInfo.getSp_name());
		}
		// 企业扩展号码
		if (Tools.notEmpty(spInfo.getExtend())) {
			redis.put("corpId", spInfo.getExtend());
			Map<String, String> corpId = new HashMap<String, String>();
			corpId.put(spInfo.getExtend(), spInfo.getUsername());
			redisTemplate.opsForHash().putAll("SMS.USER-CORPID", corpId);
		}
		if (spInfo.getIsMulti() != null) {
			redis.put("isMulti", spInfo.getIsMulti().toString());
		}
		if (spInfo.getWeight_level() != null) {
			redisTemplate.opsForHash().put("SMS.USER-" + username, "priority", spInfo.getWeight_level().toString());
		}
		if (spInfo.getSp_service_type() != null) {
			redis.put("spServiceType", spInfo.getSp_service_type().toString());
		}
		// 应用信息新增用户名（手机号）
		Map<String, Object> userInfo = userInfoService.findUserInfoById(spInfo.getUserId());
		redis.put("userAccount", userInfo.get("account_name").toString());
		redis.put("chargeCount", "0");
		redis.put("limitAllYzmCount", "0");
		redis.put("appYzmLimitCount", "0");
		redis.put("screenCount", "0");
		// 应用信息新增通道组ID
		redis.put("groupId", String.valueOf(propertiesConfig.getDefault_channe_id()));
		redis.put("joinNumber", "");
		redis.put("corpIdCutNum", "-1");
		redis.put("sendSuccessRate", "0");
		redis.put("submitSuccessRate", "0");
		redis.put("statisticalTimeLength", "0");
		redis.put("cmpp_max_connect", "2");
		redis.put("cmpp_tps", "160");
		redisTemplate.opsForHash().putAll("SMS.USER-" + spInfo.getUsername(), redis);
	}

	@SuppressWarnings("static-access")
	@Override
	public void insertUserRedis(Map<String, Object> map, SpInfo spInfo, String Type) throws Exception {
		Map<String, String> info = new HashMap<String, String>();
		info.put("password", (String) map.get("password"));
		info.put("passwordMD5", Tools.encodeByMD5((String) map.get("password")));
		info.put("userId", map.get("userId").toString());
		Map<String, Object> userInfo = userInfoService.findUserInfoById(Integer.valueOf(map.get("userId").toString()));
		if (Type.equals("ADD")) {
			redisTemplate.opsForHash().putAll("USER-" + userInfo.get("account_name"), info);
		}
		if (Type.equals("RESET")) {
			redisTemplate.opsForHash().putAll("USER-" + userInfo.get("account_name"), info);
		}

	}

	@Override
	public void insSysTemplete(FormData fromData) throws Exception {
		SpInfo spInfo = spInfoService.getSpinfoById(Integer.valueOf(fromData.get("spid").toString()));
		String content = fromData.getString("content");
		Map<String, String> sysTemplete = new HashMap<String, String>();
		sysTemplete.put(fromData.getString("templeteId"), content);
		redisTemplate.opsForHash().putAll("SMS.USER-" + spInfo.getUsername() + "-TEMPLATE", sysTemplete);
	}

	@Override
	public void insSysWhiteList(Integer spid, String phoneNum) throws Exception {
		SpInfo spInfo = spInfoService.getSpinfoById(spid);
		redisTemplate.opsForSet().add("SMS.USER-" + spInfo.getUsername() + "-WHITELIST", phoneNum);
	}

	@Override
	public void delWhiteList(Integer spid, String phoneNum) throws Exception {
		SpInfo spInfo = spInfoService.getSpinfoById(spid);
		if (redisTemplate.hasKey("SMS.USER-" + spInfo.getUsername() + "-WHITELIST")) {
			redisTemplate.opsForSet().remove("SMS.USER-" + spInfo.getUsername() + "-WHITELIST", phoneNum);
		}
	}

	@Override
	public void delSpInfoInRedis(Integer spId, List<String> detailList) throws Exception {
		SpInfo spInfo = spInfoService.getSpinfoById(spId);
		if (redisTemplate.hasKey("SMS.USER-" + spInfo.getUsername())) {
			redisTemplate.delete("SMS.USER-" + spInfo.getUsername());
		}
		if (redisTemplate.hasKey("SMS.USER-" + spInfo.getUsername() + "-WHITELIST")) {
			redisTemplate.delete("SMS.USER-" + spInfo.getUsername() + "-WHITELIST");
		}
		if (redisTemplate.hasKey("SMS.USER-" + spInfo.getUsername() + "-BLACKLIST")) {
			redisTemplate.delete("SMS.USER-" + spInfo.getUsername() + "-BLACKLIST");
		}
		if (redisTemplate.hasKey("SMS.USER-" + spInfo.getUsername() + "-TEMPLATE")) {
			redisTemplate.delete("SMS.USER-" + spInfo.getUsername() + "-TEMPLATE");
		}
		if (redisTemplate.hasKey("SMS.USER-CORPID")) {
			for (String extend : detailList) {
				redisTemplate.opsForHash().delete("SMS.USER-CORPID", extend);
			}
			redisTemplate.opsForHash().delete("SMS.USER-CORPID", spInfo.getExtend());
		}

	}

	@Override
	public void editBlackList(Map<String, Object> oldMap, FormData formData) throws Exception {
		SpInfo spinfo = spInfoService.getSpinfoById(Integer.parseInt(formData.getString("spid").toString()));
		if (oldMap != null) {
			if (oldMap.get("target_id") != null) {
				SpInfo oldSpInfo = spInfoService.getSpinfoById(Integer.valueOf(oldMap.get("target_id").toString()));
				if (oldSpInfo != null) {
					if (redisTemplate.hasKey("SMS.USER-" + oldSpInfo.getUsername() + "-BLACKLIST")) {
						redisTemplate.opsForSet().remove("SMS.USER-" + oldSpInfo.getUsername() + "-BLACKLIST",
								oldMap.get("mdn"));
					}
				}
			}
		}
		redisTemplate.opsForSet().add("SMS.USER-" + spinfo.getUsername() + "-BLACKLIST",
				formData.get("mdn").toString());
	}

	@Override
	public void delBlackList(FormData formData) throws Exception {
		SpInfo spinfo = spInfoService.getSpinfoById(Integer.valueOf(formData.get("spid").toString()));
		if (redisTemplate.hasKey("SMS.USER-" + spinfo.getUsername() + "-BLACKLIST")) {
			redisTemplate.opsForSet().remove("SMS.USER-" + spinfo.getUsername() + "-BLACKLIST", formData.get("mdn"));
		}
	}

	public void sendBlackInfo(Integer Type, Integer eventType, Integer id, String blackPhoneNumber) {
		BlackListMessage blackList = new BlackListMessage();
		blackList.setType(Type);
		blackList.setEventType(eventType);
		blackList.setId(Integer.valueOf(id));
		blackList.setPhone(blackPhoneNumber);
		redisMessageSender.sendMessage(blackList);
	}

	@Override
	public Integer findRedisUnsend(String userName) throws Exception {
		Integer num = 0;
		if (redisTemplate.hasKey("SMS.USER-" + userName)) {
			if (redisTemplate.opsForHash().get("SMS.USER-" + userName, "unsend") != null) {
				num = Integer.valueOf(redisTemplate.opsForHash().get("SMS.USER-" + userName, "unsend").toString());
			}
		}
		return num;
	}

	@Override
	public Integer findRedisPhoneCount(String userName) throws Exception {
		Integer phoneCount = Integer
				.valueOf(redisTemplate.opsForHash().get("SMS.USER-" + userName, "phoneCount").toString());
		return phoneCount;
	}

	// 废弃此方法，因为账号与手机号可以不同
	/*
	 * @Override public void updBigName(String oldMobile, String newMobile)
	 * throws Exception { if (redisTemplate.hasKey("USER-" + oldMobile)) {
	 * redisTemplate.rename("USER-" + oldMobile, "USER-" + newMobile); }
	 * List<SpInfo> list = spInfoService.findSpListByUserMobile(newMobile); for
	 * (SpInfo spInfo : list) { if (redisTemplate.hasKey("SMS.USER-" +
	 * spInfo.getUsername())) { redisTemplate.opsForHash().put("SMS.USER-" +
	 * spInfo.getUsername(), "userAccount", newMobile); } } }
	 */

	@Override
	public long addUnsend(Integer spid, Integer leftOverNum) throws Exception {
		SpInfo spInfo = spInfoService.getSpinfoById(spid);
		if (redisTemplate.hasKey("SMS.USER-" + spInfo.getUsername())) {
			return redisTemplate.opsForHash().increment("SMS.USER-" + spInfo.getUsername(), "unsend", leftOverNum);
		} else {
			return spInfo.getLeftover_num();
		}

	}

	@Override
	public void insAppBalanceReminder(AppBalanceReminder appBalanceReminder) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("reminderBalance", appBalanceReminder.getBalance().toString());
		map.put("reminderPhone", appBalanceReminder.getReminderPhone());
		SpInfo spInfo = spInfoService.getSpinfoById(appBalanceReminder.getSpId());
		redisTemplate.opsForHash().putAll("SMS.USER-" + spInfo.getUsername(), map);

	}

	@Override
	public void updSpInfoPassWord(FormData formData) throws Exception {
		if (formData.get("spId") != null && formData.get("password") != null) {
			SpInfo spInfo = spInfoService.getSpinfoById(Integer.valueOf(formData.getString("spId")));
			if (redisTemplate.hasKey("SMS.USER-" + spInfo.getUsername())) {
				redisTemplate.opsForHash().put("SMS.USER-" + spInfo.getUsername(), "password",
						formData.getString("password"));
				redisTemplate.opsForHash().put("SMS.USER-" + spInfo.getUsername(), "passwordMD5",
						Tools.encodeByMD5(formData.getString("password")));
			}

		}
	}

	@Override
	public void updSpInfoIp(FormData formData) throws Exception {
		if (formData.get("spId") != null) {
			SpInfo spInfo = spInfoService.getSpinfoById(Integer.valueOf(formData.getString("spId")));
			if (redisTemplate.hasKey("SMS.USER-" + spInfo.getUsername())) {
				redisTemplate.opsForHash().put("SMS.USER-" + spInfo.getUsername(), "ip", formData.getString("ip"));
			}
		}
	}

	@Override
	public void updateBigAccount(String oldAccount, String newAccount) throws Exception {
		if (redisTemplate.hasKey("USER-" + oldAccount)) {
			redisTemplate.rename("USER-" + oldAccount, "USER-" + newAccount);
		}
		List<SpInfo> list = spInfoService.findSpListByUserAccount(newAccount);
		for (SpInfo spInfo : list) {
			if (redisTemplate.hasKey("SMS.USER-" + spInfo.getUsername())) {
				redisTemplate.opsForHash().put("SMS.USER-" + spInfo.getUsername(), "userAccount", newAccount);
			}
		}
	}

	@Override
	public String saveCustomizedMessageInRedis(FormData formData, List<String> contentList) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String key = formData.get("userId").toString() + formData.get("spId") + format.format(new Date());
		redisTemplate.opsForList().leftPushAll(key, contentList);
		redisTemplate.expire(key, 10, TimeUnit.MINUTES);
		return key;
	}

	@Override
	public String getCustomizedMessageByRedis(String key) {
		String contentList = StringUtils
				.join(redisTemplate.opsForList().range(key, 0, redisTemplate.opsForList().size(key)), "\t\n");
		return contentList;
	}
}
