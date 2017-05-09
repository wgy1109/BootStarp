package com.chanzor.controller.chargeRecordController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.ChargeRecord;
import com.chanzor.entity.MessagePackage;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SmsAccountChargeRecord;
import com.chanzor.entity.SpCharge;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.AppCommissionStatisticsServer;
import com.chanzor.service.AppOrderInfoService;
import com.chanzor.service.ChargeRecordService;
import com.chanzor.service.MessagePackageService;
import com.chanzor.service.RedisService;
import com.chanzor.service.SmsAccountChargeRecordService;
import com.chanzor.service.SpinfoService;
import com.chanzor.util.Const;
import com.chanzor.util.DateHelper;
import com.chanzor.util.FormData;
import com.chanzor.util.Tools;
import com.chanzor.util.alipay.util.AlipaySubmit;
import com.chanzor.util.alipay.util.AlipayUtil;
import com.chanzor.util.alipay.util.UtilDate;
import com.chanzor.util.mail.SimpleMailSender;

@Controller
@RequestMapping("chargeRecord")
public class SpChargeController extends BaseController {
	@Autowired
	private ChargeRecordService chargeRecordService;
	@Autowired
	private SpinfoService spinfoService;
	@Autowired
	private AppOrderInfoService appOrderInfoService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private MessagePackageService messagePackageService;
	@Autowired
	private SmsAccountChargeRecordService smsAccountChargeRecordService;
	@Autowired
	private AppCommissionStatisticsServer commissionService;

	private final static Logger logger = LoggerFactory.getLogger(SpChargeController.class);

	@RequestMapping(value = "/spCharge")
	public ModelAndView listSpCharge(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("spCharge/chargeIndex");
		return mv;
	}

