<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>收支明细</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<link href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap-select.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap-select.js"></script>
<!-- 日期 -->
</head>
<script>
<!-- 输入框1 -->
$(document).ready(function() {
	$('.bs-select').selectpicker({});
});

$(function(){
       //得到当前时间
	var date_now = new Date();
	//得到当前年份
	var year = date_now.getFullYear();
	//得到当前月份
	//注：
	//  1：js中获取Date中的month时，会比当前月份少一个月，所以这里需要先加一
	//  2: 判断当前月份是否小于10，如果小于，那么就在月份的前面加一个 '0' ， 如果大于，就显示当前月份
	var month = date_now.getMonth()+1 < 10 ? "0"+(date_now.getMonth()+1) : (date_now.getMonth()+1);
	//得到当前日子（多少号）
	var date = date_now.getDate() < 10 ? "0"+date_now.getDate() : date_now.getDate();
	//设置input标签的max属性
	$("#starTime").attr("max",year+"-"+month+"-"+date);
	$("#endTime").attr("max",year+"-"+month+"-"+date);
	
})

</script>
<body>
<!-- 日期 -->
<!--日期 END-->
	<!-- 输入框 -->
	<div class="col-lg-6" style="width: 80%;">
		<div class="input-group">
			<span class="input-group-btn">
				<button class="btn btn-default" type="button">起始日期</button>
			</span> 
			 <input type='date' class="form-control" id='starTime'/>
			<span class="input-group-btn">
				<button class="btn btn-default" type="button">截止日期</button>
			</span> 
			 <input type='date' class="form-control" id='endTime' />
			 <span class="input-group-btn">
				<button class="btn btn-default" type="button">车牌号</button>
			</span> <input type="text" class="form-control" id="carId"> 
			<span class="input-group-btn">
				<button class="btn btn-default" type="button" onclick="search()">搜索</button>
			</span>
			<span class="input-group-btn" style="float: right;">
				<button  type="button" onclick="count()" class="btn btn-info btn-small">统计报表</button>
			</span>
		</div>
		<!-- /input-group -->
	</div>
	<!-- 输入框END -->
	<table class="table table-hover">
		<caption>收支明细列表</caption>
		<thead>
			<tr>
				<th>编号</th>
				<th>车牌号</th>
				<th>费用</th>
				<th>类型</th>
				<th>操作人</th>
				<th>创建时间</th>
				<th>发票</th>
				<th>支付类型</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="whiteList">
			<c:forEach items="${chargeList}" var="chargeList">
				<tr>
					<td>${chargeList.chargeId}</td>
					<td>${chargeList.carId}</td>
					<td>${chargeList.chargeCost}</td>
					<td>${chargeList.parmName}</td>
					<td>${chargeList.adminAccount}</td>
					<td>${chargeList.chargeCdate}</td>
					<td>${chargeList.chargeInv eq 1?"已开发票":"未开发票"}</td>
					<td>${chargeList.chargeCash eq 1?"现金支付":"线上支付"}</td>
					<td><input type="button" value="开发票" onclick="invoice(${chargeList.chargeId})" class="btn btn-info btn-small"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<input type="button" value="上一页" onclick="previousPage()" class="btn btn-info btn-small">
	<a id="pageNum" value="${pageNum}">当前页码：${pageNum}</a>/
	<a id="pages" value="${pages}">总页码：${pages}</a>
	<input type="button" value="下一页" onclick="nextPage()" class="btn btn-info btn-small">
	<!-- 跳转页码输入校验 -->
	<input type="text" class="input-group-addon" id="goPages" style="width: 50px;background-color:#FFFFFF; " onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"    > 
	<input type="button" value="GO" onclick="Go()" class="btn btn-info btn-small">
</body>
<script type="text/javascript">
$(document).ready(function(){
	 
	 
});

//开具发票
function invoice(chargeId){
	window.location.href="<%=request.getContextPath()%>/chargeHander/exportCharge.action?chargeId="+chargeId;
}

