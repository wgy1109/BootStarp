$(function() {
	$("#ex8").slider({
		tooltip : 'always'
	});
	$('#timingTime').datetimepicker({
		lang : "ch",
		format : 'Y-m-d H:i',
		TimePicker : true,
		allowBlank : true,
		step : 15,
		onChangeDateTime : function() {
			var start = new Date($('#timingTime').val().replace(/-/g, "/"));
			var end = new Date();
			if (start <= end) {
				layer.msg("定时时间必须晚于当前时间");
				$('#timingTime').val("");
			}
		}
	});
	$("#spId").change(
			function() {
				var value = $("#spId").val();
				if (value != 0) {
					$.get("smsSendTextVoice/findVoiceTemplate?spId=" + value,
							function(data) {
								$("#spsysMasterplate").empty();
								var content = [];
								content.push("<option value='0'>请选择</option>");
								$.each(data.masplist, function(i, even) {
									if (even.title) {
										content.push("<option text='"
												+ even.content + "' value="
												+ even.id + ">" + even.title
												+ "</option>");
									}
								});
								$("#spsysMasterplate").html(content.join(''));
								if (data.status != 2 && data.status != 1) {
									$("#mandatory").show();
								} else {
									$("#mandatory").hide();
									$("#resultcheckword").html("");
								}
							})
				}
			});
	$("#spsysMasterplate").change(function() {
		var value = $("#spsysMasterplate option:selected").attr("text");
		$("#plateInfocontent").val(typeof (value) == "undefined" ? "" : value);
		if ($(this).val() != 0) {
			$("#stepShow").hide();
		} else {
			$("#stepShow").show();
		}
		// $("#smsLength").text($("#plateInfocontent").val().length);
		// $("#smsCount").text($("#plateInfocontent").val().length);
		// if($("#plateInfocontent").val().length <= 70){
		// $("#smsChargeNum").text(Math.ceil(($("#plateInfocontent").val().length)
		// /
		// 70));
		// }else{
		// $("#smsChargeNum").text(Math.ceil(($("#plateInfocontent").val().length)
		// /
		// 67));
		// }
		//		
	});

	$.get("smsSendTaskClient/findSpInfoByUserId?sp_service_type=3", function(
			data) {
		if (data && data.spList) {
			$("#spId").empty();
			var content = [];
			content.push("<option value='0'>请选择</option>");
			$.each(data.spList, function(i, even) {
				if (even.sp_name) {
					content.push("<option value=" + even.spid + ">"
							+ even.sp_name + "</option>");
				}
			});
			$("#spId").html(content.join(''));
		}
		$("#plateInfocontent").attr("maxlength", data.maxlength);
	});
	textAreaCount();
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
});
function exportPhone() {
	$("#file").val("");
	$("#fileArea").removeClass("has-success");
	$('#modaljsp').modal('show');
}

function textAreaCount() {
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
		if ($(this).val().length <= 70) {
			count.text(Math.ceil(($(this).val().length) / 70));
		} else {
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
					$("#mobileLayerMsg").html(
							"提交量(" + result.phoneAllCount + "),有效号码("
									+ result.phoneCount + ")");
					$("#mobile").val(result.phoneStr);
				} else {
					$("#mobileLayerMsg").html("系统出错，请重试");
				}
			}
		})
	}
}
function checkWord() {
	var content = $("#plateInfocontent").val();
	if (content.trim().length == 0) {
		$("#resultcheckword").html("请输入短信内容");
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
					// layer.msg("没有敏感词");
					$("#resultcheckword").html("没有敏感词");
				} else {
					// layer.msg("检测到敏感词:" + data.msg);
					$("#resultcheckword")
							.html("敏感词：" + data.msg + "   请修改短信内容");
				}
			} else {
				// layer.msg(data.msg);
				$("#resultcheckword").html("敏感词：" + data.msg + "   请修改短信内容");
			}
		}
	});
}
function downLoadTemplete(type) {
	window.location.href = "smsSendTaskClient/downloadTemplate?type=" + type;
}
function changeVoice() {
	if ($("#plateInfocontent").val() != '') {
		$.post("textVoice/textConvertVoice?"
				+ $("#voiceConvertForm").serialize(), function(data) {
			$("#audioPlugin").show();
			$("#audioPlugin").find("audio").attr("src", data.voiceFullPath);
		})

	} else {
		layer.msg("短信内容不能为空");
	}

}
$("#voiceConvertForm").validate(
		{
			rules : {
				spId : {
					required : true,
					min : 1
				},
				mobile : {
					required : true
				},
				content : {
					required : true
				},
			},
			messages : {
				spId : {
					required : "应用信息不能为空",
					min : "发送应用不能为空"
				},
				mobile : {
					required : "手机号不能为空"
				},
				content : {
					required : "短信内容不能为空"
				},
			},
			submitHandler : function(form) {
				$.post("smsSendTextVoice/sendTextVoice?"
						+ $("#voiceConvertForm").serialize(), function(data) {
					layer.msg(data.message);
				})
			}
		});
