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
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<link href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap-select.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap-select.js"></script>
</head>
<script>
<!-- 输入框1 -->
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
			</span> 
			<select class="selectpicker" data-live-search="false" id="stage">
				<option value="">请选择</option>
				<option value=1>启用</option>
				<option value=2>过期</option>
				<option value=3>已退</option>
			</select> 
			<span class="input-group-btn">
				<button class="btn btn-default" type="button">车牌号</button>
			</span> <input type="text" class="form-control" id="carId"> 
			<span class="input-group-btn">
				<button class="btn btn-default" type="button" onclick="search()">搜索</button>
			</span>
<!-- 			<span class="input-group-btn" style="float:right;"> -->
<!-- 			<input type="button" value="+新增白名单" onclick="addWhiteList()" class="btn btn-info btn-small"> -->
<!-- 			</span> -->
		</div>
		<!-- /input-group -->
	</div>
	<!-- 输入框END -->
	<table class="table table-hover">
		<thead>
			<tr>
				<th>车牌号</th>
				<th>颜色</th>
				<th>车辆类型</th>
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
					<td>${wlist.carColor}</td>
					<td>${wlist.carType}</td>
					<td>${wlist.parkZone}</td>
					<td>${wlist.parkId}</td>
					<td>${wlist.parmName}</td>
					<td><c:choose>
					   <c:when test="${wlist.tranState==1}">
					    <input type="button" value="禁用" onclick="stopState(${wlist.tranId},'${wlist.owerName}')" class="btn btn-primary btn-sm">
					   </c:when>
					   <c:otherwise>
						<input type="button" value="启用" onclick="starState(${wlist.tranId},'${wlist.owerName}')" class="btn btn-primary btn-sm">
					   </c:otherwise>
					   </c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div style="width: 70%">
         <div style="float: right;">
	<input type="button" value="上一页" onclick="previousPage()"  class="btn btn-primary btn-sm"">
	<a id="pageNum" value="${pageNum}" style="display:none">当前页码：${pageNum}</a>
	<a id="pages" value="${pages}" style="display:none">总页码：${pages}</a>
	<label id="page"  class="btn btn-default">当前第${pageNum}页 共${pages}页</label>
	<input type="button" value="下一页" onclick="nextPage()"  class="btn btn-primary btn-sm">
	<!-- 跳转页码输入校验 -->
	<input type="text" class="input-group-addon" id="goPages" style="width: 50px;background-color:#FFFFFF; " onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"    > 
	<input type="button" value="GO" onclick="Go()"  class="btn btn-primary btn-sm"><a id="total">总条数：${total}</a>
         </div>
         </div>

