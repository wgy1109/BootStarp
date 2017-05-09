package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.entity.PageInfo;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.InterSmsSendTaskClientService;
import com.chanzor.service.VoiceSmsSendTaskClientService;
import com.chanzor.util.FormData;

@Service("VoiceSmsSendTaskClientService")
@SuppressWarnings("unchecked")
public class VoiceSmsSendTaskClientServiceImpl implements VoiceSmsSendTaskClientService{

	@Resource(name="daoSupport")
	private DaoSupport daoSupport;
	
	//国际发送列表
	public List<Map<String, Object>> getAllSmsSendTaskClientByPage (PageInfo page )throws Exception{
		Map<String, Object> countAll = (Map<String, Object>)daoSupport.findForObject("VoiceSmsSendTaskClientMapper.getAllSmsSendTaskClientCount", page);
		page.setTotalSize(Integer.parseInt(countAll.get("count").toString()));
		return (List<Map<String, Object>>) daoSupport.findForList("VoiceSmsSendTaskClientMapper.getAllSmsSendTaskClientP", page);
	}

	@Override
	public List<Map<String, Object>> getSendStatisticsServiceByPage(PageInfo page) throws Exception {
		String sql = getSql(page);
		page.getFormData().put("sql", sql);
		return (List<Map<String, Object>>) daoSupport.findForList("VoiceSmsSendTaskClientMapper.getSendStatisticsByPage", page);
	}
	
	public String getSql(PageInfo page){
		FormData formdata = page.getFormData();
		StringBuffer stringbuffer = new StringBuffer();
		if(formdata.get("queryStartTime") !=null && !"".equals(formdata.get("queryStartTime")) ){
			stringbuffer.append(" and s.submit_date  >= '" + formdata.get("queryStartTime") + "'::date ");
		}
		if(formdata.get("queryEndTime") !=null && !"".equals(formdata.get("queryEndTime")) ){
			stringbuffer.append(" and s.submit_date  < '" + formdata.get("queryEndTime") + "'::date ");
		}
		String sql = "";
		String groupbys = "";
		String orderbys = "";
		StringBuffer groupby = new StringBuffer();
		StringBuffer selectmessage = new StringBuffer();
		if("2".equals(formdata.get("timegroup"))){
			selectmessage.append("s.submit_date msgTime,");
			groupby.append("s.submit_date,");
		}else if("1".equals(formdata.get("timegroup"))){
			selectmessage.append("to_char(s.submit_date,'yyyy-MM')  msgTime,");
			groupby.append("msgTime,");
		}
		if(formdata.get("spId") !=null && !"".equals(formdata.get("spId")) && !"-1".equals(formdata.get("spId")) ){
			if(!"0".equals(formdata.get("spId")) ){
				stringbuffer.append(" and s.user_sp_id = " + formdata.get("spId") );
			}
			selectmessage.append("user_sp_name,");
			groupby.append("user_sp_name,");
		}
		if(formdata.get("subAccountAppIds") != null){
			stringbuffer.append(" and s.user_sp_id in " + formdata.get("subAccountAppIds").toString());
			selectmessage.append("user_sp_name,");
			groupby.append("user_sp_name,");
		}
		if( "".equals(formdata.get("timegroup")) && "0".equals(formdata.get("spId"))){
			sql = "select  COALESCE(sum(s.submit_count),0) ALLNUM, COALESCE(sum(s.sended_count),0) SENDEDNUM, COALESCE(sum(s.success_count),0) ALLYES, COALESCE(sum(s.fail_count),0) ALLNO, COALESCE(sum(s.unknown_count),0) OTHERNUM "+
				   "from voice_sms_send_statistics s where 1 = 1 " + stringbuffer.toString();
			//+" and user_sp_id in (select spinfo.id from sms_sp_info spinfo where spinfo.user_id = "+ formdata.get("ID") +" ) ";
			if(formdata.get("subAccountAppIds") != null){
				sql += " and user_sp_id in " + formdata.get("subAccountAppIds").toString();
			}
			else{
				sql += " and user_sp_id in (select spinfo.id from sms_sp_info spinfo where spinfo.user_id = "+ formdata.get("ID") +" ) ";
			}
		}else{
			groupbys = groupby.substring(0, groupby.length()-1);
			orderbys = groupbys;
			if(orderbys.indexOf("s.submit_date") >= 0){
				orderbys = orderbys.replaceAll("s.submit_date", "s.submit_date desc");
			}else if(orderbys.indexOf("msgTime") >= 0){
				orderbys = orderbys.replaceAll("msgTime", "msgTime desc");
			}
			sql = "select "+ selectmessage +"  COALESCE(sum(s.submit_count),0) ALLNUM, COALESCE(sum(s.sended_count),0) SENDEDNUM, COALESCE(sum(s.success_count),0) ALLYES, COALESCE(sum(s.fail_count),0) ALLNO, COALESCE(sum(s.unknown_count),0) OTHERNUM "+
					   "from voice_sms_send_statistics s where 1 = 1 "+ stringbuffer.toString();
			//+" and user_sp_id in (select spinfo.id from sms_sp_info spinfo where spinfo.user_id = "+ formdata.get("ID") +" ) GROUP BY "+ groupbys + " order by " + orderbys;
			if(formdata.get("subAccountAppIds") != null){
				sql += " and user_sp_id in " + formdata.get("subAccountAppIds").toString() + " GROUP BY " +  groupbys + " order by " + orderbys;
			}
			else{
				sql += " and user_sp_id in (select spinfo.id from sms_sp_info spinfo where spinfo.user_id = "+ formdata.get("ID") +" ) GROUP BY "+  groupbys + " order by " + orderbys;
			}
		}
		return sql;
	}

	@Override
	public List<Map<String, Object>> getSmsMtListByPage(PageInfo page) throws Exception {
		Map<String, Object> countAll = (Map<String, Object>) daoSupport
				.findForObject("VoiceSmsSendTaskClientMapper.getSmsMtListClientCount", page);
		page.setTotalSize(Integer.parseInt(countAll.get("count").toString()));
		return (List<Map<String, Object>>) daoSupport.findForList("VoiceSmsSendTaskClientMapper.getSmsMtListClientP", page);
	}

	@Override
	public Map<String, Object> getfileurlByfileid(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("VoiceSmsSendTaskClientMapper.getfileurlByfileid", data);
	}

}
