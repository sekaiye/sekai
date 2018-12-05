<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
	<title></title>
    <jsp:include page="../../include/edit_header.jsp"></jsp:include>
</head>
<body>
	<form role="form" method="post" action="changeMyPwdSave.do">
		<input type="hidden" id="userId" name="userId" value="${sessionScope.userId}" />
	  	<div class="form-group">
	    	<label for="username">用户名</label>
	    	<input type="text" class="form-control" id="userName" name="userName" value="${sessionScope.userName}" readonly="readonly"/>
	  	</div>
	  	<div class="form-group">
	    	<label for="newPassword">新密码</label>
	    	<input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="请输入新密码" value="${newPassword}" />
	  	</div>
	  	<div class="form-group">
	    	<label for="newPassword2">验证新密码</label>
	    	<input type="password" class="form-control" id="newPassword2" name="newPassword2" placeholder="请再次输入新密码" value="${newPassword2}" />
	  	</div>
		<button type="submit" id="btn_submit" class="btn btn-primary">保存</button>
	</form>
	
	<script type="text/javascript">
    $(function () {
        var action_result = '${action_result}';
        if(action_result == 'success'){
        	top.msgBox('修改密码成功!');
        }else if (action_result != ''){
        	top.msgBox('修改失败！\n' + action_result);
        }
     });

    </script>
</body>
</html>
