<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello , Chanzor</title>
<style type="text/css">
table {
	border-left: 1px solid #666;
	border-bottom: 1px solid #666;
}

td {
	border-right: 1px solid #666;
	border-top: 1px solid #666;
}
</style>
</head>
<body>
	<div style="text-align: center;">
		<h1>登陆信息 DEMO</h1>
		<hr>
		<form action="log" method="post" id="logform">
			<table align="center">
				<tr>
					<td>user :</td>
					<td>${sessionUser.USER_NAME }</td>
				</tr>
				<tr>
					<td>Email :</td>
					<td>${sessionUser.EMAIL }</td>
				</tr>
				<tr>
					<td>current app :</td>
					<td>${CURRENT_APP.ID }/${CURRENT_APP.SP_NAME }</td>
				</tr>
				<tr>
					<td>APPS</td>
					<td>
						<table style="padding: 5px;background-color: #D2E9FF">
							<c:forEach items="${APPS }" var="item">
								<tr>
									<td>${item.ID }</td>
									<td>${item.SP_NAME }</td>
								</tr>
							</c:forEach>
						</table>

					</td>
				</tr>
				<tr>
					<td>LAST_LOGIN_IP</td>
					<td>${sessionUser.LAST_LOGIN_IP }</td>
				</tr>
				<tr>
					<td colspan="2"><a href="logout">Logout</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script src="<%=basePath%>static/js/jquery-2.1.1.min.js"></script>
<script src="<%=basePath%>static/js/common.js"></script>
<script src="<%=basePath%>static/js/plugins/layer2.1/layer.js"></script>
</html>