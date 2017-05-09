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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
<meta name="renderer" content="webkit" />
<meta name="description" content="Bootstrap Admin App + jQuery">
<meta name="keywords"
	content="app, responsive, jquery, bootstrap, dashboard, admin">
<title>畅卓验证码短信飞常快---畅卓官网 在线短信平台,SDK短信接口,手机短信验证码,验证码通道</title>
<meta name="description"
	content="北京畅卓科技有限公司是一家拥有卓越运营支撑能力的短信服务商，为企业提供一站式的短信验证码应用解决方案，拥有106高端通道免费试用，3秒最快，99.99%到达率，咨询热线：400-793-0000，产品包括：短信验证码，短信平台，短信验证码，手机短信平台，验证码短信，在线短信群发平台，短信通道，短信接口，短信服务商，短信营销，验证码平台，验证码通道，验证码接收，验证码接收平台，手机验证码短信">
<meta name="keywords"
	content="短信验证码，短信平台，短信验证码，手机短信平台，验证码短信，在线短信群发平台，短信通道，短信接口，短信服务商，短信营销，验证码平台，验证码通道，验证码接收，验证码接收平台，手机验证码短信，SDK短信接口">
<link rel="stylesheet"
	href="<%=basePath%>static/vendor/fontawesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="<%=basePath%>static/vendor/simple-line-icons/css/simple-line-icons.css">
<link rel="stylesheet"
	href="<%=basePath%>static/vendor/animate.css/animate.min.css">
<link rel="stylesheet"
	href="<%=basePath%>static/vendor/whirl/dist/whirl.css">
<link rel="stylesheet" href="<%=basePath%>static/css/bootstrap.css"
	id="bscss">
<link rel="stylesheet" href="<%=basePath%>static/css/app.css"
	id="maincss">
<link rel="shortcut icon" href="favicon.ico">
<!-- <link rel="icon" type="image/gif" href="animated_favicon1.gif"> -->
<style type="text/css">
.headli {
	width: 140px;
	height: 55px;
	border-right: 1px white solid;
	text-align: center;
	vertical-align: inherit;
	line-height: 55px;
	cursor: pointer;
}

.headli:hover {
	background-color: grey;
}
</style>
<html>
<body>
	<div class="wrapper">
		<!-- top navbar-->
		<header class="topnavbar-wrapper"> <!-- START Top Navbar--> <nav
			role="navigation" class="navbar topnavbar"> <!-- START navbar header-->
		<div class="navbar-header">
			<a href="javascript:;"
				onclick="goPage('<%=basePath%>spInfo/mySpinfo.html','static/js/spInfo/mySpinfo.js')"
				class="navbar-brand">
				<div class="brand-logo" style="padding-top: 6px">
					<img src="<%=basePath%>static/img/logo2.png" alt="App Logo"
						class="img-responsive" style="width: 100px; height: 47px">
				</div>
				<div class="brand-logo-collapsed">
					<img src="<%=basePath%>static/img/logo2.png" alt="App Logo"
						class="img-responsive" style="width: 100px; height: 47px">
				</div>
			</a>
		</div>
		<div class="nav-wrapper"></div>
		</nav> </header>
		<div
			style="width: 500px; height: 380px; position: absolute; left: 50%; margin-left: -250px; top: 50%; margin-top: -190px;">
			<div style="text-align: center;">
				<c:if test="${chargeType == 1}">
					<img src="<%=basePath%>static/img/payComplete.png" alt="App Logo"
						style="width: 72px; height: 72px; margin-bottom: 15px;">
					<label style="font-size: 25px">${sHtmlText}</label>
					<table id="example"
						class="table table-bordered dataTable no-footer" role="grid"
						aria-describedby="example_info" style="width: 500px;">
						<tbody>
							<c:choose>
								<c:when test="${spInfo.sp_service_type==2 }">
									<tr role="row">
										<td style="border-color: lightblue"><label>充值金额</label></td>
										<td style="border-color: lightblue"><fmt:formatNumber
												type="number" value="${chargeInfo.charge_num/100}"
												pattern="0.00" maxFractionDigits="2" /></td>
									</tr>
									<tr role="row">
										<td style="border-color: lightblue"><label>剩余金额</label></td>
										<td style="border-color: lightblue"><fmt:formatNumber
												type="number" value="${spInfo.leftover_num/100}"
												pattern="0.00" maxFractionDigits="2" /></td>
									</tr>

								</c:when>
								<c:otherwise>
									<tr role="row">
										<td style="border-color: lightblue"><label>充值短信条数</label></td>
										<td style="border-color: lightblue">${chargeInfo.charge_num}</td>
									</tr>
									<tr role="row">
										<td style="border-color: lightblue"><label>剩余短信条数</label></td>
										<td style="border-color: lightblue">${spInfo.leftover_num}</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</c:if>
				<c:if test="${chargeType == 0}">
					<div class="img-responsive">
						<img src="<%=basePath%>static/img/payFail.png" alt="App Logo"
							style="width: 72px; height: 72px; margin-bottom: 15px;"> <label
							style="font-size: 25px">${sHtmlText}</label>
					</div>
				</c:if>
				<c:if test="${chargeType == 2}">
					<table id="example"
						class="table table-bordered dataTable no-footer" role="grid"
						aria-describedby="example_info" style="width: 500px;">
						<tbody>
							<tr role="row">
								<td style="border-color: lightblue"><label>账号充值金额</label></td>
								<td style="border-color: lightblue"><fmt:formatNumber
										type="number" value="${account.chargeNum/100}" pattern="0.00"
										maxFractionDigits="2" /></td>
							</tr>
							<tr role="row">
								<td style="border-color: lightblue"><label>账号剩余金额</label></td>
								<td style="border-color: lightblue"><fmt:formatNumber
										type="number" value="${leftoverNum/100}" pattern="0.00"
										maxFractionDigits="2" /></td>
							</tr>
						</tbody>
					</table>
				</c:if>
				<button id="closePage" type="button" class="btn btn-info">关闭本页面</button>
			</div>
		</div>
	</div>
</body>
</html>
<footer> <span>&copy; 2016 - Chanzor</span> </footer>
<script src="<%=basePath%>static/js/jquery-2.1.1.min.js"></script>
<script src="<%=basePath%>static/vendor/bootstrap/dist/js/bootstrap.js"></script>
<script type="text/javascript">
	$(function() {
		$("#closePage").click(function() {
			if (navigator.userAgent.indexOf("MSIE") > 0) {
				if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
					window.opener = null;
					window.close();
				} else {
					window.open('', '_top');
					window.top.close();
				}
			} else if (navigator.userAgent.indexOf("Chrome") > 0) {
				window.opener = null;
				window.open('', '_self');
				window.close();
	} else if (navigator.userAgent.indexOf("Firefox") > 0) {
				window.open('', '_parent', '');
				window.close();
			} else {
				window.opener = null;
				window.open('', '_self', '');
				window.close();
			}
		});
	});
</script>