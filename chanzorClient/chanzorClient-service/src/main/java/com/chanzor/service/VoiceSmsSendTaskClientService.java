package com.chanzor.service;

import java.util.List;
import java.util.Map;

import com.chanzor.entity.PageInfo;
import com.chanzor.util.FormData;

public interface VoiceSmsSendTaskClientService {
	public List<Map<String, Object>> getAllSmsSendTaskClientByPage (PageInfo page )throws Exception;
	public List<Map<String, Object>> getSendStatisticsServiceByPage(PageInfo page)throws Exception;
	public List<Map<String, Object>> getSmsMtListByPage (PageInfo page )throws Exception;
	public Map<String, Object> getfileurlByfileid(FormData data) throws Exception;
}
