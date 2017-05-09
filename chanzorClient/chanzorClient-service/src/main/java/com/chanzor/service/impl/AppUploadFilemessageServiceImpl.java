package com.chanzor.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.AppUploadFilemessageService;

@Service("appUploadFilemessageService")
public class AppUploadFilemessageServiceImpl implements
		AppUploadFilemessageService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public Integer selectCountByUserid(Map<String, Object> map)
			throws Exception {
		return (Integer) daoSupport.findForObject(
				"operateLogMapper.findUserId", map);
	}

}
