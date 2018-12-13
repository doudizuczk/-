<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增菜单菜单页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<form>
		<table>
			<tbody>
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
					<td>
					<select name="menuPid" id="menuPid">
						<option value="">请选择</option>
						
					</select>
					</td>
				</tr>
				<tr>
					<td>二级菜单名：</td>
					<td><input type="text" name="menuName" id="menuName" placeholder="请输入菜单名称"></td>
				</tr>
			</tbody>
		 </table>
	</form>
</body>
</html>