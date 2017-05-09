package com.chanzor.controller.intersmssendtask;

import java.text.DecimalFormat;
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
import com.chanzor.service.*;
import com.chanzor.util.Const;
import com.chanzor.util.ExportExcel;
import com.chanzor.util.FormData;

@Controller
@RequestMapping("intersmspriceClient")
@SuppressWarnings("unchecked")
public class InterSmsPriceController extends BaseController {
	
	@Autowired
	private InterSmsPriceService service;
	
	@RequestMapping("")
	public ModelAndView list(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("interSmssendtaskClient/interSmsPrice_list");
		return mv;
	}

	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page, HttpSession session) throws Exception {
		FormData f = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		f.put("userid", userid);
		page.setFormData(f);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		List<Map<String, Object>> packageList = service.getSmsPriceListByPage(page);
		result.put("data", packageList);
		result.put("recordsTotal", packageList.size());
		result.put("recordsFiltered", packageList.size());
		return result;
	}
	
	// 导出模板
	@RequestMapping("exportPriceListToExcel")
	@ResponseBody
	public void exportMasterplateListToExcel(HttpServletResponse response,
			HttpSession session) throws Exception {
		String[] rowsName = new String[] { "国家(中文)", "国家(英文)", "国家代码", "国际价格" };

		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = getFormData();
		formdata.put("userId", userid);

		List<Object[]> dataList = new ArrayList<Object[]>();
		PageInfo page = new PageInfo();
		page.setLength(1000000);
		page.setPageSize(1000000);
		page.setFormData(formdata);
		List<Map<String, Object>> data = service.getSmsPriceListByPage(page);
		DecimalFormat dicf = new DecimalFormat("0.00");
		for (Map<String, Object> map : data) {
			Object[] objs = new Object[4];
			objs[0] = map.get("country_cn");
			objs[1] = map.get("country_en");
			objs[2] = map.get("country_code");
			objs[3] = dicf.format((float)Integer.parseInt(map.get("price").toString())/100);
			dataList.add(objs);
		}
		ExportExcel ex = new ExportExcel(rowsName, dataList);
		ex.export(response, "国际短信价格列表");
	}
	
}
