package com.chanzor.service;

import java.util.List;
import java.util.Map;
import com.chanzor.entity.PageInfo;
import com.chanzor.util.FormData;

public interface AppFinanceInvoiceClientService {

	public List<Map<String, Object>> getAllAppFinanceInvoiceClientByPage(
			PageInfo page) throws Exception;

	public Map<String, Object> getAppFinanceInvoiceClientInfoById(FormData data)
			throws Exception;

	public int saveAppFinanceInvoiceClient(FormData data) throws Exception;

	public int deleteAppFinanceInvoiceClient(FormData data) throws Exception;

	public int updateAuditStatusClient(FormData data) throws Exception;

	// 根据用户ID，取得用户开过的发票信息（目的在于取出曾经用过的地址）
	public List<Map<String, Object>> getCollectAddressService(FormData data)
			throws Exception;

	// 取出用户下所有充值记录总金额
	public Map<String, Object> getAllMoney(FormData data) throws Exception;

	// 取出用户开过的发票总金额
	public Map<String, Object> getFinanceInvoiceAllMoneyService(FormData data)
			throws Exception;

	// 根据用户ID得到用户已经认证企业的数量 未认证为0，已认证为1（大于0）
	public int getAppUserAuthenticationByUserIdService(FormData data)
			throws Exception;
	
	public List<Map<String, Object>> getAppUserAuthenticationNameByUserIdService(FormData data) throws Exception;
	
	public Map<String,Object> getSumInterAmountByUserId(FormData data) throws Exception;
	
	public Map<String, Object> getUserinfo(FormData data) throws Exception;
}
