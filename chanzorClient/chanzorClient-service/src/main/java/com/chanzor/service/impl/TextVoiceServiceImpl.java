package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chanzor.entity.PageInfo;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.TextVoiceService;
import com.chanzor.util.FormData;

@Service("textVoiceService")
@SuppressWarnings("unchecked")
public class TextVoiceServiceImpl implements TextVoiceService {
	private static Logger log = Logger.getLogger(TextVoiceServiceImpl.class);
	
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;
	
	public List<Map<String, Object>> getTextVoiceList(PageInfo page)
			throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("TextVoiceMapper.getTextVoicelistPage", page);
	}

	
	public Map<String, Object> getTextVoiceById(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("TextVoiceMapper.getTextVoiceById", data);
	}
	
	public int saveTextVoice(FormData data) throws Exception {
		int result = (Integer) daoSupport.save("TextVoiceMapper.saveTextVoice", data);
		return result;
	}
	
	public int updateTextVoice(FormData data) throws Exception {
		int result = (Integer) daoSupport.save("TextVoiceMapper.updateTextVoice", data);
		return result;
	}
	
	public int deleteTextVoice(FormData data) throws Exception {
		int result = (Integer) daoSupport.update("TextVoiceMapper.deleteTextVoice", data);
		return result;
	}


	@Override
	public int commitTextVoice(FormData data) throws Exception {
		int result = (Integer) daoSupport.update("TextVoiceMapper.commitTextVoice", data);
		return result;
	}

}
