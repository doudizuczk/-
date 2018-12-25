<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>反向寻车</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<div>
	<input type="text" id="carId"> 
	<input type="button" value="查询" onclick="queryCar()">
	<input type="button" value="开始导航" onclick="navigation()">
	</div>
	<div>
		<table>
			<thead>
				<tr>
					<th>车牌号</th>
					<th>车主名</th>
					<th>车位ID</th>
					<th>所属区域</th>
					<th>车辆颜色</th>
					<th>入场时间</th>
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

</script>

</html>