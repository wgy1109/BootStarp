package com.chanzor.service;

import java.util.Map;

public interface SendTaskInfoService {
	public Integer getTackInfoByYesterDay(Map<String, Object> map) throws Exception;

	public Integer getTaskInfoByMonth(Map<String, Object> map) throws Exception;

	public Integer getTackInfoByYesterDay0(Map<String, Object> map) throws Exception;

	public Integer getTaskInfoByMonth0(Map<String, Object> map) throws Exception;
}
