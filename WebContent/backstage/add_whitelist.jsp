<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增白名单</title>
<title>白名单管理</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<link href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap-select.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap-select.js"></script>
</head>
<body>
<table>
     <tr>
        <td>
		   <button class="btn btn-default" type="button">车牌号</button>
        </td>
        <td>
            <input type="text" class="form-control" id="carId">
<!--             判断该车辆是否已经vip套餐 -->
        </td>
     </tr>
     <tr>
        <td>
		   <button class="btn btn-default" type="button">vip白名单套餐</button>
        </td>
        <td>
            <input type="text" class="form-control" id=""> 
        </td>
     </tr>
</table>
</body>
<script>
$(document).ready(function(){
	$("#carId").blur(function(){
		chechCarId();
	});
	
})
function chechCarId(){
	var carId=$("#carId").val();
	if(carId==null||carId==""){
		alert("请输入车牌号");
		return;
	}
	$.ajax({
	    url: "<%=request.getContextPath()%>/whiteListHander/chechCarId.action",
		type:"POST",
		data:{"carId":$("#carId").val()},
		dataType:"json",
		success : function(data){
			if(data==1){
			alert("该车牌号已是VIP白名单");	
			}else{
				alert("该用户可以办理");	
			}
		}
});
}
</script>
</html>