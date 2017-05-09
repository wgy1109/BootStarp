
<%@ page pageEncoding="UTF-8" import="com.chanzor.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class='right_top1'>
	<span>系统公告 :</span> <span>尊敬的用户，欢迎您登录用户自助平台，如有疑问，可点击销售支持或与我们直接联系！</span>
	<span><a href="#"
		style='font-size: 14px; line-height: 38px; color: #5c90d2; font-weight: 100;'>更多>></a></span>
</div>
<div class='right_top2'>
	<div class='right_top2_first1'>
		<div class='right_top2_first1_left'>
			<div style="width: 100%; height: 80%; text-align: center">
				<img
					src="${photo_img==null?'static/img/defaultPhoto.png': photo_img}"
					width="100" height="100" style="margin-top:15px">
			</div>
			<div style="text-align: center">
				<p>
					<img src="img/basicImg1.jpg" alt="" />
					<c:if test="${!empty baseInfo.email}">
						<img src="img/basicImg2.jpg" alt="" />
					</c:if>
					<c:if test="${empty baseInfo.email}">
						<img src="img/basicImg6.jpg" alt="" />
					</c:if>
					<img src="img/basicImg3.jpg" alt="" />
					<c:if test="${auth.status == 2}">
						<img src="img/basicImg4.jpg" alt="" />
					</c:if>
					<c:if test="${auth.status != 2}">
						<img src="img/basicImg8.jpg" alt="" />
					</c:if>
				</p>
			</div>

		</div>
		<div class='right_top2_first1_right'>
			<p>应用名称:</p>
			<p>${spInfo.sp_name}</p>
			<p>运营状态:</p>
			<c:choose>
				<c:when test="${spInfo.sp_through_status==4 }">
					<p>冻结</p>
				</c:when>
				<c:otherwise>
					<p>正常</p>
				</c:otherwise>
			</c:choose>
		</div>

	</div>
	<div class='right_top2_second2'>
		<div class='right_top2_second2_left'>
			<input type="hidden" id="spid" value="${spInfo.spid }">
			<p>
				<span>创建时间：</span>&nbsp;<span><fmt:formatDate
						value="${spInfo.createTime }" pattern="yyyy-MM-dd" /> </span>
			</p>
			<p>
				<span>App ID&nbsp;&nbsp;：</span>&nbsp;&nbsp;<span>${spInfo.username }</span>
			</p>
			<p>
				<span>App Key&nbsp;&nbsp;：</span>&nbsp;&nbsp;<span id="passwordHtml"
					data-type="hide">************</span>&nbsp;<img id="passwordimg"
					onclick="showPassword('${spInfo.password}')"
					style="cursor: pointer" src="static/img/eye.png" alt="" /> &nbsp;<img
					id="changePassword" onclick="changePass('${spInfo.spid}')"
					style="cursor: pointer" src="static/img/changePass.png" alt="" />
			</p>
			<p>
				<span>App IP&nbsp;&nbsp;：</span>&nbsp;&nbsp;<span id="appip"><c:if test="${spInfo.ip == '' or spInfo.ip == null }">---------</c:if><c:if test="${fn:length(spInfo.ip) > 15 }">${fn:substring(spInfo.ip, 0, 15)}</c:if><c:if test="${fn:length(spInfo.ip) <= 15 }">${spInfo.ip}</c:if></span>&nbsp;
					<img id="changeIP" onclick="updateIP('${spInfo.spid}','${spInfo.ip}')"
					style="cursor: pointer" src="static/img/changePass.png" alt="" />
			</p>
		</div>
		<div class='right_top2_second2_right'>
			<div class='right_top2_second2_right_top'>
				<a href="javascript:;"
					onclick="downloadInterFaceDoc(${spInfo.sp_service_type })">接口文档</a>
			</div>
			<div class='right_top2_second2_right_bottom'>
				<a href="javascript:;" onclick="showCompanyPhone()">客服帮助</a>
			</div>
		</div>
	</div>
	<div class='right_top2_third3'
		onclick="sendTestMessage(${spInfo.spid},${spInfo.sp_through_status},${spInfo.sp_service_type })"></div>
	<div class='right_top2_fourth4'>
		<div class='right_top2_fourth4_top'>
			<p>
			<div style="width: 50%; padding-left: 25px" class="pull-left">
				<span>剩余条数：<span style='font-weight: bold;' class="er">
						<c:choose>
							<c:when test="${spInfo.sp_service_type==1}">
								<span>${spInfo.leftover_num}条</span>
							</c:when>
							<c:when test="${spInfo.sp_service_type==2}">
								<span><fmt:formatNumber type="number"
										value="${spInfo.leftover_num/100}" pattern="0.00"
										maxFractionDigits="2" /> 元</span>

							</c:when>
							<c:when test="${spInfo.sp_service_type==3}">
								<span>${spInfo.leftover_num}条</span>
							</c:when>
						</c:choose>

				</span></span>
			</div>
			<div style="width: 50%" class="pull-left" style="padding-left:35px">
				<span>申请状态:</span><span style='font-weight: bold;' id="appOnlineStatus"><c:choose>
						<c:when test="${spInfo.sp_through_status ==0 }">未上线</c:when>
						<c:when test="${spInfo.sp_through_status==2 }">上线</c:when>
						<c:when test="${spInfo.sp_through_status==3 }">申请上线中</c:when>
						<c:when test="${spInfo.sp_through_status==22 }">未通过</c:when>
						<c:when test="${spInfo.sp_through_status==4}">冻结</c:when>
					</c:choose></span>
			</div>

		</div>
		<div class='right_top2_fourth4_bottom' id="spInfoConfigButton">
			<ul>
				<c:choose>
					<c:when test="${spInfo.sp_through_status==3}">
						<li>
							<div>
								<div style="background-image: url(static/img/walletgray.png)"></div>
								<p>充值</p>
							</div>
						</li>
					</c:when>
						<c:when test="${spInfo.sp_through_status==4}">
						<li>
							<div>
								<div style="background-image: url(static/img/walletgray.png)"></div>
								<p>充值</p>
							</div>
						</li>
					</c:when>
					<c:otherwise>
						<c:if test="${empty sessionScope.subAccountLogin}" >
							<li
							onclick="goPage('chargeRecord/directPaySpInfo?spId=${spInfo.spid}','static/js/spCharge/paySpInfo.js')">
							<div>
								<div></div>
								<p>充值</p>
							</div>
						</li>
						</c:if>
						<c:if test="${!empty sessionScope.subAccountLogin}" >
							<li title="暂未授权">
								<div>
									<div style="background-image: url(static/img/walletgray.png)"></div>
									<p>充值</p>
								</div>
							</li>
						</c:if>
					</c:otherwise>
				</c:choose>
				<li  
					onclick="reminderBalance(${spInfo.sp_service_type},${spInfo.spid })">
					<div></div>
					<p>余额提醒</p>
				</li>
				<c:choose>
					<c:when
						test="${spInfo.sp_through_status==0||spInfo.sp_through_status==22 }">
						<c:if test="${empty sessionScope.subAccountLogin}" >
						<li id="changeAppOnLineMth" onclick="onlineSpInfo(${spInfo.spid})">
							<div id="changeAppOnlineImg"></div>
							<p>申请上线</p>
						</li>
						<li id="changeUpdSpInfoMth" onclick="editSpInfo(${spInfo.spid})">
							<div id="changeUpdSpInfoImg"></div>
							<p>修改</p>
						</li>
						</c:if>
						<c:if test="${!empty sessionScope.subAccountLogin}" >
							<li id="changeAppOnLineMth" title="暂未授权">
							<div id="changeAppOnlineImg" style="background-image: url(static/img/mousegray.png)"></div>
								<p>申请上线</p>
							</li>
							<li id="changeUpdSpInfoMth" title="暂未授权">
								<div  id="changeUpdSpInfoImg" style="background-image: url(static/img/wrenchgray.png)"></div>
								<p>修改</p>
							</li>
						</c:if>
					</c:when>
					<c:otherwise>
						<li id="changeAppOnLineMth">
							<div id="changeAppOnlineImg" style="background-image: url(static/img/mousegray.png)"></div>
							<p>申请上线</p>
						</li>
						<li id="changeUpdSpInfoMth">
							<div  id="changeUpdSpInfoImg" style="background-image: url(static/img/wrenchgray.png)"></div>
							<p>修改</p>
						</li>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${sessionScope.LandingType=='SpInfo' }">
						<li id="changeDelSpInfoMth">
							<div id="changeDelSpInfoImg" style="background-image: url(static/img/bintgray.png)"></div>
							<p>删除应用</p>
						</li>
					</c:when>
					<c:when test="${spInfo.sp_through_status!=3 }">
					 	<c:if test="${empty sessionScope.subAccountLogin}" >
							<li  id="changeDelSpInfoMth"
								onclick="delSpInfo(${spInfo.spid},${spInfo.sp_through_status })">
								<div id="changeDelSpInfoImg"></div>
								<p>删除应用</p>
							</li>
						</c:if>
						<c:if test="${!empty sessionScope.subAccountLogin}" >
							<li id="changeDelSpInfoMth" title="暂未授权">
								<div id="changeDelSpInfoImg" style="background-image: url(static/img/bintgray.png)"></div>
								<p>删除应用</p>
							</li>
						</c:if>
					</c:when>
					<c:otherwise>
						<li  id="changeDelSpInfoMth">
							<div  id="changeDelSpInfoImg" style="background-image: url(static/img/bintgray.png)"></div>
							<p>删除应用</p>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>

	</div>
