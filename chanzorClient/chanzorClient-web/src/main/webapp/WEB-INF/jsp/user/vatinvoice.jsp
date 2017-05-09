<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script src="<%=basePath%>static/js/plugins/layer2.1/layer.js"></script>
<style>
.invAuth {
    width: 85%;
    height:1458px;
    margin-left:220px;
    margin-top:120px;
    margin-bottom:120px;
    background-color: white;
    padding-top:20px;
}
.invAuth h4 {
    margin-left:30px;
}
.invAuth_top1 {
    height:30px;
    /*background-color: white;*/
    margin-bottom:20px;
}

.invAuth_top1_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top1_left span{
    margin-right:10px;
}
.invAuth_top1_right{
    /*background-color: green;*/
    height:100%;
    float:left;
    width:68%;
    float:left;
}

.invAuth_top2 {
    height:30px;
    margin-bottom:20px;
}

.invAuth_top2_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top2_left span {
    margin-right:10px;
}
.invAuth_top2_right {
    width:68%;
    /*background-color: purple;*/
    height:100%;
    float:left;
}
.invAuth_top3 {
    height:30px;
    margin-bottom:20px;
}
.invAuth_top3_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top3_left span {
    margin-right:10px;
}
.invAuth_top3_left span {
    margin-right:10px;
}
.invAuth_top3_right {
    height:100%;
    width:68%;
    /*background-color: purple;*/
    float:left;
}
.invAuth_top4 {
    height:40px;
    margin-bottom:20px;
}
.invAuth_top4_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top4_left span {
    margin-right:10px;
}
.invAuth_top4_right {
    width:68%;
    height:100%;
    /*background-color: green;*/
    float:left;
}
.invAuth_top5 {
    height:30px;
    margin-bottom:20px;
}
.invAuth_top5_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top5_left span {
    margin-right:10px;
}
.invAuth_top5_right {
    height:100%;
    width:68%;
    /*background-color: coral;*/
    float:left;
}
/**********************************/
.invAuth_top6 {
    height:30px;
    margin-bottom:20px;
}
.invAuth_top6_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top6_left span {
    margin-right:10px;
}
.invAuth_top6_right {
    height:100%;
    width:68%;
    /*background-color: coral;*/
    float:left;
}
.invAuth_top7 {
    height:30px;
    margin-bottom:20px;
}
.invAuth_top7_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top7_left span {
    margin-right:10px;
}
.invAuth_top7_right {
    height:100%;
    width:68%;
    /*background-color: coral;*/
    float:left;
}
.invAuth_top8 {
    height:30px;
    margin-bottom:20px;
}
.invAuth_top8_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top8_left span {
    margin-right:10px;
}
.invAuth_top8_right {
    height:100%;
    width:68%;
    /*background-color: coral;*/
    float:left;
}
.invAuth_top9 {
    height:30px;
    margin-bottom:20px;
}
.invAuth_top9_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top9_left span {
    margin-right:10px;
}
.invAuth_top9_right {
    height:100%;
    width:68%;
    /*background-color: coral;*/
    float:left;
}
.invAuth_top10 {
    height:132px;
    margin-bottom:20px;
}
.invAuth_top10_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top10_left span {
    margin-right:10px;
}
.invAuth_top10_right {
    height:100%;
    width:68%;
    /*background-color: palegreen;*/
    float:left;
}
.invAuth_top10 .invAuth_top10_right .file {
    position:relative;
    display:inline-block;
    border:1px solid #99D3F5;
    border-radius:4px;
    padding:4px 36px;
    overflow:hidden;
    color:#1E88C7;
    text-decoration: none;
    text-indent:0;
    line-height:20px;
    margin-bottom:26px;
}
.invAuth_top10 .invAuth_top10_right .file input {
    position:absolute;
    font-size:100px;
    right:0;
    top:0;
    opacity:0;
}
.invAuth_top10 .invAuth_top10_right .file:hover {
    background-color: #AADFFD;
    border-color:#004974;
    text-decoration: none;
}
/*********************************************************/
.invAuth_top11 {
    height:132px;
    margin-bottom:20px;
}
.invAuth_top11_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top11_left span {
    margin-right:10px;
}
.invAuth_top11_right {
    height:100%;
    width:68%;
    /*background-color: purple;*/
    float:left;
}
.invAuth_top11 .invAuth_top11_right .file {
    position:relative;
    display:inline-block;
    border:1px solid #99D3F5;
    border-radius:4px;
    padding:4px 36px;
    overflow:hidden;
    color:#1E88C7;
    text-decoration: none;
    text-indent:0;
    line-height:20px;
    margin-bottom:26px;
}
.invAuth_top11 .invAuth_top11_right .file input {
    position:absolute;
    font-size:100px;
    right:0;
    top:0;
    opacity:0;
}
.invAuth_top11 .invAuth_top11_right .file:hover {
    background-color: #AADFFD;
    border-color:#004974;
    text-decoration: none;
}
/***********************************************************/
.invAuth_top12 {
    height:32px;
}

