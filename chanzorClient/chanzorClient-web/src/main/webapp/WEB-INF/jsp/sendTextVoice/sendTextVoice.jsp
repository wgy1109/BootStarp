<%@ page pageEncoding="UTF-8" import="com.chanzor.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class='VoiceManage' style="height: 1050px;">
	<div class='chanzorDataList_title'>
		<p>发送文本语音</p>
	</div>
	<form method="post" id="voiceConvertForm" class="form-horizontal">
		<div class='messManage_top1'>
			<div class='voice_top1_left_title'>
				<span>第一步：选择发送的应用及定时发送时间</span>
			</div>
		</div>
		<div class='messManage_top1'>
			<div class='voice_top1_left_content'>
				<span>发送应用：</span> <select id="spId" name="spId"
					class='messManage_top1_right_inp'></select>
			</div>
			<div class='voice_top1_right_content'>
				<div style="float: left">
					<span>定时发送时间：</span>
				</div>
				<div style="float: left">
					<input type="text" id="timingTime" name="timingTime"
						class="form-control voice_top1_right_inp">
				</div>
				<div style="float: left">
					<span style="color: red">(若为空，表示立即发送)</span>
				</div>
			</div>
		</div>
		<div class='messManage_top1' style="padding-top: 20px">
			<div class='voice_top1_left_title'>
				<span>第二步:填写发送手机号（多个号码以英文","分隔）</span>
			</div>
		</div>
		<div class='voice_top2'>
			<div class='voice_top2_left_content'>
				<span>手机号码：</span>
			</div>
			<div class='messManage_top2_right'>
				<textarea id="mobile" name="mobile" rows="10" cols="55"
					placeholder="请输入手机号码" class="textareawidth"></textarea>
				<p>
					<span id="mobileLayerMsg" style='color: red;'></span>
				</p>
				<p style="padding: 10px 0 0 0;">
					<button type="button" class="btn btn-primary messManage_btn"
						onclick="exportPhone()">批量导入</button>
					<button type="button" class="btn btn-primary messManage_btn"
						onclick="filterPhone()">号码过滤</button>
				</p>
			</div>
		</div>
		<div class='messManage_top1'>
			<div class='voice_top1_left_title'>
				<span>第三步:填写文本内容</span>
			</div>
		</div>
		<div class='messManage_top1'>
			<div class='voice_top1_left_content'>
				<span>选择文本语音模版：</span> <select id="spsysMasterplate"
					name="spsysMasterplate" class='messManage_top1_right_inp'></select>
				<span style="color: red">(若选择文本语音模版则文本内容自动加载且无需再次转化语音)</span>
			</div>
		</div>
		<div class='voice_top3'>
			<div class='voice_top2_left_content'>
				<span>短信内容：</span>
			</div>
			<div class='messManage_top3_right'>
				<div id="priceHtml">
					<textarea cols="55" rows="10" id="plateInfocontent" name="content"
						placeholder="请输入短信内容" class="textareawidth"></textarea>
				</div>
				<p>
					<input type="hidden" id="hiddenSignature">
				</p>
				<p>
					<span id="resultcheckword" style='color: red;'></span>
				</p>
				<p style="padding: 10px 0 0 0">
					<button type="button" id="wordfilter"
						class="btn btn-primary messManage_top3_right_btn"
						onclick="checkWord()">敏感词过滤</button>
				<p>
			</div>
		</div>
		<div id="stepShow">
			<div class='messManage_top1'>
				<div class='voice_top1_left_title'>
					<span>第四步:文本转换语音</span>
				</div>
			</div>
			<div class='messManage_top1'>
				<div class='voice_top1_left_content'>
					<span>声音类型：</span> <input type="radio" name="voice_name"
						value="xiaoyu" checked=""><span></span> 男声 <input
						type="radio" name="voice_name" value="xiaoyan"><span></span>
					女声
				</div>
			</div>
			<div class='messManage_top1' style="margin-top: 30px">
				<div class='voice_top1_left_content'>
					<span>语速控制：</span> <input id="ex8" data-slider-id='ex1Slider'
						name="speed" type="text" data-slider-min="0" data-slider-max="100"
						data-slider-step="1" data-slider-value="50" />
				</div>
			</div>
			<div class='messManage_top1'>
				<div class='voice_top1_left_content'>
					<div style="float: left">
						<button type="button" class="btn btn-primary messManage_sub"
							onclick="changeVoice()">开始转换</button>
					</div>
					<div style="float: left; padding-top: 10px; display: none"
						id="audioPlugin">
						<audio style="padding-top: 20px" src="" type="audio/wav"
							autoPlay="autoPlay" controls="controls">
						</audio>
					</div>
				</div>
			</div>
		</div>
		<div class='voice_top3_left'>
			<div class='messManage_top3_right'>
				<p style="padding: 10px 0 0 0">
					<span>注:短信内容长度（<var id="smsLength">0</var>），短信计费长度（<var
							id="smsCount">0</var>），计费条数（<var id="smsChargeNum">0</var>）
					</span>
				</p>
				<p>
					<button type="submit" class="btn btn-primary messManage_sub">提交</button>
					<button type="reset" class="btn btn-primary messManage_sub"
						onclick="delCount()">重置</button>
				</p>
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
								style="margin-top: 6px">
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