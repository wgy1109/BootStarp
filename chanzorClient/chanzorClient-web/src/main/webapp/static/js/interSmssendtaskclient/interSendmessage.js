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
	$.get("intersmsSendTaskClient/findSpInfoByUserId?sp_service_type=2", function(data) {
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
		numItem.text($(this).val().length);
		console.log("-----"+$(this).val());
		var re = new RegExp("^([0-9a-zA-Z _;',.!\$@#\n\+=\?\|]*?)$");
//		((?:[a-zA-Z]+[0-9]+(?:[0-9]|[a-zA-Z])*)|(?:[0-9]+[a-zA-Z]+(?:[0-9]|[a-zA-Z])*)*)
		if(re.test($(this).val())){
			count.text(Math.ceil(($(this).val().length) / 150));
		}else{
			count.text(Math.ceil(($(this).val().length) / 70));
		}
	});
}

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
				$.get("intersmsSendTaskClient/findPlateInfoById?id=" + value,
						function(data) {
							if (data && data.plateInfo) {
								$("#plateInfocontent").html(
										data.plateInfo.CONTENT);
							}
						})
			}
		});

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
			url : "intersmsSendTaskClient/postPayInfo",
			data : $("#payPostInfoForm").serialize(),
			dataType : "json",
			type : "post",
			success : function(data) {
				layer.closeAll('loading');
				if (data) {
					console.log("data" + data + "returnstatus" + data.returnstatus);
					if (data.returnstatus == "Success") {
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
	var smstelhtml = $("#mobile").val();
	var regEng = RegExp("[A-Za-z]", 'g');
	var regGb2312 = RegExp("[\u4e00-\u9fa5]", 'g');
	var regpattern = new RegExp(
			"[`~!@#$%^&*()=|{}':;'\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]",
			'g');
	smstelhtml = smstelhtml.replace(regGb2312, ',').replace(regEng, ',')
			.replace(regpattern, ',');
	if (smstelhtml == "") {
		$("#mobileLayerMsg").val("没有数据,无法过滤！");
	} else {
		$.ajax({
			url : "intersmsSendTaskClient/filterPhoneNum",
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
					layer.msg("系统出错，请重试");
					$("#mobileLayerMsg").val("系统出错，请重试");
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
	window.location.href = "intersmsSendTaskClient/downloadTemplate?type=" + type;
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
			url : 'intersmsSendTaskClient/uploadPhone',
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
