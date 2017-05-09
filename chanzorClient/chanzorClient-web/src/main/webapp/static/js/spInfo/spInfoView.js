(function($) {
	$.pageInfo = {
		init : function() {
			modelInfo.init();
			timePicker.init();
			pageEvent.initButton();
			pageEvent.initTable("spInfo/mainLoad?spId=" + $("#spid").val(),
					"daySendInfo");
			pageEvent.initTable(
					"spInfo/mainMonthLoad?spId=" + $("#spid").val(),
					"monthSendInfo");
		}
	}
	var modelInfo = {
		init : function() {
			var m = $('#modaljsp').clone(true);
			$('#modaljsp').remove();
			$('#modalcontent').html("");
			$('#modalcontent').append(m);
		}
	}
	var timePicker = {
		init : function() {
			$('#dayTimeStart').datetimepicker({
				format : 'Y-m-d',
				timepicker : false,
				allowBlank : true,
				onChangeDateTime : function() {
					checkStartTimeMonth("dayTimeStart", "All");
				}
			});
			$('#dayTimeEnd').datetimepicker({
				format : 'Y-m-d',
				timepicker : false,
				allowBlank : true,
				onChangeDateTime : function() {
					checkEndTimeMonth("dayTimeEnd", "queryEndTime");
				}
			});
			$('#monthTimeStart').datetimepicker({
				format : 'Y-m',
				timepicker : false,
				allowBlank : true,
				onChangeDateTime : function() {
					checkStartTimeMonth("monthTimeStart", "Month");
				}
			});
			$('#monthTimeEnd').datetimepicker({
				format : 'Y-m',
				timepicker : false,
				allowBlank : true,
				onChangeDateTime : function() {
					checkEndTimeMonth("monthTimeEnd", "queryEndTime");
				}
			});
		}
	}

	var pageEvent = {
		initButton : function() {
			$("#daySwitchTable").click(
					function() {
						$("#daySwitchTable").css("backgroundImage",
								'url(../img/ph1.png)');
						$("#daySwitchChart").css("backgroundImage",
								'url(../img/ph2.png)');
						$("#dayTableInfo").show();
						$("#right_top5_bottom").hide();
						pageEvent.initTable("spInfo/mainLoad?"
								+ $("#searchDayForm").serialize(),
								"daySendInfo");
					});
			$("#daySwitchChart").click(
					function() {
						$("#daySwitchTable").css("backgroundImage",
								'url(../img/ph3.png)');
						$("#daySwitchChart").css("backgroundImage",
								'url(../img/ph4.png)');
						$("#dayTableInfo").hide();
						$("#right_top5_bottom").show();
						pageEvent.initChart("right_top5_bottom",
								"spInfo/mainLoad?"
										+ $("#searchDayForm").serialize(),
								"daySendInfo");
					});
			$("#monthSwitchTable").click(
					function() {
						$("#monthSwitchTable").css("backgroundImage",
								'url(../img/ph1.png)');
						$("#monthSwitchChart").css("backgroundImage",
								'url(../img/ph2.png)');
						$("#monthTableInfo").show();
						$("#right_top4_bottom").hide();
						pageEvent.initTable("spInfo/mainMonthLoad?"
								+ $("#searchMonthForm").serialize(),
								"monthSendInfo");
					});
			$("#monthSwitchChart").click(
					function() {
						$("#monthSwitchTable").css("backgroundImage",
								'url(../img/ph3.png)');
						$("#monthSwitchChart").css("backgroundImage",
								'url(../img/ph4.png)');
						$("#monthTableInfo").hide();
						$("#monthChartInfo").show();
						$("#right_top4_bottom").show();
						pageEvent.initChart("right_top4_bottom",
								"spInfo/mainMonthLoad?"
										+ $("#searchMonthForm").serialize(),
								"month");
					})
			$("#searchDay").click(
					function() {
						if ($("#dayTableInfo").css("display") == "none") {
							pageEvent.initChart("right_top5_bottom",
									"spInfo/mainLoad?"
											+ $("#searchDayForm").serialize(),
									"day");
						} else {
							pageEvent.initTable("spInfo/mainLoad?"
									+ $("#searchDayForm").serialize(),
									"daySendInfo");
						}
					});
			$("#searchMonth").click(
					function() {
						if ($("#monthTableInfo").css("display") == "none") {
							pageEvent
									.initChart("right_top4_bottom",
											"spInfo/mainMonthLoad?"
													+ $("#searchMonthForm")
															.serialize(),
											"month");
						} else {
							pageEvent.initTable("spInfo/mainMonthLoad?"
									+ $("#searchMonthForm").serialize(),
									"monthSendInfo");
						}

					})
		},
		initTable : function(url, id) {
			$.get(url, function(data) {
				$("#" + id + " tbody tr[id='data']").html("");
				if (data.length > 0) {
					$.each(data, function(i, v) {
						$("#" + id + " tbody").append(
								"<tr id='data'><td>" + v.submit_date + "</td>"
										+ "<td>" + v.submit_count + "</td>"
										+ "<td>" + v.sended_count + "</td>"
										+ "<td>" + v.success_count + "</td>"
										+ "<td>" + v.fail_count + "</td>"
										+ "<td>" + v.unknown_count
										+ "</td></tr>");
					})
				} else {
					$("#" + id + " tbody").append(
							"<tr id='data'><td colspan='6'>没有找到记录</td></tr>");
				}

			});
		},
		initChart : function(id, url, type) {
			var dayInfo = new Array();
			var dataInfo = new Array();
			var submitCount = new Array();
			var sendedCount = new Array();
			var success_count = new Array();
			var failCount = new Array();
			var unknownCount = new Array();
			$.ajax({
				type : "get",
				url : url,
				async : false,
				success : function(data) {
					$.each(data, function(i, v) {
						dayInfo.push(v.submit_date);
						submitCount.push(v.submit_count);
						sendedCount.push(v.sended_count);
						success_count.push(v.success_count);
						failCount.push(v.fail_count);
						unknownCount.push(v.unknown_count);
					});
				}
			});
			var submitInfo = {
				name : '提交数量',
				data : submitCount,
			};
			var sendInfo = {
				name : '下发数量',
				data : sendedCount,
			};
			var successInfo = {
				name : '成功',
				data : success_count,
			};
			var failInfo = {
				name : '失败',
				data : failCount,
			};
			var unknowInfo = {
				name : '未知',
				data : unknownCount,
			};
			dataInfo.push(submitInfo);
			dataInfo.push(sendInfo);
			dataInfo.push(successInfo);
			dataInfo.push(failInfo);
			dataInfo.push(unknowInfo);
			if (type == "month") {
				pageEvent.paintingMonthChart(id, dayInfo, dataInfo);

			} else {
				pageEvent.paintingChart(id, dayInfo, dataInfo);

			}
		},
		paintingChart : function(id, dayInfo, dataInfo) {
			var serie = [];
			$.each(dataInfo, function(i, v) {
				var item = {
					name : v.name,
					type : 'line',
					data : v.data
				}
				serie.push(item);
			})
			var myChart = echarts.init(document.getElementById(id));
			var option = {

				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ '提交数量', '下发数量', '成功', '失败', '未知' ]
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
					data : dayInfo
				},
				yAxis : {
					type : 'value'
				},
				series : serie
			};
			myChart.setOption(option);
		},
		paintingMonthChart : function(id, dayInfo, dataInfo) {
			var serie = [];
			$.each(dataInfo, function(i, v) {
				var item = {
					name : v.name,
					type : 'line',
					data : v.data
				}
				serie.push(item);
			})
			var myChart = echarts.init(document.getElementById(id));
			var option = {

				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ '提交数量', '下发数量', '成功', '失败', '未知' ]
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
					data : dayInfo
				},
				yAxis : {
					type : 'value'
				},
				series : serie
			};
			myChart.setOption(option);
		}

	}

})(jQuery)

