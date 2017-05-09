package com.chanzor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("unused")
public class ExcelUtil {
	private static Logger logger = Logger.getLogger(ExcelUtil.class);

	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public ExcelUtil(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public ExcelUtil(HSSFWorkbook workbook, HSSFSheet sheet) {
		super();
		this.workbook = workbook;
		this.sheet = sheet;
	}

	/**
	 * 创建通用的Excel带标题行信息00000
	 * 
	 * @param workbook
	 *            如果为空 则没有样式
	 * @param sheet
	 *            (创建sheet)
	 * @param rowNO
	 *            报表的单行行号(创建第几行)
	 * @param rowHeight
	 *            报表的单行行高
	 * @param colNum
	 *            报表的总列数 (合并)
	 * @param fontCaption
	 *            报表行中显示的字符
	 */
	public void createExcelRow(HSSFWorkbook workbook, HSSFSheet sheet, int rowNO, int rowHeight, int colNum,
			String fontCaption) {
		createExcelRow(workbook, sheet, rowNO, -1, colNum, fontCaption, -1, null, null);
	}

	/**
	 * 创建通用的Excel行信息000000
	 * 
	 * @param workbook
	 *            如果为空 则没有样式
	 * @param sheet
	 *            (创建sheet)
	 * @param rowNO
	 *            报表的单行行号(创建第几行)
	 * @param rowHeight
	 *            报表的单行行高
	 * @param colNum
	 *            报表的总列数 (合并)
	 * @param fontCaption
	 *            报表行中显示的字符
	 * @param fontSize
	 *            字体的大小 (字体大小 默认 00)
	 * @param fontWeight
	 *            报表表头显示的字符
	 * @param align
	 *            字体水平位置 (center中间 right右 left左)
	 * @param colNum
	 *            报表的列数
	 */
	public void createExcelRow(HSSFWorkbook workbook, HSSFSheet sheet, int rowNO, int rowHeight, int colNum,
			String fontCaption, int fontSize, String fontWeight, String align) {
		if (colNum < 0) {
			logger.debug(this.getClass().getName() + " --> Excel column number is null");
			colNum = 100;
		}

		HSSFRow row = sheet.createRow(rowNO); // 创建第一行
		row.setHeight((short) (rowHeight < 1 ? 300 : rowHeight)); // 设置行高

		HSSFCell cell = row.createCell((short) 0); // 设置第一行
		cell.setCellType(HSSFCell.ENCODING_UTF_16); // 定义单元格为字符串类型
		cell.setCellValue(new HSSFRichTextString(fontCaption));

		sheet.addMergedRegion(new CellRangeAddress(rowNO, (short) 0, rowNO, (short) (colNum - 1))); // 指定合并区域

		HSSFCellStyle cellStyle = createCellFontStyle(workbook, fontSize, fontWeight, align); // 设定样式
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
	}

	/**
	 * 设置报表列头0000
	 * 
	 * @param sheet
	 *            (创建sheet)
	 * @param rowNO
	 *            报表的单行行号(创建第几行)
	 * @param rowHeight
	 *            报表的单行行高
	 * @param columnHeader
	 *            报表行中显示的字符
	 */
	public void createColumnHeader(HSSFSheet sheet, int rowNO, int rowHeight, String[] columnHeader) {
		createColumnHeader(sheet, rowNO, rowHeight, columnHeader, -1, null, null);
	}

	/**
	 * 设置报表列头
	 * 
	 * @param sheet
	 *            (创建sheet)
	 * @param rowNO
	 *            报表的单行行号(创建第几行)
	 * @param rowHeight
	 *            报表的单行行高
	 * @param columnHeader
	 *            报表行中显示的字符
	 * @param fontSize
	 *            字体的大小 (字体大小 默认00)
	 */
	public void createColumnHeader(HSSFSheet sheet, int rowNO, int rowHeight, String[] columnHeader, int fontSize) {
		createColumnHeader(sheet, rowNO, rowHeight, columnHeader, fontSize, null, null);
	}

	/**
	 * 设置报表列头00000
	 * 
	 * @param sheet
	 *            (创建sheet)
	 * @param rowNO
	 *            报表的单行行号(创建第几行)
	 * @param rowHeight
	 *            报表的单行行高
	 * @param columnHeader
	 *            报表行中显示的字符
	 * @param fontSize
	 *            字体的大小 (字体大小 默认00)
	 * @param fontWeight
	 *            报表表头显示的字符
	 * @param align
	 *            字体水平位置 (center中间 right右 left左)
	 */
	public void createColumnHeader(HSSFSheet sheet, int rowNO, int rowHeight, String[] columnHeader, int fontSize,
			String fontWeight, String align) {
		if (columnHeader == null || columnHeader.length < 1) {
			logger.debug(this.getClass().getName() + " --> Excel columnHeader is null");
			return;
		}
		HSSFRow row = sheet.createRow(rowNO);
		row.setHeight((short) rowHeight);

		HSSFCellStyle cellStyle = createCellFontStyle(workbook, fontSize, fontWeight, align);
		if (cellStyle != null) {
			cellStyle.setFillForegroundColor((short) 11);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}

		HSSFCell cell = null;
		for (int i = 0; i < columnHeader.length; i++) {
			sheet.setColumnWidth((short) i, (short) (20 * 256)); // 设置列宽，0个字符宽度。宽度参数为/，故乘以
			cell = row.createCell((short) i);
			cell.setCellType(HSSFCell.ENCODING_UTF_16);
			if (cellStyle != null) {
				cell.setCellStyle(cellStyle);
			}
			cell.setCellValue(new HSSFRichTextString(columnHeader[i]));
		}
	}

	/**
	 * 创建数据行00000000
	 * 
	 * @param sheet
	 *            (创建sheet)
	 * @param rowNO
	 *            报表的单行行号(创建第几行)
	 * @param rowHeight
	 *            报表的单行行高
	 * @param columnData
	 *            报表行中显示的数据
	 * @param jsonAttr
	 *            json中key值的string数组
	 * @param maxValue
	 *            Excel显示的最大上限
	 */
	public HSSFSheet createColumnData(HSSFSheet sheet, int rowNO, JSONArray columnData, String jsonAttr[], int maxValue,
			int rowHeight) {
		maxValue = (maxValue < 1 || maxValue > 65533) ? 65533 : maxValue;
		int currRowNO = rowNO;
		for (int numNO = currRowNO; numNO < columnData.size() + currRowNO; numNO++) {
			if (numNO % (maxValue + 2) == 0) {
				sheet = workbook.createSheet();
				rowNO = 0;
			}
			createColumnDataDesc(sheet, numNO, rowNO, currRowNO, rowHeight, columnData, jsonAttr);
			rowNO++;
		}
		return sheet;
	}

	/**
	 * 创建数据行0000000
	 * 
	 * @param sheet
	 *            (创建sheet)
	 * @param numNO
	 *            序列号
	 * @param rowNO
	 *            报表的单行行号(创建第几行)
	 * @param currRowNO
	 *            初始行号
	 * @param rowHeight
	 *            报表的单行行高
	 * @param columnData
	 *            报表行中显示的数据
	 */
	private void createColumnDataDesc(HSSFSheet sheet, int numNO, int rowNO, int currRowNO, int rowHeight,
			JSONArray columnData, String jsonAttr[]) {
		createColumnDataDesc(sheet, numNO, rowNO, currRowNO, rowHeight, columnData, jsonAttr, -1, null, null);
	}

	/**
	 * 创建数据行000000000
	 * 
	 * @param sheet
	 *            (创建sheet)
	 * @param numNO
	 *            序列号
	 * @param rowNO
	 *            报表的单行行号(创建第几行)
	 * @param currRowNO
	 *            初始行号
	 * @param rowHeight
	 *            报表的单行行高
	 * @param columnData
	 *            报表行中显示的数据
	 * @param fontSize
	 *            字体的大小 (字体大小 默认00)
	 * @param fontWeight
	 *            报表表头显示的字符
	 * @param align
	 *            字体水平位置 (center中间 right右 left左)
	 */
	private void createColumnDataDesc(HSSFSheet sheet, int numNO, int rowNO, int currRowNO, int rowHeight,
			JSONArray columnData, String jsonAttr[], int fontSize, String fontWeight, String align) {
		if (columnData == null || columnData.size() < 1) {
			logger.debug(this.getClass().getName() + " --> Excel columnData is null");
		}
		HSSFRow row = sheet.createRow(rowNO);
		row.setHeight((short) rowHeight);

		HSSFCellStyle cellStyle = null; // createCellFontStyle(workbook,
										// fontSize, fontWeight, align);
		cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION); // 指定单元格居中对齐
		HSSFCell cell = null;
		JSONObject jsonrow = (JSONObject) columnData.get(numNO - currRowNO);
		for (int i = 0; i < jsonAttr.length; i++) {
			sheet.setColumnWidth((short) i, (short) (20 * 256)); // 设置列宽，0个字符宽度。宽度参数为/，故乘以
			cell = row.createCell((short) i);
			cell.setCellType(HSSFCell.ENCODING_UTF_16);
			if (cellStyle != null) {
				cell.setCellStyle(cellStyle);
			}
			cell.setCellValue(
					new HSSFRichTextString(jsonrow.containsKey(jsonAttr[i]) ? jsonrow.getString(jsonAttr[i]) : ""));
		}
	}

