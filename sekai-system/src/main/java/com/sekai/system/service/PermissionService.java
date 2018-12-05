package com.sekai.system.service;
import java.util.List;
import com.sekai.system.model.Permission;

public interface PermissionService {  
    List<Permission> getAllPermissions();
    List<Permission> getPermissionsByUserId(Integer userId);
} 
