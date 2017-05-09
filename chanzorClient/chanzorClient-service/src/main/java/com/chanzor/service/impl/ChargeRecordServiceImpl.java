package com.chanzor.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.ChargeRecordService;
import com.chanzor.entity.ChargeRecord;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpCharge;
import com.chanzor.entity.SpInfo;

@Service("chargeRecordService")
@SuppressWarnings("unchecked")
public class ChargeRecordServiceImpl implements ChargeRecordService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public List<SpInfo> spChargeByUserListPage(PageInfo page) throws Exception {
		return (List<SpInfo>) daoSupport.findForList("ChargeRecordMapper.spChargeByUserListPage", page);
	}

	public SpInfo spChategById(PageInfo page) throws Exception {
		return (SpInfo) daoSupport.findForObject("ChargeRecordMapper.spChargeByUserListPage", page);
	}

	public Double getPriceBySpId(Integer spId) throws Exception {
		return (Double) daoSupport.findForObject("ChargeRecordMapper.getPriceBySpId", spId);

	}

	public Integer findConponsNum(String Conpons) throws Exception {
		return (Integer) daoSupport.findForObject("ChargeRecordMapper.findConponsNum", Conpons);

	}

	public SpInfo spChargeByUserId(Integer spId) throws Exception {
		return (SpInfo) daoSupport.findForObject("ChargeRecordMapper.spChargeByUserId", spId);
	}

	public void insertChargeRecord(ChargeRecord chargeRecord) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String result = sdf.format(new Date());
		chargeRecord.setTablename("sms_charge_record_" + result.substring(2));
		daoSupport.save("ChargeRecordMapper.insertChargeRecord", chargeRecord);
	}

	public void updateSpCharge(SpCharge spCharge) throws Exception {
		daoSupport.update("ChargeRecordMapper.updateSpCharge", spCharge);

	}

	public void updateAllSpCharge(SpCharge spCharge) throws Exception {
		daoSupport.update("ChargeRecordMapper.updateAllSpCharge", spCharge);
	}

	@Override
	public void updateSpChargeByLeftOver(SpCharge spCharge) throws Exception {
		daoSupport.update("ChargeRecordMapper.updateSpChargeByLeftOver", spCharge);
	}

	public List<ChargeRecord> ChargeRecordListPage(PageInfo page) throws Exception {
		Map<String, Object> countAll = (Map<String, Object>) daoSupport
				.findForObject("ChargeRecordMapper.ChargeRecordListCount", page);
		page.setTotalSize(Integer.parseInt(countAll.get("count").toString()));
		return (List<ChargeRecord>) daoSupport.findForList("ChargeRecordMapper.ChargeRecordListP", page);
	}

	@Override
	public Integer selCountRecordByUserId(List spIdList) throws Exception {
		return (Integer) daoSupport.findForObject("ChargeRecordMapper.selCountRecordByUserId", spIdList);
	}

	@Override
	public Integer updTypeByProductId(Map<String, Object> map) throws Exception {
		return (Integer) daoSupport.update("ChargeRecordMapper.updTypeByProductId", map);
	}

	@Override
	public Integer updTypeByProductIdInUserCharge(Map<String, Object> map) throws Exception {
		return (Integer) daoSupport.update("ChargeRecordMapper.updTypeByProductIdInUserCharge", map);
	}

	@Override
	public List<Map<String, Object>> selChargeInfoByProId(String spId) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("ChargeRecordMapper.selChargeInfoByProId", spId);
	}

	@Override
	public Integer selChargeLeftOverNumBySpId(Integer spId) throws Exception {
		return (Integer) daoSupport.findForObject("ChargeRecordMapper.selChargeLeftOverNumBySpId", spId);
	}

	@Override
	public Map<String, Object> sleCharegeInfoByChargeId(Integer chargeId) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("ChargeRecordMapper.sleCharegeInfoByChargeId", chargeId);
	}

	@Override
	public Map<String, Object> selCharegeInfoByChargeId(String productId) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("ChargeRecordMapper.findAlipayTypeByProductId",
				productId);
	}

	@Override
	public void updateBalanceInChargeRecord(Map<String, Object> chargeInfo) throws Exception {
		daoSupport.update("ChargeRecordMapper.updateBalanceInChargeRecord", chargeInfo);

	}

	@Override
	public Integer updateSpchargerecharge(Map<String, Object> map) throws Exception {
		return (Integer) daoSupport.update("ChargeRecordMapper.updateSpchargerecharge", map);
	}

	@Override
	public Integer updateUserSpchargerecharge(Map<String, Object> map) throws Exception {
		return (Integer) daoSupport.update("ChargeRecordMapper.updateUserSpchargerecharge", map);
	}

	@Override
	public void insertUserChargeRecord(ChargeRecord chargeRecord) throws Exception {
		daoSupport.save("ChargeRecordMapper.insertUserChargeRecord", chargeRecord);
	}

}
