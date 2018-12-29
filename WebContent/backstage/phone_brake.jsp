
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>调用摄像头拍照</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>

<style>

</style>
</head>
<body>
    <div style="height: 50px;width: 80%;float: right;">
    <a></a>
    </div>
    <div style="height: 50px;width: 60%;margin: 0 auto;">
    <form>
    <table>
    <tr>
    	<td>
			<input type="file" capture="camera" accept="image/*" id="photo" name="photo" class="btn btn-default" style="width: 200px;">
    	</td>
    </tr>
    <tr>
    	<td>
    	   <div style="height: 50px;"></div>
    	</td>
    </tr>
    <tr>
    <td>
	<button  type="button" onclick="updatePhoto()" class="btn btn-primary">入场</button><button  type="button" onclick="updateGetOutPhoto()" class="btn btn-primary" style="float: right;">出库</button><br><br>
    </td>
    </tr>
<!--1     <tr> -->
<!--     <td> -->
<!-- 	<button  type="button" onclick="updateGetOutPhoto()" class="btn btn-primary">出库</button><br> -->
<!--     </td> -->
<!--     </tr> -->
    </table>
	</form>	
    </div>
</body>
<script>
//出场上传图片
function updateGetOutPhoto(){
	alert("出场上传图片");
	var Form = new FormData();
	Form.append('img', document.getElementById("photo").files[0]);
	$.ajax({
		url: "<%=request.getContextPath()%>/sse/updateGetOutPhoto.action",
		type:"POST",
		data:Form,
		contentType: false,
	    processData: false,
		dataType:"json",
		success : function(data){
			alert(data.message);
		}
	})
}
//上传图片
function updatePhoto(){
	alert("入场上传图片");
	var Form = new FormData();
	Form.append('img', document.getElementById("photo").files[0]);
	$.ajax({
		url: "<%=request.getContextPath()%>/sse/updatePhoto.action",
		type:"POST",
		data:Form,
		contentType: false,
	    processData: false,
		dataType:"json",
		success : function(data){
			alert(data.message);
		}
	})
}
//设置图片上传尺寸大小
function viewImage(file) {
	  var preview = document.getElementById('preview');
	  if (file.files && file.files[0]) {
	    //火狐下
	    preview.style.display = "block";
	    preview.style.width = "300px";
	    preview.style.height = "120px";
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
	}
</script>
</html>