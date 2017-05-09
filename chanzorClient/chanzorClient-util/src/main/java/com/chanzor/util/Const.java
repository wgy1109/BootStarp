package com.chanzor.util;

import org.springframework.context.ApplicationContext;

public class Const {

	public static String SESSION_USER = "sessionUser";
	public static String SPINFO = "SpInfo";
	public static String SESSIONSPINFO = "sessionSpInfo";
	public static String CURRENT_APP = "CURRENT_APP";

	public static String APPS = "APPS";

	public static String APPIDS = "appIds";

	/** 图形验证码 */
	public static String IMAGE_CODE_SESSION = "IMAGE_CODE";
	/** 短信验证码 */
	public static String MOBILE_CODE_SESSION = "MOBILE_CODE_SESSION";

	public static String EMAIL_CODE_SESSION = "EMAIL_CODE_SESSION";

	// 不对匹配该值的访问路径拦截（正则）
	public static String NO_INTERCEPTOR_PATH = ".*/((log)|(logout)|(code)|(register)|(registerSub)|(loginCode)|(sendSMSCodeRegister)|(recover)|(loginTimeout)|(reLoginpage)|(loginAgain)|(sendResetCode)|(reset)|(adminLog)|(chargeRecord/alipay/alipay_notify_url)|(chargeRecord/alipay/alipay_return_url)).*";
	// 该值会在web容器启动时由WebAppContextListener初始化
	public static ApplicationContext WEB_APP_CONTEXT = null;
	public static String notify_url = "http://123.56.177.92:8088/chargeRecord/alipay/alipay_notify_url";
	public static String return_url = "http://123.56.177.92:8088/chargeRecord/alipay/alipay_return_url";
	/** 参数错误 */
	public static Integer ERROR_CODE_PARAM = 700;

	/** 登陆错误 */
	public static Integer ERROR_CODE_LOGIN = 800;
	/** 权限错误 */
	public static Integer ERROR_CODE_AUTH = 900;

	public static Integer ERROR_CODE_FILE = 902;

	public static String SERVER_BUSINESS_SRC = "http://123.56.177.92/httpmapping/";

	public static String mailServerHost = "smtp.exmail.qq.com";

	public static String MailServerPort = "25";

	public static String mailAddress = "czkj@chanzor.com";

	public static String mailpassword = "Jishubu123.";
	
	public static String FinanceAddress = "yjwang@chanzor.com";

	// 正式环境
	public static String sendMessageUrl = "http://123.56.177.92/sms.aspx?action";
	// 测试环境
	// public static String sendMessageUrl =
	// "http://192.168.0.145:8600/sms.aspx?action";

	public static String FILEINITPATH = "/usr/local/images/";

	public static String NGINXPATH = "http://123.56.177.92/httpmapping/";

	public final static int CHINESE_LONGSMS_LENGTH = 65;

	public final static int CHINESE_SMS_LENGTH = 70;

	public final static String SMS_SYSTEM_PARAM = "SMS_SYSTEM_PARAM";

	public final static String MAXCONTENTLENGTH = "Interface.UserMaxSmsContextLength";

	public final static String CHARGEACCOUNT = "account";

	public final static String CHARGESPINFO = "spinfo";

	public final static String PERMISSIONS = "PERMISSIONS";
	
	public final static String APPIDSSTR = "APPIDSSTR";

	public final static String APPIDSMAP = "APPIDSMAP";
	
	public final static String MDNSECTION = "MDNSECTION";
	
	public static String WORKFLOW = "WORKFLOW";
	public static String WORKFLOWYES= "1";
	public static String WORKFLOWNO= "0";
	
}
