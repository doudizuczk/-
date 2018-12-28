<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta name="renderer" content="webkit" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=0,uc-fitscreen=yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<title>反向寻车</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<%-- <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script> --%>
<%-- <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet"> --%>
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
<body class="pb12 fadeIn animated">
<header
		class="ui-header clearfix w75 h8 f46 pl3 pr3 color8 bg-color-primary t-c">
		<div class="ui-header-l fl w5">
			<a href="<%=request.getContextPath()%>/frontstage/user_main.jsp" class="icon color8 iconfont icon-home_light"></a>
		</div>
		<div class="ui-header-c fl f30 w59">反向寻车
		<input type="button" value="返回" onClick="comeBack()" id="back" class="p2 mb4 btn radius5 btn-primary" style="height: 30px;float: right;" >
		</div>
<!-- 		<div class="ui-header-r fr w5"> -->
<!-- 			<i class="icon iconfont icon-phone"></i> -->
<!-- 		</div> -->
	</header>
	<div>
	<input type="text" id="carId" class="p2 mb4 btn radius5 btn-success" style="background: white;color:#000000;"> 
	<input type="button" value="查询" onclick="queryCar()" class="p2 mb4 btn radius5 btn-success">
	<input type="button" value="开始导航" onclick="navigation()" class="p2 mb4 btn radius5 btn-warning">
	</div>
	<div class="m2 t-l colorbox clearfix color3">
		<table>
			<thead>
				<tr>
					<th >车牌号</th>
					<th >车主名</th>
					<th >车位ID</th>
					<th >所属区域</th>
					<th >车辆颜色</th>
					<th >入场时间</th>
				</tr>
			</thead>
			<tbody id="info">
			</tbody>
		
		</table>
	</div>
</body>
<script>
	var xCoords;//车位模型的x周坐标
	var yCoords;//车位模型y轴坐标
	var twoId;//车位模型的id
	//查询该车牌号所对应车辆的信息 
	function queryCar(){
		var carId=$("#carId").val();
		$.ajax({
			type:"post",
			url:"<%=request.getContextPath()%>/carLocation/queryCarInfo.action",
			data:{"carId":carId},
			dataType:"json",
			success:function(data){
				console.log(data);
				if(data[0].owerName==null){
					data[0].owerName='未绑定';
				}
				var msg="";
				msg+="<tr><td>"+data[0].carId+"</td><td>"+data[0].owerName+"</td><td>"+data[0].parkId+"</td><td>"+data[0].area+"</td><td>"+data[0].color+"</td><td>"+data[0].iDate+"</td></tr>";	
				xCoords=data[0].xCoord;
				yCoords=data[0].yCoord;
				twoId=data[0].twoId;
				$("#info").html(msg);
			}
			
			
		})
	}
	
	//开始进行反向寻车导航 
	function navigation(){
		if(xCoords==null||yCoords==null){
			window.alert("未查询出您车辆的信息");
			return;
		}
		window.location.href = "<%=request.getContextPath()%>/frontstage/startnav.jsp?xCoords="+xCoords+"&yCoords="+yCoords+"&twoId="+twoId;
	}
function comeBack(){
		window.history.back();
}
</script>

</html>