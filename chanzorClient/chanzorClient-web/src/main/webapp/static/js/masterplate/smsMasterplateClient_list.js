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
								url : "smsMasterplateClient/load",
								dataSrc : "data",
								data : data,
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									if (XMLHttpRequest.status == 404) {
										$("#example").html("数据库服务器通讯失败，请稍后重试！");
									}
								}
							},
							fnServerParams : function(aoData) {
								aoData.TITLE = $("#TITLE").val();
								aoData.content = $("#selcontent").val();
								aoData.STATUS = $("#STATUS").val();
								aoData.SYSTEMORSPINFO = $("#SYSTEMORSPINFO")
										.val();
								aoData.SP_ID = $("#SP_ID").val();
							},
							serverSide : true,
							columns : [ {
								"data" : "title"
							}, {
								"data" : "sp_name",
								render : function(data, type, row) {
									return data == null ? "" : data;
								}
							}, {
								"data" : "status",
								render : function(data, type, row) {
									if (data == 0) {
										return "审核通过";
									} else if (data == 1) {
										return "审核中";
									} else if (data == 2) {
										return "审核驳回";
									} else if (data == 3) {
										return "未提交审核";
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
								targets : 3,
								render : function(a, b, c, d) {
									if (a.id == 0) {
										return "";
									}
									var html = "";
									if (a.status == 3 || a.status == 2 || a.status == 0 ) {
										html += "<button class='mb-sm btn btn-primary' onclick='edit("
												+ a.id
												+ ","
												+ a.status
												+ ")'>修改</button>&nbsp;&nbsp;&nbsp;&nbsp;";
									}
									if (a.status == 3) {
										html += "<button class='mb-sm btn btn-primary' onclick='subValidate("
												+ a.id
												+ ","
												+ a.status
												+ ")'>提交审核</button>&nbsp;&nbsp;&nbsp;&nbsp;";
									}
									if (a.status != 1)
										html += "<button class='mb-sm btn btn-primary' onclick='del("
												+ a.id
												+ ","
												+ a.status
												+ ")'>删除</button>&nbsp;&nbsp;&nbsp;&nbsp;";
									return html;
								}
							} ],
							fnDrawCallback : function(a, b, c, d) {
								var table = $(this);
								var arrayObj = new Array();
								$.each($(a).attr("json").data, function(i, e) {
									arrayObj.push(e.content);
								})
								if (arrayObj.length > 0) {
									$
											.each(
													table.find("tr"),
													function(i, e) {
														if (i != 0) {
															$(this)
																	.after(
																			"<tr><td colspan='8' style='text-align: right;'>"
																					+ htmlEncodeJQ1(stringIntercept(arrayObj[i - 1]))
																					+ "<span class='text-danger' >（共 "
																					+ arrayObj[i - 1].length
																					+ " 字）</span></td></tr>")
														}
													})
								}
							}
						});
	}
};

$(function() {
	if ($.fn.dataTable.isDataTable('#example')){
		$('#example').dataTable().fnDraw(false);
	}else{
		dataTable.init();
	}
});

function edit(id, status) {
	if (-1 == id) {
		$
				.ajax({
					url : 'smsMasterplateClient/getspnameList',
					type : 'post',
					success : function(data) {
						$('#myModalLabel').html('新增模板');
						var html = "";
						for (var i = 0; i < data.lists.length; i++) {
							var resultMap = data.lists[i];
							html = html + "<option value='" + resultMap.id
									+ "' >" + resultMap.sp_name + "</option>";
						}
						html = "<select  class='form-control' style='width:205px; height:35px; ' name='spid' >\
										<option value='' selected='selected'>请选择</option>"
								+ html + "	</select> ";
						$("#spnameSelect").html(html);
						$("#id").val("");
						$("#title").val("");
						$("#upcontent").val("");
						$("#upcontent").attr("maxlength", data.maxlength);
						$('#myModal').modal('show');
					}
				});
	} else {
		if (status == 3 || status == 2 || status == 0) {
			$('#myModalLabel').html('编辑模板');
			$.ajax({
				url : 'smsMasterplateClient/edit',
				type : 'post',
				data : {
					id : id
				},
				success : function(resultData) {
					$("#id").val(resultData.id);
					$("#spnameSelect").html(
							"<input class='form-control' type='text' value='"
									+ resultData.sp_name
									+ "' readonly='readonly' /> ");
					$("#title").val(resultData.title);
					$("#upcontent").val(resultData.content);
					$("#upcontent").attr("maxlength", resultData.maxlength);
				}
			});
			$('#myModal').modal('show');
		} else {
			layer.msg("模板已经提交，不能修改");
		}
	}
	;
};

