<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'appSpInfo.jsp' starting page</title>
    
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
   <form class="js-validation-app-add form-horizontal"
						action="spInfo/addSpInfoMth.html" method="post">
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-username">应用名称
								<span class="text-danger">*</span> </label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="app_name"
									name="sp_name" placeholder="应用名称..">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-skill">应用类型
								<span class="text-danger">*</span> </label>
							<div class="col-md-7">
								<select class="form-control" id="sp_app_type" name="sp_app_type">
									<option value="0">请选择</option>
									<option value="1">网站</option>
									<option value="2">移动应用</option>
									<option value="3">微信公众号</option>
									<option value="4">其他</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-skill">应用行业
								<span class="text-danger">*</span> </label>
							<div class="col-md-7">
								<select class="form-control" id="sp_industry"
									name="sp_industry">
									<option value="0">请选择</option>
									<option value="1">电子商务</option>
									<option value="2">金融</option>
									<option value="3">在线社区</option>
									<option value="4">房地产</option>
									<option value="5">医疗</option>
									<option value="6">交通汽车</option>
									<option value="7">旅游</option>
									<option value="8">游戏</option>
									<option value="9">教育</option>
									<option value="10">IT硬件</option>
									<option value="11">IT软件服务</option>
									<option value="12">文化出版</option>
									<option value="13">生活信息</option>
									<option value="14">其他</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-website">网站<span
								class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="sp_website" name="sp_website"
									placeholder="http://example.com">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-website">应用描述</label>
							<div class="col-md-8">
								<label class="css-input css-checkbox css-checkbox-primary"
									for="val-terms"> <textarea class="form-control"
										id="sp_desc" name="sp_desc" rows="8"
										placeholder="请输入应用描述"></textarea> </label>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-8 col-md-offset-4">
								<button class="btn btn-sm btn-primary" type="submit">提交</button>
							</div>
						</div>
					</form>
  </body>
</html>
