package com.chanzor.service;

import java.util.List;
import java.util.Map;
import com.chanzor.entity.PageInfo;
import com.chanzor.util.FormData;

public interface BlacklistService {
	public List<Map<String, Object>> getAllBlacklistPage(PageInfo page)
			throws Exception;

	public Map<String, Object> getBlacklistById(FormData data) throws Exception;

	public List<Map<String, Object>> getBlacklistByMdn(FormData data)
			throws Exception;

	public List<Map<String, Object>> getAppList(FormData data) throws Exception;

	public int saveBlacklist(FormData data) throws Exception;

	public int deleteBlacklist(FormData data) throws Exception;
	
	public List<Map<String, Object>> getBlackListClientP(PageInfo page) throws Exception;
}
