package com.chanzor.util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.mysql.jdbc.Connection;

public class JDBCUtil {
	/*
	public static Connection getConnection () {
		Connection connection = null ;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://123.57.45.220:3306/appuser?Unicode=true&amp;characterEncoding=utf8";
			String username = "root";
			String passwd = "Chanzor1@";
			connection = (Connection) DriverManager.getConnection(url, username, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static String[] getColumnsInfo (String tableName) {
		Connection connection = getConnection();
		java.sql.Statement stt = null;
		ResultSet rs = null;
		List<String> columns = new ArrayList<String>();
	    try {
	    	stt = connection.createStatement();
	    	rs = stt.executeQuery("show full columns from  "+ tableName);
	    	while(rs.next()){
	    		String comment = rs.getString("Comment");
	    		String field = rs.getString("field");
	    		String type = rs.getString("type");
	    		if(field.equals("id")) continue;
	    		if(comment != null && !comment.equals("")){
	    			columns.add(comment+"-"+field+"-"+type);
	    		}else{
	    			columns.add(field+"-"+field+"-"+type);
	    		}
	    	}
	    	rs.close();
	    	stt.close();
	    	connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				stt.close();
		    	connection.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    for (String s : columns) {
	    	System.out.println(s);
	    }
	    String[] fields = columns.toArray(new String[columns.size()]);
	    return fields;
	}
//	public static void main(String[] args) {
////		getColumnsInfo("SMS_SP_CHARGE");
//	}

 */
}
