<%@ page pageEncoding="UTF-8" import="com.chanzor.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class='messManage'>
	<div class='chanzorDataList_title'>
		<p>发送定制短信</p>
	</div>
	<form method="post" id="sendCustomizedForm" class="form-horizontal">
		<div class='messManage_top1'>
			<div class='messManage_top1_left' style="width: 12%">
				<span>发送应用：</span>
			</div>
			<div class='messManage_top1_right'>
				<select id="spId" name="spId"
					class='messManage_top1_right_inp messManage_top1_right_inp1'></select>
			</div>
		</div>
		<div class='messManage_top2_right'
			style="margin-top: 20px; width: 55%">
			<div class='messManage_top3_left'>
				<span>短信内容：</span>
			</div>
			<input type="hidden" name="customeizedContent"
				id="customeizedContent" value="">
					<input type="hidden" name="redisKey"
				id="redisKey" value="">
			<div class='messManage_top3_right'>
				<div id="priceHtml">
					<textarea cols="55" rows="10" id="plateInfocontent"
						name="plateInfocontent" class="textareawidth" readOnly="true"></textarea>
				</div>
				<p style="padding: 10px 0 0 0">
					<button type="button" id="wordfilter"
						class="btn btn-primary messManage_top3_right_btn"
						onclick="exportExcel()" style="margin-left: 0px">导入EXCEL</button>
				<p>
				<p style="padding: 10px 0 0 0px;display:none"  id="hiddePhoneSize" >
					<span style="color:red">本次共导入<var id="smsChargeNum">0</var>个手机号，以上为预览内容。
					</span>
				</p>
				<p style="padding: 10px 0 0 0">
					<span>定时发送时间：</span><input type="search" id="timingTime"
						name="timingTime" class="messManage_top3_rightSp"> <span
						style='color: red;'>(若为空，表示立即发送)</span>
				</p>
				<p>
					<button type="submit" class="btn btn-primary messManage_sub" onkeydown="if(event.keyCode==32){return false;}">提交</button>
					<button type="reset" class="btn btn-primary messManage_sub"
						onclick="delCount()">重置</button>
				</p>
			</div>
		</div>
		<div class='messManage_top2'>
			<div class="promptMessage" style="margin-top: -55px;">
				<h2>注意：</h2>
				<br>
				<dl>
					<dd>1、 普通短信计费条数为 70 字，长短信为 67 字。</dd>
					<dd>2、 汉字、数字和英文都表示1个长度。</dd>
					<dd style="padding-top: 20px">
						<img alt="" src="../../static/img/excel3.jpg">
					</dd>
				</dl>
			</div>
		</div>
	</form>
</div>

<div id="modaljsp" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="blacktitle" class="modal-title">Excel导入</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal"
					id="impotMessageContent" method="post">
					<input class="form-control" type="hidden" id="id" name="ID" />
					<div class="form-group" id="fileArea">
						<label class="col-md-2 control-label">号码所在列：</label>
						<div class="col-md-10">
							EXCEL文件中的第 <select id="phoneIndex" name="phoneIndex">
								<option value="1">A</option>
								<option value="2">B</option>
								<option value="3">C</option>
								<option value="4">D</option>
								<option value="5">E</option>
								<option value="6">F</option>
								<option value="7">G</option>
								<option value="8">H</option>
								<option value="9">I</option>
								<option value="10">J</option>
								<option value="11">K</option>
								<option value="12">L</option>
								<option value="13">M</option>
								<option value="14">N</option>
								<option value="15">O</option>
								<option value="16">P</option>
								<option value="17">Q</option>
								<option value="18">R</option>
								<option value="19">S</option>
							</select>列(该列内容为接收用户的手机号码)
						</div>
					</div>
					<div class="form-group" id="fileArea">
						<label class="col-md-2 control-label">短信内容：</label>
						<div class="col-md-10">
							<textarea rows="8" cols="45" name="templateContent"
								id="templateContent"></textarea>
						</div>
					</div>
					<div class="form-group" id="fileArea">
						<label class="col-md-10 col-md-offset-2">将EXCEL文件中的第 <select
							id="insColumn">
								<option value="[(A)]">A</option>
								<option value="[(B)]" selected="selected">B</option>
								<option value="[(C)]">C</option>
								<option value="[(D)]">D</option>
								<option value="[(E)]">E</option>
								<option value="[(F)]">F</option>
								<option value="[(G)]">G</option>
								<option value="[(H)]">H</option>
								<option value="[(I)]">I</option>
								<option value="[(J)]">J</option>
								<option value="[(K)]">K</option>
								<option value="[(L)]">L</option>
								<option value="[(M)]">M</option>
								<option value="[(N)]">N</option>
								<option value="[(O)]">O</option>
								<option value="[(P)]">P</option>
								<option value="[(Q)]">Q</option>
								<option value="[(R)]">R</option>
								<option value="[(S)]">S</option>
						</select> 列
							<button type="button" class="btn" onclick="insertColumn()">插入</button>到消息内容中
						</label>
					</div>
					<div class="form-group" id="fileArea">
						<label class="col-md-5 control-label">请选择要导入的文件</label>
						<div class="col-md-7">
							<input type="file" id="templateFile" name="templateFile"
								onchange="ajaxFileUpload('templateFile','templateUrl')">
							<input type="hidden" id="templateUrl" name="templateUrl">
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