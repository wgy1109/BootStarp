package com.chanzor.controller.spInfoController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.AppBalanceReminder;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.AppBalanceReminderService;
import com.chanzor.service.RedisService;
import com.chanzor.service.SendTaskInfoService;
import com.chanzor.service.SmsMasterplateClientService;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.UserService;
import com.chanzor.service.WhitelistService;
import com.chanzor.util.Const;
import com.chanzor.util.FileUtil;
import com.chanzor.util.FormData;
import com.chanzor.util.Tools;

@Controller
@RequestMapping("spInfo")
@SuppressWarnings("unchecked")
public class SpInfoController extends BaseController {
	@Autowired
	private SpinfoService spinfoService;
	@Autowired
	private SmsMasterplateClientService smsMasterplateClientService;
	@Autowired
	private SendTaskInfoService sendTaskInfoService;
	@Autowired
	private WhitelistService whitelistService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private UserService userService;
	@Autowired
	private AppBalanceReminderService appBalanceReminderService;

	// 获取应用列表
	@RequestMapping(value = "/index")
	public ModelAndView listSpInfo(SpInfo spInfo, PageInfo pageInfo, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main/index");
		return mv;
	}

	// 获取应用详情
	@RequestMapping(value = "/showSpInfoView")
	public @ResponseBody Map<String, Object> showSpiInfoView(@RequestParam Integer spId) throws Exception {
		SpInfo spInfo = spinfoService.getSpinfoById(spId);
		Map<String, Object> mv = new HashMap<String, Object>();
		mv.put("spInfo", spInfo);
		return mv;
	}

	@RequestMapping(value = "/findSpInfoBySpId")
	public @ResponseBody SpInfo findSpInfoBySpId(@RequestParam Integer spId) throws Exception {
		SpInfo spInfo = spinfoService.getSpinfoById(spId);
		return spInfo;
	}

	// 获取应用列表
	@RequestMapping(value = "/findspInfo")
	public ModelAndView findspInfo(Integer spId, PageInfo pageInfo, HttpSession session) throws Exception {
		SpInfo spInfo = spinfoService.getSpinfoById(spId);
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		Map<String, Object> userImg = userService.findUserInfoById(Integer.valueOf(userInfo.get("id").toString()));
		Map<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("spId", spId);
		switch (spInfo.getSp_service_type()) {
		case 1:
			infoMap.put("tableName", "sms_send_statistics");
			break;
		case 2:
			infoMap.put("tableName", "inter_sms_send_statistics");
			break;
		case 3:
			infoMap.put("tableName", "sms_send_statistics");
			break;
		}
		List<Map<String, Object>> sendMap = spinfoService.getYesterDayMessageBySpId(infoMap);
		Integer fail_count = 0;
		Integer submit_count = 0;
		Integer success_count = 0;
		Integer sended_count = 0;
		Integer unknown_count = 0;
		if (sendMap != null) {
			for (Map<String, Object> map : sendMap) {
				fail_count += Integer.valueOf(map.get("fail_count").toString());
				submit_count += Integer.valueOf(map.get("submit_count").toString());
				success_count += Integer.valueOf(map.get("success_count").toString());
				sended_count += Integer.valueOf(map.get("sended_count").toString());
				unknown_count += Integer.valueOf(map.get("unknown_count").toString());
			}
		}
		spInfo.setError_count(fail_count);
		spInfo.setSubmitCount(submit_count);
		spInfo.setSuccessCount(success_count);
		spInfo.setSendedCount(sended_count);
		spInfo.setUnKnownCount(unknown_count);
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = userService.getUserBaseInfo(getFormData());
		if ("00".equals((String) map.get("code"))) {
			mv.addObject("baseInfo", map.get("baseInfo"));
		} else {
			mv.addObject("baseInfo", new HashMap<String, Object>());
		}
		mv.addObject("auth", userService.getComAuthInfo(getFormData()));
		mv.setViewName("spInfo/spInfoView");
		if (userImg.get("photo_img") != null) {
			mv.addObject("photo_img", properties.getNginx_url() + userImg.get("photo_img"));
		}
		mv.addObject("spInfo", spInfo);
		return mv;
	}

