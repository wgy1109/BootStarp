package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.entity.PageInfo;
import com.chanzor.service.BlacklistService;
import com.chanzor.util.FormData;

@Service("blacklistService")
@SuppressWarnings("unchecked")
public class BlacklistServiceImpl implements BlacklistService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public List<Map<String, Object>> getAllBlacklistPage(PageInfo page)
			throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList(
				"BlacklistMapper.getAllBlacklistPage", page);
	}

	public Map<String, Object> getBlacklistById(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject(
				"BlacklistMapper.getBlacklistById", data);
	}
	public List<Map<String, Object>>getBlacklistByMdn(FormData data) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList(
				"BlacklistMapper.getBlacklistByMdn", data);
	}
	public List<Map<String, Object>> getAppList(FormData data) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList(
				"BlacklistMapper.getAppList", data);
	}

	public int saveBlacklist(FormData data) throws Exception {
		Object id = data.get("id");
		int result = 0;
		if (id == null || id.toString().equals("")) {
			data.put("type", "3");
			result = (Integer) daoSupport.save("BlacklistMapper.saveBlacklist", data);
		} else {
			result = (Integer) daoSupport.update("BlacklistMapper.updateBlacklist", data);
		}
		return result;
	}

	public int deleteBlacklist(FormData data) throws Exception {
		int result = (Integer) daoSupport.update("BlacklistMapper.deleteBlacklist", data);
		return result;
	}

	@Override
	public List<Map<String, Object>> getBlackListClientP(PageInfo page) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("BlacklistMapper.getSmsBlackListClientP", page);
	}
}
