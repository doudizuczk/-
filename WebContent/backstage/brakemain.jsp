<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- 闸道主页面 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>闸道</title>
 <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
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
                <li class="tabs-1" data-tab-name="profile"><span class="tabs-text">操作1</span></li>
                <li class="tabs-2" data-tab-name="resume"><span class="tabs-text">操作2</span></li>
                <li class="tabs-3" data-tab-name="portfolio"><span class="tabs-text">操作3</span></li>             
                <li class="tabs-4" data-tab-name="contact"><span class="tabs-text">操作4</span></li>
              </ul>
              <div class="resp-tabs-container hor_1 tabs_scroll">
                <div class="fc-tab-1">
                  <div class="home-container">
                    <div class="row">
                    <!--   入口显示区 -->
                      <div class="col-md-6" style="width: 43%">
                        <div class="left-content">
                          <div class="left-line"></div>  
                          <h2>入口<em>显示</em></h2>
                          <p>We produce high quality <em>HTML</em> Website Templates for you at absolutely <em>FREE</em> of charge.</p>
                          <div class="primary-button">
                            <a href="#">进入识别</a>
                          </div>
                        </div>
                      </div>
                      <!-- 中间显示区 -->
                        <div class="col-md-6" style="width: 10px;height: 540px;background-color:none;">
                        <div class="left-content" style="width: 10px;height: 540px;background-color:none;">
                          <div class="left-line" style="width: 10px;height: 540px;background-color:none;"></div>      
                        </div>
                      </div>
                   
                   <!--   出口显示区 -->
                     <div class="col-md-6">
                        <div class="left-content">
                          <div class="left-line"></div>  
                          <h2>出口<em>显示</em></h2>
                          <p>We produce high quality <em>HTML</em> Website Templates for you at absolutely <em>FREE</em> of charge.</p>
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
</html>