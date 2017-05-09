package com.chanzor.controller.appVatinvoiceCertinfo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.persistence.annotation.ParamValidate;
import com.chanzor.service.AppVatinvoiceCertinfoClientService;
import com.chanzor.util.FileUpload;
import com.chanzor.util.FormData;

@Controller
@RequestMapping("appVatinvoiceCertinfoClient")
public class AppVatinvoiceCertinfoClientController extends BaseController{

	@Autowired
	private AppVatinvoiceCertinfoClientService service;
	
//	@RequestMapping("")
//	public ModelAndView list (PageInfo page) throws Exception{
//		ModelAndView mv = new ModelAndView("appVatinvoiceCertinfo/appVatinvoiceCertinfoClient_list");
//		return mv;
//	}
//	@RequestMapping("load")
//	@ResponseBody
//	public Map<String, Object> load (PageInfo page) throws Exception{
//		page.setFormData(getFormData());
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("page", page);
//		result.put("data", service.getAllAppVatinvoiceCertinfoByPage(page));
//		return result;
//	}
	@RequestMapping("edit")
	@ResponseBody
	public Map<String,Object> edit () throws Exception {
		return service.getAppVatinvoiceCertinfoInfoById(getFormData());
	} 
	@RequestMapping("save")
	@ResponseBody
	@ParamValidate(validateParam={
			"{\"name\":\"idenitficationNum\",\"maxLength\":30,\"errorMsg\":\"纳税人识别号\"}",
			"{\"name\":\"bankAccent\",\"maxLength\":30,\"errorMsg\":\"银行账号\"}",
			"{\"name\":\"openAccent\",\"maxLength\":100,\"errorMsg\":\"开户行\"}",
			"{\"name\":\"registeredAddress\",\"maxLength\":100,\"errorMsg\":\"注册地址\"}",
			"{\"name\":\"companyPhone\",\"maxLength\":19,\"errorMsg\":\"企业电话\"}",
			"{\"name\":\"BANK_ACCENT_IMG_SRC\",\"errorMsg\":\"银行开户许可证(扫描件)\"}",
			"{\"name\":\"TAX_REGISTRATION_IMG_SRC\",\"errorMsg\":\"税务登记证(扫描件)\"}",
			"{\"name\":\"GENERAL_TAX_IMG_SRC\",\"errorMsg\":\"一般纳税人认定(扫描件)\"}"
	})
	public Map<String, Object> save () throws Exception{
		FormData formdata = getFormData();
		Map<String, Object> res = new HashMap<String, Object>();
		if(formdata.get("idenitficationNum")==null || formdata.get("idenitficationNum")=="" || formdata.get("bankAccent")==null || formdata.get("bankAccent")=="" || formdata.get("openAccent")==null || formdata.get("openAccent")=="" || formdata.get("registeredAddress")==null || formdata.get("registeredAddress")=="" || formdata.get("companyPhone")==null || formdata.get("companyPhone")=="" ){
			res.put("code", "22");
			res.put("msg", "请填写完整数据");
			return res;
		}
		return service.saveAppVatinvoiceCertinfo(formdata, getRequest().getSession());
	}
	
	
	@RequestMapping("saveCommon")
	@ResponseBody
	@ParamValidate(validateParam={
			"{\"name\":\"vatinvoice_title\",\"minLength\":1,\"errorMsg\":\"发票抬头\"}",
			"{\"name\":\"idenitfication_num\",\"minLength\":1,\"errorMsg\":\"统一社会信用代码\"}"
	})
	public Map<String, Object> saveCommon () throws Exception{
		FormData formdata = getFormData();
		Map<String, Object> res = new HashMap<String, Object>();
		if(formdata.get("vatinvoice_title")==null || formdata.get("vatinvoice_title")==""){
			res.put("code", "22");
			res.put("msg", "请填写完整数据");
			return res;
		}
		return service.saveAppVatinvoiceCertinfoCommon(formdata, getRequest().getSession());
	}
	
	
	@RequestMapping("saveSpecial")
	@ResponseBody
	@ParamValidate(validateParam={
			"{\"name\":\"vatinvoice_title\",\"minLength\":1,\"errorMsg\":\"发票抬头\"}",
			"{\"name\":\"idenitfication_num\",\"minLength\":1,\"errorMsg\":\"统一社会信用代码\"}",
			"{\"name\":\"open_accent\",\"minLength\":1,\"errorMsg\":\"基本开户银行名称\"}",
			"{\"name\":\"bank_accent\",\"minLength\":1,\"errorMsg\":\"基本开户账号\"}",
			"{\"name\":\"registered_address\",\"minLength\":1,\"errorMsg\":\"注册场所地址\"}",
			"{\"name\":\"company_phone\",\"minLength\":1,\"errorMsg\":\"注册固定电话\"}",
			"{\"name\":\"bank_accent_img_SRC\",\"errorMsg\":\"银行开户许可证(复印件)\"}"
	})
	public Map<String, Object> saveSpecial() throws Exception{
		FormData formdata = getFormData();
		Map<String, Object> res = new HashMap<String, Object>();
		if(formdata.get("idenitfication_num")==null || formdata.get("idenitfication_num")=="" || formdata.get("bank_accent")==null || formdata.get("bank_accent")=="" || formdata.get("open_accent")==null || formdata.get("open_accent")=="" || formdata.get("registered_address")==null || formdata.get("registered_address")=="" || formdata.get("company_phone")==null || formdata.get("company_phone")=="" ){
			res.put("code", "22");
			res.put("msg", "请填写完整数据");
			return res;
		}
		return service.saveAppVatinvoiceCertinfo(formdata, getRequest().getSession());
	}
	
	
	
	
	//提交认证审核
	@RequestMapping("vatinvoiceSubmit")
	@ResponseBody
	public Map<String, Object> vatinvoiceSubmit () throws Exception{
		return service.vatinvoiceSub(getFormData());
	}
	
