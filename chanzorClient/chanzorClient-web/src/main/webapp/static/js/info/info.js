$('#authForm').validate({
	rules : {
		company : {
			required : true
		},
		company_address : {
			required : true
		},
		contact : {
			required : true
		},
		legal_representative : {
			required : true
		},
		// idcard_image_SRC : {
		// required : true
		// },
		// organization_no : {
		// required : true
		// },
		// organization_image_SRC : {
		// required : true
		// },
		// taxpayer_no : {
		// required : true
		// },
		// taxpayer_image_SRC : {
		// required : true,
		// },
		// registered_no : {
		// required : true
		// }
		// ,
		registered_image_SRC : {
			required : true
		},
		identity_front_img_SRC : {
			required : true
		},
		identity_back_img_SRC : {
			required : true
		}
	},
	messages : {
		company : {
			required : "请输入公司名称"
		},
		company_address : {
			required : "请输入公司地址"
		},
		contact : {
			required : "请输入公司电话"
		},
		legal_representative : {
			required : "请输入公司法人"
		},
		// idcard_image_src : {
		// required : "请上传法人身份证(扫描件)"
		// },
		// ORGANIZATION_NO : {
		// required : "请输入组织机构代码证"
		// },
		// ORGANIZATION_IMAGE_SRC : {
		// required : "请上传组织机构代码证(扫描件)"
		// },
		// TAXPAYER_NO : {
		// required : "请输入纳税证"
		// },
		// TAXPAYER_IMAGE_SRC : {
		// required : "请上传纳税证(扫描件)",
		// },
		// REGISTERED_NO : {
		// required : "请输入注册证"
		// }
		// ,
		registered_image_SRC : {
			required : "请上传注册证(扫描件)"
		},
		identity_front_img_SRC : {
			required : "请上传法人身份证正面(扫描件)"
		},
		identity_back_img_SRC : {
			required : "请上传法人身份证反面(扫描件)"
		}

	},
	submitHandler : function() {
		saveAuth();
	}

});

// 发票认证验证信息
$("#invoiceForm").validate({
	rules : {
		vatinvoice_title : {
			required : true
		}
	},
	messages : {
		vatinvoice_title : {
			required : "请输入发票抬头"
		}
	},
	submitHandler : function(form) {
		saveInvoice();
	}
});
// 保存发票认证信息
function saveInvoice() {
	var vatinvoiceType = $("input[name='vatinvoice_type']:checked").val();
	if (vatinvoiceType == '0') {
		$.ajax({
			url : 'appVatinvoiceCertinfoClient/saveCommon',
			type : 'post',
			data : $('#invoiceForm').serialize(),
			success : function(data) {
				if (data.code == '00') {
					layer.msg(data.msg, {
						icon : 1
					});
				} else {
					layer.msg(data.msg, {
						icon : 2
					});
				}
			}
		});
	} else {
		$.ajax({
			url : 'appVatinvoiceCertinfoClient/saveSpecial',
			type : 'post',
			data : $('#invoiceForm').serialize(),
			success : function(data) {
				if (data.code == '00') {
					layer.msg(data.msg, {
						icon : 1
					});
					$("#statusDesc").text("未提交认证");
				} else {
					layer.msg(data.msg, {
						icon : 2
					});
				}
			}
		});
	}
}

function changeInvoiceType() {
	var vatinvoiceType = $("input[name='vatinvoice_type']:checked").val();
	if (vatinvoiceType == '0') {
		$('#specialDiv').hide();
		$('#saveAndCommitInvoiceButton').hide();
	} else {
		$('#specialDiv').show();
		$('#saveAndCommitInvoiceButton').show();
	}
}

function setButtonDisplay() {
	if ($("#certinfo_status").val() == '0') {
		$('#saveInvoiceButton').hide();
		$('#saveAndCommitInvoiceButton').hide();
	}
}

