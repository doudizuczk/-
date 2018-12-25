<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新车闸</title>
<style>
    * { margin: 0; padding: 0; color: #333; font-family: "Microsoft Yahei"; font-size: 14px; }

    #lightbox_mask {
      display: none;
      position: fixed;
      z-index: 999;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, .7);
    }
    #lightbox_popup {
      display: none;
      position: fixed;
      z-index: 1000;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
    }
    #lightbox_popup .pic-view {
      position: absolute;
      top: 5%;
      left: 5%;
      width: 90%;
      height: 90%;
      text-align: center;
    }
    #lightbox_popup .pic-view .pic {
      max-width: 100%;
      max-height: 100%;
      border: 5px solid #fff;
      border-radius: 3px;
    }
    #lightbox_popup .btn-view {}
    #lightbox_popup .btn-view .btn {
      position: absolute;
      width: 40px;
      height: 40px;
      line-height: 40px;
      text-align: center;
      font-size: 24px;
      text-decoration: none;
      border-radius: 32px;
      background-color: #000;
      opacity: .4;
      color: #fff;

      transition: all .3s;
    }
    #lightbox_popup .btn-view .btn:hover {
      opacity: 1;
      transform: scale(1.4);
    }
    #lightbox_popup .btn-view .btn-prev {
      left: 10px;
      top: 48%;
    }
    #lightbox_popup .btn-view .btn-next {
      right: 10px;
      top: 48%;
    }
    #lightbox_popup .btn-view .btn-close {
      right: 10px;
      top: 10px;
    }
    #lightbox_popup .caption-view {
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      height: 38px;
      background-color: rgba(0, 0, 0, .7);
      text-align: center;
    }
    #lightbox_popup .caption-view p {
      line-height: 38px;
      color: #fff;
    }

    .lightbox-pic {
      width: 200px;
    }
  </style>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<%-- <script src="<%=request.getContextPath()%>/js/lightbox.js"></script> --%>
</head>
<body>
<div style="position: absolute;width:1100px;height:600px;left:50%;top:50%;margin-left:-550px;margin-top:-300px;border:1px solid #00F;">
<div style="float: left;width: 45%;height: 100%;">
    <div>入口<br>
         <div>空闲车位：<a id="ParkNum"></a></div><br>
      车牌号 : <div id="carId"></div><br>
      类型 : <div id="carTypes"></div><br>    
    </div>
</div>
<div style="float: left;width: 10%;height: 100%;background:#F00; color:#FFF;"></div>
<div style="float: left;width: 45%;height: 100%;">
    <div>出口</div>
     <div>空闲车位：<a id="ParkNum2"></a></div><br>
      车牌号 : <div id="oCarId"></div><br>
      类型 : <div id="oCarTypes"></div><br>
      入库时间: <div id="oStarTime"></div><br>
     应缴金额: <div id="oCost"></div><br>
<!-- -----------弹窗------------------>
<!-- <div id="lightbox_1"> -->
<!--     <img src="img/dao.gif" alt="#" class="lightbox-pic" data-role="lightbox" data-source="images/dao.gif" data-group="group-1" data-id="pic_1_2" data-caption="pic_1_2">  -->
<!--   </div> -->
<!--   <div id="lightbox_mask"></div> -->
<!--   <div id="lightbox_popup"> -->
<!--     <div class="pic-view"> -->
<!--       <img src="img/dao.gif" alt="#" class="pic"> -->
<!--     </div> -->
<!--     <div class="btn-view"> -->
<!--       <a href="#" class="btn btn-prev">←</a> -->
<!--       <a href="#" class="btn btn-next">→</a> -->
<!--       <a href="#" class="btn btn-close">×</a> -->
<!--     </div> -->
<!--     <div class="caption-view"> -->
<!--       <p>pic1</p> -->
<!--     </div> -->
<!--   </div> -->
<!-- ----------------------------->
</div>
</div>
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
<script>
//     $(function () {
//       $('#lightbox_1').lightbox({
//         ifChange: true
//       });

//       $('#lightbox_2').lightbox();

//     });
  </script>
</html>