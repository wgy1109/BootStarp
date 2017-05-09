package com.chanzor.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import com.chanzor.entity.PageInfo;
import com.chanzor.util.FormData;

public interface AppVatinvoiceCertinfoClientService {
	public List<Map<String, Object>> getAllAppVatinvoiceCertinfoByPage(
			PageInfo page) throws Exception;

	public Map<String, Object> getAppVatinvoiceCertinfoInfoById(FormData data)
			throws Exception;

	public Map<String, Object> getAppVatinvoiceCertinfoInfoByUserIdService(
			FormData data) throws Exception;

	// 修改保存提交的发票信息
	public Map<String, Object> saveAppVatinvoiceCertinfo(FormData data,
			HttpSession session) throws Exception;

	public Map<String, Object> saveAppVatinvoiceCertinfoCommon(FormData data,
			HttpSession session) throws Exception;
	/**
	 * 用户提交发票认证信息审核
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> vatinvoiceSub(FormData data) throws Exception;

	public int deleteAppVatinvoiceCertinfo(FormData data) throws Exception;

	public int updateInvoiceStatusService(FormData data) throws Exception;

	// 根据用户ID 获取用户是否做了财务认证
	public int selectFinanceValidateNumService(FormData data) throws Exception;

}
