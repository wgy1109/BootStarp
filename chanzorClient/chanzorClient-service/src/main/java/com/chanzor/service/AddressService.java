package com.chanzor.service;

import java.util.List;
import java.util.Map;

import com.chanzor.util.FormData;

public interface AddressService {
	public List<Map<String, Object>> getAllAddressList(FormData data) throws Exception;
	
	public Map<String, Object> getAddressById(FormData data)throws Exception;
	
	public int saveAddress(FormData data)throws Exception;
	
	public int setDefaultAddress(FormData data)throws Exception;
	
	public int delAddress(FormData data)throws Exception;
}
