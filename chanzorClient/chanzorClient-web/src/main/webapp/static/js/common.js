/**
 * LP
 */
/**
 * 分页执行sql 如果为form提交则取form取第一个表单
 * 
 */
function goPage(page) {
	var form = document.forms[0];
	if (form) {
		var pageInput = document.createElement("input");
		pageInput.name = "currentPage";
		pageInput.value = page;
		pageInput.type = "hidden";
		var pageSizeInput = document.createElement("input");
		pageSizeInput.name = "pageSize";
		pageSizeInput.type = "hidden";
		var pgsize = $("#pageSize").val();
		if (!pgsize)
			pgsize = 10;
		pageSizeInput.value = pgsize;
		form.appendChild(pageInput);
		form.appendChild(pageSizeInput);
		form.submit();
	} else {
		var pgsize = $("#pageSize").val();
		if (!pgsize)
			pgsize = 10;
		var url = window.location + '';
		if (url.indexOf("?") > -1) {
			if (url.indexOf("&currentPage=") > -1) {
				var reg = /currentPage=\d*/g;
				url = url.replace(reg, "currentPage=" + page + "&pageSize="
						+ pgsize);
			} else {
				url += "&currentPage=" + page + "&pageSize=" + pgsize;
			}
		} else {
			url += "?currentPage=" + page + "&pageSize=" + pgsize;
		}
		window.location.href = url;
	}
}
var oprHTML = '';
function fillTableData(url, formId, tableId, opr1) {
	var thead = $("#" + tableId).children("thead");
	var th = $(thead).children("tr").children("th");
	$("#" + tableId).children("tbody").html(
			'<td style="text-align:center" colspan="' + th.length
					+ '">Loading...</td>');
	oprHTML = opr1;
	$
			.ajax({
				url : url,
				data : $("#" + formId).serialize(),
				type : 'post',
				success : function(result) {
					var data = result.data;
					var page = result.page;
					var checkbox = result.checkbox;
					var statistics = result.statistics;
					if (statistics) {
						$("#statistics").html(statistics);
						;
					}
					var pageHTML = '';
					if (page)
						createPage(page, url, formId, tableId);
					var tbody = '';
					for (var k = 0; k < data.length; k++) {
						opr = opr1;
						var item = data[k];
						console.log(k+" --- "+item);
						tbody += '<tr>';
						var box = 0;
						if (checkbox) {
							box = 1;
							if (checkbox != true) {
								tbody += '<td><input id="taskids" name="taskids" type="checkbox" value="'
										+ item[checkbox]
										+ '"  onclick="SelectOneAuth()" /></td>';
							} else {
								tbody += '<td><input id="taskids" name="taskids" type="checkbox" value="'
										+ item["ID"]
										+ '"  onclick="SelectOneAuth()" /></td>';
							}
						}
						var flag = true;
						if (!opr || opr.trim() == '') {
							flag = false;
						}
						if (!flag) {
							for (var i = box; i < th.length - 1; i++) {
								var t = th[i];
								var dataType = $(t).attr("dataType");
								var d = $(t).attr('data');
								var v;
								if (d.split(".").length > 1) {
									var ds = d.split('.');
									d = ds[0];
									v = item[ds[0]][ds[1]];
								} else {
									v = item[d];
								}
								var c = $(t).attr('dataChange');
								var l = $(t).attr("dataLength");
								if (typeof (c) != 'undefined') {
									c = eval("(" + c + ")");
									for ( var key in c) {
										if (v == key) {
											v = c[key];
										}
									}
								}
								if (typeof (l) != 'undefined') {
									if (v.length > l) {
										v = v.substring(0, l) + "…";
									}
								}
								if (dataType == 'img') {
									tbody += '<td><img onclick="showImage(this)" style="height:50px;width:50px;" onerror=" $(this).attr(\'src\',\''
											+ basePath
											+ 'static/img/404img.png\');" src="'
											+ basePath
											+ isEmpty(v)
											+ '"/></td>';
								} else if (dataType == 'time') {
									var t = getLocalTime(v);
									tbody += '<td>' + t + '</td>';
								} else {
									tbody += '<td>' + isEmpty(v) + '</td>';
								}
							}
							tbody += '<td></td></tr>';
						} else {
							for (var i = box; i < th.length - 1; i++) {
								var t = th[i];
								var dataType = $(t).attr("dataType");
								var d = $(t).attr("data");
								var v;
								if (d.split(".").length > 1) {
									var ds = d.split('.');
									d = ds[0];
									v = item[ds[0]][ds[1]];
								} else {
									v = item[d];
								}
								var l = $(t).attr("dataLength");
								var c = $(t).attr('dataChange');
								if (typeof (c) != 'undefined') {
									c = eval("(" + c + ")");
									for ( var key in c) {
										if (v == key) {
											v = c[key];
										}
									}
								}
								if (typeof (l) != 'undefined') {
									if (v.length > l) {
										v = v.substring(0, l) + "…";
									}
								}
								if (dataType == 'img') {
									tbody += '<td><img onclick="showImage(this)" style="height:50px;width:50px;" onerror=" $(this).attr(\'src\',\''
											+ basePath
											+ 'static/img/404img.png\');" src="'
											+ basePath
											+ isEmpty(v)
											+ '"/></td>';
								} else if (dataType == 'time') {
									var t = getLocalTime(v);
									tbody += '<td>' + t + '</td>';
								} else {
									tbody += '<td>' + isEmpty(v) + '</td>';
								}
							}
							var reg = /{([^)]*?)}/g;
							var arr = opr.match(reg);
							for (var j = 0; j < arr.length; j++) {
								var p = arr[j];
								var ds = arr[j].replace("{", "").replace("}",
										"");
								if (ds.split(".").length > 1) {
									var dss = ds.split(".");
									v = item[dss[0]][dss[1]];
								} else {
									v = item[ds];
								}
								v = v == 0 ? '0' : v; // 当数据值为 int 0
								// 时，数据内容无法替换，此处把int 0
								// 修改为string 0
								if (v) {
									opr = opr.replace(p, v);
								}
							}
							tbody += '<td>' + opr + '</td>';
							tbody += '</tr>';
						}
					}
					if (tbody == '') {
						tbody += '<td style="text-align:center" colspan="'
								+ th.length + '">当前无数据数据</td>';
					}
					$("#" + tableId).children("tbody").html(tbody);
					$("#pageDIV").html(pageHTML);
				}
			});
}
function isEmpty(str) {
	if (typeof (str) == 'undefined' || str == null)
		return '';
	else
		return str;
}

