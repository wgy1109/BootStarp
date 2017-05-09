package com.chanzor.service;

import java.util.List;

import com.chanzor.entity.SystemParameter;

public interface SystemParameterService {
	public List<SystemParameter> findSystemParameterListPage()
			throws Exception;

	public SystemParameter findSystemParameterById(Integer prmId)
			throws Exception;


}
