package com.chanzor.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.entity.PageInfo;
import com.chanzor.service.AppVatinvoiceCertinfoClientService;
import com.chanzor.util.FormData;

@Service("AppVatinvoiceCertinfoClientService")
@SuppressWarnings("unchecked")
public class AppVatinvoiceCertinfoClientServiceImpl implements
		AppVatinvoiceCertinfoClientService {

	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public List<Map<String, Object>> getAllAppVatinvoiceCertinfoByPage(
			PageInfo page) throws Exception {
		return (List<Map<String, Object>>) daoSupport
				.findForList(
						"AppVatinvoiceCertinfoClientMapper.getAllAppVatinvoiceCertinfoByPage",
						page);
	}

	public Map<String, Object> getAppVatinvoiceCertinfoInfoById(FormData data)
			throws Exception {
		return (Map<String, Object>) daoSupport
				.findForObject(
						"AppVatinvoiceCertinfoClientMapper.getAppVatinvoiceCertinfoInfoById",
						data);
	}

	public Map<String, Object> getAppVatinvoiceCertinfoInfoByUserIdService(
			FormData data) throws Exception {
		return (Map<String, Object>) daoSupport
				.findForObject(
						"AppVatinvoiceCertinfoClientMapper.getAppVatinvoiceCertinfoInfoByUserId",
						data);
	}

	// 修改保存提交的发票信息
	public Map<String, Object> saveAppVatinvoiceCertinfo(FormData data,
			HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> Vatinvoice = (Map<String, Object>) daoSupport
				.findForObject(
						"AppVatinvoiceCertinfoClientMapper.getAppVatinvoiceCertinfoInfoByUserId",
						data);
		int i = 0;
		data.put("CERTINFO_STATUS", 3);
		data.put("vatinvoice_type", 1);
		data.put("CERTINFO_CREATE_TIME", new Date());
		if (Vatinvoice == null || Vatinvoice.toString().equals("")) {
			i = (Integer) daoSupport
					.save("AppVatinvoiceCertinfoClientMapper.saveAppVatinvoiceCertinfoSpecial",
							data);
		} else {
			String status = (String) Vatinvoice.get("certinfo_status");
			if ("2".equals(status) || "3".equals(status)) {
				data.put("id", Vatinvoice.get("id"));
				i = (Integer) daoSupport
						.update("AppVatinvoiceCertinfoClientMapper.updateAppVatinvoiceCertinfoSpecial",
								data);
			} else {
				res.put("code", "01");
				res.put("msg", "您已提交认证,不可修改..");
				return res;
			}
		}
		if (i > 0) {
			res.put("code", "00");
			res.put("msg", "保存成功");
			session.setAttribute("data",
					getAppVatinvoiceCertinfoInfoByUserIdService(data));
		}else{
			res.put("code", "03");
			res.put("msg", "保存失败");
		}
		return res;
	}

	public Map<String, Object> saveAppVatinvoiceCertinfoCommon(FormData data,
			HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> Vatinvoice = (Map<String, Object>) daoSupport
				.findForObject(
						"AppVatinvoiceCertinfoClientMapper.getAppVatinvoiceCertinfoInfoByUserId",
						data);
		int i = 0;
		data.put("CERTINFO_STATUS", 3);
		data.put("CERTINFO_CREATE_TIME", new Date());
		data.put("vatinvoice_type", 0);
		if (Vatinvoice == null || Vatinvoice.toString().equals("")) {
			i = (Integer) daoSupport
					.save("AppVatinvoiceCertinfoClientMapper.saveAppVatinvoiceCertinfoCommon",
							data);
		} else {
				data.put("id", Vatinvoice.get("id"));
				i = (Integer) daoSupport
						.update("AppVatinvoiceCertinfoClientMapper.updateAppVatinvoiceCertinfoCommon",
								data);
			
		}
		if (i > 0) {
			res.put("code", "00");
			res.put("msg", "保存成功");
			session.setAttribute("data",
					getAppVatinvoiceCertinfoInfoByUserIdService(data));
		}else{
			res.put("code", "03");
			res.put("msg", "保存失败");
		}
		return res;
	}
	
	/**
	 * 用户提交发票认证信息审核
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> vatinvoiceSub(FormData data) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> Vatinvoice = (Map<String, Object>) daoSupport
				.findForObject(
						"AppVatinvoiceCertinfoClientMapper.getAppVatinvoiceCertinfoInfoByUserId",
						data);
		if (Vatinvoice == null) {
			res.put("code", "01");
			res.put("msg", "请先保存资料");
			return res;
		}
		String status = (String) Vatinvoice.get("certinfo_status");
		if ("2".equals(status) || "3".equals(status)) {
			int i = (Integer) daoSupport.update(
					"AppVatinvoiceCertinfoClientMapper.updateInvoiceStatus",
					data);
			if (i > 0) {
				res.put("code", "00");
				res.put("msg", "提交成功..");
				return res;
			} else {
				res.put("code", "02");
				res.put("msg", "提交失败,请稍后重试");
			}
			return res;
		} else {
			res.put("code", "01");
			res.put("msg", "您已提交认证,不可重复提交..");
			return res;
		}
	}

	public int deleteAppVatinvoiceCertinfo(FormData data) throws Exception {
		int result = (Integer) daoSupport
				.delete("AppVatinvoiceCertinfoClientMapper.deleteAppVatinvoiceCertinfo",
						data);
		return result;
	}

	public int updateInvoiceStatusService(FormData data) throws Exception {
		return (Integer) daoSupport.update(
				"AppVatinvoiceCertinfoClientMapper.updateInvoiceStatus", data);
	}

	// 根据用户ID 获取用户是否做了财务认证
	public int selectFinanceValidateNumService(FormData data) throws Exception {
		return (Integer) daoSupport.findForObject(
				"AppVatinvoiceCertinfoClientMapper.selectFinanceValidateNum",
				data);
	}

}
