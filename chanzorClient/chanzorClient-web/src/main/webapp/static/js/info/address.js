
function saveAddress() {
	$.ajax({
		url : 'saveAddress',
		type : 'post',
		data : $('#addressForm').serialize(),
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				$('#myAddressHref').click();
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}
		}
	});
}

function setDefaultAddress(addressId){
	$.ajax({
		url : 'setDefaultAddress',
		type : 'post',
		data : {"id":addressId},
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				$('#myAddressHref').click();
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}
		}
	});
}

function delAddress(addressId){
	layer.confirm('您确定删除此地址？删除后此地址将不可用，请您谨慎提交。',function () {
		$.ajax({
			url : 'delAddress',
			type : 'post',
			data : {"id":addressId},
			success : function(data) {
				if (data.code == '00') {
					layer.msg(data.msg, {
						icon : 1
					});
					//$("#addressRead" + addressId).hide();
					$('#myAddressHref').click();
				} else {
					layer.msg(data.msg, {
						icon : 2
					});
				}
			}
		});
	 });
	}

function beginEdit(addressId){
	$("#addressRead" + addressId).hide();
	$("#addressEdit" + addressId).show();
}

function endEdit(addressId){
	$("#collect_nameRead"+addressId).text($("#collect_nameEdit"+addressId).val());
	$("#collect_addressRead"+addressId).text($("#collect_addressEdit"+addressId).val());
	$("#collect_phoneRead"+addressId).text($("#collect_phoneEdit"+addressId).val());
	$("#collect_telRead"+addressId).text($("#collect_telEdit"+addressId).val());
	$("#addressRead" + addressId).show();
	$("#addressEdit" + addressId).hide();
}

function editAddress(addressId) {
	$.ajax({
		url : 'saveAddress',
		type : 'post',
		data : $("#addressForm"+addressId).serialize(),
		success : function(data) {
			if (data.code == '00') {
				layer.msg(data.msg, {
					icon : 1
				});
				endEdit(addressId);
			} else {
				layer.msg(data.msg, {
					icon : 2
				});
			}
		}
	});
}


function beginNewAddress(){
	$("#collect_name").val("");
	$("#collect_address").val("");
	$("#collect_phone").val("");
	$("#collect_tel").val("");
	$("#addressNew").show();
}