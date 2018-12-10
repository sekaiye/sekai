<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
	<title></title>
    <jsp:include page="../../include/bill_header.jsp"></jsp:include>
</head>
<body>
	<div class="btn-toolbar" id="edit_toolbar">
	  	<div class="btn-group" >
			<button type="button" id="btn_save" class="btn btn-primary">保存</button>
			<button type="button" id="btn_back" class="btn btn-default" onclick="top.closeTab()">返回</button>
		</div>
	</div>
	<div id="div_sk_grid_users" style='margin-top:20px;'></div>
	<div id="div_sk_grid_roles" style='margin-top:20px;'></div>
	<script type='text/javascript'>
		//$(function () {
			var json_Users=[];
		    var skGrid_Users=$("#div_sk_grid_users").SKGrid({
		        columns:[
		        	{field:"userId",title:"userId",css:"width:80px;display:none;"},
		        	{field:"userName",title:"用户名",css:"width:150px;",editable:true,readonly:true},
		            {field:"nickName",title:"姓名",css:"width:150px;"}
		        ],datas:json_Users,
		        setButtonEvent: function (rowIndex, cellIndex, field) {
		            var btnEvt = "";
		            if (field == 'userName') {
		            	rowIndex_Users=rowIndex;
		                btnEvt = "selectUser()";
		                
		            }
		            return btnEvt;
		        }
		    });
		    skGrid_Users.addRow(5);

			var json_Roles=[];
		    var skGrid_Roles=$("#div_sk_grid_roles").SKGrid({
		        columns:[
		        	{field:"roleId",title:"roleId",css:"width:80px;display:none;"},
		        	{field:"roleCode",title:"角色编码",css:"width:150px;",editable:true},
		        	{field:"roleName",title:"角色",css:"width:150px;"}
		        ],datas:json_Roles,
		        setButtonEvent: function (rowIndex, cellIndex, field) {
		            var btnEvt = "";
		            if (field == 'roleCode') {
		            	rowIndex_Roles=rowIndex;
		                btnEvt = "selectRole()";
		            }
		            return btnEvt;
		        }
		    });
		    skGrid_Roles.addRow(5);
		//});
		var thisFrameId=top.getThisFrameId(this);
		var indexSelectUser;
		function selectUser(rowIndex){
			var url='system/user/list.do?dlgSelect=1&callBackFun=doSelectUser&parentFrameId='+thisFrameId;
			indexSelectUser=top.showDialog('选择用户',url);
		}
		var _userId=skGrid_Users.getFieldIndex("userId");
		var _userName=skGrid_Users.getFieldIndex("userName");
		var _nickName=skGrid_Users.getFieldIndex("nickName");
		var rowIndex_Users=null;
		function doSelectUser(frm){
            var rows=frm.$('#data_table').bootstrapTable('getSelections');
            if(rows==null || rows.length==0){
                top.msgBox('请选择用户!');
                return;
            }
            var table=skGrid_Users.getTable();
            for(var i=0;i<rows.length;i++){
                if(table.rows[rowIndex_Users+i]==null){
                    skGrid_Users.addRow(1);
                }
                var row=rows[i];
                var cells=table.rows[rowIndex_Users+i].cells;
                cells[_userId].innerHTML=row.userId;
                cells[_userName].innerHTML=row.userName;
                cells[_nickName].innerHTML=row.nickName;
            }
			top.closeDialog(indexSelectUser);
		}
		var indexSelectRole;
		function selectRole(rowIndex){
			var url='system/role/list.do?dlgSelect=1&callBackFun=doSelectRole&parentFrameId='+thisFrameId;
			indexSelectRole=top.showDialog('选择角色',url);
		}
		var _roleId=skGrid_Roles.getFieldIndex("roleId");
		var _roleCode=skGrid_Roles.getFieldIndex("roleCode");
		var _roleName=skGrid_Roles.getFieldIndex("roleName");
		var rowIndex_Roles=null;
		function doSelectRole(frm){
            var rows=frm.$('#data_table').bootstrapTable('getSelections');
            if(rows==null || rows.length==0){
                top.msgBox('请选择角色!');
                return;
            }
            var table=skGrid_Roles.getTable();
            for(var i=0;i<rows.length;i++){
                if(table.rows[rowIndex_Roles+i]==null){
                    skGrid_Roles.addRow(1);
                }
                var row=rows[i];
                var cells=table.rows[rowIndex_Roles+i].cells;
                cells[_roleId].innerHTML=row.roleId;
                cells[_roleCode].innerHTML=row.roleCode;
                cells[_roleName].innerHTML=row.roleName;
            }
			top.closeDialog(indexSelectRole);
		}
		$('#btn_save').click(function(){
			skGrid_Users.cancelEdit();
        	var json_Users='';
        	var table_Users=skGrid_Users.getTable();
        	for (var row=1;row<table_Users.rows.length;row++){
        		var cells = skGrid_Users.getTable().rows[row].cells;
	            var userId=cells[_userId].innerHTML;
        		if(userId=='')
	            	continue;
        		json_Users+='{';
	            json_Users+='"userId":"'+userId+'"';
	            json_Users+='},';
        	}
        	if(json_Users!=''){
        		json_Users=json_Users.substring(0,json_Users.length-1);
        	}
        	json_Users='['+json_Users+']';

        	skGrid_Roles.cancelEdit();
        	var json_Roles='';
        	var table_Roles=skGrid_Roles.getTable();
        	for (var row=1;row<table_Roles.rows.length;row++){
        		var cells = skGrid_Roles.getTable().rows[row].cells;
	            var roleId=cells[_roleId].innerHTML;
	            if(roleId=='')
	            	continue;
        		json_Roles+='{';
	            json_Roles+='"roleId":"'+roleId+'"';
	            json_Roles+='},';
        	}
        	if(json_Roles!=''){
        		json_Roles=json_Roles.substring(0,json_Roles.length-1);
        	}
        	json_Roles='['+json_Roles+']';
        	$.ajax({
	        	type : "POST",
	        	url : "save",
	            data : "json_Users="+json_Users+"&json_Roles="+json_Roles,
	            success : function(result) {
	            	if(result.success == 1){
	        			top.msgBox('保存成功!');
	        			top.refreshParentList("分配角色",true);
	        		}else{
	        			top.msgBox(result.data);
	        		}
	            }
	        });
		});
    </script>
</body>
</html>