// 传入时间，返回时间格式yyyy-MM-dd HH:mm:ss(24小时制)
function getLocalTime(nS) {
	if (isEmpty(nS) == '') {
		return '';
	}
	var date = new Date(nS);
	Y = date.getFullYear() + '-';
	M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
			.getMonth() + 1)
			+ '-';
	D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
	h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
	m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes())
			+ ':';
	s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
	var b = Y + M + D + h + m + s;
	return b;
}

// 传入时间，返回时间格式yyyy-MM-dd
function getDateTime(nS) {
	if (isEmpty(nS) == '') {
		return '';
	}
	var date = new Date(nS);
	Y = date.getFullYear() + '-';
	M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
			.getMonth() + 1)
			+ '-';
	D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
	var b = Y + M + D;
	return b;
}

function getTodayDate() {
	var mydate = new Date();
	var result = mydate.getFullYear()
			+ "-"
			+ (mydate.getMonth() + 1 < 10 ? '0' + (mydate.getMonth() + 1)
					: mydate.getMonth() + 1)
			+ "-"
			+ (mydate.getDate() < 10 ? '0' + mydate.getDate() : mydate
					.getDate());
	return result;
}

function getDateStringTimg(formdata) {
	if (formdata != '' && formdata != null) {
		var result = formdata.replace(/\-/g, "").substr(2) + "000000000000";
		return result;
	} else {
		return "";
	}
}

