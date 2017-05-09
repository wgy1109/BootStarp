package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chanzor.entity.PageInfo;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.RedisService;
import com.chanzor.service.VoiceService;
import com.chanzor.util.FormData;
import com.chanzor.util.PropertiesConfig;

@Service("voiceService")
@SuppressWarnings("unchecked")
public class VoiceServiceImpl implements VoiceService {
	private static Logger log = Logger.getLogger(VoiceServiceImpl.class);
	
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;
	
	public List<Map<String, Object>> getVoiceList(PageInfo page)
			throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("VoiceMapper.getVoicelistPage", page);
	}

	
	public Map<String, Object> getVoiceById(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("VoiceMapper.getVoiceById", data);
	}
	
	public int saveVoice(FormData data) throws Exception {
		int result = (Integer) daoSupport.save("VoiceMapper.saveVoice", data);
		return result;
	}
	
	public int deleteVoice(FormData data) throws Exception {
		int result = (Integer) daoSupport.update("VoiceMapper.deleteVoice", data);
		return result;
	}


	@Override
	public List<Map<String, Object>> getAuditPassVoiceList(PageInfo page) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("VoiceMapper.getAuditPassVoicelistPage", page);
	}

}
