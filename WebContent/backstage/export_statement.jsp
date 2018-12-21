<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script type="text/javascript">
function exportOrder(){
	if($("#createTime").val()==null || $("#createTime").val()=="" || $("#shift").val()==0){
		alert("请选择完整的日期和班次！"+$("#createTime").val()+","+$("#shift").val());
		return;
	}
	window.location.href="<%=request.getContextPath()%>/orderHandler/exportOrderExcel.action?shift="+$("#shift").val()+"&createTime="+$("#createTime").val();
}
</script>
<title>Insert title here</title>
</head>
<body>
<h2>导出结算单</h2>
请选择日期：<input type="date" id="createTime" name="createTime">
请选择班次：<select id="shift" name="shift">
			<option value="0">请选择</option>
			<option value="1">早班 08:00-16:00</option>
			<option value="2">中班 16:00-24:00</option>
			<option value="3">晚班 00:00-08:00</option>
		</select>
		<button onclick="exportOrder()">导出</button>
</body>
</html>