/*
 * <div class="row"><div class="col-sm-6"><div class="dataTables_info">显示 1 到
 * 10 项，共 23 项</div></div><div class="col-sm-6"> <div
 * class="dataTables_paginate paging_simple_numbers"><ul class="pagination"><li class="paginate_button previous disabled"><a
 * disabled="disabled" href="javascript:void(0)">上一页</a> </li><li class="paginate_button active"><a
 * href="javascript:void(0)">1</a></li><li class="paginate_button"><a
 * href="javascript:goPage(2)">2</a></li><li class="paginate_button"><a
 * href="javascript:goPage(3)">3</a></li><li class="paginate_button next "><a
 * href="javascript:goPage(2)">下一页</a></li></ul></div></div></div>
 * "javascript:goPageAjax(' + (currPage - 1) + ',\'' + url + '\',\'' + formId +
 * '\',\'' + tableId + '\')"
 * 
 */
function createPage(page, url, formId, tableId) {
	var currentPage = page.currentPage;
	var totalPage = page.totalPage;
	var pageSize = page.pageSize;
	var totalSize = page.totalSize;
	var pageHTML = '<div class="row" id="pageDiv"><div class="col-sm-6"><div class="dataTables_info">显示 '
			+ (pageSize * (currentPage - 1) + 1)
			+ ' 到 '
			+ (totalSize > pageSize ? pageSize * currentPage : totalSize)
			+ ' 项，共 '
			+ totalSize
			+ ' 项 ，共'
			+ totalPage
			+ '页</div></div><div class="col-sm-6">	<div class="dataTables_paginate paging_simple_numbers"><ul class="pagination">';
	if (currentPage > 1) {
		pageHTML += ("<li><a href=\"javascript:goPageAjax(1,'"
				+ url
				+ "','"
				+ formId
				+ "','"
				+ tableId
				+ "')\">首页</a></li><li class=\"paginate_button previous \"><a href=\"javascript:goPageAjax("
				+ (currentPage - 1) + ",'" + url + "','" + formId + "','"
				+ tableId + "')\">上一页</a>	</li>");
	} else {
		pageHTML += ("<li><a>首页</a></li><li class=\"paginate_button previous disabled\"><a href=\"javascript:void(0)\" disabled=\"disabled\">上一页</a>	</li>");
	}
	var begin = ((currentPage - 5) >= 0 ? (currentPage - 5) : 0);
	var end = (totalPage >= (currentPage + 5) ? (currentPage + 5) : totalPage);
	end = (end - begin) < 10 ? ((totalPage - end) > 0 ? 10 : totalPage)
			: (end > totalPage ? totalPage : end);
	begin = (end - begin) < 10 ? (begin - (10 - (end - begin))) : begin;
	begin = begin < 0 ? 0 : begin;
	for (var i = begin; i < end; i++) {
		if (currentPage == (i + 1))
			pageHTML += ("<li class=\"paginate_button active\" style=\"color:#1ab394\"><a href=\"javascript:void(0)\">"
					+ (i + 1) + "</a>");
		else
			pageHTML += ("<li class=\"paginate_button\"><a href=\"javascript:goPageAjax("
					+ (i + 1)
					+ ",'"
					+ url
					+ "','"
					+ formId
					+ "','"
					+ tableId
					+ "')\">" + (i + 1) + "</a>");
	}
	if (currentPage >= totalPage)
		pageHTML += ("<li class=\"paginate_button next disabled\"><a   	href=\"javascript:void(0)\" >下一页</a><li><a href=\"javascript:goPageAjax("
				+ totalPage + ",'" + url + "','" + formId + "','" + tableId + "')\">末页</a></li>");
	else
		pageHTML += ("<li class=\"paginate_button next \"><a 	href=\"javascript:goPageAjax("
				+ (currentPage + 1)
				+ ",'"
				+ url
				+ "','"
				+ formId
				+ "','"
				+ tableId
				+ "')\">下一页</a><li><a href=\"javascript:goPageAjax("
				+ totalPage + ",'" + url + "','" + formId + "','" + tableId + "')\">末页</a></li>");
	pageHTML += ("</ul>");
	pageHTML += ("</div></div></div>");
	pageHTML += '';
	$("#pageDiv").remove();
	$("#" + tableId).after(pageHTML);
	return pageHTML;
}
function goPageAjax(page, url, formId, tableId) {
	var pageInput = $("input[name=currentPage]").val();
	if (!pageInput) {
		$('<input name="currentPage" type="hidden" />').appendTo(
				$("#" + formId));
	}
	$("input[name=currentPage]").val(page);
	fillTableData(url, formId, tableId, oprHTML);
}
var imgIndex;
function showImage(obj) {
	var scrnH = $(document).height() - 200;
	var scrnW = $(document).width() - 400;
	var img = new Image();
	img.src = $(obj).attr("src");
	var hight = img.height;
	var width = img.width;
	if (width > hight) {
		var per = (hight / width);
		if (width > scrnW) {
			width = scrnW;
			hight = width * per;
			if (hight > scrnH) {
				hight = scrnH;
				width = hight / per;
			}
		}
	} else {
		var per = width / hight;
		if (hight > scrnH) {
			hight = scrnH;
			width = hight * per;
		}
	}
	// if ($("#hiddenImg").attr("src")) {
	// $("#hiddenImg").attr("src", $(obj).attr("src"));
	// $("#hiddenImg").width(width);
	// $("#hiddenImg").height(hight);
	// } else {
	//		
	// }
	$('#hiddenImg').remove();
	$("body").append(
			"<img src='" + $(obj).attr("src")
					+ "' id='hiddenImg' style='display:none;width:" + width
					+ "px;height:" + hight + "px;' alt='图片预览'>");
	if ($('#hiddenImg')) {
		imgIndex = layer.open({
			type : 1,
			title : false,
			closeBtn : true,
			area : [ width, hight ],
			skin : 'layui-layer-nobg', // 没有背景色
			shadeClose : true,
			content : $("#hiddenImg")
		});
	}
}
var loadLayer;
// url, arryT, title, func, data
function ajaxLoadLayer(option) {
	var title = option.title;
	var url = option.url;
	var columns = option.columns;
	var submethod = option.submethod;
	var data = option.data;
	$
			.ajax({
				url : url,
				data : data,
				type : 'post',
				success : function(result) {
					var html = '<div class="row">\
								            <div class="col-sm-12">\
							                <div class="ibox float-e-margins">\
							                    <div class="ibox-title">\
							                        <div class="ibox-tools">\
							                        </div>\
							                    </div>\
						                    <div class="ibox-content">\
						                        <form class="form-horizontal" method="post"  id="subForm" >';

					if (result.ID) {
						html += '<input name="id" id="appid" type="hidden" value="'
								+ isEmpty(result.ID) + '">';
					}
					var name, field, type, value;
					var t = new Array();
					var timeOption = '';
					for (var k = 0; k < columns.length; k++) {
						name = columns[k].name;
						field = columns[k].field;
						type = columns[k].type;
						value = columns[k].value;
						var inputs = '<input type="text" class="form-control" checktype="require" id="'
								+ name.toUpperCase()
								+ '" name="'
								+ name.toUpperCase()
								+ '" value="'
								+ isEmpty(result[name]) + '"  />';
						if (type && type == 'img') {
							inputs = '<div id="imgDIV"  style="width:100px;"><img src= "'
									+ result[name]
									+ '" onclick="showImage(this)" onerror=" $(this).attr(\'src\',\''
									+ basePath
									+ 'static/img/404img.png\');"/></div><input type="file" name="'
									+ name
									+ '" id = "'
									+ name
									+ '" onchange="previewImage(this,\'imgDIV\')" >';
						}
						if (t[2] && t[2] == 'textarea') {
							inputs = '<textarea name="' + t[1]
									+ '" class="form-control"></textarea>';

						}
						if (type && type == 'select' && value) {
							var v = isEmpty(result[name]);
							inputs = '<select name="account" class="form-control" id="'
									+ name + '">';
							for (var j = 0; j < value.length; j++) {
								var isSelect = "";
								if (v == value[j].key)
									isSelect = 'selected="selected"';
								inputs += '<option value="' + value[j].value
										+ '"  ' + isSelect + '>' + value[j].key
										+ '</option>';
							}
							inputs += '</select>';
						}
						if (type && type == 'textarea') {
							inputs = '<textarea name="' + name
									+ '" class="form-control">'
									+ isEmpty(result[name]) + '</textarea>';
						}
						if (type && type == 'time') {
							inputs = '<input id="'
									+ name
									+ 'time"  readonly class="form-control"  name="'
									+ name + '" value="'
									+ isEmpty(result[name]) + '" />';
							t.push(name + 'time');
							timeOption = columns[k].timeOption;
						}
						if (type && type == 'file') {
							inputs = '<input type="file" id="' + name
									+ '"  class="form-control"  name="' + name
									+ '" />';
						}
						html += '  <div class="form-group">\
					                            <label class="col-sm-2 control-label">'
								+ field
								+ '</label>\
					                            <div class="col-sm-9"  >\
					                                '
								+ inputs
								+ '\
					                            </div>\
					                        </div>';
					}
					html += '	<div class="hr-line-dashed"></div>\
						                        <div class="form-group">\
						                    <div class="col-sm-4 col-sm-offset-4">\
						                        <button type="button" class="btn btn-primary" onclick="'
							+ submethod
							+ '">保存内容</button>\
						                        <button type="button" class="btn btn-white" onclick="layer.close (loadLayer);">取消</button>\
						                    </div>\
						                </div>\
						            </form>\
						        </div>\
						    </div>\
						</div>\
						</div>';
					loadLayer = layer.open({
						type : 1,
						title : title,
						zIndex : 8888,
						shadeClose : true,
						area : [ '60%', '70%' ],
						content : html,
						success : function() {
							timeinit(t, timeOption);
						}
					});
				}
			});
}

