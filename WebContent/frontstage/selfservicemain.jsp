<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<!-- 自助缴费主页面 linping-->
<head>
<meta charset="UTF-8">
<title>自助服务</title>
<meta charset="utf-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/carstyle/css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="<%=request.getContextPath()%>/carstyle/css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="<%=request.getContextPath()%>/carstyle/css/style.css" type="text/css" media="all">
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/carstyle/js/cufon-yui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/carstyle/js/Copse_400.font.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/carstyle/js/jquery.nivo.slider.pack.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/carstyle/js/imagepreloader.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script type="text/javascript">
$(function () {
	//车辆登陆（前端暂存）
	$("#login").click(function(){
		if($("#carId").val()==null || $("#carId").val()==''){
			alert("输入车牌号为空！");
			return;
		}
		$("#curCar").text($("#carId").val());
		$("#show").html("");
	});
});

// 车辆信息
function carInfo(){
	$("#show").show();
	$("#trans").hide();
	
	if($("#carId").val()==null || $("#carId").val()==''){
		alert("输入车牌号为空！");
		return;
	}
	
	$.ajax({
		type : "post",
		url : "<%=request.getContextPath()%>/carHandler/carInfo.action",
			data : {"carId" : $("#carId").val()},
			success : function(data) {
				console.log(data);
				if (data.car != '' && data.car !=null) {
					var str="";
					str+="车牌号："+data.car.carId+"<br>";
					str+="车辆颜色："+data.car.carColor+"<br>";
					str+="车辆类型："+data.car.carTypeName+"<br>";
					//获取车主信息
					if(data.ower!=null &&data.ower.owerId!=0){
						str+="绑定车主："+data.ower.owerName+"("+data.ower.owerAccount+")<br>";
					}else{
						str+="绑定车主：未绑定<br>";
					}
					//获取停靠信息
					if(data.dock!=null){
						str+="入场时间："+data.dock.startTime+"<br>";
					}else{
						str+="入场时间：暂无入场信息<br>";
					}
					//获取套餐信息
					if(data.trans!=null){
						str+="所选套餐："+data.trans.PACK_NAME+"<br>";
						str+="配套车位："+data.trans.PARK_ID+"<br>";
						str+="过期时间："+data.trans.TRAN_ETIME+"<br>";
					}else{
						str+="套餐信息：未查询到办理套餐信息<br>";
					}
					
					$("#show").html(str);
				} else {
					alert("查无该车辆信息");
				}
			},
			error : function() {
				window.alert("未知错误!");
			}
		});
	
}

