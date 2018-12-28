<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>

<html>
<head>
	<title></title>
    <jsp:include page="../../include/bill_header.jsp"></jsp:include>
</head>
<body>
	<div class="btn-toolbar" id="edit_toolbar">
		<div class="btn-group">
			<shiro:hasPermission name = "purchase_in_modify">
				<button type="button" id="btn_save" class="btn btn-primary">保存</button>
			</shiro:hasPermission>
			<button type="button" id="btn_back" class="btn btn-default" onclick="top.closeTab()">返回</button>
		</div>
	</div>
	<div class="form-group">
    	<div class="col-sm-4">
	    	<label for="billNum">单据号</label>
	    	<input type="hidden" id="id" name="id" value="${inout.id}">
	    	<input type="text" class="form-control" id="billNum" name="billNum" value="${inout.billNum}" />
		</div>
    	<div class="col-sm-4">
	    	<label for="billDate">单据日期</label>
	    	<input type="text" class="form-control" id="billDate" name="billDate" value="${inout.billDate}" />
    	</div>
    	<div class="col-sm-4">
	    	<label for="billDate">仓库</label>
	    	<input type="text" class="form-control" id="whName" name="whName" value="${inout.warehouse.whName}" />
    	</div>
    	<div class="col-sm-4">
	    	<label for="billDate">创建人</label>
	    	<input type="text" class="form-control" id="createNickName" name="createNickName" value="${inout.createUser.nickName}" />
    	</div>
  	</div>
  	
	<div id="div_sk_grid"></div>
	<script type='text/javascript'>
		$(function () {
			var id='${inout.id}';
			var json=${inouts};
		    var skGrid=$("#div_sk_grid").SKGrid({
		        columns:[
		        	{field:"ids",title:"ids",css:"width:80;display:none;"},
		        	{field:"matId",title:"matId",css:"width:80;display:none;"},
		            {field:"matCode",title:"物料",editable:true,css:"width:140px;color:blue;"},
		            {field:"matName",title:"物料名称",css:"width:120px"},
		            {field:"spec",title:"规格型号",css:"width:140px"},
		            {field:"unit",title:"单位",css:"width:60px"},
		            {field:"price",title:"单价", css:"width:70px",editable:true},
		            {field:"taxprice",title:"含税单价", css:"width:70px",editable:false},
		            {field:"qty",title:"数量", css:"width:70px",editable:true},
		            {field:"amount",title:"金额", css:"width:150px",editable:true},
		            {field:"taxamount",title:"含税金额", css:"width:150px",editable:false},
		            {field:"tax",title:"税金", css:"width:150px",editable:false},
					{field:"yesNo",title:"是否", css:"width:80px",editable:true,
						combo:[{text:"是",value:"1"},{text:"否",value:"0"}],
						comboValueField:"yesNoValue"
					},
					{field:"yesNoValue",title:"是否值", css:"width:60px",editable:false},
		            {field:"remark",title:"备注", css:"width:150px",editable:true}
		        ],datas:json,
		        onButtonClick: function (rowIndex, cellIndex, field) {
		            var btnEvt = "";
		            if (field == 'matCode') {
		                alert('onButtonClick');
		            }
		        },setEditable: function (grid_tb_id, rowIndex, cellIndex, field){
		            /*
		            var cells = skGrid.getTable().rows[rowIndex].cells;
		            if(field == "matNum"){
		                if(cells[skGrid.getFieldIndex("qty")].innerHTML > 2){
		                    return false;
		                }
		            }*/
		            return true;
		        },onDataChanged: function (grid_tb_id, rowIndex, cellIndex){
		            var cells = skGrid.getTable().rows[rowIndex].cells;
		            if (cellIndex == _price || cellIndex == _qty){

		                var price = cells[_price].innerHTML;
		                var qty = cells[_qty].innerHTML;
		                var amount = price*qty;
		                cells[_amount].innerHTML = amount.toFixed(2);
		            }
		        }
		    });
		    var _ids=skGrid.getFieldIndex("ids");
		    var _matId=skGrid.getFieldIndex("matId");
		    var _price=skGrid.getFieldIndex("price");
		    var _qty=skGrid.getFieldIndex("qty");
		    var _amount=skGrid.getFieldIndex("amount");
		    var _remark=skGrid.getFieldIndex("remark");
	        laydate.render({elem: '#billDate'})
 
	        $('#btn_save').click(function(){
	        	skGrid.cancelEdit();
	        	var json='[';
	        	var table=skGrid.getTable();
	        	for (var row=1;row<table.rows.length;row++){
	        		json+='{';
		        	var cells = skGrid.getTable().rows[row].cells;
		            var ids=cells[_ids].innerHTML;
		            var matId=cells[_matId].innerHTML;
		            var price=cells[_price].innerHTML;
		            var qty=cells[_qty].innerHTML;
		            var amount=cells[_amount].innerHTML;
		            var remark=cells[_remark].innerHTML;
		            json+='"ids":"'+ids+'",'
		            	+'"id":"'+id+'",'
			            +'"matId":"'+matId+'",'
			            +'"price":"'+price+'",'
			            +'"qty":"'+qty+'",'
			            +'"amount":"'+amount+'",'
			            +'"remark":"'+remark+'"';
					json+='},';
	        	}
	        	if(json!=''){
	        		json='['+json+']';
	        	}
	        	$.ajax({  
		        	type : "POST",
		        	url : "save",  
		            data : "id="+id+"&json="+json,  
		            success : function(result) {  
		            	if(result.success == 1){
		        			top.msgBox('保存成功!');
		        		}else{
		        			top.msgBox(result.data);
		        		}
		            }  
		        });
	        });
			skGrid.addRow(5);
	     });

    </script>
</body>
</html>