function detail(id) {
	if (-1 == id) {
		$('#myModalDetailtitle').html('新增免审核模板');
		$
				.ajax({
					url : 'smsMasterplateClient/addTrialMaster',
					type : 'post',
					data : {
						id : id
					},
					success : function(resultData) {
						var html = "";
						var spinfoList = resultData.spinfoList;
						for (var i = 0; i < spinfoList.length; i++) {
							var resultMap = spinfoList[i];
							html = html + "<option value='" + resultMap.ID
									+ "' >" + resultMap.SP_NAME + "</option>";
						}
						html = "<select  class='form-control' style='width:205px; height:35px; ' name='spid' >\
									<option value='' selected='selected'>请选择</option>"
								+ html + "	</select> ";
						$("#valspnameSelect").html(html);
						html = "";
						spinfoList = "";
						spinfoList = resultData.ontrialList;
						for (var i = 0; i < spinfoList.length; i++) {
							var resultMap = spinfoList[i];
							html = html + "<option value='" + resultMap.id
									+ "' >" + resultMap.title + "</option>";
						}
						html = "<select  class='form-control' style='width:205px; height:35px; ' name='trialList' id='trialList' onchange='updateMasterplateConent()' >\
									<option value='' selected='selected'>请选择</option>"
								+ html + "	</select> ";
						$("#valtitle").html(html);
						$("#valcontent")
								.html(
										"<textarea class='form-control' rows='5' cols='5'  maxlength='1000' readonly='readonly' id = 'textareaContent' name='content'></textarea>");
						$("#valmodal")
								.html(
										"<button type='submit' class='btn btn-primary' >保存</button><button type='button' data-dismiss='modal' class='btn btn-default'>关闭</button> ");
						$('#myModalDetail').modal('show');
					}
				});
	} else {
		$('#myModalDetailtitle').html('模板详细');
		$
				.ajax({
					url : 'smsMasterplateClient/edit',
					type : 'post',
					data : {
						id : id
					},
					success : function(resultData) {
						$("#valspnameSelect").html(
								"<input class='form-control' type='text' value='"
										+ resultData.SP_NAME
										+ "' readonly='readonly' /> ");
						$("#valtitle").html(
								"<input class='form-control' type='text' value='"
										+ resultData.TITLE
										+ "' readonly='readonly' /> ");
						$("#valcontent")
								.html(
										"<textarea class='form-control' rows='5' cols='5'  maxlength='1000' readonly='readonly' >"
												+ resultData.CONTENT
												+ "</textarea>");
						$("#valmodal")
								.html(
										"<button type='button' data-dismiss='modal' class='btn btn-default'>关闭</button> ");
						$('#myModalDetail').modal('show');
					}
				});
	}

};

function delSpInfo(id, status) {
	layer.confirm('您确定删除此应用?<br>删除后此应用的所有信息将被删除，请您谨慎操作', {
		icon : 0,
		title : '应用删除',
		btn : [ '确定', '取消' ]
	// area : [ '300px', 'auto' ]
	}, function(index) {

	});
};

function del(id, status) {
	if (status != 1) {
		layer.confirm('您确定删除此模板?<br>删除后此模板的所有信息将被删除，请您谨慎操作', {
			icon : 0,
			title : '模版删除',
			btn : [ '确定', '取消' ]
		}, function(index) {
			$.ajax({
				url : 'smsMasterplateClient/del',
				type : 'post',
				data : {
					id : id
				},
				success : function(data) {
					if (data.statusCode == 200) {
						layer.msg('删除成功');
						search();
					} else {
						layer.msg('删除失败');
					}
				}
			});
		});
	} else {
		layer.msg("模板正在审核，不能删除");
	}
}

