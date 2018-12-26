<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>车主端登录页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script
	src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/userstyle/css/styles.css">
<script>
$().ready(function(){
	$("#registerBtn").attr("disabled",true);
	$("#loginForm").validate({
   	 rules: {
   		owerAccount: {
   	        required: true,
   	      },
   	   owerPwd: {
   	        required: true,
   	      },
   	    },
   	    
   	  messages: {
   		owerAccount: {
   	        required: "用户名不能为空",
   	      },
   	   owerPwd: {
   	        required: "密码不能为空",
   	      },
   	     },
   	  submitHandler: function(form) { owerLogin(); }
   });
   
$("#registerForm").validate({
	   	 rules: {
	   		owerAccount: {
	   	        required: true,
	   	      },
	   	   owerPwd: {
	   	        required: true,
	   	      },
	   	   checkPwd:{
	   		required: true,
	   		equalTo:"#owerPwds"
	   	   },
	   	owerPhone:{
	   		required: true,
	   		maxlength:11,
            minlength:11,
            isMobile:true
	   	},
	   	checkCode:{
	   		required: true,
	   	}
	   	    },
	   	    
	   	  messages: {
	   		owerAccount: {
	   	        required: "用户名不能为空",
	   	      },
	   	   owerPwd: {
	   	        required: "密码不能为空",
	   	      },
	   	   checkPwd:{
	   		required:"请再次输入密码",
	   		equalTo:"两次密码不一致"
	   	   } ,
	   	owerPhone:{
	   		required:"请输入您的手机号",
	   		maxlength:"您输入的手机号长度有误",
	   		minlength:"您输入的手机号长度有误",
	   		isMobile:"请正确填写手机号码"
	   	},
	   	checkCode:{
	   		required:"请填写您收到的验证码",
	   	}
	   	     },
	   	  submitHandler: function(form) { owerRegister(); }
	   });
});
function owerLogin(){
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/owerHandler/owerLogin.action",
		data:JSON.stringify($("#loginForm").serializeJSON()),
		contentType:"application/json",
		dataType:"json",
		success:function(data){
			if(data=="1"){
				window.alert("欢迎回来");
				window.location="<%=request.getContextPath()%>/owerHandler/getOwerCarMess.action";
			}else {
				window.alert("用户名密码出错!");
			}
		},
		error:function(){
			window.alert("登录出错");
		}
	})
};
function owerRegister(){
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/owerHandler/owerRegister.action",
		data:JSON.stringify($("#registerForm").serializeJSON()),
		contentType:"application/json",
		dataType:"json",
		success:function(data){
			if(data=="1"){
				window.alert("注册成功");
				window.location="<%=request.getContextPath()%>/frontstage/user_login.jsp";
			}else if(data=="0") {
				window.alert("注册失败!");
			}else{
				alert("验证码出错");
			}
		},
		error:function(){
			window.alert("注册出错");
		}
	})
};
jQuery.validator.addMethod("isMobile", function(value, element) {  
	 var length = value.length;  
	 var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;  
	 return this.optional(element) || (length == 11 && mobile.test(value));  
	}, "请正确填写手机号码");
	
