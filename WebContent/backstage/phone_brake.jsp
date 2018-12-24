
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<title>html5调用摄像头拍照</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
</head>
<body>
    <div style="height: 20%;width: 100%;margin: 0 auto;">
    <a></a>
    </div>
    <div style="height: 50px;width: 60%;margin: 0 auto;">
    <form>
	<input type="file" capture="camera" accept="image/*" id="photo" name="photo" class="form-control" onchange="viewImage(this)">
	<button  type="button" onclick="updatePhoto()" class="btn btn-info btn-small">进入</button>
	</form>	
    </div>
</body>
<script>
//上传图片
function updatePhoto(){
	alert("上传图片");
	var Form = new FormData();
	Form.append('img', document.getElementById("photo").files[0]);
	$.ajax({
		url: "<%=request.getContextPath()%>/updatePhoto.action",
		type:"POST",
		data:Form,
		contentType: false,
	    processData: false,
		dataType:"json",
		success : function(data){
			alert("0000000000");
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