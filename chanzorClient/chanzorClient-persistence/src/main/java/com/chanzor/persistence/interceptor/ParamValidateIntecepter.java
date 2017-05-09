package com.chanzor.persistence.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.chanzor.persistence.annotation.ParamValidate;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;
import com.chanzor.util.JSONUtil;
import com.chanzor.util.MobileUtil;

/**
 * 验证param是否为空
 * @author Administrator
 *
 */
public class ParamValidateIntecepter extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			ParamValidate paramValidate = ((HandlerMethod)handler).getMethodAnnotation(ParamValidate.class);
			if(paramValidate != null){
				String[] param = paramValidate.validateParam();
				FormData data = new FormData(request);
				for (String key : param ){
					JSONObject json = JSONUtil.toJSON(key);
					if(json != null ){
						String name = json.getString("name");
						Object v = data.get(name);
						//不为空验证
						if(v == null  || v.toString().trim().length() <=0){
							String msg = json.getString("errorMsg");
							msg = (msg != null && !msg.trim().equals(""))? (msg+"不可为空") : (name+"不可为空.");
							handlerMsg(msg, response);
							return false;
						}
						Boolean isNum = json.getBoolean("isNum");
						if(isNum != null && isNum){
							if(!JSONUtil.isNum(v.toString())){
								String msg = json.getString("errorMsg");
								msg = (msg != null && !msg.trim().equals(""))? (msg+"必须为数字") : (name+"必须为数字.");
								handlerMsg(msg, response);
								return false;
							}
						}
						Integer maxLength = json.getIntValue("maxLength");
						if(maxLength != null && maxLength >0  ){
							if(v.toString().trim().length() > maxLength){
								String msg = json.getString("errorMsg");
								msg = (msg != null && !msg.trim().equals(""))?(msg+"必须小于"+maxLength+"个字符.") : (name+"必须小于"+maxLength+"个字符.");
								handlerMsg(msg, response);
								return false;
							}
						}
						Integer minLength = json.getIntValue("minLength");
						if(minLength != null && minLength >0  ){
							if(v.toString().trim().length() < minLength){
								String msg = json.getString("errorMsg");
								msg = (msg != null && !msg.trim().equals(""))?(msg+"必须大于"+minLength+"个字符.") : (name+"必须大于"+minLength+"个字符.");
								handlerMsg(msg, response);
								return false;
							}
						}
						Boolean isEmail = json.getBoolean("isEmail");
						if(isEmail != null && isEmail){
							if(!MobileUtil.validateEmail(v.toString())){
								String msg = json.getString("errorMsg");
								msg =  ("邮箱格式错误.");
								handlerMsg(msg, response);
								return false;
							}
						}
						Boolean isMobile = json.getBoolean("isMobile");
						if(isMobile != null && isMobile){
							if(!MobileUtil.validateMobile(v.toString())){
								String msg = json.getString("errorMsg");
								msg = ("手机号码格式错误.");
								handlerMsg(msg, response);
								return false;
							}
						}
					}else{
						Object v = data.get(key);
						if(v == null  || v.toString().trim().length() <=0){
							response.setStatus(Const.ERROR_CODE_PARAM);
							return false;
						}
					}
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
	private static void handlerMsg (String msg,HttpServletResponse response)throws Exception{
		response.setContentType("application/json; charset=utf-8");
		JSONObject errorJSON = new JSONObject();
		errorJSON.put("paramError", Const.ERROR_CODE_PARAM);
		errorJSON.put("paramErrorMsg", msg);
		response.getWriter().write(errorJSON.toJSONString());
	}
}
