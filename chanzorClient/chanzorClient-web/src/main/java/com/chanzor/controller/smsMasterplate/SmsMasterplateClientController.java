package com.chanzor.controller.smsMasterplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.RedisService;
import com.chanzor.service.SmsMasterplateClientService;
import com.chanzor.service.SpinfoService;
import com.chanzor.util.Const;
import com.chanzor.util.ExportExcel;
import com.chanzor.util.FileManageUtils;
import com.chanzor.util.FileUtil;
import com.chanzor.util.FormData;
import com.chanzor.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("smsMasterplateClient")
@SuppressWarnings("unchecked")
public class SmsMasterplateClientController extends BaseController {

	@Autowired
	private SmsMasterplateClientService service;
	@Autowired
	private SpinfoService spinfoService;
	@Autowired
	private RedisService redisService;

	@RequestMapping("")
	public ModelAndView list(PageInfo page, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("smsMasterplateClient/smsMasterplateClient_list");
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = getFormData();
		formdata.put("userId", userid);
		if (checkLandType(session)) {
			formdata.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			formdata.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		mv.addObject("spinfoList", spinfoService.getYESSpinfoByUserIDService(formdata));
		return mv;
	}

	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page, HttpSession session) throws Exception {
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = getFormData();
		formdata.put("userId", userid);
		List<Map<String, Object>> data = null;
		if (checkLandType(session)) {
			formdata.put("SP_ID", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
			page.setFormData(formdata);
			data = service.getSmsMasterplateBySessionSpInfo(page);
		} else {
			Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
			if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
				formdata.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
			}
			page.setFormData(formdata);
			data = service.getAllSmsMasterplateClientByPage(page);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		result.put("data", data);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		return result;
	}

	@RequestMapping("edit")
	@ResponseBody
	public Map<String, Object> edit(HttpSession session) throws Exception {
		Map<String, Object> map = service.getSmsMasterplateClientInfoById(getFormData());
		map.put("maxlength", session.getAttribute(Const.MAXCONTENTLENGTH));
		return map;
	}

	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> save(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = getFormData();
		if (formdata.get("title") == null || formdata.get("title") == "" || formdata.get("content") == null
				|| formdata.get("content") == "") {
			result.put("statusCode", 202);
			return result;
		}
		formdata.put("userId", userid);
		int res = service.saveSmsMasterplateClient(formdata);
		logger.info("编辑一条模板记录：添加结果" + res);
		result.put("statusCode", (res >= 1 ? 200 : 201));
		return result;
	}

	@RequestMapping("saveOnTrialMaster")
	@ResponseBody
	public Map<String, Object> saveOnTrialMaster(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = getFormData();
		if (formdata.get("title") == null || formdata.get("title") == "" || formdata.get("spid") == null
				|| formdata.get("spid") == "") {
			result.put("statusCode", 202);
			return result;
		}
		int ontrialnum = service.validateontrialMessageService(formdata);
		if (ontrialnum > 0) {
			result.put("statusCode", 203);
			return result;
		}
		formdata.put("userId", userid);
		int res = service.saveonTrialSmsMasterplateClient(formdata);
		if (res >= 1) {
			redisService.insSysTemplete(formdata);
		}
		logger.info("编辑一条模板记录：添加结果" + res);
		result.put("statusCode", (res >= 1 ? 200 : 201));
		return result;
	}

	@RequestMapping("del")
	@ResponseBody
	public Map<String, Object> delete() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int res = service.deleteSmsMasterplateClient(getFormData());
		result.put("statusCode", (res >= 1 ? 200 : 201));
		return result;
	}

