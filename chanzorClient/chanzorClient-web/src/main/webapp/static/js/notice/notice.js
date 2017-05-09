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
								url : "notice/load",
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
								aoData.INVOICE_STATUS = $("#INVOICE_STATUS")
										.val();
							},
							serverSide : true,
							columns : [
									{
										"data" : "noticeName"
									},
									{
										"data" : "userName"
									},
									{
										"data" : "createTime",
										render : function(data, type, row) {
											return data == null ? ""
													: getLocalTime(data);
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
