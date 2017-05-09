<%@ page pageEncoding="UTF-8" import="com.chanzor.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="content-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<!-- START panel-->
			<div id="panelDemo8" class="panel panel-primary">
				<div class="panel-heading">账号信息</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-4">
							<h4>应用ID：${spInfo.spid }</h4>
						</div>
						<div class="col-lg-8">
							<c:if
								test="${spInfo.sp_through_status eq 0 ||spInfo.sp_through_status eq 22}">
								<div class="col-lg-2">
									<button class="btn btn-success"
										onclick="appOnLine(${spInfo.spid })">申请上线</button>
								</div>
							</c:if>
							<div class="col-lg-10">
								<button class="btn btn-danger"
									onclick="delSpInfo(${spInfo.spid})">删除应用</button>
							</div>
						</div>
						<div class="col-lg-12">
							<h4>应用号码：</h4>
						</div>
						<div class="col-lg-12">
							<h4>应用名称：${spInfo.sp_name }</h4>
						</div>
						<div class="col-lg-12">
							<h4>应用类型：</h4>
						</div>
						<div class="col-lg-12">
							<h4>服务器白名单：</h4>
						</div>
					</div>
				</div>
				<div class="panel-heading">
					<h4>测试号码</h4>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-12">
							<label class="col-md-2 control-label"><h4>测试手机号码:</h4> </label>
							<div class="input-group col-md-2"
								style="float:left;padding-right:10px;">
								<input type="text" class="form-control"> <span
									class="input-group-btn">
									<button class="btn btn-default" type="button">Go!</button> </span>
							</div>
							<div class="input-group col-md-2"
								style="float:left;padding-right:10px;">
								<input type="text" class="form-control"> <span
									class="input-group-btn">
									<button class="btn btn-default" type="button">Go!</button> </span>
							</div>
							<div class="input-group col-md-2"
								style="float:left;padding-right:10px;">
								<input type="text" class="form-control"> <span
									class="input-group-btn">
									<button class="btn btn-default" type="button">Go!</button> </span>
							</div>
							<div class="input-group col-md-2"
								style="float:left;padding-right:10px;">
								<input type="text" class="form-control"> <span
									class="input-group-btn">
									<button class="btn btn-default" type="button">Go!</button> </span>
							</div>
							<button class="btn btn-success col-md-1 control-label">新增</button>
						</div>
					</div>

				</div>
				<div class="panel-heading">
					<h4>短信模板</h4>
				</div>
				<div class="panel-body">
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
						Vestibulum tincidunt est vitae ultrices accumsan. Aliquam ornare
						lacus adipiscing, posuere lectus et, fringilla augue.</p>
				</div>
			</div>
			<!-- END panel-->
		</div>
	</div>
</div>
<div id= "modaljsp">
<div id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel" class="modal-title">应用上线</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal"
					id="uploadAddressForm" action="updateSpInfo" method="post">
					<input type="hidden" value="${spInfo.spid }" name="spid">
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signuserphone">签名
							<span class="text-danger">*</span> </label>
						<div class="col-md-7">
							<input class="form-control" type="text" id="signature"
								name="signature" value="${spInfo.signature }" placeholder="签名"
								maxlength="15" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
				<button type="button" onclick="onlineSpinfo()"
					class="btn btn-primary">上线</button>
			</div>
		</div>
	</div>
</div>
</div>
