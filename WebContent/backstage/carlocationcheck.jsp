<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>

<head>
<meta charset="utf-8">

<title>车位配置</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">


</head>

<body>
	<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">停车场实时状态查看</a>
    </div>
    <div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="#">数据显示</a></li>
            <li><a href="#">折线图统计</a></li>
        </ul>
    </div>
    </div>
</nav>
	<table class="table table-hover">
	  <thead>
	    <tr>
	      <th>统计范围</th>
	      <th>总车位</th>
	      <th>空闲车位</th>
	      <th>禁用车位</th>
	    </tr>
	  </thead>
	  <tbody id="aaa">
	  	 <tr>
	      <th>总区</th>
	      <th>总车位</th>
	      <th>空闲车位</th>
	      <th>禁用车位</th>
	    </tr>
	    <tr>
	      <th>A区</th>
	      <th>总车位</th>
	      <th>空闲车位</th>
	      <th>禁用车位</th>
	    </tr>
	    <tr>
	      <th>B区</th>
	      <th>总车位</th>
	      <th>空闲车位</th>
	      <th>禁用车位</th>
	    </tr>
	    <tr>
	      <th>C区</th>
	      <th>总车位</th>
	      <th>空闲车位</th>
	      <th>禁用车位</th>
	    </tr>
	  </tbody>
	</table>
</body>	
	<script>
	</script>

</html>