$(function() {
	$.pageInfo.init();
	$("#spInfoConfigButton").find("li").hover(function() {
		$(this).css("background-color", "gainsboro");
	}, function() {
		$(this).css("background-color", "white");
	});
	$("#uploadRelationBtn").click(function() {
		$("#sp_relation_prove").click();
	})
	$("#uploadDomainBtn").click(function() {
		$("#sp_domain_auth").click();
	})
	$("#uploadSignatureBtn").click(function() {
		$("#sp_signature_auth").click();
	})
});
function redirectpay(id) {
	goPage("chargeRecord/directPaySpInfo?spId=" + id,
			'static/js/spCharge/paySpInfo.js');
};
function spInfoView(id) {
	goPage('spInfo/findspInfo.html?spid=' + id, 'static/js/spInfo/index.js');
};
function onlineSpInfo(id) {
	$.get("spInfo/findSpInfoCompanyStatus?spId=" + id, function(data) {
		if (data.status == 0) {
			layer.msg("请先提交企业信息");
		} else if (data.status == 1) {
			layer.msg("企业信息正在审核中");
		} else if (data.status == 2) {
			if(data.spInfo.sp_service_type==2){
				$.post("spInfo/updateInterSpInfo?spid="+id,function(data){
					if(data.status==200){
						layer.msg("申请上线成功。");
						$("#changeAppOnLineMth").removeAttr("onclick");
						$("#changeUpdSpInfoMth").removeAttr("onclick");
						$("#changeDelSpInfoMth").removeAttr("onclick");
						$("#changeAppOnlineImg").css("background-image","url(static/img/mousegray.png");
						$("#changeUpdSpInfoImg").css("background-image","url(static/img/wrenchgray.png");
						$("#changeDelSpInfoImg").css("background-image","url(static/img/bintgray.png");
						$("#appOnlineStatus").html("申请上线中");
					}else{
						layer.msg("操作失败，请重试。")
					}
				})
			}else{
				$("#uploadAddressForm").find("#spid").val(id);
				$("#signature").val("【】");
				$("#signature").html("【】");
				$("#onlineModel").modal('show');
			}
		} else if (data.status == 3) {
			layer.msg("企业信息已被驳回，请重新提交");
		}
	});
}
jQuery.validator.addMethod("checkSignature", function(value, element) {
	if (value != '') {
		if (!/^(【).+(】$)$/.test(value)) {
			return false;
		}
	};
	return true;
}, "请输入正确的签名");
jQuery.validator.addMethod("signatureMinLength", function(value, element) {
	value =  value.match(/([^\【\]]+)(?=\】)/g);
	if(value[0].length<3){
		return false;
	}
	return true;
}, "签名最小长度为3");
jQuery.validator.addMethod("signatureMaxLength", function(value, element) {
	value =  value.match(/([^\【\]]+)(?=\】)/g);
	if(value[0].length>8){
		return false;
	}
	return true;
}, "签名最大长度为8");
jQuery.validator.addMethod("signatureSpace", function(value, element) {
	value =  value.match(/([^\【\]]+)(?=\】)/g);
	if(value[0]!=$.trim(value[0])){
		return false;
	}
	return true;
}, "签名前后不能有空格");

