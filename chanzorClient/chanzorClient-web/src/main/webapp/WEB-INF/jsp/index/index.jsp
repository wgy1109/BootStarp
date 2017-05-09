<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>畅卓验证码短信飞常快---畅卓官网 在线短信平台,SDK短信接口,手机短信验证码,验证码通道</title>
<meta name="description"
	content="北京畅卓科技有限公司是一家拥有卓越运营支撑能力的短信服务商，为企业提供一站式的短信验证码应用解决方案，拥有106高端通道免费试用，3秒最快，99.99%到达率，咨询热线：400-793-0000，产品包括：短信验证码，短信平台，短信验证码，手机短信平台，验证码短信，在线短信群发平台，短信通道，短信接口，短信服务商，短信营销，验证码平台，验证码通道，验证码接收，验证码接收平台，手机验证码短信">
<meta name="keywords"
	content="短信验证码，短信平台，短信验证码，手机短信平台，验证码短信，在线短信群发平台，短信通道，短信接口，短信服务商，短信营销，验证码平台，验证码通道，验证码接收，验证码接收平台，手机验证码短信，SDK短信接口">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
<meta name="renderer" content="webkit" />
<link rel="stylesheet" href="<%=basePath%>static/css/bootstrap.css"
	id="bscss">
<link rel="stylesheet" href="<%=basePath%>static/css/chanzor.css">
<style>
a {
	color: black;
}

a:hover {
	color: green
}

.login_top {
	height: 105px;
}

.login_top_img {
	background-image: url(static/img/toppage.jpg);
	background-repeat: no-repeat;
	height: 80px;
	width: 1135px;
	margin: 0 auto;
	margin-top: 38px;
	margin-bottom: 38px;
}

.login_middle {
	height: 549px;
	background-color: #0099CB;
	width: 100%;
	position: relative;
	padding-top: 23px;
}

.login_bottom {
	margin-top: 78px;
	/*position: absolute;*/
	/*left:0;*/
	/*bottom:0;*/
}

.login_bottom p {
	text-align: center;
}

.login_bottom p a {
	text-decoration: none;
	margin-right: 10px;
	margin-left: 10px;
	color: black;
}

.login_middle_box {
	margin: 0 auto;
	width: 1135px;
	height: 500px;
	/*position: absolute;*/
	margin-top: 2px;
}

.login_middle_left {
	height: 100%;
	width: 440px;
	float: left;
}

.login_middle_right {
	height: 370px;
	width: 388px;
	background-color: #FFFFFF;
	float: right;
	padding-top: 12px;
	margin-top: 78px;
}

.login_middle_right_inner {
	margin: 0 auto;
	margin-top: 20px;
}

.form-group_firstA a, .form-group_secondA a {
	color: black;
}

.form-group_secondA_bottom {
	border-top: 1px solid #BFBFBF;
	margin-top: 10px;
}

.pw-strength {
	clear: both;
	position: relative;
	top: 8px;
	width: 180px;
}

.pw-bar {
	background: url(img/pwd-1.png) no-repeat;
	background-size:300px 15px; 
	height: 15px;
	overflow: hidden;
	width: 300px;
}

.pw-bar-on {
	background: url(img/pwd-2.png) no-repeat;
	background-size:300px 15px; 
	width: 0px;
	height: 15px;
	position: absolute;
	top: 1px;
	left: 2px;
	transition: width .5s ease-in;
	-moz-transition: width .5s ease-in;
	-webkit-transition: width .5s ease-in;
	-o-transition: width .5s ease-in;
}

.pw-weak .pw-defule {
	width: 0px;
}

.pw-weak .pw-bar-on {
	width: 100px;
}

.pw-medium .pw-bar-on {
	width: 200px;
}

.pw-strong .pw-bar-on {
	width: 300px;
}

.pw-txt {
	padding-top: 2px;
	width: 300px;
	overflow: hidden;
}

