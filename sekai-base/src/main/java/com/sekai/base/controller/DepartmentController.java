package com.sekai.base.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekai.base.model.Department;
import com.sekai.base.service.DepartmentService;
import com.sekai.system.utils.ExportInfo;
import com.sekai.system.utils.JsonResult;
import com.sekai.system.utils.Page;

@Controller
public class DepartmentController {
	String exportId="export_department_list";
	@Resource
	DepartmentService departmentService;
    @RequestMapping("/base/department/delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Integer[] ids){
    	for(Integer id : ids) {
    		try{
    			departmentService.deleteDepartment(id);
    		}catch(Exception e){
    			System.out.println("del:"+e.getMessage());
    			return new JsonResult(0, e.getMessage());
    		}
    		System.out.println(id+ " ");
    	}
    	
		return new JsonResult(1, "");
    }
    @RequestMapping("/base/department/edit")
    public String edit(HttpServletRequest request, Model model, Integer id){
    	Department Department = departmentService.getDepartment(id);
    	model.addAttribute("department",Department);
		return "/base/department/department_edit";
    }
    @RequestMapping("/base/department/save")
    public String save(Model model, HttpServletRequest request, @ModelAttribute("department") Department department){
    	String url = "/base/department/department_edit";
    	if(department.getDeptCode().trim().equals("")) {
    		model.addAttribute("action_result", "请输入部门编码！");
    		return url;
    	}
    	if(department.getDeptName().trim().equals("")) {
    		model.addAttribute("action_result", "请输入部门名称！");
    		return url;
    	}
    	if(request.getParameter("parentName").trim().equals("")) {
    		department.setParentId(0);
    	}
    	if(department.getDeptId() == null){
    		departmentService.addDepartment(department);
    	}else{
    		departmentService.updateDepartment(department);
    	}
    	model.addAttribute("action_result", "success");
    	return url;
    }
    @RequestMapping("/base/department/list")
    @RequiresPermissions("Department_view")
    public String list(Model model, HttpServletRequest request){
        return "base/department/department_list";
    }
    @RequestMapping("/base/department/getDepartmentList")
    @ResponseBody
    public String getDepartmentList(
    		@RequestParam(required=true,defaultValue="1") Integer pageNumber,
    		@RequestParam(required=false,defaultValue="100") Integer pageSize,
    		@RequestParam(required=false) String sortOrder,
    		@RequestParam(required=false) String sortName,
    		@RequestParam(required=false) String keyword,
    		HttpServletRequest request, HttpSession session
    		) throws Exception{
		Page<Department> page = new Page<Department>();
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		if(sortName !=null) { 
			page.setSortName(sortName);
			page.setSortOrder(sortOrder);
		}
		Map<String,Object> mapPage = new HashMap<>();
		mapPage.put("page", page);
		mapPage.put("keyword", keyword);
		List<Department> list = departmentService.getDepartmentList(mapPage);
		Map<String,Object> mapExport = new HashMap<>();
		mapExport.put("sql", page.getSql());
		//设置要导出的列
		Map<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("deptCode", "部门编码");
		fields.put("deptName", "部门名称");
		fields.put("parentName", "上级部门");
		fields.put("forbidName", "禁用状态");
		ExportInfo export = new ExportInfo(fields, page.getSql());
		session.setAttribute(exportId, export);

		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("total", page.getTotalRecord());
		mapJson.put("pageSize", pageSize);
		mapJson.put("rows", list);
		return new ObjectMapper().writeValueAsString(mapJson);
    }
    @RequestMapping("/base/department/exportToExcel")
    public String exportToExcel() {
    	return "redirect:/system/excel/excelExport?exportId=" + exportId 
    			+ "&downloadFileName=部门列表导出";
    }
}