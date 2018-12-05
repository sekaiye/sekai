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
	<form role="form" id="form_upload" method="post" 
		enctype="multipart/form-data" class="form-inline" style="margin:20px"
		action="${readExcelAction}">
	  	<div class="form-group">
	    	<label>文件</label>
	    	<input type="file" id="file" name="file" style="display:none"/>
			<input id="fileEx" class="form-control" type="text" 
				style="width:auto" placeholder="请选择要导入的文件">
			<button type="button" id="btn_browse" class="btn btn-success">选择文件</button>
			<a href="../../files/import_template/${template}">下载导入模板</a>
	  	</div>
	  	<div class='panel-btn'>
			<button type="submit" id="btn_submit" class="btn btn-primary">上传</button>
			<button type="button" id="btn_back" class="btn btn-default" onclick="parent.closeDialog()">取消</button>
		</div>
	</form>
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
		    $('#btn_browse').click(function() {  
		    	$('input[id=file]').click();  
			});
		    $('input[id=file]').change(function() {  
		    	$('#fileEx').val($(this).val());  
			});
		 });
   </script>
</body>
</html>
