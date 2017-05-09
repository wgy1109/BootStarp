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

<style>
.mt10{margin-top: 10px;}
.mt20{margin-top: 20px;}
.mt40{margin-top: 40px;}
.ml30{margin-left: 30px;}
.ml60{margin-left: 60px;}

.p10{padding:10px;}
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

</style>

 <div class='chanzorDataList'>
     <div class='chanzorDataList_title' style="width:350px">
              <p>企业认证（<span id="statusDesc"><c:choose><c:when test="${auth.status== 0 }">未提交认证</c:when><c:when test="${auth.status == 1 }">认证中</c:when><c:when test="${auth.status == 2 }">认证通过</c:when><c:when test="${auth.status == 3 }">认证被驳回</c:when><c:otherwise>未认证</c:otherwise></c:choose></span>）</p>
     </div>
    <form id="authForm">
        <div class='enpIdentify_top1'>
           <dl class="dl-horizontal">
               <dt><span>&#42;</span>公司名称&nbsp;&#58;</dt>
               <dd><input type="text" class='form-control' style="width:300px" id="company" name="company" value="${auth.company}"/></dd>
           </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>公司地址&nbsp;&#58;</dt>
                <dd><input type="text" class='form-control' style="width:300px" id="company_address" name="company_address" value="${auth.company_address}" /></dd>
            </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>公司电话&nbsp;&#58;</dt>
                <dd><input type="text" class='form-control' style="width:300px" id="contact" name="contact" value="${auth.contact}" /></dd>
            </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>公司法人姓名&nbsp;&#58;</dt>
                <dd><input type="text" class='form-control' style="width:300px" id="legal_representative" name="legal_representative" value="${auth.legal_representative}" /></dd>
            </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>法人身份证件正面&nbsp;&#58;</dt>
                <dd>
                    <a href="#" data-toggle="modal" data-target="#identityFrontModal" style="float:left">上传</a>
                    <div id="identity_front_img_div" style="float:left;margin-left:20px"><c:if test="${!empty auth.identity_front_img}"><a href="#" onClick="javascript:previewPic('identity_front_img_SRC_imghead');">预览</a></c:if></div>
                    <input type="hidden" id="identity_front_img_SRC" name="identity_front_img_SRC" value="${auth.identity_front_img}" />
                    <img style="display:none" id="identity_front_img_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${auth.identity_front_img}' onclick="showImage(this)">
                     <span>&nbsp;&nbsp;<font style="font-size:12px;" color="#9d9d9d">(证件大小1024KB以内)</font></span>
                </dd>
            </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>法人身份证件反面&nbsp;&#58;</dt>
                <dd>
                    <a href="#" data-toggle="modal" data-target="#identityBackModal" style="float:left">上传</a>
                    <div id="identity_back_img_div" style="float:left;margin-left:20px"><c:if test="${!empty auth.identity_back_img}"><a href="#" onClick="javascript:previewPic('identity_back_img_SRC_imghead');">预览</a></c:if></div>
                    <input type="hidden" id="identity_back_img_SRC" name="identity_back_img_SRC" value="${auth.identity_back_img}" />
                    <img style="display:none" id="identity_back_img_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${auth.identity_back_img}' onclick="showImage(this)">
                    <span>&nbsp;&nbsp;<font style="font-size:12px;" color="#9d9d9d">(证件大小1024KB以内)</font></span>
                </dd>
            </dl>
            <dl class="dl-horizontal">
                <dt><span>&#42;</span>营业执照扫描件&nbsp;&#58;</dt>
                <dd>
                    <a href="#" data-toggle="modal" data-target="#registeredModal" style="float:left">上传</a>
                    <div id="registered_image_div" style="float:left;margin-left:20px"><c:if test="${!empty auth.registered_image}"><a href="#" onClick="javascript:previewPic('registered_image_SRC_imghead');">预览</a></c:if></div>
                    <input type="hidden" id="registered_image_SRC" name="registered_image_SRC" value="${auth.registered_image}" />
                    <img style="display:none" id="registered_image_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${auth.registered_image}' onclick="showImage(this)">
                    <div style='display:inline-block;'>
                        <span>&nbsp;&nbsp;<font style="font-size:12px;" color="#9d9d9d">(证件大小1024KB以内)</font></span>
                        <span><font style="font-size:12px;" color="#9d9d9d">(若证件为三证合一则只需上传一次)</font></span>
                    </div>
                </dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>组织机构代码证扫描件&nbsp;&#58;</dt>
                <dd>
                    <a href="#" data-toggle="modal" data-target="#organizationModal" style="float:left">上传</a>
                    <div id="organization_image_div" style="float:left;margin-left:20px"><c:if test="${!empty auth.organization_image}"><a href="#" onClick="javascript:previewPic('organization_image_SRC_imghead');">预览</a></c:if></div>
                    <input type="hidden" id="organization_image_SRC" name="organization_image_SRC" value="${auth.organization_image}" />
                    <img style="display:none" id="organization_image_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${auth.organization_image}' onclick="showImage(this)">
                    <span>&nbsp;&nbsp;<font style="font-size:12px;" color="#9d9d9d">(证件大小1024KB以内)</font></span>
                </dd>
            </dl>
            <dl class="dl-horizontal">
                <dt>税务登记证扫描件&nbsp;&#58;</dt>
                <dd>
                     <a href="#" data-toggle="modal" data-target="#taxpayerModal" style="float:left">上传</a>
                     <div id="taxpayer_image_div" style="float:left;margin-left:20px"><c:if test="${!empty auth.taxpayer_image}"><a href="#" onClick="javascript:previewPic('taxpayer_image_SRC_imghead');">预览</a></c:if></div>
                     <input type="hidden" id="taxpayer_image_SRC" name="taxpayer_image_SRC" value="${auth.taxpayer_image}" />
                     <img style="display:none" id="taxpayer_image_SRC_imghead" width=100 height=80 border=0 src='${NGINXPATH}${auth.taxpayer_image}' onclick="showImage(this)">
                    <span>&nbsp;&nbsp;<font style="font-size:12px;" color="#9d9d9d">(证件大小1024KB以内)</font></span>
                </dd>
            </dl>
            <c:if test="${auth.status == 3 }">
            	<dl class="dl-horizontal">
		               <dt style="color:red">驳回理由&nbsp;&#58;</dt>
		               <dd><label style="color:red">${auth.regectreason }</label></dd>
		           </dl>
		    </c:if>
        </div>
         <div class="form-group">
	         <div class="col-sm-offset-4 col-sm-10" style="margin-top:40px">
	             <button type="button" style='width:100px;margin-left:-200px;' class="btn sub-btn" onclick="javascript:saveAuth();">保存</button>
	             <button type="button" style="width:100px;margin-left:40px" class="btn sub-btn" onclick="javascript:authentication();">保存并提交</button>
	         </div>
        </div>
    </form>
 </div> 
  