	@RequestMapping("updateAppImage")
	@ResponseBody
	public String updateImage(MultipartFile file, HttpServletRequest request) throws Exception {
		if (file == null || file.getSize() / 1024 / 1024 > 10) {
			return "";
		}
		FormData data = new FormData(request);
		String src = FileUtil.updaloadPic(file,
				properties.getService_nginx_url() + "appSignCert/" + data.get("userId") + Tools.getRandomNum(),
				data.getString("filename"));
		if (!"".equals(src)) {
			src = src.substring(src.indexOf("appSignCert"));
		}
		return src;
	}

	// 我的首页
	@RequestMapping(value = "/mySpinfo")
	public ModelAndView mySpinfo(SpInfo spInfo, PageInfo pageInfo, HttpSession session) throws Exception {
		String landType = (String) session.getAttribute("LandingType");
		if (landType != null && landType.equals(Const.SPINFO)) {
			SpInfo sessionSpInfo = (SpInfo) session.getAttribute(Const.SESSIONSPINFO);
			return new ModelAndView("redirect:findspInfo?spId=" + sessionSpInfo.getSpid());
		}
		Integer userid = (Integer) session.getAttribute("user_id");
		FormData formData = getFormData();
		formData.put("userId", userid);
		pageInfo.setFormData(formData);
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		Map<String, Object> userImg = userService.findUserInfoById(Integer.valueOf(userInfo.get("id").toString()));
		List<SpInfo> spList = spinfoService.findSpListByUserId(Integer.valueOf(userInfo.get("id").toString()));
		Map<String, Object> infoMap = new HashMap<String, Object>();
		for (SpInfo spinfo : spList) {
			infoMap.put("spId", spinfo.getSpid());
			switch (spinfo.getSp_service_type()) {
			case 1:
				infoMap.put("tableName", "sms_send_statistics");
				break;
			case 2:
				infoMap.put("tableName", "inter_sms_send_statistics");
				break;
			case 3:
				infoMap.put("tableName", "sms_send_statistics");
				break;
			}
			// if ("0".equals(spinfo.getDeductionType())) { // 提交计费的应用
			// 获取当前应用昨天发送数量
			spinfo.setYesterDaySendTask(sendTaskInfoService.getTackInfoByYesterDay0(infoMap) == null ? 0
					: sendTaskInfoService.getTackInfoByYesterDay0(infoMap));
			// } else {
			// spinfo.setYesterDaySendTask(sendTaskInfoService.getTackInfoByYesterDay(infoMap)
			// == null ? 0
			// : sendTaskInfoService.getTackInfoByYesterDay(infoMap));
			// }
		}
		List<Map<String, Object>> businessList = new ArrayList<Map<String, Object>>();
		boolean bool = false;
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = userService.getUserBaseInfo(getFormData());
		if ("00".equals((String) map.get("code"))) {
			mv.addObject("baseInfo", map.get("baseInfo"));
		} else {
			mv.addObject("baseInfo", new HashMap<String, Object>());
		}
		mv.addObject("auth", userService.getComAuthInfo(getFormData()));
		mv.addObject("SERVER_BUSINESS_SRC", properties.getS_nginx_url());
		mv.addObject("bool", bool);
		mv.addObject("businessList", businessList);
		if (userImg.get("photo_img") != null) {
			mv.addObject("photo_img", properties.getNginx_url() + userImg.get("photo_img"));
		}
		mv.addObject("mobile", userInfo.get("mobile"));
		mv.addObject("spList", spList);
		mv.addObject("account_name", userInfo.get("account_name"));
		mv.setViewName("spInfo/mySpinfo");
		return mv;
	}