$("#uploadAddressForm").validate({
	onclick : false,
	onkeyup : false,
	rules : {
		signature : {
			required : true,
			maxlength : 30,
			checkSignature : true,
			signatureSpace:true,
			signatureMinLength:true,
			signatureMaxLength:true
		},
		file : {
			isFileUpLoad : true
		},
		sp_signature_type : {
			min : 2
		}
	},
	messages : {
		signature : {
			required : "签名必须填写",
			maxlength : '签名最大长度为20',
			checkSignature : "签名必须用【】包围,例如【畅卓科技】"
		},
		file : {
			isFileUpLoad : "请上传文件"
		},
		sp_signature_type : {
			min : "请选择签名证明材料"
		}
	},
	submitHandler : function() {
		$.ajax({
			type : 'POST',
			url : "spInfo/updateSpInfo",
			data : $("#uploadAddressForm").serialize(),
			success : function(data) {
				if (data.status == "200") {
					layer.msg("提交成功");
					$("#changeAppOnLineMth").removeAttr("onclick");
					$("#changeUpdSpInfoMth").removeAttr("onclick");
					$("#changeDelSpInfoMth").removeAttr("onclick");
					$("#changeAppOnlineImg").css("background-image","url(static/img/mousegray.png");
					$("#changeUpdSpInfoImg").css("background-image","url(static/img/wrenchgray.png");
					$("#changeDelSpInfoImg").css("background-image","url(static/img/bintgray.png");
					$("#appOnlineStatus").html("申请上线中");
					$("#onlineModel").modal("hide");
				}
			}
		})
	}
});
function previewImage(obj) {
	$
			.ajaxFileUpload({
				url : 'spInfo/updateAppImage',// 用于文件上传的服务器端请求地址
				secureuri : false,// 一般设置为false
				fileElementId : $(obj).attr('id'),// 文件上传控件的id属性
				dataType : 'text',// 返回值类型 一般设置为json
				type : 'post',
				data : {
					filename : $(obj).attr('filename')
				},
				success : function(data, status) // 服务器成功响应处理函数
				{
					if (data == '') {
						layer.msg('文件错误,请重新选择', {
							icon : 2
						});
						$("#APPSIGNCERT_IMAGE").val("");
						return;
					} else {
						var MAXWIDTH = 260;
						var MAXHEIGHT = 180;
						var div = document.getElementById('preview');
						if (obj.files && obj.files[0]) {
							div.innerHTML = '<img id=imghead>';
							var img = document.getElementById('imghead');
							img.onload = function() {
								var rect = clacImgZoomParam(MAXWIDTH,
										MAXHEIGHT, img.offsetWidth,
										img.offsetHeight);
								img.width = rect.width;
								img.height = rect.height;
								// img.style.marginLeft = rect.left+'px';
								// img.style.marginTop = rect.top + 'px';
							}
							var reader = new FileReader();
							reader.onload = function(evt) {
								img.src = evt.target.result;
							}
							reader.readAsDataURL(obj.files[0]);
						} else // 兼容IE
						{
							var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
							obj.select();
							var src = document.selection.createRange().text;
							div.innerHTML = '<img id=imghead>';
							var img = document.getElementById('imghead');
							img.filters
									.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
							var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
									img.offsetWidth, img.offsetHeight);
							status = ('rect:' + rect.top + ',' + rect.left
									+ ',' + rect.width + ',' + rect.height);
							div.innerHTML = "<div id=divhead style='width:"
									+ rect.width + "px;height:" + rect.height
									+ "px;" + sFilter + src + "\"'></div>";
						}
						$("#preview").show();
					}
					$('#sp_signature_img').val(data);
				},
				error : function(data, status, e)// 服务器响应失败处理函数
				{
					layer.msg(e);
				}
			})
}
function clacImgZoomParam(maxWidth, maxHeight, width, height) {
	var param = {
		top : 0,
		left : 0,
		width : width,
		height : height
	};
	if (width > maxWidth || height > maxHeight) {
		rateWidth = width / maxWidth;
		rateHeight = height / maxHeight;

		if (rateWidth > rateHeight) {
			param.width = maxWidth;
			param.height = Math.round(height / rateWidth);
		} else {
			param.width = Math.round(width / rateHeight);
			param.height = maxHeight;
		}
	}

	param.left = Math.round((maxWidth - param.width) / 2);
	param.top = Math.round((maxHeight - param.height) / 2);
	return param;
}
function editSpInfo(id) {
	if (id && typeof (id) != "undefined") {
		$.get("spInfo/showSpInfoView?spId=" + id, function(data) {
			$("#insSpInfo").find("#sp_name").val(data.spInfo.sp_name);
			$("#insSpInfo").find("#sp_industry").val(data.spInfo.sp_industry);
			$("#insSpInfo").find("#sp_website").val(data.spInfo.sp_website);
			$("#insSpInfo").find("#sp_desc").val(data.spInfo.sp_desc);
			$("#insSpInfo").find("#spid").val(data.spInfo.spid);
			$("#insSpInfo").find("#sp_desc").val(data.spInfo.sp_desc);
			$("#insSpInfo").find("#sp_service_type").val(
					data.spInfo.sp_service_type);
			if (data.spInfo.sp_app_type) {
				var appInfo = data.spInfo.sp_app_type.split(",");
				$.each(appInfo, function(index, data) {
					$("#insSpInfo").find(
							"input[type='checkbox'][value='" + data + "']")
							.prop("checked", "checked");
					$(
							"#"
									+ $("#insSpInfo").find(
											"input[type='checkbox'][value='"
													+ data + "']").attr(
											"target-input")).show();
				})
			}
			$("#insSpInfo").find("#app_input").val(data.spInfo.sp_app_info);
			$("#insSpInfo").find("#weixin_input").val(
					data.spInfo.sp_weixin_info);
			$("#insSpInfo").find("#sp_other_info").val(
					data.spInfo.sp_other_info);
			$("#insSpInfo").find("#sp_service_type").attr("disabled", 'true');
			if (data.spInfo.sp_app_type == 2) {
				$("#app_input").val(data.spInfo.sp_app_info);
				$("#app").show();
			} else if (data.spInfo.sp_app_type == 3) {
				$("#weixin_input").val(data.spInfo.sp_app_info);
				$("#weixin").show();
			}
			$("#spInfoModelTitle").html("修改应用");
			$("#editSpInfoButton").html("保存");
		});
	}
	$("#addSpInfo").modal("show");
	$("#insSpInfo").validate(
			{
				rules : {
					sp_name : {
						required : true,
						maxlength : 20,
					},
					sp_app_type_ids : {
						required : true
					},
					sp_industry : {
						min : 1
					},
					sp_website : {
						required : true,
						maxlength : 50
					},
					sp_app_info : {
						maxlength : 300,
						required : true
					},
					sp_desc : {
						maxlength : 500
					},
					sp_service_type : {
						min : 1
					},

				},
				messages : {
					sp_name : {
						required : "应用名称必填",
						maxlength : '应用名称最大长度为20',
					},
					sp_app_type_ids : {
						min : "应用类型必须选择"
					},
					sp_industry : {
						min : "应用行业必须选择"
					},
					sp_website : {
						required : "网站必须填写",
						maxlength : "网站地址最大长度为50"
					},
					sp_app_info : {
						maxlength : "长度最大为300",
						required : "App名称必须填写"
					},
					sp_desc : {
						maxlength : "应用描述长度最大为500"
					},
					sp_service_type : {
						min : "业务类型必须填写"
					},
					sp_weixin_info : {
						required : '微信公众号必须填写'
					},
					sp_other_info : {
						required : '其他信息必须填写'
					}
				},
				submitHandler : function() {
					var chk_value = '';// 定义一个数组
					$('input[name="sp_app_type_ids"]:checked').each(function() {// 遍历每一个名字为interest的复选框，其中选中的执行函数
						chk_value += $(this).val() + ",";// 将选中的值添加到数组chk_value中
					});
					$.ajax({
						type : "post",
						url : "spInfo/addSpInfoMth",
						data : $("#insSpInfo").find(
								"input[type='hidden'], :input:not(:hidden)")
								.not("input[name='sp_app_type_ids']")
								.serialize()
								+ "&sp_app_type=" + chk_value,
						success : function(obj) {
							if (obj == '200') {
								layer.msg("保存成功");
								$('#example').dataTable().fnDraw(false);
								$("#addSpInfo").modal("hide");
							} else if (obj == "500") {
								$("#addSpInfo").modal("hide");
								layer.msg("应用最多为10个，添加失败");
							} else {
								$("#addSpInfo").modal("hide");
								layer.msg("添加失败");
							}
						}

					});
				}
			});
};
function delSpInfo(id, status) {
	layer.confirm('您确定删除此应用?<br>删除后此应用的所有信息将被删除，请您谨慎操作', {
		icon : 0,
		title : '应用删除',
		btn : [ '确定', '取消' ]
	// area : [ '300px', 'auto' ]
	}, function(index) {
		if (status == "3") {
			layer.msg('申请上线中的应用不能删除');
			return;
		}
		$.post("spInfo/updateDelSpInfo?spid=" + id, function(result) {
			if (result == '200') {
				layer.close(index);
				goPage('spInfo/mySpinfo.html', 'static/js/spInfo/mySpinfo.js')
			}
		});
	});
};

