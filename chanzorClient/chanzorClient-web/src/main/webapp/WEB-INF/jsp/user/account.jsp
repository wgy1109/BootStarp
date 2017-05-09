<%@ page pageEncoding="UTF-8" import="com.chanzor.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="checkLand" uri="/chanzor"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<main id="main-container" class="main-container"
	style="padding-left:220px;padding-top:5%">
<checkLand:permissionCheck permissionCode="cwgl_zhcz">
<div class="container-fluid">
	<div class="row-fluid" style="width: 94%">
		<div class="span6 col-md-6"
			style="height: 200px; background-color: white">
			<div class="col-md-3">
				<div style="height: 20%; padding-top: 20px; font-size: 26px;">
					<h3>我的账户</h3>
				</div>
				<div style="width: 100%; height: 80%">
					<img
						src="${photo_img==null?'static/img/defaultPhoto.png': photo_img}"
						width="100px" height="100px"
						style="float: right; margin-top: 15px" />
				</div>
			</div>
			<div class="col-md-9">
				<div style="padding-top: 45px;">
					<div style="padding: 10px 0px 10px 0px">
						<label>账号余额 </label>
					</div>
					<div style="padding: 5px 0px 10px 0px; height: 40px;">
						<div class="col-md-3" style="padding-left: 0px;">
							<label style="color: #009acb; font-size: 20px;"
								id="accountBalance">${accountBalance/100}元</label>
						</div>
						<div class="col-md-2 col-md-offset-1" style="padding-left: 0px;">
							<input class="btn btn-success" type="button" value="刷新"
								onclick="findAccountBalance()">
						</div>
						<div class="col-md-3 col-md-offset-3">
							<label><input class="btn btn-success" type="button"
								onclick="goPage('<%=basePath%>chargeRecord/directPaySpInfo','static/js/spCharge/paySpInfo.js')"
								value="应用充值"></label>
						</div>
					</div>
					<div style="padding: 10px 0px 10px 0px">
						<label style="color: #CCCCCC">账户余额暂不支持提现,账户余额可用于应用充值</label>
					</div>
				</div>
			</div>
		</div>
		<div class="span6 col-md-5"
			style="background-color: white; height: 200px; margin-left: 10px">
			<form action="" id="chargeNumForm">
				<div style="padding-top: 45px;">
					<div style="padding: 10px 0px 10px 0px; height: 45px">
						<div class="col-md-3">
							<label>充值类型 :</label>
						</div>
						<div class="col-md-9">
							<input type="radio" name="chargeType" value="1" checked="checked">线上充值
						</div>
					</div>
					<div style="padding: 10px 0px 10px 0px; height: 45px">
						<div class="col-md-3">
							<label>充值金额:</label>
						</div>
						<div class="col-md-9">
							<div>
								<input type="text" name="chargeNum" id="chargeNum">元
							</div>
						</div>
					</div>
					<div style="padding: 10px 0px 10px 0px; height: 45px">
						<div class="col-md-3">
							<label>充值方式:</label>
						</div>
						<div class="col-md-9" style="padding-left:0px;">
							<div class="col-md-4">
								<img alt="" src="static/img/alipay_small.png" height="30px">
							</div>
							<div class="col-md-3">
								<input type="submit" style="margin-top:3px;" value="余额充值" id="balanceRecharge"
									class="btn btn-success">
							</div>
							<div class="col-md-3" style="padding-top: 8px;">
								<a href="javascript:;" onclick="outLineChargeAccount()">线下充值</a>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
</checkLand:permissionCheck>
<div id="outline" style="display: none">
	<img src="static/img/outLine.png">
</div>
<div class='chanzorDataList'
	style="margin-left: 15px; margin-top: 20px;">
	<div class='chanzorDataList_title' style="margin-bottom: 30px">
		<p style="font-size: 20px">交易记录</p>
	</div>
	<div class="form-inline" style='text-align: right; margin-right: 2%;'>
		<div class="form-group">
			<label for="telph" class='listLab'>交易类型：</label> <select
				class="form-control listInp" name="chargeType" id="chargeType">
				<option value="0" selected="selected">请选择</option>
				<option value="1">余额充值</option>
				<option value="2">应用充值</option>
				<option value="3">线下充值</option>
			</select>
		</div>
		<button type="button" class="btn btn-default listBtn"
			onclick="search()">查询</button>
	</div>
	<table id="example"
		class="table table-striped table-bordered table-hover">
		<thead
			style="border-top: 1px #EEEEEE solid; background-color: #0099CB">
			<tr class="dateTable-th">
				<th>日期</th>
				<th>交易金额</th>
				<th>交易类型</th>
				<th>应用名称</th>
				<th>充值状态</th>
			</tr>
		</thead>
	</table>
</div>
</main>
<!--*********************footerDiv------start**********************************-->
