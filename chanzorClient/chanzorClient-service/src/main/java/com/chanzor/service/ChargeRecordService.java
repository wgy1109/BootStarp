package com.chanzor.service;

import java.util.List;
import java.util.Map;

import com.chanzor.entity.ChargeRecord;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpCharge;
import com.chanzor.entity.SpInfo;

public interface ChargeRecordService {

	public List<SpInfo> spChargeByUserListPage(PageInfo page) throws Exception;

	public SpInfo spChategById(PageInfo page) throws Exception;

	public Double getPriceBySpId(Integer spId) throws Exception;

	public Integer findConponsNum(String Conpons) throws Exception;

	public SpInfo spChargeByUserId(Integer spId) throws Exception;

	public void insertChargeRecord(ChargeRecord chargeRecord) throws Exception;

	public void insertUserChargeRecord(ChargeRecord chargeRecord) throws Exception;

	public void updateSpCharge(SpCharge spCharge) throws Exception;

	public void updateAllSpCharge(SpCharge spCharge) throws Exception;

	public void updateSpChargeByLeftOver(SpCharge spCharge) throws Exception;

	public List<ChargeRecord> ChargeRecordListPage(PageInfo page) throws Exception;

	Integer selCountRecordByUserId(List spIdList) throws Exception;

	Integer updTypeByProductId(Map<String, Object> map) throws Exception;

	Integer updTypeByProductIdInUserCharge(Map<String, Object> map) throws Exception;

	Integer selChargeLeftOverNumBySpId(Integer spId) throws Exception;

	List<Map<String, Object>> selChargeInfoByProId(String ordernum) throws Exception;

	Map<String, Object> sleCharegeInfoByChargeId(Integer chargeId) throws Exception;

	Map<String, Object> selCharegeInfoByChargeId(String productId) throws Exception;

	void updateBalanceInChargeRecord(Map<String, Object> chargeInfo) throws Exception;

	// public void insertInterGiveMessage();

	Integer updateSpchargerecharge(Map<String, Object> map) throws Exception;
	
	Integer updateUserSpchargerecharge(Map<String, Object> map) throws Exception;


}
