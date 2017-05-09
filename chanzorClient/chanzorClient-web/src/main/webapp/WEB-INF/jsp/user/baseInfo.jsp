<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script src="<%=basePath%>static/js/plugins/layer2.1/layer.js"></script>
<style>
.basicInfor {
	width: 80%;
	height: 660px;
	margin-left: 250px;
	background-color: white;
	margin-top: 130px;
	/*margin-bottom:20px;*/
	padding-top: 20px;
}

.basicInfor_top1 {
	width: 247px;
	height: 37px;
	background-color: #449AFF;
	margin-bottom: 36px;
}

.basicInfor_top1 p {
	text-align: center;
	line-height: 37px;
	color: white;
	font-size: 18px;
}

.basicInfor_top2 {
	height: 120px;
	/*background-color: green;*/
	margin-bottom: 28px;
}

.basicInfor_top2_left {
	margin-left: 80px;
	margin-right: 20px;
	height: 100%;
	/*background-color: yellow;*/
	float: left;
}

.basicInfor_top2_right {
	/*width: 75%;*/
	height: 100%;
	/*background-color: deeppink;*/
	float: left;
}

.basicInfor_top2_left_top {
	width: 100px;
	height: 100px;
	border-radius: 50px;
	margin: 0 auto;
}

.basicInfor_top2_left p {
	text-align: center;
}

.basicInfor_top2_left p a {
	text-decoration: none;
	color: #5DA7FE;
}

.basicInfor_top2_right {
	padding-top: 18px;
}

.basicInfor_top2_right p {
	line-height: 18px;
}

