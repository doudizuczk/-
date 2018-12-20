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
<script src="<%=request.getContextPath()%>/js/jquery.ztree.core.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ztree.excheck.js"></script>
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
	<form id="treeForm">
		<div id="leftdiv">
		<input type="button" class="btn btn-default" value="查看角色" id="btn1">
		<select>
		<option value="">请选择</option>
		
		</select>
		</div>
	</form>
</body>
</html>