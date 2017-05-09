$(function() {
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
	$.get("smsSendTaskClient/findSpInfoByUserId?sp_service_type=1", function(data) {
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
		$("#plateInfocontent").attr("maxlength",data.maxlength);
	});
	textAreaCount();
});

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
		var newString = $(this).val().replace(/\r\n/g, "[]").replace(/\n\r/g, "[]").replace(/\r/g, "[]").replace(/\n/g, "[]"); 
		var num = newString.length;
		numItem.text(num);
		if(num <= 70){
			count.text(Math.ceil((num) / 70));
		}else{
			count.text(Math.ceil((num) / 67));
		}
		
	});
}
$("#spId").change(function() {
	var value = $("#spId").val();
	if (value != 0) {
		$.get("smsSendTaskClient/checkSpStatus?spId=" + value, function(data) {
			$("#spsysMasterplate").empty();
			var content = [];
			content.push("<option value=''>请选择</option>");
			$.each(data.masplist, function(i, even) {
				if (even.title) {
					content.push("<option value='" + even.content + "'>" + even.title + "</option>");
				}
			});
			$("#spsysMasterplate").html(content.join(''));
			if (data.status != 2 && data.status != 1) {
				$("#mandatory").show();
				$("#plateInfocontent").val("【畅卓科技】");
				$("#hiddenSignature").val("【畅卓科技】");
				$("#resultcheckword").html("注：未上线应用签名为【畅卓科技】");
			} else {
				$("#mandatory").hide();
				$("#plateInfocontent").val(data.signature);
				$("#hiddenSignature").val(data.signature);
				$("#resultcheckword").html("");
			}
		})
	}
});

$("#spsysMasterplate").change(function() {
	var value = $("#spsysMasterplate").val();
	var signature = $("#hiddenSignature").val();
	$("#plateInfocontent").val(value+signature);
	$("#smsLength").text($("#plateInfocontent").val().length);
	$("#smsCount").text($("#plateInfocontent").val().length);
	if($("#plateInfocontent").val().length <= 70){
		$("#smsChargeNum").text(Math.ceil(($("#plateInfocontent").val().length) / 70));
	}else{
		$("#smsChargeNum").text(Math.ceil(($("#plateInfocontent").val().length) / 67));
	}
});

function delCount(){
	$("#smsLength").text(0);
	$("#smsCount").text(0);
	$("#smsChargeNum").text(0);
	$("#mobileLayerMsg").html("");
	$("#resultcheckword").html("");
};

$("#account").change(
		function() {
			var value = $("#account").val();
			if (value != 0) {
				$.get("smsSendTaskClient/findPlateInfoById?id=" + value,
						function(data) {
							if (data && data.plateInfo) {
								$("#plateInfocontent").html(
										data.plateInfo.CONTENT);
							}
						})
			}
		});

/*function payPostInfoFormSubmit(){
	layer.load(2);
	$.ajax({
		url : "smsSendTaskClient/postPayInfo",
		data : $("#payPostInfoForm").serialize(),
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
}*/

$("#payPostInfoForm").validate({
	rules : {
		spId : {
			required : true
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
			required : "应用信息不能为空"
		},
		mobile : {
			required : "手机号不能为空"
		},
		content : {
			required : "短信内容不能为空"
		},
	},
	submitHandler : function(form) {
		layer.load(2);
		$.ajax({
			url : "smsSendTaskClient/postPayInfo",
			data : $("#payPostInfoForm").serialize(),
			dataType : "json",
			type : "post",
			success : function(data) {
				layer.closeAll('loading');
				if (data) {
					console.log("data" + data + "returnstatus" + data.returnstatus);
					if (data.returnstatus == "0") {
						layer.confirm('发送成功', {
							icon : 1,
							title : '成功提示',
							btn : [ '确定' ]
						}, function(index) {
							layer.close(index);
						});
					} else {
						layer.confirm('发送失败：' + data.message, {
							icon : 2,
							title : '失败提示',
							btn : [ '确定' ]
						}, function(index) {
							layer.close(index);
						});
					}
				}
			}
		})
	}
});

function filterPhone() {
	layer.load(2);
	var smstelhtml = $("#mobile").val();
	var regEng = RegExp("[A-Za-z]", 'g');
	var regGb2312 = RegExp("[\u4e00-\u9fa5]", 'g');
	var regpattern = new RegExp(
			"[`~!@#$%^&*()=|{}':;'\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]",
			'g');
	smstelhtml = smstelhtml.replace(regGb2312, ',').replace(regEng, ',')
			.replace(regpattern, ',');
	if (smstelhtml == "") {
		layer.closeAll('loading');
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
				layer.closeAll('loading');
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
function exportPhone() {
	$("#file").val("");
	$("#fileArea").removeClass("has-success");
	$('#modaljsp').modal('show');
}

function downLoadTemplete(type) {
	window.location.href = "smsSendTaskClient/downloadTemplate?type=" + type;
}

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
		data : {
			'spId':$("#spId").val(),
			'content':content
		},
		success : function(data) {
			layer.closeAll("loading");
			if (data.statusCode == 200) {
				if (data.msg == '') {
//					layer.msg("没有敏感词");
					$("#resultcheckword").html("没有敏感词");
				} else {
//					layer.msg("检测到敏感词:" + data.msg);
					$("#resultcheckword").html("敏感词："+data.msg+"   请修改短信内容");
				}
			} else {
//				layer.msg(data.msg);
				$("#resultcheckword").html("敏感词："+data.msg+"   请修改短信内容");
			}
		}
	});
}