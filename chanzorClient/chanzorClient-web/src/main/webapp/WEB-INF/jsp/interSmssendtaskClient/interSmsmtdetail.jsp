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
                <label for="telph" class='listLab'>短信内容：</label>
                <input type="text" class="form-control listInp" id="messagecontent" name="messagecontent" placeholder="">
            </div>
            <div class="form-group">
                <label for="telph" class='listLab'>状态报告：</label>
                <select  class="form-control listInp" name="drresult" id="drresult">
					<option value="" selected="selected">请选择</option>
					<option value="1">成功</option>
					<option value="-1">失败</option> 
				</select>
            </div>
            <div class="form-group">
                <label for="blackTime" class='listLab'>时间：</label>
                <input type="search" id="queryStartTime" name="queryStartTime" class="form-control listInp" >
            </div>
            <div class="form-group">
                <input type="search" id="queryEndTime" name="queryEndTime" class="form-control listInp" placeholder="截止时间" >
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
						<th>回执时间</th>
					</tr>
				</thead>
        </table>
    </div>
