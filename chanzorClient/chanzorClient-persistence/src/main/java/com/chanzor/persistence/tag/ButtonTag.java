package com.chanzor.persistence.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.chanzor.entity.SpInfo;
import com.chanzor.util.Const;

public class ButtonTag extends TagSupport {

	private String type;
	private Integer serviceType;

	@Override
	public int doStartTag() throws JspException {
		HttpSession session = this.pageContext.getSession();
		String landingType = session.getAttribute("LandingType") == null ? ""
				: session.getAttribute("LandingType").toString();
		SpInfo spInfo = (SpInfo) session.getAttribute(Const.SESSIONSPINFO);
		if (type != null && !type.equals("")) {
			if (landingType.equals(type)) {
				return TagSupport.EVAL_BODY_AGAIN;
			} else if (serviceType != null && !serviceType.equals("")) {
				if (spInfo.getSp_service_type().equals(serviceType)) {
					return TagSupport.EVAL_BODY_AGAIN;
				}
			}
		}
		return TagSupport.SKIP_BODY;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

}
