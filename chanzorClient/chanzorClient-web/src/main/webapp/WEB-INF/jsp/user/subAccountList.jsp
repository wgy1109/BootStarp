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

<style>
.mt5{margin-top: 5px;}
.mt10{margin-top: 10px;}
.mt20{margin-top: 20px;}
.mt40{margin-top: 40px;}
.ml30{margin-left: 30px;}
.ml60{margin-left: 60px;}
.ml80{margin-left: 80px;}

.p10{padding:10px;}
.pb5{padding-bottom:5px;}
.pt10{padding-top:10px;}


.h-line{border-bottom:solid 1px #ccc;}
.h-center{text-align:center;}

.lic-wrap{margin-left:-430px; padding:10px 10px 20px 10px; width:860px; margin-top:70px; background:#fff;}
.lic-wrap button.btn-primary{background-color: #559AFA; border:none; width: 67px; height: 40px; text-align: center;}
.lic-wrap .lic-title{font-size:18px;}
.lic-wrap .lic-notice{}
.lic-wrap .lic-img-preview{background:#ccc; height:214px; width:391px; }
.lic-wrap .lic-btn-upload{}
.lic-wrap .lic-example{padding-left:10px; padding-top:10px;}
.lic-wrap .lic-example-img{}
.lic-wrap .lic-btns{}

</style>
<div class='chanzorDataList'>
        <div class='chanzorDataList_title'>
          <p>子账号</p>
        </div>
        <form class="form-inline" style='text-align:right;margin-right:2%;' >
            <div class="form-group">
                <label for="accountName" class='listLab' >子账号：</label>
	            <input type="search" class="form-control" name="accountName" id="accountName" />
            </div>
            <div class="form-group">
                <label for="userName" class='listLab' >&nbsp;&nbsp;&nbsp;&nbsp;联系人：</label>
	            <input type="search" class="form-control" name="userName" id="userName" />
            </div>
            <button type="button" class="btn btn-default listBtn" onclick="javascript:search();">查询</button>
            <button type="button" class="btn btn-default listBtn" onclick="javascript:beginOpenSubAccountWin();" style="width:65px;">新增</button>
        </form>
        <table id="example"	class="table table-striped table-bordered table-hover">
				<thead style="border-top: 1px #EEEEEE solid">
					<tr>
						<th width="8%">子账号</th>
						<th width="8%">联系人</th>
						<th >关联应用</th>
						<th width="18%">操作</th>
					</tr>
				</thead>
        </table>
</div>
<div id="subAccountPasswordModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-dismiss="modal" aria-label="Close"
					class="close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 id="myModalLabel" class="modal-title">重设密码</h4>
			</div>
			<div class="modal-body">
				<form class="js-validation-upload-auth form-horizontal" id="psdForm"
					action="" method="post">
					<input type="hidden" id="subAccountId" name="subAccountId" value="" />
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signuseraddress">
							<span class="text-danger">*</span>新密码:
						</label>
						<div class="col-md-7">
							<input class="form-control" type="password" name="newPsd"
								id="newPsd" placeholder="新密码 " maxlength="15" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label" for="val-signuserphone">
							<span class="text-danger">*</span>重复密码:
						</label>
						<div class="col-md-7">
							<input class="form-control" type="password" id="newPsd1"
								name="newPsd1" placeholder="重复密码" maxlength="15" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn invalid-btn">关闭</button>
				<button type="button" onclick="updateSubAccountPsd();" class="btn sub-btn">修改</button>
			</div>
		</div>
	</div>
</div>

<form id="subAccountForm" class="js-validation-upload-auth form-horizontal">
 <input type="hidden" id="id" name="id" value="" />
<div id="subAccountModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
	<div class="modal-dialog">
		<div class="lic-wrap col-sm-12 ">
			<div class="col-sm-12 h-line pb5 pt10 lic-title ">子账号</div>
			<div class="col-sm-12">
					<div class="col-sm-12 mt10">
					    <div>
						    <label for="account_name" class="col-sm-2 control-label" ><span class="text-danger">*</span>子账号：</label>
						    <div class="col-sm-9" >
						    	<input type="text" id="account_name" name="account_name" class="form-control" style="width:260px;float:left" value="" /><span style="color:red;padding-left:10px;padding-top:4px;float:left">注：子账号为手机号码</span>
						    </div>
						 </div>
					</div>
					<div class="col-sm-12 mt10">
					      <div>
					         <label for="password" class="col-sm-2 control-label" style="float:left" id="passwordLabel"><span class="text-danger">*</span>密码：</label>
					         <div class="col-sm-9" id="passwordDiv">
					    	      <input type="text" id="password" name="password" class="form-control"  autocomplete="off" onfocus="$(this).attr('type','password')"style="width:260px"  value="" />
					         </div>
					     </div>
					</div>
					<div class="col-sm-12 mt10">
					    <div>
						    <label for="user_name" class="col-sm-2 control-label" style="float:left">&nbsp;&nbsp;&nbsp;<span class="text-danger">*</span>联系人：</label>
						    <div class="col-sm-9" >
						    	<input type="text" id="user_name" name="user_name" class="form-control" style="width:260px" value="" />
						    </div>
						</div>
					</div>
				    <div class="col-sm-12 mt10">
							<div>
							    <input type="hidden" name="subSp" id="subSp" value="" />
							    <label class="col-sm-2 control-label" style="float:left"><span class="text-danger">*</span>关联应用：</label>
							    <div class="col-sm-9" >
							    	<c:forEach var="item" items="${appList}" varStatus="status">
										<input type="checkbox" name="subSpTemp" id="sp${item.id}" value="${item.id}" >${item.sp_name}&nbsp;&nbsp;
										<c:if test="${status.count%4==0}">
										  <br>
										</c:if>
									</c:forEach>
							    </div>
							</div>
				    </div>
				     <div class="col-sm-12 mt10">
							<div>
							    <input type="hidden" name="subAccountPermission" id="subAccountPermission" value="" />
							    <label class="col-sm-2 control-label" style="float:left"><span class="text-danger">*</span>功能授权：</label>
							    <div class="col-sm-9" >
							    	   <input type="checkbox" name="yygl" value="yygl" onclick="return false">应用管理
							    </div>
							</div>
				     </div>
				     <div class="col-sm-12">
							<div>
							    <label class="col-sm-2 control-label" style="float:left">&nbsp;</label>
							    <div class="col-sm-9" style="margin-left:30px">
							    	   <input type="checkbox" name="yygl_yylb" value="yygl_yylb" onclick="return false">应用列表<input type="checkbox" name="yygl_hmdlb" value="yygl_hmdlb" style="margin-left:10px" >黑名单列表<input type="checkbox" name="yygl_bmdlb" value="yygl_bmdlb" style="margin-left:10px" >白名单列表
							    </div>
							</div>
				     </div>
				     <div class="col-sm-12 mt5">
							<div>
							    <label class="col-sm-2 control-label" >&nbsp;</label>
							    <div class="col-sm-9" >
							    	   <input type="checkbox" name="gndx" value="gndx" onclick="checkSubMenu('gndx')">国内短信
							    </div>
							</div>
				     </div>
				     <div class="col-sm-12">
							<div>
							    <label class="col-sm-2 control-label" style="float:left">&nbsp;</label>
							    <div class="col-sm-9" style="margin-left:30px">
							    	   <input type="checkbox" name="gndx_fstj" value="gndx_fstj" onclick="checkParentMenu('gndx')">发送统计<input type="checkbox" name="gndx_fsdx" value="gndx_fsdx" style="margin-left:20px" onclick="checkParentMenu('gndx')">发送短信<input type="checkbox" name="gndx_fslb" value="gndx_fslb" style="margin-left:20px" onclick="checkParentMenu('gndx')">发送列表
							    	   <input type="checkbox" name="gndx_yfdxmx" value="gndx_yfdxmx" style="margin-left:20px" onclick="checkParentMenu('gndx')">已发短信明细<br>
							    	   <input type="checkbox" name="gndx_dxhf" value="gndx_dxhf" onclick="checkParentMenu('gndx')">短信回复<input type="checkbox" name="gndx_dxmb" value="gndx_dxmb" style="margin-left:20px" onclick="checkParentMenu('gndx')">短信模板
							    </div>
							</div>
				     </div>
				      <div class="col-sm-12 mt10">
							<div>
							    <label class="col-sm-2 control-label" >&nbsp;</label>
							    <div class="col-sm-9">
							    	   <input type="checkbox" name="gjdx" value="gjdx" onclick="checkSubMenu('gjdx')">国际短信
							    </div>
							</div>
				     </div>
				     <div class="col-sm-12">
							<div>
							    <label class="col-sm-2 control-label" style="float:left">&nbsp;</label>
							    <div class="col-sm-9"  style="margin-left:30px">
							    	   <input type="checkbox" name="gjdx_fstj" value="gjdx_fstj" onclick="checkParentMenu('gjdx')">发送统计<input type="checkbox" name="gjdx_fsdx" value="gjdx_fsdx" style="margin-left:20px" onclick="checkParentMenu('gjdx')">发送短信<input type="checkbox" name="gjdx_fslb" value="gjdx_fslb" style="margin-left:20px" onclick="checkParentMenu('gjdx')">发送列表
							    	   <input type="checkbox" name="gjdx_yfdxmx" value="gjdx_yfdxmx" style="margin-left:20px"  onclick="checkParentMenu('gjdx')">已发短信明细<br><input type="checkbox" name="gjdx_dxbj" value="gjdx_dxbj" onclick="checkParentMenu('gjdx')">短信报价
							    </div>
							</div>
				     </div>
		     
				      <div class="col-sm-12 mt10">
							<div>
							    <label class="col-sm-2 control-label" >&nbsp;</label>
							    <div class="col-sm-9" >
							    	   <input type="checkbox" name="yyyz" value="yyyz" onclick="checkSubMenu('yyyz')">语音验证
							    </div>
							</div>
				     </div>
				      <div class="col-sm-12">
							<div>
							    <label class="col-sm-2 control-label" style="float:left">&nbsp;</label>
							    <div class="col-sm-9" style="margin-left:30px">
							    	   <input type="checkbox" name="yyyz_fstj" value="yyyz_fstj" onclick="checkParentMenu('yyyz')">发送统计<input type="checkbox" name="yyyz_fswbyy" value="yyyz_fswbyy" style="margin-left:20px" onclick="checkParentMenu('yyyz')">发送文本语音<input type="checkbox" name="yyyz_wbyymb" value="yyyz_wbyymb" style="margin-left:20px" onclick="checkParentMenu('yyyz')">文本语音模板
							    	   <input type="checkbox" name="yyyz_fsyywj" value="yyyz_fsyywj" style="margin-left:20px" onclick="checkParentMenu('yyyz')">发送语音文件<br>
							    	   <input type="checkbox" name="yyyz_yywjpz" value="yyyz_yywjpz" onclick="checkParentMenu('yyyz')">语音文件配置 <input type="checkbox" name="yyyz_fslb" value="yyyz_fslb" style="margin-left:20px" onclick="checkParentMenu('yyyz')">发送列表<input type="checkbox" name="yyyz_yydxmx" value="yyyz_yydxmx" style="margin-left:20px" onclick="checkParentMenu('yyyz')">语音短信明细
							    </div>
							</div>
					 </div>
				     <div class="col-sm-12">
						<div class="col-sm-12 lic-example">
							<div class="lic-btns mt40">
							    <button class="btn sub-btn" type="button" onclick="javascript:saveSubAccount();">保存</button>
								<button class="btn invalid-btn" type="button" onclick="javascript:closeModal('subAccountModal');">取消</button>
							</div>
						</div>
					</div>   
			</div>		
		</div>			
	</div>
</div>
</form>




