<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
<meta charset="UTF-8" />
<meta name="renderer" content="webkit" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=0,uc-fitscreen=yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<title>慧停车</title>
<meta name="keywords" content="miniMobile的demo" />
<meta name="description" content="miniMobile是一个简单易用的移动框架！" />
<!-- ui css、js -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/userstyle/css/miniMobile.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/userstyle/js/zepto.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/userstyle/js/miniMobile.js"></script>
<!-- 字体图标 -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/userstyle/plugins/fonticon/iconfont.css" />
<!-- animate.css -->
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.css" />
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<%-- <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script> --%>
<%-- <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" --%>
<!-- 	rel="stylesheet"> -->
<script>
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
		<div class="ui-header-c fl f30 w59">我的车辆
	    <input type="button" value="返回" onClick="comeBack()" id="back" class="p2 mb4 btn radius5 btn-primary" style="height: 30px;float: right;" >
		</div>
	</header>
<!-- 	<input type="button" value="返回" onClick="comeBack()" id="back" class="p2 mb4 btn radius5 btn-success"> -->
	<c:forEach items="${carList}" var="car">
		<div>
		<span class="p2 mb4 btn radius5 btn-info">${car.carId}</span>
		<span class="p2 mb4 btn radius5 btn-primary">${car.carCdate}</span>
		<span class="p2 mb4 btn radius5 btn-warning">${car.carType==1?"临时车辆":(car.carType==2?"月缴车辆":"vip车辆")}</span>
		<input type="button" onClick="escBangDing('${car.carId}')" value="解绑" class="p2 mb4 btn radius5 btn-danger">
		</div>
	</c:forEach>
		<input type="button" value="新增车辆" id="addCar" class="p2 mb4 btn radius5 btn-success" style="height: 2%"><input type="hidden"  id="owerId" name="owerId" value="${sessionScope.loginOwer.owerId}">

</body>
<script>
$("#addCar").click(function(){
	window.location="<%=request.getContextPath()%>/frontstage/addcar.jsp";
});
function escBangDing(carIds){
	var carId=carIds;
	var owerId=$("#owerId").val();
	if(confirm("确定解除绑定吗？")){
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/owerHandler/escCars.action",
		data:{"carId":carId},
		dataType:"json",
		success:function(data){
			if(data=="1"){
				alert("解绑成功");
				window.location="<%=request.getContextPath()%>/owerHandler/searchOwersCar.action?owerId="+owerId;
			}else{
				alert("解绑失败");
			}
		}
	});
	}
};
</script>
</html>