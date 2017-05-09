package com.chanzor.controller.blacklist;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.BlacklistService;
import com.chanzor.service.RedisService;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.WhitelistService;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;
import com.chanzor.util.MobileUtil;
import com.chanzor.util.Tools;

/**
 * 白名单信息维护
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("whitelist")
public class WhitelistController extends BaseController {
	private static final int WHITE_COUNT = 6;
	@Autowired
	private WhitelistService service;
	@Autowired
	private BlacklistService blacklistService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private SpinfoService spInfoService;

	@RequestMapping("")
	public ModelAndView list(PageInfo page, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("blacklist/whitelist");

		FormData appForm = new FormData();

		appForm.put("userId", getFormData().get("userId"));
		if (checkLandType(session)) {
			appForm.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			appForm.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		
		List<Map<String, Object>> appList = blacklistService.getAppList(appForm);
		mv.addObject("appList", appList);
		return mv;
	}

	/**
	 * 初始化白名单列表数据
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page, HttpSession session) throws Exception {
		FormData formData = getFormData();
		formData.put("userid", formData.get("userId"));
		if (checkLandType(session)) {
			formData.put("spid", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			formData.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		page.setFormData(formData);
		List<Map<String, Object>> data = service.getAllWhitelistPage(page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		result.put("data", data);
		return result;
	}

	/**
	 * 获取白名单列表数据
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAll")
	@ResponseBody
	public Map<String, Object> getAll() throws Exception {
		FormData formData = getFormData();
		formData.put("userid", formData.get("userId"));
		List<Map<String, Object>> data = service.getAllWhitelist(formData);

		// for (Map<String, Object> map : data) {
		// map.put("createtime", Tools.date2Str((Date)map.get("createtime")));
		// }

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", data);
		return result;
	}

	/**
	 * 跳转到编辑白名单页面
	 * 
	 * @param ID
	 * @param operate
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showInfo")
	@ResponseBody
	public Map<String, Object> showInfo(@RequestParam(required = false) String id, HttpSession session)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (id != null && !"".equals(id)) {
			FormData data = new FormData();
			data.put("id", id);
			Map<String, Object> whitelistById = service.getWhitelistById(data);
			result.put("data", whitelistById);
		}
		return result;
	}

	/**
	 * 修改白名单信息
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> save(HttpSession session) throws Exception {
		FormData formData = getFormData();
		Map<String, Object> result = new HashMap<String, Object>();
		// 手机号验证
		if (!spInfoService.isMobile(formData.getString("mdn"))) {
			result.put("statusCode", 251);
			return result;
		}
		// 验证码验证
		String whitelist_code = (String) session.getAttribute("whitelist_code");
		if (whitelist_code == null || whitelist_code.equals("")) {
			result.put("statusCode", 203);
			return result;
		}
		// 手机号重复验证
		List<Map<String, Object>> whitelistByMdn = service.getWhitelistByMdn(formData);
		if (whitelistByMdn != null && whitelistByMdn.size() != 0) {
			result.put("statusCode", 205);
			return result;
		}
		// 数量验证
		String[] split = whitelist_code.split("-");
		String mobile = split[0];
		String code = split[1];
		if (!code.equals(formData.getString("code")) || !mobile.equals(formData.getString("mdn"))) {
			result.put("statusCode", 204);
			return result;
		}

		if (formData.get("id") == null || "".equals(formData.get("id"))) {
			FormData data = new FormData();
			data.put("spid", formData.get("spid"));
			int whitelistCount = service.getWhitelistCount(data);
			if (whitelistCount >= WHITE_COUNT) {
				result.put("statusCode", 202);
				return result;
			}
		}
		session.removeAttribute("whitelist_code");

		formData.put("userid", formData.get("userId"));
		formData.put("createtime", new Date());
		int res = service.saveWhitelist(formData);
		if (res >= 1) {
			redisService.insSysWhiteList(Integer.valueOf(formData.get("spid").toString()),
					formData.get("mdn").toString());
		}
		result.put("statusCode", (res >= 1 ? 200 : 201));
		return result;
	}

	/**
	 * 删除白名单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	public Map<String, Object> delBlacklist(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		FormData formData = getFormData();
		Map<String, Object> map = service.getWhitelistById(formData);
		map.put("spid", map.get("sp_id"));
		int res = service.deleteWhitelist(formData);
		redisService.delWhiteList(Integer.valueOf(map.get("sp_id").toString()), map.get("mdn").toString());

		result.put("statusCode", (res >= 1 ? 200 : 201));

		return result;
	}

	/**
	 * 发送验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getCode")
	@ResponseBody
	public Map<String, Object> getCode(@RequestParam(required = false) String mobile, HttpSession session)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (!spInfoService.isMobile(mobile)) {
			result.put("statusCode", 201);
		} else {
			String code = sendSMSMobileCode(mobile);
			session.setAttribute("whitelist_code", mobile + "-" + code);
			logger.error("--------------" + code);
			result.put("statusCode", 200);
		}
		return result;
	}
}
