var dataTable = {
	init : function(data) {
		$('#example')
				.dataTable(
						{
							serverSide : true,
							processing : true,
							searching : false,
							bDeferRender : true,
							bLengthChange : false,
							bSort : false,
							bStateSave : true,
							ajax : {
								type : "post",
								url : "subAccountLoad",
								dataSrc : "data",
								data : data,
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									if (XMLHttpRequest.status == 404) {
										$("#example").html("数据库服务器通讯失败，请稍后重试！");
									}
								}
							},
							fnServerParams : function(aoData) {
								//aoData.mobile = $("#_mobile").val();
								aoData.accountName = $("#accountName").val();
								aoData.userName = $("#userName").val();
							},
							serverSide : true,
							columns : [
										{
										    "data" : "account_name"
										}, 
							           	{
							           		"data" : "user_name"
							           	}, 
										{
											"data" : "appNames"
										},
										{
											"data" : null
										} ],
							"language" : {
								"lengthMenu" : "_MENU_ 条记录每页",
								"zeroRecords" : "没有找到记录",
								"info" : "总共_PAGES_页，显示第_START_到第_END_条，筛选之后得到_TOTAL_条，初始_MAX_条 ",
								"infoEmpty" : "无记录",
								"infoFiltered" : "(从 _MAX_ 条记录过滤)",
								"paginate" : {
									"previous" : "上一页",
									"next" : "下一页"
								}
							},
							columnDefs : [ {
								targets : 3,
								render : function(a, b, c, d) {
										var html = "<button class='mb-sm btn btn-primary' onclick='editSubAccount(\""
											    + a.id
												+ "\")'>修改</button>&nbsp;<button class='mb-sm btn btn-primary' onclick='delSubAccount(\""
												+ a.id
												+ "\")'>删除</button>&nbsp;<button class='mb-sm btn btn-primary' onclick='setSubAccountPassword(\""
												+ a.id
												+ "\")'>重设密码</button>";
										return html;
								}
							} ]
						});
	}
};

$(function() {
	if ($.fn.dataTable.isDataTable('#example')){
		$('#example').dataTable().fnDraw(false);
	}else{
		dataTable.init();
	}
});

function search() {
	$('#example').dataTable().fnDraw(false);
}

function closeModal(modalWinId) {
	$("#" + modalWinId).modal('hide');
}

function openSubAccountWin(){
	$("#id").val("");
	$("#account_name").val("");
	$("#password").val("");
	$("#user_name").val("");
	var allCheckbox = $("input[type=checkbox]");
	$.each(allCheckbox,function (index,checkObj){
		$(checkObj).prop("checked",false);
	});
	
	 $("input[type=checkbox][name=yygl]").prop("checked",true);
	 $("input[type=checkbox][name=yygl_yylb]").prop("checked",true);
	
	
	$("#passwordLabel").show();
	$("#passwordDiv").show();
	$("#lineHeight").show();
	
	$("#subAccountModal").modal('show');
}

function beginOpenSubAccountWin(){
	$.get("getMainAccountAuthInfo", function(data) {
		 if(data.status != null && data.status != undefined && data.status == 2){
			 openSubAccountWin();
		 }
		 else{
			 layer.msg("企业不是认证通过状态，不可创建子账号", {
					icon : 2
			 });
		 }
	});
}

function saveSubAccount(){
  if($("#id").val() != ""){
	  updateSubAccount();
  }
  else{
	  addSubAccount();
  }
}

function addSubAccount(){
	var subSpTemp = $("input[type=checkbox][name=subSpTemp]");
	var subSpValue = '';
	$.each(subSpTemp,function (index,subSpObj){
		 if(($(subSpObj).prop("checked"))){
			 subSpValue+=$(subSpObj).prop("value")+",";
		 }
	});
	
	if(subSpValue == ''){
		alert("请选择关联应用！");
		return;
	}
	$("#subSp").val(subSpValue);
	
	
	
	var permissionTemp = $("input[type=checkbox][name !=subSpTemp]");
	var permissionValue = '';
	$.each(permissionTemp,function (index,subPermissionObj){
		 if(($(subPermissionObj).prop("checked"))){
			 permissionValue+=$(subPermissionObj).prop("value")+",";
		 }
	});
	
	if(permissionValue == ''){
		alert("请选择功能授权！");
		return;
	}
	$("#subAccountPermission").val(permissionValue);
	
   $.ajax({
	type : 'post',
	url : "addSubAccount",
	data : $("#subAccountForm").serialize(),
	success : function(data) {
		if (data.code == '00') {
			layer.msg(data.msg, {
				icon : 1
			});
			$('#example').dataTable().fnDraw(false);
			$("#subAccountModal").modal('hide');
		} else {
			layer.msg(data.msg, {
				icon : 2
			});
		}
	}
   });
}

