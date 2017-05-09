package com.chanzor.persistence.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chanzor.util.*;
/**
 * 登陆验证
 * @author Administrator
 *
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String pathurl = request.getServletPath();
		if(pathurl.equals("/") ) return true;
		if (pathurl.matches(Const.NO_INTERCEPTOR_PATH)) {
			return true;
		} else {
			HttpSession session = request.getSession();
			Object user = session.getAttribute(Const.SESSION_USER);
			if( user == null || user.toString().equals("")){
				ResponseBody responseBody = ((HandlerMethod)handler).getMethodAnnotation(ResponseBody.class);
				if(responseBody != null ){
					response.setStatus(Const.ERROR_CODE_LOGIN);
					return false;
				}
				String level = request.getParameter("level");
				if(level != null && "1".equals(level)){
					response.setStatus(Const.ERROR_CODE_LOGIN);
					return false;
				}
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ path + "/";
				response.sendRedirect(basePath);
				pathurl = pathurl.startsWith("/")?pathurl.substring(1, pathurl.length()):pathurl;
				request.getSession().setAttribute("backurl",pathurl);
				return false;
			}else{
				return true;
			}
		}
	}

}
