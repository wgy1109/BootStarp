package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.AppBusinessManagerClientService;
import com.chanzor.util.FormData;

@Service("appBusinessManagerClientService")
@SuppressWarnings("unchecked")
public class AppBusinessManagerClientServiceImpl implements AppBusinessManagerClientService {
	/*
	 * 
	 * @Resource(name = "daoSupport") private DaoSupport daoSupport;
	 * 
	 * // 取出用户对应的支撑经理和商务经理 public List<Map<String, Object>>
	 * getAppBusinessManagerByUserIdService( FormData data) throws Exception {
	 * return (List<Map<String, Object>>) daoSupport
	 * 
	 * .findForList(
	 * "AppBusinessManagerClientMapper.getAppBusinessManagerByUserId", data); }
	 * 
	 * public int SaveBusinessMessage(FormData data) throws Exception { return
	 * (Integer) daoSupport.save(
	 * "AppBusinessManagerClientMapper.saveBusinessManager", data); }
	 * 
	 * public int getAppBusinessManagerByIdentifService(FormData data) throws
	 * Exception { return (Integer) daoSupport.findForObject(
	 * "AppBusinessManagerClientMapper.getAppBusinessManagerByIdentif", data); }
	 * 
	 */}
