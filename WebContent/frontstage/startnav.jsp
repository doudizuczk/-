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
      <div css="tips-msg">
          <div class="msg msg2">
              <h4>模拟导航</h4>
          </div>
      </div>
    </div>
  </nav>
  <div id="description">
    暂无导航提示信息
  </div>
  <div id="pannel">
  	<input type="button" class="btn btn-default btnclass" onclick="back()" value="返回" />
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
		themeID: styleid,//样式ID
        token:"87713673"
      });

      map.openMapById(esmapID);
      map.showCompass = true; //显示指南针 
      //地图加载完成回调
      map.on('loadComplete', function () {
    	//将查到的车位变为橙色 
  		var modelId=${param.twoId};//查到的车位模型id
	  	map.changeModelColor({id:modelId,fnum:[1],color:'#FF0000'}) ;
	    map.change3dColor({id:modelId,fnum:[1],color:'#FF0000'});
        floorControl = new esmap.ESScrollFloorsControl(map, ctlOpt);
        var zoomControl = new esmap.ESZoomControl(map, ctlOpt1);
        createNavi();
        bingEvents();
        navi.setStartPoint({
            x: 13282689.21,
            y: 3011021.51,
            height: h,
            fnum: 1,
            url: 'image/start.png',
            size: 64
          });
    	 //终点坐标 
          navi.setEndPoint({
            x: ${param.xCoords},
            y: ${param.yCoords},
            height: h,
            fnum: 1,
            url: 'image/end.png',
            size: 64
          });
          // 画导航线
          navi.drawNaviLine();
      });
      //判断起点是否是同一处坐标
      var lastCoord = null;
      var curfnum = null;
      var h = 1;
   
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
    	  //起点坐标 
            navi.setStartPoint({
              x: 13282689.21,
              y: 3011021.51,
              fnum: fnum,
              height: h,
              url: 'image/start.png',
              size: 64
            });
      	 //终点坐标 
            navi.setEndPoint({
              x: ${param.xCoords},
              y: ${param.yCoords},
              fnum: fnum,
              height: h,
              url: 'image/end.png',
              size: 64
            });
            // 画导航线
            navi.drawNaviLine();
          }
        
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
    
    function back(){
    	window.history.back(-1); 
    }
  </script>
</body>

</html>