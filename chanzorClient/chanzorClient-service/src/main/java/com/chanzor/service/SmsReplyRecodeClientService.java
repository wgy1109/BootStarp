package com.chanzor.service;

import java.util.List;
import java.util.Map;

import com.chanzor.entity.PageInfo;
import com.chanzor.util.FormData;

public interface SmsReplyRecodeClientService {
	public List<Map<String, Object>> getAllSmsReplyRecodeClientByPage (PageInfo page )throws Exception;
	public Map<String, Object> getSmsReplyRecodeClientInfoByIdService (FormData data) throws Exception;
}
