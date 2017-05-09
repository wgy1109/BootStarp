	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<div class="content-wrapper">
	<h3>个人信息</h3>
	<div role="tabpanel">
		<!-- Nav tabs-->
		<ul class="nav nav-tabs" role="tablist">
			<li class="active" role="presentation"><a data-toggle="tab"
				role="tab" aria-controls="home" href="#home" aria-expanded="false">账号信息</a></li>
			<li role="presentation" class=""><a data-toggle="tab" role="tab"
				aria-controls="auth" href="#auth" aria-expanded="false">企业认证</a></li>
			<li role="presentation" class=""><a data-toggle="tab" role="tab"
				aria-controls="messages" href="#messages" aria-expanded="false">发票认证</a>
			</li>
		</ul>
		<!-- Tab panes-->
		<div class="tab-content">
			<div class="tab-pane active" role="tabpanel" id="home">
				<div class="panel panel-default">
					<div class="panel-heading">个人信息</div>
					<div class="panel-body">
						<form class="form-horizontal">
							<div class="form-group">
								<label class="col-lg-2 control-label">姓名</label>
								<div class="input-group">
									<input type="text" class="form-control" placeholder="姓名"
										id="USER_NAME" value="${sessionUser.user_name}"readonly ><span
										class="input-group-btn"><button class="btn btn-default"
											data-toggle="modal" data-target="#nameModal" type="button">
											<strong>修改姓名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>
										</button></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">邮箱</label>
								<div class="input-group">
									<input type="email" class="form-control" placeholder="邮箱"
										id="EMAIL" value="${sessionUser.email}" readonly><span
										class="input-group-btn"><button class="btn btn-default"
											data-toggle="modal" data-target="#emailModal" type="button">
											<strong>修改邮箱&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>
										</button></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">手机号</label>
								<div class="input-group">
									<input type="text" class="form-control" placeholder="手机号"
										id="MOBILE" value="${sessionUser.mobile}" readonly><span
										class="input-group-btn"><button class="btn btn-default"
											data-toggle="modal" data-target="#mobileModal" type="button">
											<strong>修改手机号</strong>
										</button></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">密码</label>
								<div class="input-group">
									<input type="password" class="form-control"
										placeholder="Password" value="888999" readonly><span
										class="input-group-btn"><button
											class="btn btn-default " type="button" data-toggle="modal"
											data-target="#passwordModal">
											<strong>修改密码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>
										</button></span>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- 企業信息 -->
			<div class="tab-pane" role="tabpanel" id="auth">
				<div class="panel panel-default">
					<div class="panel-heading">企业认证</div>
					<div class="panel-body">
						<form class="form-horizontal" id="authForm">
							<div class="form-group">
								<label class="col-lg-2 control-label">认证状态</label>
								<div class="col-lg-10">
									<input name="ID" type="hidden" value="${auth.id }" /> <input
										type="text" style="text-align: center;" class="form-control" id="userInfoType" 
										disabled=""
										value=" <c:choose><c:when test="${auth.status== 0 }">未提交认证</c:when><c:when test="${auth.status == 1 }">认证中</c:when><c:when test="${auth.status == 2 }">认证通过</c:when><c:when test="${auth.status == 3 }">认证被驳回</c:when><c:otherwise>未认证</c:otherwise></c:choose>" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">公司名称</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="公司名称"
										id="COMPANY" name="COMPANY" value="${auth.company}"  maxlength="20"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">公司地址</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="公司地址"
										name="COMPANY_ADDRESS" value="${auth.company_address}" maxlength="100">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">公司电话</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="公司电话"
										name="CONTACT" value="${auth.contact}" maxlength="20">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">公司法人</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="公司法人"
										name="LEGAL_REPRESENTATIVE"
										value="${auth.legal_representative}" maxlength="20">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">法人身份证(扫描件)</label>
								<div class="col-lg-5">
									<input type="file" name="file" id="IDCARD_IMAGE"
										filename="IDCARD_IMAGE" class="btn btn-default" onchange="updateImage(this);" value="${auth.idcard_image }">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-8 col-md-offset-4">
									<div id="IDCARD_IMAGE_SRC_preview"  >
									<c:if test="${auth.idcard_image !=null && auth.idcard_image != ''  }">
										<img id="IDCARD_IMAGE_SRC_imghead" width=260 height=180 border=0 src='${NGINXPATH }${auth.idcard_image}' onclick="showImage(this)">
									</c:if>
									</div>
									<input type="hidden" id="IDCARD_IMAGE_SRC" name="IDCARD_IMAGE_SRC" value="${auth.idcard_image }" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">组织机构代码证</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="组织机构代码证"
										name="ORGANIZATION_NO" value="${auth.organization_no}" maxlength="30">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">组织机构代码证(扫描件)</label>
								<div class="col-lg-5">
									<input type="file" name="file" id="ORGANIZATION_IMAGE"
										filename="ORGANIZATION_IMAGE" class="btn btn-default" 
										 onchange="updateImage(this);">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-8 col-md-offset-4">
									<div id="ORGANIZATION_IMAGE_SRC_preview" >
									<c:if test="${auth.organization_image !=null && auth.organization_image != ''  }">
										<img id="ORGANIZATION_IMAGE_SRC_imghead" width=260 height=180 border=0 src='${NGINXPATH }${auth.organization_image }' onclick="showImage(this)">
									</c:if>
									</div>
									<input type="hidden" id="ORGANIZATION_IMAGE_SRC" name="ORGANIZATION_IMAGE_SRC" value="${auth.organization_image }" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">纳税证</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="纳税证"
										name="TAXPAYER_NO" value="${auth.taxpayer_no}" maxlength="30">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">纳税证(扫描件)</label>
								<div class="col-lg-5">
									<input type="file" name="file" id="TAXPAYER_IMAGE"
										filename="TAXPAYER_IMAGE" class="btn btn-default"  onchange="updateImage(this);">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-8 col-md-offset-4">
									<div id="TAXPAYER_IMAGE_SRC_preview">
									<c:if test="${auth.taxpayer_image !=null && auth.taxpayer_image != ''  }">
										<img id="TAXPAYER_IMAGE_SRC_imghead" width=260 height=180  border=0 src='${NGINXPATH }${auth.taxpayer_image }' onclick="showImage(this)">
									</c:if>
									</div>
									<input type="hidden" id="TAXPAYER_IMAGE_SRC" name="TAXPAYER_IMAGE_SRC" value="${auth.taxpayer_image }" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">注册证</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="注册证"
										name="REGISTERED_NO" value="${auth.registered_no }" maxlength="30">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">注册证(扫描件)</label>
								<div class="col-lg-5">
									<input type="file" name="file" id="REGISTERED_IMAGE"
										filename="REGISTERED_IMAGE" class="btn btn-default" onchange="updateImage(this);">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-8 col-md-offset-4">
									<div id="REGISTERED_IMAGE_SRC_preview" >
									<c:if test="${auth.registered_image !=null && auth.registered_image != ''  }">
										<img id="REGISTERED_IMAGE_SRC_imghead" width=260 height=180  border=0 src='${NGINXPATH }${auth.registered_image }' onclick="showImage(this)">
									</c:if>
									</div>
									<input type="hidden" id="REGISTERED_IMAGE_SRC" name="REGISTERED_IMAGE_SRC" value="${auth.registered_image }" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<!-- onclick="saveAuth();" -->
									<button class="btn btn-primary" type="submit"> &nbsp;保&nbsp;&nbsp;&nbsp;存&nbsp; </button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button class="btn btn-primary" type="button"
										onclick="authentication();">保存并提交</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			
			
			<div class="tab-pane" role="tabpanel" id="messages">
				<div class="panel panel-default">
					<div class="panel-heading">发票认证</div>
					<div class="panel-body">
						<form class="form-horizontal" id="invoiceForm">
							<div class="form-group">
								<label class="col-lg-2 control-label">认证状态</label>
								<div class="col-lg-10">
									<input name="id" type="hidden" value="${data.id}" /> <input
										type="text" style="text-align: center;" class="form-control" id="invoiceInfoType" 
										disabled=""
										value=" <c:choose><c:when test="${data.certinfo_status== 3 }">未提交认证</c:when><c:when test="${data.certinfo_status == 0 }">认证中</c:when><c:when test="${data.certinfo_status == 1 }">认证通过</c:when><c:when test="${data.certinfo_status == 2 }">认证被驳回</c:when><c:otherwise>未认证</c:otherwise></c:choose>" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">纳税人识别号：</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="纳税人识别号" id="idenitficationNum"
										name="idenitficationNum" value="${data.idenitfication_num }" maxlength="30" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">银行账号：</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="银行账号"
										name="bankAccent" value="${data.bank_accent }" maxlength="30">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">开户行：</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="开户行"
										name="openAccent" value="${data.open_accent }" maxlength="100">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">注册地址：</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="注册地址"
										name="registeredAddress"
										value="${data.registered_address }" maxlength="100">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">企业电话：</label>
								<div class="col-lg-10">
									<input type="text" class="form-control" placeholder="企业电话"
										name="companyPhone"
										value="${data.company_phone }" maxlength="19">
								</div>
							</div>
							<div class="form-group">
								<label class="col-lg-2 control-label">银行开户许可证(扫描件)</label>
								<div class="col-lg-5">
									<input type="file" name="file" id="BANK_ACCENT_IMG"
										filename="BANK_ACCENT_IMG" class="btn btn-default" onchange="updateImage(this);">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-8 col-md-offset-4">
									<div id="BANK_ACCENT_IMG_SRC_preview" >
									<c:if test="${data.bank_accent_img !=null && data.bank_accent_img != ''  }">
										<img id="BANK_ACCENT_IMG_SRC_imghead" width=260 height=180  border=0 src='${NGINXPATH }${data.bank_accent_img }' onclick="showImage(this)">
									</c:if>
									</div>
									<input type="hidden" id="BANK_ACCENT_IMG_SRC" name="BANK_ACCENT_IMG_SRC" value="${data.bank_accent_img }" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">税务登记证(扫描件)</label>
								<div class="col-lg-5">
									<input type="file" name="file" id="TAX_REGISTRATION_IMG"
										filename="TAX_REGISTRATION_IMG" class="btn btn-default" onchange="updateImage(this);">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-8 col-md-offset-4">
									<div id="TAX_REGISTRATION_IMG_SRC_preview" >
									<c:if test="${data.tax_registration_img !=null && data.tax_registration_img != ''  }">
										<img id="TAX_REGISTRATION_IMG_SRC_imghead" width=260 height=180  border=0 src='${NGINXPATH }${data.tax_registration_img }' onclick="showImage(this)">
									</c:if>
									</div>
									<input type="hidden" id="TAX_REGISTRATION_IMG_SRC" name="TAX_REGISTRATION_IMG_SRC" value="${data.tax_registration_img }" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-lg-2 control-label">一般纳税人认定(扫描件)</label>
								<div class="col-lg-5">
									<input type="file" name="file" id="GENERAL_TAX_IMG"
										filename="GENERAL_TAX_IMG" class="btn btn-default" onchange="updateImage(this);">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-8 col-md-offset-4">
									<div id="GENERAL_TAX_IMG_SRC_preview" >
									<c:if test="${data.general_tax_img !=null && data.general_tax_img != ''  }">
										<img id="GENERAL_TAX_IMG_SRC_imghead" width=260 height=180  border=0 src='${NGINXPATH }${data.general_tax_img }' onclick="showImage(this)">
									</c:if>
									</div>
									<input type="hidden" id="GENERAL_TAX_IMG_SRC" name="GENERAL_TAX_IMG_SRC" value="${data.general_tax_img }" />
								</div>
							</div>
							
							
							<div class="form-group">
								<div class="col-lg-offset-2 col-lg-10">
									<!-- onclick="saveAuth();" -->
									<button class="btn btn-primary" type="submit"
										>&nbsp;保&nbsp;&nbsp;&nbsp;存&nbsp;</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button class="btn btn-primary" type="button"
										onclick="vatinvoiceSubmit()">保存并提交</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id= "modaljsp">
