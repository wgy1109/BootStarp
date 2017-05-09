package com.chanzor.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.entity.PageInfo;
import com.chanzor.service.AppFinanceInvoiceClientService;
import com.chanzor.util.FormData;

@Service("AppFinanceInvoiceClientService")
@SuppressWarnings("unchecked")
public class AppFinanceInvoiceClientServiceImpl implements AppFinanceInvoiceClientService {

	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public List<Map<String, Object>> getAllAppFinanceInvoiceClientByPage(PageInfo page) throws Exception {
		return (List<Map<String, Object>>) daoSupport
				.findForList("AppFinanceInvoiceClientMapper.getAllAppFinanceInvoiceClientByPage", page);
	}

	public Map<String, Object> getAppFinanceInvoiceClientInfoById(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport
				.findForObject("AppFinanceInvoiceClientMapper.getAppFinanceInvoiceClientInfoById", data);
	}

	public int saveAppFinanceInvoiceClient(FormData data) throws Exception {
		Object id = data.get("id");
		int result = 0;
		if (id == null || id.toString().equals("")) {
			result = (Integer) daoSupport.save("AppFinanceInvoiceClientMapper.saveAppFinanceInvoiceClient", data);
			if(result > 0 ){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("authid", data.get("userId"));
				map.put("reducemoney", data.get("invoiceValue"));
				daoSupport.update("AppFinanceInvoiceClientMapper.updateUserinfoInvoicevalue", map);
			}
		} else {
			//C 端发票暂时没有修改功能，如果添加修改功能，需要同时修改账户可开发票余额
//			result = (Integer) daoSupport.update("AppFinanceInvoiceClientMapper.updateAppFinanceInvoiceClient", data);
		}
		return result;
	}

	public int deleteAppFinanceInvoiceClient(FormData data) throws Exception {
		Map<String, Object> map = (Map<String, Object>) daoSupport.findForObject("AppFinanceInvoiceClientMapper.getAppFinanceInvoiceClientInfoById", data);
		int result = (Integer) daoSupport.delete("AppFinanceInvoiceClientMapper.deleteAppFinanceInvoiceClient", data);
		if(result > 0 ){
			daoSupport.update("AppFinanceInvoiceClientMapper.addUserinfoInvoicevalue", map);
		}
		return result;
	}

	public int updateAuditStatusClient(FormData data) throws Exception {
		int result = (Integer) daoSupport.update("AppFinanceInvoiceClientMapper.updateAuditStatus", data);
		return result;
	}

	// 根据用户ID，取得用户开过的发票信息（目的在于取出曾经用过的地址）
	public List<Map<String, Object>> getCollectAddressService(FormData data) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("AppFinanceInvoiceClientMapper.getCollectAddress",
				data);
	}

	// 取出用户下所有充值记录总金额
	public Map<String, Object> getAllMoney(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("ChargeRecordMapper.getSumAmountByUserId", data);
	}

	// 取出用户开过的发票总金额
	public Map<String, Object> getFinanceInvoiceAllMoneyService(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("AppFinanceInvoiceClientMapper.getFinanceInvoiceAllMoney",
				data);
	}

	// 根据用户ID得到用户已经认证企业的数量 未认证为0，已认证为1（大于0）
	public int getAppUserAuthenticationByUserIdService(FormData data) throws Exception {
		return (Integer) daoSupport.findForObject("AppFinanceInvoiceClientMapper.getAppUserAuthenticationByUserId",
				data);
	}

	// 得到已认证企业
	public List<Map<String, Object>> getAppUserAuthenticationNameByUserIdService(FormData data) throws Exception {
		return (List<Map<String, Object>>) daoSupport
				.findForList("AppFinanceInvoiceClientMapper.getAppUserAuthenticationNameByUserId", data);
	}

	@Override
	public Map<String, Object> getSumInterAmountByUserId(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("ChargeRecordMapper.getSumInterAmountByUserId",
 data);
	}

	@Override
	public Map<String, Object> getUserinfo(FormData data) throws Exception{
		return (Map<String, Object>) daoSupport.findForObject("AppFinanceInvoiceClientMapper.getuserinfobyid", data);
	}
}