.invAuth_top12_left{
    /*background-color: purple;*/
    height:100%;
    float:left;
    width:300px;
    text-align:right;
}
.invAuth_top12_left span {
    margin-right:10px;
}
.invAuth_top12_right {
    height:100%;
    width:68%;
    /*background-color: purple;*/
    float:left;
}
.invAuth_top12 .invAuth_top12_right .file {
    position:relative;
    display:inline-block;
    border:1px solid #99D3F5;
    border-radius:4px;
    padding:4px 36px;
    overflow:hidden;
    color:#1E88C7;
    text-decoration: none;
    text-indent:0;
    line-height:20px;
    margin-bottom:26px;
}
.invAuth_top12 .invAuth_top12_right .file input {
    position:absolute;
    font-size:100px;
    right:0;
    top:0;
    opacity:0;
}
.invAuth_top12 .invAuth_top12_right .file:hover {
    background-color: #AADFFD;
    border-color:#004974;
    text-decoration: none;
}
.invAuth  .invAuth_ipt {
    width:358px;
    height:30px;
    border-radius: 5px;
    border:1px solid #ccc;
    padding-left:5px;
}
.invAuth  .invAuth_lab1 {
    margin-right:40px;
}
.invAuth  .invAuth_lab{
    margin-right:104px;
}
.invAuth .invAuthbtn {
    width:129px;
    height:48px;
    border-radius:26px;
}

</style>

<style>
.mt10{margin-top: 10px;}
.mt20{margin-top: 20px;}
.mt40{margin-top: 40px;}
.ml30{margin-left: 30px;}
.ml60{margin-left: 60px;}

.p10{padding:10px;}
.p20{padding:20px;}
.pb5{padding-bottom:5px;}
.pt10{padding-top:10px;}