.basicInfor_inp {
	margin-top: 5px;
	width: 150px;
	height: 40px;
	text-align: center;
	display: none;
}
</style>
<style type="text/css">
/*密码强度*/
.pw-strength {clear: both;position: relative;top: 8px;width: 180px;}
.pw-bar{background: url(img/pwd-1.png) no-repeat;height: 14px;overflow: hidden;width: 179px;}
.pw-bar-on{background:  url(img/pwd-2.png) no-repeat; width:0px; height:14px;position: absolute;top: 1px;left: 2px;transition: width .5s ease-in;-moz-transition: width .5s ease-in;-webkit-transition: width .5s ease-in;-o-transition: width .5s ease-in;}
.pw-weak .pw-defule{ width:0px;}
.pw-weak .pw-bar-on {width: 60px;}
.pw-medium .pw-bar-on {width: 120px;}
.pw-strong .pw-bar-on {width: 179px;}
.pw-txt {padding-top: 2px;width: 180px;overflow: hidden;}
.pw-txt span {color: #707070;float: left;font-size: 12px;text-align: center;width: 58px;}
</style>

<style type="text/css">
.chanzorDataList {
	width: 35%;
	margin-left: 220px;
	background-color: white;
	height: 780px;
	margin-top: 75px;
	padding-top: 20px;
}

.chanzorDataList1 {
	width: 45%;
	margin-left: 220px;
	background-color: white;
	height: 780px;
	margin-top: 75px;
	padding-top: 20px;
}

.mt10{margin-top: 10px;}
.mt20{margin-top: 20px;}
.mt30{margin-top: 30px;}
.mt40{margin-top: 40px;}
.mt60{margin-top: 60px;}
.mt120{margin-top: 120px;}
.ml30{margin-left: 30px;}
.ml60{margin-left: 60px;}



</style>

<div class='chanzorDataList' style="float:left">
	<div class='chanzorDataList_title'>
		<p>账号信息</p>
	</div>
	<div class='basicInfor_top2'>
	    <input type="hidden" id="securityLoginMark" name="securityLoginMark" value="${baseInfo.security_login_mark}" />
		<form id="photoForm">
			<div class='basicInfor_top2_left'>
				<div class='basicInfor_top2_left_top'>
					<div id="photo_img_SRC_preview">
						<c:if
							test="${baseInfo.photo_img !=null && baseInfo.photo_img != ''}">
							<img id="photo_img_SRC_imghead" width=100 height=100 border=0
								src='${NGINXPATH}${baseInfo.photo_img}'
								onclick="showImage(this)">
						</c:if>
						<c:if
							test="${baseInfo.photo_img == null || baseInfo.photo_img == ''}">
							<img id="photo_img_SRC_imghead" width=100 height=100 border=0
								src='static/img/defaultPhoto.png'  
								onclick="showImage(this)">
						</c:if>
						
					</div>
					<input type="hidden" value="${NGINXPATH}" id="nginxPath"> <input
						type="hidden" id="photo_img_SRC" name="photo_img_SRC"
						value="${baseInfo.photo_img}" /> <input type="file" name="file"
						id="photo_img" filename="photo_img" class="btn btn-default"
						style="display: none" onchange="uploadPhoto(this);"
						value="${baseInfo.photo_img}">
				</div>
				<p style="margin-top:10px">
					<a href="#" onclick="javascript:clickImgUpload('photo_img')">更改头像</a>
				</p>
			</div>
		</form>
		<div class='basicInfor_top2_right'>
			<p>
				<span id="accountNameSpan">${baseInfo.account_name}</span>  	<a href="#" onclick="javascript:beginModifyAccount()">修改账号</a>
			</p>
			<p style="margin-top:15px">
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
			<p style="margin-top:15px">
				<span>登录IP地址为：</span><span>${baseInfo.last_login_ip}</span>
			</p>
		</div>
	</div>
	<div class='basicInfor_top3'>
		<form id="baseInfoForm" class="form-horizontal">
			<div class="form-group">
				<label for="contact" class="col-sm-2 control-label col-sm-offset-1">联系人：</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" style="width:200px" id="user_name"
						name="user_name" value="${baseInfo.user_name}">
				</div>
			</div>

			<div class="form-group ">
				<label for="contactNum"
					class="col-sm-2 control-label col-sm-offset-1"><span
					class="text-danger">*</span>联系号码：</label>
				<div class="col-sm-5 ">
					<input type="hidden" style="width:200px" id="mobileHidden" value="${baseInfo.mobile}">
					<input type="text" class="form-control" style="width:200px" readOnly="true" id="mobile" name="mobile"
						value="${baseInfo.mobile}">
<!-- 						onkeyup="javascript:isChange(this.value);"> -->
				</div>
			</div>
			<div class="form-group" id="mobileCodeDiv">
				<label for="mobileCode"
					class="col-sm-2 control-label col-sm-offset-1">手机验证码：</label>
				<div class="col-sm-5" style="width:200px;float:left" >
					<input type="text" class='form-control' id="mobileCode"
						name="mobileCode" maxlength="20" style="width:100px;float:left" />
				</div>
				<button type="submit" class="btn btn-default" style="margin-left:-80px"
					onclick="sendSMSCodeNew(this);">获取验证码</button>
			</div>
			<div class="form-group">
				<label for="inputEmail"
					class="col-sm-2 control-label col-sm-offset-1"><span
					class="text-danger">*</span>联系邮箱：</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" style="width:200px" id="email" name="email"
						value="${baseInfo.email}" maxlength="30">
				</div>
			</div>
			<div class="form-group">
				<label for="inputQQ" class="col-sm-2 control-label col-sm-offset-1">联系QQ：</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" style="width:200px" id="qq" name="qq"
						value="${baseInfo.qq}" maxlength="16">
				</div>
			</div>
			<div class="form-group">
				<label for="inputWeixin"
					class="col-sm-2 control-label col-sm-offset-1">联系微信：</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" style="width:200px" id="weixin" name="weixin"
						value="${baseInfo.weixin}" maxlength="16">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-10" style="width:100px;margin-left:200px;margin-top:40px" >
					<button type="button" class="btn sub-btn"
						onclick="javascript:baseInfoSave();">保存</button>
				</div>
			</div>
		</form>
	</div>

</div>

<div id="passwordModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel" class="modal-title">密码修改</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal" id="psdForm"
					action="" method="post">
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signusername">
							<span class="text-danger">*</span>原始密码:
						</label>
						<div class="col-md-7">
							<input class="form-control" type="password" id="oldPsd"
								name="oldPsd" placeholder="原始密码 " maxlength="20" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signuseraddress">
							<span class="text-danger">*</span>新密码:
						</label>
						<div class="col-md-7">
							<input class="form-control" type="password" name="newPsd"
								id="newPsd" placeholder="新密码 " maxlength="20" />
						</div>
					</div>
					<div id="level" class="form-group" style="margin-left:200px">    
					        <div id="level" class="pw-strength">         
					            <div class="pw-bar"></div>
					            <div class="pw-bar-on"></div>
					            <div class="pw-txt">
					            <span>弱</span>
					            <span>中</span>
					            <span>强</span>
					            </div>
				            </div>  
				    </div> 
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signuserphone">
							<span class="text-danger">*</span>重复密码:
						</label>
						<div class="col-md-7">
							<input class="form-control" type="password" id="newPsd1"
								name="newPsd1" placeholder="重复密码" maxlength="20" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn invalid-btn">关闭</button>
				<button type="button" onclick="updatePsd();" class="btn sub-btn">修改</button>
			</div>
		</div>
	</div>
</div>
<div  class='chanzorDataList1' style="float:left;margin-left:10px">
    <div class='chanzorDataList_title'>
		<p>账号安全</p>
	</div>
	<div class="form-group">
		<label class="col-md-11 control-label mt30 ml30" >
			<font style="font-size:18px;">您的账号安全等级:</font>
		</label>
	</div>
	<div class="form-group">
		<label class="col-md-11 control-label mt20 ml60">
			<img id="securityLevelImg" src="img/securityLevel${securityLevel}.png" />
		</label>
	</div>
	<div class="form-group">
		<label class="col-md-4 control-label mt60 ml30">
			<font style="font-size:18px;">账户密码</font>    您的密码强度为   <span id="passStrengthSpan"><c:if test="${baseInfo.password_strength_mark==1}" ><font size="4" color="red">弱</font></c:if><c:if test="${baseInfo.password_strength_mark==2}" ><font size="4" color="#F9D330">中</font></c:if><c:if test="${baseInfo.password_strength_mark==3}" ><font size="4" color="green">强</font></c:if></span>
		</label>
		<div class="col-md-7 mt60" style="align:right">
			<button type="button" onclick="beginModifyPassWord();" class="btn sub-btn" style="width:90px">修改密码</button>
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-11 control-label mt20 ml60">
			<font style="font-size:12px;" color="#9d9d9d">建议您【定期修改密码，保证账号安全】</font>
		</label>
	</div>
	<div class="form-group">
		<label class="col-md-4 control-label mt60 ml30">
			<font style="font-size:18px;">安全登录</font>    【<span id="securityLoginMarkDisplay"><c:if test="${baseInfo.security_login_mark==0}" >未开启</c:if><c:if test="${baseInfo.security_login_mark==1}" >已开启</c:if></span>】  
		</label>
		<div class="col-md-7 mt60" >
			<button type="button" onclick="securityLoginOnOff();" class="btn sub-btn" style="width:90px"><span id="securityLoginMarkSpan"><c:if test="${baseInfo.security_login_mark==0}" >开启</c:if><c:if test="${baseInfo.security_login_mark==1}" >关闭</c:if></span></button>
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-11 control-label mt20 ml60">
			<font style="font-size:12px;" color="#9d9d9d">建议您开启安全登录，当您的登录IP发生异常时，需要通过手机验证码登录</font>
		</label>
	</div>
</div>


<div id="accountModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel" class="modal-title">账号修改</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal" id="accountForm"
					action="" method="post">
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signusername">
							<span class="text-danger">*</span>账号:
						</label>
						<div class="col-md-7">
						    <input type="hidden" name="id" id="id" value="${baseInfo.id}" />
							<input class="form-control" type="text" id="newAccountName"
								name="newAccountName" placeholder="账号 " value="${baseInfo.account_name}" maxlength="20" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signuseraddress">
							<span class="text-danger">*</span>联系号码:
						</label>
						<div class="col-md-7">
							<input class="form-control" type="text" name="newMobile"
								id="newMobile" placeholder="联系号码" value="${baseInfo.mobile}" maxlength="20" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signuserphone">
							<span class="text-danger">*</span>验证码:
						</label>
						<div class="col-md-3">
							<input class="form-control" type="text" id="verifyCode"
								name="verifyCode" placeholder="验证码" maxlength="15" />
						</div>
						<div class="col-md-4">
							<input  type="button" id="verifyButton"
								name="verifyButton" value="获取验证码" style="width:100px" class="btn sub-btn" onclick="javascript:sendVerifyCode(this)" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-11" style="margin-left:200px">
					    	<font style="font-size:12px;" color="#9d9d9d">验证码会发往手机${baseInfo.mobile}，请注意查收！</font>
					    </div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn invalid-btn">关闭</button>
				<button type="button" onclick="modifyAccount();" class="btn sub-btn">修改</button>
			</div>
		</div>
	</div>
</div>