	@RequestMapping("updateStatus")
	@ResponseBody
	public Map<String, Object> updateStatus() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		FormData data = getFormData();
		data.put("STATUS", 1);
		int res = service.updateSmsMasterplateStatusService(data);
		result.put("statusCode", (res >= 1 ? 200 : 201));
		return result;
	}

	@RequestMapping("getspnameList")
	@ResponseBody
	public Map<String, Object> getspnameList(HttpSession session) throws Exception {
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = getFormData();
		formdata.put("userId", userid);
		if (checkLandType(session)) {
			formdata.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}

		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			formdata.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		
		List<Map<String, Object>> lists = (List<Map<String, Object>>) spinfoService
				.getYESSpinfoByUserIDService(formdata);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lists", lists);
		map.put("maxlength", session.getAttribute(Const.MAXCONTENTLENGTH));
		return map;
	}

	@RequestMapping("addTrialMaster")
	@ResponseBody
	public Map<String, Object> addTrialMaster(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = getFormData();
		formdata.put("userId", userid);
		result.put("spinfoList", spinfoService.getOKSpinfoByUserIDService(formdata));
		result.put("ontrialList", service.getOntrialList());
		return result;
	}

	@RequestMapping("getTrialMasterByid")
	@ResponseBody
	public Map<String, Object> getTrialMasterByid() throws Exception {
		return service.getOntrialByID(getFormData());
	}

	// 导出模板
	@RequestMapping("exportMasterplateListToExcel")
	@ResponseBody
	public void exportMasterplateListToExcel(HttpServletResponse response, HttpSession session) throws Exception {
		String[] rowsName = new String[] { "模板标题", "模板内容", "应用名称", "模板状态" };

		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = getFormData();
		formdata.put("userId", userid);
		formdata.put("content", formdata.get("selcontent"));
		if (checkLandType(session)) {
			formdata.put("SP_ID", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}

		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			formdata.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		PageInfo page = new PageInfo();
		page.setLength(1000000);
		page.setPageSize(1000000);
		formdata.put("exportMasterplate", 0);
		page.setFormData(formdata);	
		
		List<Map<String, Object>> data = service.getAllSmsMasterplateClientByPage(page);
		for (Map<String, Object> map : data) {
			Object[] objs = new Object[4];
			objs[0] = map.get("title");
			objs[1] = map.get("content");
			objs[2] = map.get("sp_name");
			Integer status = (Integer) map.get("status");
			String object3 = "";
			if (status == 0) {
				object3 = "审核通过";
			} else if (status == 1) {
				object3 = "审核中";
			} else if (status == 2) {
				object3 = "审核驳回";
			} else if (status == 3) {
				object3 = "未提交审核";
			}
			objs[3] = object3;
			dataList.add(objs);
		}
		ExportExcel ex = new ExportExcel(rowsName, dataList);
		ex.export(response, "模板列表");
	}

	// 下载模板
	@SuppressWarnings("unchecked")
	@RequestMapping("downloadTemplate")
	@ResponseBody
	public void downloadTemplate(HttpSession session, HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		String type = request.getParameter("type");
		if (type.equals("excel")) {
			String path = properties.getService_nginx_url() + "templete/spinfoModel.xls";
			FileManageUtils.exportFile(response, path, "导入模板.xls");
		}

	}

	// 批量导入应用模板
	@RequestMapping(value = "uploadspinfoModal", method = RequestMethod.POST)
	@ResponseBody
	public String updateImage(@RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String impotspidname, HttpServletRequest request, HttpSession session)
					throws Exception {
		Map<String, Object> answer = new HashMap<String, Object>();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		ObjectMapper mapper = new ObjectMapper();
		if (file == null || file.getSize() / 1024 / 1024 > 10) {
			answer.put("returnCode", 203);
			answer.put("error", "文件不存在");
			return mapper.writeValueAsString(answer);
		}
		Integer impotmodal = 0;
		if (file.getOriginalFilename().endsWith(".xls")) {
			InputStream stream = file.getInputStream();
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(stream);
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			if (hssfSheet == null) {
				answer.put("returnCode", 203);
				answer.put("error", "导入的文件中没有数据");
				return mapper.writeValueAsString(answer);
			}
			FormData formdata = null;
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				formdata = new FormData();
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				HSSFCell modalTitle = hssfRow.getCell(0);
				if (modalTitle == null) {
					continue;
				}

				HSSFCell modalContent = hssfRow.getCell(1);
				if (modalContent == null) {
					continue;
				}
				formdata.put("spid", impotspidname);
				formdata.put("title", getValue(modalTitle));
				formdata.put("content", getValue(modalContent));

				int ontrialnum = service.validateontrialMessageService(formdata);
				if (ontrialnum > 0) {
					continue;
				}
				formdata.put("userId", userid);
				int res = service.saveonTrialSmsMasterplateClient(formdata);
				impotmodal += res;
			}
			stream.close();
			FileManageUtils.deleteFiles(PathUtil.getClasspath() + "txtInfo");
			answer.put("returnCode", 200);
			answer.put("error", "导入了模板条数：" + impotmodal + "条");
			return mapper.writeValueAsString(answer);
		} else {
			answer.put("returnCode", 203);
			answer.put("error", "文件格式错误");
			return mapper.writeValueAsString(answer);
		}
	}

	@SuppressWarnings("deprecation")
	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			DecimalFormat df = new DecimalFormat("#");
			return String.valueOf(df.format(hssfCell.getNumericCellValue()));
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

}
