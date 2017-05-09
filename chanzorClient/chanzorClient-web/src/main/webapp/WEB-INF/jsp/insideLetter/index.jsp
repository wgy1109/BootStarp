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
<div class='chanzorDataList'>
	<div class='chanzorDataList_title'>
		<p>站内信</p>
	</div>
	<div class="form-inline" style='text-align: right; margin-right: 2%;'>
		<label> <checkLand:check type="sessionUser">
				<button type="button" onclick="delLetter();"
					class="btn btn-primary ">批量删除</button>
			</checkLand:check>
		</label>&nbsp;&nbsp;&nbsp;
	</div>
	<table id="example"
		class="table table-striped table-bordered table-hover">
		<thead style="border-top: 1px #EEEEEE solid">
			<tr>
				<th><label class="checkbox-inline c-checkbox"> <input
						name="sltAll" id="sltAll" value="" onclick="selectAllAuth()"
						type="checkbox"> <span class="fa fa-check" style="margin-left:29px;"></span>
				</label>全选</th>
				<th>內容</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
	</table>
</div>
<!-- Page footer-->
