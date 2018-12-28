;(function($){
    var defaults = {
        //grid_tb_id:"sk_grid",
        columns:[],
        datas:[],
        onButtonClick: function (rowIndex, cellIndex, field) {
            return "";
        },setEditable: function (grid_tb_id, rowIndex, cellIndex, field){
            return true;
        },onDataChanged: function (grid_tb_id, rowIndex, cellIndex){
            return true;
        },onEnter: function (grid_tb_id, rowIndex, cellIndex, field,value) {
            return true;
        }
    };
    $.fn.SKGrid = function(options){
        var opts = $.extend({},defaults,options);
        var grid_tb_id=this.get(0).id + "_table";
        var g, column, array_data, table_html, pk_id_field_index,
        curTr, curTd, cur_edit_row, cur_edit_col, curInput,
        prevTr, prevTd, prevRow, prevColumn, prevValue, cur_click_row = 0;
        var first_edit_field = -1;
        var last_edit_field = -1;
        var sys_row_num = 1;//系统预置行
        var sys_column_num = 1;//系统预置列
        this.getTable = function () {
            return document.getElementById(grid_tb_id);
        };
        this.getInputValue=function () {
            return curInput.value;
        };
        this.getColumn = function(){
            return column;
        };
        this.setColumn = function(grid_column){
            column = grid_column;
        };
        this.getEditRow = function () {
            return cur_edit_row;
        };
        this.getFieldIndex = function (field) {
            for(var i in column){
                if(column[i].field==field){
                    return parseInt(i)+sys_column_num;
                }
            }
            return sys_row_num;
        };
        this.getSysColumnHtml = function(){
            return "<input type='checkbox' name='"+grid_tb_id+"_sk_chk' class='sk-chk'/>";
        };
        this.bindData = function(grid_data){
            array_data = grid_data;
            var buttons = "<input type='button' class='sk_toolbar_btn' id='"+grid_tb_id+"_add_row' value='新增行'/>"
                        + "<input type='button' class='sk_toolbar_btn' id='"+grid_tb_id+"_insert_row' value='插入行'/>"
                        + "<input type='button' class='sk_toolbar_btn' id='"+grid_tb_id+"_del_row' value='删除行'/>";
            //var table_bar = "<div class='sk_table_bar'>" + buttons + "</div>";
            var table_bar = buttons;

            var pt = ""
            pt += "<div class='sk_toolbar'>" + table_bar + "</div>";
            
            pt += "<table id='" + grid_tb_id + "' class='sk-grid' >";
            pt += "<thead>"
            
        
            pt += "<tr class='sk_header'>";
            pt += "<th style='width:40px;text-align:center;'><input type='checkbox' id='"+grid_tb_id+"_select_all_row'></th>";
            var css;
            
            //header
            for(var i in column){
                var css = "";
                if(column[i].css!=null){
                	css="style='"+column[i].css+"'";
                }
                pt += "<th " + css + ">" + column[i].title + "</th>";
                if(column[i].editable && column[i].datefield != true){
                    if (first_edit_field==-1){
                        first_edit_field=parseInt(i)+parseInt(sys_column_num);
                        last_edit_field=parseInt(i)+parseInt(sys_column_num);
                    }else{
                        last_edit_field=parseInt(i)+parseInt(sys_column_num);
                    }
                }
            }
            pt += "</tr>";
            pt += "</thead>"
            //body
            pt += "<tbody>"

            for(var i in array_data){
                pt += "<tr><td>"+this.getSysColumnHtml()+"</td>";
                for(var j in column){
                    var field = column[j].field;
                    var val = '';
                    if (array_data[i][field]!=null)
                    	val = array_data[i][field];
                    var css = "";
                    if(column[j].css!=null){
                    	css = "style='"+column[j].css+"'";
                    }
                    pt += "<td "+css+">" + val + "</td>";
                }
            }
            pt += "</tbody>"
            pt += "</table>";
            table_html = "<div class='sk-grid-panel'>"+ pt+"</div>";
        };
        
        this.setWidth = function (width) {
            var obj = document.getElementById(grid_tb_id);
            obj.style.width = width;
        };
        //是否正在编辑
        var is_editing = false;
        this.Init = function () {
            g = document.getElementById(grid_tb_id);
            g.style.width = g.offsetWidth + "px";
            g.onclick = function (evt) {
                if(cur_click_row != undefined)
                    g.rows[cur_click_row].bgColor = "#FFFFFF";
                var thisTd;
                try{
                    thisTd = window.event.srcElement;
                }catch(e){
                    thisTd = evt.target;
                }
                cur_click_row = thisTd.parentElement.rowIndex;
                if(cur_click_row >= sys_row_num)
                    thisTd.parentElement.bgColor = "#faf8b8";//当前选中行背景色


                var td_html = thisTd.outerHTML.toUpperCase();
                if (td_html.indexOf("<INPUT") > -1
                    || td_html.indexOf("<IMG") > -1
                    || td_html.indexOf("<SPAN") > -1
                    || td_html.indexOf("<SELECT") > -1
                    || thisTd.nodeName.toUpperCase() == "INPUT")
                    return;
                if (thisTd.cellIndex == 0 || thisTd.parentElement.rowIndex == 0)
                    return;
                try{
                    curTr = thisTd.parentNode;
                }catch(er){
                    curTr = thisTd.parentElement;
                }
                //得到当前单元格所在列的index
                var cellIndex = getCellIndex(thisTd);
                curTd = thisTd;
                if (curTr.rowIndex != null && cellIndex != null
                    && prevRow != null && prevColumn != null) {
                    if (prevRow != curTr.rowIndex || prevColumn != cellIndex) {
                        if (prevTd.children[0] != null) {
                            //alert(td_html)
                            var combo=column[cellIndex - sys_column_num].combo;
                            if(combo!=null && combo!=undefined) {
                                prevTd.innerHTML = $("#" + grid_tb_id + "_sk_input").find("option:selected").text();
                            }else{
                                prevTd.innerHTML = prevTd.children[0].value;
                            }
                            //只有改变了值才计算
                            if (prevValue != prevTd.innerHTML) {
                                //行计算
                                opts.onDataChanged(grid_tb_id,prevRow, prevColumn);
                            }
                        }
                        is_editing = false;
                    }
                }
                if (cur_click_row < sys_row_num)
                    return;
                is_editing = true;
                var td_width = curTd.offsetWidth;
                var inputWidth = "100%";
                if (cellIndex >= 0 && column[cellIndex - sys_column_num].editable == true) {
                    var inputReadonly="";
                    var readonly=column[cellIndex - sys_column_num].readonly;
                    var iconbtn=column[cellIndex - sys_column_num].iconbtn;
                    var bEditable=opts.setEditable(grid_tb_id,curTr.rowIndex, cellIndex,column[cellIndex - sys_column_num].field)
                    if (!bEditable || readonly){
                    	inputReadonly=" readonly='readonly'";
                    }
                    var datefield=""
                    if(column[cellIndex - sys_column_num].datefield==true){
                    	datefield="onmousedown=\"laydate.render({elem: '#"+grid_tb_id+"_sk_input'});\"";
                    }
                    //var field=column[cellIndex-sys_column_num].field;
                    if (iconbtn && bEditable) {
                        var tdWidth=curTd.offsetWidth;
                        inputWidth = (tdWidth-20)+'px';
                    }
                    var curTd_html = "<input type='text' id='"+grid_tb_id+"_sk_input' class='sk-input' style='width:" + inputWidth
                                    + ";height:30px;float:left;' value='" + curTd.innerHTML + "'"+inputReadonly+datefield+"/>";

                    if (iconbtn && bEditable) {
                        curTd_html += "<span class='sk_select' id='"+grid_tb_id+"_sk_input_btn'/>";
                    }
                    var combo=column[cellIndex - sys_column_num].combo;
                    if(combo!=null){
                        curTd_html = "<select id='"+grid_tb_id+"_sk_input' class='sk-input' style='width:" + inputWidth
                            + ";height:30px;float:left;'>";
                        curTd_html += "<option value=''></option>";
                        for(var i in combo){
                            curTd_html += "<option value='"+combo[i].value+"'>"+combo[i].text+"</option>";
                        }
                        curTd_html += "</select>";
                    }
                    curTd.innerHTML = curTd_html;
                    cur_edit_row = curTr.rowIndex;
                    cur_edit_col = cellIndex;
                    var cellIndex = getCellIndex(curTd);
                    if (iconbtn){
                        $("#"+grid_tb_id+"_sk_input_btn").click(function(){

                            opts.onButtonClick(curTr.rowIndex,cellIndex,column[cellIndex - sys_column_num].field);
                        });
                    }
                    if (combo!=null){
                        $("#"+grid_tb_id+"_sk_input").change(function(){
                            g.rows[cur_edit_row].cells[cellIndex+1].innerHTML="123";
                        });
                    }
                }
                if (curTd.children[0] != null) {
                    curInput = curTd.children[0];
                    try{
                    	if(column[cellIndex - sys_column_num].datefield!=true
                    	|| column[cellIndex - sys_column_num].datefield == undefined){
                    		curInput.focus();
                    		curInput.select();
                    	}
                    } catch (err) { 
                    }
                }
                prevRow = curTr.rowIndex;
                prevColumn = cellIndex;
                prevTr = curTr;
                prevTd = curTd;
                if (document.getElementById(grid_tb_id+"_sk_input") != undefined) {
                    prevValue = document.getElementById(grid_tb_id+"_sk_input").value;
                }
            }
        };
        //取消编辑状态
        this.cancelEdit = function () {
            if (prevTd == null)
                return;
            if (prevTd.children == undefined)
                return;
            if (prevTd.children[0] == undefined)
                return;
            prevTd.innerHTML = prevTd.children[0].value;
            opts.onDataChanged(grid_tb_id,prevRow, prevColumn);//计算行
        };
        this.moveCell = function (keyCode) {
            //如果jQuery UI Combogrid下拉查询已出现，禁止键盘方向键移动
            //if (jq_combogrid_show)
            //    return;

            if (curTd == null)
                return;
            //得到当前单元格所在列的index
            var cellIndex = getCellIndex(curTd);
            //右
            if (keyCode == "39") {
                if (cellIndex == g.rows[0].cells.length - 1)
                    return;
                //最右可编辑列
                if (last_edit_field != -1) {
                    if (cellIndex == last_edit_field)
                        return;
                }
                if (g.rows[curTr.rowIndex].cells[cellIndex + 1] == undefined)
                    return;
                g.rows[curTr.rowIndex].cells[cellIndex + 1].click();
                var curTd_html = curTd.outerHTML.toUpperCase();
                if ((curTd_html.indexOf("<INPUT") == -1 && curTd_html.indexOf("<SELECT") == -1) || curTd_html.indexOf("DISPLAY") > -1
                		|| curTd_html.indexOf("LAYDATE.RENDER") > -1) {
                    this.moveCell(keyCode);
                }
                return;
            }
            //左
            if (keyCode == "37") {
                if (cellIndex == 0 + sys_column_num)
                    return;
                //最左可编辑列
                if (first_edit_field != -1) {
                    if (cellIndex == first_edit_field)
                        return; 
                }
                if (g.rows[curTr.rowIndex].cells[cellIndex - 1] == undefined)
                    return;
                g.rows[curTr.rowIndex].cells[cellIndex - 1].click();
                var curTd_html = curTd.outerHTML.toUpperCase();
                if ((curTd_html.indexOf("<INPUT") == -1 && curTd_html.indexOf("<SELECT") == -1) || curTd_html.indexOf("DISPLAY") > -1
                		|| curTd_html.indexOf("LAYDATE.RENDER") > -1) {
                    this.moveCell(keyCode);
                }
                return;
            }
            //上
            if (keyCode == "38") {
                if (curTr.rowIndex <= sys_row_num)
                    return;
                g.rows[curTr.rowIndex - 1].cells[cellIndex].click();
                return;
            }
            //下
            if (keyCode == "40") {
                if (curTr.rowIndex == g.rows.length - 1)
                    return;
                g.rows[curTr.rowIndex + 1].cells[cellIndex].click();
                return;
            }
        };
        this.insertRowCore = function (row) {
        	var insertTr = g.insertRow(row);
            for(var col=0;col<column.length+sys_column_num;col++){
                var td=insertTr.insertCell(col);
                if(col==0){
                    td.innerHTML = this.getSysColumnHtml();
                }else if(col<=column.length){
                	if(column[col-sys_column_num].css!=null)
                		td.setAttribute('style',column[col-sys_column_num].css);
                }
            }
        };
        this.addRow = function (add_rows_num) {
            this.cancelEdit();
            if(g.rows[cur_click_row] != undefined)
                g.rows[cur_click_row].bgColor = "#FFFFFF";
            for (var ri = 0; ri < add_rows_num; ri++) {
            	this.insertRowCore(g.rows.length);
            }
        };
        this.insertRow = function () {
            this.cancelEdit();
            if(cur_click_row==0)
                return;
            if(g.rows[cur_click_row] == undefined)
                return;
            g.rows[cur_click_row].bgColor = "#FFFFFF";
            this.insertRowCore(cur_click_row);
        };
        this.copyRow = function () {
            //var new_row = g.rows[cur_click_row].cloneNode(true);
            //g.rows[cur_click_row].parentNode.appendChild(new_row);
            //g.rows[cur_click_row].parentNode.insertBefore(new_row, g.rows[cur_click_row].nextSibling);
        };
        this.deleteRow = function () {
            var chk = document.getElementsByName(grid_tb_id+"_sk_chk");
            for (var i = chk.length-1; i >= 0; i--) {
                if (chk[i].checked) {
                    var rowIndex = chk[i].parentNode.parentNode.rowIndex;
                    g.deleteRow(rowIndex);
                    //i = -1;
                }
            }
        };
        var is_select_all = false;
        this.selectAllRow = function(){
            var chk = document.getElementsByName(grid_tb_id+"_sk_chk");
            is_select_all = !is_select_all;
            for (var i = 0; i < chk.length; i++) {
                chk[i].checked = is_select_all;
            }
        };
        //通过单元格得到所在列index，解决IE隐藏列后获取index不对的bug
        function getCellIndex(aCell) {
            var aRow = aCell.parentNode;
            for (var i = 0; i != aRow.cells.length; i++) {
                if (aRow.cells[i] == aCell)
                    return i;
            }
            return false;
        };

        this.createGrid = function(){
            //document.write(table_html);
            $(this).html(table_html);
            this.Init();
            $("#" + grid_tb_id).freezeHeader();
            //$("#" + grid_tb_id).colResizable();
        };
        this.setTableWidth = function (tb_width) {
            var act_width = tb_width + 30;
            g.style.width = act_width + 'px';
        };
        this.getTableHtml = function(){
            return table_html;
        };
        this.setColumn(opts.columns);
        this.bindData(opts.datas);
        this.createGrid();
        var that=this;

        this.each(function(){
            //各种功能
            var _this = $(this);
            _this.find('#'+grid_tb_id+'_add_row').click(function(evt){
                that.addRow(1)
            });
            _this.find('#'+grid_tb_id+'_insert_row').click(function(evt){
                that.insertRow()
            });
            _this.find('#'+grid_tb_id+'_del_row').click(function(evt){
                that.deleteRow();
            });
            _this.find('#'+grid_tb_id+'_select_all_row').click(function(evt){
                that.selectAllRow();
            });
            _this.find('#'+grid_tb_id).keyup(function(evt){
                var keyCode;
                try{
                    keyCode = evt.keyCode;
                }catch(e){
                    keyCode = window.event.keyCode;
                }
                that.moveCell(keyCode);
            });
            _this.find('#'+grid_tb_id).keydown(function(evt){
                var keyCode;
                try{
                    keyCode = evt.keyCode;
                }catch(e){
                    keyCode = window.event.keyCode;
                }
                if(keyCode=='13'){
                    var cellIndex = getCellIndex(curTd);
                    var value=curInput.value;
                    opts.onEnter(grid_tb_id,curTr.rowIndex, cellIndex,column[cellIndex - sys_column_num].field,value);
                }
            });
        });

        return this;
    }


})(jQuery);