function timeinit(ts, timeOption) {
	if (!timeOption || timeOption == '') {
		timeOption = {
			format : 'Y-m-d',
			timepicker : false
		};
	}
	for (var i = 0; i < ts.length; i++) {
		$('#' + ts[i]).datetimepicker(timeOption);
	}
}

function clacImgZoomParam(maxWidth, maxHeight, width, height) {
	var param = {
		width : width,
		height : height,
		top : 0,
		left : 0
	};

	if (width > maxWidth || height > maxHeight) {
		rateWidth = width / maxWidth;
		rateHeight = height / maxHeight;

		if (rateWidth > rateHeight) {
			param.width = maxWidth;
			param.height = height / rateWidth;
		} else {
			param.width = width / rateHeight;
			param.height = maxHeight;
		}
	}

	param.left = (maxWidth - param.width) / 2;
	param.top = (maxHeight - param.height) / 2;

	return param;
}
/**
 * 全局验证
 */
function formValidate(formId) {
	var inputs = $("#" + formId).find("input");
	alert(inputs.length);
	for (var i = 0; i < inputs.length; i++) {
		var o = inputs[i];
		$(o).live("blur", function() {
			var t = $(o).attr('checktype');
			alert(t);
			var v = $(o).val();
			if (t && t == 'req') {
				$(this).css("border", "1px solid red");
			} else {
				$(this).css("border", "1px solid yellow");
			}
		});
	}
	return true;
}
var valFlag = true;
var mobileFlag = true;
var emailFlag = true;
var idFlag = true;
function checkInputType() {
	$("input").each(function() {
		$(this).blur(function() {
			var checkType = $(this).attr("checktype");
			var val = $(this).val();
			if (checkType == 'mobile') {
				var reg = /^1\d{10}$/;
				if (!reg.test(val)) {
					layer.msg("手机号格式错误");
					mobileFlag = false;
					valFlag = mobileFlag && emailFlag && idFlag;
					$(this).css("border", "1px solid red");
					return;
				} else {
					mobileFlag = true;
					valFlag = mobileFlag && emailFlag && idFlag;
					$(this).css("border", "1px solid #ccc");
					return;
				}
			}
			if (checkType == 'email') {
				var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
				if (!reg.test(val)) {
					layer.msg("邮箱格式错误");
					emailFlag = false;
					valFlag = mobileFlag && emailFlag && idFlag;
					$(this).css("border", "1px solid red");
					return;
				} else {
					emailFlag = true;
					valFlag = mobileFlag && emailFlag && idFlag;
					$(this).css("border", "1px solid #ccc");
					return;
				}
			}
			if (checkType == "idcard") {
				var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
				if (!reg.test(val)) {
					layer.msg("身份证号码格式错误");
					idFlag = false;
					valFlag = mobileFlag && emailFlag && idFlag;
					$(this).css("border", "1px solid red");
					return;
				} else {
					idFlag = true;
					valFlag = mobileFlag && emailFlag && idFlag;
					$(this).css("border", "1px solid #ccc");
					return;
				}
			}
		});
	});
}

