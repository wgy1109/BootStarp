package com.chanzor.controller.voice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.VoiceSmsSendTaskClientService;
import com.chanzor.util.Const;
import com.chanzor.util.DateHelper;
import com.chanzor.util.ExportExcel;
import com.chanzor.util.FormData;

@Controller
@RequestMapping("voicesmsSendTaskClient")
@SuppressWarnings("unchecked")
public class VoiceSmsSendTaskClientController extends BaseController {
	public int layertype = 0;
	@Autowired
	private VoiceSmsSendTaskClientService service;

	@RequestMapping("")
	public ModelAndView list(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("voice/voiceSmssendtaskclient_list");
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


	/**
	 * 跳转到语音短信明细列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("mt")
	public ModelAndView mtlist(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("voice/voiceSmsmtdetail");
		return mv;
	}

	/**
	 * 获得语音短信明细列表信息
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
	
	@RequestMapping("getClientfileurlByfileid")
	@ResponseBody
	public Map<String, Object> getClientfileurlByfileid(HttpSession session) throws Exception {
		FormData f = getFormData();
		Map<String, Object> fileMap = service.getfileurlByfileid(f);
		String url = fileMap.get("file_name").toString().replace(properties.getFile_loc(), properties.getFile_url());
		fileMap.put("fileUrl", url);
		return fileMap;
	}
	
	// 导出短信明细
	@RequestMapping("exportMtListToExcel")
	@ResponseBody
	public void exportMtListToExcel(HttpServletResponse response, HttpSession session) throws Exception {
		layertype = 1;
		String[] rowsName = new String[] { "任务批次", "应用名称", "手机号码", "提交状态", "提交时间", "短信内容" };
		
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
			objs[i++] = map.get("message_content");
			dataList.add(objs);
		}
		ExportExcel ex = new ExportExcel(rowsName, dataList);
		ex.export(response, "语音短信明细列表");
		layertype = 0;
	}
	
	@RequestMapping("closetype")
	@ResponseBody
	public String closetype() throws Exception {
		return layertype+"";
	}

}
