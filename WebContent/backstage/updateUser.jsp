<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改用户页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script>
$().ready(function(){
	$("#addUserForm").validate({
   	 rules: {
   		owerName: {
   	        required: true,
   	      },
   	   owerAccount: {
   		required: true,
   	      },
   	   owerPwd:{
   		 required: true,
   	   },
   	owerSex:{
  		 required: true,
  	   },
  	 owerIdcard:{
  	  		 required: true,
  	  	   },
  	  	owerAge:{
  	  		 required: true,
  	  	   },
   	    },
   	    
   	  messages: {
   		owerName: {
   	        required: "请填写姓名",
   	      },
   	   owerAccount: {
   	        required: "请填写账号",
   	      },
   	   owerPwd:{
   		required:"请填写密码"
   	   },
   	owerSex:{
      		required:"请选择性别"
      	   },
      	 owerIdcard:{
       		required:"填写身份证号"
       	   },
       	owerAge:{
            		required:"填写年龄"
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
			url:path+"/czkPer/addUpdateUser.action",
			data:JSON.stringify($("#addUserForm").serializeJSON()),
			contentType:"application/json",
			dataType:"json",
			success:function(data){
				if(data==1){
				window.alert("修改成功")
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
	<form id="addUserForm" >
		<table class="table table-striped table-hover">
			<tbody>
				<tr>
					<th></th>
					<th></th>
				</tr>
				
				<input type="hidden" value="${dates.userMap.OWER_ID}" id="owerId" name="owerId">
				<tr>
					<td>

					</td>
				</tr>
				<tr>
					<td>车主姓名：</td>
					<td><input type="text" name="owerName" id="owerName" value="${dates.userMap.OWER_NAME}"></td>
				</tr>
				<tr>
					<td>车主账号：</td>
					<td><input type="text" name="owerAccount" id="owerAccount" value="${dates.userMap.OWER_ACCOUNT}"></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="text" id="owerPwd" name="owerPwd" value="${dates.userMap.OWER_PWD}"></td>
				</tr>
				<tr>
					<td>选择性别：</td>
					<td>
					<div class="radio-inline">
					  <input type="radio"  name="owerSex" value="1" ${dates.userMap.OWER_SEX==1? 'checked':''} />
					  <label for="killOrder1">男</label>
					  
					  <input type="radio"  name="owerSex" value="2" ${dates.userMap.OWER_SEX==2? 'checked':''}/>
					  <label for="killOrder2">女</label>
					</div>
					</td>
				</tr>
				<tr>
					<td>身份证号：</td>
					<td><input type="text" name="owerIdcard" id="owerIdcard" value="${dates.userMap.OWER_IDCARD}"></td>
				</tr>
				<tr>
					<td>年龄：</td>
					<td><input type="text" name="owerAge" id="owerAge" value="${dates.userMap.OWER_AGE}"></td>
				</tr>
			</tbody>
		 </table>
		 <div>
		 	<input type="submit" value="修改" id="newBtn" class="btn btn-primary"><input type="reset" value="重置" id="reBtn" class="btn btn-primary">
		 </div>
	</form>
	

</body>
</html>