function exportPOI(tableId, formId, mapper, title) {
	var tb = $('#' + tableId);
	var headers = '', columns = '';
	tb.find('th').each(function(index) {
		if (index == 0) {
			var co = $(this).attr('data');
			if (co) {
				columns = co;
				headers = $(this).html();
			}
		} else {
			var co = $(this).attr('data');
			if (co) {
				columns += ',' + $(this).attr('data');
				headers += ',' + $(this).html();
			}
		}
	});
	var action = $('#' + formId).attr('action');
	$(
			'<input name="headers" id = "headers" type="hidden" value= "'
					+ headers + '"/>').appendTo('#' + formId);
	$(
			'<input name="columns" id = "exports" type="hidden" value= "'
					+ columns + '"/>').appendTo('#' + formId);
	$(
			'<input name="mapper" id = "mappers" type="hidden" value= "'
					+ mapper + '"/>').appendTo('#' + formId);
	$(
			'<input name="title" id = "mappers" type="hidden" value= "' + title
					+ '"/>').appendTo('#' + formId);
	var location = (window.location + '').split('/');
	var basePath = location[0] + '//' + location[2] + '/' + location[3];
	$('#' + formId).attr('action', basePath + '/export');
	$('#' + formId).submit();
	$('#' + formId).attr('action', action);
	$('#headers').remove();
	$('#exports').remove();
	$('#mappers').remove();
}
// rewriting ajax handle statusCode modify by lp
(function($) {

	// 备份jquery的ajax方法
	var _ajax = $.ajax;
	// 重写jquery的ajax方法
	$.ajax = function(opt) {
		// 备份opt中error和success方法
		var fn = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			},
			success : function(data, textStatus) {
			}
		}
		if (opt.error) {
			fn.error = opt.error;
		}
		if (opt.success) {
			fn.success = opt.success;
		}
		// 扩展增强处理
		var _opt = $.extend(opt, {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (XMLHttpRequest.status != "800") {
					layer.msg('额 , 服务器异常了 ... ', {
						icon : 2
					})
					return;
				}

				// 错误方法增强处理
				fn.error(XMLHttpRequest, textStatus, errorThrown);
			},
			success : function(data, textStatus, xhr) {
				// 成功回调方法增强处理
				if (data && data.paramError) {
					layer.msg(data.paramErrorMsg, {
						icon : 2
					});
					return;
				}
				fn.success(data, textStatus);
			},
			statusCode : {
				700 : function() {
					layer.msg('数据异常，请刷新页面 ...', {
						icon : 2
					});
					return;
				},
				800 : function() {
					layer.msg('登陆超时，请重新登陆', {
						icon : 2
					});
					return;
				},
				900 : function() {
					layer.msg('抱歉，您没有此权限', {
						icon : 2
					});
					return;
				}
			}
		});
		return _ajax(_opt);
	};
})(jQuery);

