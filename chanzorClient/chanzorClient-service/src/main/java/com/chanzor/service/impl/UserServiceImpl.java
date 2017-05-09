package com.chanzor.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chanzor.entity.PageInfo;
import com.chanzor.entity.UserSubMenu;
import com.chanzor.entity.UserSubSp;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.RedisService;
import com.chanzor.service.UserService;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;
import com.chanzor.util.MobileUtil;
import com.chanzor.util.PostStrUtils;
import com.chanzor.util.PropertiesConfig;
import com.chanzor.util.RegularUtils;
import com.chanzor.util.Tools;
import com.chanzor.util.mail.SimpleMailSender;

@Service("userService")
@SuppressWarnings("unchecked")
public class UserServiceImpl implements UserService {
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;
	@Autowired
	private RedisService redisService;
	@Autowired
	public PropertiesConfig properties;
	@Autowired
	private RestTemplate restTemplate;

	private static Logger log = Logger.getLogger(UserServiceImpl.class);

	/**
	 * 获取客户的基本信息
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getUserBaseInfo(FormData data) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		List<Map<String, String>> infos = (List<Map<String, String>>) daoSupport
				.findForList("UserMapper.findUserBaseInfoByID", data);
		if (infos != null && !infos.isEmpty() && infos.size() == 1) {
			res.put("code", "00");
			res.put("baseInfo", infos.get(0));
			return res;
		} else {
			res.put("code", "01");
			res.put("msg", "个人信息异常,请联系管理员");
			return res;
		}
	}

	/**
	 * 获取个人信息
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getUserInfo(FormData data) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		List<Map<String, String>> infos = (List<Map<String, String>>) daoSupport
				.findForList("UserMapper.findUserInfoByID", data);
		if (infos != null && !infos.isEmpty() && infos.size() == 1) {
			res.put("code", "00");
			res.put("userInfo", infos.get(0));
			return res;
		} else {
			res.put("code", "01");
			res.put("msg", "个人信息异常,请联系管理员");
			return res;
		}
	}

	/**
	 * 修改个人信息
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateUserInfo(FormData data, HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		int i = (Integer) daoSupport.update("UserMapper.updateUserInfoById", data);
		if (i > 0) {
			updateSessionUser(session, data);
			res.put("code", "00");
			res.put("msg", "修改成功");
		} else {
			res.put("code", "01");
			res.put("msg", "修改失败");
		}
		return res;
	}

	/**
	 * 修改密码
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updatePsd(FormData data) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		data.put("password", data.get("newPsd"));
		data.put("oldPsd", Tools.encodeByMD5(data.getString("oldPsd")));
		data.put("newPsd", Tools.encodeByMD5(data.getString("newPsd")));
		data.put("newPsd1", Tools.encodeByMD5(data.getString("newPsd1")));
		if (data.getString("oldPsd").equals(data.getString("newPsd"))) {
			res.put("code", "02");
			res.put("msg", "新密码与旧密码一致,请重新修改.");
			return res;
		}
		if (!data.getString("newPsd1").equals(data.getString("newPsd"))) {
			res.put("code", "02");
			res.put("msg", "重复密码与新密码不一致,请重新修改.");
			return res;
		}

		data.put("passwordStrengthMark", Integer.parseInt(data.get("passwordStrengthMark").toString()));
		Map<String, Object> isHas = (Map<String, Object>) daoSupport.findForObject("UserMapper.getUserByIdAndPsd",
				data);
		if (isHas != null && isHas.get("id") != null) {
			int i = (Integer) daoSupport.update("UserMapper.updatePassword", data);
			if (i > 0) {
				res.put("code", "00");
				res.put("msg", "修改成功");

				Map<String, Object> currUser = (Map<String, Object>) daoSupport
						.findForObject("UserMapper.findUserBaseInfoByID", data);
				int securityLevel = calculateSecurityLevel(
						Integer.parseInt(currUser.get("password_strength_mark").toString()),
						Integer.parseInt(currUser.get("security_login_mark").toString()));
				res.put("securityLevel", securityLevel);

				redisService.insertUserRedis(data, null, "RESET");
				return res;
			} else {
				res.put("code", "02");
				res.put("msg", "修改失败,请重试.");
				return res;
			}
		} else {
			res.put("code", "01");
			res.put("msg", "原始密码错误");
			return res;
		}
	}

	/**
	 * 修改手机号码
	 * 
	 * @param data
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateMobile(FormData data, HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		String mobile = data.getString("mobile");
		String mobileCode = data.getString("mobileCode");
		String mobileCodeSession = (String) session.getAttribute(Const.MOBILE_CODE_SESSION);
		if (mobileCodeSession == null || mobileCodeSession.trim().equals("")
				|| mobileCodeSession.trim().split("-").length != 2) {
			res.put("code", "01");
			res.put("msg", "请先发送验证码");
			return res;
		}
		String[] mss = mobileCodeSession.split("-");
		String scode = mss[0];
		String smobile = mss[1];
		if (scode.equals(mobileCode) && smobile.equals(mobile)) {
			Map<String, Object> map = (Map<String, Object>) daoSupport.findForObject("UserMapper.findUserInfoByID",
					data);
			int cout = (Integer) daoSupport.update("UserMapper.updateMobile", data);
			if (cout > 0) {
				updateSessionUser(session, data);
				res.put("code", "00");
				res.put("msg", "修改成功");
				//修改手机号不需要更新redis modify by zyq at 20161011
				//redisService.updBigName(map.get("mobile").toString(), mobile);
				return res;
			} else {
				res.put("code", "02");
				res.put("msg", "修改失败,请稍后再试 ..");
				return res;
			}
		}
		res.put("code", "03");
		res.put("msg", "验证码错误..");
		return res;
	}

	public Map<String, Object> getComAuthInfo(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("UserMapper.selectComAuthByUid", data);
	}

	/**
	 * 更新公司认证信息
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateComAuthInfo(FormData data, HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> auth = (Map<String, Object>) daoSupport.findForObject("UserMapper.selectComAuthByUid",
				data);
		int i = 0;
		if (auth == null || auth.toString().equals("")) {
			i = (Integer) daoSupport.save("UserMapper.insertComAuth", data);
		} else {
			int status = (Integer) auth.get("status");
			if (status == 0 || status == 3) {
				i = (Integer) daoSupport.update("UserMapper.updateComAuthNew", data);
			} else {
				res.put("code", "01");
				res.put("msg", "您已提交认证,不可修改..");
				return res;
			}
		}
		if (i > 0) {
			res.put("code", "00");
			res.put("msg", "保存成功");
			session.setAttribute("auth", getComAuthInfo(data));
		}
		return res;
	}

	/**
	 * 用户提交信息审核
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> authentication(FormData data) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> auth = (Map<String, Object>) daoSupport.findForObject("UserMapper.selectComAuthByUid",
				data);
		if (auth == null) {
			res.put("code", "01");
			res.put("msg", "请先保存资料");
			return res;
		}
		int status = (Integer) auth.get("status");
		if (status == 1 || status == 2) {
			res.put("code", "02");
			res.put("msg", "您已提交审核,请耐心等待...");
			return res;
		}
		int i = (Integer) daoSupport.update("UserMapper.updateAuthStatus", data);
		if (i > 0) {
			res.put("code", "00");
			res.put("msg", "提交成功..");
			return res;
		} else {
			res.put("code", "02");
			res.put("msg", "提交失败,请稍后重试");
		}
		return res;
	}

	/**
	 * 发送验证码
	 * 
	 * @param data
	 * @param session
	 * @param isValidateMobile
	 *            是否需验证此号码在用户表中已存在
	 * @return
	 */
	public Map<String, Object> senmdSMSCode(FormData data, HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		String mobile = data.getString("mobile");
		// 此处判断发短信之前是否需验证改号码是否存在 isValidateMobile if true 判断 else 不判断
		boolean isValidateMobile = true;
		String isValidateMobileStr = data.getString("validate");
		if (isValidateMobileStr != null && isValidateMobileStr.equals("false")) {
			isValidateMobile = false;
		}
		if (isValidateMobile) {
			Map<String, Object> isHasMobiles = (Map<String, Object>) daoSupport.findForObject("UserMapper.isHasMobile",
					data.get("mobile"));
			if (isHasMobiles != null && isHasMobiles.get("id") != null) {
				res.put("code", "01");
				res.put("msg", "该手机号已经注册，不能重复使用!");
				return res;
			}
		}
		String imageCode = data.getString("imageCode");
		String sessionImageCode = (String) session.getAttribute(Const.IMAGE_CODE_SESSION);
		if (sessionImageCode == null || sessionImageCode.trim().equals("")) {
			res.put("code", "02");
			res.put("msg", "图形验证码错误....");
			return res;
		}
		if (!imageCode.trim().toLowerCase().equals(sessionImageCode.trim().toLowerCase())) {
			res.put("code", "03");
			res.put("msg", "图形验证码错误...");
			return res;
		}
		String code = servicesendSMSMobileCode(mobile);
		// String code = "0000";
		if (code == null) {
			res.put("code", "05");
			res.put("msg", "发送失败,请稍后再试..");
			return res;
		}
		log.info("mobile : " + mobile + " / code:" + code);
		res.put("code", "00");
		res.put("msg", "发送成功..");
		session.setAttribute(Const.MOBILE_CODE_SESSION, code + "-" + mobile);
		return res;
	}

