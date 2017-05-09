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
<!-- Main section-->
<div class="content-wrapper">
	<div class="container-fluid">
		<!-- START DATATABLE 1 -->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
					<div class="col-sm-6"><h4>应用列表</h4></div>
					<form method="post" id="form1">
						<div class="dataTables_wrapper form-inline">
							<div class="row">
								<div class="col-sm-12">
									<div class="dataTables_filter">
										<label>应用名称:<input type="search" name="sp_name" id="sp_name"
											style="width:120px" class="form-control width120">&nbsp;&nbsp;&nbsp;</label> 
												<label>应用类型：<select id="sp_industry" name="sp_industry" class="form-control m-b">
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
					                            </select>&nbsp;&nbsp;&nbsp;</label> 
												<label>应用状态：<select id="sp_through_status" name="sp_through_status" class="form-control m-b">
			                                   <option value="-1">请选择</option>
			                                     <option value="0">未上线</option>
			                                     <option value="1">上线</option> 			                                    
 												<option value="3">申请上线中</option> 
 												<option value="22">未通过</option> 
			                              </select>&nbsp;&nbsp;&nbsp;</label> 
										<label><button class="btn btn-success" type="button"
												onclick="search()">查询</button> </label>
												<label><button class="btn btn-success" type="button"
												onclick="addSpInfo()">新增</button> </label>
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
									<th>应用名称</th>
									<th>类型</th>
									<th>剩余短信条数</th>
									<th>测试号码</th>
									<th>短信模板</th>
									<th>创建时间</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</section>
<!-- Page footer-->
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
				<h4 id="myModalLabel" class="modal-title">新增应用</h4>
			</div>
				<form class="js-validation-app-update form-horizontal"
							action="addSpInfoMth" method="post" id="insSpInfo">
			<div class="modal-body">
			
							<div class="form-group">
								<label class="col-md-4 control-label" for="val-username">应用名称
									<span class="text-danger">*</span>
								</label>
								<div class="col-md-7">
									<input class="form-control" type="text" id="sp_name"
										name="sp_name" placeholder="应用名称.."> 
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="val-skill">应用类型
									<span class="text-danger">*</span>
								</label>
								<div class="col-md-7">
									<select class="form-control" id="sp_type" name="sp_type">
										<option value="0">请选择</option>
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
									<select class="form-control" id="sp_industry"
										name="sp_industry">
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
								<label class="col-md-4 control-label" for="val-website">网站<span class="text-danger">*</span></label>
								<div class="col-md-7">
									<input class="form-control" type="text" id="sp_website"
										name="sp_website" placeholder="http://example.com">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" for="val-website">应用描述</label>
								<div class="col-md-8">
									<label class="css-input css-checkbox css-checkbox-primary"
										for="val-terms"> <textarea class="form-control" style="width:320px"
											id="sp_desc" name="sp_desc" rows="8"
											placeholder="请输入应用描述"></textarea> </label>
								</div>
							</div>
						
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
				<button type="submit" class="btn btn-primary">新增</button>
			</div>
			</form>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
	
</script>
