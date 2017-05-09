package com.chanzor.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtils {

	//是否手机号
	public static boolean isPhone(String phone){
		Pattern p = Pattern.compile("^((134|135|136|137|138|139|150|151|152|157|158|159|182|183|184|187|188|147|130|131|132|155|156|185|186|145|176|190|133|153|180|181|189|177)[0-9]{8})$|^((1705|1709|1700)[0-9]{7})$");
		Matcher m = p.matcher(phone);  
		return m.matches();
	}
	//是否移动号
    public static boolean isMobilePhone(String phone){
    	Pattern p = Pattern.compile("^((134|135|136|137|138|139|150|151|152|157|158|159|182|183|184|187|188|147)[0-9]{8})$|^((1705)[0-9]{7})$");
		Matcher m = p.matcher(phone);  
		return m.matches();
	}
    //是否联通号
    public static boolean isUnicomPhone(String phone){
    	Pattern p = Pattern.compile("^((130|131|132|155|156|185|186|145|176|190)[0-9]{8})$|^((1709)[0-9]{7})$");
		Matcher m = p.matcher(phone);  
		return m.matches();
   	}
    //是否电信号
    public static boolean isTelecomPhone(String phone){
    	Pattern p = Pattern.compile("^((133|153|180|181|189|177)[0-9]{8})$|^((1700)[0-9]{7})$");
		Matcher m = p.matcher(phone);  
		return m.matches();
   	}   
    
    public static boolean isPositiveOrZero(String numValue){
    	Pattern p = Pattern.compile("^[1-9]\\d*|0$");
		Matcher m = p.matcher(numValue);  
		return m.matches();
   	}  
    
    public static int getOperatorType(String phone){
    	if(isUnicomPhone(phone)){
    		return  2;
    	}
    	else if(isMobilePhone(phone)){
    		return 1;
    	}
    	else if(isTelecomPhone(phone)){
    		return 3;
    	}      	
    	return 0;
    }
    
   
    
    public static boolean isWord(String wordValue){
    	Pattern p = Pattern.compile("^\\w{1,50}$");
		Matcher m = p.matcher(wordValue);  
		return m.matches();
    }
    
    public static void main(String[] args) {
    	String code = "";
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			code += r.nextInt(9);
		}
		System.out.println(code);
 }
}
