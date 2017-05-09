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
<!-- Main section-->
<div class="chanzorDataList">
	<div class="chanzorDataList_title">
		<p>订单列表</p>
	</div>
	<form class="form-inline" style='text-align: right; margin-right: 2%;'>
		<div class="dataTables_wrapper form-inline">
			<div class="row">
				<div class="col-sm-12">
					<div class="dataTables_filter">
						<label>订单号:<input type="search" name="chargeId"
							id="chargeId" style="width: 90px" class="form-control width120">&nbsp;&nbsp;&nbsp;
						</label>
						<checkLand:check type="sessionUser">
							<label>应用名称：<select id="spId" name="spId"
								class="form-control m-b">
									<option value="0">请选择</option>
									<c:forEach items="${spInfoList }" var="spInfo">
										<option value="${spInfo.spid }">${spInfo.sp_name }</option>
									</c:forEach>
							</select>&nbsp;&nbsp;&nbsp;
							</label>
						</checkLand:check>
						<label>充值类型：<select id="charge_type" name="charge_type"
							class="form-control m-b">
								<option value="0">请选择</option>
								<option value="1">线下充值</option>
								<option value="2">支付宝充值</option>
								<option value="3">账号余额充值</option>
						</select>&nbsp;&nbsp;&nbsp;
						</label> <label>状态：<select id=alipayType name="alipayType"
							class="form-control m-b">
								<option value="-1">请选择</option>
								<option value="1">成功</option>
								<option value="0">失败</option>
						</select>&nbsp;&nbsp;&nbsp;
						</label> <label>支付时间：<input type="search" id="quereyStartTime"
							name="quereyStartTime" style="width: 110px" readonly="readonly" class="form-control" value="${requestScope.queryStartTime }" >&nbsp;&nbsp;&nbsp;
						</label> <label>-<input type="search" id="queryEndTime"
							name="queryEndTime" style="width: 110px" readonly="readonly" class="form-control">&nbsp;&nbsp;&nbsp;
						</label> <label><button class="btn listBtn" type="button"
								onclick="search()">查询</button> </label>
					</div>
				</div>
			</div>
		</div>
	</form>
	<div>
		<table id="example"
			class="table table-striped table-bordered table-hover">
			<thead style="border-top: 1px #EEEEEE solid">
				<tr>
					<th>订单号</th>
					<th>应用名称</th>
					<th>业务类型</th>
					<th>充值类型</th>
					<th>充值金额</th>
					<th>充值单价</th>
					<th>充值条数</th>
					<th>充值时间</th>
					<th>充值状态</th>
					<th>操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<!-- Page footer-->
<div id="modaljsp">
	<div id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-label="Close"
						class="close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="myModalLabel" class="modal-title">修改应用</h4>
				</div>
				<div class="modal-body">
					<form class="js-validation-upload-auth form-horizontal"
						id="uploadAddressForm" action="updateSubmit.html" method="post"
						enctype="multipart/form-data">
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signusername">收件人名称
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="signusername"
									name="signusername" placeholder="收件人名称 " maxlength="10" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuseraddress">收件人地址
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="signuseraddress"
									name="signuseraddress" placeholder="收件人地址 " maxlength="50" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuserphone">收件人电话
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="signuserphone"
									name="signuserphone" placeholder="收件人电话" maxlength="15" />
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
					<button type="button" class="btn btn-primary">修改</button>
				</div>
			</div>
		</div>
	</div>
</div>