// 提交发票信息认证
function vatinvoiceSubmit() {
	layer.confirm('提交审核你将不可修改发票信息,确定要提交审核吗?', function() {
		$.ajax({
			url : 'appVatinvoiceCertinfoClient/saveSpecial',
			type : 'post',
			data : $('#invoiceForm').serialize(),
			success : function(data) {
				if (data.code == '00') {
					$.ajax({
						url : 'appVatinvoiceCertinfoClient/vatinvoiceSubmit',
						type : 'post',
						success : function(data) {
							if (data.code == '00') {
								layer.msg(data.msg, {
									icon : 1
								});
								// $("#invoiceInfoType").val("认证中");
								$("#statusDesc").text("认证中");
								// $("#certinfo_status").val("0");
								// setButtonDisplay();
							} else {
								layer.msg(data.msg, {
									icon : 2
								});
							}
						}
					});
				} else {
					layer.msg(data.msg, {
						icon : 2
					});
				}
			}
		});
	});
}

function updatePsd() {
	var newPassValue = $("#newPsd").val();
	var passwordStrengthMark = passwordStrength(newPassValue);
	if (passwordStrength(newPassValue) < '2') {
		layer.msg("请修改密码，强度太弱", {
			icon : 2
		});

		return;
	}
	var space=/\s/;
	if(space.test(newPassValue)){
		layer.msg("密码中不能包含空格，请重新设置", {
			icon : 2
		});
		return;
	}
	$.ajax({
		url : 'updatePassword?passwordStrengthMark=' + passwordStrengthMark,
		type : 'post',
		data : $('#psdForm').serialize(),
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				$("#passwordModal").modal('hide');

				// 密码强度区显示文字
				if (passwordStrength(newPassValue) == '1') {
					$("#passStrengthSpan").html(
							"<font size='3' color='red'>弱</font>");
				}
				if (passwordStrength(newPassValue) == '2') {
					$("#passStrengthSpan").html(
							"<font size='3' color='#F9D330'>中</font>");
				}
				if (passwordStrength(newPassValue) == '3') {
					$("#passStrengthSpan").html(
							"<font size='3' color='green'>强</font>");
				}
				// 调整安全等级
				$("#securityLevelImg").attr("src",
						"img/securityLevel" + data.securityLevel + ".png");

				return;
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
				return;
			}
		}
	});
}
// 异步上传文件
function updateImage(obj) {
	var textid = $(obj).attr('id') + "_SRC";
	$
			.ajaxFileUpload({
				url : 'updateImage',// 用于文件上传的服务器端请求地址
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
						layer.msg('文件错误,请重新选择图片文件', {
							icon : 2
						});
						$(obj).val("");
						return;
					} else {
						var MAXWIDTH = 260;
						var MAXHEIGHT = 180;
						var div = document.getElementById(textid + "_preview");
						if (obj.files && obj.files[0]) {
							// div.innerHTML = '<img id='+ textid + '_imghead
							// onclick="showImage(this)" >';
							// var img =
							// document.getElementById(textid+'_imghead');
							// img.onload = function() {
							// var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
							// img.offsetWidth, img.offsetHeight);
							// img.width = rect.width;
							// img.height = rect.height;
							// img.style.marginTop = rect.top + 'px';
							// }
							// var reader = new FileReader();
							// reader.onload = function(evt) {
							// img.src = evt.target.result;
							// }
							// reader.readAsDataURL(obj.files[0]);

							div.innerHTML = '<img id=' + textid + '_imghead'
									+ ' src=\"/httpmapping/' + data
									+ '\" width=\"391\" height=\"214\"  >';
							$("#" + textid + "_imghead").attr('src',
									"/httpmapping/" + data);
							var previewDiv = document.getElementById($(obj)
									.attr('id')
									+ "_div");
							previewDiv.innerHTML = '<a href=\"#\" onClick=\"javascript:previewPic(\''
									+ $(obj).attr('id')
									+ '_SRC_imghead\');\">预览</a>';

						} else // 兼容IE
						{
							var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
							obj.select();
							var src = document.selection.createRange().text;
							div.innerHTML = '<img id=' + textid
									+ '_imghead  onclick="showImage(this)" >';
							var img = document.getElementById(textid
									+ '_imghead');
							img.filters
									.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
							var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
									img.offsetWidth, img.offsetHeight);
							status = ('rect:' + rect.top + ',' + rect.left
									+ ',' + rect.width + ',' + rect.height);
							div.innerHTML = "<div id=divhead style='width:"
									+ rect.width + "px;height:" + rect.height
									+ "px;margin-top:" + rect.top + "px;"
									+ sFilter + src + "\"'></div>";
						}
						$("#" + textid + '_example').hide();
					}
					$('#' + textid).val(data);
				},
				error : function(data, status, e)// 服务器响应失败处理函数
				{
					layer.msg(e);
				}
			})
}

