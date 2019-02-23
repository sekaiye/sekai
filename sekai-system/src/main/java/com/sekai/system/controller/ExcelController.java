package com.sekai.system.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sekai.system.redis.RedisUtil;
import com.sekai.system.redis.SessionUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sekai.system.service.DirectSqlService;
import com.sekai.system.utils.ExportInfo;

@Controller
public class ExcelController {
	@Autowired
	private SessionUtil sessionUtil;
	@Resource
	private DirectSqlService directSqlService;
	
    @RequestMapping("/system/excel/excelExport")
    public String excelExport(HttpServletRequest request, HttpServletResponse response, 
    		Model model,
    		String exportId, String downloadFileName) throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet=wb.createSheet("Sheet1");
		ExportInfo export = (ExportInfo)sessionUtil.get(exportId);
		HSSFRow row=sheet.createRow(0);
		int i=0;
		for (String colName : export.getExportFields().values()) {
			HSSFCell cell=row.createCell(i);
			cell.setCellValue(colName);
			i++;
		}
		String sql = export.getSql();
		System.out.println(sql);
		List<Map<String, Object>> list = directSqlService.getList(sql);
		int j=1;
        for (Map<String, Object> map : list) {
        	HSSFRow row2=sheet.createRow(j);
        	i=0;
	        for (String colName : export.getExportFields().keySet()) {
	        	HSSFCell cell=row2.createCell(i);
	        	String value = "";
	        	if(map.get(colName) != null)
	        		value = map.get(colName).toString();
				cell.setCellValue(value);
	        	i++;
	        }
	        j++;
        }
	    OutputStream output=response.getOutputStream();
	    response.reset();
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
 
	    String filename = downloadFileName + "_" + df.format(new Date())+".xls";
		//filename = URLEncoder.encode(filename, "UTF-8");
		response.setHeader("Content-disposition", "attachment; filename="+filename);
		response.setContentType("application/msexcel");        
		wb.write(output);
		wb.close();
		output.close();
		return "/system/excel/excel_export";

    }
}