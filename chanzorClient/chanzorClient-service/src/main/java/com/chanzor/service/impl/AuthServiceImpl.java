package com.chanzor.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.AuthService;
import com.chanzor.entity.AuthInfo;

@Service("authService")
public class AuthServiceImpl implements AuthService {/*
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public AuthInfo getByUserId(Integer userId) throws Exception {
		return (AuthInfo) daoSupport.findForObject(
				"operateLogMapper.findUserId", userId);
	}

*/}
