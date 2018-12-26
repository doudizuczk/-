<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>套餐办理</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script>
$().ready(function(){
	$("#packRefundForm").validate({
   	 rules: {
   		carId: {
   	        required: true,
   	      },
   	    },
   	  messages: {
   		carId: {
   	        required: "请先输入车牌号",
   	      },
   	     },
   	  submitHandler: function(form) { CarIdSelectTransact(); }
   })	
});
var transact;//接收套餐正在使用的套餐和退费金额
var owerStates;//
//车辆id查套餐
function CarIdSelectTransact(){
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
			 	$("#oweract").html("账户："+data.owerMoney.OWER_ACCOUNT+""); //回填显示信息
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
			console.log("成功！"+packList)
			 $("#packId").empty();
			 $("#packId").append("<option value='0' >请选择要办理的套餐...</option>");
    	for (var i = 0; i < data.packList.length; i++) {
			  $("#packId").append("<option value='"+data.packList[i].PACK_ID+"' >" + data.packList[i].PACK_NAME + "</option>");
			           }
    	if($("#PyteState").val()==2){//套餐类型为2=白名单
    		$("#carPark").empty();
			 $("#carPark").append("<option value='0' >请选择车位...</option>");
    		for (var i = 0; i < data.carLoc.length; i++) {
  			  $("#carPark").append("<option value='"+data.carLoc[i].parkId+"' >VIP" + data.carLoc[i].parkId + "</option>");
  			           }
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
	 	 		    		$("#PayType").html("(【退费+余额="+$("#owerMon").text()+transact.money+"】余额不足)"); //回填显示信息
	 	 				
	 	 				}
	 	 		}else{
	 	 			$("#part1").attr("disabled", true);//余额支付不可选
	 	 			$("#PayType").html("没绑定账号)"); //回填显示信息
	 	 			
	 	 		}
	 		}
 		 }
}
	//更改套餐判断支付，退款 方式
<%-- var path="<%=request.getContextPath()%>";
	function payAndRefund(){
		$.ajax({
			url:path+"/transact/payAndRefund.action",
			type:"POST", 
			data:{"packId":$("#packId").val(),"carId":$("#carId").val()},
			dataType:"json",
			success:function(data){
				alert(data);
				if(Number(data)>0){//退费
					$("#payId").hide();
					var str="";
					str+="<h3>退费方式：</h3>"
					str+="<input name=part  type=radio value=4 style=width:20px/>现金退款 ";
					str+="<input name=part  type=radio value=5 style=width:20px/>余额退款 ";
					
					$("#RefundId").html(str);
					
				}else if(Number(data)<0){//交钱
					
					
				}else{
					
				}
				
			},
			error:function(){
				window.alert("操作出错");
			}
		})
	} --%>
	
	
var path="<%=request.getContextPath()%>";
function tranButton(){
	 
	if(PackTranPyte==1){//新办套餐
		console.log("新办"+newPackAtt.PACK_NAME);
	}else if(PackTranPyte==2){//续费
		console.log("续费"+newPackAtt.PACK_NAME);
	}else if(PackTranPyte==3){//更改
		console.log("更改"+newPackAtt.PACK_NAME);
	}
	console.log(PackTranPyte+"办理类型======套餐id"+$("#packId").val())//套餐类型
	console.log(jQuery("input[name='part']:checked").val())//支付方式
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
				location.reload()   //刷新页面
				}
			}
			if(PackTranPyte==2){
				if(data.map.state==1){
					alert(""+data.map.prompt+"");
					location.reload()   //刷新页面
				}
			}
			if(PackTranPyte==3){
				if(data.map.state==1){
					alert(""+data.map.prompt+"");
					location.reload()   //刷新页面
				}
			}
		},
		error:function(){
		}
	})
}
</script>
<style>
.error{
	color:red;
}
</style>
</head>
<body>
<h3>套餐办理页2</h3>
	 	<form id="packRefundForm">
	 	<h5>输入套餐办理车牌号：</h5>
				<input type="text" name="carId" id="carId" placeholder="请输入车牌号..."> 
		 	<input type="button" value="查询" id="newBtn" onclick="check_licensePlate()" class="btn btn-primary">
		 	<h5>套餐情况：</h5>
 		<table class="table table-striped table-hover" >
 			<thand >
 				<tr>
 					<th>套餐名称</th>
 					<th>套餐时长</th>
 					<th>套餐费用</th>
 					<th>开始时间</th>
 					<th>结束时间</th>
 					<th>套餐状态</th>
 					<th>可退金额</th>
 				</tr>
 			</thand>
 			<tbody id="packTbody">
 				
 			</tbody>
 		</table>
 		<label id="packState"  class="label label-primary"></label>
 		<h5>套餐类型</h5>
 		<div>
			<select name="PyteState" id="PyteState">
			</select>
			
 		</div>
 		<h5>办理套餐</h5>
 		<div>
			<select name="packId" id="packId">
				
			</select>
			<label id="packLabel"  class="label label-primary"></label>
			
			<select name="carPark" id="carPark" value="0">
			</select>
 		</div>
 		
 		<table class="table table-striped table-hover">
 				<tr>
					<th>套餐详情</th>
					<th>时间价格</th>
				</tr>
 			<tbody id="TransPack">
			</tbody>
		 </table>
		 <h5>绑定账户</h5>
		<table class="table table-striped table-hover">
 				<tr>
					<th>账户：<label id="oweract"  class="label label-primary"></label></th>
					<th>余额：<label id="owerMon"  class="label label-primary"></label></th>
				</tr>
 			<!-- <tbody id="owerMoney">
			</tbody> -->
		 </table>
		 <div id="RefundId">
		 	
		 </div>
		 <div id="payId">
 		<h5>支付方式：</h5>
 		<input name="part" id="part1" type="radio" value="1" style="width:20px"/>账户余额<label id="PayType"  class="label label-primary"></label>
		<input name="part" id="part2" type="radio" value="2" style="width:20px"/>现金
		<input name="part" id="part3" type="radio" value="3" style="width:20px"/>第三方支付
		 </div>

 		<div>
		 	<input type="button" onclick="tranButton()" value="确认办理" id="newBtn" class="btn btn-primary"><input type="reset" value="重置" id="reBtn" class="btn btn-primary">
		 </div>
 	</form>

</body>
</html>