	/**
	 * 创建通用的Excel最后一行的信息 (创建合计行 (最后一行))
	 * 
	 * @param workbook
	 *            如果为空 则没有样式
	 * @param sheet
	 * @param colNum
	 *            报表的总列数 (合并)
	 * @param fontCaption
	 *            报表行中显示的字符
	 * @param fontSize
	 *            字体的大小 (字体大小 默认00)
	 * @param fontWeight
	 *            报表表头显示的字符
	 * @param align
	 *            字体水平位置 (center中间 right右 left左)
	 * @param colNum
	 *            报表的列数 (需要合并到的列索引)
	 * 
	 */
	public void createSummaryRow(HSSFWorkbook workbook, HSSFSheet sheet, int colNum, String fontCaption, int fontSize,
			String fontWeight, String align) {

		HSSFCellStyle cellStyle = createCellFontStyle(workbook, fontSize, fontWeight, align);

		HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
		HSSFCell sumCell = lastRow.createCell((short) 0);

		sumCell.setCellValue(new HSSFRichTextString(fontCaption));
		if (cellStyle != null) {
			sumCell.setCellStyle(cellStyle);
		}
		sheet.addMergedRegion(
				new CellRangeAddress(sheet.getLastRowNum(), (short) 0, sheet.getLastRowNum(), (short) (colNum - 1))); // 指定合并区域
	}

