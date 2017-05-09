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
								url : "textVoice/load",
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
								aoData.TITLE = $("#TITLE").val();
								aoData.STATUS = $("#STATUS").val();
								aoData.SPID = $("#SPID").val();
							},
							serverSide : true,
							columns : [
							           	{
							           		"data" : "title"
							           	}, 
										{
											"data" : "spName",
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
												if (data == 3) {
													return "未提交";
												}else if(data == 1){
													return "审核中";
												}else if(data == 0){
													return "审核通过";
												}else if(data == 2){
													return "审核驳回";
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
									if(a.status == 3){ //未提交
										var html = "<button class='mb-sm btn btn-primary' onclick='editTextVoice(\""
											+ a.id
											+ "\")'>修改</button>&nbsp;<button class='mb-sm btn btn-primary' onclick='del("
											+ a.id
											+ ")'>删除</button>&nbsp;<button class='mb-sm btn btn-primary' onclick='commitAudit("
											+ a.id
											+ ")'>提交审核</button>";
									   return html;
									}
									if(a.status == 1){ //审核中
									     return "";
									}
									if(a.status == 0){ //审核通过
										var html = "<button class='mb-sm btn btn-primary' onclick='del("
											+ a.id
											+ ")'>删除</button>";
									   return html;
									}
									if(a.status == 2){ //审核驳回
										var html = "<button class='mb-sm btn btn-primary' onclick='del("
											+ a.id
											+ ")'>删除</button>&nbsp;<button class='mb-sm btn btn-primary' onclick='rejectReason("
											+ a.id
											+ ")'>驳回原因</button>";
									   return html;
									}
									return "";
								}
							} ],
							fnDrawCallback : function(a, b, c, d) {
								var table = $(this);
								var arrayObj = new Array();
								$.each($(a).attr("json").data, function(i, e) {
									arrayObj.push(e.content);
								})
								if (arrayObj.length > 0) {
									$
											.each(
													table.find("tr"),
													function(i, e) {
														if (i != 0) {
															$(this)
																	.after(
																			"<tr><td colspan='5' style='text-align: right;'>"
																					+ stringIntercept(arrayObj[i - 1])
																					+ "<span class='text-danger' >（共 "
																					+ arrayObj[i - 1].length
																					+ " 字）</span></td></tr>")
														}
													})
								}
							}
						});
	}
};

$(function() {
	if ($.fn.dataTable.isDataTable('#example')){
		$('#example').dataTable().fnDraw(false);
	}else{
		dataTable.init();
	}
	$("#speed").slider({
		tooltip : 'always'
	});
});

function search() {
	$('#example').dataTable().fnDraw(false);
}

function addTextVoice(){
	$("#id").val("");
    $("#title").val("");
    $("#textVoiceContent").val("");	
    $("#sp_id option:first").prop("selected", 'selected'); 
    $("input[type='radio'][name='voice_name_temp'][value='xiaoyan']").prop("checked",true); 
    $("#resultcheckword").html("");
    $("#textVoiceModal").modal("show");
}

function closeModal(modalWinId) {
	$("#" + modalWinId).modal('hide');
}

function save(){
   if($("#id").val() != ""){
	   updateTextVoice();
   }
   else{
	   saveTextVoice();
   }
}

