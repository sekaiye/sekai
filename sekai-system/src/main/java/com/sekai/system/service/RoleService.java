package com.sekai.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.sekai.system.model.Role;

public interface RoleService {  
    void addRole(Role role);
    void deleteRole(Integer roleId);
    void updateRole(Role role);
    Integer getMaxId();
    Role getRole(Integer roleId);
    List<Role> getRoleList(Map<String,Object> map);
    List<Role> getAllRoles();
    List<Role> getRolesByUserName(String userName);
} 