</body>
<script type="text/javascript">
$(document).ready(function(){
	 	 
});
function addWhiteList(){
	alert("跳转新增页");
	window.location="<%=request.getContextPath()%>/whiteListHander/addWhiteList.action";
}
//跳转
function Go(){
	var goPages=$("#goPages").val();
	if(goPages>$("#pages").attr("value")||goPages==""){
		alert("输入页码必须在总页码范围内")
		return;
	}
	$.ajax({
	    url: "<%=request.getContextPath()%>/whiteListHander/turnPageWhiteList.action",
		type:"POST",
		data:{"pageNums":goPages,"stage":$("#stage").val(),"carId":$("#carId").val()},
		dataType:"json",
		success : function(data){
			var str="";
			for(var i=1;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].carColor+"</td>";
				str+="<td>"+data[i].carType+"</td>";
				str+="<td>"+data[i].parkZone+"</td>";
				str+="<td>"+data[i].parkId+"</td>";
				str+="<td>"+data[i].parmName+"</td>";
				str+="<td>";
				if(data[i].tranState==1){
				str+="<input type='button' value='禁用'  onclick='stopState("+data[i].tranId+",&quot;"+data[i].owerName+"&quot;)' class='btn btn-primary btn-sm'>";
				}else{
				str+="<input type='button' value='启用'  onclick='starState("+data[i].tranId+",&quot;"+data[i].owerName+"&quot;)' class='btn btn-primary btn-sm'>";
				}
				str+="</td>"; 
				str+="</tr>";
			}
			$("#whiteList").html(str);
			$("#page").text("当前第"+data[0].pageNum+"页 共"+data[0].pages+"页");
			$("#pageNum").text("当前页码:"+data[0].pageNum);
			$("#pageNum").val(data[0].pageNum);
			$("#pages").text("总页码:"+data[0].pages);
			$("#pages").val(data[0].pages);	
			$("#total").text("总条数:"+data[0].total);	
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
}
//上一页
function previousPage(){
	var pageNum=$("#pageNum").attr("value");
	var pages=$("#pages").attr("value");
	var pageNums=pageNum--;
	if(pageNums==0){
		pageNums=1;
	}
	$.ajax({
	    url: "<%=request.getContextPath()%>/whiteListHander/turnPageWhiteList.action",
		type:"POST",
		data:{"pageNums":pageNums,"stage":$("#stage").val(),"carId":$("#carId").val()},
		dataType:"json",
		success : function(data){
			var str="";
			for(var i=1;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].carColor+"</td>";
				str+="<td>"+data[i].carType+"</td>";
				str+="<td>"+data[i].parkZone+"</td>";
				str+="<td>"+data[i].parkId+"</td>";
				str+="<td>"+data[i].parmName+"</td>";
				str+="<td>";
				if(data[i].tranState==1){
					str+="<input type='button' value='禁用'  onclick='stopState("+data[i].tranId+",&quot;"+data[i].owerName+"&quot;)' class='btn btn-primary btn-sm'>";
					}else{
					str+="<input type='button' value='启用'  onclick='starState("+data[i].tranId+",&quot;"+data[i].owerName+"&quot;)' class='btn btn-primary btn-sm'>";
					}
				str+="</td>"; 
				str+="</tr>";
			}
			$("#whiteList").html(str);
			$("#page").text("当前第"+data[0].pageNum+"页 共"+data[0].pages+"页");
		    $("#pageNum").text("当前页码:"+data[0].pageNum);
		    $("#pageNum").val(data[0].pageNum);
		    $("#pages").text("总页码:"+data[0].pages);
		    $("#pages").val(data[0].pages);
		    $("#total").text("总页码:"+data[0].total);	
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
	$.ajax({
	    url: "<%=request.getContextPath()%>/whiteListHander/turnPageWhiteList.action",
		type:"POST",
		data:{"pageNums":pageNums,"stage":$("#stage").val(),"carId":$("#carId").val()},
		dataType:"json",
		success : function(data){
			var str="";
			for(var i=1;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].carColor+"</td>";
				str+="<td>"+data[i].carType+"</td>";
				str+="<td>"+data[i].parkZone+"</td>";
				str+="<td>"+data[i].parkId+"</td>";
				str+="<td>"+data[i].parmName+"</td>"; 
				str+="<td>";
				if(data[i].tranState==1){
					str+="<input type='button' value='禁用'  onclick='stopState("+data[i].tranId+",&quot;"+data[i].owerName+"&quot;)' class='btn btn-primary btn-sm'>";
					}else{
					str+="<input type='button' value='启用'  onclick='starState("+data[i].tranId+",&quot;"+data[i].owerName+"&quot;)' class='btn btn-primary btn-sm'>";
					}
				str+="</td>"; 
				str+="</tr>";
			}
			$("#whiteList").html(str);
			$("#page").text("当前第"+data[0].pageNum+"页 共"+data[0].pages+"页");
			$("#pageNum").text("当前页码:"+data[0].pageNum);
			$("#pageNum").val(data[0].pageNum);
			$("#pages").text("总页码:"+data[0].pages);
			$("#pages").val(data[0].pages);
			$("#total").text("总页码:"+data[0].total);	
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
			for(var i=1;i<data.length;i++){
				str+="<tr>";
				str+="<td>"+data[i].carId+"</td>";
				str+="<td>"+data[i].carColor+"</td>";
				str+="<td>"+data[i].carType+"</td>";
				str+="<td>"+data[i].parkZone+"</td>";
				str+="<td>"+data[i].parkId+"</td>";
				str+="<td>"+data[i].parmName+"</td>"; 
				str+="<td>";
				if(data[i].tranState==1){
					str+="<input type='button' value='禁用'  onclick='stopState("+data[i].tranId+",&quot;"+data[i].owerName+"&quot;)' class='btn btn-primary btn-sm'>";
					}else{
					str+="<input type='button' value='启用'  onclick='starState("+data[i].tranId+",&quot;"+data[i].owerName+"&quot;)' class='btn btn-primary btn-sm'>";
					}
				str+="</td>"; 
				str+="</tr>";
			}
			$("#whiteList").html(str);
			$("#page").text("当前第"+data[0].pageNum+"页 共"+data[0].pages+"页");
			$("#pageNum").text("当前页码:"+data[0].pageNum);
			$("#pageNum").val(data[0].pageNum);
			$("#pages").text("总页码:"+data[0].pages);
			$("#pages").val(data[0].pages);
			$("#total").text("总页码:"+data[0].total);	
		}
});
}
		

</script>
</html>