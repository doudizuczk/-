<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>白名单管理</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script
	src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<link
	href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap-select.css"
	rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap-select.js"></script>
</head>
<script>
<!-- 输入框 -->
$(document).ready(function() {
	$('.bs-select').selectpicker({});
});
</script>
<body>
	<!-- 输入框 -->
	<div class="col-lg-6" style="width: 50%;">
		<div class="input-group">
			<span class="input-group-btn">
				<button class="btn btn-default" type="button">车辆状态</button>
			</span> <select class="selectpicker" data-live-search="false" id="stage">
				<option value="">请选择</option>
				<option value=1>启用</option>
				<option value=2>禁用</option>
			</select> <span class="input-group-btn">
				<button class="btn btn-default" type="button">车牌号</button>
			</span> <input type="text" class="form-control" id="carId"> 
			<span class="input-group-btn">
				<button class="btn btn-default" type="button" onclick="search()">搜索</button>
			</span>
		</div>
		<!-- /input-group -->
	</div>
	<!-- 输入框END -->

	<table class="table table-hover">
		<caption>白名单列表</caption>
		<thead>
			<tr>
				<th>车牌号</th>
				<th>用户</th>
				<th>联系电话</th>
				<th>车位区</th>
				<th>车位编号</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="whiteList">
			<c:forEach items="${whiteList}" var="wlist">
				<tr>
					<td>${wlist.carId}</td>
					<td>${wlist.owerName}</td>
					<td>${wlist.owerPhone}</td>
					<td>${wlist.parkZone}</td>
					<td>${wlist.parkId}</td>
					<td>${wlist.parmName}</td>
					<td><input type="button" value="禁用"
						onclick="stopState(${wlist.tranId},'${wlist.owerName}')">&nbsp;<input
						type="button" value="启用"
						onclick="starState(${wlist.tranId},'${wlist.owerName}')">
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<input type="button" value="上一页" onclick="previousPage()" class="btn btn-info btn-small">
	<a id="pageNum" value="${pageNum}">当前页码：${pageNum}</a>/
	<a id="pages" value="${pages}">总页码：${pages}</a>
	<input type="button" value="下一页" onclick="nextPage()" class="btn btn-info btn-small">
	<!-- 跳转页码输入校验 -->
	<input type="text" class="input-group-addon" id="goPages" style="width: 50px;background-color:#FFFFFF; " onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"    > 
	<input type="button" value="GO" onclick="Go()" class="btn btn-info btn-small">

