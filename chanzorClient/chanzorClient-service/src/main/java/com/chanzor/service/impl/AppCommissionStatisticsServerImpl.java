package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.*;

@Service("AppCommissionStatisticsServer")
@SuppressWarnings("unchecked")
public class AppCommissionStatisticsServerImpl implements AppCommissionStatisticsServer {
	
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;
	
	@Override
	public int insertSalesStatistics(Map<String, Object> map) throws Exception {
		return (Integer) daoSupport.save("AppCommissionStatisticsMapper.saveAccountChargerecodeMapper", map);
	}
	
}
