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
 	<form>
 		<table class="table table-striped table-hover">
 			<tbody>
 				<tr>
 					<th>参数编号</th>
 					<th>参数名称</th>
 					<th>菜单链接</th>
 					<th>父级菜单</th>
 					<th>状态</th>
 					<th>操作</th>
 				</tr>
 				<c:forEach items="${menuList}" var="menu" step=1>
 				<tr>
 					<td><input type="hidden" id="menuId" value="${menu.menuId}"></td>
 					<td>${menu.menuName}</td>
 					<td>${menu.menuUrl}</td>
 					<td>${menu.menuPname}</td>
 					<td>${menu.menuState==1?'启用':'禁用'}</td>
 					<td><input type="button" value="启用" id="canUsed" class="btn btn-primary" onClick="startMenu(${menu.menuId})"><input type="button" value="禁用" id="noUsed" class="btn btn-primary" onClick="stopMenu(${menu.menuId})"></td>
 				</tr>
 				</c:forEach>
 			</tbody>
 		</table>
 	</form>
 	<div>
 		<input type="button" value="上一页" id="upPage" class="btn btn-primary"><label id="myPage"  class="label label-primary">当前第${pageNum}页 共${allNum}页</label><input type="button" value="下一页" id="nextPage" class="btn btn-primary">
 		<input type="hidden" value="${pageNum}" id="pageNum"><input type="hidden" value="${allNum}" id="allNum">
 	</div>
</body>
<script>
/* 上一页翻页*/
$("#upPage").click(function(){
	var nowPage=$("#pageNum").val();
	if(nowPage>1){
		nowPage--;
		window.location="<%=request.getContextPath()%>/menuHandler/pageMenuList.action?pageNum="+nowPage;
	}else{
		return;
	}
});
/*下一页翻页*/
$("#nextPage").click(function(){
	var nowPage=$("#pageNum").val();
	var allPage=$("#allNum").val();
	if(nowPage<allPage){
		nowPage++;
		window.location="<%=request.getContextPath()%>/menuHandler/pageMenuList.action?pageNum="+nowPage;
	}else{
		return;
	}
	
});
/* 启用*/
function startMenu(menuId){
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/menuHandler/controlMenu.action",
		data:{"menuId":menuId},
		dataType:"json",
		success:function(data){
			if(data=="1"){
				window.location="<%=request.getContextPath()%>/menuHandler/menuList.action";
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
function stopMenu(menuId){
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/menuHandler/stopMenu.action",
		data:{"menuId":menuId},
		dataType:"json",
		success:function(data){
			if(data=="1"){
				window.location="<%=request.getContextPath()%>/menuHandler/menuList.action";
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