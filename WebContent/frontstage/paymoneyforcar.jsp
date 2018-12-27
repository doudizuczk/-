<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="UTF-8" />
		<meta name="renderer" content="webkit" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=0,uc-fitscreen=yes" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<meta name="format-detection" content="telephone=no" />
		<title>慧停车</title>
		<meta name="keywords" content="miniMobile的demo" />
		<meta name="description" content="miniMobile是一个简单易用的移动框架！" />
		<!-- ui css、js -->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/userstyle/css/miniMobile.css"/>
		<script type="text/javascript" src="<%=request.getContextPath()%>/userstyle/js/zepto.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/userstyle/js/miniMobile.js"></script>
		<!-- 字体图标 -->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/userstyle/plugins/fonticon/iconfont.css" />
		<!-- animate.css -->
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.css" />
		<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/userstyle/css/styles.css">
		<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
		
		<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<%-- 		<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet"> --%>
		<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
		<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<%-- 		<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script> --%>
	</head>
	<body class="pb12 fadeIn animated">
		<header class="ui-header clearfix w75 h8 f46 pl3 pr3 color8 bg-color-primary t-c">
			<div class="ui-header-l fl w5">
				<a href="<%=request.getContextPath()%>/frontstage/user_main.jsp" class="icon color8 iconfont icon-home_light"></a>
			</div>
			<div class="ui-header-c fl f30 w59">
				月缴办理
			</div>
			<div class="ui-header-r fr w5">
				<i class="icon iconfont icon-phone"></i>
			</div>
		</header><br />
