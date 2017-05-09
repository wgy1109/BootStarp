<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'chargeIndex.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
	<table id="chargeRecord" border="1">
	<tr>
	<td>应用名称</td>
	<td>剩余短信</td>
	<td>操作</td>
	</tr>
	</table>
  </body>
  <script src="<%=basePath%>static/js/jquery-2.1.1.min.js"></script>
<script src="<%=basePath%>static/js/common.js"></script>
<script src="<%=basePath%>static/js/plugins/layer2.1/layer.js"></script>
  <script type="text/javascript">
  </script>
</html>
