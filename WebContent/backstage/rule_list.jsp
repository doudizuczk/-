<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>通车计费规则管理页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>

<link href="<%=request.getContextPath()%>/css/jquery.dataTables.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>

<link href="<%=request.getContextPath()%>/css/bootstrap-select.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap-select.js"></script>
<style>
#noUsed{
/* margin-left: 25px; */
}
#nextPage{
/* margin-left:100px; */
}
#upPage{
/* margin-left:320px; */
}
#myPage{
/* margin-left: 50px; */
}
#createMenu{
/* margin-left: 950px; */
}
</style>
</head>
<body>
	<form  action="<%=request.getContextPath()%>/ruleHandler/ruleList.action" method="post" >
<!-- 	    <label for="sTime">创建日期:</label> -->
	    <button class="btn btn-default" type="button">创建日期:</button>
	    <input type="date" id="sTime" name="sTime" value="${param.sTime}" class="btn btn-default" style="height:35px"/>
<!-- 	    <label for="eTime">至</label> -->
	    <button class="btn btn-default" type="button">至:</button>	
	    <input type="date" id="eTime" name="eTime" value="${param.eTime}" class="btn btn-default" style="height:35px"/>
<!-- 	    <label for="type">规则状态:</label> -->
	      <button class="btn btn-default" type="button">规则状态:</button>	
	    <select id="state" name="state" class="btn btn-default">
	    	<option value="0" ${param.state==0?'selected':'' }>请选择</option>
	    	<c:forEach items="${requestScope.pageInfo.dates.stateList }" var="state">
	    		<option value="${state.parmVal }" ${param.state==state.parmVal?'selected':'' }>${state.parmName }</option>
	    	</c:forEach>
	    </select>
<!-- 	    <label for="ruleName"> 规则名称:</label> -->
	     <button class="btn btn-default" type="button">规则名称:</button>	
	    <input id="ruleName" name="ruleName" value="${param.ruleName}" class="btn btn-default"/>
	    <button type="submit" class="btn btn-default">查询</button>
		<a class="btn btn-primary" style="float:right;" href="<%=request.getContextPath()%>/backstage/createRule.jsp">新增</a>
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
         				<input type="button" value="启用" onclick="enable(${rule.sequence})" class="btn btn-primary btn-sm">
						<input type="button" value="修改" onclick="update(${rule.sequence})" class="btn btn-primary btn-sm">
 						<input type="button" value="删除" onclick="delet(${rule.sequence})" class="btn btn-primary btn-sm">
 				  	 </c:if>
 					<c:if test="${rule.state==3}">已删除</c:if>
 					</td>
 				</tr>
 				</c:forEach>
 			</tbody>
 		</table>
 	</form>
<div style="width: 80%">
         <div style="float: right;"> 	
         <button id="first" name="first"  class="btn btn-primary btn-sm">首页</button>
         <button id="prev" name="prev"  class="btn btn-primary btn-sm">上一页</button>
         <button id="next" name="next"  class="btn btn-primary btn-sm">下一页</button>
         <button id="end" name="end"  class="btn btn-primary btn-sm">末页</button>
         <input id="goTxt" name="goTxt" type="text" class="btn btn-default btn-sm" style="width: 15%;background-color:#FFFFFF;"/>
         <button id="go" name="go"  class="btn btn-primary btn-sm">跳转</button>
     	 共<span>${requestScope.pageInfo.totalNum}</span>条&nbsp;当前页数：[<span id="curPage">${requestScope.pageInfo.curPage}</span>/<span id="tolPage">${requestScope.pageInfo.totalPage}</span>]
     </div>
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