</div>
<!--昨日发送开始-->
<div class='right_top3'>
	<div class='right_top3_firstDiv'>
		<ul>
			<li>昨日发送(条)</li>
		</ul>
	</div>
	<div class='right_top3_secondDiv'>
		<ul>
			<li class='right_top3_secondDiv_firLi'>${spInfo.submitCount==null?0:spInfo.submitCount}</li>
			<li class='right_top3_secondDiv_secLi'>提交数量</li>
		</ul>
		<ul>
			<li class='right_top3_secondDiv_firLi'>${spInfo.sendedCount==null?0:spInfo.sendedCount}</li>
			<li class='right_top3_secondDiv_secLi'>下发数量</li>
		</ul>
		<ul>
			<li class='right_top3_secondDiv_firLi'>${spInfo.successCount==null?0:spInfo.successCount}</li>
			<li><span class='right_top3_secondDiv_secLi_firSp'>成功</span> <span
				class='right_top3_secondDiv_secLi_senSp'>(<fmt:formatNumber
						type="number"
						value="${spInfo.successCount/(spInfo.sendedCount==0?1:spInfo.sendedCount)*100}"
						maxFractionDigits="1" />%)
			</span></li>
		</ul>
		<ul>
			<li class='right_top3_secondDiv_firLi'>${spInfo.error_count==null?0:spInfo.error_count}</li>
			<li><span class='right_top3_secondDiv_secLi_firSp'>失败</span> <span
				class='right_top3_secondDiv_secLi_senSp1' style='color: red;'>(<fmt:formatNumber
						type="number"
						value="${spInfo.error_count/(spInfo.sendedCount==0?1:spInfo.sendedCount)*100}"
						maxFractionDigits="1" />%)
			</span></li>
		</ul>
		<ul>
			<li class='right_top3_secondDiv_firLi'>${spInfo.unKnownCount==null?0:spInfo.unKnownCount}</li>
			<li><span class='right_top3_secondDiv_secLi_firSp'>未知</span> <span
				class='right_top3_secondDiv_secLi_senSp1' style='color: red;'>(<fmt:formatNumber
						type="number"
						value="${spInfo.unKnownCount/(spInfo.sendedCount==0?1:spInfo.sendedCount)*100}"
						maxFractionDigits="1" />%)
			</span></li>
		</ul>
	</div>
