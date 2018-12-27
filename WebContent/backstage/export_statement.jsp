<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<META   HTTP-EQUIV="Pragma"   CONTENT="no-cache">
<META   HTTP-EQUIV="Cache-Control"   CONTENT="no-cache">
<META   HTTP-EQUIV="Expires"   CONTENT="0">
<meta charset="UTF-8">
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<!---->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.my-modal.1.1.js"></script>
<link href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap-select.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap-select.js"></script>
<!--win风格-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery.my-modal.1.1.winStyle.css" />
<!---->
<script type="text/javascript">
function exportOrder(){
	if($("#createTime").val()==null || $("#createTime").val()=="" || $("#shift").val()==0){
		alert("请选择完整的日期和班次！"+$("#createTime").val()+","+$("#shift").val());
		return;
	}
	window.location.href="<%=request.getContextPath()%>/orderHandler/exportOrderExcel.action?shift="+$("#shift").val()+"&createTime="+$("#createTime").val();
}

function exportOrder2(shift,createTime){
	window.location.href="<%=request.getContextPath()%>/orderHandler/exportOrderExcel.action?shift="+shift+"&createTime="+createTime;
}

</script>
<title>Insert title here</title>
</head>
<body>
<div>
<input class="btn btn-default" type="button" value="导出结算单" id="btn2" class="btn btn-default">
<input class="btn btn-default" type="button" value="请选择日期" id="btn2" class="btn btn-default">
<input type="date" id="createTime" name="createTime" class="btn btn-default" style="height: 35px">
<input class="btn btn-default" type="button" value="请选择班次：" id="btn2" class="btn btn-default">
<select id="shift" name="shift" class="btn btn-default">
			<option value="0">请选择</option>
			<option value="1">早班 08:00-16:00</option>
			<option value="2">中班 16:00-24:00</option>
			<option value="3">晚班 00:00-08:00</option>
		</select>
		<button onclick="exportOrder()" class="btn btn-primary">导出</button>
</div>	

<h2>历史结算单</h2>
	<form action="<%=request.getContextPath()%>/orderHandler/orderList.action" method="post" >
	    <button class="btn btn-default" type="button">结算日期:</button>
	    <input type="date" id="createTime2" name="createTime" value="${param.createTime}" class="btn btn-default" style="height:35px"/>
	    <button class="btn btn-default" type="button">班次:</button>	
	    <select id="shift2" name="shift" class="btn btn-default">
	    	<option value="0">请选择</option>
			<option value="1">早班 08:00-16:00</option>
			<option value="2">中班 16:00-24:00</option>
			<option value="3">晚班 00:00-08:00</option>
	    </select>
  	</form>
 	<form>
 		<table class="table table-striped table-hover" id="datatable">
 			<thead>
 				<tr>
 					<th>结算单编号</th>
 					<th>总金额</th>
 					<th>现金</th>
 					<th>交易数量</th>
 					<th>发票金额</th>
 					<th>发票数量</th>
 					<th>班次</th>
 					<th>创建时间</th>
 					<th>操作</th>
 				</tr>
 			</thead>
 			<tbody id="tbody">
 				<c:forEach items="${requestScope.pageInfo.dates.orderList}" var="order">
 				<tr>
 					<td>${order.orderId}</td>
 					<td>${order.amount}</td>
 					<td>${order.cash}</td>
 					<td>${order.count}</td>
 					<td>${order.invAmount}</td>
 					<td>${order.invCount}</td>
 					<c:if test="${order.shift==1}"><td>早班</td></c:if>
 					<c:if test="${order.shift==2}"><td>中班</td></c:if>
 					<c:if test="${order.shift==3}"><td>晚班</td></c:if>
 					<td>${order.createTime}</td>
 				    <td><input type="button" value="导出" onclick="exportOrder2(${order.shift},${order.createTime})" class="btn btn-primary btn-sm"></td>
 				</tr>
 				</c:forEach>
 			</tbody>
 		</table>
 	</form>
	<div style="width: 80%">
         <div style="float: right;"> 	
	         <button id="first" name="first"  class="btn btn-primary btn-sm">首页</button>
	         <button id="prev" name="prev"  class="btn btn-primary btn-sm">上一页</button>
	         <button id="next" name="next"  class="btn btn-primary btn-sm">下一页</button>
	         <button id="end" name="end"  class="btn btn-primary btn-sm">末页</button>
	         <input id="goTxt" name="goTxt" type="text" class="btn btn-default btn-sm" style="width: 15%;background-color:#FFFFFF;"/>
	         <button id="go" name="go"  class="btn btn-primary btn-sm">跳转</button>
	     	 共<span>${requestScope.pageInfo.totalNum}</span>条&nbsp;当前页数：[<span id="curPage">${requestScope.pageInfo.curPage}</span>/<span id="tolPage">${requestScope.pageInfo.totalPage}</span>]
    	 </div>
     </div>






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
    
    
    
var num=parseInt($("#curPage").text());
var total=parseInt($("#tolPage").text());

$("#first").click(function(){
	num=1;
	$("#curPage").text("");
	window.location.href="<%=request.getContextPath()%>/orderHandler/orderList.action?createTime="+$("#createTime").val()+"&shift="+$("#shift").val()+"&curPage="+num;
});

$("#prev").click(function(){
	if(num>1){
		num=num-1;
		$("#curPage").text("");
		window.location.href="<%=request.getContextPath()%>/orderHandler/orderList.action?createTime="+$("#createTime").val()+"&shift="+$("#shift").val()+"&curPage="+num;
	}
});

$("#next").click(function(){
	if(num<total){
		num=num+1;
		$("#curPage").text("");
		window.location.href="<%=request.getContextPath()%>/orderHandler/orderList.action?createTime="+$("#createTime").val()+"&shift="+$("#shift").val()+"&curPage="+num;
	}
});

$("#end").click(function(){
	num=total;
	$("#curPage").text("");
	window.location.href="<%=request.getContextPath()%>/orderHandler/orderList.action?createTime="+$("#createTime").val()+"&shift="+$("#shift").val()+"&curPage="+num;
});

$("#go").click(function(){
	var goNum=parseInt($("#goTxt").val());
	if(goNum>=1&&goNum<=total){
		num=goNum;
		$("#curPage").text("");
		window.location.href="<%=request.getContextPath()%>/orderHandler/orderList.action?createTime="+$("#createTime").val()+"&shift="+$("#shift").val()+"&curPage="+num;
	}else{
		alert("错误页码");
	}
}); 
</script>
</html>