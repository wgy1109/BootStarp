package com.chanzor.controller.intersmssendtask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.InterSmsSendTaskClientService;
import com.chanzor.service.SmsMasterplateClientService;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.WhitelistService;
import com.chanzor.util.Const;
import com.chanzor.util.DateHelper;
import com.chanzor.util.ExportExcel;
import com.chanzor.util.FileManageUtils;
import com.chanzor.util.FormData;
import com.chanzor.util.Tools;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("intersmsSendTaskClient")
@SuppressWarnings("unchecked")
public class InterSmsSendTaskClientController extends BaseController {
	public int layertype = 0;
	@Autowired
	private InterSmsSendTaskClientService service;
	@Autowired
	private SpinfoService spinfoService;
	@Autowired
	private WhitelistService whitelistService;
	@Autowired
	private SmsMasterplateClientService smsMasterplateClientService;

	@RequestMapping("")
	public ModelAndView list(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("interSmssendtaskClient/interSmssendtaskclient_list");
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
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			f.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
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
		ModelAndView mv = new ModelAndView("interSmssendtaskClient/interSendMessage");
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
//		logger.info("判断发送短信应用是否通过");
		// 判断发送短信应用是否通过
		/*if (spInfo.getSp_through_status() != null) {
			if (spInfo.getSp_through_status() != 1 && spInfo.getSp_through_status() != 2) {
				if (!content.contains("【畅卓科技】")) {
					map.put("returnstatus", "Faile");
					map.put("message", "未上线应用签名必须是【畅卓科技】");
					return map;
				}
			}
		}*/
//		logger.info("判断短信发送手机号是否为白名单");
		// 判断短信发送手机号是否为白名单
		/*String[] phoneNum = mobile.split(",");
		if (spInfo.getSp_through_status() != 2) {
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
		}*/
//		logger.info("判断当前应用最小发送人数");
		/*Integer minSendTotal = spinfoService.getMinSendTotalById(Integer.valueOf(spId));
		if (minSendTotal != null) {
			if (phoneNum.length < Integer.valueOf(minSendTotal)) {
				map.put("returnstatus", "Faile");
				map.put("message", "当前应用最小发送人数为" + minSendTotal);
				return map;
			}
		}*/

		if (Tools.notEmpty(timingTime)) {
			// 定时短信
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			timingTime = df.parse(timingTime).getTime() + "";
		}
		logger.info("发送短信");
		// 实时短信
		String result = this.postInterSms(spInfo.getUsername(), spInfo.getUsername(), spInfo.getPassword(), mobile,
				content, timingTime);

		InterSmsSendResult sendResult = null;
		try {
			sendResult = JSON.parseObject(result, InterSmsSendResult.class);
		} catch (Exception e) {
		}
		if (sendResult != null) {
			if (sendResult.getStatus() == 0) {
				map.put("returnstatus", "Success");
				map.put("message", "发送成功");
			} else {
				map.put("returnstatus", "Faild");
				map.put("message", sendResult.getDesc());
			}
		}

		return map;
	}

