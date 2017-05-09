package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.entity.PageInfo;
import com.chanzor.service.WhitelistService;
import com.chanzor.util.FormData;

@Service("whitelistService")
@SuppressWarnings("unchecked")
public class WhitelistServiceImpl implements WhitelistService{
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public List<Map<String, Object>> getAllWhitelistPage(PageInfo page)
			throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList(
				"WhitelistMapper.getAllWhitelistPage", page);
	}
	public List<Map<String, Object>> getAllWhitelist(FormData data)
			throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList(
				"WhitelistMapper.getAllWhitelist", data);
	}
	public int getWhitelistCount(FormData data)
			throws Exception {
		return (Integer) daoSupport.findForObject(
				"WhitelistMapper.getWhitelistCount", data);
	}

	public Map<String, Object> getWhitelistById(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject(
				"WhitelistMapper.getWhitelistById", data);
	}
	public List<Map<String, Object>> getWhitelistByMdn(FormData data) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList(
				"WhitelistMapper.getWhitelistByMdn", data);
	}

	public int saveWhitelist(FormData data) throws Exception {
		Object id = data.get("id");
		int result = 0;
		if (id == null || id.toString().equals("")) {
			result = (Integer) daoSupport.save("WhitelistMapper.saveWhitelist", data);
		} else {
			result = (Integer) daoSupport.update("WhitelistMapper.updateWhitelist", data);
		}
		return result;
	}

	public int deleteWhitelist(FormData data) throws Exception {
		int result = (Integer) daoSupport.update("WhitelistMapper.deleteWhitelist", data);
		return result;
	}

	public int updateWhitelist(FormData data) throws Exception {
		int result = (Integer) daoSupport.update("WhitelistMapper.updateWhitelist", data);
		return result;
	}
	@Override
	public List<String> getAllWhitePhoneList(FormData data) throws Exception {
		return (List<String>) daoSupport.findForList(
				"WhitelistMapper.getAllWhitePhoneList", data);
	}
}