	/**
	 * 仅仅发送验证码
	 * 
	 * @param data
	 * @param session
	 * @param isValidateMobile
	 *            是否需验证此号码在用户表中已存在
	 * @return
	 */
	public Map<String, Object> senmdSMSCodeNew(FormData data, HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		String mobile = data.getString("mobile");
		// 此处判断发短信之前是否需验证改号码是否存在 isValidateMobile if true 判断 else 不判断
		boolean isValidateMobile = true;
		String isValidateMobileStr = data.getString("validate");
		if (isValidateMobileStr != null && isValidateMobileStr.equals("false")) {
			isValidateMobile = false;
		}
		if (isValidateMobile) {
			Map<String, Object> isHasMobiles = (Map<String, Object>) daoSupport.findForObject("UserMapper.isHasMobile",
					data.get("mobile"));
			if (isHasMobiles != null && isHasMobiles.get("ID") != null) {
				res.put("code", "01");
				res.put("msg", "此号码已经存在");
				return res;
			}
		}
		// String imageCode = data.getString("imageCode");
		// String sessionImageCode = (String)
		// session.getAttribute(Const.IMAGE_CODE_SESSION);
		// if (sessionImageCode == null || sessionImageCode.trim().equals("")) {
		// res.put("code", "02");
		// res.put("msg", "图形验证码错误....");
		// return res;
		// }
		// if
		// (!imageCode.trim().toLowerCase().equals(sessionImageCode.trim().toLowerCase()))
		// {
		// res.put("code", "03");
		// res.put("msg", "图形验证码错误...");
		// return res;
		// }
		String code = servicesendSMSMobileCode(mobile);
		//String code = "0000";
		if (code == null) {
			res.put("code", "05");
			res.put("msg", "发送失败,请稍后再试..");
			return res;
		}
		log.info("mobile : " + mobile + " / code:" + code);
		res.put("code", "00");
		res.put("msg", "发送成功..");
		session.setAttribute(Const.MOBILE_CODE_SESSION, code + "-" + mobile);
		return res;
	}

