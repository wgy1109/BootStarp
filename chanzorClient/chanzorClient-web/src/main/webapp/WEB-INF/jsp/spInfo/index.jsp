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
<script type="text/javascript">
	var LandingType = "${sessionScope.LandingType}";
</script>
<div class='chanzorDataList'>
	<div class='chanzorDataList_title'>
		<p>应用列表</p>
	</div>
	<div class="form-inline" style='text-align: right; margin-right: 2%;'>
		<checkLand:check type="sessionUser">
			<div class="form-group">
				<label for="telph" class='listLab'>应用名称：</label> <input type="text"
					class="form-control listInp" id="sp_name" name="sp_name"
					placeholder="">
			</div>
			<div class="form-group">
				<label for="telph" class='listLab'>应用状态：</label> <select
					class="form-control listInp" name="sp_through_status"
					id="sp_through_status">
					<option value="-1" selected="selected">请选择</option>
					<option value="0">未上线</option>
					<option value="3">申请上线</option>
					<option value="2">上线</option>
					<option value="22">驳回</option>
					<option value="4">冻结</option>
				</select>
			</div>
			<div class="form-group">
				<label for="telph" class='listLab'>应用类型：</label> <select
					class="form-control listInp" name="sp_service_type"
					id="sp_service_type">
					<option value="0" selected="selected">请选择</option>
					<option value="1">国内短信</option>
					<option value="2">国际短信</option>
					<option value="3">语音短信</option>
				</select>
			</div>
			<button type="button" class="btn btn-default listBtn"
				onclick="search()">查询</button>
			<c:if test="${empty sessionScope.subAccountLogin}">
				<button type="button" class="btn btn-default listBtn"
					onclick="editSpInfo()">新增</button>
			</c:if>
		</checkLand:check>
	</div>
	<table id="example"
		class="table table-striped table-bordered table-hover">
		<thead
			style="border-top: 1px #EEEEEE solid; background-color: #0099CB">
			<tr class="dateTable-th">
				<th>业务类型</th>
				<th>APP ID</th>
				<th>应用名称</th>
				<th>余额</th>
				<th>签名</th>
				<th>上线状态</th>
				<th>运营状态</th>
				<th width="350px">操作</th>
			</tr>
		</thead>
	</table>
