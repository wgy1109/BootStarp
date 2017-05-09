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
								url : "appFinanceInvoiceClient/load",
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
										"data" : "invoice_value"
									},
									{
										"data" : "invoice_status",
										render : function(data, type, row) {
											if (data == 0) {
												return "增值税普通发票";
											} else {
												return "增值税专用发票";
											}
										}
									},
									{
										"data" : "collect_address"
									},
									{
										"data" : "collect_name"
									},
									{
										"data" : "collect_phone"
									},
									{
										"data" : "invoice_create_time",
										render : function(data, type, row) {
											return data == null ? ""
													: getLocalTime(data);
										}
									},
									{
										"data" : "invoice_courier_num",
										render : function(data, type, row) {
											if (row.invoice_express_type) {
												data = findExpressType(row.invoice_express_type)
														+ ":" + data;
											}
											return data == null ? "" : data;
										}
									}, {
										"data" : "audit_status",
										render : function(data, type, row) {
											if (data == 2) {
												return "未提交";
											} else if (data == 1) {
												return "已发送";
											} else {
												return "已提交";
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
								targets : 8,
								render : function(a, b, c, d) {
									if (a.audit_status == 1) {
										var html = "<button class='mb-sm btn btn-primary' onclick='checkExpress("
												+ a.id + ")'>查看快递信息</button>";
										if (a.invoice_notes != undefined) {
											html += "<button class='mb-sm btn btn-primary' onclick='explain("
													+ a.id + ")'>原因</button>";
										}
										return html;
									}
									if (a.audit_status == 0
											&& a.invoice_notes != undefined) { // 已提交,而且S端填写了原因
										var html = "<button class='mb-sm btn btn-primary' onclick='explain("
												+ a.id + ")'>原因</button>";
										return html;
									}
									if (a.audit_status == 2) { // 未提交
										var html = "<button class='mb-sm btn btn-primary' onclick='del("
												+ a.id
												+ ","
												+ a.audit_status
												+ ")'>删除发票</button>&nbsp;<button class='mb-sm btn btn-primary' onclick='submitStatus("
												+ a.id
												+ ","
												+ a.audit_status
												+ ")'>提交发票</button>";
										return html;
									}
									return "";
								}
							} ]
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

function edit() {
	$.ajax({
		url : 'appFinanceInvoiceClient/edit',
		type : 'post',
		success : function(resultData) {
			if (resultData.resultbool) {
				layer.msg("企业信息未认证通过，不能新增索要发票");
				return;
			}
			if (resultData.voiceTitleNoFinished) {
				layer.msg("发票认证菜单下没有完善发票抬头等信息，不能新增索要发票");
				return;
			}

			$("#accountName").html(resultData.accountName);
			$("#Money").val(resultData.Money);
			$("#COLLECT_NAME").val(resultData.Map.collect_name);
			$("#COLLECT_PHONE").val(resultData.Map.collect_phone);
			$("#COLLECT_ADDRESS").val(resultData.Map.collect_address);
			if (resultData.Map.invoiceStatus == '0') {
				$("input[type=radio][name='invoiceStatus'][value='0']").attr(
						"checked", 'checked');
			} else {
				$("input[type=radio][name='invoiceStatus'][value='1']").attr(
						"checked", 'checked');
			}
			$('#myModal').modal('show');
		}

	});
};

$("#addFinanceInvoice").validate({
	rules : {
		invoiceValue : {
			required : true
		},
		COLLECT_NAME : {
			required : true
		},
		COLLECT_PHONE : {
			required : true
		},
		COLLECT_ADDRESS : {
			required : true
		}
	},
	messages : {
		invoiceValue : {
			required : "请输入发票金额"
		},
		COLLECT_NAME : {
			required : "请输入收件人姓名"
		},
		COLLECT_PHONE : {
			required : "请输入收件人电话"
		},
		COLLECT_ADDRESS : {
			required : "请输入收件人地址"
		}
	},
	submitHandler : function(form) {
		var allMoney = $("#Money").val() * 1;
		var money = $("#invoiceValue").val() * 1;
		if (money > allMoney) {
			layer.msg("开票金额不能大于可开发票金额");
			return false;
		}
		var invoicestatus = $("input[name='invoiceStatus']:checked").val();
		if (invoicestatus == 1) {
			if (money < 5000) {
				layer.msg("增值税专用发票开票金额不能少于五千元");
				return false;
			}
			$.ajax({
				url : 'appFinanceInvoiceClient/getFinanceValidate',
				type : 'post',
				success : function(resultData) {
					if (resultData.code == 201) {
						layer.msg("还没有做发票认证，请先做发票认证");
						return false;
					}
				}
			});
		} else {
			if (money < 5000) {
				layer.msg("增值税普通发票开票金额不能少于五千元");
				return false;
			}
		}
		;
		$.ajax({
			url : 'appFinanceInvoiceClient/saveFinanceInvoice',
			type : 'post',
			data : $('#addFinanceInvoice').serialize(),
			success : function(data) {
				if (data.statusCode == 200) {
					layer.msg("索要发票信息保存成功！");
					$('#myModal').modal('hide');
					search();
				} else if (data.statusCode == 202) {
					layer.msg("请填写完整的数据信息！");
				} else if (data.statusCode == 203) {
					layer.msg("增值税专用发票开票金额不能小于5000元！");
				} else if (data.statusCode == 204) {
					layer.msg("增值税普通发票开票金额不能小于5000元！");
				} else if (data.statusCode == 205) {
					layer.msg("还没有做发票认证，请先做发票认证！");
				} else {
					layer.msg("很遗憾，未能保存成功！请刷新重试 ");
				}
			}
		});
	}
});

function search() {
	$('#example').dataTable().fnDraw(false);
};

function del(id, auditStatus) {
	if (auditStatus != 2) {
		layer.msg("此发票已经提交，不能删除");
		return false;
	}
	layer.confirm('确定要删除发票吗?', function() {
		$.ajax({
			url : 'appFinanceInvoiceClient/delete',
			type : 'post',
			data : {
				id : id
			},
			success : function(resultData) {
				if (resultData.statusCode == 200) {
					layer.msg("删除发票信息结束！");
					search();
				}
			}
		});
	});
};

function submitStatus(id, auditStatus) {
	if (auditStatus != 2) {
		layer.msg("此发票已经提交，不用重新提交");
		return false;
	}
	$.ajax({
		url : 'appFinanceInvoiceClient/updateAuditStatus',
		type : 'post',
		data : {
			id : id
		},
		success : function(resultData) {
			if (resultData.statusCode == 200) {
				layer.msg("提交发票信息结束！");
				search();
			}
		}
	});
};

function changeFinanceInvoiceType() {
	var invoiceStatus = $("input[name='invoiceStatus']:checked").val(); // 0为增值税普通发票，1为增值税专用发票
	if (invoiceStatus == 1) {
		$.ajax({
			url : 'appFinanceInvoiceClient/getFinanceValidate',
			type : 'post',
			success : function(resultData) {
				if (resultData.code == 201) {
					layer.msg("请先在账号管理内完成发票认证信息");
					$("#invoiceStatus0").click();
				}
			}
		});
	}
}

function explain(id) {
	$.ajax({
		url : 'appFinanceInvoiceClient/searchFinanceInvoiceNotes?id=' + id,
		type : 'post',
		success : function(resultData) {
			$("#INVOICE_NOTES").html(resultData.invoice_notes);
			$('#explain').modal('show');
		}
	});
};
function checkExpress(id) {
	$.getJSON("appFinanceInvoiceClient/findExpress?id=" + id + '&t='
			+ Math.random(1000), function(data) {
		if (data.returnCode == '200') {
			var fixInfo = JSON.parse(data.result);
			if (fixInfo.Traces.length > 0) {
				$.each(fixInfo.Traces, function(index, data) {
					$("#expressInfoForm").append(
							"<div class='form-group' id='tempData'>"
									+ data.AcceptTime + "<br/>"
									+ data.AcceptStation + "</div>");
				})
			} else {
				if (fixInfo.Reason) {
					$("#expressInfoForm").append(
							"<div class='form-group' id='tempData'>"
									+ fixInfo.Reason + "</div>");
				}
			}
		} else {
			$("#expressInfoForm").append(
					"<div class='form-group' id='tempData'>此单无物流信息</div>");
		}
		$("#expressInfo").modal("show");
	})
}

$("#expressInfo").on("hide.bs.modal", function() {
	$(this).find("div[id='tempData']").remove();
});

function findExpressType(type) {
	switch (type) {
	case 'AJ':
		return '安捷快递';
	case 'ANE':
		return '安能物流';
	case 'AXD':
		return '安信达快递';
	case 'BQXHM':
		return '北青小红帽';
	case 'BFDF':
		return '百福东方';
	case 'BTWL':
		return '百世快运';
	case 'CCES':
		return 'CCES快递';
	case 'CITY100':
		return '城市100';
	case 'COE':
		return 'COE东方快递';
	case 'CSCY':
		return '长沙创一';
	case 'CDSTKY':
		return '成都善途速运';
	case 'DBL':
		return '德邦';
	case 'DSWL':
		return 'D速物流';
	case 'DTWL':
		return '大田物流';
	case 'EMS':
		return 'EMS';
	case 'FAST':
		return '快捷速递';
	case 'FEDEX':
		return 'FEDEX联邦(国内件）';
	case 'FEDEX_GJ':
		return 'FEDEX联邦(国际件）';
	case 'FKD':
		return '飞康达';
	case 'GDEMS':
		return '广东邮政';
	case 'GSD':
		return '共速达';
	case 'GTO':
		return '国通快递';
	case 'GTSD':
		return '高铁速递';
	case 'HFWL':
		return '汇丰物流';
	case 'HHTT':
		return '天天快递';
	case 'HLWL':
		return '恒路物流';
	case 'HOAU':
		return '天地华宇';
	case 'hq568':
		return '华强物流';
	case 'HTKY':
		return '百世快递';
	case 'HXLWL':
		return '华夏龙物流';
	case 'HYLSD':
		return '好来运快递';
	case 'JGSD':
		return '京广速递';
	case 'JIUYE':
		return '九曳供应链';
	case 'JJKY':
		return '佳吉快运';
	case 'JLDT':
		return '嘉里物流';
	case 'JTKD':
		return '捷特快递';
	case 'JXD':
		return '急先达';
	case 'JYKD':
		return '晋越快递';
	case 'JYM':
		return '加运美';
	case 'JYWL':
		return '佳怡物流';
	case 'KYWL':
		return '跨越物流';
	case 'LB':
		return '龙邦快递';
	case 'LHT':
		return '联昊通速递';
	case 'MHKD':
		return '民航快递';
	case 'MLWL':
		return '明亮物流';
	case 'NEDA':
		return '能达速递';
	case 'PADTF':
		return '平安达腾飞快递';
	case 'QCKD':
		return '全晨快递';
	case 'QFKD':
		return '全峰快递';
	case 'QRT':
		return '全日通快递';
	case 'RFD':
		return '如风达';
	case 'SAD':
		return '赛澳递';
	case 'SAWL':
		return '圣安物流';
	case 'SBWL':
		return '盛邦物流';
	case 'SDWL':
		return '上大物流';
	case 'SF':
		return '顺丰快递';
	case 'SFWL':
		return '盛丰物流';
	case 'SHWL':
		return '盛辉物流';
	case 'ST':
		return '速通物流';
	case 'STO':
		return '申通快递';
	case 'STWL':
		return '速腾快递';
	case 'SURE':
		return '速尔快递';
	case 'TSSTO':
		return '唐山申通';
	case 'UAPEX':
		return '全一快递';
	case 'UC':
		return '优速快递';
	case 'WJWL':
		return '万家物流';
	case 'WXWL':
		return '万象物流';
	case 'XBWL':
		return '新邦物流';
	case 'XFEX':
		return '信丰快递';
	case 'XYT':
		return '希优特';
	case 'XJ':
		return '新杰物流';
	case 'YADEX':
		return '源安达快递';
	case 'YCWL':
		return '远成物流';
	case 'YD':
		return '韵达快递';
	case 'YDH':
		return '义达国际物流';
	case 'YFEX':
		return '越丰物流';
	case 'YFHEX':
		return '原飞航物流';
	case 'YFSD':
		return '亚风快递';
	case 'YTKD':
		return '运通快递';
	case 'YTO':
		return '圆通速递';
	case 'YXKD':
		return '亿翔快递';
	case 'YZPY':
		return '邮政平邮/小包';
	case 'ZENY':
		return '增益快递';
	case 'ZHQKD':
		return '汇强快递';
	case 'ZJS':
		return '宅急送';
	case 'ZTE':
		return '众通快递';
	case 'ZTKY':
		return '中铁快运';
	case 'ZTO':
		return '中通速递';
	case 'ZTWL':
		return '中铁物流';
	case 'ZYWL':
		return '中邮物流';
	case 'AMAZON':
		return '亚马逊物流';
	case 'SUBIDA':
		return '速必达物流';
	case 'RFEX':
		return '瑞丰速递';
	case 'QUICK':
		return '快客快递';
	case 'CJKD':
		return '城际快递';
	case 'CNPEX':
		return 'CNPEX中邮快递';
	case 'HOTSCM':
		return '鸿桥供应链';
	case 'HPTEX':
		return '海派通物流公司';
	case 'AYCA':
		return '澳邮专线';
	case 'PANEX':
		return '泛捷快递';
	case 'PCA':
		return 'PCA Express';
	case 'UEQ':
		return 'UEQ Express';
	default:
		return '未知';
	}
}
