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
    height:758px;
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
    height:100px;
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
    width:258px;
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

.lic-wrap{margin-left:230px; padding:10px 10px 20px 10px; width:860px; margin-top:70px; background:#fff;}
.lic-wrap button.btn-primary{background-color: #559AFA; border:none; width: 67px; height: 40px; text-align: center;}
.lic-wrap .lic-title{font-size:18px;}
.lic-wrap .lic-notice{}
.lic-wrap .lic-img-preview{background:#ccc; height:214px; width:391px; }
.lic-wrap .lic-btn-upload{}
.lic-wrap .lic-example{padding-left:10px; padding-top:10px;}
.lic-wrap .lic-example-img{}
.lic-wrap .lic-btns{}
</style>



<div class='chanzorDataList'>
<div class="chanzorDataList_title" >
   <p>发票认证(<span id="statusDesc"><c:choose><c:when test="${vatinvoice.certinfo_status== 3 && vatinvoice.vatinvoice_type == 0}">普通发票不需认证</c:when><c:when test="${vatinvoice.certinfo_status== 3 && vatinvoice.vatinvoice_type == 1}">未提交认证</c:when><c:when test="${vatinvoice.certinfo_status == 0 }">认证中</c:when><c:when test="${vatinvoice.certinfo_status == 1 }">认证通过</c:when><c:when test="${vatinvoice.certinfo_status == 2 }">认证被驳回</c:when><c:otherwise>未认证</c:otherwise></c:choose></span>)</p>
</div>
<form id="invoiceForm">
<input type="hidden" id="certinfo_status" name="certinfo_status" value="${vatinvoice.certinfo_status}" />
<div class='invAuth_top1'>
    <div class='invAuth_top1_left'>
       <span class="text-danger">*</span> <span>发票类型:</span>
    </div>
    <div class='invAuth_top1_right'>
        <c:if test="${vatinvoice.vatinvoice_type == 0}">增值税普通发票</c:if>
        <c:if test="${vatinvoice.vatinvoice_type == 1}">增值税专用发票</c:if>
    </div>
</div>
<div class='invAuth_top2'>
    <div class='invAuth_top2_left'>
       <span class="text-danger">*</span> <span>发票抬头:</span>
    </div>
    <div class='invAuth_top2_right'>
       ${vatinvoice.vatinvoice_title}
    </div>
</div>
<div  id="specialDiv">
<div class='invAuth_top2'>
    <div class='invAuth_top4_left'>
        <span class="text-danger">*</span><span>税务登记证号:</span>
    </div>
    <div class='invAuth_top4_right'>
        ${vatinvoice.idenitfication_num}
    </div>
</div>
<div class='invAuth_top5'>
    <div class='invAuth_top5_left'>
        <span class="text-danger">*</span><span>基本开户银行名称:</span>
    </div>
    <div class='invAuth_top5_right'>
        ${vatinvoice.open_accent}
    </div>
</div>
<div class='invAuth_top7'>
    <div class='invAuth_top7_left'>
       <span class="text-danger">*</span> <span>基本开户账号:</span>
    </div>
    <div class='invAuth_top7_right'>
        ${vatinvoice.bank_accent}
    </div>
</div>

<div class='invAuth_top8'>
    <div class='invAuth_top8_left'>
       <span class="text-danger">*</span> <span>注册场所地址:</span>
    </div>
    <div class='invAuth_top8_right'>
        ${vatinvoice.registered_address}
    </div>
</div>

<div class='invAuth_top9'>
    <div class='invAuth_top9_left'>
        <span class="text-danger">*</span><span>注册固定电话:</span>
    </div>
    <div class='invAuth_top9_right'>
        ${vatinvoice.company_phone}
    </div>
</div>

<div class='p20'>
    <div class='invAuth_top10_left'>
       <span class="text-danger">*</span> <span>营业执照复印件:</span>
    </div>
    <div class='invAuth_top10_right'>
        <div>
            <input type="hidden" id="bank_accent_img_SRC" name="bank_accent_img_SRC" value="${vatinvoice.bank_accent_img}" />
            <img style="display:none" id="bank_accent_img_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${vatinvoice.bank_accent_img}' onclick="showImage(this)">
             <c:if test="${!empty vatinvoice.bank_accent_img}"><a href="#" onClick="javascript:previewPic('bank_accent_img_SRC_imghead');">点击查看</a></c:if>
        </div>
    </div>
</div>
<!--************************************************************************-->
    <div class='p20'>
        <div class='invAuth_top11_left'>
            <span>税务登记证复印件</span>
        </div>
        <div class='invAuth_top11_right'>
            <div>
            	<input type="hidden" id="tax_registration_img_SRC" name="tax_registration_img_SRC" value="${vatinvoice.tax_registration_img}" />
            	<img style="display:none" id="tax_registration_img_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${vatinvoice.tax_registration_img}' onclick="showImage(this)">
                 <c:if test="${!empty vatinvoice.tax_registration_img}"><a href="#" onClick="javascript:previewPic('tax_registration_img_SRC_imghead');">点击查看</a></c:if>
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
                 <input type="hidden" id="general_tax_img_SRC" name="general_tax_img_SRC" value="${vatinvoice.general_tax_img}" />
				 <img style="display:none" id="general_tax_img_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${vatinvoice.general_tax_img}' onclick="showImage(this)">
                 <c:if test="${!empty vatinvoice.general_tax_img}"><a href="#" onClick="javascript:previewPic('general_tax_img_SRC_imghead');">点击查看</a></c:if>
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
    </form>
</div>
