package com.chanzor.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.chanzor.entity.PageInfo;
import com.chanzor.util.FormData;

public interface SmsSendTaskClientService {
	public List<Map<String, Object>> getAllSmsSendTaskClientByPage(PageInfo page) throws Exception;

	public List<Map<String, Object>> getSendStatisticsServiceByPage(PageInfo page) throws Exception;

	public List<Map<String, Object>> getAllSenstiveWord(Integer spId) throws Exception;

	public List<Map<String, Object>> getSmsMtListByPage(PageInfo page) throws Exception;

	public Map<String, Object> getExcelAsFile(FormData formData,String file, String content, Integer phoneIndex, String signature)
			throws FileNotFoundException, IOException;
	
	public List<Map<String, Object>> getAllSmsTimingClientByPage(PageInfo page) throws Exception;
	
	public List<Map<String, Object>> getSmsMtList(PageInfo page) throws Exception;

}
