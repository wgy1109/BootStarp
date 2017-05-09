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
<style>
.myFont {
	font-size: 15px;
	font-weight: bold;
}
</style>
<script src="<%=basePath%>static/js/plugins/layer2.1/layer.js"></script>

<div
	style="margin-left: 300px; margin-top: 100px; background: url(../../../img/card1.png) no-repeat">
	<div>&nbsp;</div>
	<br>
	<div>&nbsp;</div>
	<br>
	<div style="margin-left: 40px">
		<span class="myFont">销售经理：</span>${saleManager.name}<a target="_blank"
			href="http://wpa.qq.com/msgrd?v=3&amp;uin=${saleManager.qq}&amp;site=qq&amp;menu=yes">
			<img src="<%=basePath%>static/img/qqTalk.jpg" alt="点击这里给我发消息"
			title="点击这里给我发消息">
		</a>
	</div>
	<br>
	<div style="margin-left: 40px">
		<span class="myFont">手机：</span>${saleManager.mobile}</div>
	<br>
	<div style="margin-left: 40px">
		<span class="myFont">Email：</span>${saleManager.email}</div>
	<br>
	<div style="margin-left: 40px">
		<span class="myFont">咨询热线：</span>400 793 0000
	</div>
	<br>
	<div style="margin-left: 40px">
		<span class="myFont">营销QQ：</span>400 793 0000
	</div>
	<br>
	<div style="margin-left: 40px">
		<span class="myFont">公司地址：</span>北京市丰台区马家堡东路106号自然大厦5层505
	</div>
	<br>
	<div>&nbsp;</div>
	<br>
</div>

