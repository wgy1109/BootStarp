$('#editForm').validate({
	rules : {
		mdn : {
			required : true,
			maxlength : 20
		},
		descption : {
			maxlength : 500
		},
		spid : {
			min : 1
		}
	},
	messages : {
		mdn : {
			required : "手机号必填",
			maxlength : "手机号不能超过20位"
		},
		descption : {
			maxlength : "描述最多不能超过500"
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
								url : "blacklist/load",
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
								aoData.queryStartTime = $("#queryStartTime")
										.val();
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
										"data" : null,
										"width" : "200"
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
									var html = "<a href='javascript:;' onclick='edit("
											+ a.id + ")'>修改</button";
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
	if ($.fn.dataTable.isDataTable('#example')) {
		$('#example').dataTable().fnDraw(false);
	} else {
		dataTable.init();
	}
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

});
function edit(id) {
	$('#myModal').modal('show');
	$.ajax({
		url : 'blacklist/showInfo',
		type : 'post',
		data : {
			id : id,
		},
		success : function(data) {
			if (data.data == undefined) {
				$("#edit_id").val('');
				$("#edit_mdn").val('');
				$("#edit_descption").val('');
				document.getElementById("myModalLabel").innerHTML = '新增黑名单';
			} else {
				$("#edit_id").val(data.data.id);
				$("#edit_mdn").val(data.data.mdn);
				$("#edit_descption").val(data.data.descption);
				document.getElementById("myModalLabel").innerHTML = '修改黑名单';
				$("#edit_spid").val(data.data.target_id);
			}
		}
	});

};
function search() {
	$('#example').dataTable().fnDraw();
}

function save() {
	$.ajax({
		url : 'blacklist/save',
		type : 'post',
		data : $(editForm).serialize(),
		success : function(data) {
			if (data.statusCode == 200) {
				layer.msg("保存成功！", {
					icon : 1
				});
				$('#myModal').modal('hide');
				search();
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
				layer.msg("保存失败！", {
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
	layer.confirm('您确定删除此条黑名单？<br>删除后此手机号可正常收发消息，请您谨慎提交。', {
		icon : 0,
		title : '黑名单删除',
		btn : [ '确定', '取消' ]
	// area : [ '300px', 'auto' ]
	}, function() {
		$.ajax({
			url : 'blacklist/del',
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

function exportPOI() {
	layer.confirm('确认要导出黑名单数据吗？', {
		btn : [ '确认导出', '取消导出' ]
	// 按钮
	}, function() {
		layer.load(2);
		window.location.href = "blacklist/exportBlackListToExcel?"
				+ $("#form1").serialize();
		closelayers = setInterval("closelayer();", 500);
	}, function() {
	});
};

function closelayer() {
	$.ajax({
		type : "post",
		url : "blacklist/closetype",
		success : function(data) {
			if (data == "0") {
				clearInterval(closelayers);
				layer.closeAll();
			}
		}
	});
}