function saveTextVoice(){
	
	if($("#title").val() == ''){
		alert("请输入标题名称");
		return;
	}
	
	if($("#textVoiceContent").val() == ''){
		alert("请输入模板内容");
		return;
	}
	$("#voice_name").val($("input[name='voice_name_temp']:checked").val());
	
   $.ajax({
	type : 'post',
	url : "textVoice/save",
	data : $("#textVoiceForm").serialize(),
	success : function(data) {
		if (data.statusCode == "200") {
			$('#example').dataTable().fnDraw(false);
			$("#textVoiceModal").modal("hide");
		}
	}
   });
}
function commitAudit(textVoiceId){
	layer.confirm('您确定提交审核吗？', {
		icon : 0,
		title : '语音模板提交审核',
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : 'textVoice/commitAudit',
			type : 'post',
			data : {
				id : textVoiceId
			},
			success : function(data) {
				if (data.statusCode == 200) {
					layer.msg("提交审核成功！", {
						icon : 1
					});
					search();
				} else {
					layer.msg("提交审核失败！", {
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
function del(textVoiceId) {
	layer.confirm('您确定删除此条文本语音模板？', {
		icon : 0,
		title : '语音模板删除',
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : 'textVoice/delete',
			type : 'post',
			data : {
				id : textVoiceId
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



function editTextVoice(id) {
	if (id && typeof (id) != "undefined") {
		$.get("textVoice/getTextVoice?id=" + id, function(data) {
			$("#id").val(data.textVoice.id);
			$("#title").val(data.textVoice.title);
			$("#textVoiceContent").val(data.textVoice.content);
			$("#sp_id").val(data.textVoice.sp_id);
			$("input[type=radio][name=voice_name_temp][value="+data.textVoice.voice_name + "]").attr("checked","checked");
			
			//$("#speed").prop("data-slider-value",data.textVoice.speed);
			//$("#speed").slider({value: data.textVoice.speed});
			$("#speed").slider('setValue', data.textVoice.speed)
			
		});
	}
	$("#textVoiceModal").modal("show");
}


function updateTextVoice(){
	
	if($("#title").val() == ''){
		alert("请输入标题名称");
		return;
	}
	
	if($("#textVoiceContent").val() == ''){
		alert("请输入模板内容");
		return;
	}
	$("#voice_name").val($("input[name='voice_name_temp']:checked").val());
   $.ajax({
	type : 'post',
	url : "textVoice/update",
	data : $("#textVoiceForm").serialize(),
	success : function(data) {
		if (data.statusCode == "200") {
			$('#example').dataTable().fnDraw(false);
			$("#textVoiceModal").modal("hide");
		}
	}
   });
}


function listenTest(){
	if($("#textVoiceContent").val() == ''){
		alert("请输入模板内容");
		return;
	}
	$("#voice_name").val($("input[name='voice_name_temp']:checked").val());
	layer.load(2);
	$.ajax({
		url : 'textVoice/textConvertVoice',
		type : 'post',
		data :  $("#textVoiceForm").serialize(),
		success : function(data) {
			layer.closeAll("loading");
			if(data.voiceFullPath !=  ''){
				$("#listenTestDiv").html("<audio src=\""+data.voiceFullPath+"\" type=\"audio/wav\" autoPlay=\"autoPlay\" controls=\"controls\">");
			}
		}
	});
}

function checkWord() {
	var content = $("#textVoiceContent").val();
	if (content.trim().length == 0) {
		$("#resultcheckword").html("请输入模板内容");
		return;
	}
	layer.load(2);
	$.ajax({
		url : 'smsSendTaskClient/doCheck',
		type : 'post',
		data : "content=" + content,
		success : function(data) {
			layer.closeAll("loading");
			if (data.statusCode == 200) {
				if (data.msg == '') {
//					layer.msg("没有敏感词");
					$("#resultcheckword").html("没有敏感词");
				} else {
//					layer.msg("检测到敏感词:" + data.msg);
					$("#resultcheckword").html("敏感词："+data.msg+"   请修改模板内容");
				}
			} else {
//				layer.msg(data.msg);
				$("#resultcheckword").html("敏感词："+data.msg+"   请修改模板内容");
			}
		}
	});
}


function rejectReason(id) {
	if (id && typeof (id) != "undefined") {
		$.get("textVoice/getTextVoice?id=" + id, function(data) {
			$("#rejectReason").val(data.textVoice.regectreason);
		});
	}
	$("#rejectReasonModal").modal("show");
}
