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
</head>
<body>
<div>
	<input type="button" value="周统计" id="week" ><input type="button" value="月统计" id="mouth" ><input type="button" value="季度统计" id="threemouth" >
</div>
<table style="display:none">
	<caption>缴费渠道统计</caption>
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
</body>
</html>