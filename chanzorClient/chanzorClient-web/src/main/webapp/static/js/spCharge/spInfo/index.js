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
								url : "spInfoList",
								dataSrc : "spInfo",
								data : data,
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									if (XMLHttpRequest.status == 404) {
										$("#example").html("数据库服务器通讯失败，请稍后重试！");
									}
								}

							},
							'fnServerParams' : function(aoData) {
								aoData.sp_name = $("#sp_name").val();
								aoData.sp_industry = $("#sp_industry").val();
								aoData.sp_through_status = $(
										"#sp_through_status").val();
							},
							serverSide : true,
							columns : [
									{
										"data" : "sp_name"
									},
									{
										"data" : "sp_industry",
										render : function(data, type, row) {
											switch (data) {
											case 1:
												return "电子商务";
											case 2:
												return "金融";
											case 3:
												return "在线社区";
											case 4:
												return "房地产";
											case 5:
												return "医疗";
											case 6:
												return "交通汽车";
											case 7:
												return "旅游";
											case 8:
												return "游戏";
											case 9:
												return "教育";
											case 10:
												return "IT硬件";
											case 11:
												return "IT软件服务";
											case 12:
												return "文化出版";
											case 13:
												return "生活信息";
											case 14:
												return "其他";
											default:
												return "";
											}
										}
									},
									{
										"data" : "leftover_num"
									},
									{
										"data" : "whiteCount"
									},
									{
										"data" : "masterplateNum"
									},
									{
										"data" : "createTime",
										render : function(data, type, row) {
											return new Date(parseInt(data))
													.toLocaleString();
										}
									}, {
										"data" : "sp_through_status",
										render : function(data, type, row) {
											switch (data) {
											case 0:
												return "未上线";
											case 1:
												return "上线";
											case 3:
												return "申请上线中";
											case 22:
												return "未通过";
											default:
												return "";
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
								targets : 7,
								render : function(a, b, c, d) {
									var html = "<button class='mb-sm btn btn-primary'  onclick='redirectpay("
											+ a.spid
											+ ")'>充值</button>&nbsp;&nbsp;&nbsp;&nbsp;<button class='mb-sm btn btn-primary' onclick='spInfoView("
											+ a.spid + ")'>管理</button>";
									return html;
								}
							}

							],
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
function search() {
	$('#example').dataTable().fnDraw(false);
}
function redirectpay(id) {
	goPage("chargeRecord/directPaySpInfo?spId=" + id);
};
function spInfoView(id) {
	goPage("spInfo/showSpInfoView?spId=" + id);

};
$("#insSpInfo").validate({
	rules : {
		sp_name : {
			required : true,
			maxlength : 10,
		},
		sp_type : {
			min : 1
		},
		sp_industry : {
			min : 1
		},
		sp_website : {
			required : true
		}
	},
	messages : {
		sp_name : {
			required : "短信条数必填",
			maxlength : '短息条数最大长度为10',
		},
		sp_type : {
			min : "应用名称必须选择"
		},
		sp_industry : {
			min : "应用名称必须选择"
		},
		sp_website : {
			required : "优惠券必须填写"
		}
	},
	submitHandler : function(form) {
		form.submit();
	}
});
function addSpInfo() {
	$("#myModal").modal("show");
}