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

<style>
.mt10{margin-top: 10px;}
.mt20{margin-top: 20px;}
.mt40{margin-top: 40px;}
.ml30{margin-left: 30px;}
.ml60{margin-left: 60px;}

.p10{padding:10px;}
.pb5{padding-bottom:5px;}
.pt10{padding-top:10px;}


.h-line{border-bottom:solid 1px #ccc;}
.h-center{text-align:center;}

.lic-wrap{margin-left:-430px; padding:10px 10px 20px 10px; width:860px; margin-top:70px; background:#fff;}
.lic-wrap button.btn-primary{background-color: #559AFA; border:none; width: 67px; height: 40px; text-align: center;}
.lic-wrap .lic-title{font-size:18px;}
.lic-wrap .lic-notice{}
.lic-wrap .lic-img-preview{background:#ccc; height:214px; width:391px; }
.lic-wrap .lic-btn-upload{}
.lic-wrap .lic-example{padding-left:10px; padding-top:10px;}
.lic-wrap .lic-example-img{}
.lic-wrap .lic-btns{}

</style>
<div class='chanzorDataList'>
        <div class='chanzorDataList_title'>
          <p>语音文件配置</p>
        </div>
        <form class="form-inline" style='text-align:right;margin-right:2%;' >
            <div class="form-group">
                <label for="spId" class='listLab' >&nbsp;&nbsp;&nbsp;&nbsp;选择应用：</label>
	            <select id="spId" name="spId" class="form-control m-b" style="width:150px">
	                <option value="">请选择</option>
					<c:forEach var="item" items="${appList}">
						<option value="${item.id}">${item.sp_name}</option>
					</c:forEach>
				</select>
            </div>
            <div class="form-group">
                <label for="templateName" class='listLab' >&nbsp;&nbsp;&nbsp;&nbsp;模板名称：</label>
	            <input type="search" class="form-control" name="templateName" id="templateName" placeholder="请输入模板名称" />
            </div>
            <button type="button" class="btn btn-default listBtn" onclick="javascript:search();">查询</button>
            <button type="button" class="btn btn-default listBtn" onclick="javascript:openUploadVoiceWin();" style="width:120px;">上传语音文件</button>
        </form>
        <table id="example"	class="table table-striped table-bordered table-hover">
				<thead style="border-top: 1px #EEEEEE solid">
					<tr>
						<th width="20%">应用名称</th>
						<th width="20%">模板名称</th>
						<th width="20%">上传时间</th>
						<th width="20%">审批状态</th>
						<th width="20%">操作</th>
					</tr>
				</thead>
        </table>
</div>


<!--以下为语音上传  -->
<form id="commonVoiceForm" >
<div id="commonVoiceModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="lic-wrap col-sm-12 ">
			<div class="col-sm-12 h-line pb5 pt10 lic-title ">语音上传</div>
			<div class="col-sm-12">
				<div class="col-sm-9 lic-notice mt10">
					<ul>
						<li><font style="font-size:12px;" color="#9d9d9d">1.请您上传有效的语音文件。文件大小应不大于1024KB。</font></li>
						<li><font style="font-size:12px;" color="#9d9d9d">2.语音文件格式要求wav。如果是其它格式请转换成wav</font></li>
						<li><font style="font-size:12px;" color="#9d9d9d">3.如果上传语音您的浏览器没有反应。强烈推荐您使用IE9版本以上的浏览器。</font></li>
					</ul>
					<div>
					    <label for="sp_id" class="control-label" style="float:left"><span class="text-danger">*</span>请选择应用：&nbsp;&nbsp;&nbsp;</label>
					    <div class="col-sm-5" >
					    	<select id="sp_id" name="sp_id" class="form-control m-b" style="width:300px">
								<c:forEach var="item" items="${appList}">
									<option value="${item.id}">${item.sp_name}</option>
								</c:forEach>
							</select>
					    </div><br><br>
					    <label for="template_name" class="control-label" style="float:left"><span class="text-danger">*</span>语音模板名称：</label>
					   
					    <div class="col-sm-5" >
					    	<input type="text" id="template_name" name="template_name" class="form-control" style="width:300px;height:30px" value="" />
					    </div>
					</div><br><br><br>
					
					<div>
					     <input type="hidden" id="id" name="id" value="" />
					     <input type="hidden" id="file_size" name="file_size" value="" />
					     <input type="hidden" id="track_length" name="track_length" value="" />
					     <input type="hidden" id="type" name="type" value="1" />
					     <input type="hidden" id="file_name" name="file_name"/>
					</div><br><br>
					 <div id="listenTestDiv" >
					 </div>
					
					<div class="lic-btn-upload mt20 h-center">
						<a href="javascript:clickVoiceUpload('commonVoiceFile')">点击上传语音文件</a>
						<input type="file" name="file" id="commonVoiceFile" style="display:none"  
										filename="commonVoiceFile" class="btn btn-default" onchange="uploadVoiceFile(this);" value="">
						
					</div>
				</div>
		     </div>
		     <div class="col-sm-12">
				<div class="col-sm-12 lic-example">
					<div class="lic-btns mt40">
					    <button class="btn sub-btn" type="button" onclick="javascript:saveVoiceFile();">提交</button>
						<button class="btn invalid-btn" type="button" onclick="javascript:closeModal('commonVoiceModal');">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</form>
<!--list listenTest -->
<div id="listListenTestDiv" style="display:none"></div>

<div id="rejectReasonModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade" >
	<div class="modal-dialog">
		<div class="lic-wrap col-sm-12 ">
			<div class="col-sm-12 h-line pb5 pt10 lic-title ">驳回原因</div>
			<div class="col-sm-12">
				<div class="col-sm-9 lic-notice mt10">
					    <label for="rejectReason" class="control-label" style="float:left">驳回理由：</label>
					    <div class="col-sm-5" >
					        <textarea id="rejectReason" name="rejectReason" class="form-control"  rows="4" class="textareawidth"></textarea>
					    </div>
				</div>
		     </div>
		     <div class="col-sm-12">
				<div class="col-sm-12 lic-example">
					<div class="lic-btns mt40">
						<button class="btn invalid-btn" type="button" onclick="javascript:closeModal('rejectReasonModal');">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