function updateImageNoDisplay(obj, modalWinId) {
	var textid = $(obj).attr('id') + "_SRC";
	$.ajaxFileUpload({
		url : 'updateImage',// 用于文件上传的服务器端请求地址
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
				layer.msg('文件错误,请重新选择图片文件', {
					icon : 2
				});
				$(obj).val("");
				return;
			} else {
				// do nothing
			}
			$('#' + textid).val(data);
			// $("#" + modalWinId).modal('hide');
		},
		error : function(data, status, e)// 服务器响应失败处理函数
		{
			layer.msg(e);
		}
	})
}

function uploadPhoto(obj) {
	var textid = $(obj).attr('id') + "_SRC";
	$
			.ajaxFileUpload({
				url : 'updateImage',// 用于文件上传的服务器端请求地址
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
						layer.msg('文件错误,请重新选择图片文件', {
							icon : 2
						});
						$(obj).val("");
						return;
					} else {
						var MAXWIDTH = 100;
						var MAXHEIGHT = 100;
						var div = document.getElementById(textid + "_preview");
						if (obj.files && obj.files[0]) {
							div.innerHTML = '<img id=' + textid
									+ '_imghead  onclick="showImage(this)" >';
							var img = document.getElementById(textid
									+ '_imghead');
							img.onload = function() {
								var rect = clacImgZoomParam(MAXWIDTH,
										MAXHEIGHT, img.offsetWidth,
										img.offsetHeight);
								img.width = rect.width;
								img.height = rect.height;
								img.style.marginTop = rect.top + 'px';
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
							div.innerHTML = '<img id=' + textid
									+ '_imghead  onclick="showImage(this)" >';
							var img = document.getElementById(textid
									+ '_imghead');
							img.filters
									.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
							var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
									img.offsetWidth, img.offsetHeight);
							status = ('rect:' + rect.top + ',' + rect.left
									+ ',' + rect.width + ',' + rect.height);
							div.innerHTML = "<div id=divhead style='width:"
									+ rect.width + "px;height:" + rect.height
									+ "px;margin-top:" + rect.top + "px;"
									+ sFilter + src + "\"'></div>";
						}
						$("#" + textid + '_preview').show();
					}
					$('#' + textid).val(data);
					$("#homeImg").attr("src", $("#nginxPath").val() + data);
					$("#photoModal").modal('hide');
					updatePhoto();
				},
				error : function(data, status, e)// 服务器响应失败处理函数
				{
					layer.msg(e);
				}
			})
}
// 修改头像信息
function updatePhoto() {
	$.ajax({
		url : 'updatePhoto',
		type : 'post',
		// data : "{\"photo_img\":\""+$('#photo_img_SRC').val() +"\"}",
		data : $('#photoForm').serialize(),
		success : function(data) {
			if (data.code == '00') {
				// layer.msg('保存成功', {
				// icon : 1
				// });

				return;
			} else {
				layer.msg('保存失败', {
					icon : 2
				});
				return;
			}

		}
	});
}