.pw-txt span {
	color: #707070;
	float: left;
	font-size: 12px;
	text-align: center;
	width: 100px;
}
</style>
</head>
<body>
	<div>
		<div class='login_top' style="margin-top: 20px">
			<div class='login_top_img'></div>
		</div>
		<div class='login_middle'>
			<div class='login_middle_box'>
				<div class='login_middle_left'>
					<img src="static/img/mailpic.gif" alt=""
						style='max-width: 440px; height: auto;' />
				</div>
				<div class='login_middle_right'>
					<div class='login_middle_right_inner' id="loginDiv"
						style="display: block">
						<form class="form-horizontal" action="log" method="post"
							id="logform">
							<p
								style='text-align: center; font-size: 22px; font-weight: bold;'>
								<img src="static/img/login_left.png" alt="">账户登录<img
									src="static/img/login_right.png" alt="">
							</p>
							<br>

							<div class="form-group" id='form-groupDiv'>
								<label for="inputEmail3"
									class="col-sm-3 control-label col-sm-offset-1">账号：</label>
								<div class="col-sm-6" style="padding-left: 0px">
									<input type="text" class="form-control " id="username"
										name="username" value="">
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3"
									class="col-sm-3 control-label col-sm-offset-1">密码：</label>
								<div class="col-sm-6" style="padding-left: 0px">
									<input type="password" class="form-control" id="password"
										name="password" value="">
								</div>
							</div>
							<div class="mb-lg " style="display: none" id="show_login_smsCode">
								<div class="col-xs-4 register_marginLeft">
									<input class="form-control" type="text" id="login-smsCode"
										style="width: 99px;" name="login-smsCode" placeholder="短信验证码"
										maxLength="6">
								</div>
								<div class="col-xs-3 text-right">
									<button class="btn btn-success"
										style="width: 99px; height: 33px;"
										onclick="loginSendSMSCode(this);" type="button">
										<strong>获取验证码</strong>
									</button>
								</div>
							</div>
							<div class="form-group"></div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-12">
									<button type="button" onclick="log();"
										class="btn btn-success col-sm-5 col-sm-offset-2"
										style="height: 35px; line-height: 15px;">登录</button>
								</div>
							</div>
						</form>
						<div class='form-group'>
							<div class='col-sm-3 col-sm-offset-2 form-group_firstA'
								style="width: 100px;">
								<a onmouseover="this.style.cssText='color:green;'"
									onmouseout="this.style.cssText='color:black'" href="#"
									onclick="recoverShow();">忘记密码？</a>
							</div>
							<!-- 							<div class='col-sm-2 col-sm-offset-4 form-group_secondA' -->
							<!-- 								style="padding-left: 5px;"> -->
							<!-- 								<a href="#" onmouseover="this.style.cssText='color:green;'" -->
							<!-- 									onmouseout="this.style.cssText='color:black'" -->
							<!-- 									onclick="registerShow();">注册</a> -->
							<!-- 							</div> -->
						</div>
						<div class='col-sm-9 col-sm-offset-2 form-group_secondA_bottom'></div>
						<div class='col-sm-8 col-sm-offset-3'
							style='margin-top: 5px; color: #0099CB; font-size: 18px;'>
							<p>热线电话：400-793-0000</p>
						</div>
					</div>

					<div class='login_middle_right_inner' id="registerDiv"
						style="display: none; height: 380px; background-color: white">
						<form class="form-horizontal" role="form" method="post"
							id="registerform">
							<p
								style='text-align: center; font-size: 22px; font-weight: bold;'>
								<img src="static/img/login_left.png" alt="">账户注册<img
									src="static/img/login_right.png" alt="">
							</p>
							<input type="hidden" value="${identif }" name="identif" />
							<div
								class="form-group has-feedback col-xs-12 register_paddingLeft">
								<label
									style="float: left; padding-right: 10px; padding-top: 5px;">账号:</label>
								<input type="text" id="mobile" name="mobile" style="width: 80%"
									placeholder="请输入手机号" autocomplete="off" required
									class="form-control" maxLength="11">
							</div>
							<div
								class="form-group has-feedback col-xs-12 register_paddingLeft">
								<label
									style="float: left; padding-right: 10px; padding-top: 5px;">密码:</label>
								<input type="text" placeholder="密码" name="register_password"
									id="register_password" required class="form-control"
									style="width: 80%" minlength="6" maxLength="20"
									placeholder="请输入6~20位密码" onfocus="this.type='password'">

							</div>
							<div class="form-group mb-lg ">
								<div class="col-xs-5 register_marginLeft" style="padding: 0px">
									<label
										style="float: left; padding-right: 10px; padding-top: 5px;">校验码:</label>
									<input class="form-control" type="text" id="register-cord"
										style="width: 115px" name="register-cord" placeholder="图形校验码"
										maxLength="6" />
								</div>
								<div class="col-xs-4">
									<img style="width: 115px; height: 33px;" class="js-formcodeimg"
										alt="验证码" src="loginCode.html" onclick="refreshCode(this)" />
								</div>
							</div>
							<div class="form-group mb-lg ">
								<div class="col-xs-5 register_marginLeft" style="padding: 0px">
									<label
										style="float: left; padding-right: 10px; padding-top: 5px;">验证码:</label>
									<input class="form-control" type="text" id="register-smsCode"
										style="width: 115px;" name="register-smsCode"
										placeholder="短信验证码" maxLength="6">
								</div>
								<div class="col-xs-3 text-right">
									<button class="btn btn-success"
										style="width: 115px; height: 33px;"
										onclick="sendSMSCode(this);" type="button">
										<strong>获取验证码</strong>
									</button>
								</div>
							</div>
							<div class="form-group mb-lg ">
								<label
									style="float: left; padding-right: 10px; padding-top: 5px; padding-left: 35px;">推荐人:</label>
								<input type="text" id="recommend" name="recommend"
									style="width: 60%" placeholder="请输入推荐人" autocomplete="off"
									required class="form-control" maxLength="11">
							</div>
							<div class="form-group">
								<div class="col-xs-6">
									<button
										class="btn btn-block btn-success register_button_marginLeft"
										type="button" onclick="registerSub()"
										style="height: 35px; line-height: 24px;">注册并登录</button>
								</div>
							</div>

							<div class="form-group has-feedback">
								<div class="col-xs-5 text-right">
									<label class="css-input">
										<p onclick="showterms()">查看注册条款</p>
									</label>
								</div>
								<div class="col-xs-5 text-right">
									<a href="#" onclick="loginShow();">已有账号</a>
								</div>
							</div>
						</form>
					</div>

					<div class='login_middle_right_inner' id="saveMobileDiv"
						style="display: none">
						<p
							style='text-align: center; font-size: 22px; font-weight: bold; margin: 34px 0px'>
							您的账户存在安全问题</p>
						<form class="form-horizontal" role="form" method="post"
							id="saveMobileform">
							<input type="hidden" name="userId" />
							<div
								class="form-group has-feedback col-xs-11 register_paddingLeft">
								<input type="text" id="savemobile" name="savemobile"
									placeholder="请绑定手机号" autocomplete="off" required
									class="form-control" maxLength="11">
							</div>
							<div class="form-group mb-lg ">
								<div class="col-xs-4 register_marginLeft">
									<input class="form-control" type="text" id="savemobile-smsCode"
										style="width: 120px;" name="savemobile-smsCode"
										placeholder="短信验证码" maxLength="6">
								</div>
								<div class="col-xs-3 text-right">
									<button class="btn btn-success"
										style="width: 115px; height: 33px;"
										onclick="saveMobileSendSMSCode(this);" type="button">
										<strong>获取验证码</strong>
									</button>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-6">
									<button
										class="btn save_mobile_button btn-success savemobile_button_marginLeft"
										type="button" onclick="saveMobileSub()"
										style="height: 35px; line-height: 24px;">确定</button>
								</div>
								<div class="col-xs-6">
									<button class="btn save_mobile_button" type="button"
										onclick="loginout()" style="height: 35px; line-height: 24px;">取消</button>
								</div>
							</div>
						</form>
						<hr />
						<div class='col-sm-8 col-sm-offset-3'
							style='margin-top: 5px; color: red; font-size: 13px;'>
							<p>手机绑定后可作为登陆账号</p>
						</div>
					</div>

					<div class='login_middle_right_inner' id="recoverDiv"
						style="display: none">
						<form class="form-horizontal" role="form" method="post"
							id="recoverform">
							<p
								style='text-align: center; font-size: 22px; font-weight: bold; margin-bottom: 14px;'>
								<img src="static/img/login_left.png" alt="">修改密码<img
									src="static/img/login_right.png" alt="">
							</p>
							<div
								class="form-group has-feedback col-xs-11 register_paddingLeft">
								<input type="text" id="recoverMobile" name="recoverMobile"
									placeholder="请输入手机号" autocomplete="off" required
									class="form-control" maxLength="11">
							</div>
							<div class="form-group mb-lg ">
								<div class="col-xs-4 register_marginLeft">
									<input class="form-control" type="text" id="recover-smsCode"
										style="width: 120px;" name="recover-smsCode"
										placeholder="短信验证码" maxLength="6">
								</div>
								<div class="col-xs-3 text-right">
									<button class="btn btn-success"
										style="width: 115px; height: 33px;"
										onclick="recoverSendSMSCode(this);" type="button">
										<strong>获取验证码</strong>
									</button>
								</div>
							</div>
							<div
								class="form-group has-feedback col-xs-11 register_paddingLeft">
								<input type="text" placeholder="新密码" name="recover_password"
									id="recover_password" required class="form-control"
									minlength="6" maxLength="20" placeholder="请输入6~20位密码"
									onfocus="this.type='password'">
							</div>
							<div id="level"
								class="form-group has-feedback col-xs-11 register_paddingLeft">
								<div id="level" class="pw-strength">
									<div class="pw-bar"></div>
									<div class="pw-bar-on"></div>
									<div class="pw-txt">
										<span>弱</span> <span>中</span> <span>强</span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-6">
									<button
										class="btn btn-block btn-success register_button_marginLeft"
										type="button" onclick="recoverSub()"
										style="height: 35px; line-height: 24px;">保存</button>
								</div>
							</div>
							<div class="form-group has-feedback">
								<div class="col-xs-5 text-right">
									<label class="css-input">
										<p></p>
									</label>
								</div>
								<div class="col-xs-5 text-right">
									<a href="#" onclick="loginShow();">返回登陆</a>
								</div>
							</div>
						</form>
						<div class='col-sm-9 col-sm-offset-2 form-group_secondA_bottom'></div>
						<div class='col-sm-8 col-sm-offset-3'
							style='margin-top: 5px; color: #0099CB; font-size: 18px;'>
							<p>热线电话：400-793-0000</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class='login_bottom'>
			<p class="rightbottom_p1">
				<a href="http://www.chanzor.com" class="rightbottom_p1_a">畅卓官网</a><span>|</span><a
					href="http://www.chanzor.com/?/aboutus" class="rightbottom_p1_a">联系我们</a><span>|</span><a
					href="http://www.chanzor.com/?/help/api" class="rightbottom_p1_a">接口对接</a><span>|</span><a
					href="http://www.chanzor.com/?/aboutus" class="rightbottom_p1_a">关于畅卓</a>
			</p>
			<p>
				<span>CopyRight©2014-2016 畅卓chanzor.com版权所有&nbsp;|&nbsp;<a
					href="http://icp.alexa.cn/index.php?q=www.chanzor.com">京ICP备14027948号-1</a></span>&nbsp;|&nbsp;<img
					src="static/img/police.png" alt="">&nbsp;<span><a
					href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11010602110008">京公网安备
						11010602110008号</a></span>
		</div>
	</div>

	<div id="modaljsp">
		<div id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" data-dismiss="modal" aria-label="Close"
							class="close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 id="myModalLabel" class="modal-title">注册条款</h4>
					</div>
					<div class="modal-body">
						<div style="width: 100%; height: 85%;">
							<textarea class='form-control' rows='33' cols='50'
								readonly='readonly'>
        开发者协议
	北京畅卓科技有限公司网站服务协议
	尊敬的北京畅卓科技有限公司用户，欢迎使用北京畅卓科技有限公司（以下简称畅卓科技）。本服务协议阐述之条款和条件适用于您使用的web.chanzor.cn网站所提供的北京畅卓科技有限公司服务及相关技术、网络支持服务（以下简称服务）。
	接受条款
	1.1 您通过盖章、网络页面点击确认或以其他方式选择接受本协议，即表示您与畅卓科技已达成协议，同意接受本协议的所有内容，并受其约束。
	1.2 在此特别提醒，在接受本协议之前，请您仔细阅读、充分理解本协议的所有内容。如果您对本协议的任何条款有疑问，请通过北京畅卓科技有限公司相关业务部门进行咨询；如果您不同意本协议的任何内容或者无法准确理解北京畅卓科技有限公司对本协议相关条款的解释，请不要进行后续操作。
	注册条款
	2.1 您通过北京畅卓科技有限公司注册时，应根据要求提供真实、准确、即时、完整的信息，包括但不限于联系人和/或管理人姓名或名称、联系地址、联系电话、电子邮箱地址等，企业用户需要提交公司营业执照、组织机构代码证、法人信息、银行开户信息等；如果前述您的信息发生变更，您应自行在线更新并及时通知北京畅卓科技有限公司；如果因您的信息不真实、不准确、不完整或联系人和/或管理人的行为或不作为而引起的任何后果由您自行承担，北京畅卓科技有限公司有权暂停或终止您的账号。
	2.2 您应对自行存放在北京畅卓科技有限公司平台上的数据及进入和管理北京畅卓科技有限公司平台上各类产品与服务的口令、密码妥善保管，不得转让、转借其他第三人使用；如果因您维护不当或保管不当致使前述数据、口令、密码等丢失或泄漏所引起的任何损失及后果由您自行承担。
	服务须知
	3.1您在使用北京畅卓科技有限公司服务时应遵守相关法律法规、本协议及web.chanzor.cn网站上的相关管理规范、流程等。除另有明确规定，北京畅卓科技有限公司所推出的新产品、新功能和新服务，均适用本协议。您应知悉包括但不限于前述协议、规范、产品体系、名称和价格等内容可能会不时变更，如果发生前述变更，我们将在网站的适当版面公告变更内容。如果您不同意变更内容，您有权停止使用北京畅卓科技有限公司服务，此等情况下，您应结算相关服务费（如有）；如果您继续使用北京畅卓科技有限公司服务，则视为您接受前述变更内容并受其约束。
	3.2 北京畅卓科技有限公司服务为预付费服务，您应按照网站页面提示的现时有效的价格体系及本协议的约定支付相应服务费用，前述服务费用将在订购页面上公示。您应知悉北京畅卓科技有限公司价格体系中所有的赠送服务项目或活动均为北京畅卓科技有限公司在正常服务价格之外的一次性特别优惠，优惠内容不包括赠送服务项目的修改、更新及维护费用，并且赠送服务项目不可折价冲抵服务价格。您预付费用后，我们将开始提供相关服务；与此同时，您应经常查询账户余额变化情况，提前做好充值准备。若您的预付费用使用完毕未及时充值或余额不足，我们有权不经通知随时中止所有提供服务和/或技术支持，对于由此造成的任何后果您应自行承担。
	3.3您应负责处理和解决因非网络通信问题引起的用户咨询和投诉，提供有效通畅的投诉受理渠道，在需要时积极配合我们处理用户投诉。对双方均不能做出合理解释的用户投诉，最终由您负责进行处理和解决。
	3.4畅卓科技或其合作伙伴有权对您提供的应用进行审核，包括但不限于内容审查、功能性测试、安全性测试等。如果发现您的应用不符合国家法律法规、政策规定，或您提供的应用可能侵犯他人合法权益或含有对其他第三方的广告信息等内容，或其他认为不符合要求的情况，畅卓科技有权不予接入和传输；已接入和传输的，畅卓科技有权立即停止接入和传输，保存有关记录，并向相关主管部门报告，不经通知自行决定删除或移除任何网站内容或者您发布的应用。但是，该项约定不视为畅卓科技对您提供的应用提供合法性担保，您应自行对其提供的应用提供保证，并承担由此引发的所有责任。
	3.5. 您提供的应用一旦发生纠纷，包括但不限于应用侵犯他人的合法权利、诉讼、重大投诉、发生违约的重大情形等，畅卓科技有权停止该应用的测试、上线等，并冻结您的账户；对于前述纠纷，您应自行处理并承担由此引起的全部法律责任，及赔偿畅卓科技由此遭受的全部损失，包括但不限于遭第三方追索所造成的所有损失、诉讼费用、律师费用、差旅费用、和解金额或生效法律文书中规定的损害赔偿金额、软件使用费等；如果畅卓科技因您的原因卷入前述纠纷，畅卓科技除有权要求您先行赔偿前述全部损失外，亦有权先行处理并要求您提供必要支持、协助。尽管有前述规定，畅卓科技亦有权要求您按照本协议承担违约责任。
	3.6 服务期限内，北京畅卓科技有限公司将为您提供7×24售后故障服务，并为您提供有效的联系方式并保证您能够联系到故障联系人，故障联系人在明确故障后及时进行反馈；北京畅卓科技有限公司仅负责北京畅卓科技有限公司平台的底层部分及北京畅卓科技有限公司提供的软件的运营维护，如果故障原因是由于您未正确按照北京畅卓科技有限公司平台提供的相关文档进行配置导致的无法访问或者您自身技术原因导致的服务故障，不在北京畅卓科技有限公司服务范畴之内。
	3.7北京畅卓科技有限公司将消除您非人为操作所出现的故障，但因您的原因和/或不可抗力以及非北京畅卓科技有限公司控制范围之内的事项除外。
	3.8您应知悉并认可，畅卓科技在必要时可能会进行机房迁移。北京畅卓科技有限公司进行上述操作前将提前7日通知您，由于进行上述操作可能需要修改您的相关域名的DNS，因此您需在接到北京畅卓科技有限公司通知后按照要求的时间将DNS修改到指定IP上，否则因此造成网站无法访问的，您应自行负责。
	3.9 北京畅卓科技有限公司保留在您未按照约定支付全部费用之前不向您提供服务和/或技术支持，或者终止服务和/或技术支持的权利。
	用户管理
	4.1如果您使用北京畅卓科技有限公司提供的服务进行的经营活动需要获得国家有关部门的许可或批准的，您应事先获得该有关的许可或批准。您应依照《互联网信息服务管理办法》、《互联网电子公告服务管理规定》等法律法规的规定保留其网站的访问日志记录，包括发布的信息内容及其发布时间、互联网地址（IP）、域名等，国家有关机关依法查询时应配合提供。您应自行承担未按规定保留相关记录而引起的全部法律责任。
	4.2您承诺：
	A) 除获得事先书面许可外，不得修改、翻译、改编、出租、转许可、在信息网络上传播或转让北京畅卓科技有限公司提供的软件及代码，也不得逆向工程、反编译或试图以其他方式发现北京畅卓科技有限公司提供的软件的源代码；
	B) 不得散布违反国家相关法律、规定及不利于北京畅卓科技有限公司的垃圾短信、骚扰短信、垃圾电话、骚扰电话、电子邮件广告；不得利用北京畅卓科技有限公司提供的服务散发大量不受欢迎的或者未经请求的电子广告或包含反动、色情等有害信息的文本、语音及视频内容；
	C) 不得利用北京畅卓科技有限公司提供的资源和服务发布如下信息或者内容，不得为他人发布该等信息提供任何便利：
	i. 违反国家规定的政治宣传和/或新闻信息；
	ii. 涉及国家秘密和/或安全的信息；
	iii. 封建迷信和/或淫秽、色情、下流的信息或教唆犯罪的信息；
	iv. 博彩有奖、赌博游戏、“私服”、“外挂”等非法互联网出版活动；
	v. 违反国家民族和宗教政策的信息；
	vi. 防碍互联网运行安全的信息；
	vii. 侵害他人合法权益的信息和/或其他有损于社会秩序、社会治安、公共道德的信息或内容；
	viii. 其他违反法律法规、部门规章或国家政策的内容。
	D) 不得建立或利用有关设备、配置运行与所购服务无关的程序或进程，导致大量占用北京畅卓科技有限公司云计算资源（如北京畅卓科技有限公司、网络带宽、存储空间等）所组成的平台（以下简称“北京畅卓科技有限公司平台”）中服务器内存、CPU或者网络带宽资源，影响北京畅卓科技有限公司与互联网或者北京畅卓科技有限公司与特定网络、服务器及北京畅卓科技有限公司内部的通畅联系，或者导致北京畅卓科技有限公司平台产品与服务或者北京畅卓科技有限公司的其他用户网站所在的服务器宕机、死机或者用户基于北京畅卓科技有限公司平台的产品/应用不可访问等；
	E) 不得进行任何改变或试图改变北京畅卓科技有限公司提供的系统配置或破坏系统安全的行为；
	F) 不得从事其他违法、违规或违反本协议条款的行为。
	4.3 如果您违反上述条款的规定，畅卓科技有权根据情况采取相应的处理措施，包括但不限于立即终止/中止服务，保存有关记录，向相关主管部门报告，删除相应信息等。
	数据的保存、销毁与下载
	5.1 为便于为您服务，北京畅卓科技有限公司可能会向您发布信息，包括但不限于发布产品和服务信息等。
	5.2 您应知悉您的数据在下述情况下将部分或全部被披露：
	A)经您同意，向第三方披露；
	B) 根据法律规定或行政、司法机构要求，向第三方或者行政、司法机构披露；
	C)为维护公众及北京畅卓科技有限公司合法利益；
	D)北京畅卓科技有限公司可能会与第三方合作向您提供相关软件或服务，如果您使用该类服务，则视为您同意该第三方知悉您的数据。
	5.3 除法定及本协议另行约定外，北京畅卓科技有限公司自本协议终止之日起7日后，将不再为您保留数据，您应知悉并自行承担其数据被销毁后引发的一切后果。
	保密条款
	6.1 保密资料指由一方向另一方披露的所有技术及非技术信息(包括但不限于产品资料，产品计划，价格，财务及营销规划，业务战略，客户信息，客户数据，研发，软件硬件，API应用数据接口，技术说明，设计，特殊公式，特殊算法等)。
	6.2 本协议任何一方应对获悉的对方之上述保密资料予以保密，并严格限制接触上述保密信息的员工遵守本条之保密义务。除非国家机关依法强制要求或上述保密资料已经进入公有领域外，接受保密资料的一方不得对外披露。
	6.3 本协议双方明确认可各自用户信息和业务数据等是各自的重要资产及重点保密信息。本协议双方应尽最大的努力保护上述保密信息等不被披露。一旦发现有上述保密信息泄露事件，双方应合作采取一切合理措施避免或者减轻损害后果的产生。
	知识产权
	7.1您声明并保证其在北京畅卓科技有限公司平台上发布的应用拥有知识产权，或者已事先合法取得合法权利人授权并以被许可方式在北京畅卓科技有限公司平台使用。您不得在北京畅卓科技有限公司平台上发布、传播或分享并非其原创或者未经授权使用的内容，但依据法律法规规定合理使用或法定许可除外。您应对通过北京畅卓科技有限公司平台发布的应用及一切资料的知识产权权属自行承担一切法律责任。
	7.2您如果发布含有气象、教育、医疗、交通、金融、影视、动漫、刊物、资讯等行业专业信息的应用，或含有公众人物、名人、个人的头像、标识、肢体语言等信息的应用，应确保拥有合法的使用权、肖像权或形象权等。  
	7.3您保证您通过北京畅卓科技有限公司平台发布的应用不会侵犯任何第三方的合法权利。如果您所发布的应用存在侵犯任何第三方合法权利的情况，您将承担一切相关法律责任和风险。如果北京畅卓科技有限公司因您的应用侵犯第三方合法权利而涉入诉讼、索赔或其他司法程序（以下称“侵权诉讼”），您同意按照以下规定进行处理和赔偿：
	（1） 北京畅卓科技有限公司应在发生上述侵权诉讼后迅速通知您，并在上述侵权诉讼过程中中止向您提供服务。
	（2） 您应当在收到北京畅卓科技有限公司书面通知后，指派代表为北京畅卓科技有限公司的权益参与上述第三方提起的侵权诉讼，您应在上述侵权诉讼进行过程中就诉讼策略及其他事宜向北京畅卓科技有限公司提供必要的支持与协助，并承担所产生的一切诉讼费用、律师费用、差旅费用、和解金额或生效法律文书中规定的损害赔偿金额、软件使用费等费用。
	（3） 北京畅卓科技有限公司有权按照本合同关于违约责任的规定要求您承担违约责任。
	期限与终止
	8.1 服务有效期自您的北京畅卓科技有限公司账户成功付费之日起（而非自您获取北京畅卓科技有限公司的管理员登录号和密码之日）计算，并以您所缴纳的保底或月功能费款项数额为依据确认服务价格。开发者账户在线付费后服务即时生效，企业用户使用公对公账户打款付费后，2个工作日内服务生效。
	8.2 发生下列情形，服务期限提前终止：
	A) 双方协商一致提前终止的；
	B) 您违反本协议（包括但不限于i.未按照协议约定履行付款义务，及/或ii.严重违反法律规定等），畅卓科技有权提前终止服务，并不退还您已经支付的费用；
	C) 您知悉并充分认可，虽然北京畅卓科技有限公司已经建立（并将根据技术及市场的发展不断完善）必要的技术与商务措施来:
	i. 防御包括计算机病毒、网络入侵和攻击破坏等危害网络安全事项或行为；
	ii.组成完善的短信、语音中继、视频网络保障您的服务通畅；
	但鉴于网络安全技术的局限性、相对性以及该等行为的不可预见性，以及国家及运营商政策的不可预见性，因此若因技术或者政策原因，北京畅卓科技有限公司可自行决定暂停或终止服务。如果终止服务的，将按照实际提供服务计算服务费用，将剩余款项（如有）返还您。
	D) 畅卓科技可提前30天在web.chanzor.cn上通告及以通知方式告知您终止本协议，无需说明理由，届时将退还您已支付但未消费的款项（如有）。
	免责声明
	9.1您知悉并认可，鉴于计算机、互联网的特殊性，下述情况不属于畅卓科技违约：
	A) 北京畅卓科技有限公司在进行服务器配置、维护时，需要短时间中断服务；
	B) 由于Internet上的通路阻塞造成您网站访问速度下降；
	C) 因不可抗力、计算机病毒或黑客攻击、国家相关行业主管部门及运营商的调整、系统不稳定、您所在位置、您关机及其他任何技术、互联网络、通信线路原因等造成的服务中断或不能满足您的要求；
	D) 由于行业技术水平无法避免的瑕疵造成北京畅卓科技有限公司提供的服务存在瑕疵。
	9.2 在任何情况下，畅卓科技均不就因使用北京畅卓科技有限公司平台提供的服务所发生的任何间接性、后果性、惩戒性、偶然性、特殊性的损害，包括您使用北京畅卓科技有限公司服务而遭受的利润损失承担责任（即使您已事先被告知该等损害发生的可能性）。
	9.3您应知悉并认可，使用北京畅卓科技有限公司平台提供服务可能存在来自任何第三方的包括威胁、诽谤或非法的内容或行为或对他人合法权利的侵犯的匿名或冒名信息的风险，您应自行承担前述风险，畅卓科技或合作公司对提供的服务不作任何类型的担保，不论是明示的或默示的，包括所有有关信息真实性、适用性、所有权和非侵权性的默示担保和条件，对因此导致任何因您不正当或非法使用服务产生的直接、间接、偶然、特殊及后续的损害，不承担任何责任。
	不可抗力
	因不可抗力或者其他意外事件，使得本协议的履行不可能、不必要或者无意义的，遭受不可抗力、意外事件的一方不承担责任。不可抗力、意外事件是指不能预见、不能克服并不能避免且对一方或双方当事人造成重大影响的客观事件，包括但不限于自然灾害如洪水、地震、瘟疫流行等以及社会事件如战争、动乱、政府行为、电信主干线路中断、黑客、网路堵塞、电信部门技术调整和政府管制等。
	法律适用及争议解决
	本协议受中华人民共和国法律管辖。在履行本协议过程中如发生纠纷，双方应及时协商解决。协商不成时，任何一方可直接向北京市海淀区人民法院提起诉讼。
	附则
	12.1 本协议任何附件、北京畅卓科技有限公司平台页面上的服务说明、价格说明及您确认同意的订购页面内容等是本协议不可分割的一部分。如果前述具体约定与本协议有不一致之处，以前述具体约定为准。
	12.2 畅卓科技有权以提前30天在web.chanzor.cn上通告及以通知方式告知您的方式将本协议的权利义务全部或者部分转让给畅卓科技的关联公司。
	12.3 如果本协议中的任何条款被判定为无效或不具执行力，不影响本协议其余条款的有效性及约束力。本协议中的包括但不局限于保证、保密、知识产权、法律和管辖地条款在本协议终止应继续存在。
        </textarea>
						</div>
						<div class="modal-footer">
							<button type="button" data-dismiss="modal"
								class="btn btn-default">关闭</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- JQUERY-->
	<script src="<%=basePath%>static/vendor/jquery/dist/jquery.js"></script>
	<!-- BOOTSTRAP-->
	<script src="<%=basePath%>static/vendor/bootstrap/dist/js/bootstrap.js"></script>
	<!-- STORAGE API-->
	<script
		src="<%=basePath%>static/vendor/jQuery-Storage-API/jquery.storageapi.js"></script>
	<script src="<%=basePath%>static/js/common.js"></script>
	<script src="<%=basePath%>static/js/plugins/layer2.1/layer.js"></script>
	<script type="text/javascript">
	$(function(){
		passwordBind();
	})
		document.onkeydown = function(event) {
			var e = event || window.event
					|| arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) {
				log();
			}
		}
		function log() {
			var mobile = $("input[name=username]").val();
			if (mobile == '') {
				layer.msg("请输入手机号/账户名", {
					icon : 2
				});
				$("input[name=username]").focus();
				return;
			}
			var password = $("input[name=password]").val();
			if (password == '') {
				layer.msg("请输入密码", {
					icon : 2
				});
				$("input[name=password]").focus();
				return;
			}
			$.ajax({
				url : 'log',
				type : 'post',
				data : $('#logform').serialize(),
				success : function(data) {
					if (data.msg) {
						var icon = 1;
						if (data.code != '00' && data.code != '01' && data.code != '04' )
							icon = 2;
						layer.msg(data.msg, {
							icon : icon
						});
						if (data.code == '01') {
							$("#userId").val(data.uid);
							$("#saveMobileDiv").show();
							$("#loginDiv").hide();
						}else if (data.code == '04') {
							$("#show_login_smsCode").show();
							
						}else if (data.code == '00') {
							window.location.href = 'index';
						}
					}
				}
			});
		}

		function registerSub() {
			var mobile = $("#mobile").val();
			var password = $("#register_password").val();
			var registercord = $("#register-cord").val();
			var registersmsCode = $("#register-smsCode").val();

			var icon = 2;
			if (mobile == null || mobile == '') {
				layer.msg("请输入注册手机号!", {
					icon : icon
				});
				$("#mobile").focus();
				return false;
			} else if (registercord == null || registercord == '') {
				layer.msg("请输入图形验证码!", {
					icon : icon
				});
				$("#register-cord").focus();
				return false;
			} else if (registersmsCode == null || registersmsCode == '') {
				layer.msg("请输入短信验证码!", {
					icon : icon
				});
				$("#register-smsCode").focus();
				return false;
			} else if (password == null || password == ''
					|| password.length < 6) {
				layer.msg("请输入密码，密码长度不能小于六位!", {
					icon : icon
				});
				$("#password").focus();
				return false;
			}
			$.ajax({
				url : 'registerSub',
				type : 'post',
				data : $('#registerform').serialize(),
				success : function(data) {
					if (data == 200) {
						layer.msg("注册成功!");
						$.ajax({
							url : 'log',
							type : 'post',
							data : {
								username : $('#mobile').val(),
								password : $('#register_password').val()
							},
							success : function(data) {
								if (data.msg) {
									var icon = 1;
									if (data.code != '00')
										icon = 2;
									layer.msg(data.msg, {
										icon : icon
									});
									if (data.code == '00') {
										window.location.href = 'index';
									}
								}
							}
						});
					} else if (data == 205) {
						layer.msg("请填写完整数据!", {
							icon : icon
						});
					} else if (data == 201) {
						layer.msg("该手机号已经注册，不能重复使用!", {
							icon : icon
						});
					} else if (data == 203) {
						layer.msg("连接附带客户经理不存在，请联系客户经理确认注册链接!", {
							icon : icon
						});
					} else if (data == 204) {
						layer.msg("短信验证码错误!", {
							icon : icon
						});
					} else {
						layer.msg("注册未成功", {
							icon : icon
						});
					}
				}
			});
		}

		//发送验证码
		var wait = 60;
		function sendSMSCode(obj) {
			var mobile = $('input[name=mobile]').val();
			var reg = /^[1][3,4,5,7,8][0-9]{9}$/g;
			var r = reg.exec(mobile);
			if (r == null) {
				layer.msg("请输入正确的手机号", {
					icon : 2
				});
				$('input[name=savemobile]').focus();
				return;
			}
			var imageCode = $('input[name=register-cord]').val();
			var d = {
				mobile : mobile,
				imageCode : imageCode,
				validate : 'true'
			};
			$.ajax({
				url : 'sendSMSCodeRegister',
				data : d,
				type : 'post',
				success : function(data) {
					if (data.code == '00') {
						layer.msg(data.msg, {
							icon : 1
						});
						time(obj);
					} else {
						layer.msg(data.msg, {
							icon : 2
						});
					}
				}
			});
		}
		
		//账户安全绑定手机号发送验证码
		function saveMobileSendSMSCode(obj) {
			var mobile = $('input[name=savemobile]').val();
			var reg = /^[1][3,4,5,7,8][0-9]{9}$/g;
				var r = reg.exec(mobile);
				if (r == null) {
					layer.msg("请输入正确的手机号", {
						icon : 2
					});
					$('input[name=savemobile]').focus();
					return;
				}
			var d = {
				mobile : mobile,
				validate : 'true'
			};
			$.ajax({
				url : 'saveMobileSendSMSCode',
				data : d,
				type : 'post',
				success : function(data) {
					if (data.code == '00') {
						layer.msg(data.msg, {
							icon : 1
						});
						time(obj);
					} else {
						layer.msg(data.msg, {
							icon : 2
						});
					}
				}
			});
		}
		
		//绑定手机号内容提交
		function saveMobileSub(){
			var mobile = $("#savemobile-smsCode").val();
			if (mobile == null || mobile == '') {
				layer.msg("请输入手机验证码!", {
					icon : 2
				});
				$("#savemobile-smsCode").focus();
				return false;
			}
			$.ajax({
				url : 'saveMobileSub',
				data : $('#saveMobileform').serialize(),
				type : 'post',
				success : function(data) {
					if (data.msg) {
						var icon = 1;
						if (data.code != '00')
							icon = 2;
						layer.msg(data.msg, {
							icon : icon
						});
						if (data.code == '00') {
							window.location.href = 'index';
						}
					}
				}
			});
		}
		//登陆验证码发送 
		function loginSendSMSCode(obj){
			$.ajax({
				url : 'loginSendSMSCode',
				data : $('#logform').serialize(),
				type : 'post',
				success : function(data) {
					if (data.code == '00') {
						layer.msg(data.msg, {
							icon : 1
						});
						time(obj);
					} else {
						layer.msg(data.msg, {
							icon : 2
						});
					}
				}
			});
		}
		
		//忘记密码发送验证码
		function recoverSendSMSCode(obj) {
			var mobile = $('input[name=recoverMobile]').val();
			var reg = /^[1][3,4,5,7,8][0-9]{9}$/g;
				var r = reg.exec(mobile);
				if (r == null) {
					layer.msg("请输入正确的手机号", {
						icon : 2
					});
					$('input[name=recoverMobile]').focus();
					return;
				}
			var d = {
				mobile : mobile,
				validate : 'true'
			};
			$.ajax({
				url : 'sendResetCode',
				data : d,
				type : 'post',
				success : function(data) {
					if (data.msg) {
						var icon = 1;
						if (data.statusCode != 200) {
							icon = 2;
						}else{
							time(obj);
						}
						layer.msg(data.msg, {
							icon : icon
						});
					}
				}
			});
		}
		
		function recoverSub(){
			var mobile = $("#recoverMobile").val();
			var password = $("#recover_password").val();
			var code = $("#recover-smsCode").val();
			if (mobile == null || mobile == '') {
				layer.msg("请输入手机号码!", {
					icon : 2
				});
				$("#recoverMobile").focus();
				return false;
			}
			if (code == null || code == '') {
				layer.msg("请输入手机验证码!", {
					icon : 2
				});
				$("#recover-smsCode").focus();
				return false;
			}
			if (password == null || password == '') {
				layer.msg("请输入新密码!", {
					icon : 2
				});
				$("#recover_password").focus();
				return false;
			}
			if (passwordStrength(password) < '2') {
				layer.msg("请修改密码，强度太弱", {
					icon : 2
				});

				return;
			}
			$.ajax({
				url : 'reset',
				type : 'post',
				data : {mobile:mobile, password:password, code:code},
				success : function(data) {
					if (data.msg) {
						var icon = 1;
						if (data.statusCode != 200) {
							icon = 2;
						}
						layer.msg(data.msg, {
							icon : icon
						});
						if (data.statusCode == 200) {
							window.setTimeout(redirect, 2000);
						}
					}
				}
			});
		}
		
		//2秒后自动跳转到登陆页
		function redirect() {
			window.location.href = '<%=basePath%>';
		}

		function loginout() {
			window.location.href = 'logout';
		}

		function refreshCode(obj) {
			var d = new Date();
			$(obj).attr('src', 'loginCode.html?t=' + d);
		}

		function showterms() {
			$('#myModal').modal('show');
		}

		function registerShow() {
			$("#loginDiv").hide();
			$("#registerDiv").show();
		}

		function loginShow() {
			$("#registerDiv").hide();
			$("#recoverDiv").hide();
			$("#loginDiv").show();
		}

		function recoverShow() {
			$("#loginDiv").hide();
			$("#recoverDiv").show();
		}
		function passwordBind() {
			$('#recover_password')
					.keyup(
							function() {
								var strongRegex0 = new RegExp(
										"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$",
										"g"); // 四种均包含 强
								var strongRegex1 = new RegExp(
										"^(?=.{7,})(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$",
										"g"); // 小写，数字，特殊
								var strongRegex2 = new RegExp(
										"^(?=.{7,})(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$",
										"g"); // 大写，数字，特殊
								var strongRegex3 = new RegExp(
										"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$",
										"g"); // 大写，小写，数字
								var strongRegex4 = new RegExp(
										"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*\\W).*$",
										"g"); // 大写，小写，特殊
								var mediumRegex0 = new RegExp(
										"^(?=.{6,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",
										"g");
								var mediumRegex1 = new RegExp(
										"^(?=.{6,})(((?=.*[A-Z])(?=.*\\W))|((?=.*\\W)(?=.*[0-9]))|((?=.*[a-z])(?=.*\\W))).*$",
										"g");
								var enoughRegex = new RegExp("(?=.{5,}).*", "g");
								if (false == enoughRegex.test($(this).val())) {
									$('#level').removeClass('pw-weak');
									$('#level').removeClass('pw-medium');
									$('#level').removeClass('pw-strong');
									$('#level').addClass(' pw-defule');
									// 密码小于六位的时候，密码强度图片都为灰色
								} else if (strongRegex0.test($(this).val())) {
									$('#level').removeClass('pw-weak');
									$('#level').removeClass('pw-medium');
									$('#level').removeClass('pw-strong');
									$('#level').addClass(' pw-strong');
								} else if (strongRegex1.test($(this).val())) {
									$('#level').removeClass('pw-weak');
									$('#level').removeClass('pw-medium');
									$('#level').removeClass('pw-strong');
									$('#level').addClass(' pw-strong');
								} else if (strongRegex2.test($(this).val())) {
									$('#level').removeClass('pw-weak');
									$('#level').removeClass('pw-medium');
									$('#level').removeClass('pw-strong');
									$('#level').addClass(' pw-strong');
								} else if (strongRegex3.test($(this).val())) {
									$('#level').removeClass('pw-weak');
									$('#level').removeClass('pw-medium');
									$('#level').removeClass('pw-strong');
									$('#level').addClass(' pw-strong');
								} else if (strongRegex4.test($(this).val())) {
									$('#level').removeClass('pw-weak');
									$('#level').removeClass('pw-medium');
									$('#level').removeClass('pw-strong');
									$('#level').addClass(' pw-strong');
								} else if (mediumRegex0.test($(this).val())) {
									$('#level').removeClass('pw-weak');
									$('#level').removeClass('pw-medium');
									$('#level').removeClass('pw-strong');
									$('#level').addClass(' pw-medium');
								} else if (mediumRegex1.test($(this).val())) {
									$('#level').removeClass('pw-weak');
									$('#level').removeClass('pw-medium');
									$('#level').removeClass('pw-strong');
									$('#level').addClass(' pw-medium');
								} else {
									$('#level').removeClass('pw-weak');
									$('#level').removeClass('pw-medium');
									$('#level').removeClass('pw-strong');
									$('#level').addClass('pw-weak');
								}
								return true;
							});
		}
		function passwordStrength(passwordValue) {
			var strongRegex0 = new RegExp(
					"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$",
					"g"); // 四种均包含
			// 强
			var strongRegex1 = new RegExp(
					"^(?=.{7,})(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); // 小写，数字，特殊
			var strongRegex2 = new RegExp(
					"^(?=.{7,})(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$", "g"); // 大写，数字，特殊
			var strongRegex3 = new RegExp(
					"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$", "g"); // 大写，小写，数字
			var strongRegex4 = new RegExp(
					"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*\\W).*$", "g"); // 大写，小写，特殊
			var mediumRegex0 = new RegExp(
					"^(?=.{6,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",
					"g");
			var mediumRegex1 = new RegExp(
					"^(?=.{6,})(((?=.*[A-Z])(?=.*\\W))|((?=.*\\W)(?=.*[0-9]))|((?=.*[a-z])(?=.*\\W))).*$",
					"g");
			var enoughRegex = new RegExp("(?=.{5,}).*", "g");

			if (false == enoughRegex.test(passwordValue)) { // 密码小于6位，不允许
				return 0;
			} else if (strongRegex0.test(passwordValue)) { // 密码强
				return 3;
			} else if (strongRegex1.test(passwordValue)) { // 密码强
				return 3;
			} else if (strongRegex2.test(passwordValue)) { // 密码强
				return 3;
			} else if (strongRegex3.test(passwordValue)) { // 密码强
				return 3;
			} else if (strongRegex4.test(passwordValue)) { // 密码强
				return 3;
			} else if (mediumRegex0.test(passwordValue)) { // 密码中
				return 2;
			} else if (mediumRegex1.test(passwordValue)) { // 密码中
				return 2;
			} else { // 密码弱
				return 1;
			}
		}
	</script>
</body>
</html>