	/**
	 * 设置字体样式 (字体为宋体 ，上下居中对齐，可设置左右对齐，字体粗细，字体大小 )
	 * 
	 * @param workbook
	 *            如果为空 则没有样式
	 * @param fontSize
	 *            字体大小 默认 00
	 * @param fontWeight
	 *            字体粗细 ( 值为bold 为加粗)
	 * @param align
	 *            字体水平位置 (center中间 right右 left左)
	 */
	public HSSFCellStyle createCellFontStyle(HSSFWorkbook workbook, int fontSize, String fontWeight, String align) {
		if (workbook == null) {
			logger.debug(this.getClass().getName() + " --> Excel HSSFWorkbook FontStyle is not set");
			return null;
		}

		HSSFCellStyle cellStyle = workbook.createCellStyle();

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		if (align != null && align.equalsIgnoreCase("left")) {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 指定单元格居中对齐
		}
		if (align != null && align.equalsIgnoreCase("right")) {
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 指定单元格居中对齐
		}

		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 指定单元格垂直居中对齐
		cellStyle.setWrapText(true); // 指定单元格自动换行

		// 单元格字体
		HSSFFont font = workbook.createFont();
		if (fontWeight != null && fontWeight.equalsIgnoreCase("normal")) {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		} else {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}

		font.setFontName("宋体");
		font.setFontHeight((short) (fontSize < 1 ? 200 : fontSize));
		cellStyle.setFont(font);

		return cellStyle;
	}

