package com.chanzor.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.entity.PageInfo;
import com.chanzor.service.ExportService;
import com.chanzor.util.FormData;
import com.chanzor.util.POIUtil;

@Service
@SuppressWarnings("unchecked")
public class ExportServiceImpl implements ExportService{

	@Resource(name="daoSupport")
	private DaoSupport daoSupport;
	
	public int exportExcel (PageInfo page , HttpServletResponse response ){
		try {
			FormData data = page.getFormData();
			page.setPageSize(50000);
			String headers = data.getString("headers");
			if (headers == null || headers.equals(""))return 1;
			String columns = data.getString("columns");
			if (columns == null || columns.equals(""))return 1;
			String mapper = data.getString("mapper");
			if (mapper == null || mapper.equals(""))return 1;
			String title = data.getString("title");
			if (title == null || title.equals("")) title = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			List<Map<String, Object>> res = (List<Map<String, Object>>) daoSupport.findForList(mapper, page);
			POIUtil.exportExcel(headers, columns, res, title, response);;
			page.setPageSize(10);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	public List<Map<String, Object>> selectMap (String sql )throws Exception{
		FormData data = new FormData();
		data.put("sql", sql);
		return (List<Map<String, Object>>) daoSupport.findForList("tCodeMapper.selectMap", data);
	}
}
