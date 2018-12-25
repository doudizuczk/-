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
<title>实名认证</title>
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
	<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.se2{
    width:100px;
    height:36px;
    position:absolute;
    top:200px;
    left:100px;
    z-index: 1;
    opacity: 0;
}
.se1{
    width:100px;
    height:36px;
    font-size:16px;
    color:#fff;
    background: #28abde;
    border-radius:5px;
    position:absolute;
    top:200px;
    left:100px;
}
.se1:hover{
    cursor: pointer;
}
</style>
</head>
<body class="pb12 fadeIn animated">
	<header
		class="ui-header clearfix w75 h8 f46 pl3 pr3 color8 bg-color-primary t-c">
		<div class="ui-header-l fl w5">
			<a href="<%=request.getContextPath()%>/frontstage/user_main.jsp" class="icon color8 iconfont icon-home_light"></a>
		</div>
		<div class="ui-header-c fl f30 w59">实名认证</div>
		<div class="ui-header-r fr w5">
			<i class="icon iconfont icon-phone"></i>
		</div>
	</header>
	<div>
		<input type="file" capture="camera" accept="image/*" id="IDcard" onchange="viewImage(this)" class="se2">
		<label for="IDcard" class="label">
        <input class="se1" type="button" value="上传身份证" />
		</label><input type="button" value="提交" onClick="up()" id="myBtn">
	</div>
	<div id="localImag">
		<img id="preview" width=-1 height=-1 style="diplay: none;" /> <input
			type="hidden" value="${sessionScope.loginOwer.owerId}" id="owerId"
			name="owerId">
	</div>
	
</body>
<script>
function viewImage(file) {
	  var preview = document.getElementById('preview');
	  if (file.files && file.files[0]) {
	    preview.style.display = "block";
	    preview.style.width = "300px";
	    preview.style.height = "150px";
	    preview.src = window.URL.createObjectURL(file.files[0]);
	  } else {
	    //ie下，使用滤镜
	    file.select();
	    var imgSrc = document.selection.createRange().text;
	    var localImagId = document.getElementById("localImag");
	    //必须设置初始大小 
	    localImagId.style.width = "250px";
	    localImagId.style.height = "200px";
	    try {
	      localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
	      locem("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
	    } catch (e) {
	      alert("您上传的图片格式不正确，请重新选择!");
	      return false;
	    }
	    preview.style.display = 'none';
	    document.selection.empty();
	  }
	  return true;
	};
	function up() {
		  var form = new FormData();
		  form.append('img', document.getElementById("IDcard").files[0]);
		  $.ajax({
		    type: 'post',
		    url: '<%=request.getContextPath()%>/owerHandler/upImage.action',
		    data:form,
		    contentType: false,
		    processData: false,
		    dataType: 'json',
		    success: function (data) {
		      if (data== '1') {
		    	  alert("认证成功");
		      }else{
		    	  alert("认证失败");
		      }
		    }
		    	  });
		      };	
</script>
</html>