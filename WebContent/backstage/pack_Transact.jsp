<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>套餐办理页</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script>
$().ready(function(){
	$("#addAdminForm").validate({
   	 rules: {
   		name: {
   	        required: true,
   	      },
   	    },
   	    
   	  messages: {
   		name: {
   	        required: "请填写姓名",
   	      },
   	     },
   	  submitHandler: function(form) { addQueryAdminExist(); }
   })	
});
var path="<%=request.getContextPath()%>";
function createAdmin(){
	var path="<%=request.getContextPath()%>";
		$.ajax({
			type:"post",
			url:path+"/czkPer/addAdmin.action",
			data:JSON.stringify($("#addAdminForm").serializeJSON()),
			contentType:"application/json",
			dataType:"json",
			success:function(data){
				if(data==1){
				window.alert("添加成功")
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
		url:path+"/transact/SelectTypeChange.action",
		type:"POST", 
		data:{"packType":$("#packType").val()},
		dataType:"json",
		success:function(data){
			packList=data.packList;
			console.log("成功！"+packList)
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
.error{
	color:red;
}
</style>
</head>
<body>
<h3>套餐办理页</h3>
	<form id="addAdminForm" >
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
						<input type="text" name="carAccount" id="carAccount" placeholder="请输入要办理的车牌号..."> 
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
</html>