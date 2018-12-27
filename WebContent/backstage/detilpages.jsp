<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>反向寻车</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<div>
		<table class="table table-hover">
			<tbody>
				<tr>
					<td>车牌号</td>
					<td>${param.carId}<input type="hidden" value="${param.carId}" id="chepai"></td>
				</tr>
				<tr>
					<td>户主</td>
					<td>${param.ower}</td>
				</tr>
				<tr>
					<td>车位ID</td>
					<td>${param.carLocationId}</td>
				</tr>
				<tr>
					<td>所属区域</td>
					<td>${param.area}</td>
				</tr>
				<tr>
					<td>入场时间</td>
					<td>${param.inTime}</td>
				</tr>
				<tr>
					<td>图片详情</td>
					<td>
					<c:if test="${ param.picture != 'null'}">
						<img alt="sdfd" src="${pageContext.request.contextPath}/images/${param.picture}" >
					</c:if>
					<c:if test="${ param.picture == 'null'}">
						<input type="file" value="上传" id="f">
					</c:if>
					</td>
				</tr>
			</tbody>
		</table>
		<input type="button" value="返回" onclick="back()">
		<input type="button" value="提交" onclick="sendImg()">
	</div>
</body>
<script>
	//提交上传图片
	function sendImg(){
		var form = new FormData();
		  form.append('img', document.getElementById("f").files[0]);
		  form.append('carId',$("#chepai").val());
		  $.ajax({
		    type: 'post',
		    url:"<%=request.getContextPath()%>/carLocation/sendImg.action",
		    data: form,
		    contentType: false,
		    processData: false,
		    dataType: 'json',
		    success: function (data) {
		    	if(data!=0&&data!=null&&data!='0'){
		    		window.alert("上传成功！");
		    	}else{
		    		widow.alert("上传失败，请重试！");
		    	}
		    }
		  })
	}
	
	//返回鸟瞰图页面
	function back(){
		window.location.href="<%=request.getContextPath()%>/carLocation/queryForbid.action";
	}

</script>

</html>