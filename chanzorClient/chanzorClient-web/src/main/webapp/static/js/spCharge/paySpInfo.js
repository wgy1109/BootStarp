$(function() {
	var price = 0;
	$('#payForm').off('submit.Parsley');
	if ($("#hideSpId").val()) {
		$.get("spInfo/findSpInfoBySpId?spId=" + $("#hideSpId").val(), function(
				data) {
			price = data.saleprice;
			switch (data.sp_service_type) {
			case 1:
				$('#myTab a[href="#domiestic"]').tab('show');
				$('#myTab a[href="#voice"]').hide();
				$('#myTab a[href="#inter"]').hide();
				$("#chargeType").val("domiestic");
				break;
			case 2:
				$('#myTab a[href="#inter"]').tab('show');
				$('#myTab a[href="#voice"]').hide();
				$('#myTab a[href="#domiestic"]').hide();
				$("#chargeType").val("inter");
				break;
			case 3:
				$('#myTab a[href="#voice"]').tab('show');
				$('#myTab a[href="#domiestic"]').hide();
				$('#myTab a[href="#inter"]').hide();
				$("#chargeType").val("voice");
				break;
			default:
				$('#myTab a[href="#domiestic"]').tab('show');
			}
			$("#spInfoList").empty();
			$("#spInfoList").append(
					"<option value='" + data.spid + "'>" + data.sp_name
							+ "</option>");
		})
	} else {
		getSpList(1);
	}
	$("#spInfoList").change(function(a, b) {
		if ($(this).val() == 0) {
			return;
		}
		if ($("#chargeType").val() == 'domiestic') {
			getPrice($(this).val(), "priceHtml");
		} else {
			getPrice($(this).val(), "voicePriceHtml");
		}
	});
	function getPrice(value, content) {
		$.get("chargeRecord/findChargeNum", "spId=" + value, function(data) {
			if (data.spInfo.saleprice) {
				price = data.spInfo.saleprice;
			} else {
				price = 0.06;
			}
			if ($("#chargeType").val() == 'domiestic') {
				var input = $("#messageNumInput").val();
				$("#domiesticPrice").html(price + "元/条");
			} else {
				var input = $("#inputVoice").val();
				$("#voicePrice").html(price + "元/条");
			}
			if (input) {
				totalPrice(price, content);
			}
		});
	}
	$('#myTab a').click(function(e) {
		getSpList($(this).attr("service-type"));
		$("#chargeType").attr("value", $(this).attr("target-type"));
		$(this).tab('show');// 显示当前选中的链接及关联的content
	})

	$('#messageNumInput').blur(function() {
		var value = $("#messageNumInput").val();
		$("#messageNumInput").val(value.replace(/[^\d]/g, ''));
	});
	$('#messageNumInput').bind('input propertychange', function() {
		totalPrice(price, "priceHtml");
	});
	$('#inputVoice').blur(function() {
		var value = $("#inputVoice").val();
		$("#inputVoice").val(value.replace(/[^\d]/g, ''));
	});
	$('#inputVoice').bind('input propertychange', function() {
		totalPrice(price, "voicePriceHtml");
	});
	$('#inputInter').bind('input propertychange', function() {
		if ($("#inputInter").val().length < 1) {
			$("#interDiscount").html("无折扣");
			$("#interPriceHtml").html("");
			return;
		}
		var fixPrice = fixInterDisCount($("#inputInter").val());
		$("#interPriceHtml").html(fixPrice + "元");
	});
});
function fixInterDisCount(price) {
	if (price > 10000 && price <= 20000) {
		price = (price * 0.95).toFixed(0);
		$("#interDiscount").html("9.5折");
	} else if (price > 20000 && price <= 50000) {
		price = (price * 0.81).toFixed(0);
		$("#interDiscount").html("8.1折");
	} else if (price > 50000) {
		price = (price * 0.81).toFixed(0);
		$("#interDiscount").html("8.1折");
	} else {
		$("#interDiscount").html("无折扣");
	}
	return price;
}
function totalPrice(price, content) {
	if ($("#chargeType").val() == 'domiestic') {
		var sum = $("#messageNumInput").val();
	} else {
		var sum = $("#inputVoice").val();
	}
	if (sum) {
		if ($("#spInfoList").val() != 0) {
			var fixprice = checkDisCount(price, sum);
			if ($("#chargeType").val() == 'domiestic') {
				$("#domiesticPrice").html(fixprice + "元/条");
			} else {
				$("#voicePrice").html(fixprice + "元/条");
			}
			$("#" + content).html(
					sum + "/" + fixprice + "=" + (sum / fixprice).toFixed(0)
							+ "条");
		} else {
			$("#" + content).html("");
		}
	} else {
		$("#" + content).html("");
	}
};
function changeMinCharge(data) {
	if (data == 2) {
		$("span[name='minCharge']").show();
	} else {
		$("span[name='minCharge']").hide();
	}

}
function checkDisCount(price, sum) {
	if (price == 0.06) {
		if (10000 < sum && sum <= 20000) {
			price = 0.058
		} else if (20001 <= sum && sum <= 30000) {
			price = 0.055
		} else if (sum > 30000) {
			price = 0.05
		} else {
			price = 0.06;
		}
	}
	return price;
}
$("#couponsInput").blur(
		function() {
			var conpons = $("#couponsInput").val();
			if (conpons) {
				$.get("chargeRecord/findConponsNum", "conpons=" + conpons,
						function(data) {
							if (data.num) {
								$("#couponsNum").html(data.num);
							}
						});
			}
		});
