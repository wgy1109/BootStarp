package com.chanzor.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chanzor.entity.ChannelConfig;
import com.chanzor.entity.ChargeRecord;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpCharge;
import com.chanzor.entity.SpInfo;
import com.chanzor.entity.SpOrderPackage;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.ChargeRecordService;
import com.chanzor.service.RedisService;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.TestappService;
import com.chanzor.service.UserService;
import com.chanzor.util.FormData;
import com.chanzor.util.PropertiesConfig;
import com.chanzor.util.Tools;

@Service("spInfoService")
@SuppressWarnings("unchecked")
public class SpinfoServiceImpl implements SpinfoService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;
	@Autowired
	private TestappService testAppService;
	@Autowired
	private ChargeRecordService chargeRecordService;
	@Autowired
	private RedisService redisService;
	@Autowired
	PropertiesConfig propertiesConfig;
	@Autowired
	private UserService userService;

	private static Logger log = Logger.getLogger(SpinfoServiceImpl.class);

	public List<Map<String, Object>> SpInfolistPage(PageInfo page) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SpinfoMapper.SpInfolistPage", page);
	}

	public List<SpInfo> findSpInfolistPage(PageInfo page) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("SpinfoMapper.SpInfolistPage", page);
	}

	public List<SpInfo> findSpListByUserId(Integer userId) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("SpinfoMapper.findSpListByUserId", userId);
	}

	public List<SpInfo> FindSpInfolistPage(PageInfo page) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("SpinfoMapper.SpInfolistPage", page);
	}

	public SpInfo getSpinfoById(Integer spid) throws Exception {
		return (SpInfo) daoSupport.findForObject("SpinfoMapper.getSpinfoById", spid);
	}

	public void updateSpInfo(SpInfo spInfo) throws Exception {
		daoSupport.update("SpinfoMapper.updateSpInfo", spInfo);
	}

	public Integer getSpCountByUsername(SpInfo spInfo) throws Exception {
		return (Integer) daoSupport.findForObject("SpinfoMapper.getSpCountByUsername", spInfo);
	}

	public Integer selSpCountByUserId(Integer user_id) throws Exception {
		return (Integer) daoSupport.findForObject("SpinfoMapper.selSpCountByUserId", user_id);
	}

	public void insertSpinfo(SpInfo spInfo) throws Exception {
		daoSupport.save("SpinfoMapper.insertSpinfo", spInfo);
	}

	public void insertSpSetting(SpInfo spInfo) throws Exception {
		daoSupport.save("SpinfoMapper.insertSpSetting", spInfo);
	}

	public Integer getSpBySuffix(Map map) throws Exception {
		return (Integer) daoSupport.findForObject("SpConfigMapper.getSpBySuffix", map);
	}

	public Integer getSpBySuffix2(Map map) throws Exception {
		return (Integer) daoSupport.findForObject("SpConfigMapper.getSpBySuffix2", map);
	}

	public void updateSpSetting(SpInfo spinfo) throws Exception {
		daoSupport.update("SpinfoMapper.updateSpSetting", spinfo);
	}

	public void deleteSpChannel(Integer spid) throws Exception {
		daoSupport.delete("SpConfigMapper.deleteSpChannel", spid);
	}

	public void insertChannelConfig(ChannelConfig config) throws Exception {
		daoSupport.save("SpConfigMapper.insertChannelConfig", config);
	}

	public void updateSpChannel(SpInfo spInfo) throws Exception {
		daoSupport.update("SpinfoMapper.updateSpChannel", spInfo);
	}

	public Integer updateSpConfig(SpInfo spInfo) throws Exception {
		return (Integer) daoSupport.update("SpinfoMapper.updateSpConfig", spInfo);
	}

	public Integer getChargeCountById(SpCharge spCharge) throws Exception {
		return (Integer) daoSupport.findForObject("SpinfoMapper.getChargeCountById", spCharge);
	}

	public void insertSpCharge(SpCharge spCharge) throws Exception {
		daoSupport.save("SpinfoMapper.insertSpCharge", spCharge);
	}

	public void insertSpChargeRecord(ChargeRecord chargeRecord) throws Exception {
		daoSupport.save("SpinfoMapper.insertSpChargeRecord", chargeRecord);
	}

	public void updateSpCharge(SpCharge spCharge) throws Exception {
		daoSupport.save("SpinfoMapper.updateSpCharge", spCharge);
	}

	public List<SpInfo> listPageSpCharge(PageInfo page) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("SpinfoMapper.spChargeListPage", page);

	}

	public void updateSpLeftover(SpCharge spCharge) throws Exception {
		daoSupport.update("SpinfoMapper.updateSpLeftover", spCharge);

	}

	public SpInfo getSpInfo(SpInfo spInfo) throws Exception {
		return (SpInfo) daoSupport.findForObject("SpinfoMapper.getSpInfo", spInfo);
	}

	public void updateOrderPackage(SpOrderPackage spOrderPackage) throws Exception {
		daoSupport.update("SpinfoMapper.updateOrderPackage", spOrderPackage);
	}

	public void insertOrderPackage(SpOrderPackage spOrderPackage) throws Exception {
		daoSupport.save("SpinfoMapper.insertOrderPackage", spOrderPackage);
	}

	public void deleteSpInfo(Integer spId) throws Exception {
		daoSupport.delete("SpinfoMapper.deleteSpInfo", spId);
	}

	public List<SpInfo> listSpChannelInfoPage(PageInfo page) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("SpinfoMapper.listSpChannelInfoPage", page);

	}

	public List<SpInfo> listSpConfigPage(PageInfo page) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("SpinfoMapper.listSpConfigPage", page);

	}

	public void updateSpInfostatus(Map<String, Object> map) throws Exception {
		daoSupport.update("SpinfoMapper.updateSpInfostatus", map);

	}

	public void updateSpInfoDescById(SpInfo spInfo) throws Exception {
		daoSupport.update("SpinfoMapper.updateSpInfoDescById", spInfo);

	}

	// 根据组ID查询应用列表
	public List<Map<String, Object>> getSpinfoListByGroupIdService(FormData data) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SpinfoMapper.getSpinfoListByGroupId", data);
	}

	public Map<String, Object> getSpinfoListNumByGroupIdService(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("SpinfoMapper.getSpinfoListNumByGroupId", data);
	}

	// 修改应用通道组
	public int updateGroupidByListService(FormData data) throws Exception {
		String authid = data.getString("ids");
		List<Integer> list = new ArrayList<Integer>();
		if (authid == null || authid.equals(""))
			return -1;
		String[] ids = authid.split(",");
		for (String s : ids) {
			list.add(Integer.parseInt(s));
		}
		data.put("list", list);
		return (Integer) daoSupport.update("SpinfoMapper.updateGroupidByList", data);
	}

	// public void deleteBathSpInfo(Integer spId) throws Exception{
	// for (spInfoDeleteService spInfoService : spInfoDeleteServices) {
	// spInfoService.deleteSpInfo(spId);
	// }
	// daoSupport.delete("SpinfoMapper.deleteSpInfo", spId);
	//
	// }

	// 获取用户下应用集合，传入USERID，得出应用ID，应用名称
	public List<Map<String, Object>> getOKSpinfoByUserIDService(FormData data) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SpinfoMapper.getOKSpinfoByUserID", data);
	}

	// 获取用户下已经上线应用集合，传入USERID，得出应用ID，应用名称
	public List<Map<String, Object>> getYESSpinfoByUserIDService(FormData data) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SpinfoMapper.getYESSpinfoByUserID", data);
	}

	public Integer selSpName(Integer id) throws Exception {
		return (Integer) daoSupport.findForObject("SpinfoMapper.selSpName", id);

	}

	public void updSpName(Integer value) throws Exception {
		daoSupport.update("SpinfoMapper.updSpName", value);
	}

	public synchronized void addDefaultSpInfo(SpInfo spInfo) throws Exception {
		Integer userName = this.selSpName(1);
		if (userName == null) {
			userName = 0;
		}
		userName = userName + 1;
		this.updSpName(userName);
		spInfo.setUsername(Integer.toHexString(userName));
		spInfo.setPassword(this.generateRandomStr());
		// 默认创建的都为测试用户
		spInfo.setSp_type(1);
		// 应用状态0:'未上线',1:'上线',2:'审核通过',3:'申请上线',22:'未通过'
		spInfo.setSp_through_status(0);
		List<Map<String, Object>> data = testAppService.getTestappConf();
		for (Map<String, Object> map : data) {
			if (map.get("prm_name").equals("content_type")) {
				spInfo.setContent_type((String) map.get("prm_value"));
				continue;
			} else if (map.get("prm_name").equals("channel_group_id")) {
				spInfo.setChannelgroupid(Integer.valueOf(map.get("prm_value").toString()));
				continue;
			} else if (map.get("prm_name").equals("same_mdn_max_num")) {
				spInfo.setSamemdnmaxnum((String) map.get("prm_value"));
				continue;
			} else if (map.get("prm_name").equals("min_send_total")) {
				spInfo.setMinsendtotal((String) map.get("prm_value"));
				continue;
			} else if (map.get("prm_name").equals("is_valid_bome")) {
				spInfo.setIsvalidbome(Integer.valueOf((String) map.get("prm_value")));
				continue;
			} else if (map.get("prm_name").equals("same_content_max_num")) {
				spInfo.setSamecontentmaxnum((String) map.get("prm_value"));
				continue;
			} else if (map.get("prm_name").equals("sale_price")) {
				spInfo.setSaleprice(Double.parseDouble((String) map.get("prm_value")));
				continue;
			} else if (map.get("prm_name").equals("sp_audit_type")) {
				spInfo.setAudit_type((String) map.get("prm_value"));
				continue;
			} else if (map.get("prm_name").equals("sp_audit_number")) {
				spInfo.setSp_audit_number((String) map.get("prm_value"));
				continue;
			} else if (map.get("prm_name").equals("is_report_now")) {
				spInfo.setIsreportnow(Integer.valueOf((String) map.get("prm_value")));
				continue;
			} else if (map.get("prm_name").equals("return_type")) {
				spInfo.setReturn_type((String) map.get("prm_value"));
				continue;
			} else if (map.get("prm_name").equals("deduction_type")) {
				if (spInfo.getSp_service_type().toString().equals("1")
						|| spInfo.getSp_service_type().toString().equals("3")) {
					spInfo.setDeductionType((String) map.get("prm_value"));
				}
				continue;
			} else if (map.get("prm_name").equals("is_template_send")) {
				spInfo.setIs_templete_send(Integer.valueOf(map.get("prm_value").toString()));
				continue;
			} else if (map.get("prm_name").equals("is_audit_word")) {
				spInfo.setIs_audit_word(Integer.valueOf(map.get("prm_value").toString()));
				continue;
			} else if (map.get("prm_name").equals("is_use_black_list")) {
				spInfo.setIs_use_blacklist(Integer.valueOf(map.get("prm_value").toString()));
				continue;
			} else if (map.get("prm_name").equals("receive_mode")) {
				spInfo.setReceive_mode(Integer.valueOf(map.get("prm_value").toString()));
				continue;
			} else if (map.get("prm_name").equals("weight_level")) {
				spInfo.setWeight_level(Integer.valueOf(map.get("prm_value").toString()));
				continue;
			} else if (map.get("prm_name").equals("inter_deduction_type")) {
				if (spInfo.getSp_service_type().toString().equals("2")) {
					spInfo.setDeductionType((String) map.get("prm_value"));
				}
				continue;
			}

		}
		// 新增应用分配测试扩展号
		spInfo.setChannelgroupid(Integer.valueOf(propertiesConfig.getDefault_channe_id()));
		spInfo.setExtend(this.disTestExtend());
		spInfo.setCorpIdCutNum("-1");
		// 验证方式0:默认1：签名库2：不验证
		spInfo.setIsMulti(0);
		this.insertSpinfo(spInfo);
		this.insertSpSetting(spInfo);
		if (!spInfo.isIs_default()) {
			SpCharge spCharge = new SpCharge();
			spCharge.setSp_id(spInfo.getSpid());
			spCharge.setEffect_month(Tools.date2Str(new Date(), "yyyy-MM"));
			spCharge.setLeftover_num(0);
			this.insertSpCharge(spCharge);
		}

	}

	public String generateRandomStr() {
		// 字符源，可以根据需要删减
		String generateSource = "0123456789abcdefghijklmnopqrstuvwxyz";
		String rtnStr = "";
		for (int i = 0; i < 10; i++) {
			String nowStr = String
					.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
			rtnStr += nowStr;
			generateSource = generateSource.replaceAll(nowStr, "");
		}
		return rtnStr;
	}

	public String disTestExtend() throws Exception {
		List<String> extendList = this.getTestExtend();
		String prefixExtend = this.selPrefixExtend();
		if (extendList.contains(prefixExtend + "0001")) {
			List<String> nullStr = new ArrayList<String>();
			nullStr.add(null);
			extendList.removeAll(nullStr);
			return this.findListNum(extendList, prefixExtend);
		} else {
			return prefixExtend + "0001";
		}

	}

	public String findListNum(List<String> list, String prefixExtend) {
		Pattern p = Pattern.compile(prefixExtend);
		Integer value = Integer.valueOf(list.get(list.size() - 1)) + 1;
		List<String> fixList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (Tools.notEmpty(list.get(i))) {
				if (list.get(i).startsWith(prefixExtend) && list.get(i).length() == 8) {
					fixList.add(list.get(i));
				}
			}
		}
		for (int i = fixList.size() - 1; i >= 0; i--) {
			if (Tools.notEmpty(fixList.get(i))) {
				value = Integer.valueOf(fixList.get(i)) + 1;
				break;
			}
		}
		for (int i = 0; i < fixList.size(); i++) {
			if (i < fixList.size() - 1) {
				if (Tools.notEmpty(fixList.get(i))) {
					if (fixNum(p, fixList.get(i)) + 1 != fixNum(p, fixList.get(i + 1))) {
						if (!fixNum(p, fixList.get(i)).toString().equals(fixNum(p, fixList.get(i + 1)).toString())) {
							value = Integer.valueOf(fixList.get(i)) + 1;
							break;
						}
					}
				}
			}

		}
		return value.toString();

	}

	public Integer fixNum(Pattern p, String num) {
		Matcher m = p.matcher(num);
		String tmp = m.replaceFirst("");
		Integer newStr = Integer.valueOf(tmp.replaceAll("^(0+)", ""));
		return newStr;
	}

	public List<String> getTestExtend() throws Exception {
		return (List<String>) daoSupport.findForList("SpinfoMapper.getTestExtend", null);
	}

	public String selPrefixExtend() throws Exception {
		return (String) daoSupport.findForObject("SpinfoMapper.selPrefixExtend", null);
	}

	public List<Integer> getSpIdListByForm(FormData formData) throws Exception {
		return (List<Integer>) daoSupport.findForList("SpinfoMapper.getSpIdListByForm", formData);
	}

	public String getSpUserName(Integer spId) throws Exception {
		return (String) daoSupport.findForObject("SpinfoMapper.getSpUserName", spId);

	}

	public Integer findSpInfoCompanyStatus(Integer userId) throws Exception {
		return (Integer) daoSupport.findForObject("SpinfoMapper.findSpInfoCompanyStatus", userId);
	}

	public Integer selAllSpCountByUserId(Integer userId) throws Exception {
		return (Integer) daoSupport.findForObject("SpinfoMapper.selAllSpCountByUserId", userId);
	}

	@Override
	public Map<String, Object> checkPhone(String mobile) {
		mobile = mobile.replaceAll("\n", ",");
		String mobiles[] = mobile.split(",");
		List<String> listMobiles = Stream.of(mobiles).distinct().collect(Collectors.toList());
		List<String> mobileList = listMobiles.stream().filter(phone -> {
			if (phone.length() != 11) {
				return false;
			}

			return true;
		}).collect(Collectors.toList());

		mobile = StringUtils.join(mobileList, ",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneAllCount", listMobiles.size());
		map.put("phoneStr", mobile);
		map.put("phoneCount", mobileList.size());
		return map;
	}

	@Override
	public Integer getMinSendTotalById(Integer id) throws Exception {
		return (Integer) daoSupport.findForObject("SpinfoMapper.getMinsendTotalByIdClient", id);
	}

	public boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String mdnSection = "";
		try {
			mdnSection = getmdn();
		} catch (Exception e) {
			mdnSection = "^[1][3,4,5,7,8][0-9]{9}$";
		}
		p = Pattern.compile(mdnSection); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	@Override
	public List<SpInfo> findSpListNoFrozenByUserId(Integer userId) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("SpinfoMapper.findSpListNoFrozenByUserId", userId);
	}

	@Override
	public List<SpInfo> findSpListByUserMobile(String mobile) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("SpinfoMapper.findSpListByUserMobile", mobile);
	}

	@Override
	public List<Map<String, Object>> getYesterDayMessageBySpId(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SpinfoMapper.getYesterDayMessageBySpId", map);
	}

	public List<SpInfo> findSpListByuserIdType(Map<String, Object> map) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("SpinfoMapper.findSpListByuserIdType", map);

	}

	public List<Map<String, Object>> getPreWeekMessageBySpId(FormData formData) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SpinfoMapper.getPreWeekMessageBySpId", formData);

	}

	@Override
	public List<Map<String, Object>> getPreMonthMessageBySpId(FormData formData) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SpinfoMapper.getPreMonthMessageBySpId", formData);

	}

	@Override
	public List<Map<String, Object>> selSendInfoBYMonth(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SpinfoMapper.selSendInfoBYMonth", map);

	}

	@Override
	public List<SpInfo> findSpInfoListByUserIdAndServiceType(FormData formData) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("SpinfoMapper.findSpInfoListByUserIdAndServiceType", formData);

	}

	@Override
	public void createSpInfoByNewUser(Integer userId, String mobile) throws Exception {
		String[] spnameArray = new String[] { "国内短信应用", "国际短信应用", "语音短信应用" };
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		for (int i = 1; i <= spnameArray.length; i++) {
			SpInfo spInfo = new SpInfo();
			spInfo.setSp_service_type(i);
			spInfo.setSp_name(mobile.substring(mobile.length() - 4, mobile.length()) + spnameArray[i - 1]);
			spInfo.setIs_default(true);
			log.info("设置应用默认状态为true");
			spInfo.setSp_industry(14);// 默认创建应用为其他
			spInfo.setUserId(userId);
			this.addDefaultSpInfo(spInfo);
			SpCharge spCharge = new SpCharge();
			spCharge.setSp_id(spInfo.getSpid());
			spCharge.setEffect_month(Tools.date2Str(new Date(), "yyyy-MM"));
			// 充值30条记录添加到充值记录中
			ChargeRecord chargeInfo = new ChargeRecord();
			chargeInfo.setSp_id(spInfo.getSpid());
			chargeInfo.setCharge_time(new Date());
			chargeInfo.setType(1);
			switch (spInfo.getSp_service_type()) {
			case 1:
				spCharge.setLeftover_num(Integer.valueOf(propertiesConfig.getDefault_charge_num()));
				spInfo.setLeftover_num(Integer.valueOf(propertiesConfig.getDefault_charge_num()));
				break;
			case 2:
				spCharge.setLeftover_num(Integer.valueOf(propertiesConfig.getInter_default_charge_num()));
				spInfo.setLeftover_num(Integer.valueOf(propertiesConfig.getInter_default_charge_num()));

				break;
			case 3:
				spCharge.setLeftover_num(Integer.valueOf(propertiesConfig.getDefault_charge_num()));
				spInfo.setLeftover_num(Integer.valueOf(propertiesConfig.getDefault_charge_num()));
				break;
			}
			this.insertSpCharge(spCharge);
			redisService.InsertNewRedis(spInfo, map);
		}

	}

	@Override
	public Integer selDefaultSpInfoByServiceType(FormData formData) throws Exception {
		return (Integer) daoSupport.findForObject("SpinfoMapper.selDefaultSpInfoByServiceType", formData);

	}

	@Override
	public SpInfo selectSpInfoByAccount(Map<String, Object> map) throws Exception {
		return (SpInfo) daoSupport.findForObject("SpinfoMapper.selectSpInfoByAccount", map);

	}

	@Override
	public void updSpInfoPassWord(FormData formData) throws Exception {
		daoSupport.update("SpinfoMapper.updSpInfoPassWord", formData);

	}

	@Override
	public List<Map<String, Object>> getAuditPassVoiceSpinfoByUserID(FormData data) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SpinfoMapper.getAuditPassVoiceSpinfoByUserID", data);
	}

	@Override
	public void updatespinfoIp(FormData formData) throws Exception {
		daoSupport.update("SpinfoMapper.updSpInfoIP", formData);
	}

	@Override
	public List<Map<String, Object>> getAuditPassSpinfoByUserID(FormData data) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SpinfoMapper.getAuditPassSpinfoByUserID", data);
	}

	public String getmdn() throws Exception {
		List<Map<String, Object>> mdnList = userService.getMdnSection();
		StringBuffer resultlist = new StringBuffer();
		for (Map<String, Object> map : mdnList) {
			resultlist.append(map.get("num").toString() + "、");
		}
		String[] resultall = (resultlist.substring(0, resultlist.length() - 1)).split("、");
		List<String> list = Arrays.asList(resultall);
		StringBuffer threemdn = new StringBuffer();
		StringBuffer fourmdn = new StringBuffer();
		for (String s : list) {
			if (s.length() == 4) {
				fourmdn.append(s + "|");
			} else {
				threemdn.append(s + "|");
			}
		}
		String result = "";
		if (fourmdn.length() > 0) {
			result = "^((" + threemdn.toString().substring(0, threemdn.length() - 1) + ")[0-9]{8})$|^(("
					+ fourmdn.toString().substring(0, fourmdn.length() - 1) + ")[0-9]{7})$";
		} else {
			result = "^((" + threemdn.toString().substring(0, threemdn.length() - 1) + ")[0-9]{8})$";
		}
		return result;
	}

	@Override
	public List<SpInfo> findSpListByUserAccount(String accountName) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("SpinfoMapper.findSpListByUserAccount", accountName);
	}

	@Override
	public List<String> getSpInfoDetailList(Integer id) throws Exception {
		return (List<String>) daoSupport.findForList("SpinfoMapper.getSpInfoDetailList", id);
	}

	@Override
	public void delSpInfoDetail(Integer spid) throws Exception {
		daoSupport.delete("SpinfoMapper.delSpInfoDetail", spid);
		
	}

}
