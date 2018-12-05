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
	<div style="color:blue">上传成功！<br/>保存路径：${savePath}</div>
	<script type="text/javascript">
		$(function () {

		 });
        var callbackdata = function () {
            var data = {
                savePath: '${savePath}'
            };
            return data;
        }
   </script>
</body>
</html>
