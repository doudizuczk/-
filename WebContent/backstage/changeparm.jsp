<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改参数页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<style>
#turnBack{
margin-left: 950px;
margin-bottom: 20px;
}
#savechange{
margin-left: 380px;
}
#nosavechange{
margin-left: 250px;
}
</style>
</head>
<body>
 	<form id="myForm">
 		<div>
 			<input type="button" value="返回" id="turnBack" class="btn btn-primary">
 		</div>
 		<table class="table table-striped table-hover">
 			<tbody>
 				<tr>
 				<th></th>
 				<th></th>
 				</tr>
 				<tr>
 					<td>参数编号：</td><td><input value="${parm.parmId-12}" class="form-control" id="parmId" name="parmId" onfocus="this.blur();"><input type="hidden" id="menuId" value="${parm.parmId}"></td>
 				</tr>
 				<tr>
 					<td>参数名称：</td><td><input value="${parm.parmName}" class="form-control" id="parmName" name="parmName"></td>
 				</tr>
 				<tr>
 					<td>参数类型：</td>
 					<td>
 						<select id="parmPid" name="parmPid">
 							<option value="">请选择</option>
 							<c:forEach items="${parmTypeList}" var="parmType">
 							<option value="${parmType.parmId}">${parmType.parmName}</option>
 							</c:forEach>
 						</select>
 					</td>
 				</tr>
 				<tr>
 					<td>参数值：</td><td><input value="${parm.parmVal}" class="form-control" onfocus="this.blur();"></td>
 				</tr>
 			</tbody>
 		</table>
 		<div>
 			<input type="button" value="保存" id="savechange" class="btn btn-primary"><input type="button" value="取消" id="nosavechange" class="btn btn-primary">
 		</div>
 	</form>
</body>
<script>
/*保存*/
$("#savechange").click(function(){
	var parmPid=$("#parmPid").val();
	var parmName=$("#parmName").val()
	if(window.confirm("确定修改吗？")){
		if(parmPid==""){
			alert("请选择参数类型");
		}else{
			if(parmName==""||parmName==null){
				alert("请输入参数名称");
			}else{
				$.ajax({
					type:"post",
					url:"<%=request.getContextPath()%>/parm/saveChanges.action",
					contentType:"application/json",
					dataType:"json",
					data:JSON.stringify($("#myForm").serializeJSON()),
					success:function(data){
						if(data=="1"){
							alert("修改成功");
						}else{
							alert("修改失败");
						}
					},
					error:function(){
						alert("保存出错");
					}
				});
			}
		}
	}
});
/* 返回*/
$("#turnBack").click(function(){
	console.log("点击到返回");
	history.back();
});
</script>
</html>