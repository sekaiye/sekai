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
	<div style="color:blue">${shortMsg}</div>
	<div><textarea style="width:100%;height:500px">${msg}</textarea></div>
	<script type="text/javascript">
		$(function () {
		    var action_result = '${action_result}';
		    if(action_result == 'success'){
	        	top.msgBox('上传成功!');
	        	//parent.searchTable();
	        	//parent.closeDialog();
		    }else if (action_result != ''){
		    	top.msgBox('上传失败!\n' + action_result);
		    }
		 });
   </script>
</body>
</html>
