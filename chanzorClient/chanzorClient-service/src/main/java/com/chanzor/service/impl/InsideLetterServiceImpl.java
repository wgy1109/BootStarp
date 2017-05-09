package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.entity.PageInfo;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.InsideLetterService;

@Service("insideLetterService")
@SuppressWarnings("unchecked")
public class InsideLetterServiceImpl implements InsideLetterService {
	@Resource(name="daoSupport")
	private DaoSupport daoSupport;
	
	public List<Map<String,Object>>  findLetterListPage (PageInfo page)throws Exception{
		return (List<Map<String, Object>>) daoSupport.findForList("InsideLetterMapper.findLetterListPage", page);
	}
	public void delLetter(Integer id) throws Exception{
		 daoSupport.delete("InsideLetterMapper.delLetter", id);
	}
	public Integer findUnReadLetter(Integer userId) throws Exception{
		return  (Integer) daoSupport.findForObject("InsideLetterMapper.findUnreadLetter", userId);
	}
	public void updUnReadLetter(Integer userId) throws Exception{
	daoSupport.update("InsideLetterMapper.updUnreadLetter", userId);
	}
	@Override
	public List<Map<String, Object>> findTopLteerByUser(Integer userId) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("InsideLetterMapper.findTopLteerByUser", userId);

	}
	@Override
	public void checkAllLetter(Integer userId) throws Exception {
	daoSupport.update("InsideLetterMapper.checkAllLetter", userId);
		
	}
	@Override
	public Map<String, Object> getLetterByid(Integer id) throws Exception {
		return (Map<String, Object>)daoSupport.findForObject("InsideLetterMapper.getLetterByidMapper", id);
	}
}
