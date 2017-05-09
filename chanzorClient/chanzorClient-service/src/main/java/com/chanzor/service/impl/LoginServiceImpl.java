package com.chanzor.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chanzor.entity.SystemParameter;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.*;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;
import com.chanzor.util.Tools;

@Service
@SuppressWarnings("unchecked")
public class LoginServiceImpl implements LoginService {

	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	@Autowired
	private SpinfoService spInfoService;
	@Autowired
	private UserService userService;

	public Map<String, Object> login(FormData data, HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 如果是后台管理员登录不用转换密码直接后台匹配
		if (data.get("type") != null && data.get("type").equals("adminPass")) {
			data.put("password", data.getString("password"));
		} else {
			data.put("initPassword", data.getString("password"));
			data.put("password", Tools.encodeByMD5(data.getString("password")));
		}
		String mdnSection = getmdn();
		String username = data.getString("username");
		Pattern p = Pattern.compile(mdnSection);
		Matcher m = p.matcher(username);
		boolean ismobile = m.matches();
		if (ismobile) {
			data.put("ismobile", 1);
		} else {
			data.put("ismobile", 0);
		}
		List<Map<String, Object>> user = (List<Map<String, Object>>) daoSupport.findForList("LoginMapper.login", data);
		if (user == null || user.size() != 1) {
			result.put("code", "03");
			result.put("msg", "用户名或密码错误.");
			return result;
			// SpInfo spInfo = spInfoService.selectSpInfoByAccount(data);
			// if (spInfo != null) {
			// if (spInfo.getStatus() != null &&
			// Integer.valueOf(spInfo.getStatus()) != 1) {
			// result.put("code", "02");
			// result.put("msg", "应用已删除，请联系管理员.");
			// return result;
			// } else {
			// Map<String, Object> userInfo =
			// userService.findUserInfoById(spInfo.getUserId());
			// session.setAttribute(Const.SESSION_USER, userInfo);
			// session.setAttribute(Const.SESSIONSPINFO, spInfo);
			// session.setAttribute("LandingType", Const.SPINFO);
			// List<SystemParameter> smsSystemParam = (List<SystemParameter>)
			// daoSupport
			// .findForList("SystemParameterMapper.findSystemParameterList",
			// null);
			// for (SystemParameter s : smsSystemParam) {
			// if (Const.MAXCONTENTLENGTH.equals(s.getPrm_name())) {
			// session.setAttribute(Const.MAXCONTENTLENGTH,
			// Integer.parseInt(s.getPrm_value()));
			// }
			// }
			// session.setMaxInactiveInterval(0);
			// result.put("code", "00");
			// result.put("msg", "登陆成功,跳转中 ...");
			// return result;
			// }
			// } else {
			// result.put("code", "01");
			// result.put("msg", "用户名或密码错误.");
			// return result;
			// }
		} else {
			Map<String, Object> u = user.get(0);
			if (u.get("status") == null || !u.get("status").toString().equals("1")) {
				result.put("code", "02");
				result.put("msg", "该用户已冻结,请联系管理员.");
				return result;
			}
		}
		Map<String, Object> u = user.get(0);
		boolean ipeq = true;
		if (data.get("type")==null||!data.get("type").equals("adminPass")){
			if (data.getString("login-smsCode") != null) {
				if (data.getString("login-smsCode").length() > 0 && !"".equals(data.getString("login-smsCode"))) {
					String mobileCodeSession = (String) session.getAttribute(Const.MOBILE_CODE_SESSION);
					String mobileCode = data.getString("login-smsCode") + "-" + u.get("mobile");
					if (mobileCodeSession != null) {
						if (!mobileCodeSession.equals(mobileCode.trim())) {
							result.put("code", "05");
							result.put("msg", "验证码输入错误");
							return result;
						} else {
							data.put("ID", u.get("id"));
							daoSupport.update("LoginMapper.updateLoginIP", data); // 修改登陆Ip
							ipeq = false;
						}
					}
				}
			}
			if (!ismobile && u.get("mobile") == null) {
				System.out.println("账户登陆，并且没有绑定手机号，先不校验登陆ip");
			} else if (validateIp(data.get("ip").toString()) || "0".equals(u.get("security_login_mark").toString())) {
				System.out.println("内部IP登陆" + u.get("security_login_mark").toString());
			} else if (u.get("last_login_ip") == null || validateIp(u.get("last_login_ip").toString())){
				System.out.println("历史IP登陆为内部登陆，不做IP限制，当前登陆IP：" + data.get("ip").toString());
				data.put("ID", u.get("id"));
				daoSupport.update("LoginMapper.updateLoginIP", data);
			} else if (!data.get("ip").toString().equals(u.get("last_login_ip")) && ipeq) {
				result.put("code", "04");
				result.put("msg", "登陆IP与上次登陆IP不同");
				return result;
			}
		}
		List<Map<String, Object>> apps = null;
		List<String> appIds = null;
		String permissions = "";
		String appIdsStr = "";
		String subAccountLogin = "";
		Map<String, String> appIdsMap = new HashMap<String, String>();
		if ((Integer) u.get("is_sub_account") == 0) { // 大账号
			apps = (List<Map<String, Object>>) daoSupport.findForList("LoginMapper.getAppsByUserId", u.get("id"));
			appIds = (List<String>) daoSupport.findForList("LoginMapper.getAppsIdByUserId", u.get("id"));
		} else { // 小账号
			apps = (List<Map<String, Object>>) daoSupport.findForList("LoginMapper.getAppsBySubAccountId", u.get("id"));
			appIds = (List<String>) daoSupport.findForList("LoginMapper.getAppsIdBySubAccountId", u.get("id"));
			if (appIds != null && appIds.size() > 0) {
				appIdsStr += "(";
				for (int i = 0; i < appIds.size(); i++) {
					if (i != appIds.size() - 1) {
						appIdsStr += appIds.get(i) + ",";
					} else {
						appIdsStr += appIds.get(i);
					}
					appIdsMap.put(appIds.get(i), appIds.get(i));
				}
				appIdsStr += ")";
			}

			List<String> permissionList = (List<String>) daoSupport
					.findForList("LoginMapper.getPermissionsBySubAccountId", u.get("id"));

			if (permissionList != null && permissionList.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < permissionList.size(); i++) {
					sb.append(permissionList.get(i) + ",");
				}
				permissions = sb.toString();
			}

			subAccountLogin = "subAccountLogin";
			session.setAttribute("subAccountLogin", subAccountLogin);
		}

