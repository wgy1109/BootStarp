<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<div class='percentageList chanzorDataList'>
        <div class='chanzorDataList_title'>
          <p>短信明细</p>
        </div>
        <form class="form-inline" style='text-align:right;margin-right:2%;' id="form1" >
            <div class="form-group">
                <label for="telph" class='listLab'>手机号码：</label>
                <input type="text" class="form-control listInp" id="mobile" name="mobile" placeholder="">
            </div>
            <div class="form-group">
                <label for="telph" class='listLab'>应用名称：</label>
                <input type="text" class="form-control listInp" id="spName" name="spName" placeholder="">
            </div>
            <div class="form-group">
                <label for="telph" class='listLab'>短信内容：</label>
                <input type="text" class="form-control listInp" id="messagecontent" name="messagecontent" placeholder="">
            </div>
            
            <div class="form-group">
            	<label>提交状态：</label>
            	<select name="submittedState" id="submittedState" style="width:110px; height:30px; padding:0px" class="form-control" onchange="showDrResult()" >
						<option value="">全部状态</option>
						<option value="2" <c:if test="${returnMap.submittedState=='2'}">selected = "selected"</c:if> >提交成功</option>
						<option value="3" <c:if test="${returnMap.submittedState=='3'}">selected = "selected"</c:if> >提交失败</option>
					</select>&nbsp;</div>
			<div class="form-group" id="div_drResult" style="display:none" >
				<label>状态报告：</label>
				<select name="drresult" id="drresult" style="width:110px; height:30px; padding:0px" class="form-control" >
								<option value="">请选择</option>
								<option value="1" <c:if test="${returnMap.drresult=='1'}">selected = "selected"</c:if> >成功</option>
								<option value="0" <c:if test="${returnMap.drresult=='0'}">selected = "selected"</c:if> >未知</option>
								<option value="-1" <c:if test="${returnMap.drresult=='-1'}">selected = "selected"</c:if> >失败</option>
							</select>&nbsp;</div>
            
            <!-- <div class="form-group">
                <label for="telph" class='listLab'>状态报告：</label>
                <select  class="form-control listInp" name="drresult" id="drresult">
					<option value="" selected="selected">请选择</option>
					<option value="1">成功</option>
					<option value="-1">失败</option> 
				</select>
            </div> -->
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
						<th>任务批次</th>
						<th>应用名称</th>
						<th>手机号码</th>
						<th>提交状态</th>
						<th>提交时间</th>
						<th>状态报告</th>
						<th>状态报告值</th>
						<th>回执时间</th>
					</tr>
				</thead>
        </table>
    </div>
