package com.chanzor.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.chanzor.entity.ChannelConfig;
import com.chanzor.entity.ChargeRecord;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpCharge;
import com.chanzor.entity.SpInfo;
import com.chanzor.entity.SpOrderPackage;
import com.chanzor.util.FormData;

public interface SpinfoService {
	public List<Map<String, Object>> SpInfolistPage(PageInfo page) throws Exception;

	public List<SpInfo> findSpInfolistPage(PageInfo page) throws Exception;

	public List<SpInfo> findSpListByUserId(Integer userId) throws Exception;

	public List<SpInfo> FindSpInfolistPage(PageInfo page) throws Exception;

	public SpInfo getSpinfoById(Integer spid) throws Exception;

	public void updateSpInfo(SpInfo spInfo) throws Exception;

	public Integer getSpCountByUsername(SpInfo spInfo) throws Exception;

	public Integer selSpCountByUserId(Integer user_id) throws Exception;

	public void insertSpinfo(SpInfo spInfo) throws Exception;

	public void insertSpSetting(SpInfo spInfo) throws Exception;

	public Integer getSpBySuffix(Map map) throws Exception;

	public Integer getSpBySuffix2(Map map) throws Exception;

	public void updateSpSetting(SpInfo spinfo) throws Exception;

	public void deleteSpChannel(Integer spid) throws Exception;

	public void insertChannelConfig(ChannelConfig config) throws Exception;

	public void updateSpChannel(SpInfo spInfo) throws Exception;

	public Integer updateSpConfig(SpInfo spInfo) throws Exception;

	public Integer getChargeCountById(SpCharge spCharge) throws Exception;

	public void insertSpCharge(SpCharge spCharge) throws Exception;

	public void insertSpChargeRecord(ChargeRecord chargeRecord) throws Exception;

	public void updateSpCharge(SpCharge spCharge) throws Exception;

	public List<SpInfo> listPageSpCharge(PageInfo page) throws Exception;

	public void updateSpLeftover(SpCharge spCharge) throws Exception;

	public SpInfo getSpInfo(SpInfo spInfo) throws Exception;

	public void updateOrderPackage(SpOrderPackage spOrderPackage) throws Exception;

	public void insertOrderPackage(SpOrderPackage spOrderPackage) throws Exception;

	public void deleteSpInfo(Integer spId) throws Exception;

	public List<SpInfo> listSpChannelInfoPage(PageInfo page) throws Exception;

	public List<SpInfo> listSpConfigPage(PageInfo page) throws Exception;

	public void updateSpInfostatus(Map<String, Object> map) throws Exception;

	public void updateSpInfoDescById(SpInfo spInfo) throws Exception;

	public List<Map<String, Object>> getSpinfoListByGroupIdService(FormData data) throws Exception;

	public Map<String, Object> getSpinfoListNumByGroupIdService(FormData data) throws Exception;

	public int updateGroupidByListService(FormData data) throws Exception;

	public List<Map<String, Object>> getOKSpinfoByUserIDService(FormData data) throws Exception;

	public List<Map<String, Object>> getYESSpinfoByUserIDService(FormData data) throws Exception;

	public Integer selSpName(Integer id) throws Exception;

	public void updSpName(Integer value) throws Exception;

	public void addDefaultSpInfo(SpInfo spInfo) throws Exception;

	public List<Integer> getSpIdListByForm(FormData formData) throws Exception;

	public String getSpUserName(Integer userId) throws Exception;

	public Integer findSpInfoCompanyStatus(Integer userId) throws Exception;

	public Map<String, Object> checkPhone(String mobile);

	public boolean isMobile(String mobile);

	public Integer getMinSendTotalById(Integer id) throws Exception;

	public List<SpInfo> findSpListNoFrozenByUserId(Integer userId) throws Exception;

	public List<SpInfo> findSpListByuserIdType(Map<String, Object> map) throws Exception;

	public List<SpInfo> findSpListByUserMobile(String mobile) throws Exception;

	public List<Map<String, Object>> getYesterDayMessageBySpId(Map<String,Object> map) throws Exception;

	public List<Map<String, Object>> getPreWeekMessageBySpId(FormData formData) throws Exception;

	public List<Map<String, Object>> getPreMonthMessageBySpId(FormData formData) throws Exception;

	public List<Map<String, Object>> selSendInfoBYMonth(Map<String,Object> map) throws Exception;

	public List<SpInfo> findSpInfoListByUserIdAndServiceType(FormData formData) throws Exception;

	public void createSpInfoByNewUser(Integer userId, String mobile) throws Exception;

	public Integer selDefaultSpInfoByServiceType(FormData formData) throws Exception;

	public SpInfo selectSpInfoByAccount(Map<String, Object> map) throws Exception;
	
	public void updSpInfoPassWord(FormData formData) throws Exception;
	
	public void updatespinfoIp(FormData formData) throws Exception;
	
    //取得审核通过的语音类应用
	public List<Map<String, Object>> getAuditPassVoiceSpinfoByUserID(FormData data) throws Exception;
	
	public List<Map<String, Object>> getAuditPassSpinfoByUserID(FormData data) throws Exception;
	
	//通过账号查询应用
	public List<SpInfo> findSpListByUserAccount(String accountName) throws Exception;

	public List<String> getSpInfoDetailList(Integer id) throws Exception;

	public void delSpInfoDetail(Integer spid) throws Exception;
	
}