<div id="identityFrontModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
<div class="modal-dialog">
<div class="lic-wrap col-sm-12 ">
	<div class="col-sm-12 h-line pb5 pt10 lic-title ">身份证（正面）</div>
	<div class="col-sm-12">
		<div class="col-sm-9 lic-notice mt10">
			<ul>
				<li><font style="font-size:12px;" color="#9d9d9d">1.请您上传有效的负责人证件。证件应不大于1024KB。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">2.证件扫描件图片完整（不缺少边线）。信息清晰可辨，不得做任何修改。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">3.如果上传图片您的浏览器没有反应。强烈推荐您使用IE9版本以上的浏览器。</font></li>
			</ul>
			<div id="identity_front_img_SRC_example" class="lic-img-preview mt20 ml30">
			     <img src="static/img/preview_1.png" >
			</div>
			<div id="identity_front_img_SRC_preview" >
			 
			</div>
			<div class="lic-btn-upload mt20 h-center">
				<a href="javascript:clickImgUpload('identity_front_img')">点击上传图片</a>
				<input type="file" name="file" id="identity_front_img" style="display:none"  
										filename="identity_front_img" class="btn btn-default" onchange="updateImage(this);" value="${auth.identity_front_img}">
			</div>
		</div>
		<div class="col-sm-3 lic-example">
			<label class="mt20"><font style="font-size:12px;" color="#9d9d9d">示例：</font></label>
			<div class="lic-example-img mt20">
				<img src="static/img/id_fro_ok.jpg" >
			</div>
			<div class="lic-example-img mt20">
				<img src="static/img/id_fro_err.jpg" >
			</div>
			<div class="lic-btns mt40">
				<button class="btn invalid-btn" onclick="javascript:closeModal('identityFrontModal');">关闭</button>
			   <!--  <button class="btn-primary ml30">取消</button> -->
			</div>
		</div>
	</div>