function count(){
	window.location="<%=request.getContextPath()%>/chargeHander/statisticalChart.action";
}
//跳转
function Go(){
	var goPages=$("#goPages").val();
	if(goPages>$("#pages").attr("value")||goPages==""){
		alert("输入页码必须在总页码范围内")
		return;
	}
	$.ajax({
	    url: "<%=request.getContextPath()%>/chargeHander/turnPageChargeList.action",
		type:"POST",
		data:{"pageNums":goPages,"starTime":$("#starTime").val(),"endTime":$("#endTime").val(),"carId":$("#carId").val(),"adminId":0,"chargeId":0},
		dataType:"json",
		success : function(data){
			var str="";
			for(var i=1;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].chargeId+"</td>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].chargeCost+"</td>";
				str+="<td>"+data[i].parmName+"</td>";
				str+="<td>"+data[i].adminAccount+"</td>";
				str+="<td>"+data[i].chargeCdate+"</td>";  
				if(data[i].chargeInv==1){
					str+="<td>已开发票</td>";	
				}else{
					str+="<td>未开发票</td>";	
				}
				if(data[i].chargeCash==1){
					str+="<td>现金支付</td>";	
				}else{
					str+="<td>线上支付</td>";	
				}
				str+="<td><input type='button' value='打印发票' onclick='invoice("+data[i].chargeId+")' class='btn btn-info btn-small'></td>";
				str+="</tr>";
			}
			$("#whiteList").html(str);
			  $("#pageNum").text("当前页码:"+data[0].pageNum);
			    $("#pageNum").val(data[0].pageNum);
			    $("#pages").text("总页码:"+data[0].pages);
			    $("#pages").val(data[0].pages);
		}
});
}
//上一页
function previousPage(){
	var pageNum=$("#pageNum").attr("value");
	var pages=$("#pages").attr("value");
	var pageNums=--pageNum;
	if(pageNums==0){
		pageNums=1;
	}
	$.ajax({
	    url: "<%=request.getContextPath()%>/chargeHander/turnPageChargeList.action",
		type:"POST",
		data:{"pageNums":pageNums,"starTime":$("#starTime").val(),"endTime":$("#endTime").val(),"carId":$("#carId").val(),"adminId":0,"chargeId":0},
		dataType:"json",
		success : function(data){
			var str="";
			for(var i=1;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].chargeId+"</td>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].chargeCost+"</td>";
				str+="<td>"+data[i].parmName+"</td>";
				str+="<td>"+data[i].adminAccount+"</td>";
				str+="<td>"+data[i].chargeCdate+"</td>";  
				if(data[i].chargeInv==1){
					str+="<td>已开发票</td>";	
				}else{
					str+="<td>未开发票</td>";	
				}
				if(data[i].chargeCash==1){
					str+="<td>现金支付</td>";	
				}else{
					str+="<td>线上支付</td>";	
				}
				str+="<td><input type='button' value='打印发票' onclick='invoice("+data[i].chargeId+")' class='btn btn-info btn-small'></td>";
				str+="</tr>";
			}
			$("#whiteList").html(str);
			 $("#pageNum").text("当前页码:"+data[0].pageNum);
			    $("#pageNum").val(data[0].pageNum);
			    $("#pages").text("总页码:"+data[0].pages);
			    $("#pages").val(data[0].pages);
		}
});
	
}
//下一页
function nextPage(){
	var pageNum=$("#pageNum").attr("value");
	var pages=$("#pages").attr("value");
	var pageNums=++pageNum;
	if(pageNums>pages){
		pageNums=pages;
	}
	$.ajax({
	    url: "<%=request.getContextPath()%>/chargeHander/turnPageChargeList.action",
		type:"POST",
		data:{"pageNums":pageNums,"starTime":$("#starTime").val(),"endTime":$("#endTime").val(),"carId":$("#carId").val(),"adminId":0,"chargeId":0},
		dataType:"json",
		success : function(data){
			var str="";
			for(var i=1;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].chargeId+"</td>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].chargeCost+"</td>";
				str+="<td>"+data[i].parmName+"</td>";
				str+="<td>"+data[i].adminAccount+"</td>";
				str+="<td>"+data[i].chargeCdate+"</td>";  
				if(data[i].chargeInv==1){
					str+="<td>已开发票</td>";	
				}else{
					str+="<td>未开发票</td>";	
				}
				if(data[i].chargeCash==1){
					str+="<td>现金支付</td>";	
				}else{
					str+="<td>线上支付</td>";	
				}
				str+="<td><input type='button' value='打印发票' onclick='invoice("+data[i].chargeId+")' class='btn btn-info btn-small'></td>";
				str+="</tr>";
			}
			$("#whiteList").html(str);
			 $("#pageNum").text("当前页码:"+data[0].pageNum);
			 $("#pageNum").val(data[0].pageNum);
			 $("#pages").text("总页码:"+data[0].pages);
			 $("#pages").val(data[0].pages);
		}
});
	
}
//搜索
function search(){
	$.ajax({
	    url: "<%=request.getContextPath()%>/chargeHander/turnPageChargeList.action",
		type:"POST",
		data:{"pageNums":1,"starTime":$("#starTime").val(),"endTime":$("#endTime").val(),"carId":$("#carId").val(),"adminId":0,"chargeId":0},
		dataType:"json",
		success : function(data){
			var str="";
			for(var i=1;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].chargeId+"</td>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].chargeCost+"</td>";
				str+="<td>"+data[i].parmName+"</td>";
				str+="<td>"+data[i].adminAccount+"</td>";
				str+="<td>"+data[i].chargeCdate+"</td>";  
				if(data[i].chargeInv==1){
					str+="<td>已开发票</td>";	
				}else{
					str+="<td>未开发票</td>";	
				}
				if(data[i].chargeCash==1){
					str+="<td>现金支付</td>";	
				}else{
					str+="<td>线上支付</td>";	
				}
				str+="<td><input type='button' value='打印发票' onclick='invoice("+data[i].chargeId+")' class='btn btn-info btn-small'></td>";
				str+="</tr>";
			}
			$("#whiteList").html(str);
			 $("#pageNum").text("当前页码:"+data[0].pageNum);
			 $("#pageNum").val(data[0].pageNum);
			 $("#pages").text("总页码:"+data[0].pages);
			 $("#pages").val(data[0].pages);
		}
});
}
</script>
</html>