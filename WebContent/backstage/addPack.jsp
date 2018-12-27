<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增套餐页</title>
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
	$("#addAdminForm").validate({
   	 rules: {
   		packType: {
   	        required: true,
   	      },
   	   account: {
   		packName: true,
   	      },
   	   packTime:{
   		 required: true,
   	   },
   	packCost:{
  		 required: true,
  	   },
   	    },
   	    
   	  messages: {
   		packType: {
   	        required: "请选择套餐类型！",
   	      },
   	   packName: {
   	        required: "请输入套餐名称",
   	      },
   	   packTime:{
   		required:"请输入套餐时常"
   	   },
   	packCost:{
      		required:"请输入套餐费用"
      	   },
   	     },
   	  submitHandler: function(form) { addQuerypackExist(); }
   })	
});
//添加套餐
var path="<%=request.getContextPath()%>";
function createAdmin(){
	var path="<%=request.getContextPath()%>";
		$.ajax({
			type:"post",
			url:path+"/pack/addpack.action",
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

//查存在
function addQuerypackExist(){
	var packName = $("#packName").val();
var path="<%=request.getContextPath()%>";
	$.ajax({
		url:path+"/pack/addQuerypackExist.action",
		type:"POST", 
		data:{"packName":packName},
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

</script>
<style>
.error{
	color:red;
}
</style>
</head>
<body>
<h3>新增套餐页</h3>
	<form id="addAdminForm" >
		<table class="table table-striped table-hover">
			<tbody>
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
				<td>选择要添加的套餐类型：</td>
					<td>
				<select name="packType" id="packType" class="btn btn-default" style="width: 200px;">
						<option value="">请选择类型...</option>
					<c:forEach items="${dates.TypePack}" var="ttt">
						<option value="${ttt.PARM_VAL}">${ttt.PARM_NAME}</option>
					</c:forEach>
				</select>
					</td>
				</tr>
				<tr>
					<td>套餐名称：</td>
					<td><input type="text" name="packName" id="packName" placeholder="请输入套餐名称..." class="btn btn-default"></td>
				</tr>
				<tr>
					<td>套餐时长(月/30天)：</td>
					<td><input type="text" name="packTime" id="packTime" placeholder="套餐时长..." class="btn btn-default"></td>
				</tr>
				<tr>
					<td>套餐费用(/元)：</td>
					<td><input type="text" id="packCost" name="packCost" placeholder="请输套餐费用..." class="btn btn-default"></td>
				</tr>
				
			</tbody>
		 </table>
		 <div style="width: 68%">
		<div style="float: right;">
		 	<input type="submit" value="新增" id="newBtn" class="btn btn-primary" style="margin-right: 100px;width: 100px;"><input type="reset" value="重置" id="reBtn" class="btn btn-primary" style="width: 100px;">
		 </div>
		 </div>
	</form>
	

</body>
</html>