	@RequestMapping(value = "/mySpinfoSendCount")
	public @ResponseBody Map<String, Object> mySpinfoSendCount(SpInfo spInfo, PageInfo pageInfo, HttpSession session)
			throws Exception {
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		List<SpInfo> spList = spinfoService.findSpListByUserId(Integer.valueOf(userInfo.get("id").toString()));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> allInfo = new ArrayList<Map<String, Object>>();
		for (SpInfo childInfo : spList) {
			Map<String, Object> infoMap = new HashMap<String, Object>();
			infoMap.put("spId", childInfo.getSpid());
			switch (childInfo.getSp_service_type()) {
			case 1:
				infoMap.put("tableName", "sms_send_statistics");
				break;
			case 2:
				infoMap.put("tableName", "inter_sms_send_statistics");
				break;
			case 3:
				infoMap.put("tableName", "sms_send_statistics");
				break;
			}
			List<Map<String, Object>> spInfoMap = spinfoService.selSendInfoBYMonth(infoMap);
			Map<String, Object> fixMap = new HashMap<String, Object>();
			fixMap.put("name", childInfo.getSp_name());
			List<Integer> sendInfo = new ArrayList<Integer>();
			for (Integer i = 1; i <= 12; i++) {
				boolean isExit = false;
				String fixIndex = "";
				if (i < 10) {
					fixIndex = "0" + i;
				} else {
					fixIndex = i.toString();
				}
				for (Map<String, Object> spMap : spInfoMap) {
					if (spMap.get("month").equals(fixIndex)) {
						sendInfo.add(Integer.valueOf(spMap.get("count").toString()));
						isExit = true;
						continue;
					}
				}
				if (!isExit) {
					sendInfo.add(0);
				}
			}
			fixMap.put("data", sendInfo);
			allInfo.add(fixMap);
		}
		map.put("data", allInfo);
		return map;
	}

	/**
	 * 获取应用信息列表
	 * 
	 * @param spInfo
	 * @param pageInfo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/spInfoList")
	public @ResponseBody Map<String, Object> spInfoList(SpInfo spInfo, PageInfo pageInfo, HttpSession session)
			throws Exception {
		// 检查是否用应用账号登陆
		if (checkLandType(session)) {
			spInfo.setSpid(((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formData = getFormData();
		formData.put("userId", userid);
		pageInfo.setFormData(formData);
		if (spInfo.getSpid() != null) {
			formData.put("spid", spInfo.getSpid());
		}

		// subAccount
		Map<String, Object> currUser = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		if ((Integer) currUser.get("is_sub_account") == 1) {
			pageInfo.getFormData().put("subAccountAppIds", (String) session.getAttribute(Const.APPIDSSTR));
		}
		List<SpInfo> spList = spinfoService.findSpInfolistPage(pageInfo);
		for (SpInfo spinfo : spList) {
			formData.put("spid", spinfo.getSpid());
			spinfo.setWhiteCount(Integer.valueOf(whitelistService.getWhitelistCount(formData)) == null ? 0
					: whitelistService.getWhitelistCount(formData));
			Map<String, Object> plateMap = smsMasterplateClientService.getNumBySpidService(spinfo.getSpid());
			if (plateMap != null) {
				spinfo.setMasterplateNum(
						Integer.parseInt(plateMap.get("allnum") == null ? "0" : plateMap.get("allnum").toString()));
			} else {
				spinfo.setMasterplateNum(0);
			}
		}
		map.put("spInfo", spList);
		map.put("page", pageInfo);
		map.put("recordsTotal", pageInfo.getTotalSize());
		map.put("recordsFiltered", pageInfo.getTotalSize());
		return map;
	}

	/**
	 * 添加应用
	 * 
	 * @return
	 */
	@RequestMapping("/addSpInfo")
	public ModelAndView appSpInfo() {
		ModelAndView mod = new ModelAndView();
		mod.setViewName("spInfo/addSpInfo");
		return mod;
	}

	/**
	 * 添加应用方法
	 * 
	 * @param spInfo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addSpInfoMth")
	public @ResponseBody String addSpInfo(SpInfo spInfo, HttpSession session) throws Exception {
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		spInfo.setUserId(userid);
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		if (Tools.isEmpty(spInfo.getSp_app_type()) || spInfo.getSp_industry() == 0) {
			return "400";
		}
		// if (spinfoService.selSpCountByUserId(userid) >= 10) {
		// return "500";
		// }
		if (spInfo.getSpid() != null) {
			spinfoService.updateSpInfo(spInfo);
			redisService.InsertRedis(spInfo, userInfo);
			return "200";
		}
		FormData formData = getFormData();
		if (formData.get("sp_fix_name") != null && formData.get("sp_fix_name") != "0") {
			spInfo.setSp_name(spInfo.getSp_name() + formData.getString("sp_fix_name"));
		}
		spinfoService.addDefaultSpInfo(spInfo);
		// 将应用信息添加到redis中
		redisService.InsertNewRedis(spInfo, userInfo);
		return "200";
	}

	/**
	 * 跳转应用申请上线
	 */
	@RequestMapping("/SpInfoApply")
	public ModelAndView SpInfoApply(Integer spId, HttpSession session) throws Exception {
		ModelAndView mod = new ModelAndView();
		mod.setViewName("spInfo/applySpInfo");
		mod.addObject("spId", spId);
		return mod;
	}

