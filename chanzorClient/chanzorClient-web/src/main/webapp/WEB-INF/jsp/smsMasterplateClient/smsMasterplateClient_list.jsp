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

<div class='percentageList chanzorDataList'>
	<div class='chanzorDataList_title'>
		<p>模板管理</p>
	</div>
	<form class="form-inline" style='text-align: right; margin-right: 2%;'
		id="form1">
		<div class="form-group">
			<label for="telph" class='listLab'>模板标题：</label> <input type="text"
				class="form-control listInp" id="TITLE" name="TITLE" placeholder="">
		</div>
		<div class="form-group">
			<label for="telph" class='listLab'>模板内容：</label> <input type="text"
				class="form-control listInp" id="selcontent" name="selcontent" placeholder="">
		</div>
		<div class="form-group">
			<label for="telph" class='listLab'>模板状态：</label> <select
				class="form-control listInp" name="STATUS" id="STATUS">
				<option value="" selected="selected">请选择</option>
				<option value="3">未提交审核</option>
				<option value="1">审核中</option>
				<option value="0">审核通过</option>
				<option value="2">审核驳回</option>
			</select>
		</div>
		<div class="form-group">
			<label for="telph" class='listLab'>模板类型：</label> <select
				class="form-control listInp" name="SYSTEMORSPINFO"
				id="SYSTEMORSPINFO">
				<option value="" selected="selected">请选择</option>
				<option value="1">系统模板</option>
				<option value="2">应用模板</option>
			</select>
		</div>
		<checkLand:check type="sessionUser">
			<div class="form-group">
				<label for="telph" class='listLab'>应用名称：</label> <select
					class="form-control listInp" name="SP_ID" id="SP_ID">
					<option value="" selected="selected">请选择</option>
					<c:forEach var="spinfo" items="${spinfoList }">
						<option value="${spinfo.id}">${spinfo.sp_name}</option>
					</c:forEach>
				</select>
			</div>
		</checkLand:check>
		<button type="button" class="btn btn-default listBtn"
			onclick="search()">查询</button>
		<button type="button" class="btn btn-default listBtn"
			onclick="edit(-1)">新增</button>
		<button type="button" class="btn btn-default listBtn"
			onclick="importTxt()">导入</button>
		<button type="button" class="btn btn-default listBtn" style="width:100px" 
			onclick="exportPOI()">导出应用模板</button>
	</form>
	<table id="example"
		class="table table-striped table-bordered table-hover">
		<thead style="border-top: 1px #EEEEEE solid">
			<tr>
				<th width:250px>模板标题</th>
				<th width:250px>应用名称</th>
				<th width:300px>模板状态</th>
				<th width:100px>操作</th>
			</tr>
		</thead>
	</table>
</div>

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
					<h4 id="myModalLabel" class="modal-title">编辑模板</h4>
				</div>
				<div class="modal-body">
					<form class="js-validation-upload-auth form-horizontal"
						id="editMasterplate" method="post">
						<input class="form-control" type="hidden" id="id" name="id" />
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signusername">应用选择
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7" id="spnameSelect"></div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signusername">模板标题
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="title" name="title"
									placeholder="模板标题 " maxlength="100" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuseraddress">模板内容
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<textarea class="form-control" rows="5" cols="5" id="upcontent"
									name="content"></textarea>
							</div>
						</div>
						<!-- <div class="form-group">
							<label class="col-md-11 control-label" for="val-signuseraddress"><span
								class="text-danger">注：申请上线应用才可添加模板</span></label>
						</div> -->
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">保存</button>
							<button type="button" data-dismiss="modal"
								class="btn btn-default">关闭</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div id="myModalDetail" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-label="Close"
						class="close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="myModalDetailtitle" class="modal-title">模板详细</h4>
				</div>
				<div class="modal-body">
					<form class="js-validation-upload-auth form-horizontal"
						id="addtrialMasterplate" method="post">
						<input class="form-control" type="hidden" id="id" name="id" /> <input
							class="form-control" type="hidden" id="templeteId"
							name="templeteId" /> <input class="form-control" type="hidden"
							id="MasterTitlecontent" name="title" />
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signusername">应用选择
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7" id="valspnameSelect"></div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signusername">模板标题
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7" id="valtitle"></div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuseraddress">模板内容
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7" id="valcontent"></div>
						</div>
						<div class="modal-footer" id="valmodal"></div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div id="spinfomodal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-label="Close"
						class="close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="blacktitle" class="modal-title">批量导入模板</h4>
				</div>
				<div class="modal-body">
					<form class="js-validation-upload-auth form-horizontal"
						id="impotspinfoModalForm" method="post">
						<input class="form-control" type="hidden" id="id" name="ID" />
						<div class="form-group">
							<label class="col-md-4 control-label">请选择应用：</label>
							<div class="col-md-7">
								<select class="form-control" style="width: 205px;"
									name="impotspidname" id="impotspidname">
									<option value="" selected="selected">请选择</option>
									<c:forEach var="spinfo" items="${spinfoList }">
										<option value="${spinfo.id}">${spinfo.sp_name}</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label">选择文件（excel）：</label>
							<div class="col-md-7">
								<input id="file" type="file" filename="SPINFOMODAL" name="file"
									style="margin-top: 6px">
							</div>
						</div>

						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<a href="javascript:;" onclick="downLoadTemplete('excel')">Excel模板下载</a>
							</div>
						</div>

						<div class="modal-footer">
							<button class="btn btn-primary" type="submit">确定</button>
							<button type="button" data-dismiss="modal"
								class="btn btn-default">关闭</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</div>

