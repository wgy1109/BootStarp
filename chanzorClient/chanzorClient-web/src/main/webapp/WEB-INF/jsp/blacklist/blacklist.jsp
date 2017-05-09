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

 <!-- END Sidebar -->
    <div class='chanzorDataList'>
        <div class='chanzorDataList_title'>
          <p>黑名单列表</p>
        </div>
        <form class="form-inline" style='text-align:right;margin-right:2%;' >
            <div class="form-group">
                <label for="mdn" class='listLab'>手机号码：</label>
                <input type="text" class="form-control listInp"  id="mdn" name="mdn" placeholder="">
            </div>
            <checkLand:check type="sessionUser">
            <div class="form-group">
                <label for="effectApp" class='listLab' >&nbsp;&nbsp;&nbsp;&nbsp;生效应用：</label>
                                             <select id="spid" 
												name="spid" class="form-control m-b">
													<option value="">--全部--</option>
													<c:forEach var="item" items="${appList }">
														<option value="${item.id }">${item.sp_name }</option>
													</c:forEach>
											</select>
            </div></checkLand:check>
            <div class="form-group">
                <label for="blackTime" class='listLab'>&nbsp;&nbsp;&nbsp;&nbsp;时间：</label>
                <input type="text" class="form-control listInp" id="queryStartTime" name="queryStartTime" placeholder="开始时间" style='text-align:center;'  > -
            </div>
            <div class="form-group">
                <input type="text" class="form-control listInp" id="queryEndTime"  name="queryEndTime" placeholder="截止时间" style="text-align:center;">
            </div>
            <button type="button" class="btn btn-default listBtn" onclick="javascript:search();">查询</button>
            <button type="button" class="btn btn-default listBtn" onclick="javascript:edit();">新增</button>
            <button type="button" class="btn btn-default listBtn" onclick="exportPOI()">导出</button>
        </form>
        <table id="example"	class="table table-striped table-bordered table-hover">
				<thead style="border-top: 1px #EEEEEE solid">
					<tr>
						<th>手机号码</th>
						<th>生效应用</th>
						<th>创建时间</th>
						<th>备注</th>
						<th>操作</th>
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
					<h4 id="myModalLabel" class="modal-title">修改黑名单</h4>
				</div>
				<form class="js-validation-upload-auth form-horizontal"
					id="editForm" method="post">
					<div class="modal-body">

						<input type="hidden" name="id" id="edit_id">

						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signusername">手机号码
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="edit_mdn" name="mdn"
									placeholder="手机号码" maxlength="11" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuseraddress">生效应用
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<select id="edit_spid" name="spid" class="form-control m-b">
									<option value="0">请选择生效应用</option>
									<c:forEach var="item" items="${appList }">
										<option value="${item.id }">${item.sp_name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-signuserphone">备注
							</label>
							<div class="col-md-7">
								<textarea rows="3" cols="" class="form-control"
									id="edit_descption" name="descption" placeholder="备注"
									maxlength="500"></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">保存</button>
						<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	
</script>
