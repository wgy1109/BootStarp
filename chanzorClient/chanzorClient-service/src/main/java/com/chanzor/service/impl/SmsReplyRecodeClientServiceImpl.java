package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.entity.PageInfo;
import com.chanzor.service.SmsReplyRecodeClientService;
import com.chanzor.util.FormData;

@Service("SmsReplyRecodeClientService")
@SuppressWarnings("unchecked")
public class SmsReplyRecodeClientServiceImpl implements SmsReplyRecodeClientService{

	@Resource(name="daoSupport")
	private DaoSupport daoSupport;
	
	public List<Map<String, Object>> getAllSmsReplyRecodeClientByPage (PageInfo page )throws Exception{
		Map<String, Object> countAll = (Map<String, Object>)daoSupport.findForObject("SmsReplyRecodeClientMapper.getAllSmsReplyRecodeClientCount", page);
		page.setTotalSize(Integer.parseInt(countAll.get("count").toString()));
		return (List<Map<String, Object>>) daoSupport.findForList("SmsReplyRecodeClientMapper.getAllSmsReplyRecodeClientP", page);
	}

	@Override
	public Map<String, Object> getSmsReplyRecodeClientInfoByIdService(
			FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("SmsReplyRecodeClientMapper.getSmsReplyRecodeClientInfoById", data);
	}

}
