package com.chanzor.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chanzor.entity.PageInfo;
import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.service.RedisService;
import com.chanzor.service.SmsSendTaskClientService;
import com.chanzor.util.FormData;
import com.chanzor.util.PropertiesConfig;

@Service("SmsSendTaskClientService")
@SuppressWarnings("unchecked")
public class SmsSendTaskClientServiceImpl implements SmsSendTaskClientService {

	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;
	@Autowired
	public PropertiesConfig properties;
	@Autowired
	public RedisService redisService;

	// 发送列表
	public List<Map<String, Object>> getAllSmsSendTaskClientByPage(PageInfo page) throws Exception {
		Map<String, Object> countAll = (Map<String, Object>) daoSupport
				.findForObject("SmsSendTaskClientMapper.getAllSmsSendTaskClientCount", page);
		page.setTotalSize(Integer.parseInt(countAll.get("count").toString()));
		return (List<Map<String, Object>>) daoSupport.findForList("SmsSendTaskClientMapper.getAllSmsSendTaskClientP",
				page);
	}

	@Override
	public List<Map<String, Object>> getSendStatisticsServiceByPage(PageInfo page) throws Exception {
		String sql = getSql(page);
		page.getFormData().put("sql", sql);
		return (List<Map<String, Object>>) daoSupport.findForList("SmsSendTaskClientMapper.getSendStatisticsByPage",
				page);
	}

	public String getSql(PageInfo page) {
		FormData formdata = page.getFormData();
		StringBuffer stringbuffer = new StringBuffer();
		if (formdata.get("queryStartTime") != null && !"".equals(formdata.get("queryStartTime"))) {
			stringbuffer.append(" and s.submit_date  >= '" + formdata.get("queryStartTime") + "'::date ");
		}
		if (formdata.get("queryEndTime") != null && !"".equals(formdata.get("queryEndTime"))) {
			stringbuffer.append(" and s.submit_date  < '" + formdata.get("queryEndTime") + "'::date ");
		}
		String sql = "";
		String groupbys = "";
		String orderbys = "";
		StringBuffer groupby = new StringBuffer();
		StringBuffer selectmessage = new StringBuffer();
		if ("2".equals(formdata.get("timegroup"))) {
			selectmessage.append("s.submit_date msgTime,");
			groupby.append("s.submit_date,");
		} else if ("1".equals(formdata.get("timegroup"))) {
			selectmessage.append("to_char(s.submit_date,'yyyy-MM')  msgTime,");
			groupby.append("msgTime,");
		}
		if (formdata.get("spId") != null && !"".equals(formdata.get("spId")) && !"-1".equals(formdata.get("spId"))) {
			if (!"0".equals(formdata.get("spId"))) {
				stringbuffer.append(" and s.user_sp_id = " + formdata.get("spId"));
			}
			selectmessage.append("user_sp_name,");
			groupby.append("user_sp_name,");
		}
		if (formdata.get("subAccountAppIds") != null) {
			stringbuffer.append(" and s.user_sp_id in " + formdata.get("subAccountAppIds").toString());
			selectmessage.append("user_sp_name,");
			groupby.append("user_sp_name,");
		}
		if ("".equals(formdata.get("timegroup")) && "0".equals(formdata.get("spId"))) {
			sql = "select  COALESCE(sum(s.submit_count),0) ALLNUM, COALESCE(sum(s.sended_count),0) SENDEDNUM, COALESCE(sum(s.success_count),0) ALLYES, COALESCE(sum(s.fail_count),0) ALLNO, COALESCE(sum(s.unknown_count),0) OTHERNUM "
					+ "from sms_send_statistics s where 1 = 1 " + stringbuffer.toString();
			// +" and user_sp_id in (select spinfo.id from sms_sp_info spinfo
			// where spinfo.user_id = "+ formdata.get("ID") +" ) ";
			if (formdata.get("subAccountAppIds") != null) {
				sql += " and user_sp_id in " + formdata.get("subAccountAppIds").toString();
			} else {
				sql += " and user_sp_id in (select spinfo.id from sms_sp_info spinfo where spinfo.user_id = "
						+ formdata.get("ID") + " ) ";
			}
		} else {
			groupbys = groupby.substring(0, groupby.length() - 1);
			orderbys = groupbys;
			if (orderbys.indexOf("s.submit_date") >= 0) {
				orderbys = orderbys.replaceAll("s.submit_date", "s.submit_date desc");
			} else if (orderbys.indexOf("msgTime") >= 0) {
				orderbys = orderbys.replaceAll("msgTime", "msgTime desc");
			}
			sql = "select " + selectmessage
					+ "  COALESCE(sum(s.submit_count),0) ALLNUM, COALESCE(sum(s.sended_count),0) SENDEDNUM, COALESCE(sum(s.success_count),0) ALLYES, COALESCE(sum(s.fail_count),0) ALLNO, COALESCE(sum(s.unknown_count),0) OTHERNUM "
					+ "from sms_send_statistics s where 1 = 1 " + stringbuffer.toString();
			// +" and user_sp_id in (select spinfo.id from sms_sp_info spinfo
			// where spinfo.user_id = "+ formdata.get("ID") +" ) GROUP BY "+
			// groupbys + " order by " + orderbys;
			if (formdata.get("subAccountAppIds") != null) {
				sql += " and user_sp_id in " + formdata.get("subAccountAppIds").toString() + " GROUP BY " + groupbys
						+ " order by " + orderbys;
			} else {
				sql += " and user_sp_id in (select spinfo.id from sms_sp_info spinfo where spinfo.user_id = "
						+ formdata.get("ID") + " ) GROUP BY " + groupbys + " order by " + orderbys;
			}
		}
		return sql;
	}

