package com.chanzor.controller.indexController;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.persistence.annotation.ParamValidate;
//import com.chanzor.service.AppBusinessManagerClientService;
import com.chanzor.service.LoginService;
import com.chanzor.service.RedisService;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.UserService;
import com.chanzor.util.Const;
import com.chanzor.util.FileManageUtils;
import com.chanzor.util.FormData;
import com.chanzor.util.MobileUtil;
import com.chanzor.util.PropertiesConfig;
import com.chanzor.util.Tools;
import com.chanzor.util.alipay.util.httpClient.HttpResponse;

import net.sf.json.JSONObject;

@Controller
public class LoginController extends BaseController {

	private static Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	private LoginService Service;
	/*
	 * @Autowired private AppBusinessManagerClientService businessService;
	 */
	@Autowired
	private SpinfoService spinfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	public PropertiesConfig properties;
	private int width = 80;// 定义图片的width
	private int height = 17;// 定义图片的height
	private int codeCount = 4;// 定义图片上显示验证码的个数
	private int xx = 15;
	private int fontHeight = 18;
	private int codeY = 16;
	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 登陆
	 * 
	 * @return
	 * @throws Exception
	 */
	@ParamValidate(validateParam = { "username", "password" })
	@RequestMapping("log")
	@ResponseBody
	public Map<String, Object> log() throws Exception {
		FormData data = this.getFormData();
		data.put("ip", getIpAddr());
		HttpSession session = getRequest().getSession();
		Map<String, Object> m = Service.login(data, session);
		log.info(m);
		String backurl = (String) session.getAttribute("backurl");
		if (backurl != null && backurl.trim().length() > 0) {
			session.removeAttribute("backurl");
			m.put("backurl", backurl);
		}
		return m;
	}

