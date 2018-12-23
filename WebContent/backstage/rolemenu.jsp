<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>权限配置页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script
	src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/zTreeStyle.css" />
<script src="<%=request.getContextPath()%>/js/jquery.ztree.core.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.ztree.excheck.js"></script>
<style>
#divtwo{
margin-left: 200px;
}
</style>
</head>
<body>
	<form id="treeForm">
		<div id="leftdiv" style="float: left">
			<input type="button" class="btn btn-default" value="查看角色" id="btn1">
			<select id="myselect" onchange="createTree()">
				<option value="">请选择</option>
				<c:forEach items="${roleList}" var="role">
					<option value="${role.roleId}">${role.roleName}</option>
				</c:forEach>
			</select>
		</div>
		<div id="divtwo" style="float:inherit">
			<input type="button" class="btn btn-default" value="已有权限"  id="btn2">
			<ul id="myTree" class="ztree"></ul>
		</div>
		<input type="hidden" name="menuId" id="menuId">
		<input type="button" value="提交"  id="confirm" onClick="doChange()">
	</form>
</body>
<script>
var path="<%=request.getContextPath()%>";
	function createTree() {
		var roleId = $("#myselect").val();
		$.ajax({
			type : "post",
			url : path + "/roleMenuHandler/queryAll.action",
			data : {"roleId" : roleId},
			dataType : "json",
			success : function(data) {
				var setting = {
					check : {
						enable : true
					},
					data : {
						key : {
							name : "menuName",
							checked : "checked"
						},
						simpleData : {
							enable : true,
							idKey : 'menuId',
							pIdKey : 'menuPid',
							rootPId : 0
						}
					}
				}
				treeObj = $.fn.zTree.init($("#myTree"), setting, data);
				treeObj.expandAll(true);
			},
			error : function() {

			}
		})
	};
	function doChange(){
		 var treeObj = $.fn.zTree.getZTreeObj("myTree");
		var nodes = treeObj.getCheckedNodes(true);
		var str="";
		console.log("长度="+nodes.length);
		for(var i=0;i<nodes.length;i++){
			str+=nodes[i].menuId+",";
			}
		$("#menuId").val(str);
		console.log("值是="+$("#menuId").val());
		$.ajax({
			type:"post",
			url:"<%=request.getContextPath() %>/roleMenuHandler/changeRoleMenu.action",
			data:{"menuId":$("#menuId").val(),"roleId":$("#myselect").val()},
			dataType:"json",
			success:function(data){
				if(data=='1'){
				window.alert("权限修改成功");
				}else {
					window.alert("权限修改失败");
				}
			}
		});
	}
</script>
</html>