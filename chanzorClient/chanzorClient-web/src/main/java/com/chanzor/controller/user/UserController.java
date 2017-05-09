package com.chanzor.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SysUser;
import com.chanzor.entity.UserSubMenu;
import com.chanzor.entity.UserSubSp;
import com.chanzor.persistence.annotation.ParamValidate;
import com.chanzor.service.AppVatinvoiceCertinfoClientService;
import com.chanzor.service.RedisService;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.UserService;
import com.chanzor.util.Const;
import com.chanzor.util.FileUtil;
import com.chanzor.util.FormData;
import com.chanzor.util.JSONUtil;
import com.chanzor.util.Tools;

/**
 * 用户信息维护
 * 
 * @author Administrator
 *
 */
@Controller
public class UserController extends BaseController {

	@Autowired
	private UserService service;
	@Autowired
	private RedisService redisService;
	@Autowired
	private AppVatinvoiceCertinfoClientService vatinvoiceService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SpinfoService spInfoService;

	@Value("${office_url}")
	private String officeUrl;

	@RequestMapping("baseInfo.html")
	public String toUserBaseInfo(Model model) throws Exception {
		Map<String, Object> map = service.getUserBaseInfo(getFormData());
		if ("00".equals((String) map.get("code"))) {
			model.addAttribute("baseInfo", map.get("baseInfo"));
		} else {
			model.addAttribute("baseInfo", new HashMap<String, Object>());
		}
		model.addAttribute("auth", service.getComAuthInfo(getFormData()));

		model.addAttribute("NGINXPATH", properties.getNginx_url());

		int securityLevel = calculateSecurityLevel(
				Integer.parseInt(((Map<String, Object>) map.get("baseInfo")).get("password_strength_mark").toString()),
				Integer.parseInt(((Map<String, Object>) map.get("baseInfo")).get("security_login_mark").toString()));

		model.addAttribute("securityLevel", securityLevel);
		return "user/baseInfo";
	}

	@RequestMapping("info.html")
	public String toUserInfo(Model model) throws Exception {
		model.addAttribute("auth", service.getComAuthInfo(getFormData()));
		model.addAttribute("data", vatinvoiceService.getAppVatinvoiceCertinfoInfoByUserIdService(getFormData()));
		model.addAttribute("NGINXPATH", properties.getNginx_url());
		return "user/info";
	}

	@RequestMapping("auth.html")
	public String toAuthInfo(Model model) throws Exception {
		Map<String, Object> map = service.getComAuthInfo(getFormData());
		model.addAttribute("auth", map);
		model.addAttribute("NGINXPATH", properties.getNginx_url());
		if (map != null && ((Integer) map.get("status")).intValue() == 2) {
			return "user/authPass";
		}
		return "user/authNoPass";
	}

	@RequestMapping("vatinvoice.html")
	public String toVatInvoice(Model model) throws Exception {

		Map<String, Object> map = vatinvoiceService.getAppVatinvoiceCertinfoInfoByUserIdService(getFormData());
		int vatinvoiceType = 0;
		String certinfoStatus = "3";
		if (map != null) {
			vatinvoiceType = (Integer) map.get("vatinvoice_type");
			certinfoStatus = (String) map.get("certinfo_status");
		}
		model.addAttribute("vatinvoice", map);
		model.addAttribute("NGINXPATH", properties.getNginx_url());
		if (map != null && vatinvoiceType == 1 && (certinfoStatus.equals("0") || certinfoStatus.equals("1"))) { // 增值税专用发票提交申请中或申请通过，不可编辑
			return "user/vatinvoiceOnlyRead";
		}
		return "user/vatinvoice";
	}

	/*
	 * 加载个人信息
	 */
	@RequestMapping("loadinfo")
	@ResponseBody
	public Map<String, Object> info() throws Exception {
		FormData data = getFormData();
		return service.getUserInfo(data);
	}

