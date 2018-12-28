<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新车闸</title>
<meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/brakestyle/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/brakestyle/css/easy-responsive-tabs.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/brakestyle/css/tabs.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/brakestyle/css/templatemo-style.css">
        <script src="<%=request.getContextPath()%>/brakestyle/js/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
        
        <script src="<%=request.getContextPath()%>/brakestyle/js/vendor/jquery-1.11.2.min.js"></script>
        <script src="<%=request.getContextPath()%>/brakestyle/js/vendor/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/brakestyle/js/plugins.js"></script>
        <script src="<%=request.getContextPath()%>/brakestyle/js/main.js"></script>
        <script src="<%=request.getContextPath()%>/brakestyle/js/jquery.nicescroll.min.js"></script>
        <script src="<%=request.getContextPath()%>/brakestyle/js/easyResponsiveTabs.js"></script>
<style>
  
  </style>
<%-- <script src="<%=request.getContextPath()%>/js/lightbox.js"></script> --%>
</head>
<body>
 <div class="preloader">
      <div class="spinner">
        <div class="dot1"></div>
        <div class="dot2"></div>
      </div>
    </div>
    
    <section class="section-full image-bg">
      <div class="container">
<div style="width: 85%">
         <div style="float: right;"> 
        <div class="row" >
          <div class="col-md-12">
            <a href=""><div class="responsive-logo hidden-lg hidden-md hidden-sm"><img src="<%=request.getContextPath()%>/brakestyle/img/logo.png"></div></a>
            <!-- Begin .HorizontalTab -->
            <div class="VerticalTab VerticalTab_hash_scroll VerticalTab_6 tabs_ver_6">
              <div class="resp-tabs-container hor_1 tabs_scroll" style="width: 100%;height: 50%;">
                <div class="fc-tab-1" style="width: 100%">
                  <div class="home-container" style="width: 100%">
                    <div class="row">
                    <!--   入口显示区 -->
                      <div class="col-md-6" style="width: 43%;">
                        <div class="left-content">
                          <div class="left-line"></div>  
<!-- 时钟-->
      <div id="time" style="font-size:19px;color: red;"></div>
<!-- 时钟END-->
                          <h2>入口<em>显示</em></h2>
                          <p>空闲车位 <em id="ParkNum" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌号 :<em id="carId" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;车辆类型: <em id="carTypes" style="color: red;"></em>
                          </p>
                          <div class="primary-button">
                            <a href="#">进入识别</a>
                          </div>
                          <br>
                        </div>
                      </div>
                      <!-- 中间显示区 -->
                        <div class="col-md-6" style="width: 10px;height: 550px;background-color:none;">
                        <div class="left-content" style="width: 10px;height: 550px;background-color:none;">
                          <div class="left-line" style="width: 10px;height: 550px;background-color:none;"></div>      
                        </div>
                      </div>
                   
                   <!--   出口显示区 -->
                     <div class="col-md-6" style="height: 550px;">
                        <div class="left-content">
                          <div class="left-line"></div>  
<!-- 时钟-->
      <div id="time2" style="font-size:19px;color: red;"></div>
<!-- 时钟END-->
                          <h2>出口<em>显示</em></h2>
                          <p>空闲车位 <em id="ParkNum2" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌号 :<em id="oCarId" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;车辆类型: <em id="oCarTypes" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;入库时间: <em id="oStarTime" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;应缴金额: <em id="oCost" style="color: red;"></em>
                          </p>
                          <div class="primary-button" style="width: 390px;height: 300px">
                            <a href="#">出口识别</a>
                          </div>
                        </div>
                      </div>
                      
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
   </div>
      </div>
    </section>
<!-- -----------弹窗------------------>
<!-- ----------------------------->
<script type="text/javascript">
var source;
if (!!window.EventSource) {
       source = new EventSource('<%=request.getContextPath()%>/sse/goIn.action'); 
       s='';
       c='';
     //进场
       source.addEventListener('message', function(e) {
           var obj = eval('(' + e.data + ')');
           if(obj.gCanGo=="success"){
        	   alert("放行");
           }else{
        	   alert("禁止入内");
           }
           s=obj.gCarId+"<br/>";
           c=obj.gCarTypes;
           $("#carId").html(s);
           $("#carTypes").html(c);
           console.log("车辆进场");
           getParkNum();
       });
       //出场
       source.addEventListener('slide', function(e) {
          var obj = eval('(' + e.data + ')');
          var t=obj.oCarTypes;
          var d=obj.oCarId;
          var ct=obj.oCost;
          var st=obj.oStarTime;
              $("#oCarTypes").html(t);
              $("#oCarId").html(d);
              $("#oCost").html(ct);
              $("#oStarTime").html(st);
          if(obj.oCanGo=="success"){
       	      alert("放行");
          }else{
       	   alert(obj.oCanGo);
          }
          console.log("车辆出场");
          getParkNum();
       });
       //出场
       source.addEventListener('cango', function(e) {
          var obj = eval('(' + e.data + ')');
          if(obj.gOut=="success"){
       	      alert("放行");
          }
          console.log("车辆出场");
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
//----------时钟------------------------------------------------
document.getElementById('time').innerHTML = new Date().toLocaleString()
                + ' 星期' + '日一二三四五六'.charAt(new Date().getDay());
setInterval(
"document.getElementById('time').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",
1000);
document.getElementById('time2').innerHTML = new Date().toLocaleString()
                + ' 星期' + '日一二三四五六'.charAt(new Date().getDay());
setInterval(
"document.getElementById('time2').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",
1000);
$(document).ready(function(){
	getParkNum();
})
//----------------------------------------------------------
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
<script>
//     $(function () {
//       $('#lightbox_1').lightbox({
//         ifChange: true
//       });

//       $('#lightbox_2').lightbox();

//     });
  </script>
</html>