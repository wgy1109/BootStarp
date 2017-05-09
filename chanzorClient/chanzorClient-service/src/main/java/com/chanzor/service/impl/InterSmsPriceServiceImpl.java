package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.entity.PageInfo;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.*;

@Service("InterSmsPriceService")
@SuppressWarnings("unchecked")
public class InterSmsPriceServiceImpl implements InterSmsPriceService{

	@Resource(name="daoSupport")
	private DaoSupport daoSupport;
	
	@Override
	public List<Map<String, Object>> getSmsPriceListByPage(PageInfo page) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("InterSmsPriceMapper.getSmsPriceP", page);
	}
	
}
