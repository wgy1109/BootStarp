package com.chanzor.controller.appFinanceInvoice;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.service.*;
import com.chanzor.util.Const;
import com.chanzor.util.DateHelper;
import com.chanzor.util.FormData;
import com.chanzor.util.KdniaoTrackQueryAPI;

@Controller
@RequestMapping("appFinanceInvoiceClient")
public class AppFinanceInvoiceClientController extends BaseController {

	@Autowired
	private AppFinanceInvoiceClientService service;

	@Autowired
	private AddressService addressService;

	@Autowired
	private AppVatinvoiceCertinfoClientService vatinvoiceCertinfoservice;

	@RequestMapping("")
	public ModelAndView list(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("appFinanceInvoiceClient/appFinanceInvoiceClient_list");
		return mv;
	}

	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page, HttpSession session) throws Exception {
		FormData data = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		data.put("userId", userid);
		Map<String, Object> result = new HashMap<String, Object>();
		page.setFormData(data);
		result.put("page", page);
		result.put("data", service.getAllAppFinanceInvoiceClientByPage(page));
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		return result;
	}

	@RequestMapping("edit")
	@ResponseBody
	public Map<String, Object> edit(HttpSession session) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		FormData data = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		data.put("userId", userid);
		Integer num = (Integer) service.getAppUserAuthenticationByUserIdService(data);
		resultMap.put("resultbool", false);
		if (num < 1) {
			logger.info("还没有认证企业信息，不能新增索取发票");
			resultMap.put("Map", new HashMap<String, Object>());
			resultMap.put("Money", "0");
			resultMap.put("accountName", "");
			resultMap.put("resultbool", true);
			return resultMap;
		}

		Map<String, Object> vatinvoiceCertinfo = vatinvoiceCertinfoservice
				.getAppVatinvoiceCertinfoInfoByUserIdService(data);

		if (vatinvoiceCertinfo == null || vatinvoiceCertinfo.size() == 0) {
			logger.info("发票认证下未完善发票抬头等信息，不能新增索取发票");
			resultMap.put("Map", new HashMap<String, Object>());
			resultMap.put("Money", "0");
			resultMap.put("accountName", "");
			resultMap.put("voiceTitleNoFinished", true);
			return resultMap;

		}

		Map<String, Object> map = new HashMap<String, Object>();
		// -------
		/*
		 * map = service.getAllMoney(data); Map<String, Object> interMap = new
		 * HashMap<String, Object>(); interMap =
		 * service.getSumInterAmountByUserId(data); String Money = "0"; if (map
		 * != null) { DecimalFormat df = new DecimalFormat(
		 * "###############0.00 "); Double allmoney =
		 * Double.parseDouble(map.get("ALLAMOUNT").toString()); if (interMap !=
		 * null) { allmoney +=
		 * Double.parseDouble(interMap.get("ALLAMOUNT").toString()); }
		 * 
		 * Map<String, Object> maps =
		 * service.getFinanceInvoiceAllMoneyService(data); if (maps != null) {
		 * Double allFinancemoney =
		 * Double.parseDouble(maps.get("AllINVOICE").toString()); Double Moneys
		 * = allmoney - allFinancemoney; if (Moneys > 0) { Money =
		 * df.format(Moneys); } else { Money = "0"; } } else { Money =
		 * df.format(allmoney); } } else { map = new HashMap<String, Object>();
		 * }
		 */
		// -------
		// 获取曾经使用过的发票邮寄地址。
		List<Map<String, Object>> addressMessage = service.getCollectAddressService(data);
		List<Map<String, Object>> addressList = addressService.getAllAddressList(data);
		String collectAddress = "";
		String collectPhone = "";
		String collectName = "";
		if (addressList != null && addressList.size() > 0) {
			for (int i = 0; i < addressList.size(); i++) {
				Map<String, Object> currAddressMap = addressList.get(i);
				if (i == 0) {
					collectName = (String) currAddressMap.get("collect_name");
					collectAddress = (String) currAddressMap.get("collect_address");
					String collect_phone = (String) currAddressMap.get("collect_phone");
					String collect_tel = (String) currAddressMap.get("collect_tel");
					if (!StringUtils.isEmpty(collect_phone)) {
						collectPhone = collect_phone;
					} else {
						collectPhone = collect_tel;
					}
				}
				if (((Integer) currAddressMap.get("default_mark")).intValue() == 1) {
					collectName = (String) currAddressMap.get("collect_name");
					collectAddress = (String) currAddressMap.get("collect_address");
					String collect_phone = (String) currAddressMap.get("collect_phone");
					String collect_tel = (String) currAddressMap.get("collect_tel");
					if (!StringUtils.isEmpty(collect_phone)) {
						collectPhone = collect_phone;
					} else {
						collectPhone = collect_tel;
					}
				} else {
					continue;
				}
			}
		}

		if (addressMessage.size() > 0) {
			map = addressMessage.get(0);
		} else {
			map.put("collect_name", "");
			map.put("collect_phone", "");
			map.put("collect_address", "");
		}

		if (!StringUtils.isEmpty(collectName)) {
			map.put("collect_name", collectName);
		}

		if (!StringUtils.isEmpty(collectAddress)) {
			map.put("collect_address", collectAddress);
		}
		if (!StringUtils.isEmpty(collectPhone)) {
			map.put("collect_phone", collectPhone);
		}

		// add by zyq 查询企业是否做了财务认证
		int numFinance = vatinvoiceCertinfoservice.selectFinanceValidateNumService(data);
		if (numFinance > 0) {
			map.put("invoiceStatus", "1");
		} else {
			map.put("invoiceStatus", "0");
		}

		resultMap.put("Map", map);
		// 获取可开发票金额
		Map<String, Object> userinfo = new HashMap<String, Object>();
		userinfo = service.getUserinfo(data);
		DecimalFormat df = new DecimalFormat("###############0.00 ");
		String money = df.format(Double.parseDouble(userinfo.get("open_invoice_value").toString()) / 100);
		resultMap.put("Money", money);
		List<Map<String, Object>> accountNameList = service.getAppUserAuthenticationNameByUserIdService(data);
		// resultMap.put("accountName", ((Map<String, Object>)
		// accountNameList.get(0)).get("company").toString());
		resultMap.put("accountName", vatinvoiceCertinfo.get("vatinvoice_title").toString());
		return resultMap;
	}

	@RequestMapping("getFinanceValidate")
	@ResponseBody
	public Map<String, Object> getFinanceValidate(HttpSession session) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		FormData data = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		data.put("userId", userid);
		int num = vatinvoiceCertinfoservice.selectFinanceValidateNumService(data);
		if (num > 0) {
			resultMap.put("code", 200);
		} else {
			resultMap.put("code", 201);
		}
		return resultMap;
	}

	/*
	 * //跳转至增加发票前，获取曾经使用过的发票邮寄地址。
	 * 
	 * @RequestMapping("addFinanceInvoice") public ModelAndView
	 * addFinanceInvoice (HttpSession session) throws Exception{ ModelAndView mv
	 * = new
	 * ModelAndView("appFinanceInvoiceClient/appFinanceInvoiceClient_edit");
	 * FormData formdata = getFormData(); List<Map<String, Object>> collectList
	 * = service.getCollectAddressService(formdata); Map<String, Object>
	 * collectMap = new HashMap<String, Object>(); if(collectList.size() > 0){
	 * collectMap = collectList.get(0); }
	 * mv.addObject("INVOICE_STATUS",formdata.get("INVOICE_STATUS"));
	 * mv.addObject("data",collectMap); return mv; }
	 */

	// 添加一条发票索取记录
	@RequestMapping("saveFinanceInvoice")
	@ResponseBody
	public Map<String, Object> saveFinanceInvoice(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = getFormData();
		formdata.put("userId", userid);
		if (formdata.get("invoiceValue") == null || formdata.get("invoiceValue") == ""
				|| formdata.get("COLLECT_NAME") == null || formdata.get("COLLECT_NAME") == ""
				|| formdata.get("COLLECT_PHONE") == null || formdata.get("COLLECT_PHONE") == ""
				|| formdata.get("COLLECT_ADDRESS") == null || formdata.get("COLLECT_ADDRESS") == "") {
			result.put("statusCode", 202);
			return result;
		}
		if ("1".equals(formdata.get("invoiceStatus"))) {
			int num = vatinvoiceCertinfoservice.selectFinanceValidateNumService(formdata);
			if (num < 1) {
				result.put("statusCode", 205);
				return result;
			}
			if (Integer.parseInt(formdata.get("invoiceValue").toString()) < 5000) {
				result.put("statusCode", 203);
				return result;
			}
		} else {
			if (Integer.parseInt(formdata.get("invoiceValue").toString()) < 5000) {
				result.put("statusCode", 204);
				return result;
			}
		}

		formdata.put("INVOICE_CREATE_TIME", DateHelper.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		formdata.put("AUDIT_STATUS", 2); // 2：未提交，0，已提交，1：服务端已发送发票
		int res = service.saveAppFinanceInvoiceClient(formdata);
		logger.info("新添加一条发票记录：添加结果" + res);
		result.put("statusCode", (res >= 1 ? 200 : 201));
		return result;
	}

	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> save() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int res = service.saveAppFinanceInvoiceClient(getFormData());
		result.put("statusCode", (res >= 1 ? 200 : 201));
		return result;
	}

	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int res = service.deleteAppFinanceInvoiceClient(getFormData());
		result.put("statusCode", (res >= 1 ? 200 : 201));
		return result;
	}

	@RequestMapping("updateAuditStatus")
	@ResponseBody
	public Map<String, Object> updateAuditStatus() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		FormData data = getFormData();
		data.put("AUDIT_STATUS", 0); // 2：未提交，0，已提交，1：服务端已发送发票
		int res = service.updateAuditStatusClient(data);
		result.put("statusCode", (res >= 1 ? 200 : 201));
		return result;
	}

	@RequestMapping("searchFinanceInvoiceNotes")
	@ResponseBody
	public Map<String, Object> searchFinanceInvoiceNotes(HttpSession session) throws Exception {
		FormData data = getFormData();
		Map<String, Object> result = service.getAppFinanceInvoiceClientInfoById(data);
		return result;
	}

	@RequestMapping("findExpress")
	@ResponseBody
	public Map<String, Object> findExpress(HttpSession session) throws Exception {
		FormData data = getFormData();
		Map<String, Object> Info = service.getAppFinanceInvoiceClientInfoById(data);
		KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
		String result = "当前订单无物流信息";
		Map<String, Object> map = new HashMap<String, Object>();
		if (Info.get("invoice_courier_num") != null
				&& StringUtils.isNotBlank(Info.get("invoice_courier_num").toString())
				&& Info.get("invoice_express_type") != null
				&& StringUtils.isNotBlank(Info.get("invoice_express_type").toString())) {
			try {
				result = api.getOrderTracesByJson(Info.get("invoice_express_type").toString(),
						Info.get("invoice_courier_num").toString());
				map.put("result", result);
				map.put("returnCode", 200);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			map.put("result", result);
			map.put("returnCode", 500);
		}
		return map;
	}
}
