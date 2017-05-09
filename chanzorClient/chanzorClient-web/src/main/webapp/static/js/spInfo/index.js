var dataTable = {
	init : function(data) {
		var url = "spInfo/spInfoList";
		if (data) {
			url = "spInfo/spInfoList?spid=" + data;
		}
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
							bAutoWidth : true,
							ajax : {
								type : "post",
								url : url,
								dataSrc : "spInfo",
								data : data,
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									if (XMLHttpRequest.status == 404) {
										$("#example").html("数据库服务器通讯失败，请稍后重试！");
									}
								}

							},
							'fnServerParams' : function(aoData) {
								aoData.sp_name = $("#sp_name").val();
								aoData.sp_service_type = $("#sp_service_type")
										.val();
								aoData.sp_through_status = $(
										"#sp_through_status").val();
							},
							serverSide : true,
							columns : [
									{
										"data" : "sp_service_type",
										render : function(data, type, row) {
											switch (data) {
											case 1:
												return "国内短信";
											case 2:
												return "国际短信";
											case 3:
												return "语音短信";
											default:
												return "";
											}

										}
									},
									{
										"data" : "username"
									},
									{
										"data" : "sp_name",
										render : function(data, type, row) {
											return "<a href='javascript:;' style='color:#009ACB' onclick='findSpInfo("
													+ row.spid
													+ ")'>"
													+ data
													+ "</a>";
										}
									},
									{
										"data" : "leftover_num",
										render : function(data, type, row) {
											switch (row.sp_service_type) {
											case 1:
												return data + "条";
											case 2:
												return (data / 100).toFixed(2)
														+ "元";
											case 3:
												return data + "条";
											default:
												return "";
											}
										}
									},
									{
										"data" : "signature"
									},
									{
										"data" : "sp_through_status",
										render : function(data, type, row) {
											switch (data) {
											case 0:
												return "未上线";
											case 2:
												return "上线";
											case 3:
												return "申请上线中";
											case 22:
												// row.spRejectReason
												return "<a href='javascript:;' style='color:red' onclick=showRejectReason('"
														+ row.spRejectReason
														+ "')>未通过</a>";
											case 4:
												return "冻结";
											default:
												return "";
											}
										}
									}, {
										"data" : "sp_through_status",
										render : function(data, type, row) {
											switch (data) {
											case 4:
												return "冻结";
											default:
												return "正常";
											}
										}
									}, {
										"data" : null
									} ],
							"language" : {
								"lengthMenu" : "_MENU_条记录每页",
								"zeroRecords" : "没有找到记录",
								"info" : "总共_PAGES_页，显示第_START_到第_END_条，筛选之后得到_TOTAL_条，初始_MAX_条 ",
								"infoEmpty" : "无记录",
								"infoFiltered" : "(从 _MAX_条记录过滤)",
								"processing" : "处理中...",
								"paginate" : {
									"previous" : "上一页",
									"next" : "下一页"
								}
							},
							columnDefs : [ {
								targets : 7,
								render : function(a, b, c, d) {
									if (a.sp_through_status == 4) {
										return "";
									}
									var html = "";
									// html = "<button class='mb-sm btn
									// btn-primary' onclick='redirectpay("
									// + a.spid + ")'>充值</button>&nbsp;";
									if (a.sp_service_type == 3) {
										html = "<button class='mb-sm btn btn-primary'  onclick='redirectMsg()'>充值</button>&nbsp;";
									} else {
										html = "<button class='mb-sm btn btn-primary'  onclick='redirectpay("
												+ a.spid
												+ ")'>充值</button>&nbsp;";
									}
									var online = "<button class='mb-sm btn btn-danger' onclick='online("
											+ a.spid
											+ ")'>申请上线</button></button>&nbsp;<button class='mb-sm btn btn-primary' onclick='editSpInfo("
											+ a.spid + ")'>修改</button>&nbsp;";
									var del = "<checkLand:check type='sessionUser'><button class='mb-sm btn btn-danger' onclick='delSpInfo("
											+ a.spid
											+ ","
											+ a.sp_through_status
											+ ")'>删除</button></checkLand:check>";
									if (a.sp_through_status == 0
											|| a.sp_through_status == 22) {
										html += online;
									}
									if (LandingType == 'SpInfo') {
										return html;
									}
									if (a.sp_through_status != 3) {

										html += del;
									}
									if (subAccountLogin == "subAccountLogin") {
										html = "";
									}

									return html;
								}
							}

							],
						});
	}
};
$(function() {
	$("#spInfoType").find("input[type='checkbox']").click(function() {
		if (this.checked) {
			$("#" + $(this).attr("target-input")).show();
		} else {
			$("#" + $(this).attr("target-input")).hide();
		}
	})
	var a = $("#showMainInfo").val();
	if (a && a != null) {
		var data = a;
		dataTable.init(data);
	} else {
		if ($.fn.dataTable.isDataTable('#example')){
			$('#example').dataTable().fnDraw(false);
		}else{
			dataTable.init();
		}
	}
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
jQuery.validator.addMethod("checkSignature", function(value, element) {
	if (value != '') {
		if (!/^(【).+(】$)$/.test(value)) {
			return false;
		}
	}
	;
	return true;
}, "请输入正确的签名");
jQuery.validator.addMethod("signatureMinLength", function(value, element) {
	value = value.match(/([^\【\]]+)(?=\】)/g);
	if (value[0].length < 3) {
		return false;
	}
	return true;
}, "签名最小长度为3");
jQuery.validator.addMethod("signatureMaxLength", function(value, element) {
	value = value.match(/([^\【\]]+)(?=\】)/g);
	if (value[0].length > 8) {
		return false;
	}
	return true;
}, "签名最大长度为8");
jQuery.validator.addMethod("signatureSpace", function(value, element) {
	value = value.match(/([^\【\]]+)(?=\】)/g);
	if (value[0] != $.trim(value[0])) {
		return false;
	}
	return true;
}, "签名前后不能有空格");

