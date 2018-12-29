<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
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
		<!-- swiper -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/userstyle/css/swiper.min.css">
		<script src="<%=request.getContextPath()%>/userstyle/js/swiper.min.js"></script>
		<!-- animate.css -->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/userstyle/css/animate.css" />
	</head>

	<body class="fadeIn animated">
		<section class="userbox p5 bg-color-success pl5 clearfix">
				<div class="userbox-l fl w20">
					<img src="<%=request.getContextPath()%>/userstyle/img/user2.jpg" class="radius-o col-12" />
				</div>
				<div class="userbox-r fl w41 pl4 pt3 color8">
					<p class="f60">${sessionScope.loginOwer.owerName}</p>
					<p class="f34"><b>${sessionScope.loginOwer.owerAccount}</b></p>
				</div>
			</section>
		<style>
			.swiper-button-next,
			.swiper-button-prev {
				/*swiper 默认图标适应性较差，使用rem单位规定左右按钮大小，图标大小*/
				width: 0.3rem !important;
				height: 0.5rem !important;
				background-size: cover !important;
				margin-top: -0.23rem !important;
			}
		</style>
		<!--  -->
		<div class="t-c f28 p6 color4 bg-color6">
			<p>
			<marquee onmouseout=this.start() onmouseover=this.stop() behavior="scroll" direction="up" width="850px" height="80px" SCROLLDELAY="320" >
			<c:if test="${empty tranList}">
				<h5>未查询到车辆套餐信息</h5>
			</c:if>
			<c:if test="${not empty tranList}">
			<c:forEach items="${tranList}" var="tran">
				<h5>套餐内车辆 ${tran.carId}</h5>
				<h5>套餐截止日期 ${tran.tranEtime}:00</h5>
			</c:forEach>
			</c:if>
			</marquee>
			</p>
		</div>
		<!-- 导航 -->
		<section class="demo-nav t-c f28 clearfix">
			<div class="col-3 h19">
				<a href="<%=request.getContextPath()%>/frontstage/addcar.jsp" class="pt4 pb4"><i class="f42 color-primary icon iconfont icon-anniu"></i>
					<p>新增车辆</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="<%=request.getContextPath()%>/owerHandler/searchOwersCar.action?owerId=${sessionScope.loginOwer.owerId}" class="pt4 pb4">
					<i class="f46 color-success icon iconfont icon-dialog"></i>
					<p>我的车辆</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="<%=request.getContextPath()%>/owerHandler/getMeans.action?owerId=${sessionScope.loginOwer.owerId}" class="pt4 pb4"><i class="f44 color-info icon iconfont icon-biaoge"></i>
					<p>我的资料</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="<%=request.getContextPath()%>/owerHandler/toMyCount.action?owerId=${sessionScope.loginOwer.owerId}" class="pt4 pb4"><i class="f44 color-warning icon iconfont icon-iconfontliebiao1copy"></i>
					<p>我的账户</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="<%=request.getContextPath()%>/frontstage/checkage.jsp" class="pt4 pb4"><i class="f46 color-danger icon iconfont icon-tupian"></i>
					<p>实名认证</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="<%=request.getContextPath()%>/owerHandler/searchPayNotes.action" class="pt4 pb4"><i class="f46 color-warning icon iconfont icon-menu"></i>
					<p>账单管理</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="<%=request.getContextPath()%>/backstage/pack_Refund2.jsp" class="pt4 pb4"><i class="f50 color-info icon iconfont icon-font"></i>
					<p>套餐办理</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="<%=request.getContextPath()%>/carLocation/toSearchCar.action" class="pt4 pb4"><i class="f50 color-primary icon iconfont icon-zhediemianban"></i>
					<p>反向寻车</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="#"  class="pt4 pb4"><i class="f42 color-warning icon iconfont icon-pubuliu"></i>
					<p>缴纳停车费</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="#" class="pt4 pb4"><i class="f50 color-success icon iconfont icon-ic_view_carousel_px"></i>
					<p>敬请期待...</p>
				</a>
			</div>
		</section>
		<style>
			.demo-nav {
				line-height: 1.8em;
			}
			
			.demo-nav div {
				border-left: 1px solid #f1f1f1;
				border-bottom: 1px solid #f1f1f1;
			}
			
			.demo-nav a {
				display: block;
				width: 100%;
				height: 100%;
			}
			
			.demo-nav div:nth-child(4n+1) {
				border-left: none;
			}
		</style>
		<style type="text/css">
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