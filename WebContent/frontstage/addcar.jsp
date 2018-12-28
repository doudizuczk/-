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
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/userstyle/css/styles.css">
		<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
	</head>
	<body class="pb12 fadeIn animated">
		<header class="ui-header clearfix w75 h8 f46 pl3 pr3 color8 bg-color-primary t-c">
			<div class="ui-header-l fl w5">
				<a href="<%=request.getContextPath()%>/frontstage/user_main.jsp" class="icon color8 iconfont icon-home_light"></a>
			</div>
			<div class="ui-header-c fl f30 w59">
				新增车辆
			</div>
<!-- 			<div class="ui-header-r fr w5"> -->
<!-- 				<i class="icon iconfont icon-phone"></i> -->
<!-- 			</div> -->
		</header><br />
		
		<form id="carForm">
		<input type="hidden" value="${sessionScope.loginOwer.owerId}" id="owerId" name="owerId">
		<input type="button" value="车牌号" class="p2 mb4 btn radius5 btn-info"><input id="carId" name="carId" class="p2 mb4 btn radius5 btn-success" style="background: white;color:#000000;"><br>
		<input type="button" value="车辆颜色" class="p2 mb4 btn radius5 btn-info"><input id="carColor" name="carColor" class="p2 mb4 btn radius5 btn-success" style="background: white;color:#000000;"><br>
<!-- 		<input type="button" value="取消" class="p2 mb4 btn radius5 btn-success" id="cancel" style="float:left;"> -->
         <div style="width: 60%">
         <div style="float: right;"> 
		<input type="button" value="确定" class="p2 mb4 btn radius5 btn-success" id="confirm" onClick="addMyCar()" >
		<input type="button" value="返回" onClick="comeBack()" class="p2 mb4 btn radius5 btn-success" style="float: right;margin-left: 50px">
		</div>
		</div>
		</form>
		
		
	</body>
	<script>
	function comeBack(){
		window.history.back();
	}
	/*车牌验证*/
	function isVehicleNumber(vehicleNumber) {
	      var result = false;
	      if (vehicleNumber.length == 7){
	        var express = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
	        result = express.test(vehicleNumber);
	      }
	      return result;
	  };
	function addMyCar(){
		var carId=$("#carId").val();
		var carColor=$("#carColor").val();
		var owerId=$("#owerId").val();
		if(isVehicleNumber(carId)){
		$.ajax({
			type:"post",
			url:"<%=request.getContextPath()%>/owerHandler/addCars.action",
			data:JSON.stringify($("#carForm").serializeJSON()),
			contentType:"application/json",
			dataType:"json",
			success:function(data){
				if(data=="1"){
					alert("绑定成功");
				}else if(data=="0"){
					alert("绑定失败");
				}else{
					alert("未通过实名认证，无法绑定");
				}
			}
		});
		}else{
			alert("请输入正确的车牌号");
		}
	};
	</script>
<style>
#cancel{
margin-left: 100px;
}
</style>
</html>