<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
		</head>
<body>
 <button onclick="hump()" value="taozhuan"></button>

</body>
<script >
function hump(){
	window.location.href="<%=request.getContextPath()%>/carLocation/queryForbid.action";
}



</script>
</html>