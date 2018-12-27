<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改管理员页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script>
$().ready(function(){
	$("#addAdminForm").validate({
   	 rules: {
   		name: {
   	        required: true,
   	      },
   	   account: {
   		required: true,
   	      },
   	   password:{
   		 required: true,
   	   },
   	adminPassword2:{
  		 required: true,
  	   },
  	 roleId:{
  	  		 required: true,
  	  	   },
   	    },
   	    
   	  messages: {
   		name: {
   	        required: "请填写姓名",
   	      },
   	   account: {
   	        required: "请填写账号",
   	      },
   	   adminPassword:{
   		required:"请填写密码"
   	   },
   	password:{
      		required:"请确认密码"
      	   },
    	 roleId:{
       		required:"请确选择要添加的角色"
       	   },
   	     },
   	  submitHandler: function(form) { createAdmin(); }
   })	
});

var path="<%=request.getContextPath()%>";
function createAdmin(){
	console.log("修改管理员的AJAX")
	//修改管理员
	var path="<%=request.getContextPath()%>";
			console.log("修改管理员");
		$.ajax({
			type:"post",
			url:path+"/czkPer/addUpdateAdmin.action",
			data:JSON.stringify($("#addAdminForm").serializeJSON()),
			contentType:"application/json",
			dataType:"json",
			success:function(data){
				if(data==1){
				window.alert("修改成功")
				window.location="<%=request.getContextPath()%>/admin/adminList.action";
				}
			},
			error:function(){
				window.alert("修改出错");
			}
		})
	

}


</script>
<style>
.error{
	color:red;
}
</style>
</head>
<body>
	<form id="addAdminForm" >
		<table class="table table-striped table-hover">
			<tbody>
				<tr>
					<th></th>
					<th></th>
				</tr>
				
				<input type="hidden" value="${dates.adminMap.ADMIN_ID}" id="adminId" name="adminId">
				<tr>
				<td>选择要添加的角色：</td>
					<td>
					<select name="roleId" id="roleId">
						<option value="${dates.adminMap.ROLE_ID}">${dates.adminMap.ROLE_NAME}</option>
						<c:forEach items="${dates.roleList}" var="role">
							<option value="${role.ROLE_ID}">${role.ROLE_NAME}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>管理员姓名：</td>
					<td><input type="text" name="name" id="name" value="${dates.adminMap.ADMIN_NAME}"></td>
				</tr>
				<tr>
					<td>管理员账号：</td>
					<td><input type="text" name="account" id="account" value="${dates.adminMap.ADMIN_ACCOUNT}"></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="text" id="password" name="password" value="${dates.adminMap.ADMIN_PWD}"></td>
				</tr>
							
				
			</tbody>
		 </table>
		 <div>
		 	<input type="submit" value="修改" id="newBtn" class="btn btn-primary"><input type="reset" value="重置" id="reBtn" class="btn btn-primary">
		 </div>
	</form>
	

</body>
</html>