	@RequestMapping(value = "/findSpInfoByUserId")
	public @ResponseBody Map<String, Object> mySpinfo(SpInfo spInfo, String sp_service_type, PageInfo pageInfo,
			HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		map.put("sp_service_type", Integer.valueOf(sp_service_type));
		map.put("userid", Integer.valueOf(userInfo.get("id").toString()));
		if (checkLandType(session)) {
			map.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			map.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		
		List<SpInfo> spList = spinfoService.findSpListByuserIdType(map);
		map.put("spList", spList);
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
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> masplist = new ArrayList<Map<String, Object>>();
		if (spId != null) {
			SpInfo spinfo = spinfoService.getSpinfoById(spId);
			if (spinfo.getSp_through_status() != null) {
				status = spinfo.getSp_through_status();
			}
			masplist = smsMasterplateClientService.selectSpMaspAndSysMasp(spId);
		}
		map.put("masplist", masplist);
		map.put("status", status);
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

	@RequestMapping("downloadTemplate")
	@ResponseBody
	public void downloadTemplate(HttpSession session, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		String type = request.getParameter("type");
		if (type.equals("excel")) {
			String path = properties.getService_nginx_url() + "templete/exportPhone.xls";
			FileManageUtils.exportFile(response, path, "导入手机号.xls");
		} else if (type.equals("txt")) {
			String path = properties.getService_nginx_url() + "templete/exportPhone.txt";
			FileManageUtils.exportFile(response, path, "导入手机号.txt");
		} else {
			String path = properties.getService_nginx_url() + "templete/interCode.xls";
			FileManageUtils.exportFile(response, path, "国家代码.xls");
		}

	}

	// 批量导入手机号
	@RequestMapping(value = "uploadPhone", method = RequestMethod.POST)
	@ResponseBody
	public String updateImage(@RequestParam(required = false) MultipartFile file, HttpServletRequest request,
			HttpSession session) throws Exception {
		Map<String, Object> answer = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		if (file == null || file.getSize() / 1024 / 1024 > 10) {
			answer.put("returnCode", 203);
			answer.put("error", "文件不存在");
			return mapper.writeValueAsString(answer);
		}
		String allPhone = "";
		InputStream stream = file.getInputStream();
		if (file.getOriginalFilename().endsWith(".xls")) {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(stream);
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			if (hssfSheet == null) {
				answer.put("returnCode", 203);
				answer.put("error", "导入的文件中没有数据");
				return mapper.writeValueAsString(answer);
			}
			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				HSSFCell xh = hssfRow.getCell(0);
				if (xh == null) {
					continue;
				}
				allPhone += getValue(xh) + ",";
			}
			stream.close();
			Map<String, Object> map = spinfoService.checkPhone(allPhone);
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
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (!(lineTxt.trim().length() > 0)) {
						continue;
					}
					allPhone += lineTxt + ",";
				}
				read.close();
				Map<String, Object> map = spinfoService.checkPhone(allPhone);
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
	 * 跳转到国际短信明细列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mt")
	public ModelAndView mtlist(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("interSmssendtaskClient/interSmsmtdetail");
		return mv;
	}

	/**
	 * 获得国际短信明细列表信息
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

		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			f.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		
		page.setFormData(f);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		List<Map<String, Object>> packageList = service.getSmsMtListByPage(page);
		result.put("data", packageList);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		return result;
	}
	
	// 导出短信明细
	@RequestMapping("exportMtListToExcel")
	@ResponseBody
	public void exportMtListToExcel(HttpServletResponse response, HttpSession session) throws Exception {
		layertype = 1;
		String[] rowsName = new String[] { "任务批次", "应用名称", "手机号码", "提交状态", "提交时间", "状态报告", "回执时间", "短信内容" };
		
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
		List<Object[]> dataList = new ArrayList<Object[]>();
		PageInfo page = new PageInfo();
		page.setLength(1000000);
		page.setPageSize(1000000);
		page.setFormData(f);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		List<Map<String, Object>> data = service.getSmsMtListByPage(page);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Map<String, Object> map : data) {
			Object[] objs = new Object[rowsName.length];
			int i = 0 ;
			objs[i++] = map.get("package_id");
			objs[i++] = map.get("user_sp_name");
			objs[i++] = map.get("mobile");
			objs[i++] = map.get("status");
			objs[i++] = (map.get("send_time") != null) ? df.format(map.get("send_time")) : "";
			objs[i++] = map.get("dr_result");
			objs[i++] = (map.get("dr_time") != null) ? df.format(map.get("dr_time")) : "";
			objs[i++] = map.get("message_content");
			dataList.add(objs);
		}
		ExportExcel ex = new ExportExcel(rowsName, dataList);
		ex.export(response, "国际短信明细列表");
		layertype = 0;
	}
	
	@RequestMapping("closetype")
	@ResponseBody
	public String closetype() throws Exception {
		return layertype+"";
	}

}

class InterSmsSendResult {

	private String taskId;

	private long balance;

	private int mobileCount;

	private int status;

	private String desc;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public int getMobileCount() {
		return mobileCount;
	}

	public void setMobileCount(int mobileCount) {
		this.mobileCount = mobileCount;
	}

}
