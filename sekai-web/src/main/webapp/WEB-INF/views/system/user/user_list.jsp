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
			<shiro:hasPermission name="user_add">
		    	<button type="button" id="btn_add" class="btn btn-default" >
		        <span class="glyphicon glyphicon-plus"></span>增加</button>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="user_modify">
				<button type="button" id="btn_modify" class="btn btn-default" >
				<span class="glyphicon glyphicon-pencil"></span>修改</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="user_delete">
		    	<button type="button" id="btn_delete" class="btn btn-default" >
		        <span class="glyphicon glyphicon-remove"></span>删除</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="user_modify">
				<div class="btn-group" >
				    <button type="button" class="btn btn-default" id="btn_enable">激活</button>
				    <button type="button" class="btn btn-default dropdown-toggle" 
				        data-toggle="dropdown">
				        <span class="caret"></span>
				    </button>
				    <ul class="dropdown-menu" role="menu">
				        <li><a href="#" id="btn_forbid">禁用</a></li>
				    </ul>
				</div>
			</shiro:hasPermission>
			<shiro:hasPermission name="user_add">
				<button type="button" id="btn_import" class="btn btn-default" >
		        <span class="glyphicon glyphicon-import"></span>导入</button>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="user_export">
				<button type="button" id="btn_export" class="btn btn-default" >
		        <span class="glyphicon glyphicon-export"></span>导出</button>
			</shiro:hasPermission>
		</div>
		<div class="form-inline" id="searchbar">
			<button type="button" id="btn_dlg_select" class="btn btn-info">选择</button>
			<div class="input-group">
				<input type="text" class="form-control" style="width:auto" placeholder="关键字"  id="keyword">
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
    	//table_box.offsetHeight=table_box.offsetHeight-180;
	    var table_height = table_box.offsetHeight;
        $('#data_table').bootstrapTable({
            method: 'get',
            //toolbar: '#toolbar',    //工具按钮用哪个容器
            striped: true,      //是否显示行间隔色
            showToggle: false,
            cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,     //是否显示分页（*）
            sortable: true,      //是否启用排序
            resizable: false,
            pageNumber:1,      //初始化加载第一页，默认第一页
            pageSize: 50,      //每页的记录行数（*）
            pageList: [50,100,500,1000,5000],  //可供选择的每页的行数（*）
            url: "getUserList.do",
            queryParamsType:'', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
                                // 设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
            queryParams: queryParams,//前端调用服务时，会默认传递上边提到的参数，如果需要添加自定义参数，可以自定义一个函数返回请求参数
            sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
            //showColumns: true,     //是否显示所有的列
            //showRefresh: true,     //是否显示刷新按钮
            minimumCountColumns: 2,    //最少允许的列数
            //clickToSelect: true,    //是否启用点击选中行
            checkbox : true,
            height:table_height,
            columns: [{
                field: 'check',
                title: '选择',
                checkbox : true,
                align: 'center'
            },{
                field: 'userId',
                title: '用户ID',
                align: 'center',
                sortable:true
            }, {
                field: 'userName',
                title: '用户名',
                align: 'center',
                sortable:true,
                    formatter:function(value,row,index){
                        //value：当前field的值，即id
                        //row：当前行的数据
                    	return '<a href=\'javascript:edit('+row.userId+')\'>'+value+'</a>';
                    } 
            }, {
                field: 'nickName',
                title: '姓名',
                align: 'center',
                sortable:true
            },{
                field: 'email',
                title: 'email',
                align: 'center',
                sortable:true
            }, {
                field: 'cellphone',
                title: '手机号',
                align: 'center',
                sortable:true
            },{
                field: 'forbid',
                title: '禁用状态',
                align: 'center',
                sortable:true
            }],
            onDblClickRow: function (row) {
                if('${param.dlgSelect}'=='1'){
            		selectThis(row);
                }else{
                	edit(row.userId);
                }
            }
        });
    }
    //新增
    function addRecord(){
		var url = 'system/user/edit?';
		top.openTab('用户 -新增', url);
		//top.showDialog(url,null);
	}
	//修改
    function edit(id){
		var url='system/user/edit?id=' + id;
		top.openTab('用户-修改', url);
    }
    function editRecord(){
    	var ids = getSelectRows('data_table', 'userId');
		if(ids.length!=1){
			top.msgBox('请选中一行！');
			return;
		}
		edit(ids);
	}
    //删除
    function deleteRecord(){
    	var ids = getSelectRows('data_table', 'userId');
		if(ids.length==0){
			top.msgBox('请至少选中一行！');
			return;
		}
		top.bootbox.confirm('确定要删除所选记录吗？', function(result){
			if(!result)
				return;
			$.ajax({
				type:'POST',
				url:'../../system/user/delete?ids='+ids,
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
    	var ids = getSelectRows('data_table', 'userId');
		if(ids.length==0){
			top.msgBox('请至少选中一行！');
			return;
		}
		top.bootbox.confirm('确定要'+statusName+'所选记录吗？', function(result){
			if(!result)
				return;
			$.ajax({
				type:'POST',
				url:'../../system/user/setStatus?ids='+ids+'&status='+status,
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
  	//导入Excel
  	function importExcel(){
        var template=encodeURIComponent('导入模板_用户.xlsx');
    	var url = 'file/importExcel/upload?readExcelAction=../../system/user/importExcel&template='+template;
    	top.showDialogSmall("导入",url);
    }

    function selectThis(row){
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
        $('#keyword').keydown(function(e){
        	//搜索回车事件
        	if(e.keyCode==13){
        		searchTable();
        	}
		});
        /*
        laydate.render({
        	elem: '#begindate'
		});*/
		$('#btn_dlg_select').click(function(){
        	selectThis(null);
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
        $('#btn_import').click(function(){
        	importExcel();
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
