package com.chanzor.persistence.tag;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.chanzor.util.Const;

public class PermissionTag extends TagSupport {

	private static final long serialVersionUID = 8425285172175749771L;
	
	private String permissionCode;
	
	private static Properties  properties;
	
	
	
	@Override
	public int doStartTag() throws JspException {
		if(properties == null){
			try{
				InputStream inStream = PermissionTag.class.getClassLoader().getResourceAsStream("application.properties"); 
				properties = new Properties();    
				properties.load(inStream); 
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		String subaccountExtendFinance = properties.getProperty("subaccount_extend_finance");  
		
		HttpSession session = this.pageContext.getSession();
		Map<String, Object> u = (Map<String, Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)u.get("is_sub_account") == 0){ //大账号拥有所有权限
			return TagSupport.EVAL_BODY_AGAIN;
		}
		else{
			String permissions = (String)session.getAttribute(Const.PERMISSIONS);
			//针对乐元素的定制，乐元素要求某个子账号可以查询 消费明细，财务相关的内容
			String userName = (String)u.get("user_name");
			if(subaccountExtendFinance != null && subaccountExtendFinance.indexOf(userName) != -1){
				permissions += "cwgl"+","; //增加财务管理权限
			}
			
			if(permissions.indexOf(getPermissionCode()) != -1){
				return TagSupport.EVAL_BODY_AGAIN;
			}
		}
		return TagSupport.SKIP_BODY;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}
	
}