	@RequestMapping("del")
	@ResponseBody
	public Map<String, Object> delete () throws Exception{
		Map<String, Object> result = new HashMap<String, Object> ();
		int  res = service.deleteAppVatinvoiceCertinfo(getFormData());
		result.put("statusCode", (res>=1?200:201));
		return result;
	}
	
	//发票认证审核
	@RequestMapping("examine")
	@ResponseBody
	public Map<String, Object> examine () throws Exception{
		Map<String, Object> result = new HashMap<String, Object> ();
		int res =service.updateInvoiceStatusService(getFormData());
		result.put("statusCode", (res>=1?200:201));
	/*	FormData map = new FormData();
		map.put("opearte_type", Const.EXAMINE_INVOICE);
		map.put("opearte_result", (res>=1?1:2));
		map.put("opearte_content", (res>=1?"发票认证审核完成！":"发票认证审核异常"));
		//记录系统日志
		this.insertOperalog(map);*/
		return result;
	}
	
	//跳转到详细信息
	@RequestMapping("detail")
	public ModelAndView detail (HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("appVatinvoiceCertinfo/appVatinvoiceCertinfoClient_detail");
		FormData formdata = getFormData();
		mv.addObject("data", service.getAppVatinvoiceCertinfoInfoById(formdata));
		String url = request.getContextPath()+"/appVatinvoiceCertinfo.html";
		mv.addObject("url", url);
		return mv;
	}
	
	//跳转到添加企业资质
	@RequestMapping("editUpdate")
	public ModelAndView editUpdate (HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("appVatinvoiceCertinfo/appVatinvoiceCertinfoClient_edit");
		FormData formdata = getFormData();
		mv.addObject("data", service.getAppVatinvoiceCertinfoInfoById(formdata));
//		String url = request.getContextPath()+"/appVatinvoiceCertinfo.html";
//		mv.addObject("url", url);
		return mv;
	}
	
	// 保存发票信息
	@RequestMapping("/invoice_submit")
	public ModelAndView submitInvoice(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			@RequestParam(required = true) String id,
			@RequestParam(required = true) String idenitficationNum,
			@RequestParam(required = true) String bankAccent,
			@RequestParam(required = true) String openAccent,
			@RequestParam(required = true) String registeredAddress,
			@RequestParam(required = true) String companyPhone,
			@RequestParam(required = true) MultipartFile bankAccentImg,
			@RequestParam(required = true) MultipartFile taxRegistrationImg,
			@RequestParam(required = true) MultipartFile generalTaxImg) throws Exception {
		String realPath = request.getSession().getServletContext()
				.getRealPath("upload");
//		List<Map<String, Object>> userJson = (List<Map<String, Object>>) session.getAttribute(Const.APPS);
//		Integer user_id = (Integer) userJson.get(0).get("user_id");
		Integer user_id = 4575;
		String bankAccentImgSrc = FileUpload.fileUp(bankAccentImg, realPath,
				user_id + "vatkhxkz");
		String taxRegistrationImgSrc = FileUpload.fileUp(taxRegistrationImg,
				realPath, user_id + "vatswdjz");
		String generalTaxImgSrc = FileUpload.fileUp(generalTaxImg, realPath,
				user_id + "vatnsrrd");
		
		FormData data = new FormData();
		data.put("id", id);
		data.put("USER_ID", user_id);
		data.put("IDENITFICATION_NUM", idenitficationNum);
		data.put("BANK_ACCENT", bankAccent);
		data.put("OPEN_ACCENT", openAccent);
		data.put("REGISTERED_ADDRESS", registeredAddress);
		data.put("COMPANY_PHONE", companyPhone);
		data.put("BANK_ACCENT_IMG", bankAccentImgSrc);
		data.put("TAX_REGISTRATION_IMG", taxRegistrationImgSrc);
		data.put("GENERAL_TAX_IMG", generalTaxImgSrc);
//		int res =service.saveAppVatinvoiceCertinfo(data);

		return new ModelAndView("redirect:/appVatinvoiceCertinfoClient.html");
	}

//	PUBLIC STATIC VOID MAIN(STRING[] ARGS) {
//		SYSTEM.OUT.PRINTLN(123123);
//	}
}
