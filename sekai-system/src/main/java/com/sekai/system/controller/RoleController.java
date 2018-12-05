package com.sekai.system.controller;

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
import com.sekai.system.model.Role;
import com.sekai.system.service.RoleService;
import com.sekai.system.utils.ExportInfo;
import com.sekai.system.utils.JsonResult;
import com.sekai.system.utils.Page;
/*角色控制器*/
@Controller
public class RoleController {
	String exportId = "export_role_list";
	@Resource
	RoleService roleService;
    @RequestMapping("/system/role/delete")
    @RequiresPermissions("role_delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Integer[] ids){
    	System.out.println(ids.toString());
    	for(Integer id : ids) {
    		try{
    			roleService.deleteRole(id);
    		}catch(Exception e){
    			return new JsonResult(0, e.getMessage());
    		}
    	}
		return new JsonResult(1, "");
    }
    @RequestMapping("/system/role/setStatus")
    @RequiresPermissions("role_modify")
    @ResponseBody
    public JsonResult setStatus(HttpServletRequest request, Integer[] ids, Integer status){
    	for(Integer id : ids) {
    		try{
    			Role r = roleService.getRole(id);
    			roleService.updateRole(r);
    		}catch(Exception e){
    			return new JsonResult(0, e.getMessage());
    		}
    	}
		return new JsonResult(1, "");
    }
    @RequestMapping("/system/role/edit")
    @RequiresPermissions("role_view")
    public String edit(HttpServletRequest request, Model model, Integer id){
    	if(id !=null) {
    		Role role = roleService.getRole(id);
    		model.addAttribute("role",role);
    	}
		return "/system/role/role_edit";
    }
    @RequestMapping("/system/role/save")
    @RequiresPermissions("role_modify")
    public String save(Model model, HttpServletRequest request, @ModelAttribute("role") Role role){
    	String url = "/system/role/role_edit";
    	if(role.getRoleCode().trim().equals("")) {
    		model.addAttribute("action_result", "请输入角色编码！");
    		return url;
    	}
    	if(role.getRoleName().trim().equals("")) {
    		model.addAttribute("action_result", "请输入角色名称！");
    		return url;
    	}
    	if(role.getRoleId() == null){
    		roleService.addRole(role);
    		//role.setRoleId(roleService.getMaxId());
    	}else{
    		roleService.updateRole(role);
    	}
    	model.addAttribute("action_result", "success");
    	return url;
    }
    @RequestMapping("/system/role/list")
    @RequiresPermissions("role_view")
    public String list(Model model, HttpServletRequest request)throws Exception{
        return "system/role/role_list";
    }
    @RequestMapping("/system/role/getRoleList")
    @ResponseBody
    public String getRoleList(
    		@RequestParam(required=true) Integer pageNumber,
    		@RequestParam(required=true) Integer pageSize,
    		@RequestParam(required=false) String sortOrder,
    		@RequestParam(required=false) String sortName,
    		@RequestParam(required=false) String keyword,
    		HttpServletRequest request, HttpSession session
    		) throws Exception{
    	/*
    	Enumeration pNames=request.getParameterNames();  
    	while(pNames.hasMoreElements()){  
    	    String name=(String)pNames.nextElement();  
    	    String value=request.getParameter(name);  
    	    System.out.println(name + "=" + value);  
    	}
    	*/
		Page<Role> page = new Page<Role>();
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		if(sortName !=null) { 
			page.setSortName(sortName);
			page.setSortOrder(sortOrder);
		}
		Map<String,Object> mapPage = new HashMap<>();
		mapPage.put("page", page);
		mapPage.put("keyword", keyword);
		List<Role> list = roleService.getRoleList(mapPage);
		Map<String,Object> mapExport = new HashMap<>();
		mapExport.put("sql", page.getSql());
		//设置要导出的列
		Map<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("roleCode", "角色编码");
		fields.put("roleName", "角色名称");
		ExportInfo export = new ExportInfo(fields, page.getSql());
		session.setAttribute(exportId, export);

		Map<String, Object> mapJson = new HashMap<String, Object>();
		mapJson.put("total", page.getTotalRecord());
		mapJson.put("pageSize", pageSize);
		mapJson.put("rows", list);
		return new ObjectMapper().writeValueAsString(mapJson);
    }
    @RequestMapping("/system/role/exportToExcel")
    public String exportToExcel() {
    	return "redirect:/system/excel/excelExport?exportId=" + exportId;
    }
}