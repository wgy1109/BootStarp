var dataTable = {
	init : function(data) {
		if ($('#quereyStartTime').val() == "") {
			$('#quereyStartTime').val(getTodayDate());
		}
		if($('#queryEndTime').val() == ""){
			$("#queryEndTime").val(CurentTimes($("#quereyStartTime").val(), 1));
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
							ajax : {
								type : "post",
								url : "chargeRecord/chargeRecordList",
								dataSrc : "chargeList",
								data : data,
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									if (XMLHttpRequest.status == 404) {
										$("#example").html("数据库服务器通讯失败，请稍后重试！");
									}
								}

							},
							'fnServerParams' : function(aoData) {
								aoData.spId = $("#spId").val();
								aoData.alipayType = $("#alipayType").val();
								aoData.charge_type = $("#charge_type").val();
								aoData.quereyStartTime = $("#quereyStartTime")
										.val();
								aoData.queryEndTime = $("#queryEndTime").val();
								aoData.chargeId = $("#chargeId").val();
							},
							serverSide : true,
							columns : [
									{
										"data" : "id"
									},
									{
										"data" : "sp_name"
									},
									{
										"data" : "spServiceType",
										render : function(data, type, row) {
											switch (data) {
											case 1:
												return "国内短信";
											case 2:
												return "国际短信";
											case 3:
												return "语音验证码";
											default:
												return "";
											}
										}
									},
									{
										"data" : "charge_type",
										render : function(data, type, row) {
											switch (data) {
											case 1:
												return "线下充值";
											case 2:
												return "支付宝充值";
											case 3:
												return "账号余额充值";
											default:
												return "";
											}
										}
									},
									{
										"data" : "amount",
										render : function(data, type, row) {
											return data / 100;
										}
									},

									{
										"data" : "salePrice",
										render : function(data, type, row) {
											switch (row.spServiceType) {
											case 2:
												return "/";
											default:
												if (row.is_default) {
													return 0;
												} else {
													return row.price == null ? data
															: row.price;
												}

											}
										}
									},
									{
										"data" : "chargenum",
										render : function(data, type, row) {
											switch (row.spServiceType) {
											case 2:
												return "/";
											default:
												return data;
											}
										}
									},
									{
										"data" : "charge_time",
										render : function(data, type, row) {
											return data == null ? ""
													: getLocalTime(data);
										}
									}, {
										"data" : "alipayType",
										render : function(data, type, row) {
											if (data == 1) {
												return "成功";
											} else {
												return "失败";
											}
										}
									}, {
										"data" : null,
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
								targets : 9,
								render : function(a, b, c, d) {
									if (subAccountLogin == "subAccountLogin") {
										return "";
									}
									var html = "<button class='mb-sm btn btn-primary'  onclick='edit("
											+ c.sp_id + ")'>重新支付</button>";
									if (c.alipayType == 0) {
										return html;
									} else {
										return "";
									}
								}
							}

							],
						});
	}
};
$(function() {
	$('#quereyStartTime').datetimepicker({
		format : 'Y-m-d',
		timepicker : false,
		allowBlank : true,
		onChangeDateTime : function() {
			checkStartTimeMonthFiveDay("quereyStartTime", "All");
		}

	});
	$('#queryEndTime').datetimepicker({
		format : 'Y-m-d',
		timepicker : false,
		allowBlank : true,
		onChangeDateTime : function() {
			checkEndTimeMonthFiveDay("quereyStartTime", "queryEndTime");
		}

	});
	if ($.fn.dataTable.isDataTable('#example')){
		$('#example').dataTable().fnDraw(false);
	}else{
		dataTable.init();
	}
});
function edit(id) {
	goPage('chargeRecord/directPaySpInfo?spId=' + id,
			'static/js/spCharge/paySpInfo.js');
};
function search() {
	var curentTime = CurentTimes($('#quereyStartTime').val(), 7);
	if ($('#queryEndTime').val()) {
		if ($('#quereyStartTime').val() > $('#queryEndTime').val()) {
			layer.msg("结束时间不能早于开始时间");
			$('#queryEndTime').val(curentTime);
			return;
		}
		if($('#queryEndTime').val() > curentTime){
			layer.msg("查询时间段只能在7天内");
			$('#queryEndTime').val(curentTime);
			return;
		}
	}else{
		$('#queryEndTime').val(curentTime);
	}
	$('#example').dataTable().fnDraw(false);
}
function del(deleteID) {
	alert(deleteID);
	// $.ajax('servlet/DeleteUser', {
	// dataType: 'json',
	// data: {
	// userID: deleteID
	// },
	// success: function(data) {
	// if (data.success == 'true') {
	// $.modal.alert('删除成功!');
	// start = $("#sorting-advanced").dataTable().fnSettings()._iDisplayStart;
	// total =
	// $("#sorting-advanced").dataTable().fnSettings().fnRecordsDisplay();
	// window.location.reload();
	// if ((total - start) == 1) {
	// if (start > 0) {
	// $("#sorting-advanced").dataTable().fnPageChange('previous', true);
	// }
	// }
	// } else {
	// $.modal.alert('删除发生错误，请联系管理员!');
	// }
	// },
	// error: function() {
	// $.modal.alert('服务器无响应，请联系管理员!');
	// }
	// });
};