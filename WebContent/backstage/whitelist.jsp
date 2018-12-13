<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>白名单管理</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
你好
       <table>
          <tbody>
 				<tr>
 					<th>车牌号</th>
 					<th>用户</th>
 					<th>联系电话</th>
 					<th>车位区</th>
 					<th>车位编号</th>
 				</tr>
 					<c:forEach items="${whiteList}" var="wlist">
 				<tr>
 					<td>${wlist.carId}</td>
 					<td>${wlist.owerName}</td>
 					<td>${wlist.owerPhone}</td>
 					<td>${wlist.parkZone}</td>
 					<td>${wlist.parkId}</td> 
 				</tr>
 				</c:forEach>
 			</tbody>
 		</table>
</body>
</html>