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
	<!-- 尊敬的用户，欢迎您登录用户自助平台，如有疑问，可点击销售支持或与我们直接联系！ -->
	<span>系统公告 :</span> <span id="sysNotice"></span> <span><a
		href="javaScript:;" onclick="goPage('<%=basePath%>noticeList.html','static/js/notice/index.js')"
		style='font-size: 14px; line-height: 38px; color: #5c90d2; font-weight: 500;'>更多>></a></span>
</div>
<div class='all'>
	<div class='firstDiv'>
		<div class='firstDiv_left'>
			<div style="width: 60%; height: 100%; float: left">
				<div style="width: 100%; height: 70%; text-align: center">
					<div>
						<img
							src="${photo_img==null?'static/img/defaultPhoto.png': photo_img}"
							width="100" height="120" style="padding-top: 20px">
					</div>
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
					<p>
						<span class='fisrtDiv_firstDiv_left_pSp'>${account_name}</span>
					</p>
				</div>
			</div>
			<div style="height: 100%; width: 40%; float: left">
				<ul>
					<li class="firstDiv_left_fir"><p>
							<a href="javascript:;"
								onclick="goPage('chargeRecord/chargeRecordIndex.html','static/js/chargeRecord/index.js')">订单列表</a>
						</p>
					<li class="firstDiv_left_sec"><p>
							<a href="javascript:;"
								onclick="goPage('consumerDetail/spConsumer.html','static/js/spConsumerDetail/ConsumeIndex.js')">消费明细</a>
						</p>
					<li class='firstDiv_left_thi'>
						<p>
							<a href="javascript:;" onclick="helpbtn()">意见反馈</a>
						</p>
					</li>
				</ul>
			</div>
		</div>
		<div class='firstDiv_right'>
			<ul>
				<li id='firstLi' class='spaceDiv'>
					<div id="testgndx" title='发送国内测试短信' name="testgndx"
						onmouseover="changeMessageImgOver(this,'gndxl.png')"
						style="width: 94px; height: 75px; text-align: center;"
						onmouseout="changeMessageImgOut(this,'gndxh.png')">
						<img alt="" src="static/img/gndxh.png ">
						<p>国内短信</p>
					</div>
				</li>
				<li id='secondLi' class='spaceDiv'>
					<div id="testgjdx" name="testgjdx" title='发送国际测试短信'
						style="width: 94px; height: 75px; text-align: center;"
						onmouseover="changeMessageImgOver(this,'gjdxl.png')"
						onmouseout="changeMessageImgOut(this,'gjdxh.png')">
						<img alt="" src="static/img/gjdxh.png ">
						<p>国际短信</p>
					</div>
				</li>
				<li id='thirdLi'>
					<div
						style='background-color: #559AFA; width: 100px; height: 30px; position: absolute; top: -42px; right: -70px; text-align: center;'
						class='spaceDiv';>
						<a href="#" style='color: white; line-height: 30px;'>体验中心</a>
					</div>
					<div id="testyydx" name="testyydx" title='发送语音测试短信'
						style="width: 94px; height: 75px; text-align: center;"
						onmouseover="changeMessageImgOver(this,'yyyzml.png')"
						onmouseout="changeMessageImgOut(this,'yyyzmh.png')">
						<img alt="" src="static/img/yyyzmh.png">
						<p>语音验证码</p>
					</div>
				</li>
			</ul>
		</div>
	</div>
	<div class='secondDiv'>
		<c:forEach items="${spList}" var="spInfo">
			<div class='secondDiv_middle'>
				<c:choose>
					<c:when test="${spInfo.sp_service_type==1 }">
						<div class='secondDiv_left_top'>
							<p>${fn:substring(spInfo.sp_name, 0, 10)}<span style="margin-left:25px;">${spInfo.username}</span></p>
						</div>
					</c:when>
					<c:when test="${spInfo.sp_service_type==2 }">
						<div class='interDiv_left_top'>
							<p>${fn:substring(spInfo.sp_name, 0, 10)}<span style="margin-left:25px;">${spInfo.username}</span></p>
						</div>
					</c:when>
					<c:when test="${spInfo.sp_service_type==3 }">
						<div class='voiceDiv_left_top'>
							<p>${fn:substring(spInfo.sp_name, 0, 10)}<span style="margin-left:25px;">${spInfo.username}</span></p>
						</div>
					</c:when>
				</c:choose>

				<div class='secondDiv_left_bottom'>
					<c:choose>
						<c:when test="${spInfo.sp_service_type==1 }">
							<div class='secondDiv_left_bottom_bor1'>
						</c:when>
						<c:when test="${spInfo.sp_service_type==2 }">
							<div class='interDiv_middle_bottom_bor1'>
						</c:when>
						<c:when test="${spInfo.sp_service_type==3 }">
							<div class='voiceDiv_right_bottom_bor1'>
						</c:when>
					</c:choose>

					<p class='p1'>
						<span>昨日用量</span>
					</p>
					<p class='p2'>
						<span>${spInfo.yesterDaySendTask }</span>
					</p>
					<c:choose>
						<c:when test="${spInfo.sp_service_type==1}">
							<p class='p1'>
								<span>剩余条数</span>
							</p>
							<p class='p2'>
								<span>${spInfo.leftover_num}条</span>
							</p>
						</c:when>
						<c:when test="${spInfo.sp_service_type==2}">
							<p class='p1'>
								<span>剩余金额</span>
							</p>
							<p class='p2'>
								<span><fmt:formatNumber type="number"
										value="${spInfo.leftover_num/100}" pattern="0.00"
										maxFractionDigits="2" /> 元</span>
							</p>
						</c:when>
						<c:when test="${spInfo.sp_service_type==3}">
							<p class='p1'>
								<span>剩余条数</span>
							</p>
							<p class='p2'>
								<span>${spInfo.leftover_num}条</span>
							</p>
						</c:when>
					</c:choose>


				</div>
				<c:choose>
					<c:when test="${spInfo.sp_service_type==1}">
						<p class='center' style="padding-top: 10px">
							<span>本月用量：${spInfo.monthSendTask }</span>
						</p>

					</c:when>
					<c:otherwise>
						<br>
						<p class='center' style="padding-bottom: 10px; padding-top: 15px;">
							<span>本月用量：${spInfo.monthSendTask }</span>
						</p>

					</c:otherwise>
				</c:choose>
				<br>
				<p class='center'>
					<c:choose>
						<c:when test="${spInfo.sp_through_status==4 }">
							<span>运营状态:冻结</span>
						</c:when>
						<c:otherwise>
							<span>运营状态:正常</span>
						</c:otherwise>
					</c:choose>
				</p>
				<br>
				<c:choose>
					<c:when test="${spInfo.sp_service_type==1}">
						<p class='center'>
							<span>签&nbsp;&nbsp;名：${spInfo.signature}</span>
						</p>
						<br>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${spInfo.sp_service_type==1}">
						<div class='secondDiv_left_bottom_bor1_btm'>
							<div class='secondDiv_left_bottom_bor1_btmLf'>
								<a href="javaScript:void(0)"
									onclick="findSpInfoById(${spInfo.spid})">查看</a>
							</div>
							<div class='secondDiv_left_bottom_bor1_btmRt'>
								<c:choose>
									<c:when test="${spInfo.sp_through_status==4 }">
										<a href="javascript:;" onclick="frozenSpCharge()">充值</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:;"
											onclick="goPage('chargeRecord/directPaySpInfo?spId=${spInfo.spid}','static/js/spCharge/paySpInfo.js')">充值</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:when>
					<c:when test="${spInfo.sp_service_type==2}">
						<div class='secondDiv_left_bottom_bor1_btm'
							style="padding-top: 5px">
							<div class='interDiv_middle_bottom_bor1_btmLf'>
								<a href="javaScript:void(0)"
									onclick="findSpInfoById(${spInfo.spid})">查看</a>
							</div>
							<div class='interDiv_middle_bottom_bor1_btmRt'>
								<c:choose>
									<c:when test="${spInfo.sp_through_status==4 }">
										<a href="javascript:;" onclick="frozenSpCharge()">充值</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:;"
											onclick="goPage('chargeRecord/directPaySpInfo?spId=${spInfo.spid}','static/js/spCharge/paySpInfo.js')">充值</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:when>
					<c:when test="${spInfo.sp_service_type==3}">
						<div class='secondDiv_left_bottom_bor1_btm'
							style="padding-top: 5px">
							<div class='voiceDiv_right_bottom_bor1_btmLf'>
								<a href="javaScript:void(0)"
									onclick="findSpInfoById(${spInfo.spid})">查看</a>
							</div>
							<div class='voiceDiv_right_bottom_bor1_btmRt'>
								<c:choose>
									<c:when test="${spInfo.sp_through_status==4 }">
										<a href="javascript:;" onclick="frozenSpCharge()">充值</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:;"
											onclick="goPage('chargeRecord/directPaySpInfo?spId=${spInfo.spid}','static/js/spCharge/paySpInfo.js')">充值</a>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:when>
				</c:choose>
			</div>
	</div>
	</c:forEach>
