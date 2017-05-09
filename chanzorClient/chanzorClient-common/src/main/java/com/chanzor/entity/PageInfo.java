package com.chanzor.entity;

import java.util.List;

import com.chanzor.util.FormData;

public class PageInfo {

	private int pageSize=10;

	private int totalPage;

	private int currentPage;
	
	private int totalSize;
	
	private FormData formData;
	
	private List<Object> listData;
	
	private String pageStr ;
	private int start;
	private int length;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
		totalPage = totalSize%pageSize== 0 ? totalSize/pageSize : totalSize/pageSize +1;
	}

	public FormData getFormData() {
		return formData;
	}

	public void setFormData(FormData formData) {
		this.formData = formData;
	}

	public List<Object> getListData() {
		return listData;
	}

	public void setListData(List<Object> listData) {
		this.listData = listData;
	}

	public String getPageStr() {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"row\">" +
				"<div class=\"col-sm-6\">" +
				"<div class=\"dataTables_info\">显示 "+(pageSize*(currentPage-1)+1)+" 到 "+(totalSize>pageSize?pageSize*currentPage:totalSize)+" 项，共 "+totalSize+" 项</div></div>" +
				"<div class=\"col-sm-6\">" +
						"	<div class=\"dataTables_paginate paging_simple_numbers\">");
		sb.append("<ul class=\"pagination\">");
		if(currentPage>1){
			sb.append("<li class=\"paginate_button previous \"><a href=\"javascript:goPage("+(currentPage-1)+")\">上一页</a>	</li>");
		}else{
			sb.append("<li class=\"paginate_button previous disabled\"><a href=\"javascript:void(0)\" disabled=\"disabled\">上一页</a>	</li>");
		}
		for(int i = 0 ; i < totalPage ; i ++ ){
			if(currentPage == (i+1) )sb.append("<li class=\"paginate_button active\"><a href=\"javascript:void(0)\">"+(i+1)+"</a>");
			else sb.append("<li class=\"paginate_button\"><a href=\"javascript:goPage("+(i + 1)+")\">"+(i+1)+"</a>");
		}
		if(currentPage>=totalPage)sb.append("<li class=\"paginate_button next disabled\"><a   	href=\"javascript:void(0)\" >下一页</a>");
		else sb.append("<li class=\"paginate_button next \"><a 	href=\"javascript:goPage("+(currentPage+1)+")\">下一页</a>");
	    sb.append("</ul>");
		sb.append("</div></div></div>");
		pageStr = sb.toString();
		return pageStr;
	}

	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}


		
}
