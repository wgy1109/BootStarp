package com.chanzor.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.AddressService;
import com.chanzor.util.FormData;

@Service("addressService")
@SuppressWarnings("unchecked")
public class AddressServiceImpl implements AddressService {

	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;
	
	@Override
	public List<Map<String, Object>> getAllAddressList(FormData data) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("AddressMapper.getAllAddressList",data);
	}

	@Override
	public Map<String, Object> getAddressById(FormData data) throws Exception {
		return (Map<String, Object>) daoSupport.findForObject("AddressMapper.getAddressById",data);
	}

	@Override
	public int saveAddress(FormData data) throws Exception {
		Object id = data.get("id");
		int result = 0;
		if (id == null || id.toString().equals("")) {
			result = (Integer) daoSupport.save("AddressMapper.saveAddress",data);
		} else {
			result = (Integer) daoSupport.update("AddressMapper.updateAddress",data);
		}
		return result;
	}

	

	@Override
	public int setDefaultAddress(FormData data) throws Exception {
		//修改本条地址为默认地址
		int num = (Integer) daoSupport.update("AddressMapper.updateDefaultAddress",data);
		//修改属于该客户的其它地址为非默认地址
		int num0 = (Integer) daoSupport.update("AddressMapper.updateOtherNoDefault",data);
		
		return num;
		
	}

	@Override
	public int delAddress(FormData data) throws Exception {
		return (Integer) daoSupport.update("AddressMapper.delAddress",data);
	}

}