	@RequestMapping(value = "/spChargeList")
	@ResponseBody
	public Map<String, Object> spChargeList(PageInfo page, SpInfo spInfo) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		FormData formData = getFormData();
		page.setFormData(getFormData());
		page.setFormData(formData);
		List<SpInfo> spChargeList = chargeRecordService.spChargeByUserListPage(page);
		data.put("data", spChargeList);
		data.put("page", page);
		return data;
	}

	@RequestMapping(value = "/findConponsNum")
	@ResponseBody
	public Map<String, Object> findConponsNum(String conpons, SpInfo spInfo) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		Integer num = chargeRecordService.findConponsNum(conpons);
		data.put("num", num);
		return data;
	}

	@RequestMapping(value = "/findChargeNum")
	@ResponseBody
	public Map<String, Object> findChargeNum(Integer spId, PageInfo page, SpInfo spInfo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		SpInfo spinfo = chargeRecordService.spChargeByUserId(spId);
		System.out.println(spinfo.getSaleprice());
		map.put("spInfo", spinfo);
		return map;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/directPaySpInfo")
	public ModelAndView directPaySpInfo(Integer spId, PageInfo page, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		mv.setViewName("spCharge/paySpInfo");
		List<MessagePackage> domiesticList = messagePackageService.findMessagePackageByType(1);
		List<MessagePackage> interList = messagePackageService.findMessagePackageByType(2);
		List<MessagePackage> voiceList = messagePackageService.findMessagePackageByType(3);

		if (spId != null && spId != 0) {
			mv.addObject("spId", spId);
		}
		if (checkLandType(session)) {
			mv.addObject("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		Integer accountLeftOverNum = smsAccountChargeRecordService.findAccountBalance(userid);
		// DecimalFormat df = new DecimalFormat("###############0.00 ");
		mv.addObject("accountBalance", accountLeftOverNum == null ? 0 : accountLeftOverNum);
		mv.addObject("domiesticList", domiesticList);
		mv.addObject("interList", interList);
		mv.addObject("voiceList", voiceList);
		return mv;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/findSpInfoList")
	public @ResponseBody Map<String, Object> findSpInfoList(Integer serviceType, HttpSession session) throws Exception {
		FormData formData = getFormData();
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		formData.put("userId", userInfo.get("id"));
		Map<String, Object> map = new HashMap<String, Object>();
		List<SpInfo> spList = spinfoService.findSpInfoListByUserIdAndServiceType(formData);
		map.put("info", spList);
		return map;
	}

	/**
	 * 短信充值方法
	 * 
	 * @param spId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "payCoupons")
	public @ResponseBody String payCoupons(Integer spId, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FormData formData = getFormData();
		if (Tools.notEmpty((String) formData.get("couponsInput"))) {
			Map<String, Object> map = new HashMap<String, Object>();
			Integer num = chargeRecordService.findConponsNum((String) formData.get("couponsInput"));
			map.put("sp_id", spId);
			map.put("num", num);
			// 查询优惠券是否失效
			if (num != null && num != 0) {
				Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
				Map<String, Object> coupon = appOrderInfoService.getCouponByNum((String) formData.get("couponsInput"));
				if (coupon == null || coupon.toString().equals("")) {
					this.insSpChargeRecordInfo(spId, num, 0, "优惠券已失效");
					return "102";// 优惠券已失效
				}
				if (String.valueOf(coupon.get("ISCLOSE")).equals("2")
						&& String.valueOf(coupon.get("TYPE")).equals("2")) {
					this.insSpChargeRecordInfo(spId, num, 0, "优惠券已失效");
					return "102";// 优惠券已失效
				}
				if (String.valueOf(coupon.get("TYPE")).equals("1")) {
					List<SpInfo> list = spinfoService
							.findSpListByUserId(Integer.valueOf(userInfo.get("ID").toString()));
					List<Integer> spIdList = new ArrayList<Integer>();
					for (SpInfo spInfo : list) {
						spIdList.add(spInfo.getSpid());
					}
					Integer count = chargeRecordService.selCountRecordByUserId(spIdList);
					if (count > 0) {
						this.insSpChargeRecordInfo(spId, num, 0, "当前用户已使用过此优惠券");
						return "203";
					} else {
						this.chargeCoupon(coupon, spId, num);
						this.insSpChargeRecordInfo(spId, num, 1, "优惠券充值成功");
						return "200";
					}

				}
				if (String.valueOf(coupon.get("TYPE")).equals("2")) {
					if (Integer.valueOf(coupon.get("USERID").toString()).equals(Integer.valueOf(userInfo.get("ID").toString()))) {
						this.chargeCoupon(coupon, spId, num);
						this.insSpChargeRecordInfo(spId, num, 1, "优惠券充值成功");
						// 充值后将优惠卷更新为失效
						appOrderInfoService.updateInvalidByNum((String) formData.get("couponsInput"));
						return "200";
					} else {
						// 专用优惠券指定用户和当前用户不不匹配
						this.insSpChargeRecordInfo(spId, num, 0, "专用优惠券指定用户和当前用户不不匹配");
						return "202";
					}
				}
			} else {
				this.insSpChargeRecordInfo(spId, num, 0, "没找到输入的优惠卷");
				return "104";// 没找到输入的优惠卷
			}
		}
		return "200";

	}

	public void chargeCoupon(Map<String, Object> coupon, Integer spId, Integer num) throws Exception {
		SpCharge spCharge = new SpCharge();
		spCharge.setLeftover_num(Integer.parseInt(coupon.get("SMS_NUM").toString()));
		spCharge.setSp_id(spId);
		// 更新SMS_SP_CHARGE 即当前应用剩余短信
		chargeRecordService.updateSpCharge(spCharge);
	}

	public void insSpChargeRecordInfo(Integer spId, Integer num, Integer alipayType, String desc) throws Exception {
		ChargeRecord chargeRecord = new ChargeRecord();
		chargeRecord.setSp_id(spId);
		chargeRecord.setChargenum(0);
		chargeRecord.setType(1);
		chargeRecord.setAlipayType(alipayType);
		chargeRecord.setCharge_type(3);
		chargeRecord.setAmount(0);
		chargeRecord.setPrice(0);
		chargeRecord.setDescption("优惠券充值,优惠券编号:" + num);
		// 更新充值记录表
		chargeRecordService.insertChargeRecord(chargeRecord);
		chargeRecordService.insertUserChargeRecord(chargeRecord);
	}

	@RequestMapping(value = "payMth")
	public ModelAndView recharge(Integer spId, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FormData formData = getFormData();
		String sHtmlText = this.pay(spId, null, formData);
		if (spinfoService.getSpinfoById(spId).getSp_service_type().toString().equals("3")) {
			ModelAndView mv = new ModelAndView("spCharge/alipay");
			mv.addObject("chargeType", 0);
			mv.addObject("sHtmlText", "充值失败");
			mv.setViewName("spCharge/alipayComplete");
			return mv;
		}
		ModelAndView mv = new ModelAndView("spCharge/alipay");
		mv.addObject("sHtmlText", sHtmlText);
		return mv;

	}

	public String pay(Integer spId, Integer packageId, FormData formData) throws Exception {
		SpInfo spinfo = chargeRecordService.spChargeByUserId(spId);
		Map<String, Object> countPriceMap = findNumByChargeInfo(formData, spinfo);
		if (countPriceMap.get("price") != null && countPriceMap.get("leftover_num") != null
				&& countPriceMap.get("total") != null) {
			double price = (double) countPriceMap.get("price");
			String leftover_num = (String) countPriceMap.get("leftover_num");
			double total = (double) countPriceMap.get("total");
			String notify_url = properties.getNotify_url();
			String return_url = properties.getReturn_url();
			String out_trade_no = formData.get("userId") + UtilDate.getOrderNum();
			String subject = "畅卓短信充值";
			Map<String, Object> conds = new HashMap<String, Object>();
			String body = "";
			if (formData.get("chargeType").equals("domstic")) {
				body = "畅卓短信订购,单价:" + price + " ,条数:" + leftover_num + " ,共计:" + total;
			} else if (formData.get("chargeType").equals("inter")) {
				body = "畅卓短信订购,总价:" + leftover_num + " ,折扣:" + (price == 1 ? price : (price * 10)) + "折" + " ,共计:"
						+ total;
			} else {
				body = "畅卓短信订购,单价:" + price + " ,条数:" + leftover_num + " ,共计:" + total;
			}
			// 添加用户充值记录
			ChargeRecord chargeRecord = new ChargeRecord();
			chargeRecord.setSp_id(spId);
			if (formData.get("chargeType").equals("inter")) {
				chargeRecord.setChargenum((int) total * 100);
				chargeRecord.setAmount((int) total * 100);
			} else {
				chargeRecord.setChargenum(Integer.valueOf(leftover_num.toString()));
				chargeRecord.setAmount((float) (total * 100));
			}
			chargeRecord.setType(1);
			chargeRecord.setCharge_type(2);
			chargeRecord.setAlipayType(0);
			chargeRecord.setPrice((float) price);
			chargeRecord.setProductid(out_trade_no);
			chargeRecord.setBalance(spinfo.getLeftover_num());
			chargeRecordService.insertChargeRecord(chargeRecord);
			chargeRecordService.insertUserChargeRecord(chargeRecord);
			Map<String, String> parameterMap = AlipayUtil.buildParameterMap(notify_url, return_url, out_trade_no,
					subject, total + "", body, Const.CHARGESPINFO);
			String sHtmlText = AlipaySubmit.buildRequest(parameterMap, "post", "确认");
			return sHtmlText;
		} else {
			return "";
		}

	}

	@RequestMapping(value = "payOrderMth")
	public ModelAndView payOrderMth(Integer orderId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = chargeRecordService.sleCharegeInfoByChargeId(orderId);
		FormData formData = getFormData();
		ModelAndView mv = new ModelAndView("spCharge/alipay");
		if (map != null && map.get("charge_num") != null && map.get("sp_id") != null) {
			formData.put("messageNumInput", map.get("charge_num").toString());
			String sHtmlText = this.pay(Integer.valueOf(map.get("sp_id").toString()), null, formData);
			mv.addObject("sHtmlText", sHtmlText);
			return mv;
		}
		return null;
	}

	@RequestMapping(value = "chargeAccountMth")
	public @ResponseBody Map<String, Object> chargeAccountMth(Integer spId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Integer accountLeftOverNum = smsAccountChargeRecordService.findAccountBalance(userid);
		if (accountLeftOverNum == null) {
			returnMap.put("statusCode", 205);
			return returnMap;
		}
		FormData formData = getFormData();
		SpInfo spinfo = chargeRecordService.spChargeByUserId(spId);
		if (spinfo.getSp_service_type().toString().equals("3")) {
			returnMap.put("statusCode", 300);
			return returnMap;
		}
		Map<String, Object> countPriceMap = findNumByChargeInfo(formData, spinfo);
		if (countPriceMap.get("price") != null && countPriceMap.get("leftover_num") != null
				&& countPriceMap.get("total") != null) {
			double price = (double) countPriceMap.get("price");
			String leftover_num = (String) countPriceMap.get("leftover_num");
			double total = (double) countPriceMap.get("total");
			if ((accountLeftOverNum / 100) >= total) {
				Map<String, Object> accountChargeInfo = new HashMap<String, Object>();
				accountChargeInfo.put("total", total * 100);
				accountChargeInfo.put("userId", userid);
				smsAccountChargeRecordService.subtractAccountBalance(accountChargeInfo);
				Integer balance = smsAccountChargeRecordService.findAccountBalance(userid.intValue());
				SmsAccountChargeRecord account = new SmsAccountChargeRecord();
				account.setChargeUser((short) userid.intValue());
				account.setChargeSpId(spId);
				account.setChargeStatus((short) 1);
				account.setChargeDate(new Date());
				account.setChargeNum((double) total);
				account.setChargeType((short) 2);
				account.setAccountBalance(balance);
				smsAccountChargeRecordService.insert(account);
				SpCharge spCharge = new SpCharge();
				spCharge.setSp_id(spinfo.getSpid());
				long unSend = 0;
				if (spinfo.getSp_service_type().toString().equals("2")) {
					unSend = redisService.addUnsend(spinfo.getSpid(), Integer.valueOf(leftover_num) * 100);
				} else {
					unSend = redisService.addUnsend(spinfo.getSpid(), (int) Double.parseDouble(leftover_num));
				}
				spCharge.setLeftover_num((int) unSend);
				chargeRecordService.updateAllSpCharge(spCharge);
				spinfo = chargeRecordService.spChargeByUserId(spId);
				ChargeRecord chargeRecord = new ChargeRecord();
				chargeRecord.setSp_id(spId);
				if (spinfo.getSp_service_type().toString().equals("2")) {
					chargeRecord.setChargenum((int) total * 100);
					chargeRecord.setAmount(Float.parseFloat(leftover_num) * 100);
				} else {
					chargeRecord.setChargenum(Integer.valueOf(leftover_num.toString()));
					chargeRecord.setAmount((float) (total) * 100);
				}
				chargeRecord.setType(1);
				chargeRecord.setCharge_type(3);
				chargeRecord.setAlipayType(1);
				chargeRecord.setPrice((float) price);
				chargeRecord.setBalance((int) unSend);
				chargeRecordService.insertChargeRecord(chargeRecord);
				chargeRecordService.insertUserChargeRecord(chargeRecord);
				// 销售提成信息记录 国内提成计算公式
				/*
				 * if (spinfo.getSp_service_type().toString().equals("1")) {
				 * Map<String, Object> userinfo =
				 * smsAccountChargeRecordService.userinfo(userid); Map<String,
				 * Object> map = new HashMap<String, Object>();
				 * map.put("sp_manager_id", userinfo.get("app_manager_id"));
				 * map.put("company_name", userinfo.get("company"));
				 * map.put("company_account", userinfo.get("account_name"));
				 * map.put("sp_id", spinfo.getSpid()); map.put("sp_name",
				 * spinfo.getSp_name()); map.put("sp_recharge_money", total *
				 * 100); map.put("sp_recharge_unitprice", price); Double
				 * commissionMoney = ((int) Math.floor(Double.valueOf(total /
				 * price)) * (price - 0.03)) * 0.1;
				 * logger.info("销售提成记录：大账户转应用充值类型  " +
				 * spinfo.getSp_service_type().toString() +
				 * " (1：国内，2：国际，3：语音)，充值金额：" + total + "，充值提成：" +
				 * commissionMoney); map.put("commission_money",
				 * commissionMoney);
				 * commissionService.insertSalesStatistics(map); }
				 */
				returnMap.put("balance", balance);
				returnMap.put("statusCode", 200);
				return returnMap;
			} else {
				returnMap.put("statusCode", 205);
				return returnMap;
			}
		} else

		{
			returnMap.put("statusCode", 205);
			return returnMap;
		}

	}

	public Map<String, Object> findNumByChargeInfo(FormData formData, SpInfo spinfo) {
		double price = 0.05;
		double total = 0;
		String leftover_num = "";
		Map<String, Object> map = new HashMap<String, Object>();
		if (formData.get("chargeType").equals("inter")) {
			Integer num = Integer.valueOf(formData.getString("inputInter"));
			if (num > 10000 && num <= 20000) {
				price = 0.95;
			} else if (num > 20000 && num <= 50000) {
				price = 0.81;
			} else if (num > 50000) {
				price = 0.81;
			} else {
				price = 1;
			}
			leftover_num = num.toString();
			total = num * price;
		} else {
			Double salePrice = spinfo.getSaleprice();
			Integer num = 0;
			if (formData.get("chargeType").equals("voice")) {
				num = Integer.valueOf(formData.getString("inputVoice"));
			} else {
				num = Integer.valueOf(formData.getString("messageNumInput"));
			}

			price = 0.05;
			if (salePrice != null && salePrice != 0) {
				price = salePrice;
			}
			if (salePrice == 0.06) {
				if (10000 < num && num <= 20000) {
					price = 0.058;
				} else if (num >= 20001 && num <= 30000) {
					price = 0.055;
				} else if (num > 30000) {
					price = 0.05;
				}
			}
			total = num;
			leftover_num = String.valueOf(Math.round(num / price));
		}
		map.put("total", total);
		map.put("leftover_num", leftover_num);
		map.put("price", price);
		return map;
	}

	@RequestMapping(value = "findAccountBalance")
	public @ResponseBody Integer findAccountBalance(Integer orderId, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) throws Exception {
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		Integer leftOverNum = smsAccountChargeRecordService.findAccountBalance(userid);
		return leftOverNum == null ? 0 : leftOverNum;
	}

	@RequestMapping(value = "alipay/alipay_notify_url")
	@SuppressWarnings("unchecked")
	public String alipay_notify_url(HttpServletRequest request, HttpSession session) throws Exception {
		logger.info("----------------------------调用异步返回方法-----------------");
		Map<String, Object> param = bindParamToMap(request);
		Map<String, String> p = new HashMap<String, String>();
		for (String key : param.keySet()) {
			p.put(key, param.get(key).toString());
		}
		Map<String, Object> useridmap = new HashMap<String, Object>();
		boolean flag = AlipaySubmit.verify(p);
		SimpleMailSender mail = new SimpleMailSender();
		if (flag) {
			String commonParam = p.get("extra_common_param");
			String ordernum = p.get("out_trade_no");
			if (commonParam.equals(Const.CHARGEACCOUNT)) {
				SmsAccountChargeRecord account = smsAccountChargeRecordService.selectByOrderNum(ordernum);
				account.setChargeStatus((short) 1);
				Integer row = smsAccountChargeRecordService.updChargeStatus(account);
				if (row != null && row == 0) {
					return "success";
				}
				Integer userCount = smsAccountChargeRecordService.selAccountRefUserMap((int) account.getChargeUser());
				if (userCount < 1) {
					smsAccountChargeRecordService.insAccountRefUser((int) account.getChargeUser());
				}

				Map<String, Object> addAccountBalance = new HashMap<String, Object>();
				addAccountBalance.put("total", account.getChargeNum());
				addAccountBalance.put("userId", account.getChargeUser());
				smsAccountChargeRecordService.addAccountBalance(addAccountBalance);
				account.setAccountBalance((int) (account.getAccountBalance() == null ? 0
						: account.getAccountBalance() + account.getChargeNum()));
				smsAccountChargeRecordService.updAaccountBalance(account);
				useridmap.put("userid", account.getChargeUser());
				// 增加可开发票金额
				useridmap.put("addmoney", account.getChargeNum());
				smsAccountChargeRecordService.updateUserinfoInvoicevalue(useridmap);
				// 首单充值记录
				Map<String, Object> userinfo = smsAccountChargeRecordService
						.userinfo(Integer.parseInt(account.getChargeUser().toString()));
				if ("0".equals(userinfo.get("is_recharge").toString())) {
					smsAccountChargeRecordService.updateIsRecharge(useridmap);
					account.setIsbeginrecharge(1);
					smsAccountChargeRecordService.updateIsbeginrecharge(account);
				}
				// 销售提成信息记录 国内提成计算公式
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sp_manager_id", userinfo.get("app_manager_id"));
				map.put("company_name", userinfo.get("company"));
				map.put("company_account", userinfo.get("account_name"));
				map.put("sp_id", 0);
				map.put("sp_name", "");
				map.put("sp_recharge_money", account.getChargeNum());
				map.put("sp_recharge_unitprice", 0);
				map.put("commission_money", 0);
				map.put("sp_recharge_num", 0);
				map.put("remark", "[大账户线上充值]");
				if ("0".equals(userinfo.get("is_recharge").toString())) {
					map.put("is_begin_recharge", 1);
				}
				commissionService.insertSalesStatistics(map);
				String content = "大账户充值通知  -- 公司名称：" + userinfo.get("company") + ", 公司账户："
						+ userinfo.get("account_name") + ", 充值金额：" + account.getChargeNum() / 100 + "元";
				mail.sendMail(Const.FinanceAddress, "大账户充值通知", content);
				return "success";
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("alipayType", 1);
				map.put("productId", ordernum);
				Integer row = chargeRecordService.updTypeByProductId(map);
				chargeRecordService.updTypeByProductId(map);
				if (row != null && row == 0) {
					return "success";
				}
				List<Map<String, Object>> chargeRecordList = chargeRecordService.selChargeInfoByProId(ordernum);
				for (Map<String, Object> chargeInfo : chargeRecordList) {
					SpInfo spInfo = spinfoService.getSpinfoById(Integer.valueOf(chargeInfo.get("sp_id").toString()));
					// 更新SMS_SP_CHARGE 即当前应用剩余短信
					SpCharge spCharge = new SpCharge();
					Map<String, Object> balanceInfo = new HashMap<String, Object>();
					switch (spInfo.getSp_service_type()) {
					case 1:
						redisService.addUnsend(Integer.valueOf(chargeInfo.get("sp_id").toString()),
								Integer.valueOf(chargeInfo.get("charge_num").toString()));
						spCharge.setLeftover_num(Integer.valueOf(chargeInfo.get("charge_num").toString()));
						balanceInfo.put("chargeNum", Integer.valueOf(chargeInfo.get("charge_num").toString()));
						break;
					case 2:
						System.out.println(chargeInfo.get("amount").toString());
						redisService.addUnsend(Integer.valueOf(chargeInfo.get("sp_id").toString()),
								(int) Double.parseDouble(chargeInfo.get("charge_num").toString()));
						spCharge.setLeftover_num((int) Double.parseDouble(chargeInfo.get("charge_num").toString()));
						balanceInfo.put("chargeNum", (int) Double.parseDouble(chargeInfo.get("charge_num").toString()));
						break;
					case 3:
						redisService.addUnsend(Integer.valueOf(chargeInfo.get("sp_id").toString()),
								Integer.valueOf(chargeInfo.get("charge_num").toString()));
						spCharge.setLeftover_num(Integer.valueOf(chargeInfo.get("charge_num").toString()));
						balanceInfo.put("chargeNum", Integer.valueOf(chargeInfo.get("charge_num").toString()));
						break;
					}
					spCharge.setSp_id(Integer.valueOf(chargeInfo.get("sp_id").toString()));
					chargeRecordService.updateSpCharge(spCharge);
					balanceInfo.put("productId", ordernum);
					chargeRecordService.updateBalanceInChargeRecord(balanceInfo);
					useridmap.put("userid", spInfo.getUserId());
					// 增加可开发票金额
					DecimalFormat df = new DecimalFormat("###############0.00 ");
					useridmap.put("addmoney", df.format(Double.parseDouble(chargeInfo.get("amount").toString())));
					smsAccountChargeRecordService.updateUserinfoInvoicevalue(useridmap);
					// 首单充值记录
					Map<String, Object> userinfo = smsAccountChargeRecordService.userinfo(spInfo.getUserId());
					if ("0".equals(userinfo.get("is_recharge").toString())) {
						smsAccountChargeRecordService.updateIsRecharge(useridmap);
						chargeRecordService.updateSpchargerecharge(chargeInfo);
						chargeRecordService.updateUserSpchargerecharge(chargeInfo);
					}
					// 销售提成信息记录 国内提成计算公式
					if (spInfo.getSp_service_type().toString().equals("1")) {
						map.put("sp_manager_id", userinfo.get("app_manager_id"));
						map.put("company_name", userinfo.get("company"));
						map.put("company_account", userinfo.get("account_name"));
						map.put("sp_id", spInfo.getSpid());
						map.put("sp_name", spInfo.getSp_name());
						map.put("sp_recharge_money", chargeInfo.get("amount"));
						map.put("sp_recharge_unitprice", chargeInfo.get("price"));
						Double commissionMoney = (((int) Math
								.floor(Double.parseDouble(chargeInfo.get("amount").toString()) / 100
										/ Double.valueOf(chargeInfo.get("price").toString())))
								* (Double.valueOf(chargeInfo.get("price").toString()) - 0.03)) * 0.1;
						logger.info("销售提成记录：线上支付宝应用充值类型  " + spInfo.getSp_service_type().toString()
								+ " (1：国内，2：国际，3：语音)，充值金额：" + chargeInfo.get("amount").toString() + "，充值提成："
								+ commissionMoney);
						map.put("commission_money", commissionMoney);
						map.put("sp_recharge_num", Integer.valueOf(chargeInfo.get("charge_num").toString()));
						map.put("remark", "[国内应用线上充值]");
						if ("0".equals(userinfo.get("is_recharge").toString())) {
							map.put("is_begin_recharge", 1);
						}
						commissionService.insertSalesStatistics(map);
					} else if (spInfo.getSp_service_type().toString().equals("2")) { // 国际提成
						map.put("sp_manager_id", userinfo.get("app_manager_id"));
						map.put("company_name", userinfo.get("company"));
						map.put("company_account", userinfo.get("account_name"));
						map.put("sp_id", spInfo.getSpid());
						map.put("sp_name", spInfo.getSp_name());
						map.put("sp_recharge_money", chargeInfo.get("amount"));
						map.put("sp_recharge_unitprice", 0);
						map.put("commission_money", 0);
						map.put("sp_recharge_num", 0);
						map.put("remark", "[国际应用线上充值]");
						if ("0".equals(userinfo.get("is_recharge").toString())) {
							map.put("is_begin_recharge", 1);
						}
						commissionService.insertSalesStatistics(map);
					}
					String content = "应用充值通知  -- 公司名称：" + userinfo.get("company") + ", 公司账户："
							+ userinfo.get("account_name") + ",应用名称：" + spInfo.getSp_name() + ", 充值金额："
							+ Double.parseDouble(chargeInfo.get("amount").toString()) / 100 + "元";
					mail.sendMail(Const.FinanceAddress, "应用充值通知", content);
				}
				return "success";
			}

		} else

		{
			return "fail";
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "alipay/alipay_return_url")
	public ModelAndView alipay_return_url(HttpServletRequest request, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> param = bindParamToMap(request);
		Map<String, String> p = new HashMap<String, String>();
		for (String key : param.keySet()) {
			p.put(key, param.get(key).toString());
		}
		boolean flag = AlipaySubmit.verify(p);
		if (flag) {
			String ordernum = p.get("out_trade_no");
			String commonParam = p.get("extra_common_param");
			if (commonParam.equals(Const.CHARGEACCOUNT)) {
				SmsAccountChargeRecord account = smsAccountChargeRecordService.selectByOrderNum(ordernum);
				Integer leftoverNum = smsAccountChargeRecordService
						.findAccountBalance(Integer.valueOf(account.getChargeUser()));
				if (account != null && Integer.valueOf(account.getChargeStatus()) == 1) {
					mv.addObject("account", account);
					mv.addObject("sHtmlText", "充值成功!");
					mv.addObject("chargeType", 2);
					mv.addObject("leftoverNum", leftoverNum);
					mv.setViewName("spCharge/alipayComplete");
					return mv;
				} else {
					mv.addObject("chargeType", 0);
					mv.addObject("sHtmlText", "正在充值，请稍等。。。。。。");
					mv.setViewName("spCharge/alipayComplete");
					return mv;
				}
			} else {
				List<Map<String, Object>> map = chargeRecordService.selChargeInfoByProId(ordernum);
				SpInfo spInfo = spinfoService.getSpinfoById(Integer.valueOf(map.get(0).get("sp_id").toString()));
				if (map.get(0).get("alipay_type") != null
						&& Integer.valueOf(map.get(0).get("alipay_type").toString()) == 1) {
					mv.addObject("chargeInfo", map.get(0));
					mv.addObject("sHtmlText", "充值成功!");
					mv.addObject("chargeType", 1);
					mv.addObject("spInfo", spInfo);
					mv.setViewName("spCharge/alipayComplete");
					return mv;
				} else {
					mv.addObject("chargeType", 0);
					mv.addObject("sHtmlText", "正在充值，请稍等。。。。。。");
					mv.setViewName("spCharge/alipayComplete");
					return mv;
				}
			}
		} else {
			mv.addObject("chargeType", 0);
			mv.addObject("sHtmlText", "充值失败,验签名失败...");
			mv.setViewName("spCharge/alipayComplete");
		}
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "chargeRecordIndex")
	public ModelAndView chargeRecordIndex(PageInfo page, HttpSession session) throws Exception {
		ModelAndView mod = new ModelAndView();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		List<SpInfo> spList = spinfoService.findSpListNoFrozenByUserId(userid);
		
		//乐元素需求，子账号可以查询
		Map<String, Object> currUser = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		if ((Integer) currUser.get("is_sub_account") == 1) {
			Map<String, Object> paramMap = new HashMap<String,Object>();
			paramMap.put("subAccountAppIds", (String) session.getAttribute(Const.APPIDSSTR));
			paramMap.put("sp_service_type", "");
			paramMap.put("spId", "");
			spList = spinfoService.findSpListByuserIdType(paramMap);
		}
		
		mod.addObject("spInfoList", spList);
		mod.setViewName("chargeRecord/index");
		mod.addObject("queryStartTime", DateHelper.getOtherDateString(0, "yyyy-MM-dd"));
		return mod;
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@RequestMapping(value = "chargeRecordList")
	public @ResponseBody Map<String, Object> chargeRecordList(PageInfo page, HttpSession session) throws Exception {
		FormData formData = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		formData.put("userId", userid);
		if (checkLandType(session)) {
			formData.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		Map<String, Object> currUser = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		if ((Integer) currUser.get("is_sub_account") == 1) { // 是子账号，过滤非管辖的应用
			formData.put("subAccountAppIds", (String) session.getAttribute(Const.APPIDSSTR));
		}
		formData = DateHelper.formDataDateString(formData, "quereyStartTime", "queryEndTime");
		//乐元素，特定子账号查询，将当前子账户身份提高到主账户
		if ((Integer) currUser.get("is_sub_account") == 1 && properties.getSubaccount_extend_finance().indexOf((String)currUser.get("user_name")) != -1) {
			//修改userId 为大账户的去查询
			userid =(Integer)currUser.get("parent_id");
			formData.put("userId", userid);
		}
		
		List spList = spinfoService.getSpIdListByForm(formData);
		
		//乐元素需求，子账号可以查询
		/*if ((Integer) currUser.get("is_sub_account") == 1) { 
			String appIds = (String) session.getAttribute(Const.APPIDSSTR);
			if(appIds != null && appIds.length() > 0){
				appIds = appIds.trim();
				if(appIds.startsWith("(")){
					appIds = appIds.substring(1);
				}
				if(appIds.endsWith(")")){
					appIds = appIds.substring(0,appIds.length() - 1);
				}
				String[] appIdsArray = appIds.split(",");
				spList = new ArrayList();
				for(int k = 0; k < appIdsArray.length; k ++){
					spList.add(new Integer(Integer.parseInt(appIdsArray[k])));
				}
			}		
		}*/
		
		page.setFormData(formData);
		page.setListData(spList);
		List<ChargeRecord> list = new ArrayList<ChargeRecord>();
		if (spList.size() > 0) {
			list = chargeRecordService.ChargeRecordListPage(page);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chargeList", list);
		map.put("page", page);
		map.put("recordsTotal", page.getTotalSize());
		map.put("recordsFiltered", page.getTotalSize());
		return map;
	}
}
