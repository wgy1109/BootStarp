<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

</div>
</body>
</html>
<div id="modalcontent">
	<div id="workflowshow" tabindex="-1" role="dialog"
		data-backdrop="false" aria-labelledby="myModalLabel"
		aria-hidden="true" class="modal fade">
		<div class="modal-dialog" style="width: 739px">
			<div class="modal-content" style="float: right; width: 777px;"
				style="width:800px">
				<div>
					<img src="static/img/goback.png" onclick="closeflow();"
						style="float: right;">
				</div>
				<img src="static/img/flow.png" style="float: right;">
			</div>
		</div>
	</div>
	<div class="modal-backdrop in" style="display: none; opacity: 0.75;"
		id="modelopacity"></div>
</div>
<!-- =============== VENDOR SCRIPTS ===============-->
<script src="<%=basePath%>static/js/jquery-2.1.1.min.js"></script>
<!-- MODERNIZR-->
<script src="<%=basePath%>static/vendor/bootstrap/dist/js/bootstrap.js"></script>
<script src="<%=basePath%>static/js/jquerySession.js"></script>

<!-- STORAGE API-->
<script
	<script
	src="<%=basePath%>static/vendor/jquery.easing/js/jquery.easing.js"></script><!-- ANIMO-->
<script src="<%=basePath%>static/vendor/animo.js/animo.js"></script>
<!-- DATETIMEPICKER-->
<link rel="stylesheet"
	href="<%=basePath%>static/vendor/datapicker/jquery.datetimepicker.css">
<!-- DATETIMEPICKER-->
<script type="text/javascript"
	src="<%=basePath%>static/vendor/datapicker/jquery.datetimepicker.js"></script>
<!-- SLIMSCROLL-->

<!-- SCREENFULL-->
<script src="<%=basePath%>static/vendor/screenfull/dist/screenfull.js"></script>
<!-- LOCALIZE-->
<script
	src="<%=basePath%>static/vendor/jquery-localize-i18n/dist/jquery.localize.js"></script>
<!-- RTL demo-->
<%-- <script src="<%=basePath%>static/js/demo/demo-rtl.js"></script> --%>
<!-- =============== PAGE VENDOR SCRIPTS ===============-->
<!-- FLATDOC-->
<script src="<%=basePath%>static/vendor/flatdoc/flatdoc.js"></script>
<!-- DATATABLE-->
<script
	src="<%=basePath%>static/vendor/datatables/media/js/jquery.dataTables.js"></script>
<script
	src="<%=basePath%>static/vendor/datatables-colvis/js/dataTables.colVis.js"></script>
<!-- =============== APP SCRIPTS ===============-->
  <script src="<%=basePath%>static/js/app.js"></script>
<script src="<%=basePath%>static/js/common.js"></script>
<script src="<%=basePath%>static/js/plugins/layer2.1/layer.js"></script>
<script
	src="<%=basePath%>static/vendor/bootstrap-filestyle/src/bootstrap-filestyle.js"></script>
<script src="<%=basePath%>static/js/ajaxfileupload.js"></script>
<script
	src="<%=basePath%>static/vendor/datatable-bootstrap/js/dataTables.bootstrap.js"></script>
<!-- 表单验证 -->
<script src="<%=basePath%>static/vendor/validate/jquery.validate.min.js"></script>
<script src="<%=basePath%>static/vendor/validate/messages_zh.min.js"></script>
<script src="<%=basePath%>static/vendor/validate/jquery.metadata.js"></script>
<script
	src="<%=basePath%>static/vendor/slimScroll/jquery.slimscroll.min.js"></script>
<script
	src="<%=basePath%>assets/js/core/jquery.scrollLock.min.js"></script>
<script src="<%=basePath%>assets/js/core/jquery.countTo.min.js"></script>
<script src="<%=basePath%>assets/js/core/jquery.placeholder.min.js"></script>

