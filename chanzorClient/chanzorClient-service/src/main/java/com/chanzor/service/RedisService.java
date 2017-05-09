package com.chanzor.service;

import java.util.List;
import java.util.Map;

import com.chanzor.entity.AppBalanceReminder;
import com.chanzor.entity.SpInfo;
import com.chanzor.util.FormData;

public interface RedisService {
	public void InsertRedis(SpInfo spInfo, Map<String, Object> userInfo) throws Exception;

	public void InsertNewRedis(SpInfo spInfo, Map<String, Object> userInfo) throws Exception;
	
	public void insertUserRedis(Map<String, Object> map, SpInfo spInfo, String Type) throws Exception;

	// 客戶端添加系統模板
	public void insSysTemplete(FormData fromData) throws Exception;

	public void insSysWhiteList(Integer spid, String phone) throws Exception;

	public void delSpInfoInRedis(Integer spId,List<String> detailList) throws Exception;

	public void editBlackList(Map<String, Object> oldMap, FormData formData) throws Exception;

	public Integer findRedisUnsend(String userName) throws Exception;

	public Integer findRedisPhoneCount(String userName) throws Exception;

	//废弃此方法，因为账号与mobile 可能是不同的
	//public void updBigName(String oldMobile, String newMobile) throws Exception;
	
	public void updateBigAccount(String oldAccount, String newAccount) throws Exception;

	public void delBlackList(FormData formData) throws Exception ;

	public void sendBlackInfo(Integer Type, Integer eventType, Integer id, String blackPhoneNumber);

	public void delWhiteList(Integer spid, String phoneNum) throws Exception ;
	
	
	public long addUnsend(Integer spid,Integer leftOverNum) throws Exception;
	
	public void insAppBalanceReminder(AppBalanceReminder appBalanceReminder) throws Exception;
	
	public void updSpInfoPassWord(FormData formData) throws Exception;
	
	public void updSpInfoIp(FormData formData) throws Exception;
	

	public String saveCustomizedMessageInRedis(FormData formData,List<String> contentList);
	
	public String getCustomizedMessageByRedis(String key);


}
