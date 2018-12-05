package com.sekai.system.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.sekai.system.model.RolePermission;

public interface RolePermissionService {  
    void addRolePermission(RolePermission RolePermission);
    void deleteRolePermission(Integer rpId);
    void deleteRolePermissionByRoleId(Integer roleId);
    RolePermission getRolePermission(Integer rpId);
    boolean checkUserHasPermission(Integer userId, String permissionCode);
    boolean checkLoginUserHasPermission(HttpSession session, String permissionCode);
    List<Map<String,Object>> getRolePermissionList(Integer roleId);
} 