function saveAuth() {
	$.ajax({
		url : 'authSubNew',
		type : 'post',
		data : $('#authForm').serialize(),
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}
		}
	});
}
// 提交企业认证
function authentication() {
	$.get("checkCompany?company=" + $("#company").val(), function(data) {
		if (data.status == 200) {
			layer.confirm('提交审核你将不可修改企业信息,确定要提交审核吗?', function() {
				$.ajax({
					url : 'authSubNew',
					type : 'post',
					data : $('#authForm').serialize(),
					success : function(data) {
						if (data.code == '00') {
							$.ajax({
								url : 'authentication',
								type : 'post',
								success : function(data) {
									if (data.code == '00') {
										layer.msg(data.msg, {
											icon : 1
										});
										// $("#userInfoType").val("认证中");
										$("#statusDesc").text("认证中");
									} else {
										layer.msg(data.msg, {
											icon : 2
										});
									}
								}
							});
						} else {
							layer.msg(data.msg, {
								icon : 2
							});
						}
					}
				});
			});
		} else {
			layer.alert("尊敬的用户您好，该企业已被注册，负责该企业销售为" + data.name + "，电话："
					+ data.phone + "，如需业务合作可与销售直接联系！", {
				skin : 'layui-layer-molv',
				closeBtn : 0
			});
		}
	})
}
// 修改账户信息
function update() {
	$.ajax({
		url : 'updateUserInfo',
		type : 'post',
		data : $('#infoform').serialize(),
		success : function(data) {
			if (data.code == '00') {
				layer.msg('保存成功', {
					icon : 1
				});
				$('#USER_NAME').val($('input[name=USER_NAME]').val());
				$('#nameModal').modal('hide');
				return;
			} else {
				layer.msg('保存失败', {
					icon : 2
				});
				return;
			}

		}
	});
}

// 发送验证码
function sendSMSCode(obj) {
	var mobile = $('input[name=mobile]').val();
	var imageCode = $('input[name=imageCode]').val();
	var d = {
		mobile : mobile,
		imageCode : imageCode,
		validate : 'true'
	};
	$.ajax({
		url : 'sendSMSCode',
		data : d,
		type : 'post',
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				time(obj);
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}

		}
	});

}

function sendVerifyCode(obj) {
	var mobile = $('input[name=mobile]').val();
	var d = {
		mobile : mobile,
		validate : 'false'
	};
	$.ajax({
		url : 'sendSMSCodeNew',
		data : d,
		type : 'post',
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				time(obj);
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}

		}
	});
}

function sendSMSCodeNew(obj) {
	var mobile = $('input[name=mobile]').val();
	var d = {
		mobile : mobile,
		validate : 'true'
	};
	$.ajax({
		url : 'sendSMSCodeNew',
		data : d,
		type : 'post',
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				time(obj);
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}

		}
	});

}

// 获取图形验证码
function refreshCode2(obj) {
	var d = new Date();
	$(obj).attr('src', 'loginCode.html?t=' + d);
}

// 修改手机号
function updateMobile() {
	$.ajax({
		url : 'updateMobile',
		type : 'post',
		data : $('#mobileForm').serialize(),
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				$('#MOBILE').val($('input[name=mobile]').val());
				$('#mobileModal').modal('hide');
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}
		}
	});
}
function sendEmailCode(obj) {
	$.ajax({
		url : 'sendEmailCode',
		data : {
			email : $('input[name=email]').val()
		},
		type : 'post',
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				time(obj);
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}
		}
	});
}

function updateEmail() {
	$.ajax({
		url : 'updateEmail',
		type : 'post',
		data : $('#emailForm').serialize(),
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				$('#EMAIL').val($('input[name=email]').val());
				$('#emailModal').modal('hide');
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}
		}
	});
}

function baseInfoSave() {
	if ($("#mobileHidden").val() != $("#mobile").val()) {
		$.ajax({
			url : 'updateBaseInfoIncludeMobile',
			type : 'post',
			data : $('#baseInfoForm').serialize(),
			success : function(data) {
				if (data.code == '00') {
					layer.msg(data.msg, {
						icon : 1
					});
				} else {
					layer.msg(data.msg, {
						icon : 2
					});
				}
			}
		});
	} else {
		$.ajax({
			url : 'updateBaseInfoExceptMobile',
			type : 'post',
			data : $('#baseInfoForm').serialize(),
			success : function(data) {
				if (data.code == '00') {
					layer.msg(data.msg, {
						icon : 1
					});
				} else {
					layer.msg(data.msg, {
						icon : 2
					});
				}
			}
		});
	}
}

