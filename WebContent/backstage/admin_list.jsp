+<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理员列表</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<style>
#noUsed{
margin-left: 25px;
}
#nextPage{
margin-left:100px;
}
#upPage{
margin-left:320px;
}
#myPage{
margin-left: 50px;
}
#createMenu{
margin-left: 950px;
}
</style>
</head>
<body>
	<div>
		<input type="button" id="createMenu"  value="新增菜单" class="btn btn-primary" style="background-color: green;">
	</div>
	<form class="bs-example bs-example-form"  action="" method="post" id="queryForm">
	按条件查找:<input type="button" id="selsect"  value="查找"  onclick="queryNameAndzh()" class="btn btn-primary" style="background-color: Orange;">
    <div style="margin:5px 0px;">
				<input type="text" placeholder="账号查询..." name="account" id="account"/>
				<input type="text" placeholder="姓名查询..." name="name" id="name"/>
				<select name="roleId" id="roleId">
						<option value="">请选择</option>
						<c:forEach items="${pageInfo.dates.roleList}" var="role">
							<option value="${role.ROLE_ID}">${role.ROLE_NAME}</option>
						</c:forEach>
				</select>
			</div>
	</form>
	
 	<form>
 		<table class="table table-striped table-hover">
 			<tbody>
 				<tr>
 					<th>编号</th>
 					<th>管理员账号</th>
 					<th>管理员姓名</th>
 					<th>角色</th>
 					<th>创建时间</th>
 					<th>管理员状态</th>
 					<th>操作</th>
 				</tr>
 				<c:forEach items="${pageInfo.dates.adminList}" var="adm">
 				<tr>
 					<td>${adm.ADMIN_ID}</td>
 					<td>${adm.ADMIN_ACCOUNT}</td>
 					<td>${adm.ADMIN_NAME}</td>
 					<td>${adm.ROLE_NAME}</td>
 					<td>${adm.ADMIN_CDATE}</td>
 					<td>${adm.PARM_NAME}</td>
 					<td><input type="button" value="${adm.ADMIN_STATE==1?'禁用':'启用'}" id="enable" class="btn btn-primary">
 					<input type="button" value="修改" id="update" class="btn btn-primary"></td>
 				</tr>
 				<!-- 注释1 -->
 				</c:forEach>
 			</tbody>
 		</table>
 	</form>
 	<div>
 		<input type="button" value="上一页" id="upPage" class="btn btn-primary"><label id="myPage"  class="label label-primary">当前第${pageInfo.curPage}页 共${pageInfo.totalPage}页</label><input type="button" value="下一页" id="nextPage" class="btn btn-primary">
 		<input type="hidden" value="${pageInfo.curPage}" id="pageCurr"><input type="hidden" value="${pageInfo.totalPage}" id="pageMax">
 	</div>
</body>
<script>
$("#upPage").click(function(){//上一页
	var pageCurr=$("#pageCurr").val();
	if(pageCurr>1){
		pageCurr--;
		console.log(pageCurr)
		window.location="<%=request.getContextPath()%>/admin/adminList.action?pageCurr="+pageCurr;
	}else{
		return;
	}
});
$("#nextPage").click(function(){//下一页
	var pageCurr=$("#pageCurr").val();
	var pageMax=$("#pageMax").val();
	if(pageCurr<pageMax){
		pageCurr++;
		console.log(pageCurr)
		window.location="<%=request.getContextPath()%>/admin/adminList.action?pageCurr="+pageCurr;
	}else{
		return;
	}
	
});

$("#createMenu").click(function(){
	window.location="<%=request.getContextPath()%>/"
});
var path="<%=request.getContextPath()%>";
function queryNameAndzh(){
	alert(33333333)
		console.log($("#roleId").val());
	$.ajax({
		type:"post",
		url:path+"/admin/queryAdminList.action",
		data:JSON.stringify($("#queryForm").serializeJSON()),
		contentType:"application/json",
		dataType:"json",
		success:function(data){
			alert(22222)
		},
		error:function(){
			window.alert("添加出错");
		}
	})
	
}
</script>
</html>