<!-- Page Plugins -->
<script src="<%=basePath%>assets/js/plugins/slick/slick.min.js"></script>
<script src="<%=basePath%>assets/js/plugins/chartjs/Chart.min.js"></script>
<script src='<%=basePath%>static/js/echarts.min.js'></script>
<!-- bootstrap=silder -->
<script src="<%=basePath%>static/js/bootstrap-slider.js"></script>
<script src='<%=basePath%>static/js/modernizr.js'></script>
<!-- Page JS Code -->
<!--<script src="<%=basePath%>assets/js/pages/base_pages_dashboard.js"></script>-->
<script type="text/javascript">
var LandingType="${sessionScope.LandingType}";
var subAccountLogin = "${sessionScope.subAccountLogin}";
    $(function() {
    	$("#myHomePage").css("color","#009ACB");
    	$("#myHomePage").parent().css("background-color","#37424F");
    	$("#myHomePage").parent().hover(function(){
    		$("#myHomePage").css("color","#009ACB");
        	$("#myHomePage").parent().css("background-color","#37424F");
    	})
    	$("#sidebar").find("a[class='nav-submenu']").hover(function(){
    		$("#myHomePage").css("color","white");
        	$("#myHomePage").parent().css("background-color","#333333");
    	})
           
    	   var url="<%=basePath%>spInfo/mySpinfo.html";
    	   var js="<%=basePath%>static/js/spInfo/mySpinfo.js";
    	   layer.load(2);
    	   if(LandingType!=null&&LandingType=='SpInfo'){
    		   js="<%=basePath%>static/js/spInfo/spInfoView.js";  
    	   }
    	   //如果是子账号，进入应用列表首页
    	   if(subAccountLogin != null && subAccountLogin != ""){
    		   url="<%=basePath%>spInfo/mySpInfoList.html";
    		   js="<%=basePath%>static/js/spInfo/index.js";  
    	   }
    	   
           $('#content').load(url,function (a,b,data) { 
            include(js);
            showInsideLetter();
			layer.closeAll("loading");
						});
		$("#dropdownBtn").hover(function() {
			$(this).addClass('open');
			$(this).find("button").css("background-color", "#394F67");
		}, function() {
			$(this).removeClass('open');
			$(this).find("button").css("background-color", "#009ACB");
		})
		var workflowvalue = <%=s.getAttribute("WORKFLOW")%>;
		if(workflowvalue != null && workflowvalue == '1'){
			$('#workflowshow').modal('show');
			$("#modelopacity").css("display","block");
		}
		$("#sidebar").find("ul[class='nav-main']>li>ul>li>a").click(function(){
			$(this).css({
				"color":"#009ACB",
				"background-color":"#37424F"
			})
			$(this).parent().siblings().find("a").css({
				"color":"white",
				"background-color":"#2B2B2B"
			});
			$(this).parent().parent().parent().siblings().find("ul>li>a").css({
				"color":"white",
				"background-color":"#2B2B2B"
			});
		})


	});
    function closeflow(){
    	$.ajax({
    		url : 'updateflowvalue',
    		type : 'post',
    		success : function(data) {
    			$("#modelopacity").css("display","none");
    			$('#workflowshow').modal('hide');
    		}
    	});
    }
    
	var includePath = "";
	function include(path) {
		includePath = path;
		var a = document.createElement("script");
		a.type = "text/javascript";
		a.src = path+"?rnd="+Math.random();
		var head = document.getElementsByTagName("head")[0];
		head.appendChild(a);
	}
	function goPage(url, js) {
		 if(LandingType!=null&&LandingType=='SpInfo'&&js=='static/js/spInfo/mySpinfo.js'){
  		   js="static/js/spInfo/spInfoView.js";  
  	   }
		if (url) {
			layer.load(2);
			//level 参数标注页面是否是ajax加载,当level为1是为ajax加载
			$('#content').load(url, {
				level : 1
			}, function(response, textStatus, xhr) {
				if (textStatus == 'error') {
					window.location.href = '    ';
				}
				var m = $('#modaljsp').clone(true);
				$('#modaljsp').remove();
				$('#modalcontent').html("");
				$('#modalcontent').append(m);
				//移除之前加载的js 
				$("script[src*='" + includePath + "']").remove();
				//加载js
				include(js);

			});
			layer.closeAll("loading");
		}
	}
	function helpbtn() {
		window.location.href = "downloadhelpbtn";
	}
	
	function ContractDownload() {
		$.get("downloadcontract",function(data){
			if(data=='success'){
				window.location.href = "<%=basePath%>static/word/北京畅卓科技有限公司—短信合同.docx";
			}else{
				layer.msg("企业认证通过后才可下载短信合同");
			}
			
		})
		
	}
	
	function showInsideLetter(){
		  $.get("<%=basePath%>InsideLetter/findUnReadLetter",
						function(v) {
							if (v.unRead && v.unRead > 0) {
								if (v.unRead >= 99) {
									$("#unReadNum").html(99);
									$("#unReadNum").show();
								} else {
									$("#unReadNum").html(v.unRead);
									$("#unReadNum").show();
								}
							} else {
								$("#unReadNum").hide();
							}
							for (var i = v.letterInfo.length - 1; i >= 0; i--) {
								var info = "<li class='dropdown-header'>"
										+ "<div style='width: 75%;overflow:hidden' class='pull-left'>"
										+ v.letterInfo[i].content
										+ "</div>"
										+ "<div style='width: 20%;padding-left:10px' class='pull-left'>"
										+ getDateTime(v.letterInfo[i].create_time)
										+ "</div>" + "</li>";
								$("#insideLetterInfo").after(info);
							}
						});
	}
</script>