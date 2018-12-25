<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>反向寻车</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<div>
		<table class="table table-hover">
			<tbody>
				<tr>
					<td>车牌号</td>
					<td>${param.carId}</td>
				</tr>
				<tr>
					<td>户主</td>
					<td>${param.ower}</td>
				</tr>
				<tr>
					<td>车位ID</td>
					<td>${param.carLocationId}</td>
				</tr>
				<tr>
					<td>所属区域</td>
					<td>${param.area}</td>
				</tr>
				<tr>
					<td>入场时间</td>
					<td>${param.inTime}</td>
				</tr>
				<tr>
					<td>图片详情</td>
					<td>
					<c:if test="${empty param.picture}">
						<input type="file">
					</c:if>
					<c:if test="${not empty param.picture}">
						
					</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>

</html>