package com.chanzor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.entity.MessagePackage;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.MessagePackageService;

@SuppressWarnings("unchecked")
@Service("messagePackageService")
public class MessagePackageServiceImpl implements MessagePackageService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public List<MessagePackage> findMessagePackageByType(Integer type) throws Exception {
		return (List<MessagePackage>) daoSupport.findForList("MessagePackageMapper.findMessagePackageByType", type);

	}

	@Override
	public MessagePackage selMessagePackageById(Integer id) throws Exception {
		return (MessagePackage) daoSupport.findForObject("MessagePackageMapper.selectByPrimaryKey", id);

	}

}
