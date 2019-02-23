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
import com.sekai.base.model.Material;
import com.sekai.base.service.MaterialService;
import com.sekai.system.utils.ExportInfo;
import com.sekai.system.utils.JsonResult;
import com.sekai.system.utils.Page;

@Controller
public class MaterialController {
	String exportId="export_material_list";
	@Autowired
	private SessionUtil sessionUtil;
	@Resource
	private MaterialService MaterialService;
    @RequestMapping("/base/material/delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Integer[] ids){
    	for(Integer id : ids) {
    		try{
    			MaterialService.deleteMaterial(id);
    		}catch(Exception e){
    			System.out.println("del:"+e.getMessage());
    			return new JsonResult(0, e.getMessage());
    		}
    		System.out.println(id+ " ");
    	}
    	
		return new JsonResult(1, "");
    }
    @RequestMapping("/base/material/edit")
    public String edit(HttpServletRequest request, Model model, Integer id){
    	Material Material = MaterialService.getMaterial(id);
    	model.addAttribute("material",Material);
		return "/base/material/material_edit";
    }
    @RequestMapping("/base/material/save")
    public String save(Model model, HttpServletRequest request, @ModelAttribute("material") Material material){
    	String url = "/base/material/material_edit";
    	if(material.getMatCode().trim().equals("")) {
    		model.addAttribute("action_result", "请输入物料编码！");
    		return url;
    	}
    	if(material.getMatName().trim().equals("")) {
    		model.addAttribute("action_result", "请输入物料名称！");
    		return url;
    	}
    	if(material.getMatId() == null){
    		MaterialService.addMaterial(material);
    		//Material.setMatId(MaterialService.getMaxId());
    	}else{
    		MaterialService.updateMaterial(material);
    	}
    	model.addAttribute("action_result", "success");
    	return url;
    }
    @RequestMapping("/base/material/list")
    @RequiresPermissions("Material_view")
    public String list(Model model, HttpServletRequest request){
        return "base/material/material_list";
    }
    @RequestMapping("/base/material/getMaterialList")
    @ResponseBody
    public String getMaterialList(
    		@RequestParam(required=true,defaultValue="1") Integer pageNumber,
    		@RequestParam(required=false,defaultValue="100") Integer pageSize,
    		@RequestParam(required=false) String sortOrder,
    		@RequestParam(required=false) String sortName,
    		@RequestParam(required=false) String keyword,
    		HttpServletRequest request
    		) throws Exception{
		Page<Material> page = new Page<Material>();
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		if(sortName !=null) { 
			page.setSortName(sortName);
			page.setSortOrder(sortOrder);
		}
		Map<String,Object> mapPage = new HashMap<>();
		mapPage.put("page", page);
		mapPage.put("keyword", keyword);
		List<Material> list = MaterialService.getMaterialList(mapPage);
		Map<String,Object> mapExport = new HashMap<>();
		mapExport.put("sql", page.getSql());
		//设置要导出的列
		Map<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("matId", "id");
		fields.put("matCode", "物料编码");
		fields.put("matName", "物料名称");
		fields.put("spec", "规格型号");
		fields.put("unit", "单位");
		fields.put("spec", "规格型号");
		ExportInfo export = new ExportInfo(fields, page.getSql());
		sessionUtil.set(exportId, export);

		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("total", page.getTotalRecord());
		mapJson.put("pageSize", pageSize);
		mapJson.put("rows", list);
		return new ObjectMapper().writeValueAsString(mapJson);
    }
    @RequestMapping("/base/material/exportToExcel")
    public String exportToExcel() {
    	return "redirect:/system/excel/excelExport?exportId=" + exportId;
    }
}