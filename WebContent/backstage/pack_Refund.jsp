<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>套餐退费页</title>
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
/*    $("#carId").blur(function() { 
  	 alert(222222222)
   }); */
});
var transact;//接收套餐MAp和退费金额
//车辆id查套餐
function CarIdSelectTransact(){
var path="<%=request.getContextPath()%>";
var str ="";
	$.ajax({
		url:path+"/transact/CarIdSelectTransact.action",
		type:"POST", 
		data:{"carId":$("#carId").val()},
		dataType:"json",
		success:function(data){
			transact=data;
			console.log("id查询套餐成功！"+data.tran.TRAN_STATE)
			str+="<tr>";
			str+="<td>套餐时长:</td>";
			str+="<td>"+data.tran.PACK_TIME+"/个月("+data.tran.PACK_TIME*30+"天)</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>套餐费用:</td>";
			str+="<td>"+data.tran.PACK_COST+"/元(人民币)</td>";
			str+="</tr>";

			str+="<td>开始时间</td>";
			str+="<td>"+data.tran.TRAN_STIME+"</td>";
			str+="</tr>";
			str+="<tr>";
			str+="<td>结束时间</td>";
			str+="<td>"+data.tran.TRAN_ETIME+"</td>";
			str+="</tr>";
			
			if(data.tran.TRAN_STATE=="1"){
				console.log("data.tran.TRAN_STATE=="+data.tran.TRAN_STATE)
				str+="<tr>";
				str+="<td>可退金额</td>";
				str+="<td>"+data.money+"/元(人民币)</td>";
				str+="</tr>";
			}else{
				str+="<tr>";
				str+="<td>可退金额</td>";
				str+="<td>套餐已经过期，暂无可退金额</td>";
				str+="</tr>";
			}
			
			 $("#packTbody").html(str); //回填列表
		},
		error:function(){
			window.alert("该车没有办理任何套餐");
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

var path="<%=request.getContextPath()%>";
function yesbut(){
	if(transact.tran.TRAN_STATE!="1"){
		alert("不可退费!");
		return;
	}
	var path="<%=request.getContextPath()%>";
	var money = transact.money
	alert(money);
		$.ajax({
			type:"post",
			url:path+"/transact/RefunndTransact.action",
			data:{"carId":$("#carId").val(),"money":money},
			dataType:"json",
			success:function(data){
				if(data.refundState==1){
				window.alert("余额退款成功")
				check_licensePlate();
				}
				if(data.refundState==2){
					window.alert("该车没有绑定账户,请现金退款"+data.money+"/元(人民币)")
				}
			},
			error:function(){
				window.alert("查询出错");
			}
		})
}
//打开页面加载改变事件判断
 $(document).ready(function(){
     //绑定下拉框change事件，当下来框改变时调用 SelectChange()方法
     $("#packType").change(function() { 
    	 SelectTypeChange(); 
     });
     $("#packId").change(function() { 
    	 packNameChange(); 
     });
      $("#carAccount").change(function() { 
    	 packNameChange(); 
     });
})
</script>
<style>
.error{
	color:red;
}
</style>
</head>
<body>
<h3>套餐退费办理页</h3>
	<form id="packRefundForm" >
		<table class="table table-striped table-hover">
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
				<td>输入退费车牌号：</td>
					<td>
				<input type="text" name="carId" id="carId" placeholder="请输入要退费的车牌号..."> 
					</td>
					<td> 
		 	<input type="button" value="查询" id="newBtn" onclick="check_licensePlate()" class="btn btn-primary">
					 </td>
				</tr>
			<tbody id="packTbody">
			</tbody>
		 </table>
		 <div>
		 	<input type="button" onclick="yesbut()" value="确认办理" id="newBtn" class="btn btn-primary"><input type="reset" value="重置" id="reBtn" class="btn btn-primary">
		 </div>
	</form>

</body>
</html>