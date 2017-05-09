package com.chanzor.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.SendTaskInfoService;

@Service("sendTaskInfoService")
@SuppressWarnings("unchecked")
public class SendTaskInfoServiceImpl implements SendTaskInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public Integer getTackInfoByYesterDay(Map<String,Object> map) throws Exception {
		return (Integer) daoSupport.findForObject("SendTaskInfoMapper.getTaskInfoByYesterDay", map);
	}

	public Integer getTaskInfoByMonth(Map<String, Object> map) throws Exception {
		return (Integer) daoSupport.findForObject("SendTaskInfoMapper.getTaskInfoByMonth", map);
	}

	@Override
	public Integer getTackInfoByYesterDay0(Map<String,Object> map) throws Exception {
		return (Integer) daoSupport.findForObject("SendTaskInfoMapper.getTaskInfoByYesterDay0", map);
	}

	@Override
	public Integer getTaskInfoByMonth0(Map<String, Object> map) throws Exception {
		Integer result = (Integer) daoSupport.findForObject("SendTaskInfoMapper.getTaskInfoByMonth0", map);
		return result;
	}
}