function findTemplateContent(select, status, signature) {
	if(status==2){
		if($(select).val() != 0 ){
			$("#testMessage").attr("readOnly",true);
		}else{
			$("#testMessage").attr("readOnly",false);
		}
	}
		$("#testMessage").val('');
		$("#testMessage").val($(select).val() == 0 ? signature : $(select).val() + signature);
}
function reminderBalance(service_type, id) {
	switch (service_type) {
	case 1:
		$("#serviceBalance").html("国内短信余额小于：");
		$("#unit").html("条");
		break;
	case 2:
		$("#serviceBalance").html("国际短信余额小于：");
		$("#unit").html("元");
		break;
	case 3:
		$("#serviceBalance").html("语音短信余额小于：");
		$("#unit").html("条");
		break;
	default:
		$("#serviceBalance").html("短信余额小于：");
	}
	$.get("spInfo/getBalanceReminderBySpId?spId=" + id, function(data) {
		if (data) {
			$("#balanceId").val(data.id);
			$("#balance").val(data.balance);
			$("#reminderPhone").val(data.reminderPhone);
			if (service_type == 2) {
				$("#balance").val(data.balance / 100);
			}
		}
		$("#balanceSpId").val(id);
		$("#balanceReminde").modal("show");
	})

}
$("#balanceRemindeForm").validate(
		{
			rules : {
				balance : {
					required : true,
				},
				reminderPhone : {
					required : true
				},

			},
			messages : {
				balance : {
					required : "余额不能为空",
				},
				reminderPhone : {
					required : "手机号不能为空",

				}
			},
			submitHandler : function() {
				$.post("spInfo/insBalanceReminder?"
						+ $("#balanceRemindeForm").serialize(), function(data) {
					if (data == 'success') {
						layer.msg("保存成功");
						$("#balanceReminde").modal("hide");
					} else {
						layer.msg("保存失败");
					}

				})

			}
		});