function subValidate(id, status) {
	if (status != 3) {
		layer.msg("您已成功提交审核信息，请耐心等待审核结果");
		return false;
	}
	$.ajax({
		url : 'smsMasterplateClient/updateStatus',
		type : 'post',
		data : {
			id : id
		},
		success : function(resultData) {
			if (resultData.statusCode == 200) {
				layer.msg("您已成功提交审核信息，请耐心等待审核结果");
				search();
			}
		}
	});
}

$("#editMasterplate").validate({
	rules : {
		title : {
			required : true
		},
		content : {
			required : true
		},
		spid : {
			required : true
		}
	},
	messages : {
		title : {
			required : "请输入模板标题"
		},
		content : {
			required : "请输入模板内容"
		},
		spid : {
			required : "请选择应用"
		}
	},
	submitHandler : function(form) {
		$.ajax({
			url : 'smsMasterplateClient/save',
			type : 'post',
			data : $('#editMasterplate').serialize(),
			success : function(data) {
				if (data.statusCode == 200) {
					layer.msg("模板保存成功！");
					$('#myModal').modal('hide');
					search();
				} else if (data.statusCode == 202) {
					layer.msg("请填写完整的数据信息！");
				} else {
					layer.msg("很遗憾，未能保存成功！");
				}
			}
		});
	}
});

function updateMasterplateConent() {
	var tailMasterId = $("#trialList").val();
	$.ajax({
		url : 'smsMasterplateClient/getTrialMasterByid',
		type : 'post',
		data : {
			id : tailMasterId
		},
		success : function(data) {
			$("#templeteId").val(data.id);
			$("#MasterTitlecontent").val(data.title);
			$("#textareaContent").html(data.content);
		}
	});
};

$("#addtrialMasterplate").validate({
	rules : {
		trialList : {
			required : true
		},
		spid : {
			required : true
		}
	},
	messages : {
		trialList : {
			required : "请选择模板"
		},
		spid : {
			required : "请选择应用"
		}
	},
	submitHandler : function(form) {
		$.ajax({
			url : 'smsMasterplateClient/saveOnTrialMaster',
			type : 'post',
			data : $('#addtrialMasterplate').serialize(),
			success : function(data) {
				if (data.statusCode == 200) {
					layer.msg("免审核模板保存成功！");
					$('#myModalDetail').modal('hide');
					search();
				} else if (data.statusCode == 202) {
					layer.msg("请填写完整的数据信息！");
				} else if (data.statusCode == 203) {
					layer.msg("该模板已经存在，请更换模板添加！");
				} else {
					layer.msg("很遗憾，未能保存成功！");
				}
			}
		});
	}
});

function search() {
	$('#example').dataTable().fnDraw(false);
};

function exportPOI() {
	window.location.href = "smsMasterplateClient/exportMasterplateListToExcel?"
			+ $("#form1").serialize();
};

function importTxt() {
	$('#spinfomodal').modal('show');
};

function downLoadTemplete(type) {
	window.location.href = "smsMasterplateClient/downloadTemplate?type=" + type;
};

$("#impotspinfoModalForm").validate({
	rules : {
		impotspidname : {
			required : true
		},
		file : {
			required : true
		},
	},
	messages : {
		impotspidname : {
			required : "请选择应用名称"
		},
		file : {
			required : "上传文件不能为空"
		},
	},
	submitHandler : function(form) {
		$.ajaxFileUpload({
			url : 'smsMasterplateClient/uploadspinfoModal',
			secureuri : false,
			fileElementId : 'file',
			dataType : "json",
			data : {
				"impotspidname" : $("#impotspidname").val()
			},
			success : function(data, status) // 服务器成功响应处理函数
			{
				$("#impotspidname").val('');
				$("#file").val('');
				layer.closeAll("loading");
				$('#spinfomodal').modal('hide');
				var fixData = JSON.parse(data);
				layer.msg(fixData.error);
				$('#example').dataTable().fnDraw(false);
			},
			error : function(data, status, e)// 服务器响应失败处理函数
			{
				layer.closeAll("loading");
				layer.msg(e);
			}
		});
	}
});
