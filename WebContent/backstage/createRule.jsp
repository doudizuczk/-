<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新增计费规则</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">
<%-- <link href="<%=request.getContextPath()%>/css/bootstrap.min2.css" rel="stylesheet"> --%>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.validate.js"></script>
<script src="<%=request.getContextPath()%>/js/messages_zh.min.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.serializejson.min.js"></script>
<script src="<%=request.getContextPath()%>/js/vue.js"></script>
<script>
window.onload=function(){
    var vu=new Vue({
        el:'#box',
        data:{
            myData:[],
            criticalHours:'',
            fixCost:'',
            outCost:'',
            nowIndex:-100
        },
        methods:{
            add:function(){
                this.myData.push({
                	criticalHours:this.criticalHours,
                	fixCost:this.fixCost,
                	outCost:this.outCost
                });

                this.criticalHours='';
                this.fixCost='';
                this.outCost='';
            },
            deleteMsg:function(n){
                if(n==-2){
                    this.myData=[];
                }else{
                    this.myData.splice(n,1);
                }
            }
        }
    });
    
	//检测同名
	$("#ruleName").blur(function(){
		checkRuleName();
	});
	function checkRuleName() {
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/ruleHandler/checkRuleName.action",
			data : {"ruleName" : $("#ruleName").val()},
			success : function(data) {
				console.log(data);
				if (data == '0') {
					$("#checkName").text("该账号已存在！");
				} else {
					$("#checkName").text("该账号可用！");
				}
			}
		});
	}
	
	//添加规则
	$("#add").click(function(){
		//拼接规则
		var str="";
		for(var i=0;i<vu.myData.length;i++){
			str+=vu.myData[i].criticalHours+",";
			str+=vu.myData[i].fixCost+",";
			str+=vu.myData[i].outCost+";";
		}
		alert(str);
		
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath()%>/ruleHandler/addRule.action",
			data : {"ruleName" : $("#ruleName").val(),"ruleDetail":str},
			success : function(data) {
				if (data == '1') {
					alert("添加成功！");
					window.location.href="<%=request.getContextPath()%>/ruleHandler/ruleList.action";
				} else {
					alert("添加失败");
				}
			},
			error:function(){
				window.alert("未知错误!");
			}
		});
		
	});
	
	
	
	
};

</script>
<style>
.error{
	color:red;
}
</style>
</head>
<body>
	<form id="createForm">
		<table class="table table-striped table-hover">
			<tbody>
				<tr>
					<th>规则名称：</th>
					<td><input type="text" name=ruleName id="ruleName" placeholder="请输入规则名称...">
					<span style="color: red; width: 150px; font-size:12px;" id="checkName"></span></td>
					<td><input type="hidden" name=ruleList id="ruleList"></td>
				</tr>
			</tbody>
		 </table>
	</form>
	<div class="container" id="box">
        <form role="form">
            <div class="form-group">
                <label for="criticalHours">临界小时数:</label>
                <input id="criticalHours" name="criticalHours" onkeyup="if(isNaN(value))execCommand('undo')" v-model="criticalHours" type="text" /><br/>
            </div>
            <div class="form-group">
                <label for="fixCost">超额前固定收费:</label>
                <input id="fixCost" name="fixCost" onkeyup="if(isNaN(value))execCommand('undo')" v-model="fixCost" type="text" /><br/>
            </div>
            <div class="form-group">
                <label for="outCost">超额每小时收费:</label>
                <input id="outCost" name="outCost" onkeyup="if(isNaN(value))execCommand('undo')" v-model="outCost" type="text" /><br/>
            </div>
            <div class="form-group">
                <input type="button" value="添加" class="btn btn-primary" v-on:click="add()">
                <input type="reset" value="重置" class="btn btn-danger">
            </div>
        </form>
        <hr>
        <table class="table table-bordered table-hover">
            <tr class="text-danger">
                <th class="text-center">序号</th>
                <th class="text-center">临界小时数</th>
                <th class="text-center">超额前固定收费</th>
                <th class="text-center">超额每小时收费</th>
                <th class="text-center">操作</th>
            </tr>
            <tr class="text-center" v-for="item in myData">
                <td>{{$index+1}}</td>
                <td>{{item.criticalHours}}</td>
                <td>{{item.fixCost}}</td>
                <td>{{item.outCost}}</td>
                <td>
                    <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#layer" v-on:click="nowIndex=$index">删除</button>
                </td>
            </tr>
            <tr v-show="myData.length!=0">
                <td colspan="5" class="text-right">
                    <button class="btn btn-danger btn-sm" v-on:click="nowIndex=-2" data-toggle="modal" data-target="#layer" >删除全部</button>
                </td>
            </tr>
            <tr v-show="myData.length==0">
                <td colspan="5" class="text-center text-muted">
                    <p>暂无数据....</p>
                </td>
            </tr>
        </table>

        <!--模态框 弹出框-->
        <div role="dialog" class="modal fade bs-example-modal-sm" id="layer">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                            <span>&times;</span>
                        </button>
                        <h4 class="modal-title">确认删除么?</h4>
                    </div>
                    <div class="modal-body text-right">
                        <button data-dismiss="modal" class="btn btn-primary btn-sm">取消</button>
                        <button data-dismiss="modal" class="btn btn-danger btn-sm" v-on:click="deleteMsg(nowIndex)">确认</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <button id="add" class="btn btn-primary btn-sm">保存提交</button>
</body>
</html>