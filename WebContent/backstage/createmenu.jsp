<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增菜单菜单页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap-select.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap-select.js"></script>
<script>
$().ready(function(){
	$("#menuForm").validate({
   	 rules: {
   		menuPid: {
   	        required: true,
   	      },
   	   menuName: {
   	        required: true,
   	      },
   	   menuUrl:{
   		 required: true,
   	   },
   	    },
   	    
   	  messages: {
   		menuPid: {
   	        required: "请选择所属菜单",
   	      },
   	   menuName: {
   	        required: "请填写菜单名称",
   	      },
   	   menuUrl:{
   		required:"请填写菜单链接"
   	   }
   	     },
   	  submitHandler: function(form) { createMenu(); }
   })	
});
var path="<%=request.getContextPath()%>";
function createMenu(){
	console.log($("#menuPid").val());
	$.ajax({
		type:"post",
		url:path+"/menuHandler/addMenu.action",
		data:JSON.stringify($("#menuForm").serializeJSON()),
		contentType:"application/json",
		dataType:"json",
		success:function(data){
			if(data=="1"){
				window.alert("添加"+$("#menuName").val()+"成功");
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
};
function back(){
	window.history.back();
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
	<input type="button" value="返回" onClick="back()" class="btn btn-primary">
		<table class="table table-striped table-hover">
			<tbody>
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
				<td>所属菜单：</td>
					<td>
					<select name="menuPid" id="menuPid" class="selectpicker" data-live-search="false">
						<option value="">请选择</option>
						<c:forEach items="${firstMenuList}" var="menu">
							<option value="${menu.menuId}">${menu.menuName}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>二级菜单名：</td>
					<td><input type="text" name="menuName" id="menuName" placeholder="请输入菜单名称..." class="btn btn-default" style="width: 220px;"></td>
				</tr>
				<tr>
					<td>菜单链接：</td>
					<td><input type="text" id="menuUrl" name="menuUrl" placeholder="请输入菜单链接..." class="btn btn-default" style="width: 220px;"></td>
				</tr>
			</tbody>
		 </table>
		<div style="width: 55%">
		<div style="float: right;">
		 	<input type="submit" value="新增" id="newBtn" class="btn btn-primary btn-sm" style="margin-right: 100px;width: 100px;"><input type="reset" value="重置" id="reBtn" class="btn btn-primary btn-sm" style="width: 100px;">
		 </div>
		 </div>
	</form>
</body>
</html>