$('#editForm').validate({
	rules : {
		mdn : {
			required : true,
			maxlength : 20
		},
		descption : {
			maxlength : 500
		},
		code : {
			required : true,
			maxlength : 8
		},
		spid : {
			min : 1
		}
	},
	messages : {
		mdn : {
			required : "手机号必须填写",
			maxlength : "手机号不能超过20位"
		},
		descption : {
			maxlength : "描述最大长度为500"
		},
		code : {
			required : "验证码必须填写",
			maxlength : "验证码最大长度为8位"
		},
		spid : {
			min : "生效应用必须选择"
		}
	},
	submitHandler : function() {
		save();
	}

});
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
								url : "whitelist/load",
								dataSrc : "data",
								data : data,
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									if (XMLHttpRequest.status == 404) {
										$("#example").html("数据库服务器通讯失败，请稍后重试！");
									}
								}
							},
							'fnServerParams' : function(aoData) {
								aoData.mdn = $("#mdn").val();
								aoData.spid = $("#spid").val();
								aoData.queryStartTime = $("#queryStartTime").val();
								aoData.queryEndTime = $("#queryEndTime").val();
							},
							serverSide : true,
							columns : [
									{
										"data" : "mdn"
									},
									{
										"data" : "sp_name",
									},
									{
										"data" : "create_time",
										render : function(data, type, row) {
											return data == null ? ""
													: getLocalTime(data);
										}
									},
									{
										"data" : "descption",
										render : function(data, type, row) {
											if (data == null) {
												return "";
											} else {
												if (data.length > 40) {
													data = data.toString()
															.substring(0, 40)
															+ "...";
												}
												return data;
											}
										}
									}, {
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
								targets : 4,
								render : function(a, b, c, d) {
									var html = "<button class='mb-sm btn btn-primary' onclick='del("
											+ a.id + ")'>删除</button>";
									return html;
								}
							}

							],
						});
	}
};
$(function() {
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 27) { // 按 Esc
			// 要做的事情
		}
		if (e && e.keyCode == 113) { // 按 F2
			// 要做的事情
		}
		if (e && e.keyCode == 13) { // enter 键
			search();
		}
	};
	$('#queryStartTime').datetimepicker({
		format : 'Y-m-d',
		timepicker : false,
		allowBlank : true,
		onChangeDateTime : function() {
			checkStartTimeMonth("queryStartTime", "All");
		}
	});
	$('#queryEndTime').datetimepicker({
		format : 'Y-m-d',
		timepicker : false,
		allowBlank : true,
		onChangeDateTime : function() {
			checkEndTimeMonth("queryStartTime", "queryEndTime");
		}
	});
	if ($.fn.dataTable.isDataTable('#example')){
		$('#example').dataTable().fnDraw(false);
	}else{
		dataTable.init();
	}
});
function edit(id) {
	$('#myModal').modal('show');

	$.ajax({
		url : 'whitelist/showInfo',
		type : 'post',
		data : {
			id : id,
		},
		success : function(data) {
			if (data.data == undefined) {
				$("#edit_id").val('');
				$("#edit_mdn").val('');
				$("#edit_code").val('');
				$("#edit_descption").val('');
				document.getElementById("myModalLabel").innerHTML = '新增白名单';
				// $("#edit_spid").val(0)
			} else {
				$("#edit_id").val(data.data.id);
				$("#edit_mdn").val(data.data.mdn);
				$("#edit_descption").val(data.data.descption);
				$("#edit_spid").val(data.data.spid)
				document.getElementById("myModalLabel").innerHTML = '修改白名单';
			}
		}
	});

};
function search() {
	$('#example').dataTable().fnDraw();
}

function save() {
	$.ajax({
		url : 'whitelist/save',
		type : 'post',
		data : $(editForm).serialize(),
		success : function(data) {
			if (data.statusCode == 200) {
				// layer.msg('保存成功!', {icon : 1});
				layer.msg("保存成功！", {
					icon : 1
				});
				$('#myModal').modal('hide');
				search();
			} else if (data.statusCode == 202) {
				// 超过上限
				layer.msg("每个应用白名单最多设置6个！", {
					icon : 2
				});
			} else if (data.statusCode == 203) {
				// 未获取验证码
				layer.msg("您还未获取验证码！", {
					icon : 2
				});
			} else if (data.statusCode == 204) {
				// 验证码错误
				layer.msg("验证码有误！", {
					icon : 2
				});
			} else if (data.statusCode == 205) {
				// 手机号码已存在
				layer.msg("手机号码已存在！", {
					icon : 2
				});
			} else if (data.statusCode == 251) {
				// 手机格式错误
				layer.msg("请输入正确的手机号！", {
					icon : 2
				});
			} else {
				layer.msg("服务器无响应，请联系管理员！", {
					icon : 2
				});
			}
		},
		error : function() {
			layer.msg("服务器无响应，请联系管理员！", {
				icon : 2
			});
		}
	});
}

function del(deleteID) {
	layer.confirm('您确定删除此条白名单？', {
		icon : 0,
		title : '白名单删除',
		btn : [ '确定', '取消' ]
	// area : [ '300px', 'auto' ]
	}, function() {
		$.ajax({
			url : 'whitelist/del',
			type : 'post',
			data : {
				id : deleteID
			},
			success : function(data) {
				if (data.statusCode == 200) {
					layer.msg("删除成功！", {
						icon : 1
					});
					search();
				} else {
					layer.msg("删除失败！", {
						icon : 2
					});
				}
			},
			error : function() {
				layer.msg("服务器无响应，请联系管理员！", {
					icon : 2
				});
			}
		});
	});
};
function getCode(obj) {
	$.ajax({
		url : 'whitelist/getCode',
		type : 'post',
		data : {
			mobile : function() {
				return $("#edit_mdn").val();
			}
		},
		success : function(data) {
			if (data.statusCode == 200) {
				layer.msg("验证码已发送到您的手机上，请注意查收！", {
					icon : 1
				});
				time(obj);
			} else if (data.statusCode == 201) {
				// 手机号有误
				layer.msg("请输入正确的手机号！", {
					icon : 2
				});
			} else {
				layer.msg("服务器无响应，请联系管理员！", {
					icon : 2
				});
			}
		},
		error : function() {
			layer.msg("服务器无响应，请联系管理员！", {
				icon : 2
			});
		}
	});
};