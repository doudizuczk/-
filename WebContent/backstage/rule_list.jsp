+<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>通车计费规则管理页</title>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
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
	<form class="form-inline" action="<%=request.getContextPath()%>/ruleHandler/ruleList.action" method="post" >
	    <label for="sTime">创建日期:</label>
	    <input class="form-control" type="date" id="sTime" name="sTime" value="${param.sTime}"/>
	    <label for="eTime">至</label>	
	    <input class="form-control" type="date" id="eTime" name="eTime" value="${param.eTime}"/>
	    <label for="type">规则状态:</label>
	    <select class="form-control" id="state" name="state" >
	    	<option value="0" ${param.state==0?'selected':'' }>请选择</option>
	    	<c:forEach items="${requestScope.pageInfo.dates.stateList }" var="state">
	    		<option value="${state.parmVal }" ${param.state==state.parmVal?'selected':'' }>${state.parmName }</option>
	    	</c:forEach>
	    </select>
	    <br><br><label for="ruleName"> 规则名称:</label>
	    <input class="form-control" id="ruleName" name="ruleName" value="${param.ruleName}"/>
	    <button type="submit" class="btn btn-primary">查询</button>
		<a class="btn btn-info btn-small" href="<%=request.getContextPath()%>/backstage/createRule.jsp">新增</a>
  	</form>
 	<form>
 		<table class="table table-striped table-hover" id="datatable">
 			<thead>
 				<tr>
 					<th>规则编号</th>
 					<th>规则名称</th>
 					<th>规则状态</th>
 					<th>创建时间</th>
 					<th>操作</th>
 				</tr>
 			</thead>
 			<tbody id="tbody">
 				<c:forEach items="${requestScope.pageInfo.dates.ruleList}" var="rule">
 				<tr>
 					<td>${rule.sequence}</td>
 					<td>${rule.ruleName}</td>
 					<td>${rule.stateName}</td>
 					<td>${rule.createTime}</td>
 					<td>
 					<c:if test="${rule.state==1}">启动中无法更改</c:if>
 					<c:if test="${rule.state==2}">
 						<input type="button" value="启用" onclick="enable(${rule.sequence})" class="btn btn-primary">
 						<input type="button" value="修改" onclick="update(${rule.sequence})" class="btn btn-primary">
 						<input type="button" value="删除" onclick="delet(${rule.sequence})" class="btn btn-primary">
 					</c:if>
 					<c:if test="${rule.state==3}">已删除</c:if></td>
 				</tr>
 				</c:forEach>
 			</tbody>
 		</table>
 	</form>
 	<div style="margin:0px 0px 10px 65%;">
     	<p style="font-size:10px;">共<span>${requestScope.pageInfo.totalNum}</span>条&nbsp;
     	当前页数：[<span id="curPage">${requestScope.pageInfo.curPage}</span>/<span id="tolPage">${requestScope.pageInfo.totalPage}</span>]</p>
         <button id="first" name="first" class="button2">首页</button>
         <button id="prev" name="prev" class="button2">上一页</button>
         <button id="next" name="next" class="button2">下一页</button>
         <button id="end" name="end" class="button2">末页</button>
         <input id="goTxt" name="goTxt" type="text" style="width:30px;height:12px;"/>
         <button id="go" name="go" class="button2">跳转</button>
     </div>
</body>
<script>
//启用
function enable(sequence){
	window.location.href="<%=request.getContextPath()%>/ruleHandler/enableRule.action?sTime="+$("#sTime").val()+"&eTime="+$("#eTime").val()+"&state="+$("#state").val()+"&ruleName="+$("#ruleName").val()+"&sequence="+sequence+"&curPage="+num;
}

//删除
function delet(sequence){
	window.location.href="<%=request.getContextPath()%>/ruleHandler/deleteRule.action?sTime="+$("#sTime").val()+"&eTime="+$("#eTime").val()+"&state="+$("#state").val()+"&ruleName="+$("#ruleName").val()+"&sequence="+sequence+"&curPage="+num;
}

//修改
function update(sequence){
	window.location.href="<%=request.getContextPath()%>/ruleHandler/updatingRule.action?sequence="+sequence;
}


var num=parseInt($("#curPage").text());
var total=parseInt($("#tolPage").text());

$("#first").click(function(){
	num=1;
	$("#curPage").text("");
	window.location.href="<%=request.getContextPath()%>/ruleHandler/ruleList.action?sTime="+$("#sTime").val()+"&eTime="+$("#eTime").val()+"&state="+$("#state").val()+"&ruleName="+$("#ruleName").val()+"&curPage="+num;
});

$("#prev").click(function(){
	if(num>1){
		num=num-1;
		$("#curPage").text("");
		window.location.href="<%=request.getContextPath()%>/ruleHandler/ruleList.action?sTime="+$("#sTime").val()+"&eTime="+$("#eTime").val()+"&state="+$("#state").val()+"&ruleName="+$("#ruleName").val()+"&curPage="+num;
	}
});

$("#next").click(function(){
	if(num<total){
		num=num+1;
		$("#curPage").text("");
		window.location.href="<%=request.getContextPath()%>/ruleHandler/ruleList.action?sTime="+$("#sTime").val()+"&eTime="+$("#eTime").val()+"&state="+$("#state").val()+"&ruleName="+$("#ruleName").val()+"&curPage="+num;
	}
});

$("#end").click(function(){
	num=total;
	$("#curPage").text("");
	window.location.href="<%=request.getContextPath()%>/ruleHandler/ruleList.action?sTime="+$("#sTime").val()+"&eTime="+$("#eTime").val()+"&state="+$("#state").val()+"&ruleName="+$("#ruleName").val()+"&curPage="+num;
});

$("#go").click(function(){
	var goNum=parseInt($("#goTxt").val());
	if(goNum>=1&&goNum<=total){
		num=goNum;
		$("#curPage").text("");
		window.location.href="<%=request.getContextPath()%>/ruleHandler/ruleList.action?sTime="+$("#sTime").val()+"&eTime="+$("#eTime").val()+"&state="+$("#state").val()+"&ruleName="+$("#ruleName").val()+"&curPage="+num;
	}else{
		alert("错误页码");
	}
}); 


</script>
</html>