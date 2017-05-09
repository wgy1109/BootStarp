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
	HttpSession s = request.getSession();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
<meta name="renderer" content="webkit" />
<meta name="description" content="Bootstrap Admin App + jQuery">
<meta name="keywords"
	content="app, responsive, jquery, bootstrap, dashboard, admin">
<title>畅卓验证码短信飞常快---畅卓官网 在线短信平台,SDK短信接口,手机短信验证码,验证码通道</title>
<meta name="description"
	content="北京畅卓科技有限公司是一家拥有卓越运营支撑能力的短信服务商，为企业提供一站式的短信验证码应用解决方案，拥有106高端通道免费试用，3秒最快，99.99%到达率，咨询热线：400-793-0000，产品包括：短信验证码，短信平台，短信验证码，手机短信平台，验证码短信，在线短信群发平台，短信通道，短信接口，短信服务商，短信营销，验证码平台，验证码通道，验证码接收，验证码接收平台，手机验证码短信">
<meta name="keywords"
	content="短信验证码，短信平台，短信验证码，手机短信平台，验证码短信，在线短信群发平台，短信通道，短信接口，短信服务商，短信营销，验证码平台，验证码通道，验证码接收，验证码接收平台，手机验证码短信，SDK短信接口">
<link rel="stylesheet"
	href="<%=basePath%>static/vendor/simple-line-icons/css/simple-line-icons.css">
<link rel="stylesheet" href="<%=basePath%>static/css/bootstrap.css"
	id="bscss">
<link rel="stylesheet" href="<%=basePath%>static/css/app.css"
	id="maincss">
<link rel="stylesheet" href="<%=basePath%>static/css/font-awesome.css">
<link rel="stylesheet"
	href="<%=basePath%>assets/js/plugins/slick/slick.min.css">
<link rel="stylesheet"
	href="<%=basePath%>assets/js/plugins/slick/slick-theme.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/css/oneui.css">
<link rel="stylesheet"
	href="<%=basePath%>assets/css/themes/flat.min.css">
<link rel="stylesheet" href="<%=basePath%>static/css/chanzor.css">
<link rel="stylesheet"
	href="<%=basePath%>static/css/bootstrap-slider.css">
