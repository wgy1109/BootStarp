package com.chanzor.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
	
	/**
	 * 把时间转换成相应格式的字符串  , 默认返回当前时间'yyyy-MM-dd HH:mm:ss' 格式 
	 * @param date	传入时间
	 * @param type	要得到的时间类型
	 * @return
	 */
	public static String getDateString(Date date, String type){
		try {
			if(date == null ){
				date = new Date();
			}
			SimpleDateFormat sdf = new SimpleDateFormat(type);
			String result = sdf.format(date);
			return result ;
		} catch (Exception e) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String result = sdf.format(new Date());
			return result ;
		}
	}
	
	//固定格式，返回 yyMMdd....字符串
	public static String getDateStringTimg(String formdata){
		if(Tools.notEmpty(formdata)){
			String result = formdata.replaceAll("-", "").substring(2) + "000000000000";
			return result;
		}else{
			return "";
		}
	}
	
	public static String getOtherDateString(Integer d, String type){
		Calendar   cal   =   Calendar.getInstance();
	    cal.add(Calendar.DATE,   d);
	    String day = new SimpleDateFormat(type).format(cal.getTime());
		return day;
	}
	
	public static String getNextdDayString(Integer d, String date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			Date date1 = sdf.parse(date);
			Calendar   cal   =   Calendar.getInstance();
			cal.setTime(date1);
			cal.add(Calendar.DATE,   d);
		    String day = new SimpleDateFormat("yyMMdd").format(cal.getTime());
			return day;
		} catch (ParseException e) {
			return getOtherDateString(1, "yyMMdd");
		}
	}
	
	//固定格式，返回 yyMMdd....字符串
	public static String getDateStringTimgToday(String formdata){
		if(Tools.notEmpty(formdata)){
			String result = formdata.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").substring(2) + "000000000000";
			return result;
		}else{
			DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String result = sdf.format(new Timestamp(System.currentTimeMillis()));
			return result.substring(2) + "000000000000";
		}
	}
	
	public static boolean compareTime(String begin, String end) throws ParseException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			Date date1 = sdf.parse(begin);
			Date date2 = sdf.parse(end);
			if(date1.getTime() > date2.getTime()){
				return true;
			}else{
				return false;
			}
	}
	
	public static String getDayUpdateType(String date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			Date date1 = sdf.parse(date);
		    String day = new SimpleDateFormat("yyyy-MM-dd").format(date1.getTime());
			return day;
		} catch (ParseException e) {
			return getOtherDateString(0, "yyyy-MM-dd");
		}
	}
	
	public static FormData formData(FormData f, String begin, String end){
		try {
			if(f.containsKey(begin) && f.get(begin) != null && !"".equals(f.get(begin).toString())){
				String curentTime = getNextdDayString(7,f.getString(begin).substring(0, 6));
				if(f.containsKey(end) && f.get(end) != null && !"".equals(f.get(end).toString())){
					if (compareTime(f.getString(begin).substring(0, 6),f.getString(end).substring(0, 6))) {
						f.put(end, getDateStringTimgToday(getNextdDayString(1,f.getString(begin).substring(0, 6))));
					}
					if(compareTime(f.getString(end).substring(0, 6),curentTime)){
						f.put(end, getDateStringTimgToday(curentTime));
					}
				}else{
					f.put(end, getDateStringTimgToday(curentTime));
				}
			}else{
				f.put(begin, getDateStringTimgToday(getOtherDateString(0,"yyyyMMdd")));
				f.put(end, getDateStringTimgToday(getOtherDateString(1,"yyyyMMdd")));
			}
			return f;
		} catch (Exception e) {
			f.put(begin, getDateStringTimgToday(getOtherDateString(0,"yyyyMMdd")));
			f.put(end, getDateStringTimgToday(getOtherDateString(1,"yyyyMMdd")));
			return f;
		}
	}
	
	public static FormData formDataDateString(FormData f, String begin, String end){
		try {
			if(f.containsKey(begin) && f.get(begin) != null && !"".equals(f.get(begin).toString())){
				String curentTime = getDayUpdateType(getNextdDayString(7,f.getString(begin).substring(0, 6)));
				if(f.containsKey(end) && f.get(end) != null && !"".equals(f.get(end).toString())){
					if(compareTime(f.getString(begin).substring(0, 6),f.getString(end).substring(0, 6))) {
						f.put(end, getDayUpdateType(getNextdDayString(1,f.getString(begin).substring(0, 6))));
					}
					if(compareTime(f.getString(end).substring(0, 6),curentTime)){
						f.put(end, curentTime);
					}
				}else{
					f.put(end, curentTime);
				}
			}else{
				f.put(begin, getOtherDateString(0,"yyyy-MM-dd"));
				f.put(end, getOtherDateString(1,"yyyy-MM-dd"));
			}
			return f;
		} catch (Exception e) {
			f.put(begin, getOtherDateString(0,"yyyy-MM-dd"));
			f.put(end, getOtherDateString(1,"yyyy-MM-dd"));
			return f;
		}
	}
	
/*	public static void main(String[] args) {
		String a = "170208";
		String b = getNextdDayString(7,a);
		System.out.println(b);
	}*/
}