//自助缴费
function payment(){
	$("#show").show();
	$("#trans").hide();
	if($("#curCar").text()==null || $("#curCar").text()==''){
		alert("输入车牌号为空！");
		return;
	}
	
	$.ajax({
		type : "post",
		url : "<%=request.getContextPath()%>/chargeHander/payment.action",
		data : {"carId" : $("#curCar").text()},
		dataType : 'json',
		success : function(data) {
			console.log(data);
			if (data != '') {
				var str="";
				str+="车牌号："+data.carId+"<br>";
				str+="入场时间："+data.startTime+"<br>";
				str+="停车费："+data.cost+"元<br>";
				if(data.cost!=0){
					str+="<button onclick='pay("+data.cost+",&quot;"+data.carId+"&quot;)'>立即缴费</button>";
				}
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
		data : {"cost" : cost ,"carId" : carId ,"isCash":2,"adminId":1,"type":1},
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

//开具发票11
function invoice(chargeId){
	window.location.href="<%=request.getContextPath()%>/chargeHander/exportCharge.action?chargeId="+chargeId;
}


//*******************************************以下为月缴办理********************************************/
var transact;//接收套餐正在使用的套餐和退费金额
var owerStates;//
//车辆id查套餐
function CarIdSelectTransact(){
$("#trans").show();
$("#show").hide();
	
if($("#curCar").text()==null || $("#curCar").text()==''){
	alert("输入车牌号为空！");
	return;
}
	
var path="<%=request.getContextPath()%>";
var str ="";
	$.ajax({
		url:path+"/transact/CarIdSelectPack2.action",
		type:"POST", 
		data:{"carId":$("#carId").val()},
		dataType:"json",
		success:function(data){
			if(data.rstState==1){
			transact=data;//正在使用的套餐
			str+="<tr>";
				str+="<td>"+data.tran.PACK_NAME+"</td>";
				str+="<td>"+data.tran.PACK_TIME+"</td>";
				str+="<td>"+data.tran.PACK_COST+"</td>";
				str+="<td>"+data.tran.TRAN_STIME+"</td>";
				str+="<td>"+data.tran.TRAN_ETIME+"</td>";
				str+="<td>正在使用</td>";
				str+="<td>"+data.money+"</td>"; 
				str+="</tr>";
			 $("#packTbody").html(str); //回填列表
			}else{
				 $("#packState").html("没有正在使用的套餐"); //回填显示信息
			}
			$("#PyteState").empty();//局部刷新
			$("#PyteState").append("<option value='0' >请选择套餐类型...</option>");
	    	for (var i = 0; i < data.TypePack.length; i++) {//回填套餐类型
				  $("#PyteState").append("<option value='"+data.TypePack[i].PARM_VAL+"' >" + data.TypePack[i].PARM_NAME + "</option>");
		    } 
	    	if(data.owerState==1){
			 	$("#oweract").html(""+data.owerMoney.OWER_ACCOUNT+""); //回填显示信息
				$("#owerMon").html(""+data.owerMoney.OWER_BALANCE+""); //回填显示信息
				owerStates=1;
	    	}else{
	    		$("#oweract").html("该车牌没有绑定账户"); //回填显示信息
	    		 $("#part1").attr("disabled", true);//余额支付不可选
	    		$("#PayType").html("(没有绑定账户)"); //回填显示信息
	    		owerStates=2;
	    	}
		},
		error:function(){
		}
	})
}
function check_licensePlate() {
	
	console.info("进入到车牌校验");
	var licensePlate = $("#carId").val()
	var re = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
	if(licensePlate.search(re) == -1) {
		alert("您输入的车牌号不合格！")
	} else {
		CarIdSelectTransact();
	}
}
//打开页面加载改变事件判断
 $(document).ready(function(){
     //绑定下拉框change事件，当下来框改变时调用 SelectChange()方法
     $("#PyteState").change(function() { 
    	 SelectTypeChange(); 
     });
     $("#packId").change(function() { 
    	 packNameChange(); 
     });
      $("#carAccount").change(function() { 
    	 packNameChange(); 
     });
})
var vipcarPark = 0;
var packList;
function SelectTypeChange(){//根据类型回填所有套餐
var path="<%=request.getContextPath()%>";
	$.ajax({
		url:path+"/transact/SelectTypeChange2.action",
		type:"POST", 
		data:{"packType":$("#PyteState").val()},
		dataType:"json",
		success:function(data){
			packList=data.packList;
			 $("#packId").empty();
			$("#carPark").empty();//车位刷新
			 $("#packId").append("<option value='0' >请选择要办理的套餐...</option>");
    	for (var i = 0; i < data.packList.length; i++) {
			  $("#packId").append("<option value='"+data.packList[i].PACK_ID+"' >" + data.packList[i].PACK_NAME + "</option>");
			           }
    	if($("#PyteState").val()==2 ){//套餐类型为2=白名单
    		$("#carPark").empty();
			 $("#carPark").append("<option value='0' >请选择车位...</option>");
			 if(data.carLoc.length==0){
				/*  $("#newBtn").html("(没有车位不可办理)"); //回填显示信息 */
				vipcarPark=1;
				alert("没有车位不可办理")
			 }else{
    		for (var i = 0; i < data.carLoc.length; i++) {
  			  $("#carPark").append("<option value='"+data.carLoc[i].parkId+"' >VIP" + data.carLoc[i].parkId + "</option>");
  			           }
			 }
    	}else {//选择套餐类型为月缴套餐
    		vipcarPark=0;
    	}
		},
		error:function(){
			window.alert("操作出错");
		}
	})
}
//套餐改变回填套餐属性
var PackTranPyte;//办理套餐，新，续，改
var newPackAtt;//所办套餐
function packNameChange(){
 		var packId= $("#packId").val()
 		console.log("套餐ID="+packId)
 		var str ="";
 	for (var i = 0; i < packList.length; i++) {
 		console.log("套餐id==="+packList[i].PACK_ID)
 		if(packList[i].PACK_ID==packId){//=====选择办理的套餐属性
 			newPackAtt=packList[i];
 			str+="<tr>";
			str+="<td>套餐时长</td>";
			str+="<td>"+packList[i].PACK_TIME+"/个月("+packList[i].PACK_TIME*30+"天)</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>套餐费用</td>";
			str+="<td>"+packList[i].PACK_COST+"/元(人民币)</td>";
			str+="</tr>";
 		}
	} 
 		 $("#TransPack").html(str); //回填需要办理的套餐的详情
 		 if(transact==null){//新办
 			$("#packLabel").html("(新办套餐)"); //回填显示信息
 			PackTranPyte=1;
 			if(owerStates==1){//有账号
 				console.log("余额="+$("#owerMon").text())//余额
 			console.log("套餐费="+newPackAtt.PACK_COST)//
 				if($("#owerMon").text()<newPackAtt.PACK_COST){//判断余额是否不足
 					$("#part1").attr("disabled", true);//余额支付不可选
 		    		$("#PayType").html("(余额不足)"); //回填显示信息
 				}
 			}
 		 }else{
	 		if(packId==transact.tran.PACK_ID){
	 			if($("#PyteState").val()==2){//续费时清空车位
	 				$("#carPark").empty();
	 			}
	 			console.log(packId+"=="+transact.tran.PACK_ID)
				$("#packLabel").html("(续费)"); //回填显示信息
	 			PackTranPyte=2;
	 			if(owerStates==1){//有账号
	 				console.log("余额="+$("#owerMon").text())//余额
	 				console.log("套餐费="+newPackAtt.PACK_COST)//
	 	 				if($("#owerMon").text()<newPackAtt.PACK_COST){//判断余额是否不足
	 	 					$("#part1").attr("disabled", true);//余额支付不可选
	 	 		    		$("#PayType").html("(余额不足)"); //回填显示信息
	 	 				}
	 	 			}
	 		}else{
	 			$("#packLabel").html("(更改)"); //回填显示信息
	 			PackTranPyte=3;
	 			/* payAndRefund();//更改套餐判断支付，退款 方式 */
	 			//有账号
	 			if(owerStates==1){
	 	 			console.log("余额="+$("#owerMon").text())//余额
	 	 			console.log("套餐费="+newPackAtt.PACK_COST)//
	 	 			console.log("退费="+transact.money)//退费
	 	 				if($("#owerMon").text()+transact.money<newPackAtt.PACK_COST){//判断余额是否不足--支付
	 	 					$("#part1").attr("disabled", true);//余额支付不可选
	 	 					var mon = Number($("#owerMon").text())+Number(transact.money)
	 	 		    		$("#PayType").html("(【退费+余额="+mon+"】余额不足)"); //回填显示信息
	 	 				
	 	 				}
	 	 		}else{
	 	 			$("#part1").attr("disabled", true);//余额支付不可选
	 	 			$("#PayType").html("没绑定账号)"); //回填显示信息
	 	 			
	 	 		}
	 		}
 		 }
}


var path="<%=request.getContextPath()%>";
function tranButton(){
	if(vipcarPark==1){
		alert("没有车位不可办理VIP套餐")
		return;		
	}
	
	if(PackTranPyte==1){//新办套餐
		console.log("新办"+newPackAtt.PACK_NAME);
	}else if(PackTranPyte==2){//续费
		console.log("续费"+newPackAtt.PACK_NAME);
	}else if(PackTranPyte==3){//更改
		console.log("更改"+newPackAtt.PACK_NAME);
	}
	var payType = jQuery("input[name='part']:checked").val();
	var adminId =${sessionScope.loggingAdmin.adminId} 
	$.ajax({
		url:path+"/transact/confirmPay.action",
		type:"POST", 
		data:{"PackTranPyte":PackTranPyte,"payType":payType,"packId":$("#packId").val(),"carId":$("#carId").val(),"adminId":adminId,"carPark":$("#carPark").val()},
		dataType:"json",
		success:function(data){
			if(PackTranPyte==1){
				if(data.map.state==1){
				alert(""+data.map.prompt+"");

				}
			}
			if(PackTranPyte==2){
				if(data.map.state==1){
					alert(""+data.map.prompt+"");
				}
			}
			if(PackTranPyte==3){
				if(data.map.state==1){
					alert(""+data.map.prompt+"");
				}
			}
			var str="缴费成功！此次编号："+data.map.seq;
			str+="<button onclick='invoice("+data.map.seq+")'>开发票</button>";
			$("#trans").html(str);
		},
		error:function(){
		}
	})
}



//*******************************************以下为反向寻车********************************************/
//查询该车牌号所对应车辆的信息 
function queryCar(){
	if($("#curCar").text()==null || $("#curCar").text()==''){
		alert("输入车牌号为空！");
		return;
	}
	
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/carLocation/queryCarInfo.action",
		data:{"carId":$("#curCar").text()},
		dataType:"json",
		success:function(data){
			var xCoords=data[0].xCoord;//车位模型的x周坐标
			var yCoords=data[0].yCoord;//车位模型y轴坐标
			var twoId=data[0].twoId;//车位模型的id
			//开始导航			
			if(xCoords==null||yCoords==null){
				window.alert("未查询出您车辆的信息");
				return;
			}
			window.location.href = "<%=request.getContextPath()%>/frontstage/startnav.jsp?xCoords="+xCoords+"&yCoords="+yCoords+"&twoId="+twoId;
		}
	})
}

</script>
</head>
<body id="page1">
	<div class="body1">
		<div class="body2">
			<div class="main">
				<!-- header -->
				<header>
					<div class="wrapper">
						<h1>
							<a href="index.html" id="logo">First</a>
						</h1>
						<div class="right">
							<nav>
								<ul id="top_nav">
									<li><a href="#">当前车辆：<span id="curCar"></span></a></li>
								</ul>
								<form id="search" method="post">
									<div>
										<input id="login" type="button" class="submit" value=""> <input id="carId" class="input" placeholder="请输入车牌..">
									</div>
								</form>
							</nav>
						</div>
					</div>
					<nav id="menu">
						<ul>
							<li class="nav1"><a href="">主页</a></li>
							<li class="nav2"><a href="#" onclick="payment()">自助缴费</a></li>
							<li class="nav3"><a href="#" onclick="CarIdSelectTransact()">月缴办理</a></li>
							<li class="nav4"><a href="#" onclick="carInfo()">车辆信息</a></li>
							<li class="nav5"><a href="#" onclick="queryCar()">反向寻车</a></li>
						</ul>
					</nav>
				</header>
			</div>
		</div>
	</div>
	<div class="body3" style="height: 100%">
		<div id="show" style="display: none;" class="body4"></div>
		<div id="trans" style="display: none;" class="body4">
			<form id="packRefundForm">
			<h5>套餐情况：</h5>
	 		<table class="table table-striped table-hover" >
	 			<thead>
	 				<tr>
	 					<th>套餐名称</th>
	 					<th>套餐时长</th>
	 					<th>套餐费用</th>
	 					<th>开始时间</th>
	 					<th>结束时间</th>
	 					<th>套餐状态</th>
	 					<th>可退金额</th>
	 				</tr>
	 			</thead>
	 			<tbody id="packTbody"></tbody>
	 		</table>
	 		
	 		<label id="packState"  class="label label-primary"></label>
	 		<h5>套餐类型</h5>
	 		<div>
				<select name="PyteState" id="PyteState"></select>
	 		</div>
	 		<h5>办理套餐</h5>
	 		<div>
				<select name="packId" id="packId"></select>
				<label id="packLabel"  class="label label-primary"></label>
				<select name="carPark" id="carPark" value="0"></select>
	 		</div>
	 		
	 		<table class="table table-striped table-hover">
	 			<tr>
					<th>套餐详情</th>
					<th>时间价格</th>
				</tr>
	 			<tbody id="TransPack"></tbody>
			</table>
			
			<h5>绑定账户</h5>
			<table class="table table-striped table-hover">
	 				<tr>
						<th>账户：<label id="oweract"  class="label label-primary"></label></th>
						<th>余额：<label id="owerMon"  class="label label-primary"></label></th>
					</tr>
			 </table>
			 <div id="RefundId"></div>
			<div id="payId">
	 		<h5>结款方式：</h5>
	 		<input name="part" id="part1" type="radio" value="1" style="width:20px"/>账户余额<label id="PayType"  class="label label-primary"></label>
			<input name="part" id="part2" type="radio" value="2" style="width:20px"/>现金
			<input name="part" id="part3" type="radio" value="3" style="width:20px"/>第三方支付
			 </div>
	
	 		<div><input type="button" onclick="tranButton()" value="确认办理" id="newBtn" class="btn btn-primary"><input type="reset" value="重置" id="reBtn" class="btn btn-primary"></div>
	 		</form>
		</div>
	</div>
</body>
</html>