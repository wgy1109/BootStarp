package com.chanzor.controller.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;
import com.chanzor.util.PropertiesConfig;
import com.chanzor.util.Tools;

@SuppressWarnings("rawtypes")
public class BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;

	@Autowired
	public PropertiesConfig properties;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${voice_send_message_url}")
	private String voiceSendMessageUrl;

	/**
	 * 得到FormData
	 */
	public FormData getFormData() {
		return new FormData(this.getRequest());
	}

	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		return request;
	}

	/**
	 * 得到分页列表的信�?
	 */
	public PageInfo getPage() {

		return new PageInfo();
	}

	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}

	public static void logAfter(Logger logger) {
		logger.info("end");
		logger.info("");
	}

	public String getIpAddr() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取客户端的Ip
	 * 
	 * @param request
	 * @return
	 */
	public String getClientIp(HttpServletRequest request) {
		if (request.getHeader("X-Forwarded-For") == null || "".equals(request.getHeader("X-Forwarded-For"))) {
			return request.getRemoteAddr();
		} else {
			return request.getHeader("X-Forwarded-For");
		}
	}

	/**
	 * 记录系统日志
	 * 
	 * @param map
	 *            FormData map = new FormData(); map.put("sp_id", 0); IP_ID
	 *            map.put("username", userName); 操作用户姓名 map.put("opearte_type",
	 *            Const.USER_LOGIN); 用户操作类型 Const内操作类型 map.put("opearte_result",
	 *            1); 用户操作结果 map.put("opearte_content", "登录成功"); 用户操作
	 *            map.put("createtime", new Date()); 用户操作时间 map.put("ip",
	 *            loginip); 用户IP
	 */
	// public void insertOperalog(FormData map) throws Exception{
	// if (map != null){
	// map.put("sp_id", 0);
	// map.put("username", this.getFormData().getString("userName"));
	// map.put("ip", getClientIp(this.getRequest()));
	// map.put("createtime", new Date());
	// ServiceHelper.getUserService().insertOperalog(map);
	// }
	//
	// }
	//

	// 检索敏感词
	// public String validContent(String MessageContent) throws Exception {
	// StringBuffer buff = new StringBuffer();
	//
	// List<Map<String, Object>> list_word =
	// ServiceHelper.getSenstiveWordService().getAllSenstiveWord();
	// logger.info("=========list_word:" + list_word.size());
	// String sub_pattern = "[^0-9a-zA-Z\u4E00-\u9FA5]*";
	// for (int i = 0; i < list_word.size(); i++) {
	// Map<String, Object> word = list_word.get(i);
	// String content = (String) word.get("content");
	// if (word != null && content.length() > 0) {
	// StringBuffer pattern_buff = new StringBuffer();
	// pattern_buff.append("^.*");
	// for (int j = 0; j < content.length(); j++) {
	// String sub_word = content.substring(j, j + 1);
	// if (sub_word.equals("\\") || sub_word.equals("^")
	// || sub_word.equals("$") || sub_word.equals("*")
	// || sub_word.equals("+") || sub_word.equals("?")
	// || sub_word.equals("{") || sub_word.equals("}")
	// || sub_word.equals("[") || sub_word.equals("]")
	// || sub_word.equals("(") || sub_word.equals(")")
	// || sub_word.equals(".") || sub_word.equals("|")) {
	// sub_word = "\\" + sub_word;
	// }
	// pattern_buff.append(sub_word);
	// if (j != content.length() - 1) {
	// pattern_buff.append(sub_pattern);
	// }
	// }
	// pattern_buff.append(".*$");
	// logger.error(pattern_buff.toString());
	// Pattern pattern = Pattern.compile(pattern_buff.toString());
	// Matcher matcher = pattern.matcher(MessageContent);
	// if (matcher.matches()) {
	// buff.append(",");
	// buff.append(content);
	// }
	// }
	// }
	//
	// return buff.length() == 0 ? "" : buff.substring(1);// .toString();
	// }
	protected Map<String, Object> bindParamToMap(HttpServletRequest request) {
		Enumeration enumer = request.getParameterNames();
		Map<String, Object> map = new HashMap<String, Object>();
		while (enumer.hasMoreElements()) {
			String key = (String) enumer.nextElement();
			map.put(key, request.getParameter(key));
		}
		return map;
	}

	// 辅助 得到 总数和总计费数
	public String Allnum(Map<String, Object> sms) {
		int num = (Integer) sms.get("charge_count") + (Integer) sms.get("error_count");
		String Allnum = sms.get("phone_count").toString() + "/" + num;
		return Allnum;
	}

	// 辅助 得到 成功条数、失败条数、未知条数
	public String yesOrNo(Map<String, Object> sms) {
		String yesOrNo = sms.get("sended_count") + "/" + sms.get("error_count") + "/" + 0;
		return yesOrNo;
	}

	// 发送验证码
	public String sendSMSMobileCode(String mobile) {
		String code = "";
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			code += r.nextInt(9);
		}
		String msg = "您的验证码是:" + code + "【畅卓科技】";
		String flag = post(properties.getSp_userid(), properties.getSp_account(), properties.getSp_password(), mobile,
				msg, null);
		if (flag != "")
			return code;
		else
			return null;
	}

	public String post(String userId, String userName, String passWord, String mobile, String content,
			String timingTime) {
		OutputStreamWriter out = null;
		StringBuilder sTotalString = new StringBuilder();
		String result = "";
		try {
			// logger.info("----------------------------发送url为--------------------"
			// + properties.getSend_message_url());
			URL urlTemp = new URL(properties.getSend_message_url());
			URLConnection connection = urlTemp.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			StringBuffer sb = new StringBuffer();
			sb.append("&userid=" + userId);
			sb.append("&account=" + URLEncoder.encode(userName, "utf-8"));
			sb.append("&password=" + URLEncoder.encode(passWord, "utf-8"));
			sb.append("&mobile=" + mobile);
			if (Tools.notEmpty(timingTime)) {
				sb.append("&sendTime=" + timingTime);
			}
			// URLEncoder.encode(urlParameter,"UTF-8");
			sb.append("&content=" + URLEncoder.encode(content, "utf-8"));
			// sb.append("&content=" + content);
			out.write(sb.toString());
			// logger.info("----------------------------发送url为--------------------"
			// + sb.toString());
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

	public String postCustomizedMessage(String content, String redisKey, String timingTime, SpInfo spInfo) {
		OutputStreamWriter out = null;
		StringBuilder sTotalString = new StringBuilder();
		String result = "";
		try {
			logger.info("----------------------------发送url为--------------------"
					+ properties.getCustomized_send_message_url());
			URL urlTemp = new URL(properties.getCustomized_send_message_url() + "?account=" + spInfo.getUsername()
					+ "&password=" + spInfo.getPassword());
			URLConnection connection = urlTemp.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setReadTimeout(100000);
			connection.setConnectTimeout(100000);

			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			StringBuffer sb = new StringBuffer();
			sb.append("&content=" + URLEncoder.encode(content, "utf-8"));
			sb.append("&redisKey=" + redisKey);
			if (Tools.notEmpty(timingTime)) {
				sb.append("&sendTime=" + timingTime);
			}
			logger.info("----------------------------发送url为--------------------" + sb.toString());
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

	public String postInterSms(String userId, String userName, String passWord, String mobile, String content,
			String timingTime) {
		OutputStreamWriter out = null;
		StringBuilder sTotalString = new StringBuilder();
		String result = "";
		try {
			logger.info(
					"----------------------------发送url为--------------------" + properties.getInter_send_message_url());
			URL urlTemp = new URL(properties.getInter_send_message_url());
			URLConnection connection = urlTemp.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			StringBuffer sb = new StringBuffer();
			sb.append("&userid=" + userId);
			sb.append("&account=" + userName);
			sb.append("&password=" + passWord);
			sb.append("&mobile=" + mobile);
			if (Tools.notEmpty(timingTime)) {
				sb.append("&sendTime=" + timingTime);
			}
			sb.append("&content=" + URLEncoder.encode(content, "utf-8"));
			// sb.append("&content=" + content);
			out.write(sb.toString());
			logger.info("----------------------------发送url为--------------------" + sb.toString());
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

	public String postVoiceSms(String userId, String userName, String passWord, String mobile, String content,
			String timingTime) {
		OutputStreamWriter out = null;
		StringBuilder sTotalString = new StringBuilder();
		String result = "";
		try {
			logger.info(
					"----------------------------发送url为--------------------" + properties.getVoice_send_message_url());
			URL urlTemp = new URL(properties.getVoice_send_message_url());
			URLConnection connection = urlTemp.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			StringBuffer sb = new StringBuffer();
			sb.append("&userid=" + userId);
			sb.append("&account=" + userName);
			sb.append("&password=" + passWord);
			sb.append("&mobile=" + mobile);
			if (Tools.notEmpty(timingTime)) {
				sb.append("&sendTime=" + timingTime);
			}
			sb.append("&content=" + URLEncoder.encode(content, "utf-8"));
			// sb.append("&content=" + content);
			out.write(sb.toString());
			logger.info("----------------------------发送url为--------------------" + sb.toString());
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

	// 辅助得到短信回复条数和记位数
	public String pk_size_number(Map<String, Object> d) {
		return "(" + d.get("pk_size") + "/" + d.get("pk_number") + ")";
	}

	public boolean checkLandType(HttpSession session) {
		String LandType = (String) session.getAttribute("LandingType");
		if (LandType != null && LandType.equals(Const.SPINFO)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param userId
	 *            客户ID
	 * @param account
	 *            应用账户
	 * @param passWord
	 *            应用密码
	 * @param mobile
	 *            手机
	 * @param file
	 *            语音文件ID(voice_user_file表)
	 * @param timingTime
	 *            设定的定时时间
	 * @return
	 */
	public String postVoiceFile(String userId, String account, String passWord, String mobile, String file,
			String timingTime) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
		headers.setContentType(type);
		StringBuffer sb = new StringBuffer();
		sb.append("userid=" + userId);
		sb.append("&account=" + account);
		sb.append("&password=" + passWord);
		sb.append("&mobile=" + mobile);
		sb.append("&uploaded=0");
		sb.append("&file=" + file);
		if (Tools.notEmpty(timingTime)) {
			sb.append("&sendTime=" + timingTime);
		}

		ResponseEntity<String> result = null;
		try {
			// HttpHeaders headers = new HttpHeaders();
			// headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			String body = sb.toString();
			logger.info("voiceSendMessageUrl ====" + voiceSendMessageUrl + ",param=====" + sb.toString());
			result = restTemplate.exchange(voiceSendMessageUrl, HttpMethod.POST, new HttpEntity<>(body, headers),
					String.class);
			logger.info("result=" + result.getBody());
		} catch (Exception e) {
			logger.error("error occured at [postVoiceFile],{}", e);
			return "{status=-1}";
		}
		return result.getBody();
	}

	/**
	 * 
	 * @param userId
	 * @param account
	 * @param passWord
	 * @param mobile
	 * @param content
	 * @param voiceName
	 *            男生：xiaoyu 女声：xiaoyan
	 * @param speed
	 *            语速
	 * @param timingTime
	 * @return
	 */
	public String postTextVoiceFile(String userId, String account, String passWord, String mobile, String content,
			String voiceName, String speed, String timingTime) {
		ResponseEntity<String> result = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
			headers.setContentType(type);
			StringBuffer sb = new StringBuffer();
			sb.append("userid=" + userId);
			sb.append("&account=" + account);
			sb.append("&password=" + passWord);
			sb.append("&mobile=" + mobile);
			sb.append("&content=" + URLEncoder.encode(content, "utf-8"));
			if (Tools.notEmpty(voiceName)) {
				sb.append("&voiceName=" + voiceName);
			}
			if (Tools.notEmpty(speed)) {
				sb.append("&speed=" + speed);
			}
			if (Tools.notEmpty(timingTime)) {
				sb.append("&sendTime=" + timingTime);
			}

			String body = sb.toString();
			result = restTemplate.exchange(voiceSendMessageUrl, HttpMethod.POST, new HttpEntity<>(body, headers),
					String.class);
			logger.info("result=" + result.getBody());
		} catch (Exception e) {
			logger.error("error occured at [postTextVoiceFile],{}", e);
			return "{status=-1}";
		}
		return result.getBody();
	}

	ForkJoinPool pool = new ForkJoinPool(4);

	public <T> Future<T> execute(Callable<T> call) {
		return pool.submit(call);
	}

}
