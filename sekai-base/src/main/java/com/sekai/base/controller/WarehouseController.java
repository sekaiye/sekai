package com.sekai.base.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sekai.system.redis.SessionUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekai.base.model.Warehouse;
import com.sekai.base.service.WarehouseService;
import com.sekai.system.utils.ExportInfo;
import com.sekai.system.utils.JsonResult;
import com.sekai.system.utils.Page;

@Controller
public class WarehouseController {
	String exportId="export_warehouse_list";
	@Autowired
	private SessionUtil sessionUtil;
	@Resource
	private WarehouseService WarehouseService;
    @RequestMapping("/base/warehouse/delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Integer[] ids){
    	for(Integer id : ids) {
    		try{
    			WarehouseService.deleteWarehouse(id);
    		}catch(Exception e){
    			System.out.println("del:"+e.getMessage());
    			return new JsonResult(0, e.getMessage());
    		}
    		System.out.println(id+ " ");
    	}
    	
		return new JsonResult(1, "");
    }
    @RequestMapping("/base/warehouse/edit")
    public String edit(HttpServletRequest request, Model model, Integer id){
    	Warehouse Warehouse = WarehouseService.getWarehouse(id);
    	model.addAttribute("warehouse",Warehouse);
		return "/base/warehouse/warehouse_edit";
    }
    @RequestMapping("/base/warehouse/save")
    public String save(Model model, HttpServletRequest request, @ModelAttribute("warehouse") Warehouse warehouse){
    	String url = "/base/warehouse/warehouse_edit";
    	if(warehouse.getWhCode().trim().equals("")) {
    		model.addAttribute("action_result", "请输入仓库编码！");
    		return url;
    	}
    	if(warehouse.getWhName().trim().equals("")) {
    		model.addAttribute("action_result", "请输入仓库名称！");
    		return url;
    	}
    	if(warehouse.getWhId() == null){
    		WarehouseService.addWarehouse(warehouse);
    		//Warehouse.setWhId(WarehouseService.getMaxId());
    	}else{
    		WarehouseService.updateWarehouse(warehouse);
    	}
    	model.addAttribute("action_result", "success");
    	return url;
    }
    @RequestMapping("/base/warehouse/list")
    @RequiresPermissions("Warehouse_view")
    public String list(Model model, HttpServletRequest request){
        return "base/warehouse/warehouse_list";
    }
    @RequestMapping("/base/warehouse/getWarehouseList")
    @ResponseBody
    public String getWarehouseList(
    		@RequestParam(required=true,defaultValue="1") Integer pageNumber,
    		@RequestParam(required=false,defaultValue="100") Integer pageSize,
    		@RequestParam(required=false) String sortOrder,
    		@RequestParam(required=false) String sortName,
    		@RequestParam(required=false) String keyword,
    		HttpServletRequest request
    		) throws Exception{
		Page<Warehouse> page = new Page<Warehouse>();
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		if(sortName !=null) { 
			page.setSortName(sortName);
			page.setSortOrder(sortOrder);
		}
		Map<String,Object> mapPage = new HashMap<>();
		mapPage.put("page", page);
		mapPage.put("keyword", keyword);
		List<Warehouse> list = WarehouseService.getWarehouseList(mapPage);
		Map<String,Object> mapExport = new HashMap<>();
		mapExport.put("sql", page.getSql());
		//设置要导出的列
		Map<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("whCode", "仓库编码");
		fields.put("whName", "仓库名称");
		ExportInfo export = new ExportInfo(fields, page.getSql());
		sessionUtil.set(exportId, export);

		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("total", page.getTotalRecord());
		mapJson.put("pageSize", pageSize);
		mapJson.put("rows", list);
		return new ObjectMapper().writeValueAsString(mapJson);
    }
    @RequestMapping("/base/warehouse/exportToExcel")
    public String exportToExcel() {
    	return "redirect:/system/excel/excelExport?exportId=" + exportId;
    }
}