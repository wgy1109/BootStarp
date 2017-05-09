package com.chanzor.service;

import java.util.List;
import java.util.Map;

import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SmsAccountChargeRecord;

public interface SmsAccountChargeRecordService {
	int deleteByPrimaryKey(Integer id);

	int insert(SmsAccountChargeRecord record) throws Exception;

	int insertSelective(SmsAccountChargeRecord record) throws Exception;

	SmsAccountChargeRecord selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SmsAccountChargeRecord record) throws Exception;

	int updateByPrimaryKey(SmsAccountChargeRecord record);

	List<SmsAccountChargeRecord> getAccountChargelistPage(PageInfo page) throws Exception;

	Integer findAccountBalance(Integer id) throws Exception;

	Integer subtractAccountBalance(Map<String, Object> map) throws Exception;

	SmsAccountChargeRecord selectByOrderNum(String orderNum) throws Exception;

	Integer addAccountBalance(Map<String, Object> map) throws Exception;

	Integer updChargeStatus(SmsAccountChargeRecord account) throws Exception;

	Integer selAccountRefUserMap(Integer userId) throws Exception;

	void insAccountRefUser(Integer userId) throws Exception;

	int updAaccountBalance(SmsAccountChargeRecord record) throws Exception;
	
	Integer updateIsRecharge(Map<String, Object> map) throws Exception;
	
	int updateIsbeginrecharge(SmsAccountChargeRecord record) throws Exception;
	
	int updateUserinfoInvoicevalue(Map<String, Object> map) throws Exception;
	
	Map<String, Object> userinfo(Integer userId) throws Exception;

}