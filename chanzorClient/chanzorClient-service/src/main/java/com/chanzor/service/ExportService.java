package com.chanzor.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.chanzor.entity.PageInfo;

public interface ExportService {

	public int exportExcel(PageInfo page, HttpServletResponse response);

	public List<Map<String, Object>> selectMap(String sql) throws Exception;
}