	/**
	 * 获取应用单价
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPirce(FormData data) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> price = (Map<String, Object>) daoSupport.findForObject("UserMapper.getPriceBySpId", data);
		if (price != null && price.get("price") != null) {
			res.put("code", "00");
			res.put("price", price.get("price"));
			return res;
		}
		res.put("code", "01");
		res.put("msg", "获取异常请稍后在试 ..");
		return res;
	}

	public Map<String, Object> updateEamil(FormData data, HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		String email = data.getString("email");
		if (!MobileUtil.validateEmail(email)) {
			res.put("code", "04");
			res.put("msg", "邮箱格式错误");
			return res;
		}
		String emailCode = data.getString("emailCode");
		String emailCodeSession = (String) session.getAttribute(Const.EMAIL_CODE_SESSION);
		if (emailCodeSession == null || emailCodeSession.trim().equals("")
				|| emailCodeSession.trim().split("-").length != 2) {
			res.put("code", "01");
			res.put("msg", "请先发送验证码");
			return res;
		}
		String[] mss = emailCodeSession.split("-");
		String scode = mss[1];
		String semail = mss[0];
		if (scode.equals(emailCode) && semail.equals(email)) {
			int cout = (Integer) daoSupport.update("UserMapper.updateEmail", data);
			if (cout > 0) {
				res.put("code", "00");
				res.put("msg", "修改成功");
				updateSessionUser(session, data);
				return res;
			} else {
				res.put("code", "02");
				res.put("msg", "修改失败,请稍后再试 ..");
				return res;
			}
		}
		res.put("code", "03");
		res.put("msg", "验证码错误..");
		return res;
	}

	public void updateSessionUser(HttpSession session, FormData data) throws Exception {
		List<Map<String, Object>> user = (List<Map<String, Object>>) daoSupport.findForList("UserMapper.getUserById",
				data);
		if (user != null && user.size() > 0) {
			session.setAttribute(Const.SESSION_USER, user.get(0));
		}
	}

	public Map<String, Object> sendEmailCode(HttpSession session, FormData data) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		String email = data.getString("email");
		if (!MobileUtil.validateEmail(email)) {
			res.put("code", "04");
			res.put("msg", "邮箱格式错误");
			return res;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("address", properties.getMailAddress());
		map.put("password", properties.getMailpassword());
		String code = SimpleMailSender.sendEmailCode(email, map);
		if (code == null) {
			res.put("code", "04");
			res.put("msg", "邮箱格式错误");
			return res;
		} else {
			res.put("code", "00");
			res.put("msg", "发送成功");
			session.setAttribute(Const.EMAIL_CODE_SESSION, email + "-" + code);
			return res;
		}
	}

	public void charge(FormData data) throws Exception {
		String spId = data.getString("spId");
		String count = data.getString("count");

	}

	@Override
	public Map<String, Object> getSaleManager(Map map) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("UserMapper.getSaleManagerMapper", map);
	}

	@Override
	public Map<String, Object> getSupportManager(Map map) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("UserMapper.getSupportManagerMapper", map);
	}

	// 发送验证码
	public String servicesendSMSMobileCode(String mobile) {
		String code = "";
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			code += r.nextInt(9);
		}
		String msg = "您的验证码是:" + code + "【畅卓科技】";
		String flag = servicepost(properties.getSp_userid(), properties.getSp_account(), properties.getSp_password(),
				mobile, msg);
		if (flag != "")
			return code;
		else
			return null;
	}

	public String servicepost(String userId, String userName, String passWord, String mobile, String content) {
		OutputStreamWriter out = null;
		StringBuilder sTotalString = new StringBuilder();
		String result = "";
		try {
			URL urlTemp = new URL(properties.getSend_message_url());
			URLConnection connection = urlTemp.openConnection();
			connection.setDoOutput(true);
			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			StringBuffer sb = new StringBuffer();
			sb.append("&userid=" + userId);
			sb.append("&account=" + userName);
			sb.append("&password=" + passWord);
			sb.append("&mobile=" + mobile);
			sb.append("&content=" + URLEncoder.encode(content, "utf-8"));
			// sb.append("&content=" + content);
			out.write(sb.toString());
			out.flush();
			String sCurrentLine;
			sCurrentLine = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();// 请求
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString.append(sCurrentLine);
			}
			result = new String(sTotalString.toString().getBytes(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 修改基本信息不包括手机号
	 * 
	 * @param data
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateBaseInfoExceptMobile(FormData data, HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		int cout = (Integer) daoSupport.update("UserMapper.updateBaseInfoExceptMobile", data);
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
		headers.setContentType(type);
		String supportUrl = properties.getOffice_url() + "/crm/client/updUserInfo";
		HttpEntity<String> requestEntity = new HttpEntity<String>(new PostStrUtils().getPostStrFromMap(data), headers);
		restTemplate.exchange(supportUrl, HttpMethod.POST, requestEntity, String.class);
		if (cout > 0) {
			res.put("code", "00");
			res.put("msg", "修改成功");
			updateSessionUser(session, data);
			return res;
		} else {
			res.put("code", "02");
			res.put("msg", "修改失败,请稍后再试 ..");
			return res;
		}
	}

	/**
	 * 修改基本信息包括手机号
	 * 
	 * @param data
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateBaseInfoIncludeMobile(FormData data, HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		String mobile = data.getString("mobile");
		String mobileCode = data.getString("mobileCode");
		String mobileCodeSession = (String) session.getAttribute(Const.MOBILE_CODE_SESSION);
		if (mobileCodeSession == null || mobileCodeSession.trim().equals("")
				|| mobileCodeSession.trim().split("-").length != 2) {
			res.put("code", "01");
			res.put("msg", "请先发送验证码");
			return res;
		}
		String[] mss = mobileCodeSession.split("-");
		String scode = mss[0];
		String smobile = mss[1];
		if (scode.equals(mobileCode) && smobile.equals(mobile)) {
			Map<String, Object> map = (Map<String, Object>) daoSupport.findForObject("UserMapper.findUserInfoByID",
					data);
			int cout = (Integer) daoSupport.update("UserMapper.updateBaseInfoIncludeMobile", data);
			if (cout > 0) {
				updateSessionUser(session, data);
				res.put("code", "00");
				res.put("msg", "修改成功");
				//修改手机号不需要更新redis modify by zyq at 20161011
				//redisService.updBigName(map.get("mobile").toString(), mobile);
				return res;
			} else {
				res.put("code", "02");
				res.put("msg", "修改失败,请稍后再试 ..");
				return res;
			}
		}
		res.put("code", "03");
		res.put("msg", "验证码错误..");
		return res;
	}

	/**
	 * 修改照片
	 * 
	 * @param data
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updatePhoto(FormData data, HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		int cout = (Integer) daoSupport.update("UserMapper.updatePhoto", data);
		if (cout > 0) {
			res.put("code", "00");
			res.put("msg", "修改成功");
			Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
			userInfo.put("photo_img", data.get("photo_img_SRC").toString());
			session.setAttribute(Const.SESSION_USER, userInfo);
			return res;
		} else {
			res.put("code", "02");
			res.put("msg", "修改失败,请稍后再试 ..");
			return res;
		}
	}

	public Map<String, Object> findUserInfoById(Integer userId) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("UserMapper.findUserInfoById", userId);

	}

	public Map<String, Object> saveMobile(FormData data) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		int cout = (Integer) daoSupport.update("UserMapper.updateBaseInfoExceptMobile", data);
		if (cout > 0) {
			res.put("code", "00");
			res.put("msg", "修改成功，进入登陆页");
			return res;
		} else {
			res.put("code", "02");
			res.put("msg", "修改失败");
			return res;
		}
	}

	@Override
	public Map<String, Object> addSubAccount(FormData data, HttpSession session) throws Exception {
		// 判断用户名是否重复，创建的用户名 为 手机号
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> map = (Map<String, Object>) daoSupport.findForObject("UserMapper.getAppUserByAccountName",
				data.getString("account_name"));
		if (map != null && map.size() > 0) {
			res.put("code", "01");
			res.put("msg", "用户账号重复，请修改");
			return res;
		}

		int count = (Integer) daoSupport.update("UserMapper.addSubAccount", data);
		if (count == 1) {
			map = (Map<String, Object>) daoSupport.findForObject("UserMapper.getAppUserByAccountName",
					data.getString("account_name"));
			List<UserSubSp> userSubSpList = (List<UserSubSp>) data.get("userSubSpList");
			List<UserSubMenu> userSubMenuList = (List<UserSubMenu>) data.get("userSubMenuList");

			for (UserSubSp userSubSp : userSubSpList) {
				userSubSp.setSubAccountId((Integer) map.get("id"));
			}
			for (UserSubMenu userSubMenu : userSubMenuList) {
				userSubMenu.setSubAccountId((Integer) map.get("id"));
			}

			daoSupport.update("UserMapper.insertUserSubSp", (List) data.get("userSubSpList"));
			daoSupport.update("UserMapper.insertUserSubMenu", (List) data.get("userSubMenuList"));
		}

		res.put("code", "00");
		res.put("msg", "新增子账户成功");
		return res;
	}

	@Override
	public Map<String, Object> updateSubAccount(FormData data, HttpSession session) throws Exception {
		// 判断账户是否重复
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> map = (Map<String, Object>) daoSupport
				.findForObject("UserMapper.getAppUserByAccountNameAndId", data);
		if (map != null && map.size() > 0) {
			res.put("code", "01");
			res.put("msg", "用户账号重复，请修改");
			return res;
		}

		int i = (Integer) daoSupport.update("UserMapper.updateSubAccount", data);
		if (i > 0) {
			// updateSessionUser(session, data);

			daoSupport.update("UserMapper.delUserSubSp", data);
			daoSupport.update("UserMapper.insertUserSubSp", (List<UserSubSp>) data.get("userSubSpList"));
			daoSupport.update("UserMapper.delUserSubMenu", data);
			daoSupport.update("UserMapper.insertUserSubMenu", (List<UserSubMenu>) data.get("userSubMenuList"));

			res.put("code", "00");
			res.put("msg", "修改成功");
		} else {
			res.put("code", "01");
			res.put("msg", "修改失败");
		}
		return res;
	}

	@Override
	public Map<String, Object> delSubAccount(FormData data, HttpSession session) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		int i = (Integer) daoSupport.update("UserMapper.delSubAccount", data);
		if (i > 0) {
			res.put("code", "00");
			res.put("msg", "修改成功");
		} else {
			res.put("code", "01");
			res.put("msg", "修改失败");
		}
		return res;
	}

	@Override
	public List<Map<String, Object>> getSubAccountList(PageInfo page) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("UserMapper.findSubAccountListPage", page);
	}

	@Override
	public List<Map<String, Object>> getSubAccountSpList(Integer parentAccountId) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("UserMapper.findSubAccountSpList", parentAccountId);
	}

	@Override
	public Map<String, Object> findSubAccountById(Integer subAccountId) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("UserMapper.findSubAccountById", subAccountId);
	}

	@Override
	public List<Map<String, Object>> getSubAccountSps(Integer subAccountId) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("UserMapper.findSubAccountSpById", subAccountId);
	}

	@Override
	public List<Map<String, Object>> getSubAccountMenus(Integer subAccountId) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("UserMapper.findSubAccountMenuById", subAccountId);
	}

	@Override
	public Map<String, Object> updateSubAccountPwd(FormData data) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		int i = (Integer) daoSupport.update("UserMapper.updateSubAccountPwd", data);
		if (i > 0) {
			res.put("code", "00");
			res.put("msg", "修改密码成功");
		} else {
			res.put("code", "01");
			res.put("msg", "修改密码失败");
		}
		return res;
	}

	@Override
	public Map<String, Object> updateMainAccountSecurityLoginMark(FormData data) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		int i = (Integer) daoSupport.update("UserMapper.updateMainAccountSecurityLoginMark", data);
		if (i > 0) {
			res.put("code", "00");
			res.put("msg", "设置安全登录状态成功");
			Map<String, Object> currUser = (Map<String, Object>) daoSupport
					.findForObject("UserMapper.findUserBaseInfoByID", data);
			int securityLevel = calculateSecurityLevel(
					Integer.parseInt(currUser.get("password_strength_mark").toString()),
					Integer.parseInt(currUser.get("security_login_mark").toString()));
			res.put("securityLevel", securityLevel);
		} else {
			res.put("code", "01");
			res.put("msg", "设置安全登录状态失败");
		}
		return res;
	}

	@Override
	public Map<String, Object> updateSubAccountSecurityLoginMark(FormData data) throws Exception {
		Map<String, Object> res = new HashMap<String, Object>();
		int i = (Integer) daoSupport.update("UserMapper.updateSubAccountSecurityLoginMark", data);
		if (i > 0) {
			res.put("code", "00");
			res.put("msg", "设置安全登录状态成功");

		} else {
			res.put("code", "01");
			res.put("msg", "设置安全登录状态失败");
		}
		return res;
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

	public List<Map<String, Object>> getMdnSection() throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("UserMapper.findMdnSection", null);
	}

	@Override
	public Map<String, Object> selPassAuthById(Integer userId) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("UserMapper.selPassAtuhById", userId);
	}

	@Override
	public Map<String, Object> updateAccount(FormData data, HttpSession session) throws Exception {
		//数据验证
		//1.账号符合规则，字母数字下划线，不超过20字符（页面验证）, 账号唯一性验证，需要将ID 传入		
		//2.联系号码为手机号码
		//3.验证码与session中比对
		
		Map<String, Object> res = new HashMap<String, Object>();
		String newAccountName = data.getString("newAccountName");
		String newMobile = data.getString("newMobile");
		String verifyCode = data.getString("verifyCode");
				
		if(!RegularUtils.isWord(newAccountName)){
			res.put("code", "01");
			res.put("msg", "账号应为字母数字或下划线或手机号");
			return res;
		}
		Map<String, Object> repeatUser = (Map<String, Object>) daoSupport
				.findForObject("UserMapper.findUserBaseInfoByIDAndAccount", data);
		
		if(repeatUser != null){
			res.put("code", "01");
			res.put("msg", "账号重复");
			return res;
		}
		
		if(!RegularUtils.isPhone(newMobile)){
			res.put("code", "01");
			res.put("msg", "联系号码非手机号");
			return res;
		}
		
		String mobileCodeSession = (String) session.getAttribute(Const.MOBILE_CODE_SESSION);
		if (mobileCodeSession == null || mobileCodeSession.trim().equals("")
				|| mobileCodeSession.trim().split("-").length != 2) {
			res.put("code", "01");
			res.put("msg", "请先发送验证码");
			return res;
		}
		
		String[] mobileCodeArray = mobileCodeSession.trim().split("-");
		if(!verifyCode.equals(mobileCodeArray[0])){
			res.put("code", "01");
			res.put("msg", "验证码不正确，请重新输入");
			return res;
		}
		
		Map<String, Object> oldUser = (Map<String, Object>) daoSupport
				.findForObject("UserMapper.findUserBaseInfoByID", data);
		
		int i = (Integer) daoSupport.update("UserMapper.updateAccountAndMobile", data);
		if (i == 1) {
			updateSessionUser(session,data);
			//更新redis，将原来的账号对应的USER-accountname  改名为新的
			//更新账号下的所有的应用中的 userAccount 修改为新的账号
			if(!newAccountName.equals((String)oldUser.get("account_name"))){
				redisService.updateBigAccount((String)oldUser.get("account_name"), newAccountName);
			}
			res.put("code", "00");
			res.put("msg", "信息修改成功");
		} else {
			res.put("code", "01");
			res.put("msg", "信息修改失败");
		}
		return res;
	}

	@Override
	public Map<String,Object> checkCompany(String company) throws Exception {
		return (Map<String,Object>) daoSupport.findForObject("UserMapper.checkCompany", company);
	}

}
