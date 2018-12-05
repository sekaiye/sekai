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
				<shiro:hasPermission name = "user_modify">
					<button type="submit" id="btn_submit" class="btn btn-primary">保存</button>
				</shiro:hasPermission>
				<button type="button" id="btn_back" class="btn btn-default" onclick="top.closeTab()">关闭</button>
			</div>
		</div>
		<div class="form-group">
	    	<div class="col-sm-4">
	    		<label class="control-label" for="userName">用户名</label>
	    		<input type="hidden" id="userId" name="userId" value="${user.userId}">
	    		<input type="text" class="form-control" id="userName" name="userName" placeholder="请输入登录名" value="${user.userName}" />
			</div>
	    	<div class="col-sm-4">
	    		<label class="control-label" for="nickName">昵称</label>
	    		<input type="text" class="form-control" id="nickName" name="nickName" placeholder="请输入姓名" value="${user.nickName}" />
	    	</div>
	    	<div class="col-sm-4">
	    		<label class="control-label" for="pwd">密码</label>
	    		<input type="text" class="form-control" id="pwd" name="pwd" value="" />
	    	</div>
	    	<div class="col-sm-4">
	    		<label class="control-label" for="email">email</label>
	    		<input type="text" class="form-control" id="email" name="email" value="${user.email}" />
	    	</div>
	    	<div class="col-sm-4">
	    		<label class="control-label" for="cellphone">手机</label>
	    		<input type="text" class="form-control" id="cellphone" name="cellphone" value="${user.cellphone}" />
	    	</div>
		</div>
	</form>
	
	<script type="text/javascript">
    $(function () {
        var action_result = '${action_result}';
        if(action_result == 'success'){
        	top.msgBox('保存成功!');
        	top.refreshParentList("用户",true);
        	
        }else if (action_result != ''){
        	top.msgBox('保存失败！\n' + action_result);
        }
     });

    </script>
</body>
</html>