	/**
	 * S端登陆
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("adminLog")
	public void adminLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FormData data = this.getFormData();
		data.put("ip", getIpAddr());
		HttpSession session = getRequest().getSession();
		Map<String, Object> result = Service.login(data, session);
		result.put("returnCode", "success");
		PrintWriter out = response.getWriter();
		JSONObject resultJSON = JSONObject.fromObject(result); // 根据需要拼装json
		String jsonpCallback = request.getParameter("callback");// 客户端请求参数
		out.println(jsonpCallback + "(" + resultJSON.toString(1, 1) + ")");// 返回jsonp格式数据
		out.flush();
		out.close();
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("reset")
	@ResponseBody
	public Map<String, Object> reset(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String resetpwd_code = (String) session.getAttribute("resetpwd_code");
		String[] split = resetpwd_code.split("-");
		String mobile = split[0];
		String code = split[1];
		FormData data = this.getFormData();
		String password = data.getString("password");
		if (!spinfoService.isMobile(data.getString("mobile"))) {
			result.put("statusCode", 201);
			result.put("msg", "请输入正确的手机号码");
			return result;
		} else if (data.getString("password") == null || data.getString("password").length() < 6) {
			result.put("statusCode", 204);
			result.put("msg", "密码格式有误");
			return result;
		} else if (code == null || code.equals("")) {
			result.put("statusCode", 202);
			result.put("msg", "您还未获取验证码");
			return result;
		} else if (!code.equals(data.getString("code")) || !mobile.equals(data.getString("mobile"))) {
			result.put("statusCode", 203);
			result.put("msg", "手机验证码有误");
			return result;
		} else {
			result = Service.reset(data);
			Map<String, Object> map = Service.getNum(data);
			data.put("userId", map.get("id"));
			// 修改密码后更新redis信息
			data.put("password", password);
			redisService.insertUserRedis(data, null, "RESET");
		}
		return result;
	}

	/**
	 * 发送重置密码短信验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sendResetCode")
	@ResponseBody
	public Map<String, Object> sendResetCode(HttpSession session) throws Exception {
		FormData data = this.getFormData();
		Map<String, Object> result = new HashMap<String, Object>();
		String mobile = data.getString("mobile");

		if (!spinfoService.isMobile(mobile)) {
			result.put("statusCode", 201);
			result.put("msg", "请输入正确的手机号码");
			return result;
		} else {
			Map<String, Object> exits = Service.isMobileExits(mobile);
			if (!"200".equals(exits.get("statusCode").toString())) {
				return exits;
			} else {
				String code = sendSMSMobileCode(mobile);
				logger.error(mobile + "---------" + code);
				session.setAttribute("resetpwd_code", mobile + "-" + code);
				result.put("statusCode", 200);
				result.put("msg", "验证码已发送到您的手机上，请注意查收！");
			}
		}
		return result;
	}

	@RequestMapping("recover.html")
	public String recover() throws Exception {
		return "index/recover";
	}

	@RequestMapping("register.html")
	public ModelAndView register() throws Exception {
		FormData data = getFormData();
		ModelAndView mv = new ModelAndView("index/register");
		mv.addObject("identif", data.getString("identif"));
		return mv;
	}

	@RequestMapping("logout")
	public String logout() {
		HttpServletRequest request = getRequest();
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}

	/**
	 * 注册
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("registerSub")
	@ResponseBody
	public String registerSub(HttpSession session, HttpServletRequest request) throws Exception {
		FormData data = getFormData();
		if (data.get("mobile") == null || data.get("mobile") == "" || data.get("register_password") == null
				|| data.get("register_password") == "" || data.get("register-cord") == null
				|| data.get("register-cord") == "" || data.get("register-smsCode") == null
				|| data.get("register-smsCode") == "") {
			return "205";
		}
		data.put("password", data.get("register_password"));
		String mobileCodeSession = (String) session.getAttribute(Const.MOBILE_CODE_SESSION);
		String mobileCode = data.getString("register-smsCode") + "-" + data.getString("mobile");
		if (null != mobileCodeSession) {
			if (!mobileCodeSession.equals(mobileCode.trim())) {
				return "204";
			}
		} else {
			return "204";
		}
		data.put("LAST_LOGIN_IP", getClientIp(request));
		Map<String, Object> map = Service.getNum(data);
		if (map != null) {
			return "201";
		}
		if (data.get("recommend") != null) {
			String supportUrl = properties.getOffice_url() + "/crm/client/findUserInfo?appUserName="
					+ data.get("recommend");
			ResponseEntity<Map> resultEntitySupport = restTemplate.exchange(supportUrl, HttpMethod.GET, null,
					Map.class);
			Map<String, Object> result = resultEntitySupport.getBody();
			if (result.get("managerId") != null && result.get("managerId") != "") {
				data.put("appManagerId", result.get("managerId"));
			} else {
				data.put("appManagerId", properties.getUser_manager_id());
			}
		}

		int num = Service.register(data);
		if (num > 0) {
			map = Service.getNum(data);
			request.setAttribute("userId", map.get("id"));
			data.put("userId", map.get("id"));
			// 注册成功后添加redis信息
			spinfoService.createSpInfoByNewUser((Integer) map.get("id"), data.getString("mobile"));
			redisService.insertUserRedis(data, null, "ADD");
			session.setAttribute(Const.WORKFLOW, Const.WORKFLOWYES);
			return "200";
		} else {
			return "202";
		}
	}

	@RequestMapping("createServiceUser")
	@ResponseBody
	public String createServiceUser(HttpSession session, HttpServletRequest request) throws Exception {
		FormData data = getFormData();
		data.put("LAST_LOGIN_IP", getClientIp(request));
		Map<String, Object> map = Service.getNum(data);
		if (map != null) {
			return "201";
		}
		int num = Service.register(data);
		if (num > 0) {
			map = Service.getNum(data);
			request.setAttribute("userId", map.get("id"));
			data.put("userId", map.get("id"));
			// 注册成功后添加redis信息
			spinfoService.createSpInfoByNewUser((Integer) map.get("id"), data.getString("mobile"));
			redisService.insertUserRedis(data, null, "ADD");
			return "200";
		} else {
			return "202";
		}
	}

	// 得到图形验证码
	@RequestMapping("/loginCode")
	public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// Graphics2D gd = buffImg.createGraphics();
		// Graphics2D gd = (Graphics2D) buffImg.getGraphics();
		Graphics gd = buffImg.getGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
		// 设置字体。
		gd.setFont(font);

		// 画边框。
		gd.setColor(Color.BLACK);
		gd.drawRect(0, 0, width - 1, height - 1);

		// 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
		gd.setColor(Color.BLACK);
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gd.drawLine(x, y, x + xl, y + yl);
		}

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;

		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String code = String.valueOf(codeSequence[random.nextInt(36)]);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);

			// 用随机产生的颜色将验证码绘制到图像中。
			gd.setColor(new Color(red, green, blue));
			gd.drawString(code, (i + 1) * xx, codeY);

			// 将产生的四个随机数组合在一起。
			randomCode.append(code);
		}
		// 将四位数字的验证码保存到Session中。
		HttpSession session = req.getSession();
		System.out.print(randomCode);
		session.setAttribute(Const.IMAGE_CODE_SESSION, randomCode.toString());

		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		resp.setContentType("image/jpeg");

		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}

	/**
	 * 发送验证码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("sendSMSCodeRegister")
	@ResponseBody
	public Map<String, Object> sendSMSCodeRegister(HttpServletRequest request) throws Exception {
		FormData data = getFormData();
		return userService.senmdSMSCode(data, request.getSession());
	}

	// 绑定手机号发送验证码
	@RequestMapping("saveMobileSendSMSCode")
	@ResponseBody
	public Map<String, Object> saveMobileSendSMSCode(HttpServletRequest request, HttpSession session) throws Exception {
		FormData data = getFormData();
		String code = userService.servicesendSMSMobileCode(data.getString("mobile"));
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", "00");
		res.put("msg", "发送成功..");
		session.setAttribute(Const.MOBILE_CODE_SESSION, code + "-" + data.getString("mobile"));
		return res;
	}

	// 账户绑定手机号码
	@RequestMapping("saveMobileSub")
	@ResponseBody
	public Map<String, Object> saveMobileSub(HttpServletRequest request, HttpSession session) throws Exception {
		FormData data = getFormData();
		Map<String, Object> res = new HashMap<String, Object>();
		String mobileCodeSession = (String) session.getAttribute(Const.MOBILE_CODE_SESSION);
		String mobileCode = data.getString("savemobile-smsCode") + "-" + data.getString("savemobile");
		if (mobileCodeSession != null) {
			if (!mobileCodeSession.equals(mobileCode.trim())) {
				res.put("code", "02");
				res.put("msg", "验证码输入错误");
				return res;
			}
		} else {
			res.put("code", "02");
			res.put("msg", "请获取新的验证码");
			return res;
		}
		return userService.saveMobile(data);
	}

	// 登陆校验发送验证码
	@RequestMapping("loginSendSMSCode")
	@ResponseBody
	public Map<String, Object> loginSendSMSCode(HttpServletRequest request, HttpSession session) throws Exception {
		FormData data = getFormData();
		Map<String, Object> m = Service.gerLoginMobile(data, session);
		String resultCode = m.get("code").toString();
		if (!resultCode.equals("00")) {
			return m;
		}
		String code = userService.servicesendSMSMobileCode(m.get("msg").toString());
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", "00");
		res.put("msg", "发送成功..");
		session.setAttribute(Const.MOBILE_CODE_SESSION, code + "-" + m.get("msg").toString());
		return res;
	}

	@RequestMapping("updateflowvalue")
	@ResponseBody
	public Map<String, Object> updateflowvalue(HttpSession session) throws Exception {
		session.setAttribute(Const.WORKFLOW, Const.WORKFLOWNO);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", "00");
		return res;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("downloadhelpbtn")
	@ResponseBody
	public void downloadhelpbtn(HttpSession session, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		// 这里下载的文档，是用Nginx反射代理静态文件。直接访问下载服务器上静态文件。
		String path = properties.getService_nginx_url() + "templete/help.doc";
		FileManageUtils.exportFile(response, path, "自助平台操作手册.doc");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("downloadcontract")
	@ResponseBody
	public String downloadcontract(HttpSession session, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		String result = "fail";
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		Integer id = (Integer) userInfo.get("id");
		Map<String, Object> authInfo = userService.selPassAuthById(id);
		if (authInfo != null) {
			result = "success";
		}
		return result;
	}

	@RequestMapping("showHelpMessage")
	public ModelAndView list(PageInfo page) throws Exception {
		ModelAndView mv = new ModelAndView("common/helpmessage");
		return mv;
	}

}
