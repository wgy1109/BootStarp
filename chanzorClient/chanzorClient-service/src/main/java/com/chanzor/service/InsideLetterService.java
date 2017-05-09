package com.chanzor.service;

import java.util.List;
import java.util.Map;
import com.chanzor.entity.PageInfo;

public interface InsideLetterService {
	public List<Map<String, Object>> findLetterListPage(PageInfo page)
			throws Exception;

	public void delLetter(Integer id) throws Exception;

	public Integer findUnReadLetter(Integer userId) throws Exception;

	public void updUnReadLetter(Integer userId) throws Exception;
	
	public List<Map<String,Object>> findTopLteerByUser(Integer userId) throws Exception;
	
	public void checkAllLetter(Integer userId) throws Exception;
	
	public Map<String, Object> getLetterByid(Integer id) throws Exception;
	
}
