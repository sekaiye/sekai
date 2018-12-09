//得到所选行
function getSelectRows(tableName, columnId) {
    var arr = $('#' + tableName).bootstrapTable('getSelections');
    var results = [];
    if (arr != null) {
        for (var i = 0; i < arr.length; i++) {
            var text = arr[i][columnId];
            results.push(text);
        }
    }
  //JSON.stringify(results);
    return results;
}
//选择或者双击行
function getSelectOrDbClickRows(table,row) {
	var obj;
	if(row==null){
		var sel=table.bootstrapTable('getSelections');
		return sel;
		/*
		if(sel.length==0)
			return null;
		obj=sel[0];*/
	}else{
		obj = row;
	}
	return obj;
}
function checkBy(table,field,value) {
    $('#'+table).bootstrapTable("checkBy", {field:field, values:[value]});
}
//导出Excel
function exportToExcel(){
	var url = 'exportToExcel';
	window.open(url);
}