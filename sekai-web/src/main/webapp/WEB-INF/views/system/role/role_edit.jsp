<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		  	<div class="btn-group" >
		  		<shiro:hasPermission name = "role_modify">
					<button type="submit" id="btn_submit" class="btn btn-primary">保存</button>
				</shiro:hasPermission>
				<button type="button" id="btn_back" class="btn btn-default" onclick="top.closeTab()">返回</button>
			</div>
		</div>
	  	<div class="col-sm-4">
	    	<label for="roleCode">角色编码</label>
	    	<input type="hidden" id="roleId" name="roleId" value="${role.roleId}">
	    	<input type="text" class="form-control" id="roleCode" name="roleCode" placeholder="请输入角色编码" value="${role.roleCode}" />
	  	</div>
	  	<div class="col-sm-4">
	    	<label for="roleName">角色名称</label>
	    	<input type="text" class="form-control" id="roleName" name="roleName" placeholder="请输入角色名称" value="${role.roleName}" />
	  	</div>

	</form>
	
	<script type="text/javascript">
    $(function () {
        var action_result = '${action_result}';
        if(action_result == 'success'){
        	top.msgBox('保存成功!');
        	top.refreshParentList("角色",true);
        }else if (action_result != ''){
        	top.msgBox('保存失败！\n' + action_result);
        }
     });

    </script>
</body>
</html>
