var dataTable = {
	init : function(data) {
		if ($('#queryStartTime').val() == "") {
			$('#queryStartTime').val(getTodayDate());
		}
		if($('#queryEndTime').val() == ""){
			$("#queryEndTime").val(CurentTimes($("#queryStartTime").val(), 1));
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
								url : "consumerDetail/spConsumerList",
								dataSrc : "consumerList",
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
								aoData.sp_name = $("#sp_name").val();
								aoData.type = $("#type").val();
								aoData.queryStartTime = $("#queryStartTime")
										.val();
								aoData.queryEndTime = $("#queryEndTime").val();
								aoData.sp_service_type = $("#sp_service_type")
										.val();
							},
							serverSide : true,
							columns : [
									{
										"data" : "sp_name"
									},
									{
										"data" : "type",
										render : function(data, type, row) {
											var typeName = "";
											switch (data) {
											case 1:
												typeName = "充值";
												break;
											case 2:
												typeName = "扣除";
												break;
											case 3:
												typeName = "审核驳回";
												break;
											case 4:
												typeName = "扣除";
												break;
											case 5:
												typeName = "失败返还";
												break;
											case 6:
												typeName = "赠送";
												break;
											case 7:
												typeName = "透支";
												break;
											}
											return typeName;
										}
									},
									{
										"data" : "amount",
										render : function(data, type, row) {
											switch (row.spservicetype) {
											case 2:
												return data / 100;
												break;
											default:
												return "/";
											}

										}

									},
									{
										"data" : "charge_num",
										render : function(data, type, row) {
											switch (row.spservicetype) {
											case 2:
												return "/";
												break;
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
										"data" : "spservicetype",
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
									}, {
										"data" : "descption",
										render : function(data,type,row){
											if(row.type==1){
												return "/";
											}else{
												return  data;	
											}
										}
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
						});
	}
};
$(function() {
	$('#queryStartTime').datetimepicker({
		format : 'Y-m-d',
		timepicker : false,
		allowBlank : true,
		onChangeDateTime : function() {
			checkStartTimeMonthFiveDay("queryStartTime", "All");
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
	$('#example').dataTable().fnDraw();
}