$("#testMessageForm").validate(
		{
			rules : {
				testPhone : {
					required : true,
				},
				testMessage : {
					required : true
				}

			},
			messages : {
				reminderPhone : {
					required : "手机号不能为空",
				},
				testMessage : {
					required : "短信内容不能为空",
				}
			},
			submitHandler : function() {
				$.post("spInfo/sendTestMessageBySpInfo?"
						+ $("#testMessageForm").serialize(), function(data) {
					if (data.returnstatus == 0) {
						$("#testMessageModal").modal("hide");
					}
					layer.msg(data.desc);
				})

			}
		});

function showPassword(password) {
	if ($("#passwordHtml").attr("data-type") == 'hide') {
		$("#passwordHtml").html(password);
		$("#passwordHtml").attr("data-type", "show");
	} else {
		$("#passwordHtml").html("************");
		$("#passwordHtml").attr("data-type", "hide");

	}
}
function showCompanyPhone() {
	layer.alert("热线电话：400-793-0000", {
		title : '客服帮助'
	});
}
function changePass(id) {
	$("#changePasswordSpId").val(id);
	$("#changePasswordModal").modal("show");
}
jQuery.validator.addMethod("checkSpace", function(value, element) {
	var space=/\s/;
	return !space.test(value);
}, $.validator.format("应用密码不能含有空格"));
$("#changePasswordForm").validate(
		{
			rules : {
				password : {
					required : true,
					checkSpace:true
				},
				password1 : {
					required : true,
					equalTo : "#password"
				}

			},
			messages : {
				password : {
					required : "密码不能为空",
				},
				password1 : {
					required : "再次确认密码不能为空",
					equalTo : "两次密码必须一致"
				}
			},
			submitHandler : function() {
				$.post("spInfo/changeSpInfoPassWord?"
						+ $("#changePasswordForm").serialize(), function(data) {
					if (data.returnstatus == 'success') {
						layer.msg("修改成功");
						$("#passwordimg").attr("onclick",
								"showPassword(" + data.fixPassword + ")");
						$("#changePasswordModal").modal("hide");
					} else {
						layer.msg("修改失败，请重试");
					}

				})

			}
		});

