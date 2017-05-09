package com.chanzor.service;

import java.util.List;
import java.util.Map;

import com.chanzor.entity.PageInfo;
import com.chanzor.util.FormData;

public interface VoiceService {

	public List<Map<String, Object>> getVoiceList(PageInfo page)throws Exception;

	public Map<String, Object> getVoiceById(FormData data) throws Exception; 
	
	public int saveVoice(FormData data) throws Exception;
	
	public int deleteVoice(FormData data) throws Exception;
	
	public List<Map<String, Object>> getAuditPassVoiceList(PageInfo page)throws Exception;
	
}
