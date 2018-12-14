<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>白名单管理</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
<link href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/brakestyle/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap-select.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap-select.js"></script>
</head>
<script>
<!-- 输入框 -->
$(document).ready(function() {
	$('.bs-select').selectpicker({});
});
</script>
<body>
<!-- 输入框 -->
  <div class="col-lg-6" style="width: 70%">
				<div class="input-group">
   <select class="selectpicker" data-live-search="true">
   <option>1111111111111111</option>
   <option>1</option>
   <option>1</option>
   </select>
					<input type="text" class="form-control">
					<span class="input-group-btn">
						<button class="btn btn-default" type="button">
							搜索
						</button>
					</span>
				</div><!-- /input-group -->
			</div>
<!-- 输入框END -->

       <table class="table table-hover">
          <tbody>
 				<tr>
 					<th>车牌号</th>
 					<th>用户</th>
 					<th>联系电话</th>
 					<th>车位区</th>
 					<th>车位编号</th>
 				</tr>
 					<c:forEach items="${whiteList}" var="wlist">
 				<tr>
 					<td>${wlist.carId}</td>
 					<td>${wlist.owerName}</td>
 					<td>${wlist.owerPhone}</td>
 					<td>${wlist.parkZone}</td>
 					<td>${wlist.parkId}</td> 
 				</tr>
 				</c:forEach>
 			</tbody>
 		</table>
</body>
<script type="text/javascript">
$(document).ready(function(){
	 
})
</script>
</html>