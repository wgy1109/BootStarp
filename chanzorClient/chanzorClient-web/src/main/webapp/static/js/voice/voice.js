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
								url : "voice/load",
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
								aoData.templateName = $("#templateName").val();
								aoData.spId = $("#spId").val();
							},
							serverSide : true,
							columns : [
										{
										    "data" : "sp_name"
										}, 
							           	{
							           		"data" : "template_name"
							           	}, 
										{
											"data" : "create_time",
											render : function(data, type, row) {
													return data == null ? "" : getLocalTime(data);
											}
										},
										{
											"data" : "status",
											render : function(data, type, row) {
												if (data == 0) {
													return "审批中";
												}else if(data == 1){
													return "通过";
												}else if(data == -1){
													return "驳回";
												}else if(data == 2){
													return "停用";
												}else{
													return "";
												}
											}
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
								targets : 4,
								render : function(a, b, c, d) {
									//var voicePath = "'" + a.nginxUrl + a.file_name + "'";
									if(a.status == 0){ //审批中
										var html = "<button class='mb-sm btn btn-primary' onclick='javascript:listenTest(\""+a.fileUrl + a.fileShortPath+"\")'>试听</button>"
										return html;
									}
									if(a.status == 1){ //审批通过
										var html = "<button class='mb-sm btn btn-primary' onclick='listenTest(\""
												+ a.fileUrl + a.fileShortPath 
												+ "\")'>试听</button>&nbsp;<button class='mb-sm btn btn-primary' onclick='del(\""
												+ a.id
												+ "\")'>删除</button>";
										return html;
									}
									if(a.status == -1 || a.status == 2){ //审批未通过   或暂停
										var html = "<button class='mb-sm btn btn-primary' onclick='listenTest(\""
												+ a.fileUrl + a.fileShortPath
												+ "\")'>试听</button>&nbsp;<button class='mb-sm btn btn-primary' onclick='del(\""
												+ a.id
												+ "\")'>删除</button>&nbsp;<button class='mb-sm btn btn-primary' onclick='rejectReason(\""
												+ a.id
												+ "\")'>驳回原因</button>";
										return html;
									}
									return "";
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
	$("#listListenTestDiv").html("");
});

function search() {
	$('#example').dataTable().fnDraw(false);
}

function closeModal(modalWinId) {
	$("#" + modalWinId).modal('hide');
}

function clickVoiceUpload(fileAreaId) {
	$("#" + fileAreaId).click();
}

function listenTest(voiceFullPath) {
	$("#listListenTestDiv").html("<audio src=\""+voiceFullPath+"\" type=\"audio/wav\" autoPlay=\"autoPlay\" controls=\"controls\">");
}

function openUploadVoiceWin(){
	$("#id").val("");
	$("#file_size").val("");
	$("#track_length").val("");
	$("#file_name").val("");
	$("#template_name").val("");
	$("#listenTestDiv").html("");
	$("#sp_id option:first").prop("selected", 'selected'); 
	$("#commonVoiceModal").modal('show');
}


function uploadVoiceFile(obj) {
   var originalFileName = $(obj).val();
   var extName = '';
   if(originalFileName != '' && originalFileName.lastIndexOf(".") > 0){
	   extName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
   }
   if(extName == '' || extName != 'wav'){
	   alert("请选择wav文件上传，目前语音仅支持wav格式");
	   return;
   }
	
	$.ajaxFileUpload({
		url : 'voice/uploadVoiceFile',// 用于文件上传的服务器端请求地址
		secureuri : false,// 一般设置为false
		fileElementId : $(obj).attr('id'),// 文件上传控件的id属性
		dataType : 'text',// 返回值类型 一般设置为json
		type : 'post',
		data : {
			filename : $(obj).attr('filename'),
			spId:$("#sp_id").val()
		},
		success : function(data, status) // 服务器成功响应处理函数
		{
			if(data == 'fileSizeOutOf'){
				alert("文件尺寸超过1M,请重新选择文件上传");
				$(obj).val("");
				return false;
			}
			
			var fileArray =  data.split("|");
			$("#id").val(fileArray[0]);
			$("#file_size").val(fileArray[1]);
			$("#track_length").val(fileArray[2]);
			$("#file_name").val(fileArray[5]);
			
			$("#listenTestDiv").html("<audio src=\""+fileArray[4] + fileArray[3]+"\" type=\"audio/wav\" controls=\"controls\">");
		},
		error : function(data, status, e)// 服务器响应失败处理函数
		{
			alert("出错了");
			layer.msg(e);
		}
	});
}


function saveVoiceFile(){
	
	if($("#template_name").val() == ''){
		alert("请输入语音模板名称");
		return;
	}
	if($("#file_name").val() == ''){
		alert("请首先选择语言文件");
		return;
	}
	
   $.ajax({
	type : 'post',
	url : "voice/save",
	data : $("#commonVoiceForm").serialize(),
	success : function(data) {
		if (data.statusCode == "200") {
			$('#example').dataTable().fnDraw(false);
			$("#commonVoiceModal").modal("hide");
		}
	}
   });
}

function del(voiceFileId) {
	layer.confirm('您确定删除此语音文件吗？', {
		icon : 0,
		title : '语音文件删除',
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : 'voice/delete',
			type : 'post',
			data : {
				id : voiceFileId
			},
			success : function(data) {
				if (data.statusCode == 200) {
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


function rejectReason(id) {
	if (id && typeof (id) != "undefined") {
		$.get("voice/getVoice?id=" + id, function(data) {
			$("#rejectReason").val(data.data.rejectreason);
		});
	}
	$("#rejectReasonModal").modal("show");
}
