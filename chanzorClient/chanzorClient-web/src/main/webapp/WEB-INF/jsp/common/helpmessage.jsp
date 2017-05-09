<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style type="text/css">

.helptop{
		width: 80%;
		margin-left: 220px;
		/* background-color: white; */
		height: 80px;
		margin-top: 75px;
		padding-top: 30px;
}

.chanzorDataList {
	width: 15%;
	margin-left: 220px;
	background-color: white;
	height: 780px;
	margin-top: 10px;
	padding-top: 30px;
}

.chanzorDataList1 {
	width: 65%;
	margin-left: 220px;
	background-color: white;
	height: 780px;
	margin-top: 10px;
	padding-top: 20px;
}

dt{ font-size: 16px; margin-top:30px; }
dd{ margin:10px;}

.tab-content{
	border-width:0px;
}

.floatleft{ float:left; }
.mt20{margin: 20px;}


</style>

<div class='helptop' style="float:left">
	<h1 class="floatleft">帮助中心</h1><a href="javascript:;" onclick="helpbtn()" class='floatleft mt20'>操作手册</a>
</div>

<div class='chanzorDataList' style="float:left">
	<ul id="myTab" class="nav nav-tabs  nav-stacked">
		<li class="active"><a href="#accountAuthentication" data-toggle="tab"> <strong>账号与企业认证</strong> </a></li>
		<li><a href="#spinfoSendout" data-toggle="tab"> <strong>应用与发送</strong> </a></li>
		<li><a href="#autoTemplate" data-toggle="tab"> <strong>签名与模板</strong> </a></li>
		<li><a href="#rechargeInvoice" data-toggle="tab"> <strong>充值与发票</strong> </a></li>
	</ul>
</div>

<div class='chanzorDataList1' style="float: left; margin-left: 10px">
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="accountAuthentication">
			<dt>新注册的用户能做什么？</dt>
			<dd>
				新注册畅卓自助平台的用户，可以通过畅卓赠送的应用进行测试短信发送，其中包括国内短信应用30条；国际短信应用5元；语音短信应用30条。</dd>
			<dt>怎么进行短信测试？</dt>
			<dd>
				可以通过首页的体验中心，选择需要测试的业务类型，畅卓科技提供不同的模板，填写手机号即可进行测试；也可以通过左侧相应的业务类型菜单，选择发送短信。
			</dd>
			<dt>测试应用余额用完后还能进行测试吗？</dt>
			<dd>
				测试应用一般提供足够的余额进行测试，不适于大规模发送；测试应用也可以进行充值，但企业认证未通过时，仅适用于测试，测试短信签名为【畅卓科技】；通过企业认证后，可修改应用签名。
			</dd>
			<dt>怎么变更账号？</dt>
			<dd>暂不支持账号变更，用户注册时手机号即为账号信息中的联系号码，不支持变更。</dd>
			<dt>企业认证是什么？</dt>
			<dd>
				根据运营商短信息服务安全管理规定，对下发的短信提供者进行报备；用户通过企业认证上传相关资质，审核通过后即完成企业认证；企业认证一旦通过无法进行修改。
			</dd>
			<dt>企业认证有什么用？</dt>
			<dd>通过企业认证后，新增的应用才可以上线正式使用。</dd>
		</div>
		<div class="tab-pane fade" id="spinfoSendout">
			<dt>短信服务都有哪些业务类型？</dt>
			<dd>
				畅卓科技提供国内短信服务、国际短信服务和语音短信服务，国内短信适用于国内手机验证码、通知类消息发送；国际短信适用于国外手机验证码、通知类消息发送；语音短信适用于通过拨打用户手机播放语音消息通知。
			</dd>
			<dt>什么是应用？</dt>
			<dd>为了方便用户区分业务和服务类型，畅卓科技提供短信服务应用，通过应用定义业务和服务的类型，方便对短信的统计和计费。</dd>
			<dt>应用是否有数量限制？</dt>
			<dd>用户自定义应用没有数量限制，可根据实际业务创建应用。</dd>
			<dt>短信服务怎么计费？</dt>
			<dd>
				国内短信是按条计费，根据运营商对短信息规定，短信字数<=70个字数，按照70个字数一条短信计算；短信字数>70个字数，即为长短信，按照67个字数记为一条短信计算。<br/>
				国际短信是按钱计费，因为各个国家地区通道收费不同，详情参考国际短信短信报价。<br/>
				语音短信是按条计费，根据运营商对语音通话规定，时长<=60秒，为一条。</dd>
			<dt>支持联通、移动和电信手机发送吗？</dt>
			<dd>畅卓科技短信服务支持全国范围手机的三网发送。</dd>
			<dt>什么是敏感词？</dt>
			<dd>
				根据运营商短信息服务安全管理规定，一些敏感词语禁止发送，畅卓科技提供敏感词过滤功能，如果您的短信内容包含敏感词，需进行修改以提高短信发送成功率。
			</dd>
			<dt>为什么发送后没有收到？</dt>
			<dd>当提交短信发送后，先进行短信发送统计确认，查看提交状态。 通常有以下情况：<br/>
			    1)接收手机停机，无法正常接收短信；<br/>
				2)进入人工审核，待通过人工审核后查询下发状态；<br/> 
				3)接收手机为黑名单用户。 请根据实际情况联系客服。</dd>
			<dt>什么是黑名单用户？</dt>
			<dd>运营商的手机用户信息备案，被列为黑名单用户，可咨询接收用户的通信运营商，解除黑名单限制。</dd>
		</div>
		<div class="tab-pane fade" id="autoTemplate">
			<dt>什么是签名？</dt>
			<dd>
				根据运营商短信息服务安全管理规定，短信前必须增加短信签名；短信签名一般代表了公司名称或企业标识符，在短信内容开头处显示，例如：“【畅卓科技】+短信内容”。
			</dd>
			<dt>在哪设置签名？</dt>
			<dd>每个应用申请上线前，需要填写该应用的签名，需要注意，应用提交申请上线后是不能修改签名的。</dd>
			<dt>签名能不能修改？</dt>
			<dd>签名是应用的附加属性，已上线和申请上线中的应用是不能修改签名的。</dd>
			<dt>什么是短信模板？</dt>
			<dd>
				为了方便用户使用短信服务，畅卓科技提供用户自定义模板，模板通过审核后，可直接使用模板进行发送。例如“您的验证码是@”，@代表变量符号，企业可根据应用变量修改。
			</dd>
			<dt>可以不使用短信模板吗？</dt>
			<dd>可以不选择短信模板，但内容需要通过人工审核。</dd>
		</div>
		<div class="tab-pane fade" id="rechargeInvoice">
			<dt>如何进行充值？</dt>
			<dd>用户可以通过应用或财务管理对应用进行充值，线上支持支付宝充值，线下支持银行转账。</dd>
			<dt>支持网银、微信充值吗？</dt>
			<dd>暂不支持网银和微信充值。</dd>
			<dt>怎么开发票？</dt>
			<dd>
				畅卓科技提供增值税普通发票和增值税专用发票，用户需要先进行发票认证，发票认证通过审核后，可以在财务管理，发票管理中添加发票。消费单次/累积满5000元以上才可开具发票。</dd>
		</div>
	</div>
</div>