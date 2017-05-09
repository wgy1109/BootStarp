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
								aoData.status = "1";
								aoData.spId = $("#spId").val();
							},
							serverSide : true,
							columns : [
										{
										   "data" : "id",
											render : function(data, type, row) {
													return "<span style=\"margin-left:30px\"><input type=\"radio\" name=\"voiceFileRadio\" value=\""+data +"\"/></span>";
											}
										}, 
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
								targets : 5,
								render : function(a, b, c, d) {
									//var voicePath = "'" + a.nginxUrl + a.file_name + "'";
									if(a.status == 0){ //审批中
										var html = "<button class='mb-sm btn btn-primary' onclick='javascript:listenTest(\""+a.fileUrl + a.fileShortPath+"\")'>试听</button>"
										return html;
									}
									if(a.status == 1){ //审批通过
										var html = "<button class='mb-sm btn btn-primary' onclick='listenTest(\""
												+ a.fileUrl + a.fileShortPath 
												+ "\")'>试听</button>";
										return html;
									}
									if(a.status == -1 || a.status == 2){ //审批未通过   或暂停
										var html = "<button class='mb-sm btn btn-primary' onclick='listenTest(\""
												+ a.fileUrl + a.fileShortPath
												+ "\")'>试听</button>&nbsp;<button class='mb-sm btn btn-primary' onclick='del("
												+ a.id
												+ ")'>删除</button>&nbsp;<button class='mb-sm btn btn-primary' onclick='explain("
												+ a.id
												+ ")'>原因</button>";
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
	$('#timingTime').datetimepicker({
		lang:"ch",  
		format:'Y-m-d H:i',
		TimePicker: true,
		allowBlank : true,
		step:15,
		onChangeDateTime:function() {
			var start = new Date($('#timingTime').val().replace(/-/g, "/"));
			var end = new Date();
			if (start <= end) {
				layer.msg("定时时间必须晚于当前时间");
				$('#timingTime').val("");
			}
		}
	});
	$("#impotPhoneListForm").validate({
		rules : {
			file : {
				required : true
			},
		},
		messages : {
			file : {
				required : "上传文件不能为空"

			},
		},
		submitHandler : function(form) {
			layer.load(2);
			$.ajaxFileUpload({
				url : 'smsSendTaskClient/uploadPhone',
				secureuri : false,
				fileElementId : 'file',
				dataType : "json",
				success : function(data, status) // 服务器成功响应处理函数
				{
					layer.closeAll("loading");
					var fixData = JSON.parse(data);
					if (fixData.returnCode == 200) {
						layer.msg('导入成功');
						$("#modaljsp").modal("hide");
						$("#mobile").val(fixData.text);
					} else if (fixData.returnCode == 203) {
						layer.msg(fixData.error);
					}
				},
				error : function(data, status, e)// 服务器响应失败处理函数
				{
					layer.closeAll("loading");
					layer.msg(e);
				}
			})
		}
	});
	
	$("#voiceSendForm").validate({
		rules : {
			mobile : {
				required : true
			}
		},
		messages : {
			mobile : {
				required : "手机号不能为空"
			}
		},
		submitHandler : function(form) {
			var  checked = $("input[name='voiceFileRadio']:checked").val();
			if(checked == null ||checked == "" || checked == undefined){
				alert("请选择语音文件！");
				return;
			}
			
			$("#voiceFileId").val($("input[name='voiceFileRadio']:checked").val());
			layer.load(2);
			$.ajax({
				url : "sendVoice/sendVoiceFile",
				data : $("#voiceSendForm").serialize(),
				dataType : "json",
				type : "post",
				success : function(data) {
					layer.closeAll('loading');
					if (data) {
						console.log("data" + data + "returnstatus" + data.returnstatus);
						if (data.returnstatus == "0") {
							layer.msg("发送成功");
						} else {
							layer.msg("发送失败：" + data.message);
						}
					}
				}
			})
		}
	});
});

function search() {
	$('#example').dataTable().fnDraw(false);
}

function listenTest(voiceFullPath) {
	$("#listListenTestDiv").html("<audio src=\""+voiceFullPath+"\" type=\"audio/wav\" autoPlay=\"autoPlay\" controls=\"controls\">");
}

function exportPhone() {
	$("#file").val("");
	$("#fileArea").removeClass("has-success");
	$('#modaljsp').modal('show');
}
/*$(function (){
	

	$("#impotPhoneListForm").validate({
		rules : {
			file : {
				required : true
			},
		},
		messages : {
			file : {
				required : "上传文件不能为空"

			},
		},
		submitHandler : function(form) {
			layer.load(2);
			$.ajaxFileUpload({
				url : 'smsSendTaskClient/uploadPhone',
				secureuri : false,
				fileElementId : 'file',
				dataType : "json",
				success : function(data, status) // 服务器成功响应处理函数
				{
					layer.closeAll("loading");
					var fixData = JSON.parse(data);
					if (fixData.returnCode == 200) {
						layer.msg('导入成功');
						$("#modaljsp").modal("hide");
						$("#mobile").val(fixData.text);
					} else if (fixData.returnCode == 203) {
						layer.msg(fixData.error);
					}
				},
				error : function(data, status, e)// 服务器响应失败处理函数
				{
					layer.closeAll("loading");
					layer.msg(e);
				}
			})
		}
	});
});*/


function textAreaCount(){
	// 先选出 textarea 和 统计字数 dom 节点
	var wordCount = $("#priceHtml"), textArea = wordCount.find("textarea"), count = $("#smsChargeNum")
	word = $("#smsLength,#smsCount");
	// 调用
	statInputNum(textArea, word, count);
}
/*
 * 剩余字数统计 注意 最大字数只需要在放数字的节点哪里直接写好即可 如：<var class="word">200</var>
 */
function statInputNum(textArea, numItem, count) {
	textArea.on('input propertychange', function() {
		numItem.text($(this).val().length);
		if($(this).val().length <= 70){
			count.text(Math.ceil(($(this).val().length) / 70));
		}else{
			count.text(Math.ceil(($(this).val().length) / 67));
		}
		
	})
}
function filterPhone() {
	var smstelhtml = $("#mobile").val();
	var regEng = RegExp("[A-Za-z]", 'g');
	var regGb2312 = RegExp("[\u4e00-\u9fa5]", 'g');
	var regpattern = new RegExp(
			"[`~!@#$%^&*()=|{}':;'\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]",
			'g');
	smstelhtml = smstelhtml.replace(regGb2312, ',').replace(regEng, ',')
			.replace(regpattern, ',');
	if (smstelhtml == "") {
		$("#mobileLayerMsg").html("没有数据,无法过滤！");
	} else {
		$.ajax({
			url : "smsSendTaskClient/filterPhoneNum",
			data : {
				mobile : smstelhtml
			},
			dataType : "json",
			type : "post",
			success : function(result) {
				if (result) {
					$("#mobileLayerMsg").html("提交量(" + result.phoneAllCount + "),有效号码(" + result.phoneCount + ")");
					$("#mobile").val(result.phoneStr);
				} else {
					$("#mobileLayerMsg").html("系统出错，请重试");
				}
			}
		})
	}
}

function downLoadTemplete(type) {
	window.location.href = "smsSendTaskClient/downloadTemplate?type=" + type;
}