.h-line{border-bottom:solid 1px #ccc;}
.h-center{text-align:center;}

.lic-wrap{margin-left:-430px; padding:10px 10px 20px 10px; width:860px; margin-top:70px; background:#fff;}
.lic-wrap button.btn-primary{background-color: #559AFA; border:none; width: 67px; height: 40px; text-align: center;}
.lic-wrap .lic-title{font-size:18px;}
.lic-wrap .lic-notice{}
.lic-wrap .lic-img-preview{background:#ccc; height:214px; width:391px; }
.lic-wrap .lic-btn-upload{}
.lic-wrap .lic-example{padding-left:10px; padding-top:10px;}
.lic-wrap .lic-example-img{}
.lic-wrap .lic-btns{}

.chanzorDataList_title {
	width: 350px;
	margin-bottom: 60px;
}
.chanzorDataList_title p {
	line-height: 40px;
	text-align: center;
	padding-left: 20px;
	font-size: 26px;
	float: left;
}

</style>



<div class='chanzorDataList'>
<div class="chanzorDataList_title" >
       <p>发票认证(<span id="statusDesc"><c:choose><c:when test="${vatinvoice.certinfo_status== 3 && vatinvoice.vatinvoice_type == 0}">普通发票不需认证</c:when><c:when test="${vatinvoice.certinfo_status== 3 && vatinvoice.vatinvoice_type == 1}">未提交认证</c:when><c:when test="${vatinvoice.certinfo_status == 0 }">认证中</c:when><c:when test="${vatinvoice.certinfo_status == 1 }">认证通过</c:when><c:when test="${vatinvoice.certinfo_status == 2 }">认证被驳回</c:when><c:otherwise>未认证</c:otherwise></c:choose></span>)</p>
</div>
<form id="invoiceForm">
<input type="hidden" id="certinfo_status" name="certinfo_status" value="${vatinvoice.certinfo_status}" />
<div class='invAuth_top1'>
    <div class='invAuth_top1_left'>
       <span class="text-danger">*</span><span>发票类型</span>
    </div>
    <div class='invAuth_top1_right'>
        <input type="radio" name='vatinvoice_type' id='vatinvoice_type0' <c:if test="${vatinvoice.vatinvoice_type == 0}">checked</c:if> value="0" onclick="javascript:changeInvoiceType();" /><label for="vatinvoice_type0" class='invAuth_lab1'>增值税普通发票</label>
        <input type="radio" name='vatinvoice_type' id='vatinvoice_type1' <c:if test="${vatinvoice.vatinvoice_type == null || vatinvoice.vatinvoice_type == 1}">checked</c:if> value="1" onclick="javascript:changeInvoiceType();" /><label for="vatinvoice_type1" class='invAuth_lab1'>增值税专用发票</label>
    </div>
</div>
<div class='invAuth_top2'>
    <div class='invAuth_top2_left'>
       <span class="text-danger">*</span> <span>发票抬头</span>
    </div>
    <div class='invAuth_top2_right'>
        <input type="text" class='form-control' style="width:300px" id="vatinvoice_title" name="vatinvoice_title" value="${vatinvoice.vatinvoice_title}" />
    </div>
</div>

<div class='invAuth_top4'>
    <div class='invAuth_top4_left'>
         <span class="text-danger">*</span><span>统一社会信用代码</span>
    </div>
    <div class='invAuth_top4_right'>
        <input type="text" name='idenitfication_num' id='idenitfication_num' class='form-control' style="width:300px" value="${vatinvoice.idenitfication_num}" />
        <p><span><font style="font-size:12px;" color="#9d9d9d">原税务登记证号，请与贵司财务核实并准确填写</font></span></p>
    </div>
</div>

<div  id="specialDiv">
<div class='invAuth_top5'>
    <div class='invAuth_top5_left'>
         <span class="text-danger">*</span><span>基本开户银行名称</span>
    </div>
    <div class='invAuth_top5_right'>
        <input type="text" name='open_accent' id='open_accent' class='form-control' style="width:300px" value="${vatinvoice.open_accent}" />
    </div>
</div>
<div class='invAuth_top7'>
    <div class='invAuth_top7_left'>
        <span class="text-danger">*</span> <span>基本开户账号</span>
    </div>
    <div class='invAuth_top7_right'>
        <input type="text" name='bank_accent' id='bank_accent' class='form-control' style="width:300px" value="${vatinvoice.bank_accent}" />
    </div>
</div>

<div class='invAuth_top8'>
    <div class='invAuth_top8_left'>
        <span class="text-danger">*</span> <span>注册场所地址</span>
    </div>
    <div class='invAuth_top8_right'>
        <input type="text" name='registered_address' id='registered_address'  class='form-control' style="width:300px" value="${vatinvoice.registered_address}" />
    </div>
</div>

<div class='invAuth_top9'>
    <div class='invAuth_top9_left'>
        <span class="text-danger">*</span> <span>注册固定电话</span>
    </div>
    <div class='invAuth_top9_right'>
        <input type="text" name='company_phone' id='company_phone' class='form-control' style="width:300px" value="${vatinvoice.company_phone}" />
    </div>
</div>
<div class='p20'>
    <div class='invAuth_top10_left'>
         <span class="text-danger">*</span><span>银行开户许可证复印件</span>
    </div>
    <div class='invAuth_top10_right'>
        <div>
            <a href="#" data-toggle="modal" data-target="#bankaccentModal" style="float:left">上传</a>
            <div id="bank_accent_img_div" style="float:left;margin-left:20px;"><c:if test="${!empty vatinvoice.bank_accent_img}"><a href="#" onClick="javascript:previewPic('bank_accent_img_SRC_imghead');">预览</a></c:if></div>
            <input type="hidden" id="bank_accent_img_SRC" name="bank_accent_img_SRC" value="${vatinvoice.bank_accent_img}" />
            <img style="display:none" id="bank_accent_img_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${vatinvoice.bank_accent_img}' onclick="showImage(this)">
          <!--   <a href="javascript:;" class="file">上传图片
                <input type="file" name="" id="">
            </a>
 -->
           <!--  <div style='display:inline-block;'>
                <span>三证合一或五证合一仅传一个</span><br/>
            </div> -->
        </div>
    </div>
</div>
<!--************************************************************************-->
    <div class='p20'>
        <div class='invAuth_top11_left'>
            <span>三证合一营业执照复印件</span>
        </div>
        <div class='invAuth_top11_right'>
            <div>
                <a href="#" data-toggle="modal" data-target="#taxregistrationModal" style="float:left">上传</a>
 				 <div id="tax_registration_img_div" style="float:left;margin-left:20px;"><c:if test="${!empty vatinvoice.tax_registration_img}"><a href="#" onClick="javascript:previewPic('tax_registration_img_SRC_imghead');">预览</a></c:if></div>
            	<input type="hidden" id="tax_registration_img_SRC" name="tax_registration_img_SRC" value="${vatinvoice.tax_registration_img}" />
            	<img style="display:none" id="tax_registration_img_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${vatinvoice.tax_registration_img}' onclick="showImage(this)">
            	<div><font style="font-size:12px;margin-left:20px;" color="#9d9d9d">或：税务登记证复印件</font></div>
            </div>
        </div>
    </div>

<!--******************************************************************************-->
    <div class='p20'>
        <div class='invAuth_top12_left'>
            <span>一般纳税人资格认证复印件</span>
        </div>
        <div class='invAuth_top12_right'>
            <div>
                 <a href="#" data-toggle="modal" data-target="#generaltaxModal" style="float:left">上传</a>
                 <div id="general_tax_img_div" style="float:left;margin-left:20px;"><c:if test="${!empty vatinvoice.general_tax_img}"><a href="#" onClick="javascript:previewPic('general_tax_img_SRC_imghead');">预览</a></c:if></div>
                 <input type="hidden" id="general_tax_img_SRC" name="general_tax_img_SRC" value="${vatinvoice.general_tax_img}" />
				 <img style="display:none" id="general_tax_img_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${vatinvoice.general_tax_img}' onclick="showImage(this)">
                 <img style="display:none" id="noProvide_SRC_imghead" width=100 height=80 border=0 src='/static/img/noProvide.jpg' onclick="showImage(this)">
                 <a href="#" onClick="javascript:previewPic('noProvide_SRC_imghead');" style="float:left;margin-left:20px;">无法提供怎么办？</a>
            </div>
        </div>
    </div>
    <c:if test="${vatinvoice.certinfo_status == 2 }">
	    <div class='p20'>
	        <div class='invAuth_top12_left'>
	            <span style="color:red">驳回理由：</span>
	        </div>
	        <div class='invAuth_top12_right'>
	            <div>
	                <label style="color:red">${vatinvoice.certinfo_reject_reason }</label>
	            </div>
	        </div>
	    </div>
    </c:if>
    </div>
    <div class="form-group mt40">
         <div class="col-sm-offset-4 col-sm-10">
                    <button id="saveInvoiceButton" type="button"  class="btn sub-btn" style="width:100px;margin-left:-200px;" onclick="javascript:saveInvoice();">保存</button>
                    <button id="saveAndCommitInvoiceButton" type="button"  class="btn sub-btn" style="width:100px;margin-left:40px" onclick="javascript:vatinvoiceSubmit();">保存并提交</button>
         </div>
    </div>
    </form>
</div>

 <div id="bankaccentModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
<div class="modal-dialog">
<div class="lic-wrap col-sm-12 ">
	<div class="col-sm-12 h-line pb5 pt10 lic-title ">银行开户许可证</div>
	<div class="col-sm-12">
		<div class="col-sm-9 lic-notice mt10">
			<ul>
				<li><font style="font-size:12px;" color="#9d9d9d">1.请您上传有效的银行开户许可证。证件应不大于1024KB。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">2.证件扫描件图片完整（不缺少边线）。信息清晰可辨，不得做任何修改。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">3.如果上传图片您的浏览器没有反应。强烈推荐您使用IE9版本以上的浏览器。</font></li>
			</ul>
			<div id="bank_accent_img_SRC_example" class="lic-img-preview mt20 ml30">
			     <img src="static/img/preview_1.png" >
			</div>
			<div id="bank_accent_img_SRC_preview" >
			 
			</div>
			<div class="lic-btn-upload mt20 h-center">
				<a href="javascript:clickImgUpload('bank_accent_img')">点击上传图片</a>
				<input type="file" name="file" id="bank_accent_img" style="display:none"  
										filename="bank_accent_img" class="btn btn-default" onchange="updateImage(this);" value="${vatinvoice.bank_accent_img}">
			</div>
		</div>
		<div class="col-sm-3 lic-example">
			<label class="mt20"><font style="font-size:12px;" color="#9d9d9d">示例：</font></label>
			<div class="lic-example-img mt20">
				<img src="static/img/yyzz_ok.jpg" >
			</div>
			<div class="lic-example-img mt20">
				<img src="static/img/yyzz_err.jpg" >
			</div>
			<div class="lic-btns mt40">
				<button class="btn invalid-btn" onclick="javascript:closeModal('bankaccentModal');">关闭</button>
			   <!--  <button class="btn-primary ml30">取消</button> -->
			</div>
		</div>
	</div>
</div>
	 </div>
</div>

<div id="taxregistrationModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
<div class="modal-dialog">
<div class="lic-wrap col-sm-12 ">
	<div class="col-sm-12 h-line pb5 pt10 lic-title ">税务登记证</div>
	<div class="col-sm-12">
		<div class="col-sm-9 lic-notice mt10">
			<ul>
				<li><font style="font-size:12px;" color="#9d9d9d">1.请您上传有效的税务登记证。证件应不大于1024KB。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">2.证件扫描件图片完整（不缺少边线）。信息清晰可辨，不得做任何修改。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">3.如果上传图片您的浏览器没有反应。强烈推荐您使用IE9版本以上的浏览器。</font></li>
			</ul>
			<div id="tax_registration_img_SRC_example" class="lic-img-preview mt20 ml30">
			     <img src="static/img/preview_1.png" >
			</div>
			<div id="tax_registration_img_SRC_preview" >
			 
			</div>
			<div class="lic-btn-upload mt20 h-center">
				<a href="javascript:clickImgUpload('tax_registration_img')">点击上传图片</a>
				<input type="file" name="file" id="tax_registration_img" style="display:none"  
										filename="tax_registration_img" class="btn btn-default" onchange="updateImage(this);" value="${vatinvoice.tax_registration_img}">
			</div>
		</div>
		<div class="col-sm-3 lic-example">
			<label class="mt20"><font style="font-size:12px;" color="#9d9d9d">示例：</font></label>
			<div class="lic-example-img mt20">
				<img src="static/img/swdjz_ok.jpg" >
			</div>
			<div class="lic-example-img mt20">
				<img src="static/img/swdjz_err.jpg" >
			</div>
			<div class="lic-btns mt40">
				<button class="btn invalid-btn" onclick="javascript:closeModal('taxregistrationModal');">关闭</button>
			   <!--  <button class="btn-primary ml30">取消</button> -->
			</div>
		</div>
	</div>
</div>
	 </div>
</div>


 <div id="generaltaxModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
<div class="modal-dialog">
<div class="lic-wrap col-sm-12 ">
	<div class="col-sm-12 h-line pb5 pt10 lic-title ">一般纳税人资格认证</div>
	<div class="col-sm-12">
		<div class="col-sm-9 lic-notice mt10">
			<ul>
				<li><font style="font-size:12px;" color="#9d9d9d">1.请您上传有效的一般纳税人资格认证。证件应不大于1024KB。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">2.证件扫描件图片完整（不缺少边线）。信息清晰可辨，不得做任何修改。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">3.如果上传图片您的浏览器没有反应。强烈推荐您使用IE9版本以上的浏览器。</font></li>
			</ul>
			<div id="general_tax_img_SRC_example" class="lic-img-preview mt20 ml30">
			     <img src="static/img/preview_1.png" >
			</div>
			<div id="general_tax_img_SRC_preview" >
			 
			</div>
			<div class="lic-btn-upload mt20 h-center">
				<a href="javascript:clickImgUpload('general_tax_img')">点击上传图片</a>
				<input type="file" name="file" id="general_tax_img" style="display:none"  
										filename="general_tax_img" class="btn btn-default" onchange="updateImage(this);" value="${vatinvoice.general_tax_img}">
			</div>
		</div>
		<div class="col-sm-3 lic-example">
			<label class="mt20">示例：</label>
			<div class="lic-example-img mt20">
				<img src="static/img/ybnsr_ok.png" >
			</div>
			<div class="lic-example-img mt20">
				<img src="static/img/ybnsr_err.png" >
			</div>
			<div class="lic-btns mt40">
				<button class="btn invalid-btn" onclick="javascript:closeModal('generaltaxModal');">关闭</button>
			   <!--  <button class="btn-primary ml30">取消</button> -->
			</div>
		</div>
	</div>
</div>
	 </div>
</div>