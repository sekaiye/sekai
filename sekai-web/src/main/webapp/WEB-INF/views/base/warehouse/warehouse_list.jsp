<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>仓库列表</title>
    <jsp:include page="../../include/list_header.jsp"></jsp:include>
</head>
<body>
	<div id="list_header">
		<button type="button" id="btn_dlg_select" class="btn btn-warning" 
				onclick="selectThis()">选择</button>
		<div class="btn-group" id="list_toolbar">
			<shiro:hasPermission name="warehouse_add">
		    	<button type="button" id="btn_add" class="btn btn-default" >
		        <span class="glyphicon glyphicon-plus"></span>增加</button>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="warehouse_modify">
				<button type="button" id="btn_modify" class="btn btn-default" >
				<span class="glyphicon glyphicon-pencil"></span>修改</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="warehouse_delete">
		    	<button type="button" id="btn_delete" class="btn btn-default" >
		        <span class="glyphicon glyphicon-remove"></span>删除</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="warehouse_modify">
				<div class="btn-group" >
				    <button type="button" class="btn btn-default" id="btn_enable">启用</button>
				    <button type="button" class="btn btn-default dropdown-toggle" 
				        data-toggle="dropdown">
				        <span class="caret"></span>
				    </button>
				    <ul class="dropdown-menu" role="menu">
				        <li><a href="#" id="btn_forbid">禁用</a></li>
				    </ul>
				</div>
			</shiro:hasPermission>
		    <shiro:hasPermission name="warehouse_export">
				<button type="button" id="btn_export" class="btn btn-default" >
		        <span class="glyphicon glyphicon-export"></span>导出</button>
			</shiro:hasPermission>
		</div>
		<div class="form-inline" id="searchbar">
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
            url: "getWarehouseList",
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
                field: 'whId',
                title: '仓库ID',
                align: 'center',
                sortable:true
            },{
                field: 'whCode',
                title: '仓库编码',
                align: 'center',
                sortable:true,
                    forwhter:function(value,row,index){
                        //value：当前field的值，即id
                        //row：当前行的数据
                    	return '<a href=\'javascript:edit('+row.whId+')\'>'+value+'</a>';
                    } 
            },{
                field: 'whName',
                title: '仓库名称',
                align: 'center',
                sortable:true
            },{
                field: 'isCtrlStockName',
                title: '禁止负库存',
                align: 'center',
                sortable:true
            },{
                field: 'forbidName',
                title: '禁用状态',
                align: 'center',
                sortable:true
            }],
            onDblClickRow: function (row) {
                edit(row.whId);
            }
        });
    }
  //新增
    function addRecord(){
		var url = 'base/warehouse/edit?';
		top.openTab('仓库 -新增', url);
		//top.showDialog(url,null);
	}
	//修改
    function edit(id){
		var url='base/warehouse/edit?id=' + id;
		top.openTab('仓库-修改', url);
    }
    function editRecord(){
    	var ids = getSelectRows('data_table', 'whId');
		if(ids.length!=1){
			top.msgBox('请选中一行！');
			return;
		}
		edit(ids);
	}
    //删除
    function deleteRecord(){
    	var ids = getSelectRows('data_table', 'whId');
		if(ids.length==0){
			top.msgBox('请至少选中一行！');
			return;
		}
		top.bootbox.confirm('确定要删除所选记录吗？', function(result){
			if(!result)
				return;
			$.ajax({
				type:'POST',
				url:'../../base/warehouse/delete?ids='+ids,
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
  	//设置状态
    function setStatus(status, statusName){
    	var ids = getSelectRows('data_table', 'whId');
		if(ids.length==0){
			top.msgBox('请至少选中一行！');
			return;
		}
		top.bootbox.confirm('确定要'+statusName+'所选记录吗？', function(result){
			if(!result)
				return;
			$.ajax({
				type:'POST',
				url:'../../base/warehouse/setStatus?ids='+ids+'&status='+status,
				success:function(result){
		       		if(result.success == 1){
		       			top.msgBox(statusName + '成功!');
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
    function selectThis(){
    	//执行回调函数
    	<%if(request.getParameter("parentFrameId") != null){%>
  		top.document.getElementById('${param.parentFrameId}').contentWindow.${param.callBackFun}(this);
  		<%}%>
    }
    $(function () {
    	if('${param.dlgSelect}'=='1'){
    		$('#toolbar').css('display','none');
    	}else{
    		$('#btn_dlg_select').css('display','none');
    	}
        initTable();
        $('#keyword').keydown(function(e){
        	//搜索回车事件
        	if(e.keyCode==13){
        		searchTable();
        	}
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
        $('#btn_forbid').click(function(){
        	setStatus(1, '禁用');
        });
        $('#btn_enable').click(function(){
        	setStatus(0, '启用');
        });
    });
    </script>
</body>
</html>
