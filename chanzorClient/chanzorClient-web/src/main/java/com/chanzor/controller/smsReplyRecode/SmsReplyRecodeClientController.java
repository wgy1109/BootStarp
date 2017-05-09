package com.chanzor.controller.smsReplyRecode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

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
import com.chanzor.service.SmsReplyRecodeClientService;
import com.chanzor.util.Const;
import com.chanzor.util.DateHelper;
import com.chanzor.util.ExportExcel;
import com.chanzor.util.FormData;

@Controller
@RequestMapping("smsReplyRecodeClient")
public class SmsReplyRecodeClientController extends BaseController {
	public int layertype = 0;
	@Autowired
	private SmsReplyRecodeClientService service;

	@RequestMapping("")
	public ModelAndView list(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("smsReplyRecodeClient/smsReplyRecodeClient_list");
		mv.addObject("queryStartTime", DateHelper.getOtherDateString(0,"yyyy-MM-dd"));
		return mv;
	}

	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page,HttpSession session) throws Exception {
		FormData formData = getFormData();
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			formData.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		formData = DateHelper.formDataDateString(formData, "queryStartTime", "queryEndTime");
		page.setFormData(formData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		List<Map<String, Object>> data = service.getAllSmsReplyRecodeClientByPage(page);
		for (Map<String, Object> d : data) {
			if ((Integer) d.get("pk_size") > 1) {
				d.put("messageNum", d.get("message_content").toString().length());
				d.put("message_content", pk_size_number(d) + d.get("message_content"));
			}
		}
		result.put("data", data);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		return result;
	}

	@RequestMapping("detail")
	@ResponseBody
	public Map<String, Object> detail() throws Exception {
		return service.getSmsReplyRecodeClientInfoByIdService(getFormData());
	}
	
	// 导出短信回复
	@RequestMapping("exportListToExcel")
	@ResponseBody
	public void exportListToExcel(HttpServletResponse response, HttpSession session) throws Exception {
		layertype = 1;
		logger.info("导出短信回复数据库信息开始时刻："+new Date());
		String[] rowsName = new String[] { "手机号码", "应用名称", "回执时间", "是否接收", "短信内容" };
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
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
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
				return service.getAllSmsReplyRecodeClientByPage(page);
			});
			list.add(FutureResultMap);
            beginpage++;
            if(beginpage/66==1){
            	break;
            }
        }
		list.forEach(future->{
			try {
				dataList.addAll(future.get());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		});
		logger.info("获取数据库信息结束时刻："+new Date());
		String[] key = new String[] { "source", "spname", "receive_time", "sendtype", "message_content" };
		ExportExcel ex = new ExportExcel();
		ex.exportBySXSSFWorkbook(response, "国内短信回复列表", rowsName, dataList, key, "国内短信回复列表");
		logger.info("导出数据结束时刻："+new Date());
		layertype = 0;
	}
	
	@RequestMapping("closetype")
	@ResponseBody
	public String closetype() throws Exception {
		return layertype+"";
	}

}
