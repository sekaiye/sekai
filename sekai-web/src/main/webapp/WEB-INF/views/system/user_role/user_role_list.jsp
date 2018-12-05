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
			<shiro:hasPermission name="user_role_add">
		    	<button type="button" id="btn_add" class="btn btn-default" >
		        <span class="glyphicon glyphicon-plus"></span>增加</button>
		    </shiro:hasPermission>
			<shiro:hasPermission name="user_role_delete">
		    	<button type="button" id="btn_delete" class="btn btn-default" >
		        <span class="glyphicon glyphicon-remove"></span>删除</button>
			</shiro:hasPermission>
		    <shiro:hasPermission name="user_role_export">
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
				</span>
			</div>
		</div>
	</div>
	<div id="table_box" style='width:100%;height:100%;'>	
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
            url: "getUserRoleList.do",
            queryParamsType:'',
            queryParams: queryParams,
            sidePagination: "server",
            //showColumns: true,
            //showRefresh: true,
            minimumCountColumns: 2,
            clickToSelect: true,
            checkbox : true,
            height:table_height,
            columns: [{
                field: 'check',
                title: '选择',
                checkbox : true,
                align: 'center'
            }, {
                field: 'urId',
                title: 'ID',
                align: 'center',
                sortable:true
            },{
                field: 'userName',
                title: '用户名',
                align: 'center',
                sortable:true
            },{
                field: 'nickName',
                title: '姓名',
                align: 'center',
                sortable:true
            },{
                field: 'roleCode',
                title: '角色编码',
                align: 'center',
                sortable:true 
            },{
                field: 'roleName',
                title: '角色名称',
                align: 'center',
                sortable:true
            }],
            onDblClickRow: function (row) {
                edit(row.urId);
            }
        });
    }
    //新增
    function addRecord(){
		var url = 'system/userRole/allocateRoles';
		top.openTab('分配角色', url);
	}
    //删除
    function deleteRecord(){
    	var ids = getSelectRows('data_table', 'urId');
		if(ids.length==0){
			top.msgBox('请至少选中一行！');
			return;
		}
		top.bootbox.confirm('确定要删除所选记录吗？', function(result){
			if(!result)
				return;
			$.ajax({
				type:'POST',
				url:'../../system/userRole/delete?ids='+ids,
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
    $(function () {

        initTable();
      	//隐藏列
        $('#data_table').bootstrapTable('hideColumn', 'urId');
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

        $('#btn_delete').click(function(){
        	deleteRecord();
        });
        $('#btn_export').click(function(){
        	exportToExcel();
        });
    });
    </script>
</body>
</html>
