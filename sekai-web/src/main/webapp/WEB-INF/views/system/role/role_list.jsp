<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <jsp:include page="../../include/list_header.jsp"></jsp:include>
</head>
<body>
	<div id="list_header">
		
		<div class="btn-group" id="list_toolbar">
			<shiro:hasPermission name="role_modify">
		    	<button type="button" id="btn_give_permission" class="btn btn-info">角色授权</button>
		    </shiro:hasPermission>
			<shiro:hasPermission name="role_modify">
		    	<button type="button" id="btn_add" class="btn btn-default" >
		        <span class="glyphicon glyphicon-plus"></span>增加</button>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="role_modify">
				<button type="button" id="btn_modify" class="btn btn-default" >
				<span class="glyphicon glyphicon-pencil"></span>修改</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="role_delete">
		    	<button type="button" id="btn_delete" class="btn btn-default" >
		        <span class="glyphicon glyphicon-remove"></span>删除</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="role_modify">
				<button type="button" id="btn_import" class="btn btn-default" >
		        <span class="glyphicon glyphicon-import"></span>导入</button>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="role_modify">
				<button type="button" id="btn_export" class="btn btn-default" >
		        <span class="glyphicon glyphicon-export"></span>导出</button>
			</shiro:hasPermission>
		</div>
		<div class="form-inline" id="searchbar">
			<button type="button" id="btn_dlg_select" class="btn btn-info">选择</button>
			<div class="input-group">
				<input type="text" class="form-control" style="width:auto" placeholder="关键字"  id="keyword" name="keyword">
				<span class="input-group-btn" style='float:left'>
					<button class="btn btn-success" id="btn_search">
					<span class="glyphicon glyphicon-search"></span>搜索</button>
					<button class="btn btn-default" id="btn_more_search">高级搜索</button>
				</span>
			</div>
		</div>
	</div>
	<div id="table_box">	
		<table id="data_table" ></table>
	</div>
	<script type="text/javascript">
	//创建列表
    function initTable(){
    	var table_box = document.getElementById("table_box");
	    var table_height = table_box.offsetHeight;
        $('#data_table').bootstrapTable({
            method: 'get',
            //toolbar: '#toolbar',
            striped: true,
            showToggle: false,
            cache: false,
            pagination: true,
            sortable: true,
            pageNumber:1,
            pageSize: 50,
            pageList: [50,100,500,1000,5000],
            url: "getRoleList",
            queryParamsType:'',
            queryParams: queryParams,
            sidePagination: "server",
            //showColumns: true,
            //showRefresh: true,
            minimumCountColumns: 2,
            //clickToSelect: true,
            checkbox : true,
            height:table_height,
            columns: [{
                field: 'check',
                title: '选择',
                checkbox : true,
                align: 'center'
            }, {
                field: 'roleId',
                title: '角色ID',
                align: 'center',
                sortable:true
            },{
                field: 'roleCode',
                title: '角色编码',
                align: 'center',
                sortable:true,
                    formatter:function(value,row,index){
                        //value：当前field的值，即id
                        //row：当前行的数据
                    	return '<a href=\'javascript:edit('+row.roleId+')\'>'+value+'</a>';
                    } 
            },{
                field: 'roleName',
                title: '角色名称',
                align: 'center',
                sortable:true
            },{
                field: 'statusName',
                title: '状态',
                align: 'center',
                sortable:true
            }],
            onDblClickRow: function (row) {
                if('${param.dlgSelect}'=='1'){
            		selectThis(row);
                }else{
                	edit(row.roleId);
                }
            }
        });
    }
    //新增
    function addRecord(){
		var url = 'system/role/edit';
		top.openTab('角色-新增', url);
	}
	//修改
    function edit(id){
		var url='system/role/edit?id=' + id;
		top.openTab('角色-修改', url);
    }
    function editRecord(){
    	var ids = getSelectRows('data_table', 'roleId');
		if(ids.length!=1){
			top.msgBox('请选中一行！');
			return;
		}
		edit(ids);
	}

    //删除
    function deleteRecord(){
    	var ids = getSelectRows('data_table', 'roleId');
		if(ids.length==0){
			top.msgBox('请至少选中一行！');
			return;
		}
		top.bootbox.confirm('确定要删除所选记录吗？', function(result){
			if(!result)
				return;
			$.ajax({
				type:'POST',
				url:'../../system/role/delete?ids='+ids,
				success:function(result){
	        		if(result.success == 1){
	        			top.msgBox('删除成功!');
	        			searchTable();
	        		}else{
	        			top.msgBox(result.data);
	        		}
	        		
				}
			});
		});
	}
    //刷新列表
    function searchTable(){
    	$('#data_table').bootstrapTable('refresh');
    }
	//列表参数
    function queryParams(params) { 
		var temp = { 
			pageSize: params.pageSize, 
			pageNumber: params.pageNumber,
			keyword: $("#keyword").val(),
			sortName: params.sortName, 
			sortOrder: params.sortOrder 
		}; 
		return temp; 
	}
    //角色授权
    function giveAuth(){
    	var ids = getSelectRows('data_table', 'roleId');
		if(ids.length!=1){
			top.msgBox('请选中一个角色！');
			return;
		}
		var url='system/rolePermission/edit.do?roleId='+ids;
		top.openTab('角色授权', url);
    }
    function selectThis(){
    	//执行回调函数
    	<%if(request.getParameter("parentFrameId") != null){%>
  		top.document.getElementById('${param.parentFrameId}').contentWindow.${param.callBackFun}(this);
  		<%}%>
    }
    $(function () {
    	if('${param.dlgSelect}'=='1'){
    		$('#list_toolbar').css('display','none');
    	}else{
    		$('#btn_dlg_select').css('display','none');
    	}
        initTable();
      	//隐藏列
        $('#data_table').bootstrapTable('hideColumn', 'roleId');
        $('#keyword').keydown(function(e){
        	//搜索回车事件
        	if(e.keyCode==13){
        		searchTable();
        	}
		});
        $('#btn_dlg_select').click(function(){
        	selectThis();
        });
        $('#btn_search').click(function(){
        	searchTable();
        });
        $('#btn_add').click(function(){
        	addRecord();
        });
        $('#btn_modify').click(function(){
        	editRecord();
        });
        $('#btn_delete').click(function(){
        	deleteRecord();
        });
        $('#btn_export').click(function(){
        	exportToExcel();
        });
        $('#btn_give_permission').click(function(){
        	giveAuth();
        });
    });
    </script>
</body>
</html>
