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

<%String[] addressStrArray = new String[]{"零","一","二","三","四","五","六","七","八","九","十"}; %>

<script src="<%=basePath%>static/js/plugins/layer2.1/layer.js"></script>
<style>
.myAdress {
    width: 80%;
    margin-left: 250px;
    height: 1550px;
    padding-top: 20px;
    background-color: white;
    margin-top: 120px;
    margin-bottom: 125px;
}

.myAdress_top {
    height: 40px;
    width: 246px;
    background-color: #449BFF;
    margin-bottom: 49px;
    color: white;
    line-height: 40px;
    font-size: 16px;
    text-align: center;
    font-family: '微软雅黑';
}

.myAdress_topT {
    margin-bottom: 40px;
    margin-left: 10%;

}

.myAdress_top1 {
    margin-left: 10%;
    height: 306px;
    width: 45%;
    background-image: url(../img/adbackgorund.png);
    background-repeat: round;
    margin-bottom: 40px;
    border: 1px solid #B2B2B2;
    border-radius: 8px 8px 8px 8px;
    /*padding-top:36px;*/

}

.deleteFloat {
    width: 46px;
    height: 36px;
    background-image: url(../img/deleteIcon.png);
    background-repeat: no-repeat;
    background-position: right top;
    float: right;
    border-top-right-radius: 32px;
}

.myAdress_top1_p1 {
    margin-bottom: 10px;
}

.myAdress_top1_p1 span {
    display: inline-block;
    margin-top: 36px;
    margin-left: 6%;
}

/*******************myAdress_top1_second---start**********************/
.myAdress_top1_second {
    height: 110px;
    margin-bottom: 16px;
}

.myAdress_top1_second_left {
    width: 20%;
    height: 100%;
    /*background-color: pink;*/
    float: left;
}

.myAdress_top1_second_right {
    width: 80%;
    height: 100%;
    /*background-color: green;*/
    float: left;
}

.myAdress_top1_second_left div, .myAdress_top1_second_right div {
    height: 25%;
}

.myAdress_top1_second_left div span {
    float: right;
}

/*******************myAdress_top1_second结束*********************************************/
.myAdress_top2 {
    margin-left: 10%;
    height: 246px;
    width: 45%;
    /*background-color: pink;*/
    margin-bottom: 40px;
    border: 1px solid #B2B2B2;
    border-radius: 8px 8px 8px 8px;
}

.myAdress_top3 {
    margin-left: 10%;
    height: 246px;
    width: 45%;
    /*background-color: pink;*/
    border: 1px solid #B2B2B2;
    border-radius: 8px 8px 8px 8px;
}

.myAdress_top1 {
    margin-left: 10%;
}

/*****************************************************************************/
.appRightTop {
    width: 80%;
    height: 558px;
    background-color: white;
    padding-top: 20px;
    margin-left: 250px;
    margin-top: 130px;
    margin-bottom: 300px;
}

.appRightTop_top {
    width: 157px;
    height: 40px;
    background-color: #449bff;
    margin-bottom: 28px;
}

.appRightTop_A a {
    display: block;
    width: 88px;
    height: 32px;
    background-color: #449AFF;
    float: right;
    border-radius: 5px;
    margin-right: 20px;
    line-height: 32px;
    text-align: center;
    margin-bottom: 10px;
    color: white;
}

.appRightTop_topTab {
    background-color: #0aaadd;
    font-size: 14px;
    color: white;
    height: 45px;
    line-height: 45px;

}

.appRightTop_top p {
    line-height: 40px;
    color: white;
    font-size: 20px;
    text-align: center;
}

.appRightTop_table {
    border: 1px solid black;
    text-align: center;
    line-height: 49px;
    width: 98%;
    margin-left: 10px;
    margin-right: 10px;
    margin: 0 auto;
    border-top-left-radius: 10px;
}

.appRightTop_table td {
    border: 1px solid #D8D8D8;
    height: 49px;
}

.charging, .delete, .appling, .modify {
    margin-left: 10px;
    margin-right: 10px;
}

.charging a {
    color: #f3af5b;
}

.delete a {
    color: #ea6066;
}

.appling a {
    color: #38b48f;
}

.modify a {
    color: #0000ff;
}

.td_span {
    display: inline-block;
    width: 108px;
    height: 50px;
    border: 1px solid red;
    position: absolute;
    border-radius: 25px 25px 25px 1px;
    left: 90px;
    top: -30px;
    color: #333333;
    display: none;
}
</style>

<div class='chanzorDataList' style="height:2000px">
        <div class='chanzorDataList_title'><p>我的地址</p></div>
        
        <div class='myAdress_topT'>
            <input type="button"  value='新增收货地址' class="btn sub-btn" style="width:100px" onclick="javascript:beginNewAddress();" /> <span style="padding-left: 20px;
