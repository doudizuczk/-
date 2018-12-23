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
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script>
var path="<%=request.getContextPath()%>";
function createMenu(){
	var roleName=$("#roleName").val();
	if(roleName!=null&&roleName!=""){
	$.ajax({
		type:"post",
		url:path+"/roleHandler/createRole.action",
		data:{"roleName":$("#roleName").val()},
		dataType:"json",
		success:function(data){
			if(data=="1"){
				window.alert("添加"+$("#roleName").val()+"成功");
				$("#menuPid").val("");
				$("#menuName").val("");
				$("#menuUrl").val("");
			}else {
				window.alert("添加失败");
			}
		},
		error:function(){
			window.alert("添加出错");
		}
	})
	}else{
		alert("请输入角色名称");
	}
};
</script>
<style>
.error{
	color:red;
}
</style>
</head>
<body>
	<form id="menuForm">
		<table class="table table-striped table-hover">
			<tbody>
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
				<td>角色名称:</td>
				<td><input type="text" name="roleName" id="roleName" placeholder="请输入角色名称..."></td>
				</tr>
			</tbody>
		 </table>
		 <div>
		 	<input type="button" value="新增" id="newBtn" class="btn btn-primary" onClick="createMenu()"><input type="reset" value="重置" id="reBtn" class="btn btn-primary">
		 </div>
	</form>
</body>
</html>