	public void exportExcel(String fileName) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(fileName));
			workbook.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exportExcelToweb(String fileName, HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment;filename=" + java.net.URLEncoder.encode(fileName, "utf-8"));
		OutputStream ouputStream = response.getOutputStream();
		workbook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	/**
	 * 利用模板导出Excel
	 * 
	 * @param inputFile
	 *            输入模板文件路径
	 * @param outputFile
	 *            输入文件存放于服务器路径
	 * @param dataList
	 *            待导出数据
	 * @throws Exception
	 * @roseuid:
	 */
	@SuppressWarnings("deprecation")
	public void exportExcelFile(String inputFileName, String outputFileName, List<?> dataList) throws Exception {
		// 用模板文件构造poi
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(inputFileName));
		// 创建模板工作表
		HSSFWorkbook templatewb = new HSSFWorkbook(fs);
		// 直接取模板第一个sheet对象
		HSSFSheet templateSheet = templatewb.getSheetAt(1);
		if (dataList.size() % 65535 == 0) {
			templateSheet = templatewb.createSheet();
		}
		// 得到模板的第一个sheet的第一行对象 为了得到模板样式
		HSSFRow templateRow = templateSheet.getRow(0);

		// 取得Excel文件的总列数
		int columns = templateSheet.getRow((short) 0).getPhysicalNumberOfCells();
		logger.debug("columns   is   :   " + columns);
		// 创建样式数组
		HSSFCellStyle styleArray[] = new HSSFCellStyle[columns];

		// 一次性创建所有列的样式放在数组里
		for (int s = 0; s < columns; s++) {
			// 得到数组实例
			styleArray[s] = templatewb.createCellStyle();
		}
		// 循环对每一个单元格进行赋值
		// 定位行
		for (int rowId = 1; rowId < dataList.size(); rowId++) {
			// 依次取第rowId行数据 每一个数据是valueList
			List<?> valueList = (List<?>) dataList.get(rowId - 1);
			// 定位列
			for (int columnId = 0; columnId < columns; columnId++) {
				// 依次取出对应与colunmId列的值
				// 每一个单元格的值
				String dataValue = (String) valueList.get(columnId);
				// 取出colunmId列的的style
				// 模板每一列的样式
				HSSFCellStyle style = styleArray[columnId];
				// 取模板第colunmId列的单元格对象
				// 模板单元格对象
				HSSFCell templateCell = templateRow.getCell((short) columnId);
				// 创建一个新的rowId行 行对象
				// 新建的行对象
				HSSFRow hssfRow = templateSheet.createRow(rowId);
				// 创建新的rowId行 columnId列 单元格对象
				// 新建的单元格对象
				HSSFCell cell = hssfRow.createCell((short) columnId);
				// 如果对应的模板单元格 样式为非锁定
				if (templateCell.getCellStyle().getLocked() == false) {
					// 设置此列style为非锁定
					style.setLocked(false);
					// 设置到新的单元格上
					cell.setCellStyle(style);
				}
				// 否则样式为锁定
				else {
					// 设置此列style为锁定
					style.setLocked(true);
					// 设置到新单元格上
					cell.setCellStyle(style);
				}
				// 设置编码
				// cell.setEncoding(HSSFCell.ENCODING_UTF_);
				// Debug.println( "dataValue : " + dataValue);
				// 设置值 统一为String
				cell.setCellValue(dataValue);
			}
		}
		// 设置输入流
		FileOutputStream fOut = new FileOutputStream(outputFileName);
		// 将模板的内容写到输出文件上
		templatewb.write(fOut);
		fOut.flush();

		// 操作结束，关闭文件
		fOut.close();
	}

	@SuppressWarnings("deprecation")
	public List<List<String>> getBlacklists() {
		List<List<String>> result = new ArrayList<List<String>>();
		int rowNum = sheet.getLastRowNum();

		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			List<String> item = new ArrayList<String>();
			HSSFRow row = sheet.getRow(i);
			HSSFCell cell = row.getCell(0);
			for (int j = 0; j < 4; j++) {
				item.add(row.getCell(j).getStringCellValue());
			}
		}
		return result;
	}

	

}