function editSubAccount(id) {
	$("#id").val("");
	$("#account_name").val("");
	$("#password").val("");
	$("#user_name").val("");
	var allCheckbox = $("input[type=checkbox]");
	$.each(allCheckbox,function (index,checkObj){
		$(checkObj).prop("checked",false);
	});
	
	if (id && typeof (id) != "undefined") {
		$.get("getSubAccount?id=" + id, function(data) {
			$("#id").val(data.subAccount.id);
			$("#account_name").val(data.subAccount.account_name);
			$("#user_name").val(data.subAccount.user_name);
			$("#password").val("");
			$("#passwordLabel").hide();
			$("#passwordDiv").hide();
			$("#lineHeight").hide();
			
			
			var spIdArray = data.subAccount.spIds.split(",");
			for(var i = 0; i < spIdArray.length;i ++){
				if(spIdArray[i] != ''){
				   $("#sp" + spIdArray[i]).prop("checked",true);
				}
			}
			var menuCodeArray = data.subAccount.menuCodes.split(",");
			for(var i = 0; i < menuCodeArray.length;i ++){
				if(menuCodeArray[i] != ''){
					$("input[type=checkbox][value="+menuCodeArray[i] + "]").prop("checked",true);
				}
			}
		});
	}
	$("#subAccountModal").modal("show");
}


function updateSubAccount(){
	var subSpTemp = $("input[type=checkbox][name=subSpTemp]");
	var subSpValue = '';
	$.each(subSpTemp,function (index,subSpObj){
		 if(($(subSpObj).prop("checked"))){
			 subSpValue+=$(subSpObj).prop("value")+",";
		 }
	});
	
	if(subSpValue == ''){
		alert("请选择关联应用！");
		return;
	}
	$("#subSp").val(subSpValue);
	
	
	
	var permissionTemp = $("input[type=checkbox][name !=subSpTemp]");
	var permissionValue = '';
	$.each(permissionTemp,function (index,subPermissionObj){
		 if(($(subPermissionObj).prop("checked"))){
			 permissionValue+=$(subPermissionObj).prop("value")+",";
		 }
	});
	
	if(permissionValue == ''){
		alert("请选择功能授权！");
		return;
	}
	$("#subAccountPermission").val(permissionValue);
	
   $.ajax({
	type : 'post',
	url : "updateSubAccount",
	data : $("#subAccountForm").serialize(),
	success : function(data) {
		if (data.code == '00') {
			layer.msg(data.msg, {
				icon : 1
			});
			$('#example').dataTable().fnDraw(false);
			$("#subAccountModal").modal('hide');
		} else {
			layer.msg(data.msg, {
				icon : 2
			});
		}
	}
   });
}





function delSubAccount(id) {
	layer.confirm('您确定删除子账号吗？', {
		icon : 0,
		title : '子账号删除',
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : 'deleteSubAccount',
			type : 'post',
			data : {
				id : id
			},
			success : function(data) {
				if (data.code == '00') {
					layer.msg("删除成功！", {
						icon : 1
					});
					search();
				} else {
					layer.msg("删除失败！", {
						icon : 2
					});
				}
			},
			error : function() {
				layer.msg("服务器无响应，请联系管理员！", {
					icon : 2
				});
			}
		});
	});
};

function checkSubMenu(mainMenu){
		if($("input[type=checkbox][name=" + mainMenu + "][value="+mainMenu + "]").prop("checked")){  //选中
			$("input[type=checkbox][name^=" + mainMenu +"_]").prop("checked",true);
		}
		else{
			$("input[type=checkbox][name^=" + mainMenu +"_]").prop("checked",false);
		}
}

function checkParentMenu(mainMenu){
	var subMenu = $("input[type=checkbox][name^=" + mainMenu +"_]");
	var parentChecked = false;
	$.each(subMenu,function (index,subObj){
		 if(($(subObj).prop("checked"))){
			 parentChecked = true;
		 }
	});
	$("input[type=checkbox][name=" + mainMenu + "][value="+mainMenu + "]").prop("checked",parentChecked);
}

function setSubAccountPassword(id){
	$("#subAccountId").val(id);
	$("#newPsd").val("");
	$("#newPsd1").val("");
	$("#subAccountPasswordModal").modal('show');
}


function updateSubAccountPsd() {
	$.ajax({
		url : 'updateSubAccountPassword',
		type : 'post',
		data : $('#psdForm').serialize(),
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				$("#subAccountPasswordModal").modal('hide');
				return;
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
				return;
			}
		}
	});
}

