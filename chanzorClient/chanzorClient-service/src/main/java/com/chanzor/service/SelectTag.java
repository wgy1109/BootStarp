package com.chanzor.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;




public class SelectTag extends SimpleTagSupport{

	
	private String name ;
	
	private String cls ;
	
	private String id ;
	
	private String sql ;
	
	private String value ;
	
	private String onChge;
	
	private String other;
	
	private String isQuery;

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = this.getJspContext().getOut();
		try {
			List<Map<String, Object>> data = ServiceHelper.getExportService().selectMap(sql);
			StringBuffer sb = new StringBuffer() ;
			StringBuffer attr = new StringBuffer();
			if(id != null && !id.equals("")) attr.append("id = \""+id+"\"");
			if(cls != null && !cls.equals("")) attr.append("class = \""+id+"\"");
			if(onChge != null && !onChge.equals("")) attr.append("onchange = \""+onChge+"\"");
			if(other != null && !other.equals("")) attr.append(other);
			sb.append("<select "+attr.toString()+" name=\""+name+"\">");
			if(isQuery != null && !isQuery.equals(""))sb.append("<option selected = \"selected\" value=\"\">全部</option>");
			if(value == null ) value = "";
			for (Map<String, Object> m : data) {
				String k = m.get("k") == null? "" : m.get("k").toString();
				String v = m.get("v") == null? "" : m.get("v").toString();
				if(k.equals(value)){
					sb.append("<option selected = \"selected\" value=\""+k+"\">"+v+"</option>");
				}else{
					sb.append("<option value=\""+k+"\">"+v+"</option>");
				}
				
			}
			sb.append("</select>");
			out.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.doTag();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOnChge() {
		return onChge;
	}

	public void setOnChge(String onChge) {
		this.onChge = onChge;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}
	
}