	@RequestMapping("/updateSpInfo")
	public @ResponseBody Map<String, Object> updateSpInfo(SpInfo spInfo, HttpSession session) throws Exception {
		spInfo.setSp_through_status(3);
		if (!spInfo.getSignature().startsWith("【") && !spInfo.getSignature().endsWith("】")) {
			spInfo.setSignature("【" + spInfo.getSignature() + "】");
		}
		spinfoService.updateSpInfo(spInfo);
		// 应用申请上线时修改redis中的值
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		redisService.InsertRedis(spInfo, userInfo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "200");
		map.put("spId", spInfo.getSpid());
		return map;
	}

	@RequestMapping("/updateInterSpInfo")
	public @ResponseBody Map<String, Object> updateInterSpInfo(SpInfo spInfo, HttpSession session) throws Exception {
		spInfo.setSp_through_status(3);
		spinfoService.updateSpInfo(spInfo);
		// 应用申请上线时修改redis中的值
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		redisService.InsertRedis(spInfo, userInfo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "200");
		map.put("spId", spInfo.getSpid());
		return map;
	}

	@RequestMapping("/updateDelSpInfo")
	public @ResponseBody String updateDelSpInfo(SpInfo spInfo, HttpSession session) throws Exception {
		SpInfo spinfo = spinfoService.getSpinfoById(spInfo.getSpid());
		if (spinfo.getLeftover_num() >= 0) {
			spInfo.setStatus("2");
			spinfoService.updateSpInfo(spInfo);
			List<String> detailList = spinfoService.getSpInfoDetailList(spInfo.getSpid());
			spinfoService.delSpInfoDetail(spInfo.getSpid());
			// 应用删除后删除redis中信息
			redisService.delSpInfoInRedis(spInfo.getSpid(),detailList);
		} else {
			return "205";
		}
		return "200";
	}

