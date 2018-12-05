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
				<shiro:hasPermission name = "warehouse_modify">
					<button type="submit" id="btn_submit" class="btn btn-primary">保存</button>
				</shiro:hasPermission>
				<button type="button" id="btn_back" class="btn btn-default" onclick="top.closeTab()">返回</button>
			</div>
		</div>
		<div class="form-group">
	    	<div class="col-sm-4">
		    	<label for="whCode">仓库编码</label>
		    	<input type="hidden" id="whId" name="whId" value="${warehouse.whId}">
		    	<input type="text" class="form-control" id="whCode" name="whCode" value="${warehouse.whCode}" />
			</div>
	    	<div class="col-sm-4">
		    	<label for="whName">仓库名称</label>
		    	<input type="text" class="form-control" id="whName" name="whName" value="${warehouse.whName}" />
	    	</div>
	    	<div class="col-sm-4">
		    	<label for="isCtrlStock">仓库名称</label>
		    	<select class="form-control" id="isCtrlStock" name="isCtrlStock">
		    		<option value="1">是</option>
		    		<option value="0">否</option>
		    	</select>
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
    $(function () {
        var action_result = '${action_result}';
        if(action_result == 'success'){
        	top.msgBox('保存成功!');
        	top.refreshParentList("仓库",true);
        }else if (action_result != ''){
        	top.msgBox('保存失败！\n' + action_result);
        }
        $('#isCtrlStock').val('${warehouse.isCtrlStock}');
        $('#forbid').val('${warehouse.forbid}');
     });

    </script>
</body>
</html>
