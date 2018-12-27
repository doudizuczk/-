<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>车主列表</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
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
	<div>
		
	</div>
	<form  action="" method="post" id="queryForm">
<!--     <div style="margin:5px 0px;"> -->
                <button type="button" class="btn btn-default">账号:</button>
				<input type="text" placeholder="请输入账号..." name="owerAccount" id="owerAccount" class="btn btn-default" style="width: 15%"/>
                <button type="button" class="btn btn-default">姓名:</button>
				<input type="text" placeholder="请输入姓名..." name="owerName" id="owerName" class="btn btn-default" style="width: 10%"/>
                <button type="button" class="btn btn-default">性别:</button>
				<select name="owerSex" id="owerSex" class="selectpicker" data-live-search="false" style="width: 10%">
						<option value="">请选择性别...</option>
						<option value="1">男</option>
						<option value="2">女</option>
				</select>
                <button type="button" class="btn btn-default">状态:</button>
				<select name="owerState" id="owerState" class="selectpicker" data-live-search="false">
						<option value="">请选择状态...</option>
						<c:forEach items="${dates.StatePack}" var="state">
							<option value="${state.PARM_VAL}">${state.PARM_NAME}</option>
						</c:forEach>
				</select>
	            <input type="button" id="selsect"  value="查找"  onclick="queryNameAndzh()" class="btn btn-default"">
<!-- 			</div> -->
	</form>
 	<form>
 		<table class="table table-striped table-hover" >
 				<tr>
 			<thand >
 					<th>ID编号</th>
 					<th>车主员姓名</th>
 					<th>车主账号</th>
 					<th>电话号</th>
 					<th>性别</th>
 					<th>身份证</th>
 					<th>年龄</th>
 					<th>车主状态</th>
 					<th>创建时间</th>
 					<th>操作</th>
 				</tr>
 			</thand>
 			<tbody id="adminList">
 				
 			</tbody>
 		</table>
 	</form>
 	<div style="width: 70%">
         <div style="float: right;">
 		<input type="button" value="上一页" id="upPage" class="btn btn-primary btn-sm"><label id="myPage"  class="btn btn-default">当前第${pageInfo.curPage}页 共${pageInfo.totalPage}页，共${pageInfo.totalNum}条记录</label>
 		<input type="button" value="下一页" id="nextPage" class="btn btn-primary btn-sm">
 		<input type="hidden" value="${pageInfo.curPage}" id="pageCurr" name="pageCurr"><input type="hidden" value="${pageInfo.totalPage}" id="pageMax" name="pageMax">
 		<!-- 跳转页码输入校验 -->
	<input type="text" class='btn btn-default btn-sm' id="goPages" style="width: 15%;background-color:#FFFFFF; " onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"    > 
	<input type="button" value="GO" onclick="Go()" class="btn btn-primary btn-sm">
 		
 	</div>
 	</div>
</body>
<script>


//跳转
function Go(){
	var pageCurr=$("#goPages").val();
	if(pageCurr>$("#pageMax").attr("value")||goPages==""){
		alert("输入页码必须在总页码范围内")
		return;
	}
	queryNameAndzh(pageCurr);
}
$("#createAdmin").click(function(){
	window.location="<%=request.getContextPath()%>/czkPer/addAdmin.action";
});
//上一页
$("#upPage").click(function(){
	var pageCurr=$("#pageCurr").val();
	if(pageCurr>1){
		pageCurr--;
		console.log(pageCurr)
		queryNameAndzh(pageCurr);
		<%-- window.location="<%=request.getContextPath()%>/admin/adminList.action?pageCurr="+pageCurr; --%>
	}else{
		console.log(pageCurr)
		return;
	}
});
//下一页
$("#nextPage").click(function(){
	var pageCurr=$("#pageCurr").val();
	var pageMax=$("#pageMax").val();
	if(pageCurr<pageMax){
		pageCurr++;
		console.log(pageCurr)
		queryNameAndzh(pageCurr);
	}else{
		return;
	}
});

<%-- $("#createMenu").click(function(){
	window.location="<%=request.getContextPath()%>/"
});
 --%>
 //页面初始化加载方法
$(function(){  
    var page = 1;
	queryNameAndzh(page);
});
//条件查找
var path="<%=request.getContextPath()%>";
function queryNameAndzh(pageCurr){
		console.log("进入查找方法");
var page=1;
		if (pageCurr!=null){
		page=pageCurr
		}
	$.ajax({
		type:"post",
		url:path+"/czkPer/queryOwerList.action?pageCurr="+page,
		data:JSON.stringify($("#queryForm").serializeJSON()),
		contentType:"application/json",
		dataType:"json",
		success:function(data){
			var str="";
			for(var i=0;i<data.dates.adminList.length;i++){
				var state=data.dates.adminList[i].OWER_STATE==1?'禁用':'启用'
						console.log(state);
				var sex=data.dates.adminList[i].OWER_SEX==1?'男':'女'
				str+="<tr>";
				str+="<td>"+data.dates.adminList[i].OWER_ID+"</td>";
				str+="<td>"+data.dates.adminList[i].OWER_NAME+"</td>";
				str+="<td>"+data.dates.adminList[i].OWER_ACCOUNT+"</td>";
				str+="<td>"+data.dates.adminList[i].OWER_PHONE+"</td>";
				str+="<td>"+sex+"</td>";
				str+="<td>"+data.dates.adminList[i].OWER_IDCARD+"</td>";
				str+="<td>"+data.dates.adminList[i].OWER_AGE+"</td>"; 
				
				str+="<td>"+data.dates.adminList[i].PARM_NAME+"</td>"; 
				str+="<td>"+data.dates.adminList[i].OWER_CDATE+"</td>"; 
 				str+="<td><input type='button' value='"+state+"'  onclick='starState("+data.dates.adminList[i].OWER_ID+","+data.dates.adminList[i].OWER_STATE+")' class='btn btn-primary btn-sm'>&nbsp;<input type='button' value='修改'  onclick='updateUser("+data.dates.adminList[i].OWER_ID+")'  class='btn btn-primary btn-sm'>";
				str+="</td>"; 
				str+="</tr>";
			}
			 $("#adminList").html(str); //回填列表
			 $("input[name=pageCurr]").eq(0).val(data.curPage);//回填input隐藏域
			 $("input[name=pageMax]").eq(0).val(data.totalPage);//回填input隐藏域
			 $("#myPage").html("当前第"+data.curPage+"页 共"+data.totalPage+"页/总条数"+data.totalNum+""); //回填显示信息
		},
		error:function(){
			window.alert("查询出错");
		}
	})
}
//禁用启用
var path="<%=request.getContextPath()%>";
function starState(id,state){
	console.log(id+"and"+state)
	$.ajax({
		url: "<%=request.getContextPath()%>/czkPer/updateUserState.action",
		type:"POST", 
		data:{"userId":id,"state":state},
		dataType:"json",
		success:function(data){
			if(data==1){
			window.alert("修改状态成功!");
			var a = $("#pageCurr").val();
			queryNameAndzh(a);
			}
		},
		error:function(){
			window.alert("操作出错");
		}
	})
}
//修改
function updateUser(userId){
	console.log("点击修改用户id="+userId)
	window.location="<%=request.getContextPath()%>/czkPer/updateUser.action?userId="+userId;
}
</script>
</html>