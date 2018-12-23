<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增管理员页</title>
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
   	  submitHandler: function(form) { addQueryAdminExist(); }
   })	
});
//添加管理员
var path="<%=request.getContextPath()%>";
function createAdmin(){
	console.log("添加管理员的AJAX")
	//添加管理员
	var path="<%=request.getContextPath()%>";
			console.log("新增管理员");
		$.ajax({
			type:"post",
			url:path+"/czkPer/addAdmin.action",
			data:JSON.stringify($("#addAdminForm").serializeJSON()),
			contentType:"application/json",
			dataType:"json",
			success:function(data){
				if(data==1){
				window.alert("添加成功")
				}
			},
			error:function(){
				window.alert("查询出错");
			}
		})
}
//打开页面加载失去焦点判断
/* $(document).ready(function(){
	$("#account").blur(function(){
		console.log("失去焦点");
		addQueryAdminExist();
	})
}) */
//查存在
function addQueryAdminExist(){
	console.log(name+"=对其查存");	
	var account = $("#account").val();
var path="<%=request.getContextPath()%>";
	$.ajax({
		url:path+"/czkPer/addQueryAdminExist.action",
		type:"POST", 
		data:{"account":account},
		dataType:"json",
		success:function(data){
			if(data==1){
			window.alert("账号已存在!");
			}else{
				createAdmin();
			}
		},
		error:function(){
			window.alert("操作出错");
		}
	})
}
<%-- var path="<%=request.getContextPath()%>";
function starState(adminId,state){
	console.log(adminId+"and"+state)
	alert("提交修改")
} --%>

</script>
<style>
.error{
	color:red;
}
</style>
</head>
<body>
<h3>新增管理员页</h3>
	<form id="addAdminForm" >
		<table class="table table-striped table-hover">
			<tbody>
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
				<td>选择要添加的角色：</td>
					<td>
					<select name="roleId" id="roleId">
						<option value="">请选择</option>
						<c:forEach items="${dates.roleList}" var="role">
							<option value="${role.ROLE_ID}">${role.ROLE_NAME}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>管理员姓名：</td>
					<td><input type="text" name="name" id="name" placeholder="请输入管理员姓名..."></td>
				</tr>
				<tr>
					<td>管理员账号：</td>
					<td><input type="text" name="account" id="account" placeholder="请输入账号名称..."></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" id="password" name="password" placeholder="请输入密码..."></td>
				</tr>
								<tr>
					<td>确认密码：</td>
					<td><input type="password" id="adminPassword2" name="adminPassword2" placeholder="请再次输入密码..."></td>
				</tr>
				
			</tbody>
		 </table>
		 <div>
		 	<input type="submit" value="新增" id="newBtn" class="btn btn-primary"><input type="reset" value="重置" id="reBtn" class="btn btn-primary">
		 </div>
	</form>
	

</body>
</html>