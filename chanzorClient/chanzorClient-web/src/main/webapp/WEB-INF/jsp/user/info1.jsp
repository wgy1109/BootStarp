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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello , UserInfo</title>
<style type="text/css">
table {
	border-left: 1px solid #1ab394;
	border-bottom: 1px solid #1ab394;
}

td {
	border-right: 1px solid #1ab394;
	border-top: 1px solid #1ab394;
}
</style>
</head>
<body>
	<div style="float: left;">
		<h1>导航</h1>
		<hr>
		<ul>
			<li><a href="info">UserInfo</a></li>
			<li><a href="index">Index</a></li>
			<li><a href="logout">logout</a></li>
		</ul>
	</div>
	<div style="text-align: center;float: right;width: 80%">
		<h1>个人信息</h1>
		<hr>
		<form action="" method="post" id="infoform">
			<table align="center" id="dataTable">
				
				<tr><td colspan="2"><a href="javascript:void(0);" onclick="update();">update</a></td></tr>
			</table>
		</form>
		<hr>
		<br>
		<br>
		<h1>密码修改</h1>
		<form action="updatePassword" id="psdForm">
			<table align="center" >
				<tr>
					<td>原始密码:</td><td><input type="password" name="oldPsd"/></td>
				</tr>
				<tr>
					<td>新密码:</td><td><input type="password" name="newPsd"/></td>
				</tr>
				<tr><td colspan="2"><a href="javascript:void(0);" onclick="updatePsd();">Change PassWord</a></td></tr>
		</table>
		</form>
		<hr>
		<br>
		<br>
		<h1>手机号修改</h1>
		<form action="updatePassword" id="mobileForm">
			<table align="center" >
				<tr>
					<td>手机号码:</td><td colspan="2" align="left"><input type="text" name="mobile"/></td>
				</tr>
				<tr>
					<td>验证码:</td><td ><input type="text" name="mobileCode"/></td> <td><a href="javascript:void(0);" onclick="sendSMSCode();" style="font-size: 10px;">Send SMS code</a></td>
				</tr>
				<tr><td colspan="3" align="right"><a style="margin-right: 15px;" href="javascript:void(0);" onclick="updateMobile();">Change Mobile</a></td></tr>
		</table>
		</form>
		<hr>
		<br>
		<br>
		<h1>企业信息认证</h1>
		<form action="updatePassword" id="authForm">
			<table align="center" >
				<tr>
					<td>公司名称:</td><td  align="left"><input type="text" name="COMPANY" value="${auth.COMPANY }"/></td>
				</tr>
				<tr>
					<td>公司地址:</td><td  align="left"><input type="text" name="COMPANY_ADDRESS"  value="${auth.COMPANY_ADDRESS }"/></td>
				</tr>
				<tr>
					<td>公司电话:</td><td  align="left"><input type="text" name="CONTACT"  value="${auth.CONTACT }"/></td>
				</tr>
				<tr>
					<td>公司法人:</td><td  align="left"><input type="text" name="LEGAL_REPRESENTATIVE"  value="${auth.LEGAL_REPRESENTATIVE }"/></td>
				</tr>
				<tr>
					<td>法人身份证(扫描件):</td>
					<td  align="left"><input type="file" name="file" id="IDCARD_IMAGE" filename="IDCARD_IMAGE" onchange="updateImage(this);"/>
						<input type="button" src="${auth.IDCARD_IMAGE }" onclick="showImage(this)">
						<input type="hidden" id="IDCARD_IMAGE_SRC" name="IDCARD_IMAGE_SRC"  value="${auth.IDCARD_IMAGE }"/>
					</td>
				</tr>
				
				<tr>
					<td>组织机构代码证:</td><td  align="left"><input type="text" name="ORGANIZATION_NO"  value="${auth.ORGANIZATION_NO }" /></td>
				</tr>
				<tr>
					<td>组织机构代码证(扫描件):</td><td  align="left"><input type="file" name="file" id="ORGANIZATION_IMAGE" filename="ORGANIZATION_IMAGE" onchange="updateImage(this);" /><input type="hidden" id="ORGANIZATION_IMAGE_SRC" name="ORGANIZATION_IMAGE_SRC"  value="${auth.ORGANIZATION_IMAGE }"/></td>
				</tr>
				
				<tr>
					<td>纳税证:</td><td  align="left"><input type="text" name="TAXPAYER_NO"  value="${auth.COMPANY }" /></td>
				</tr>
				<tr>
					<td>纳税证(扫描件):</td><td  align="left"><input type="file" name="file" id="TAXPAYER_IMAGE"  filename="TAXPAYER_IMAGE" onchange="updateImage(this);" /><input type="hidden" id="TAXPAYER_IMAGE_SRC" name="TAXPAYER_IMAGE_SRC"  value="${auth.TAXPAYER_IMAGE }"/></td>
				</tr>
				
				<tr>
					<td>注册证:</td><td  align="left"><input type="text" name="REGISTERED_NO"  value="${auth.REGISTERED_NO }" /></td>
				</tr>
				<tr>
					<td>注册证(扫描件):</td><td  align="left"><input type="file" name="file" id="REGISTERED_IMAGE"  filename="REGISTERED_IMAGE" onchange="updateImage(this);" /><input type="hidden" id="REGISTERED_IMAGE_SRC" name="REGISTERED_IMAGE_SRC" value="${auth.REGISTERED_IMAGE }"/></td>
				</tr>
				
				<tr>
					<td colspan="1" align="right"><a style="margin-right: 15px;" href="javascript:void(0);" onclick="saveAuth();" >Save</a></td>
					<td colspan="1" align="right"><a style="margin-right: 15px;" href="javascript:void(0);" onclick="authentication();">Authentication</a></td>
				</tr>
		</table>
		</form>
		
		<hr>
		<br>
		<br>
		<h1>充值</h1>
		<form action="updatePassword" id="chargeForm">
			<table align="center" >
				<tr>
					<td>选择应用:</td>
					<td align="left">
						<select name="spId" onchange="getPrice(this);">
							<option>请选择</option>
							<c:forEach items="${APPS }" var="item">
								<option value="${item.ID }">${item.SP_NAME }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>条数:</td>
					<td align="left">
						<input name="count" onchange="calcMoney(this);" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  
                                    onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}" />
					</td>
				</tr>
				<tr>
					<td>单价:</td>
					<td align="left">
						<span id="price"></span>
					</td>
				</tr>
				<tr>
					<td>总金额:</td>
					<td align="left">
						<span id="money"></span>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right"><a id="charge"  style="margin-right: 15px;display: none;" href="javascript:void(0);" onclick="charge();" >Charge</a></td>
				</tr>
		</table>
		</form>
	</div>
	<br><br><br><br><br><br><br><br>
	<br><br><br><br><br><br><br><br>
