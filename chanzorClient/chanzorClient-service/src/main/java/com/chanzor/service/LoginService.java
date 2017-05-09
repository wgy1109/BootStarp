package com.chanzor.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.chanzor.util.FormData;

public interface LoginService {

	public Map<String, Object> login(FormData data, HttpSession session)
			throws Exception;

	public Map<String, Object> isMobileExits(String mobile) throws Exception;

	public Map<String, Object> reset(FormData data) throws Exception;

	public Map<String, Object> getNum(FormData data) throws Exception;

	public int register(FormData data) throws Exception;
	
	public Map<String, Object> numSmsUserInfo(FormData data) throws Exception;
	
	public int saveBusinessMessage(FormData data) throws Exception;
	
	public Map<String, Object> gerLoginMobile(FormData data, HttpSession session)
			throws Exception;
}
