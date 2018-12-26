<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="css/basic.css" type="text/css" rel="stylesheet" />

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

</head>
<body>
<div id="week" style="margin-left:auto; margin-right:auto">
<table style="display:none">
	<caption>车位统计</caption>
	<thead>
		<tr>
			<td></td>
			<th scope="col">A区</th>
			<th scope="col">B区</th>
			<th scope="col">C区</th>
		</tr>
	</thead>
	<tbody>
		<tr>
		  <th scope="row">该区域总车位</th>
          <c:forEach items="${listAll}" var="wlist">
			<td>${wlist}</td>
          </c:forEach>
		</tr>
		<tr>
		  <th scope="row">该区域空闲车位</th>
          <c:forEach items="${listFree}" var="wlist">
			<td>${wlist}</td>
          </c:forEach>
		</tr>
		<tr>
		  <th scope="row">该区域已用车位</th>
          <c:forEach items="${listUsed}" var="wlist">
			<td>${wlist}</td>
          </c:forEach>
		</tr>
	</tbody>
</table>	
</div>

</body>
</html>