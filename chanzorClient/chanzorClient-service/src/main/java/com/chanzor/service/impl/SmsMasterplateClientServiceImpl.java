package com.chanzor.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.entity.PageInfo;
import com.chanzor.service.SmsMasterplateClientService;
import com.chanzor.util.FormData;

@Service("SmsMasterplateClientService")
@SuppressWarnings("unchecked")
public class SmsMasterplateClientServiceImpl implements SmsMasterplateClientService {

	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public List<Map<String, Object>> getAllSmsMasterplateClientByPage(PageInfo page) throws Exception {
		List<Map<String, Object>> result = (List<Map<String, Object>>) daoSupport
				.findForList("SmsMasterplateClientMapper.getAllSmsMasterplateClientByPage", page);

		// List<Map<String, Object>> result2 = (List<Map<String, Object>>)
		// daoSupport.findForList(
		// "SmsMasterplateClientMapper.getMouldListToMasterplate", page);

		// result.addAll(result2);

		return result;
	}

	public List<Map<String, Object>> getSmsMasterplateBySessionSpInfo(PageInfo page) throws Exception {
		List<Map<String, Object>> result = (List<Map<String, Object>>) daoSupport
				.findForList("SmsMasterplateClientMapper.getSmsMasterplateBySessionSpInfo", page);
		return result;
	}

	public Map<String, Object> getSmsMasterplateClientInfoById(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport
				.findForObject("SmsMasterplateClientMapper.getSmsMasterplateClientInfoById", data);
	}

	public int saveSmsMasterplateClient(FormData data) throws Exception {
		Object id = data.get("id");
		int result = 0;
		data.put("STATUS", 3); // 默认 3，未提交，将不会进入审核状态，后台管理员也不能看到此信息
		data.put("TYPE", 0); // 默认0，未通过状态，只有当status 为0审核通过时，此状态才有作用
		data.put("UPDATE_TIME", new Date());
		if (id == null || id.toString().equals("")) {
			data.put("CREATE_TIME", new Date());
			result = (Integer) daoSupport.save("SmsMasterplateClientMapper.saveSmsMasterplateClient", data);
		} else {
			result = (Integer) daoSupport.update("SmsMasterplateClientMapper.updateSmsMasterplateClient", data);
		}
		return result;
	}

	public int deleteSmsMasterplateClient(FormData data) throws Exception {
		int result = (Integer) daoSupport.delete("SmsMasterplateClientMapper.deleteSmsMasterplateClient", data);
		return result;
	}

	// 提交模板，修改模板状态，进入待审核状态
	public int updateSmsMasterplateStatusService(FormData data) throws Exception {
		return (Integer) daoSupport.update("SmsMasterplateClientMapper.updateSmsMasterplateStatusClient", data);
	}

	// 得到应用下所有模板数量ALLNUM, 未审核数量（未提交审核+提交未审核）NOVALIDATENUM
	public Map<String, Object> getNumBySpidService(Integer id) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("SmsMasterplateClientMapper.getNumBySpidService", id);
	}

	// 得到系统模板 id + title
	public List<Map<String, Object>> getOntrialList() throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("MouldMapper.getMouldList", null);
	}

	// 根据系统模板id得到系统模板内容
	public Map<String, Object> getOntrialByID(FormData data) throws Exception {
		Map<String, Object> b = (Map<String, Object>) daoSupport.findForObject("MouldMapper.getMouldById", data);
		return b;
	}

	// 添加免审核模板
	public int saveonTrialSmsMasterplateClient(FormData data) throws Exception {
		int result = 0;
		data.put("STATUS", 1); // 默认 1 提交审核，导入的模板即提交审核。
								// 3，未提交，将不会进入审核状态，后台管理员也不能看到此信息
		data.put("TYPE", 0); // 默认0，未通过状态，只有当status为0审核通过时，此状态才有作用 tpye = 1 审核通过
		data.put("UPDATE_TIME", new Date());
		data.put("CREATE_TIME", new Date());
		result = (Integer) daoSupport.save("SmsMasterplateClientMapper.saveSmsMasterplateClient", data);
		return result;
	}

	// 根据输入的模板标题和内容判断模板是否已经存在
	public int validateontrialMessageService(FormData data) throws Exception {
		return (Integer) daoSupport.findForObject("SmsMasterplateClientMapper.validateontrialMessage", data);
	}

	@Override
	public List<Map<String, Object>> selPlateBySpId(Integer spId) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SmsMasterplateClientMapper.selPlateBySpId", spId);

	}

	@Override
	public List<Map<String, Object>> selectSpMaspAndSysMasp(Integer spId) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SmsMasterplateClientMapper.selSpSysMaspBySpId",
				spId);
	}

	@Override
	public List<Map<String, Object>> getVoiceTemplateBySpId(Integer spId) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SmsMasterplateClientMapper.getVoiceTemplateBySpId",
				spId);
	}
}
