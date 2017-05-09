package com.chanzor.controller.user;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SmsAccountChargeRecord;
import com.chanzor.service.SmsAccountChargeRecordService;
import com.chanzor.service.UserService;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;
import com.chanzor.util.PropertiesConfig;
import com.chanzor.util.alipay.util.AlipaySubmit;
import com.chanzor.util.alipay.util.AlipayUtil;
import com.chanzor.util.alipay.util.UtilDate;

@Controller
@RequestMapping("account")
public class AccountController extends BaseController {

	@Autowired
	SmsAccountChargeRecordService smsAccountChargeRecordService;
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/index")
	public ModelAndView addressList(Model model, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("user/account");
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		Map<String, Object> userImg = userService.findUserInfoById(userid);
		if (userImg.get("photo_img") != null) {
			mv.addObject("photo_img", properties.getNginx_url() + userImg.get("photo_img"));
		}
		Integer accountLeftOverNum = smsAccountChargeRecordService.findAccountBalance(userid);
//		DecimalFormat df = new DecimalFormat("###############0.00 ");
		mv.addObject("accountBalance", accountLeftOverNum == null ? 0 : accountLeftOverNum);
		return mv;
	}

	@RequestMapping(value = "/accountList")
	public @ResponseBody Map<String, Object> accountList(Model model, PageInfo page, HttpSession session) throws Exception {
		FormData formData = getFormData();
		@SuppressWarnings("unchecked")
		Integer userId = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		
		//乐元素，扩展子账号的消费明细查询功能
		Map<String, Object> currUser = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		//是子账号且需要扩展权限，将该子账号对应的大账户的id 放入formData
		if ((Integer) currUser.get("is_sub_account") == 1 && properties.getSubaccount_extend_finance().indexOf((String)currUser.get("user_name")) != -1) {
			//修改userId 为大账户的去查询
			userId =(Integer)currUser.get("parent_id");
		}
		
		formData.put("userId", userId);
		page.setFormData(formData);
		List<SmsAccountChargeRecord> data = smsAccountChargeRecordService.getAccountChargelistPage(page);
		DecimalFormat df = new DecimalFormat("###############0.00 ");
		for (SmsAccountChargeRecord smsAccountChargeRecord : data) {
			smsAccountChargeRecord.setChargeNum(Double.parseDouble(df.format(smsAccountChargeRecord.getChargeNum()/100)));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		result.put("data", data);
		return result;
	}

	@RequestMapping(value = "/chargeAccount")
	public @ResponseBody String chargeAccount(Model model, HttpSession session) throws Exception {
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		if (userInfo != null) {
			FormData formData = getFormData();
			String chargeNum = formData.getString("chargeNum");
			String notify_url = properties.getNotify_url();
			String return_url = properties.getReturn_url();
			String out_trade_no = userInfo.get("id") + UtilDate.getOrderNum();
			String subject = "畅卓短信充值";
			Map<String, Object> conds = new HashMap<String, Object>();
			String body = "";
			body = "畅卓账号充值,金额:" + chargeNum;
			SmsAccountChargeRecord accountCharge = new SmsAccountChargeRecord();
			accountCharge.setChargeDate(new Date());
			accountCharge.setChargeUser((short) (int) userInfo.get("id"));
			accountCharge.setChargeType((short) 1);
			accountCharge.setChargeNum(Double.parseDouble(chargeNum));
			accountCharge.setChargeStatus((short) 0);
			accountCharge.setOrderNum(out_trade_no);
			accountCharge.setAccountBalance(
					smsAccountChargeRecordService.findAccountBalance((Integer) userInfo.get("id")) == null ? 0
							: smsAccountChargeRecordService.findAccountBalance((Integer) userInfo.get("id")));
			smsAccountChargeRecordService.insert(accountCharge);
			Map<String, String> parameterMap = AlipayUtil.buildParameterMap(notify_url, return_url, out_trade_no,
					subject, chargeNum + "", body, Const.CHARGEACCOUNT);
			String sHtmlText = AlipaySubmit.buildRequest(parameterMap, "post", "确认");
			return sHtmlText;
		}
		return null;
	}

	@RequestMapping(value = "/findAccountBalance")
	public @ResponseBody Map<String, Object> findAccountBalance(Model model, HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		Integer accountLeftOverNum = smsAccountChargeRecordService.findAccountBalance(userid);
		result.put("balance", accountLeftOverNum == null ? 0 : accountLeftOverNum);
		return result;
	}
}
