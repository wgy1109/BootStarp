package com.chanzor.controller.smsVoiceTemplate;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chanzor.common.FileConvertor;
import com.chanzor.controller.base.BaseController;
import com.chanzor.entity.PageInfo;
import com.chanzor.entity.SpInfo;
import com.chanzor.service.SpinfoService;
import com.chanzor.service.TextVoiceService;
import com.chanzor.sms.common.utils.SequenceGenerator;
import com.chanzor.util.Const;
import com.chanzor.util.FormData;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;

@Controller
@RequestMapping("textVoice")
public class SmsVoiceTemplateController extends BaseController {
	

	@Autowired
	private TextVoiceService textVoiceService;
	
	@Autowired
	private SpinfoService spinfoService;
	
	@Autowired
	private SequenceGenerator voiceIdGenerator;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("")
	public ModelAndView list(PageInfo page, HttpSession session,Model model) throws Exception {
		ModelAndView mv = new ModelAndView("textVoice/textVoiceList");
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		FormData formdata = new FormData();
		formdata.put("userId", userid);
		Map<String,Object> currUser = (Map<String,Object>)session.getAttribute(Const.SESSION_USER);
		if((Integer)currUser.get("is_sub_account") == 1){  //是子账号，过滤非管辖的应用
			formdata.put("subAccountAppIds",(String)session.getAttribute(Const.APPIDSSTR));
		}
		
		List<Map<String, Object>> lists = (List<Map<String, Object>>) spinfoService
				.getAuditPassVoiceSpinfoByUserID(formdata);
		
		model.addAttribute("appList", lists);
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("load")
	@ResponseBody
	public Map<String, Object> load(PageInfo page, HttpSession session) throws Exception {
		FormData data = getFormData();
		if(!data.get("STATUS").equals("")){
			data.put("STATUS", Integer.parseInt((String)data.get("STATUS")));
		}
		if(!data.get("SPID").equals("")){
			data.put("SPID", Integer.parseInt((String)data.get("SPID")));
		}
		
		Integer userid = (Integer) ((Map<String, Object>) session.getAttribute(Const.SESSION_USER)).get("id");
		data.put("userId", userid);
		Map<String, Object> result = new HashMap<String, Object>();
		page.setFormData(data);
		result.put("page", page);
		List<Map<String, Object>> dataList = textVoiceService.getTextVoiceList(page);
		List<SpInfo> appList = spinfoService.findSpListByUserId(userid);
		Map<Integer,String> spMap = new HashMap<Integer,String>();
		for(SpInfo spInfo : appList){
			spMap.put(spInfo.getSpid(), spInfo.getSp_name());
		}
		
		if(dataList != null && dataList.size() > 0){
			for(Map<String,Object> map : dataList){
				map.put("nginxUrl", properties.getNginx_url());
				map.put("spName", spMap.get((Integer)map.get("sp_id")));
			}
		}
		result.put("data", dataList);
		result.put("recordsTotal", page.getTotalSize());
		result.put("recordsFiltered", page.getTotalSize());
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> save(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		
		FormData data = getFormData();
	
		data.put("type", 0);
		data.put("user_id", (Integer)userInfo.get("id"));
		Date currDate = new Date();
		data.put("create_time", currDate);
		data.put("update_time", currDate);
		data.put("status", 3); //3:未提交审核  1：审核中  0:审核通过  2：审核驳回
		data.put("speed", data.getString("speed") == null ? 50 : Integer.parseInt(data.getString("speed")));
		
		data.put("pitch", 50);
		data.put("volume", 50);
		data.put("sp_id", Integer.parseInt((String)data.get("sp_id")));
		
		int res = textVoiceService.saveTextVoice(data);
		result.put("statusCode", (res == 1 ? 200 : 201));
		return result;
	}

	@RequestMapping(value = "/getTextVoice")
	@ResponseBody
	public  Map<String, Object> getTextVoice(@RequestParam Integer id) throws Exception {
		FormData data = getFormData();
		Map<String,Object> dataMap = textVoiceService.getTextVoiceById(data);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("textVoice", dataMap);
		return resultMap;
	}
	
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String, Object> update(HttpSession session) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		FormData data = getFormData();
	
		Date currDate = new Date();
		data.put("update_time", currDate);
		data.put("sp_id", Integer.parseInt((String)data.get("sp_id")));
		data.put("id", Integer.parseInt((String)data.get("id")));
		data.put("speed", data.getString("speed") == null ? 50 : Integer.parseInt(data.getString("speed")));
		
		int res = textVoiceService.updateTextVoice(data);
		result.put("statusCode", (res == 1 ? 200 : 201));
		return result;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int res = textVoiceService.deleteTextVoice(getFormData());
		result.put("statusCode", (res == 1 ? 200 : 201));
		return result;
	}

	@RequestMapping("commitAudit")
	@ResponseBody
	public Map<String, Object> commitAudit() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int res = textVoiceService.commitTextVoice(getFormData());
		result.put("statusCode", (res == 1 ? 200 : 201));
		return result;
	}
	
	
	
	@RequestMapping("textConvertVoice")
	@ResponseBody
	public Map<String, Object> textConvertVoice(HttpServletRequest request,HttpSession session) throws Exception {
		//Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(Const.SESSION_USER);
		Map<String, Object> result = new HashMap<String, Object>();
		FormData data = getFormData();
		String content = data.getString("content");
		String voiceName = data.getString("voice_name");
		String speed = data.getString("speed");
		Object obj = new Object();
		if(content != null && !"".equals(content) && content.indexOf("@") != -1){
		    content = content.replaceAll("@", "");
		}
		SpeechSynthesizer speechSynthesizer = SpeechSynthesizer
	                .createSynthesizer();
		 // 设置发音人
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, voiceName);
        // 设置语速，范围0~100
        speechSynthesizer.setParameter(SpeechConstant.SPEED, speed==null?"50":speed);
     // 设置语调，范围0~100
        speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
     // 设置音量，范围0~100
        speechSynthesizer.setParameter(SpeechConstant.VOLUME, "50");
        speechSynthesizer.setParameter(SpeechConstant.SAMPLE_RATE, "96000");
        
        String fileId = voiceIdGenerator.get();
        String fileLoc = properties.getFile_loc();
		//String middleFileUrl = "voice/" + userInfo.get("id").toString() + "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String middleFileUrl = "voice/" + "tempFile/";
		File filePath = new File(fileLoc + middleFileUrl);
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		
		//非常关键，必需有如下语句，才可合成成功
		SpeechUtility.createUtility("appid=" + fileId);
		
        speechSynthesizer.synthesizeToUri(content, String.format(filePath.toString() +"/%s.pcm", fileId),
        		  new SynthesizeToUriListener() {
            public void onBufferProgress(int progress) {

            }
            public void onSynthesizeCompleted(String uri, SpeechError error) {
                try {
                	logger.error(error);
                	//logger.info("语音合成文件：{} {} ",uri,error);
                    String destFile = String.format(filePath.toString() + "/%s.wav", fileId);
                    FileConvertor.convertAudioFiles(uri, destFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally{
                    synchronized(obj){
                        obj.notify();
                    }
                }
            }
        });
        synchronized(obj){	
            try {
                obj.wait(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String destFile = String.format(filePath.toString() + "/%s.wav", fileId);
        File file = new File(destFile);
        String voiceFullPath = "";
        if(file.exists()){
        	voiceFullPath = properties.getFile_url() + middleFileUrl + fileId + ".wav";
        }
        result.put("voiceFullPath", voiceFullPath);
        return result;
	}
}