function CurentTime() {
	var now = new Date();
	var year = now.getFullYear(); // 年
	var month = now.getMonth() + 1; // 月
	var day = now.getDate(); // 日
	var clock = year + "-";
	if (month < 10)
		clock += "0";
	clock += month + "-";
	if (day < 10)
		clock += "0";
	clock += day;
	return (clock);
}
function CurentTimes(startTime,addday) {
	var now0 = new Date(startTime);
	var now = new Date(now0);
	now.setDate(now0.getDate()+addday);
	var year = now.getFullYear(); // 年
	var month = now.getMonth() + 1; // 月
	var day = now.getDate(); // 日
	var clock = year + "-";
	if (month < 10)
		clock += "0";
	clock += month + "-";
	if (day < 10)
		clock += "0";
	clock += day;
	return (clock);
}

function CurentTimeMonth() {
	var now = new Date();
	var year = now.getFullYear(); // 年
	var month = now.getMonth() + 1; // 月
	var day = now.getDate(); // 日
	var hh = now.getHours(); // 时
	var mm = now.getMinutes(); // 分
	var clock = year + "-";
	if (month < 10)
		clock += "0";
	clock += month;
	return (clock);
}

function checkStartTimeMonth(startTime, status) {
	$.ajax({
        type: "post",
        url: "smsSendTaskClient/getDate",
        success: function(data){
        	 curentTime = data.day;
        	 if (status == 'Month') {
    			curentTime = data.month;
    		 }
    		if ($("#" + startTime).val() > curentTime) {
    			layer.msg("开始时间不能晚于当前时间");
    			$("#" + startTime).val(curentTime);
    		}
        }
    });
}
function checkEndTimeMonth(startTime, EndTime) {
	if ($("#" + EndTime).val()) {
		if ($("#" + startTime).val()) {
			if ($("#" + startTime).val() > $("#" + EndTime).val()) {
				layer.msg("结束时间不能早于开始时间");
				$("#" + EndTime).val(CurentTimes($("#" + startTime).val(), 1));
			}
		}
	}
}