<div id="mobileModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel" class="modal-title">手机号修改</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal"
					id="mobileForm" method="post">
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signusername">
							手机号 <span class="text-danger">*</span>
						</label>
						<div class="col-md-7">
							<input class="form-control" type="text" id="mobile" name="mobile"
								placeholder="手机号 " maxlength="11" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signusername">
							图形验证码 <span class="text-danger">*</span>
						</label>
						<div class="col-md-3">
							<input class="form-control" type="text" id="register-cord" name="imageCode" placeholder="图形验证码" maxLength="4"/>
			            </div>
						<div class="col-md-3">
			                <img style="width:115px; height:35px;" class="js-formcodeimg  text-right"  alt="验证码" src="loginCode.html" onclick="refreshCode2(this)" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signuseraddress">手机验证码<span
							class="text-danger">*</span>
						</label>
						<div class="col-md-3">
							<input class="form-control" type="text" id="mobileCode"
								name="mobileCode" placeholder="验证码 " maxlength="4" />
						</div>
						<div class="col-md-3">
							<button class="btn btn-default" onclick="sendSMSCode(this);"
								type="button">
								<strong>获取验证码</strong>
							</button>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
				<button type="button" class="btn btn-primary"
					onclick="updateMobile();">修改</button>
			</div>
		</div>
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
						<label class="col-md-4 control-label" for="val-signusername">原始密码<span
							class="text-danger">*</span>
						</label>
						<div class="col-md-7">
							<input class="form-control" type="password" id="oldPsd"
								name="oldPsd" placeholder="原始密码 " maxlength="15" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signuseraddress">新密码
							<span class="text-danger">*</span>
						</label>
						<div class="col-md-7">
							<input class="form-control" type="password" name="newPsd"
								id="newPsd" placeholder="新密码 " maxlength="15" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signuserphone">重复密码
							<span class="text-danger">*</span>
						</label>
						<div class="col-md-7">
							<input class="form-control" type="password" id="newPsd1"
								placeholder="重复密码" maxlength="15" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
				<button type="button" onclick="updatePsd();" class="btn btn-primary">修改</button>
			</div>
		</div>
	</div>