<h3>套餐办理页</h3>
	<form id="packForm" >
		<table class="table table-striped table-hover">
				<tr>
					<th></th>
					<th></th>
				</tr>
				<tr>
				<td>选择要办理的套餐类型：</td>
					<td>
				<select name="packType" id="packType">
						<option value="">请选择套餐类型...</option>
					<c:forEach items="${dates.TypePack}" var="ttt">
						<option value="${ttt.PARM_VAL}">${ttt.PARM_NAME}</option>
					</c:forEach>
				</select>
					</td>
				</tr>
				<tr>
					<td>选择你要办理的套餐：</td>
					<td>
						<select name="packId" id="packId">
							<option value="">请选择套餐...</option>
						</select>
					</td>
				</tr>

			<tr>
			<td>请输入车牌号：</td>
			<td>
				<select name="carAccount" id="carAccount">
						<option value="">请选择办理车辆...</option>
					<c:forEach items="${carList}" var="ttt">
						<option value="${ttt.carId}">${ttt.carId}</option>
					</c:forEach>
				</select>
				</td>
				</tr> 

				
			<tbody id="packTbody">
			</tbody>
		 </table>
		 <div>
		 	<input type="submit" value="确认办理" id="newBtn" class="btn btn-primary"><input type="reset" value="重置" id="reBtn" class="btn btn-primary">
		 </div>
	</form>
	</body>
	<script>

	
	
	$().ready(function(){
		$("#packForm").validate({
	   	 rules: {
	   		carAccount: {
	   	        required: true,
	   	      },
	   	   packType: {
	   	        required: true,
	   	      },
	   	   packId: {
	   	        required: true,
	   	      },
	   	    },
	   	  messages: {
	   		carAccount: {
	   	        required: "请填写车牌号",
	   	      },
	   	   packType: {
	   	        required: "请选择套餐类型",
	   	      },
	   	   packId: {
	   	        required: "请选择套餐",
	   	      },
	   	     },
	   	  submitHandler: function(form) { check_licensePlate(); }
	   })	
	});
	function check_licensePlate() {
		console.info("进入到车牌校验");
		var licensePlate = $("#carAccount").val()
		var re = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/;
		if(licensePlate.search(re) == -1) {
			alert("您输入的车牌号不合格！")
		} else {
			CarIdPackTransact();
		}
	}
	//办理套餐的方法
	var path="<%=request.getContextPath()%>";
	function CarIdPackTransact(){
		var carId = $("#carAccount").val()
		alert(carId);
		var path="<%=request.getContextPath()%>";
			$.ajax({
				type:"post",
				url:path+"/owerHandler/carIdPackTransact.action",
				data:{"carAccount":$("#carAccount").val(),"packId":$("#packId").val()},
				dataType:"json",
				success:function(data){
					if(data.addState==1){
					window.alert("套餐办理成功！")
					}else if(data.addState==2){
						window.alert("套餐续费成功！结束日期为"+data.eDate+"")
					}else if(data.addState==3){
						if(data.refundState==1){
							window.alert("套餐更换成功！原套餐退款"+data.money+"/元(人民币),账户余额退款成功。")
							}
						if(data.refundState==2){
							window.alert("套餐更换成功！原套餐退款"+data.money+"/元(人民币),该车没有绑定账户,请退现金。")
						}
	/* 					window.alert("该车没有绑定账户,请现金退款"+data.money+"/元(人民币)") */
					}
				},
				error:function(){
					window.alert("查询出错");
				}
			})
	}
	//打开页面加载改变事件判断
	 $(document).ready(function(){
	     //绑定下拉框change事件，当下来框改变时调用 SelectChange()方法
	     $("#packType").change(function() { 
	    	 SelectTypeChange(); 
	     });
	     $("#packId").change(function() { 
	    	 packNameChange(); 
	     });
	      $("#carAccount").change(function() { 
	    	 packNameChange(); 
	     });
	})
	//套餐改变回填套餐属性
	function packNameChange(){
	 		var packId= $("#packId").val()
	 		console.log("套餐ID="+packId)
	 		var str ="";
	 		$("#packTbody").empty();
	 	for (var i = 0; i < packList.length; i++) {
	 		console.log("套餐id==="+packList[i].PACK_ID)
	 		if(packList[i].PACK_ID==packId){
	 			console.log("套餐id============"+packList[i].PACK_ID)
	 			str+="<tr>";
				str+="<td>套餐时长</td>";
				str+="<td>"+packList[i].PACK_TIME+"/个月("+packList[i].PACK_TIME*30+"天)</td>";
				str+="</tr>";
				str+="<tr>";
				str+="<td>套餐费用</td>";
				str+="<td>"+packList[i].PACK_COST+"/元(人民币)</td>";
				str+="</tr>";
	 		}
	 		 $("#packTbody").html(str); //回填列表
		} 

	}
	var packList;
	function SelectTypeChange(){
	var path="<%=request.getContextPath()%>";
		$.ajax({
			url:path+"/owerHandler/SelectTypeChange.action",
			type:"POST", 
			data:{"packType":$("#packType").val()},
			dataType:"json",
			success:function(data){
				packList=data.packList;
				console.log("成功！"+packList)
				$("#packId").empty();
	    	for (var i = 0; i < data.packList.length; i++) {
				  $("#packId").append("<option value='"+data.packList[i].PACK_ID+"' >" + data.packList[i].PACK_NAME + "</option>");
				           } 
			},
			error:function(){
				window.alert("操作出错");
			}
		})
	}
	<%-- var path="<%=request.getContextPath()%>";
	function starState(adminId,state){
		console.log(adminId+"and"+state)
		alert("提交修改")
	} --%>

	function isLicenseNo(str) {
	    return /(^[\u4E00-\u9FA5]{1}[A-Z0-9]{6}$)|(^[A-Z]{2}[A-Z0-9]{2}[A-Z0-9\u4E00-\u9FA5]{1}[A-Z0-9]{4}$)|(^[\u4E00-\u9FA5]{1}[A-Z0-9]{5}[挂学警军港澳]{1}$)|(^[A-Z]{2}[0-9]{5}$)|(^(08|38){1}[A-Z0-9]{4}[A-Z0-9挂学警军港澳]{1}$)/.test(str);
	}
	</script>
<style>
#cancel{
margin-left: 100px;
}
</style>
</html>