<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META   HTTP-EQUIV="Pragma"   CONTENT="no-cache">
<META   HTTP-EQUIV="Cache-Control"   CONTENT="no-cache">
<META   HTTP-EQUIV="Expires"   CONTENT="0">
<meta charset="UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/carstyle/js/jquery-1.4.2.js"></script>
<!---->
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.my-modal.1.1.js"></script>
<!--win风格-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.my-modal.1.1.winStyle.css" />
<!---->
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
			if (data != '' && data !=null) {
				var str="";
				str+="车牌号："+data.carId+"<br>";
				str+="入场时间："+data.startTime+"<br>";
				str+="停车费："+data.cost+"元<br>";
				if(data.cost!=0){
					str+="<button onclick='pay("+data.cost+",&quot;"+data.carId+"&quot;)'>立即缴费</button>";
				}
				
				$("#show").html(str);
			} else {
				alert("缴费信息载入失败！");
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
		data : {"cost" : cost ,"carId" : carId ,"isCash":1,"type":2},
		success : function(data) {
			if (data != '0') {
				alert("缴费成功！");
				var str="缴费成功！此次编号："+data;
				str+="<button onclick='invoice("+data+")'>开发票</button>";
				$("#show").html(str);
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
<button onclick="payment()">获取该车停车费</button>
<div id="show"></div>
<!-- -----------弹窗------------------>
<div class="m-modal" aria-hidden="true" data-backdrop="static" data-target="myModal">
			<div class="m-modal-dialog" style="width: 25%">
				<div class="m-top">
					<h4 class="m-modal-title">
						请尽快收费
					</h4>
					<span class="m-modal-close">&times;</span>
				</div>
				<div class="m-middle">
					<!--content-->
					<table>
					      <tr>
					      <td>车牌号：</td>
					      <td><input type="text" id="cCarId" readOnly="true"></td>
					      </tr>
					      <tr>
					      <td>类型：</td>
					      <td><input type="text" id="cCarTypes" readOnly="true"></td>
					      </tr>
					      <tr>
					        <td>费用：</td>
					      <td><input type="text" id="cCost" readOnly="true"></td>
					      </tr>
					</table>
					<p>
					</p>
					
				</div>
				<div class="m-bottom">
					<button class="m-btn-sure" style="float:right;">确定</button>
					<button class="m-btn-cancel" style="float:left;">取消</button>
				</div>
			</div>
</div>
<!-- ----------------------------->
</body>
<script>
	var m1 = new MyModal.modal(function() {
		backdrop: 'static';
	alert("确认收费！");
	//进行缴费
		});
</script>
<!------------------------------->
<script type="text/javascript">
var source;
if (!!window.EventSource) {
       source = new EventSource('<%=request.getContextPath()%>/sse/goIn.action'); 
       //出场
       source.addEventListener('paymoney', function(e) {
          var obj = eval('(' + e.data + ')');
          var t=obj.cCarTypes;
          var d=obj.cCarId;
          var ct=obj.cCost;
          var st=obj.oStarTime;
              $("#cCarTypes").val(t);
              $("#cCarId").val(d);
              $("#cCost").val(ct);
              m1.show();
              console.log("车辆出场");
       });
       source.addEventListener('open', function(e) {
            console.log("连接打开.");
       }, false);
       source.addEventListener('error', function(e) {
            if (e.readyState == EventSource.CLOSED) {
               console.log("连接关闭");
            } else {
                console.log(e.readyState);    
            }
       }, false);
    } else {
            console.log("没有sse");
    }
</script>
</html>