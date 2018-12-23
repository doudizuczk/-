<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/carstyle/js/jquery-1.4.2.js"></script>
<script type="text/javascript">

//查询停车费用信息
function payment(){
	if($("#carId").val()==null || $("#carId").val()==''){
		alert("输入车牌号为空！");
		return;
	}
	
	$.ajax({
		type : "post",
		url : "<%=request.getContextPath()%>/chargeHander/payment.action",
		data : {"carId" : $("#carId").val()},
		dataType : 'json',
		success : function(data) {
			console.log(data);
			if (data != '') {
				var str="";
				str+="车牌号："+data.carId+"<br>";
				str+="入场时间："+data.startTime+"<br>";
				str+="停车费："+data.cost+"元<br>";
				str+="<button onclick='pay("+data.cost+",&quot;"+data.carId+"&quot;)'>立即缴费</button>";
				
				$("#show").html(str);
			} else {
				alert("缴费信息载入失败！请到柜台缴费");
			}
		},
		error : function() {
			window.alert("未知错误!");
		}
	});
}

//立即缴费
function pay(cost,carId){
	$.ajax({
		type : "post",
		url : "<%=request.getContextPath()%>/chargeHander/addCharge.action",
		data : {"cost" : cost ,"carId" : carId ,"isCash":1},
		success : function(data) {
			if (data != '0') {
				alert("缴费成功！");
				var str="缴费成功！此次编号："+data;
				str+="<button onclick='invoice("+data+")'>开发票</button>";
			} else {
				alert("缴费失败！");
			}
		},
		error : function() {
			window.alert("未知错误!");
		}
	});
}

//开具发票
function invoice(chargeId){
	window.location.href="<%=request.getContextPath()%>/chargeHander/exportCharge.action?chargeId="+chargeId;
}

</script>
<title>停车缴费</title>
</head>
<body>
<input id="carId" name="carId" placeholder="请输入车牌">
<button onclick="payment()">立即缴费</button>
<div id="show"></div>
</body>
</html>