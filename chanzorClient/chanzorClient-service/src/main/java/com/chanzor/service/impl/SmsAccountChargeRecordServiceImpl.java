package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SmsAccountChargeRecord;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.SmsAccountChargeRecordService;

@Service("smsAccountChargeRecordServiceImpl")
@SuppressWarnings("unchecked")
public class SmsAccountChargeRecordServiceImpl implements SmsAccountChargeRecordService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(SmsAccountChargeRecord record) throws Exception {
		return (int) daoSupport.save("SmsAccountChargeRecordMapper.insert", record);
	}

	@Override
	public int insertSelective(SmsAccountChargeRecord record) throws Exception {
		return (int) daoSupport.findForList("SmsAccountChargeRecordMapper.insertSelective", record);
	}

	@Override
	public SmsAccountChargeRecord selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(SmsAccountChargeRecord record) throws Exception {
		return (Integer)daoSupport.update("AppBalanceReminderMapper.updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(SmsAccountChargeRecord record) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int updAaccountBalance(SmsAccountChargeRecord record) throws Exception {
		return  (int) daoSupport.update("SmsAccountChargeRecordMapper.updAaccountBalance", record);
	}

	@Override
	public List<SmsAccountChargeRecord> getAccountChargelistPage(PageInfo page) throws Exception {
		// TODO Auto-generated method stub
		return (List<SmsAccountChargeRecord>) daoSupport
				.findForList("SmsAccountChargeRecordMapper.getAccountChargelistPage", page);
	}

	@Override
	public Integer findAccountBalance(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return (Integer) daoSupport.findForObject("SmsAccountChargeRecordMapper.findAccountBalance", id);
	}

	@Override
	public Integer subtractAccountBalance(Map<String, Object> map) throws Exception {
		return (Integer) daoSupport.update("SmsAccountChargeRecordMapper.subtractAccountBalance", map);
	}

	@Override
	public SmsAccountChargeRecord selectByOrderNum(String orderNum) throws Exception {
		return (SmsAccountChargeRecord) daoSupport.findForObject("SmsAccountChargeRecordMapper.selectByOrderNum",
				orderNum);
	}

	@Override
	public Integer addAccountBalance(Map<String, Object> map) throws Exception {
		return (Integer) daoSupport.update("SmsAccountChargeRecordMapper.addAccountBalance", map);

	}

	@Override
	public Integer updChargeStatus(SmsAccountChargeRecord account) throws Exception {
		return (Integer) daoSupport.update("SmsAccountChargeRecordMapper.updChargeStatus", account);
	}

	@Override
	public Integer selAccountRefUserMap(Integer userId) throws Exception {
		return (Integer) daoSupport.findForObject("SmsAccountChargeRecordMapper.selAccountRefUserMap", userId);
	}

	@Override
	public void insAccountRefUser(Integer userId) throws Exception {
		daoSupport.save("SmsAccountChargeRecordMapper.insAccountRefUser", userId);

	}
	
	@Override
	public Integer updateIsRecharge(Map<String, Object> map) throws Exception{
		return (Integer) daoSupport.update("SmsAccountChargeRecordMapper.updateUserinfoIsRecharge", map);
	}
	
	@Override
	public int updateIsbeginrecharge(SmsAccountChargeRecord record) throws Exception{
		return (int) daoSupport.update("SmsAccountChargeRecordMapper.updateIsbeginrecharge", record);
	}
	
	@Override
	public int updateUserinfoInvoicevalue(Map<String, Object> map) throws Exception{
		return (int) daoSupport.update("SmsAccountChargeRecordMapper.updateUserinfoInvoicevalue", map);
	}
	
	@Override
	public Map<String, Object> userinfo(Integer userId) throws Exception{
		return (Map<String, Object>) daoSupport.findForObject("SmsAccountChargeRecordMapper.getUserinfoByid", userId);
	}

}
