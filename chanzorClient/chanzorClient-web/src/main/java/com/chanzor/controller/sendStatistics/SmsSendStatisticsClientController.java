package com.chanzor.controller.sendStatistics;

import java.text.SimpleDateFormat;
import java.util.*;

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
import com.chanzor.service.SmsSendTaskClientService;
import com.chanzor.util.*;

//发送统计
@Controller
@RequestMapping("SendStatistics")
@SuppressWarnings("unchecked")
public class SmsSendStatisticsClientController extends BaseController {
	public int layertype = 0;
	@Autowired
	private SmsSendTaskClientService service;

	@RequestMapping("")
	public ModelAndView SendStatisticsmessage(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("sendStatistics/sendStatisticsmessage");
		return mv;
	}

	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page, HttpSession session) throws Exception {
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = getFormData();
		formdata.put("ID", userid);
		page.setFormData(formdata);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (checkLandType(session)) {
			formdata.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			formdata.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		
		data = (List<Map<String, Object>>) service.getSendStatisticsServiceByPage(page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		result.put("data", data);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		return result;
	}
	
	// 导出统计列表
	@RequestMapping("exportSendStatisticsToExcel")
	@ResponseBody
	public void exportSendStatisticsToExcel(HttpServletResponse response, HttpSession session) throws Exception {
		layertype = 1;
		String[] rowsName = new String[] { "应用名称", "信息发送时间", "提交数量", "下发条数", "成功条数", "失败条数", "未知状态条数" };
		
		FormData f = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		f.put("ID", userid);
		if (checkLandType(session)) {
			f.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}

		Map<String, Object> currUser = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		if ((Integer) currUser.get("is_sub_account") == 1) { // 是子账号，过滤非管辖的应用
			f.put("subAccountAppIds", (String) session.getAttribute(Const.APPIDSSTR));
		}
		List<Object[]> dataList = new ArrayList<Object[]>();
		PageInfo page = new PageInfo();
		page.setLength(1000000);
		page.setPageSize(1000000);
		page.setFormData(f);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		List<Map<String, Object>> data = service.getSendStatisticsServiceByPage(page);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Map<String, Object> map : data) {
			Object[] objs = new Object[rowsName.length];
			int i = 0 ;
			objs[i++] = map.get("user_sp_name");
			objs[i++] = map.get("msgtime");
			objs[i++] = map.get("allnum");
			objs[i++] = map.get("sendednum");
			objs[i++] = map.get("allyes");
			objs[i++] = map.get("allno");
			objs[i++] = map.get("othernum");
			dataList.add(objs);
		}
		ExportExcel ex = new ExportExcel(rowsName, dataList);
		ex.export(response, "国内短信发送统计列表");
		layertype = 0;
	}
	
	@RequestMapping("closetype")
	@ResponseBody
	public String closetype() throws Exception {
		return layertype+"";
	}

}
