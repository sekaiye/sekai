<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>角色授权</title>
    <jsp:include page="../../include/list_header.jsp"></jsp:include>
    <link rel="stylesheet" href="../../js/ztree/css/metroStyle/metroStyle.css"/>
	<script type="text/javascript" src="../../js/ztree/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="../../js/ztree/js/jquery.ztree.excheck.js"></script>
    <style type='text/css'>
	.chkbox{
		width:100px;float:left;
	}    
    </style>
</head>
<body>
	<div class="btn-toolbar" id="edit_toolbar">
	  	<div class="btn-group" >
		    <button type="button" id="btn_save" class="btn btn-info" >保存授权</button>
		    <button type="button" id="btn_back" class="btn btn-default" onclick="top.closeTab()">返回</button>
		</div>
	</div>
	<ul id="z_tree" class="ztree"></ul>
	<script type="text/javascript">
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		var zNodes =[${json}];
		$(document).ready(function(){
			$.fn.zTree.init($("#z_tree"), setting, zNodes);
		});
		$('#btn_save').click(function(){
			var nodes=$.fn.zTree.getZTreeObj("z_tree").getCheckedNodes();
			var v="";
            for(var i=0;i<nodes.length;i++){
            	v+=nodes[i].permissionId + ",";
            }
            if(v.length>0)
            	v=v.substring(0,v.length-1);
			var roleId=${param.roleId};
	        $.ajax({  
	        	type : "POST",
	        	url : "save",  
	            data : "permissions="+v+"&roleId="+roleId,  
	            success : function(result) {  
	            	if(result.success == 1){
	        			top.msgBox('授权成功!');
	        		}else{
	        			top.msgBox(result.data);
	        		}
	            }  
	        });  
		});
    </script>
</body>	
</html>
