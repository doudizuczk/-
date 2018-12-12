<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>菜单页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
 	<form>
 		<table>
 			<tbody>
 				<tr>
 					<th>菜单编号</th>
 					<th>菜单名称</th>
 					<th>菜单链接</th>
 					<th>父级菜单</th>
 					<th>操作</th>
 				</tr>
 				<c:forEach items="${menuList}" var="menu">
 				<tr>
 					<td>${menu.menuId}</td>
 					<td>${menu.menuName}</td>
 					<td>${menu.menuUrl}</td>
 					<c:forEach items="${menuList}" var="menutwo">
 						<c:choose>
 							<c:when test="${menu.menuPid==menutwo.menuId}">	
 								<td>${menutwo.menuName}</td>	
 							</c:when>
 						<c:otherwise>
 							<c:if test="${menu.menuPid==0}">
 								<td>1</td>
 							</c:if>
 						</c:otherwise>
 						</c:choose>	
 					</c:forEach>
 				</tr>
 				</c:forEach>
 			</tbody>
 		</table>
 	</form>
</body>
</html>