$(document).ready(function() {
	try {
		$("#mobileCodeDiv").hide();
		passwordBind();
	} catch (e) {
	}
	try {
		changeInvoiceType();
		// setButtonDisplay();
	} catch (e) {
	}
});

function isChange() {
	$("#mobileHidden").val($.trim($("#mobileHidden").val()));
	if ($("#mobileHidden").val() != $("#mobile").val()) {
		$("#mobileCodeDiv").show();
	} else {
		$("#mobileCodeDiv").hide();
	}
}

function previewPic(picDivId) {
	$("#" + picDivId).click();
}

function clickImgUpload(fileAreaId) {
	$("#" + fileAreaId).click();
}

function closeModal(modalWinId) {
	$("#" + modalWinId).modal('hide');
}

function passwordBind() {
	$('#newPsd')
			.keyup(
					function() {
						var strongRegex0 = new RegExp(
								"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$",
								"g"); // 四种均包含 强
						var strongRegex1 = new RegExp(
								"^(?=.{7,})(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$",
								"g"); // 小写，数字，特殊
						var strongRegex2 = new RegExp(
								"^(?=.{7,})(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$",
								"g"); // 大写，数字，特殊
						var strongRegex3 = new RegExp(
								"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$",
								"g"); // 大写，小写，数字
						var strongRegex4 = new RegExp(
								"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*\\W).*$",
								"g"); // 大写，小写，特殊
						var mediumRegex0 = new RegExp(
								"^(?=.{6,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",
								"g");
						var mediumRegex1 = new RegExp(
								"^(?=.{6,})(((?=.*[A-Z])(?=.*\\W))|((?=.*\\W)(?=.*[0-9]))|((?=.*[a-z])(?=.*\\W))).*$",
								"g");
						var enoughRegex = new RegExp("(?=.{5,}).*", "g");

						if (false == enoughRegex.test($(this).val())) {
							$('#level').removeClass('pw-weak');
							$('#level').removeClass('pw-medium');
							$('#level').removeClass('pw-strong');
							$('#level').addClass(' pw-defule');
							// 密码小于六位的时候，密码强度图片都为灰色
						} else if (strongRegex0.test($(this).val())) {
							$('#level').removeClass('pw-weak');
							$('#level').removeClass('pw-medium');
							$('#level').removeClass('pw-strong');
							$('#level').addClass(' pw-strong');
						} else if (strongRegex1.test($(this).val())) {
							$('#level').removeClass('pw-weak');
							$('#level').removeClass('pw-medium');
							$('#level').removeClass('pw-strong');
							$('#level').addClass(' pw-strong');
						} else if (strongRegex2.test($(this).val())) {
							$('#level').removeClass('pw-weak');
							$('#level').removeClass('pw-medium');
							$('#level').removeClass('pw-strong');
							$('#level').addClass(' pw-strong');
						} else if (strongRegex3.test($(this).val())) {
							$('#level').removeClass('pw-weak');
							$('#level').removeClass('pw-medium');
							$('#level').removeClass('pw-strong');
							$('#level').addClass(' pw-strong');
						} else if (strongRegex4.test($(this).val())) {
							$('#level').removeClass('pw-weak');
							$('#level').removeClass('pw-medium');
							$('#level').removeClass('pw-strong');
							$('#level').addClass(' pw-strong');
						}

						else if (mediumRegex0.test($(this).val())) {
							$('#level').removeClass('pw-weak');
							$('#level').removeClass('pw-medium');
							$('#level').removeClass('pw-strong');
							$('#level').addClass(' pw-medium');
						} else if (mediumRegex1.test($(this).val())) {
							$('#level').removeClass('pw-weak');
							$('#level').removeClass('pw-medium');
							$('#level').removeClass('pw-strong');
							$('#level').addClass(' pw-medium');
						}

						else {
							$('#level').removeClass('pw-weak');
							$('#level').removeClass('pw-medium');
							$('#level').removeClass('pw-strong');
							$('#level').addClass('pw-weak');
						}
						return true;
					});
}