</div>
	 </div>
</div>

 

<div id="identityBackModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
<div class="modal-dialog">
<div class="lic-wrap col-sm-12 ">
	<div class="col-sm-12 h-line pb5 pt10 lic-title ">身份证（反面）</div>
	<div class="col-sm-12">
		<div class="col-sm-9 lic-notice mt10">
			<ul>
				<li><font style="font-size:12px;" color="#9d9d9d">1.请您上传有效的负责人证件。证件应不大于1024KB。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">2.证件扫描件图片完整（不缺少边线）。信息清晰可辨，不得做任何修改。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">3.如果上传图片您的浏览器没有反应。强烈推荐您使用IE9版本以上的浏览器。</font></li>
			</ul>
			<div id="identity_back_img_SRC_example" class="lic-img-preview mt20 ml30">
			     <img src="static/img/preview_1.png" >
			</div>
			<div id="identity_back_img_SRC_preview" >
			 
			</div>
			<div class="lic-btn-upload mt20 h-center">
				<a href="javascript:clickImgUpload('identity_back_img')">点击上传图片</a>
				<input type="file" name="file" id="identity_back_img" style="display:none"  
										filename="identity_back_img" class="btn btn-default" onchange="updateImage(this);" value="${auth.identity_back_img}">
			</div>
		</div>
		<div class="col-sm-3 lic-example">
			<label class="mt20"><font style="font-size:12px;" color="#9d9d9d">示例：</font></label>
			<div class="lic-example-img mt20">
				<img src="static/img/id_back_ok.jpg" >
			</div>
			<div class="lic-example-img mt20">
				<img src="static/img/id_back_err.jpg" >
			</div>
			<div class="lic-btns mt40">
				<button class="btn invalid-btn" onclick="javascript:closeModal('identityBackModal');">关闭</button>
			   <!--  <button class="btn-primary ml30">取消</button> -->
			</div>
		</div>
	</div>
</div>
	 </div>
</div>


 <div id="registeredModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
<div class="modal-dialog">
<div class="lic-wrap col-sm-12 ">
	<div class="col-sm-12 h-line pb5 pt10 lic-title ">营业执照</div>
	<div class="col-sm-12">
		<div class="col-sm-9 lic-notice mt10">
			<ul>
				<li><font style="font-size:12px;" color="#9d9d9d">1.请您上传有效的营业执照证件。证件应不大于1024KB</font>。</li>
				<li><font style="font-size:12px;" color="#9d9d9d">2.证件扫描件图片完整（不缺少边线）。信息清晰可辨，不得做任何修改。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">3.如果上传图片您的浏览器没有反应。强烈推荐您使用IE9版本以上的浏览器。</font></li>
			</ul>
			<div id="registered_image_SRC_example" class="lic-img-preview mt20 ml30">
			     <img src="static/img/preview_1.png" >
			</div>
			<div id="registered_image_SRC_preview" >
			 
			</div>
			<div class="lic-btn-upload mt20 h-center">
				<a href="javascript:clickImgUpload('registered_image')">点击上传图片</a>
				<input type="file" name="file" id="registered_image" style="display:none"  
										filename="registered_image" class="btn btn-default" onchange="updateImage(this);" value="${auth.registered_image}">
			</div>
		</div>
		<div class="col-sm-3 lic-example">
			<label class="mt20">示例：</label>
			<div class="lic-example-img mt20">
				<img src="static/img/yyzz_ok.jpg" >
			</div>
			<div class="lic-example-img mt20">
				<img src="static/img/yyzz_err.jpg" >
			</div>
			<div class="lic-btns mt40">
				<button class="btn invalid-btn" onclick="javascript:closeModal('registeredModal');">关闭</button>
			   <!--  <button class="btn-primary ml30">取消</button> -->
			</div>
		</div>
	</div>
