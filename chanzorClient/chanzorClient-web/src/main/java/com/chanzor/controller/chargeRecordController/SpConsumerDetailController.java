package com.chanzor.controller.chargeRecordController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.SpConsumerDetailService;
import com.chanzor.util.Const;
import com.chanzor.util.DateHelper;
import com.chanzor.util.FormData;

@Controller
@RequestMapping("consumerDetail")
@SuppressWarnings("unchecked")
public class SpConsumerDetailController extends BaseController {
	@Autowired
	private SpConsumerDetailService spConsumerDetailService;

	@RequestMapping(value = "/spConsumer")
	public ModelAndView listSpCharge(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("spConsumerDetail/ConsumeIndex");
		mv.addObject("queryStartTime", DateHelper.getOtherDateString(0,"yyyy-MM-dd"));
		return mv;
	}

	@RequestMapping(value = "/spConsumerList")
	@ResponseBody
	public Map<String, Object> spChargeList(PageInfo pageInfo, HttpSession session, SpInfo spInfo) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		FormData formData = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		formData.put("userId", userid);
		if (checkLandType(session)) {
			formData.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}

//		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
//		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
//			formData.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
//		}
		
		//乐元素，扩展子账号的消费明细查询功能
		Map<String, Object> currUser = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		//是子账号且需要扩展权限，将该子账号对应的大账户的id 放入formData
		if ((Integer) currUser.get("is_sub_account") == 1 && properties.getSubaccount_extend_finance().indexOf((String)currUser.get("user_name")) != -1) {
			//修改userId 为大账户的去查询
			userid =(Integer)currUser.get("parent_id");
			formData.put("userId", userid);
		}
		
		formData = DateHelper.formDataDateString(formData, "queryStartTime", "queryEndTime");
		pageInfo.setFormData(formData);
		List<Map<String, Object>> consumerList = spConsumerDetailService.findConsumerDetaillistPage(pageInfo);
		data.put("consumerList", consumerList);
		data.put("page", pageInfo);
		data.put("recordsTotal", pageInfo.getTotalSize());
		data.put("recordsFiltered", pageInfo.getTotalSize());
		return data;
	}

}