// 返回密码强度
function passwordStrength(passwordValue) {
	var strongRegex0 = new RegExp(
			"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); // 四种均包含
	// 强
	var strongRegex1 = new RegExp(
			"^(?=.{7,})(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g"); // 小写，数字，特殊
	var strongRegex2 = new RegExp(
			"^(?=.{7,})(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$", "g"); // 大写，数字，特殊
	var strongRegex3 = new RegExp(
			"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$", "g"); // 大写，小写，数字
	var strongRegex4 = new RegExp(
			"^(?=.{7,})(?=.*[A-Z])(?=.*[a-z])(?=.*\\W).*$", "g"); // 大写，小写，特殊
	var mediumRegex0 = new RegExp(
			"^(?=.{6,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",
			"g");
	var mediumRegex1 = new RegExp(
			"^(?=.{6,})(((?=.*[A-Z])(?=.*\\W))|((?=.*\\W)(?=.*[0-9]))|((?=.*[a-z])(?=.*\\W))).*$",
			"g");
	var enoughRegex = new RegExp("(?=.{5,}).*", "g");

	if (false == enoughRegex.test(passwordValue)) { // 密码小于6位，不允许
		return 0;
	} else if (strongRegex0.test(passwordValue)) { // 密码强
		return 3;
	} else if (strongRegex1.test(passwordValue)) { // 密码强
		return 3;
	} else if (strongRegex2.test(passwordValue)) { // 密码强
		return 3;
	} else if (strongRegex3.test(passwordValue)) { // 密码强
		return 3;
	} else if (strongRegex4.test(passwordValue)) { // 密码强
		return 3;
	} else if (mediumRegex0.test(passwordValue)) { // 密码中
		return 2;
	} else if (mediumRegex1.test(passwordValue)) { // 密码中
		return 2;
	} else { // 密码弱
		return 1;
	}
}

function securityLoginOnOff() {
	var securityLoginMark = $("#securityLoginMark").val();
	if (securityLoginMark == "1") {
		securityLoginMark = "0";
	} else {
		securityLoginMark = "1";
	}
	$.ajax({
		url : 'securityLoginOnOff?securityLoginMark=' + securityLoginMark,
		type : 'post',
		data : null,
		success : function(data) {
			if (data.code == '00') {
				$("#securityLoginMark").val(securityLoginMark);
				if (securityLoginMark == '0') {
					$("#securityLoginMarkSpan").text("开启");
					$("#securityLoginMarkDisplay").text("未开启");
				} else {
					$("#securityLoginMarkSpan").text("关闭");
					$("#securityLoginMarkDisplay").text("已开启");
				}
				// 调整安全等级
				$("#securityLevelImg").attr("src",
						"img/securityLevel" + data.securityLevel + ".png");

				layer.msg(data.msg, {
					icon : 1
				});
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}
		}
	});

}

function beginModifyPassWord() {
	$("#passwordModal").modal('show');
}

function beginModifyAccount() {
	$("#verifyCode").val("");
	$("#accountModal").modal('show');
}

function modifyAccount() {
	var newAccountName = $('input[name=newAccountName]').val();
	var newMobile = $('input[name=newMobile]').val();
	var verifyCode = $('input[name=verifyCode]').val();
	var id = $('#id').val();
	var d = {
		newAccountName : newAccountName,
		newMobile : newMobile,
		verifyCode : verifyCode
	};

	$.ajax({
		url : 'updateAccount',
		type : 'post',
		data : d,
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				$("#accountNameSpan").html(newAccountName);
				$("#mobile").val(newMobile);
				$("#mobileHidden").val(newMobile);
				$("#accountModal").modal('hide');
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}
		}
	});
}
