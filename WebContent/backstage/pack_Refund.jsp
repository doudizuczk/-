<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<META   HTTP-EQUIV="Pragma"   CONTENT="no-cache">
<META   HTTP-EQUIV="Cache-Control"   CONTENT="no-cache">
<META   HTTP-EQUIV="Expires"   CONTENT="0">
<title>套餐退费页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<!---->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.my-modal.1.1.js"></script>
<!--win风格-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.my-modal.1.1.winStyle.css" />
<!---->
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
			if(data.rstState==1){
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
			}else{
			window.alert("该车没有办理任何套餐");
			}
			
			 $("#packTbody").html(str); //回填列表
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

var path="<%=request.getContextPath()%>";
function yesbut(){
	if(transact.rstState!="1"){
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