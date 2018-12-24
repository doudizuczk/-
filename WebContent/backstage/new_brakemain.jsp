<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新车闸</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
</head>
<body>
<div>
    <div>入口<br>
         <div>空闲车位：<a id="ParkNum"></a></div><br>
      车牌号 : <div id="carId"></div><br>
      类型 : <div id="carTypes"></div><br>    
    </div>
</div>
<div>
    <div>出口</div>
     <div>空闲车位：<a id="ParkNum2"></a></div><br>
      车牌号 : <div id=""></div><br>
      入库时间: <div id=""></div><br>
     应缴金额: <div id=""></div><br>
    
</div>
<script type="text/javascript">
var source;
if (!!window.EventSource) {
       source = new EventSource('<%=request.getContextPath()%>/goIn.action'); 
       s='';
       source.addEventListener('message', function(e) {
    	   if(e.data==null||e.data==""){
    		   s="未识别"+"<br/>"; 
    	   }else{
    	   var eva=typeof e.data=='string' ?JSON.parse(e.data):e.data; 
           s=eva.carId+"<br/>";
    	   }
           $("#carId").html(s);
           console.log(eva);
          
       });
       source.addEventListener('slide', function(e) {
    	   if(e.data==null||e.data==""){
    		   s="未识别"+"<br/>" 
    	   }else{
           s=e.data+"<br/>"
    	   }
           $("#carTypes").html(s);
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
</body>
<script>
$(document).ready(function(){
	getParkNum();
})
//入口屏幕清除信息
function clearIn(){
	setInterval(bb,10000);
	function bb(){
		$("#carIds").text("");
		$("#parkIds").text("");
		$("#carTypes").text("");
	}
};
//出口屏幕清除信息
function clearOut(){
	setInterval(aa,10000);
	function aa(){
	$("#carIdsOut").text("");
	$("#parkIdsOut").text("");
	$("#carTypesOut").text("");
	$("#dockSTimeOut").text("");
	$("#money").text("");
	}
};
//实时获取空闲车位数
function getParkNum(){
	$.ajax({
	    url: "<%=request.getContextPath()%>/carBrakeHander/getParkNum.action",
		type:"POST",
		data:{},
		dataType:"json",
		success : function(data){
			$("#ParkNum").text(data);
			$("#ParkNum2").text(data);
		}
});
}
</script>
</html>