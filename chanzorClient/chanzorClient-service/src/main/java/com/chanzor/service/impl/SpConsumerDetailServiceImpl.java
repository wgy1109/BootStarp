package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
//import com.chanzor.service.AuthService;
import com.chanzor.service.SpConsumerDetailService;
import com.chanzor.entity.AuthInfo;
import com.chanzor.entity.Page;
import com.chanzor.entity.PageInfo;

@SuppressWarnings("unchecked")
@Service("spConsumerDetailService")
public class SpConsumerDetailServiceImpl implements SpConsumerDetailService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	@Override
	public List<Map<String, Object>> findConsumerDetaillistPage(PageInfo page)
			throws Exception {
		Map<String, Object> countAll = (Map<String, Object>)daoSupport.findForObject("SpConsumerDetailMapper.selConmsumerlistCount", page);
		page.setTotalSize(Integer.parseInt(countAll.get("count").toString()));
		return (List<Map<String, Object>>) daoSupport.findForList("SpConsumerDetailMapper.selConmsumerlistP", page);
	}

}
