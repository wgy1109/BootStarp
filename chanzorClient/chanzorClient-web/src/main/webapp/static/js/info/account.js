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
								url : "account/accountList",
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
								aoData.chargeType = $("#chargeType").val();
							},
							serverSide : true,
							columns : [
									{
										"data" : "chargeDate",
										render : function(data, type, row) {
											return data == null ? ""
													: getLocalTime(data);
										}
									}, {
										"data" : "chargeNum",
										render : function(data, type, row) {
											return data;

										}
									}, {
										"data" : "chargeType",
										render : function(data, type, row) {
											switch (data) {
											case 1:
												return "余额充值";
											case 2:
												return "应用充值";
											case 3:
												return "线下充值";
											default:
												return "";

											}
										}
									}, {
										"data" : "spName"
									}, {
										"data" : "chargeStatus",
										render : function(data, type, row) {
											if (data == 0) {
												return "失败";
											} else {
												return "成功";
											}
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
})
function outLineChargeAccount() {
	layer.open({
		type : 1,
		title : false,
		closeBtn : 0,
		area : [ '481px', '601px' ],
		skin : 'layui-layer-nobg', // 没有背景色
		shadeClose : true,
		content : $('#outline')
	});

}
jQuery.validator.addMethod("positiveinteger", function(value, element) {
	var aint = parseInt(value);
	return aint > 0 && (aint + "") == value;
}, "Please enter a valid number.");
$("#chargeNumForm").validate({
	errorPlacement : function(error, element) {
		error.appendTo(element.parent());
	},
	rules : {
		chargeNum : {
			required : true,
			positiveinteger : true,
			min:10000
		},

	},
	messages : {
		chargeNum : {
			required : '充值金额必填',
			positiveinteger : '充值金额为正整数',
			min:"单次充值最少为10000元"
		},
	},
	submitHandler : function(form) {
		var info = $("#chargeNumForm").serialize();
		window.open("account/chargeAccount?" + info);
	}
});
function findAccountBalance() {
	$.get("account/findAccountBalance", function(data) {
		if (data.balance != null) {
			$("#accountBalance").html(data.balance/100 + "元");
			layer.msg("刷新成功");
		} else {
			layer.msg("刷新失败");
		}

	})
}
function search() {
	$('#example').dataTable().fnDraw(false);
}