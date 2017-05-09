package com.chanzor.controller.smsInsideLetter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.service.InsideLetterService;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;

//发送统计列表
@Controller
@RequestMapping("InsideLetter")
@SuppressWarnings("unchecked")
public class SmsInsideLetterController extends BaseController {

	@Autowired
	private InsideLetterService insideLetterService;

	@RequestMapping(value = "index")
	public ModelAndView findInsideLetterIndex(PageInfo page, HttpSession session)
			throws NumberFormatException, Exception {
		ModelAndView mod = new ModelAndView();
		mod.setViewName("insideLetter/index");
		return mod;
	}

	@RequestMapping(value = "insideList")
	public @ResponseBody Map<String, Object> letterList(PageInfo page, HttpSession session)
			throws NumberFormatException, Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		FormData formData = getFormData();
		formData.put("userId", Integer.valueOf(userInfo.get("id").toString()));
		page.setFormData(formData);
		List<Map<String, Object>> list = insideLetterService.findLetterListPage(page);
		map.put("letter", list);
		map.put("page", page);
		map.put("recordsTotal", page.getTotalSize());
		map.put("recordsFiltered", page.getTotalSize());
		return map;
	}

	@RequestMapping(value = "delLetter")
	public @ResponseBody String delLetter(@RequestParam String ids) throws NumberFormatException, Exception {
		String returnCode = "";
		if (ids == null) {
			returnCode = "400";
			return returnCode;
		}
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			insideLetterService.delLetter(Integer.valueOf(id[i]));
		}
		return "200";
	}

	@RequestMapping(value = "findUnReadLetter")
	public @ResponseBody Map<String, Object> fundUnReadLetter(HttpSession session)
			throws NumberFormatException, Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		Integer unReadLetter = insideLetterService.findUnReadLetter(Integer.valueOf(userInfo.get("id").toString()));
		List<Map<String, Object>> topLetter = insideLetterService
				.findTopLteerByUser(Integer.valueOf(userInfo.get("id").toString()));
		map.put("unRead", unReadLetter);
		map.put("letterInfo", topLetter);
		return map;
	}

	@RequestMapping(value = "updUnRead")
	public @ResponseBody String updUnRead(HttpSession session, Integer id) throws NumberFormatException, Exception {
		insideLetterService.updUnReadLetter(id);
		Map<String, Object> map = insideLetterService.getLetterByid(id);
		return map.get("content").toString();
	}
	
	@RequestMapping(value = "checkAllLetter")
	public @ResponseBody String checkAllLetter(HttpSession session, Integer id) throws NumberFormatException, Exception {
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		insideLetterService.checkAllLetter(Integer.valueOf(userInfo.get("id").toString()));
		return "success";
	}
}