package com.chanzor.controller.voice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.*;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;

@Controller
@RequestMapping("voiceSendStatistics")
@SuppressWarnings("unchecked")
public class VoiceSendStatisticsController extends BaseController {
	
	@Autowired
	private VoiceSmsSendTaskClientService service;
	
	@RequestMapping("")
	public ModelAndView SendStatisticsmessage(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("voice/voiceSendStatisticsMessage");
		return mv;
	}
	
	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page, HttpSession session) throws Exception {
		Integer userid = (Integer)((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = getFormData();
		formdata.put("ID", userid);
		if (checkLandType(session)) {
			formdata.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			formdata.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		
		page.setFormData(formdata);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		data = (List<Map<String, Object>>) service.getSendStatisticsServiceByPage(page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		result.put("data", data);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		return result;
	}

}
