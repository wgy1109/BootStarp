 var dataTable={
    init:function(data){
     $('#example').dataTable({
        serverSide: true,
        processing: true,
        searching: false,
        bDeferRender: true,
        bLengthChange: false,
        bSort: false,
        bStateSave: true,
        ajax: {
            type: "post",
            url: "voiceSendStatistics/load",
            dataSrc: "data",
            data: data,
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 404) {
                    $("#example").html("数据库服务器通讯失败，请稍后重试！");
                }
            }
        },
        fnServerParams: function (aoData) {
            aoData.queryStartTime = $("#queryStartTime").val(); 
            aoData.queryEndTime = $("#queryEndTime").val(); 
            aoData.timegroup = $("#timegroup").val(); 
            aoData.spId = $("#spId").val(); 
        },
        serverSide: true,
        columns: [
        {
            "data": "user_sp_name",
            render: function(data, type, row) {
             	return data==null?"":data;
            }
        },
        {
            "data": "msgtime",
            render: function(data, type, row) {
             	return data==null?"":data;
            }
        },
        {
            "data": "allnum",
            render: function(data, type, row) {
             	return data==null?"":data;
            }
        },
        {
            "data": "sendednum",
            render: function(data, type, row) {
             	return data==null?"":data;
            }
        },
        {
            "data": "allyes",
            render: function(data, type, row) {
             	return data==null?"":data;
            }
        },
        {
            "data": "allno",
            render: function(data, type, row) {
             	return data==null?"":data;
            }
        },
        {
            "data": "othernum",
            render: function(data, type, row) {
             	return data==null?"":data;
            }
        }],
        "language": {
            "lengthMenu": "_MENU_ 条记录每页",
            "zeroRecords": "没有找到记录",
			"info" : "总共_PAGES_页，显示第_START_到第_END_条，筛选之后得到_TOTAL_条，初始_MAX_条 ",
            "infoEmpty": "无记录",
            "infoFiltered": "(从 _MAX_ 条记录过滤)",
            "paginate": {
                "previous": "上一页",
                "next": "下一页"
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
			onChangeDateTime:function() {
				checkStartTimeMonth("queryStartTime","All");
			}
		});
		$('#queryEndTime').datetimepicker({
			format : 'Y-m-d',
			timepicker : false,
			allowBlank : true,
			onChangeDateTime:function() {
				checkEndTimeMonth("queryStartTime","queryEndTime");
			}
		});
		
		if($('#queryStartTime').val() == ""){
			var mydate = new Date();
			$('#queryStartTime').val(mydate.getFullYear()+"-"+((mydate.getMonth()+1)<10 ? '0'+(mydate.getMonth()+1) : (mydate.getMonth()+1) )+"-"+ (mydate.getDate() < 10 ? '0'+ mydate.getDate() : mydate.getDate()) );
		}
		
		$.get("smsSendTaskClient/findSpInfoByUserId?sp_service_type=3", function(data) {
			if (data && data.spList) {
				$("#spId").empty();
				var content = [];
				content.push("<option value='-1'>请选择</option>");
				content.push("<option value='0'>分组</option>");
				$.each(data.spList, function(i, even) {
					if (even.sp_name) {
						content.push("<option value=" + even.spid + ">"
								+ even.sp_name + "</option>");
					}
				});
				$("#spId").html(content.join(''));
			}
		});
		
		if ($.fn.dataTable.isDataTable('#example')){
			$('#example').dataTable().fnDraw(false);
		}else{
			dataTable.init();
		}
     });
     
	function search(){
	 $('#example').dataTable().fnDraw(false); 
	};