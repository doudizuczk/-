<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增参数页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script>
$().ready(function(){
	$("#myForm").validate({
   	 rules: {
   		parmPid: {
   	        required: true,
   	      },
   	   parmName: {
   	        required: true,
   	      },
   	   parmVal:{
   		 required: true,
   		digits:true 
   	   },
   	    },
   	    
   	  messages: {
   		parmPid: {
   	        required: "请选择参数类型",
   	      },
   	   parmName: {
   	        required: "请填写参数名称",
   	      },
   	   parmVal:{
   		required:"请填写参数值",
   		digits:"请输入整数"
   	   }
   	     },
   	  submitHandler: function(form) { createParm(); }
   })	
});
var path="<%=request.getContextPath()%>";
function createParm(){
	$.ajax({
		type:"post",
		url:path+"/parm/createParm.action",
		data:JSON.stringify($("#myForm").serializeJSON()),
		contentType:"application/json",
		dataType:"json",
		success:function(data){
			if(data=="1"){
				window.alert("添加"+$("#parmName").val()+"成功");
				$("#parmPid").val("");
				$("#parmName").val("");
				$("#parmVal").val("");
			}else if(data=="0") {
				window.alert("添加失败");
			}else{
				alert("参数已存在");
			}
		},
		error:function(){
			window.alert("添加出错");
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
	<form id="myForm">
		<table class="table table-striped table-hover">
			<tbody>
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
				<td>参数类型：</td>
					<td>
					<select id="parmPid" name="parmPid" class="btn btn-default">
 							<option value="">请选择</option>
 							<c:forEach items="${parmTypeList}" var="parmType">
 							<option value="${parmType.parmId}">${parmType.parmName}</option>
 							</c:forEach>
 						</select>
					</td>
				</tr>
				<tr>
					<td>参数名：</td>
					<td><input type="text" name="parmName" id="parmName" placeholder="请输入参数名称..." class="btn btn-default" style="width: 135px;"></td>
				</tr>
				<tr>
					<td>参数值：</td>
					<td><input type="text" id="parmVal" name="parmVal" placeholder="请输入参数值..." class="btn btn-default" style="width: 135px;"></td>
				</tr>
			</tbody>
		 </table>
		 <div style="width: 58%">
		<div style="float: right;">
		 	<input type="submit" value="新增" id="newBtn" class="btn btn-primary btn-sm" style="margin-right: 100px;width: 100px;"><input type="reset" value="重置" id="reBtn" class="btn btn-primary btn-sm" style="width: 100px;">
		 </div>
		 </div>
	</form>
</body>
</html>