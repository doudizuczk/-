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
		<input type="button" id="createAdmin"  value="新增管理员" class="btn btn-primary" style="background-color: green;">
	</div>
	<form class="bs-example bs-example-form"  action="" method="post" id="queryForm">
	按条件查找:<input type="button" id="selsect"  value="查找"  onclick="queryNameAndzh()" class="btn btn-primary" style="background-color: Orange;">
    <div style="margin:5px 0px;">
				<input type="text" placeholder="账号查询..." name="account" id="account"/>
				<input type="text" placeholder="姓名查询..." name="name" id="name"/>
				<select name="roleId" id="roleId">
						<option value="">请选择角色...</option>
						<c:forEach items="${dates.roleList}" var="role">
							<option value="${role.ROLE_ID}">${role.ROLE_NAME}</option>
						</c:forEach>
				</select>
				<select name="state" id="state">
						<option value="">请选择状态...</option>
						<c:forEach items="${dates.StatePack}" var="state">
							<option value="${state.PARM_VAL}">${state.PARM_NAME}</option>
						</c:forEach>
				</select>
			</div>
	</form>
	
 	<form>
 		<table class="table table-striped table-hover" >
 				<tr>
 			<thand >
 					<th>编号</th>
 					<th>管理员账号</th>
 					<th>管理员姓名</th>
 					<th>角色</th>
 					<th>创建时间</th>
 					<th>管理员状态</th>
 					<th>操作</th>
 				</tr>
 			</thand>
 			<tbody id="adminList">
 				
 			</tbody>
 		</table>
 	</form>
 	<div>
 		<input type="button" value="上一页" id="upPage" class="btn btn-primary"><label id="myPage"  class="label label-primary">当前第${pageInfo.curPage}页 共${pageInfo.totalPage}页</label><input type="button" value="下一页" id="nextPage" class="btn btn-primary">
 		<input type="hidden" value="${pageInfo.curPage}" id="pageCurr" name="pageCurr"><input type="hidden" value="${pageInfo.totalPage}" id="pageMax" name="pageMax">
 		<!-- 跳转页码输入校验 -->
	<input type="text" class="input-group-addon" id="goPages" style="width: 50px;background-color:#FFFFFF; " onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"    > 
	<input type="button" value="GO" onclick="Go()" class="btn btn-info btn-small">
 		
 	</div>
</body>
<script>
//页面加载时更新页面，之后的都是ajax局部刷新
$(function(){  
    var page = 1;
	queryNameAndzh(page);
});
//跳转
function Go(){
	var pageCurr=$("#goPages").val();
	if(pageCurr>$("#pageMax").attr("value")||goPages==""){
		alert("输入页码必须在总页码范围内")
		return;
	}
	queryNameAndzh(pageCurr);
}
//新增管理员点击事件
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
		url:path+"/admin/queryAdminList.action?pageCurr="+page,
		data:JSON.stringify($("#queryForm").serializeJSON()),
		contentType:"application/json",
		dataType:"json",
		success:function(data){
			var str="";
			for(var i=0;i<data.dates.adminList.length;i++){
				var state=data.dates.adminList[i].ADMIN_STATE==1?'禁用':'启用'
						console.log(state);
				str+="<tr>";
				str+="<td>"+data.dates.adminList[i].ADMIN_ID+"</td>";
				str+="<td>"+data.dates.adminList[i].ADMIN_ACCOUNT+"</td>";
				str+="<td>"+data.dates.adminList[i].ADMIN_NAME+"</td>";
				str+="<td>"+data.dates.adminList[i].ROLE_NAME+"</td>";
				str+="<td>"+data.dates.adminList[i].ADMIN_CDATE+"</td>";
				str+="<td>"+data.dates.adminList[i].PARM_NAME+"</td>"; 
				str+="<td><input type='button' value='"+state+"'  onclick='starState("+data.dates.adminList[i].ADMIN_ID+","+data.dates.adminList[i].ADMIN_STATE+")' class='btn btn-primary'>&nbsp;<input type='button' value='修改'  onclick='updateAdmin("+data.dates.adminList[i].ADMIN_ID+")'  class='btn btn-primary'  >";
				str+="</td>"; 
				str+="</tr>";
			}
			 $("#adminList").html(str); //回填列表
			 $("input[name=pageCurr]").eq(0).val(data.curPage);//回填input隐藏域
			 $("input[name=pageMax]").eq(0).val(data.totalPage);//回填input隐藏域
			 $("#myPage").html("当前第"+data.curPage+"页 共"+data.totalPage+"页"); //回填显示信息
		},
		error:function(){
			window.alert("查询出错");
		}
	})
}
//禁用启用
var path="<%=request.getContextPath()%>";
function starState(adminId,state){
	console.log(adminId+"and"+state)
	$.ajax({
		url: "<%=request.getContextPath()%>/czkPer/updateAdminState.action",
		type:"POST", 
		data:{"adminId":adminId,"state":state},
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
function updateAdmin(adminId){
	console.log("点击修改管理员id="+adminId)
	window.location="<%=request.getContextPath()%>/czkPer/updateAdmin.action?adminId="+adminId;
}
</script>
</html>