</body>
<script type="text/javascript">
$(document).ready(function(){
	 
	 
});
//跳转
function Go(){
	var goPages=$("#goPages").val();
	if(goPages>$("#pages").attr("value")||goPages==""){
		alert("输入页码必须在总页码范围内")
		return;
	}
	$("#pageNum").text("当前页码:"+goPages);
	$("#pageNum").val(goPages);
	$.ajax({
	    url: "<%=request.getContextPath()%>/whiteListHander/turnPageWhiteList.action",
		type:"POST",
		data:{"pageNums":goPages,"stage":$("#stage").val(),"carId":$("#carId").val()},
		dataType:"json",
		success : function(data){
			var str="";
			for(var i=0;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].owerName+"</td>";
				str+="<td>"+data[i].owerPhone+"</td>";
				str+="<td>"+data[i].parkZone+"</td>";
				str+="<td>"+data[i].parkId+"</td>";
				str+="<td>"+data[i].parmName+"</td>"; 
				str+="<td><input type='button' value='禁用'  onclick='stopState("+data[i].tranId+",'"+data[i].owerName+"')'>&nbsp;<input type='button' value='启用'  onclick='starState("+data[i].tranId+",'"+data[i].owerName+"')'>";
				str+="</td>"; 
				str+="</tr>";
			}
			$("#whiteList").html(str);
			
		}
});
}
//禁用
function stopState(tranId,owerName){
	$.ajax({
		    url: "<%=request.getContextPath()%>/whiteListHander/stopWhiteList.action",
			type:"POST",
			data:{"tranId":tranId},
			dataType:"json",
			success : function(data){
				if(data==1){
				alert("用户"+owerName+"已被禁用");
				window.location="<%=request.getContextPath()%>/whiteListHander/whiteList.action";	
				}else{
					alert("禁用失败");	
				}
			}
	});
	
}; 
//启用
function starState(tranId,owerName){
	$.ajax({
	    url: "<%=request.getContextPath()%>/whiteListHander/starWhiteList.action",
		type:"POST",
		data:{"tranId":tranId},
		dataType:"json",
		success : function(data){
			if(data==1){
			alert("用户"+owerName+"已启用");
			window.location="<%=request.getContextPath()%>/whiteListHander/whiteList.action";	
			}else{
				alert("启用失败");	
			}
		}
});
};
//上一页
function previousPage(){
	var pageNum=$("#pageNum").attr("value");
	var pages=$("#pages").attr("value");
	var pageNums=pageNum--;
	if(pageNums==0){
		pageNums=1;
	}
	$("#pageNum").text("当前页码:"+pageNums);
	$("#pageNum").val(pageNums);
	$.ajax({
	    url: "<%=request.getContextPath()%>/whiteListHander/turnPageWhiteList.action",
		type:"POST",
		data:{"pageNums":pageNums,"stage":$("#stage").val(),"carId":$("#carId").val()},
		dataType:"json",
		success : function(data){
			var str="";
			for(var i=0;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].owerName+"</td>";
				str+="<td>"+data[i].owerPhone+"</td>";
				str+="<td>"+data[i].parkZone+"</td>";
				str+="<td>"+data[i].parkId+"</td>";
				str+="<td>"+data[i].parmName+"</td>"; 
				str+="<td><input type='button' value='禁用'  onclick='stopState("+data[i].tranId+",'"+data[i].owerName+"')'>&nbsp;<input type='button' value='启用'  onclick='starState("+data[i].tranId+",'"+data[i].owerName+"')'>";
				str+="</td>"; 
				str+="</tr>";
			}
			$("#whiteList").html(str);
			
		}
});
	
}
//下一页
function nextPage(){
	var pageNum=$("#pageNum").attr("value");
	var pages=$("#pages").attr("value");
	var pageNums=pageNum++;
	if(pageNums>=pages){
		pageNums=pages;
	}
	$("#pageNum").text("当前页码:"+pageNums);
	$("#pageNum").val(pageNums);
	$.ajax({
	    url: "<%=request.getContextPath()%>/whiteListHander/turnPageWhiteList.action",
		type:"POST",
		data:{"pageNums":pageNums,"stage":$("#stage").val(),"carId":$("#carId").val()},
		dataType:"json",
		success : function(data){
			var str="";
			for(var i=0;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].owerName+"</td>";
				str+="<td>"+data[i].owerPhone+"</td>";
				str+="<td>"+data[i].parkZone+"</td>";
				str+="<td>"+data[i].parkId+"</td>";
				str+="<td>"+data[i].parmName+"</td>"; 
				str+="<td><input type='button' value='禁用'  onclick='stopState("+data[i].tranId+",'"+data[i].owerName+"')'>&nbsp;<input type='button' value='启用'  onclick='starState("+data[i].tranId+",'"+data[i].owerName+"')'>";
				str+="</td>"; 
				str+="</tr>";
			}
			$("#whiteList").html(str);
		}
});
	
}
//搜索
function search(){
	$.ajax({
	    url: "<%=request.getContextPath()%>/whiteListHander/turnPageWhiteList.action",
		type:"POST",
		data:{"pageNums":1,"stage":$("#stage").val(),"carId":$("#carId").val()},
		dataType:"json",
		success : function(data){
			var str="";
			for(var i=0;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].owerName+"</td>";
				str+="<td>"+data[i].owerPhone+"</td>";
				str+="<td>"+data[i].parkZone+"</td>";
				str+="<td>"+data[i].parkId+"</td>";
				str+="<td>"+data[i].parmName+"</td>"; 
				str+="<td><input type='button' value='禁用'  onclick='stopState("+data[i].tranId+",'"+data[i].owerName+"')'>&nbsp;<input type='button' value='启用'  onclick='starState("+data[i].tranId+",'"+data[i].owerName+"')'>";
				str+="</td>"; 
				str+="</tr>";
			}
			$("#whiteList").html(str);
		}
});
}
		

</script>
</html>