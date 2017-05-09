<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>500错误</title>
    <meta name="keywords" content="Chanzor">
    <meta name="description" content="Chanzor">

    <link href="<%=basePath %>static/css/bootstrap.min.css?v=3.4.0" rel="stylesheet">
    <link href="<%=basePath %>static/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">

    <link href="<%=basePath %>static/css/animate.min.css" rel="stylesheet">
    <link href="<%=basePath %>static/css/style.min.css?v=3.0.0" rel="stylesheet">

</head>

<body class="gray-bg">


    <div class="middle-box text-center animated fadeInDown">
        <h1>500</h1>
        <h3 class="font-bold">服务器内部错误</h3>

        <div class="error-desc">
            服务器好像出错了...
            <br><span style="cursor: pointer;" onclick="if($('#message').is(':hidden')) $('#message').show();else $('#message').hide();" >${exception }</span>
            <div style="display: none;" id="message">${exceptionMessage }</div>
        </div>
    </div>

    <!-- 全局js -->
    <script src="<%=basePath %>static/js/jquery-2.1.1.min.js"></script>
    <script src="<%=basePath %>static/js/bootstrap.min.js?v=3.4.0"></script>


</body>

</html>