</div>
	 </div>
</div>
 


 <div id="organizationModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" class="modal fade">
<div class="modal-dialog">
<div class="lic-wrap col-sm-12 ">
	<div class="col-sm-12 h-line pb5 pt10 lic-title ">组织机构代码证</div>
	<div class="col-sm-12">
		<div class="col-sm-9 lic-notice mt10">
			<ul>
				<li><font style="font-size:12px;" color="#9d9d9d">1.请您上传有效的组织机构代码证。证件应不大于1024KB。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">2.证件扫描件图片完整（不缺少边线）。信息清晰可辨，不得做任何修改。</font></li>
				<li><font style="font-size:12px;" color="#9d9d9d">3.如果上传图片您的浏览器没有反应。强烈推荐您使用IE9版本以上的浏览器。</font></li>
			</ul>
			<div id="organization_image_SRC_example" class="lic-img-preview mt20 ml30">
			     <img src="static/img/preview_1.png" >
			</div>
			<div id="organization_image_SRC_preview" >
			 
			</div>
			<div class="lic-btn-upload mt20 h-center">
				<a href="javascript:clickImgUpload('organization_image')">点击上传图片</a>
				<input type="file" name="file" id="organization_image" style="display:none"  
										filename="organization_image" class="btn btn-default" onchange="updateImage(this);" value="${auth.organization_image}">
			</div>
		</div>
		<div class="col-sm-3 lic-example">
			<label class="mt20">示例：</label>
			<div class="lic-example-img mt20">
				<img src="static/img/jgdmz_ok.jpg" >
			</div>
			<div class="lic-example-img mt20">
				<img src="static/img/jgdmz_err.jpg" >
			</div>
			<div class="lic-btns mt40">
				<button class="btn invalid-btn" onclick="javascript:closeModal('organizationModal');">关闭</button>
			   <!--  <button class="btn-primary ml30">取消</button> -->
			</div>
		</div>
	</div>
</div>
	 </div>
</div>


<div id="taxpayerModal" tabindex="-1" role="dialog"
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
			<div id="taxpayer_image_SRC_example" class="lic-img-preview mt20 ml30">
			     <img src="static/img/preview_1.png" >
			</div>
			<div id="taxpayer_image_SRC_preview" >
			 
			</div>
			<div class="lic-btn-upload mt20 h-center">
				<a href="javascript:clickImgUpload('taxpayer_image')">点击上传图片</a>
				<input type="file" name="file" id="taxpayer_image" style="display:none"  
										filename="taxpayer_image" class="btn btn-default" onchange="updateImage(this);" value="${auth.taxpayer_image}">
			</div>
		</div>
		<div class="col-sm-3 lic-example">
			<label class="mt20">示例：</label>
			<div class="lic-example-img mt20">
				<img src="static/img/swdjz_ok.jpg" >
			</div>
			<div class="lic-example-img mt20">
				<img src="static/img/swdjz_err.jpg" >
			</div>
			<div class="lic-btns mt40">
				<button class="btn invalid-btn" onclick="javascript:closeModal('taxpayerModal');">关闭</button>
			   <!--  <button class="btn-primary ml30">取消</button> -->
			</div>
		</div>
	</div>
</div>
	 </div>
</div>
 