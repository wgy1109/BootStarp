(function($) {
	$.fn.extend({
		insertAtCaret : function(myValue) {
			var $t = $(this)[0];
			if (document.selection) {
				this.focus();
				sel = document.selection.createRange();
				sel.text = myValue;
				this.focus();
			} else if ($t.selectionStart || $t.selectionStart == '0') {
				var startPos = $t.selectionStart;
				var endPos = $t.selectionEnd;
				var scrollTop = $t.scrollTop;
				$t.value = $t.value.substring(0, startPos) + myValue
						+ $t.value.substring(endPos, $t.value.length);
				this.focus();
				$t.selectionStart = startPos + myValue.length;
				$t.selectionEnd = startPos + myValue.length;
				$t.scrollTop = scrollTop;
			} else {
				this.value += myValue;
				this.focus();
			}
		}
	})
})(jQuery);
$(function() {
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
	$.get("smsSendTaskClient/findSpInfoByUserId?sp_service_type=1", function(
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
	});
})
function exportExcel() {
	if ($("#spId").val() == 0) {
		layer.msg("请先选择发送应用");
		return;
	}
	$("#modaljsp").modal("show");
}

function insertColumn(obj) {
	$("#templateContent").insertAtCaret($("#insColumn").val());
	$("#templateContent").focus();
}

function ajaxFileUpload(obj, hideObj) {
	$.ajaxFileUpload({
		url : 'smsSendTaskClient/uploadCustomized', // 用于文件上传的服务器端请求地址
		type : 'post',
		secureuri : false, // 一般设置为false
		fileElementId : obj, // 文件上传空间的id属性 <input type="file" id="file"
		// name="file" />
		dataType : 'text', // 返回值类型 一般设置为json
		data : {
			"dataType" : hideObj
		},
		success : function(data, status) // 服务器成功响应处理函数
		{
			if (data) {
				$("#" + hideObj).val(data);
			}
		},
		error : function(data, status, e)// 服务器响应失败处理函数
		{
			alert(e);
		}
	})
	return false;
}
$.validator.addMethod("checkExport", function(value, element) {

	return returnVal;
}, "导入文件不能为空");
$("#impotMessageContent")
		.validate(
				{
					onkeyup : false,
					onblur : false,
					onfocus : false,
					onkeydown : false,
					rules : {
						templateContent : {
							required : true
						}
					},
					messages : {
						templateContent : {
							required : "应用信息不能为空"
						}
					},
					submitHandler : function(form) {
						if ($("#templateUrl").val() == '') {
							$("#templateUrl")
									.after(
											"<label id='templateFile-error' class='error' for='templateFile'>导入文件不能为空</label>");
							return false;
						}
						layer.load(2)
						$.ajax({
							url : "smsSendTaskClient/saveCustomizedMessage",
							data : $("#impotMessageContent").serialize()
									+ "&spId=" + $("#spId").val(),
							dataType : "json",
							type : "post",
							success : function(data) {
								 layer.closeAll('loading');
								if (data.status == "200") {
									$("#plateInfocontent").html("");
									var htmlText = "";
									$.each(data.contentList, function(index,
											value) {
										htmlText += value + "\r\n";
									})
									$("#plateInfocontent").html(htmlText);
									layer.msg("导入成功");
									$("#customeizedContent").val(data.content);
									$("#smsChargeNum").html(data.phoneSize);
									$("#hiddePhoneSize").show();
									$("#redisKey").val(data.key);
									$("#modaljsp").modal("hide");

								} else {
									layer.msg(data.msg);
									$("#templateFile").val('');
								}
							}
						})
					}
				});
$("#sendCustomizedForm").validate({
	rules : {
		spId : {
			min : 1
		},
		plateInfocontent : {
			required : true
		}
	},
	messages : {
		spId : {
			min : "应用信息不能为空"
		},
		plateInfocontent : {
			required : "短信内容不能为空"
		}
	},
	submitHandler : function(form) {
		layer.load(2);
		$.ajax({
			url : "smsSendTaskClient/sendCustomizedMessage",
			data : $("#sendCustomizedForm").serialize(),
			timeout : 100000, 
			dataType : "json",
			type : "post",
			success : function(data) {
				layer.closeAll('loading');
				layer.msg("发送成功");
				layer.confirm(data.message, {
					icon : 1,
					title : '信息',
					btn : [ '确定' ]
				}, function(index) {
					layer.close(index);
				});
			}
		})
	}
});
function delCount(){
	$("#plateInfocontent").html("");
	$("#customeizedContent").val("");
	$("#hiddePhoneSize").hide();
	$("#redisKey").val("");
}
$("#modaljsp").on(
		"hide.bs.modal",
		function() {
			$(':input', '#impotMessageContent').not(
					':button, :submit, :reset,:checkbox,select').val('');
			$("#modaljsp").find("label.error").remove();
			$("#modaljsp").find(".error").removeClass("error");
			$("#phoneIndex").val("1");
			$("#insColumn").val("[(B)]");
		});