</div>
<div id="modaljsp">
	<div id="addSpInfo" tabindex="-1" role="dialog"
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
							<label class="col-md-4 control-label" for="val-skill">业务类型
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<select class="form-control" id="sp_service_type"
									name="sp_service_type">
									<option value="0" selected>请选择</option>
									<option value="1">国内短信</option>
									<option value="2">国际短信</option>
									<option value="3">语音短信</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-username">应用名称
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-4">
								<input class="form-control" type="text" id="sp_name"
									name="sp_name" placeholder="建议输入产品名称" value="">
							</div>
							<div class="col-md-3" id="suffix">
								<select class="form-control" id="sp_fix_name" name="sp_fix_name">
									<option value="0">请选择</option>
									<option value="验证码">验证码</option>
									<option value="通知">通知</option>
									<option value="营销">营销</option>
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
						<input type="hidden" id="spid" name="spid">
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-skill">应用类型
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7" id="spInfoType">
								<input type="checkbox" checked target-input="website" value="1"
									name="sp_app_type_ids">网站 <input type="checkbox"
									target-input="app" value="2" name="sp_app_type_ids">移动应用
								<input type="checkbox" target-input="weixin" value="3"
									name="sp_app_type_ids">微信公众号 <input type="checkbox"
									target-input="other" name="sp_app_type_ids" value="4">其他
							</div>
						</div>
						<div class="form-group" id="website">
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
								class="text-danger">*</span></label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="app_input"
									name="sp_app_info" placeholder="App名称">
							</div>
						</div>
						<div class="form-group" id="weixin" style="display: none">
							<label class="col-md-4 control-label" for="val-website">微信公众号<span
								class="text-danger">*</span></label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="weixin_input"
									name="sp_weixin_info" placeholder="微信公众号">
							</div>
						</div>
						<div class="form-group" id="other" style="display: none">
							<label class="col-md-4 control-label" for="val-website">其他<span
								class="text-danger">*</span></label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="sp_other_info"
									name="sp_other_info" placeholder="其他">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" id="editSpInfoButton" class="btn sub-btn">保存</button>
						<button type="button" data-dismiss="modal" class="btn invalid-btn">关闭</button>
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
							<label class="col-md-3 control-label" for="val-signuserphone">签名
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-9">
								<input class="form-control" type="text" id="signature"
									style="float: left; width: 200px;" name="signature"
									placeholder="签名" maxlength="15" style="width: 200px" /><span
									style="color: red; line-height: 30px; padding: 0 10px 0 10px;">注:请在【】内输入您的签名</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">选择签名证明材料 <span
								class="text-danger">*</span>
							</label>
							<div class="col-md-5">
								<div id="preview"
									style="width: 200px; text-align: center; border: 1px #eee solid">
									<img id="imghead" width=100 height=100 border=0
										src='<%=request.getContextPath()%>static/img/preview.png'>
								</div>

								<input type="hidden" name="sp_signature_img"
									id="sp_signature_img">
							</div>
							<div class="col-md-4">
								<img style="display: none" id="signExplain_SRC_imghead"
									width=100 height=80 border=0 src='/static/img/signExplain.png'
									onclick="showImage(this)"> <a href="#"
									onClick="javascript:previewPic('signExplain_SRC_imghead');"
									style="float: left;">什么是签名证明材料？</a>
							</div>
						</div>
						<div class="form-group align-center">
							<div class="col-md-9 col-md-offset-3">
								<input type="file" name="file" id="APPSIGNCERT_IMAGE"
									filename="APPSIGNCERT_IMAGE" class="btn btn-default"
									onchange="previewImage(this)" />
							</div>
						</div>
							<div class="form-group align-center">
							<div class="col-md-12">
					<span style="color:red">注:应用上线后需要在应用详情页面绑定IP，如果不添加接口调用会受到3分钟3条的限制
					</span>
							</div>
						</div>
						<div class="modal-footer">
							<div class="form-group">
								<label class="col-md-3 control-label">其他证明材料 </label>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">公司关系证明 </label>
								<div class="col-md-9">

									<label class="col-md-2 control-label"> <a
										id="uploadRelationBtn" class="pull-left" href="javascript:;">资料上传
									</a></label> <input type="file" name="file" id="sp_relation_prove"
										style="display: none" filename="RELATION_PROVE_FILE"
										onchange="uploadAuth(this,'uploadRelationBtn')" /> <input
										type="hidden" name="sp_relation_prove"> <label
										class="col-lg-8 col-lg-offset-2 control-label"><a
										class="pull-left"
										href="<%=request.getContextPath()%>/static/word/公司关系证明.docx">模版下载</a>
									</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">授权委托书 </label>
								<div class="col-md-9">
									<label class="col-md-2 control-label"><a
										id="uploadDomainBtn" class="pull-left" href="javascript:;">资料上传
									</a> </label> <input type="file" id="sp_domain_auth" name="file"
										style="display: none" filename="DOMAIN_AUTH_FILE"
										onchange="uploadAuth(this,'uploadDomainBtn')" /> <input
										type="hidden" name="sp_domain_auth"> <label
										class="col-lg-4 col-lg-offset-2 control-label"><a
										class="pull-left"
										href="<%=request.getContextPath()%>/static/word/授权委托书.docx">模版下载</a>
									</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label">签名确认函 </label>
								<div class="col-md-9">
									<label class="col-md-2 control-label"><a
										id="uploadSignatureBtn" class="pull-left" href="javascript:;">资料上传
									</a> </label> <input type="file" id="sp_signature_auth"
										class="proof-upload" name="file"
										filename="SIGNATURE_AUTH_FILE" style="display: none"
										onchange="uploadAuth(this,'uploadSignatureBtn')" /> <input
										type="hidden" name="sp_signature_auth"> <label
										class="col-lg-4  col-lg-offset-2 control-label"><a
										class="pull-left"
										href="<%=request.getContextPath()%>/static/word/签名确认函.doc">模版下载</a>
									</label>
								</div>
							</div>
							<button type="submit" class="btn sub-btn">上线</button>
							<button type="button" data-dismiss="modal"
								class="btn invalid-btn">关闭</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>
	<div id="testMessageModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-label="Close"
						class="close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="spInfoModelTitle" class="modal-title">公告</h4>
				</div>
				<div class="modal-body">
					<div>
						近期全国整治通讯资源，重点清理语音专线码号违规传送。由于运营商对语音专线的治理影响，导致部分地区用户无法接听到“语音通知”，给广大用户带来的不便，敬请谅解！
						畅卓本着“用户体验至上”的原则，目前将尽最大努力保证在网用户的正常使用，并暂停新增用户的语音充值功能。
						暂停期间，我司将升级语音业务技术，提升监测拦截与数据分析功能。
						待运营商语音专线通讯恢复正常后，我司将第一时间重启“语音业务”，感谢各位用户的支持与配合，谢谢！</div>
					<div style="text-align: right">北京畅卓科技有限公司</div>
					<div style="text-align: right">
						<span>2016年9月24日</span>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>