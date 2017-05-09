package com.chanzor.service;

import java.util.List;
import java.util.Map;

import com.chanzor.entity.PageInfo;
import com.chanzor.util.FormData;

public interface TextVoiceService {

	public List<Map<String, Object>> getTextVoiceList(PageInfo page)throws Exception;

	public Map<String, Object> getTextVoiceById(FormData data) throws Exception; 
	
	public int saveTextVoice(FormData data) throws Exception;
	
	public int updateTextVoice(FormData data) throws Exception;
	
	public int deleteTextVoice(FormData data) throws Exception;
	
	public int commitTextVoice(FormData data) throws Exception;
	
}
