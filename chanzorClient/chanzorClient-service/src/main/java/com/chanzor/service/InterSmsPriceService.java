package com.chanzor.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.chanzor.entity.PageInfo;


public interface InterSmsPriceService {
	public List<Map<String, Object>> getSmsPriceListByPage (PageInfo page )throws Exception;
}
