<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<!-- 自助缴费主页面 -->
<head>
<meta charset="UTF-8">
<title>自助服务</title>
<meta charset="utf-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/carstyle/css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="<%=request.getContextPath()%>/carstyle/css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="<%=request.getContextPath()%>/carstyle/css/style.css" type="text/css" media="all">
<script type="text/javascript" src="<%=request.getContextPath()%>/carstyle/js/jquery-1.4.2.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/carstyle/js/cufon-yui.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/carstyle/js/Copse_400.font.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/carstyle/js/jquery.nivo.slider.pack.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/carstyle/js/imagepreloader.js"></script>
<script type="text/javascript">
$(function () {
	preloadImages([
	'images/menu1_active.gif',
	'images/menu2_active.gif',
	'images/menu3_active.gif',
	'images/menu4_active.gif',
	'images/marker_right_active.jpg',
	'images/marker_left_active.jpg',
	'images/menu5_active.gif']);
	
	function carLogin(){
		if($("#carId").val()==null || $("#carId").val()==''){
			alert("输入车牌号为空！");
			return;
		}
		
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/carHandler/carLogin.action",
			data : {"carId" : $("#carId").val()},
			success : function(data) {
				console.log(data);
				if (data == '1') {
					alert("车辆登录成功！");
				} else {
					alert("车辆登录失败！");
				}
			},
			error:function(){
				window.alert("未知错误!");
			}
		});
	}
	
	
	
});


</script>
</head>
<body id="page1">
<div class="body1">
	<div class="body2">
		<div class="main">
<!-- header -->
			<header>
				<div class="wrapper">
					<h1><a href="index.html" id="logo">First</a></h1>
					<div class="right">
						<nav>
							<ul id="top_nav">
								<li><a href="#">当前车辆</a></li>
<!-- 								<li><a href="#">搜索</a></li> -->
							</ul>
							<form id="search" method="post">
								<div>
									<input type="button" class="submit" value="" onclick="carLogin()">
									<input id="carId" class="input" placeholder="请输入车牌..">
								</div>
							</form>
						</nav>
					</div>
				</div>	 
				<nav id="menu">
					<ul>
					    <li class="nav1"><a href="">主页</a></li>
						<li class="nav2"><a href="" target="center">自助缴费</a></li>
						<li class="nav3"><a href="" target="center">月缴办理</a></li>
						<li class="nav4"><a href="" target="center">车辆信息</a></li>
						<li class="nav5"><a href="" target="center">我是车主</a></li>
					</ul>
				</nav>
			</header>
			</div>
		</div>
	</div>
	<div class="body3"style="height:100%">
		<div class="body4">
			<iframe style="height: 100%;width: 100%" name="center"></iframe>
		</div>
	</div>
	<div class="body8">
	<div class="body9">
		<div class="main">
		</div>
	</div>
</div>
</body>
</html>