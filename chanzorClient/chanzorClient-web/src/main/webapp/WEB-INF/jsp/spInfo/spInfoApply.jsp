<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'spInfoApply.jsp' starting page</title>

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
	<form class="js-validation-company-auth form-horizontal" id="applyForm"
		action="" method="post" enctype="multipart/form-data">
		<div class="form-group">
			<label class="col-md-4 control-label" for="val-username">签名 <span
				class="text-danger">*</span>
			</label>
			<div class="col-md-7">
				<input class="form-control" type="text" id="signature"
					name="signature" placeholder="签名"><input name="sp_id"
					type="hidden" value="${spId}" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-8 col-md-offset-4">
				<button class="btn btn-sm btn-primary" type="button"
					onclick="applyApp();">提交</button>
			</div>
		</div>
	</form>
</body>
</html>
