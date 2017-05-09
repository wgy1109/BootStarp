package com.chanzor.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.util.StopWatch;

public class ExportExcel {
	// 显示的导出表的标题
	private String title;
	// 导出表的列名
	private String[] rowName;

	private List<Object[]> dataList = new ArrayList<Object[]>();

	HttpServletResponse response;

	// 构造方法，传入要导出的数据
	public ExportExcel(String[] rowName, List<Object[]> dataList) {
		this.dataList = dataList;
		this.rowName = rowName;
	}
	
	public ExportExcel() {
	}

	/*
	 * 导出数据
	 */
	public void export(HttpServletResponse httpResponse, String fileName)
			throws Exception {
		try {
			StopWatch sw = new StopWatch(fileName); 
			HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
//			int sheetSize = Const.EXPORTEVERYSIZE;
			int sheetSize = 65535;
			int sheetpage = dataList.size()/sheetSize+1;
			for(int k = 0 ; k<sheetpage; k++){
				sw.start(k+"");
			HSSFSheet sheet = workbook.createSheet(); // 创建工作表
			// 产生表格标题行
			HSSFRow rowm = sheet.createRow(0);
			HSSFCell cellTiltle = rowm.createCell((short) 0);

			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
			HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
			HSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象

			// 定义所需列数
			int columnNum = rowName.length;
			HSSFRow rowRowName = sheet.createRow(0); // 在索引2的位置创建行(最顶端的行开始的第二行)

			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				HSSFCell cellRowName = rowRowName.createCell((short) n); // 创建列头对应个数的单元格
				cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
				HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的值
				cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
			}

			// 将查询出的数据设置到sheet对应的单元格中
			for (int i =k * sheetSize; i < (k+1) * sheetSize; i++) {
				if(i>=dataList.size()){
					break;
				}
				Object[] obj = dataList.get(i);// 遍历每个对象
				HSSFRow row = sheet.createRow(i + 1 - (k * sheetSize));// 创建所需的行数

				for (int j = 0; j < obj.length; j++) {
					HSSFCell cell = null; // 设置单元格的数据类型
						cell = row.createCell((short) j,
								(short) HSSFCell.CELL_TYPE_STRING);
						if (!"".equals(obj[j]) && obj[j] != null) {
							cell.setCellValue(obj[j].toString()); // 设置单元格的值
						} else {
							cell.setCellValue("");
					}
					cell.setCellStyle(style); // 设置单元格样式
				}
			}
			// 让列宽随着导出的列长自动适应
			for (int colNum = 0; colNum < columnNum; colNum++) {
				int columnWidth = sheet.getColumnWidth((short) colNum) / 256;
				for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
					HSSFRow currentRow;
					// 当前行未被使用过
					if (sheet.getRow(rowNum) == null) {
						currentRow = sheet.createRow(rowNum);
					} else {
						currentRow = sheet.getRow(rowNum);
					}
					if (currentRow.getCell(colNum) != null) {
						HSSFCell currentCell = currentRow.getCell(colNum);
						if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							if (currentCell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
								continue;
							}
							int length = currentCell.getStringCellValue()
									.getBytes().length;
							if (columnWidth < length) {
								columnWidth = length;
							}
						}
					}
				}
				if (colNum == 0) {
					sheet.setColumnWidth((short) colNum,
							(short) ((short) (columnWidth - 2) * 256));
				} else {
					sheet.setColumnWidth((short) colNum,
							(short) ((short) (columnWidth + 4) * 256));
				}
			}
			sw.stop();
			}
			System.out.println(sw.prettyPrint());
			if (workbook != null) {
				try {
					String fixFileName = "Excel-" + fileName + ".xls";
					String headStr = "attachment; filename=\""
							+ java.net.URLEncoder.encode(fixFileName, "UTF-8")
							+ "\"";
					response = httpResponse;
					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition", headStr);
					OutputStream out = response.getOutputStream();
					workbook.write(out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 列头单元格样式
	 */
	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	/*
	 * 列数据信息单元格样式
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
	
	// SXSSFWorkbook 导出 
	public void exportBySXSSFWorkbook(HttpServletResponse httpResponse, String title, String[] rowsName, List<Map<String, Object>> dataList, String[] key, String fileName){
		List<String> headerList = Arrays.asList(rowsName);
		Workbook wb = null;
		OutputStream out = null;
		try{
		   wb = new SXSSFWorkbook(100); // keep 100 rows in memory,
												// exceeding rows will be
												// flushed to disk
		Sheet sh = wb.createSheet(title);
		Row headerRow = sh.createRow(0);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			String[] ss = StringUtils.split(headerList.get(i), "**", 2);
			if (ss.length==2){
				cell.setCellValue(ss[0]);
				Comment comment = sh.createDrawingPatriarch().createCellComment(
						new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			}else{
				cell.setCellValue(headerList.get(i));
			}
		}
		for (int i = 0; i < headerList.size(); i++) { 
			int colWidth = sh.getColumnWidth(i)*2; 
			colWidth = colWidth < 3000 ? 3000 : colWidth;
			colWidth = colWidth > 25*256 ? 25*256 : colWidth ;
			sh.setColumnWidth(i, colWidth);  
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int rownum = 0; rownum < dataList.size(); rownum++) {
			Map<String, Object> map  = dataList.get(rownum);
			Row row = sh.createRow(rownum+1);
			Cell cell = null;
			for(int k = 0 ; k<key.length ; k++){
				cell = row.createCell(k);
				  if(key[k].indexOf("time")>0 && map.get(key[k]) != null){
					  cell.setCellValue(df.format(map.get(key[k])));
				  }else if(key[k].indexOf("time")>0 && map.get(key[k]) == null){
					  cell.setCellValue("");
				  }else{
					  cell.setCellValue(map.get(key[k])+"");
				  }
			  }
		}
		String fixFileName = "Excel-" + fileName + ".xls";
		String headStr = "attachment; filename=\""
				+ java.net.URLEncoder.encode(fixFileName, "UTF-8")
				+ "\"";
		response = httpResponse;
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", headStr);
		out = response.getOutputStream();
		wb.write(out);
		dataList.clear();
		dataList = null;
		wb = null;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(out != null){
					out.close();
					out = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
