package com.chanzor.service;

import java.util.List;
import java.util.Map;

import com.chanzor.entity.PageInfo;
import com.chanzor.util.FormData;

public interface SmsMasterplateClientService {
	public List<Map<String, Object>> getAllSmsMasterplateClientByPage (PageInfo page )throws Exception;
	public Map<String, Object> getSmsMasterplateClientInfoById (FormData data) throws Exception;
	public int saveSmsMasterplateClient (FormData data) throws Exception;
	public int deleteSmsMasterplateClient(FormData data) throws Exception;
	public int updateSmsMasterplateStatusService(FormData data) throws Exception;
	public Map<String, Object> getNumBySpidService(Integer id) throws Exception;
	public List<Map<String, Object>> getOntrialList() throws Exception;
	public Map<String, Object> getOntrialByID(FormData data) throws Exception;
	public int saveonTrialSmsMasterplateClient (FormData data) throws Exception;
	public int validateontrialMessageService(FormData data) throws Exception;
	public List<Map<String, Object>> selPlateBySpId(Integer spId) throws Exception;
	public List<Map<String,Object>> getVoiceTemplateBySpId(Integer spId) throws Exception;
	public List<Map<String, Object>> selectSpMaspAndSysMasp(Integer spId) throws Exception;
	public List<Map<String, Object>> getSmsMasterplateBySessionSpInfo(PageInfo page) throws Exception;

	
	
}