$.validator.addMethod("isPositiveInteger", function(value, element, params) {
	if (value.replace(/(^s*)|(s*$)/g, "").length != 0) {
		var r = /^[0-9]*[1-9][0-9]*$/;
		return r.test(value);
	} else {
		return true;
	}
}, "金额为正整数");
$.validator.addMethod("isAlipay", function(value, element, params) {
	if ($('input:radio[name="accountChargeType"]:checked').val() == 2) {
		if (value < 10000) {
			return false;
		} else {
			return true;
		}
	} else {
		return true;
	}
}, "");
function getSpList(serviceType) {
	$.get("chargeRecord/findSpInfoList?serviceType=" + serviceType, function(
			data) {
		$("#spInfoList").empty();
		$("#spInfoList").append("<option value='0'>请选择应用</option>");
		$.each(data.info, function(index, value) {
			$("#spInfoList").append(
					"<option value='" + value.spid + "'>" + value.sp_name
							+ "</option>");
		})

	})
}
$("#payForm").validate(
		{
			rules : {
				messageNumInput : {
					maxlength : 10,
					isPositiveInteger : true,
					isAlipay : true
				},
				spId : {
					min : 1
				},
				inputVoice : {
					maxlength : 10,
					isPositiveInteger : true,
					isAlipay : true
				},
				inputInter : {
					maxlength : 10,
					isPositiveInteger : true,
					isAlipay : true
				}

			},
			messages : {
				messageNumInput : {
					maxlength : '短信条数最大长度为10',
					isPositiveInteger : "国内短信金额为正整数"

				},
				spId : {
					min : "应用名称必须选择"
				},
				inputVoice : {
					maxlength : '短信条数最大长度为10',
					isPositiveInteger : "语音短信金额为正整数"

				},
				inputInter : {
					maxlength : '短信条数最大长度为10',
					isPositiveInteger : "国际短信金额为正整数"
				}
			},
			submitHandler : function(form) {
				if ($("#chargeType").val() == "voice") {
					$("#testMessageModal").modal("show");
					return false;
				}
				if ($("input[name='accountChargeType']:checked").val() == 1) {
					$.post("chargeRecord/chargeAccountMth?"
							+ $("#payForm").serialize(), function(data) {
						if (data) {
							if (data.statusCode == 205) {
								layer.msg("账户余额不足无法充值");
							} else if (data.statusCode == 200) {
								layer.msg("充值成功");
								if (data.balance != null) {
									$("#leftoverNum").html("");
									$("#leftoverNum").html(data.balance / 100);
								}
							} else {
								layer.msg("系统错误");
							}
						}
					})
				} else {
					var info = $("#payForm").serialize();
					window.open("chargeRecord/payMth?" + info);
				}

			}
		});

function outLineCharge() {
	$("#zfbCharegeArea").hide();
	$("#outLineCharegeArea").show();
}