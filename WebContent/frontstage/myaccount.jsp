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
		<title>慧停车</title>
		<meta name="keywords" content="miniMobile的demo" />
		<meta name="description" content="miniMobile是一个简单易用的移动框架！" />
		<!-- ui css、js -->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/userstyle/css/miniMobile.css"/>
		<script type="text/javascript" src="<%=request.getContextPath()%>/userstyle/js/zepto.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/userstyle/js/miniMobile.js"></script>
		<!-- 字体图标 -->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/userstyle/plugins/fonticon/iconfont.css" />
		<!-- animate.css -->
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.css" />
		<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
		<style>
		#invest{
		margin-left: 170px;
		}
		</style>
	</head>

	<body class="pb12 fadeIn animated">
		<header class="ui-header clearfix w75 h8 f46 pl3 pr3 color8 bg-color-primary t-c">
			<div class="ui-header-l fl w5">
				<a href="frontstage/user_main.jsp" class="icon color8 iconfont icon-home_light"></a>
			</div>
			<div class="ui-header-c fl f30 w59">
				我的账户
			</div>
			<div class="ui-header-r fr w5">
				<i class="icon iconfont icon-phone"></i>
			</div>
		</header><br />
		<div class="p3 f30">
			<!-- <span class="w12 h7 btn radius5 btn-success">按钮</span>
			<span class="w12 h7 btn radius5 btn-info">按钮</span>
			<span class="w12 h7 btn radius5 btn-warning">按钮</span>
			<span class="w12 h7 btn radius5 btn-danger">按钮</span> -->
			<span class="tag tag-warning">账户余额 &nbsp;&nbsp;&nbsp;${sessionScope.loginOwer.owerBalance}.00</span>
			<span class="w12 h7 btn radius5 btn-info" id="invest">充值</span>
		</div>
		<hr />

		<div class="m3 f28 clearfix">
			<div class="btn-select w34 fl">
				<font class="btn-select-content h8 w28 radius5 btn btn-primary">
					<span>下拉导航组件</span>&nbsp;&nbsp;
					<i class="icon iconfont icon-unfold"></i>
				</font>
				<ul class="btn-select-list radio5 bg-color-primary">
					<li>
						<span class="pl3">首页</span>
					</li>
					<li>
						<span class="pl3">关于我们</span>
					</li>
					<li>
						<span class="pl3">新闻中心</span>
					</li>
					<li>
						<span class="pl3">产品中心</span>
					</li>
					<li>
						<span class="pl3">联系我们</span>
					</li>
				</ul>
			</div>
			<div class="btn-select w34 fl">
				<font class="btn-select-content h8 w28 radius5 btn btn-primary">
					<span>下拉导航组件</span>&nbsp;&nbsp;
					<i class="icon iconfont icon-unfold"></i>
				</font>
				<ul class="btn-select-list radio5 bg-color-primary">
					<li>
						<span class="pl3">首页</span>
					</li>
					<li>
						<span class="pl3">关于我们</span>
					</li>
					<li>
						<span class="pl3">新闻中心</span>
					</li>
					<li>
						<span class="pl3">产品中心</span>
					</li>
					<li>
						<span class="pl3">联系我们</span>
					</li>
				</ul>
			</div>
			<script type="text/javascript">
				//下拉组件操作
				$(".btn-select .btn-select-content").tap(function() {
					$(this).siblings(".btn-select-list").toggle();
				});
				$(".btn-select .btn-select-list li").tap(function() {
					$(this).parents(".btn-select").find(".btn-select-content span").text($(this).text());
					$(this).parent(".btn-select-list").hide();
				});
			</script>
		</div>
		<hr />
		<div class="m3 f28 ui-btnlist clearfix">
			<span class="p2 btn btn-primary">首页</span>
			<span class="p2 btn btn-info"><</span>
			<span class="p2 btn btn-warning">1</span>
			<span class="p2 btn btn-info">></span>
			<span class="p2 btn btn-primary">尾页</span>
		</div>
		<hr />
		<div class="p3 f30">
			<span class="w10 h6 btn radius5">按钮</span>
			<span class="w15 h8 btn radius5">按钮</span>
			<span class="w20 h10 btn radius5 disable">按钮</span>
			<button class="w21 h12 btn radius5" disabled="disabled">按钮</button>
			<br /><br />
			<span class="h15 w69 btn f60 radius10">按钮</span>
		</div>
		<hr />
		<div class="p3 f30" style="line-height:1.8em;">
			<span class="tag">tag</span>
			<span class="tag tag-primary">tag-primary</span>
			<span class="tag tag-success">tag-success</span>
			<span class="tag tag-info">tag-info</span>
			<span class="tag tag-warning">tag-warning</span>
			<span class="tag tag-danger">tag-danger</span>
		</div>
	</body>

</html>