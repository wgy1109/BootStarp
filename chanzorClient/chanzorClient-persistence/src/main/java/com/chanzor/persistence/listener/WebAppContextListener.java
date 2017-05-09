package com.chanzor.persistence.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chanzor.util.*;

public class WebAppContextListener implements ServletContextListener {
	private static Logger log = Logger.getLogger(WebAppContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		/*event.getServletContext().getServlets().nextElement().destroy();*/
	}

	public void contextInitialized(ServletContextEvent event) {
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		log.debug("========获取Spring WebApplicationContext");
	}

}