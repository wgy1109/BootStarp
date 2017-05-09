package com.chanzor.controller.smsSendTextVoiceController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.SmsMasterplateClientService;
import com.chanzor.service.SpinfoService;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;
import com.chanzor.util.Tools;

//发送列表
@Controller
@RequestMapping("smsSendTextVoice")
@SuppressWarnings("unchecked")
public class SmsSendTextVoiceController extends BaseController {
    @Autowired
    private SmsMasterplateClientService smsMasterplateClientService;
    @Autowired
    private SpinfoService spinfoService;
	@RequestMapping("index")
	public ModelAndView list(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("sendTextVoice/sendTextVoice");
		return mv;


}
	

	@RequestMapping("findVoiceTemplate")
	@ResponseBody
	public Map<String,Object> findVoiceTemplate(Integer spId) throws Exception {
		List<Map<String,Object>> listMap=smsMasterplateClientService.getVoiceTemplateBySpId(spId);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("masplist", listMap);
		return map;


}
	@RequestMapping("sendTextVoice")
	@ResponseBody
	public Map<String,Object> sendTextVoice(HttpSession session) throws Exception {
		FormData formData=getFormData();
		Map<String, Object> map = new HashMap<String, Object>();
		String spId = formData.getString("spId");
		// 判断短信字数是否超过短信最大条数
		if ("".equals(spId) || "0".equals(spId)) {
			map.put("returnstatus", "Faile");
			map.put("message", "请选择应用");
			return map;
		}	
//		List<String> apps = (List<String>) session.getAttribute(Const.APPIDS);
//		if (!apps.contains(spId)) {
//		map.put("returnstatus", "Faile");
//		map.put("message", "输入内容有误，请重新输入");
//		return map;
//		}
		String content = formData.getString("content");
		String mobile = formData.getString("mobile");
		String speed = formData.getString("speed");
		String voiceName = formData.getString("voice_name");
		String timingTime = formData.getString("timingTime");
		String templateId=formData.getString("spsysMasterplate");
		if(templateId!=null&&!templateId.equals("0")){
			formData.put("id",templateId);
		 Map<String,Object> templateInfo=smsMasterplateClientService.getSmsMasterplateClientInfoById(formData);
		 if(templateInfo!=null){
			 speed=templateInfo.get("speed").toString();
			 voiceName=templateInfo.get("voice_name").toString();
		 }
		}
		SpInfo spInfo = spinfoService.getSpinfoById(Integer.valueOf(spId));
		String[] phoneNum = mobile.split(",");
		logger.info("判断当前应用最小发送人数");
		Integer minSendTotal = spinfoService.getMinSendTotalById(Integer.valueOf(spId));
		if (minSendTotal != null) {
			if (phoneNum.length < Integer.valueOf(minSendTotal)) {
				map.put("returnstatus", "Faile");
				map.put("message", "当前应用最小发送人数为" + minSendTotal);
				return map;
			}
		}
		logger.info("判断发送短信应用是否通过");
		// 判断手机号格式是否正确
		for (String phone : phoneNum) {
			if (!spinfoService.isMobile(phone)) {
				map.put("returnstatus", "Faile");
				map.put("message", "手机号格式有误:" + phone);
				return map;
			}
		}
		if (Tools.notEmpty(timingTime)) {
			// 定时短信
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			timingTime = df.parse(timingTime).getTime() + "";
		}
		logger.info("发送短信");
		// 实时短信
		String result = this.postTextVoiceFile(spInfo.getUserId().toString(),spInfo.getUsername(), spInfo.getPassword(), mobile, content,
				voiceName,speed,timingTime);
		JSONObject json = JSONObject.parseObject(result);
		String msg = (String) json.get("desc");
		Integer status = (Integer) json.get("status");
		map.put("returnstatus", status);
		map.put("message", msg);
		return map;
}
	
}
