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
				<shiro:hasPermission name = "material_modify">
					<button type="submit" id="btn_submit" class="btn btn-primary">保存</button>
				</shiro:hasPermission>
				<button type="button" id="btn_back" class="btn btn-default" onclick="top.closeTab()">返回</button>
			</div>
		</div>
		<div class="form-group">
	    	<div class="col-sm-4">
		    	<label for="matCode">物料编码</label>
		    	<input type="hidden" id="matId" name="matId" value="${material.matId}">
		    	<input type="text" class="form-control" id="matCode" name="matCode" value="${material.matCode}" />
			</div>
	    	<div class="col-sm-4">
		    	<label for="matName">物料名称</label>
		    	<input type="text" class="form-control" id="matName" name="matName" value="${material.matName}" />
	    	</div>
	    	<div class="col-sm-4">
		    	<label for="matName">规格型号</label>
		    	<input type="text" class="form-control" id="spec" name="spec" value="${material.spec}" />
	    	</div>
	    	<div class="col-sm-4">
		    	<label for="matName">计量单位</label>
		    	<input type="text" class="form-control" id="unit" name="unit" value="${material.unit}" />
			</div>
			<div class="col-sm-4">
				<img id="matPic" name="matPic"/>
				<input type="button" value="上传" id="btn_matPic"/>
			</div>
	  	</div>
	</form>
	
	<script type="text/javascript">
    $(function () {
        var action_result = '${action_result}';
        if(action_result == 'success'){
        	top.msgBox('保存成功!');
        	top.refreshParentList("物料",true);
        }else if (action_result != ''){
        	top.msgBox('保存失败！\n' + action_result);
        }
        $('#btn_matPic').click(function(){
            var url = 'file/fileUpload/upload?saveDir=/material';
            //top.showDialogSmall("上传文件",url);
            var obj=top.layer.ready(function(){
                top.layer.open({
                    type: 2,
                    title: '上传文件'	,
                    maxmin: true,
                    closeBtn:false,
                    area: ['500px', '400px'],
                    content: url,
                    btn: ['确定','关闭'],
                    yes: function(index){
                    var res = top.window["layui-layer-iframe" + index].callbackdata();
                    //alert(res.savePath);
                    top.layer.close(index);
                    var url='../../fileDownLoad/images/?fileName='+res.savePath;
					$('#matPic').attr('src',url);
				}
                });
            });
        });
     });

    </script>
</body>
</html>
