var dataTable = {
	init : function(data) {
		if($('#queryStartTime').val() == ""){
			$('#queryStartTime').val(getTodayDate());
		}
		$('#example')
				.dataTable(
						{
							serverSide : true,
							processing : true,
							searching : false,
							bDeferRender : true,
							bLengthChange : false,
							bPaginate:false,
							bSort : false,
							bStateSave : true,
							ajax : {
								type : "post",
								url : "intersmspriceClient/load",
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
								aoData.country_cn = $("#country_cn").val();
								aoData.country_en = $("#country_en").val();
								aoData.country_code = $("#country_code").val();
							},
							serverSide : true,
							columns : [
									{
										"data" : "country_cn",
										render : function(data, type, row) {
											return data == null ? "" : data;
										}
									}, {
										"data" : "country_en",
										render : function(data, type, row) {
											return data == null ? "" : data;
										}
									}, {
										"data" : "country_code",
										render : function(data, type, row) {
											return data == null ? "" : data;
										}
									}, {
										"data" : "price",
										render : function(data, type, row) {
											return data == null ? "" : Math.round(data)/100;
										}
									} ],
							"language" : {
								"lengthMenu" : "_MENU_ 条记录每页",
								"zeroRecords" : "没有找到记录",
								"info" : "显示第_START_到第_END_条，筛选之后得到_END_条 ",
								"infoEmpty" : "无记录",
								"infoFiltered" : "(从 _MAX_ 条记录过滤)",
								"paginate" : {
									"previous" : "上一页",
									"next" : "下一页"
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
			checkStartTimeMonth("queryStartTime", "ALL");
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

function exportPOI() {
	window.location.href = "intersmspriceClient/exportPriceListToExcel?"
			+ $("#form1").serialize();
};

function search() {
	$('#example').dataTable().fnDraw(false);
};