	<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	%>
	<aside class="aside"> 
	<div class="aside-inner" id="aside">
		<nav data-sidebar-anyclick-close="" class="sidebar"> <!-- 					START sidebar nav -->
		<ul class="nav">
			<!-- 						START user info -->
			<li class="has-user-block">
				<div id="user-block" class="collapse">
					<div class="item user-block">
						<!-- 									User picture -->
						<div class="user-block-picture">
							<div class="user-block-status">
								<img src="<%=basePath%>static/img/user/02.jpg" alt="Avatar"
									width="60" height="60" class="img-thumbnail img-circle">
									<div class="circle circle-success circle-lg"></div>
							</div>
						</div>
						<!-- 									Name and Job -->
						<div class="user-block-info">
							<span class="user-block-name">Hello, Mike</span> <span
								class="user-block-role">Designer</span>
						</div>
					</div>
				</div>
			</li>
			<!-- 						END user info -->
			<!-- 						Iterates over all sidebar items -->
			<li class="nav-heading "><span
				data-localize="sidebar.heading.HEADER">导航栏</span>
			</li>
			<li class=" "><a href="<%=basePath%>spInfo/mySpinfo.html"
				title="Dashboard"><em class="icon-layers"></em> <span
					data-localize="sidebar.nav.DASHBOARD"> 我的首页</span> </a>
			</li>
			<li class="level"><a href="#layout" title="Layouts"
				data-toggle="collapse"> <em class="icon-layers"></em> <span>账号管理</span>
			</a>
				<ul id="layout" class="nav sidebar-subnav collapse">
					<li class="sidebar-subnav-header">Layouts</li>
					<li class=" "><a href="<%=basePath%>info.html"
						title="Horizontal"> <span>账号信息</span> </a>
					</li>
					<li class="sidebar-subnav-header">Layouts</li>
					<li class=" "><a href="dashboard_h.html" title="Horizontal">
							<span>企业认证</span> </a>
					</li>
					<li class="sidebar-subnav-header">Layouts</li>
					<li class=" "><a href="dashboard_h.html" title="Horizontal">
							<span>财务认证</span> </a>
					</li>
				</ul>
			</li>
			<li class=" "><a href="<%=basePath%>spInfo/findspInfo.html">
					<em class="icon-layers"></em> <span>应用管理</span> </a>
			</li>


			<li class="level"><a href="#message" title="Forms"
				data-toggle="collapse"> <em class="icon-note"></em> <span
					data-localize="sidebar.nav.form.FORM">短信管理</span> </a>
				<ul id="message" class="nav sidebar-subnav collapse">
					<li class="sidebar-subnav-header">Elements</li>
					<li class=" "><a href="<%=basePath%>smsSendTaskClient.html"
						title="Buttons"> <span
							data-localize="sidebar.nav.element.BUTTON">发送列表</span> </a></li>
					<li class=" "><a
						href="<%=basePath%>smsReplyRecodeClient.html"
						title="Notifications"> <span
							data-localize="sidebar.nav.element.NOTIFICATION">短信回复</span> </a></li>
					<li class=" "><a
						href="<%=basePath%>smsSendTaskClient/sendMessage" title="Colors">
							<span data-localize="sidebar.nav.element.COLOR">发送短信</span> </a>
					</li>
					<li class=" "><a
						href="<%=basePath%>smsMasterplateClient.html" title="Colors">
							<span data-localize="sidebar.nav.element.COLOR">短信模板</span> </a></li>
				</ul>
			</li>


			<li class="level"><a href="#forms" data-toggle="collapse"> <em
					class="icon-note"></em> <span data-localize="sidebar.nav.form.FORM">财务管理</span>
			</a>
				<ul id="forms" class="nav sidebar-subnav collapse">
					<li class="sidebar-subnav-header">Forms</li>
					<li class=" "><a
						href="<%=basePath%>chargeRecord/directPaySpInfo" title="Extended">
							<span data-localize="sidebar.nav.form.EXTENDED">充值</span> </a>
					</li>
					<li class=" "><a
						href="<%=basePath%>chargeRecord/chargeRecordIndex.html"
						title="Validation"> <span
							data-localize="sidebar.nav.form.VALIDATION">订单列表</span> </a>
					</li>
					<li class=" "><a href="form-validation.html"
						title="Validation"> <span
							data-localize="sidebar.nav.form.VALIDATION">消费历史</span> </a>
					</li>
					<li class=" "><a
						href="<%=basePath%>appFinanceInvoiceClient.html"
						title="Validation"> <span
							data-localize="sidebar.nav.form.VALIDATION">发票</span> </a></li>
				</ul>
			</li>
		</ul>
		<!-- 					END sidebar nav --> </nav>
	</div>
	</aside>