	@Override
	public List<Map<String, Object>> getAllSenstiveWord(Integer spId) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SmsSendTaskClientMapper.getAllSenstiveWord", spId);
	}

	@Override
	public List<Map<String, Object>> getSmsMtListByPage(PageInfo page) throws Exception {
		Map<String, Object> countAll = (Map<String, Object>) daoSupport
				.findForObject("SmsSendTaskClientMapper.getSmsMtListClientCount", page);
		page.setTotalSize(Integer.parseInt(countAll.get("count").toString()));
		return (List<Map<String, Object>>) daoSupport.findForList("SmsSendTaskClientMapper.getSmsMtListClientP", page);
	}

	@Override
	public List<Map<String, Object>> getSmsMtList(PageInfo page) throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList("SmsSendTaskClientMapper.getSmsMtListClientP", page);
	}

	@SuppressWarnings("resource")
	@Override
	public Map<String, Object> getExcelAsFile(FormData formData, String file, String content, Integer phoneIndex,
			String signature) throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> contentList = new ArrayList<String>();
		Workbook wb = null;
		File fileInfo = new File(file);
		String fileName = fileInfo.getName();
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!fileInfo.exists()) {
			resultMap.put("status", "205");
			resultMap.put("msg", "请重新导入文件");
			return resultMap;
		}
		InputStream stream = new FileInputStream(file);
		if (prefix.equals("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (prefix.equals("xlsx")) {
			wb = new XSSFWorkbook(stream);
		} else {
			resultMap.put("status", "500");
			resultMap.put("msg", "导入文件格式不对");
			return resultMap;
		}
		// 3.得到Excel工作表对象
		Sheet sheet = wb.getSheetAt(0);
		// 总行数
		int trLength = sheet.getLastRowNum();
		if (trLength > Integer.valueOf(properties.getCustomized_message_size())) {
			if (fileInfo.isFile() && fileInfo.exists()) {
				fileInfo.delete();
			}
			resultMap.put("status", "205");
			resultMap.put("msg", "导入内容最多为" + properties.getCustomized_message_size() + "行");
			return resultMap;
		}
		// 4.得到Excel工作表的行
		Row row = sheet.getRow(0);
		// 总列数
		Cell cell = row.getCell((short) 1);
		for (int i = 1; i <= trLength; i++) {
			// 得到Excel工作表的行
			String fixContent = content;
			Row row1 = sheet.getRow(i);
			// if (row1 == null) {
			// continue;
			// }
			Cell phoneCell = row1.getCell(phoneIndex - 1);
			Pattern pattern = Pattern.compile("\\[\\([A-Z]\\)\\]");
			Matcher matcher = pattern.matcher(fixContent);
			List<String> list = new ArrayList<String>();
			while (matcher.find()) {
				list.add(matcher.group());
			}
			HashSet<String> h = new HashSet<String>(list);
			list.clear();
			list.addAll(h);
			for (String regx : list) {
				Integer index = formatContent(regx);
				Cell cell1 = row1.getCell(index - 1);
				fixContent = fixContent.replace(regx, getCellValue(cell1));
			}
			if (!content.startsWith("【", 0) && !content.endsWith("】")) {
				if (StringUtils.isNotBlank(signature)) {
					fixContent += signature;
					content += signature;
				} else {
					fixContent += "【畅卓科技】";
					content += "【畅卓科技】";
				}
			}
			if (StringUtils.isNotBlank(getCellValue(phoneCell))) {
				if (getCellValue(phoneCell).length() != 11) {
					resultMap.put("msg", "Excel第" + i + "行手机号码格式不正确，请修改后重新导入");
					resultMap.put("status", "500");
					return resultMap;
				}
				fixContent = getCellValue(phoneCell) + " " + fixContent;
				contentList.add(fixContent);
			}
		}
		if (fileInfo.isFile() && fileInfo.exists()) {
			fileInfo.delete();
		}
		if (!content.startsWith("【", 0) && !content.endsWith("】")) {
			if (StringUtils.isNotBlank(signature)) {
				content += signature;
			} else {
				content += "【畅卓科技】";
			}
		}
		List<String> listWithoutDup = new ArrayList<String>(new HashSet<String>(contentList));
		String key = redisService.saveCustomizedMessageInRedis(formData, listWithoutDup);
		resultMap.put("content", content);
		resultMap.put("key", key);
		resultMap.put("status", "200");
		resultMap.put("phoneSize", contentList == null ? 0 : contentList.size());
		if (contentList.size() >= 3) {
			resultMap.put("contentList", contentList.subList(0, 3));
		} else {
			resultMap.put("contentList", contentList);
		}
		return resultMap;
	}

	public String getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue().trim();
		case Cell.CELL_TYPE_NUMERIC:
			HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
			String cellFormatted = dataFormatter.formatCellValue(cell);
			return cellFormatted;
		default:
			return cell.getStringCellValue().trim();
		}

	}

	public int formatContent(String index) {
		switch (index) {
		case "[(A)]":
			return 1;
		case "[(B)]":
			return 2;
		case "[(C)]":
			return 3;
		case "[(D)]":
			return 4;
		case "[(E)]":
			return 5;
		case "[(F)]":
			return 6;
		case "[(G)]":
			return 7;
		case "[(H)]":
			return 8;
		case "[(I)]":
			return 9;
		case "[(J)]":
			return 10;
		case "[(K)]":
			return 11;
		case "[(L)]":
			return 12;
		case "[(M)]":
			return 13;
		case "[(N)]":
			return 14;
		case "[(O)]":
			return 15;
		case "[(P)]":
			return 16;
		case "[(Q)]":
			return 17;
		case "[(R)]":
			return 18;
		case "[(S)]":
			return 19;
		default:
			return 1;
		}
	}

	// 定时短信发送列表
	public List<Map<String, Object>> getAllSmsTimingClientByPage(PageInfo page) throws Exception {
		Map<String, Object> countAll = (Map<String, Object>) daoSupport
				.findForObject("SmsSendTaskClientMapper.getAllSmsTimingClientCount", page);
		page.setTotalSize(Integer.parseInt(countAll.get("count").toString()));
		return (List<Map<String, Object>>) daoSupport.findForList("SmsSendTaskClientMapper.getAllSmsTimingClientP",
				page);
	}
}
