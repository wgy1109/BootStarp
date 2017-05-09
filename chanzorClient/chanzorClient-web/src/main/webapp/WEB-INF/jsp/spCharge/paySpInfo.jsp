<%@ page pageEncoding="UTF-8" import="com.chanzor.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class='charging_online'>
	<div class='chanzorDataList_title'>
		<p>充值</p>
	</div>
	<input type="hidden" id="hideSpId" value="${spId }" />
	<form action="#" method='post' id="payForm">
		<div class='charging_online_top1'>
			<div class='charging_online_top1_left'>
				<span>充值类型</span>
			</div>
			<div class='charging_online_top1_right'>
				<input type="radio" name='charging_onlineInp'
					id='charging_onlineInp1' checked onclick="zfbCharge()" /> <label
					for="charging_onlineInp1" id="charging_online_top1_rightLabel1">在线充值</label>
				<input type="radio" name='charging_onlineInp'
					id='charging_onlineInp2' onclick="outLineCharge()" /><label
					for="charging_onlineInp2" id="charging_online_top1_rightLabel2">线下充值</label>
				<span class='charging_online_top1_rightSp0'>消费单次/累积满5000元以上才可以开具发票</span>
				<span class='charging_online_top1_rightSp'>你目前没有可以用的优惠券</span>&nbsp;&nbsp;
				<span class='charging_online_top1_rightSp'>优惠券不可开具发票</span> <span
					class='charging_online_top1_rightSp1'>你目前有5张可用优惠券</span>&nbsp;&nbsp;
				<span class='charging_online_top1_rightSp1'>优惠卷不可开具发票</span>
			</div>
		</div>

		<!--************************************这里还需要设置2个盒子，其他的要设置隐藏*************************************************-->
		<div class='charging_online_bigBox' id="zfbCharegeArea"
			style='width: 100%;'>
			<div class='charging_online_top2'>
				<div class='charging_online_top2_left'>
					<span>充值应用</span>
				</div>
				<div class='charging_online_top2_right'>
					<select name="spId" id="spInfoList">
					</select>
				</div>
			</div>
			<div class='charging_online_top4'>
				<div class='charging_online_top4_left'>
					<span>充值方式</span>
				</div>
				<div class='charging_online_top4_right'>
					<div
						style="height: 100%; line-height: 30px; /* vertical-align: baseline; */ float: left; padding-right: 5px;">
						<input type="radio" value="1" name="accountChargeType"
							onclick="changeMinCharge(1)" checked=""><span></span>
					</div>
					<div style="float: left">
						<div style="float: left">
							<img src="static/img/walletAccount.png" alt="" /> <span
								style="font-size: 16px;"></span>
						</div>
						<div style="float: left; padding-left: 10px">
							<p>账户余额</p>
							<p style="color: #CCCCCC">
								可用余额<span id="leftoverNum">${accountBalance/100}</span>元
							</p>
						</div>
					</div>

					<div style="padding-left: 300px">
						<input type="radio" name="accountChargeType"
							onclick="changeMinCharge(2)" value="2"><span></span> <img
							src="static/img/alipay.png" alt="" />
					</div>
				</div>
			</div>
			<div class='charging_online_top3'>
				<div class='charging_online_top3_left'>
					<span>产品充值</span>
				</div>
				<!--**************************tab切换栏开始************************************-->
				<div class='charging_online_top3_right'>
					<ul id="myTab" class="nav nav-tabs">
						<li class="active"><a href="#domiestic"
							target-type="domiestic" service-type="1" data-toggle="tab">国内短信</a></li>
						<li><a href="#inter" target-type="inter" service-type="2"
							data-toggle="tab">国际短信</a></li>
						<li><a href="#voice" target-type="voice" service-type="3"
							data-toggle="tab">语音验证码</a></li>
					</ul>
					<div id="myTabContent" class="tab-content" style="height: 260px">
						<input type="hidden" name="chargeType" id="chargeType"
							value="domiestic">
						<div class="tab-pane fade in active" id="domiestic">
							<div style="width: 50%" class="pull-left">
								<input type="hidden" id="packageId" name="packageId" value="">
								<div class='charging_online_top3Div1'>
									<div class="charging_online_price">
										<span>充值金额：</span>
									</div>
									<div class="charging_online_input">
										<input type="text" id="messageNumInput" name="messageNumInput" />
										<span name="minCharge" style="display: none">单次充值最少为10000元。</span>
									</div>
								</div>
								<div class='charging_online_top3Div1'>
									<div class="charging_online_price">
										<span>单价：</span>
									</div>
									<div class="charging_online_input">
										<span id="domiesticPrice"></span>
									</div>
								</div>
								<div class='charging_online_top3Div1'>
									<div class="charging_online_price">
										<span>充值条数:</span>
									</div>
									<div class="charging_online_input">
										<span id="priceHtml"></span>
									</div>
								</div>
							</div>
							<div class="pull-left chargin_online_desc_div">
								<div class="charging_online_desc">产品价格说明</div>
								<div class="charging_online_desc_detail">
									<p>充值金额<=10000元，单价0.06元一条</p>
									<p>充值金额10001-20000元，单价0.058元一条</p>
									<p>充值金额20001-30000元，单价0.055元一条</p>
									<p>充值金额>30000元，单价0.05元一条</p>
									<p>充值金额大于50000元请联系客服</p>
								</div>
							</div>
						</div>
						<div class="tab-pane fade" id="inter">
							<div style="width: 50%" class="pull-left">
								<input type="hidden" id="packageId" name="packageId" value="">
								<div class='charging_online_top3Div1'>
									<div class="charging_online_price">
										<span>充值金额：</span>
									</div>
									<div class="charging_online_input">
										<input type="text" id="inputInter" name="inputInter" /> <span
											name="minCharge" style="display: none">单次充值需大于10000元。</span>
									</div>
								</div>
								<div class='charging_online_top3Div1'>
									<div class="charging_online_price">
										<span>折扣：</span>
									</div>
									<div class="charging_online_input">
										<span id="interDiscount">无折扣</span>
									</div>
								</div>
								<div class='charging_online_top3Div1'>
									<div class="charging_online_price">
										<span>实付金额:</span>
									</div>
									<div class="charging_online_input">
										<span id="interPriceHtml"></span>
									</div>
								</div>
							</div>
							<div class="pull-left chargin_online_desc_div">
								<div class="charging_online_desc">产品价格说明</div>
								<div class="charging_online_desc_detail">
									<p>充值金额<=10000元，无折扣</p>
									<p>充值金额10001-20000元，9.5折</p>
									<p>充值金额20001-50000元，8.1折</p>
									<p>充值金额大于50000元请联系客服</p>
								</div>
							</div>
						</div>
						<!-- 语音短信开始 -->
						<div class="tab-pane fade" id="voice">
							<div style="width: 50%" class="pull-left">
								<input type="hidden" id="packageId" name="packageId" value="">
								<div class='charging_online_top3Div1'>
									<div class="charging_online_price">
										<span>充值金额：</span>
									</div>
									<div class="charging_online_input">
										<input type="text" id="inputVoice" name="inputVoice" /> <span
											name="minCharge" style="display: none">单次充值需大于10000元。</span>
									</div>
								</div>
								<div class='charging_online_top3Div1'>
									<div class="charging_online_price">
										<span>单价：</span>
									</div>
									<div class="charging_online_input">
										<span id="voicePrice"></span>
									</div>
								</div>
								<div class='charging_online_top3Div1'>
									<div class="charging_online_price">
										<span>充值条数:</span>
									</div>
									<div class="charging_online_input">
										<span id="voicePriceHtml"></span>
									</div>
								</div>
							</div>
							<div class="pull-left chargin_online_desc_div">
								<div class="charging_online_desc">产品价格说明</div>
								<div class="charging_online_desc_detail">
									<p>充值金额<=10000元，单价0.06元一条</p>
									<p>充值金额10001-20000元，单价0.058元一条</p>
									<p>充值金额20001-30000元，单价0.055元一条</p>
									<p>充值金额>30000元，单价0.05元一条</p>
									<p>充值金额大于50000元请联系客服</p>
								</div>
							</div>
							<!--*******************************tab栏切换结束***************************************-->
						</div>
					</div>
				</div>
			</div>
			<div class='charging_online_top4_rightSub'>
				<input type="submit" class='btn  btn-primary btn-lg ' value='充值' />
			</div>
		</div>
	</form>
	<div class='charging_online_bigBox' id="outLineCharegeArea"
		style='width: 100%; display: none'>
		<div style="text-align: center">
			<img src="static/img/outLine.png" width="480px" height="600px">
		</div>
	</div>
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
