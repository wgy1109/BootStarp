<%@ page pageEncoding="UTF-8" import="com.chanzor.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>

 .sendVoice_outDiv {
    margin-left:58px;
    height:300px;
    /*background-color: green;*/
    margin-right:149px;
}

</style>
   <div class='chanzorDataList' style="height:950px">
        <div class='chanzorDataList_title'>
          <p>发送语音文件</p>
        </div>
        <div class='sendVoice_outDiv'>
            <p><span>第一步：选择配置的语音文件</span></p>
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
	        </form>
	        <form method="post" id="voiceSendForm" class="form-horizontal">
	        <input type="hidden" id="voiceFileId" name="voiceFileId" value="" />
	        <table id="example"	class="table table-striped table-bordered table-hover">
					<thead style="border-top: 1px #EEEEEE solid">
						<tr>
						    <th width="5%"></th>
						    <th width="20%">应用名称</th>
							<th width="20%">模板名称</th>
							<th width="20%">上传时间</th>
							<th width="20%">审批状态</th>
							<th width="15%">操作</th>
						</tr>
					</thead>
	        </table>
            <p>第二步：选择定时发送时间</p>
            <div class='sendVoice_outDiv3' style="padding-top:20px">
                <div style="float:left">
                  <span>定时发送时间：</span>       
                 </div>
                 <div style="float:left">
                    <input type="text" id="timingTime" name="timingTime" class="form-control voice_top1_right_inp">
                 </div>
                  <div style="float:left">
                     <span style="color:red">(若为空，表示立即发送)</span>
                  </div>
                <span>(若为空，表示立即发送)</span>
            </div><br>
             
            <p>第三步：填写发送手机号(多个号码以英文","分隔)</p>
            <div class='voice_top2'>
                <div class='voice_top2_left_content' style="padding-left:0px">
                    <span>手机号码：</span>
                </div>
                <div class='messManage_top2_right'>
                    <textarea id="mobile" name="mobile" rows="10" cols="55" placeholder="请输入手机号码" class="textareawidth"></textarea>
                    <p><span id="mobileLayerMsg" style='color:red;'></span></p>
                    <p style="padding: 10px 0 0 0;">
                        <button type="button" class="btn btn-primary messManage_btn" onclick="exportPhone()" style="margin-left: 235px;">批量导入</button>
                        <button type="button" class="btn btn-primary messManage_btn" onclick="filterPhone()" style="margin-left:10px">号码过滤</button>
                    </p>
                </div>
            </div>

 			<div class='voice_top3_left'>
              <div class='messManage_top3_right'>
                    <p>
                        <button type="submit" class="btn btn-primary messManage_sub" >提交</button>
                    </p>
              </div>
           </div>
           </form>
        </div>
  
</div>
<!--list listenTest -->
<div id="listListenTestDiv" style="display:none"></div>

<div id="modaljsp" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="blacktitle" class="modal-title">批量导入手机号</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal"
					id="impotPhoneListForm" method="post">
					<input class="form-control" type="hidden" id="id" name="ID" />
					<div class="form-group" id="fileArea">
						<label class="col-md-4 control-label">选择文件（excel、txt）：</label>
						<div class="col-md-7">
							<input id="file" type="file" filename="PHONEINFO" name="file"
								style="margin-top:6px">
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-3 col-md-offset-4">
							<a href="javascript:;" onclick="downLoadTemplete('excel')">Excel模板下载</a>
						</div>
						<div class="col-md-3">
							<a href="javascript:;" onclick="downLoadTemplete('txt')">txt模板下载</a>
						</div>
					</div>

					<div class="modal-footer">
						<button class="btn btn-primary" type="submit">确定</button>
						<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
