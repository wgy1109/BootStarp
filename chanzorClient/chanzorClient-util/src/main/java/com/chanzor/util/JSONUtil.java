package com.chanzor.util;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {

	
	public static JSONObject toJSON (String jsonStr) {
		try{
			JSONObject json = JSONObject.parseObject(jsonStr);
			return json;
		}catch(Exception e){
			return null;
		}
	}
//	public static void main(String[] args) {
////		JSONObject json = JSONUtil.toJSON("123");
////		System.out.println(json);
////		JSONObject p = new JSONObject();
////		p.put("name", "username");
////		p.put("length", 10);
////		p.put("isMobile", true);
////		System.out.println(p.toJSONString());
//		System.out.println(isNum("123.001"));
//		
//	}
//	
	public static boolean isNum (String str) {
		try {
			Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
