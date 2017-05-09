package com.chanzor.service;

import com.chanzor.service.impl.UserServiceImpl;
import com.chanzor.util.Const;


/**
 * @author Administrator 获取Spring容器中的service bean
 */
public final class ServiceHelper {

	 public static Object getService(String serviceName) {
	 return Const.WEB_APP_CONTEXT.getBean(serviceName);
	 }
	
	 public static UserServiceImpl getUserService() {
	 return (UserServiceImpl) getService("userService");
	 }
	
	
	/* public static MenuService getMenuService() {
	 return (MenuService) getService("menuService");
	 }*/
	 public static ExportService getExportService() {
	 return (ExportService) getService("exportService");
	 }

}
