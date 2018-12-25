<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>缴费渠道统计页面</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/countstyle/css/basic.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/countstyle/css/visualize.css" type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/countstyle/css/visualize-dark.css" type="text/css" rel="stylesheet" />
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
<style>
#week{
background-color: blue;
}
#mouth{
margin-left: 100px;
background-color: blue;
}
#threemouth{
margin-left: 100px;
background-color: blue;
}
</style>
<script>
$().ready(function(){
	$("#mouths").hide();
	$("#threemouths").hide();
});
function weekt(){
	$("#weeks").show();
	$("#mouths").hide();
	$("#threemouths").hide();
};
function moutht(){
	$("#weeks").hide();
	$("#mouths").show();
	$("#threemouths").hide();
};
function halfyear(){
	$("#weeks").hide();
	$("#mouths").hide();
	$("#threemouths").show();
};
function report(){
	var type=$("#myreport").val();
	if(type==1){
		window.location="<%=request.getContextPath()%>/aisTran/getCountExcel.action";
	}else if(type==2){
		window.location="<%=request.getContextPath()%>/aisTran/getCountmouthExcel.action";
	}else if(type==3){
		window.location="<%=request.getContextPath()%>/aisTran/getsixMouthExcel.action";
	}else{
		alert("请选择报表类型");
	}
}
</script>
</head>
<body>
<div>
	<input type="button" value="周统计" id="week" onClick="weekt()"><input type="button" value="月统计" id="mouth" onClick="moutht()" ><input type="button" value="季度统计" id="threemouth" onClick="halfyear()">
	<input type="button" value="统计报表">
	<select id="myreport" onchange="report()">
	<option value="">请选择</option>
	<option value="1">周统计报表</option>
	<option value="2">月统计报表</option>
	<option value="3">近半年统计报表</option>
	</select>
</div>
<div id="weeks">
<table style="display:none">
	<caption>缴费渠道统计(本周)</caption>
	<thead>
		<tr>
			<td></td>
			<th scope="col">星期一</th>
			<th scope="col">星期二</th>
			<th scope="col">星期三</th>
			<th scope="col">星期四</th>
			<th scope="col">星期五</th>
			<th scope="col">星期六</th>
			<th scope="col">星期日</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th scope="row">临时用户</th>
			<c:forEach items="${list}" var="payList">
			<td>${payList.VALUE}</td>
			</c:forEach>
		</tr>
		<tr>
			<th scope="row">用户充值</th>
			<c:forEach items="${list2}" var="list">
				<td>${list.VALUE}</td>
			</c:forEach>
		</tr>
		<tr>
			<th scope="row">月缴用户</th>
			<c:forEach items="${list3}" var="list3">
				<td>${list3.VALUE}</td>
			</c:forEach>
		</tr>
	</tbody>
</table>
</div>
<div id="mouths">
<table style="display:none">
	<caption>缴费渠道统计(本月)</caption>
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
			<th scope="row">临时用户</th>
			<c:forEach items="${list4}" var="payList">
			<td>${payList.COUNT}</td>
			</c:forEach>
		</tr>
		<tr>
			<th scope="row">用户充值</th>
			<c:forEach items="${list5}" var="list">
				<td>${list.COUNT}</td>
			</c:forEach>
		</tr>
		<tr>
			<th scope="row">月缴用户</th>
			<c:forEach items="${list6}" var="list3">
				<td>${list3.COUNT}</td>
			</c:forEach>
		</tr>
	</tbody>
</table>
</div>
<div id="threemouths">
<table style="display:none">
	<caption>缴费渠道统计(近半年)</caption>
	<thead>
		<tr>
			<td></td>
			<th scope="col">${mouth-5}月</th>
			<th scope="col">${mouth-4}月</th>
			<th scope="col">${mouth-3}月</th>
			<th scope="col">${mouth-2}月</th>
			<th scope="col">${mouth-1}月</th>
			<th scope="col">${mouth}月</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<th scope="row">临时用户</th>
			<c:forEach items="${list7}" var="payList">
			<td>${payList.COUNT}</td>
			</c:forEach>
		</tr>
		<tr>
			<th scope="row">用户充值</th>
			<c:forEach items="${list8}" var="list">
				<td>${list.COUNT}</td>
			</c:forEach>
		</tr>
		<tr>
			<th scope="row">月缴用户</th>
			<c:forEach items="${list9}" var="list3">
				<td>${list3.COUNT}</td>
			</c:forEach>
		</tr>
	</tbody>
</table>
</div>
</body>
</html>