	/**
	 * 修改个人信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateUserInfo")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"USER_NAME\",\"minLength\":2,\"maxLength\":10,\"errorMsg\":\"姓名\"}" })
	public Map<String, Object> updateInfo() throws Exception {
		return service.updateUserInfo(getFormData(), getRequest().getSession());
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("updatePassword")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"oldPsd\",errorMsg:\"原始密码\"}",
			"{\"name\":\"newPsd\",\"minLength\":6,\"maxLength\":20,\"errorMsg\":\"新密码\"}",
			"{\"name\":\"newPsd1\",\"minLength\":6,\"maxLength\":20,\"errorMsg\":\"重复密码\"}" })
	public Map<String, Object> updatePassword(HttpSession session) throws Exception {
		FormData formData = getFormData();
		Map<String, Object> map = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		formData.put("mobile", map.get("mobile"));
		return service.updatePsd(formData);
	}

	/**
	 * 发送验证码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sendSMSCode")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"mobile\",\"isMobile\":true,\"errorMsg\":\"手机号\"}",
			"{\"name\":\"imageCode\",\"errorMsg\":\"图形验证码\"}" })
	public Map<String, Object> sendSMSCode(HttpServletRequest request) throws Exception {
		FormData data = getFormData();
		return service.senmdSMSCode(data, request.getSession());
	}

	/**
	 * 发送验证码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sendSMSCodeNew")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"mobile\",\"isMobile\":true,\"errorMsg\":\"手机号\"}" })
	public Map<String, Object> sendSMSCodeNew(HttpServletRequest request) throws Exception {
		FormData data = getFormData();
		return service.senmdSMSCodeNew(data, request.getSession());
	}

	/**
	 * 修改手机号码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateMobile")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"mobile\",\"isMobile\":true,\"errorMsg\":\"手机号\"}",
			"{\"name\":\"mobileCode\",\"errorMsg\":\"验证码\"}" })
	public Map<String, Object> updateMobile() throws Exception {
		return service.updateMobile(getFormData(), getRequest().getSession());
	}

	/**
	 * 保存企业认证信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("authSub")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"COMPANY\",\"maxLength\":20,\"errorMsg\":\"公司名称\"}",
			"{\"name\":\"COMPANY_ADDRESS\",\"maxLength\":100,\"errorMsg\":\"公司地址\"}",
			"{\"name\":\"CONTACT\",\"maxLength\":20,\"errorMsg\":\"公司电话\"}",
			"{\"name\":\"LEGAL_REPRESENTATIVE\",\"maxLength\":20,\"errorMsg\":\"公司法人\"}",
			"{\"name\":\"IDCARD_IMAGE_SRC\",\"errorMsg\":\"法人身份证(扫描件)\"}",
			"{\"name\":\"ORGANIZATION_NO\",\"maxLength\":30,\"errorMsg\":\"组织机构代码证\"}",
			"{\"name\":\"ORGANIZATION_IMAGE_SRC\",\"errorMsg\":\"组织机构代码证(扫描件)\"}",
			"{\"name\":\"TAXPAYER_NO\",\"maxLength\":30,\"errorMsg\":\"纳税证\"}",
			"{\"name\":\"TAXPAYER_IMAGE_SRC\",\"errorMsg\":\"纳税证(扫描件)\"}",
			"{\"name\":\"REGISTERED_NO\",\"maxLength\":30,\"errorMsg\":\"注册证\"}",
			"{\"name\":\"REGISTERED_IMAGE_SRC\",\"errorMsg\":\"注册证(扫描件)\"}" })
	public Map<String, Object> authSub(HttpServletRequest request) throws Exception {
		FormData data = new FormData(request);
		return service.updateComAuthInfo(data, getRequest().getSession());
	}

	/**
	 * 保存企业认证信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("authSubNew")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"company\",\"maxLength\":50,\"errorMsg\":\"公司名称\"}",
			"{\"name\":\"company_address\",\"maxLength\":100,\"errorMsg\":\"公司地址\"}",
			"{\"name\":\"contact\",\"maxLength\":20,\"errorMsg\":\"公司电话\"}",
			"{\"name\":\"legal_representative\",\"maxLength\":20,\"errorMsg\":\"公司法人\"}",
			"{\"name\":\"identity_front_img_SRC\",\"errorMsg\":\"法人身份证正面(扫描件)\"}",
			"{\"name\":\"identity_back_img_SRC\",\"errorMsg\":\"法人身份证反面(扫描件)\"}",
			"{\"name\":\"registered_image_SRC\",\"errorMsg\":\"营业执照(扫描件)\"}", })
	public Map<String, Object> authSubNew(HttpServletRequest request) throws Exception {
		FormData data = new FormData(request);
		return service.updateComAuthInfo(data, getRequest().getSession());
	}

	@RequestMapping("checkCompany")
	@ResponseBody
	public Map<String, Object> checkCompany(HttpServletRequest request) throws Exception {
		FormData data = new FormData(request);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> companyInfo = service.checkCompany(data.getString("company"));
		if (companyInfo != null && companyInfo.get("id") != null) {
			Integer userId = Integer.valueOf(companyInfo.get("id").toString());
			Map<String, Object> userInfo = service.findUserInfoById(userId);
			if (StringUtils.isNotBlank(userInfo.get("app_manager_id").toString())) {
				Integer userManagerId = Integer.valueOf(userInfo.get("app_manager_id").toString());
				String supportUrl = properties.getOffice_url() + "/crm/client/findUserInfoById?appUserId="
						+ userManagerId;
				ResponseEntity<Map> resultEntitySupport = restTemplate.exchange(supportUrl, HttpMethod.GET, null,
						Map.class);
				Map<String, Object> result = resultEntitySupport.getBody();
				returnMap.put("name", result.get("name"));
				returnMap.put("phone", result.get("mobile") == null || result.get("mobile") == "" ? "400 793 0000"
						: result.get("mobile"));
				returnMap.put("status", "201");
				return returnMap;
			} else {
				returnMap.put("name", "畅小卓");
				returnMap.put("phone", "400 793 0000");
				returnMap.put("status", "202");
				return returnMap;
			}
		} else {
			returnMap.put("status", "200");
		}
		return returnMap;
	}

	/**
	 * 用户提交企业审核信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("authentication")
	@ResponseBody
	public Map<String, Object> authentication() throws Exception {
		return service.authentication(getFormData());
	}

	/**
	 * 上传图片
	 * 
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateImage")
	@ResponseBody
	public String updateImage(MultipartFile file, HttpServletRequest request) throws Exception {
		if (file == null || file.getSize() / 1024 / 1024 > 10) {
			return "";
		}
		FormData data = new FormData(request);
		String serviceNginxUrl = properties.getService_nginx_url();
		String src = FileUtil.updaloadPic(file,
				serviceNginxUrl + "authimg/" + data.get("userId") + Tools.getRandomNum(), data.getString("filename"));
		if (!"".equals(src)) {
			src = src.substring(src.indexOf("authimg"));
		}
		return src;
	}

	@RequestMapping("getPrice")
	@ResponseBody
	@ParamValidate(validateParam = "spId")
	public Map<String, Object> getPringBySpId() throws Exception {
		return service.getPirce(getFormData());
	}

	@RequestMapping("charge")
	public String charge() throws Exception {
		FormData data = getFormData();
		System.out.println(data);
		return null;
	}

	@RequestMapping("updateEmail")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"email\",\"isEmail\":true,\"maxLength\":50,\"errorMsg\":\"邮箱\"}",
			"{\"name\":\"emailCode\",\"errorMsg\":\"邮箱验证码\"}" })
	public Map<String, Object> updateEmail() throws Exception {
		return service.updateEamil(getFormData(), getRequest().getSession());
	}

	@RequestMapping("sendEmailCode")
	@ResponseBody
	@ParamValidate(validateParam = "{\"isEmail\":true,\"maxLength\":30,\"minLength\":6,\"name\":\"email\",\"errorMsg\":\"邮箱\"}")
	public Map<String, Object> sendEmailCode() throws Exception {
		return service.sendEmailCode(getRequest().getSession(), getFormData());
	}

	public static void main(String[] args) {
		String c = "/usr/local/images/authimg/31/2016-05-12/IDCARD_IMAGE.jpg";
		int b = c.indexOf("authimg");
		System.out.println(b);
		c = c.substring(c.indexOf("authimg"));
		System.out.println(c);

	}

	/**
	 * 修改企业基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateBaseInfoExceptMobile")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"email\",\"isEmail\":true,\"errorMsg\":\"联系邮箱\"}" })
	public Map<String, Object> updateBaseInfoExceptMobile() throws Exception {
		return service.updateBaseInfoExceptMobile(getFormData(), getRequest().getSession());
	}

	/**
	 * 修改企业基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateBaseInfoIncludeMobile")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"email\",\"isEmail\":true,\"errorMsg\":\"联系邮箱\"}",
			"{\"name\":\"mobile\",\"isMobile\":true,\"errorMsg\":\"联系号码\"}",
			"{\"name\":\"mobileCode\",\"minLength\":4,\"errorMsg\":\"手机验证码\"}" })
	public Map<String, Object> updateBaseInfoIncludeMobile() throws Exception {
		return service.updateBaseInfoIncludeMobile(getFormData(), getRequest().getSession());
	}

	/**
	 * 修改头像
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updatePhoto")
	@ResponseBody
	public Map<String, Object> updatePhoto() throws Exception {
		return service.updatePhoto(getFormData(), getRequest().getSession());
	}

	@RequestMapping("saleSupport.html")
	public String saleSupport(Model model) throws Exception {
		Map<String, Object> map = service.getUserBaseInfo(getFormData());
		Map<String, Object> baseInfoMap = (Map<String, Object>) map.get("baseInfo");
		SysUser saleManager = new SysUser();
		saleManager.setDuty("销售经理");
		// SysUser supportManager = new SysUser();
		// supportManager.setDuty("支撑经理");
		boolean isExist = false;

		try {
			if (baseInfoMap != null && baseInfoMap.get("app_manager_id") != null) {
				String saleUrl = officeUrl + "/api/v1/user/" + baseInfoMap.get("app_manager_id").toString();
				ResponseEntity<String> resultEntitySale = restTemplate.exchange(saleUrl, HttpMethod.GET, null,
						String.class);
				saleManager = fillOtherProperty(resultEntitySale.getBody(), saleManager);
				isExist = true;
			}
		} catch (Exception e) {
			logger.error("get appManager error,{}", e);
		}
		// 不考虑支撑经理
		// try{
		// if(baseInfoMap != null && baseInfoMap.get("support_manager_id") !=
		// null){
		// String supportUrl = officeUrl + "/api/v1/user/"+
		// (Integer)baseInfoMap.get("support_manager_id");
		// ResponseEntity<String> resultEntitySupport =
		// restTemplate.exchange(supportUrl, HttpMethod.GET, null,
		// String.class);
		// supportManager =
		// fillOtherProperty(resultEntitySupport.getBody(),supportManager);
		// isExist = true;
		// }
		// }catch(Exception e){
		// logger.error("get supportManager error,{}",e);
		// }
		// List<SysUser> saleSupportUsers = new ArrayList<SysUser>();
		// saleSupportUsers.add(saleManager);
		// saleSupportUsers.add(supportManager);

		// model.addAttribute("saleSupportUsers", saleSupportUsers);

		// TODO 暂时写成false，因目前系统中存的销售经理或支撑经理的数据与office 中的数据不对应
		// model.addAttribute("isExist", isExist);

		model.addAttribute("saleManager", saleManager);
		return "user/saleSupport";
	}

	private SysUser fillOtherProperty(String userJsonStr, SysUser currSysUser) {
		if (userJsonStr == null || "".equals(userJsonStr)) {
			return currSysUser;
		}
		JSONObject jsonObject = JSONUtil.toJSON(userJsonStr);
		currSysUser.setId(jsonObject.getInteger("id"));
		currSysUser.setEmail(jsonObject.getString("email"));
		currSysUser.setPhone(jsonObject.getString("phone"));
		currSysUser.setMobile(jsonObject.getString("mobile"));
		currSysUser.setName(jsonObject.getString("name"));
		currSysUser.setQq(jsonObject.getString("qq"));
		return currSysUser;
	}

	// 小账号

	@RequestMapping("/subAccount.html")
	public ModelAndView list(PageInfo page, HttpSession session, Model model) throws Exception {
		ModelAndView mv = new ModelAndView("user/subAccountList");
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = new FormData();
		formdata.put("userId", userid);
		List<Map<String, Object>> spList = spInfoService.getAuditPassSpinfoByUserID(formdata);
		model.addAttribute("appList", spList);
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("subAccountLoad")
	@ResponseBody
	public Map<String, Object> subAccountLoad(PageInfo page, HttpSession session) throws Exception {
		FormData data = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		data.put("userId", userid);

		Map<String, Object> result = new HashMap<String, Object>();
		page.setFormData(data);
		result.put("page", page);
		List<Map<String, Object>> dataList = service.getSubAccountList(page);

		List<Map<String, Object>> subAccountSpList = service.getSubAccountSpList(userid);

		// 查询大账户下所有上线审核通过的应用
		List<Map<String, Object>> spList = spInfoService.getAuditPassSpinfoByUserID(data);
		Map<String, String> resultMap = new HashMap<String, String>();
		for (Map<String, Object> sp : spList) {
			resultMap.put(sp.get("id").toString(), (String) sp.get("sp_name"));
		}
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> currMap = dataList.get(i);
			String appNames = "";
			for (int k = 0; k < subAccountSpList.size(); k++) {
				Map<String, Object> subSp = subAccountSpList.get(k);
				if (subSp.get("sub_account_id").toString().equals(currMap.get("id").toString())) {
					appNames += resultMap.get(subSp.get("sp_id").toString()) + ",";
				}
			}
			if (appNames.endsWith(",")) {
				appNames = appNames.substring(0, appNames.length() - 1);
			}
			currMap.put("appNames", appNames);
		}

		result.put("data", dataList);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());

		return result;
	}

	@RequestMapping("addSubAccount")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"account_name\",\"isMobile\":true,\"errorMsg\":\"子账号\"}",
			"{\"name\":\"password\",\"minLength\":6,\"errorMsg\":\"密码\"}",
			"{\"name\":\"user_name\",\"minLength\":1,\"errorMsg\":\"联系人\"}",
			"{\"name\":\"subSp\",\"minLength\":1,\"errorMsg\":\"关联应用\"}" })
	public Map<String, Object> addSubAccount(HttpSession session) throws Exception {

		Integer userId = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData data = getFormData();
		String subSp = data.getString("subSp");
		String subAccountPermission = data.getString("subAccountPermission");

		data.put("password", Tools.encodeByMD5(data.getString("password")));
		data.put("createtime", new java.util.Date());
		data.put("parentId", userId);

		// 包含应用
		List<Map<String, Object>> spList = spInfoService.getAuditPassSpinfoByUserID(data);
		Map<String, String> resultMap = new HashMap<String, String>();
		for (Map<String, Object> sp : spList) {
			resultMap.put(sp.get("id").toString(), (String) sp.get("account_name"));
		}

		List<UserSubSp> userSubSpList = new ArrayList<UserSubSp>();

		String[] subSpArray = subSp.split(",");
		for (int i = 0; i < subSpArray.length; i++) {
			UserSubSp userSubSp = new UserSubSp();
			userSubSp.setParentAccountId(userId);
			userSubSp.setSpId(Integer.parseInt(subSpArray[i]));
			userSubSp.setSpAccountName(resultMap.get(subSpArray[i]));
			userSubSpList.add(userSubSp);
		}
		data.put("userSubSpList", userSubSpList);

		List<UserSubMenu> userSubMenuList = new ArrayList<UserSubMenu>();

		String[] subMenuArray = subAccountPermission.split(",");
		for (int i = 0; i < subMenuArray.length; i++) {
			UserSubMenu userSubMenu = new UserSubMenu();
			userSubMenu.setParentAccountId(userId);
			userSubMenu.setMenuCode(subMenuArray[i]);
			userSubMenuList.add(userSubMenu);
		}
		data.put("userSubMenuList", userSubMenuList);
		Map<String, Object> res = service.addSubAccount(data, session);
		try {
			String content = "尊敬的用户，您好！您的畅卓自助平台用户账号为：" + data.getString("mobile") + "，密码请咨询管理员，登录后请您第一时间修改密码。【畅卓科技】";
			this.post(properties.getSp_userid(), properties.getSp_account(), properties.getSp_password(),
					data.getString("mobile"), content, null);
		} catch (Exception e) {
			logger.error("新增子账号，发送短信错误，{}", e);
		}
		return res;
	}

	@RequestMapping("updateSubAccount")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"account_name\",\"isMobile\":true,\"errorMsg\":\"子账号\"}",
			"{\"name\":\"user_name\",\"minLength\":1,\"errorMsg\":\"联系人\"}",
			"{\"name\":\"subSp\",\"minLength\":1,\"errorMsg\":\"关联应用\"}" })
	public Map<String, Object> updateSubAccount(HttpSession session) throws Exception {

		Integer userId = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData data = getFormData();
		String subSp = data.getString("subSp");
		String subAccountPermission = data.getString("subAccountPermission");

		// 包含应用
		List<Map<String, Object>> spList = spInfoService.getAuditPassSpinfoByUserID(data);
		Map<String, String> resultMap = new HashMap<String, String>();
		for (Map<String, Object> sp : spList) {
			resultMap.put(sp.get("id").toString(), (String) sp.get("account_name"));
		}

		List<UserSubSp> userSubSpList = new ArrayList<UserSubSp>();

		String[] subSpArray = subSp.split(",");
		for (int i = 0; i < subSpArray.length; i++) {
			UserSubSp userSubSp = new UserSubSp();
			userSubSp.setParentAccountId(userId);
			userSubSp.setSpId(Integer.parseInt(subSpArray[i]));
			userSubSp.setSpAccountName(resultMap.get(subSpArray[i]));
			userSubSp.setSubAccountId(Integer.parseInt(data.getString("id")));
			userSubSpList.add(userSubSp);
		}
		data.put("userSubSpList", userSubSpList);

		List<UserSubMenu> userSubMenuList = new ArrayList<UserSubMenu>();

		String[] subMenuArray = subAccountPermission.split(",");
		for (int i = 0; i < subMenuArray.length; i++) {
			UserSubMenu userSubMenu = new UserSubMenu();
			userSubMenu.setParentAccountId(userId);
			userSubMenu.setMenuCode(subMenuArray[i]);
			userSubMenu.setSubAccountId(Integer.parseInt(data.getString("id")));
			userSubMenuList.add(userSubMenu);
		}
		data.put("userSubMenuList", userSubMenuList);
		data.put("id", Integer.parseInt(data.getString("id")));

		Map<String, Object> userOldInfo = service.findUserInfoById((Integer) data.get("id"));
		String oldMobile = (String) userOldInfo.get("mobile");
		if (!oldMobile.equals(data.getString("mobile"))) {
			try {
				String content = "尊敬的用户，您好！您的畅卓自助平台用户账号为：" + data.getString("mobile")
						+ "，密码请咨询管理员，登录后请您第一时间修改密码。【畅卓科技】";
				this.post(properties.getSp_userid(), properties.getSp_account(), properties.getSp_password(),
						data.getString("mobile"), content, null);
			} catch (Exception e) {
				logger.error("修改子账号，发送短信错误，{}", e);
			}
		}
		Map<String, Object> res = service.updateSubAccount(data, session);
		return res;
	}

	@RequestMapping(value = "/getSubAccount")
	@ResponseBody
	public Map<String, Object> getSubAccount(@RequestParam Integer id) throws Exception {
		FormData data = getFormData();
		Map<String, Object> dataMap = service.findSubAccountById(id);

		List<Map<String, Object>> sps = service.getSubAccountSps(id);
		List<Map<String, Object>> menus = service.getSubAccountMenus(id);

		String spIds = "";
		for (int i = 0; i < sps.size(); i++) {
			spIds += sps.get(i).get("sp_id").toString() + ",";
		}

		String menuCodes = "";
		for (int i = 0; i < menus.size(); i++) {
			menuCodes += menus.get(i).get("menu_code").toString() + ",";
		}

		dataMap.put("spIds", spIds);
		dataMap.put("menuCodes", menuCodes);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("subAccount", dataMap);
		return resultMap;
	}

	/**
	 * 重设密码
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateSubAccountPassword")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"newPsd\",\"minLength\":6,\"errorMsg\":\"新密码\"}",
			"{\"name\":\"newPsd1\",\"minLength\":6,\"errorMsg\":\"重复密码\"}" })
	public Map<String, Object> updateSubAccountPassword() throws Exception {
		FormData data = getFormData();

		Map<String, Object> res = new HashMap<String, Object>();
		data.put("newPsd", Tools.encodeByMD5(data.getString("newPsd")));
		data.put("newPsd1", Tools.encodeByMD5(data.getString("newPsd1")));

		if (!data.getString("newPsd1").equals(data.getString("newPsd"))) {
			res.put("code", "02");
			res.put("msg", "重复密码与新密码不一致,请重新修改.");
			return res;
		}

		data.put("subAccountId", Integer.parseInt(data.getString("subAccountId")));
		return service.updateSubAccountPwd(data);
	}

	@RequestMapping("deleteSubAccount")
	@ResponseBody
	public Map<String, Object> deleteSubAccount() throws Exception {
		FormData data = getFormData();
		Map<String, Object> res = new HashMap<String, Object>();
		data.put("id", Integer.parseInt(data.getString("id")));
		return service.delSubAccount(data, null);
	}

	@RequestMapping("getMainAccountAuthInfo")
	@ResponseBody
	public Map<String, Object> getMainAccountAuthInfo() throws Exception {
		FormData data = getFormData();
		Map<String, Object> authInfo = service.getComAuthInfo(getFormData());
		if (authInfo != null) {
			return authInfo;
		}

		return new HashMap<String, Object>();
	}

	@RequestMapping("securityLoginOnOff")
	@ResponseBody
	public Map<String, Object> securityLoginOnOff() throws Exception {
		FormData data = getFormData();
		data.put("securityLoginMark", Integer.parseInt(data.getString("securityLoginMark")));
		Map<String, Object> map = service.updateMainAccountSecurityLoginMark(data);
		service.updateSubAccountSecurityLoginMark(data);
		return map;
	}

	// 计算安全等级
	private int calculateSecurityLevel(int passwordStrengthMark, int securityLoginMark) {
		if (passwordStrengthMark == 1) { // 密码弱
			return 1;
		}
		if (passwordStrengthMark == 2 && securityLoginMark == 0) { // 密码中，安全登录关闭
			return 1;
		}
		if (passwordStrengthMark == 3 && securityLoginMark == 0) {// 密码强，安全登录关闭
			return 2;
		}
		if (passwordStrengthMark == 2 && securityLoginMark == 1) {// 密码中，安全登录开启
			return 2;
		}
		if (passwordStrengthMark == 3 && securityLoginMark == 1) {// 密码强，安全登录开启
			return 3;
		}
		return 1;
	}

	/**
	 * 修改账号
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateAccount")
	@ResponseBody
	@ParamValidate(validateParam = { "{\"name\":\"newMobile\",\"isMobile\":true,\"errorMsg\":\"联系号码\"}",
			"{\"name\":\"newAccountName\",\"minLength\":4,\"errorMsg\":\"账号\"}",
			"{\"name\":\"verifyCode\",\"minLength\":4,\"errorMsg\":\"验证码\"}" })
	public Map<String, Object> updateAccount() throws Exception {
		return service.updateAccount(getFormData(), getRequest().getSession());
	}

	/**
	 * 修改账号
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findSysNotice")
	@ResponseBody
	public Map<String, Object> findSysNotice() throws Exception {
		Integer userManagerId = 1;
		String supportUrl = properties.getOffice_url() + "/a/oa/oaNotify/findTopNotify";
		ResponseEntity<Map> resultEntitySupport = restTemplate.exchange(supportUrl, HttpMethod.GET, null, Map.class);
		Map<String, Object> result = resultEntitySupport.getBody();
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("findSysNoticeList")
	@ResponseBody
	public Map<String, Object> findSysNoticeList(String content) throws Exception {
		String supportUrl = properties.getOffice_url() + "/a/oa/oaNotify/findSysNoticeList";
		if (StringUtils.isNotBlank(content)) {
			supportUrl += "?content=" + content;
		}
		ResponseEntity<Map> resultEntitySupport = restTemplate.exchange(supportUrl, HttpMethod.GET, null, Map.class);
		Map<String, Object> result = resultEntitySupport.getBody();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject object = (JSONObject) JSONObject.toJSON(result);
		Map<String, Object> notifyList = object.getJSONObject("notifyList");
		List<Map<String, Object>> data = (List<Map<String, Object>>) notifyList.get("list");
		map.put("data", data);
		map.put("recordsTotal", notifyList.get("pageNo"));
		map.put("recordsFiltered", notifyList.get("pageSize"));
		return map;
	}

	@RequestMapping("noticeList")
	public String noticeList() throws Exception {
		return "notice/index";
	}
}
