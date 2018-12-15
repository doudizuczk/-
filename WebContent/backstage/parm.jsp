<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>参数页</title>
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
</style>
</head>
<body>
 	<form>
 		<table class="table table-striped table-hover">
 			<tbody>
 				<tr>
 					<th>参数编号</th>
 					<th>参数名称</th>
 					<th>参数类型</th>
 					<th>参数值</th>
 					<th>操作</th>
 				</tr>
 				<c:forEach items="${parmList}" var="parm">
 				<tr>
 					<td>${parm.parmId-12}<input type="hidden" id="menuId" value="${parm.parmId}"></td>
 					<td>${parm.parmName}</td>
 					<td>${parm.parmType}</td>
 					<td>${parm.parmVal}</td>
 					<td><input type="button" value="修改" id="canUsed" class="btn btn-primary" onClick="changeParm(${parm.parmId})"></td>
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
	if(nowPage>1){
		nowPage--;
		window.location="<%=request.getContextPath()%>/parm/pageParmList.action?pageNum="+nowPage;
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
		window.location="<%=request.getContextPath()%>/parm/pageParmList.action?pageNum="+nowPage;
	}else{
		return;
	}
	
});
/* 修改*/
function changeParm(parmId){
	window.location="<%=request.getContextPath()%>/parm/beforeChange.action?parmId="+parmId;
};
/*跳转*/
$("#turnPage").click(function(){
	var nowPage=$("#goPages").val();
	var allPage=$("#allNum").val()
	if(nowPage>allPage){
		alert("超出页码范围");
	}else{
	window.location="<%=request.getContextPath()%>/parm/pageParmList.action?pageNum="+nowPage;
	}
});
</script>
</html>