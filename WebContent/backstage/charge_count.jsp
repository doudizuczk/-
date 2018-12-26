<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="<%=request.getContextPath()%>/countstyle/css/basic.css" type="text/css" rel="stylesheet" />

<link href="<%=request.getContextPath()%>/countstyle/css/visualize.css" type="text/css" rel="stylesheet"/>

<link href="<%=request.getContextPath()%>/countstyle/css/visualize-dark.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/countstyle/js/jquery-1.4.2.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/countstyle/js/excanvas.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/countstyle/js/visualize.jQuery.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/countstyle/js/example-filtering.js"></script>

<style type="text/css">

/*some demo styles*/

body { font-size: 62.5%; }

.enhanced h2, .enhanced pre { margin: 3em 20px .5em; }

.enhanced pre { width: 50%; overflow: auto; font-size: 1.4em; margin-top: 0; background: #444; padding: 15px; color: #fff; }

</style>

</head>
<script>
$(document).ready(function(){
	$('#week').show();
	$('#mouth').hide();
	$('#quarter').hide();
});
function weekShow(){
	$('#week').show();
	$('#mouth').hide();
	$('#quarter').hide();
}
function mouthShow(){
	$('#week').hide();
	$('#mouth').show();
	$('#quarter').hide();
}
function quarterShow(){
	$('#week').hide();
	$('#mouth').hide();
	$('#quarter').show();
}
</script>
<body>
<input type="button" id="" value="周统计" onclick="weekShow()"/>
<input type="button" id="" value="月统计" onclick="mouthShow()"/>
<input type="button" id="" value="近半年统计 " onclick="quarterShow()"/>
<select id="excel">
<option value=1>周统计</option>
<option value=2>月统计</option>
<option value=3>近半年统计</option>
</select><input type="button" value="导出EXCEL" onclick="getOutExcel()">
<div id="week" style="margin-left:auto; margin-right:auto">
<table style="display:none">
	<caption>周缴费统计</caption>
	<thead>
		<tr>
			<td></td>
			<th scope="col">周一</th>
			<th scope="col">周二</th>
			<th scope="col">周三</th>
			<th scope="col">周四</th>
			<th scope="col">周五</th>
			<th scope="col">周六</th>
			<th scope="col">周日</th>
		</tr>
	</thead>
	<tbody>
				<tr>
				  <th scope="row">周总统计</th>
		          <c:forEach items="${weekList}" var="wlist">
					<td>${wlist.VALUE}</td>
		          </c:forEach>
				</tr>
				<tr>
				  <th scope="row">自助缴费</th>
		          <c:forEach items="${selfHelpWeekList}" var="selfHelpWeekList">
					<td>${selfHelpWeekList.VALUE}</td>
		          </c:forEach>
				</tr>
				<tr>
				  <th scope="row">人工缴费</th>
		          <c:forEach items="${laborWeekList}" var="laborWeekList">
					<td>${laborWeekList.VALUE}</td>
		          </c:forEach>
				</tr>
				<tr>
				  <th scope="row">月缴充值</th>
		          <c:forEach items="${rechargeWeekList}" var="rechargeWeekList">
					<td>${rechargeWeekList.VALUE}</td>
		          </c:forEach>
				</tr>
	</tbody>
</table>	
</div>
<div id="mouth">
<table style="display:none">
	<caption>本月缴费统计</caption>
	<thead>
		<tr>
			<td></td>
			<th scope="col">第一周</th>
			<th scope="col">第二周</th>
			<th scope="col">第三周</th>
			<th scope="col">第四周</th>
			<th scope="col">第五周</th>
		</tr>
	   </thead>
	   <tbody>
				<tr>
				  <th scope="row">月总统计</th>
		          <c:forEach items="${mouthList}" var="mouthList">
					<td>${mouthList.VALUE}</td>
		          </c:forEach>
				</tr>
				<tr>
				  <th scope="row">自助缴费</th>
		          <c:forEach items="${selfHelpmouthList}" var="selfHelpmouthList">
					<td>${selfHelpmouthList.VALUE}</td>
		          </c:forEach>
				</tr>
				<tr>
				  <th scope="row">人工缴费</th>
		          <c:forEach items="${labormouthList}" var="labormouthList">
					<td>${labormouthList.VALUE}</td>
		          </c:forEach>
				</tr>
				<tr>
				  <th scope="row">月缴充值</th>
		          <c:forEach items="${rechargemouthList}" var="rechargemouthList">
					<td>${rechargemouthList.VALUE}</td>
		          </c:forEach>
				</tr>
	</tbody>
</table>	
</div>
<div id="quarter">
<table style="display:none">
	<caption>近半年缴费统计</caption>
	<thead>
		<tr>
			<td></td>
			<th scope="col">${halfyearmouths-5}月</th>
			<th scope="col">${halfyearmouths-4}月</th>
			<th scope="col">${halfyearmouths-3}月</th>
			<th scope="col">${halfyearmouths-2}月</th>
			<th scope="col">${halfyearmouths-1}月</th>
			<th scope="col">${halfyearmouths}月</th>
		</tr>
	   </thead>
	   <tbody>
				<tr>
				  <th scope="row">季度总统计</th>
		          <c:forEach items="${halfyearList}" var="halfyearList">
					<td>${halfyearList.VALUE}</td>
		          </c:forEach>
				</tr>
				<tr>
				  <th scope="row">自助缴费</th>
		          <c:forEach items="${selfHelpHalfyearList}" var="selfHelpHalfyearList">
					<td>${selfHelpHalfyearList.VALUE}</td>
		          </c:forEach>
				</tr>
				<tr>
				  <th scope="row">人工缴费</th>
		          <c:forEach items="${laborHalfyearList}" var="laborHalfyearList">
					<td>${laborHalfyearList.VALUE}</td>
		          </c:forEach>
				</tr>
				<tr>
				  <th scope="row">月缴充值</th>
		          <c:forEach items="${rechargeHalfyearList}" var="rechargeHalfyearList">
					<td>${rechargeHalfyearList.VALUE}</td>
		          </c:forEach>
				</tr>
	</tbody>
</table>	
</div>	
</body>
<script>
function getOutExcel(){
	if($("#excel").val()==1){
		alert("确认导出周统计");
		document.location.href="${pageContext.request.contextPath}/chargeHander/getWeekCountExcel.action";
	
	}else if($("#excel").val()==2){
		alert("确认导出月统计");
		document.location.href="${pageContext.request.contextPath}/chargeHander/getMouthCountExcel.action";
	
	}else{
		alert("确认导出近半年统计");
		document.location.href="${pageContext.request.contextPath}/chargeHander/getHalfyearCountExcel.action";
	}
}
</script>
</html>