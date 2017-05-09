	$(function() {
		$.ajax({
			url : 'chargeRecord/spChargeList',// 跳转到 action  
			success : function(data) {
				var a = "";
				$.each(data.data, function() {
									a += "<tr> <td>"+this.sp_name+"</td><td>"+this.spCharge.leftover_num+"</td><td><a href='javascript:void(0)' onclick='pay("+this.spid+")'>充值</a></td></tr>";
				});
				$("#chargeRecord").append(a);
			}
		});
	});
	function pay(id){
	layer.open({
    type: 2,
    title: 'layer mobile页',
    shadeClose: true,
    shade: 0.8,
    area: ['380px', '90%'],
    content: 'chargeRecord/directPaySpInfo?spid='+id //iframe的url
}); 
 	}
	function applay(id){
	 goPage('chargeRecord/SpInfoApply?spId='+id);
	}
  