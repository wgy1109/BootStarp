package com.chanzor.service;

import java.util.List;
import java.util.Map;

import com.chanzor.entity.PageInfo;
import com.chanzor.util.FormData;

public interface WhitelistService {
	public List<Map<String, Object>> getAllWhitelistPage(PageInfo page) throws Exception;
	public List<Map<String, Object>> getAllWhitelist(FormData data)	throws Exception ;
	public int getWhitelistCount(FormData data)	throws Exception ;
	public Map<String, Object> getWhitelistById(FormData data) throws Exception;
	public List<Map<String, Object>> getWhitelistByMdn(FormData data) throws Exception ;
	public int saveWhitelist(FormData data) throws Exception;
	public int deleteWhitelist(FormData data) throws Exception;
	public int updateWhitelist(FormData data) throws Exception ;
	public List<String> getAllWhitePhoneList(FormData data)	throws Exception ;

	
}