function updateIP(id,ip){
	$("#updateIPSpId").val(id);
	$("#updateIP").html(ip.replace(/,/g,'\n'));
	$("#updateIPModel").modal("show");
};

function updateIPsub(){
	var spid =$("#updateIPSpId").val();
	var newip=$("#updateIP").val();
	newip = newip.replace(/\n/g, ',');
	$.ajax({
		url : 'spInfo/updateSpInfoIP',
		type : 'post',
		data : {spId:spid, ip:newip},
		success : function(data) {
			if (data.returnstatus == 'success') {
				layer.msg("修改成功");
				if(newip == '' ){
					$("#appip").html("---------");
				}else if(newip.length > 15){
					$("#appip").html(newip.substring(0,15));
				}else{
					$("#appip").html(newip);
				}
				$("#changeIP").attr("onclick","updateIP('"+spid+"','"+newip+"')");
				$("#updateIPModel").modal("hide");
			} else {
				layer.msg("修改失败，请重试");
			}
		}
	});
	
};

function downloadInterFaceDoc(type) {
	switch (type) {
	case 1:
		window.location.href = "static/word/Domesticmessageinterface.docx";
		break;
	case 2:
		window.location.href = "static/word/Internationalmessageinterface.docx";
		break;
	case 3:
		window.location.href = "static/word/语音接入规范(http)base.doc";
		break;
	}
}
function sendTestMessage(id,status,service_type) {
	$("#spid").val(id);
	if(status==22||status==0||status==3){
		$("#templateName").empty();
		if(service_type==1){
			$("#templateName").append("<option value='【畅卓科技】您的短信验证码是1234。如非本人操作，请忽略此短信。本短信免费。'>验证码模版</option>"); 
			$("#templateName").append("<option value='【畅卓科技】您好，您预约的订单，已被服务商接单，下载APP（http://chanzor.com）查看更多信息'>会员通知模版</option>"); 
			$("#testMessage").attr("readOnly",true);
			$("#testMessage").html("【畅卓科技】您的短信验证码是1234。如非本人操作，请忽略此短信。本短信免费。");
			$("#testMessageModal").modal("show");
		} else if(service_type==2){
			$("#templateName").append("<option value='【chanzor】Dear user, your verification code is @.'>验证码模版</option>"); 
			$("#templateName").append("<option value='【chanzor】Dear user, your account login IP changes, please confirm your account security issues.'>通知模版</option>"); 	
			$("#testMessage").attr("readOnly",true);
			$("#testMessage").html("【chanzor】Dear user, your verification code is @.");
			$("#testMessageModal").modal("show");
		}
	}else if(status==4){
		layer.msg("应用已被冻结无法发送短信");
	}else {
		$("#templateName").empty();
		if(service_type==1){
			$("#templateName").append("<option value='0'>请选择</option>");
			$("#templateName").append("<option value='您的短信验证码是1234。如非本人操作，请忽略此短信。本短信免费。'>验证码模版</option>"); 
			$("#templateName").append("<option value='您好，您预约的订单，已被服务商接单，下载APP（http://chanzor.com）查看更多信息'>会员通知模版</option>"); 
			$("#testMessageModal").modal("show");
		} else if(service_type==2){
			$("#templateName").append("<option value='0'>请选择</option>");
			$("#templateName").append("<option value='【chanzor】Dear user, your verification code is @.'>验证码模版</option>"); 
			$("#templateName").append("<option value='【chanzor】Dear user, your account login IP changes, please confirm your account security issues.'>通知模版</option>"); 	
			$("#testMessageModal").modal("show");
		}
	}
}
function uploadAuth(obj, btn) {
	$
			.ajaxFileUpload({
				url : 'spInfo/uploadAuth',// 用于文件上传的服务器端请求地址
				secureuri : false,// 一般设置为false
				fileElementId : $(obj).attr('id'),// 文件上传控件的id属性
				dataType : 'text',// 返回值类型 一般设置为json
				type : 'post',
				data : {
					filename : $(obj).attr('filename')
				},
				success : function(data, status) // 服务器成功响应处理函数
				{
					if (data.src == '') {
						layer.msg('文件错误,请重新选择', {
							icon : 2
						});
						$(obj).val("");
						return;
					} else {
						layer.msg("上传成功!");
						$("#uploadAddressForm").find(
								"input[name='" + $(obj).attr("id") + "']").val(
								data);
						$("#" + btn).parent().parent().parent().find(
								"div[id='templateData']").remove();
						$("#" + btn)
								.parent()
								.parent()
								.after(
										"<div id='templateData' class='col-md-offset-3 col-md-9' style='text-align:left'>"
												+ $(obj)
														.val()
														.replace(
																/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,
																"$1")
												+ "."
												+ $(obj).val().replace(/.+\./,
														"") + "</div>");
						// return data;
					}
				},
				error : function(data, status, e)// 服务器响应失败处理函数
				{
					layer.msg(e);
				}
			})
}