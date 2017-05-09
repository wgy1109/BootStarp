package com.chanzor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

public class MobileUtil {
	
	public static boolean sendSMS(String mobile, String msg) {
		OutputStreamWriter out = null;
		StringBuilder sTotalString = new StringBuilder();
		try {
			URL urlTemp = new URL(Const.sendMessageUrl);
			URLConnection connection = urlTemp.openConnection();
			connection.setDoOutput(true);
			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			StringBuffer sb = new StringBuffer();
			sb.append("&userid=" + "test1");
			sb.append("&account=" + "test1");
			sb.append("&password=" + "test1");
			sb.append("&mobile=" + mobile);
			sb.append("&content=" + URLEncoder.encode(msg,"utf-8"));
//			sb.append("&content=" + msg);
			System.out.println("send message : "+sb.toString());
			out.write(sb.toString());
			out.flush();
			String sCurrentLine;
			sCurrentLine = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();// 请求
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(
					l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString.append(sCurrentLine);
			}
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
		return true;
	}

	public static String sendSMSMobileCode(String mobile) {
		String code = "";
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			code += r.nextInt(9);
		}
		String msg = "您的验证码是:" + code + "【畅卓科技】";
		boolean flag = sendSMS( mobile , msg);
		if (flag)
			return code;
		else
			return null;
	}
	public static String sendEmailCode (String email){
		if(validateEmail(email)){
			
		}
		return null;
	}
	public static boolean validateMobile (String mobile) {
		if(mobile == null) return false;
		return mobile.matches("^1[0-9]{10}$");
	}
	public static boolean validateEmail (String email){
		if(email == null) return false;
		if(email.length()>50) return false;
		return email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	}
}