</div>
<!--昨日发送结束-->
<!--日发送统计(条)开始-->
<div class="right_top5">
	<div class='right_top5_top'>
		<ul>
			<li>日发送统计(条)</li>
		</ul>
		<div class='right_top5_top_div'>
			<form id="searchDayForm">
				<input type="hidden" name="spId" value="${spInfo.spid }"> <span>时间：</span>
				<input type="text" name="dayTimeStart" id="dayTimeStart"
					placeholder="开始时间" class='form-control'
					style="width: 120px; height: 28px; float: left" />
				<div class='right_top5_top_div_first'></div>
				<input type="text" placeholder="结束时间" id="dayTimeEnd"
					name="dayTimeEnd" class='form-control'
					style="width: 120px; height: 28px; float: left" />
				<button class="btn btn-default listBtn" type="button" id="searchDay">查询</button>
			</form>
			<div class='right_top4_top_div_second' id="daySwitchTable"></div>
			<div class='right_top4_top_div_third' id="daySwitchChart"></div>
		</div>
	</div>
	<div class='right_top4_bottom' id="dayTableInfo">
		<table cellpadding='0' cellspacing='0' height='20px' id="daySendInfo">
			<tr>
				<td class='tbBg'>时间</td>
				<td class='tbBg'>提交数量</td>
				<td class='tbBg'>下发数量</td>
				<td class='tbBg'>成功</td>
				<td class='tbBg'>失败</td>
				<td class='tbBg lastCol'>未知</td>
			</tr>
		</table>
	</div>
	<div id="right_top5_bottom" class='right_top4_bottom'
		style="display: none"></div>