	@RequestMapping("/findSpInfoCompanyStatus")
	public @ResponseBody Map<String, Object> findSpInfoCompanyStatus(Integer spId, HttpSession session)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		SpInfo spInfo = spinfoService.getSpinfoById(spId);
		Integer status = spinfoService.findSpInfoCompanyStatus(spInfo.getUserId());
		map.put("spInfo", spInfo);
		map.put("status", status);
		return map;
	}

	@RequestMapping("/mainLoad")
	public @ResponseBody List<Map<String, Object>> mainLoad(Integer spId, HttpSession session) throws Exception {
		FormData formData = getFormData();
		SpInfo spInfo = spinfoService.getSpinfoById(Integer.valueOf(formData.get("spId").toString()));
		switch (spInfo.getSp_service_type()) {
		case 1:
			formData.put("tableName", "sms_send_statistics");
			break;
		case 2:
			formData.put("tableName", "inter_sms_send_statistics");
			break;
		case 3:
			formData.put("tableName", "voice_sms_send_statistics");
			break;
		}
		return spinfoService.getPreWeekMessageBySpId(formData);
	}

	@RequestMapping("/mainMonthLoad")
	public @ResponseBody List<Map<String, Object>> mainMonthLoad(Integer spId, HttpSession session) throws Exception {
		FormData formData = getFormData();
		SpInfo spInfo = spinfoService.getSpinfoById(Integer.valueOf(formData.get("spId").toString()));
		switch (spInfo.getSp_service_type()) {
		case 1:
			formData.put("tableName", "sms_send_statistics");
			break;
		case 2:
			formData.put("tableName", "inter_sms_send_statistics");
			break;
		case 3:
			formData.put("tableName", "voice_sms_send_statistics");
			break;
		}
		return spinfoService.getPreMonthMessageBySpId(formData);
	}

	@RequestMapping("/mySpInfoList")
	public ModelAndView mySpInfoList(Integer spId, HttpSession session) throws Exception {
		ModelAndView mod = new ModelAndView();
		mod.setViewName("spInfo/index");
		return mod;
	}

	@RequestMapping("uploadAuth")
	@ResponseBody
	public String uploadAuth(MultipartFile file, HttpServletRequest request) throws Exception {
		if (file == null || file.getSize() / 1024 / 1024 > 10) {
			return "";
		}
		FormData data = new FormData(request);
		String src = FileUtil.uploadFile(file,
				properties.getService_nginx_url() + "SIGNATURE_AUTH/" + data.get("userId") + Tools.getRandomNum(),
				data.getString("filename"));
		if (!"".equals(src)) {
			src = src.substring(src.indexOf("SIGNATURE_AUTH"));
		}
		return src;
	}

	@RequestMapping("sendTestMessage")
	@ResponseBody
	public Map<String, Object> uploadAuth(HttpServletRequest request, HttpSession session) throws Exception {
		FormData formData = getFormData();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		formData.put("user_id", userid);
		Integer spId = spinfoService.selDefaultSpInfoByServiceType(formData);
		String content = formData.getString("testMessage");
		String mobile = formData.getString("testPhone");
		SpInfo spInfo = spinfoService.getSpinfoById(spId);
		if (spInfo == null || spInfo.getStatus().toString().equals("2")) {
			map.put("returnstatus", "500");
			map.put("desc", "当前账号没有测试国内应用，请在应用详情中测试。");
			return map;
		}
		String messageType = formData.getString("messageType");
		String returnMsg = "";
		if (messageType.equals("0")) {
			returnMsg = this.post(userid.toString(), spInfo.getUsername(), spInfo.getPassword(), mobile, content.trim(),
					null);
		} else {
			if (spInfo.getSp_through_status() != null) {
				if (spInfo.getSp_through_status() != 1 && spInfo.getSp_through_status() != 2) {
					if (!content.contains("【畅卓科技】")) {
						map.put("returnstatus", "Faile");
						map.put("desc", "未上线应用签名必须是【畅卓科技】");
						return map;
					}
				}
			}
			// 判断短信字数是否超过短信最大条数
			if (content.length() > (Integer) session.getAttribute(Const.MAXCONTENTLENGTH)) {
				map.put("returnstatus", "Faile");
				map.put("desc", "短信最多发送字数为" + session.getAttribute(Const.MAXCONTENTLENGTH));
				return map;
			}
			returnMsg = this.post(userid.toString(), spInfo.getUsername(), spInfo.getPassword(), mobile, content, null);
		}
		JSONObject json = JSONObject.parseObject(returnMsg);
		String msg = (String) json.get("desc");
		Integer status = (Integer) json.get("status");
		map.put("returnstatus", status);
		map.put("desc", msg);
		return map;
	}

	@RequestMapping("sendInterTestMessage")
	@ResponseBody
	public Map<String, Object> sendInterTestMessage(HttpServletRequest request, HttpSession session) throws Exception {
		FormData formData = getFormData();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		formData.put("user_id", userid);
		Integer spId = spinfoService.selDefaultSpInfoByServiceType(formData);
		String content = formData.getString("testMessage");
		String mobile = formData.getString("testPhone");
		SpInfo spInfo = spinfoService.getSpinfoById(spId);
		if (spInfo == null || spInfo.getStatus().toString().equals("2")) {
			map.put("returnstatus", "500");
			map.put("desc", "当前账号没有测试国际应用，请在应用详情中测试。");
			return map;
		}
		String returnMsg = "";
		returnMsg = this.postInterSms(userid.toString(), spInfo.getUsername(), spInfo.getPassword(), mobile,
				content.trim(), null);
		JSONObject json = JSONObject.parseObject(returnMsg);
		String msg = (String) json.get("desc");
		Integer status = (Integer) json.get("status");
		map.put("returnstatus", status);
		map.put("desc", msg);
		return map;
	}

	@RequestMapping("sendVoiceTestMessage")
	@ResponseBody
	public Map<String, Object> sendVoiceTestMessage(HttpServletRequest request, HttpSession session) throws Exception {
		FormData formData = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		formData.put("user_id", userid);
		Integer spId = spinfoService.selDefaultSpInfoByServiceType(formData);
		// String content = formData.getString("testMessage");
		String content = "您的验证码是：1，2，3，4，您的验证码是：1，2，3，4";
		String mobile = formData.getString("testPhone");
		SpInfo spInfo = spinfoService.getSpinfoById(spId);
		Map<String, Object> map = new HashMap<String, Object>();
		if (spInfo == null || spInfo.getStatus().toString().equals("2")) {
			map.put("returnstatus", "500");
			map.put("desc", "当前账号没有测试语音应用，请在应用详情中测试。");
			return map;
		}
		String returnMsg = "";
		returnMsg = this.postVoiceSms(userid.toString(), spInfo.getUsername(), spInfo.getPassword(), mobile,
				content.trim(), null);
		JSONObject json = JSONObject.parseObject(returnMsg);
		String msg = (String) json.get("desc");
		Integer status = (Integer) json.get("status");
		map.put("returnstatus", status);
		map.put("desc", msg);
		return map;
	}

	@RequestMapping("insBalanceReminder")
	@ResponseBody
	public String insAppBananceReminder(HttpSession session, AppBalanceReminder appBalanceReminder) throws Exception {
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		SpInfo spInfo = spinfoService.getSpinfoById(appBalanceReminder.getSpId());
		if (spInfo.getSp_service_type() == Integer.valueOf("2")) {
			appBalanceReminder.setBalance(appBalanceReminder.getBalance() * 100);
		}
		appBalanceReminder.setCreateUser(userid);
		if (appBalanceReminder.getId() != null) {
			appBalanceReminder.setUpdateTime(new Date());
			appBalanceReminderService.updateByPrimaryKeySelective(appBalanceReminder);
		} else {
			appBalanceReminder.setCreateTime(new Date());
			appBalanceReminderService.addBalanceReminder(appBalanceReminder);
		}
		redisService.insAppBalanceReminder(appBalanceReminder);
		return "success";
	}

	@RequestMapping("getBalanceReminderBySpId")
	@ResponseBody
	public AppBalanceReminder getBalanceReminderBySpId(HttpSession session) throws Exception {
		FormData formData = getFormData();
		AppBalanceReminder appBalanceReminder = appBalanceReminderService
				.selectBalanceReminderBySpId(Integer.valueOf(formData.get("spId").toString()));
		return appBalanceReminder;
	}

	@RequestMapping("sendTestMessageBySpInfo")
	@ResponseBody
	public Map<String, Object> sendTestMessageBySpInfo(HttpServletRequest request, HttpSession session)
			throws Exception {
		FormData formData = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		formData.put("user_id", userid);
		Integer spId = Integer.valueOf(formData.get("spId").toString());
		String content = formData.getString("testMessage");
		String mobile = formData.getString("testPhone");
		SpInfo spInfo = spinfoService.getSpinfoById(spId);
		Map<String, Object> map = new HashMap<String, Object>();
		String result = "";
		switch (spInfo.getSp_service_type()) {
		case 1:
			result = this.post(userid.toString(), spInfo.getUsername(), spInfo.getPassword(), mobile, content, null);
			break;
		case 2:
			result = this.postInterSms(userid.toString(), spInfo.getUsername(), spInfo.getPassword(), mobile, content,
					null);
			break;
		default:
			result = this.post(userid.toString(), spInfo.getUsername(), spInfo.getPassword(), mobile, content, null);
		}

		JSONObject json = JSONObject.parseObject(result);
		String msg = (String) json.get("desc");
		Integer status = (Integer) json.get("status");
		map.put("returnstatus", status);
		map.put("desc", msg);
		return map;
	}

	@RequestMapping("changeSpInfoPassWord")
	@ResponseBody
	public Map<String, Object> changeSpInfoPassWord(HttpServletRequest request, HttpSession session) throws Exception {
		FormData formData = getFormData();
		Map<String, Object> map = new HashMap<String, Object>();
		spinfoService.updSpInfoPassWord(formData);
		redisService.updSpInfoPassWord(formData);
		map.put("returnstatus", "success");
		map.put("fixPassword", formData.get("password"));
		return map;
	}

	@RequestMapping("updateSpInfoIP")
	@ResponseBody
	public Map<String, Object> updateSpInfoIP(HttpServletRequest request, HttpSession session) throws Exception {
		FormData formData = getFormData();
		Map<String, Object> map = new HashMap<String, Object>();
		spinfoService.updatespinfoIp(formData);
		redisService.updSpInfoIp(formData);
		map.put("returnstatus", "success");
		map.put("fixPassword", formData.get("password"));
		return map;
	}
}
