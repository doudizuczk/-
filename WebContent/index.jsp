<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="UTF-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>sse 测试</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
</head>
<body>
<input type="button" value="传递1" onclick="closee()">
<div id="msg_from_server"></div>
<script type="text/javascript">
var source;
if (!!window.EventSource) {
       source = new EventSource('push.action'); //为http://localhost:8080/testSpringMVC/push
       s='';
       source.addEventListener('message', function(e) {
          
           s=e.data+"<br/>"
           $("#msg_from_server").html(s);

       });

       source.addEventListener('open', function(e) {
            console.log("连接打开.");
       }, false);

       source.addEventListener('error', function(e) {
            if (e.readyState == EventSource.CLOSED) {
               console.log("连接关闭");
            } else {
                console.log(e.readyState);    
            }
       }, false);
    } else {
            console.log("没有sse");
    }
</script>
<script>
function closee(){
	alert("断开连接2");
	$.ajax({
	    url: "<%=request.getContextPath()%>/push2.action",
		type:"POST",
		data:{"name":"name"},
		dataType:"json",
		success : function(data){
			if(data==1){
			alert("成功");
			}
		}
});	
}
</script>
</body>
</html>