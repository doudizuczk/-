<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" />
<meta name="renderer" content="webkit" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=0,uc-fitscreen=yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<title>慧停车</title>
<meta name="keywords" content="miniMobile的demo" />
<meta name="description" content="miniMobile是一个简单易用的移动框架！" />
<!-- ui css、js -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/userstyle/css/miniMobile.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/userstyle/js/zepto.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/userstyle/js/miniMobile.js"></script>
<!-- 字体图标 -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/userstyle/plugins/fonticon/iconfont.css" />
<!-- animate.css -->
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.css" />
<script>
	$(document).ready(function() {
		$("#owerName").attr("readonly", "readonly");
		$("#owerPhone").attr("readonly", "readonly");
	});
</script>
</head>
<body class="pb12 fadeIn animated">
	<header
		class="ui-header clearfix w75 h8 f46 pl3 pr3 color8 bg-color-primary t-c">
		<div class="ui-header-l fl w5">
			<a href="<%=request.getContextPath()%>/frontstage/user_main.jsp"
				class="icon color8 iconfont icon-home_light"></a>
		</div>
		<div class="ui-header-c fl f30 w59">我的资料
		<input type="button" value="返回" onClick="comeBack()" id="back" class="p2 mb4 btn radius5 btn-primary" style="height: 30px;float: right;" >
		</div>
<!-- 		<div class="ui-header-r fr w5"> -->
<!-- 			<i class="icon iconfont icon-phone"></i> -->
<!-- 		</div> -->
	</header>
	<br />
	<hr />
	<div>
		<table>
			<tbody>
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
					<td>昵称</td>
					<td><input type="text" value="${mess.owerName}" id="owerName"
						name="owerName"></td>
				</tr>
				<tr>
					<td>电话</td>
					<td>
					<input type="text" value="${mess.owerPhone}" id="owerPhone" name="owerPhone"> 
					<input type="hidden" value="${mess.owerId}" id="owerId" name="owerId"></td>
				</tr>
				<tr>
				<td>身份证号</td>
				<td><input value="${mess.owerIdcard}" id="owerIdcard" readonly="readonly"></td>
				</tr>
				<tr>
				<td>年龄</td>
				<td><input value="${mess.owerAge}" id="owerAge" readonly="readonly"></td>
				</tr>
				<tr>
					<td><input type="button" value="编辑资料" id="write" onClick="canwrite()" class="p2 mb4 btn radius5 btn-info"></td>
					<td><input type="button" value="修改密码" id="changePwd" onClick="changePwd(${sessionScope.loginOwer.owerId})" class="p2 mb4 btn radius5 btn-info"></td>
				</tr>
				<tr>
					<td><input type="button" value="保存" id="save" onClick="saveChange()" style="display: none" class="p2 mb4 btn radius5 btn-info"></td>
					<td><input type="button" value="取消" id="cancel" style="display: none" class="p2 mb4 btn radius5 btn-info"></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
<script>
function comeBack(){
	window.history.back();
}
	function canwrite() {
		$("#owerName").prop("readonly", false);
		$("#owerName").removeClass("readonly");
		$("#owerPhone").prop("readonly", false);
		$("#owerPhone").removeClass("readonly");
		$("#save").show();
		$("#cancel").show();
	};
	function isPoneAvailable(str) {
	            var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
	            if (!myreg.test(str)) {
	                return false;
	            } else {
	                return true;
	            }
		 };
function saveChange(){
		var owerName=$("#owerName").val();
		var owerPhone=$("#owerPhone").val();
		var owerId=$("#owerId").val();
		if(confirm("确定修改？")){
		if(isPoneAvailable(owerPhone)){
			if(owerName!=null&&owerName!=""){
			$.ajax({
				type:"post",
				url:"<%=request.getContextPath()%>/owerHandler/changeMeans.action",
				data:{"owerName":owerName,"owerPhone":owerPhone,"owerId":owerId},
				datatype:"json",
				success:function(data){
					if(data=="1"){
						alert("修改成功");
						window.location="<%=request.getContextPath()%>/owerHandler/beforeToMy.action";
					}else{
						alert("修改失败");
					}
				}
			});
			}else{
				alert("忘记输入您的昵称啦");
			}
		}else{
			alert("手机号有误");
				}
			}
		};
function changePwd(owerId){
		var owerPwd=prompt("请输入新的密码");
		if (owerPwd!=null && owerPwd!=""){
		    $.ajax({
		    	type:"post",
		    	url:"<%=request.getContextPath()%>/owerHandler/changePwd.action",
		    	data:{"owerId":owerId,"owerPwd":owerPwd},
		    	datatype:"json",
		    	success:function(data){
		    		if(data=="1"){
		    			alert("修改成功，下次登录生效哦");
		    		}else{
		    			alert("修改失败");
		    		}
		    	}
		    });
		}else{
			alert("请重新输入");
		}
};
</script>
</html>