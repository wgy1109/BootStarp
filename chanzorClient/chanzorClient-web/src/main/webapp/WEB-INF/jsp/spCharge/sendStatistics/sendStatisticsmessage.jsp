<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- Main section-->
<section>
	<div class="content-wrapper">
		<div class="container-fluid">
			<!-- START DATATABLE 1 -->
			<div class="row">
				 <div class="panel panel-default">
					<div class="ibox-title">
						<h4>&nbsp;&nbsp;&nbsp;&nbsp;发送统计</h4>
						<div class="ibox-tools"></div>
					</div>
					<div class="ibox-content">
						<form action="" method="post" id="form1">
						<div class="dataTables_wrapper form-inline">
							<div class="row">
								<div class="col-sm-12">
									<div class="dataTables_filter">
										<label>时间分组：<select name="timegroup" id="timegroup" style="width:110px; height:30px; padding:0px" class="form-control">
												<option value="">全部</option>
												<option value="1">月</option>
												<option value="2">日</option>
										</select>&nbsp;</label> 
									
										<label>信息发送时间：<input type="search" id="queryStartTime" name="queryStartTime"
											style="width:110px" class="form-control">&nbsp;&nbsp;&nbsp;</label>
											
										<label>-<input type="search" id="queryEndTime"
											name="queryEndTime" style="width:110px" class="form-control">&nbsp;&nbsp;&nbsp;</label>
										<label><button type="button" class="btn btn-success" onclick="search()">查询</button></label>
									</div>
								</div>
							</div>
						</div>
						</form>
						<table id="example" class="table table-striped table-bordered table-hover">
							<thead style="border-top: 1px #EEEEEE solid">
								<tr>
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
				</div>
			</div>
		</div>
	</div>
</section>

