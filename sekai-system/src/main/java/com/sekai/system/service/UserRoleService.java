package com.sekai.system.service;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sekai.system.model.UserRole;

public interface UserRoleService {  
    void addUserRole(UserRole UserRole);
    void deleteUserRole(Integer urId);
    Integer getMaxId();
    UserRole getUserRole(Integer urId);
    List<UserRole> getUserRoleList(Map<String,Object> map);
    Integer checkExist(Integer userId,Integer roleId);
} 
