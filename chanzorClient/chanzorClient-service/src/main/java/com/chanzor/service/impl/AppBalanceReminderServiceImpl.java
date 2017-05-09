package com.chanzor.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.entity.AppBalanceReminder;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.AppBalanceReminderService;

@Service("appBalanceReminderService")
@SuppressWarnings("unchecked")
public class AppBalanceReminderServiceImpl implements AppBalanceReminderService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	@Override
	public AppBalanceReminder selectBalanceReminderBySpId(Integer id) throws Exception {
		return (AppBalanceReminder) daoSupport.findForObject("AppBalanceReminderMapper.selectBalanceReminderBySpId", id);
	}

	@Override
	public void addBalanceReminder(AppBalanceReminder appBalanceReminder) throws Exception {
		daoSupport.save("AppBalanceReminderMapper.insert", appBalanceReminder);

	}

	@Override
	public void updateByPrimaryKeySelective(AppBalanceReminder appBalanceReminder) throws Exception {
		daoSupport.save("AppBalanceReminderMapper.updateByPrimaryKeySelective", appBalanceReminder);
	}

}
