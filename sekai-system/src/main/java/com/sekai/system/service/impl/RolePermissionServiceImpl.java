package com.sekai.system.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sekai.system.mapper.RolePermissionMapper;
import com.sekai.system.model.RolePermission;
import com.sekai.system.service.RolePermissionService;

@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {  
    @Resource
    private RolePermissionMapper mapper;
    public void addRolePermission(RolePermission userRole){
    	this.mapper.addRolePermission(userRole);
    }
    public void deleteRolePermission(Integer raId){
    	this.mapper.deleteRolePermission(raId);
    }
    public void deleteRolePermissionByRoleId(Integer roleId){
    	this.mapper.deleteRolePermissionByRoleId(roleId);
    }
    public RolePermission getRolePermission(Integer raId){
        return this.mapper.getRolePermission(raId);
    }
    public boolean checkUserHasPermission(Integer userId, String permissionCode){
        if(this.mapper.checkUserHasPermission(userId, permissionCode)>0)
        	return true;
        return false;
    }
    public boolean checkLoginUserHasPermission(HttpSession session,String permissionCode){
    	Integer userId=Integer.parseInt(session.getAttribute("userId").toString());
    	//判断当前登录用户是否超级管理员
    	if(session.getAttribute("isAdmin") != null) {
			if(session.getAttribute("isAdmin").toString().equals("1"))
				return true;
		}
        if(this.mapper.checkUserHasPermission(userId, permissionCode)>0)
        	return true;
        return false;
    }
    public List<Map<String,Object>> getRolePermissionList(Integer roleId){
    	return this.mapper.getRolePermissionList(roleId);
    }
}  
