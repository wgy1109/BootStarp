package com.chanzor.service;

import com.chanzor.entity.AppBalanceReminder;

public interface AppBalanceReminderService {

	public AppBalanceReminder selectBalanceReminderBySpId(Integer spId) throws Exception;

	public void addBalanceReminder(AppBalanceReminder appBalanceReminder) throws Exception;
	
	public void updateByPrimaryKeySelective(AppBalanceReminder appBalanceReminder) throws Exception;

}