</div>



<div class='right_top4'>
	<div class='right_top4_top'>
		<ul>
			<li>月发送统计(条)</li>
		</ul>
		<div class='right_top4_top_div'>
			<form id="searchMonthForm">
				<input type="hidden" name="spId" value="${spInfo.spid }"> <span>时间：</span>
				<input type="text" name="monthTimeStart" id="monthTimeStart"
					placeholder="开始时间" class='form-control'
					style="width: 120px; height: 28px; float: left" />
				<div class='right_top4_top_div_first'></div>
				<input type="text" placeholder="结束时间" name="monthTimeEnd"
					id="monthTimeEnd" class='form-control'
					style="width: 120px; height: 28px; float: left" />
				<button class="btn btn-default listBtn" type="button"
					class="btn btn-default" id="searchMonth">查询</button>
			</form>
			<div class='right_top4_top_div_second' id="monthSwitchTable"></div>
			<div class='right_top4_top_div_third' id="monthSwitchChart"></div>
		</div>
	</div>
	<div class='right_top4_bottom' id="monthTableInfo">
		<table cellpadding='0' cellspacing='0' height='20px'
			id="monthSendInfo">
			<tr>
				<td class='tbBg'>时间</td>
				<td class='tbBg'>提交数量</td>
				<td class='tbBg'>下发数量</td>
				<td class='tbBg'>成功</td>
				<td class='tbBg'>失败</td>
				<td class='tbBg lastCol'>未知</td>
			</tr>
		</table>
	</div>
	<div id="right_top4_bottom" class='right_top4_bottom'
		style="display: none"></div>
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
							<div class="col-md-7" id="spInfoType">
								<input type="checkbox" checked target-input="website" value="1"
									name="sp_app_type_ids">网站 <input type="checkbox"
									target-input="app" value="2" name="sp_app_type_ids">移动应用
								<input type="checkbox" target-input="weixin" value="3"
									name="sp_app_type_ids">微信公众号 <input type="checkbox"
									target-input="other" name="sp_app_type_ids" value="4">其他
							</div>
						</div>
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
									name="sp_other_info" placeholder="微信公众号">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" id="editSpInfoButton"
							class="btn btn-primary">保存</button>
						<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
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
							<div class="col-md-9">
								<div id="preview"
									style="width: 200px; text-align: center; border: 1px #eee solid">
									<img id="imghead" width=100 height=100 border=0
										src='<%=request.getContextPath()%>static/img/preview.png'>
								</div>
								<input type="hidden" name="sp_signature_img"
									id="sp_signature_img">
							</div>
						</div>
						<div class="form-group align-center">
							<div class="col-md-9 col-md-offset-3">
								<input type="file" name="file" id="APPSIGNCERT_IMAGE"
									filename="APPSIGNCERT_IMAGE" class="btn btn-default"
									onchange="previewImage(this)" />
							</div>
						</div>
						<<div class="modal-footer">
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
					<h4 id="spInfoModelTitle" class="modal-title">测试短信</h4>
				</div>
				<form class="js-validation-app-update form-horizontal"
					id="testMessageForm" method="post">
					<div class="modal-body">
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-username">选择模版
							</label> <input type="hidden" value="${spInfo.spid}" name="spId">
							<div class="col-md-7">
								<select class="form-control" name="templateName"
									id="templateName"
									onchange="findTemplateContent(this,${spInfo.sp_through_status},'${spInfo.signature }')">
									<option value="0">请选择</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-username">手机号
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="testPhone"
									name="testPhone" placeholder="手机号" value="">
							</div>
						</div>
						<div class="form-group" id="weixin">
							<label class="col-md-4 control-label" for="val-website">短信内容<span
								class="text-danger">*</span></label>
							<div class="col-md-7">
								<textarea rows="10" class="form-control" cols="35"
									name="testMessage" id="testMessage"></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" id="editSpInfoButton"
							class="btn btn-primary">发送</button>
						<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="balanceReminde" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-label="Close"
						class="close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">余额提醒</h4>
				</div>
				<form class="js-validation-app-update form-horizontal"
					id="balanceRemindeForm" method="post">
					<div class="modal-body">
						<input type="hidden" value="" name="spId" id="balanceSpId">
						<input type="hidden" value="" name="id" id="balanceId">
						<div class="form-group">
							<label class="col-md-4 control-label" id="serviceBalance"></label>
							<div class="col-md-4 control-label">
								<input type="text" class="form-control" name="balance"
									id="balance">
							</div>
							<label class="col-md-4 pull-left" id="serviceBalance"><span
								id="unit"></span>时提醒</label>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-username">余额不足通知手机号码
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="reminderPhone"
									name="reminderPhone" placeholder="手机号" value="">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" id="editSpInfoButton"
							class="btn btn-primary">保存</button>
						<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="changePasswordModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-label="Close"
						class="close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">修改密码</h4>
				</div>
				<form class="js-validation-app-update form-horizontal"
					id="changePasswordForm" method="post">
					<div class="modal-body">
						<input type="hidden" value="" name="spId" id="changePasswordSpId">
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-username">密码
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="password"
									name="password" placeholder="密码" value="">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-username">再次确认密码
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<input class="form-control" type="text" id="password1"
									name="password1" placeholder="再次确认密码" value="">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" id="editSpInfoButton"
							class="btn btn-primary">保存</button>
						<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="updateIPModel" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-label="Close"
						class="close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">修改IP</h4>
				</div>
				<form class="js-validation-app-update form-horizontal" id="updateIPForm" method="post">
					<div class="modal-body">
						<input type="hidden" value="" name="updateIPSpId" id="updateIPSpId">
						<div class="form-group">
							<label class="col-md-2 control-label" for="val-username">IP
								<span class="text-danger"></span>
							</label>
							<div class="col-md-9">
								<textarea id="updateIP" cols="50" rows="5"
									name="updateIP" placeholder="绑定IP，多个IP之间用英文逗号‘,’隔开或换行隔开" maxlength='999'></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button"  class="btn btn-primary" onclick="updateIPsub()">保存</button>
						<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>