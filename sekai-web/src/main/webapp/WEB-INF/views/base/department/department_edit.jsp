<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>

<html>
<head>
	<title></title>
    <jsp:include page="../../include/edit_header.jsp"></jsp:include>
</head>
<body>
	<form role="form" id="form_data" method="post" action="save">
		<div class="btn-toolbar" id="edit_toolbar">
			<div class="btn-group">
				<shiro:hasPermission name = "department_modify">
					<button type="submit" id="btn_submit" class="btn btn-primary">保存</button>
				</shiro:hasPermission>
				<button type="button" id="btn_back" class="btn btn-default" onclick="top.closeTab()">返回</button>
			</div>
		</div>
		<div class="form-group">
	    	<div class="col-sm-4">
		    	<label for="deptCode">部门编码</label>
		    	<input type="hidden" id="deptId" name="deptId" value="${department.deptId}">
		    	<input type="text" class="form-control" id="deptCode" name="deptCode" value="${department.deptCode}" />
			</div>
	    	<div class="col-sm-4">
		    	<label for="deptName">部门名称</label>
		    	<input type="text" class="form-control" id="deptName" name="deptName" value="${department.deptName}" />
	    	</div>
			<div class="col-sm-4">
		    	<label for="deptName">上级部门</label>
		    	<div class="input-group">
			    	<input type="hidden" id="parentId" name="parentId" value="${department.parent.deptId}">
			    	<input type="text" class="form-control" id="parentName" name="parentName" value="${department.parent.deptName}" />
				    <span class="input-group-btn">
				        <button class="btn btn-success" type='button' onclick="selectDepartment()">选择</button>
				    </span>
			    </div>
	    	</div>
	    	<div class="col-sm-4">
		    	<label for="forbid">禁用状态</label>
		    	<select class="form-control" id="forbid" name="forbid">
		    		<option value="0">否</option>
		    		<option value="1">是</option>
		    	</select>
	    	</div>
	  	</div>
	</form>
	
	<script type="text/javascript">
	var thisFrameId=top.getThisFrameId(this);
    $(function () {
        var action_result = '${action_result}';
        if(action_result == 'success'){
        	top.msgBox('保存成功!');
        	top.refreshParentList("部门",true);
        }else if (action_result != ''){
        	top.msgBox('保存失败！\n' + action_result);
        }

        $('#forbid').val('${department.forbid}');
     });
	var indexSelectDepartment;
	function selectDepartment(){
		var url='base/department/list.do?dlgSelect=1&callBackFun=doSelectDepartment&parentFrameId='+thisFrameId;
		indexSelectDepartment=top.showDialog('选择上级部门',url);
	}
	function doSelectDepartment(frm,row){
		var table=frm.$('#data_table');
		var obj=frm.getSelectOrDbClickRows(table,row);
		if(obj==null || obj.length==0){
			top.msgBox('请选择一行!');
			return;
		}
		$("#parentId").val(obj.deptId);
		$("#parentName").val(obj.deptName);
		top.closeDialog(indexSelectDepartment);
	}
    </script>
</body>
</html>
