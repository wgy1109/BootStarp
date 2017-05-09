package com.chanzor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.chanzor.entity.SystemParameter;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.SystemParameterService;


@Service("SystemParameterService")
@SuppressWarnings("unchecked")
@Transactional
public class SystemParameterServerImpl implements SystemParameterService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	@Override
	public List<SystemParameter> findSystemParameterListPage() throws Exception {
		return (List<SystemParameter>) daoSupport.findForList(
				"SystemParameterMapper.findSystemParameterListPage", null);

	}

	@Override
	public SystemParameter findSystemParameterById(Integer prmId)
			throws Exception {
		return (SystemParameter) daoSupport.findForObject(
				"SystemParameterMapper.findSystemParameterById", prmId);
	}

}
