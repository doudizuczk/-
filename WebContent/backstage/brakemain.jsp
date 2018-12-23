<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- 闸道主页面 -->
<head>
<!-- <meta http-equiv="refresh" content="20"> 定时刷新页面-->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>闸道</title>
 <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/brakestyle/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/brakestyle/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/brakestyle/css/easy-responsive-tabs.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/brakestyle/css/tabs.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/brakestyle/css/templatemo-style.css">
        <script src="<%=request.getContextPath()%>/brakestyle/js/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
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
        <div class="row">
          <div class="col-md-12">
            <a href=""><div class="responsive-logo hidden-lg hidden-md hidden-sm"><img src="<%=request.getContextPath()%>/brakestyle/img/logo.png"></div></a>
            <!-- Begin .HorizontalTab -->
            <div class="VerticalTab VerticalTab_hash_scroll VerticalTab_6 tabs_ver_6">
              <ul class="resp-tabs-list hor_1">
                <a href=""><div class="logo"><img src="<%=request.getContextPath()%>/brakestyle/img/logo.png"></div></a>
                <li class="" data-tab-name=""><input type="text" id="carId"></li>
                <li class="" data-tab-name=""><span class="tabs-text" onclick="goIn()">进</span></li>
                <li class="" data-tab-name=""><span class="tabs-text" onclick="getOut()">出</span></li>             
                <li class="" data-tab-name=""><span class="tabs-text" onclick="updatePhoto()">备用
                </span></li>
              </ul>
              <div class="resp-tabs-container hor_1 tabs_scroll">
                <div class="fc-tab-1">
                  <div class="home-container">
                    <div class="row">
                    <!--   入口显示区 -->
                      <div class="col-md-6" style="width: 43%">
                        <div class="left-content">
                          <div class="left-line"></div>  
<!-- 时钟-->
      <div id="time" style="font-size:19px;color: red;"></div>
<!-- 时钟END-->
                          <h2>入口<em>显示</em></h2>
                          <p>空闲车位 <em id="ParkNum" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌号 :<em id="carIds" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;车位编号 :<em id="parkIds" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;车辆类型: <em id="carTypes" style="color: red;"></em>
                          </p>
                          <div class="primary-button">
                            <a href="#">进入识别</a>
                          </div>
                          <br>
                          <div>
                          <form>
                           <input type="file" name="file" id="file">
                           <br>
                           <input type="button" value="upload" onclick=""/>
                            </form>
                          </div>
                        </div>
                      </div>
                      <!-- 中间显示区 -->
                        <div class="col-md-6" style="width: 10px;height: 550px;background-color:none;">
                        <div class="left-content" style="width: 10px;height: 550px;background-color:none;">
                          <div class="left-line" style="width: 10px;height: 550px;background-color:none;"></div>      
                        </div>
                      </div>
                   
                   <!--   出口显示区 -->
                     <div class="col-md-6">
                        <div class="left-content">
                          <div class="left-line"></div>  
<!-- 时钟-->
      <div id="time2" style="font-size:19px;color: red;"></div>
<!-- 时钟END-->
                          <h2>出口<em>显示</em></h2>
                          <p>空闲车位 <em id="ParkNum2" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车牌号 :<em id="carIdsOut" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;车位编号 :<em id="parkIdsOut" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;车辆类型: <em id="carTypesOut" style="color: red;"></em>
                          <br>&nbsp;&nbsp;&nbsp;&nbsp;入库时间: <em id="dockSTimeOut" style="color: red;"></em>
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
    </section>
        <script src="<%=request.getContextPath()%>/brakestyle/js/vendor/jquery-1.11.2.min.js"></script>
        <script src="<%=request.getContextPath()%>/brakestyle/js/vendor/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/brakestyle/js/plugins.js"></script>
        <script src="<%=request.getContextPath()%>/brakestyle/js/main.js"></script>
        <script src="<%=request.getContextPath()%>/brakestyle/js/jquery.nicescroll.min.js"></script>
        <script src="<%=request.getContextPath()%>/brakestyle/js/easyResponsiveTabs.js"></script>
    </body>
<script>
document.getElementById('time').innerHTML = new Date().toLocaleString()
                + ' 星期' + '日一二三四五六'.charAt(new Date().getDay());
setInterval(
"document.getElementById('time').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",
1000);
</script>
<script>
document.getElementById('time2').innerHTML = new Date().toLocaleString()
                + ' 星期' + '日一二三四五六'.charAt(new Date().getDay());
setInterval(
"document.getElementById('time2').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",
1000);
</script>
<script>
$(document).ready(function(){
	getParkNum();
})
//上传图片
function updatePhoto(){
	alert("上传图片");
	var Form = new FormData();
	Form.append('img', document.getElementById("file").files[0]);
	$.ajax({
		url: "<%=request.getContextPath()%>/carBrakeHander/updatePhoto.action",
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
function goIn(){
	$.ajax({
	    url: "<%=request.getContextPath()%>/carBrakeHander/carGoIn.action",
		type:"POST",
		data:{"carId":$("#carId").val()},
		dataType:"json",
		success : function(data){
			if(data.result==true){
				$("#carIds").text(data.carId);
				$("#parkIds").text(data.parkId);
				$("#carTypes").text(data.carType);
				getParkNum();
				clearIn();
			}else{
				$("#carIds").text(data.carId);
				$("#parkIds").text("空");
				$("#carTypes").text(data.carType);
				getParkNum();
				clearIn();
			}
				
		}
});
}
function getOut(){
	$.ajax({
	    url: "<%=request.getContextPath()%>/carBrakeHander/carGetOut.action",
		type:"POST",
		data:{"carId":$("#carId").val()},
		dataType:"json",
		success : function(data){
			if(data.result==true){
				$("#carIdsOut").text(data.carId);
				$("#parkIdsOut").text(data.parkId);
				$("#carTypesOut").text(data.carType);
				$("#dockSTimeOut").text(data.dockSTime);
				getParkNum();
				clearOut();
			}
			
		}
});
	
}
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
	}
};
// 实时获取空闲车位数
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