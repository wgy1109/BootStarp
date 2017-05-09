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
<div class=chanzorDataList>
	<div class="chanzorDataList_title">
		<p>消费明细</p>
	</div>
	<form class="form-inline" style='text-align: right; margin-right: 2%;'>
		<div class="dataTables_wrapper form-inline">
			<div class="row">
				<div class="col-sm-12">
					<div class="dataTables_filter">
						<checkLand:check type="sessionUser">
							<label>应用名称:<input type="search" name="sp_name"
								id="sp_name" style="width: 120px" class="form-control width120">&nbsp;&nbsp;&nbsp;
							</label>
						</checkLand:check>
						<label>消费类型：<select id="type" name="type"
							class="form-control m-b">
								<option value="0">请选择</option>
								<option value="1">充值</option>
								<option value="3">审核驳回</option>
								<option value="4">扣除</option>
								<option value="5">失败返还</option>
								<option value="6">赠送</option>
								<option value="7">透支</option>
						</select>&nbsp;&nbsp;&nbsp;
						</label>
						<checkLand:check type="sessionUser">
							<label>业务类型：<select id="sp_service_type"
								name="sp_service_type" class="form-control m-b">
									<option value="0">请选择</option>
									<option value="1">国内短信</option>
									<option value="2">国际短信</option>
									<option value="3">语音短信</option>
							</select>&nbsp;&nbsp;&nbsp;
							</label>
						</checkLand:check>
						<label>开始时间：<input type="search" id="queryStartTime"
							name="queryStartTime" style="width: 110px" readonly="readonly" class="form-control" value="${requestScope.queryStartTime }" >&nbsp;&nbsp;&nbsp;
						</label> <label>-<input type="search" id="queryEndTime"
							name="queryEndTime" style="width: 110px" readonly="readonly" class="form-control">&nbsp;&nbsp;&nbsp;
						</label> <label><button class="btn listBtn" type="button"
								onclick="search()">查询</button> </label>
					</div>
				</div>
			</div>
		</div>
	</form>
	<table id="example"
		class="table table-striped table-bordered table-hover">
		<thead style="border-top: 1px #EEEEEE solid">
			<tr>
				<th>应用名称</th>
				<th>消费类型</th>
				<th>出入账金额</th>
				<th>出入账条数</th>
				<th>充值时间</th>
				<th>业务类型</th>
				<th>备注</th>
			</tr>
		</thead>
	</table>
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
					<h4 id="spInfoModelTitle" class="modal-title">新增应用</h4>
				</div>
				<form class="js-validation-app-update form-horizontal"
					id="insSpInfo" method="post">
					<div class="modal-body">
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-username">应用名称
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="sp_name"
									name="sp_name" placeholder="应用名称.." value="">
							</div>
						</div>
						<input type="hidden" id="spid" name="spid">
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-skill">应用类型
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<select class="form-control" id="sp_app_type"
									onchange="changeWebSite()" name="sp_app_type">
									<option value="0" selected>请选择</option>
									<option value="1">网站</option>
									<option value="2">移动应用</option>
									<option value="3">微信公众号</option>
									<option value="4">其他</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-skill">应用行业
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<select class="form-control" id="sp_industry" name="sp_industry">
									<option value="0">请选择</option>
									<option value="1">电子商务</option>
									<option value="2">金融</option>
									<option value="3">在线社区</option>
									<option value="4">房地产</option>
									<option value="5">医疗</option>
									<option value="6">交通汽车</option>
									<option value="7">旅游</option>
									<option value="8">游戏</option>
									<option value="9">教育</option>
									<option value="10">IT硬件</option>
									<option value="11">IT软件服务</option>
									<option value="12">文化出版</option>
									<option value="13">生活信息</option>
									<option value="14">其他</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-website">网站<span
								class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="sp_website"
									name="sp_website" placeholder="http://example.com">
							</div>
						</div>
						<div class="form-group" id="app" style="display: none">
							<label class="col-md-4 control-label" for="val-website">App名称<span
								class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="app_input"
									name="sp_app_info" placeholder="App名称">
							</div>
						</div>
						<div class="form-group" id="weixin" style="display: none">
							<label class="col-md-4 control-label" for="val-website">微信公众号<span
								class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="weixin_input"
									name="sp_app_info" placeholder="微信公众号">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-website">应用描述</label>
							<div class="col-md-8">
								<label class="css-input css-checkbox css-checkbox-primary"
									for="val-terms"> <textarea class="form-control"
										style="width: 320px" id="sp_desc" name="sp_desc" rows="8"
										placeholder="请输入应用描述"></textarea>
								</label>
							</div>
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
						<button type="submit" id="editSpInfoButton"
							class="btn btn-primary">新增</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="onlineModel" tabindex="-1" role="dialog"
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
						id="uploadAddressForm" method="post">
						<input type="hidden" id="spid" name="spid">
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuserphone">签名
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="signature"
									name="signature" value="${spInfo.signature }" placeholder="签名"
									maxlength="15" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">选择签名证明材料 <span
								class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<select class="form-control" name="sp_signature_type">
									<option value="1">请选择</option>
									<option value="2">商标证书</option>
									<option value="3">商标受理单</option>
									<option value="4">微信公众号备案截图</option>
									<option value="5">网站备案截图</option>
									<option value="6">商标授权函</option>
								</select>
							</div>
						</div>
						<div class="form-group align-center">
							<div class="col-md-8 col-md-offset-4">
								<input type="file" name="file" id="APPSIGNCERT_IMAGE"
									filename="APPSIGNCERT_IMAGE" class="btn btn-default"
									onchange="previewImage(this)" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-8 col-md-offset-4">

								<div id="preview" style="display: none">
									<img id="imghead" width=100 height=100 border=0
										src='<%=request.getContextPath()%>/images/defaul.jpg'>
								</div>
								<input type="hidden" name="sp_signature_img"
									id="sp_signature_img">
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal"
								class="btn btn-default">关闭</button>
							<button type="submit" class="btn btn-primary">上线</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>
</div>