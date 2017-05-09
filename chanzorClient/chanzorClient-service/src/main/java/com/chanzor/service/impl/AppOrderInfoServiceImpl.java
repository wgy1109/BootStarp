package com.chanzor.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.AppOrderInfoService;

@Service("appOrderInfoService")
@SuppressWarnings("unchecked")
public class AppOrderInfoServiceImpl implements AppOrderInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public Map<String, Object> getCouponByNum(String num) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("AppOrderInfoMapper.getCouponByNum", num);
	}

	public int updateCouponByNum(String string) throws Exception {
		return (Integer) daoSupport.update("AppOrderInfoMapper.updateCouponByNum", string);
	}

	public Object getCouponUseInfoByNumAndSpId(Map<String, Object> map) throws Exception {
		return (Object) daoSupport.update("AppOrderInfoMapper.getCouponUseInfoByNumAndSpId", map);
	}

	public int selCouponUseId() throws Exception {
		return (Integer) daoSupport.findForObject("AppOrderInfoMapper.selCouponUseId", null);
	}

	public void insertCouponUse(Map<String, Object> couponUse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInvalidByNum(String num) throws Exception {
		daoSupport.update("AppOrderInfoMapper.updateInvalidByNum", num);

	}

}
