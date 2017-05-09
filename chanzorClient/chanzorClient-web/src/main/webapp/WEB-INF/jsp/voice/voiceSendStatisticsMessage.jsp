<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="checkLand" uri="/chanzor"%>


<div class='percentageList chanzorDataList'>
	<div class='chanzorDataList_title'>
		<p>发送统计</p>
	</div>
	<form class="form-inline" style='text-align: right; margin-right: 2%;'>
		<checkLand:check type="sessionUser">
			<div class="form-group">
				<label for="telph" class='listLab'>应用名称：</label> <select id="spId"
					name="spId" class='form-control listInp'></select>
			</div>
		</checkLand:check>
		<div class="form-group">
			<label for="telph" class='listLab'>统计类型：</label> <select
				class="form-control listInp" name="timegroup" id="timegroup">
				<option value="1">月</option>
				<option value="2">日</option>
			</select>
		</div>
		<div class="form-group">
			<label for="blackTime" class='listLab'>信息发送时间：</label> <input
				type="search" id="queryStartTime" name="queryStartTime"
				class="form-control listInp">
		</div>
		<div class="form-group">
			<input type="search" id="queryEndTime" name="queryEndTime"
				class="form-control listInp" placeholder="截止时间">
		</div>
		<button type="button" class="btn btn-default listBtn"
			onclick="search()">查询</button>
	</form>
	<table id="example"
		class="table table-striped table-bordered table-hover">
		<thead style="border-top: 1px #EEEEEE solid">
			<tr>
				<th>应用名称</th>
				<th>信息发送时间</th>
				<th>提交数量</th>
				<th>下发条数</th>
				<th>成功条数</th>
				<th>失败条数</th>
				<th>未知状态条数</th>
			</tr>
		</thead>
	</table>
</div>
