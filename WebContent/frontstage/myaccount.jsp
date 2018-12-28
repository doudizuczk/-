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
		<script>
		function addMoney(owerId){
			var sum=$("#balance").val();
			$.ajax({
				type:"post",
				url:"<%=request.getContextPath()%>/owerHandler/addMoney.action",
				data:{"owerId":owerId,"balance":sum},
				datatype:"json",
				success:function(data){
					if(data=="1"){
						alert(sum+"充值成功");
						window.location="<%=request.getContextPath()%>/owerHandler/toMyCount.action?owerId="+owerId;
					}else{
						alert("充值失败");
					}
				}
			});
		};
		function comeBack(){
			window.history.back();
		}
		</script>
	</head>

	<body class="pb12 fadeIn animated">
		<header class="ui-header clearfix w75 h8 f46 pl3 pr3 color8 bg-color-primary t-c">
			<div class="ui-header-l fl w5">
				<a href="<%=request.getContextPath()%>/frontstage/user_main.jsp" class="icon color8 iconfont icon-home_light"></a>
			</div>
			<div class="ui-header-c fl f30 w59">
				我的账户
				<input type="button" value="返回" onClick="comeBack()" id="back" class="p2 mb4 btn radius5 btn-primary" style="height: 30px;float: right;" >
			</div>
<!-- 			<div class="ui-header-r fr w5"> -->
<!-- 				<i class="icon iconfont icon-phone"></i> -->
<!-- 			</div> -->
		</header><br />
		<div class="p3 f30">
			<span class="p2 mb4 btn radius5 btn-warning">账户余额 &nbsp;&nbsp;&nbsp;${owers.balance} .00</span>
		</div>
		<div>
		
		<input type="button" value="充值金额" style="disabled:disabled" class="p2 mb4 btn radius5 btn-info">
		<input type="text" id="balance" name="balance" class="p2 mb4 btn radius5 btn-success" style="background: white;color:#000000;">
		<input type="button" value="充值" id="invest" onClick="addMoney(${owers.owerId})"  class="p2 mb4 btn radius5 btn-success">
<!-- 		<input type="button" value="返回" onClick="comeBack()"> -->
		</div>
		<hr />
	</body>

</html>