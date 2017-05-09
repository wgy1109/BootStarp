<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<div class='percentageList chanzorDataList'>
        <div class='chanzorDataList_title'>
          <p>发送列表</p>
        </div>
        <form class="form-inline" style='text-align:right;margin-right:2%;' >
            <div class="form-group">
                <label for="telph" class='listLab'>任务批次：</label>
                <input type="text" class="form-control listInp" id="packageid" name="packageid" placeholder="">
            </div>
            <div class="form-group">
                <label for="telph" class='listLab'>应用名称：</label>
                <input type="text" class="form-control listInp" id="spName" name="spName" placeholder="">
            </div>
            <div class="form-group">
                <label for="telph" class='listLab'>任务状态：</label>
                <select  class="form-control listLab" name="status" id="status">
							<option value="" selected="selected">请选择</option>
							<option value="1">待审核</option>
							<option value="3">通过</option> 
							<option value="2">驳回</option> 
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
        </form>
        <table id="example"	class="table table-striped table-bordered table-hover">
				<thead style="border-top: 1px #EEEEEE solid">
					<tr>
						<th>任务批次</th>
						<th>应用名称</th>
						<th>提交时间</th>
						<th>发送时间</th>
						<th>总数/总计费数</th>
						<th>信息来源</th>
						<th>任务状态</th>
						<th>任务描述</th>
					</tr>
				</thead>
        </table>
    </div>
