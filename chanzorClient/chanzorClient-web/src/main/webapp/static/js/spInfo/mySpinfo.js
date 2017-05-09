$(function() {
	$.get("findSysNotice", function(data) {
		if (data.topOaNotify && data.topOaNotify.content) {
			$("#sysNotice").html(data.topOaNotify.content);
		}

	})
	$.validator
			.addMethod(
					"isMobile",
					function(value, element) {
						var length = value.length;
						var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
						return this.optional(element)
								|| (length == 11 && mobile.test(value));
					}, "请正确填写您的手机号码");
	$("#testgndx").click(function() {
		$("#sp_service_type").val(1);
		$("#testMessageModal").modal("show");
	})
	$("#testgjdx").click(function() {
		$("#sp_Inter_service_type").val(2);
		$("#testInterMessageModal").modal("show");
	})
	$("#testyydx").click(function() {
		$("#sp_voice_service_type").val(3);
		$("#testVoiceMessageModal").modal("show");
	})
	$.get("spInfo/mySpinfoSendCount", function(data) {
		var serie = [];
		var dataTitle = [];
		$.each(data.data, function(i, v) {
			var item = {
				name : v.name,
				type : 'line',
				data : v.data
			}
			serie.push(item);
			dataTitle.push(v.name);
		})
		var myChart = echarts.init(document.getElementById('footerDiv_div'))
		var option = {

			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : dataTitle
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
				data : [ '01月', '02月', '03月', '04月', '05月', '06月', '07月',
						'08月', '09月', '10月', '11月', '12月' ]
			},
			yAxis : {
				type : 'value'
			},
			series : serie
		};
		myChart.setOption(option);

	})
})
function changeTestMessage(select) {
	if ($(select).val() == 0) {
		$('#testMessage').empty();
		$('#testMessage').html("【畅卓科技】您的短信验证码是1234。如非本人操作，请忽略此短信。本短信免费。");
	} else {
		$('#testMessage').empty();
		$('#testMessage').html(
				"【畅卓科技】您好，您预约的订单，已被服务商接单，下载APP（http://chanzor.com）查看更多信息");
	}
}
function changeTestInterMessage(select) {
	if ($(select).val() == 0) {
		$('#testInterMessage').empty();
		$('#testInterMessage').html(
				"【chanzor】Dear user, your verification code is @.");
	} else {
		$('#testInterMessage').empty();
		$('#testInterMessage')
				.html(
						"【chanzor】Dear user, your account login IP changes, please confirm your account security issues.");
	}
}

function findSpInfoById(id) {
	goPage('spInfo/findspInfo?spId=' + id, 'static/js/spInfo/spInfoView.js')

}
$("#testMessageForm").validate(
		{
			rules : {
				testPhone : {
					required : true,
					isMobile : true,
				},
				testMessage : {
					required : true,
				}

			},
			messages : {
				testPhone : {
					required : "手机号必填",
					isMobile : "手机号格式不正确"
				},
				testMessage : {
					required : "短信内容必填",
				}
			},
			submitHandler : function() {
				$.post("spInfo/sendTestMessage?"
						+ $("#testMessageForm").serialize(), function(data) {
					layer.msg(data.desc);
					$("#testMessageModal").modal("hide");
				})

			}
		});

$("#testInterMessageForm").validate(
		{
			rules : {
				testPhone : {
					required : true,
				},
				testMessage : {
					required : true,
				}

			},
			messages : {
				testPhone : {
					required : "手机号必填",
				},
				testMessage : {
					required : "短信内容必填",
				}
			},
			submitHandler : function() {
				$.post("spInfo/sendInterTestMessage?"
						+ $("#testInterMessageForm").serialize(),
						function(data) {
							layer.msg(data.desc);
							$("#testInterMessageModal").modal("hide");
						})

			}
		});

$("#testVoiceMessageForm").validate(
		{
			rules : {
				testPhone : {
					required : true,
				},
				testMessage : {
					required : true,
				}

			},
			messages : {
				testPhone : {
					required : "手机号必填",
				},
				testMessage : {
					required : "短信内容必填",
				}
			},
			submitHandler : function() {
				$.post("spInfo/sendVoiceTestMessage?"
						+ $("#testVoiceMessageForm").serialize(),
						function(data) {
							layer.msg(data.desc);
							$("#testVoiceMessageModal").modal("hide");
						})

			}
		});
function changeMessageImgOver(select, img) {
	$(select).find("img").attr("src", 'static/img/' + img);
	$(select).find("p").css("color", "#559AFA");
}
function changeMessageImgOut(select, img) {
	$(select).find("img").attr("src", 'static/img/' + img);
	$(select).find("p").css("color", "#B2B2B2");

}
function zfbCharge() {
	$("#outLineCharegeArea").hide();
	$("#zfbCharegeArea").show();
}
function outLineCharge() {
	$("#zfbCharegeArea").hide();
	$("#outLineCharegeArea").show();
}
function frozenSpCharge() {
	layer.msg("冻结应用不能充值");
}