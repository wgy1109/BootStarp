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
								url : "InsideLetter/insideList",
								dataSrc : "letter",
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
										"data" : null,
									},
									{
										"data" : "content",
										"sWidth" : "60%",
										render : function(a, b, c) {
											if (c.status == 0) {
												return "<span id='"
														+ c.id
														+ "' style='font-weight:bold;'>"
														+ htmlEncodeJQ(a) + "</span>";
											} else {
												return htmlEncodeJQ(a);
											}
										}
									},
									{
										"data" : "create_time",
										render : function(data, type, row) {
											return data == null ? ""
													: getLocalTime(data);
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
								"processing" : "处理中...",
								"paginate" : {
									"previous" : "上一页",
									"next" : "下一页"
								}
							},
							columnDefs : [
									{
										targets : 3,
										render : function(a, b, c, d) {
											var html = "<button class='mb-sm btn btn-primary'  onclick=findLetter("
													+ a.id
													+ ")>查看详情</button>&nbsp;&nbsp;&nbsp;<button class='mb-sm btn btn-primary'  onclick='delLetter("
													+ a.id + ")'>删除</button>";
											return html;
										}
									},
									{
										targets : 0,
										render : function(a, b, c, d) {
											var html = " <label class='checkbox-inline c-checkbox'>"
													+ "<input id='taskids' value='"
													+ a.id
													+ "' name='taskids' type='checkbox'>"
													+ "<span class='fa fa-check'></span></label>";
											return html;
										}
									} ],
						});
	}
};
$(function() {
	$.post("InsideLetter/checkAllLetter", function(data) {
		if (data == 'success') {
			$("#unReadNum").hide();
			if ($.fn.dataTable.isDataTable('#example')){
				$('#example').dataTable().fnDraw(false);
			}else{
				dataTable.init();
			}
		}
	})
});
function search() {
	$('#example').dataTable().fnDraw(false);
}
function selectAllAuth() {
	var ischecked = document.getElementById("sltAll").checked;
	if (ischecked) {
		checkallbox();
	} else {
		discheckallbox();
	}
}
function checkallbox() {
	var boxarray = document.getElementsByName("taskids");
	for (var i = 0; i < boxarray.length; i++) {
		boxarray[i].checked = true;
	}
}

function discheckallbox() {
	var boxarray = document.getElementsByName("taskids");
	for (var i = 0; i < boxarray.length; i++) {
		boxarray[i].checked = false;
	}
}
function delLetter(ids) {
	var index = 0;
	if (!ids) {
		var ids = '';
		if ($("#taskids:checked").length > 0) {
			for (var i = 0; i < $("#taskids:checked").length; i++) {
				ids += $($("#taskids:checked")[i]).val() + ',';
				index++;
			}
		} else {
			layer.msg('没有选中内容！');
			return;
		}
	} else {
		index = 1;
	}
	$.ajax({
		url : 'InsideLetter/delLetter?ids=' + ids,
		type : "POST",
		success : function(data) {
			var result = eval(data);
			if (result == '200') {
				layer.msg('操作成功');
				$('#example').dataTable().fnDraw(false);
			} else {
				layer.msg('操作失败请重试！');
			}
		}
	});
};
function findLetter(id) {
	$.ajax({
		url : 'InsideLetter/updUnRead?id=' + id,
		type : "POST",
		success : function(data) {
			layer.alert(htmlEncodeJQ(data), {
				skin : 'layui-layer-molv', // 样式类名
				closeBtn : 0,
				end : function() {
					$("#" + id).removeAttr("style");
				}
			});
		}
	});

}