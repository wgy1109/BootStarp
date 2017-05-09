<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="checkLand" uri="/chanzor"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	var LandingType = "${sessionScope.LandingType}";
</script>
<div class='chanzorDataList'>
	<div class='chanzorDataList_title'>
		<p>公告列表</p>
	</div>
	<div class="form-inline" style='text-align: right; margin-right: 2%;'>
		<checkLand:check type="sessionUser">
			<div class="form-group">
				<label for="telph" class='listLab'>公告内容：</label> <input type="text"
					class="form-control listInp" id="notifyContent"
					name="notifyContent" placeholder="">
			</div>
			<button type="button" class="btn btn-default listBtn"
				onclick="search()">查询</button>
		</checkLand:check>
	</div>
	<table id="example"
		class="table table-striped table-bordered table-hover">
		<thead
			style="border-top: 1px #EEEEEE solid; background-color: #0099CB">
			<tr class="dateTable-th">
				<th>公告内容</th>
				<th>发布时间</th>
			</tr>
		</thead>
	</table>
</div>
