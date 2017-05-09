package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.OperateLogService;
import com.chanzor.util.FormData;
import com.chanzor.entity.PageInfo;

@Service("operateLogService")
@SuppressWarnings("unchecked")
public class OperateLogServiceImpl implements OperateLogService{/*

	@Resource(name="daoSupport")
	private DaoSupport daoSupport;
	
	public List<Map<String, Object>> getAlloperateLogByPage (PageInfo page )throws Exception{
		return (List<Map<String, Object>>) daoSupport.findForList("operateLogMapper.getAlloperateLogByPage", page);
	}
	public Map<String, Object> getoperateLogInfoById (FormData data) throws Exception{
		return (Map<String, Object>) daoSupport.findForObject("operateLogMapper.getoperateLogInfoById", data);
	}
	public int saveoperateLog (FormData data) throws Exception {
		Object id = data.get("id");
		int result = 0 ;
		if(id == null || id.toString().equals("")){
			result = (Integer) daoSupport.save("operateLogMapper.saveoperateLog", data);
		}else{
			result = (Integer) daoSupport.update("operateLogMapper.updateoperateLog", data);
		}
		return result;
	} 
	public int deleteoperateLog(FormData data) throws Exception{
		int result = (Integer) daoSupport.delete("operateLogMapper.deleteoperateLog", data);
		return result;
	}
	
	public int delLogByTime(FormData data) throws Exception{
		int result = (Integer) daoSupport.delete("operateLogMapper.delLogByTime", data);
		return result;
	}
	
	public List<Map<String, Object>> getOpearType() throws Exception{
		return (List<Map<String, Object>>)daoSupport.findForList("operateLogMapper.getOpearType", null);
	}
*/}
