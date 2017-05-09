package com.chanzor.service;

import java.util.List;
import java.util.Map;

import com.chanzor.entity.Page;
import com.chanzor.entity.PageInfo;

public interface SpConsumerDetailService {

	public List<Map<String,Object>> findConsumerDetaillistPage(PageInfo page)
			throws Exception;

}
