package com.chanzor.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chanzor.controller.base.BaseController;
import com.chanzor.persistence.annotation.ParamValidate;
import com.chanzor.service.AddressService;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;
import com.chanzor.util.MobileUtil;

@Controller
public class AddressController extends BaseController {
	//新增，修改，删除，设置默认，列表显示（不分页）
	
	@Autowired
	private AddressService addressService;
	
	@RequestMapping("address.html")
	public String addressList(Model model) throws Exception {
		model.addAttribute("addressList", addressService.getAllAddressList(getFormData()));
		return "user/address";
	}
	
	@RequestMapping("saveAddress")
	@ResponseBody
	@ParamValidate(validateParam={
				  "{\"name\":\"collect_name\",\"minLength\":1,\"errorMsg\":\"收件人\"}",
			      "{\"name\":\"collect_address\",\"minLength\":1,\"errorMsg\":\"地址\"}"
	          })
	public Map<String,Object> saveAddress(HttpSession session) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		//最多不超过10个地址
		List<Map<String, Object>> addressList = addressService.getAllAddressList(getFormData());
		if(addressList.size() >= 10){
			result.put("code", "01");
			result.put("msg", "已经创建了10个地址，不能再创建了！");
			return result;
		}
		
		Map<String,Object> map=(Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		FormData formData = getFormData();
		Object id = formData.get("id");
		if(id != null && !id.equals("")){
			formData.put("id", Integer.parseInt((String)formData.get("id")));
			formData.put("update_time", new Date());
			formData.put("update_id",Integer.valueOf(map.get("id").toString()));
		}
		else{
			formData.put("create_id", Integer.valueOf(map.get("id").toString()));
			formData.put("create_time", new Date());
			formData.put("update_time", new Date());
			formData.put("update_id",Integer.valueOf(map.get("id").toString()));
			formData.put("default_mark", 0);
		}
		
		String collectPhone = (String)formData.get("collect_phone");
		String collectTel = (String)formData.get("collect_tel");
		
		if(StringUtils.isEmpty(collectPhone) && StringUtils.isEmpty(collectTel)){
			result.put("code", "01");
			result.put("msg", "手机或电话必须填写一个");
			return result;
		}
		
		//对手机号进行格式验证
		if(!StringUtils.isEmpty(collectPhone)){
			boolean isMobile = MobileUtil.validateMobile(collectPhone);
			if(!isMobile){
				result.put("code", "01");
				result.put("msg", "手机格式不正确");
				return result;
			}
		}
		
		int affectRowCount = addressService.saveAddress(formData);
		if(affectRowCount == 1){
			result.put("code", "00");
			result.put("msg", "保存地址成功");
		}
		else{
			result.put("code", "01");
			result.put("msg", "保存地址失败");
		}
		return result;
	} 
	
	@RequestMapping("setDefaultAddress")
	@ResponseBody
	public Map<String,Object> updateAddress() throws Exception {
		FormData formData = getFormData();
		formData.put("id", Integer.parseInt((String)formData.get("id")));
		int affectRowCount = addressService.setDefaultAddress(formData);
		Map<String,Object> result = new HashMap<String,Object>();
		if(affectRowCount == 1){
			result.put("code", "00");
			result.put("msg", "设置默认地址成功");
		}
		else{
			result.put("code", "01");
			result.put("msg", "设置默认地址失败");
		}
		return result;
	} 
	
	@RequestMapping("delAddress")
	@ResponseBody
	public Map<String,Object> delAddress() throws Exception {
		FormData formData = getFormData();
		formData.put("id", Integer.parseInt((String)formData.get("id")));
		int affectRowCount = addressService.delAddress(formData);
		Map<String,Object> result = new HashMap<String,Object>();
		if(affectRowCount == 1){
			result.put("code", "00");
			result.put("msg", "删除地址成功");
		}
		else{
			result.put("code", "01");
			result.put("msg", "删除地址失败");
		}
		return result;
	} 
	
}
