+<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>套餐列表</title>
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
		<input type="button" id="createPack"  value="新增套餐" class="btn btn-primary" style="background-color: green;">
	</div>	
	<form class="bs-example bs-example-form"  action="" method="post" id="queryForm">
	按条件查找:<input type="button" id="selsect"  value="查找"  onclick="queryNameAndzh()" class="btn btn-primary" style="background-color: Orange;">
    <div style="margin:5px 0px;">
				<input type="text" placeholder="套餐名称查询..." name="packName" id="packName"/>
				<select name="pactState" id="pactState">
						<option value="">请选择状态...</option>
					<c:forEach items="${dates.StatePack}" var="state">
						<option value="${state.PARM_VAL}">${state.PARM_NAME}</option>
					</c:forEach>
				</select>
				
				<select name="packType" id="packType">
						<option value="">请选择类型...</option>
					<c:forEach items="${dates.Type}" var="ttt">
						<option value="${ttt.PARM_VAL}">${ttt.PARM_NAME}</option>
					</c:forEach>
				</select>
<%-- 				<select name="packType" id="packType">
						<option value="">请选择状态...</option>
					<c:forEach items="${dates.Type}" var="type">
						<option value="${type.PARM_VAL}">${type.PARM_NAME}</option>
					</c:forEach>
				</select> --%>
			</div>
	</form>
 	<form>
 		<table class="table table-striped table-hover" >
 				<tr>
 			<thand >
 					<th>ID编号</th>
 					<th>套餐名称</th>
 					<th>套餐时间(/月)</th>
 					<th>费用(/元)</th>
 					<th>套餐类型</th>
 					<th>套餐状态</th>
 					<th>创建时间</th>
 					<th>操作</th>
 				</tr>
 			</thand>
 			<tbody id="adminList">
 				
 			</tbody>
 		</table>
 	</form>
 	<div>
 		<input type="button" value="上一页" id="upPage" class="btn btn-primary"><label id="myPage"  class="label label-primary">当前第${pageInfo.curPage}页 共${pageInfo.totalPage}页</label>
 		<input type="button" value="下一页" id="nextPage" class="btn btn-primary">
 		<input type="hidden" value="${pageInfo.curPage}" id="pageCurr" name="pageCurr"><input type="hidden" value="${pageInfo.totalPage}" id="pageMax" name="pageMax">
 		<!-- 跳转页码输入校验 -->
	<input type="text" class="input-group-addon" id="goPages" style="width: 50px;background-color:#FFFFFF; " onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"    > 
	<input type="button" value="GO" onclick="Go()" class="btn btn-info btn-small">
 		
 	</div>
</body>
<script>
//新增套餐点击事件

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
		url:path+"/pack/queryPackList.action?pageCurr="+page,
		data:JSON.stringify($("#queryForm").serializeJSON()),
		contentType:"application/json",
		dataType:"json",
		success:function(data){
			var str="";
			for(var i=0;i<data.dates.packList.length;i++){
				var state=data.dates.packList[i].PACK_STATE==1?'禁用':'启用'
					console.log(state);
				str+="<tr>";
				str+="<td>"+data.dates.packList[i].PACK_ID+"</td>";
				str+="<td>"+data.dates.packList[i].PACK_NAME+"</td>";
				str+="<td>"+data.dates.packList[i].PACK_TIME+"</td>";
				str+="<td>"+data.dates.packList[i].PACK_COST+"</td>";
				str+="<td>"+data.dates.packList[i].PARM_NAME+"</td>";
				str+="<td>"+data.dates.packList[i].PARM_NAME2+"</td>"; 
				str+="<td>"+data.dates.packList[i].PACK_CDATE+"</td>"; 
 				str+="<td><input type='button' value='"+state+"'  onclick='starState("+data.dates.packList[i].PACK_ID+","+data.dates.packList[i].PACK_STATE+")' class='btn btn-primary'>&nbsp;<input type='button' value='修改'  onclick='updateUser("+data.dates.packList[i].PACK_ID+")'  class='btn btn-primary'  >";
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
		url: "<%=request.getContextPath()%>/pack/updatePackState.action",
		type:"POST", 
		data:{"packId":id,"state":state},
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
function updateUser(id){
	console.log("点击修改套餐id="+id)
	window.location="<%=request.getContextPath()%>/pack/updatePack.action?packId="+id;
}
</script>
</html>