</div>
<!-- 姓名修改 -->
<div id="nameModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel" class="modal-title">姓名修改</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal"
					id="infoform" action="" method="post">
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signusername">姓名<span
							class="text-danger">*</span>
						</label>
						<div class="col-md-7">
							<input type="hidden" name="ID" value="${sessionUser.id }">
							<input class="form-control" type="text" id="username"
								name="USER_NAME" placeholder="姓名 " maxlength="10"
								value="${sessionUser.user_name }" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
				<button type="button" onclick="update();" class="btn btn-primary">修改</button>
			</div>
		</div>
	</div>
</div>


<!-- 邮箱修改 -->
<div id="emailModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel" class="modal-title">邮箱修改</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal"
					id="emailForm" method="post">
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signusername">
							邮箱 <span class="text-danger">*</span>
						</label>
						<div class="col-md-7">
							<input class="form-control" type="text" id="email" name="email"
								placeholder="邮箱 " maxlength="30" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signuseraddress">验证码<span
							class="text-danger">*</span>
						</label>
						<div class="col-md-3">
							<input class="form-control" type="text" id="mobileCode"
								name="emailCode" placeholder="验证码 " maxlength="4" />
						</div>
						<div class="col-md-3">
							<button class="btn btn-default" onclick="sendEmailCode(this);"
								type="button">
								<strong>获取验证码</strong>
							</button>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
				<button type="button" class="btn btn-primary"
					onclick="updateEmail();">修改</button>
			</div>
		</div>
	</div>
</div>
</div>