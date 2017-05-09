<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<div class='percentageList chanzorDataList'>
        <div class='chanzorDataList_title'>
          <p>短信回复</p>
        </div>
        <form class="form-inline" style='text-align:right;margin-right:2%;' id="form1" >
        	<div class="form-group">
                <label for="telph" class='listLab'>应用名称：</label>
                <input type="text" class="form-control listInp" id="spName" name="spName" placeholder="">
            </div>
            <div class="form-group">
                <label for="blackTime" class='listLab'>时间：</label>
                <input type="search" id="queryStartTime" name="queryStartTime" readonly="readonly" class="form-control listInp" value="${requestScope.queryStartTime }">
            </div>
            <div class="form-group">
                <input type="search" id="queryEndTime" name="queryEndTime" readonly="readonly" class="form-control listInp" placeholder="截止时间" >
            </div>
            <button type="button" class="btn btn-default listBtn" onclick="search()" >查询</button>
            <button type="button" class="btn btn-default listBtn" onclick="exportPOI()">导出</button>
        </form>
        <table id="example"	class="table table-striped table-bordered table-hover">
				<thead style="border-top: 1px #EEEEEE solid">
					<tr>
						<th>手机号码</th>
						<th>应用名称</th>
						<th>回执时间</th>
						<th>是否接收</th>
					</tr>
				</thead>
        </table>
    </div>
