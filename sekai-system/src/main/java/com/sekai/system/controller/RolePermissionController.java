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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sekai.system.model.Menu;
import com.sekai.system.model.Role;
import com.sekai.system.model.User;
import com.sekai.system.model.RolePermission;
import com.sekai.system.service.RoleService;
import com.sekai.system.service.MenuService;
import com.sekai.system.service.RolePermissionService;
import com.sekai.system.service.UserService;
import com.sekai.system.utils.ExportInfo;
import com.sekai.system.utils.JsonResult;
import com.sekai.system.utils.Page;

@Controller
public class RolePermissionController {
	@Resource
	private MenuService MenuService;
	@Resource
	private RolePermissionService rolePermissionService;
	/*
    @RequestMapping("/system/role_permission/delete")
    @ResponseBody
    public JsonResult delete(HttpServletRequest request, Integer[] ids){
    	for(Integer id : ids) {
    		try{
    			rolePermissionService.deleteRolePermission(id);
    		}catch(Exception e){
    			return new JsonResult(0, e.getMessage());
    		}
    	}
		return new JsonResult(1, "");
    }*/
    @RequestMapping("/system/rolePermission/save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request, 
    		@ModelAttribute("permissions") String permissions,
    		@ModelAttribute("roleId") Integer roleId){
    	try {
	    	rolePermissionService.deleteRolePermissionByRoleId(roleId);
	    	for(String permissionId : permissions.split(",")) {
	    		RolePermission rp= new RolePermission();
	    		rp.setPermissionId(Integer.parseInt(permissionId));
	    		rp.setRoleId(roleId);
	    		rolePermissionService.addRolePermission(rp);
	    	}
    	}catch(Exception e){
			return new JsonResult(0, e.getMessage());
		}
    	return new JsonResult(1, "");
    }
    @RequestMapping("/system/rolePermission/edit")
    public String edit(Model model, HttpServletRequest request, Integer roleId){
    	//Map<String,Object> map = new LinkedHashMap<String,Object>();

    	//StringBuilder tb = new StringBuilder();
    	StringBuilder js = new StringBuilder();
    	
		List<Map<String,Object>> permissions = rolePermissionService.getRolePermissionList(roleId);
		for(Map<String,Object> permission : permissions) {
			/*
			tb.append("<div class='chkbox'>");
			String checked="";
			if(permission.get("hasPermission").toString().equals("1"))
				checked="checked";
			tb.append("<input type='checkbox' " + checked + " class='chk' id='chk_" + permission.get("permissionId").toString() 
					+ "' name='permission' value='"+permission.get("permissionId").toString()+"'/>"
					+permission.get("permissionName").toString());
			tb.append("</div>");*/
			String permissionId=permission.get("permissionId").toString();
			String permissionCode=permission.get("permissionCode").toString();
			String parentCode=permission.get("parentCode").toString();
			String permissionName=permission.get("permissionName").toString();
			String checked="";
			if(permission.get("hasPermission").toString().equals("1"))
				checked=",checked:true";
			js.append("{ id:'"+permissionCode+"', pId:'"+parentCode+"', name:'"+permissionName+"', open:false"+checked+",permissionId:'"+permissionId+"'},");
		}
		String json=js.substring(0,js.length()-1);
    	//model.addAttribute("map",map);
    	model.addAttribute("json",json);
        return "system/role_permission/role_permission_edit";
    }
}
