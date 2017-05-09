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
          <p>文本语音模板</p>
        </div>
        <form class="form-inline" style='text-align:right;margin-right:2%;' >
           <div class="form-group">
			<label for="telph" class='listLab'>模板标题：</label> <input type="text"
				class="form-control listInp" id="TITLE" name="TITLE" placeholder="">
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
				<label for="telph" class='listLab'>应用名称：</label> <select
					class="form-control listInp" name="SPID" id="SPID">
					<option value="" selected="selected">请选择</option>
					<c:forEach var="spinfo" items="${appList}">
						<option value="${spinfo.id}">${spinfo.sp_name}</option>
					</c:forEach>
				</select>
		  </div>
		  
            <button type="button" class="btn btn-default listBtn" onclick="javascript:search();">查询</button>
            <button type="button" class="btn btn-default listBtn" onclick="javascript:addTextVoice();">新增</button>
        </form>
        <table id="example"	class="table table-striped table-bordered table-hover">
				<thead style="border-top: 1px #EEEEEE solid">
					<tr>
						<th>模板标题</th>
						<th width="20%">应用名称</th>
						<th width="20%">创建时间</th>
						<th width="20%">审批状态</th>
						<th width="20%">操作</th>
					</tr>
				</thead>
        </table>
</div>


<!--以下为语音上传  -->
<form id="textVoiceForm" >
<div id="textVoiceModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="lic-wrap col-sm-12 ">
			<div class="col-sm-12 h-line pb5 pt10 lic-title ">文本语音</div>
			<div class="col-sm-12">
				<div class="col-sm-9 lic-notice mt10">
					<div>
					    <label for="sp_id" class="control-label" style="float:left"><span class="text-danger">*</span>选择应用：</label>
					    <div class="col-sm-5" >
					    	<select id="sp_id" name="sp_id" class="form-control m-b" style="width:300px">
								<c:forEach var="item" items="${appList}">
									<option value="${item.id}">${item.sp_name}</option>
								</c:forEach>
							</select>
					    </div><br><br>
					    <label for="title" class="control-label" style="float:left"><span class="text-danger">*</span>模板标题：</label>
					    <div class="col-sm-5" >
					    	<input type="text" id="title" name="title" class="form-control" style="width:300px;height:30px" value="" />
					    </div><br><br>
					    <label for="content" class="control-label" style="float:left"><span class="text-danger">*</span>模板内容：</label>
					    <div class="col-sm-5" >
					    	<textarea id="textVoiceContent" name="content" class="form-control"  rows="4" ></textarea>
					    </div><br><br><br><br>
					     <p style="padding:10px 100px 20px 100px"><span id="resultcheckword" style='color:red;'></span></p>
					     <p style="padding:10px 0 0 0 "><button type="button" id="wordfilter" class="btn btn-primary messManage_top3_right_btn" style="width:120px" onclick="checkWord()">敏感词过滤</button><p>
					    
					    <label for="voice_name_temp" class="control-label" style="float:left"><span class="text-danger">*</span>声音类型：</label>
					    <div class="col-sm-5" >
					    	 <input type="radio" name="voice_name_temp"  value="xiaoyu">男声 <input type="radio" name="voice_name_temp" checked value ="xiaoyan">女声
					    </div><br><br><br>
					    <label for="voice_name_temp" class="control-label" style="float:left"><span class="text-danger">*</span>语速控制：</label>
						<div class='col-sm-5'>
							   <input id="speed" data-slider-id='ex1Slider'
								name="speed" type="text" data-slider-min="0" data-slider-max="100"
								data-slider-step="1" data-slider-value="50" />
						</div>
					    <input type="hidden" id="voice_name" name="voice_name" value="" />
					    <input type="hidden" id="id" name="id" value="" />
					</div>
					 <div id="listenTestDiv" style="display:none">
					 </div>
				</div>
		     </div>
		     <div class="col-sm-12">
				<div class="col-sm-12 lic-example">
					<div class="lic-btns mt40">
					    <button class="btn sub-btn" type="button" onclick="javascript:listenTest();">试听</button>
					    <button class="btn sub-btn" type="button" onclick="javascript:save();">保存</button>
						<button class="btn invalid-btn" type="button" onclick="javascript:closeModal('textVoiceModal');">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</form>

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
