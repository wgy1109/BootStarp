package com.chanzor.util.alipay.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.chanzor.util.Const;
import com.chanzor.util.alipay.config.AlipayConfig;

public class AlipayUtil {

	public static Map<String, String> buildParameterMap(String notify_url, String return_url, String out_trade_no,
			String subject, String total_fee, String body, String common_param) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("notify_url", notify_url);// 异步通知地址
		param.put("service", "create_direct_pay_by_user");
		param.put("partner", AlipayConfig.partner);
		param.put("_input_charset", AlipayConfig.input_charset);
		param.put("seller_email", AlipayConfig.seller_email);
		param.put("payment_type", "1");
		param.put("return_url", return_url);// 同步通知地址
		param.put("out_trade_no", out_trade_no);// 订单号
		param.put("subject", subject); // 订单名称
		param.put("total_fee", total_fee);
		param.put("body", body);// 订单描述
		String anti_phishing_key = AlipaySubmit.query_timestamp();
		param.put("anti_phishing_key", anti_phishing_key);
		param.put("extra_common_param", common_param);
		return param;
	}
}
