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
							bSort : false,
							bStateSave : true,
							ajax : {
								type : "post",
								url : "intersmsSendTaskClient/getMtList",
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
								aoData.drresult = $("#drresult").val();
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
																	"<tr><td colspan='7' style='text-align: right;'>"
																			+ htmlEncodeJQ(stringIntercept(arrayObj[i - 1]))
																			+ "<span class='text-danger'>（共 "
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
	layer.load(2);
	window.location.href = "intersmsSendTaskClient/exportMtListToExcel?" + $("#form1").serialize();
	closelayers = setInterval("closelayer();", 500); 
};

function closelayer(){
	$.ajax({
         type: "post",
         url: "intersmsSendTaskClient/closetype",
         success: function(data){
	            	if(data == "0"){
	 					clearInterval(closelayers);
	 					layer.closeAll();
	 				}
                  }
     });
}

function search() {
	$('#example').dataTable().fnDraw(false);
};