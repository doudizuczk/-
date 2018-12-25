<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
  <meta charset="UTF-8">
  <title>鸟瞰图</title>
  <meta name="keywords" content="易景地图模拟导航,室内地图,三维地图引擎,三维地图制作,室内定位,易景地图,ESMap" />
  <meta name="description" content="易景地图第三人称导航,易景室内三维地图引擎提供地图浏览、缩放、旋转、图层显隐等基础功能，支持自定义室内地图显示风格及样式，可自动绘制楼层热力图、散点图等专题地图，快速进行空间大数据分析展示。支持跨楼层精准的点到点之间的最短、最优路径计算，支持对路径结果进行导航和动画,并提供丰富的地图主题资源供二次开发调用。" />
  <link href="css/common.css" rel="stylesheet">
</head>
<style type="text/css">
  #pannel {
    position: absolute;
    left: 2%;
    bottom: 10%;
    z-index: 999;
  }
	button,input[type="button"] {
			padding: 7px 11px;
			background-color: #fff;
			border: none;
			cursor: pointer;
			border-radius: 3px;
		}
  #description {
    position: absolute;
    left: 50%;
    top: 86px;
    padding: 10px 25px;
    background: rgba(255, 255, 255, 255);
    border-radius: 4px;
    margin-left: -140px;
    opacity: 0.7;
  }

  #tool-tip {
    position: absolute;
    color: #fff;
    font-size: 18px;
    height: 25px;
    line-height: 25px;
    padding: 0 5px;
    z-index: 2;
    pointer-events: none;
    background-color: rgba(0, 0, 0, 0.3);
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    display: none;
  }

  .container-fluid h1 {
    text-align: center;
  }
</style>

