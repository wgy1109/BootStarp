package com.chanzor.service;

import java.util.Map;

public interface AppOrderInfoService {

	public Map<String, Object> getCouponByNum(String num) throws Exception;

	public int updateCouponByNum(String string) throws Exception;

	public Object getCouponUseInfoByNumAndSpId(Map<String, Object> map)
			throws Exception;

	public int selCouponUseId() throws Exception;

	public void insertCouponUse(Map<String, Object> couponUse);
	
	void updateInvalidByNum(String num) throws Exception;

}
