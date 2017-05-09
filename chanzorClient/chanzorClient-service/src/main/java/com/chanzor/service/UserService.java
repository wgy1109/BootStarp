package com.chanzor.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.chanzor.entity.PageInfo;
import com.chanzor.util.FormData;

public interface UserService {
	public Map<String, Object> getUserInfo(FormData data) throws Exception;

	public Map<String, Object> getUserBaseInfo(FormData data) throws Exception; // add
																				// by
																				// zyq

	public Map<String, Object> updateBaseInfoExceptMobile(FormData data, HttpSession session) throws Exception; // add
																												// by
																												// zyq

	public Map<String, Object> updateBaseInfoIncludeMobile(FormData data, HttpSession session) throws Exception; // add
																													// by
																													// zyq

	public Map<String, Object> updatePhoto(FormData data, HttpSession session) throws Exception; // add
																									// by
																									// zyq

	public Map<String, Object> updateUserInfo(FormData data, HttpSession session) throws Exception;

	public Map<String, Object> updatePsd(FormData data) throws Exception;

	public Map<String, Object> updateMobile(FormData data, HttpSession session) throws Exception;

	public Map<String, Object> getComAuthInfo(FormData data) throws Exception;

	public Map<String, Object> updateComAuthInfo(FormData data, HttpSession session) throws Exception;

	public Map<String, Object> checkCompany(String company) throws Exception;

	public Map<String, Object> authentication(FormData data) throws Exception;

	public Map<String, Object> senmdSMSCode(FormData data, HttpSession session) throws Exception;

	public Map<String, Object> getPirce(FormData data) throws Exception;

	public Map<String, Object> updateEamil(FormData data, HttpSession session) throws Exception;

	public void updateSessionUser(HttpSession session, FormData data) throws Exception;

	public Map<String, Object> sendEmailCode(HttpSession session, FormData data) throws Exception;

	public void charge(FormData data) throws Exception;

	public Map<String, Object> getSaleManager(Map map) throws Exception;

	public Map<String, Object> getSupportManager(Map map) throws Exception;

	Map<String, Object> findUserInfoById(Integer userId) throws Exception;

	public String servicesendSMSMobileCode(String mobile);

	public Map<String, Object> saveMobile(FormData data) throws Exception;

	public Map<String, Object> addSubAccount(FormData data, HttpSession session) throws Exception;

	public Map<String, Object> updateSubAccount(FormData data, HttpSession session) throws Exception;

	public Map<String, Object> delSubAccount(FormData data, HttpSession session) throws Exception;

	public List<Map<String, Object>> getSubAccountList(PageInfo page) throws Exception;

	public List<Map<String, Object>> getSubAccountSpList(Integer parentAccountId) throws Exception;

	Map<String, Object> findSubAccountById(Integer subAccountId) throws Exception;

	public List<Map<String, Object>> getSubAccountSps(Integer subAccountId) throws Exception;

	public List<Map<String, Object>> getSubAccountMenus(Integer subAccountId) throws Exception;

	public Map<String, Object> updateSubAccountPwd(FormData data) throws Exception;

	public Map<String, Object> updateMainAccountSecurityLoginMark(FormData data) throws Exception;

	public Map<String, Object> updateSubAccountSecurityLoginMark(FormData data) throws Exception;

	public List<Map<String, Object>> getMdnSection() throws Exception;

	public Map<String, Object> selPassAuthById(Integer userId) throws Exception;

	public Map<String, Object> updateAccount(FormData data, HttpSession session) throws Exception;

	public Map<String, Object> senmdSMSCodeNew(FormData data, HttpSession session) throws Exception;

}
