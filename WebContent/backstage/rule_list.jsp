+<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>通车计费规则管理页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
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
 	<form>
 		<table class="table table-striped table-hover">
 			<tbody>
 				<tr>
 					<th>规则编号</th>
 					<th>规则名称</th>
 					<th>规则状态</th>
 					<th>创建时间</th>
 					<th>操作</th>
 				</tr>
 				<c:forEach items="${pageInfo.dates.ruleList}" var="rule">
 				<tr>
 					<td>${rule.sequence}</td>
 					<td>${rule.ruleName}</td>
 					<td>${rule.state}</td>
 					<td>${rule.createTime}</td>
 					<td><input type="button" value="启用" id="enable" class="btn btn-primary">
 					<input type="button" value="修改" id="update" class="btn btn-primary"></td>
 				</tr>
 				<!-- 注释1 -->
 				</c:forEach>
 			</tbody>
 		</table>
 	</form>
 	<div>
 		<input type="button" value="上一页" id="upPage" class="btn btn-primary"><label id="myPage"  class="label label-primary">当前第${pageInfo.curePage}页 共${pageInfo.totalPage}页</label><input type="button" value="下一页" id="nextPage" class="btn btn-primary">
 		<input type="hidden" value="${pageNum}" id="pageNum"><input type="hidden" value="${allNum}" id="allNum">
 	</div>
</body>
<script>
$("#upPage").click(function(){
	var nowPage=$("#pageNum").val();
	if(nowPage>1){
		nowPage--;
		window.location="<%=request.getContextPath()%>/menuHandler/pageMenuList.action?pageNum="+nowPage;
	}else{
		return;
	}
});
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
$("#createMenu").click(function(){
	window.location="<%=request.getContextPath()%>/"
});
</script>
</html>