		data.put("ID", u.get("id"));
		// daoSupport.update("LoginMapper.updateLoginIP", data); //修改登陆Ip
		session.setAttribute(Const.APPS, apps);
		session.setAttribute(Const.APPIDS, appIds);
		session.setAttribute(Const.PERMISSIONS, permissions);
		session.setAttribute(Const.APPIDSSTR, appIdsStr);
		session.setAttribute(Const.APPIDSMAP, appIdsMap);
		session.setAttribute(Const.MDNSECTION, mdnSection);

		if (apps != null && apps.size() > 0)
			session.setAttribute(Const.CURRENT_APP, apps.get(0));
		u.put("LAST_LOGIN_IP", data.get("ip"));
		session.setAttribute(Const.SESSION_USER, u);
		session.setMaxInactiveInterval(0);
		session.setAttribute("LandingType", Const.SESSION_USER);
		List<SystemParameter> smsSystemParam = (List<SystemParameter>) daoSupport
				.findForList("SystemParameterMapper.findSystemParameterList", null);
		for (SystemParameter s : smsSystemParam) {
			if (Const.MAXCONTENTLENGTH.equals(s.getPrm_name())) {
				session.setAttribute(Const.MAXCONTENTLENGTH, Integer.parseInt(s.getPrm_value()));
			}
		}
		// 下面这个 session 留下备用，这是全部系统参数，留待以后系统参数使用较多时打开
		// session.setAttribute(Const.SMS_SYSTEM_PARAM, smsSystemParam);
		if (!ismobile && u.get("mobile") == null) {
			result.put("code", "01");
			result.put("uid", u.get("id"));
			result.put("msg", "账户存在风险，请绑定手机号");
			return result;
		} else {
			result.put("code", "00");
		}
		result.put("msg", "登陆成功,跳转中 ...");
		return result;
	}

	public Map<String, Object> isMobileExits(String mobile) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> isHasMobiles = (List<Map<String, Object>>) daoSupport.findForList("LoginMapper.getnumByMobile",
				mobile);
		if (isHasMobiles == null || isHasMobiles.size() == 0) {
			result.put("statusCode", 202);
			result.put("msg", "此号码尚未绑定账号");
		}else if(isHasMobiles.size() > 1){
			result.put("statusCode", 203);
			result.put("msg", "此号码绑定多个账号，不能修改忘记密码，请联系管理员");
		}else{
			result.put("statusCode", 200);
			result.put("msg", "此号码绑定一个账号，可以修改密码");
		}
		return result;
	}

	public Map<String, Object> reset(FormData data) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		data.put("password", Tools.encodeByMD5(data.getString("password")));

		int res = (Integer) daoSupport.update("LoginMapper.reset", data);

		result.put("statusCode", (res >= 1 ? 200 : 201));
		result.put("msg", (res >= 1 ? "密码重置成功，请重新登录" : "当前网络不佳，请稍后重试"));
		return result;
	}

	public Map<String, Object> getNum(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("LoginMapper.getnumByMobile", data);
	}

	public int register(FormData data) throws Exception {
		data.put("PASSWORD", Tools.encodeByMD5(data.getString("password")));
		data.put("CREATETIME", new Date());
		data.put("STATUS", 1);
		return (Integer) daoSupport.save("LoginMapper.saveappBusinessManager", data);
	}

	@Override
	public Map<String, Object> numSmsUserInfo(FormData data) throws Exception {
		// return (Map<String, Object>)
		// daoSupport.findForObject("LoginMapper.numSmsUserinfoMapper", data);
		return null;
	}

	@Override
	public int saveBusinessMessage(FormData data) throws Exception {
		return (Integer) daoSupport.update("LoginMapper.saveBusinessMessageMapper", data);
	}

	@Override
	public Map<String, Object> gerLoginMobile(FormData data, HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 如果是后台管理员登录不用转换密码直接后台匹配
		data.put("password", Tools.encodeByMD5(data.getString("password")));
		String mdnSection = getmdn();
		String username = data.getString("username");
		Pattern p = Pattern.compile(mdnSection);
		Matcher m = p.matcher(username);
		boolean ismobile = m.matches();
		if (ismobile) {
			data.put("ismobile", 1);
		} else {
			data.put("ismobile", 0);
		}
		List<Map<String, Object>> user = (List<Map<String, Object>>) daoSupport.findForList("LoginMapper.login", data);
		if (user == null || user.size() != 1) {
			result.put("code", "03");
			result.put("msg", "用户名或密码错误.");
			return result;
		} else {
			Map<String, Object> u = user.get(0);
			if (u.get("status") == null || !u.get("status").toString().equals("1")) {
				result.put("code", "02");
				result.put("msg", "该用户已冻结,请联系管理员.");
				return result;
			}
		}
		Map<String, Object> u = user.get(0);
		result.put("code", "00");
		result.put("msg", u.get("mobile"));
		return result;
	}

	public boolean validateIp(String ip) throws Exception {
		List<Map<String, Object>> ipList = (List<Map<String, Object>>) daoSupport.findForList("LoginMapper.getipList",
				null);
		for (Map<String, Object> map : ipList) {
			if (map.get("ip").toString().equals(ip)) {
				return true;
			}
		}
		return false;
	}

	public String getmdn() throws Exception {
		List<Map<String, Object>> mdnList = userService.getMdnSection();
		StringBuffer resultlist = new StringBuffer();
		for (Map<String, Object> map : mdnList) {
			resultlist.append(map.get("num").toString() + "、");
		}
		String[] resultall = (resultlist.substring(0, resultlist.length() - 1)).split("、");
		List<String> list = Arrays.asList(resultall);
		StringBuffer threemdn = new StringBuffer();
		StringBuffer fourmdn = new StringBuffer();
		for (String s : list) {
			if (s.length() == 4) {
				fourmdn.append(s + "|");
			} else {
				threemdn.append(s + "|");
			}
		}
		String result = "";
		if (fourmdn.length() > 0) {
			result = "^((" + threemdn.toString().substring(0, threemdn.length() - 1) + ")[0-9]{8})$|^(("
					+ fourmdn.toString().substring(0, fourmdn.length() - 1) + ")[0-9]{7})$";
		} else {
			result = "^((" + threemdn.toString().substring(0, threemdn.length() - 1) + ")[0-9]{8})$";
		}
		return result;
	}

}
