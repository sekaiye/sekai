package com.sekai.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekai.system.model.Role;
import com.sekai.system.model.User;
import com.sekai.system.model.UserRole;
import com.sekai.system.service.RoleService;
import com.sekai.system.service.UserRoleService;
import com.sekai.system.service.UserService;
import com.sekai.system.utils.ExportInfo;
import com.sekai.system.utils.JsonResult;
import com.sekai.system.utils.Page;

@Controller
public class UserRoleController {
	String exportId = "export_user_role_list";
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;

    @RequestMapping("/system/userRole/delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Integer[] ids){
    	for(Integer id : ids) {
    		try{
    			userRoleService.deleteUserRole(id);
    		}catch(Exception e){
    			return new JsonResult(0, e.getMessage());
    		}
    	}
		return new JsonResult(1, "");
    }

    @RequestMapping("/system/userRole/edit")
    public String edit(HttpServletRequest request, Model model, Integer id){
    	UserRole userRole = userRoleService.getUserRole(id);
    	model.addAttribute("userRole",userRole);
    	User user=null;
    	Role role=null;
    	if(userRole!=null) {
	    	user = userService.getUser(userRole.getUserId());
	    	role = roleService.getRole(userRole.getRoleId());

    	}
    	model.addAttribute("user",user);
    	model.addAttribute("role",role);

		return "/system/user_role/user_role_edit";
    }
    @RequestMapping("/system/userRole/allocateRoles")
    public String allocateRoles(){
		return "/system/user_role/allocate_roles";
    }
    @RequestMapping("/system/userRole/save")
    @ResponseBody
    public JsonResult save(Model model, HttpServletRequest request,
    		@ModelAttribute("json_Users") String json_Users,
    		@ModelAttribute("json_Roles") String json_Roles) throws Exception{
    	ObjectMapper jackson = new ObjectMapper();
    	List<Role> listRole=jackson.readValue(json_Roles,new TypeReference<List<Role>>() { });
    	List<User> listUser=jackson.readValue(json_Users,new TypeReference<List<User>>() { });
    	
    	for(User user : listUser) {
    		for(Role role:listRole) {
    			if(userRoleService.checkExist(user.getUserId(), role.getRoleId())>0) {
    				//此用户已拥有该角色,无须再添加
    				continue;
    	    	}
    			UserRole userRole=new UserRole();
    			userRole.setUserId(user.getUserId());
    			userRole.setRoleId(role.getRoleId());
    			userRoleService.addUserRole(userRole);
    		}
    	}
    	return new JsonResult(1, "success");
    }
    @RequestMapping("/system/userRole/list")
    public String list(Model model, HttpServletRequest request){
        return "system/user_role/user_role_list";
    }
    @RequestMapping("/system/userRole/getUserRoleList")
    @ResponseBody
    public String getUserRoleList(
    		@RequestParam(required=true) Integer pageNumber,
    		@RequestParam(required=true) Integer pageSize,
    		@RequestParam(required=false) String sortOrder,
    		@RequestParam(required=false) String sortName,
    		@RequestParam(required=false) String keyword,
    		HttpServletRequest request, HttpSession session
    		) throws Exception{
		Page<UserRole> page = new Page<UserRole>();
		page.setPageNo(pageNumber);
		page.setPageSize(pageSize);
		if(sortName !=null) { 
			page.setSortName(sortName);
			page.setSortOrder(sortOrder);
		}
		Map<String,Object> mapPage = new HashMap<>();
		mapPage.put("page", page);
		mapPage.put("keyword", keyword);
		List<UserRole> list = userRoleService.getUserRoleList(mapPage);
		Map<String,Object> mapExport = new HashMap<>();
		mapExport.put("sql", page.getSql());
		//设置要导出的列
		Map<String, String> fields = new LinkedHashMap<String, String>();
		fields.put("userName", "用户名");
		fields.put("nickName", "姓名");
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
    @RequestMapping("/system/userRole/exportToExcel")
    public String exportToExcel() {
    	return "redirect:/system/excel/excelExport?exportId=" + exportId;
    }
}