jQuery.validator.addMethod("isFileUpLoad", function(value, element) {
	return value != null && value != "";
}, "文件不能为空");
function search() {
	$('#example').dataTable().fnDraw(false);
}
function redirectpay(id) {
	goPage("chargeRecord/directPaySpInfo?spId=" + id,
			'static/js/spCharge/paySpInfo.js');
};
function editSpInfo(id) {
	if (id && typeof (id) != "undefined") {
		$.get("spInfo/showSpInfoView?spId=" + id, function(data) {
			$("#insSpInfo").find("#sp_name").val(data.spInfo.sp_name);
			$("#suffix").hide();
			$("#insSpInfo").find("#sp_name").parent().removeClass("col-md-4")
					.addClass("col-md-7");
			$("#insSpInfo").find("#sp_industry").val(data.spInfo.sp_industry);
			$("#insSpInfo").find("#sp_website").val(data.spInfo.sp_website);
			$("#insSpInfo").find("#sp_desc").val(data.spInfo.sp_desc);
			$("#insSpInfo").find("#spid").val(data.spInfo.spid);
			$("#insSpInfo").find("#sp_desc").val(data.spInfo.sp_desc);
			$("#insSpInfo").find("#sp_service_type").val(
					data.spInfo.sp_service_type);
			$("#insSpInfo").find("#sp_service_type").attr("disabled", 'true');
			var appInfo = data.spInfo.sp_app_type.split(",");
			$.each(appInfo, function(index, data) {
				$("#insSpInfo").find(
						"input[type='checkbox'][value='" + data + "']").prop(
						"checked", "checked");
				$(
						"#"
								+ $("#insSpInfo").find(
										"input[type='checkbox'][value='" + data
												+ "']").attr("target-input"))
						.show();
			})
			$("#insSpInfo").find("#app_input").val(data.spInfo.sp_app_info);
			$("#insSpInfo").find("#weixin_input").val(
					data.spInfo.sp_weixin_info);
			$("#insSpInfo").find("#sp_other_info").val(
					data.spInfo.sp_other_info);
			$.each()
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
	} else {
		$("#suffix").show();
		$("#insSpInfo").find("#sp_name").parent().removeClass("col-md-7")
				.addClass("col-md-4");
	}
	$("#addSpInfo").modal("show");
	return;
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
				$('#example').dataTable().fnDraw(false);
			}else if(result=='205'){
				layer.msg("应用欠费时禁止删除");
			}
		});
	});
};
function online(id) {
	$.get("spInfo/findSpInfoCompanyStatus?spId=" + id, function(data) {
		if (data.status == 0 || data.status == null) {
			layer.msg("请先提交企业信息");
		} else if (data.status == 1) {
			layer.msg("企业信息正在审核中");
		} else if (data.status == 2) {
			if (data.spInfo.sp_service_type != 1) {
				$.post("spInfo/updateInterSpInfo?spid=" + id, function(data) {
					if (data.status == 200) {
						layer.msg("申请上线成功。");
						$('#example').dataTable().fnDraw(false);
					} else {
						layer.msg("操作失败，请重试。")
					}
				})
			} else {
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
function changeWebSite(selecter) {
	if ($("#sp_app_type").val() == "2") {
		$("#app").show();
		$("#weixin").hide();
	} else if ($("#sp_app_type").val() == "3") {
		$("#app").hide();
		$("#weixin").show();
	} else {
		$("#app").hide();
		$("#weixin").hide();
	}
}
jQuery.validator.addMethod("minselect", function(value, element) {
	return $("#sp_fix_name").val() == 0 ? false : true;
}, "应用名称后缀必须选择");
$("#insSpInfo").validate(
		{
			rules : {
				sp_name : {
					required:true,
					maxlength : 30
				},
				sp_fix_name : {
					minselect : true
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
				sp_weixin_info : {
					required : true
				},
				sp_other_info : {
					required : true
				}

			},
			messages : {
				sp_name : {
					required : "应用名称必填",
					maxlength : '应用名称最大长度为30',
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
							"input[type='hidden'], :input:not(:hidden)").not(
							"input[name='sp_app_type_ids']").serialize()
							+ "&sp_app_type=" + chk_value,
					success : function(obj) {
						if (obj == '200') {
							layer.msg("保存成功");
							$('#example').dataTable().fnDraw(false);
							$("#addSpInfo").modal("hide");
						} else {
							$("#addSpInfo").modal("hide");
							layer.msg("添加失败");
						}
					}

				});
			}
		});
function addSpInfo() {
	$("#spInfoModelTitle").html("新增应用");
	$("#editSpInfoButton").html("保存");
	$("#sp_app_type").val(0);
	$("#sp_industry").val(0);
	$("#addSpInfo").modal("show");
}
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

$("#uploadAddressForm").validate({
	onclick : false,
	onkeyup : false,
	rules : {
		signature : {
			required : true,
			maxlength : 30,
			checkSignature : true,
			signatureSpace : true,
			signatureMinLength : true,
			signatureMaxLength : true
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
					$('#example').dataTable().fnDraw(false);
					$("#onlineModel").modal("hide");
					layer.msg("申请上线成功。");
				}
			}
		})
	}
});
$("#addSpInfo").on(
		"hide.bs.modal",
		function() {
			$(':input', '#insSpInfo').not(
					':button, :submit, :reset,:checkbox,select').val('');
			$("#addSpInfo").find("select option:first").prop("selected",
					'selected');
			$("#addSpInfo").find("#sp_industry option:first").prop("selected",
					"selected");
			$("#addSpInfo").find("label.error").remove();
			$("#addSpInfo").find('#sp_service_type').removeAttr("disabled");
			$("#addSpInfo").find(".error").removeClass("error");
		});

$("#onlineModel").on(
		"hide.bs.modal",
		function() {
			$(':input', '#uploadAddressForm').not(':button, :submit, :reset')
					.val('').removeAttr('checked').removeAttr('selected');
			$("#onlineModel").find("label.error").remove();
			$("#onlineModel").find(".error").removeClass("error");
		});

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
function findSpInfo(id) {
	goPage('spInfo/findspInfo?spId=' + id, 'static/js/spInfo/spInfoView.js');
}
function showRejectReason(reason) {
	layer.msg(reason);
}

function previewPic(picDivId) {
	$("#" + picDivId).click();
}
function redirectMsg() {
	$("#testMessageModal").modal("show");
}