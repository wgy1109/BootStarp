package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.TestappService;
import com.chanzor.util.FormData;

@Service("testappService")
@SuppressWarnings("unchecked")
public class TestappServiceImpl implements TestappService{
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public List<Map<String, Object>> getTestappConf() throws Exception {
		return ((List<Map<String, Object>>) daoSupport.findForList(
				"TestappMapper.getTestappConf", null));
	}

	public int saveTestapp(FormData data) throws Exception {
		int result = (Integer) daoSupport.update("TestappMapper.updateTestapp",
				data);
		return result;
	}
}