</div>
<!--*********************footerDiv------start**********************************-->
<div class='footerDiv'
	style="padding-top: 20px; background-color: white; clear: both;">
	<h3 class='footerDiv_h3'
		style="text-align: center; padding-bottom: 15px;">发送量统计</h3>
	<div id='footerDiv_div' style='width: 100%; height: 500px'></div>
</div>
<div id="modaljsp">
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
						<input type="hidden" name="sp_service_type" id="sp_service_type">
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-username">选择模版
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<select class="form-control" name="messageType" id="messageType"
									onchange="changeTestMessage(this)">
									<option value="0">验证码模版</option>
									<option value="1">会员通知模版</option>
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
									name="testMessage" id="testMessage" readOnly="readOnly">
									【畅卓科技】您的短信验证码是1234。如非本人操作，请忽略此短信。本短信免费。
								</textarea>
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
	<div id="testInterMessageModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-label="Close"
						class="close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="spInfoModelTitle" class="modal-title">测试国际短信</h4>
				</div>
				<form class="js-validation-app-update form-horizontal"
					id="testInterMessageForm" method="post">
					<div class="modal-body">
						<input type="hidden" name="sp_service_type"
							id="sp_Inter_service_type">
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-username">选择模版
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<select class="form-control" name="messageType" id="messageType"
									onchange="changeTestInterMessage(this)">
									<option value="0">验证码模板</option>
									<option value="1">通知模板</option>
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
									name="testMessage" id="testInterMessage" readOnly="readOnly">【chanzor】Dear user, your verification code is @.
								</textarea>
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
	<div id="testVoiceMessageModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-dismiss="modal" aria-label="Close"
						class="close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="spInfoModelTitle" class="modal-title">测试语音短信</h4>
				</div>
				<form class="js-validation-app-update form-horizontal"
					id="testVoiceMessageForm" method="post">
					<div class="modal-body">
						<input type="hidden" name="sp_service_type"
							id="sp_voice_service_type">
						<div class="form-group">
							<label class="col-md-4 control-label" for="val-username">选择模版
								<span class="text-danger">*</span>
							</label>
							<div class="col-md-7">
								<select class="form-control" name="messageType" id="messageType"
									onchange="changeTestInterMessage(this)">
									<option value="0">验证码模板</option>
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
									name="testMessage" id="testInterMessage" readOnly="readOnly">您的短信验证码是1234。</textarea>
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
</div>
<div style="padding-top: 20px">
	<div class='rightbottom'>
		<p class='rightbottom_p1'>
			<a href="http://www.chanzor.com" class="rightbottom_p1_a">畅卓官网</a><span>|</span><a
				href="http://www.chanzor.com/?/aboutus" class="rightbottom_p1_a">联系我们</a><span>|</span><a
				href="http://www.chanzor.com/?/help/api" class="rightbottom_p1_a">接口对接</a><span>|</span><a
				href="http://www.chanzor.com/?/aboutus" class="rightbottom_p1_a">关于畅卓</a>
		</p>
		<p>
			<span>CopyRight&copy;2014-2016 畅卓chanzor.com版权所有&nbsp;|&nbsp;<a
				href="http://icp.alexa.cn/index.php?q=www.chanzor.com">京ICP备14027948号-1</a></span>&nbsp;|&nbsp;<img
				src="static/img/police.png" alt="" />&nbsp;<span><a
				href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11010602110008">京公网安备
					11010602110008号</a></span>
		</p>

	</div>
</div>
