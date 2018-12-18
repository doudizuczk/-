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
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script
	src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<style>
#nextPage {
	margin-left: 50px;
}

#upPage {
	margin-left: 320px;
}

#myPage {
	margin-left: 50px;
}
</style>
<script>
$(function(){
	//得到今天的日期
	var date_now=new Date();
	//得到该日期所在年份
	var year=date_now.getFullYear();
	var month = date_now.getMonth()+1 < 10 ? "0"+(date_now.getMonth()+1) : (date_now.getMonth()+1);
	var date = date_now.getDate() < 10 ? "0"+date_now.getDate() : date_now.getDate();
	$("#startTime").attr("max",year+"-"+month+"-"+date);
	$("#finalTime").attr("max",year+"-"+month+"-"+date);

});
function search(){
	var startTime=$("#startTime").val();
	var finalTime=$("#finalTime").val();
	var adminAccount=$("#adminAccount").val();
	var pageNum=$("#pageNum").val();
	window.location="<%=request.getContextPath()%>/logHandler/queryLogList.action?startTime="+startTime+"&finalTime="+finalTime+"&adminAccount="+adminAccount;
};
</script>
</head>
<body>
	<form id="myForm">
	<div>
				<input type="button" class="btn btn-default" value="日志时间" id="btn1"><input id="startTime" name="startTime"  placeholder="起始时间..." value="${searchUitl.startTime}" type="date">
				<input class="btn btn-default" type="button" value="至" id="btn2"><input id="finalTime" name="finalTime"  placeholder="终止时间..." value="${searchUitl.finalTime}" type="date">
				<input type="button" class="btn btn-default" value="操作人员" id="btn3"><input id="adminAccount" name="adminAccount"  placeholder="管理员账号..." value="${searchUitl.adminAccount}">
				<input type="button" value="搜索" class="btn btn-primary" id="btn4" onClick="search()">
	</div>
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th>日志编号</th>
					<th>操作人员</th>
					<th>操作时间</th>
					<th>操作内容</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${logList}" var="log">
					<tr>
						<td>${log.logId}<input type="hidden" id="logId"
							value="${log.logId}"></td>
						<td>${log.adminName}</td>
						<td>${log.LOGTIME}</td>
						<td>${log.logInfo}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	<div>
		<input type="button" value="上一页" id="upPage" class="btn btn-primary"><label
			id="myPage" class="label label-primary">当前第${pageNum}页
			共${allNum}页</label><input type="button" value="下一页" id="nextPage"
			class="btn btn-primary"> <input type="text"
			class="input-group-addon" id="goPages"
			style="width: 100px; background-color: #FFFFFF; height: 35px;"
			onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
			onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"
			placeholder="请输入页码..."> <input type="button" value="跳转"
			class="btn btn-primary" id="turnPage"> <input type="hidden"
			value="${pageNum}" id="pageNum"><input type="hidden"
			value="${allNum}" id="allNum">
	</div>
	</form>
</body>
<script>
/* 上一页翻页*/
$("#upPage").click(function(){
		var nowPage=$("#pageNum").val();
		var startTime=$("#startTime").val();
		var finalTime=$("#finalTime").val();
		var adminAccount=$("#adminAccount").val();
	if(nowPage>1){
		nowPage--;
		window.location="<%=request.getContextPath()%>/logHandler/queryLogList.action?startTime="+startTime+"&finalTime="+finalTime+"&adminAccount="+adminAccount+"&pageNum="+nowPage;
	}else{
		window.location="<%=request.getContextPath()%>/logHandler/queryLogList.action?startTime="+startTime+"&finalTime="+finalTime+"&adminAccount="+adminAccount+"&pageNum="+nowPage;
	}
});
/*下一页翻页*/
$("#nextPage").click(function(){
	var nowPage=$("#pageNum").val();
	var allPage=$("#allNum").val();
	var startTime=$("#startTime").val();
	var finalTime=$("#finalTime").val();
	var adminAccount=$("#adminAccount").val();
	if(nowPage<allPage){
		nowPage++;
		window.location="<%=request.getContextPath()%>/logHandler/queryLogList.action?startTime="+startTime+"&finalTime="+finalTime+"&adminAccount="+adminAccount+"&pageNum="+nowPage;
	}else{
		window.location="<%=request.getContextPath()%>/logHandler/queryLogList.action?startTime="+startTime+"&finalTime="+finalTime+"&adminAccount="+adminAccount+"&pageNum="+nowPage;
	}
	
});
$("#turnPage").click(function(){
	var nowPage=$("#goPages").val();
	var allPage=$("#allNum").val();
	var startTime=$("#startTime").val();
	var finalTime=$("#finalTime").val();
	var adminAccount=$("#adminAccount").val();
	if(nowPage>allPage){
		alert("超出页码范围");
	}else{
		window.location="<%=request.getContextPath()%>/logHandler/queryLogList.action?startTime="+startTime+"&finalTime="+finalTime+"&adminAccount="+adminAccount+"&pageNum="+nowPage;
	}
});

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