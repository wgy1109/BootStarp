package com.chanzor.controller.blacklist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.BlacklistService;
import com.chanzor.service.RedisService;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.impl.WhitelistServiceImpl;
import com.chanzor.sms.redis.RedisMessage;
import com.chanzor.util.Const;
import com.chanzor.util.DateHelper;
import com.chanzor.util.ExportExcel;
import com.chanzor.util.FormData;
import com.chanzor.util.Tools;

/**
 * 白名单信息维护
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("blacklist")
public class BlacklistController extends BaseController {
	public int layertype = 0;
	@Autowired
	private BlacklistService service;
	@Autowired
	private RedisService redisService;
	@Autowired
	private SpinfoService spInfoService;

	@RequestMapping("")
	public ModelAndView list(PageInfo page, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("blacklist/blacklist");
		FormData appForm = new FormData();
		appForm.put("userId", getFormData().get("userId"));
		if (checkLandType(session)) {
			appForm.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			appForm.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		
		List<Map<String, Object>> appList = service.getAppList(appForm);
		mv.addObject("appList", appList);
		return mv;
	}

	/**
	 * 初始化黑名单列表数据
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page, HttpSession session) throws Exception {
		FormData formData = getFormData();
		formData.put("userid", formData.get("userId"));
		if (checkLandType(session)) {
			formData.put("spid", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			formData.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		page.setFormData(formData);
		List<Map<String, Object>> data = service.getAllBlacklistPage(page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("page", page);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		result.put("data", data);

		return result;
	}

	/**
	 * 跳转到编辑黑名单页面
	 * 
	 * @param id
	 * @param operate
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showInfo")
	@ResponseBody
	public Map<String, Object> showInfo(@RequestParam(required = false) String id, HttpSession session)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (id != null && !"".equals(id)) {
			FormData data = new FormData();
			data.put("id", id);
			Map<String, Object> blacklistById = service.getBlacklistById(data);
			result.put("data", blacklistById);
		}
		return result;
	}

	/**
	 * 修改黑名单信息
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> save(HttpSession session) throws Exception {
		FormData formData = getFormData();
		Map<String, Object> result = new HashMap<String, Object>();
		// 手机号验证
		if (!spInfoService.isMobile(formData.getString("mdn"))) {
			result.put("statusCode", 251);
			return result;
		}
		// 手机号重复验证
		List<Map<String, Object>> whitelistByMdn = service.getBlacklistByMdn(formData);
		if (whitelistByMdn != null && whitelistByMdn.size() != 0) {
			result.put("statusCode", 205);
			return result;
		}
		formData.put("createtime", new Date());
		Map<String, Object> oldMap = null;
		if (formData.get("id") != null && Tools.notEmpty(formData.get("id").toString())) {
			oldMap = service.getBlacklistById(formData);
		}
		int res = service.saveBlacklist(formData);
		redisService.editBlackList(oldMap, formData);
		redisService.sendBlackInfo(RedisMessage.TYPE_USER,
				oldMap == null ? RedisMessage.EVENTTYPE_ADD : RedisMessage.EVENTTYPE_EDIT,
				Integer.valueOf(formData.getString("id")), formData.get("mdn").toString());
		result.put("statusCode", (res >= 1 ? 200 : 201));
		return result;
	}

	/**
	 * 删除黑名单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("del")
	@ResponseBody
	public Map<String, Object> delBlacklist(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		FormData formData = getFormData();
		Map<String, Object> blackMap = service.getBlacklistById(formData);
		int res = service.deleteBlacklist(formData);
		if (res >= 1) {
			formData.put("mdn", blackMap.get("mdn"));
			formData.put("spid", blackMap.get("target_id"));
			redisService.delBlackList(formData);
			redisService.sendBlackInfo(RedisMessage.TYPE_USER, RedisMessage.EVENTTYPE_DELETE,
					Integer.valueOf(formData.get("id").toString()), formData.get("mdn").getClass().toGenericString());
		}
		result.put("statusCode", (res >= 1 ? 200 : 201));

		return result;
	}
	
	// 导出短信明细
	@RequestMapping("exportBlackListToExcel")
	@ResponseBody
	public void exportMtListToExcel(HttpServletResponse response, HttpSession session) throws Exception {
		logger.info("导出开始时刻："+new Date());
		layertype = 1;
		String[] rowsName = new String[] { "手机号码", "生效应用", "创建时间", "备注" };
		FormData f = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		f.put("userid", userid);
		if (checkLandType(session)) {
			f.put("spId", ((SpInfo) session.getAttribute(Const.SESSIONSPINFO)).getSpid());
		}
		Map<String, Object> currUser = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		if ((Integer) currUser.get("is_sub_account") == 1) { // 是子账号，过滤非管辖的应用
			f.put("subAccountAppIds", (String) session.getAttribute(Const.APPIDSSTR));
		}
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		int pagesize = 1000;				//每次从数据库获取信息数量
		PageInfo page = new PageInfo();
		page.setFormData(f);
		int beginpage = 1;
		while(true){
	        page.setStart((beginpage-1)*pagesize);
			page.setLength(pagesize);
	        List<Map<String, Object>> resultMap = service.getBlackListClientP(page);
            data.addAll(resultMap);
            if(resultMap.size() < pagesize){
        		 	break;
        	  }
            beginpage++;
            if(beginpage/66==1){
            	break;
            }
        }
		logger.info("获取数据库信息结束时刻："+new Date());

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Map<String, Object> map : data) {
			Object[] objs = new Object[rowsName.length];
			int i = 0;
			objs[i++] = map.get("mdn");
			objs[i++] = map.get("sp_name");
			objs[i++] = (map.get("create_time") != null) ? df.format(map.get("create_time")) : "";
			objs[i++] = map.get("descption");
			dataList.add(objs);
		}
		ExportExcel ex = new ExportExcel(rowsName, dataList);
		ex.export(response, "黑名单列表");
		layertype = 0;
	}
	
	@RequestMapping("closetype")
	@ResponseBody
	public String closetype() throws Exception {
		return layertype + "";
	}
	
}
