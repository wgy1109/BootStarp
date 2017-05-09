package com.chanzor.controller.smsSendVoiceController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.VoiceService;
import com.chanzor.sms.common.utils.SequenceGenerator;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;
import com.chanzor.util.Tools;

@Controller
@RequestMapping("sendVoice")
public class SmsSendVoiceController extends BaseController {
	

	@Autowired
	private VoiceService voiceService;
	
	@Autowired
	private SpinfoService spinfoService;
	
//	@Autowired
//	private SequenceGenerator voiceIdGenerator;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("")
	public ModelAndView list(PageInfo page, HttpSession session,Model model) throws Exception {
		ModelAndView mv = new ModelAndView("sendVoice/sendVoice");
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = new FormData();
		formdata.put("userId", userid);
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			formdata.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		List<Map<String, Object>> lists = (List<Map<String, Object>>) spinfoService
				.getAuditPassVoiceSpinfoByUserID(formdata);
		
		model.addAttribute("appList", lists);
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page, HttpSession session) throws Exception {
		FormData data = getFormData();
		
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		data.put("userId", userid);

		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			data.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		page.setFormData(data);
		result.put("page", page);
		List<Map<String, Object>> dataList = voiceService.getAuditPassVoiceList(page);
		List<SpInfo> appList = spinfoService.findSpListByUserId(userid);
		Map<Integer,String> spMap = new HashMap<Integer,String>();
		for(SpInfo spInfo : appList){
			spMap.put(spInfo.getSpid(), spInfo.getSp_name());
		}
		
		if(dataList != null && dataList.size() > 0){
			for(Map<String,Object> map : dataList){
				map.put("fileUrl", properties.getFile_url());
				map.put("fileShortPath", ((String)map.get("file_name")).replace(properties.getFile_loc(),""));
				map.put("spName", spMap.get((Integer)map.get("sp_id")));
			}
		}
		result.put("data", dataList);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		return result;
	}
	
	
	@RequestMapping("sendVoiceFile")
	@ResponseBody
	public  Map<String, Object> sendVoiceFile(HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		FormData formData = getFormData();

		String voiceFileId = formData.getString("voiceFileId");
		if (voiceFileId == null || "".equals(voiceFileId)) {
			map.put("returnstatus", "failed");
			map.put("message", "请选择语音文件");
			return map;
		}
		
		formData.put("id", formData.getString("voiceFileId"));
		
		String mobile = formData.getString("mobile");
		String timingTime = formData.getString("timingTime");
		
		Map<String, Object> voiceFile = voiceService.getVoiceById(formData);
		SpInfo spInfo = spinfoService.getSpinfoById((Integer)voiceFile.get("sp_id"));
			
		
		String[] phoneNum = mobile.split(",");
		logger.info("判断当前应用最小发送人数");
		Integer minSendTotal = spinfoService.getMinSendTotalById((Integer)voiceFile.get("sp_id"));
		if (minSendTotal != null) {
			if (phoneNum.length < Integer.valueOf(minSendTotal)) {
				map.put("returnstatus", "failed");
				map.put("message", "当前应用最小发送人数为" + minSendTotal);
				return map;
			}
		}

		// 判断手机号格式是否正确
		for (String phone : phoneNum) {
			if (!spinfoService.isMobile(phone)) {
				map.put("returnstatus", "failed");
				map.put("message", "手机号格式有误:" + phone);
				return map;
			}
		}

		if (Tools.notEmpty(timingTime)) {
			// 定时短信
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			timingTime = df.parse(timingTime).getTime() + "";
		}
		logger.info("发送语音");
		String result = this.postVoiceFile(null, spInfo.getUsername(), spInfo.getPassword(), mobile, formData.getString("voiceFileId"), timingTime);
		
		JSONObject json = JSONObject.parseObject(result);
		String msg = (String) json.get("desc");
		Integer status = (Integer) json.get("status");
		map.put("returnstatus", status);
		map.put("message", msg);
		return map;
	}
	
	
}