</body>
<script src="<%=basePath%>static/js/jquery-2.1.1.min.js"></script>
<script src="<%=basePath%>static/js/common.js"></script>
<script src="<%=basePath%>static/js/plugins/layer2.1/layer.js"></script>
<script src="<%=basePath%>static/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	//加载个人信息
	function loadUserInfo (){
		$.ajax({
			url:'loadinfo',
			type:'post',
			success:function (data) {
				if (data.code && data.code == '00'){
					if(data.userInfo){
						var info = data.userInfo ;
						for (var i in info){
							$('<tr><td>'+i+'</td><td><input name="'+i+'" id = "'+i+'" value= "'+info[i]+'"/></td></tr>').appendTo ('#dataTable');
						}
					}
				}
			}
		});
	}
	//修改账户信息
	function update (){
		$.ajax({
			url:'updateUserInfo',
			type:'post',
			data: $('#infoform').serialize(),
			success:function (data) {
				if(data.code  == '00') {
					layer.msg ('保存成功',{icon : 1});
					return ;
				}else{
					layer.msg ('保存失败',{icon : 2});
					return ;
				}
				
			}
		});
	}
	//修改密码
	function updatePsd () {
		$.ajax ({
			url:'updatePassword',
			type:'post',
			data:$('#psdForm').serialize(),
			success:function (data) {
				if(data.code == '00'){
					layer.msg(data.msg , {icon:1});
					return ;
				}else{
					layer.msg(data.msg , {icon:2});
					return ;
				}
			}
		});
		
	}
	//发送验证码
	function sendSMSCode () {
		$.ajax ({
			url:'sendSMSCode',
			data : {mobile : $('input[name=mobile]').val(),imageCode:'1234'},
			type:'post',
			success:function (data) {
				if(data.code == '00'){
					layer.msg(data.msg , {icon : 1});
				}else{
					layer.msg(data.msg , {icon : 2});
				}
				
			}
		}) ;
		
	}
	//修改手机号
	function updateMobile () {
		$.ajax ({
			url:'updateMobile',
			type:'post',
			data:$('#mobileForm').serialize(),
			success:function (data) {
				if(data.code == '00'){
					layer.msg (data.msg,{icon:1});
				}else{
					layer.msg (data.msg,{icon:2});
				}
			}
		});
	}
	//认证修改
	function saveAuth () {
		$.ajax ({
			url:'authSub',
			type:'post',
			data:$('#authForm').serialize(),
			success:function (data) {
				if(data.code == '00'){
					layer.msg (data.msg,{icon:1});
				}else{
					layer.msg (data.msg,{icon:2});
				}
			}
		});
	}
	//异步上传文件
	function updateImage(obj){
		 var textid = $(obj).attr('id')+"_SRC";
		 $.ajaxFileUpload( {  
		        url : 'updateImage',//用于文件上传的服务器端请求地址  
		        secureuri : false,//一般设置为false  
		        fileElementId : $(obj).attr('id'),//文件上传控件的id属性  
		        dataType : 'text',//返回值类型 一般设置为json  
		        type:'post',
		        data:{filename:$(obj).attr('filename')},
		        success : function(data, status) //服务器成功响应处理函数  
		        {  
		          if(data == ''){
		        	  layer.msg('文件类型错误',{icon:2});
		        	  return;
		          }
		          $('#'+textid).val(data);
		        },  
		        error : function(data, status, e)//服务器响应失败处理函数  
		        {  
		            alert(e);  
		        }  
		    })  
		
	}
	//提交企业认证
	function authentication () {
		layer.confirm('提交审核你将不可修改企业信息,确定要提交审核吗?',function () {
			$.ajax({
				url:'authentication',
				type:'post',
				success:function (data) {
					if(data.code == '00'){
						layer.msg(data.msg,{icon:1});
					}else{
						layer.msg(data.msg,{icon:2});
					}
				}
			});	
		});
	}
	//获取单价
	function getPrice(obj) {
		$('#price').html("");
		$.ajax ({
			url:'getPrice',
			data:{spId:$(obj).val()},
			type:'post',
			success:function (data){
				if(data.code == '00'){
					$('#charge').show();
					$('#price').html(data.price);
				}else{
					$('#charge').hide();
				}
			}
			
		});
	}
	//计算总金额
	function calcMoney(obj){
		var count = $(obj).val();
		if(!isNaN(count) && count >0 ){
			var price = $('#price').html ();
			var money  = count * price;
			$('#money').html (money);
		}
	}
	//充值
	function charge () {
		layer.confirm('确定充值?',function () {
			$.ajax({
				url:'charge',
				type:'post',
				data:$('#chargeForm').serialize(),
				success:function (data){
					
				} 
			});
			
		});
	}
	$(function () {loadUserInfo();})
</script>
</html>