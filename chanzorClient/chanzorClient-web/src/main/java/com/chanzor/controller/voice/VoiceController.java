package com.chanzor.controller.voice;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.VoiceService;
import com.chanzor.sms.common.utils.SequenceGenerator;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;

@Controller
@RequestMapping("voice")
public class VoiceController extends BaseController {

	@Autowired
	private SequenceGenerator voiceIdGenerator;
	
	@Autowired
	private VoiceService voiceService;
	
	@Autowired
	private SpinfoService spinfoService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("")
	public ModelAndView list(PageInfo page, HttpSession session,Model model) throws Exception {
		ModelAndView mv = new ModelAndView("voice/voiceList");
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = new FormData();
		formdata.put("userId", userid);
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			formdata.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		List<Map<String, Object>> lists = (List<Map<String, Object>>) spinfoService
				.getAuditPassVoiceSpinfoByUserID(formdata);
		
		model.addAttribute("appList", lists);;
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page, HttpSession session) throws Exception {
		FormData data = getFormData();
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		data.put("userId", userid);
		if(data.getString("status") != null && (!"".equals(data.getString("status")))){
			data.put("status", Integer.parseInt(data.getString("status")));
		}
		//此处查询仅仅包含上传的语音文件，type=1
		data.put("type", 1);
		if(data.getString("spId") != null && (!"".equals(data.getString("spId")))){
			data.put("spId", Integer.parseInt(data.getString("spId")));
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		page.setFormData(data);
		result.put("page", page);
		List<Map<String, Object>> dataList = voiceService.getVoiceList(page);
		if(dataList != null && dataList.size() > 0){
			for(Map<String,Object> map : dataList){
				map.put("fileUrl", properties.getFile_url());
				map.put("fileShortPath", ((String)map.get("file_name")).replace(properties.getFile_loc(),""));
			}
		}
		result.put("data", dataList);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		
		
		return result;
	}
	
	/**
	 * 上传语音文件
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping (value="uploadVoiceFile")
	@ResponseBody
	public  String uploadVoiceFile (MultipartFile file,HttpServletRequest request,HttpSession session)throws Exception {
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		//logger.info("file.size()=======" + file.getSize());
		
		if(file == null || file.getSize()/1024/1024 > 1){   //限制1M大小
			return "fileSizeOutOf";
		}
		
		String spId = request.getParameter("spId");
		//logger.info("spId========" +spId);
		
		SpInfo spInfo = spinfoService.getSpinfoById(Integer.parseInt(spId));
		logger.info("accountName========" +spInfo.getUsername());
		
	    String fileId = voiceIdGenerator.get();
		
		String voiceStorageLoc = properties.getFile_loc();
		String middleFileUrl = "voice/" + spInfo.getUsername() + "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		File filePath = new File(voiceStorageLoc + middleFileUrl);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		//保存的文件名称为
		String fileName = filePath + "/" + fileId + ".wav";
		
		byte[] bytes = file.getBytes();  
		BufferedOutputStream bufferedOutputStream = null;
		try{
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));  
			bufferedOutputStream.write(bytes);  
		}catch(Exception e){
			logger.error("上传文件出错，{}",e);
		}finally{
			if(bufferedOutputStream != null){
				bufferedOutputStream.close();
			}
		}
		
		
		int trackLength = 0;
	    try{
	      	AudioFile audioFile = AudioFileIO.read(new File(fileName));
	      	trackLength = audioFile.getAudioHeader().getTrackLength();
	    }catch(Exception e){
	    	logger.error("读取上传文件出错，{}",e);
	    }
		
		//返回文件的属性信息
	
//		returnMap.put("fileId",fileId);  //主键，同时也是文件名称
//		returnMap.put("fileSize", bytes.length);
//		returnMap.put("trackLength", trackLength);
//		returnMap.put("fileName", middleFileUrl + "/" + fileId + ".wav");   //文件相对路径部分，含文件名
	    
	    
	    String returnStr = fileId + "|" +  bytes.length +"|" +trackLength + "|" + middleFileUrl + "/" +fileId + ".wav"+"|"+properties.getFile_url()+"|" + voiceStorageLoc+middleFileUrl + "/" +fileId + ".wav";
	
		return returnStr;
		
	}
	
	
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> save(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		
		FormData data = getFormData();
		SpInfo spInfo = spinfoService.getSpinfoById(Integer.parseInt(data.getString("sp_id")));
		 
		data.put("type", Integer.parseInt(data.getString("type")));
		data.put("sp_id", Integer.parseInt(data.getString("sp_id")));
		data.put("user_id", (Integer)userInfo.get("id"));
		data.put("file_size", Integer.parseInt(data.getString("file_size")));
		data.put("track_length", Integer.parseInt(data.getString("track_length")));
		data.put("create_time", new Date());
		data.put("sp_account", spInfo.getUsername());
		data.put("sp_name", spInfo.getSp_name());
		
		int res = voiceService.saveVoice(data);
		result.put("statusCode", (res == 1 ? 200 : 201));
		return result;
	}

	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int res = voiceService.deleteVoice( getFormData());
		result.put("statusCode", (res == 1 ? 200 : 201));
		return result;
	}

	@RequestMapping("getVoice")
	@ResponseBody
	public Map<String, Object> getVoice(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String,Object> data = voiceService.getVoiceById(getFormData()); 
		result.put("data",data);
		return result;
	}
	
	
}