">您已经创建<c:out value="${addressList.size()}" />个收货地址，最多可以创建10个</span>
        </div>
       <div class='myAdress_top1' id="addressNew" style="display:none">
	            <div class='deleteFloat'></div>
	            <p class='myAdress_top1_p1'><span style='float:right;'><a href="#" style='margin-right:12px;' onclick="saveAddress();">保存</a></span></p>
	            <p class='myAdress_top1_p1'><span>新增地址</span></p>
	            <div class='myAdress_top1_second'>
	                <div class='myAdress_top1_second_left'>
	                    <div class="form-group"><span><font color="red">*</font>收件人：</span></div>
	                    <div class="form-group"><span><font color="red">*</font>地址：</span></div>
	                    <div class="form-group"><span>手机：</span></div>
	                    <div class="form-group"><span>座机：</span></div>
	                </div>
	                <form name="addressForm" id="addressForm" >
		                <div class='myAdress_top1_second_right'>
		                    <div><span><input type="text" id="collect_name" class="form-group" style="width:280px" name="collect_name" value=""  maxLength="100" /></span></div>
		                    <div><span><input type="text" id="collect_address" class="form-group" style="width:280px" name="collect_address" value=""  maxLength="100" /></span></div>
		                    <div><span><input type="text" id="collect_phone" class="form-group" style="width:280px" name="collect_phone" value=""  maxLength="15" /></span></div>
		                    <div><span><input type="text" id="collect_tel" class="form-group" style="width:280px" name="collect_tel" value=""  maxLength="15" /></span></div>
		                </div>
	                </form>
	            </div>
	            <p style='float:left;'>&nbsp;&nbsp;&nbsp;<font color="red">注：手机，座机至少需要填写一项  <p style='float:right;'></font>
	    </div>
        
        <c:forEach var="item" items="${addressList}" varStatus="status">
	         <div class='myAdress_top1' id="addressRead${item.id}">
	            <div class='deleteFloat'></div>
	            <p class='myAdress_top1_p1' style='float:right;'><span id="defaultSpan${item.id}" ><c:if test="${item.default_mark == 0}"><a href="#" onclick="javascript:setDefaultAddress('${item.id}');" style='margin-right:10px;'>设为默认</a></c:if ><c:if test="${item.default_mark == 1}"> <font style="font-weight: bold;">默认地址&nbsp;&nbsp;&nbsp;</font></c:if></span><a href="#" style='margin-right:12px;' onclick="javascript:beginEdit('${item.id}')">编辑</a><a href="#" style='margin-right:12px;' onclick="javascript:delAddress('${item.id}');">删除</a></p>
	            <p class='myAdress_top1_p1'><span style="font-weight: bold;">地址<c:set var="currAddress" value="${status.index + 1}" scope="request" /><%=addressStrArray[Integer.parseInt(((Long)request.getAttribute("currAddress")).toString())]%></span>
	            <div class='myAdress_top1_second'>
	                <div class='myAdress_top1_second_left'>
	                    <div><span>收件人：</span></div>
	                    <div><span>地址：</span></div>
	                    <div><span>手机：</span></div>
	                    <div><span>座机：</span></div>
	                </div>
	                <div class='myAdress_top1_second_right'>
	                    <div><span id="collect_nameRead${item.id}"><c:out value="${item.collect_name}" /></span></div>
	                    <div><span id="collect_addressRead${item.id}"><c:out value="${item.collect_address}" /></span></div>
	                    <div><span id="collect_phoneRead${item.id}"><c:out value="${item.collect_phone}" /></span></div>
	                    <div><span id="collect_telRead${item.id}"><c:out value="${item.collect_tel}" /></span></div>
	                </div>
	            </div>
	            
	        </div>
	        <div class='myAdress_top1' id="addressEdit${item.id}" style="display:none">
	            <div class='deleteFloat'></div>
	            <p class='myAdress_top1_p1'><span style='float:right;'><a href="#" style='margin-right:12px;' onclick="editAddress('${item.id}');">保存</a></span></p>
	            <p class='myAdress_top1_p1'><span>地址<c:set var="currAddress" value="${status.index + 1}" scope="request" /><%=addressStrArray[Integer.parseInt(((Long)request.getAttribute("currAddress")).toString())]%> </span></p>
	            <div class='myAdress_top1_second'>
	                <div class='myAdress_top1_second_left'>
	                    <div class="form-group"><span><font color="red">*</font>收件人：</span></div>
	                    <div class="form-group"><span><font color="red">*</font>地址：</span></div>
	                    <div class="form-group"><span>手机：</span></div>
	                    <div class="form-group"><span>座机：</span></div>
	                </div>
	                <form name="addressForm${item.id}" id="addressForm${item.id}" >
	                    <input type="hidden" name="id" value="${item.id}" />
		                <div class='myAdress_top1_second_right'>
		                  	<div><span><input type="text" class="form-group" style="width:280px" id="collect_nameEdit${item.id}" name="collect_name" value="${item.collect_name}"  maxLength="100" /></span></div>
		                    <div><span><input type="text" class="form-group" style="width:280px" id="collect_addressEdit${item.id}" name="collect_address" value="${item.collect_address}"  maxLength="100" /></span></div>
		                    <div><span><input type="text" class="form-group" style="width:280px" id="collect_phoneEdit${item.id}" name="collect_phone" value="${item.collect_phone}"  maxLength="15" /></span></div>
		                    <div><span><input type="text" class="form-group" style="width:280px" id="collect_telEdit${item.id}" name="collect_tel" value="${item.collect_tel}"  maxLength="15" /></span></div>
		                </div>
	                </form>
	            </div>
	            <p style='float:left;'>&nbsp;&nbsp;&nbsp;<font color="red">注：手机，座机至少需要填写一项  </font></p>
	        </div>
        </c:forEach>
  </div>      