function checkAccount(){
	console.log("进入失去焦点方法");
	var userAccount=$("#owerAccount").val();
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/owerHandler/checkOwerAccount.action",
		data:{"owerAccount":userAccount},
		dataType:"json",
		success:function(data){
			if(data=="1"){
				$("#errorMess").text("可以使用该帐号");
				$("#registerBtn").attr('disabled',false);
			}else{
				$("#errorMess").text("该帐号已存在");
			}
		},
		error:function(){
			
		}
	});
}
</script>
<style>
.error {
	color: blue;
}
</style>
</head>
<body>
	<div class="jq22-container" style="padding-top: 0px">
		<div class="login-wrap">
			<div class="login-html">
				<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label
					for="tab-1" class="tab">登陆</label> <input id="tab-2" type="radio"
					name="tab" class="sign-up"><label for="tab-2" class="tab">注册</label>
				<div class="login-form">
					<!-- 登陆 -->
					<form id="loginForm">
						<div class="sign-in-htm">
							<div class="group">
								<label for="user" class="label">用户名</label> <input id="user"
									type="text" class="input" name="owerAccount">
							</div>
							<div class="group">
								<label for="pass" class="label">密码</label> <input id="pass"
									type="password" class="input" data-type="password"
									name="owerPwd">
							</div>
							<div class="group">
								<input id="check" type="checkbox" class="check" checked>
								<label for="check"><span class="icon"></span> 记住密码</label>
							</div>
							<div class="group">
								<input type="submit" class="button" value="登陆">
							</div>
							<div class="hr"></div>
							<div class="foot-lnk">
								<a href="#forgot">忘记密码?</a>
							</div>
						</div>
					</form>
					<!-- 登陆 END-->
					<!-- 注册 -->
					<div class="sign-up-htm">
						<form id="registerForm">
							<div class="group">
								<label for="owerAccount" class="label">用户名</label> <input
									id="owerAccount" type="text" class="input" name="owerAccount"
									onblur="checkAccount()"><span id="errorMess"></span>
							</div>
							<div class="group">
								<label for="owerPwd" class="label">密码</label> <input
									id="owerPwds" type="password" class="input"
									data-type="password" name="owerPwd">
							</div>
							<div class="group">
								<label for="confirm" class="label">确认密码</label> <input
									id="confirm" type="password" class="input" data-type="password"
									name="checkPwd">
							</div>
							<div class="group">
								<label for="owerPhone" class="label">手机号</label> <input
									id="owerPhone" type="text" class="input" name="owerPhone"><input
									type="button" value="获取验证码" id="getCode" onClick="getDuanxin()">
							</div>
							<div class="group">
								<label for="checkCode" class="label">验证码</label> <input id="checkCode" type="text" class="input" name="checkCode" placeholder="请输入您收到的短信验证码...">
							</div>
							<div class="group">
								<input type="submit" class="button" value="注册" id="registerBtn">
							</div>
						</form>
						<div class="hr"></div>
						<div class="foot-lnk">
							<label for="tab-1"></a>
						</div>
					</div>
					<!-- 注册 END-->
				</div>
			</div>
		</div>
	</div>
</body>
<script>
/* function setBtn(){
	setInterval(aa,120000);
	function aa(){
		$("#getCode").remove("disabled");
	}
} */
/*获取短信验证吗*/
function getDuanxin(){
	var owerPhone=$("#owerPhone").val();
	if(owerPhone!=null&&owerPhone!=""){
		timer(120);
		if(isPoneAvailable(owerPhone)){
			$.ajax({
				type:"post",
				url:"<%=request.getContextPath()%>/CheckCodeHandler/getMessageCode.action",
							data : {
								"owerPhone" : owerPhone
							},
							datatype : "json",
							success : function(data) {
								if (data == "1") {
								
								} else {

								}
							}
						});
			} else {
				alert("请输入正确的手机号");
			}
		} else {
			alert("请输入正确的手机号");
		}
	};
	/*手机格式验证*/
	function isPoneAvailable(str) {
		var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
		if (!myreg.test(str)) {
			return false;
		} else {
			return true;
		}
	};
	/*定时*/
	function timer(time) {
		var btn = $("#getCode");
		btn.attr("disabled", true); //按钮禁止点击
		btn.val(time <= 0 ? "重新发送" : ("" + (time) + "秒后可发送"));
		var hander = setInterval(function() {
			if (time <= 0) {
				clearInterval(hander); //清除倒计时
				btn.val("重新发送");
				btn.attr("disabled", false);
				return false;
			} else {
				btn.val("" + (time--) + "秒后可发送");
			}
		}, 1000);
	}
</script>
</html>