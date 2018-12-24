<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<meta charset="UTF-8" />
		<meta name="renderer" content="webkit" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=0,uc-fitscreen=yes" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<meta name="format-detection" content="telephone=no" />
		<title>miniMobile</title>
		<meta name="keywords" content="miniMobile的demo" />
		<meta name="description" content="miniMobile是一个简单易用的移动框架！" />
		<!-- miniMObile.css、js -->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/userstyle/css/miniMobile.css"/>
		<script type="text/javascript" src="<%=request.getContextPath()%>/userstyle/js/zepto.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/userstyle/js/miniMobile.js"></script>
		<!-- fonticon -->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/userstyle/plugins/fonticon/iconfont.css" />
		<!-- animate.css -->
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.css" />
		<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
	</head>

	<body class="fadeIn animated bg-color7">
		<article class="w75 o-h f36">
			<section class="userbox p5 bg-color-success pl5 clearfix">
				<div class="userbox-l fl w20">
					<img src="<%=request.getContextPath()%>/userstyle/img/user2.jpg" class="radius-o col-12" />
				</div>
				<div class="userbox-r fl w41 pl4 pt3 color8">
					<p class="f60">${ower.owerName}</p>
					<p class="f34"><b>${ower.owerAccount}</b></p>
				</div>
			</section>
			<ul class="mt5 mb5 f36 color3">
				<li class="bg-color8 clearfix">
					<a href="">
						<p class="w15 t-c fl">
							<i class="icon iconfont icon-servicefill f42 color-success"></i>
						</p>
						<p class="w60 fl">
							<font class="fr pr5"><i class="icon iconfont icon-arrow-right"></i></font>
							联系我们
						</p>
					</a>
				</li>
			</ul>
			<ul class="mt5 mb5 f36 color3">
			<li class="bg-color8 clearfix">
					<a href="<%=request.getContextPath()%>/owerHandler/getMeans.action?owerId=${sessionScope.loginOwer.owerId}">
						<p class="w15 t-c fl">
							<i class="icon iconfont icon-deletefill f42 color-primary"></i>
						</p>
						<p class="w60 fl">
							<font class="fr pr5"><i class="icon iconfont icon-arrow-right"></i></font>
							我的资料
						</p>
					</a>
				</li> 
				<li class="bg-color8 clearfix">
					<a href="<%=request.getContextPath()%>/frontstage/myaccount.jsp">
						<p class="w15 t-c fl">
							<i class="icon iconfont icon-cartfill f42 color-danger"></i>
						</p>
						<p class="w60 fl">
							<font class="fr pr5"><i class="icon iconfont icon-arrow-right"></i></font>
							我的账户
						</p>
					</a>
				</li>
				<li class="bg-color8 clearfix">
					<a href="<%=request.getContextPath()%>/frontstage/addcar.jsp">
						<p class="w15 t-c fl">
							<i class="icon iconfont icon-deletefill f42 color-primary"></i>
						</p>
						<p class="w60 fl">
							<font class="fr pr5"><i class="icon iconfont icon-arrow-right"></i></font>
							新增车辆
						</p>
					</a>
				</li> 
				<li class="bg-color8 clearfix">
					<a href="<%=request.getContextPath()%>/frontstage/checkage.jsp">
						<p class="w15 t-c fl">
							<i class="icon iconfont icon-deletefill f42 color-primary"></i>
						</p>
						<p class="w60 fl">
							<font class="fr pr5"><i class="icon iconfont icon-arrow-right"></i></font>
							实名认证
						</p>
					</a>
				</li> 
				<li class="bg-color8 clearfix">
					<a href="">
						<p class="w15 t-c fl">
							<i class="icon iconfont icon-iconfontliebiao1copy f42 color-info"></i>
						</p>
						<p class="w60 fl">
							<font class="fr pr5"><i class="icon iconfont icon-arrow-right"></i></font>
							账单管理
						</p>
					</a>
				</li>
				<li class="bg-color8 clearfix">
					<a href="<%=request.getContextPath()%>/owerHandler/searchOwersCar.action?owerId=${sessionScope.loginOwer.owerId}">
						<p class="w15 t-c fl">
							<i class="icon iconfont icon-rankfill f42 color-warning"></i>
						</p>
						<p class="w60 fl">
							<font class="fr pr5"><i class="icon iconfont icon-arrow-right"></i></font>
							我的车辆
						</p>
					</a>
				</li> 
				<li class="bg-color8 clearfix">
					<a href="<%=request.getContextPath()%>/owerHandler/searchPack.action?owerId=${sessionScope.loginOwer.owerId}">
						<p class="w15 t-c fl">
							<i class="icon iconfont icon-cartfill f42 color-danger"></i>
						</p>
						<p class="w60 fl">
							<font class="fr pr5"><i class="icon iconfont icon-arrow-right"></i></font>
							月缴办理
						</p>
					</a>
				</li>
			</ul>
			<ul class="mt5 mb5 f36 color3">
				<li class="bg-color8 clearfix">
					<a href="">
						<p class="w15 t-c fl">
							<i class="icon iconfont icon-zhediemianban f42 color-success"></i>
						</p>
						<p class="w60 fl">
							<font class="fr pr5"><i class="icon iconfont icon-arrow-right"></i></font>
							设置
						</p>
					</a>
				</li>
			</ul>
		</article>
		<!-- 底部导航 -->
		<nav class="demo-bottomNav mt6 w75 h12 pt1 t-c f28 bg-color8 o-h clearfix">
			<div class="w15 fl">
				<a href="user_main.jsp">
					<i class="f46 icon iconfont icon-home_light"></i>
					<p>首页</p>
				</a>
			</div>
			<div class="w15 fl">
				<a href="">
					<i class="f46 icon iconfont icon-comment"></i>
					<p>左滑</p>
				</a>
			</div>
			<div class="w15 fl">
				<a href="">
					<i class="f46 icon iconfont icon-rank"></i>
					<p>列表</p>
				</a>
			</div>
			<div class="w15 fl">
				<a href="">
					<i class="f46 icon iconfont icon-list"></i>
					<p>评论</p>
				</a>
			</div>
			<div class="w15 fl">
				<a href="my.jsp">
					<i class="f46 icon iconfont icon-servicefill"></i>
					<p>我的</p>
				</a>
			</div>
		</nav>
		<style type="text/css">
			.userbox {
				line-height: 2em;
				background: url(userstyle/img/s5.jpg) center 40% no-repeat;
				background-size: cover
			}
			
			.userbox-l img {
				border: 2px solid #fff;
			}
			
			li {
				line-height: 2.5em;
				border-top: 1px solid #F1F1F1;
				border-bottom: 1px solid #F1F1F1;
				margin-top: -1px;
			}
			
			li a {
				display: block;
				width: 100%;
				height: 100%;
			}
			
			.demo-bottomNav {
				line-height: 1.8em;
				border-top:1px solid #F1F1F1;
			}
			
			.demo-bottomNav a {
				display: block;
				width: 100%;
				height: 100%;
			}
		</style>

	</body>

</html>