<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>主页</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/media/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/media/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/media/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/media/css/style-metro.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/media/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/media/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/media/css/default.css" rel="stylesheet" type="text/css" id="style_color" />
<link href="<%=request.getContextPath()%>/media/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/media/css/jquery.gritter.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/media/css/daterangepicker.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/media/css/fullcalendar.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/media/css/jqvmap.css" rel="stylesheet" type="text/css" media="screen" />
<link href="<%=request.getContextPath()%>/media/css/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/media/image/favicon.ico" />
</head>
<body class="page-header-fixed">
	<!-- 1==grey==blue===default====light=====brown====================================================================================== -->
	<div class="header navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="brand" href=""> 后台管理系统</a> 
				<ul class="nav pull-right">
					<!-- 用户名属性=======================================================================================-->
					<li class="dropdown user"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
					<img alt="" src="<%=request.getContextPath()%>/media/image/avatar1_small.jpg" /> <span class="username">${sessionScope.loggingAdmin.account}</span>
							<i class="icon-angle-down"></i>
					</a>
						<ul class="dropdown-menu">
							<li><a href=""><i class="icon-user"></i> 属性1</a></li>
							<li><a href=""><i class="icon-calendar"></i> 属性2</a></li>
							<li><a href=""><i class="icon-envelope"></i> 属性3</a></li>
						</ul></li>
					<!-- 用户名属性=======================================================================================-->
				</ul>
			</div>
		</div>
	</div>
	<!-- 1====================================================================================================== -->
	<!--2====================================================================================================== -->
	<div class="page-container">
		<div class="page-sidebar nav-collapse collapse">
			<ul class="page-sidebar-menu" id="menuUl">
				<li>
					<div class="sidebar-toggler hidden-phone"></div>
				</li>
				<c:forEach items="${menuList}" var="menu">
					<c:if test="${menu.menuPid eq 0 }">
						<li  class=""><a href="javascript:;"> <i class="icon-cogs"></i>
							<span class="title">${menu.menuName}</span> <span class="arrow "></span> </a>
							<ul class="sub-menu"> 
						<c:forEach items="${menuList}" var="menutwo">
							<c:if test="${menutwo.menuPid eq menu.menuId}">
							<li><a href="<%=request.getContextPath()%>/${menutwo.menuUrl}" target="center" onclick="changePageName('${menu.menuName}','${menutwo.menuName}')">${menutwo.menuName}</a></li>
							</c:if>
						</c:forEach>
							</ul>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>

		<div class="page-content">
			<div class="container-fluid">
				<div class="row-fluid">
			<!-- 导航栏==================== -->
					<div class="span12">
						<div class="color-panel hidden-phone">
							<div class="color-mode-icons icon-color"></div>
							<div class="color-mode-icons icon-color-close"></div>
							<div class="color-mode">
								<p>皮肤选择</p>
								<ul class="inline">
									<li class="color-black current color-default"
										data-style="default"></li>
									<li class="color-blue" data-style="blue"></li>
									<li class="color-brown" data-style="brown"></li>
									<li class="color-purple" data-style="purple"></li>
									<li class="color-grey" data-style="grey"></li>
									<li class="color-white color-light" data-style="light"></li>
								</ul>
								<label> <span>Layout</span> <select
									class="layout-option m-wrap small">
										<option value="fluid" selected>Fluid</option>
										<option value="boxed">Boxed</option>
								</select>
								</label> <label> <span>Header</span> <select
									class="header-option m-wrap small">
										<option value="fixed" selected>Fixed</option>
										<option value="default">Default</option>
								</select>
								</label> <label> <span>Sidebar</span> <select
									class="sidebar-option m-wrap small">
										<option value="fixed">Fixed</option>
										<option value="default" selected>Default</option>
								</select>
								</label> <label> <span>Footer</span> <select
									class="footer-option m-wrap small">
										<option value="fixed">Fixed</option>
										<option value="default" selected>Default</option>
								</select>
								</label>
							</div>
						</div>
						<h3 class="page-title">
							欢迎登陆 <small></small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="index.jsp">首页</a>
							 <i class="icon-angle-right"></i>
							 </li><li><a href="" id="pageNameF">页面1</a></li>
							 <li>><a href="" id="pageName">页面2</a></li>
						</ul>
					</div>
					<!-- 导航栏END==================== -->
					<!-- 显示区块==================== -->
				    <iframe style="height: 600px;width: 100%" name="center" frameborder="no"></iframe>
				</div>
			</div>
		</div>
	</div>
	<!-- 置顶 -->
	<div class="footer">
		<div class="footer-tools">
			<span class="go-top"><i class="icon-angle-up"></i>
			</span>
		</div>
	</div>
	</div>
	<!-- --------------------------------------------------------------------------------------- -->

	<script src="<%=request.getContextPath()%>/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery-migrate-1.2.1.min.js"
		type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery-ui-1.10.1.custom.min.js"
		type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.cookie.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.uniform.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.vmap.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.vmap.russia.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.vmap.world.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.vmap.europe.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.vmap.germany.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.vmap.usa.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.vmap.sampledata.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.flot.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.flot.resize.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.pulsate.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/date.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/daterangepicker.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.gritter.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/fullcalendar.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.easy-pie-chart.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/jquery.sparkline.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/app.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/media/js/index.js" type="text/javascript"></script>
	<script>
		jQuery(document).ready(function() {
			App.init(); // initlayout and core plugins
			Index.init();
			Index.initJQVMAP(); // init index page's custom scripts
			Index.initCalendar(); // init index page's custom scripts
			Index.initCharts(); // init index page's custom scripts
			Index.initChat();
			Index.initMiniCharts();
			Index.initDashboardDaterange();
			Index.initIntro();
		});
	</script>
	<script type="text/javascript">
	//页面标题名称
	function changePageName(pageNameF,pageName){
		$("#pageNameF").text(pageNameF);
		$("#pageName").text(pageName);
	}
	</script>
</html>