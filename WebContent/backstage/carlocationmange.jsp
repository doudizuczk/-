<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>

<head>
<meta charset="utf-8">

<title>车位配置</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>

<link href="<%=request.getContextPath()%>/css/bootstrap-select.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap-select.js"></script>


</head>

<body>
	<form action="">
		<label>车位号</label>
		 <input type="text" name="carLocationId" id="carLocationId">
		 <label>状态</label>
		 <input type="text" name="stateName" id="stateName">
		 <label>所属区域</label>
		 <input type="text" name="area" id="area">
		 <input type="button" value="查询" onclick="queryByCondition()">	
	</form>
	<table class="table table-hover">
	  <thead>
	    <tr>
	      <th>车位号</th>
	      <th>车位状态</th>
	      <th>所属区域</th>
	      <th>操作</th>
	    </tr>
	  </thead>
	  <tbody id="aaa">
	  </tbody>
	</table>
	<button type="button" class="btn btn-default" onclick="beforePage()">上一页</button>
	<p></p>
	<button type="button" class="btn btn-default" onclick="nextPage()">下一页</button>
</body>	
	<script>
		//获取到所有列表
		var nowPage=1;
		var allPage=1;
		$(document).ready(function(){
			var allUrl="<%=request.getContextPath()%>/carLocation/queryAll.action";//查询全部列表的地址
			$.ajax({
				type:"post",
				url:allUrl,
				dataType:"json",
				success:function(data){
					var msg = "";
					for(var i=0;i<data.list.length;i++){
						var carLocation = data.list[i];
						msg+="<tr><td>"+carLocation.carLocationId+"</td><td>"+carLocation.stateName+"</td> <td>"+carLocation.area+"</td><td><button onclick='updateMsg("+carLocation.carLocationId+")'>禁用</button><button onclick='delMsg("+carLocation.carLocationId+")'>启用</button></td></tr>";
					}
					$("#aaa").html(msg);
					nowPage = data.pageNum;
					allPage = data.pages;
				}
			})	
		})
		//禁用操作
		function updateMsg(carLocationId){
			var url="<%=request.getContextPath()%>/carLocation/forbidden.action";
			$.ajax({
				type : "post",
				url :url,
				dataType : "json",
				data : {
					"carLocationId" : carLocationId
				},
				success : function(data) {
					if (data=="0") {
						window.alert("禁用失败！")	
					} else {
						window.alert("禁用成功！")
						window.location.href="<%=request.getContextPath()%>/carLocation/tojsp.action";
					}

				}

			})
		}
		//启用操作
		function delMsg(carLocationId){
			var url="<%=request.getContextPath()%>/carLocation/permission.action";
			$.ajax({
				type : "post",
				url :url,
				dataType : "json",
				data : {
					"carLocationId" : carLocationId
				},
				success : function(data) {
					if (data=="0") {
						window.alert("启用失败！")	
					} else {
						window.alert("启用成功！")
						window.location.href="<%=request.getContextPath()%>/carLocation/tojsp.action";
					}

				}

			})
		}
		//翻页上一页请求操作
		function beforePage(){
			if(nowPage>1){
				nowPage--;
			}
			$.ajax({
				type:"post",
				url:"<%=request.getContextPath()%>/carLocation/queryPage.action",
				dataType:"json",
				data:{
					"allPage":allPage,"nowPage":nowPage
				},
				success:function(data){
					console.log(data);
					var msg = "";
					for(var i=0;i<data.length;i++){
						var carLocation = data[i];
						msg+="<tr><td>"+carLocation.carLocationId+"</td><td>"+carLocation.stateName+"</td> <td>"+carLocation.area+"</td><td><button onclick='updateMsg("+carLocation.carLocationId+")'>禁用</button><button onclick='delMsg("+carLocation.carLocationId+")'>启用</button></td></tr>";
					}
					$("#aaa").html(msg);
				}
				
			})
		}
		//翻页下一页请求操作
		function nextPage(){
			if(nowPage<allPage){
				nowPage++;
			}
			$.ajax({
				type:"post",
				url:"<%=request.getContextPath()%>/carLocation/queryPage.action",
				dataType:"json",
				data:{
					"allPage":allPage,"nowPage":nowPage
				},
				success:function(data){
					var msg = "";
					for(var i=0;i<data.length;i++){
						var carLocation = data[i];
						msg+="<tr><td>"+carLocation.carLocationId+"</td><td>"+carLocation.stateName+"</td> <td>"+carLocation.area+"</td><td><button onclick='updateMsg("+carLocation.carLocationId+")'>禁用</button><button onclick='delMsg("+carLocation.carLocationId+")'>启用</button></td></tr>";
					}
					$("#aaa").html(msg);
				}
				
			})
		}
		//模糊查询
		function queryByCondition(){
			var id=$("#carLocationId").val();
			var sname=$("#stateName").val();
			var area=$("#area").val();
			$.ajax({
				type:"post",
				url:"<%=request.getContextPath()%>/carLocation/queryAll.action",
				data:{
					"carLocationId":id,"stateName":sname,"area":area
				},
				success:function(data){
					console.log(data);
					var msg = "";
					for(var i=0;i<data.list.length;i++){
						var carLocation = data.list[i];
						msg+="<tr><td>"+carLocation.carLocationId+"</td><td>"+carLocation.stateName+"</td> <td>"+carLocation.area+"</td><td><button onclick='updateMsg("+carLocation.carLocationId+")'>禁用</button><button onclick='delMsg("+carLocation.carLocationId+")'>启用</button></td></tr>";
					}
					$("#aaa").html(msg);
				}
			})
		}
		
	</script>

</html>