function checkStartTimeMonthFiveDay(startTime, status) {
	$.ajax({
        type: "post",
        url: "smsSendTaskClient/getDate",
        success: function(data){
        	 curentTime = data.day;
        	 if (status == 'Month') {
    			curentTime = data.month;
    		 }
        	 if ($("#" + startTime).val() > curentTime) {
        			layer.msg("开始时间不能晚于当前时间");
        			$("#" + startTime).val(curentTime);
        		}
        		if ($("#" + startTime).val() =='' ) {
        			layer.msg("开始时间不能为空");
        			$("#" + startTime).val(curentTime);
        		}
        }
    });
}
function checkEndTimeMonthFiveDay(startTime, EndTime) {
	if ($("#" + startTime).val()) {
		var curentTime = CurentTimes($("#" + startTime).val(), 7);
		if ($("#" + EndTime).val()) {
			if ($("#" + startTime).val() > $("#" + EndTime).val()) {
				$("#" + EndTime).val(CurentTimes($("#" + startTime).val(), 1));
				layer.msg("结束时间不能早于开始时间");
			}
			if($("#" + EndTime).val() > curentTime){
				layer.msg("查询时间段只能在7天内");
				$("#" + EndTime).val(curentTime);
			}
		}else{
			$("#" + EndTime).val(CurentTimes($("#" + startTime).val(), 1));
		}
	}else{
		$("#" + startTime).val(CurentTime());
		$("#" + EndTime).val(CurentTimes($("#" + startTime).val(), 1));
	}
}

// 发送验证码60秒倒计时
var wait = 60;
function time(o) {
	console.log("1");
	if (wait == 0) {
		o.removeAttribute("disabled");
		$(o).html("获取验证码");
		wait = 60;
	} else {
		o.setAttribute("disabled", true);
		$(o).html("重新发送(" + wait + ")");
		wait--;
		setTimeout(function() {
			time(o)
		}, 1000)
	}
}

// 内容换行截取
function stringIntercept(obj) {
	var x = obj;
	/*while (true) {
		if (obj.length > 100) {
			x += obj.substring(0, 100) + "<br>";
			obj = obj.substring(100);
		} else {
			x += obj;
			break;
		}
	}*/
	return x;
}

function htmlEncodeJQ ( str ) {
	  return $('<span/>').text( str.replace(/\s/g, "") ).html();
	}

function htmlEncodeJQ1 ( str ) {
	  return $('<span/>').text( str ).html();
	}
