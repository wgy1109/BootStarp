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
.enpIdentify {
    width:85%;
    background-color: white;
    height: 698px;
    margin-bottom:400px;
    margin-top:120px;
    margin-left:220px;
    padding-top:50px;
}

.enpIdentify .h4 {
    margin-left:108px;
}
.enpIdentify_top1 {
    margin-top:40px;
    margin-left:100px;
}
.enpIdentify  .enpIdentify_dd {
    background-color: #0D9DBF;
    width:78px;
    height:26px;
    line-height:6px;
    font-size:14px;
    border-radius:3px;
    font-family:'microsoft yahei';
    border:none;
}
.enpIdentify_inp {
    width:218px;
    height:30px;
}
.dl-horizontal dt span {
    color:red;
}
.dl-horizontal dt {
    font-weight: normal;
}
.enpIdentify   .enpIdentify_a {
    margin-bottom:10px;
}

.pass {
    width: 80%;
    height: 1440px;
    margin-left: 250px;
    margin-top: 130px;
    background-color: white;
    padding-top: 20px;
    font-size: 16px;

}

.passTop1_left, .passTop2_left, .passTop3_left, .passTop4_left, .passTop5_left {
    width: 39%;
    height: 100%;
    /*background-color: green;*/
    float: left;
}

.passTop1_right, .passTop2_right, .passTop3_right, .passTop4_right, .passTop5_right {
    width: 61%;
    height: 100%;
    /*background-color: yellow;*/
    float: left;
}

.passTop1_left p, .passTop2_left p, .passTop3_left p, .passTop4_left p, .passTop5_left p {
    text-align: right;

}

.passTop1_left p span, .passTop2_left p span, .passTop3_left p span, .passTop4_left p span, .passTop5_left p span {
    margin-right: 12px;
}

.passTop1 {
    height: 109px;
}

.passTop2 {
    height: 120px;
    margin-bottom: 166px;
}

.passTop3 {
    height: 130px;
    margin-bottom: 166px;
}

.passTop4 {
    height: 109px;
    margin-bottom: 166px;
}

.passTop5 {
    height: 109px;
}

.pass h4 {
    margin-left: 6%;
}

.pass img {
    max-width: 500px;
    height: auto;
}

</style>


 <div class='chanzorDataList'>
        <div class='chanzorDataList_title'>
            <p>企业认证（认证通过）</p>
        </div>
    <form>
        <div class='enpIdentify_top1'>
           <dl class="dl-horizontal">
               <dt><span>&#42;</span>公司名称&nbsp;&#58;</dt>
               <dd>${auth.company}</dd>
           </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>公司地址&nbsp;&#58;</dt>
                <dd>${auth.company_address}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>公司电话&nbsp;&#58;</dt>
                <dd>${auth.contact}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>公司法人姓名&nbsp;&#58;</dt>
                <dd>${auth.legal_representative}</dd>
            </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>法人身份证件正面&nbsp;&#58;</dt>
                <dd>
                     <input type="hidden" id="identity_front_img_SRC" name="identity_front_img_SRC" value="${auth.identity_front_img}" />
                     <img style="display:none" id="identity_front_img_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${auth.identity_front_img}' onclick="showImage(this)">
                   <c:if test="${!empty auth.identity_front_img}"><a href="#" onClick="javascript:previewPic('identity_front_img_SRC_imghead');">点击查看</a></c:if>
                </dd>
            </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>法人身份证件反面&nbsp;&#58;</dt>
                <dd>
                    <input type="hidden" id="identity_back_img_SRC" name="identity_back_img_SRC" value="${auth.identity_back_img}" />
                    <img style="display:none" id="identity_back_img_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${auth.identity_back_img}' onclick="showImage(this)">
                    <c:if test="${!empty auth.identity_back_img}"><a href="#"  onClick="javascript:previewPic('identity_back_img_SRC_imghead');">点击查看</a></c:if>
                </dd>
            </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>营业执照扫描件&nbsp;&#58;</dt>
                <dd>
                    <input type="hidden" id="registered_image_SRC" name="registered_image_SRC" value="${auth.registered_image}" />
                    <img style="display:none" id="registered_image_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${auth.registered_image}' onclick="showImage(this)">
                     <c:if test="${!empty auth.registered_image}"><a href="#"  onClick="javascript:previewPic('registered_image_SRC_imghead');">点击查看</a></c:if>

                </dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>组织机构代码证扫描件&nbsp;&#58;</dt>
                <dd>
                    <input type="hidden" id="organization_image_SRC" name="organization_image_SRC" value="${auth.organization_image}" />
                    <img style="display:none" id="organization_image_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${auth.organization_image}' onclick="showImage(this)">
                    <c:if test="${!empty auth.organization_image}"> <a href="#"  onClick="javascript:previewPic('organization_image_SRC_imghead');">点击查看</a></c:if>

                </dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>税务登记证扫描件&nbsp;&#58;</dt>
                <dd>
                    <input type="hidden" id="taxpayer_image_SRC" name="taxpayer_image_SRC" value="${auth.taxpayer_image}" />
                    <img style="display:none" id="taxpayer_image_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${auth.taxpayer_image}' onclick="showImage(this)">
                    <c:if test="${!empty auth.taxpayer_image}"> <a href="#"  onClick="javascript:previewPic('taxpayer_image_SRC_imghead');">点击查看</a></c:if>
                </dd>
            </dl>
        </div>
    </form>
 </div>
 
