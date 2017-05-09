<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class='chanzorDataList'>
	<div class='chanzorDataList_title'>
		<p>发票管理</p>
	</div>
	<form class="form-inline" style='text-align: right; margin-right: 2%;'>
		<div class="form-group">
			<label for="INVOICE_STATUS" class='listLab'>&nbsp;&nbsp;&nbsp;&nbsp;发票类型：</label>
			<select class="form-control m-b" name="INVOICE_STATUS"
				id="INVOICE_STATUS">
				<option value="">请选择</option>
				<option value="0">增值税普通发票</option>
				<option value="1">增值税专用发票</option>
			</select>
		</div>
		<button type="button" class="btn btn-default listBtn"
			onclick="javascript:search();">查询</button>
		<button type="button" class="btn btn-default listBtn"
			onclick="javascript:edit();">新增</button>
	</form>
	<table id="example"
		class="table table-striped table-bordered table-hover">
		<thead style="border-top: 1px #EEEEEE solid">
			<tr>
				<th width="8%">发票金额(元)</th>
				<th width="10%">发票类型</th>
				<th>收件地址</th>
				<th width="8%">收件人姓名</th>
				<th width="8%">收件人电话</th>
				<th width="10%">创建时间</th>
				<th width="10%">快递信息</th>
				<th width="10%">发票状态</th>
				<th width="12%">操作</th>
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
					<h4 id="myModalLabel" class="modal-title">新增发票</h4>
				</div>
				<div class="modal-body">
					<form class="js-validation-upload-auth form-horizontal"
						id="addFinanceInvoice" method="post">

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signusername">发票抬头
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7" id="accountName"></div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuseraddress">可开发票金额
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="Money" name="Money"
									readonly="readonly" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">&nbsp; </label>
							<div class="col-md-7">
								<span class="text-danger">(消费单次/累积满5000元以上才可开具发票)</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signusername">发票类型
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input type="radio" name="invoiceStatus" id="invoiceStatus0"
									value="0" onclick="javascript:changeFinanceInvoiceType();" />增值税普通发票&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="invoiceStatus" id="invoiceStatus0"
									value="1" onclick="javascript:changeFinanceInvoiceType();" />增值税专用发票
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuseraddress">开发票金额
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="invoiceValue"
									name="invoiceValue" maxlength="10" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuseraddress">收件人姓名<span
								class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="COLLECT_NAME"
									name="COLLECT_NAME" maxlength="10" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuseraddress">收件人地址
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="COLLECT_ADDRESS"
									name="COLLECT_ADDRESS" placeholder="收件人地址" maxlength="150" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuseraddress">收件人电话
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="COLLECT_PHONE"
									name="COLLECT_PHONE" placeholder="收件人电话 " maxlength="11" />
							</div>
						</div>

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
</div>


<div id="explain" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel" class="modal-title">问题说明</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal"
					id="explainForm" method="post">
					<input type="hidden" id="explainId" name="id">
					<div class="form-group">
						<label class="col-md-4 control-label">说明：</label>
						<div class="col-md-7">
							<!-- <input type="text" id="INVOICE_COURIER_NUM"
								name="INVOICE_COURIER_NUM" value="" class="form-control"> -->
							<textarea class='form-control' rows='6' id="INVOICE_NOTES"
								name="INVOICE_NOTES" maxlength="255" value="问题"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<div id="expressInfo" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel" class="modal-title">快递信息</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal"
					id="expressInfoForm" method="post">
					<input type="hidden" id="explainId" name="id">
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
			</div>
		</div>
	</div>
</div>