package com.chanzor.controller.smsSendTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.ChargeRecordService;
import com.chanzor.service.RedisService;
import com.chanzor.service.SmsMasterplateClientService;
import com.chanzor.service.SmsSendTaskClientService;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.WhitelistService;
import com.chanzor.util.Const;
import com.chanzor.util.DateHelper;
import com.chanzor.util.ExportExcel;
import com.chanzor.util.FileManageUtils;
import com.chanzor.util.FormData;
import com.chanzor.util.PropertiesConfig;
import com.chanzor.util.Tools;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.Future;

//发送列表
@Controller
@RequestMapping("smsSendTaskClient")
@SuppressWarnings("unchecked")
public class SmsSendTaskClientController extends BaseController {
	public int layertype = 0;
	@Autowired
	private SmsSendTaskClientService service;
	@Autowired
	private SpinfoService spinfoService;
	@Autowired
	private WhitelistService whitelistService;
	@Autowired
	private SmsMasterplateClientService smsMasterplateClientService;
	@Autowired
	private ChargeRecordService chargeRecordService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private PropertiesConfig propertiesConfig;

	@RequestMapping("")
	public ModelAndView list(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("smsSendTaskClient/smsSendTaskClient_list");
		mv.addObject("queryStartTime", DateHelper.getOtherDateString(0,"yyyy-MM-dd"));
		return mv;
	}

	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page, HttpSession session) throws Exception {
		FormData f = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		f.put("userid", userid);
		if (checkLandType(session)) {
			f.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}

		Map<String, Object> currUser = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		if ((Integer) currUser.get("is_sub_account") == 1) { // 是子账号，过滤非管辖的应用
			f.put("subAccountAppIds", (String) session.getAttribute(Const.APPIDSSTR));
		}
		f = DateHelper.formData(f, "queryStartTime", "queryEndTime");
		page.setFormData(f);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		List<Map<String, Object>> packageList = service.getAllSmsSendTaskClientByPage(page);
		for (Map<String, Object> sms : packageList) {
			sms.put("Allnum", Allnum(sms));
		}
		result.put("data", packageList);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		return result;
	}

	@RequestMapping("sendMessage")
	public ModelAndView sendMessage(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("smsSendTaskClient/sendMessage");
		return mv;
	}

	@RequestMapping("postPayInfo")
	public @ResponseBody Map<String, Object> postPayInfo(HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		FormData fromData = getFormData();
		String spId = fromData.getString("spId");
		// 判断短信字数是否超过短信最大条数
		if ("".equals(spId) || "0".equals(spId)) {
			map.put("returnstatus", "Faile");
			map.put("message", "请选择应用");
			return map;
		}
		/*
		 * List<String> apps = (List<String>)
		 * session.getAttribute(Const.APPIDS); if (!apps.contains(spId)) {
		 * map.put("returnstatus", "Faile"); map.put("message", "输入内容有误，请重新输入");
		 * return map; }
		 */
		String content = fromData.getString("content");
		String mobile = fromData.getString("mobile");
		String timingTime = fromData.getString("timingTime");
		SpInfo spInfo = spinfoService.getSpinfoById(Integer.valueOf(spId));
		logger.info("判断短信字数是否超过短信最大条数");
		// 判断短信字数是否超过短信最大条数
		if (content.length() > (Integer) session.getAttribute(Const.MAXCONTENTLENGTH)) {
			map.put("returnstatus", "Faile");
			map.put("message", "短信最多发送字数为" + session.getAttribute(Const.MAXCONTENTLENGTH));
			return map;
		}
		logger.info("判断发送短信应用是否通过");
		// 判断发送短信应用是否通过
		if (spInfo.getSp_through_status() != null) {
			if (spInfo.getSp_through_status() != 1 && spInfo.getSp_through_status() != 2) {
				if (!content.contains("【畅卓科技】")) {
					map.put("returnstatus", "Faile");
					map.put("message", "未上线应用签名必须是【畅卓科技】");
					return map;
				}
			}
		}
		logger.info("判断短信发送手机号是否为白名单");
		// 判断短信发送手机号是否为白名单
		String[] phoneNum = mobile.split(",");
		// 判断 企业账户是否放开白名单限制
		Integer isValidateWhite = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER))
				.get("is_validate_white");
		if (isValidateWhite == 0) {
			if (spInfo.getSp_through_status() != 2 && spInfo.getSp_through_status() != 3) {
				FormData data = new FormData();
				data.put("spid", spInfo.getSpid());
				List<String> whiteList = whitelistService.getAllWhitePhoneList(data);
				for (String phone : phoneNum) {
					if (whiteList.contains(phone)) {
						continue;
					} else {
						map.put("returnstatus", "Faile");
						map.put("message", "未上线应用只能发送白名单中添加的号码," + phone + "此号码不在白名单列表中");
						return map;
					}
				}
			}
		}
		logger.info("判断当前应用最小发送人数");
		Integer minSendTotal = spinfoService.getMinSendTotalById(Integer.valueOf(spId));
		if (minSendTotal != null) {
			if (phoneNum.length < Integer.valueOf(minSendTotal)) {
				map.put("returnstatus", "Faile");
				map.put("message", "当前应用最小发送人数为" + minSendTotal);
				return map;
			}
		}

		// 判断手机号格式是否正确
		/*
		 * for (String phone : phoneNum) { if (!spinfoService.isMobile(phone)) {
		 * map.put("returnstatus", "Faile"); map.put("message", "手机号格式有误:" +
		 * phone); return map; } }
		 */

		if (Tools.notEmpty(timingTime)) {
			// 定时短信
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			timingTime = df.parse(timingTime).getTime() + "";
		}
		logger.info("发送短信");
		// 实时短信
		String result = this.post(((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id").toString(),
				spInfo.getUsername(), spInfo.getPassword(), mobile, content, timingTime);
		JSONObject json = JSONObject.parseObject(result);
		String msg = (String) json.get("desc");
		Integer status = (Integer) json.get("status");
		map.put("returnstatus", status);
		map.put("message", msg);
		return map;
	}

	@RequestMapping(value = "/findSpInfoByUserId")
	public @ResponseBody Map<String, Object> mySpinfo(SpInfo spInfo, String sp_service_type, PageInfo pageInfo,
			HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		map.put("sp_service_type", Integer.valueOf(sp_service_type));
		map.put("userid", Integer.valueOf(userInfo.get("id").toString()));
		if ((Integer) userInfo.get("is_sub_account") == 1) {
			map.put("subAccountAppIds", (String) session.getAttribute(Const.APPIDSSTR));
		}

		List<SpInfo> spList = spinfoService.findSpListByuserIdType(map);
		if (checkLandType(session)) {
			List<SpInfo> list = new ArrayList<SpInfo>();
			list.add((SpInfo) session.getAttribute(Const.SESSIONSPINFO));
			map.put("spList", list);
		} else {
			map.put("spList", spList);
		}
		map.put("maxlength", session.getAttribute(Const.MAXCONTENTLENGTH));
		return map;
	}

	@RequestMapping(value = "/findPlateInfoBySpId")
	public @ResponseBody Map<String, Object> findPlateInfoBySpId(Integer spId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> plateList = smsMasterplateClientService.selPlateBySpId(spId);
		map.put("plate", plateList);
		return map;
	}

	@RequestMapping(value = "/checkSpStatus")
	public @ResponseBody Map<String, Object> checkSpStatus(Integer spId) throws Exception {
		Integer status = 0;
		String signature = "";
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> masplist = new ArrayList<Map<String, Object>>();
		if (spId != null) {
			SpInfo spinfo = spinfoService.getSpinfoById(spId);
			signature = spinfo.getSignature();
			if (spinfo.getSp_through_status() != null) {
				status = spinfo.getSp_through_status();
			}
			masplist = smsMasterplateClientService.selectSpMaspAndSysMasp(spId);
		}
		map.put("masplist", masplist);
		map.put("status", status);
		map.put("signature", signature);
		return map;
	}

	@RequestMapping(value = "/findPlateInfoById")
	public @ResponseBody Map<String, Object> findPlateInfoById() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		FormData fromData = getFormData();
		Map<String, Object> plateInfo = smsMasterplateClientService.getSmsMasterplateClientInfoById(fromData);
		map.put("plateInfo", plateInfo);
		return map;
	}

	@RequestMapping(value = "/filterPhoneNum")
	public @ResponseBody Map<String, Object> filterPhoneNum() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		FormData fromData = getFormData();
		String mobile = fromData.getString("mobile");
		map = spinfoService.checkPhone(mobile);
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("downloadTemplate")
	@ResponseBody
	public void downloadTemplate(HttpSession session, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		String type = request.getParameter("type");
		if (type.equals("excel")) {
			String path = properties.getService_nginx_url() + "templete/exportPhone.xls";
			FileManageUtils.exportFile(response, path, "导入手机号.xls");
		} else {
			String path = properties.getService_nginx_url() + "templete/exportPhone.txt";
			FileManageUtils.exportFile(response, path, "导入手机号.txt");
		}

	}

	// 批量导入手机号
	@RequestMapping(value = "uploadPhone", method = RequestMethod.POST)
	@ResponseBody
	public String updateImage(@RequestParam(required = false) MultipartFile file, HttpServletRequest request,
			HttpSession session) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
		Map<String, Object> answer = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		if (file == null || file.getSize() / 1024 / 1024 > 10) {
			answer.put("returnCode", 203);
			answer.put("error", "文件不存在");
			return mapper.writeValueAsString(answer);
		}
		// StringBuilder allPhone = new StringBuilder("");
		InputStream stream = file.getInputStream();
		if (file.getOriginalFilename().endsWith(".xls")) {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(stream);
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			if (hssfSheet == null) {
				answer.put("returnCode", 203);
				answer.put("error", "导入的文件中没有数据");
				return mapper.writeValueAsString(answer);
			}
			Set<String> mobileSet = new HashSet();
			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				HSSFCell xh = hssfRow.getCell(0);
				if (xh == null) {
					continue;
				}
				mobileSet.add(getValue(xh).trim());
			}
			List<String> mobileList = mobileSet.stream().filter(phone -> {
				if (phone.length() != 11) {
					return false;
				}
				return true;
			}).collect(Collectors.toList());
			String mobiles = StringUtils.join(mobileList, ",");
			stream.close();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("phoneAllCount", mobileSet.size());
			map.put("phoneStr", mobiles);
			map.put("phoneCount", mobileList.size());
			if (map.get("phoneStr") != null && map.get("phoneStr").toString().length() > 0) {
				answer.put("returnCode", 200);
				answer.put("text", map.get("phoneStr"));
				return mapper.writeValueAsString(answer);
			} else {
				answer.put("returnCode", 203);
				answer.put("error", "导入数据不符合手机号格式，导入失败");
				return mapper.writeValueAsString(answer);
			}
		} else if (file.getOriginalFilename().endsWith(".txt")) {
			try {
				String encoding = "utf-8";
				InputStreamReader read = new InputStreamReader(stream, encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = "";
				Set<String> mobileSet = new HashSet();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (!(lineTxt.trim().length() > 0)) {
						continue;
					}
					mobileSet.add(lineTxt.trim());
				}
				List<String> mobileList = mobileSet.stream().filter(phone -> {
					if (phone.length() != 11) {
						return false;
					}
					return true;
				}).collect(Collectors.toList());
				String mobiles = StringUtils.join(mobileList, ",");
				read.close();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("phoneAllCount", mobileSet.size());
				map.put("phoneStr", mobiles);
				map.put("phoneCount", mobileList.size());
				if (map.get("phoneStr") != null && map.get("phoneStr").toString().length() > 0) {
					answer.put("returnCode", 200);
					answer.put("text", map.get("phoneStr"));
					return mapper.writeValueAsString(answer);
				} else {
					answer.put("returnCode", 203);
					answer.put("error", "导入数据不符合手机号格式，导入失败");
					return mapper.writeValueAsString(answer);
				}
			} catch (Exception e) {
				answer.put("returnCode", 203);
				answer.put("error", "读取文件内容出错");
				e.printStackTrace();
				return mapper.writeValueAsString(answer);
			}
		} else {
			answer.put("returnCode", 203);
			answer.put("error", "文件格式错误");
			return mapper.writeValueAsString(answer);
		}
	}

	@SuppressWarnings("deprecation")
	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			DecimalFormat df = new DecimalFormat("#");
			return String.valueOf(df.format(hssfCell.getNumericCellValue()));
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	public static int getMessageSize(String message) {
		int size = 1;
		int len = message.length();
		if (len > Const.CHINESE_SMS_LENGTH) {
			size = len / Const.CHINESE_LONGSMS_LENGTH;
			if (len % Const.CHINESE_LONGSMS_LENGTH > 0) {
				size++;
			}
		}
		return size;
	}

	/**
	 * 敏感词检测
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("doCheck")
	@ResponseBody
	public Map<String, Object> doCheck(@RequestParam(required = false) String content, Integer spId,
			HttpSession session, HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (content != null) {
			String sensitive = validContent(content.replace("\n", ""), spId);
			result.put("statusCode", 200);
			result.put("msg", sensitive);
		} else {
			result.put("statusCode", 201);
			result.put("msg", "检测失败");
		}
		return result;
	}

	public String validContent(String MessageContent, Integer spId) throws Exception {
		StringBuffer buff = new StringBuffer();

		List<Map<String, Object>> list_word = service.getAllSenstiveWord(spId);
		String sub_pattern = "[^0-9a-zA-Z\u4E00-\u9FA5]*";
		for (int i = 0; i < list_word.size(); i++) {
			Map<String, Object> word = list_word.get(i);
			String content = (String) word.get("content");
			if (word != null && content.length() > 0) {
				StringBuffer pattern_buff = new StringBuffer();
				pattern_buff.append("^.*");
				for (int j = 0; j < content.length(); j++) {
					String sub_word = content.substring(j, j + 1);
					if (sub_word.equals("\\") || sub_word.equals("^") || sub_word.equals("$") || sub_word.equals("*")
							|| sub_word.equals("+") || sub_word.equals("?") || sub_word.equals("{")
							|| sub_word.equals("}") || sub_word.equals("[") || sub_word.equals("]")
							|| sub_word.equals("(") || sub_word.equals(")") || sub_word.equals(".")
							|| sub_word.equals("|")) {
						sub_word = "\\" + sub_word;
					}
					pattern_buff.append(sub_word);
					if (j != content.length() - 1) {
						pattern_buff.append(sub_pattern);
					}
				}
				pattern_buff.append(".*$");
				Pattern pattern = Pattern.compile(pattern_buff.toString());
				Matcher matcher = pattern.matcher(MessageContent);
				if (matcher.matches()) {
					return content;
				}
			}
		}
		return buff.length() == 0 ? "" : buff.substring(1);// .toString();
	}

	/**
	 * 跳转到短信明细列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mt")
	public ModelAndView mtlist(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("smsSendTaskClient/smsMtDetail");
		mv.addObject("queryStartTime", DateHelper.getOtherDateString(0,"yyyy-MM-dd"));
		return mv;
	}

	/**
	 * 获得短信明细列表信息
	 * 
	 * @param page
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMtList")
	@ResponseBody
	public Map<String, Object> getMtList(PageInfo page, HttpSession session) throws Exception {
		FormData f = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		f.put("userid", userid);
		if (checkLandType(session)) {
			f.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}

		Map<String, Object> currUser = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		if ((Integer) currUser.get("is_sub_account") == 1) { // 是子账号，过滤非管辖的应用
			f.put("subAccountAppIds", (String) session.getAttribute(Const.APPIDSSTR));
		}
		f = DateHelper.formData(f, "queryStartTime", "queryEndTime");
		page.setFormData(f);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		List<Map<String, Object>> packageList = service.getSmsMtListByPage(page);
		result.put("data", packageList);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		return result;
	}

	@RequestMapping("customizedIndex")
	public ModelAndView customizedIndex(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("smsSendTaskClient/sendCustomizedMessage");
		return mv;
	}

	@RequestMapping(value = "uploadCustomized")
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response, String dataType)
			throws IllegalStateException, IOException {
		// 需要返回的fileName
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmsss");
		String realPath = "customized/";
		String filePath = propertiesConfig.getFile_loc() + realPath + format.format(new Date());
		File uploadPathFile = new File(propertiesConfig.getFile_loc() + realPath);
		if (!uploadPathFile.exists()) {
			uploadPathFile.mkdirs();
		}
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile mulfile = multipartRequest.getFile(key);
			filePath = filePath + mulfile.getOriginalFilename()
					.substring(mulfile.getOriginalFilename().lastIndexOf("."), mulfile.getOriginalFilename().length());
			File file = new File(filePath);
			mulfile.transferTo(file);
		}
		return filePath;
	}

	@RequestMapping(value = "saveCustomizedMessage")
	@ResponseBody
	public Map<String, Object> saveCustomizedMessage(HttpServletRequest request, HttpServletResponse response,
			String dataType, HttpSession session) throws NumberFormatException, Exception {
		// 需要返回的fileName
		FormData formData = getFormData();
		Map<String, Object> map = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		String templateUrl = formData.getString("templateUrl");
		String templateContent = formData.getString("templateContent");
		String phoneIndex = formData.getString("phoneIndex");
		String spId = formData.getString("spId");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SpInfo spInfo = spinfoService.getSpinfoById(Integer.valueOf(spId));
		resultMap = service.getExcelAsFile(formData, templateUrl, templateContent, Integer.valueOf(phoneIndex),
				spInfo.getSignature());
		return resultMap;
	}

	@RequestMapping(value = "sendCustomizedMessage")
	@ResponseBody
	public Map<String, Object> sendCustomizedMessage(HttpServletRequest request, HttpServletResponse response,
			String dataType) throws NumberFormatException, Exception {
		// 需要返回的fileName
		FormData formData = getFormData();
		Map<String, Object> map = new HashMap<String, Object>();
		String customeizedContent = formData.getString("customeizedContent");
		String redisKey = formData.getString("redisKey");
		// String content = redisService.getCustomizedMessageByRedis(redisKey);
		String timingTime = formData.getString("timingTime");
		String spId = formData.getString("spId");
		if (StringUtils.isBlank(spId) || StringUtils.isBlank(customeizedContent) || StringUtils.isBlank(redisKey)) {
			map.put("returnstatus", "500");
			map.put("message", "参数错误");
			return map;
		}
		SpInfo spInfo = spinfoService.getSpinfoById(Integer.valueOf(spId));
		if (Tools.notEmpty(timingTime)) {
			// 定时短信
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			timingTime = df.parse(timingTime).getTime() + "";
		}
		long startTine = System.currentTimeMillis();
		String result = this.postCustomizedMessage(customeizedContent, redisKey, timingTime, spInfo);
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + (System.currentTimeMillis() - startTine)
				+ "xxxxxxxxxxxxxxxxxxxxxxxxxx");
		JSONObject json = JSONObject.parseObject(result);
		String msg = (String) json.get("desc");
		Integer status = (Integer) json.get("status");
		map.put("returnstatus", status);
		map.put("message", msg);
		return map;
	}

	// 导出短信明细
	@RequestMapping("exportMtListToExcel")
	@ResponseBody
	public void exportMtListToExcel(HttpServletResponse response, HttpSession session) throws Exception {
		logger.info("导出开始时刻：" + new Date());
		layertype = 1;
		String[] rowsName = new String[] { "任务批次", "应用名称", "手机号码", "提交状态", "提交时间", "状态报告", "状态报告值", "回执时间", "短信内容" };
		FormData f = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		f.put("userid", userid);
		if (checkLandType(session)) {
			f.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		Map<String, Object> currUser = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		if ((Integer) currUser.get("is_sub_account") == 1) { // 是子账号，过滤非管辖的应用
			f.put("subAccountAppIds", (String) session.getAttribute(Const.APPIDSSTR));
		}
		f.put("queryStartTime", DateHelper.getDateStringTimg(f.get("queryStartTime").toString()));
		f.put("queryEndTime", DateHelper.getDateStringTimg(f.get("queryEndTime").toString()));
		f.put("content", f.get("messagecontent"));
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		int pagesize = 1000; // 每次从数据库获取信息数量
		int beginpage = 1;
		List<Future<List<Map<String, Object>>>> list = new ArrayList<Future<List<Map<String, Object>>>>();
		while (true) {
			PageInfo page = new PageInfo();
			page.setFormData(f);
			page.setStart((beginpage - 1) * pagesize);
			page.setLength(pagesize);
			Future<List<Map<String, Object>>> FutureResultMap = execute(()->
			{
				return service.getSmsMtList(page);
			});
			list.add(FutureResultMap);
            beginpage++;
            if(beginpage/66==1){
            	break;
            }
        }
		list.forEach(future->{
			try {
				data.addAll(future.get());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		});
		logger.info("获取数据库信息结束时刻："+new Date());
		String[] key = new String[] { "package_id", "user_sp_name", "mobile", "status", "send_time", "dr_result", "dr_reason", "dr_time", "message_content" };
		ExportExcel ex = new ExportExcel();
		ex.exportBySXSSFWorkbook(response, "国内短信明细列表", rowsName, data, key, "国内短信明细列表");
		logger.info("导出数据结束时刻："+new Date());
		layertype = 0;
	}

	@RequestMapping("closetype")
	@ResponseBody
	public String closetype() throws Exception {
		return layertype + "";
	}

	// 定时短信页面跳转
	@RequestMapping("timing")
	public ModelAndView timinglist(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("smsSendTaskClient/smsTiming");
		mv.addObject("queryStartTime", DateHelper.getOtherDateString(0,"yyyy-MM-dd"));
		return mv;
	}

	// 定时短信列表查询
	@RequestMapping("timingload")
	@ResponseBody
	public Map<String, Object> timingload(PageInfo page, HttpSession session) throws Exception {
		FormData f = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		f.put("userid", userid);
		if (checkLandType(session)) {
			f.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}

		Map<String, Object> currUser = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		if ((Integer) currUser.get("is_sub_account") == 1) { // 是子账号，过滤非管辖的应用
			f.put("subAccountAppIds", (String) session.getAttribute(Const.APPIDSSTR));
		}

		page.setFormData(f);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		List<Map<String, Object>> packageList = service.getAllSmsTimingClientByPage(page);
		for (Map<String, Object> sms : packageList) {
			sms.put("Allnum", Allnum(sms));
		}
		result.put("data", packageList);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getDate")
	@ResponseBody
	public Map<String, Object> getDate(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("day", DateHelper.getOtherDateString(0,"yyyy-MM-dd"));
		map.put("month", DateHelper.getOtherDateString(0,"yyyy-MM"));
		return map;
	}

}