<link rel="stylesheet" href="<%=basePath%>static/css/layer.css">
</head>
<body>
	<div class='topDiv'>
		<ul class='topDivUl'>
			<!-- 			<li><a href="javascript:;" class='opDivUl_firstLi'></a> <span -->
			<!-- 				id="unReadNum" class="dropdown-toggle" -->
			<!-- 				style='display: none; width: 27px; height: 20px; border-radius: 5px; background-color: darkorange; position: absolute; top: 17px; right: 280px; text-align: center; color: white; font-size: 14px;'></span> -->
			<!-- 			</li> -->
			<checkLand:check type="sessionUser">
				<li>
					<div class="btn-group" id="dropdownBtn">
						<button
							class="btn-lg btn-info btn-image dropdown-toggle toggleBtn"
							data-toggle="dropdown" type="button" aria-expanded="false"
							onclick="goPage('<%=basePath%>InsideLetter/index.html','static/js/insideLetter/index.js')"
							style="height: 50px; border: 1px white; border-left: 1px #3cacd0 solid; border-radius: 0px; background-color: #009ACB;">站内信</button>

						<span id="unReadNum" class="dropdown-toggle"
							style="width: 20px; height: 18px; border-radius: 5px; position: absolute; top: 16px; right: 3px; color: white; font-size: 12px; background-color: rgb(255, 140, 0); z-index: 10; display: none">""</span>
						<ul class="dropdown-menu dropdown-menu-left"
							style="background-color: #394F67; width: 350px">
							<li class="dropdown-header" id="insideLetterInfo">站内信消息通知</li>
							<li class="dropdown-header"
								style="clear: both; text-align: center">
								<div>
									<a href="javascript:;" style="color: white;"
										onclick="goPage('<%=basePath%>InsideLetter/index.html','static/js/insideLetter/index.js')">查看更多</a>
								</div>
							</li>
						</ul>
					</div>
				</li>
			</checkLand:check>
			<li><a href="#" class='topDivUl_li_pad'
				onclick="goPage('<%=basePath%>saleSupport.html','static/js/info/info.js')">销售支持</a></li>
			<li><a href="#" class='topDivUl_li_pad'
				onclick="goPage('<%=basePath%>showHelpMessage.html','static/js/info/helpmessage.js')">帮助</a></li>
			<li><img
				src="${photo_img==null?'static/img/defaultPhoto.png': photo_img}"
				id="homeImg" width="20px" height="20px"
				style="position: absolute; margin: 16px 10px 10px 5px"><a
				href="<%=basePath%>logout" class='opDivUl_fouLi'>退出</a></li>
		</ul>
	</div>
	<nav id="sidebar">
		<div id="sidebar-scroll">
			<div class="sidebar-content">
				<div class="side-content">
					<ul class="nav-main">
						<checkLand:permissionCheck permissionCode="wdsy">
							<li><a data-toggle="nav-submenu" href="javascript:;"
								onclick="goPage('<%=basePath%>spInfo/mySpinfo.html','static/js/spInfo/mySpinfo.js')"><i><img
										src="img/myfir.png"></i><span class="sidebar-mini-hide"
									id="myHomePage">我的首页</span></a></li>
						</checkLand:permissionCheck>
						<checkLand:permissionCheck permissionCode="yygl">
							<li><a class="nav-submenu" data-toggle="nav-submenu"
								href="#"><i><img class="img-position"
										src="static/img/appAd.png"></i><span
									class="sidebar-mini-hide">应用管理</span></a>
								<ul>
									<checkLand:permissionCheck permissionCode="yygl_yylb">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>spInfo/mySpInfoList.html','static/js/spInfo/index.js')"><span>应用列表</span></a></li>
									</checkLand:permissionCheck>
									<checkLand:permissionCheck permissionCode="yygl_hmdlb">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>blacklist.html','static/js/blacklist/blacklist.js')">黑名单列表</a></li>
									</checkLand:permissionCheck>
									<checkLand:permissionCheck permissionCode="yygl_bmdlb">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>whitelist.html','static/js/blacklist/whitelist.js')">白名单列表</a></li>
									</checkLand:permissionCheck>
								</ul></li>
						</checkLand:permissionCheck>
						<checkLand:permissionCheck permissionCode="gndx">
							<li><a class="nav-submenu" data-toggle="nav-submenu"
								href="#"><i><img class="img-position"
										src="img/mesAdm.png"></i><span class="sidebar-mini-hide">国内短信</span></a>
								<ul>
									<checkLand:permissionCheck permissionCode="gndx_fstj">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>SendStatistics.html','static/js/sendStatistics/sendStatisticslist.js')">发送统计</a></li>
									</checkLand:permissionCheck>

									<checkLand:permissionCheck permissionCode="gndx_fsdx">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>smsSendTaskClient/sendMessage','static/js/smsSendTaskClient/sendMessage.js')">发送短信</a></li>
									</checkLand:permissionCheck>
									<checkLand:permissionCheck permissionCode="gndx_fsdx">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>smsSendTaskClient/customizedIndex','static/js/smsSendTaskClient/sendCustomizedMessage.js')">发送定制短信</a></li>
									</checkLand:permissionCheck>
									<checkLand:permissionCheck permissionCode="gndx_fslb">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>smsSendTaskClient.html','static/js/sendTask/smsSendTaskClient_list.js')">发送列表</a></li>
									</checkLand:permissionCheck>
									<checkLand:permissionCheck permissionCode="gndx_fslb">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>smsSendTaskClient/timing','static/js/smsSendTaskClient/smsTiming.js')">定时短信列表</a></li>
									</checkLand:permissionCheck>

									<checkLand:permissionCheck permissionCode="gndx_yfdxmx">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>smsSendTaskClient/mt','static/js/smsSendTaskClient/smsMtDetail.js')">已发短信明细</a></li>
									</checkLand:permissionCheck>

									<checkLand:permissionCheck permissionCode="gndx_dxhf">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>smsReplyRecodeClient.html','static/js/replyRecode/smsReplyRecodeClient_list.js')">短信回复</a></li>
									</checkLand:permissionCheck>

									<checkLand:permissionCheck permissionCode="gndx_dxmb">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>smsMasterplateClient.html','static/js/masterplate/smsMasterplateClient_list.js')">短信模板</a></li>
									</checkLand:permissionCheck>
								</ul></li>
						</checkLand:permissionCheck>
						<checkLand:permissionCheck permissionCode="gjdx">
							<li><a class="nav-submenu" data-toggle="nav-submenu"
								href="#"><i><img class="img-position"
										src="static/img/b.png"></i><span class="sidebar-mini-hide">国际短信</span></a>
								<ul>
									<checkLand:permissionCheck permissionCode="gjdx_fstj">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>interSendStatistics.html','static/js/interSmssendtaskclient/interSendStatisticsMessage.js')">发送统计</a></li>
									</checkLand:permissionCheck>

									<checkLand:permissionCheck permissionCode="gjdx_fsdx">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>intersmsSendTaskClient/sendMessage','static/js/interSmssendtaskclient/interSendmessage.js')">发送短信</a></li>
									</checkLand:permissionCheck>

									<checkLand:permissionCheck permissionCode="gjdx_fslb">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>intersmsSendTaskClient.html','static/js/interSmssendtaskclient/interSmssendtaskclient_list.js')">发送列表</a></li>
									</checkLand:permissionCheck>

									<checkLand:permissionCheck permissionCode="gjdx_yfdxmx">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>intersmsSendTaskClient/mt','static/js/interSmssendtaskclient/interSmsmtdetail.js')">已发短信明细</a></li>
									</checkLand:permissionCheck>

									<checkLand:permissionCheck permissionCode="gjdx_dxbj">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>intersmspriceClient.html','static/js/interSmssendtaskclient/interSmsPrice_list.js')">短信报价</a></li>
									</checkLand:permissionCheck>
								</ul></li>
						</checkLand:permissionCheck>

						<checkLand:permissionCheck permissionCode="yyyz">
							<li><a class="nav-submenu" data-toggle="nav-submenu"
								href="#"><i><img class="img-position"
										src="static/img/c.png"></i><span class="sidebar-mini-hide">语音验证</span></a>
								<ul>
									<checkLand:permissionCheck permissionCode="yyyz_fstj">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>voiceSendStatistics.html','static/js/voice/voiceSendStatisticsMessage.js')">发送统计</a></li>
									</checkLand:permissionCheck>
									<checkLand:permissionCheck permissionCode="yyyz_fswbyy">
										<li><a href="javascript:;"
											onclick="goPage('smsSendTextVoice/index','static/js/sendTextVoice/sendTextVoice.js')"">发送文本语音</a></li>
									</checkLand:permissionCheck>
									<checkLand:permissionCheck permissionCode="yyyz_wbyymb">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>textVoice.html','static/js/textVoice/textVoice.js')">文本语音模板</a></li>
									</checkLand:permissionCheck>
									<checkLand:permissionCheck permissionCode="yyyz_fsyywj">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>sendVoice.html','static/js/sendVoice/sendVoice.js')">发送语音文件</a></li>
									</checkLand:permissionCheck>
									<checkLand:permissionCheck permissionCode="yyyz_yywjpz">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>voice.html','static/js/voice/voice.js')">语音文件配置</a></li>
									</checkLand:permissionCheck>
									<checkLand:permissionCheck permissionCode="yyyz_fslb">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>voicesmsSendTaskClient.html','static/js/voice/voiceSmssendtaskclient_list.js')">发送列表</a></li>
									</checkLand:permissionCheck>
									<checkLand:permissionCheck permissionCode="yyyz_yydxmx">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>voicesmsSendTaskClient/mt','static/js/voice/voiceSmsmtdetail.js')">语音短信明细</a></li>
									</checkLand:permissionCheck>
								</ul></li>
						</checkLand:permissionCheck>
						<checkLand:permissionCheck permissionCode="cwgl">
							<li><a class="nav-submenu" data-toggle="nav-submenu"
								href="#"><i><img class="img-position"
										src="static/img/payAdm.png" /></i><span class="sidebar-mini-hide">财务管理</span></a>
								<ul>
									<li><a href="javascript:;"
										onclick="goPage('<%=basePath%>account/index.html','static/js/info/account.js')">账户充值</a></li>
									<checkLand:permissionCheck permissionCode="cwgl_yycz">
									<li><a href="javascript:;"
										onclick="goPage('<%=basePath%>chargeRecord/directPaySpInfo','static/js/spCharge/paySpInfo.js')">应用充值</a></li>
									</checkLand:permissionCheck>
									<li><a href="javascript:;"
										onclick="goPage('<%=basePath%>chargeRecord/chargeRecordIndex.html','static/js/chargeRecord/index.js')">订单列表</a></li>
									<li><a href="javascript:;"
										onclick="goPage('<%=basePath%>consumerDetail/spConsumer.html','static/js/spConsumerDetail/ConsumeIndex.js')">消费明细</a></li>
									<%-- <checkLand:check type="sessionUser">
										<li><a href="javascript:;"
											onclick="goPage('<%=basePath%>appFinanceInvoiceClient.html','static/js/financeInvoice/appFinanceInvoiceClient_list.js')">发票管理</a></li>
									</checkLand:check> --%>
									<checkLand:permissionCheck permissionCode="cwgl_dxht">
									<li><a href="javascript:;" onclick="ContractDownload()">短信合同</a></li>
									</checkLand:permissionCheck>
									<!-- 								<li><a href="javascript:;" -->
									<%-- 									onclick="goPage('<%=basePath%>notice/index.html','static/js/notice/notice.js')">合同管理 --%>
									<!-- 								</a></li> -->
								</ul></li>
						</checkLand:permissionCheck>
						<checkLand:permissionCheck permissionCode="zhgl">
							<li><a class="nav-submenu" data-toggle="nav-submenu"
								href="#"><i><img class="img-position"
										src="static/img/accAd.png"></i><span
									class="sidebar-mini-hide">账号管理</span></a>
								<ul>
									<li><a href="javascript:;"
										onclick="goPage('<%=basePath%>baseInfo.html','static/js/info/info.js')">账号信息</a></li>
									<li><a href="javascript:;"
										onclick="goPage('<%=basePath%>auth.html','static/js/info/info.js')">企业认证</a></li>

									<li><a href="javascript:;"
										onclick="goPage('<%=basePath%>vatinvoice.html','static/js/info/info.js')">发票认证</a></li>

<!-- 									<li><a href="javascript:;" id="myAddressHref" -->
<%-- 										onclick="goPage('<%=basePath%>address.html','static/js/info/address.js')">我的地址</a></li> --%>
									<li><a href="javascript:;"
										onclick="goPage('<%=basePath%>subAccount.html','static/js/info/subaccount.js')">子账号</a></li>
								</ul></li>
						</checkLand:permissionCheck>
					</ul>
				</div>
			</div>
		</div>
	</nav>
	<!-- 	<div -->
	<!-- 		style="background-color: #559AFA; position: fixed; right: 0%; top: 120px; width: 40px; z-index: 999; height: 200px; border: 2px solid #9f9f9f;"> -->
	<!-- 		<div class="lxw" style="color: white; font-size: 12px; height: 25px;"> -->
	<!-- 			<img src="../static/img/support.png" /> -->
	<!-- 			<ul class="supportFontInfo"> -->
	<!-- 				<li>技</li> -->
	<!-- 				<li>术</li> -->
	<!-- 				<li>支</li> -->
	<!-- 				<li>持</li> -->
	<!-- 			</ul> -->
	<!-- 		</div> -->
	<!-- 	</div> -->
	<div id="content">