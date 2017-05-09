var dataTable = {
	init : function(data) {
		if($('#queryStartTime').val() == ""){
			$('#queryStartTime').val(getTodayDate());
		}
		if($('#queryEndTime').val() == ""){
			$("#queryEndTime").val(CurentTimes($("#queryStartTime").val(), 1));
		}
		
		$('#example').dataTable(
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
								url : "smsSendTaskClient/getMtList",
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
								aoData.queryStartTime = getDateStringTimg($("#queryStartTime").val());
								aoData.queryEndTime = getDateStringTimg($("#queryEndTime").val());
								aoData.mobile = $("#mobile").val();
								aoData.content = $("#messagecontent").val();
								aoData.submittedState = $("#submittedState").val();
								aoData.drresult = $("#drresult").val();
								aoData.spName = $("#spName").val();
							},
							serverSide : true,
							columns : [
									{
										"data" : "package_id",
										render : function(data, type, row) {
											return data == null ? "" : data;
										}
									}, {
										"data" : "user_sp_name",
										render : function(data, type, row) {
											return data == null ? "" : data;
										}
									}, {
										"data" : "mobile",
										render : function(data, type, row) {
											return data == null ? "" : data;
										}
									}, {
										"data" : "status",
										render : function(data, type, row) {
											return data == null ? "" : data;
										}
									}, {
										"data" : "send_time",
										render : function(data, type, row) {
											return data == null ? "" : getLocalTime(data);
										}
									}, {
										"data" : "dr_result",
										render : function(data, type, row) {
											return data == null ? "" : data;
										}
									}, {
										"data" : "dr_reason",
										render : function(data, type, row) {
											return data == null ? "" : data;
										}
									}, {
										"data" : "dr_time",
										render : function(data, type, row) {
											return data == null ? "" : getLocalTime(data);
										}
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
							fnDrawCallback : function(a, b, c, d) {
								var table = $(this);
								var arrayObj = new Array();
								$.each($(a).attr("json").data, function(i, e) {
									arrayObj.push(e.message_content);
								})
								if(arrayObj.length>0){
									$.each(
											table.find("tr"),
											function(i, e) {
												if (i != 0) {
													$(this)
															.after(
																	"<tr><td colspan='8' style='text-align: right;'>"
																			+ htmlEncodeJQ(stringIntercept(arrayObj[i - 1]))
																			+ "<span class='text-danger' >（共 "
																			+ arrayObj[i - 1].length
																			+ " 字 ）</span></td></tr>")
												}
											})
								}
							}
						});
	}
};

$(function() {
	$('#queryStartTime').datetimepicker({
		format : 'Y-m-d',
		timepicker : false,
		allowBlank : true,
		onChangeDateTime : function() {
			checkStartTimeMonthFiveDay("queryStartTime", "ALL");
		}

	});
	$('#queryEndTime').datetimepicker({
		format : 'Y-m-d',
		timepicker : false,
		allowBlank : true,
		onChangeDateTime : function() {
			checkEndTimeMonthFiveDay("queryStartTime", "queryEndTime");
		}

	});
	if ($.fn.dataTable.isDataTable('#example')){
		$('#example').dataTable().fnDraw(false);
	}else{
		dataTable.init();
	}
});

function exportPOI() {
	layer.confirm('确认要导出短信明细数据吗？<br>(注：客户服务平台最多可导出65000条数据，如需导出更多，请联系客服)', {
		  btn: ['确认导出','取消导出'] //按钮
		}, function(){
			layer.load(2);
			window.location.href = "smsSendTaskClient/exportMtListToExcel?" + $("#form1").serialize();
			closelayers = setInterval("closelayer();", 500); 
		}, function(){
		});
};

function closelayer(){
	$.ajax({
         type: "post",
         url: "smsSendTaskClient/closetype",
         success: function(data){
	            	if(data == "0"){
	 					clearInterval(closelayers);
	 					layer.closeAll();
	 				}
                  }
     });
}

function showDrResult(){
	if($('#submittedState').val() == '2'){
		$("#div_drResult").css("display", "");
	}else{
		$("#drresult").prop("selectedIndex",0);
		$("#div_drResult").css("display", "none");
	}
}

function search() {
	var curentTime = CurentTimes($('#queryStartTime').val(), 7);
	if ($('#queryEndTime').val()) {
		if ($('#queryStartTime').val() > $('#queryEndTime').val()) {
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
};