<body ms-controller="ctrl" class="ms-controller">
  <span id="tool-tip"> </span>
  <div id="map-container"></div>
  <div class="loading">
    <div class="lodingImg"></div>
  </div>
  <nav class="navbar navbar-inverse">
    <div class="container-fluid">
      <h1>智能停车场鸟瞰图</h1>
      <div>
      	<form>
      		<input type="text" name="carId">
      	</form>
      </div>
      <div class="tips-right">
          <span class="tip1"></span>
          <span class="tip2"></span>
      </div>
      <div css="tips-msg">
          <div class="msg msg1">
              <div class="erweima"></div>
              <p>手机扫一扫进入体验</p>
          </div>
          <div class="msg msg2">
              <h4>模拟导航</h4>
              <p>1. 用于车辆/行人导航，自动计算路程和所剩时间</p>
              <p>2. new esmap.ESNavigation({...}) 创建导航对象</p>

              <div style="display: none">易景地图第三人称导航,易景室内三维地图引擎提供地图浏览、缩放、旋转、图层显隐等基础功能，支持自定义室内地图显示风格及样式，可自动绘制楼层热力图、散点图等专题地图，快速进行空间大数据分析展示。支持跨楼层精准的点到点之间的最短、最优路径计算，支持对路径结果进行导航和动画,并提供丰富的地图主题资源供二次开发调用。</div>
          </div>
      </div>
    </div>
  </nav>
  <div id="description">
    暂无导航提示信息
  </div>
  <div id="pannel">
    <input type="button" class="btn btn-default btnclass" onclick="clearNavi()" value="清除" />
    <input type="button" class="btn btn-default btnclass" onclick="startNavi1()" value="开始第一人称导航" />
    <input type="button" class="btn btn-default btnclass" onclick="startNavi2()" value="开始第三人称导航" />
  </div>
  <div class="viewmode-floor btn-floor-vertical" data-toggle="buttons">
    <button id="btn2D" class="btn btn-default">2D</button>
    <button id="btn3D" class="btn btn-default">3D</button>
  </div>
  <script src="../lib/config.js"></script>
  <script src="../lib/esmap.min.js"></script>
  <script src="../lib/jquery-2.1.4.min.js"></script>
    <script src="../lib/jquery.qrcode.min.js"></script>
    <script src="../lib/tips_controls.js"></script>
  <script type="text/javascript">
    //定义全局map变量
    var map;
    var esmapID = "wangchi_smartparking";
    var styleid = getQueryString("styleid") || defaultOpt.themeID;
    // 导航对象
    var navi;
    // 点击计数
    var clickCount = 0;
    var floorControl;
    var mapCoord = null;
    var twoList=new Array();//存放禁用车位2did
	var threeList=new Array();//存放禁用车位3did
    window.onload = function () {
      if (navi) {
        navi.stop();
        navi = null;
      }
      // 楼层控制控件配置参数
      var ctlOpt = new esmap.ESControlOptions({
        position: esmap.ESControlPositon.RIGHT_TOP,
        imgURL: 'image/wedgets/'
        // allLayer: true
        // size:"normal"
      })
      // 放大、缩小控件配置
      var ctlOpt1 = new esmap.ESControlOptions({
        position: esmap.ESControlPositon.LEFT_TOP, // 位置 左上角
        // 位置x,y的偏移量
        offset: {
          x: 20,
          y: 70
        },
        imgURL: 'image/wedgets/'
      })
	var container = document.getElementById('map-container');
      map = new esmap.ESMap({
        container:container, // 渲染dom
        mapDataSrc: "../data", //地图数据位置
        mapThemeSrc: "../data/theme", //主题数据位置
        focusAlphaMode: false,
		themeID: styleid,				//样式ID
        token:"87713673"
      });

      map.openMapById(esmapID);
      map.showCompass = true; //显示指南针 
      //地图加载完成回调
      map.on('loadComplete', function () {
    	var forbidList=${forbidList};//取到的被禁用的列表
    	console.log(forbidList);
    	for(var i=0;i<forbidList.length;i++){
    		var a=forbidList[i];
    		console.log(a);
    		twoList.push(forbidList[i].twoId);
    		threeList.push(forbidList[i].threeId);
    	}
	  	map.changeModelColor({id:twoList,fnum:[1],color:'#FF0000'}) ;
	    map.change3dColor({id:threeList,fnum:[1],color:'#FF0000'});
        floorControl = new esmap.ESScrollFloorsControl(map, ctlOpt);
        var zoomControl = new esmap.ESZoomControl(map, ctlOpt1);
        createNavi();
        bingEvents();

      });
      //判断起点是否是同一处坐标
      var lastCoord = null;
      var curfnum = null;
      var h = 1;
      //点击地图事件。开始选点开始后，点击地图一次为起点，第二次点击为终点
      map.on('mapClickNode', function (event) {
    	//点击房间模型，获取该车位车辆详情
    	if(event.nodeType==esmap.ESNodeType.MODEL){
    		var flag=false;//标志符
    		var modelId;//获取到的房间id
    		for(var i=0;i<twoList.length;i++){
    			if(event.ID==twoList[i]){
    				modelId=event.ID;
    				flag=true;
    			}
    		}
    		if(flag){
    			$.ajax({
    				type:"post",
    				url:"<%=request.getContextPath()%>/carLocation/getDetil.action",
    				dataType:"json",
    				data:{"modelId":modelId},
    				success:function(data){
    					console.log(data);
    					//成功后进入到图片添加界面
    					if(data!=null&&data!=''){
    						window.location.href="<%=request.getContextPath()%>/backstage/detilpages.jsp?carId="+data[0].carId+"&ower"
    								+data[0].owerName+"&carLocationId"+data[0].parkId+"&area"+data[0].area+"&inTime"+data[0].iDate+"&picture"+data[0].file;
    					}
    				}
    			})
    		}else{
    			window.alert("此车为没有停车！")
    		}
    
    	}
        if (event.nodeType == 4) {
          curfnum = event.floor;
          h = 1;
          mapCoord = event.hitCoord;
        }
        if (event.nodeType == 5) {
          curfnum = event.FloorNum;
          h = event.data_.RoomHigh;
          mapCoord = event.hitCoord;
        }
      })
      //为模型填充div添加点击事件
      container.onclick = function () {
        var fnum = curfnum;
        fnum&&mapCoord&&show(fnum, mapCoord);
      };
      container.ontouchend = function(){
        var fnum = curfnum;
        fnum&&mapCoord&&show(fnum, mapCoord);
      }
      show = function (fnum, coord) {
        if (!navi) return;
        if (coord != null) {
          //第三次点击清除路径，重现设置起点起点
          if (clickCount == 2) {
            navi.clearAll();
            clickCount = 0;
            lastCoord = null;
          }

          //第一次点击添加起点
		 /*
          if (clickCount == 0) {
            lastCoord = coord;
            navi.setStartPoint({
              x: 13282689.21,
              y: 3011021.51,
              fnum: fnum,
              height: h,
              url: 'image/start.png',
              size: 64
            });
          } else if (clickCount == 1) { //添加终点并画路线
            //判断起点和终点是否相同
            if (lastCoord.x == coord.x) {
              alert("起点和终点不能相同!,请重新选点")
              return;
            }
            navi.setEndPoint({
              x: 13282710.21,
              y: 3011026.41,
              fnum: fnum,
              height: h,
              url: 'image/end.png',
              size: 64
            });
            
            // 画导航线
            navi.drawNaviLine();
          }*/
          clickCount++;
        }
        curfnum = null;
      };
    };

    function createNavi() {
      if (map.mapService.sourceData.navs.length == 0 || map.mapService.sourceData.navs[0].Nodes.length == 0) {
        console.warn("地图导航数据信息不存在！");
        return;
      }
      if (!navi) {
        //初始化导航对象
        navi = new esmap.ESNavigation({
          map: map,
          locationMarkerUrl: 'image/pointer.png',
          locationMarkerSize: 150,
          speed: 1,
          followAngle: true,
          followPosition: true,
          followGap: 3,
          tiltAngle: 30,
          audioPlay: true,
          // scaleLevel:0,
          mode: 1,
          offsetHeight: 1,
          // 设置导航线的样式
          lineStyle: {
            color: '#58a2e4',
            //设置线为导航线样式
            lineType: esmap.ESLineType.ESARROW,
            // lineType: esmap.ESLineType.FULL,
            lineWidth: 6,
            offsetHeight: 0.5,
            smooth: true,
            seeThrough: false,
            noAnimate: true
            // 设置边线的颜色   
            // godEdgeColor: '#920000',
            // 设置箭头颜色
            // godArrowColor: "#ffffff"
          },
          scaleAnimate: true,
          isMultiFloors: false
        });
      }

      navi.on("walking", function (data) {
        //显示导航展示信息
        showDis(data);
      })

      navi.on("complete", function () {
        console.log("停止");
        document.getElementById('description').innerText = "到达终点";
      })
    }

    function clearNavi() {
      if (navi)
        navi.clearAll();
      clickCount = 0;
      document.getElementById("description").innerText = "暂无导航提示信息";
    }

    function startNavi1() {
      navi.followAngle = true;
      navi.followPosition = true;
      navi.scaleAnimate = true;
      if(navi.isSimulating){
        navi.stop();
        navi.reset();
      }
      navi.simulate();
    }
    function startNavi2() {
      if(navi.isSimulating){
        navi.stop();
        navi.reset();
      }
      navi.followAngle = false;
      navi.followPosition = false;
      navi.scaleAnimate = false;
      navi.simulate();
    }
    
    //绑定事件
    function bingEvents() {
      // 2维显示事件
      document.getElementById('btn2D').onclick = function () {
        map.viewMode = esmap.ESViewMode.MODE_2D; // 2维模式
      };

      // // 3维显示事件
      document.getElementById('btn3D').onclick = function () {
        map.viewMode = esmap.ESViewMode.MODE_3D;; // 3维模式
      };
    }
    //显示路径数据
    function showDis(data) {
      //距终点的距离
      var distance = data.remain;
      //路线提示信息
      var info = navi.naviDescriptions[data.index];
      var f = info[0] + parseInt(data.distanceToNext) + info[2];
      //普通人每分钟走80米。
      var time = distance / 80;
      var m = parseInt(time);
      var s = Math.floor((time % 1) * 60);
      document.getElementById('description').innerHTML = '<p>距终点：' + distance.toFixed(1) + ' 米</p><p>大约需要：  ' + m + '  分钟   ' + s +
        '   秒</p><p>路线提示：' + f + ' </p>';
    };
  </script>
</body>

</html>