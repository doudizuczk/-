<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>菜单页</title>
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
margin-left:50px;
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
<script>
function search(){
	var roleName=$("#roleName").val();
	window.location="<%=request.getContextPath()%>/roleHandler/roleList.action?roleName="+roleName;
};
</script>
</head>
<body>
 	<form>
 		<div>
 		<input type="button" class="btn btn-default" value="角色名称" id="btn1"><input id="roleName" name="roleName"  placeholder="角色名称..." value="${role.roleName}" >
		<input type="button" value="搜索" class="btn btn-primary" id="btn4" onClick="search()">
 		</div>
 		<table class="table table-striped table-hover">
 			<tbody>
 				<tr>
 					<th>角色编号</th>
 					<th>角色名称</th>
 					<th>状态</th>
 					<th>操作</th>
 				</tr>
 				<c:forEach items="${roleList}" var="role">
 				<tr>
 					<td>${role.roleId}<input type="hidden" id="roleId" value="${role.roleId}"></td>
 					<td>${role.roleName}</td>
 					<td>${role.roleState==1?'启用':'禁用'}</td>
 					<td><input type="button" value="启用" id="canUsed" class="btn btn-primary" onClick="startMenu(${role.roleId})"><input type="button" value="禁用" id="noUsed" class="btn btn-primary" onClick="stopMenu(${role.roleId})"></td>
 				</tr>
 				</c:forEach>
 			</tbody>
 		</table>
 	</form>
 	<div>
 		<input type="button" value="上一页" id="upPage" class="btn btn-primary"><label id="myPage"  class="label label-primary">当前第${pageNum}页 共${allNum}页</label><input type="button" value="下一页" id="nextPage" class="btn btn-primary">
 		<input type="text" class="input-group-addon" id="goPages" style="width: 100px;background-color:#FFFFFF;height:35px; " onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}" placeholder="请输入页码...">
        <input type="button" value="跳转" class="btn btn-primary" id="turnPage">
 		<input type="hidden" value="${pageNum}" id="pageNum"><input type="hidden" value="${allNum}" id="allNum">
 	</div>
</body>
<script>
/* 上一页翻页*/
$("#upPage").click(function(){
	var nowPage=$("#pageNum").val();
	var roleName=$("#roleName").val();
	if(nowPage>1){
		nowPage--;
		window.location="<%=request.getContextPath()%>/roleHandler/roleList.action?pageNum="+nowPage+"&roleName="+roleName;
	}else{
		window.location="<%=request.getContextPath()%>/roleHandler/roleList.action?pageNum="+nowPage+"&roleName="+roleName;
	}
});
/*下一页翻页*/
$("#nextPage").click(function(){
	var nowPage=$("#pageNum").val();
	var allPage=$("#allNum").val();
	var roleName=$("#roleName").val();
	if(nowPage<allPage){
		nowPage++;
		window.location="<%=request.getContextPath()%>/roleHandler/roleList.action?pageNum="+nowPage+"&roleName="+roleName;
	}else{
		window.location="<%=request.getContextPath()%>/roleHandler/roleList.action?pageNum="+nowPage+"&roleName="+roleName;
	}
	
});
$("#turnPage").click(function(){
	var nowPage=$("#goPages").val();
	var allPage=$("#allNum").val();
	var roleName=$("#roleName").val();
	if(nowPage>allPage){
		alert("超出页码范围");
	}else{
	window.location="<%=request.getContextPath()%>/roleHandler/roleList.action?pageNum="+nowPage+"&roleName="+roleName;
	}
});
/* 启用*/
function startMenu(roleId){
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/roleHandler/startRole.action",
		data:{"roleId":roleId},
		dataType:"json",
		success:function(data){
			if(data=="1"){
				window.location="<%=request.getContextPath()%>/roleHandler/roleList.action";
				alert("启用成功");
			}else{
				alert("启用失败");
			}
		},
		error:function(){
			alert("启用出错");
		}
	});
};
function stopMenu(roleId){
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/roleHandler/stopRole.action",
		data:{"roleId":roleId},
		dataType:"json",
		success:function(data){
			if(data=="1"){
				window.location="<%=request.getContextPath()%>/roleHandler/roleList.action";
				alert("禁用成功");
			}else{
				alert("禁用失败");
			}
		},
		error:function(){
			alert("禁用出错");
		}
	});
};
</script>
</html>