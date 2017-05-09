$(function(){

alert("${spInfo.sp_through_status}");
})

function appOnLine(id){
$("#myModal").modal('show'); 	
};
function onlineSpinfo(){
$("#uploadAddressForm").submit();
}
function delSpInfo(){
layer.confirm('确定删除应用?', {
    btn: ['确定','取消'] //按钮
}, function(){
goPage("spInfo/updateDelSpInfo?spid=${spInfo.spid}");
});
};